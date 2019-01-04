/**
 * get application
 */
var app = agrid.getApp();

/** 매장이동관리 그리드 controller */
app.controller('storeMoveCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeMoveCtrl', $scope, $http, true));

  var srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
  $scope.outStoreCd = gvStoreCd;

  $scope.ioFgMap = new wijmo.grid.DataMap([
    {id: "out", name: messages["storeMove.out"]},
    {id: "in", name: messages["storeMove.in"]}
  ], 'id', 'name');

  $scope.dlvrFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["storeMove.dlvrFg0"]},
    {id: "1", name: messages["storeMove.dlvrFg1"]},
    {id: "2", name: messages["storeMove.dlvrFg2"]}
  ], 'id', 'name');

  $scope.procFgMap = new wijmo.grid.DataMap([
    {id: "0", name: messages["storeMove.procFg0"]},
    {id: "1", name: messages["storeMove.procFg1"]},
    {id: "2", name: messages["storeMove.procFg2"]},
    {id: "3", name: messages["storeMove.procFg3"]}
  ], 'id', 'name');

  $scope._setComboData("srchIoFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["storeMove.out"], "value": "out"},
    {"name": messages["storeMove.in"], "value": "in"}
  ]);

  $scope._setComboData("srchDlvrFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["storeMove.dlvrFg0"], "value": "0"},
    {"name": messages["storeMove.dlvrFg1"], "value": "1"},
    {"name": messages["storeMove.dlvrFg2"], "value": "2"}
  ]);

  $scope._setComboData("srchProcFg", [
    {"name": messages["cmm.all"], "value": ""},
    {"name": messages["storeMove.procFg0"], "value": "0"},
    {"name": messages["storeMove.procFg1"], "value": "1"},
    {"name": messages["storeMove.procFg2"], "value": "2"},
    {"name": messages["storeMove.procFg3"], "value": "3"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeMoveCtrl");

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
          $scope._broadcast('storeMoveDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("storeMoveCtrl", function (event, data) {
    $scope.searchStoreMoveList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장이동관리 리스트 조회
  $scope.searchStoreMoveList = function () {
    // 파라미터
    var params       = {};
    params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
    params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
    params.outStoreCd = $scope.outStoreCd;
    params.inStoreCd  = $("#storeMoveInSelectStoreCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/move/storeMove/storeMove/list.sb", params);
  };

  // 신규등록
  $scope.newRegist = function () {
    var params    = {};
    $scope._broadcast("storeMoveRegistCtrl", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeMoveInSelectStoreShow = function () {
    $scope._broadcast('storeMoveInSelectStoreCtrl');
  };

}]);
