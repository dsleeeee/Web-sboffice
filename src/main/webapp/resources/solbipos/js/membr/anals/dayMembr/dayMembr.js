/****************************************************************
 *
 * 파일명 : dayMembr.js
 * 설  명 : 일자별회원 구매내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.08.13     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  일자별회원 구매내역 그리드 생성
 */
app.controller('dayMembrCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dayMembrCtrl', $scope, $http, true));
    /*
    // 접속사용자의 권한(H : 본사, S: 매장)
    $scope.orgnFg = gvOrgnFg;

    // 매장권한으로 로그인 한 경우, 본인매장만 내역 조회가능.
    if($scope.orgnFg === 'S') {
       $scope.storeCds = gvStoreCd;
    }
   */

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 페이지 스케일
    // comboBox 초기화
    //$scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.saleFgDataMap = new wijmo.grid.DataMap(saleFgData, 'value', 'name');

        //합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("dayMembrCtrl", function(event, data) {
        $scope.searchDayMembr();
        event.preventDefault();
    });

    // 후불회원상세 그리드 조회
    $scope.searchDayMembr = function(){

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); //조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); //조회기간

        $scope._inquiryMain("/membr/anals/dayMembr/dayMembr/getDayMembrPurchaseList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);