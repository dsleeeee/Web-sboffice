/**
 * get application
 */
var app = agrid.getApp();

/** 물량오류관리 그리드 controller */
app.controller('volmErrCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('volmErrCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["volmErr.orderSlipFg"]},
    {id: "-1", name: messages["volmErr.rtnSlipFg"]},
  ], 'id', 'name');

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["volmErr.reg"]},
    {id: "1", name: messages["volmErr.confirm"]},
  ], 'id', 'name');

  $scope._setComboData("srchSlipFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["volmErr.orderSlipFg"], "value": "1"},
    {"name": messages["volmErr.rtnSlipFg"], "value": "-1"}
  ]);

  $scope._setComboData("srchDateFg", [
    {"name": messages["volmErr.outDate"], "value": "out"},
    {"name": messages["volmErr.inDate"], "value": "in"}
  ]);

  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["volmErr.reg"], "value": "0"},
    {"name": messages["volmErr.confirm"], "value": "1"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("volmErrCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
        else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "slipNo") { // 전표번호 클릭
          var params    = {};
          params.slipNo = selectedRow.slipNo;
          params.slipFg = selectedRow.slipFg;
          params.procFg = selectedRow.procFg;
          params.storeCd = selectedRow.storeCd;
          params.storeNm = selectedRow.storeNm;
          params.hdRemark = selectedRow.remark;
          $scope._broadcast('volmErrDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("volmErrCtrl", function (event, data) {
    $scope.searchVolmErrList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 물량오류 리스트 조회
  $scope.searchVolmErrList = function () {
    // 파라미터
    var params       = {};
    params.dateFg    = $scope.dateFg;
    params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.storeCd   = $("#volmErrSelectStoreCd").val();
    params.slipFg    = $scope.slipFg;
    params.procFg    = $scope.procFg;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/volmErr/volmErr/volmErr/list.sb", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.volmErrSelectStoreShow = function () {
    $scope._broadcast('volmErrSelectStoreCtrl');
  };

}]);
