/****************************************************************
 *
 * 파일명 : dayStoreDtl.js
 * 설  명 : 매장별 매출현황 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.02.18     안동관      1.0
 * 2019.12.11     김설아      1.1
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('dayStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayStoreDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 합계
      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("dayStoreDtlCtrl", function(event, data) {
        $scope.wjDayStoreDtlLayer.show(true);
        $scope.searchDayStoreDtlList(data);
        event.preventDefault();
    });

    $scope.searchDayStoreDtlList = function(data){
        var params = {};
        // 기간별매출 > 일자별 탭 > 일별종합 탭
        if(data.gubun == "dayTotal") {
          params.saleDate = data.saleDate;
        }
        // 기간별매출 > 월별 탭 > 월별종합 탭
        if(data.gubun == "monthTotal") {
          params.yearMonth = data.yearMonth;
        }
        params.storeCd  = data.storeCd;
        params.gubun  = data.gubun;

        $scope._inquiryMain("/sale/day/day/dayStoreDtl/list.sb", params, function() {}, false);
    };

}]);