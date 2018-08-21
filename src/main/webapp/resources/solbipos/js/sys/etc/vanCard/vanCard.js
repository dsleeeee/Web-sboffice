/****************************************************************
 *
 * 파일명 : vanCard.js
 * 설  명 : Van/Card사 관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

  // 조회버튼 클릭 
  $("#btnSearch").click(function (e) {
    searchVan();
    searchCard();
  });

  // VAN사 그리드 
  var gridVanData =
    [
      {
        binding: "gChk",
        header: messages["vanCard.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "vanCd", header: messages["vanCard.vanCd"], width: 60},
      {binding: "vanNm", header: messages["vanCard.vanNm"], width: "*"},
      {binding: "mainIp", header: messages["vanCard.mainIp"], width: "*"},
      {binding: "mainPort", header: messages["vanCard.mainPort"], width: "*"},
      {binding: "subIp", header: messages["vanCard.subIp"], width: "*"},
      {binding: "subPort", header: messages["vanCard.subPort"], width: "*"},
      {binding: "telNo", header: messages["vanCard.telNo"], width: "*"},
      {binding: "faxNo", header: messages["vanCard.faxNo"], width: "*"}
    ];
  // VAN사 그리드 생성 
  var gridVan = wgrid.genGrid("#gridVan", gridVanData);
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. 
  gridVan.isReadOnly = false;

  // ReadOnly 효과설정
  gridVan.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "vanCd") {
        var item = s.rows[e.row].dataItem;
        if (item.status != "I") {
          wijmo.addClass(e.cell, 'wijLink wj-custom-readonly');
          // wijmo.addClass(e.cell, 'wijLink');
          // wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else {
          wijmo.removeClass(e.cell, 'wj-custom-readonly');
        }
      }
    }
  });

  // VAN사 그리드 에디팅 방지
  gridVan.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "vanCd") {
      var dataItem = gridVan.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        e.cancel = true;
      }
    }
  });

  // VAN사 그리드 선택 이벤트
  gridVan.selectionChanged.addHandler(function (s, e) {
    var col = s.columns[e.col];
    var selectedRow = gridVan.rows[e.row].dataItem;
    if (col.binding === "vanCd" && selectedRow.status != "I") {
      searchMapping(selectedRow.vanCd);
    }
  });

  // VAN사 그리드 조회
  function searchVan() {

    var param = {};

    $.postJSON("/sys/etc/vanCard/vanCard/van/list.sb", param,
      function (result) {

        // 버튼 Show
        $("#btnAddVan").show();
        $("#btnDelVan").show();
        $("#btnSaveVan").show();

        var list = result.data.list;
        gridVan.itemsSource = new wijmo.collections.CollectionView(list);
        gridVan.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          gridVan.itemsSource = new wijmo.collections.CollectionView([]);
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

  // VAN사 그리드 추가 버튼 클릭 
  $("#btnAddVan").click(function (e) {
    gridVan.collectionView.trackChanges = true;
    var newRow = gridVan.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;

    gridVan.collectionView.commitNew();
    // 추가된 Row 선택
    gridVan.select(gridVan.rows.length, 1);
  });

  // VAN사 그리드 저장 버튼 클릭 
  $("#btnSaveVan").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridVan.collectionView.itemsEdited.length; i++) {
      gridVan.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridVan.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridVan.collectionView.itemsAdded.length; i++) {
      gridVan.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridVan.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/etc/vanCard/vanCard/van/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridVan.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

  // CARD사 그리드 
  var gridCardData =
    [
      {
        binding: "gChk",
        header: messages["vanCard.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "cardcoCd", header: messages["vanCard.cardcoCd"], width: 70},
      {binding: "cardcoNm", header: messages["vanCard.cardcoNm"], width: "*"},
      {binding: "bizNo", header: messages["vanCard.bizNo"], width: "*"},
      {binding: "telNo", header: messages["vanCard.telNo"], width: "*"},
      {binding: "faxNo", header: messages["vanCard.faxNo"], width: "*"},
      {binding: "hmpgAddr", header: messages["vanCard.hmpgAddr"], width: "*"}
    ];
  // CARD사 그리드 생성 
  var gridCard = wgrid.genGrid("#gridCard", gridCardData);
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. 
  gridCard.isReadOnly = false;

  // ReadOnly 효과설정
  gridCard.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "cardcoCd") {
        var item = s.rows[e.row].dataItem;
        if (item.status != "I") {
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else {
          wijmo.removeClass(e.cell, 'wj-custom-readonly');
        }
      }
    }
  });

  // CARD사 그리드 에디팅 방지
  gridCard.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "cardcoCd") {
      var dataItem = gridCard.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        e.cancel = true;
      }
    }
  });

  // CARD사 그리드 선택 이벤트
  gridCard.selectionChanged.addHandler(function (s, e) {
    var col = s.columns[e.col];
    var selectedRow = gridCard.rows[e.row].dataItem;
    if (col.binding === "cardcoCd" && selectedRow.status != "I") {
      searchMapping(selectedRow.vanCd);
    }
  });

  // CARD사 그리드 조회
  function searchCard() {

    var param = {};

    $.postJSON("/sys/etc/vanCard/vanCard/card/list.sb", param,
      function (result) {
        // 버튼 Show
        $("#btnAddCard").show();
        $("#btnDelCard").show();
        $("#btnSaveCard").show();

        var list = result.data.list;
        gridCard.itemsSource = new wijmo.collections.CollectionView(list);
        gridCard.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          gridCard.itemsSource = new wijmo.collections.CollectionView([]);
          s_alert.pop(result.message);
          return;
        }

      },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  // CARD사 그리드 추가 버튼 클릭 
  $("#btnAddCard").click(function (e) {
    gridCard.collectionView.trackChanges = true;
    var newRow = gridCard.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;

    gridCard.collectionView.commitNew();
    // 추가된 Row 선택
    gridCard.select(gridCard.rows.length, 1);
  });

  // CARD사 그리드 저장 버튼 클릭 
  $("#btnSaveCard").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridCard.collectionView.itemsEdited.length; i++) {
      gridCard.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridCard.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridCard.collectionView.itemsAdded.length; i++) {
      gridCard.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridCard.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/etc/vanCard/vanCard/card/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridCard.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

  var cardCmpnyDataMap = new wijmo.grid.DataMap(cardCmpnyList, 'value', 'name');

  // VAN/CARD사 매핑 그리드 
  var gridMapngData =
    [
      {
        binding: "gChk",
        header: messages["vanCard.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "vanNm", header: messages["vanCard.vanNm"], width: "*"},
      {binding: "vanCardcoCd", header: messages["vanCard.vanCardcoCd"], width: "*"},
      {binding: "vanCardcoNm", header: messages["vanCard.vanCardcoNm"], width: "*"},
      {
        binding: "cardcoCd",
        header: messages["vanCard.cardcoCd"],
        width: "*",
        dataMap: cardCmpnyDataMap
      }
    ];
  // VAN/CARD사 매핑 그리드 생성 
  var gridMapng = wgrid.genGrid("#gridMapping", gridMapngData);
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다. 
  gridMapng.isReadOnly = false;

  // ReadOnly 효과설정
  gridMapng.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "vanCd") {
        var item = s.rows[e.row].dataItem;
        if (item.status != "I") {
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else {
          wijmo.removeClass(e.cell, 'wj-custom-readonly');
        }
      }
    }
  });

  // VAN/CARD사 매핑 그리드 에디팅 방지
  gridMapng.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "vanCd") {
      var dataItem = gridMapng.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        e.cancel = true;
      }
    }
  });

  // VAN/CARD사 매핑 그리드 조회
  function searchMapping(value) {

    var param = {};
    param.vanCd = value;

    $.postJSON("/sys/etc/vanCard/vanCard/mapng/list.sb", param,
      function (result) {

        // 버튼 Show 
        $("#btnAddMapng").show();
        $("#btnDelMapng").show();
        $("#btnSaveMapng").show();

        var list = result.data.list;
        gridMapng.itemsSource = new wijmo.collections.CollectionView(list);
        gridMapng.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          // 그리드 초기화 
          gridMapng.itemsSource = new wijmo.collections.CollectionView([]);
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

  // VAN/CARD사 매핑 그리드 추가 버튼 클릭 
  $("#btnAddMapng").click(function (e) {

    var selectedVan = gridVan.selectedRows[0]._data;

    gridMapng.collectionView.trackChanges = true;
    var newRow = gridMapng.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;
    newRow.vanCd = selectedVan.vanCd;
    newRow.vanNm = selectedVan.vanNm;

    gridMapng.collectionView.commitNew();
    // 추가된 Row 선택
    gridMapng.select(gridMapng.rows.length, 1);
  });

  // VAN/CARD사 매핑 그리드 저장 버튼 클릭 
  $("#btnSaveMapng").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridMapng.collectionView.itemsEdited.length; i++) {
      gridMapng.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridMapng.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridMapng.collectionView.itemsAdded.length; i++) {
      gridMapng.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridMapng.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/etc/vanCard/vanCard/mapng/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridMapng.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

});
