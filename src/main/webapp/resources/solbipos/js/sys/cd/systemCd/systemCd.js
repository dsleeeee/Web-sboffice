/****************************************************************
 *
 * 파일명 : systemCd.js
 * 설  명 : 시스템명칭관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     노현수      1.0
 *
 * **************************************************************/
// angularjs App 생성
var gridRepresentApp = agrid.genGrid('app', 'appTCCtrl');
var gridRepresent;
// 페이지로드 후 동작 선언
$(document).ready(function () {

  var srchNmcodeCd = wcombo.genInputText("#srchNmcodeCd", 3, "");
  var srchNmcodeNm = wcombo.genInputText("#srchNmcodeNm", 50, "");

  // 대표명칭 그리드 생성
  gridRepresent = agrid.getGrid("gridRepresent");

  // 조회버튼 클릭
  $("#btnSearch").click(function (e) {
    srchGridRepresent();
  });

  // var gridRepresentData =
  //   [
  //     {
  //       binding: "gChk",
  //       header: messages["systemCd.chk"],
  //       dataType: wijmo.DataType.Boolean,
  //       width: 40
  //     },
  //     {binding: "nmcodeCd", header: messages["systemCd.nmcodeCd"], width: 60},
  //     {binding: "nmcodeNm", header: messages["systemCd.nmcodeNm"], width: "*"},
  //     {binding: "nmcodeItem1", header: messages["systemCd.nmcodeItem1"], width: "*"},
  //     {binding: "nmcodeItem2", header: messages["systemCd.nmcodeItem2"], width: "*"},
  //     {binding: "useColNm", header: messages["systemCd.useColNm"], width: "*"}
  //   ];
  // // 대표명칭 그리드 생성
  // var gridRepresent = wgrid.genGrid("#gridRepresent", gridRepresentData);
  // gridRepresent.isReadOnly = false;

  // ReadOnly 효과설정
  gridRepresent.flex.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "nmcodeCd") {
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
  gridRepresent.flex.beginningEdit.addHandler(function (sender, elements) {
    var col = sender.columns[elements.col];
    if (col.binding === "nmcodeCd") {
      var dataItem = gridRepresent.flex.rows[elements.row].dataItem;
      if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
        elements.cancel = true;
      }
    }
  });

  // 대표명칭 그리드 선택 이벤트
  gridRepresent.flex.selectionChanged.addHandler(function (s, e) {
    var col = s.columns[e.col];
    var selectedRow = gridRepresent.flex.rows[e.row].dataItem;
    if (col.binding === "nmcodeCd" && selectedRow.status != "I") {
      srchGridDetail(selectedRow.nmcodeCd);
    }
  });

  function showBtn() {
    // 버튼 Show
    $("#btnAddRepresent").show();
    $("#btnDelRepresent").show();
    $("#btnSaveRepresent").show();
  }

  // 대표명칭 그리드 목록 조회
  function srchGridRepresent() {
    var param = {};
    param.nmcodeGrpCd = "000";
    param.nmcodeCd = srchNmcodeCd.value;
    param.nmcodeNm = srchNmcodeNm.value;
    // 그리드 조회
    gridRepresent.search("/sys/cd/systemCd/systemCd/list.sb", param, showBtn());

  }

  // 대표명칭 추가 버튼 클릭
  $("#btnAddRepresent").click(function (e) {
    var value = {};
    value.nmcodeGrpCd = "000";

    gridRepresent.addNewRow(value, 1);
  });

  // 대표명칭 저장 버튼 클릭
  $("#btnSaveRepresent").click(function (e) {

    var paramArr = new Array();

    for (var i = 0; i < gridRepresent.flex.collectionView.itemsEdited.length; i++) {
      gridRepresent.flex.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridRepresent.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridRepresent.flex.collectionView.itemsAdded.length; i++) {
      gridRepresent.flex.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridRepresent.flex.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/cd/systemCd/systemCd/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridRepresent.flex.collectionView.clearChanges();
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
        header: messages["systemCd.chk"],
        dataType: wijmo.DataType.Boolean,
        width: 40
      },
      {binding: "nmcodeCd", header: messages["systemCd.nmcodeCd"], width: 60},
      {binding: "nmcodeNm", header: messages["systemCd.nmcodeNm"], width: "*"},
      {binding: "nmcodeItem1", header: messages["systemCd.nmcodeItem1"], width: "*"},
      {binding: "nmcodeItem2", header: messages["systemCd.nmcodeItem2"], width: "*"}
    ];
  // 세부명칭 그리드 생성
  var gridDetail = wgrid.genGrid("#gridDetail", gridDetailData);
  // 읽기전용을 해제하지 않으면 그리드 에디팅이 되지 않는다.
  gridDetail.isReadOnly = false;

  // 세부명칭 그리드 목록 조회
  function srchGridDetail(value) {

    var param = {};
    param.nmcodeGrpCd = value;

    $.postJSON("/sys/cd/systemCd/systemCd/list.sb", param,
      function (result) {

        // 버튼 Show
        $("#btnAddDetail").show();
        $("#btnDelDetail").show();
        $("#btnSaveDetail").show();

        var list = result.data.list;
        gridDetail.itemsSource = new wijmo.collections.CollectionView(list);
        gridDetail.itemsSource.trackChanges = true;

        if (list.length === undefined || list.length == 0) {
          // 그리드 초기화
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
    var selectedRow = gridDetail.selectedRows[0]._data;

    gridDetail.collectionView.trackChanges = true;
    var newRow = gridDetail.collectionView.addNew();
    newRow.nmcodeGrpCd = selectedRow.nmcodeCd;
    newRow.gChk = true;

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

    $.postJSONArray("/sys/cd/systemCd/systemCd/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridDetail.collectionView.clearChanges();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

});
