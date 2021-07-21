/****************************************************************
 *
 * 파일명 : mobileResveInfo.js
 * 설  명 : (모바일) 부가서비스 > 예약현황
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.12     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 입력구분 VALUE
var inFg = [
    {"name":"POS","value":"1"},
    {"name":"WEB","value":"2"}
];
/**
 *  일자-시간대별 그리드 생성
 */
app.controller('mobileResveInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('mobileResveInfoCtrl', $scope, $http, false));

    // 등록 일자 전체기간 체크박스
    $scope.isChecked = true;
    // 예약 일자 전체기간 체크박스
    $scope.isResveChecked = true;

    // 일자 셋팅
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);
    $scope.resveStartDate = wcombo.genDateVal("#resveStartDate", gvStartDate);
    $scope.resveEndDate   = wcombo.genDateVal("#resveEndDate", gvEndDate);

    $scope.isChkDt = function () {      // 등록 일자 전체기간 체크박스 클릭이벤트
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked;
    };
    $scope.isResveChkDt = function () {      // 예약 일자 전체기간 체크박스 클릭이벤트
        $scope.resveStartDate.isReadOnly = $scope.isResveChecked;
        $scope.resveEndDate.isReadOnly = $scope.isResveChecked;
    };

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.srchStartDate.isReadOnly = $scope.isChecked;
        $scope.srchEndDate.isReadOnly = $scope.isChecked
        $scope.resveStartDate.isReadOnly = $scope.isResveChecked;
        $scope.resveEndDate.isReadOnly = $scope.isResveChecked;

        // 입력구분
        $scope.inFgDataMap = new wijmo.grid.DataMap(inFg, 'value', 'name');
    };

    // <-- 검색 호출 -->
    $scope.$on("mobileResveInfoCtrl", function(event, data) {
        $scope.searchMobileResveInfo(data);
        event.preventDefault();
    });



    $scope.searchMobileResveInfo = function(data){

        var params = {};
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

            // 시작일자가 종료일자보다 빠른지 확인
            if(params.startDate > params.endDate){
                $scope._popMsg(messages['mobile.cmm.dateChk.error']);
                return false;
            }
        }
        if(!$scope.isResveChecked){
            params.resveStartDate = wijmo.Globalize.format($scope.resveStartDate.value, 'yyyyMMdd');
            params.resveEndDate = wijmo.Globalize.format($scope.resveEndDate.value, 'yyyyMMdd');

            // 시작일자가 종료일자보다 빠른지 확인
            if(params.resveStartDate > params.resveEndDate){
                $scope._popMsg(messages['mobile.cmm.dateChk.error']);
                return false;
            }
        };

        params.srchStoreCd = $("#mobileResveInfoStoreCd").val();
        params.resveNo = $scope.resveNo;
        params.resveGuestNm = $scope.resveGuestNm;

        $scope._inquirySub("/mobile/adi/resve/resveInfo/mobileResveInfo/getResveInfo.sb", params, function() {
            // 조회 결과가 없으면 grid에'조회 결과 없음' Msg 띄우기
            gridShowMsgNoData("mobileResveInfo", $scope.flexMobileResveInfo, "N");

        }, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.mobileResveInfoStoreShow = function () {
        $scope._broadcast('mobileResveInfoStoreCtrl');
    };

}]);