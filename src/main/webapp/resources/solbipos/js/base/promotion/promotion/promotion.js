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
    {"name":"일부구매","value":"2"}
];

// 적용상품 - 교차선택구분
var selectProdCrossFgData = [
    {"name":"교차선택안함","value":"1"},
    {"name":"교차선택","value":"2"}
];

// 혜택유형
var typeCdFgData = [
    {"name":"전체할인","value":"1"},
    {"name":"적용상품할인","value":"2"},
    {"name":"혜택상품할인","value":"3"},
    {"name":"혜택상품증정","value":"4"}
];

// 할인구분
var applyDcDsData = [
    {"name":"정률할인","value":"1"},
    {"name":"정액할인","value":"2"}
];

// 혜택상품 - 증정구분
var presentDsFgData = [
    {"name":"전체증정","value":"1"},
    {"name":"선택증정","value":"2"}
];

// 혜택상품 - 교차선택구분
var selectCrossFgData = [
    {"name":"교차선택안함","value":"1"},
    {"name":"교차선택","value":"2"}
];

// 상품코드/분류 구분
var gubunDsFgData=[
    {"name":"상품","value":"1"},
    {"name":"분류","value":"2"}
];

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
    $scope._setComboData("promotionStartHhCombo", Hh); // 적용조건 - 적용요일(시작 시)
    $scope._setComboData("promotionStartMmCombo", Mm); // 적용조건 - 적용요일(시작 분)
    $scope._setComboData("promotionEndHhCombo", Hh); // 적용조건 - 적용요일(종료 시)
    $scope._setComboData("promotionEndMmCombo", Mm); // 적용조건 - 적용요일(종료 분)
    $scope._setComboData("memberTargetDs", memberTargetDsFgData); // 적용조건 - 적용대상
    $scope._setComboData("memberClassCd", memberClassCdFgData); // 적용조건 - 적용등급
    $scope._setComboData("selectProdDs", selectProdDsFgData); // 적용상품 - 구매대상
    $scope._setComboData("selectProdCrossFg", selectProdCrossFgData); // 적용상품 - 교차선택구분
    $scope._setComboData("typeCd", typeCdFgData); // 혜택유형
    $scope._setComboData("applyDcDs", applyDcDsData); // 할인구분
    $scope._setComboData("presentDs", presentDsFgData); // 혜택상품 - 증정구분
    $scope._setComboData("selectCrossFg", selectCrossFgData); // 혜택상품 - 교차선택구분

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

    // 적용상품 셋팅
    $scope.isCheckedProd = false;
    $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
    $("#trSelectProdDs").css("display", "none");
    $("#trSelectProdCrossFg").css("display", "none");
    $("#trSelectProdGrid").css("display", "none");

    // 매장권한에서는 적용매장 hidden
    if(orgnFg === "STORE") {
        $("#tblPromotionStore").css("display" , "none");
    }else{
        $("#tblPromotionStore").css("display" , "");
    }
    
    // 상세조회 후, 화면 이벤트 발생 시, 다시 원래 데이터로 셋팅하기 위한 임시 변수
    var vSelectProdDs = "";
    var vSelectProdCrossFg = "";
    var vSelectProdCnt = "";
    var vApplyDcDs = ""
    var vDcSet = "";
    var vPresentDs = "";
    var vSelectCrossFg = "";
    var vSelectGiftCnt = "";

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

            // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
            $("#trSelectProdGrid").css("display", "");
            $("#trSelectPresentGrid").css("display", "");

            // 매장권한인 경우, 적용매장 등록 불가
            if(orgnFg === "STORE") {
                $("#tblPromotionStore").css("display", "none");
            }else{
                // 적용매장 목록 조회
                $scope._pageView('promotionSelectStoreGridCtrl', 1);
                // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
                $("#tblPromotionStore").css("display", "");
            }

        }else{
            // 초기화
            $scope.reset();

            // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
            $("#trSelectProdGrid").css("display", "none");
            $("#tblPromotionStore").css("display", "none");
            $("#trSelectPresentGrid").css("display", "none");
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

                // ------------ 기본정보 ------------
                $("#promotionNm").val(info.promotionNm); // 프로모션명
                $("#memo").val(info.memo); // 메모
                $scope.useYnCombo.selectedValue = info.useYn; // 사용여부

                // ------------ 적용조건 ------------
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

                if(info.minSaleAmt !== "" && info.minSaleAmt != "0"){
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
                if(info.prodCdYn === "Y"){
                    $("input:checkbox[id='chkProd']").prop("checked", true); // 적용상품
                    $scope.selectProdDsCombo.selectedValue = info.selectProdDs; // 구매대상
                    $scope.isCheckedProd = true;
                    $("#trProdTop").css("border-bottom", "1px solid #EEEEEE");
                    $("#trSelectProdDs").css("display", "");
                    $("#trSelectProdGrid").css("display", "");

                    if(info.selectProdDs === "2"){
                        $scope.selectProdCrossFgCombo.selectedValue = info.selectProdCrossFg; // 교차선택구분
                        $("#selectProdCnt").val(info.selectProdCnt); // 수량
                        $("#trSelectProdCrossFg").css("display", "");
                    }else{
                        $scope.selectProdCrossFgCombo.selectedIndex = 0; // 교차선택구분
                        $("#selectProdCnt").val(""); // 수량
                        $("#trSelectProdCrossFg").css("display", "none");
                    }

                    // 상세조회 후, 화면 이벤트 발생 시, 다시 원래 데이터로 셋팅하기 위한 임시 변수
                    vSelectProdDs = info.selectProdDs;
                    vSelectProdCrossFg = info.selectProdCrossFg;
                    vSelectProdCnt = info.selectProdCnt;

                }else{
                    $("input:checkbox[id='chkProd']").prop("checked", false); // 적용상품
                    $scope.selectProdDsCombo.selectedIndex = 0; // 구매대상
                    $scope.selectProdCrossFgCombo.selectedIndex = 0; // 교차선택구분
                    $("#selectProdCnt").val(""); // 수량
                    $scope.isCheckedProd = false;
                    $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
                    $("#trSelectProdDs").css("display", "none");
                    $("#trSelectProdCrossFg").css("display", "none");
                    $("#trSelectProdGrid").css("display", "none");
                }

                // ------------ 적용혜택 ------------
                $scope.typeCdCombo.selectedValue = info.typeCd; // 혜택유형

                if(4 > info.typeCd){
                    $scope.applyDcDsCombo.selectedValue = info.applyDcDs; // 할인구분
                    $("#dcSet").val(info.dcSet) // 할인율

                    // 상세조회 후, 화면 이벤트 발생 시, 다시 원래 데이터로 셋팅하기 위한 임시 변수
                    vApplyDcDs = info.applyDcDs;
                    vDcSet = info.dcSet;
                }else{
                    $scope.applyDcDsCombo.selectedIndex = 0;
                    $("#dcSet").val("");
                }

                $("#printMessage").val(info.printMessage); // 출력문구

                // ------------ 혜택상품 ------------
                if(info.typeCd > 2){
                    $scope.presentDsCombo.selectedValue = info.presentDs; // 증정구분
                    if(info.presentDs === "2"){
                        $scope.selectCrossFgCombo.selectedValue = info.selectCrossFg; // 교차선택구분
                        $("#selectGiftCnt").val(info.selectGiftCnt); // 수량
                    }else{
                        $scope.selectCrossFgCombo.selectedIndex = 0;
                        $("#selectGiftCnt").val("");
                    }

                    // 상세조회 후, 화면 이벤트 발생 시, 다시 원래 데이터로 셋팅하기 위한 임시 변수
                    vPresentDs  = info.presentDs;
                    vSelectCrossFg  = info.selectCrossFg;
                    vSelectGiftCnt  = info.selectGiftCnt;

                }else{
                    $scope.presentDsCombo.selectedIndex = 0; // 증정구분
                    $scope.selectCrossFgCombo.selectedIndex = 0; // 교차선택구분
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


            // ------------ 적용조건 ------------
            params.dateYn = $("#chkPeriod").is(":checked") === true ? 'Y' : 'N'; // 적용기간 사용여부
            params.startYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(promotionStartDate.value, 'yyyyMMdd') : '00000101'; // 적용기간 시작일
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
            params.prodCdYn = $("#chkProd").is(":checked") === true ? 'Y' : 'N'; // 적용상품
            if ($("#chkProd").is(":checked")) {
                params.selectProdDs = $scope.selectProdDsCombo.selectedValue; // 적용상품 - 구매대상
                if ($scope.selectProdDsCombo.selectedValue === "2") {
                    params.selectProdCrossFg = $scope.selectProdCrossFgCombo.selectedValue; // 적용상품 - 교차선택구분
                    params.selectProdCnt = $("#selectProdCnt").val(); // 적용상품 - 수량
                }else{
                    params.selectProdCrossFg = "";
                    params.selectProdCnt = "";
                }
            }else{
                params.selectProdDs = "";
                params.selectProdCrossFg = "";
                params.selectProdCnt = "";
            }

            // ------------ 적용혜택 ------------
            params.typeCd = $scope.typeCdCombo.selectedValue; // 혜택유형
            if (4 > $scope.typeCdCombo.selectedValue) {
                params.applyDcDs = $scope.applyDcDsCombo.selectedValue; // 할인구분
                params.dcSet = $("#dcSet").val(); // 할인율
            }else{
                params.applyDcDs = "";
                params.dcSet = "";
            }
            params.printMessage = $("#printMessage").val(); // 출력문구


            // ------------ 혜택상품 ------------
            if ($scope.typeCdCombo.selectedValue > 2) {
                params.presentDs = $scope.presentDsCombo.selectedValue; // 증정구분
                if ($scope.presentDsCombo.selectedValue === "2") {
                    params.selectCrossFg = $scope.selectCrossFgCombo.selectedValue; // 혜택상품 교차선택구분
                    params.selectGiftCnt = $("#selectGiftCnt").val(); // 혜택상품 수량
                }else{
                    params.selectCrossFg = "";
                    params.selectGiftCnt = "";
                }
            }else{
                params.presentDs = "";
                params.selectCrossFg = "";
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
        if($("#chkProd").is(":checked")){
            if($scope.selectProdDsCombo.selectedValue === "2"){
                if($("#selectProdCnt").val() === "" || $("#selectProdCnt").val() === null){
                    if($scope.selectProdCrossFgCombo.selectedValue === "1"){
                        $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossNCnt"] + messages["cmm.require.text"]);
                    }else{
                        $scope._popMsg(messages["promotion.prod"] + "의 " + messages["promotion.selectProdCrossYCnt"] + messages["cmm.require.text"]);
                    }
                    return false;
                }
            }
        }


        if(4 > $scope.typeCdCombo.selectedValue){
            // 적용혜택의 할인율을(를) 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === ""){
                $scope._popMsg(messages["promotion.bene"] + "의 " + messages["promotion.dcSet"] +  messages["cmm.require.text"]);
                return false;
            }
            // 정률할인의 할인율은 0~100 사이의 숫자만 입력하세요.
            if($scope.applyDcDsCombo.selectedValue === "1") {
                if (0 > $("#dcSet").val() || $("#dcSet").val() > 100) {
                    $scope._popMsg(messages["promotion.chk.dcSet"]);
                    return false;
                }
            }
            // 정액할인은 1원단위를 입력할 수 없습니다.
            if($scope.applyDcDsCombo.selectedValue === "2") {
                if(Number($("#dcSet").val()) % 10 > 0){
                    $scope._popMsg(messages["promotion.chk.dcSetAmt"]);
                    return false;
                }
            }
        }

        // 혜택상품의 상품수/수량을(를) 입력하세요.
        if($scope.typeCdCombo.selectedValue > 2){
            if ($scope.presentDsCombo.selectedValue === "2") {
                if ($("#selectGiftCnt").val() === "" || $("#selectGiftCnt").val() === null) {
                    if($scope.selectCrossFgCombo.selectedValue === "1"){
                        $scope._popMsg(messages["promotion.present"] + "의 " + messages["promotion.selectGiftNCnt"] + messages["cmm.require.text"]);
                    }else{
                        $scope._popMsg(messages["promotion.present"] + "의 " + messages["promotion.selectGiftYCnt"] + messages["cmm.require.text"]);
                    }
                    return false;
                }
            }
        }

        return true;
    }

    // 초기화
    $scope.reset = function(){

        $("#hdPromotionCd").val("");

        // ------------ 권한, 프로모션기간, 환경설정값 에 따른 hidden 처리 ------------
        $("#btnSave").css("display", "");

        // ------------ 기본정보 ------------
        $("#promotionNm").val(""); // 프로모션명
        $("#memo").val(""); // 메모
        $scope.useYnCombo.selectedIndex = 0; // 사용여부


        // ------------ 적용조건 ------------
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
        $("input:checkbox[id='chkProd']").prop("checked", false); // 적용상품
        $scope.selectProdDsCombo.selectedIndex = 0; // 구매대상
        $scope.selectProdCrossFgCombo.selectedValue = 0; // 교차선택구분
        $("#selectProdCnt").val(""); // 수량
        $scope.isCheckedProd = false;
        $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
        $("#trSelectProdDs").css("display", "none");
        $("#trSelectProdCrossFg").css("display", "none");
        $("#trSelectProdGrid").css("display", "none");


        // ------------ 적용혜택 ------------
        $scope.typeCdCombo.selectedIndex = 0; // 혜택유형
        $scope.applyDcDsCombo.selectedIndex = 0; // 할인구분
        $("#dcSet").val(""); // 할인율
        $("#printMessage").val(""); // 출력문구


        // ------------ 혜택상품 ------------
        $scope.presentDsCombo.selectedIndex = 0; // 증정구분
        $scope.selectCrossFgCombo.selectedIndex = 0; // 교차선택구분
        $("#selectGiftCnt").val(""); // 수량

        // ------------ 상세조회 임시변수 ------------
        vSelectProdDs = "";
        vSelectProdCrossFg = "";
        vSelectProdCnt = "";
        vApplyDcDs = ""
        vDcSet = "";
        vPresentDs = "";
        vSelectCrossFg = "";
        vSelectGiftCnt = "";

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

    // 적용상품 - 구매대상 선택에 따른 교차선택구분, 수량 disabled 여부
    $scope.setSelectProdCrossFg = function (s) {
        if(s.selectedValue === "2"){
            $("#trSelectProdCrossFg").css("display", "");
        }else{
            $("#trSelectProdCrossFg").css("display", "none");
        }
    };

    // 적용상품 - 교차선택구분 선택에 따른 selectProdCnt 명칭(상품수, 수량) 변경
    $scope.setSelectProdCnt = function (s){
        if(s.selectedValue === "1"){
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossNCnt"]);
        }else{
            $("#lblSelectProdCnt").text(messages["promotion.selectProdCrossYCnt"]);
        }
    };
    
    // 적용혜택 - 혜택유형 선택에 따른 할인구분, 할인율 disabled 여부
    $scope.setApplyDcDs = function (s) {

        if(s.selectedValue === "4") {
            $("#trApplyDcDs").css("display", "none");
            $("#tblBene").css("display", "");

            if (vApplyDcDs !== "") { // 상세조회인 경우 기존값 재셋팅
                $scope.applyDcDsCombo.selectedValue = vApplyDcDs;
                $("#dcSet").val(vDcSet);
            }else{
                $scope.applyDcDsCombo.selectedIndex = 0;
                $("#dcSet").val("");
            }

        }else if (s.selectedValue === "3"){
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "");
        }else{
            $("#trApplyDcDs").css("display", "");
            $("#tblBene").css("display", "none");

            if (vPresentDs !== "") { // 상세조회인 경우 기존값 재셋팅
                $scope.presentDsCombo.selectedValue = vPresentDs;
                if (vPresentDs === "2") {
                    $scope.selectCrossFgCombo.selectedValue = vSelectCrossFg;
                    $("#selectGiftCnt").val(vSelectGiftCnt);
                    $("#trSelectCrossFg").css("display", "");
                } else {
                    $scope.selectCrossFgCombo.selectedIndex = 0;
                    $("#selectGiftCnt").val("");
                    $("#trSelectCrossFg").css("display", "none");
                }
            }else{
                $scope.presentDsCombo.selectedIndex = 0;
                $scope.selectCrossFgCombo.selectedIndex = 0;
                $("#selectGiftCnt").val("");
            }
        }

        if(s.selectedValue > 2) {
            // 신규 등록 시, 혜택상품 리스트 및 선택 불가(등록 후 가능)
            if($("#hdPromotionCd").val() !== "") {
                $("#trSelectPresentGrid").css("display", "");

                // 혜택상품 목록 재조회(grid header가 안나오는 경우가 있어서 체크되면 다시 조회)
                $scope._pageView('promotionSelectPresentGridCtrl', 1);

            }else{
                $("#trSelectPresentGrid").css("display", "none");
            }
        }
    };
    
    // 혜택상품 - 증정구분 선택에 따른 교차선택구분, 수량 disabled 여부
    $scope.setSelectCrossFg = function (s) {
        if(s.selectedValue === "2"){
            $("#trSelectCrossFg").css("display", "");
        }else{
            if(vSelectCrossFg !== ""){ // 상세조회인 경우 기존값 재셋팅
                $scope.selectCrossFgCombo.selectedValue = vSelectCrossFg;
                $("#selectGiftCnt").val(vSelectGiftCnt);
            }else{
                $scope.selectCrossFgCombo.selectedIndex = 0;
                $("#selectGiftCnt").val("");
            }

            $("#trSelectCrossFg").css("display", "none");
        }
    };

    // 혜택상품 - 교차선택구분 선택에 따른 selectProdCnt 명칭(상품수, 수량) 변경
    $scope.setSelectGiftCnt = function (s){
        if(s.selectedValue === "1"){
            $("#lblSelectGiftCnt").text(messages["promotion.selectGiftNCnt"]);
        }else{
            $("#lblSelectGiftCnt").text(messages["promotion.selectGiftYCnt"]);
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
    
    // 적용상품 입력 사용/미사용 체크박스
    $scope.isChkProd = function () {
        if($scope.isCheckedProd){
            $("#trProdTop").css("border-bottom", "1px solid #EEEEEE");
            $("#trSelectProdDs").css("display", "");

            if(vSelectProdDs !== "") { // 상세조회인 경우 기존값 재셋팅
                $scope.selectProdDsCombo.selectedValue = vSelectProdDs;
                if (vSelectProdDs === "2") {
                    $scope.selectProdCrossFgCombo.selectedValue = vSelectProdCrossFg;
                    $("#selectProdCnt").val(vSelectProdCnt);
                    $("#trSelectProdCrossFg").css("display", "");
                } else {
                    $scope.selectProdCrossFgCombo.selectedIndex = 0;
                    $("#selectProdCnt").val("");
                    $("#trSelectProdCrossFg").css("display", "none");
                }
            }else{
                $scope.selectProdDsCombo.selectedIndex = 0;
                $scope.selectProdCrossFgCombo.selectedIndex = 0;
                $("#selectProdCnt").val("");
                $("#trSelectProdCrossFg").css("display", "none");
            }

            // 신규 등록 시, 적용 상품 리스트 및 선택 불가(등록 후 가능)
            if($("#hdPromotionCd").val() === "") {
                $("#trSelectProdGrid").css("display", "none");
            }else{
                $("#trSelectProdGrid").css("display", "");

                // 적용상품 목록 재조회(grid header가 안나오는 경우가 있어서 체크되면 다시 조회)
                $scope._pageView('promotionSelectProdGridCtrl', 1);
            }
            
        }else{
            $("#trProdTop").css("border-bottom", "1px solid #CCCCCC");
            $("#trSelectProdDs").css("display", "none");
            $("#trSelectProdCrossFg").css("display", "none");
            $("#trSelectProdGrid").css("display", "none");

            // 초기화
            $scope.selectProdDsCombo.selectedIndex = 0;
            $scope.selectProdCrossFgCombo.selectedIndex = 0;
            $("#selectProdCnt").val("");
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
        $scope._broadcast('promotionProdRegCtrl');
    }

    // 적용분류 추가 팝업
    $scope.classAdd = function () {
        $scope.promotionClassRegLayer.show(true);
        $scope._broadcast('promotionClassRegCtrl');
    };

    // 적용상품 저장
    $scope.prodSave = function () {

        // 파라미터 설정
        var params = new Array();

        // 조건수량이 수정된 내역이 있는지 체크
        if ($scope.flexSelectProdGrid.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
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

            if (item.gChk === true && (item.prodQty === null || item.prodQty === "" || item.prodQty === "0" )) {
                $scope._popMsg(messages["promotion.chk.prodQty"]); // 선택한 상품의 조건수량을 반드시 입력하세요.
                return false;
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.condiProdSeq = item.condiProdSeq;
                obj.prodQty =  item.prodQty;

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
