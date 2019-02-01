/****************************************************************
 *
 * 파일명 : charge.js
 * 설  명 : 선불금 충전 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.01     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선불충전 그리드 생성
 */
app.controller('chargeCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('chargeCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "membrNo" || col.binding === "membrNm") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 회원 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "membrNo" ||  col.binding === "membrNm") {
          $scope.save(selectedRow);
        }
      }
    });
  };

  $scope.$on("chargeCtrl", function(event, data) {
    $scope.getMemberList();
    event.preventDefault();
  });

  // 선불금 충전할 회원 목록 조회
  $scope.getMemberList = function(){
    var params = {};

    params.prepaidStoreCd = $("#searchChargeStoreCd").val();
    params.prepaidStoreNm = $("#searchChargeStoreNm").val();
    params.membrNo = $("#searchChargeMemberNo").val();
    params.membrNm = $("#searchChargeMemberNm").val();

    $scope._inquirySub(baseUrl + "charge/getChargeMemberList.sb", params, function() {}, false);
  };

  // 선불금 충전 처리
  $scope.save = function(data){

    if( $("#prepaidAmt").val() === null || $("#prepaidAmt").val() === "" || $("#prepaidAmt").val() === "0") {
      $scope._popMsg(messages["prepaid.request.prepaidAmt"]);
      return false;
    }

    var params = {};

    params.prepaidStoreCd = data.storeCd;
    params.membrNo = data.membrNo;
    params.prepaidAmt = $("#prepaidAmt").val();

    $scope._save(baseUrl + "charge/saveChargeAmt.sb", params, function(){ $scope.saveResult() });
  };

  // 선불금 충전 후처리
  $scope.saveResult = function (){
    var prepaidGrid = agrid.getScope('prepaidCtrl');
    prepaidGrid.searchPostpaid();
  };
}]);
