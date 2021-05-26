/****************************************************************
 *
 * 파일명 : resveInfo.js
 * 설  명 : 거래처관리목록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.20     권지현      1.0
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
 *  예약현황 목록 생성
 */
app.controller('resveInfoCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('resveInfoCtrl', $scope, $http, false));

    // 콤보박스 데이터 Set
    $scope._setComboData('listScaleBox', gvListScaleBoxData);

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
    $scope.$on("resveInfoCtrl", function(event, data) {
        $scope.searchResveInfoList();
        event.preventDefault();
    });
    
    // 예약현황 조회
    $scope.searchResveInfoList = function(){
        var params = {};
        if(!$scope.isChecked){
            params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
            params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
        }
        if(!$scope.isResveChecked){
            params.resveStartDate = wijmo.Globalize.format($scope.resveStartDate.value, 'yyyyMMdd');
            params.resveEndDate = wijmo.Globalize.format($scope.resveEndDate.value, 'yyyyMMdd');
        };
        params.storeCd = $("#resveInfoStoreCd").val();

        $scope._inquiryMain("/adi/resve/resveInfo/view.sb", params, function() {}, false);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.resveInfoStoreShow = function () {
        $scope._broadcast('resveInfoStoreCtrl');
    };
}]);