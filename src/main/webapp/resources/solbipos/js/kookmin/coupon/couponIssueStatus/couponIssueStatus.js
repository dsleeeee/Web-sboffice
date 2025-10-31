/****************************************************************
 *
 * 파일명 : couponIssueStatus.js
 * 설  명 : 국민대 > 쿠폰관리 > 쿠폰상태관리(관리자) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.30     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 검색기간 콤보박스
var periodData = [
    {"name":"기간 미사용","value":"N"},
    {"name":"기간 사용","value":"Y"}
];

// 최종상태 콤보박스
var finalStatusData = [
    {"name":"전체","value":""},
    {"name":"사용가능","value":"Y"},
    {"name":"사용불가","value":"N"}
];

/** 쿠폰상태관리 그리드 controller */
app.controller('couponIssueStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('couponIssueStatusCtrl', $scope, $http, false));

    // 조회일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope._setComboData("periodType", periodData); // 검색기간 콤보박스

    $scope._setComboData("finalStatusFgCombo", finalStatusData); // 검색기간 콤보박스

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("couponIssueStatusCtrl", function (event, data) {
        $scope.getCouponIssueStatusList();
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 쿠폰상태정보 조회
    $scope.getCouponIssueStatusList = function (type) {

        // 파라미터
        var params  = {};
        if($scope.periodType !== 'N' && $scope.periodType !== null && $scope.periodType !== undefined) {
            var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
            var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
            var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

            // 시작일자가 종료일자보다 빠른지 확인
            if (startDt.getTime() > endDt.getTime()) {
                $scope._popMsg(messages['cmm.dateChk.error']);
                return false;
            }

            // 조회일자 최대 1년(365일) 제한
            if (diffDay > 365) {
                $scope._popMsg(messages['cmm.dateOver.1year.error']);
                return false;
            }

            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        params.coupnStatusFg = type;
        params.useStatusFg = $scope.finalStatusFg;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/kookmin/coupon/couponIssueStatus/couponIssueStatus/getCouponIssueStatusList.sb", params, function () {
        });
    };

    // 기간 설정
    $scope.setPeriodType = function(s) {

        if (s.selectedValue === "N") {
            $scope.srchStartDate.isReadOnly = true;
            $scope.srchEndDate.isReadOnly = true;
        } else {
            $scope.srchStartDate.isReadOnly = false;
            $scope.srchEndDate.isReadOnly = false;
        }
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
        }
    };

    // 쿠폰 발행,회수,만료 처리
    $scope.couponProcess = function (type) {

        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        if($("#issueRemark").val() === null || $("#issueRemark").val() === ''){
            $scope._popMsg(messages["couponIssueStatus.require.remark"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].coupnStatusFg = type;
                $scope.flex.collectionView.items[i].issueRemark = $("#issueRemark").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/kookmin/coupon/couponIssueStatus/couponIssueStatus/saveCouponIssueStatus.sb", params, function(){
            $scope.getCouponIssueStatusList();
        });
    }

}]);
