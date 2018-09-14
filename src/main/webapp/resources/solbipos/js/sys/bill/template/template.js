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
$(document).ready(function () {

  // 출력물종류 선택 콤보박스
  var srchPrtTypeCombo = wcombo.genCommonBoxFun("#srchPrtTypeCombo", printTypeComboData, function (e) {
    searchPrintCodeList();
  });

  // 템플릿 그리드 Data
  var gridTemplateData =
    [
      {
        binding: "gChk",
        header: messages["template.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "templtNm", header: messages["template.templtNm"], width: "*"},
      {binding: "prtForm", header: messages["template.prtForm"], visible: false}
    ];
  // 템플릿 그리드 생성
  var gridTemplate = wgrid.genGrid("#gridTemplate", gridTemplateData);
  gridTemplate.isReadOnly = false;

  // ReadOnly 효과설정
  gridTemplate.formatItem.addHandler(function (s, e) {
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
  gridTemplate.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "templtNm") {
      var dataItem = gridTemplate.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") === "" && dataItem.status !== "I") {
        e.cancel = true;
      }
    }
  });

  // 템플릿 그리드 선택 이벤트
  gridTemplate.addEventListener(gridTemplate.hostElement, 'mousedown', function(e) {
    var ht = gridTemplate.hitTest(e);
    if ( ht.cellType === wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      var selectedRow = gridTemplate.rows[ht.row].dataItem;
      if ( col.binding === "templtNm" && selectedRow.status !== "I") {
        if (selectedRow.prtForm != null) {
          theTarget.value = selectedRow.prtForm;
          makePreview();
        } else {
          theTarget.value = "";
          thePreview.innerHTML = "";
        }
        $("#btnSaveTemplate").show();
      }
    }
  });

  // 템플릿 추가버튼 클릭
  $("#btnAddTemplate").click(function (e) {
    gridTemplate.collectionView.trackChanges = true;
    var newRow = gridTemplate.collectionView.addNew();
    newRow.status = "I";
    newRow.prtClassCd = srchPrtTypeCombo.selectedItem["value"];
    newRow.gChk = true;

    gridTemplate.collectionView.commitNew();
    // 추가된 Row 선택
    gridTemplate.select(gridTemplate.rows.length-1, 1);
  });

  // 템플릿 삭제버튼 클릭
  $("#btnDelTemplate").click(function (e) {
    for (var i = gridTemplate.itemsSource.itemCount - 1; i >= 0; i--) {
      if (gridTemplate.collectionView.items[i].gChk === true) {
        gridTemplate.itemsSource.removeAt(i);
      }
    }
  });

  // 템플릿 저장버튼 클릭
  $("#btnSaveTemplate").click(function (e) {

    console.log(gridTemplate.collectionView);

    var paramArr = new Array();
    for (var i = 0; i < gridTemplate.collectionView.itemsEdited.length; i++) {
      gridTemplate.collectionView.itemsEdited[i].status = "U";
      gridTemplate.collectionView.itemsEdited[i].prtForm = theTarget.value;
      paramArr.push(gridTemplate.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridTemplate.collectionView.itemsAdded.length; i++) {
      gridTemplate.collectionView.itemsAdded[i].status = "I";
      gridTemplate.collectionView.itemsAdded[i].prtForm = theTarget.value;
      paramArr.push(gridTemplate.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < gridTemplate.collectionView.itemsRemoved.length; i++) {
      gridTemplate.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(gridTemplate.collectionView.itemsRemoved[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/bill/template/item/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridTemplate.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

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
  function searchPrintCodeList() {

    var param = {};
    param.prtClassCd = srchPrtTypeCombo.selectedItem["value"];

    $.postJSON("/sys/bill/template/code/list.sb", param,
      function (result) {

        var list = result.data.list;
        listBoxCodeList.itemsSource = list;

        if (list.length === undefined || list.length == 0) {
          // 코드리스트 초기화
          listBoxCodeList.itemsSource = new wijmo.collections.CollectionView([]);
          // 그리드 초기화
          gridTemplate.itemsSource = new wijmo.collections.CollectionView([]);
          // 편집/미리보기 폼 초기화
          theTarget.value = "";
          thePreview.innerHTML = "";
        } else {

          $.postJSON("/sys/bill/template/item/list.sb", param,
            function (result) {

              list = result.data.list;
              gridTemplate.itemsSource = new wijmo.collections.CollectionView(list);
              gridTemplate.itemsSource.trackChanges = true;

              // 버튼 Show
              $("#btnAddTemplate").show();
              $("#btnDelTemplate").show();
              $("#btnSaveTemplate").show();
              $("#btnSaveEditTemplate").show();

              if (list.length === undefined || list.length == 0) {
                // 그리드 초기화
                gridTemplate.itemsSource = new wijmo.collections.CollectionView([]);
                // 편집/미리보기 폼 초기화
                theTarget.value = "";
                thePreview.innerHTML = "";
              } else {
                gridTemplate.select(0, 1);
              }

            },
            function (result) {
              s_alert.pop(result.message);
              return;
            }
          );

        }

      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );

  };

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
      if (iStartPos == iEndPos) {
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
  })

  // 편집 저장버튼 클릭
  $("#btnSaveEditTemplate").click(function (e) {

    var selectedRow = gridTemplate.selectedRows[0]._data;
    var param = {};
    param.prtClassCd = srchPrtTypeCombo.selectedItem["value"];
    param.templtCd = selectedRow.templtCd;
    param.templtNm = selectedRow.templtNm;
    param.prtForm = theTarget.value;

    $.postJSONSave("/sys/bill/template/bill/save.sb", param, function (result) {
      s_alert.pop(messages["cmm.saveSucc"]);
      gridTemplate.collectionView.clearChanges();
    },
    function (result) {
      s_alert.pop(result.message);
      return;
    });

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
          if (listBoxData[l].prtCd == matches[k] && listBoxData[l].content != null) {
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
    var newValues = new Array();
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

  // 조회버튼 클릭
  $("#btnSrchTemplate").click(function (e) {
    searchPrintCodeList();
  });

  // 미적용 본사/단독매장 버튼 클릭
  $("#btnApplyTemplate").click(function (e) {
    if (gridTemplate.itemsSource.items.length <= 0) {
      s_alert.pop(messages['template.msg.fail']);
      return;
    }
    showApplyTemplateLayer();
  });

  // 레이어 템플릿 선택 콤보박스
  var srchTemplateTypeCombo = new wijmo.input.ComboBox("#srchTemplateTypeCombo",
    {
      displayMemberPath: "templtNm",
      selectedValuePath: "prtClassCd",
      isAnimated: true
    });

  // 레이어 상태 선택 콤보박스
  var srchSysStatFgCombo = wcombo.genCommonBoxFun("#srchSysStatFgCombo", sysStatFgComboData, function (e) {
    srchLayerGrid();
  });

  // 레이어 용도 선택 콤보박스
  var srchClsFgCombo = wcombo.genCommonBoxFun("#srchClsFgCombo", clsFgComboData, function (e) {
    srchLayerGrid();
  });

  // 레이어 그리드 Data
  var gridLayerData =
    [
      {
        binding: "gChk",
        header: messages["template.layer.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {
        binding: "storeCd",
        header: messages["template.layer.storeCd"],
        width: "*",
        isReadOnly: true
      },
      {
        binding: "storeNm",
        header: messages["template.layer.storeNm"],
        width: "*",
        isReadOnly: true
      },
      {
        binding: "sysStatFgNm",
        header: messages["template.layer.sysStatFg"],
        width: "*",
        isReadOnly: true
      },
      {binding: "clsFgNm", header: messages["template.layer.clsFg"], width: "*", isReadOnly: true}
    ];

  var gridLayer = wgrid.genGrid("#gridLayer", gridLayerData);
  gridLayer.isReadOnly = false;
  gridLayer.headersVisibility = 'Column';

  // 레이어 그리드 조회
  function srchLayerGrid() {

    var param = {};
    param.prtClassCd = srchTemplateTypeCombo.selectedValue;
    param.sysStatFg = srchSysStatFgCombo.selectedValue;
    param.clsFg = srchClsFgCombo.selectedValue;

    $.postJSON("/sys/bill/template/unUsed/list.sb", param,
      function (result) {

        var list = result.data.list;
        gridLayer.itemsSource = new wijmo.collections.CollectionView(list);
        gridLayer.itemsSource.trackChanges = true;
        // 레이어 팝업시 그리드 사이즈 뭉개짐 해결
        gridLayer.autoSizeMode = 1;
        gridLayer.autoSizeRows();

        // 버튼 Show
        $("#btnApplyStore").show();

        if (list.length === undefined || list.length == 0) {
          // 그리드 초기화
          gridLayer.itemsSource = new wijmo.collections.CollectionView([]);
        }

      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  // 레이어 조회버튼 클릭
  $("#btnSrchApplyStore").click(function (e) {
    srchLayerGrid();
  });

  // 레이어 저장버튼 클릭
  $("#btnSaveApplyStore").click(function (e) {

    var paramArr = new Array();
    for (var i = 0; i < gridLayer.collectionView.itemsEdited.length; i++) {
      gridLayer.collectionView.itemsEdited[i].status = "U";
      gridLayer.collectionView.itemsEdited[i].prtClassCd = srchTemplateTypeCombo.selectedValue;
      gridLayer.collectionView.itemsEdited[i].prtForm = srchTemplateTypeCombo.selectedItem.prtForm;
      paramArr.push(gridLayer.collectionView.itemsEdited[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["template.msg.select"]);
      return;
    }

    $.postJSONArray("/sys/bill/template/unUsed/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridLayer.collectionView.clearChanges();
        // 그리드 재조회
        srchLayerGrid();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

  // 레이어 보이기
  function showApplyTemplateLayer() {
    $("#applyTemplateDim").show();
    $("#applyTemplateLayer").show();

    srchTemplateTypeCombo.itemsSource = JSON.parse(JSON.stringify(gridTemplate.itemsSource._src));
    // 레이어 팝업시 그리드 사이즈 뭉개짐 해결
    gridLayer.autoSizeMode = 2;
    gridLayer.autoSizeRow(0, true);

  }

  // 레이어 닫기 버튼 클릭
  $(".btn_close").click(function (e) {
    closeApplyTemplateLayer();
  });

  // 레이어 닫기
  function closeApplyTemplateLayer() {
    $("#applyTemplateLayer").hide();
    $("#applyTemplateDim").hide();
    // 레이어 그리드 내용 삭제
    gridLayer.itemsSource = [];
  }

});
