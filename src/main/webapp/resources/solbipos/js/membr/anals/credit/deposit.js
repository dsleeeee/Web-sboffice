/****************************************************************
 *
 * 파일명 : deposit.js
 * 설  명 : 외상입금 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.09.26     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  후불회원 그리드 생성
 */
app.controller('depositCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('depositCtrl', $scope, $http, true));
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

  $scope.$on("depositCtrl", function(event, data) {
    $scope.getMemberList();
    event.preventDefault();
  });

  // 외상입금 대상 회원 목록 조회
  $scope.getMemberList = function(){
    var params = {};

    params.creditStoreCd = $("#searchDepositStoreCd").val();
    params.creditStoreNm = $("#searchDepositStoreNm").val();
    params.memberNo = $("#searchDepositMemberNo").val();
    params.memberNm = $("#searchDepositMemberNm").val();

    $scope._inquirySub(baseUrl + "deposit/getDepositMemberList.sb", params, function() {}, false);
  };

  // 외상입금 처리
  $scope.save = function(data){
    if( $("#creditAmt").val() === null || $("#creditAmt").val() === "" || $("#creditAmt").val() === "0") {
      s_alert.pop(messages["credit.request.creditAmt"]);
      return false;
    }

    var params = {};

    params.creditStoreCd = data.creditStoreCd;
    params.memberNo = data.membrNo;
    params.creditAmt = $("#creditAmt").val();

    $scope._save(baseUrl + "deposit/saveDeposit.sb", params, function(){ $scope.saveResult() });
  };

  // 외상입금 후처리
  $scope.saveResult = function (){


    var creditGrid = agrid.getScope('creditCtrl');
    creditGrid.searchCredit();
  };
}]);
