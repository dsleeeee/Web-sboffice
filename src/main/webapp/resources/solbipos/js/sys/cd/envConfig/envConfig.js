/****************************************************************
 *
 * 파일명 : envConfig.js
 * 설  명 : 환경설정관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
$(document).ready(function () {

  // 조회버튼 클릭
  $("#btnSearch").click(function (e) {
    srchRepresent();
  });

  var srchEnvstCd = wcombo.genInputText("#srchEnvstCd", 3, "");
  var srchEnvstNm = wcombo.genInputText("#srchEnvstNm", 100, "");

  var envstFgNmDataMap = new wijmo.grid.DataMap(envstFgNm, 'value', 'name');
  var envstGrpCdNmDataMap = new wijmo.grid.DataMap(envstGrpCdNm, 'value', 'name');
  var targtFgDataMap = new wijmo.grid.DataMap(targtFg, 'value', 'name');
  var dirctInYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "직접"}, {
    id: "N",
    name: "선택"
  }], 'id', 'name');
  var useYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "사용"}, {
    id: "N",
    name: "사용안함"
  }], 'id', 'name');
  var defltYnDataMap = new wijmo.grid.DataMap([{id: "Y", name: "기본"}, {
    id: "N",
    name: "기본아님"
  }], 'id', 'name');

  // 대표명칭 그리드
  var gridRepresentData =
    [
      {
        binding: "gChk",
        header: messages["envConfig.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "envstCd", header: messages["envConfig.envstCd"], width: 70},
      {binding: "envstNm", header: messages["envConfig.envstNm"], maxLength: 100},
      {
        binding: "envstFg",
        header: messages["envConfig.envstFgNm"],
        width: 140,
        dataMap: envstFgNmDataMap
      },
      {
        binding: "envstGrpCd",
        header: messages["envConfig.envstGrpCdNm"],
        width: 100,
        dataMap: envstGrpCdNmDataMap
      },
      {
        binding: "dirctInYn",
        header: messages["envConfig.dirctInYn"],
        width: 70,
        dataMap: dirctInYnDataMap
      },
      {binding: "targtFg", header: messages["envConfig.targtFgNm"], dataMap: targtFgDataMap},
      {binding: "useYn", header: messages["envConfig.useYn"], width: 80, dataMap: useYnDataMap},
      {binding: "remark", header: messages["envConfig.remark"], width: 200, maxLength: 250}
    ];
  // 대표명칭 그리드 생성
  var gridRepresent = wgrid.genGrid("#gridRepresent", gridRepresentData, "Y");
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다.
  gridRepresent.isReadOnly = false;

  // ReadOnly 효과설정
  gridRepresent.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "envstCd") {
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

  // 대표명칭 그리드 에디팅 방지
  gridRepresent.beginningEdit.addHandler(function (s, e) {
    var col = s.columns[e.col];
    if (col.binding === "envstCd") {
      var dataItem = gridRepresent.rows[e.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        e.cancel = true;
      }
    }
  });

  // 대표명칭 그리드 선택 이벤트
  gridRepresent.selectionChanged.addHandler(function (s, e) {
    var col = s.columns[e.col];
    var selectedRow = gridRepresent.rows[e.row].dataItem;
    if (col.binding === "envstCd" && selectedRow.status != "I") {
      srchGridDetail(selectedRow.envstCd);
    }
  });

  // 대표명칭 그리드 조회
  function srchRepresent() {
    var param = {};
    param.envstCd = srchEnvstCd.value;
    param.envstNm = srchEnvstNm.value;

    $.postJSON("/sys/cd/envConfig/envConfig/envst/list.sb", param,
      function (result) {
        // 버튼 Show
        $("#btnAddRepresent").show();
        $("#btnDelRepresent").show();
        $("#btnSaveRepresent").show();

        var list = result.data.list;
        gridRepresent.itemsSource = new wijmo.collections.CollectionView(list);
        gridRepresent.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          gridRepresent.itemsSource = new wijmo.collections.CollectionView([]);
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

  // 대표명칭 추가 버튼 클릭
  $("#btnAddRepresent").click(function (e) {
    gridRepresent.collectionView.trackChanges = true;
    var newRow = gridRepresent.collectionView.addNew();
    newRow.status = "I";
    newRow.gChk = true;
    newRow.dirctInYn = "N";
    newRow.useYn = "N";

    gridRepresent.collectionView.commitNew();
    // 추가된 Row 선택
    gridRepresent.select(gridRepresent.rows.length, 1);
  });

  // 대표명칭 저장 버튼 클릭
  $("#btnSaveRepresent").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridRepresent.collectionView.itemsEdited.length; i++) {
      gridRepresent.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridRepresent.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridRepresent.collectionView.itemsAdded.length; i++) {
      gridRepresent.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridRepresent.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/cd/envConfig/envConfig/envst/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridRepresent.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

  // 세부명칭 그리드
  var gridDetailData =
    [
      {
        binding: "gChk",
        header: messages["envConfig.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "envstValCd", header: messages["envConfig.envstValCd"], width: 70},
      {binding: "envstValNm", header: messages["envConfig.envstValNm"], width: "*"},
      {
        binding: "defltYn",
        header: messages["envConfig.defltYn"],
        width: 80,
        dataMap: defltYnDataMap
      },
      {
        binding: "useYn",
        header: messages["envConfig.useYn"],
        width: 80,
        dataMap: useYnDataMap
      }
    ];
  // 세부명칭 그리드 생성
  var gridDetail = wgrid.genGrid("#gridDetail", gridDetailData);
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다.
  gridDetail.isReadOnly = false;

  // 세부명칭 그리드 조회
  function srchGridDetail(value) {

    var param = {};
    param.envstCd = value;

    $.postJSON("/sys/cd/envConfig/envConfig/envstDtl/list.sb", param,
      function (result) {
        // 버튼 Show
        $("#btnAddDetail").show();
        $("#btnDelDetail").show();
        $("#btnSaveDetail").show();

        var list = result.data.list;
        gridDetail.itemsSource = new wijmo.collections.CollectionView(list);
        gridDetail.itemsSource.trackChanges = true;
        if (list.length === undefined || list.length == 0) {
          gridDetail.itemsSource = new wijmo.collections.CollectionView([]);
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

  // 세부명칭 추가 버튼 클릭
  $("#btnAddDetail").click(function (e) {
    var selectedRow = gridRepresent.selectedRows[0]._data;
    var newRow = gridDetail.collectionView.addNew();
    newRow.envstCd = selectedRow.envstCd;
    newRow.gChk = true;
    newRow.defltYn = "N";
    newRow.useYn = "N";

    gridDetail.collectionView.commitNew();
    // 추가된 Row 선택
    gridDetail.select(gridDetail.rows.length, 1);
  });

  // 세부명칭 저장 버튼 클릭
  $("#btnSaveDetail").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridDetail.collectionView.itemsEdited.length; i++) {
      gridDetail.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridDetail.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridDetail.collectionView.itemsAdded.length; i++) {
      gridDetail.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridDetail.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/cd/envConfig/envConfig/envstDtl/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridDetail.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

});
