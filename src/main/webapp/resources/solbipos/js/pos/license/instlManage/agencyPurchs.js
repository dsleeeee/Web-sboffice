/****************************************************************
 *
 * 파일명 : agencyPurchs.js
 * 설  명 : 매장별매출 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.09.16     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  팝업 그리드 생성
 */
app.controller('agencyPurchsCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyPurchsCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 팝업 호출시 상세정보 조회
    $scope.$on("agencyPurchsCtrl", function(event, data) {
        $scope.agencyPurchsLayer.show(true);
        $scope.searchAgencyPurchsList(data);

        $("#lblAgencyCdPurchs").text("[" + data.agencyCd + "]");
        $("#lblAgencyNmPurchs").text(data.agencyNm);
        $("#lblStartDatePurchs").text(messages["instl.date"] + " : " + data.startDate + " ~");
        $("#lblEndDatePurchs").text(data.endDate);

        event.preventDefault();
    });

    // 업체현황 상세조회
    $scope.searchAgencyPurchsList = function(data){
        var params = {};
        params.agencyCd = data.agencyCd;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        $scope._inquiryMain("/pos/license/instlManage/instlManage/getAgencyPurchsList.sb", params, function() {}, false);
    };

}]);