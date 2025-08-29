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

// 언어타입 콤보박스
var langTypeData = [
    {"name":"한글","value":"ko"},
    {"name":"영문","value":"en"},
    {"name":"중문","value":"cn"},
    {"name":"일문","value":"jp"}
];

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
  $("#btnSaveEditTemplate").hide();
  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchPrtClassCdCombo", prtClassComboData);
  $scope._setComboData("langType", langTypeData);
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
  theTarget.disabled = true;
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

          // 템플릿 선택시, 기본 한글 템플릿 부터 보이도록 set
          $scope.langTypeCombo.selectedIndex = 0;

          if (selectedRow.prtForm != null) {
            theTarget.value = selectedRow.prtForm;
            makePreview();
          } else {
            theTarget.value = "";
            thePreview.innerHTML = "";
          }

          // 템플릿 클릭시, 템플릿 편집이 가능
          $("#divLangType").css("display", "");
          $("#btnSaveEditTemplate").show();

          $scope.$apply(function() {
            if (selectedRow.templtRegFg === "C") {
              $scope.templtRegFgNm = "시스템";
            } else if (selectedRow.templtRegFg === "H") {
              $scope.templtRegFgNm = "본사";
            } else {
              $scope.templtRegFgNm = "매장";
            }
            // 편집영역의 '저장' 버튼 명칭 변경
            if("S" === gvOrgnFg){
              if (selectedRow.templtRegFg === "C") {
                $("#btnSaveEditTemplate").text("실제출력물적용");
              } else if (selectedRow.templtRegFg === "H") {
                $("#btnSaveEditTemplate").text("실제출력물적용");
              } else {
                if(selectedRow.templtCd === "000"){
                  $("#btnSaveEditTemplate").text("저장");
                }else{
                  $("#btnSaveEditTemplate").text("저장 및 실제출력물적용");
                }
              }
            }
            $scope.showTempltRegFgNm = true;
            // 본사는 상위에서 내려준걸 수정 하지 못한다.
            if ( gvOrgnFg === selectedRow.templtRegFg ) {
              $scope.templtEditableTxt = "수정가능";
              theTarget.disabled = false;
              if ( "H" === gvOrgnFg ) {
                  $("#btnSaveEditTemplate").show();
              }
            } else {
              $scope.templtEditableTxt = "수정불가";
              theTarget.disabled = true;
              if ( "H" === gvOrgnFg ) {
                  $("#btnSaveEditTemplate").hide();
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

      var grid = wijmo.Control.getControl("#wjGridPosTemplate");
      var rows = grid.rows;

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];

        if(item.templtRegFg !== gvOrgnFg || item.templtCd === "000"){
          item.gChk = false;
          rows[i].isReadOnly = true;
        }
      }

        // 코드리스트 조회
        $scope._postJSONQuery.withOutPopUp("/base/output/posTemplate/code/list.sb", params,
          function(response) {

              var list = response.data.data.list;

              if (list.length === undefined || list.length === 0) {
                // 코드리스트 초기화
                  $scope.listBoxCodeList.itemsSource = new wijmo.collections.CollectionView([]);
              } else {
                // 코드리스트 셋팅
                $scope.listBoxCodeList.itemsSource = list;
              }

              // 템플릿 추가/삭제/저장 버튼 show
              $("#btnAddTemplate").show();
              $("#btnDelTemplate").show();
              $("#btnSaveTemplate").show();

              // 가장 첫번째 템플릿 값 갖고 있기(조회 클릭시, 바로 편집내용과 미리보기 셋팅을 하기 위해)
              var selectedTemplate = $scope.flex.selectedRows[0]._data;

              // 템플릿 선택시, 기본 한글 템플릿 부터 보이도록 set
              $scope.langTypeCombo.selectedIndex = 0;

              if (selectedTemplate.prtForm != null) {
                theTarget.value = selectedTemplate.prtForm;
                makePreview();
              } else {
                theTarget.value = "";
                thePreview.innerHTML = "";
              }

              // 템플릿 클릭시, 템플릿 편집이 가능
              $("#divLangType").css("display", "");
              $("#btnSaveEditTemplate").show();

              if (selectedTemplate.templtRegFg === "C") {
                $scope.templtRegFgNm = "시스템";
              } else if (selectedTemplate.templtRegFg === "H") {
                $scope.templtRegFgNm = "본사";
              } else {
                $scope.templtRegFgNm = "매장";
              }
              // 편집영역의 '저장' 버튼 명칭 변경
              if("S" === gvOrgnFg){
                if (selectedTemplate.templtRegFg === "C") {
                  $("#btnSaveEditTemplate").text("실제출력물적용");
                } else if (selectedTemplate.templtRegFg === "H") {
                  $("#btnSaveEditTemplate").text("실제출력물적용");
                } else {
                  if(selectedTemplate.templtCd === "000"){
                    $("#btnSaveEditTemplate").text("저장");
                  }else{
                    $("#btnSaveEditTemplate").text("저장 및 실제출력물적용");
                  }
                }
              }
              $scope.showTempltRegFgNm = true;
              // 본사는 상위에서 내려준걸 수정 하지 못한다.
              if ( gvOrgnFg === selectedTemplate.templtRegFg ) {
                $scope.templtEditableTxt = "수정가능";
                theTarget.disabled = false;
                if ( "H" === gvOrgnFg ) {
                    $("#btnSaveEditTemplate").show();
                }
              } else {
                $scope.templtEditableTxt = "수정불가";
                theTarget.disabled = true;
                if ( "H" === gvOrgnFg ) {
                    $("#btnSaveEditTemplate").hide();
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
    $scope.showTempltRegFgNm = false;
    $scope.templtRegFgNm = "";
    $scope.templtEditableTxt = "";

    // 편집/미리보기 폼 초기화
    theTarget.value = "";
    thePreview.innerHTML = "";
    $("#divLangType").css("display", "none");
    $("#btnSaveEditTemplate").hide();
    theTarget.disabled = true;

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
      params.push($scope.flex.collectionView.itemsEdited[u]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var d = 0; d < $scope.flex.collectionView.itemsRemoved.length; d++) {
      $scope.flex.collectionView.itemsRemoved[d].status = "D";
      params.push($scope.flex.collectionView.itemsRemoved[d]);
    }

    for (var i = 0; i < params.length; i++) {
        var item = params[i];

        if (item.templtNm === "") {
            $scope._popMsg(messages["posTemplate.templtNm"] + messages["cmm.require.text"]);  // 템플릿명(을)를 입력하세요.
            return false;
        }

        if (nvl(item.templtNm + '', '').getByteLengthForOracle() > 50) {
            $scope._popMsg(messages["posTemplate.templtNm"] +  messages["cmm.overLength"] + " 50 ");  // 템플릿명의 데이터 중 문자열의 길이가 너무 긴 데이터가 있습니다. 최대 : 50
            return false;
        }
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
    params.langType = $scope.langTypeCombo.selectedValue;
    if(params.langType === "ko"){
        params.prtForm = theTarget.value;
    }else if(params.langType === "en"){
        params.prtEnForm = theTarget.value;
    }else if(params.langType === "cn"){
        params.prtCnForm = theTarget.value;
    }else if(params.langType === "jp"){
        params.prtJpForm = theTarget.value;
    }

    // 자신이 등록한것만 수정 가능하므로 분기처리
    if ( gvOrgnFg === selectedRow.templtRegFg ) {
      $scope._popConfirm("저장하시겠습니까?",function () {
          $scope._postJSONSave.withOutPopUp("/base/output/posTemplate/template/save.sb", params,
              function (response) {
                // 본사 또는 실제출력물 인 경우 패스
                if ("H" !== gvOrgnFg && selectedRow.templtCd !== "000") {
                  $scope._popConfirm(messages['cmm.saveSucc'] + "<br><br>해당 템플릿을 실제출력물에 업데이트 하시겠습니까?",
                      function () {
                            var nParams = {};
                            nParams.prtClassCd = params.prtClassCd;
                            nParams.templtRegFg = params.templtRegFg;
                            nParams.templtCd = params.templtCd;
                            nParams.langType = params.langType;
                            if(nParams.langType === "ko"){
                                nParams.prtForm = params.prtForm;
                            }else if(nParams.langType === "en"){
                                nParams.prtEnForm = params.prtEnForm;
                            }else if(nParams.langType === "cn"){
                                nParams.prtCnForm = params.prtCnForm;
                            }else if(nParams.langType === "jp") {
                                nParams.prtJpForm = params.prtJpForm;
                            }

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
      });
    } else {
      // 자신이 등록하지 않은 템플릿은 수정할 수 없으므로 실제출력물에만 적용한다.
      $scope._popConfirm("해당 템플릿을 실제출력물에 업데이트 하시겠습니까?",
        function () {
          var nParams = {};
          nParams.prtClassCd = params.prtClassCd;
          nParams.templtRegFg = params.templtRegFg;
          nParams.templtCd = params.templtCd;
          nParams.langType = params.langType;
          if(nParams.langType === "ko"){
              nParams.prtForm = params.prtForm;
          }else if(nParams.langType === "en"){
              nParams.prtEnForm = params.prtEnForm;
          }else if(nParams.langType === "cn"){
              nParams.prtCnForm = params.prtCnForm;
          }else if(nParams.langType === "jp") {
              nParams.prtJpForm = params.prtJpForm;
          }

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
  // 템플릿 매장 적용 버튼
  $scope.applyToStoreTemplate = function() {

    var selectedRow = $scope.flex.selectedRows[0]._data;
    $scope._popConfirm($scope.prtClassCdCombo.text + "의 '" + selectedRow.templtNm + "' 템플릿을 전매장에 적용하시겠습니까?<br>매장에 템플릿이 존재하는 경우, 업데이트 처리됩니다.",
      function() {
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
  // 실제출력물 매장 적용 버튼
  $scope.applyToStoreReal = function() {
    // $scope._popConfirm("전체 매장에 해당 출력물을 적용하시겠습니까?<br>매장에 출력물이 존재하는 경우, 업데이트 처리됩니다.",
    //     function() {
    //       var selectedRow = $scope.flex.selectedRows[0]._data;
    //       var params = {};
    //       params.prtClassCd = $scope.prtClassCdCombo.selectedValue;
    //       params.templtRegFg = selectedRow.applyTempltRegFg;
    //       params.templtCd = selectedRow.templtCd;
    //
    //       $scope._postJSONSave.withPopUp("/base/output/posTemplate/template/applyToStoreReal.sb", params,
    //           function (response) {
    //             $scope.flex.collectionView.clearChanges();
    //             $scope._broadcast('templateCtrl');
    //           }
    //       );
    //     }
    // );
    // // 기능수행 종료 : 반드시 추가
    // event.preventDefault();

    //$scope.storePosTemplateLayer.show(true, function (s) {
      // var storePosTemplateGrid = agrid.getScope('storePosTemplateCtrl');
      // storePosTemplateGrid.$apply(function(){
      //   storePosTemplateGrid._gridDataInit();
      // });
      // $scope._pageView('templateCtrl');
    //});

    var selectedRow = $scope.flex.selectedRows[0]._data;

    // 출력물종류와 템플릿을 선택하세요.
    if($scope.prtClassCdCombo.selectedValue === null || $scope.prtClassCdCombo.selectedValue === undefined || $scope.prtClassCdCombo.selectedValue === "" ||
       selectedRow.applyTempltRegFg === null || selectedRow.applyTempltRegFg === undefined || selectedRow.applyTempltRegFg === "" ||
       selectedRow.templtCd === null || selectedRow.templtCd === undefined || selectedRow.templtCd === ""){
       $scope._popMsg(messages["posTemplate.prtForm.select.chk.msg"]);
       return false;
    }

    // 본사등록 템플릿만 실제출력물매장적용이 가능합니다.<br/> 템플릿을 먼저 등록하세요.
    if(selectedRow.applyTempltRegFg === "C"){
      $scope._popMsg(messages["posTemplate.prtFormToStore.chk.msg"]);
      return false;
    }

    $scope.storePosTemplateLayer.show(true);
    $scope._broadcast('storePosTemplateCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 매장 팝업 핸들러 추가
    $scope.storePosTemplateLayer.shown.addHandler(function (s) {
      var selectedRow = $scope.flex.selectedRows[0]._data;
      $("#hdPrtClassCd").val($scope.prtClassCdCombo.selectedValue);
      $("#hdApplyTempltRegFg").val(selectedRow.applyTempltRegFg);
      $("#hdTempltCd").val(selectedRow.templtCd);
      $("#lblApplyTempltNm").text(selectedRow.templtNm);
    });
  });

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

      // 언어타입에 따른 다국어 예제문구 셋팅
      var content = "";
      if($scope.langTypeCombo.selectedValue === "ko"){
          content = s.itemsSource[s.selectedIndex].content;
      }else if($scope.langTypeCombo.selectedValue === "en"){
          content = s.itemsSource[s.selectedIndex].contentEn;
      }else if($scope.langTypeCombo.selectedValue === "cn"){
          content = s.itemsSource[s.selectedIndex].contentCn;
      }else if($scope.langTypeCombo.selectedValue === "jp"){
          content = s.itemsSource[s.selectedIndex].contentJp;
      }

      var mData = {
        prtCd: s.selectedValue,
        content: content
      };
      var dragRow = JSON.stringify(mData);
      // 드래그 데이터 set
      e.dataTransfer.setData("text", dragRow);
    }, true);
  };

  // 출력코드명 조회
  $scope.setCodeDesc = function(s){

    // 파라미터
    var params= {};
    params.prtCd = s.selectedValue;

    $.postJSON("/base/output/posTemplate/template/getPrintCodeNm.sb", params, function(result) {
        $("#lblCodeDesc").text(result.data);
    },
    function(result){
        s_alert.pop(result.message);
    });
  };

  // 언어타입 변경에 따른 양식 셋팅
  $scope.setPrtForm = function (s) {
      if ($scope.flex.rows.length > 0) {
          if (s.selectedValue === "ko") { // 한글
              if ($scope.flex.selectedRows[0]._data.prtForm != null) {
                  theTarget.value = $scope.flex.selectedRows[0]._data.prtForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "en") { // 영문

              if ($scope.flex.selectedRows[0]._data.prtEnForm != null) {
                  theTarget.value = $scope.flex.selectedRows[0]._data.prtEnForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "cn") { // 중문
              if ($scope.flex.selectedRows[0]._data.prtCnForm != null) {
                  theTarget.value = $scope.flex.selectedRows[0]._data.prtCnForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "jp") { // 일문
              if ($scope.flex.selectedRows[0]._data.prtJpForm != null) {
                  theTarget.value = $scope.flex.selectedRows[0]._data.prtJpForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }
          }
      }
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
        // 언어타입에 따른 다국어 예제문구 셋팅
        if (listBoxData[l].prtCd === matches[k]){
            if(scope.langTypeCombo.selectedValue === "ko" && listBoxData[l].content != null){
                // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
                value = value.replace(matches[k], listBoxData[l].content);
            }else if(scope.langTypeCombo.selectedValue === "en" && listBoxData[l].contentEn != null){
                // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
                value = value.replace(matches[k], listBoxData[l].contentEn);
            }else if(scope.langTypeCombo.selectedValue === "cn" && listBoxData[l].contentCn != null){
                // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
                value = value.replace(matches[k], listBoxData[l].contentCn);
            }else if(scope.langTypeCombo.selectedValue === "jp" && listBoxData[l].contentJp != null){
                // 코드값을 listBox 데이터에서 찾아서 예제 문구로 치환
                value = value.replace(matches[k], listBoxData[l].contentJp);
            }
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
        /*splitStr = lineArray[m].splitByteLen(42);
        for (var n = 0; n < splitStr.length; n++) {
          newValues[newLine++] = splitStr[n];
        }*/
        newValues[newLine++] = lineArray[m];    // 42byte 초과 문자열 쪼개서 배열넣던 방식 -> 쪼개지 않고 통으로 사용(20230509)
      }
    }
  }

  //thePreview.innerHTML = "<PRE><P>" + newValues.join("</P><P>") + "</P></PRE>";


  var innerVal = "<PRE>";

  var pTag = "<p";       // 전체(정렬 + 폰트) tag
  var pAlignTag = "";    // 정렬 tag
  var pFontTag = "";     // 폰트 style tag
  var endStyle = false;  // 전체 style tag 종료 구분
  var fontSizeType = ""; // 폰트 사이즈 구분(사이즈별 문단 height 값 지정시 사용)

  for(var i=0; i<newValues.length; i++){

    // 정렬 ------------------------------------------------------------------------------------------------------------  
    // 중앙 정렬
    if(newValues[i].includes("{CENTER}")){
        // 정렬 tag 구성
        pAlignTag = " align=\"center\"";
        pTag += pAlignTag;
        // 기존 구분자값 제거
        newValues[i] = newValues[i].replace("{CENTER}", "");
    }

    // 왼쪽 정렬
    if(newValues[i].includes("{LEFT}")){
        // 정렬 tag 구성
        pAlignTag = " align=\"left\"";
        pTag += pAlignTag;
        // 기존 구분자값 제거
        newValues[i] = newValues[i].replace("{LEFT}", "");
    }

    // 오른쪽 정렬
    if(newValues[i].includes("{RIGHT}")){
        // 정렬 tag 구성
        pAlignTag = " align=\"right\"";
        pTag += pAlignTag;
        // 기존 구분자값 제거
        newValues[i] = newValues[i].replace("{RIGHT}", "");
    }

    // 정렬 종료 시
    if(newValues[i].includes("{/CENTER}") || newValues[i].includes("{/LEFT}") || newValues[i].includes("{/RIGHT}")){
        // 정렬 tag 초기화 
        pAlignTag = "";
        // 기존 구분자값 제거
        newValues[i] = newValues[i].replace("{/CENTER}", "");
        newValues[i] = newValues[i].replace("{/LEFT}", "");
        newValues[i] = newValues[i].replace("{/RIGHT}", "");
    }

    // 폰트 사이즈 또는 강조 ---------------------------------------------------------------------------------------------
    if(newValues[i].includes("FONT_H2X") || newValues[i].includes("FONT_HV2X") || newValues[i].includes("FONT_V2X") ||
        newValues[i].includes("FONT_1") || newValues[i].includes("FONT_2") || newValues[i].includes("FONT_3") ||
        newValues[i].includes("FONT_4") || newValues[i].includes("FONT_5") || newValues[i].includes("FONT_6") ||
        newValues[i].includes("FONT_7") || newValues[i].includes("EMPHASIS")){

        // 'style' tag 추가
        if(!pTag.includes(" style=\"")){
            pTag += " style=\"";
            endStyle = true;
        }

        // 폰트 사이즈 - 가로 두배 늘리기
        if(newValues[i].includes("{FONT_H2X}")){
          // 폰트 사이즈 구분 추가      
          fontSizeType = "FONT_H2X";
          // 폰트 사이즈 tag 구성
          pFontTag = "font-size:24px; transform:scaleY(0.5); transform-origin:0 0; height:12px;";
          pTag += pFontTag;
          // 기존 구분자값 제거
          newValues[i] = newValues[i].replace("{FONT_H2X}", "");
        }
        
        // 폰트 사이즈 - 가로 두배 늘리기 종료 시
        if(newValues[i].includes("{/FONT_H2X}")){
          // 폰트 style tag 초기화
          pFontTag = "";
          // 기존 구분자값 제거
          newValues[i] = newValues[i].replace("{/FONT_H2X}", "");
        }
        
        // 폰트 사이즈 - 가로,세로 두배 늘리기
        if(newValues[i].includes("{FONT_HV2X}")){
          fontSizeType = "FONT_HV2X";
          pFontTag = "font-size:24px; transform:scaleY(1); transform-origin:0 0; height:24px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_HV2X}", "");
        }
        
        // 폰트 사이즈 - 가로,세로 두배 늘리기 종료 시
        if(newValues[i].includes("{/FONT_HV2X}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_HV2X}", "");
        }

        // 폰트 사이즈 - 세로 두배 늘리기
        if(newValues[i].includes("{FONT_V2X}")){
          fontSizeType = "FONT_V2X";
          pFontTag = "font-size:24px; transform:scaleX(0.5); transform-origin:0 0; width:200%; height:24px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_V2X}", "");
        }
        
        // 폰트 사이즈 - 세로 두배 늘리기 종료 시
        if(newValues[i].includes("{/FONT_V2X}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_V2X}", "");
        }

        // 폰트 사이즈 - FONT_1
        if(newValues[i].includes("{FONT_1}")){
          fontSizeType = "FONT_1";
          pFontTag = "font-size:50px; transform:scaleY(0.6); transform-origin:0 0; height:30px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_1}", "");
        }

        // 폰트 사이즈 - FONT_1 종료 시 
        if(newValues[i].includes("{/FONT_1}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_1}", "");
        }
        
        // 폰트 사이즈 - FONT_2
        if(newValues[i].includes("{FONT_2}")){
          fontSizeType = "FONT_2";
          pFontTag = "font-size:50px; transform:scaleY(0.8); transform-origin:0 0; height:40px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_2}", "");
        }

        // 폰트 사이즈 - FONT_2 종료 시 
        if(newValues[i].includes("{/FONT_2}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_2}", "");
        }

        // 폰트 사이즈 - FONT_3
        if(newValues[i].includes("{FONT_3}")){
          fontSizeType = "FONT_3";
          pFontTag = "font-size:50px; transform:scaleY(1); transform-origin:0 0; height:50px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_3}", "");
        }

        // 폰트 사이즈 - FONT_3 종료 시
        if(newValues[i].includes("{/FONT_3}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_3}", "");
        }

        // 폰트 사이즈 - FONT_4
        if(newValues[i].includes("{FONT_4}")){
          fontSizeType = "FONT_4";
          pFontTag = "font-size:50px; transform:scaleY(1.2); transform-origin:0 0; height:60px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_4}", "");
        }

        // 폰트 사이즈 - FONT_4 종료 시
        if(newValues[i].includes("{/FONT_4}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_4}", "");
        }

        // 폰트 사이즈 - FONT_5
        if(newValues[i].includes("{FONT_5}")){
          fontSizeType = "FONT_5";
          pFontTag = "font-size:50px; transform:scaleY(1.4); transform-origin:0 0; height:70px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_5}", "");
        }

        // 폰트 사이즈 - FONT_5 종료 시
        if(newValues[i].includes("{/FONT_5}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_5}", "");
        }

        // 폰트 사이즈 - FONT_6
        if(newValues[i].includes("{FONT_6}")){
          fontSizeType = "FONT_6";
          pFontTag = "font-size:50px; transform:scaleY(1.6); transform-origin:0 0; height:80px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_6}", "");
        }

        // 폰트 사이즈 - FONT_6 종료 시
        if(newValues[i].includes("{/FONT_6}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_6}", "");
        }

        // 폰트 사이즈 - FONT_7
        if(newValues[i].includes("{FONT_7}")){
          fontSizeType = "FONT_7";
          pFontTag = "font-size:50px; transform:scaleY(1.8); transform-origin:0 0; height:90px;";
          pTag += pFontTag;
          newValues[i] = newValues[i].replace("{FONT_7}", "");
        }

        // 폰트 사이즈 - FONT_7 종료 시
        if(newValues[i].includes("{/FONT_7}")){
          pFontTag = "";
          newValues[i] = newValues[i].replace("{/FONT_7}", "");
        }

        // 폰트 강조
        if(newValues[i].includes("{EMPHASIS}")){
          // 폰트 강조 tag 구성
          pFontTag = " font-weight:bold;";
          pTag += pFontTag;
          // 기존 구분자값 제거
          newValues[i] = newValues[i].replace("{EMPHASIS}", "");
        }

        // 폰트 강조 종료 시
        if(newValues[i].includes("{/EMPHASIS}")){
          // 폰트 style tag 초기화
          pFontTag = "";
          // 기존 구분자값 제거
          newValues[i] = newValues[i].replace("{/EMPHASIS}", "");
        }

        // 'style' tag 종료
        if(endStyle){
            pTag += "\"";
            endStyle = false;
        }
    }

    var vLine = 1;      // 문단 Line 수
    var pTag2 = pTag;   // 기존 tag 값 유지를 위해

    // 글자 갯수로 문단 Line 수 판단
    if(fontSizeType === "FONT_H2X" || fontSizeType === "FONT_HV2X"){
        vLine = parseInt(newValues[i].getByteLength()/(2*11)) + 1; // 2byte * 한줄에 11자
        if(newValues[i].getByteLength()%(2*11) === 0){
            vLine = vLine - 1;
        }
    }else if(fontSizeType === "FONT_V2X"){
        vLine = parseInt(newValues[i].getByteLength()/(2*22)) + 1; // 2byte * 한줄에 22자
        if(newValues[i].getByteLength()%(2*22) === 0){
            vLine = vLine - 1;
        }
    }else{
        vLine = parseInt(newValues[i].getByteLength()/(2*5)) + 1;  // 2byte * 한줄에 5자씩
        if(newValues[i].getByteLength()%(2*5) === 0){
            vLine = vLine - 1;
        }
    }

    // (문단 Line 수 * 글자크기)로 문단 height값 계산하여 기존값에 치환
    if(fontSizeType === "FONT_H2X"){
      pTag2 = pTag2.replace(" height:12px;", " height:" + (vLine * 12).toString() + "px;");
    }else if(fontSizeType === "FONT_HV2X"){
      pTag2 = pTag2.replace(" height:24px;", " height:" + (vLine * 24).toString() + "px;");
    }else if(fontSizeType === "FONT_V2X"){
      pTag2 = pTag2.replace(" height:24px;", " height:" + (vLine * 24).toString() + "px;");
    }else if(fontSizeType === "FONT_1"){
      pTag2 = pTag2.replace(" height:30px;", " height:" + (vLine * 30).toString() + "px;");
    }else if(fontSizeType === "FONT_2"){
      pTag2 = pTag2.replace(" height:40px;", " height:" + (vLine * 40).toString() + "px;");
    }else if(fontSizeType === "FONT_3"){
      pTag2 = pTag2.replace(" height:50px;", " height:" + (vLine * 50).toString() + "px;");
    }else if(fontSizeType === "FONT_4"){
      pTag2 = pTag2.replace(" height:60px;", " height:" + (vLine * 60).toString() + "px;");
    }else if(fontSizeType === "FONT_5"){
      pTag2 = pTag2.replace(" height:70px;", " height:" + (vLine * 70).toString() + "px;");
    }else if(fontSizeType === "FONT_6"){
      pTag2 = pTag2.replace(" height:80px;", " height:" + (vLine * 80).toString() + "px;");
    }else if(fontSizeType === "FONT_7"){
      pTag2 = pTag2.replace(" height:90px;", " height:" + (vLine * 90).toString() + "px;");
    }

    // style 적용된 문단 완성
    innerVal += pTag2 + ">" + newValues[i] + "</p>";

    // 전체 tag 초기화(폰트 style tag 제거)
    pTag  = "<p" + pAlignTag;

    // 전체 tag 초기화(정렬 tag 제거)
    pTag  = "<p" + pFontTag;
  }

  innerVal += "</PRE>";

  // 미리보기 화면에 set
  thePreview.innerHTML = innerVal;

}
