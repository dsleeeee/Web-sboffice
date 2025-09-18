/****************************************************************
 *
 * 파일명 : postpaidSale.js
 * 설  명 : 외상매출조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.09.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분
var saleFgComboData = [
    {"name": "전체", "value": ""},
    {"name": "POS", "value": "POS"},
    {"name": "WEB", "value": "WEB"},
    {"name": "ERP", "value": "ERP"}
];

/**
 *  외상매출조회 그리드 생성
 */
app.controller('postpaidSaleCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('postpaidSaleCtrl', $scope, $http, false));

    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#startDate", gvStartDate);
    var endDate = wcombo.genDateVal("#endDate", gvEndDate);

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("saleFgCombo", saleFgComboData); // 매출구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("postpaidSaleCtrl", function(event, data) {
        $scope.searchPostpaidSale();
        event.preventDefault();
    });

    $scope.searchPostpaidSale = function(){
        var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));

        // 시작일자가 종료일자보다 빠른지 확인
        if(startDt.getTime() > endDt.getTime()){
            $scope._popMsg(messages['cmm.dateChk.error']);
            return false;
        }

        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd'); // 조회기간
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd'); // 조회기간
        params.membrNo = $scope.membrNo;
        params.membrNm = $scope.membrNm;
        params.saleFg = $scope.saleFg;

        $scope._inquiryMain("/membr/anals/postpaidSale/postpaidSale/getPostpaidSaleList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

}]);