"use strict";
!function (win, $) {
  // angular Grid 생성
  var agrid = {
    genGrid: function (appName, ctrlName, isPicker) {
      var app = angular.module(appName, ['wj']);
      app.controller(ctrlName, ['$scope', '$http', function ($scope, $http) {
        $scope.search = function (url, param, callback) {
          $http({
            method: 'POST', //방식
            url: url, /* 통신할 URL */
            params: param, /* 파라메터로 보낼 데이터 */
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
          }).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            var list = response.data.data.list;
            if (list.length === undefined || list.length === 0) {
              $scope.data = new wijmo.collections.CollectionView([]);
              s_alert.pop(response.data.message);
              return;
            }
            var data = new wijmo.collections.CollectionView(list);
            data.trackChanges = true;
            $scope.data = data;
          }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);
            s_alert.pop(response.data.message);
            return;
          }).then(function() {
            // "complete" code here
            if ( callback != null ) {
              setTimeout(function() {
                callback;
              }, 10);
            }
          });
        }
        $scope.addNewRow = function(values, pos) {
          var flex = $scope.flex;
          flex.collectionView.trackChanges = true;
          flex.select(flex.rows.length, 0);

          var newRow = flex.collectionView.addNew();
          newRow.status = "I";
          newRow.gChk = true;
          for (var prop in values) {
            newRow[prop] = values[prop];
          }
          flex.collectionView.commitNew();
          // 추가된 Row 선택
          flex.scrollIntoView(flex.rows.length - 1, 0);
          flex.select(flex.rows.length - 1, 1);
          setTimeout(function() {
            flex.startEditing(true, flex.rows.length - 1, (pos === null ? 0 : pos), true);
            flex.focus();
          }, 10);
        }
        // show column picker for the grid
        $scope.makePickColumns = function (appName, toMake) {
          if (toMake) {
            // create column picker (once)
            if (!$scope.picker) {
              $scope.picker = new wijmo.grid.ColumnPicker('#'+appName);
              $scope.picker.orgColumns = $scope.flex.columns;
            }
            // show column picker in a dialog
            $scope.picker.grid = $scope.flex;
            $scope.pickColumn.show(true, function (s) {
              var dr = s.dialogResult;
              if (dr && dr.indexOf('apply') > -1) {
                $scope.picker.save();
              }
            });
          }
        }
        // show grid and restore scroll position
        $scope.initGrid = function (s, e) {
          // prevent editing 'expenses' if 'editable' is false
          s.beginningEdit.addHandler(function (sender, e) {
            if (sender.columns[e.col].binding !== 'gChk') {
              if (!sender.rows[e.row].dataItem.gChk) {
                e.cancel = true;
              } else {
                setTimeout(function () {
                  var _cellData = s.getCellData(e.row, e.col, true);
                  if (sender.activeEditor !== null && sender.activeEditor.value !== "") {
                    wijmo.setSelectionRange(sender.activeEditor, _cellData.length); // caret position
                  }
                }, 0);
              }
            }
          });
          if (isPicker) {
            s.hostElement.addEventListener('mousedown', function(e) {
              var ht = s.hitTest(e);
              if( ht.cellType === wijmo.grid.CellType.TopLeft) {
                $scope.makePickColumns(appName, isPicker);
                e.preventDefault();
              }
            });
          }
        }
        $scope.itemFormatter = function (panel, r, c, cell) {
          // 컬럼헤더 merged 의 헤더타이틀 중앙(vertical) 정렬
          if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
            var mRange = $scope.flex.getMergedRange(panel, r, c);
            if (mRange) {
              cell.innerHTML = "<div class='wj-header merged-custom'>" + cell.innerHTML + "</div>";
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
        }
      }]);
      app.factory('myHttpInterceptor', function ($timeout, $q) {
        return {
          'request': function (config) {
            $("#_loadTent, #_loading").show();
            return config;
          },
          'requestError': function (rejectionRequest) {
            console.log("from REQUEST ERROR")
            $("#_loadTent, #_loading").hide();
            return $q.reject("Couldnot have a successfull request, Sorry :(");
          },
          'response': function (response) {
            $("#_loadTent, #_loading").hide();
            return response;
          },
          'responseError': function (rejectionRequest) {
            console.log("from RESPONSE ERROR")
            $("#_loadTent, #_loading").hide();
            return $q.reject(rejectionRequest)
          }

        }
      });
      app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
      });
      return app;
    },
    getGrid: function (div) {
      var grid = angular.element(document.getElementById(div)).scope();
      return grid;
    }
  };

  win.agrid = agrid;

}("undefined" != typeof window ? window : this, jQuery);















