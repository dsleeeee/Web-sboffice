/****************************************************************
 *
 * 파일명 : envConfg.js
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

  var srchEnvstCd = wcombo.genInputText("#srchEnvstCd", 4, "");
  var srchEnvstNm = wcombo.genInputText("#srchEnvstNm", 100, "");

  //TODO 검색조건 추가 => 진행중임
  var srchEnvstFg = wcombo.genCommonBox("#srchEnvstFg", envstFgNm);
  var srchEnvstGrpCd = wcombo.genCommonBox("#srchEnvstGrpCd", envstGrpCdNm);
  var srchTargtFg = wcombo.genCommonBox("#srchTargtFg", targtFg);

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
        header: messages["envConfg.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "envstCd", header: messages["envConfg.envstCd"], width: 70},
      {binding: "envstNm", header: messages["envConfg.envstNm"], maxLength: 100},
      {
        binding: "envstFg",
        header: messages["envConfg.envstFgNm"],
        width: 140,
        dataMap: envstFgNmDataMap
      },
      {
        binding: "envstGrpCd",
        header: messages["envConfg.envstGrpCdNm"],
        width: 100,
        dataMap: envstGrpCdNmDataMap
      },
      {
        binding: "dirctInYn",
        header: messages["envConfg.dirctInYn"],
        width: 70,
        dataMap: dirctInYnDataMap
      },
      {binding: "targtFg", header: messages["envConfg.targtFgNm"], dataMap: targtFgDataMap},
      {binding: "useYn", header: messages["envConfg.useYn"], width: 80, dataMap: useYnDataMap},
      {binding: "remark", header: messages["envConfg.remark"], width: 200, maxLength: 250}
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
    param.envstFg = srchEnvstFg.selectedValue;
    param.envstGrpCd = srchEnvstGrpCd.selectedValue;
    param.targtFg = srchTargtFg.selectedValue;

    console.log(param);

    $.postJSON("/sys/cd/envConfg/envConfg/envst/list.sb", param,
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
    newRow.envstFg = "";
    newRow.envstGrpCd = "";
    newRow.targtFg = "";
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

    console.log(paramArr)

    for(var i=0; i<paramArr.length; i++) {
      console.log(i + "번째");
      console.log(paramArr);
      if(paramArr[i].envstCd == "") {
        s_alert.pop(messages["envConfg.envstCd"] + messages["envConfg.require"]);
        return;
      }
      if(paramArr[i].envstNm == "") {
        s_alert.pop(messages["envConfg.envstNm"] + messages["envConfg.require"]);
        return;
      }
      if(paramArr[i].envstFg == "") {
        s_alert.pop(messages["envConfg.envstFg"] + messages["envConfg.require"]);
        return;
      }
      if(paramArr[i].envstGrpCd == "") {
        s_alert.pop(messages["envConfg.envstGrpCd"] + messages["envConfg.require"]);
        return;
      }
      if(paramArr[i].targtFg == "") {
        s_alert.pop(messages["envConfg.targtFg"] + messages["envConfg.require"]);
        return;
      }
    }

    $.postJSONArray("/sys/cd/envConfg/envConfg/envst/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridRepresent.collectionView.clearChanges();
        srchRepresent();
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
        header: messages["envConfg.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "envstValCd", header: messages["envConfg.envstValCd"], width: 70},
      {binding: "envstValNm", header: messages["envConfg.envstValNm"], width: "*"},
      {
        binding: "defltYn",
        header: messages["envConfg.defltYn"],
        width: 80,
        dataMap: defltYnDataMap
      },
      {
        binding: "useYn",
        header: messages["envConfg.useYn"],
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

    $.postJSON("/sys/cd/envConfg/envConfg/envstDtl/list.sb", param,
      function (result) {
        // 버튼 Show
        $("#btnAddDetail").show();
        $("#btnDelDetail").show();
        $("#btnSaveDetail").show();

        var list = result.data.list;
        //gridDetail.itemsSource.trackChanges = true;
        if (list.length === undefined || list.length == 0) {
          gridDetail.itemsSource = new wijmo.collections.CollectionView([]);
          gridDetail.itemsSource.trackChanges = true;
          s_alert.pop(result.message);
          return;
        }
        gridDetail.itemsSource = new wijmo.collections.CollectionView(list);
        gridDetail.itemsSource.trackChanges = true;
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

    console.log(gridDetail)

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

    $.postJSONArray("/sys/cd/envConfg/envConfg/envstDtl/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridDetail.collectionView.clearChanges();
        srchRepresent();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

});
