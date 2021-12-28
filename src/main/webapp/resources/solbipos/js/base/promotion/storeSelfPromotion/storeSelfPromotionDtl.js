/****************************************************************
 *
 * 파일명 : storeSelfPromotionDtl.js
 * 설  명 : 매장자체프로모션현황 상세화면 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.08     이다솜      1.0
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

/**
 * 매장자체프로모션현황 상세화면 생성
 */
app.controller('storeSelfPromotionDtlCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSelfPromotionDtlCtrl', $scope, $http, $timeout, true));

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
    $scope._setComboData("presentDs", presentDsFgData); // 혜택상품 - 증정구분

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

    $scope.$on("storeSelfPromotionDtlCtrl", function(event, data) {

        // layer show
        $scope.storeSelfPromotionDtlLayer.show(true);

        if(!isEmptyObject(data)){

            // 기존 프로모션 상세정보 셋팅
            $scope.setPromotionDetail(data);
            // 적용상품 목록 조회
            $scope._pageView('storeSelfPromotionSelectProdGridCtrl', 1);
            // 혜택상품 목록 조회
            $scope._pageView('storeSelfPromotionSelectPresentGridCtrl', 1);
        }
    });

    // 기존 프로모션 상세정보 셋팅
    $scope.setPromotionDetail = function(data){

        // hidden에 promotionCd 값 가지고 있기(상품, 매장, 혜택상품 리스트 저장 시 사용)
        $("#hdStoreCd").val(data.storeCd);
        $("#hdPromotionCd").val(data.promotionCd);

        var params = {};
        params.storeCd = data.storeCd;
        params.promotionCd = data.promotionCd;
        params.beneSeq = "1";

        $.postJSON("/base/promotion/storeSelfPromotion/getStoreSelfPromotionDtl.sb", params, function(result) {

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

    // ===============================================================================================================================

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

            // 할인구분 제목 -> 특별가로 변경
            // 할인구분 selectBox와 할인금액 제목을 숨김
            $("#lblApplyDcDs").text(messages["promotion.specialPrice"]);
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
app.controller('storeSelfPromotionSelectProdGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSelfPromotionSelectProdGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.gubunDsFgDataMap = new wijmo.grid.DataMap(gubunDsFgData, 'value', 'name'); //상품코드/분류 구분
    };

    //
    $scope.$on("storeSelfPromotionSelectProdGridCtrl", function(event, data) {

        // 적용상품 목록 조회
        $scope.getPromotionProdList();
        event.preventDefault();
    });

    // 적용상품 목록 조회
    $scope.getPromotionProdList = function(){

        var params = {};
        params.storeCd = $("#hdStoreCd").val();
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/storeSelfPromotion/getStoreSelfPromotionProdList.sb", params, function () {});

    };

}]);

/**
 * 프로모션관리 혜택상품 그리드
 */
app.controller('storeSelfPromotionSelectPresentGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSelfPromotionSelectPresentGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("storeSelfPromotionSelectPresentGridCtrl", function(event, data) {

        // 혜택상품 목록 조회
        $scope.getPromotionPresentList();
        event.preventDefault();
    });

    // 혜택상품 목록 조회
    $scope.getPromotionPresentList = function(){

        var params = {};
        params.storeCd = $("#hdStoreCd").val();
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/storeSelfPromotion/getStoreSelfPromotionPresentList.sb", params, function () {});

    };

}]);