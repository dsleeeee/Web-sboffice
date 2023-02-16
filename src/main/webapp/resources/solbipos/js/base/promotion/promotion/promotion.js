/****************************************************************
 *
 * 파일명 : promotion.js
 * 설  명 : 프로모션관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.04.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 사용여부(전체)
var useYnAllFgData = [
    {"name":"전체","value":""},
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 사용여부
var useYnFgData = [
    {"name":"사용","value":"Y"},
    {"name":"미사용","value":"N"}
];

// 시 VALUE
var Hh = [24];
for(i =0 ; i < 24; i++){
    var timeVal = i.toString();
    if(i>=0 && i<=9){
        timeVal = "0" + timeVal;
    }
    Hh[i] = {"name":timeVal,"value":timeVal}
}

// 분 VALUE
var Mm = [60];
for(i =0 ; i < 60; i++) {
    var timeVal = i.toString();
    if (i >= 0 && i <= 9) {
        timeVal = "0" + timeVal;
    }
    Mm[i] = {"name": timeVal, "value": timeVal}
}

// 적용조건 - 적용대상
var memberTargetDsFgData = [
    {"name":"전체","value":"1"},
    {"name":"회원","value":"2"},
    {"name":"회원등급","value":"3"}
];

// 적용조건 - 적용등급
var memberClassCdFgData = [
    {"name":"골드","value":"001"},
    {"name":"플래티넘","value":"002"},
    {"name":"VIP","value":"003"}
];

// 적용조건 - 분담금구분
var chargeDsData = [
    {"name":"미사용","value":""},
    {"name":"정률","value":"1"},
    {"name":"정액","value":"2"}
];

// 적용상품 - 구매대상
var selectProdDsFgData = [
    {"name":"전체구매","value":"1"},
    {"name":"일부구매(종류+수량)","value":"2"},
    {"name":"일부구매(수량=교차선택)","value":"3"},
    {"name":"제외상품","value":"4"},
    {"name":"품목개별할인","value":"5"}
];

// 혜택유형
var typeCdFgData = [
    {"name":"영수증전체할인","value":"1"},
    {"name":"적용품목할인","value":"2"},
    {"name":"혜택품목할인","value":"3"},
    {"name":"증정","value":"4"},
    {"name":"특별가(=세트가)","value":"5"},
    {"name":"품목개별할인","value":"6"}
];

// 할인구분
var applyDcDsData = [
    {"name":"정률할인","value":"1"},
    {"name":"정액할인","value":"2"}
];

// 혜택상품 - 구분
var presentDsFgData = [
    {"name":"전체","value":"1"},
    {"name":"선택","value":"2"}
];

// 상품코드/분류 구분
var gubunDsFgData=[
    {"name":"상품","value":"1"},
    {"name":"분류","value":"2"}
];

// 매장등록구분
var storeSelectExceptFgData=[
    {"name":"선택매장","value":"0"},
    {"name":"제외매장","value":"1"}
];

// 프로모션 종류 변경시 필요한 임시변수 (권한, 프로모션기간, 환경설정값에 따른 변경 가능/금지 처리)
var vPromotionType = ""; // 프로모션 종류
var vRegFg = "";          // 프로모션등록구분
var vDateYn = "";         // 적용기간체크여부
var vStartYmd = "";       // 적용시작일
var vRegDt = "";          // 등록일시

/**
 * 프로모션관리 그리드 생성
 */
app.controller('promotionCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionCtrl', $scope, $http, $timeout, true));

    // 행사일 달력 셋팅
    $scope.promotionDate = wcombo.genDateVal("#promotionDate", gvStartDate);
    $scope.promotionDate.isReadOnly = true;

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "promotionNm") { // 프로모션명
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 클릭 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            if (ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];
                var selectedRow = s.rows[ht.row].dataItem;
                if (col.binding === "promotionNm") { // 프로모션명 클릭
                    var params    = {};
                    params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.promotionCd = selectedRow.promotionCd;
                    $scope._broadcast('promotionRegCtrl', params);
                }
            }
        });

    };

    //
    $scope.$on("promotionCtrl", function(event, data) {

        // 프로모션 목록 조회
        $scope.getPromotionList();

        // 셋팅된 정보 초기화 밑 입력폼 숨기기
        $scope.openPromotionReg();
        $("#promotionReg").css("display", "none");

        event.preventDefault();
    });

    // 프로모션 목록 조회
    $scope.getPromotionList = function () {

        // 파라미터
        var params = {};
        params.promotionNm = $scope.promotionNm;
        params.useYn = $scope.useYn;
        if($scope.isChecked) {
            params.promotionDate = wijmo.Globalize.format($scope.promotionDate.value, 'yyyyMMdd');
        }

        $scope._inquirySub("/base/promotion/promotion/list.sb", params, function () {});
    };

    // 행사일 검색조건 사용/미사용 체크박스
    $scope.isChkDt = function () {
        if($scope.isChecked){
            $scope.promotionDate.isReadOnly = false;
        }else{
            $scope.promotionDate.isReadOnly = true;
        }
    };

    // 신규 등록 버튼 클릭 시, 프로모션 등록 영역 visible
    $scope.openPromotionReg = function () {
        $scope._broadcast('promotionRegCtrl');
    };

}]);

/**
 * 프로모션관리 등록
 */
app.controller('promotionRegCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionRegCtrl', $scope, $http, $timeout, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFgData); // 기본정보 - 사용여부
    $scope._setComboData("promotionType", promotionTypeList); // 기본정보 - 프로모션 종류
    $scope._setComboData("couponYn", useYnFgData); // 적용조건 - 쿠폰사용여부
    $scope._setComboData("promotionStartHhCombo", Hh); // 적용조건 - 적용요일(시작 시)
    $scope._setComboData("promotionStartMmCombo", Mm); // 적용조건 - 적용요일(시작 분)
    $scope._setComboData("promotionEndHhCombo", Hh); // 적용조건 - 적용요일(종료 시)
    $scope._setComboData("promotionEndMmCombo", Mm); // 적용조건 - 적용요일(종료 분)
    $scope._setComboData("memberTargetDs", memberTargetDsFgData); // 적용조건 - 적용대상
    $scope._setComboData("memberClassCd", memberClassCdFgData); // 적용조건 - 적용등급
    $scope._setComboData("chargeDs", chargeDsData); // 적용조건 - 분담금구분
    //$scope._setComboData("selectProdDs", selectProdDsFgData); // 적용상품 - 구매대상
    $scope._setComboData("storeSelectExceptFg", storeSelectExceptFgData); // 적용매장 - 매장등록구분
    //$scope._setComboData("typeCd", typeCdFgData); // 혜택유형
    $scope._setComboData("applyDcDs", applyDcDsData); // 할인구분
    $scope._setComboData("presentDs", presentDsFgData); // 혜택상품 - 구분

    // 적용조건 - 적용구분 셋팅
    $scope.dlvFgInStore = true;
    $scope.dlvFgDelivery = false;
    $scope.dlvFgPacking = false;
    $scope.dlvFgAppInStore = false;
    $scope.dlvFgAppDelivery = false;
    $scope.dlvFgAppPacking = false;
    $scope.dlvFgYogiyo = false;
    $scope.dlvFgCoupangeats = false;
    $scope.dlvFgWmpo = false;
    $scope.dlvFgPayco = false;
    $scope.dlvFgSpecialdelivery = false;
    $scope.dlvFgKakao = false;
    $scope.dlvFgBaemin = false;
    $scope.dlvFgDdangyo = false;
    $scope.dlvFgNpaysmartorder = false;

    // 적용조건 - 적용기간 셋팅
    $scope.isCheckedPeriod = false;
    var promotionStartDate = wcombo.genDateVal("#promotionStartDate", gvStartDate);
    var promotionEndDate = wcombo.genDateVal("#promotionEndDate", gvEndDate);

    // 적용조건 - 적용시간 셋팅
    $scope.isCheckedTime = false;

    // 적용조건 - 적용요일 셋팅
    $scope.isCheckedDayOfWeek = false;

    // 적용조건 - 구매금액 셋팅
    $scope.isCheckedMinSaleAmt = false;

    // 적용조건 - 입금일 셋팅
    $scope.isCheckedDepositYmd = false;
    var depositYmd = wcombo.genDateVal("#depositYmd", gvStartDate);

    // 적용상품 셋팅
    $scope.isCheckedProd = false;

    // 매장권한에서는 적용매장 hidden
    if(orgnFg === "STORE") {
        $("#tblPromotionStore").css("display" , "none");
    }else{
        $("#tblPromotionStore").css("display" , "");
    }

    $scope.$on("promotionRegCtrl", function(event, data) {

        // 등록 영역 Open
        $("#promotionReg").css("display", "");

        if(!isEmptyObject(data)){

            // 기존 프로모션 상세정보 셋팅
            $scope.setPromotionDetail(data);

            // 적용상품 목록 조회
            $scope._pageView('promotionSelectProdGridCtrl', 1);
            // 혜택상품 목록 조회
            $scope._pageView('promotionSelectPresentGridCtrl', 1);

            // 매장권한인 경우, 적용매장 등록 불가
            if(orgnFg === "STORE") {
                $("#tblPromotionStore").css("display", "none");
            }else{
                // 적용매장 목록 조회
                $scope._pageView('promotionSelectStoreGridCtrl', 1);
                // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
                $("#tblPromotionStore").css("display", "");
            }

            // 상세등록영역 보이기
            $("#divDetailReg").css("display", "");

        }else{
            // 초기화
            $scope.reset();

            // 가장 첫번째 프로모션 종류로 셋팅
            $scope.setForm($scope.promotionTypeCombo.selectedValue);
            
            // 신규등록인 경우, 상세등록영역의 필수입력값 자동 셋팅 (Validation Check에 걸리지 않도록)
            $("#dcSet").val("1");          // 적용혜택 - 할인율

            // 상세등록영역 숨기기(저장 후 오픈됨)
            $("#divDetailReg").css("display", "none");
        }

    });

    // 기존 프로모션 상세정보 셋팅
    $scope.setPromotionDetail = function(data){

        // hidden에 promotionCd 값 가지고 있기(상품, 매장, 혜택상품 리스트 저장 시 사용)
        $("#hdPromotionCd").val(data.promotionCd);

        var params = {};
        params.promotionCd = data.promotionCd;
        params.beneSeq = "1";

        $.postJSON("/base/promotion/promotion/detail.sb", params, function(result) {

                var info = result.data;

                // 프로모션 종류 변경시 필요한 임시변수
                vPromotionType = info.promoType;
                vRegFg = info.regFg;
                vDateYn = info.dateYn;
                vStartYmd = info.startYmd;
                vRegDt = info.regDt;
                
                // ------------ 권한, 프로모션기간, 환경설정값 에 따른 hidden 처리 ------------

                // 오늘 날짜
                var date = new Date();
                var year = new String(date.getFullYear());
                var month = new String(date.getMonth()+1);
                var day = new String(date.getDate());

                // 한자리수일 경우 0을 채워준다.
                if(month.length == 1){
                    month = "0" + month;
                }
                if(day.length == 1){
                    day = "0" + day;
                }
                var now = year + "" + month + "" + day;

                if(orgnFg === "STORE") { // 매장권한 일 때
                    if(storePromoRegYnVal === '0'){ // 본사 환경변수(매장프로모션생성 - 1253)이 '미사용'인 경우 프로모션 생성, 수정 불가
                        $scope.setButtonVisible("N");
                    } else {
                        if (info.regFg === 'S') { // 등록자가 매장일 때,
                            if (modPromotionEnvstVal === "1") { // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'인 경우 버튼 보임
                                $scope.setButtonVisible("Y");
                            } else {
                                if (info.dateYn === "Y") { // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 일부 버튼 숨김
                                    if (Number(now) >= Number(info.startYmd)) {
                                        $scope.setButtonVisible("N");
                                    } else {
                                        $scope.setButtonVisible("Y");
                                    }
                                } else { // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 일부 버튼 숨김
                                    if (Number(now) > Number(info.regDt)) {
                                        $scope.setButtonVisible("N");
                                    } else {
                                        $scope.setButtonVisible("Y");
                                    }
                                }
                            }
                        } else { // 등록자가 본사일 때
                            $scope.setButtonVisible("N");
                        }
                    }
                }else{ // 본사권한 일 때
                    if(modPromotionEnvstVal === "1"){ // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'인 경우 버튼 보임
                        $scope.setButtonVisible("Y");
                    }else{
                        if(info.dateYn === "Y"){ // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 일부 버튼 숨김
                            if(Number(now) >= Number(info.startYmd)){
                                $scope.setButtonVisible("N");
                            }else{
                                $scope.setButtonVisible("Y");
                            }
                        }else{ // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 일부 버튼 숨김
                            if(Number(now) > Number(info.regDt)) {
                                $scope.setButtonVisible("N");
                            }else{
                                $scope.setButtonVisible("Y");
                            }
                        }
                    }
                }

                // ------------ 기본정보 ------------
                $("#promotionNm").val(info.promotionNm); // 프로모션명
                $("#memo").val(info.memo); // 메모
                $scope.useYnCombo.selectedValue = info.useYn; // 사용여부

                // 프로모션 종류별 입력값 셋팅
                $scope.setForm(info.promoType);
                $scope.promotionTypeCombo.selectedValue = info.promoType; // 프로모션 종류

                // ------------ 적용조건 ------------
                $scope.getImg();// 프로모션 키오스크 배너 이미지 셋팅
                if(info.dlv1Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgInStore']").prop("checked", true); // 적용구분(내점)
                }else{
                    $("input:checkbox[id='chkDlvFgInStore']").prop("checked", false); // 적용구분(내점)
                }
                if(info.dlv2Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgDelivery']").prop("checked", true); // 적용구분(배달)
                }else{
                    $("input:checkbox[id='chkDlvFgDelivery']").prop("checked", false); // 적용구분(배달)
                }
                if(info.dlv3Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgPacking']").prop("checked", true); // 적용구분(포장)
                }else{
                    $("input:checkbox[id='chkDlvFgPacking']").prop("checked", false); // 적용구분(포장)
                }
                if(info.dlv4Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgAppInStore']").prop("checked", true); // 적용구분(자사앱(내점))
                }else{
                    $("input:checkbox[id='chkDlvFgAppInStore']").prop("checked", false); // 적용구분(자사앱(내점))
                }
                if(info.dlv5Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgAppDelivery']").prop("checked", true); // 적용구분(자사앱(배달))
                }else{
                    $("input:checkbox[id='chkDlvFgAppDelivery']").prop("checked", false); // 적용구분(자사앱(배달))
                }
                if(info.dlv6Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgAppPacking']").prop("checked", true); // 적용구분(자사앱(포장))
                }else{
                    $("input:checkbox[id='chkDlvFgAppPacking']").prop("checked", false); // 적용구분(자사앱(포장)
                }
                if(info.dlv7Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgYogiyo']").prop("checked", true); // 적용구분(요기요)
                }else{
                    $("input:checkbox[id='chkDlvFgYogiyo']").prop("checked", false); // 적용구분(요기요)
                }
                if(info.dlv8Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgCoupangeats']").prop("checked", true); // 적용구분(쿠팡이츠)
                }else{
                    $("input:checkbox[id='chkDlvFgCoupangeats']").prop("checked", false); // 적용구분(쿠팡이츠)
                }
                if(info.dlv9Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgWmpo']").prop("checked", true); // 적용구분(위메프오)
                }else{
                    $("input:checkbox[id='chkDlvFgWmpo']").prop("checked", false); // 적용구분(위메프오)
                }
                if(info.dlv10Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgPayco']").prop("checked", true); // 적용구분(페이코)
                }else{
                    $("input:checkbox[id='chkDlvFgPayco']").prop("checked", false); // 적용구분(페이코)
                }
                if(info.dlv11Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgSpecialdelivery']").prop("checked", true); // 적용구분(배달특급)
                }else{
                    $("input:checkbox[id='chkDlvFgSpecialdelivery']").prop("checked", false); // 적용구분(배달특급)
                }
                if(info.dlv12Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgKakao']").prop("checked", true); // 적용구분(카카오)
                }else{
                    $("input:checkbox[id='chkDlvFgKakao']").prop("checked", false); // 적용구분(카카오)
                }
                if(info.dlv13Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgBaemin']").prop("checked", true); // 적용구분(배달의민족)
                }else{
                    $("input:checkbox[id='chkDlvFgBaemin']").prop("checked", false); // 적용구분(배달의민족)
                }
                if(info.dlv14Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgDdangyo']").prop("checked", true); // 적용구분(땡겨요)
                }else{
                    $("input:checkbox[id='chkDlvFgDdangyo']").prop("checked", false); // 적용구분(땡겨요)
                }
                if(info.dlv15Yn === "Y"){
                    $("input:checkbox[id='chkDlvFgNpaysmartorder']").prop("checked", true); // 적용구분(네이버주문)
                }else{
                    $("input:checkbox[id='chkDlvFgNpaysmartorder']").prop("checked", false); // 적용구분(네이버주문)
                }

                $scope.couponYnCombo.selectedValue = info.couponYn; // 쿠폰사용여부
                
                if(info.dateYn === "Y"){
                    $("input:checkbox[id='chkPeriod']").prop("checked", true); // 적용기간
                    promotionStartDate.value = new Date(getFormatDate(info.startYmd, "-"));
                    promotionEndDate.value = new Date(getFormatDate(info.endYmd, "-"));
                    $scope.isCheckedPeriod = true;
                    $("#divChkPeriod").css("display", "");
                }else{
                    $("input:checkbox[id='chkPeriod']").prop("checked", false);
                    promotionStartDate.value = getCurDate('-');
                    promotionEndDate.value = getCurDate('-');
                    $scope.isCheckedPeriod = false;
                    $("#divChkPeriod").css("display", "none");
                }

                if(info.timeYn === "Y"){
                    $("input:checkbox[id='chkTime']").prop("checked", true); // 적용시간
                    $scope.promotionStartHhCombo.selectedValue = info.startTime.substr(0, 2);
                    $scope.promotionStartMmCombo.selectedValue = info.startTime.substr(2, 2);
                    $scope.promotionEndHhCombo.selectedValue = info.endTime.substr(0, 2);
                    $scope.promotionEndMmCombo.selectedValue = info.endTime.substr(2, 2);
                    $scope.isCheckedTime = true;
                    $("#divChkTime").css("display", "");
                }else{
                    $("input:checkbox[id='chkTime']").prop("checked", false);
                    $scope.promotionStartHhCombo.selectedValue = "00";
                    $scope.promotionStartMmCombo.selectedValue = "00";
                    $scope.promotionEndHhCombo.selectedValue = "00";
                    $scope.promotionEndMmCombo.selectedValue = "00";
                    $scope.isCheckedTime = false;
                    $("#divChkTime").css("display", "none");
                }

                if(info.weekYn === "Y"){
                    $("input:checkbox[id='chkDayOfWeek']").prop("checked", true); // 적용요일
                    $("input:checkbox[id='chkDayOfWeekMon']").prop("checked", info.monYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekTue']").prop("checked", info.tueYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekWed']").prop("checked", info.wedYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekThu']").prop("checked", info.thuYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekFri']").prop("checked", info.friYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekSat']").prop("checked", info.satYn === 'Y' ? true : false);
                    $("input:checkbox[id='chkDayOfWeekSun']").prop("checked", info.sunYn === 'Y' ? true : false);
                    $scope.isCheckedDayOfWeek = true;
                    $("#divChkDayOfWeek").css("display", "");
                }else{
                    $("input:checkbox[id='chkDayOfWeek']").prop("checked", false); // 적용요일
                    $("input:checkbox[id='chkDayOfWeekMon']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekTue']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekWed']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekThu']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekFri']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekSat']").prop("checked", false);
                    $("input:checkbox[id='chkDayOfWeekSun']").prop("checked", false);
                    $scope.isCheckedDayOfWeek = false;
                    $("#divChkDayOfWeek").css("display", "none");
                }

                $scope.memberTargetDsCombo.selectedValue = info.memberTargetDs; // 적용대상

                if(info.memberTargetDs === "3"){
                    $scope.memberClassCdCombo.selectedValue = info.memberClassCd; // 적용등급
                }else{
                    $scope.memberClassCdCombo.selectedIndex = 0;
                }

                var mCnt = 0; // 최소 또는 최대 구매금액이 있는지 파악
                if(info.minSaleAmt !== "" && info.minSaleAmt !== "0" && info.minSaleAmt !== 0){ // 최소구매금액
                    mCnt++;
                }
                $("#minSaleAmt").val(info.minSaleAmt);

                if(info.maxSaleAmt !== "" && info.maxSaleAmt !== "0" && info.maxSaleAmt !== 0){ // 최대구매금액
                    mCnt++;
                }
                $("#maxSaleAmt").val(info.maxSaleAmt);

                if(mCnt > 0){ // 최소 또는 최대 구매금액이 있으면 오픈
                    $("input:checkbox[id='chkMinSaleAmt']").prop("checked", true);
                    $scope.isCheckedMinSaleAmt = true;
                    $("#divChkMinSaleAmt").css("display", "");
                }else{
                    $("input:checkbox[id='chkMinSaleAmt']").prop("checked", false);
                    $scope.isCheckedMinSaleAmt = false;
                    $("#divChkMinSaleAmt").css("display", "none");
                }

                if(info.momsPurchsCntLimit !== "" && info.momsPurchsCntLimit !== "0" && info.momsPurchsCntLimit !== 0){ // 구매횟수제한
                    $("#momsPurchsCntLimit").val(info.momsPurchsCntLimit);
                }else{
                    $("#momsPurchsCntLimit").val("");
                }

                if(info.momsPurchsQtyLimit !== "" && info.momsPurchsQtyLimit !== "0" && info.momsPurchsQtyLimit !== 0){ // 구매갯수제한
                    $("#momsPurchsQtyLimit").val(info.momsPurchsQtyLimit);
                }else{
                    $("#momsPurchsQtyLimit").val("");
                }

                if(info.chargeDs !== null && info.chargeDs !== undefined && info.chargeDs !== "") { // 분담금구분
                    $scope.chargeDsCombo.selectedValue = info.chargeDs;

                    if(info.hqChargeUprc !== ""){ // 본사분담금
                        $("#hqChargeUprc").val(info.hqChargeUprc);
                    }else{
                        $("#hqChargeUprc").val("");
                    }

                    if(info.msChargeUprc !== ""){ // 매장분담금
                        $("#msChargeUprc").val(info.msChargeUprc);
                    }else{
                        $("#msChargeUprc").val("");
                    }

                    if(info.partnerChargeUprc !== ""){ // 제휴분담금
                        $("#partnerChargeUprc").val(info.partnerChargeUprc);
                    }else{
                        $("#partnerChargeUprc").val("");
                    }

                }else{
                    $scope.chargeDsCombo.selectedIndex = 0;
                    $("#hqChargeUprc").val("");
                    $("#msChargeUprc").val("");
                    $("#partnerChargeUprc").val("");
                }

                if(info.depositYmd !== "" && info.depositYmd !== null && info.depositYmd !== undefined){ // 입금일
                    $("input:checkbox[id='chkDepositYmd']").prop("checked", true);
                    depositYmd.value = new Date(getFormatDate(info.depositYmd, "-"));
                    $scope.isCheckedDepositYmd = true;
                    $("#divChkDepositYmd").css("display", "");
                }else{
                    $("input:checkbox[id='chkDepositYmd']").prop("checked", false);
                    depositYmd.value = getCurDate('-');
                    $scope.isCheckedDepositYmd = false;
                    $("#divChkDepositYmd").css("display", "none");
                }

                $("input:checkbox[id='chkPayFgMomsGiftcard']").prop("checked", false);

                if(info.blockPayCd !== "" && info.blockPayCd !== null && info.blockPayCd !== undefined){ // 제한된 결제
                    var arrBlockPayCd = info.blockPayCd.split(',');
                    for(var i = 0; i < arrBlockPayCd.length; i++) {
                        if(arrBlockPayCd[i] === "13"){$("input:checkbox[id='chkPayFgMomsGiftcard']").prop("checked", true);}
                    }
                }

                // ------------ 적용상품 ------------

                // 프로모션종류 '영수증 전체할인'은 적용상품 사용 안함
                if(vPromotionType === "001" && info.prodCdYn !== "N"){
                    info.prodCdYn = "N";
                }

                if(info.prodCdYn === "Y"){
                    $("input:checkbox[id='chkProd']").prop("checked", true); // 적용상품
                    $scope.selectProdDsCombo.selectedValue = info.selectProdDs; // 구매대상

                    if(info.selectProdDs !== "1" && info.selectProdDs !== "5") { // 구매대상이 전체구매, 품목개별할인 일때는 수량 사용 안함.
                        $("#selectProdCnt").val(info.selectProdCnt); // 수량
                        $("#thSelectProdCnt").css("display", "");
                        $("#tdSelectProdCnt").css("display", "");
                    }else{
                        $("#selectProdCnt").val(""); // 수량
                        $("#thSelectProdCnt").css("display", "none");
                        $("#tdSelectProdCnt").css("display", "none");
                    }

                    $scope.isCheckedProd = true;
                    $("#trProdTop").css("border-bottom", "1px solid #EEEEEE");
                    $("#trSelectProdDs").css("display", "");
                    $("#trSelectProdGrid").css("display", "");
                }else{
                    $("input:checkbox[id='chkProd']").prop("checked", false); // 적용상품
                    $scope.selectProdDsCombo.selectedIndex = 0; // 구매대상
                    $("#selectProdCnt").val(""); // 수량
                    $("#thSelectProdCnt").css("display", "none");
                    $("#tdSelectProdCnt").css("display", "none");
                    $scope.isCheckedProd = false;
                    $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
                    $("#trSelectProdDs").css("display", "none");
                    $("#trSelectProdGrid").css("display", "none");
                }

                // ------------ 적용매장 ------------
                if(orgnFg === "HQ"){
                    if(info.storeSelectExceptFg !== "" && info.storeSelectExceptFg !== null) {
                        $scope.storeSelectExceptFgCombo.selectedValue = info.storeSelectExceptFg; // 매장등록구분
                    }else{
                        $scope.storeSelectExceptFgCombo.selectedIndex = 0;
                    }
                }

                // ------------ 적용혜택 ------------
                $scope.typeCdCombo.selectedValue = info.typeCd; // 혜택유형

                if(info.typeCd === "1" || info.typeCd === "2" || info.typeCd === "3") {
                    $scope.applyDcDsCombo.selectedValue = info.applyDcDs; // 할인구분
                    $("#dcSet").val(info.dcSet); // 할인율 or 할인금액
                }else if(info.typeCd === "5"){ // 특별가(=세트가) 별도 체크
                    $scope.applyDcDsCombo.selectedIndex = 1; // 정액할인(고정)
                    $("#dcSet").val(info.dcSet); // 할인금액
                }else{
                    $scope.applyDcDsCombo.selectedIndex = 0;
                    $("#dcSet").val("");
                }

                $("#printMessage").val(info.printMessage); // 출력문구

                // ------------ 혜택상품 ------------
                if(info.typeCd === "3" || info.typeCd === "4"){
                    $scope.presentDsCombo.selectedValue = info.presentDs; // 구분

                    if(info.presentDs === "2"){
                        $("#selectGiftCnt").val(info.selectGiftCnt); // 상품수
                    }else{
                        $("#selectGiftCnt").val("");
                    }
                }else{
                    $scope.presentDsCombo.selectedIndex = 0; // 구분
                    $("#selectGiftCnt").val(""); // 수량
                }

            },
            function (result) {
                s_alert.pop(result.message);
                return false;
            }
        );
    };

    // 프로모션 등록/수정
    $scope.savePromotion = function(){

        if($scope.chkValid()) {

            var params = {};

            params.beneSeq = '1';
            params.promotionCd = $("#hdPromotionCd").val();

            // ------------ 기본정보 ------------
            params.promotionNm = $("#promotionNm").val(); // 프로모션명
            params.memo = $("#memo").val(); // 메모
            params.useYn = $scope.useYnCombo.selectedValue; // 사용여부
            params.promoType = $scope.promotionTypeCombo.selectedValue; // 프로모션 종류 

            // ------------ 적용조건 ------------
            params.dlv1Yn = $("#chkDlvFgInStore").is(":checked") === true ? 'Y' : 'N'; // 적용구분(내점)
            params.dlv2Yn = $("#chkDlvFgDelivery").is(":checked") === true ? 'Y' : 'N'; // 적용구분(배달)
            params.dlv3Yn = $("#chkDlvFgPacking").is(":checked") === true ? 'Y' : 'N'; // 적용구분(포장)
            params.dlv4Yn = $("#chkDlvFgAppInStore").is(":checked") === true ? 'Y' : 'N'; // 적용구분(자사앱(내점))
            params.dlv5Yn = $("#chkDlvFgAppDelivery").is(":checked") === true ? 'Y' : 'N'; // 적용구분(자사앱(배달))
            params.dlv6Yn = $("#chkDlvFgAppPacking").is(":checked") === true ? 'Y' : 'N'; // 적용구분(자사앱(포장))
            params.dlv7Yn = $("#chkDlvFgYogiyo").is(":checked") === true ? 'Y' : 'N'; // 적용구분(요기요)
            params.dlv8Yn = $("#chkDlvFgCoupangeats").is(":checked") === true ? 'Y' : 'N'; // 적용구분(쿠팡이츠)
            params.dlv9Yn = $("#chkDlvFgWmpo").is(":checked") === true ? 'Y' : 'N'; // 적용구분(위메프오)
            params.dlv10Yn = $("#chkDlvFgPayco").is(":checked") === true ? 'Y' : 'N'; // 적용구분(페이코)
            params.dlv11Yn = $("#chkDlvFgSpecialdelivery").is(":checked") === true ? 'Y' : 'N'; // 적용구분(배달특급)
            params.dlv12Yn = $("#chkDlvFgKakao").is(":checked") === true ? 'Y' : 'N'; // 적용구분(카카오)
            params.dlv13Yn = $("#chkDlvFgBaemin").is(":checked") === true ? 'Y' : 'N'; // 적용구분(배달의민족)
            params.dlv14Yn = $("#chkDlvFgDdangyo").is(":checked") === true ? 'Y' : 'N'; // 적용구분(땡겨요)
            params.dlv15Yn = $("#chkDlvFgNpaysmartorder").is(":checked") === true ? 'Y' : 'N'; // 적용구분(네이버주문)
            params.couponYn = $scope.couponYnCombo.selectedValue; // 쿠폰사용여부
            params.dateYn = $("#chkPeriod").is(":checked") === true ? 'Y' : 'N'; // 적용기간 사용여부
            params.startYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(promotionStartDate.value, 'yyyyMMdd') : '00010101'; // 적용기간 시작일
            params.endYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(promotionEndDate.value, 'yyyyMMdd') : '99991231'; // 적용기간 종료일
            params.timeYn = $("#chkTime").is(":checked") === true ? 'Y' : 'N'; // 적용시간 사용여부
            params.startTime = $("#chkTime").is(":checked") === true ? ($scope.promotionStartHh + $scope.promotionStartMm) : ''; //적용시간 시작시간
            params.endTime = $("#chkTime").is(":checked") === true ? ($scope.promotionEndHh + $scope.promotionEndMm) : ''; // 적용시간 종료시간
            params.weekYn = $("#chkDayOfWeek").is(":checked") === true ? 'Y' : 'N'; // 적용요일 사용여부
            if($("#chkDayOfWeek").is(":checked")) {
                params.monYn = 'N';  // 적용요일
                params.tueYn = 'N';
                params.wedYn = 'N';
                params.thuYn = 'N';
                params.friYn = 'N';
                params.satYn = 'N';
                params.sunYn = 'N';

                if ($("#chkDayOfWeekMon").is(":checked")) {
                    params.monYn = 'Y';
                }

                if ($("#chkDayOfWeekTue").is(":checked")) {
                    params.tueYn = 'Y';
                }

                if ($("#chkDayOfWeekWed").is(":checked")) {
                    params.wedYn = 'Y';
                }

                if ($("#chkDayOfWeekThu").is(":checked")) {
                    params.thuYn = 'Y';
                }

                if ($("#chkDayOfWeekFri").is(":checked")) {
                    params.friYn = 'Y';
                }

                if ($("#chkDayOfWeekSat").is(":checked")) {
                    params.satYn = 'Y';
                }

                if ($("#chkDayOfWeekSun").is(":checked")) {
                    params.sunYn = 'Y';
                }

            }else{
                params.monYn = "N";
                params.tueYn = "N";
                params.wedYn = "N";
                params.thuYn = "N";
                params.friYn = "N";
                params.satYn = "N";
                params.sunYn = "N";
            }

            params.memberTargetDs = $scope.memberTargetDsCombo.selectedValue; //적용조건 적용대상
            if ($scope.memberTargetDsCombo.selectedValue === "3") {
                params.memberClassCd = $scope.memberClassCdCombo.selectedValue; //적용조건 적용등급
            }else{
                params.memberClassCd = "";
            }

            if ($("#chkMinSaleAmt").is(":checked")) { // 구매금액
                params.minSaleAmt = $("#minSaleAmt").val(); // 최소구매금액
                params.maxSaleAmt = $("#maxSaleAmt").val(); // 최대구매금액
            }else{
                params.minSaleAmt = "";
                params.maxSaleAmt = "";
            }

            params.momsPurchsCntLimit = $("#momsPurchsCntLimit").val(); // 구매횟수제한
            params.momsPurchsQtyLimit = $("#momsPurchsQtyLimit").val(); // 구매갯수제한

            params.chargeDs = $scope.chargeDsCombo.selectedValue; // 본사분담금
            if($scope.chargeDsCombo.selectedValue === "1" || $scope.chargeDsCombo.selectedValue === "2") {
                params.hqChargeUprc = $("#hqChargeUprc").val(); // 본사분담금
                params.msChargeUprc = $("#msChargeUprc").val(); // 매장분담금
                params.partnerChargeUprc = $("#partnerChargeUprc").val(); // 제휴분담금
            }else{
                params.hqChargeUprc = "";
                params.msChargeUprc = "";
                params.partnerChargeUprc = "";
            }

            params.depositYmd = $("#chkDepositYmd").is(":checked") === true ? wijmo.Globalize.format(depositYmd.value, 'yyyyMMdd') : ''; // 입금일

            var vChkBlockPayCd = "";
            if($("#chkPayFgMomsGiftcard").is(":checked")){ vChkBlockPayCd += "13,"; }
            params.blockPayCd = vChkBlockPayCd.substr(0, vChkBlockPayCd.length - 1); // 제한된 결제


           // ------------ 적용상품 ------------
            params.prodCdYn = $("#chkProd").is(":checked") === true ? 'Y' : 'N'; // 적용상품
            if ($("#chkProd").is(":checked")) {
                params.selectProdDs = $scope.selectProdDsCombo.selectedValue; // 적용상품 - 구매대상
                if($scope.selectProdDsCombo.selectedValue !== "1" && $scope.selectProdDsCombo.selectedValue !== "5") {           // 구매대상이 전체구매, 품목개별할인 일때는 수량 사용 안함.
                    params.selectProdCnt = $("#selectProdCnt").val();        // 적용상품 - 수량
                }else{
                    params.selectProdCnt = "";
                }
            }else{
                params.selectProdDs = "";
                params.selectProdCnt = "";
            }

            // ------------ 적용매장 ------------
            if(orgnFg === "HQ") {
                params.storeSelectExceptFg = $scope.storeSelectExceptFgCombo.selectedValue; // 매장등록구분
            }

            // ------------ 적용혜택 ------------
            params.typeCd = $scope.typeCdCombo.selectedValue; // 혜택유형
            if($scope.typeCdCombo.selectedValue === "1" || $scope.typeCdCombo.selectedValue === "2" || $scope.typeCdCombo.selectedValue === "3"){
                params.applyDcDs = $scope.applyDcDsCombo.selectedValue; // 할인구분
                params.dcSet = $("#dcSet").val(); // 할인율 or 할인금액
            } else if($scope.typeCdCombo.selectedValue === "5"){ // 특별가(=세트가) 별도 체크
                params.applyDcDs = "2"; // 정액할인(고정)
                params.dcSet = $("#dcSet").val(); // 할인금액
            }else{
                params.applyDcDs = "";
                params.dcSet = "";
            }
            params.printMessage = $("#printMessage").val(); // 출력문구


            // ------------ 혜택상품 ------------
            if($scope.typeCdCombo.selectedValue === "3" || $scope.typeCdCombo.selectedValue === "4"){
                params.presentDs = $scope.presentDsCombo.selectedValue; // 구분
                if ($scope.presentDsCombo.selectedValue === "2") {
                    params.selectGiftCnt = $("#selectGiftCnt").val(); // 상품수
                }else{
                    params.selectGiftCnt = "";
                }
            }else{
                params.presentDs = "";
                params.selectGiftCnt = "";
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $.postJSONArray("/base/promotion/promotion/save.sb", params, function (result) {
                if (result.status === "OK") {

                    // 새 프로모션 배너를 등록하는 경우
                    if (!isNull($("#fileKioskBanner")[0].files[0])) {

                        var formData = new FormData($("#regForm")[0]);

                        formData.append("orgnFg", orgnFg); // sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 추가, form태그가 원인??
                        formData.append("orgnCd", orgnCd);
                        formData.append("hqOfficeCd", hqOfficeCd);
                        formData.append("storeCd", storeCd);
                        formData.append("fileType", "003");
                        formData.append("useYn", $scope.useYnCombo.selectedValue);
                        formData.append("startDate", params.startYmd);
                        formData.append("endDate", params.endYmd);
                        formData.append("timeYn", params.timeYn);
                        formData.append("startTime", params.startTime);
                        formData.append("endTime", params.endTime);
                        formData.append("weekYn", params.weekYn);
                        formData.append("monYn", params.monYn);
                        formData.append("tueYn", params.tueYn);
                        formData.append("wedYn", params.wedYn);
                        formData.append("thuYn", params.thuYn);
                        formData.append("friYn", params.friYn);
                        formData.append("satYn", params.satYn);
                        formData.append("sunYn", params.sunYn);
                        formData.append("promotionCd", $("#hdPromotionCd").val());
                        formData.append("regId", userId);
                        formData.append("modId", userId);
                        formData.append("verSerNo", $("#hdFileNo").val()); // 기존 파일이 있는지 확인하여, 등록 or 수정

                        $.ajax({
                            url: "/base/promotion/promotion/savePromotionBanner.sb",
                            type: "POST",
                            data: formData,
                            processData: false,
                            contentType: false,
                            cache: false,
                            success: function(result2) {
                                if (result2.status === "OK") {
                                    console.log("이미지 저장 OK");
                                }
                                else if (result2.status === "FAIL") {
                                    var msg = result2.status + " : " + result2.data.msg;
                                    $scope._popMsg(msg);
                                    $scope.$broadcast('loadingPopupInactive');
                                }
                                else if (result2.status === "SERVER_ERROR") {
                                    $scope._popMsg(result2.message);
                                    $scope.$broadcast('loadingPopupInactive');
                                }
                                /*else if(result2.status === undefined) {
                                    location.href = "/";
                                }*/
                                else {
                                    var msg = result2.status + " : " + result2.message;
                                    $scope._popMsg(msg);
                                    $scope.$broadcast('loadingPopupInactive');
                                }
                            },
                            error : function(result2){
                                $scope._popMsg("error");
                                $scope.$broadcast('loadingPopupInactive');
                            }
                        },function(){
                            $scope._popMsg("Ajax Fail By HTTP Request 2");
                            $scope.$broadcast('loadingPopupInactive');
                        });
                        
                    }else{
                        if ($("#hdFileNo").val() !== "") { // 기존 이미지 파일이 있는 경우

                            // 파일 정보 테이블에 프로모션 정보만 UPDATE

                            var params3 = {};
                            params3.fileType = "003";
                            params3.useYn = $scope.useYnCombo.selectedValue;
                            params3.startDate = params.startYmd;
                            params3.endDate = params.endYmd;
                            params3.timeYn = params.timeYn;
                            params3.startTime = params.startTime;
                            params3.endTime = params.endTime;
                            params3.weekYn = params.weekYn;
                            params3.monYn = params.monYn;
                            params3.tueYn = params.tueYn;
                            params3.wedYn = params.wedYn;
                            params3.thuYn = params.thuYn;
                            params3.friYn = params.friYn;
                            params3.satYn = params.satYn;
                            params3.sunYn = params.sunYn;
                            params3.promotionCd = $("#hdPromotionCd").val();
                            params3.verSerNo = $("#hdFileNo").val();

                            $scope._postJSONSave.withPopUp("/base/promotion/promotion/modPromotionBanner.sb", params3, function () {});
                        }
                    }

                    setTimeout(function() {

                        $scope.$broadcast('loadingPopupInactive');

                        // 저장되었습니다.
                        $scope._popMsg(messages["cmm.saveSucc"]);

                        // 입력 폼 초기화
                        $scope.reset();

                        // 리스트 재조회
                        $scope._broadcast('promotionCtrl');

                        // 상세 재조회
                        var params2    = {};
                        params2.hqOfficeCd = hqOfficeCd;
                        params2.promotionCd = result.data;
                        $scope._broadcast('promotionRegCtrl', params2);

                    }, 500);

                } else {
                    $scope.$broadcast('loadingPopupInactive');
                    $scope._popMsg(result.status);
                    return false;
                }
            }, function (err) {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(err.message);
            });
        }

    };

    // 프로모션 등록/수정 Validation Check
    $scope.chkValid = function(){

        // 프로모션명을(를) 입력하세요.
        if($("#promotionNm").val() === "" || $("#promotionNm").val() === null) {
            $scope._popMsg(messages["promotion.promotionNm"] +  messages["cmm.require.text"]);
            return false;
        }

        // 키오스크 배너 이미지가 있는 경우, 체크
        if (!isNull($("#fileKioskBanner")[0].files[0])) {

            // 상품이미지 크기제한 체크
            var maxSize = 20 * 1024 * 1024;
            var fileSize = document.getElementById("fileKioskBanner").files[0].size;
            if (fileSize > maxSize) {
                $scope._popMsg(messages["promotion.fileSize.max"]); // 파일사이즈는 최대 20MB까지 가능합니다.
                return;
            }

            // 이미지명 형식 체크
            var imgFullNm = $("#fileKioskBanner").val().substring($("#fileKioskBanner").val().lastIndexOf('\\') + 1);
            if(1 > imgFullNm.lastIndexOf('.')){
                $scope._popMsg(messages["promotion.fileNmChk.msg"]); // 파일명 또는 확장자가 올바르지 않습니다. 다시 확인해주세요.
                return;
            }

            // 이미지 확장자 체크
            var reg = /(.*?)\.(png|PNG|avi|AVI|wmv|WMV)$/;

            if(!$("#fileKioskBanner").val().match(reg)) {
               $scope._popMsg(messages["promotion.fileExtensionChk.msg"]); // 확장자가 .png .PNG 또는 .avi .AVI 또는 .wmv .WMV 인 파일만 등록가능합니다.
               return;
            }
        }

        // 적용구분은 한 개 이상 선택하세요.
        var chkDlvFg = 0;

        if($("#chkDlvFgInStore").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgDelivery").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgPacking").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgAppInStore").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgAppDelivery").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgAppPacking").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgYogiyo").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgCoupangeats").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgWmpo").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgPayco").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgSpecialdelivery").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgKakao").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgBaemin").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgDdangyo").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgNpaysmartorder").is(":checked")) chkDlvFg++;

        if(chkDlvFg === 0){
            $scope._popMsg(messages["promotion.chk.DlvFg"]);
            return false;
        }

        // 적용기간 종료일자가 시작일자보다 빠릅니다.
        if($("#chkPeriod").is(":checked")) {
            if(wijmo.Globalize.format(promotionStartDate.value, 'yyyyMMdd') > wijmo.Globalize.format(promotionEndDate.value, 'yyyyMMdd')){
                $scope._popMsg(messages["promotion.periodChk"]);
                return false;
            }
        }

        // 적용요일을(를) 선택하세요.
        if($("#chkDayOfWeek").is(":checked")){

            var chkCnt = 0;

            if($("#chkDayOfWeekMon").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekTue").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekWed").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekThu").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekFri").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekSat").is(":checked")) chkCnt++;
            if($("#chkDayOfWeekSun").is(":checked")) chkCnt++;

            if(chkCnt === 0){
                $scope._popMsg(messages["promotion.promotionDayofWeek"] +  messages["cmm.require.select"]);
                return false;
            }
        }

        // 구매금액을(를) 입력하세요.
        if($("#chkMinSaleAmt").is(":checked")){
            if ($("#minSaleAmt").val() === "" || $("#minSaleAmt").val() === null || $("#minSaleAmt").val() === "0") {
                if ($("#maxSaleAmt").val() === "" || $("#maxSaleAmt").val() === null || $("#maxSaleAmt").val() === "0") {
                    $scope._popMsg(messages["promotion.minSaleAmt"] + messages["cmm.require.text"]);
                    return false;
                }
            }
        }

        // 본사/매장/제휴 분담율/분담금을(를) 입력하세요.
        if($scope.chargeDsCombo.selectedValue === "1" || $scope.chargeDsCombo.selectedValue === "2"){
            if (($("#hqChargeUprc").val() === "" || $("#hqChargeUprc").val() === null || $("#hqChargeUprc").val() === "0") &&
                ($("#msChargeUprc").val() === "" || $("#msChargeUprc").val() === null || $("#msChargeUprc").val() === "0") &&
                ($("#partnerChargeUprc").val() === "" || $("#partnerChargeUprc").val() === null || $("#partnerChargeUprc").val() === "0")) {
                    if ($scope.chargeDsCombo.selectedValue === "1") {
                        $scope._popMsg(messages["promotion.hq"] + "/" + messages["promotion.ms"] + "/" + messages["promotion.partner"] + " " + messages["promotion.chargeRate"] + messages["cmm.require.text"]);
                    } else {
                        $scope._popMsg(messages["promotion.hq"] + "/" + messages["promotion.ms"] + "/" + messages["promotion.partner"] + " " + messages["promotion.chargeUprc"] + messages["cmm.require.text"]);
                    }
                    return false;
            }
            // 본사/매장/제휴 분담율은(는) 0~100 사이의 숫자만 입력하세요.
            if($scope.chargeDsCombo.selectedValue === "1") {
                if($("#hqChargeUprc").val() !== "" && $("#hqChargeUprc").val() !== null) {
                    if (0 > $("#hqChargeUprc").val() || $("#hqChargeUprc").val() > 100) {
                        $scope._popMsg(messages["promotion.hq"] + " " + messages["promotion.chk.chargeRate"]);
                        return false;
                    }
                }
                if($("#msChargeUprc").val() !== "" && $("#msChargeUprc").val() !== null) {
                    if (0 > $("#msChargeUprc").val() || $("#msChargeUprc").val() > 100) {
                        $scope._popMsg(messages["promotion.ms"] + " " + messages["promotion.chk.chargeRate"]);
                        return false;
                    }
                }
                if($("#partnerChargeUprc").val() !== "" && $("#partnerChargeUprc").val() !== null) {
                    if (0 > $("#partnerChargeUprc").val() || $("#partnerChargeUprc").val() > 100) {
                        $scope._popMsg(messages["promotion.partner"] + " " + messages["promotion.chk.chargeRate"]);
                        return false;
                    }
                }
            }
            // 본사/매장/제휴 분담금은(는) 1원단위를 입력할 수 없습니다.
            if($scope.chargeDsCombo.selectedValue === "2") {
                if($("#hqChargeUprc").val() !== "" && $("#hqChargeUprc").val() !== null) {
                    if (Number($("#hqChargeUprc").val()) % 10 > 0) {
                        $scope._popMsg(messages["promotion.hq"] + " " + messages["promotion.chk.chargeUprc"]);
                        return false;
                    }
                }
                if($("#msChargeUprc").val() !== "" && $("#msChargeUprc").val() !== null) {
                    if (Number($("#msChargeUprc").val()) % 10 > 0) {
                        $scope._popMsg(messages["promotion.ms"] + " " + messages["promotion.chk.chargeUprc"]);
                        return false;
                    }
                }
                if($("#partnerChargeUprc").val() !== "" && $("#partnerChargeUprc").val() !== null) {
                    if (Number($("#partnerChargeUprc").val()) % 10 > 0) {
                        $scope._popMsg(messages["promotion.partner"] + " " + messages["promotion.chk.chargeUprc"]);
                        return false;
                    }
                }
            }
        }

        // 적용상품의 상품수/수량을(를) 입력하세요.
        if($("#chkProd").is(":checked")) {
            if ($scope.selectProdDsCombo.selectedValue !== "1" && $scope.selectProdDsCombo.selectedValue !== "5") { // 구매대상이 전체구매, 풀목개별할인 일때는 수량 사용 안함.
                if ($("#selectProdCnt").val() === "" || $("#selectProdCnt").val() === null) {
                    if ($scope.selectProdDsCombo.selectedValue === "2") {
                        $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossNCnt"] + messages["cmm.require.text"]);
                    } else {
                        $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossYCnt"] + messages["cmm.require.text"]);
                    }
                    return false;
                }
            }
        }

        if($scope.typeCdCombo.selectedValue === "1" || $scope.typeCdCombo.selectedValue === "2" || $scope.typeCdCombo.selectedValue === "3"){
            // 적용혜택의 할인율/할인금액을(를) 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === null){
                if($scope.applyDcDsCombo.selectedValue === "1") {
                    $scope._popMsg(messages["promotion.bene"] + "의 " + messages["promotion.dcSet"] +  messages["cmm.require.text"]);
                }else{
                    $scope._popMsg(messages["promotion.bene"] + "의 " + messages["promotion.dcSetAmt"] +  messages["cmm.require.text"]);
                }
                return false;
            }
            // 정률할인의 할인율은 0~100 사이의 숫자만 입력하세요.
            if($scope.applyDcDsCombo.selectedValue === "1") {
                if (0 > $("#dcSet").val() || $("#dcSet").val() > 100) {
                    $scope._popMsg(messages["promotion.chk.dcSet"]);
                    return false;
                }
            }
            // 할인금액은(는) 1원단위를 입력할 수 없습니다.
            if($scope.applyDcDsCombo.selectedValue === "2") {
                if(Number($("#dcSet").val()) % 10 > 0){
                    $scope._popMsg(messages["promotion.dcSetAmt"] + messages["promotion.chk.dcSetAmt"]);
                    return false;
                }
            }
            /*// 프로모션가격의 판매금액은 1원단위를 입력할 수 없습니다.
            if($scope.applyDcDsCombo.selectedValue === "3") {
                if(Number($("#dcSet").val()) % 10 > 0){
                    $scope._popMsg(messages["promotion.chk.saleAmt"]);
                    return false;
                }
            }*/
        }else if($scope.typeCdCombo.selectedValue === "5"){ // 특별가(=세트가) 별도 체크
            // 적용혜택의 특별가(을)를 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === null) {
                $scope._popMsg(messages["promotion.bene"] + "의 " + messages["promotion.specialPrice"] +  messages["cmm.require.text"]);
                return false;
            }
            // 특별가은(는) 1원단위를 입력할 수 없습니다.
            if(Number($("#dcSet").val()) % 10 > 0){
                $scope._popMsg(messages["promotion.specialPrice"] + messages["promotion.chk.dcSetAmt"]);
                return false;
            }
        }

        // 혜택상품의 상품수을(를) 입력하세요.
        if($scope.typeCdCombo.selectedValue === "3" || $scope.typeCdCombo.selectedValue === "4"){
            if ($scope.presentDsCombo.selectedValue === "2") {
                if ($("#selectGiftCnt").val() === "" || $("#selectGiftCnt").val() === null) {
                    $scope._popMsg(messages["promotion.present"] + "의 " + messages["promotion.selectGiftNCnt"] + messages["cmm.require.text"]);
                    return false;
                }
            }
        }

        return true;
    };

    // 초기화
    $scope.reset = function(){

        $("#hdPromotionCd").val("");

        // ------------ 권한, 프로모션기간, 환경설정값 에 따른 hidden 처리 ------------
        $("#btnSave").css("display", "");

        // ------------ 기본정보 ------------
        $("#promotionNm").val(""); // 프로모션명
        $("#memo").val(""); // 메모
        $scope.useYnCombo.selectedIndex = 0; // 사용여부
        $scope.promotionTypeCombo.selectedIndex = 0; // 프로모션 종류

        // ------------ 적용조건 ------------
        $("#lblFileNm").text("");
        $("#hdFileNo").val("");
        $("#lblFileSize").text("");
        $scope.resetFile("fileKioskBanner"); // 프로모션 키오스크 배너 이미지
        $("input:checkbox[id='chkDlvFgInStore']").prop("checked", true); // 적용구분(내점)
        $("input:checkbox[id='chkDlvFgDelivery']").prop("checked", false); // 적용구분(배달)
        $("input:checkbox[id='chkDlvFgPacking']").prop("checked", false); // 적용구분(포장)
        $("input:checkbox[id='chkDlvFgAppInStore']").prop("checked", false); // 적용구분(자사앱(내점))
        $("input:checkbox[id='chkDlvFgAppDelivery']").prop("checked", false); // 적용구분(자사앱(배달))
        $("input:checkbox[id='chkDlvFgAppPacking']").prop("checked", false); // 적용구분(자사앱(포장))
        $("input:checkbox[id='chkDlvFgYogiyo']").prop("checked", false); // 적용구분(요기요)
        $("input:checkbox[id='chkDlvFgCoupangeats']").prop("checked", false); // 적용구분(쿠팡이츠)
        $("input:checkbox[id='chkDlvFgWmpo']").prop("checked", false); // 적용구분(위메프오)
        $("input:checkbox[id='chkDlvFgPayco']").prop("checked", false); // 적용구분(페이코)
        $("input:checkbox[id='chkDlvFgSpecialdelivery']").prop("checked", false); // 적용구분(배달특급)
        $("input:checkbox[id='chkDlvFgKakao']").prop("checked", false); // 적용구분(카카오)
        $("input:checkbox[id='chkDlvFgBaemin']").prop("checked", false); // 적용구분(배민)
        $("input:checkbox[id='chkDlvFgDdangyo']").prop("checked", false); // 적용구분(땡겨요)
        $("input:checkbox[id='chkDlvFgNpaysmartorder']").prop("checked", false); // 적용구분(네이버주문)

        $scope.couponYnCombo.selectedIndex = 1; // 쿠폰사용여부

        $("input:checkbox[id='chkPeriod']").prop("checked", false); // 적용기간
        promotionStartDate.value = getCurDate('-');
        promotionEndDate.value = getCurDate('-');
        $scope.isCheckedPeriod = false;
        $("#divChkPeriod").css("display", "none");

        $("input:checkbox[id='chkTime']").prop("checked", false); // 적용시간
        $scope.promotionStartHhCombo.selectedValue = "00";
        $scope.promotionStartMmCombo.selectedValue = "00";
        $scope.promotionEndHhCombo.selectedValue = "00";
        $scope.promotionEndMmCombo.selectedValue = "00";
        $scope.isCheckedTime = false;
        $("#divChkTime").css("display", "none");

        $("input:checkbox[id='chkDayOfWeek']").prop("checked", false); // 적용요일
        $("input:checkbox[id='chkDayOfWeekMon']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekTue']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekWed']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekThu']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekFri']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekSat']").prop("checked", false);
        $("input:checkbox[id='chkDayOfWeekSun']").prop("checked", false);
        $scope.isCheckedDayOfWeek = false;
        $("#divChkDayOfWeek").css("display", "none");

        $scope.memberTargetDsCombo.selectedIndex = 0; // 적용대상
        $scope.memberClassCdCombo.selectedIndex = 0; // 적용등급
        
        $("input:checkbox[id='chkMinSaleAmt']").prop("checked", false); //구매금액
        $("#minSaleAmt").val("");
        $("#maxSaleAmt").val("");
        $scope.isCheckedMinSaleAmt = false;
        $("#divChkMinSaleAmt").css("display", "none");

        $("#momsPurchsCntLimit").val(""); // 구매횟수제한
        $("#momsPurchsQtyLimit").val(""); // 구매갯수제한

        $scope.chargeDsCombo.selectedIndex = 0; // 분담금구분

        $("input:checkbox[id='chkDepositYmd']").prop("checked", false); // 입금일
        depositYmd.value = getCurDate('-');
        $scope.isCheckedDepositYmd = false;
        $("#divChkDepositYmd").css("display", "none");

        $("input:checkbox[id='chkPayFgMomsGiftcard']").prop("checked", false); // 제한된 결제

        // ------------ 적용상품 ------------
        $("input:checkbox[id='chkProd']").prop("checked", false); // 적용상품
        $scope.selectProdDsCombo.selectedIndex = 0; // 구매대상
        $("#selectProdCnt").val(""); // 수량
        $("#thSelectProdCnt").css("display", "none");
        $("#tdSelectProdCnt").css("display", "none");
        $scope.isCheckedProd = false;
        $("#tblProd").css("display", "none");
        $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
        $("#trSelectProdDs").css("display", "none");
        $("#trSelectProdGrid").css("display", "none");

        // ------------ 적용매장 ------------
        $scope.storeSelectExceptFgCombo.selectedIndex = 0; // 매장등록구분
        
        // ------------ 적용혜택 ------------
        $scope.typeCdCombo.selectedIndex = 0; // 혜택유형
        $scope.applyDcDsCombo.selectedIndex = 0; // 할인구분
        $("#dcSet").val(""); // 할인율
        $("#printMessage").val(""); // 출력문구

        // ------------ 혜택상품 ------------
        $scope.presentDsCombo.selectedIndex = 0; // 구분
        $("#selectGiftCnt").val(""); // 상품수

        // ------------ 프로모션 종류 변경시 필요한 임시변수 ------------
        vPromotionType = "";
        vRegFg = "";
        vDateYn = "";
        vStartYmd = "";
        vRegDt = "";

        // ------------ 그리드 초기화 ------------
        var wjGridSelectProd = wijmo.Control.getControl("#wjGridSelectProd"); // 적용상품
        while(wjGridSelectProd.rows.length > 0){
            wjGridSelectProd.rows.removeAt(wjGridSelectProd.rows.length-1);
        }

        var wjGridSelectStore = wijmo.Control.getControl("#wjGridSelectStore"); // 적용매장
        while(wjGridSelectStore.rows.length > 0){
            wjGridSelectStore.rows.removeAt(wjGridSelectStore.rows.length-1);
        }

        var wjGridSelectPresent = wijmo.Control.getControl("#wjGridSelectPresent"); // 혜택상품
        while(wjGridSelectPresent.rows.length > 0){
            wjGridSelectPresent.rows.removeAt(wjGridSelectPresent.rows.length-1);
        }
    };

    // 권한, 프로모션기간, 환경설정값에 따른 버튼 Visible 설정
    $scope.setButtonVisible = function(type){

        if(type === "N"){ // 일부 버튼 hidden 처리

            // 저장 버튼
            $("#btnSave").css("display", "none");

            // 키오스크배너 선택취소, 삭제버튼
            $("#btnImgCancel").css("display", "none");
            $("#btnDelImg").css("display", "none");

            // 적용상품 grid 버튼
            $("#btnProdAdd").css("display", "none");
            $("#btnClassAdd").css("display", "none");
            $("#btnProdSave").css("display", "none");
            $("#btnProdDel").css("display", "none");
            $("#divSelectProdBatch").css("display", "none");

            // 적용매장 grid 버튼
            $("#btnStoreSampleDown").css("display", "none");
            $("#btnStoreExcelUpload").css("display", "none");
            $("#btnStoreExcelDown").css("display", "none");
            $("#btnStoreAdd").css("display", "none");
            $("#btnStoreDel").css("display", "none");

            // 혜택상품 grid 버튼
            $("#btnPresentAdd").css("display", "none");
            $("#btnPresentSave").css("display", "none");
            $("#btnPresentDel").css("display", "none");
            
        }else{ // 모든 버튼 보이게 처리

            // 저장 버튼
            $("#btnSave").css("display", "");

            // 키오스크배너 선택취소, 삭제버튼
            $("#btnImgCancel").css("display", "");
            $("#btnDelImg").css("display", "");

            // 적용상품 grid 버튼
            $("#btnProdAdd").css("display", "");
            $("#btnClassAdd").css("display", "");
            $("#btnProdSave").css("display", "");
            $("#btnProdDel").css("display", "");
            if(vPromotionType === "501"){
                $("#divSelectProdBatch").css("display", "");
            }else{
                $("#divSelectProdBatch").css("display", "none");
            }

            // 적용매장 grid 버튼
            $("#btnStoreSampleDown").css("display", "");
            $("#btnStoreExcelUpload").css("display", "");
            $("#btnStoreExcelDown").css("display", "");
            $("#btnStoreAdd").css("display", "");
            $("#btnStoreDel").css("display", "");

            // 혜택상품 grid 버튼
            $("#btnPresentAdd").css("display", "");
            $("#btnPresentSave").css("display", "");
            $("#btnPresentDel").css("display", "");
        }
    };

    // ===============================================================================================================================

    // 프로모션종류 선택에 따른 상세 입력 폼 셋팅
    $scope.setPromotionRegDetail = function(s){

        // 오늘 날짜
        var date = new Date();
        var year = new String(date.getFullYear());
        var month = new String(date.getMonth()+1);
        var day = new String(date.getDate());

        // 한자리수일 경우 0을 채워준다.
        if(month.length == 1){
            month = "0" + month;
        }
        if(day.length == 1){
            day = "0" + day;
        }
        var now = year + "" + month + "" + day;

        // 신규등록은 프로모션 종류 선택시 바로 입력값 셋팅
        // 상세는 confirm 후 OK일 때 입력값 셋팅 후 저장
        if($("#hdPromotionCd").val() !== null && $("#hdPromotionCd").val() !== ""){ // 프로모션 상세조회일때

            // 상세조회 화면에서 프로모션 종류 변경 시
            if(vPromotionType !== s.selectedValue){

                // 프로모션 종류 변경시 권한, 프로모션기간, 환경설정값에 따른 변경 가능/금지 처리
                if(orgnFg === "STORE") { // 매장권한 일 때
                    if(storePromoRegYnVal !== "0") {
                        if (vRegFg === 'S') { // 등록자가 매장일 때,
                            if (modPromotionEnvstVal !== "1") { // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'이 아닌 경우
                                if (vDateYn === "Y") { // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 변경 불가능
                                    if (Number(now) >= Number(vStartYmd)) {

                                        // 다시 이전 값으로 재셋팅
                                        s.selectedValue = vPromotionType;
                                        return false;
                                    }
                                } else { // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 변경 불가능
                                    if (Number(now) > Number(vRegDt)) {

                                        // 다시 이전 값으로 재셋팅
                                        s.selectedValue = vPromotionType;
                                        return false;
                                    }
                                }
                            }
                        } else { // 등록자가 본사일 때

                            // 다시 이전 값으로 재셋팅
                            s.selectedValue = vPromotionType;
                            return false;
                        }
                    } else { // 본사 환경변수(매장프로모션생성 - 1253)이 '미사용'인 경우 프로모션 생성, 수정 불가

                        // 다시 이전 값으로 재셋팅
                        s.selectedValue = vPromotionType;
                        return false;
                    }
                }else{ // 본사권한 일 때
                    if(modPromotionEnvstVal !== "1"){ // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'이 아닌 경우
                        if(vDateYn === "Y"){ // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 변경 불가능
                            if(Number(now) >= Number(vStartYmd)){

                                // 다시 이전 값으로 재셋팅
                                s.selectedValue = vPromotionType;
                                return false;
                            }
                        }else{ // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 변경 불가능
                            if(Number(now) > Number(vRegDt)) {

                                // 다시 이전 값으로 재셋팅
                                s.selectedValue = vPromotionType;
                                return false;
                            }
                        }
                    }
                }

                // 프로모션 종류가 바뀌면 등록된 프로모션 정보가 변경됩니다. 진행하시겠습니까?
                promotionTypeChgConfirmPop(messages["promotion.chk.chgPromotionType"], function () {

                    // 프로모션 종류별 입력값 셋팅
                    $scope.setForm(s.selectedValue);

                    // 프로모션 종류 변경에 따른 필수값 저장
                    var params = {};
                    params.promotionCd = $("#hdPromotionCd").val();
                    params.promoType = s.selectedValue;    // 프로모션 종류

                    // 프로모션 종류별 기본 값 셋팅하여 저장
                    if(s.selectedValue === "001"){
                        params.prodCdYn = "N";        // 적용상품 - 사용여부
                        params.selectProdDs = "";    // 적용상품 - 구매대상
                        params.selectProdCnt = "";   // 수량
                        params.typeCd = "1";          // 혜택유형
                        params.applyDcDs = "1";      // 할인구분
                        params.dcSet = "1";           // 할인금액
                        params.presentDs = "";        // 혜택상품 - 구분
                        params.selectGiftCnt = "";   // 상품수
                    }else if (s.selectedValue === "101"){
                        params.prodCdYn = "Y";
                        params.selectProdDs = "1";
                        params.selectProdCnt = "";
                        params.typeCd = "2";
                        params.applyDcDs = "1";
                        params.dcSet = "1";
                        params.presentDs = "";
                        params.selectGiftCnt = "";
                    }else if (s.selectedValue === "201"){
                        params.prodCdYn = "N";
                        params.selectProdDs = "";
                        params.selectProdCnt = "";
                        params.typeCd = "3";
                        params.applyDcDs = "1";
                        params.dcSet = "1";
                        params.presentDs = "1";
                        params.selectGiftCnt = "";
                    }else if (s.selectedValue === "301"){
                        params.prodCdYn = "Y";
                        params.selectProdDs = "1";
                        params.selectProdCnt = "";
                        params.typeCd = "4";
                        params.applyDcDs = "";
                        params.dcSet = "";
                        params.presentDs = "1";
                        params.selectGiftCnt = "";
                    }else if (s.selectedValue === "401"){
                        params.prodCdYn = "Y";
                        params.selectProdDs = "1";
                        params.selectProdCnt = "";
                        params.typeCd = "5";
                        params.applyDcDs = "2";
                        params.dcSet = "10";
                        params.presentDs = "";
                        params.selectGiftCnt = "";
                    }else if (s.selectedValue === "501"){
                        params.prodCdYn = "Y";
                        params.selectProdDs = "5";
                        params.selectProdCnt = "";
                        params.typeCd = "6";
                        params.applyDcDs = "";
                        params.dcSet = "";
                        params.presentDs = "";
                        params.selectGiftCnt = "";
                    }

                    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                    $.postJSONArray("/base/promotion/promotion/savePromotionDefaultSet.sb", params, function (result) {
                        if (result.status === "OK") {
                            $scope.$broadcast('loadingPopupInactive');

                            // 저장되었습니다.
                            $scope._popMsg(messages["cmm.saveSucc"]);

                            // 입력 폼 초기화
                            $scope.reset();

                            // 리스트 재조회
                            $scope._broadcast('promotionCtrl');

                            // 상세 재조회
                            var params    = {};
                            params.hqOfficeCd = hqOfficeCd;
                            params.promotionCd = result.data;
                            $scope._broadcast('promotionRegCtrl', params);

                        } else {
                            $scope.$broadcast('loadingPopupInactive');
                            $scope._popMsg(result.status);
                            return false;
                        }
                    }, function (err) {
                        $scope.$broadcast('loadingPopupInactive');
                        $scope._popMsg(err.message);
                    });
                    
                },function(){ // 취소 클릭시 실행
                    // 다시 이전 값으로 재셋팅
                    s.selectedValue = vPromotionType;
                });
            }
        }else{
            // 프로모션 종류별 입력값 셋팅
            $scope.setForm(s.selectedValue);

            if(s.selectedValue === "001"){
                $("input:checkbox[id='chkProd']").prop("checked", false);
                //$scope.selectProdDsCombo.selectedValue = "";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "1";
                $scope.applyDcDsCombo.selectedValue= "1";
                $("#dcSet").val("1");
                //$scope.presentDsCombo.selectedValue = "";
                $("#selectGiftCnt").val("");
            }else if(s.selectedValue === "101"){
                $("input:checkbox[id='chkProd']").prop("checked", true);
                $scope.selectProdDsCombo.selectedValue = "1";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "2";
                $scope.applyDcDsCombo.selectedValue= "1";
                $("#dcSet").val("1");
                //$scope.presentDsCombo.selectedValue = "";
                $("#selectGiftCnt").val("");
            }else if(s.selectedValue === "201"){
                $("input:checkbox[id='chkProd']").prop("checked", false);
                //$scope.selectProdDsCombo.selectedValue = "";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "3";
                $scope.applyDcDsCombo.selectedValue= "1";
                $("#dcSet").val("1");
                $scope.presentDsCombo.selectedValue = "1";
                $("#selectGiftCnt").val("");
            }else if(s.selectedValue === "301"){
                $("input:checkbox[id='chkProd']").prop("checked", true);
                $scope.selectProdDsCombo.selectedValue = "1";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "4";
                //$scope.applyDcDsCombo.selectedValue= "1";
                $("#dcSet").val("");
                $scope.presentDsCombo.selectedValue = "1";
                $("#selectGiftCnt").val("");
            }else if(s.selectedValue === "401"){
                $("input:checkbox[id='chkProd']").prop("checked", true);
                $scope.selectProdDsCombo.selectedValue = "1";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "5";
                $scope.applyDcDsCombo.selectedValue= "2";
                $("#dcSet").val("10");
                //$scope.presentDsCombo.selectedValue = "";
                $("#selectGiftCnt").val("");
            }else if(s.selectedValue === "501"){
                $("input:checkbox[id='chkProd']").prop("checked", true);
                $scope.selectProdDsCombo.selectedValue = "5";
                $("#selectProdCnt").val("");
                $scope.typeCdCombo.selectedValue = "6";
                //$scope.applyDcDsCombo.selectedValue= "1";
                $("#dcSet").val("");
                //$scope.presentDsCombo.selectedValue = "";
                $("#selectGiftCnt").val("");
            }
        }
    };

    // 프로모션 종류별 입력값 셋팅
    // 적용상품 - 입력항목전체, 구매대상 comboBox / 적용혜택 - 혜택유형 comboBox, 할인구분, 할인율 셋팅
    $scope.setForm= function(promoType){
        if (promoType === "001") { // 영수증전체할인
            $("#tblProd").css("display", "none"); // 적용상품 사용안함
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(0, 1)); // 적용혜택 - 혜택유형
            $scope.applyDcDsCombo.itemsSource = new wijmo.collections.CollectionView(applyDcDsData.slice(0, 1)); // 적용혜택 - 할인구분
            $("#trApplyDcDs").css("display", "");
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "101") { // 적용품목할인
            $("#tblProd").css("display", "");
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 3)); // 적용상품 - 구매대상
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(1, 2)); // 적용혜택 - 혜택유형
            $scope.applyDcDsCombo.itemsSource = new wijmo.collections.CollectionView(applyDcDsData); // 적용혜택 - 할인구분
            $("#trApplyDcDs").css("display", "");
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "201") { // 1+1 할인
            $("#tblProd").css("display", "");
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3))); // 적용상품 - 구매대상
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(2, 3)); // 적용혜택 - 혜택유형
            $scope.applyDcDsCombo.itemsSource = new wijmo.collections.CollectionView(applyDcDsData.slice(0, 1)); // 적용혜택 - 할인구분
            $scope.presentDsCombo.itemsSource = new wijmo.collections.CollectionView(presentDsFgData.slice(0, 1)); // 혜택상품 - 구분
            $("#trApplyDcDs").css("display", "");
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "301") { // 1+1 증정
            $("#tblProd").css("display", "");
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3))); // 적용상품 - 구매대상
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(3, 4)); // 적용혜택 - 혜택유형
            $scope.presentDsCombo.itemsSource = new wijmo.collections.CollectionView(presentDsFgData); // 혜택상품 - 구분
            $("#trApplyDcDs").css("display", "none");
        } else if (promoType === "401") { // 특별가
            $("#tblProd").css("display", "");
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(0, 1)); // 적용상품 - 구매대상
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(4, 5)); // 적용혜택 - 혜택유형

            // 할인구분 제목 -> 특별가로 변경
            // 할인구분 selectBox와 할인금액 제목을 숨김
            $("#trApplyDcDs").css("display", "");
            $("#lblApplyDcDs").text(messages["promotion.specialPrice"]);
            $("#tdApplyDcDs").css("display", "none");
            $("#thDcSet").css("display", "none");
        } else if (promoType === "501") { // 품목개별할인
            $("#tblProd").css("display", "");
            $scope.selectProdDsCombo.itemsSource = new wijmo.collections.CollectionView(selectProdDsFgData.slice(4, 5)); // 적용상품 - 구매대상
            $scope.typeCdCombo.itemsSource = new wijmo.collections.CollectionView(typeCdFgData.slice(5, 6)); // 적용혜택 - 혜택유형
            $("#trApplyDcDs").css("display", "none");
            //$("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            //$("#tdApplyDcDs").css("display", "");
            //$("#thDcSet").css("display", "");
        }
    };

    // 적용조건 - 적용대상 선택에 따른 적용등급 disabled 여부
    $scope.setMemberClassCd = function (s) {
       if(s.selectedValue === "3"){
           $("#divMemberClassCd").css("display", "");
           $("#trTitleMemberClassCdY").css("display", "");
           $("#trTitleMemberClassCdN").css("display", "none");
       }else{
           $("#divMemberClassCd").css("display", "none");
           $("#trTitleMemberClassCdY").css("display", "none");
           $("#trTitleMemberClassCdN").css("display", "");
       }
    };

    // 적용조건 - 분담금구분 선택에 따른 본사/매장/제휴 chargeUprc 명칭(할인율, 할인금액) 변경
    $scope.setChargeUprcSet = function(s) {

        if(s.selectedValue === "" || momsEnvstVal === "0"){

            $("#thHqChargeUprcY").css("display", "none");
            $("#tdHqChargeUprcN").css("display", "");
            $("#trChargeDs").css("display", "none");

            $("#hqChargeUprc").css("display", "none");
            $("#msChargeUprc").css("display", "none");
            $("#partnerChargeUprc").css("display", "none");

        }else{

            if(s.selectedValue === "1"){
                $("#lblHqChargeUprc").text(messages["promotion.hqChargeRate"]);
                $("#lblMsChargeUprc").text(messages["promotion.msChargeRate"]);
                $("#lblPartnerChargeUprc").text(messages["promotion.partnerChargeRate"]);
            }else if(s.selectedValue === "2"){
                $("#lblHqChargeUprc").text(messages["promotion.hqChargeUprc"]);
                $("#lblMsChargeUprc").text(messages["promotion.msChargeUprc"]);
                $("#lblPartnerChargeUprc").text(messages["promotion.partnerChargeUprc"]);
            }

            $("#thHqChargeUprcY").css("display", "");
            $("#tdHqChargeUprcN").css("display", "none");
            $("#trChargeDs").css("display", "");

            $("#hqChargeUprc").css("display", "");
            $("#msChargeUprc").css("display", "");
            $("#partnerChargeUprc").css("display", "");

        }
    };

    // 적용상품 - 구매대상 선택에 따른 selectProdCnt 명칭(상품수, 수량) 변경
    $scope.setSelectProdCnt = function (s){

        // 구매대상 선택값에 따른 적용상품 그리드 조건수량입력 제어를 위해
        // 구매대상이 전체구매, 일부구매(종류+수량)인 경우 조건수량 입력
        // 구매대상이 일부구매(수량=교차선택), 제외상품인 경우 조건수량 미입력(숨김처리)
        // 전채구매, 일부구매(수량=교차선택)만 사용 중
        var grid = wijmo.Control.getControl("#wjGridSelectProd");
        var columns = grid.columns;

        if(s.selectedValue === "1"){ // 전체구매
            //$("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // 전체구매는 수량 입력 안해서 숨김
            $("#thSelectProdCnt").css("display", "none");
            $("#tdSelectProdCnt").css("display", "none");
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = true;
        }else if(s.selectedValue === "2"){ // 일부구매(종류+수량)
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossNCnt"]); // title : 상품수
            $("#thSelectProdCnt").css("display", "");
            $("#tdSelectProdCnt").css("display", "");
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = true;
        }else if (s.selectedValue ==="3"){ // 일부구매(수량=교차선택)
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // title : 수량
            $("#thSelectProdCnt").css("display", "");
            $("#tdSelectProdCnt").css("display", "");
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = false;
        }else if (s.selectedValue ==="4"){ // 제외상품
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // title : 수량
            $("#thSelectProdCnt").css("display", "");
            $("#tdSelectProdCnt").css("display", "");
            $("#btnProdAdd").text(messages["promotion.exceptProdAdd"]);
            $("#btnClassAdd").text(messages["promotion.exceptClassAdd"]);
            columns[5].visible = false;
        }else{ // 품목개별할인
            //$("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]); // 품목개별할인은 수량 입력 안해서 숨김
            $("#thSelectProdCnt").css("display", "none");
            $("#tdSelectProdCnt").css("display", "none");
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = false;
        }
    };

    // 적용매장 - 매장등록구분 선택에 따른 버튼명 변경
    $scope.setStoreRegBtn = function(s){
        if(s.selectedValue === "0"){
            $("#btnStoreAdd").text(messages["promotion.storeAdd"]);
        }else{
            $("#btnStoreAdd").text(messages["promotion.exceptStoreAdd"]);
        }  
    };
    
    // 적용혜택 - 혜택유형 선택에 따른 할인구분, 할인율, 혜택상품 입력영역 disabled 여부
    $scope.setApplyDcDs = function (s) {
        if(s.selectedValue === "1"){
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "none");
        }else if (s.selectedValue === "2"){
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "none");
        }else if (s.selectedValue === "3"){
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "");
        }else if(s.selectedValue === "4") {
            $("#trApplyDcDs").css("display", "none");
            $("#tblBene").css("display", "");
        }else if(s.selectedValue === "5") {
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "none");
        }else if(s.selectedValue === "6") {
            $("#trApplyDcDs").css("display", "none");
            $("#tblBene").css("display", "none");
        }
    };

    // 적용혜택 - 할인구분 선택에 따른 dcSet 명칭(할인율, 할인금액) 변경
    $scope.setDcSet = function (s) {
        if(s.selectedValue === "1"){
            $("#lblDcSet").text(messages["promotion.dcSet"]);
        }else if(s.selectedValue === "2"){
            $("#lblDcSet").text(messages["promotion.dcSetAmt"]);
        }
    };

    // 혜택상품 - 구분 선택에 따른 상품수 disabled 여부
    $scope.setSelectGiftCnt = function (s){

        // 혜택상품이 전체인 경우, 상품수 미입력 & 조건수량 입력
        // 혜택상품이 선택인 경우, 상품수 입력 & 조건수량 미입력
        var grid = wijmo.Control.getControl("#wjGridSelectPresent");
        var columns = grid.columns;

        if(s.selectedValue === "1"){
            $("#thSelectGiftCnt").css("display", "none");
            $("#selectGiftCnt").css("display", "none");
            columns[3].visible = true;
        }else{
            $("#thSelectGiftCnt").css("display", "");
            $("#selectGiftCnt").css("display", "");
            columns[3].visible = false;
        }
    };

    // ===============================================================================================================================

    // 적용조건 - 적용기간 입력 사용/미사용 체크박스
    $scope.isChkPeriod = function () {
        if($scope.isCheckedPeriod){
            $("#divChkPeriod").css("display", "");
        }else{
            $("#divChkPeriod").css("display", "none");
        }
    };

    // 적용조건 - 적용시간 입력 사용/미사용 체크박스
    $scope.isChkTime = function () {
        if($scope.isCheckedTime){
            $("#divChkTime").css("display", "");
        }else{
            $("#divChkTime").css("display", "none");
        }
    };

    // 적용조건 - 적용요일 입력 사용/미사용 체크박스
    $scope.isChkDayOfWeek = function () {
        if($scope.isCheckedDayOfWeek){
            $("#divChkDayOfWeek").css("display", "");
        }else{
            $("#divChkDayOfWeek").css("display", "none");
        }
    };

    // 적용조건 - 구매금액 입력 사용/미사용 체크박스
    $scope.isChkMinSaleAmt = function(){
        if($scope.isCheckedMinSaleAmt){
            $("#divChkMinSaleAmt").css("display", "");
        }else{
            $("#divChkMinSaleAmt").css("display", "none");
        }
    };

    // 적용조건 - 입금일 입력 사용/미사용 체크박스
    $scope.isChkDepositYmd = function(){
        if($scope.isCheckedDepositYmd){
            $("#divChkDepositYmd").css("display", "");
        }else{
            $("#divChkDepositYmd").css("display", "none");
        }
    };

    // 적용상품 입력 사용/미사용 체크박스
    $scope.isChkProd = function () {

        // 프로모션 종류 '영수증전체할인'은 적용상품 사용안함.
        if(vPromotionType === "001"){
            $("input:checkbox[id='chkProd']").prop("checked", false);
            $scope.isCheckedProd = false;
            return false;
        }

        // 프로모션 종류가 '적용품목할인', '1+1증정', '특별가'는 적용상품 무조건 사용
        if(vPromotionType === "101" || vPromotionType === "301" || vPromotionType === "401" || vPromotionType === "501"){
            $("input:checkbox[id='chkProd']").prop("checked", true);
            $scope.isCheckedProd = true;
            return false;
        }

        if($scope.isCheckedProd){
            $("#trProdTop").css("border-bottom", "1px solid #EEEEEE");
            $("#trSelectProdDs").css("display", "");
            $("#trSelectProdGrid").css("display", "");

            // 적용상품 목록 재조회(grid header가 안나오는 경우가 있어서 체크되면 다시 조회)
            if($("#hdPromotionCd").val() !== "") {
                $scope._pageView('promotionSelectProdGridCtrl', 1);
            }

        }else{
            $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
            $("#trSelectProdDs").css("display", "none");
            $("#trSelectProdGrid").css("display", "none");
        }
    };

    // ===============================================================================================================================
    
    // 프로모션 예시사례 안내 팝업
    $scope.openPromotionExample = function () {
        $scope.promotionExampleLayer.show(true);
        $scope._broadcast('promotionExampleCtrl');
    };

    // 파일업로드시 파일사이즈 변경
    $scope.uploadChange = function(){
        $scope.$apply(function() {
          var maxSize = 20 * 1024 * 1024;
          var fileSize = document.getElementById("fileKioskBanner").files[0].size;
          if(fileSize > maxSize) {
            $scope._popMsg(messages["promotion.fileSize.max"]); // 파일사이즈는 최대 20MB까지 가능합니다.

            // 첨부파일 리셋
            var agent = navigator.userAgent.toLowerCase();
            if ((navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1)){
              // ie 일때
              $("#fileKioskBanner").replaceWith( $("#fileKioskBanner").clone(true) );
              $scope.fileKioskBanner = "";
            } else {
              // other browser 일때
              $("#fileKioskBanner").val("");
              $scope.fileKioskBanner = "";
            }
            $("#lblFileSize").text("");
            return false;
          } else {
            $("#lblFileSize").text(fileSize);
          }
        });
    };

    // 이미지 조회
    $scope.getImg = function () {

        // 이미지와 첨부파일 초기화
        $scope.imgCancel("003", 'A');

        var params = [];
        params.fileType = "003";
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._postJSONQuery.withOutPopUp("/base/promotion/promotion/getPromotionBanner.sb", params, function (response) {

            if (response.data.data.list.length > 0) {
                var list = response.data.data.list;

                for (var i = 0; i < list.length; i++) {

                    if(list[i].fileUseType === "003"){
                        $("#lblFileNm").text(list[i].fileOrgNm);
                        $("#hdFileNo").val(list[i].adverFileNo);
                    }
                }
            }

        });

    };

    // 이미지 선택취소
    $scope.imgCancel = function(imgFg, type){

        // type - A: 무조건 이미지 관련 데이터 초기화(프로모션 리스트 조회, 프로모션코드 클릭 시 해당)
        // type - F: 이미지 파일이 등록되어있으면 '선택취소' 버튼 클릭 시 동작 안하도록
        if(type === "F"){
            if(imgFg === "003") {
                if ($("#hdFileNo").val() !== "") { // 기존 이미지 파일이 있는 경우
                    if ($("#fileKioskBanner").val() !== "") { // 새 첨부 이미지를 넣고 선택 취소 시,
                        // 재조회
                        $scope.getImg();
                        return;
                    } else {
                        return;
                    }
                }
            }
        }

        var element = "";

        // 첨부파일 초기화
        if (imgFg === "003") {
            $("#lblFileNm").text("");
            $("#hdFileNo").val("");
            $("#lblFileSize").text("");
            element = "fileKioskBanner";
        }

        $scope.resetFile(element);
    };

    // 브라우저에 따른 첨부파일 초기화
    $scope.resetFile = function(element){

        var agent = navigator.userAgent.toLowerCase();

        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
            // ie 일때
            //$("#" + element).replaceWith( $("#" + element).clone(true) );
            $("#" + element).val("");
        } else {
            // other browser 일때
            $("#" + element).val("");
        }
    };

    // 이미지 삭제
    $scope.delImg = function (imgFg) {

        // 등록한 이미지가 없을때 삭제버튼 클릭 시 동작 안하도록
        if(imgFg === "003"){
            if($("#hdFileNo").val() === ""){
                return;
            }
        }

        // 이미지를 삭제하시겠습니까?
        var msg = messages["promotion.fileDel.msg"];
        s_alert.popConf(msg, function () {

            var formData = new FormData($("#regForm")[0]);

            formData.append("orgnFg", orgnFg); // sessionInfoVO에 있는 orgnFg 값을 제대로 읽어오지 못하는 현상때문에 추가, form태그가 원인??
            formData.append("orgnCd", orgnCd);
            formData.append("hqOfficeCd", hqOfficeCd);
            formData.append("storeCd", storeCd);
            formData.append("fileType", "003");
            formData.append("promotionCd", $("#hdPromotionCd").val());
            formData.append("verSerNo", $("#hdFileNo").val());

            $scope.$broadcast('loadingPopupActive');

            $.ajax({
                url: "/base/promotion/promotion/delPromotionBanner.sb",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                cache: false,
                success: function(result) {

                    if (result.status === "OK") {
                        // 삭제 되었습니다.
                        $scope._popMsg(messages["cmm.delSucc"]);

                        // 재조회
                        $scope.getImg();

                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "FAIL") {
                        $scope._popMsg('Ajax Fail By HTTP Request');
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    else if (result.status === "SERVER_ERROR") {
                        $scope._popMsg(result.message);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                    /*else if(result.status === undefined) {
                        location.href = "/";
                    }*/
                    else {
                        var msg = result.status + " : " + result.message;
                        $scope._popMsg(msg);
                        $scope.$broadcast('loadingPopupInactive');
                    }
                },
                error : function(result){
                    $scope._popMsg("error");
                    $scope.$broadcast('loadingPopupInactive');
                }
            },function(){
                $scope._popMsg("Ajax Fail By HTTP Request");
                $scope.$broadcast('loadingPopupInactive');
            });
        });
    }

}]);

/**
 * 프로모션관리 적용상품 그리드
 */
app.controller('promotionSelectProdGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionSelectProdGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.gubunDsFgDataMap = new wijmo.grid.DataMap(gubunDsFgData, 'value', 'name'); //상품코드/분류 구분
        $scope.applyDcDsDataMap = new wijmo.grid.DataMap(applyDcDsData, 'value', 'name'); //할인구분
    };

    //
    $scope.$on("promotionSelectProdGridCtrl", function(event, data) {

        // 적용상품 목록 조회
        $scope.getPromotionProdList();
        event.preventDefault();
    });

    // 적용상품 목록 조회
    $scope.getPromotionProdList = function(){

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/promotion/getPromotionProdList.sb", params, function () {

            var grid = wijmo.Control.getControl("#wjGridSelectProd");
            var columns = grid.columns;

            // 적용상품 구매대상이 '품목개별할인' 인 경우만 할인구분, 할인값 상품별 개별입력 가능.
            if($scope.selectProdDsCombo.selectedValue === "5"){
                columns[4].width = 180;   // 할인구분, 할인값 컬럼추가로 인한 명칭 컬럼 width 값 조정
                columns[6].visible = true;
                columns[7].visible = true;
            }else {
                columns[4].width = 250;
                columns[6].visible = false;
                columns[7].visible = false;
            }

            // 일괄등록 입력값 초기화
            $scope.applyDcDsBatchCombo.selectedIndex = 0; // 할인구분
            $("#dcSetBatch").val(""); // 할인값
        });
    };

    // 적용상품 추가 팝업
    $scope.prodAdd = function () {
        $scope.promotionProdRegLayer.show(true);
        $scope._broadcast('promotionProdRegCtrl', $scope.selectProdDsCombo.selectedValue);
    };

    // 적용분류 추가 팝업
    $scope.classAdd = function () {
        $scope.promotionClassRegLayer.show(true);
        $scope._broadcast('promotionClassRegCtrl', $scope.selectProdDsCombo.selectedValue);
    };

    // 적용상품 저장
    $scope.prodSave = function () {

        // 파라미터 설정
        var params = new Array();

        // 구매대상 선택값이 전체구매, 일부구매(종류+수량)인 경우만 조건수량 체크
        if($scope.selectProdDsCombo.selectedValue === "1" || $scope.selectProdDsCombo.selectedValue === "2") {
            // 조건수량이 수정된 내역이 있는지 체크
            if ($scope.flexSelectProdGrid.collectionView.itemsEdited.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            }
        }

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flexSelectProdGrid.collectionView.itemsEdited.length; i++) {
            var item = $scope.flexSelectProdGrid.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("저장할 " + messages["promotion.product"] + " 또는 " + messages["promotion.class"] + "의 체크박스" + messages["promotion.chk.item"]); // 저장할 상품 또는 분류의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectProdGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectProdGrid.collectionView.itemsEdited[i];

            // 구매대상 선택값이 전체구매, 일부구매(종류+수량)인 경우만 조건수량 체크
            if($scope.selectProdDsCombo.selectedValue === "1" || $scope.selectProdDsCombo.selectedValue === "2") {
                if (item.gChk === true && (item.prodQty === null || item.prodQty === "" || item.prodQty === "0" || item.prodQty === 0)) {
                    $scope._popMsg(messages["promotion.chk.prodQty"]); // 선택한 상품의 조건수량을 반드시 입력하세요.
                    return false;
                }
            }

            // 적용상품 구매대상이 '품목개별할인' 인 경우만 할인구분, 할인값 체크
            if($scope.selectProdDsCombo.selectedValue === "5"){
                if (item.gChk === true && (item.applyDcDs === null || item.applyDcDs === "" || item.applyDcDs === undefined)) {
                    $scope._popMsg(messages["promotion.chk.applyDcDs"]); // 선택한 상품의 할인구분을 반드시 입력하세요.
                    return false;
                }

                if (item.gChk === true && (item.dcSet === null || item.dcSet === "" || item.dcSet === "0" || item.dcSet === 0)) {
                    $scope._popMsg(messages["promotion.chk.dcSetVal"]); // 선택한 상품의 할인값을 반드시 입력하세요.
                    return false;
                }

                // 정률할인의 할인값은 0~100 사이의 숫자만 입력하세요.
                if(item.gChk === true && item.applyDcDs === "1") {
                    if (0 > item.dcSet || item.dcSet > 100) {
                        $scope._popMsg(messages["promotion.chk.dcSet.limit1"]);
                        return false;
                    }
                }

                // 정액할인의 할인값은 1원단위를 입력할 수 없습니다.
                if(item.gChk === true && item.applyDcDs === "2") {
                    if(Number(item.dcSet) % 10 > 0){
                        $scope._popMsg(messages["promotion.chk.dcSet.limit2"]);
                        return false;
                    }
                }
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.condiProdSeq = item.condiProdSeq;

                // 구매대상 선택값이 전체구매, 일부구매(종류+수량)인 경우만 조건수량 입력
                if($scope.selectProdDsCombo.selectedValue === "1" || $scope.selectProdDsCombo.selectedValue === "2") {
                    obj.prodQty = item.prodQty;
                }else{
                    obj.prodQty = 1;
                }

                // 적용상품 구매대상이 '품목개별할인' 인 경우만 할인구분, 할인값 입력
                if($scope.selectProdDsCombo.selectedValue === "5"){
                    obj.applyDcDs = item.applyDcDs;
                    obj.dcSet = item.dcSet;
                }else{
                    obj.applyDcDs = "1";
                    obj.dcSet = 1;
                }

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/promotion/savePromotionProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.saveSucc"]); // 저장 되었습니다.

                // 적용상품 목록 재조회
                $scope.getPromotionProdList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };
    
    // 적용상품 삭제
    $scope.prodDel = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flexSelectProdGrid.collectionView.items.length; i++) {
            var item =  $scope.flexSelectProdGrid.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["promotion.product"] + " 또는 " + messages["promotion.class"] + "의 체크박스" + messages["promotion.chk.item"]); //  삭제할 상품 또는 분류의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i=0; i< $scope.flexSelectProdGrid.collectionView.items.length; i++) {

            var item =  $scope.flexSelectProdGrid.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.condiProdSeq = item.condiProdSeq;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/promotion/savePromotionProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 적용상품 목록 재조회
                $scope.getPromotionProdList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };

    // 할인구분 일괄적용
    $scope.batchApplyDcDs = function () {

        var selectCnt = 0;
        for(var i = $scope.flexSelectProdGrid.collectionView.items.length-1; i >= 0; i-- ){
          var item = $scope.flexSelectProdGrid.collectionView.items[i];
          if(item.gChk === true) selectCnt++;
        }

        if(selectCnt < 1) {
          $scope._popMsg(messages["promotion.chk.batch"]); // 상품 또는 분류를 선택해주세요.
          return false;
        }

        for(var i = $scope.flexSelectProdGrid.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flexSelectProdGrid.collectionView.items[i].gChk) {
                $scope.flexSelectProdGrid.collectionView.items[i].applyDcDs = $scope.applyDcDsBatchCombo.selectedValue;
            }
        }

        $scope.flexSelectProdGrid.collectionView.commitEdit();
        $scope.flexSelectProdGrid.collectionView.refresh();
    };

    // 할인값 일괄적용
    $scope.batchDcSet = function () {

        var selectCnt = 0;
        for(var i = $scope.flexSelectProdGrid.collectionView.items.length-1; i >= 0; i-- ){
          var item = $scope.flexSelectProdGrid.collectionView.items[i];
          if(item.gChk === true) selectCnt++;
        }

        if(selectCnt < 1) {
          $scope._popMsg(messages["promotion.chk.batch"]); // 상품 또는 분류를 선택해주세요.
          return false;
        }

        for(var i = $scope.flexSelectProdGrid.collectionView.items.length-1; i >= 0; i-- ) {
            if ($scope.flexSelectProdGrid.collectionView.items[i].gChk) {
                $scope.flexSelectProdGrid.collectionView.items[i].dcSet = $("#dcSetBatch").val();
            }
        }

        $scope.flexSelectProdGrid.collectionView.commitEdit();
        $scope.flexSelectProdGrid.collectionView.refresh();
    };

}]);

/**
 * 프로모션관리 적용매장 그리드
 */
app.controller('promotionSelectStoreGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionSelectStoreGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("promotionSelectStoreGridCtrl", function(event, data) {

        // 적용매장 목록 조회
        $scope.getPromotionStoreList();
        event.preventDefault();
    });

    // 적용매장 목록 조회
    $scope.getPromotionStoreList = function(){

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/promotion/getPromotionStoreList.sb", params, function () {});

    };
    
    // 적용매장 추가 팝업
    $scope.storeAdd = function () {

        if(momsEnvstVal === '1') { // [1250 맘스터치] 환경설정값 '사용'인 경우, 맘스터치용 적용매장 추가 팝업 호출
            $scope.promotionMomsStoreRegLayer.show(true);
            $scope._broadcast('promotionMomsStoreRegCtrl');
        }else{
            $scope.promotionStoreRegLayer.show(true);
            $scope._broadcast('promotionStoreRegCtrl');
        }
    };

    // 적용매장 삭제
    $scope.storeDel = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flexSelectStoreGrid.collectionView.itemsEdited.length; i++) {
            var item = $scope.flexSelectStoreGrid.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["promotion.store"] + "의 체크박스" + messages["promotion.chk.item"]); // 삭제할 매장의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectStoreGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectStoreGrid.collectionView.itemsEdited[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.storeCd = item.storeCd;
                obj.verSerNo = $("#hdFileNo").val();

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/promotion/savePromotionStore.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 적용매장 목록 재조회
                $scope.getPromotionStoreList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };

    // 양식다운로드
    $scope.storeSampleDown = function () {
        var vScope = agrid.getScope('excelUploadPromotionCtrl');
        vScope.excelFormDownload();
    };

    // 엑셀업로드
    $scope.storeExcelUpload = function () {

       var vScope = agrid.getScope('excelUploadPromotionCtrl');
       var msg = messages["promotion.excelUpload.confmMsg"];  // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?

       s_alert.popConf(msg, function () {

           /* 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. */
           vScope.parentCtrl = 'promotionSelectStoreGridCtrl';

           $("#excelUpFile").val('');
           $("#excelUpFile").trigger('click');

       });
    };

    /** 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. */
    $scope.uploadCallBack = function () {
        $scope._pageView('promotionSelectStoreGridCtrl', 1);
    };

    // 엑셀다운로드
    $scope.storeExcelDown = function () {
        if($scope.flexSelectStoreGrid.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
            return false;
        }

        // 엑셀 데이터에 체크박스 컬럼은 안나오도록 숨김
        $scope.flexSelectStoreGrid.columns[0].visible = false;

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flexSelectStoreGrid, {
                includeColumnHeaders: true,
                includeCellStyles   : true,
                includeColumns      : function (column) {
                    return column.visible;
                }
            }, '프로모션[' + $("#promotionNm").val() + ']_적용매장_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    // 체크박스 컬럼 다시 보이게 처리
                    $scope.flexSelectStoreGrid.columns[0].visible = true;
                }, 10);
            });
        }, 10);
    };

}]);

/**
 * 프로모션관리 혜택상품 그리드
 */
app.controller('promotionSelectPresentGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('promotionSelectPresentGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("promotionSelectPresentGridCtrl", function(event, data) {

        // 혜택상품 목록 조회
        $scope.getPromotionPresentList();
        event.preventDefault();
    });

    // 혜택상품 목록 조회
    $scope.getPromotionPresentList = function(){

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/promotion/getPromotionPresentList.sb", params, function () {});

    };
    
    // 혜택상품 추가
    $scope.presentAdd = function () {
        $scope.promotionPresentRegLayer.show(true);
        $scope._broadcast('promotionPresentRegCtrl',  $scope.presentDsCombo.selectedValue);
    };
    
    // 혜택상품 저장
    $scope.presentSave = function () {

        // 파라미터 설정
        var params = new Array();

        // 조건수량이 수정된 내역이 있는지 체크
        if ($scope.flexSelectPresentGrid.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
        }

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flexSelectPresentGrid.collectionView.itemsEdited.length; i++) {
            var item = $scope.flexSelectPresentGrid.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("저장할 " + messages["promotion.product"] + "의 체크박스" + messages["promotion.chk.item"]); // 저장할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectPresentGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectPresentGrid.collectionView.itemsEdited[i];

            if (item.gChk === true && (item.giftQty === null || item.giftQty === "" || item.giftQty === "0" )) {
                $scope._popMsg(messages["promotion.chk.prodQty"]); // 선택한 상품의 조건수량을 반드시 입력하세요.
                return false;
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;
                obj.giftQty =  item.giftQty;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/promotion/savePromotionPresent.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.saveSucc"]); // 저장 되었습니다.

                // 혜택상품 목록 재조회
                $scope.getPromotionPresentList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    };
    
    // 혜택상품 삭제
    $scope.presentDel = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i=0; i< $scope.flexSelectPresentGrid.collectionView.items.length; i++) {
            var item =  $scope.flexSelectPresentGrid.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("삭제할 " + messages["promotion.product"] + "의 체크박스" + messages["promotion.chk.item"]); // 삭제할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i=0; i< $scope.flexSelectPresentGrid.collectionView.items.length; i++) {

            var item =  $scope.flexSelectPresentGrid.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/promotion/savePromotionPresent.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 혜택상품 목록 재조회
                $scope.getPromotionPresentList();

                $scope.$broadcast('loadingPopupInactive');

            } else {
                $scope.$broadcast('loadingPopupInactive');
                $scope._popMsg(result.status);
                return false;
            }
        }, function (err) {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(err.message);
        });
    }

}]);


// 프로모션 종류 선택 시 사용 팝업
function promotionTypeChgConfirmPop(msg, callbackY, callbackN) {
    var id = s_alert.randomString(5);
    var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);
    pop.find("p").text(msg);

    pop.find("a.btn_blue.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        if (typeof callbackY === 'function') {
            setTimeout(function () {
                callbackY();
            }, 50);
        }
        return false;
    });

    pop.find("a.btn_gray.conf").bind("click", function () {
        $("#_alertTent").hide();
        pop.remove();
        if (typeof callbackN === 'function') {
            setTimeout(function () {
                callbackN();
            }, 50);
        }
        return false;
    });

    $("#_alertTent").show();
    pop.show();
}