/****************************************************************
 *
 * 파일명 : postemplate.js
 * 설  명 : 포스출력물관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.04     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 템플릿 그리드 생성
 */
app.controller('templateCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('templateCtrl', $scope, $http, false));
  // 텍스트 show 설정
  $scope.showTempltRegFgNm = false;
  $scope.templtEditableTxt = "수정불가";
  // 버튼 show 설정
  $scope.showBtnApplyStore = false;
  // 매장적용 버튼은 본사만
  if ( "H" === gvOrgnFg ) {
    $scope.showBtnApplyStore = true;
  } else {
    $scope.showBtnApplyStore = false;
  }
  $scope.showBtnSaveEdit = false;
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchPrtClassCdCombo", prtClassComboData);
  $scope.isCombo = false;
  // 조회조건 콤보박스 change event
  $scope.setPrtClassCdCombo = function(s) {
    if($scope.isCombo) {
      $scope._broadcast("templateCtrl");
    }
  };
  // 조회조건 콤보박스 처음 로딩시 조회되지 않도록.
  $scope.prtClassCdComboFocus = function(s,e) {
    $scope.isCombo = true;
  };
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "gChk") {
          // 자신이 등록한것만 수정/삭제 가능 ( 본사/매장 )
          if (item.templtRegFg !== gvOrgnFg || item.templtCd === "000") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
            e.cell.children[0].disabled = true;
          }
        }
        // 템플릿명 ReadOnly 효과
        if (col.binding === "templtNm") {
          // 자신이 등록한것만 수정 가능 ( 본사/매장 )
          if (item.templtRegFg !== gvOrgnFg || item.templtCd === "000") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 템플릿 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "templtNm") {
        var item = s.rows[e.row].dataItem;
        // 자신이 등록한것만 수정 가능 ( 본사/매장 )
        if (item.templtRegFg !== gvOrgnFg || item.templtCd === "000") {
          e.cancel = true;
        }
      }
    });
    // 템플릿 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var selectedRow = s.rows[ht.row].dataItem;
        var col = ht.panel.columns[ht.col];
        if (col.binding === "templtNm" && selectedRow.status !== "I") {
          if (selectedRow.prtForm != null) {
            theTarget.value = selectedRow.prtForm;
            makePreview();
          } else {
            theTarget.value = "";
            thePreview.innerHTML = "";
          }
          // 그리드 저장버튼 show
          $("#btnSaveTemplate").show();

          $scope.$apply(function() {
            if (selectedRow.templtRegFg === "C") {
              $scope.templtRegFgNm = "시스템";
            } else if (selectedRow.templtRegFg === "H") {
              $scope.templtRegFgNm = "본사";
            } else {
              $scope.templtRegFgNm = "매장";
            }
            $scope.showTempltRegFgNm = true;
            // 본사는 상위에서 내려준걸 수정 하지 못한다.
            if ( gvOrgnFg === selectedRow.templtRegFg ) {
              $scope.templtEditableTxt = "수정가능";
              theTarget.disabled = false;
              if ( "H" === gvOrgnFg ) {
                $scope.showBtnSaveEdit = true;
              }
            } else {
              $scope.templtEditableTxt = "수정불가";
              theTarget.disabled = true;
              if ( "H" === gvOrgnFg ) {
                $scope.showBtnSaveEdit = false;
              }
            }
          });
        }
      }
    });
  };
  // 템플릿 그리드 조회
  $scope.$on("templateCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/output/posTemplate/template/list.sb", params, function() {
        // 코드리스트 조회
        $scope._postJSONQuery.withOutPopUp("/base/output/posTemplate/code/list.sb", params,
          function(response) {
            var list = response.data.data.list;
            $scope.listBoxCodeList.itemsSource = list;
            if (list.length === undefined || list.length === 0) {
              // 코드리스트 초기화
              $scope.listBoxCodeList.itemsSource = new wijmo.collections.CollectionView([]);
              // 편집/미리보기 폼 초기화
              theTarget.value = "";
              thePreview.innerHTML = "";
            } else {
              $("#btnAddTemplate").show();
              $("#btnDelTemplate").show();
              $("#btnSaveTemplate").show();

              if ($scope.flex.rows.length > 0) {
                $scope.flex.select(0,1);
                var selectedRow = $scope.flex.rows[0].dataItem;
                if (selectedRow.templtRegFg === "C") {
                  $scope.templtRegFgNm = "시스템";
                } else if (selectedRow.templtRegFg === "H") {
                  $scope.templtRegFgNm = "본사";
                  // 매장적용 버튼은 본사만
                  $scope.showBtnApplyStore = true;
                } else {
                  $scope.templtRegFgNm = "매장";
                }
                $scope.showTempltRegFgNm = true;
                // 자신이 등록한것만 수정 가능
                if ( gvOrgnFg === selectedRow.templtRegFg ) {
                  $scope.templtEditableTxt = "수정가능";
                  $scope.showBtnSaveEdit = true;
                  theTarget.disabled = false;
                } else {
                  $scope.templtEditableTxt = "수정불가";
                  $scope.showBtnSaveEdit = false;
                  theTarget.disabled = true;
                }
                theTarget.value = nvl($scope.flex.rows[0]._data.prtForm, '');
                makePreview();
              } else {
                $scope.showTempltRegFgNm = false;
                // 편집/미리보기 폼 초기화
                theTarget.value = "";
                thePreview.innerHTML = "";
              }
            }
          }
        );
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 템플릿 그리드 행 추가
  $scope.addRow = function() {
    // 편집/미리보기 폼 초기화
    theTarget.value = "";
    thePreview.innerHTML = "";
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.prtClassCd = $scope.prtClassCdCombo.selectedValue;
    params.templtRegFg = gvOrgnFg;
    params.gChk = true;
    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };
  // 템플릿 그리드 삭제
  $scope.delete = function() {
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.itemsSource.removeAt(i);
      }
    }
  };
  // 템플릿 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = [];
    for (var u = 0; u < $scope.flex.collectionView.itemsEdited.length; u++) {
      $scope.flex.collectionView.itemsEdited[u].status = "U";
      $scope.flex.collectionView.itemsEdited[u].prtForm = theTarget.value;
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].prtForm = theTarget.value;
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/base/output/posTemplate/template/saveList.sb", params, function(){
      // 저장 후 재조회
      $scope._broadcast('templateCtrl');
    });
  };
  // 템플릿 편집 저장버튼
  $scope.saveEditTemplate = function() {
    var selectedRow = $scope.flex.selectedRows[0]._data;
    var params = {};
    params.prtClassCd = $scope.prtClassCdCombo.selectedValue;
    params.templtRegFg = selectedRow.templtRegFg;
    params.templtCd = selectedRow.templtCd;
    params.templtNm = selectedRow.templtNm;
    params.prtForm = theTarget.value;

    // 자신이 등록한것만 수정 가능하므로 분기처리
    if ( gvOrgnFg === selectedRow.templtRegFg ) {
      $scope._postJSONSave.withOutPopUp("/base/output/posTemplate/template/save.sb", params,
        function (response) {
          // 본사 또는 실제출력물 인 경우 패스
          if ( "H" !== gvOrgnFg && selectedRow.templtCd !== "000") {
            $scope._popConfirm(messages['cmm.saveSucc'] + "<br><br>해당 템플릿을 실제출력물에 업데이트 하시겠습니까?",
              function () {
                var nParams = {};
                nParams.prtClassCd = params.prtClassCd;
                nParams.templtRegFg = params.templtRegFg;
                nParams.templtCd = params.templtCd;
                nParams.prtForm = params.prtForm;

                $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToPrint.sb", nParams,
                  function (response) {
                    // 저장 후 재조회
                    $scope._broadcast('templateCtrl');
                  }
                );
              }
            );
          } else {
            // 실제출력물인경우 저장완료 팝업표시
            $scope._popMsg(messages['cmm.saveSucc']);
          }
          $scope.flex.collectionView.clearChanges();
          $scope._broadcast('templateCtrl');
        }
      );
    } else {
      // 자신이 등록하지 않은 템플릿은 수정할 수 없으므로 실제출력물에만 적용한다.
      $scope._popConfirm("해당 템플릿을 실제출력물에 업데이트 하시겠습니까?",
        function () {
          var nParams = {};
          nParams.prtClassCd = params.prtClassCd;
          nParams.templtRegFg = params.templtRegFg;
          nParams.templtCd = params.templtCd;
          nParams.prtForm = params.prtForm;

          $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToPrint.sb", nParams,
            function (response) {
              $scope.flex.collectionView.clearChanges();
              $scope._broadcast('templateCtrl');
            }
          );
        }
      );
    }
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  };
  // 매장 적용 버튼
  $scope.applyToStoreTemplate = function() {
    $scope._popConfirm("전체 매장에 해당 템플릿을 적용하시겠습니까?<br>매장에 템플릿이 존재하는 경우, 업데이트 처리됩니다.",
      function() {
        var selectedRow = $scope.flex.selectedRows[0]._data;
        var params = {};
        params.prtClassCd = $scope.prtClassCdCombo.selectedValue;
        params.templtRegFg = selectedRow.applyTempltRegFg;
        params.templtCd = selectedRow.templtCd;

        $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToStore.sb", params,
          function (response) {
            $scope.flex.collectionView.clearChanges();
            $scope._broadcast('templateCtrl');
          }
        );
      }
    );
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  };
  // 리스트박스 초기화 : 드래그설정
  $scope.initListBox = function(s, e) {
    // 드래그 사용하도록 설정
    s.formatItem.addHandler(function(s, e) {
      e.item.setAttribute('draggable', 'true');
    });
    // 클릭드래그시 선택이벤트 발생
    s.hostElement.addEventListener("mousedown", function (e) {
      s.selectedValue = e.target.innerText;
    });
    // 드래그 시작이벤트
    s.hostElement.addEventListener('dragstart', function (e) {
      var mData = {
        prtCd: s.selectedValue,
        content: s.itemsSource[s.selectedIndex].content
      };
      var dragRow = JSON.stringify(mData);
      // 드래그 데이터 set
      e.dataTransfer.setData("text", dragRow);
    }, true);
  };
}]);

// 편집/미리보기 폼 element 할당
var theTarget = document.getElementById('editTextArea');
var thePreview = document.getElementById('preview');

// 드래그시 이벤트 설정
theTarget.addEventListener('dragover', function (e) {
  // prtCd 값으로 판단하여 copy 모드 설정
  var dragRow = e.dataTransfer.getData("text");
  if (dragRow != null) {
    e.dataTransfer.dropEffect = 'copy';
    e.preventDefault();
  }
});

// listBox 아이템 드랍이벤트
theTarget.addEventListener('drop', function (e) {

  if ( !theTarget.disabled ) {
    var scope = agrid.getScope("templateCtrl");
    if (scope.flex.rows.length > 0) {
      var dragRow = JSON.parse(e.dataTransfer.getData("text"));
      var prtCd = dragRow.prtCd;
      var content = dragRow.content;

      // 출력물코드가 있는 경우에만 작동
      if (prtCd != null) {
        var strOriginal = theTarget.value;
        var iStartPos = theTarget.selectionStart;
        var iEndPos = theTarget.selectionEnd;
        var strFront = "";
        var strEnd = "";
        // textarea 의 커서 위치 구해서 커서위치에 값 넣기
        if (iStartPos === iEndPos) {
          strFront = strOriginal.substring(0, iStartPos);
          strEnd = strOriginal.substring(iStartPos, strOriginal.length);
        } else {
          return;
        }
        theTarget.value = strFront + prtCd + strEnd;
        // 미리보기 적용
        makePreview();
      }
    } else {
      scope.$apply(function() {
        scope._popMsg("템플릿을 먼저 등록해주세요.");
      });
    }
  }
  e.preventDefault();
});

// 키이벤트 (키보드수정시 이벤트발생)
theTarget.addEventListener('keyup', function (e) {
  makePreview();
});

// 미리보기 적용
function makePreview() {

  var value = theTarget.value;
  var codeLen = 0;
  // 리스트박스 데이터 가져옴
  var scope = agrid.getScope('templateCtrl');
  var listBoxData = scope.listBoxCodeList.itemsSource;
  // {} 코드값 정규식 처리
  var matches = value.match(/\{([^}]+)\}/gm);
  if (matches != null) {
    // 정규식처리된 문자 처리
    for (var k = 0; k < matches.length; k++) {
      for (var l = 0; l < listBoxData.length; l++) {
        if (listBoxData[l].prtCd === matches[k] && listBoxData[l].content != null) {
          // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
          value = value.replace(matches[k], listBoxData[l].content);
        }
      }
    }
  }
  value = value.replace(/\r\n|\n/g, "</P><P>").replace(new RegExp("<P></P>", "g"), "<P>&nbsp;</P>");
  // 특수태그용 정규식 처리
  var exceptMatches = value.match(/\{:([LRC])\d{2}\}.*?\{\/:\1\}?/g);
  if (exceptMatches != null) {
    for (var i = 0; i < exceptMatches.length; i++) {
      // 추출된 특수태그를 정규식에 의해 분리 ( 추출퇸태그/첫문자/바이트길이/내용 )
      var textSplit = exceptMatches[i].match(/\{:([LRC])(\d{2})\}(.*?)\{\/:\1\}/);
      codeLen = parseInt(textSplit[2]);
      if (codeLen <= 42) {
        // 내용에 태그 길이 측정
        var tagMatches = textSplit[3].match(/(<([^>]+)>)/gi);
        var tagLen = 0;
        if (!isEmpty(tagMatches)) {
          for (var j = 0; j < tagMatches.length; j++) {
            tagLen += tagMatches[j].length;
          }
        }
        // 바이트길이 만큼 좌우 여백채우기
        value = value.replace(textSplit[0], textSplit[3].setPadding(textSplit[1], " ", codeLen + tagLen));
      }
    }
  }
  // 라인별로 글자수 체킹
  var lineArray = ("<P>" + value + "</P>").match(/<P>.*?<\/P>?/g);
  var newValues = [];
  var newLine = 0;
  var splitStr = "";
  if (lineArray != null) {
    for (var m = 0; m < lineArray.length; m++) {
      lineArray[m] = lineArray[m].replace(/<P>|<\/P>?/g, "");
      if (lineArray[m].getByteLength() <= 42 || !isEmpty(lineArray[m].match(/<img src.*?>/g)) || !isEmpty(lineArray[m].match(/<font.*?>/g))) {
        newValues[newLine++] = lineArray[m];
      } else {
        splitStr = lineArray[m].splitByteLen(42);
        for (var n = 0; n < splitStr.length; n++) {
          newValues[newLine++] = splitStr[n];
        }
      }
    }
  }
  thePreview.innerHTML = "<PRE><P>" + newValues.join("</P><P>") + "</P></PRE>";
}
