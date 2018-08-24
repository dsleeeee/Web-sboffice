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
var gridRepresentApp = agrid.genGrid('representApp', 'representCtrl', true);
var gridDetailApp = agrid.genGrid('detailApp', 'detailCtrl', false);
// 페이지로드 후 동작 선언
$(document).ready(function () {

  // grid 여러개일때 2번째 그리드부터 갯수만큼 지정해야 정상적으로 그려짐.
  angular.bootstrap(document.getElementById("gridDetail"), ['detailApp']);

  var srchNmcodeCd = wcombo.genInputText("#srchNmcodeCd", 3, "");
  var srchNmcodeNm = wcombo.genInputText("#srchNmcodeNm", 50, "");

  // 대표명칭 그리드 생성
  var gridRepresent = agrid.getGrid("gridRepresent");
  // 조회버튼 클릭
  $("#btnSearch").click(function (e) {
    srchGridRepresent();
  });

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

  // 대표명칭 그리드 목록 조회
  function srchGridRepresent() {
    var param = {};
    param.nmcodeGrpCd = "000";
    param.nmcodeCd = srchNmcodeCd.value;
    param.nmcodeNm = srchNmcodeNm.value;

    // 대표명칭 그리드 버튼 show
    function btnShowRepresent() {
      $("#btnAddRepresent").show();
      $("#btnDelRepresent").show();
      $("#btnSaveRepresent").show();
    }

    // 그리드 조회
    gridRepresent.search("/sys/cd/systemCd/systemCd/list.sb", param, btnShowRepresent());
  }

  // 대표명칭 추가 버튼 클릭
  $("#btnAddRepresent").click(function (e) {
    var value = {};
    value.nmcodeGrpCd = "000";

    gridRepresent.addNewRow(value, 1);
  });

  // 대표명칭 저장 버튼 클릭
  $("#btnSaveRepresent").click(function (e) {
    console.log(gridRepresent.flex);
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

  // 세부명칭 그리드 생성
  var gridDetail = agrid.getGrid("gridDetail");

  // ReadOnly 효과설정
  gridDetail.flex.formatItem.addHandler(function (s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      if (col.binding === "nmcodeCd") {
        var item = s.rows[e.row].dataItem;
        if (item.status != "I") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        } else {
          wijmo.removeClass(e.cell, 'wj-custom-readonly');
        }
      }
    }
  });

  // 세부명칭 그리드 목록 조회
  function srchGridDetail(value) {
    var param = {};
    param.nmcodeGrpCd = value;
    if (!value) {
      var selectedRow = gridRepresent.flex.selectedRows[0]._data;
      param.nmcodeGrpCd = selectedRow.nmcodeCd;
    }

    // 세부명칭 그리드 버튼 show
    function btnShowDetail() {
      $("#btnAddDetail").show();
      $("#btnDelDetail").show();
      $("#btnSaveDetail").show();
    }

    // 그리드 조회
    gridDetail.search("/sys/cd/systemCd/systemCd/list.sb", param, btnShowDetail());
  }

  // 세부명칭 추가 버튼 클릭
  $("#btnAddDetail").click(function (e) {
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    var value = {};
    value.nmcodeGrpCd = selectedRow.nmcodeCd;

    gridDetail.addNewRow(value, 1);
  });

  // 세부명칭 저장 버튼 클릭
  $("#btnSaveDetail").click(function (e) {
    console.log(gridDetail.flex);
    var paramArr = new Array();
    for (var i = 0; i < gridDetail.flex.collectionView.itemsEdited.length; i++) {
      gridDetail.flex.collectionView.itemsEdited[i].status = "U";
      paramArr.push(gridDetail.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < gridDetail.flex.collectionView.itemsAdded.length; i++) {
      gridDetail.flex.collectionView.itemsAdded[i].status = "I";
      paramArr.push(gridDetail.flex.collectionView.itemsAdded[i]);
    }

    if (paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/sys/cd/systemCd/systemCd/save.sb", paramArr, function (result) {
        s_alert.pop(messages["cmm.saveSucc"]);
        gridDetail.flex.collectionView.clearChanges();
        srchGridDetail();
      },
      function (result) {
        s_alert.pop(result.message);
      }
    );

  });

});
