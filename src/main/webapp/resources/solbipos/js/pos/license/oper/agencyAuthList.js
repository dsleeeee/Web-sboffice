/****************************************************************
 *
 * 파일명 : agencyAuthList.js
 * 설  명 : 운영현황 대리점인증현황탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.30     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 업체구분 DropBoxDataMap
var agencyFgData = [
    {"name":"전체","value":""},
    {"name":"자사","value":"1"},
    {"name":"대리점","value":"2"}
];

/**
 *  대리점인증현황 그리드 생성
 */
app.controller('agencyAuthListCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('agencyAuthListCtrl', $scope, $http, true));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("agencyFg", agencyFgData); //업체구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부

        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("agencyAuthListCtrl", function(event, data) {
        $scope.searchAgencyAuthList();
        event.preventDefault();
    });

    // 대리점인증현황 그리드 조회
    $scope.searchAgencyAuthList = function() {
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간
        params.agencyCd = $("#agencyCdAuth").val();
        params.agencyNm = $("#agencyNmAuth").val();
        params.orgnCd = orgnCd;

        $scope._inquiryMain("/pos/license/oper/oper/getAgencyAuthList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);