/****************************************************************
 *
 * 파일명 : posPostpaid.js
 * 설  명 : 후불금액 세금계산서 발행 요청 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.03     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();



/**
 *  요청 대상 회원 그리드 생성
 */
app.controller('memberCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberCtrl', $scope, $http, true));

  // 선택된 멤버
  $scope.member;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // $scope.searchPostPaidList();
    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;

        if (col.binding === "deposit") {
          if(item.deposit === '발행요청') {
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      }
    });

    // 세금계산서 발행 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        $scope.member = s.rows[ht.row].dataItem;
        if ( col.binding === "deposit" ) {
          console.log('$scope.member.deposit', $scope.member.deposit);
          if( $scope.member.deposit === '발행요청' ) {
            // 세금계산서 발행요청 팝업 오픈
            $scope.requestTaxBillLayer.show(true, function () {
              $scope.searchPostPaidList();
            });
          }
        }
      }
    });
  };

  $scope.$on("memberCtrl", function(event, data) {
    $scope.searchPostPaidList();
    event.preventDefault();
  });

  // 대상회원 목록 조회
  $scope.searchPostPaidList = function(){
    var params = {};
    params.listScale = 30;
    $scope._inquiryMain("/application/pos/posPostpaid/posPostpaid/getMemberList.sb", params, function() {}, false);
  };


  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 세금계산서 발행 팝업
    $scope.requestTaxBillLayer.shown.addHandler(function (s) {
      setTimeout(function() {
        var params = $scope.member;
        $scope._broadcast('requestTaxBillCtrl', params);
      }, 50);
    });
  });
}]);


