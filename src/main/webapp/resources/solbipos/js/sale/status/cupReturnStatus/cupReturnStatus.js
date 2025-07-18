/****************************************************************
 *
 * 파일명 : cupReturnStatus.js
 * 설  명 : 모바일쿠폰 현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.17     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 회수방법
var returnTypeData = [
    {"name":"전체","value":""},
    {"name":"현금전달","value":"0"},
    {"name":"계좌전달","value":"1"}
];

// 전송성공여부
var sendYnData = [
    {"name":"전체","value":""},
    {"name":"성공","value":"Y"},
    {"name":"실패","value":"N"}
];

/**
 *  컵보증금회수현황 그리드 생성
 */
app.controller('cupReturnStatusCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('cupReturnStatusCtrl', $scope, $http, false));

    // 전체기간 체크박스
    $scope.isChecked = true;
    // 검색조건에 조회기간
    var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
    var endDate = wcombo.genDateVal("#srchEndDate", gvEndDate);

    // 검색조건에 전송일자
    $scope.sendStartDate = wcombo.genDateVal("#srchSendStartDate", gvStartDate);
    $scope.sendEndDate = wcombo.genDateVal("#srchSendEndDate", gvEndDate);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("srchReturnType", returnTypeData); // 회수방법
    $scope._setComboData("srchSendYn", sendYnData); // 전송성공여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.returnTypeDataMap = new wijmo.grid.DataMap(returnTypeData, 'value', 'name');
        $scope.sendYnDataMap = new wijmo.grid.DataMap(sendYnData, 'value', 'name');

        // 전체기간 체크박스 선택에 따른 날짜선택 초기화
        $scope.sendStartDate.isReadOnly = $scope.isChecked;
        $scope.sendEndDate.isReadOnly = $scope.isChecked;

    };

    // <-- 검색 호출 -->
    $scope.$on("cupReturnStatusCtrl", function(event, data) {
        $scope.searchCupReturnStatus();
        event.preventDefault();
    });

    $scope.searchCupReturnStatus = function(){
        // var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
        // var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
        // var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨
        //
        // // 시작일자가 종료일자보다 빠른지 확인
        // if(startDt.getTime() > endDt.getTime()){
        //     $scope._popMsg(messages['cmm.dateChk.error']);
        //     return false;
        // }

        // 파라미터
        var params = {};
        params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
        params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
        // 등록일자 '전체기간' 선택에 따른 params
        if(!$scope.isChecked){
            params.sendStartDate = wijmo.Globalize.format($scope.sendStartDate.value, 'yyyyMMdd');
            params.sendEndDate = wijmo.Globalize.format($scope.sendEndDate.value, 'yyyyMMdd');
        }
        params.barCd = $scope.barCd;
        params.returnType = $scope.returnType;
        params.sendYn = $scope.sendYn;
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
        params.listScale = 500;

        $scope._inquiryMain("/sale/status/cupReturnStatus/cupReturnStatus/getCupReturnStatusList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 전체기간 체크박스 클릭이벤트
    $scope.isChkDt = function() {
        $scope.sendStartDate.isReadOnly = $scope.isChecked;
        $scope.sendEndDate.isReadOnly = $scope.isChecked;
    };


}]);