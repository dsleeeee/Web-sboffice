/****************************************************************
 *
 * 파일명 : dayStoreBill.js
 * 설  명 : 매장별 영수건수 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.12.18     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('dayStoreBillCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayStoreBillCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("dayStoreBillCtrl", function(event, data) {
        $scope.wjDayStoreBillLayer.show(true);
        $scope.searchDayStoreBillList(data);
        event.preventDefault();
    });

    $scope.searchDayStoreBillList = function(data){
        var params = {};
        // 기간별매출 > 일자별 탭 > 일별종합 탭
        if(data.gubun == "day") {
            params.saleDate = data.saleDate;
        }
        // 기간별매출 > 월별 탭 > 월별종합 탭
        if(data.gubun == "month") {
            params.yearMonth = data.yearMonth;
        }
        params.storeCds  = data.storeCd;
        params.gubun  = data.gubun;

        $scope._inquiryMain("/sale/cmmSalePopup/dayBillInfo/dayBillInfo/getDayBillInfoList.sb", params, function() {}, false);
    };

}]);