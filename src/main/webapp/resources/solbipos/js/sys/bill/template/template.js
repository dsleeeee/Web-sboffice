/****************************************************************
 *
 * 파일명 : template.js
 * 설  명 : 출력물샘플 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.07.30     노현수      1.0
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

// 선택한 템플릿 관련 정보
var ht;

/**
 * 템플릿 그리드 생성
 */
app.controller('templateCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('templateCtrl', $scope, $http, false));
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
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "templtNm") {
          var item = s.rows[e.row].dataItem;
          if (item.status !== "I") {
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else {
            wijmo.removeClass(e.cell, 'wj-custom-readonly');
          }
        }
      }
    });
    // 템플릿 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      if (col.binding === "templtNm") {
        var dataItem = s.rows[e.row].dataItem;
        if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
          e.cancel = true;
        }
      }
    });
    // 템플릿 그리드 선택 이벤트
    s.hostElement.addEventListener('mousedown', function(e) {
      ht = s.hitTest(e);
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
          $("#btnSaveEditTemplate").show();
          $("#divLangType").css("display", "");
          $("#editTextArea").css("display", "");
          $("#preview").css("display", "");
        }
      }
    });
  };
  // 템플릿 그리드 조회
  $scope.$on("templateCtrl", function(event, data) {
    // 파라미터
    var params = {};

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/sys/bill/template/item/list.sb", params, function() {
      // 코드리스트 조회
      searchPrintCodeList(params);
    });

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 템플릿 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.prtClassCd =  $scope.prtClassCdCombo.selectedValue;
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
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/bill/template/item/save.sb", params, function () {
        // 재조회
        $scope.$broadcast('templateCtrl');
    });
  };
  // 편집 저장버튼
  $scope.$on("saveEditTemplate", function(event, data) {
    $scope._popConfirm("저장시 전체 본사 및 단독매장의 해당 템플릿이 같이 수정 됩니다.<br>저장 하시겠습니까?",
      function() {
        var selectedRow = $scope.flex.selectedRows[0]._data;
        var param = {};
        param.prtClassCd = $scope.prtClassCdCombo.selectedValue;
        param.templtCd = selectedRow.templtCd;
        param.templtNm = selectedRow.templtNm;
        param.langType = $scope.langTypeCombo.selectedValue;
        if(param.langType === "ko"){
            param.prtForm = theTarget.value;
        }else if(param.langType === "en"){
            param.prtEnForm = theTarget.value;
        }else if(param.langType === "cn"){
            param.prtCnForm = theTarget.value;
        }else if(param.langType === "jp"){
            param.prtJpForm = theTarget.value;
        }

        $.postJSONSave("/sys/bill/template/bill/save.sb", param, function (result) {
            s_alert.pop(messages["cmm.saveSucc"]);
            $scope.flex.collectionView.clearChanges();

            // 재조회
            $scope.$broadcast('templateCtrl');

          },
          function (result) {
            s_alert.pop(result.message);
            return false;
          });
      }
    );
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  // 미적용 본사/단독매장 팝업 보기
  $scope.$on("showPopUp", function(event, data) {
    if ($scope.flex.itemsSource && $scope.flex.itemsSource.itemCount > 0) {
      // 팝업의 콤보데이터 설정
      $scope._setComboData("srchTempltCdCombo", JSON.parse(JSON.stringify($scope.flex.itemsSource._src)));
      var popup = $scope.popUpSelLayer;
      popup.shown.addHandler(function (s) {
        // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
        setTimeout(function() {
          $scope._broadcast('popUpApplyTemplateCtrl', $scope.prtClassCdCombo.text);
        }, 50)
      });
      // 팝업 닫을때
      popup.show(true, function (s) {
        // 적용 버튼 눌렀을때만
        if (popup.dialogResult === "wj-hide-apply") {
          // 팝업 컨트롤러 Get
          var scopeLayer = agrid.getScope("popUpApplyTemplateCtrl");
          // 저장 파라미터 설정
          var paramArr = [];

          for (var i = 0; i < scopeLayer.flexLayer.collectionView.itemsEdited.length; i++) {
            scopeLayer.flexLayer.collectionView.itemsEdited[i].status = "U";
            scopeLayer.flexLayer.collectionView.itemsEdited[i].prtClassCd = $scope.prtClassCdCombo.selectedValue;
            scopeLayer.flexLayer.collectionView.itemsEdited[i].templtCd = scopeLayer.srchTempltCdCombo.templtCd;
            scopeLayer.flexLayer.collectionView.itemsEdited[i].templtNm = scopeLayer.srchTempltCdCombo.templtNm;
            scopeLayer.flexLayer.collectionView.itemsEdited[i].prtForm = scopeLayer.srchTempltCdCombo.prtForm;
            paramArr.push(scopeLayer.flexLayer.collectionView.itemsEdited[i]);
          }

          if (paramArr.length <= 0) {
            s_alert.pop(messages["template.msg.select"]);
            return;
          }
          // 미적용 본사/단독매장 템플릿 적용
          $.postJSONArray("/sys/bill/template/unUsed/save.sb", paramArr, function (result) {
              s_alert.pop(messages["cmm.saveSucc"]);
              scopeLayer.flexLayer.collectionView.clearChanges();
            },
            function (result) {
              s_alert.pop(result.message);
            }
          );
        }

      });

    } else {
      $scope._popMsg(messages['template.msg.fail']);
      return false;
    }
  });

  // 언어타입 변경에 따른 양식 셋팅
  $scope.setPrtForm = function (s) {
      if ($scope.flex.rows.length > 0) {
          if (s.selectedValue === "ko") { // 한글
              if ($scope.flex.rows[ht.row]._data.prtForm != null) {
                  theTarget.value = $scope.flex.rows[ht.row]._data.prtForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "en") { // 영문

              if ($scope.flex.rows[ht.row]._data.prtEnForm != null) {
                  theTarget.value = $scope.flex.rows[ht.row]._data.prtEnForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "cn") { // 중문
              if ($scope.flex.rows[ht.row]._data.prtCnForm != null) {
                  theTarget.value = $scope.flex.rows[ht.row]._data.prtCnForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }

          } else if (s.selectedValue === "jp") { // 일문
              if ($scope.flex.rows[ht.row]._data.prtJpForm != null) {
                  theTarget.value = $scope.flex.rows[ht.row]._data.prtJpForm;
                  makePreview();
              } else {
                  theTarget.value = "";
                  thePreview.innerHTML = "";
              }
          }
      }
  };
}]);

// 리스트박스 생성
var listBoxCodeList = new wijmo.input.ListBox('#listBoxCode',
  {
    // 보여지는 데이터
    displayMemberPath: 'prtCd',
    // 선택시 데이터
    selectedValuePath: 'prtCd',
    // 드래그 사용하도록 설정
    formatItem: function (s, e) {
      e.item.setAttribute('draggable', 'true');
    }
  });

// 출력물코드 목록 조회
function searchPrintCodeList(params) {

  var scope = agrid.getScope("templateCtrl");

  $.ajax({
    type: "POST",
    cache: false,
    async: true,
    dataType: "json",
    url: "/sys/bill/template/code/list.sb",
    data: params,
    success: function(result) {
      if(result.status === "OK") {

        var list = result.data.list;

        if (list.length === undefined || list.length === 0) {
          // 코드리스트 초기화
          listBoxCodeList.itemsSource = new wijmo.collections.CollectionView([]);
        } else {
          // 코드리스트 셋팅
          listBoxCodeList.itemsSource = list;
        }

        // 편집/미리보기 폼 초기화
        theTarget.value = "";
        thePreview.innerHTML = "";
        ht = null;

        // 템플릿 클릭시, 템플릿 편집이 가능
        $("#btnSaveEditTemplate").hide();
        $("#divLangType").css("display", "none");
        $("#editTextArea").css("display", "none");
        $("#preview").css("display", "none");

      }
      else if(result.status === "FAIL") {
        return fail(result);
      }
      else if(result.status === "SESSION_EXFIRE") {
        s_alert.popOk(result.message, function() {
          location.href = result.url;
        });
      }
      else if(result.status === "SERVER_ERROR") {
        s_alert.pop(result.message);
      }
      /*else if(result.status === undefined) {
        location.href = "/";
      }*/
      else {
        var msg = result.status + " : " + result.message;
        alert(msg);
      }
    }
  });

}

// 클릭드래그시 선택이벤트 발생
listBoxCodeList.hostElement.addEventListener("mousedown", function (e) {
  listBoxCodeList.selectedValue = e.target.innerText;
});

// 드래그 시작 이벤트
listBoxCodeList.hostElement.addEventListener('dragstart', function (e) {

  var mData = {
    prtCd: listBoxCodeList.selectedValue,
    content: listBoxCodeList.itemsSource[listBoxCodeList.selectedIndex].content
  };

  var dragRow = JSON.stringify(mData);
  // 드래그 데이터 set
  e.dataTransfer.setData("text", dragRow);

}, true);

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
  var listBoxData = listBoxCodeList.itemsSource;
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
      // 추출된 특수태그를 정규식에 의해 분리 ( 추출된태그/첫문자/바이트길이/내용 )
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
