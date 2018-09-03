"use strict";

function RootController(ctrlName, $scope, $http, isPicker) {
  // set $scope Name
  $scope.name = ctrlName;
  // 그리드 초기화
  $scope._gridDataInit = function () {
    $scope.data = new wijmo.collections.CollectionView([]);
  };
  // 조회 : 마스터 그리드
  $scope._inquiryMain = function (url, params, callback) {
    $scope._inquiry(url, params, callback, true, true);
  };
  // 조회 : 서브 그리드
  $scope._inquirySub = function (url, params, callback, isView) {
    if (!isView) {
      isView = true;
    }
    $scope._inquiry(url, params, callback, isView, false);
  };
  // 그리드 조회
  $scope._inquiry = function (url, params, callback, isView, isMaster) {
    if (isMaster) {
      var el = angular.element('input');
      var name = '';
      for (var i = 0, l = el.length; i < l; i += 1) {
        name = angular.element(el[i]).attr('ng-model');
        if (name && $scope[name]) {
          params[name] = $scope[name];
        }
      }
    }
    // 페이징 처리
    if ($scope.getCurr() > 0) {
      params['curr'] = $scope.getCurr();
    } else {
      params['curr'] = 1;
    }
    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: url, /* 통신할 URL */
      params: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // this callback will be called asynchronously
      // when the response is available
      var list = response.data.data.list;
      if (list.length === undefined || list.length === 0) {
        $scope.data = new wijmo.collections.CollectionView([]);
        if (isView) {
          $scope.s_alert(response.data.message);
        }
        return;
      }
      var data = new wijmo.collections.CollectionView(list);
      data.trackChanges = true;
      $scope.data = data;

      // 페이징 처리
      if (response.data.data.page.curr) {
        $scope.pagingInfo = response.data.data.page;
        $scope.pagingInfo.ctrlName = $scope.name;
        $scope._broadcast('drawPaging', $scope.pagingInfo);
      }

    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope.s_alert(messages["cmm.error"]);
      // console.log(response);
      return;
    }).then(function () {
      // "complete" code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });
  };
  // 행 추가
  $scope._addRow = function (params, pos) {
    var flex = $scope.flex;
    if (!flex.collectionView) {
      flex.itemsSource = new wijmo.collections.CollectionView();
    }
    var newRow = flex.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;
    for (var prop in params) {
      newRow[prop] = params[prop];
    }
    flex.collectionView.trackChanges = true;
    flex.collectionView.commitNew();
    // 추가된 Row 선택
    setTimeout(function () {
      flex.scrollIntoView(flex.rows.length - 1, 0);
      flex.select(flex.rows.length - 1, 1);
      flex.focus();
      flex.startEditing(true, flex.rows.length - 1, (pos === null ? 0 : pos), true);
    }, 50);
  };
  // 저장
  $scope._save = function (url, params, callback) {
    // 길이체크
    if (params.length <= 0) {
      $scope.s_alert(messages["cmm.not.modify"]);
      return;
    } else {
      params = JSON.stringify(params);
    }
    // ajax 통신 설정
    $http({
      method: 'POST', //방식
      url: url, /* 통신할 URL */
      data: params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      // this callback will be called asynchronously
      // when the response is available
      $scope.s_alert(messages["cmm.saveSucc"]);
      $scope.flex.collectionView.clearChanges();
    }, function errorCallback(response) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      $scope.s_alert(messages["cmm.saveFail"]);
      // console.log(response);
      return;
    }).then(function () {
      // "complete" code here
      if (callback != null) {
        setTimeout(function () {
          callback;
        }, 10);
      }
    });
  };
  // itemFormatter 기본설정
  $scope._itemFormatter = function (panel, r, c, cell) {
    // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
    if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
      var mRange = $scope.flex.getMergedRange(panel, r, c);
      if (mRange) {
        cell.innerHTML = "<div class='wj-header merged-custom'>" + cell.innerHTML + "</div>";
      }
      // picker 를 위한 설정
    } else if (panel.cellType === wijmo.grid.CellType.TopLeft) {
      if (!isPicker) {
        $(cell).css({"background": "none", "background-color": "#e8e8e8"});
      }
      // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
    } else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
      // GroupRow 인 경우에는 표시하지 않는다.
      if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
        cell.textContent = "";
      } else {
        if (!isEmpty(panel._rows[r]._data.rnum)) {
          cell.textContent = (panel._rows[r]._data.rnum).toString();
        } else {
          cell.textContent = (r + 1).toString();
        }
      }
      // readOnly 배경색 표시
    } else if (panel.cellType === wijmo.grid.CellType.Cell) {
      var col = panel.columns[c];
      if (col.isReadOnly) {
        wijmo.addClass(cell, 'wj-custom-readonly');
      }
    }
  };
  // 에디팅 관련 기본설정
  $scope.$watch('flex', function () {
    var flex = $scope.flex;
    if (flex) {
      flex.beginningEdit.addHandler(function (s, e) {
        if (s.columns[e.col].binding !== 'gChk') {
          // 선택(gChk) 되어있지 않으면 수정 불가
          if (!s.rows[e.row].dataItem.gChk) {
            e.cancel = true;
            flex.endUpdate();
          } else {
            if (s.columns[e.col].dataType !== wijmo.DataType.Boolean) {
              setTimeout(function () {
                var _cellData = s.getCellData(e.row, e.col, true);
                if (s.activeEditor !== null && s.activeEditor.value !== "") {
                  wijmo.setSelectionRange(s.activeEditor, _cellData.length); // caret position
                }
              }, 0);
            }
          }
        }
      });
      flex.cellEditEnded.addHandler(function (s, e) {
        // console.log('Cell Edit End');
      });
      flex.rowEditEnded.addHandler(function (s, e) {
        // console.log('Row edit end');
      });
    }
  });
  // get a grid cell from the row/col indices
  $scope.getGridCell = function (r, c) {
    var flex = $scope.flex;
    // find the cell from its bounding rectangle
    var rc = flex.getCellBoundingRect(r, c);
    var cell = document.elementFromPoint(rc.left + rc.width / 2, rc.top + rc.height / 2);

    // make sure this is a regular cell (not a header)
    if (wijmo.hasClass(cell, 'wj-header')) {
      cell = null;
    }

    // make sure this is not an element within a cell
    while (cell && !wijmo.hasClass(cell, 'wj-cell')) {
      cell = cell.parentElement;
    }

    // ready!
    return cell;
  }
  // columnPicker 생성
  $scope._makePickColumns = function (ctrlName) {
    var flex = $scope.flex;
    if (flex && isPicker) {
      flex.hostElement.addEventListener('mousedown', function (e) {
        var ht = flex.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.TopLeft) {
          // create column picker (once)
          if (!$scope.picker) {
            $scope.picker = new wijmo.grid.ColumnPicker('#' + ctrlName);
            $scope.picker.orgColumns = $scope.flex.columns;
          }
          // show column picker in a dialog
          $scope.picker.grid = $scope.flex;
          var pickerPopup = $scope.colPicker;
          pickerPopup.show(true, function (s) {
            var dr = s.dialogResult;
            if (dr && dr.indexOf('apply') > -1) {
              $scope.picker.save();
            }
          });
          e.preventDefault();
        }
      });
    }
  };
};

!function (win, $) {
  var app = angular.module('rootApp', ['wj', 'ngSanitize']);
  // main-controller
  app.controller('rootCtrl', ['$scope', '$http', '$compile', 'comboData', 'pagingData',
    function ($scope, $http, $compile, comboData, pagingData) {
      // 조회
      $scope._broadcast = function (controllerName, params) {
        $scope.$broadcast('init');
        $scope.$broadcast(controllerName, params);
      };
      // 페이징조회
      $scope._pagingView = function (ctrlName, curr) {
        $scope.setCurr(curr);
        $scope.$broadcast(ctrlName);
      };
      // 로딩 메시지 팝업 열기
      $scope.$on('loadingPopupActive', function () {
        $scope._loadingTent.show(true);
      });
      // 로딩 메시지 팝업 닫기
      $scope.$on('loadingPopupInactive', function () {
        $scope._loadingTent.hide();
      });
      // 메시지 팝업
      $scope.s_alert = function (msg, callback) {
        $scope.s_alert_msg = msg;
        setTimeout(function () {
          $scope._alertTent.show(true, function () {
            if (typeof callback === 'function') {
              setTimeout(function () {
                callback();
              }, 10);
            }
          });
        }, 100);
      };
      // 페이징 HTML
      $scope.$on('drawPaging', function (event, pagingInfo) {
        // 페이징바 갯수
        var page_scale = pagingInfo.pageScale;
        var page_end = page_scale == 10 ? 9 : 4;
        // 버튼 태그 동적 생성
        var prevBtnTag = "<li class=\"btn_previous\" data-tot={tot}><a href=\"javascript:;\"></a></li>";
        var pageBtnTag = "<li><a href=\"javascript:;\" class=\"{cnm}\" data-value={i} ng-click=\"_pagingView('{ctrlName}', '{i}');\">{i}</a></li>";
        var nextBtnTag = "<li class=\"btn_next\" data-curr={curr}><a href=\"javascript:;\"></a></li>";
        var pagerTag = "";

        var item = {};
        item.ctrlName = pagingInfo.ctrlName;
        item.curr = pagingInfo.curr;
        item.tot = pagingInfo.totCnt;
        item.start = 0;
        item.end = 0;
        // 페이징 계산
        var t = pagingInfo.curr / page_scale;
        if (t.toString().indexOf(".") === -1) {
          item.end = pagingInfo.curr;
          item.start = item.end - page_end;
        } else {
          item.start = (parseInt(t) * page_scale) + 1;
          item.end = item.start + page_end;
        }
        if (item.end > pagingInfo.totalPage) {
          item.end = pagingInfo.totalPage;
        }
        // 페이징 제작
        if (pagingInfo.totCnt > page_scale) {
          pagerTag += wijmo.format(prevBtnTag, item);
        }
        for (var i = item.start; i <= item.end; i++) {
          item.i = i;
          item.cnm = i === pagingInfo.curr ? "on pagenav" : "pagenav";
          pagerTag += wijmo.format(pageBtnTag, item);
        }
        if (pagingInfo.totalPage > page_scale) {
          pagerTag += wijmo.format(nextBtnTag, item);
        }

        var pager = $compile(pagerTag)($scope);
        var pagerName = pagingInfo.ctrlName + 'Pager';
        angular.element(document.getElementById(pagerName)).children().remove();
        angular.element(document.getElementById(pagerName)).append(pager);

      });
      // 페이징 Data Setter
      $scope.setCurr = function (idx) {
        pagingData.set(idx);
      };
      // 페이징 Data Getter
      $scope.getCurr = function () {
        return pagingData.get();
      };
      // 콤보박스 Data Setter
      $scope.setComboData = function (id, data) {
        comboData.set(id, data);
      };
      // 콤보박스 Data Getter
      $scope.getComboData = function (id) {
        return comboData.get(id);
      };
      // 콤보박스 초기화.. ng-model 사용하기 위한 설정 : 20180831 노현수
      $scope.initComboBox = function (s) {
        s._tbx.id = s._orgAtts.id.value;
        s._tbx.setAttribute("ng-model", s._orgAtts['ng-model']);
        s._tbx.attributes['ng-model'].value = s._orgAtts['ng-model'].value;
      };
    }]);
  app.directive('compile', ['$compile', function ($compile) {
    return function (scope, element, attrs) {
      scope.$watch(
        function (scope) {
          return scope.$eval(attrs.compile);
        },
        function (value) {
          element.html(value);
          $compile(element.contents())(scope);
        }
      )
    };
  }]);
  app.factory('myHttpInterceptor', function ($timeout, $q, $rootScope) {
    return {
      'request': function (config) {
        $rootScope.$broadcast('loadingPopupActive');
        return config;
      },
      'requestError': function (rejectionRequest) {
        console.log("from REQUEST ERROR")
        $rootScope.$broadcast('loadingPopupInactive');
        return $q.reject("Couldnot have a successfull request, Sorry :(");
      },
      'response': function (response) {
        $rootScope.$broadcast('loadingPopupInactive');
        return response;
      },
      'responseError': function (rejectionRequest) {
        console.log("from RESPONSE ERROR")
        $rootScope.$broadcast('loadingPopupInactive');
        return $q.reject(rejectionRequest)
      }
    }
  }).factory('comboData', function () {
    var comboDataMap = [];
    comboDataMap.set = function (id, data) {
      comboDataMap[id] = data;
    }
    comboDataMap.get = function (id) {
      return comboDataMap[id];
    }
    return comboDataMap;
  }).factory('pagingData', function () {
    var currentPage = {};
    currentPage.set = function (idx) {
      currentPage.value = idx;
    }
    currentPage.get = function () {
      return currentPage.value;
    }
    return currentPage;
  });
  app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
  });
  // angular Grid 생성
  var agrid = {
    getApp: function () {
      return app;
    },
    getScope: function (ctrlName) {
      var sel = 'div[ng-controller="' + ctrlName + '"]';
      return angular.element(sel).scope();
    }
  };

  win.agrid = agrid;

}("undefined" != typeof window ? window : this, angular);















