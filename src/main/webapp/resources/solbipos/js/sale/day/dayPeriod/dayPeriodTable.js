/****************************************************************
 *
 * 파일명 : dayPeriodTable.js
 * 설  명 : 기간별매출 > 설정기간별탭 > 외식테이블별 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.03     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  외식테이블별 매출 조회 그리드 생성
 */
app.controller('dayPeriodTableCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayPeriodTableCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDateDayPeriodTable", gvStartDate);
    var endDate = wcombo.genDateVal("#endDateDayPeriodTable", gvEndDate);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayPeriodTableCtrl", function(event, data) {
        $scope.searchDayPeriodTable();
        event.preventDefault();
    });

    $scope.searchDayPeriodTable = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._inquiryMain("/sale/day/dayPeriod/dayPeriod/getDayPeriodTableList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);