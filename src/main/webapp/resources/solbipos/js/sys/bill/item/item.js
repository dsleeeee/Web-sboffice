/****************************************************************
 *
 * 파일명 : item.js
 * 설  명 : 출력코드구성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.10     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

  var srchPrtCd = wcombo.genInputText("#srchPrtCd", 30, "");
  var srchPrtNm = wcombo.genInputText("#srchPrtNm", 50, "");
  // var srchPrtNm = wcombo.genInputNumber("#srchPrtNm", "n2", "", 0, 100);

  // 출력코드구성 그리드
  var gridPrintCodeData =
    [
      {binding: "gChk", header: messages["item.chk"], dataType: wijmo.DataType.Boolean, width: 40},
      {binding: "prtCd", header: messages["item.prtCd"], width: 200},
      {binding: "prtNm", header: messages["item.prtNm"], width: 200},
      {
        binding: "samplYn",
        header: messages["item.samplYn"],
        dataType: wijmo.DataType.Boolean,
        width: 60
      },
      {binding: "content", header: messages["item.content"], width: "*"}
    ];
  // 출력코드구성 그리드 생성
  var gridPrintCode = wgrid.genGrid("#gridPrintCode", gridPrintCodeData);
  gridPrintCode.isReadOnly = false;

  // ReadOnly 효과설정
  gridPrintCode.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "prtCd") {
        var item = s.rows[e.row].dataItem;
        if (item.status != "I") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else {
          wijmo.removeClass(e.cell, 'wj-custom-readonly');
        }
      }
    }
  });

  // 출력코드구성 그리드 에디팅 방지
  gridPrintCode.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "prtCd") {
      var dataItem = gridPrintCode.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        e.cancel = true;
      }
    }
  });

  var contentColumn = gridPrintCode.columns.getColumn("content");
  contentColumn.multiLine = true;
  contentColumn.wordWrap = true;

  // auto-size visible rows
  function autoSizeVisibleRows(flex, force) {
    var rng = flex.viewRange;
    for (var r = rng.row; r <= rng.row2; r++) {
      if (force || flex.rows[r].height == null) {
        // flex.autoSizeRow(r, false)
      }
    }
  }

  // validation
  gridPrintCode.cellEditEnded.addHandler(function (s, e) {
    // autoSizeVisibleRows(s, true);
  });

  // 조회버튼 클릭
  $("#btnSearch").click(function (e) {
    search();
  });

  // 출력코드구성 목록 조회
  function search() {

    var param = {};
    param.prtCd = srchPrtCd.text;
    param.prtNm = srchPrtNm.text;

    $.postJSON("/sys/bill/item/item/list.sb", param,
      function (result) {

        // 버튼 Show
        $("#btnAdd").show();
        $("#btnDel").show();
        $("#btnSave").show();

        var list = result.data.list;
        gridPrintCode.itemsSource = new wijmo.collections.CollectionView(list);
        gridPrintCode.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          // 그리드 초기화
          gridPrintCode.itemsSource = new wijmo.collections.CollectionView([]);
          s_alert.pop(result.message);
          return;
        }

      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );

  };

  // 출력코드구성 저장 버튼 클릭
  $("#btnSave").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridPrintCode.collectionView.itemsEdited.length; i++) {
      gridPrintCode.collectionView.itemsEdited[i].status = "U";
      paramArr.push(theGrid.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridPrintCode.collectionView.itemsAdded.length; i++) {
      gridPrintCode.collectionView.itemsAdded[i].status = "I";
      paramArr.push(theGrid.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/bill/item/item/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridPrintCode.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

  // 출력코드구성 추가 버튼 클릭
  $("#btnAdd").click(function (e) {
    gridPrintCode.collectionView.trackChanges = true;
    var newRow = gridPrintCode.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;
    newRow.samplYn = false;

    gridPrintCode.collectionView.commitNew();
    // 추가된 Row 선택
    gridPrintCode.select(gridPrintCode.rows.length, 1);
  });

});
