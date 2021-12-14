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

// 적용등급
var memberClassCdFgData = [
    {"name":"플래티넘","value":"1"},
    {"name":"VIP","value":"2"}
];

// 적용상품 - 구매대상
var selectProdDsFgData = [
    {"name":"전체구매","value":"1"},
    {"name":"일부구매(종류+수량)","value":"2"},
    {"name":"일부구매(수량=교차선택)","value":"3"},
    {"name":"제외상품","value":"4"}
];

// 혜택유형
var typeCdFgData = [
    {"name":"전체할인(=영수증할인)","value":"1"},
    {"name":"적용품목할인","value":"2"},
    {"name":"혜택품목할인","value":"3"},
    {"name":"증정","value":"4"},
    {"name":"특별가(=세트가)","value":"5"}
];

// 할인구분
var applyDcDsData = [
    {"name":"정률할인","value":"1"},
    {"name":"정액할인","value":"2"}
];

// 혜택상품 - 구분
var presentDsFgData = [
    {"name":"전체증정","value":"1"},
    {"name":"선택증정","value":"2"}
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
    $scope._setComboData("promotionStartHhCombo", Hh); // 적용조건 - 적용요일(시작 시)
    $scope._setComboData("promotionStartMmCombo", Mm); // 적용조건 - 적용요일(시작 분)
    $scope._setComboData("promotionEndHhCombo", Hh); // 적용조건 - 적용요일(종료 시)
    $scope._setComboData("promotionEndMmCombo", Mm); // 적용조건 - 적용요일(종료 분)
    $scope._setComboData("memberTargetDs", memberTargetDsFgData); // 적용조건 - 적용대상
    $scope._setComboData("memberClassCd", memberClassCdFgData); // 적용조건 - 적용등급
    //$scope._setComboData("selectProdDs", selectProdDsFgData); // 적용상품 - 구매대상
    $scope._setComboData("storeSelectExceptFg", storeSelectExceptFgData); // 적용매장 - 매장등록구분
    //$scope._setComboData("typeCd", typeCdFgData); // 혜택유형
    $scope._setComboData("applyDcDs", applyDcDsData); // 할인구분
    $scope._setComboData("presentDs", presentDsFgData); // 혜택상품 - 구분

    // 적용조건 - 적용구분 셋팅
    $scope.dlvFgInStore = true;
    $scope.dlvFgDelivery = false;
    $scope.dlvFgPacking = false;

    // 적용조건 - 적용기간 셋팅
    $scope.isCheckedPeriod = false;
    var promotionStartDate = wcombo.genDateVal("#promotionStartDate", gvStartDate);
    var promotionEndDate = wcombo.genDateVal("#promotionEndDate", gvEndDate);

    // 적용조건 - 적용시간 셋팅
    $scope.isCheckedTime = false;

    // 적용조건 - 적용요일 셋팅
    $scope.isCheckedDayOfWeek = false;

    // 최소구매금액 셋팅
    $scope.isCheckedMinSaleAmt = false;

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
            
            // 신규등록인 경우, 상세등록영역의 필수입력값 자동 셋팅 (Validation Check에 걸리지 않도록)
            $("#selectProdCnt").val("1"); // 적용상품 - 수량
            $("#dcSet").val("1");          // 적용혜택 - 할인율
            $("#selectGiftCnt").val("1"); // 혜택상품 - 상품수

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
                    if (info.regFg === 'S') { // 등록자가 매장일 때,

                        $(".updownSet").css("display", "");

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
                    } else { // 등록자가 본사일 때
                        $("#btnSave").css("display", "none");
                        $(".updownSet").css("display", "none");
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

                // 프로모션 종류 변경시 필요한 임시변수
                vPromotionType = info.promoType;
                vRegFg = info.regFg;
                vDateYn = info.dateYn;
                vStartYmd = info.startYmd;
                vRegDt = info.regDt;

                // ------------ 기본정보 ------------
                $("#promotionNm").val(info.promotionNm); // 프로모션명
                $("#memo").val(info.memo); // 메모
                $scope.useYnCombo.selectedValue = info.useYn; // 사용여부

                // 프로모션 종류별 입력값 셋팅
                $scope.setForm(info.promoType);
                $scope.promotionTypeCombo.selectedValue = info.promoType; // 프로모션 종류

                // ------------ 적용조건 ------------
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
                    $("input:checkbox[id='chkDayOfWeekSun']").prop("checked", info.sunYn === 'Y' ? true : false);;
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

                if(info.minSaleAmt !== "" && info.minSaleAmt !== "0" && info.minSaleAmt !== 0){
                    $("input:checkbox[id='chkMinSaleAmt']").prop("checked", true); //최소구매금액
                    $("#minSaleAmt").val(info.minSaleAmt);
                    $scope.isCheckedMinSaleAmt = true;
                    $("#divChkMinSaleAmt").css("display", "");
                }else{
                    $("input:checkbox[id='chkMinSaleAmt']").prop("checked", false);
                    $("#minSaleAmt").val("");
                    $scope.isCheckedMinSaleAmt = false;
                    $("#divChkMinSaleAmt").css("display", "none");
                }

                // ------------ 적용상품 ------------
                $scope.selectProdDsCombo.selectedValue = info.selectProdDs; // 구매대상
                $("#selectProdCnt").val(info.selectProdCnt); // 수량

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

                if(info.typeCd < 4) {
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

                    if(info.presentDs === "1"){
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

            if ($("#chkMinSaleAmt").is(":checked")) {
                params.minSaleAmt = $("#minSaleAmt").val(); // 최소구매금액
            }else{
                params.minSaleAmt = "";
            }

            // ------------ 적용상품 ------------
            params.prodCdYn = 'Y'; // 적용상품 체크여부
            params.selectProdDs = $scope.selectProdDsCombo.selectedValue; // 적용상품 - 구매대상
            params.selectProdCnt = $("#selectProdCnt").val(); // 적용상품 - 수량

            // ------------ 적용매장 ------------
            if(orgnFg === "HQ") {
                params.storeSelectExceptFg = $scope.storeSelectExceptFgCombo.selectedValue; // 매장등록구분
            }

            // ------------ 적용혜택 ------------
            params.typeCd = $scope.typeCdCombo.selectedValue; // 혜택유형
            if($scope.typeCdCombo.selectedValue < 4){
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
                if ($scope.presentDsCombo.selectedValue === "1") {
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
        }

    };

    // 프로모션 등록/수정 Validation Check
    $scope.chkValid = function(){

        // 프로모션명을(를) 입력하세요.
        if($("#promotionNm").val() === "" || $("#promotionNm").val() === null) {
            $scope._popMsg(messages["promotion.promotionNm"] +  messages["cmm.require.text"]);
            return false;
        }

        // 적용구분은 한 개 이상 선택하세요.
        var chkDlvFg = 0;

        if($("#chkDlvFgInStore").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgDelivery").is(":checked")) chkDlvFg++;
        if($("#chkDlvFgPacking").is(":checked")) chkDlvFg++;

        if(chkDlvFg === 0){
            $scope._popMsg(messages["promotion.chk.DlvFg"]);
            return false;
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

        // 최소구매금액을(를) 입력하세요.
        if($("#chkMinSaleAmt").is(":checked")){
            if ($("#minSaleAmt").val() === "" || $("#minSaleAmt").val() === null) {
                $scope._popMsg(messages["promotion.minSaleAmt"] + messages["cmm.require.text"]);
                return false;
            }
        }

        // 적용상품의 상품수/수량을(를) 입력하세요.
        if($("#selectProdCnt").val() === "" || $("#selectProdCnt").val() === null){
            if($scope.selectProdDsCombo.selectedValue === "2"){
                $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossNCnt"] + messages["cmm.require.text"]);
            }else{
                $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossYCnt"] + messages["cmm.require.text"]);
            }
            return false;
        }

        if($scope.typeCdCombo.selectedValue < 4){
            // 적용혜택의 할인율/할인금액을(를) 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === ""){
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
            // 할인금액은 1원단위를 입력할 수 없습니다.
            if($scope.applyDcDsCombo.selectedValue === "2") {
                if(Number($("#dcSet").val()) % 10 > 0){
                    $scope._popMsg(messages["promotion.chk.dcSetAmt"]);
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
            // 적용혜택의 할인금액을(를) 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === "") {
                $scope._popMsg(messages["promotion.bene"] + "의 " + messages["promotion.dcSetAmt"] +  messages["cmm.require.text"]);
                return false;
            }
            // 할인금액은 1원단위를 입력할 수 없습니다.
            if(Number($("#dcSet").val()) % 10 > 0){
                $scope._popMsg(messages["promotion.chk.dcSetAmt"]);
                return false;
            }
        }

        // 혜택상품의 상품수을(를) 입력하세요.
        if($scope.typeCdCombo.selectedValue === "3" || $scope.typeCdCombo.selectedValue === "4"){
            if ($scope.presentDsCombo.selectedValue === "1") {
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
        $("input:checkbox[id='chkDlvFgInStore']").prop("checked", true); // 적용구분(내점)
        $("input:checkbox[id='chkDlvFgDelivery']").prop("checked", false); // 적용구분(배달)
        $("input:checkbox[id='chkDlvFgPacking']").prop("checked", false); // 적용구분(포장)

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
        
        $("input:checkbox[id='chkMinSaleAmt']").prop("checked", false); //최소구매금액
        $("#minSaleAmt").val("");
        $scope.isCheckedMinSaleAmt = false;
        $("#divChkMinSaleAmt").css("display", "none");

        // ------------ 적용상품 ------------
        $scope.selectProdDsCombo.selectedIndex = 0; // 구매대상
        $("#selectProdCnt").val(""); // 수량

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

            // 적용상품 grid 버튼
            $("#btnProdAdd").css("display", "none");
            $("#btnClassAdd").css("display", "none");
            $("#btnProdSave").css("display", "none");
            $("#btnProdDel").css("display", "none");

            // 적용매장 grid 버튼
            $("#btnStoreAdd").css("display", "");
            $("#btnStoreDel").css("display", "none");

            // 혜택상품 grid 버튼
            $("#btnPresentAdd").css("display", "");
            $("#btnPresentSave").css("display", "none");
            $("#btnPresentDel").css("display", "none");
            
        }else{ // 모든 버튼 보이게 처리

            // 저장 버튼
            $("#btnSave").css("display", "");

            // 적용상품 grid 버튼
            $("#btnProdAdd").css("display", "");
            $("#btnClassAdd").css("display", "");
            $("#btnProdSave").css("display", "");
            $("#btnProdDel").css("display", "");

            // 적용매장 grid 버튼
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
                    if (vRegFg === 'S') { // 등록자가 매장일 때,
                        if(modPromotionEnvstVal !== "1"){ // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'이 아닌 경우
                            if(vDateYn === "Y"){ // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 변경 불가능
                                if(Number(now) >= Number(vStartYmd)){
                                    return false;
                                }
                            }else{ // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 변경 불가능
                                if(Number(now) > Number(vRegDt)) {
                                    return false;
                                }
                            }
                        }
                    } else { // 등록자가 본사일 때
                        return false;
                    }
                }else{ // 본사권한 일 때
                    if(modPromotionEnvstVal !== "1"){ // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'이 아닌 경우
                        if(vDateYn === "Y"){ // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 변경 불가능
                            if(Number(now) >= Number(vStartYmd)){
                                return false;
                            }
                        }else{ // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 변경 불가능
                            if(Number(now) > Number(vRegDt)) {
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
                        params.selectProdDs = "3";   // 적용상품 - 구매대상
                        params.typeCd = "1";          // 혜택유형
                        params.applyDcDs = "1";       // 할인구분
                        params.dcSet = "1";           // 할인금액
                        params.presentDs = "";        // 혜택상품 - 구분
                        params.selectGiftCnt = "";   // 상품수
                    }else if (s.selectedValue === "101"){
                        params.selectProdDs = "1";
                        params.typeCd = "1";
                        params.applyDcDs = "1";
                        params.dcSet = "1";
                        params.presentDs = "";
                        params.selectGiftCnt = "";
                    }else if (s.selectedValue === "201"){
                        params.selectProdDs = "1";
                        params.typeCd = "3";
                        params.applyDcDs = "1";
                        params.dcSet = "1";
                        params.presentDs = "1";
                        params.selectGiftCnt = "1";
                    }else if (s.selectedValue === "301"){
                        params.selectProdDs = "1";
                        params.typeCd = "4";
                        params.applyDcDs = "";
                        params.dcSet = "";
                        params.presentDs = "1";
                        params.selectGiftCnt = "1";
                    }else if (s.selectedValue === "401"){
                        params.selectProdDs = "1";
                        params.typeCd = "5";
                        params.applyDcDs = "2";
                        params.dcSet = "10";
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

            // 프로모션 종류가 특별가인 경우, 할인율(할인금액) 10으로 셋팅(Validation Check에 걸리지 않기 위해)
            if(s.selectedValue === "401"){
                $("#dcSet").val("10"); // 적용혜택 - 할인율
            }else{
                $("#dcSet").val("1");
            }
        }
    };

    // 프로모션 종류별 입력값 셋팅
    $scope.setForm= function(promoType){
        if (promoType === "001") { // 전체할인
            $scope._setComboData("selectProdDs", selectProdDsFgData.slice(2, 4));  // 적용상품 - 구매대상
            $scope._setComboData("typeCd", typeCdFgData.slice(0, 2));                // 적용혜택 - 혜택유형
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "101") { // 적용상품할인
            $scope._setComboData("selectProdDs", selectProdDsFgData.slice(0, 3));
            $scope._setComboData("typeCd", typeCdFgData.slice(0, 2));
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "201") { // 1+1 할인
            $scope._setComboData("selectProdDs", selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3)));
            $scope._setComboData("typeCd", typeCdFgData.slice(2, 3));
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else if (promoType === "301") { // 1+1 증정
            $scope._setComboData("selectProdDs", selectProdDsFgData.slice(0, 1).concat(selectProdDsFgData.slice(2, 3)));
            $scope._setComboData("typeCd", typeCdFgData.slice(3, 4));
            $("#lblApplyDcDs").text(messages["promotion.applyDcDs"]);
            $("#tdApplyDcDs").css("display", "");
            $("#thDcSet").css("display", "");
        } else { // 특별가
            $scope._setComboData("selectProdDs", selectProdDsFgData.slice(0, 1));
            $scope._setComboData("typeCd", typeCdFgData.slice(4, 5));

            // 할인구분 제목 -> 할인금액으로 변경
            // 할인구분 selectBox와 할인금액 제목을 숨김
            $("#lblApplyDcDs").text(messages["promotion.dcSetAmt"]);
            $("#tdApplyDcDs").css("display", "none");
            $("#thDcSet").css("display", "none");
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

    // 적용상품 - 교차선택구분 선택에 따른 selectProdCnt 명칭(상품수, 수량) 변경
    $scope.setSelectProdCnt = function (s){

        // 구매대상 선택값에 따른 적용상품 그리드 조건수량입력 제어를 위해
        // 구매대상이 전체구매, 일부구매(종류+수량)인 경우 조건수량 입력
        // 구매대상이 일부구매(수량=교차선택), 제외상품인 경우 조건수량 미입력(숨김처리)
        var grid = wijmo.Control.getControl("#wjGridSelectProd");
        var columns = grid.columns;

        if(s.selectedValue === "1"){
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]);
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = true;
        }else if(s.selectedValue === "2"){
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossNCnt"]);
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = true;
        }else if (s.selectedValue ==="3"){
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]);
            $("#btnProdAdd").text(messages["promotion.prodAdd"]);
            $("#btnClassAdd").text(messages["promotion.classAdd"]);
            columns[5].visible = false;
        }else{
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]);
            $("#btnProdAdd").text(messages["promotion.exceptProdAdd"]);
            $("#btnClassAdd").text(messages["promotion.exceptClassAdd"]);
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
    
    // 적용혜택 - 혜택유형 선택에 따른 할인구분, 할인율 disabled 여부
    $scope.setApplyDcDs = function (s) {
        if(s.selectedValue === "1" || s.selectedValue === "2"){
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

        }
    };

    // 적용혜택 - 할인구분 선택에 따른 dcSet 명칭(할인율, 할인금액, 판매금액) 변경
    $scope.setDcSet = function (s) {
        if(s.selectedValue === "1"){
            $("#lblDcSet").text(messages["promotion.dcSet"]);
        }else if(s.selectedValue === "2"){
            $("#lblDcSet").text(messages["promotion.dcSetAmt"]);
        }
    };

    // 혜택상품 - 구분 선택에 따른 상품수 disabled 여부
    $scope.setSelectGiftCnt = function (s){
        if(s.selectedValue === "1"){
            $("#thSelectGiftCnt").css("display", "");
            $("#selectGiftCnt").css("display", "");
        }else{
            $("#thSelectGiftCnt").css("display", "none");
            $("#selectGiftCnt").css("display", "none");
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

    // 최소구매금액 입력 사용/미사용 체크박스
    $scope.isChkMinSaleAmt = function(){
        if($scope.isCheckedMinSaleAmt){
            $("#divChkMinSaleAmt").css("display", "");
        }else{
            $("#divChkMinSaleAmt").css("display", "none");
        }
    };

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

        $scope._inquirySub("/base/promotion/promotion/getPromotionProdList.sb", params, function () {});

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

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.condiProdSeq = item.condiProdSeq;

                // 구매대상 선택값이 전체구매, 일부구매(종류+수량)인 경우만 조건수량 체크
                if($scope.selectProdDsCombo.selectedValue === "1" || $scope.selectProdDsCombo.selectedValue === "2") {
                    obj.prodQty = item.prodQty;
                }else{
                    obj.prodQty = 1;
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
    }
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
        $scope.promotionStoreRegLayer.show(true);
        $scope._broadcast('promotionStoreRegCtrl');
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
        $scope._broadcast('promotionPresentRegCtrl');
    }
    
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