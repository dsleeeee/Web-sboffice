/**
 * get application
 */
var app = agrid.getApp();

/** 매장여신관리 그리드 controller */
app.controller('storeLoanManageCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeLoanManageCtrl', $scope, $http, true));

  //페이지 스케일 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 그리드 DataMap 설정
  $scope.orderFg = new wijmo.grid.DataMap([
    {id: "1", name: messages["loan.orderFg1"]},
    {id: "2", name: messages["loan.orderFg2"]},
    {id: "3", name: messages["loan.orderFg3"]}
  ], 'id', 'name');

  $scope.noOutstockAmtFg = new wijmo.grid.DataMap([
    {id: "N", name: messages["loan.noOutstockAmtFgN"]},
    {id: "Y", name: messages["loan.noOutstockAmtFgY"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("storeLoanManageCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd") { // 매장코드
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd") { // 매장코드 클릭
          var params     = {};
          params.storeCd = selectedRow.storeCd;
          params.storeNm = selectedRow.storeNm;
          $scope._broadcast('storeLoanManageDtlCtrl', params);
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 리스트 조회
  $scope.searchStoreLoanManage = function () {
    // 파라미터
    var params = {};
    // param.storeCd = $("#srchStoreCd").val();
    // param.storeNm = $("#srchStoreNm").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", params);
  };

  // 저장
  $scope.save = function () {
    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      if (item.limitLoanAmt !== null && item.maxOrderAmt === null) {
        $scope._popMsg(messages["loan.maxOrderAmt"]+" "+messages["cmm.require.text"]); // 1회주문한도액을 입력해주세요.
        return false;
      }
      if (item.maxOrderAmt !== null && item.limitLoanAmt === null) {
        $scope._popMsg(messages["loan.limitLoanAmt"]+" "+messages["cmm.require.text"]); // 여신한도액을 입력해주세요.
        return false;
      }

      item.status = "U";
      params.push(item);
    }

    $scope._save("/iostock/loan/storeLoanManage/storeLoanManage/save.sb", params, function () {
      $scope.searchStoreLoanManage()
    });
  };

}]);
