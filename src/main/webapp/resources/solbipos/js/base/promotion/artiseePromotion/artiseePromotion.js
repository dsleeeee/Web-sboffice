/****************************************************************
 *
 * 파일명 : artiseePromotion.js
 * 설  명 : 아티제전용프로모션 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.13     이다솜      1.0
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

// 프로모션적용업체구분
var promoCompFgData = [
    {"name":"PCMK(카드사)","value":"1"},
    {"name":"SKT","value":"2"}
];

// 프로모션타입구분
var promoTypeFgData = [
    {"name":"1+1","value":"1"},
    {"name":"전체%할인","value":"2"},
    {"name":"전체금액할인","value":"3"},
    {"name":"특정상품%할인","value":"4"},
    {"name":"특정상품-금액할인","value":"5"}
];

// 적용대상
var prodTypeFgData = [
    {"name":"상품","value":"1"},
    {"name":"분류","value":"2"}
];

/**
 * 아티제전용프로모션 그리드 생성
 */
app.controller('artiseePromotionCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseePromotionCtrl', $scope, $http, $timeout, true));

    // 행사일 달력 셋팅
    $scope.promotionDate = wcombo.genDateVal("#promotionDate", gvStartDate);
    $scope.promotionDate.isReadOnly = true;

    // 사용여부 조회조건 콤보박스 데이터 Set
    $scope._setComboData("useYnAll", useYnAllFgData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부
        $scope.promoCompFgDataMap = new wijmo.grid.DataMap(promoCompFgData, 'value', 'name'); //프로모션적용업체구분
        $scope.promoTypeFgDataMap = new wijmo.grid.DataMap(promoTypeFgData, 'value', 'name'); //프로모션타입구분

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
                    //params.hqOfficeCd = selectedRow.hqOfficeCd;
                    params.promotionCd = selectedRow.promotionCd;
                    params.prodTypeFg = selectedRow.prodTypeFg;
                    $scope._broadcast('artiseePromotionRegCtrl', params);
                }
            }
        });
    };

    //
    $scope.$on("artiseePromotionCtrl", function(event, data) {

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

        $scope._inquirySub("/base/promotion/artiseePromotion/list.sb", params, function () {});
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
        $scope._broadcast('artiseePromotionRegCtrl');
    };

}]);

/**
 * 아티제전용프로모션 등록
 */
app.controller('artiseePromotionRegCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseePromotionRegCtrl', $scope, $http, $timeout, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("useYn", useYnFgData);           // 사용여부
    $scope._setComboData("promoCompFg", promoCompFgData); // 프로모션적용업체구분
    $scope._setComboData("promoTypeFg", promoTypeFgData); // 프로모션타입구분
    $scope._setComboData("prodTypeFg", prodTypeFgData);   // 적용대상

    // 적용기간 셋팅
    $scope.isCheckedPeriod = true; // 일단은 체크박스 히든처리하고 적용기간 무조건 입력(2024.06.20)
    var promotionStartDate = wcombo.genDateVal("#promotionStartDate", gvStartDate);
    var promotionEndDate = wcombo.genDateVal("#promotionEndDate", gvEndDate);

    // 매장권한에서는 적용매장 hidden
    if(orgnFg === "STORE") {
        $("#tblPromotionStore").css("display" , "none");
    }else{
        $("#tblPromotionStore").css("display" , "");
    }

    $scope.$on("artiseePromotionRegCtrl", function(event, data) {

        // 등록 영역 Open
        $("#promotionReg").css("display", "");

        if(!isEmptyObject(data)) {

            // 기존 프로모션 상세정보 셋팅
            $scope.setPromotionDetail(data);

            // 적용상품 목록 조회
            $scope._pageView('artiseePromotionSelectProdGridCtrl', 1);

            // 매장권한인 경우, 적용매장 조회 불가
            if(orgnFg === "STORE") {
                $("#tblPromotionStore").css("display", "none");
            }else{
                // 적용매장 목록 조회
                $scope._pageView('artiseePromotionSelectStoreGridCtrl', 1);
                // 신규 등록 시, 적용 매장 리스트 및 선택 불가(등록 후 가능)
                $("#tblPromotionStore").css("display", "");
            }

            // 상세등록영역 보이기
            $("#divDetailReg").css("display", "");

        }else{

            // 초기화
            $scope.reset();

            // 상세등록영역 숨기기(저장 후 오픈됨)
            $("#tblProd").css("display", "none");
            $("#tblPromotionStore").css("display", "none");
            $("#divDetailReg").css("display", "none");
        }
    });

    // 기존 프로모션 상세정보 셋팅
    $scope.setPromotionDetail = function(data){

        // hidden에 promotionCd 값 가지고 있기(상품, 매장, 혜택상품 리스트 저장 시 사용)
        $("#hdPromotionCd").val(data.promotionCd);
        // 적용대상(적용대상 콤보박스 변경하더라도 기존 저장값 확인을 위해)
        $("#hdProdTypeFg").val(data.prodTypeFg);

        var params = {};
        params.promotionCd = data.promotionCd;

        $.postJSON("/base/promotion/artiseePromotion/detail.sb", params, function(result) {

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
                $scope.setButtonVisible("N");
                
                // 매장 프로모션 신규등록 불가
                $("#btnRegist").css("display", "none");

            }else{ // 본사권한 일 때
                if(modPromotionEnvstVal === "1"){ // 본사 또는 매장의 환경변수(진행중인프로모션수정여부 - 1097)이 '수정가능'인 경우 버튼 보임
                    $scope.setButtonVisible("Y");

                    // 적용대상 선택값에 따른, 적용상품 or 적용분류의 상품추가/분류추가 버튼 셋팅
                    if(info.prodTypeFg === "1"){
                        $("#btnProdAdd").css("display", "");
                        $("#btnClassAdd").css("display", "none");
                    }else if(info.prodTypeFg === "2"){
                        $("#btnProdAdd").css("display", "none");
                        $("#btnClassAdd").css("display", "");
                    }else{
                        $("#btnProdAdd").css("display", "none");
                        $("#btnClassAdd").css("display", "none");
                    }

                }else{
                    if(info.startYmd !== "00010101" && info.endYmd !== "99991231"){ // 프로모션 기간이 있는 경우, 오늘날짜가 프로모션 시작날짜보다 크거나 같으면 일부 버튼 숨김
                        if(Number(now) >= Number(info.startYmd)){
                            $scope.setButtonVisible("N");
                        }else{
                            $scope.setButtonVisible("Y");

                            // 적용대상 선택값에 따른, 적용상품 or 적용분류의 상품추가/분류추가 버튼 셋팅
                            if(info.prodTypeFg === "1"){
                                $("#btnProdAdd").css("display", "");
                                $("#btnClassAdd").css("display", "none");
                            }else if(info.prodTypeFg === "2"){
                                $("#btnProdAdd").css("display", "none");
                                $("#btnClassAdd").css("display", "");
                            }else{
                                $("#btnProdAdd").css("display", "none");
                                $("#btnClassAdd").css("display", "none");
                            }
                        }
                    }else{ // 프로모션 기간이 없는 경우, 오늘날짜가 프로모션 등록날짜보다 크면 일부 버튼 숨김
                        if(Number(now) > Number(info.regDt)) {
                            $scope.setButtonVisible("N");
                        }else{
                            $scope.setButtonVisible("Y");

                            // 적용대상 선택값에 따른, 적용상품 or 적용분류의 상품추가/분류추가 버튼 셋팅
                            if(info.prodTypeFg === "1"){
                                $("#btnProdAdd").css("display", "");
                                $("#btnClassAdd").css("display", "none");
                            }else if(info.prodTypeFg === "2"){
                                $("#btnProdAdd").css("display", "none");
                                $("#btnClassAdd").css("display", "");
                            }else{
                                $("#btnProdAdd").css("display", "none");
                                $("#btnClassAdd").css("display", "none");
                            }
                        }
                    }
                }
                
                // 본사 프로모션 신규등록 가능
                $("#btnRegist").css("display", "");
            }

            // ------------ 프로모션정보 ------------
            $("#promotionNm").val(info.promotionNm); // 프로모션명
            $("#memo").val(info.memo); // 메모
            $scope.promoCompFgCombo.selectedValue = info.promoCompFg; // 프로모션적용업체구분
            $scope.useYnCombo.selectedValue = info.useYn; // 사용여부
            $scope.promoTypeFgCombo.selectedValue = info.promoTypeFg; // 프로모션타입구분

            if(info.promoTypeFg !== "1"){
                $("#dcSet").val(info.dcSet); // 할인율 or 할인금액
            }else{
                $("#dcSet").val("");
            }

            if(info.startYmd !== "00010101" && info.endYmd !== "99991231"){
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

            // 프로모션타입구분 선택값에 따른, 적용대상 콤보박스 show/hidden
            // 프로모션타입구분 - 2. 전체%할인, 3. 전체금액할인 인 경우 hidden
            if(info.promoTypeFg === "2" || info.promoTypeFg === "3"){
                $scope.prodTypeFgCombo.selectedIndex = 0;
            }else{
                $scope.prodTypeFgCombo.selectedValue = info.prodTypeFg; // 적용대상
            }

            // 적용대상 선택값에 따른, 적용상품 or 적용분류 리스트 제목 셋팅
            if(info.prodTypeFg === "1"){
                $("#lblProd").text(messages["artiseePromotion.prod"]);
            }else{
                $("#lblProd").text(messages["artiseePromotion.prodClass"]);
            }

            // 프로모션타입구분 선택값에 따른, 적용상품 상품추가/분류추가 show/hidden
            // 프로모션타입구분 - 2. 전체%할인, 3. 전체금액할인 인 경우 hidden
            if(info.promoTypeFg === "2" || info.promoTypeFg === "3"){
                $("#tblProd").css("display", "none");
            }else{
                $("#tblProd").css("display", "");
            }

        },
        function (result) {
            s_alert.pop(result.message);
            return false;
        });
    };

    // 프로모션 등록/수정
    $scope.savePromotion = function(){

        if($scope.chkValid()) {

            var params = {};
            params.promotionCd = $("#hdPromotionCd").val();
            params.promotionNm = $("#promotionNm").val(); // 프로모션명
            params.memo = $("#memo").val(); // 메모
            params.promoCompFg = $scope.promoCompFgCombo.selectedValue; // 프로모션적용업체구분
            params.useYn = $scope.useYnCombo.selectedValue; // 사용여부
            params.promoTypeFg = $scope.promoTypeFgCombo.selectedValue; //프로모션타입구분
            params.dcSet = params.promoTypeFg === "1" ? "" :  $("#dcSet").val(); // 할인율 or 할인금액
            params.startYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(promotionStartDate.value, 'yyyyMMdd') : '00010101'; // 적용기간 시작일
            params.endYmd = $("#chkPeriod").is(":checked") === true ? wijmo.Globalize.format(promotionEndDate.value, 'yyyyMMdd') : '99991231'; // 적용기간 종료일

            if(params.promoTypeFg === "2" || params.promoTypeFg === "3"){
                params.prodTypeFg = "";
            }else{
                params.prodTypeFg = $scope.prodTypeFgCombo.selectedValue; // 적용대상
            }
            
            // 수정 시, 적용대상 변경여부 파악
            params.prodTypeFgChgYn = $("#hdProdTypeFg").val() !== params.prodTypeFg ? "Y" : "N"; // 적용대상 변경여부
            
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $.postJSONArray("/base/promotion/artiseePromotion/save.sb", params, function (result) {
                if (result.status === "OK") {
                    $scope.$broadcast('loadingPopupInactive');

                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);

                    // 입력 폼 초기화
                    $scope.reset();

                    // 리스트 재조회
                    $scope._broadcast('artiseePromotionCtrl');

                    // 상세 재조회
                    var params2    = {};
                    // params2.hqOfficeCd = hqOfficeCd;
                    params2.promotionCd = result.data;       // 정상적으로 등록 및 수정 시, 프로모션코드가 반환됨
                    params2.prodTypeFg = params.prodTypeFg;  // 적용분류 리스트 재조회를 위해 적용대상 선택값 셋팅
                    $scope._broadcast('artiseePromotionRegCtrl', params2);

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
            $scope._popMsg(messages["artiseePromotion.promotionNm"] +  messages["cmm.require.text"]);
            return false;
        }

        if($scope.promoTypeFgCombo.selectedValue !== "1"){

            // 할인율/할인금액을(를) 입력하세요.
            if($("#dcSet").val() === "" || $("#dcSet").val() === null){

                if($scope.promoTypeFgCombo.selectedValue === "2" || $scope.promoTypeFgCombo.selectedValue === "4") {
                    $scope._popMsg( messages["artiseePromotion.dcSet"] + messages["cmm.require.text"]);
                } else {
                    $scope._popMsg( messages["artiseePromotion.dcSetAmt"] + messages["cmm.require.text"]);
                }
                return false;
            }

            // 할인율은 0~100 사이의 숫자만 입력하세요.
            if($scope.promoTypeFgCombo.selectedValue === "2" || $scope.promoTypeFgCombo.selectedValue === "4") {
                if (0 > $("#dcSet").val() || $("#dcSet").val() > 100) {
                    $scope._popMsg(messages["artiseePromotion.chk.dcSet"]);
                    return false;
                }
            }

            // 할인금액은(는) 1원단위를 입력할 수 없습니다.
            if($scope.promoTypeFgCombo.selectedValue === "3" || $scope.promoTypeFgCombo.selectedValue === "5") {
                if(Number($("#dcSet").val()) % 10 > 0){
                    $scope._popMsg(messages["artiseePromotion.dcSetAmt"] + messages["artiseePromotion.chk.dcSetAmt"]);
                    return false;
                }
            }
        }

        // 적용기간 종료일자가 시작일자보다 빠릅니다.
        if($("#chkPeriod").is(":checked")) {
            if(wijmo.Globalize.format(promotionStartDate.value, 'yyyyMMdd') > wijmo.Globalize.format(promotionEndDate.value, 'yyyyMMdd')){
                $scope._popMsg(messages["artiseePromotion.periodChk"]);
                return false;
            }
        }

        return true;
    };

    // 초기화
    $scope.reset = function() {

        $("#hdPromotionCd").val(""); // 프로모션코드
        $("#hdProdTypeFg").val("");  // 적용대상(적용대상 콤보박스 변경하더라도 기존 저장값 확인을 위해)

        // ------------ 권한, 프로모션기간, 환경설정값 에 따른 hidden 처리 ------------
        $("#btnSave").css("display", ""); // 저장버튼

        // ------------ 프로모션정보 ------------
        $("#promotionNm").val(""); // 프로모션명
        $("#memo").val(""); // 메모
        $scope.promoCompFgCombo.selectedIndex = 0; // 프로모션적용업체구분
        $scope.useYnCombo.selectedIndex = 0; // 사용여부
        $scope.promoTypeFgCombo.selectedIndex = 0; // 프로모션타입구분
        $("#dcSet").val(""); // 할인율 or 할인금액
        
        /*$("input:checkbox[id='chkPeriod']").prop("checked", false); // 적용기간
        promotionStartDate.value = getCurDate('-');
        promotionEndDate.value = getCurDate('-');
        $scope.isCheckedPeriod = false;
        $("#divChkPeriod").css("display", "none");*/
        // 일단은 체크박스 히든처리하고 적용기간 무조건 입력(2024.06.20)
        $("input:checkbox[id='chkPeriod']").prop("checked", true); // 적용기간
        promotionStartDate.value = getCurDate('-');
        promotionEndDate.value = getCurDate('-');
        $scope.isCheckedPeriod = true;
        $("#divChkPeriod").css("display", "");
        
        $scope.prodTypeFgCombo.selectedIndex = 0; // 적용대상

        // ------------ 그리드 초기화 ------------
        var wjGridSelectProd = wijmo.Control.getControl("#wjGridSelectProd"); // 적용상품
        while(wjGridSelectProd.rows.length > 0){
            wjGridSelectProd.rows.removeAt(wjGridSelectProd.rows.length-1);
        }

        var wjGridSelectStore = wijmo.Control.getControl("#wjGridSelectStore"); // 적용매장
        while(wjGridSelectStore.rows.length > 0){
            wjGridSelectStore.rows.removeAt(wjGridSelectStore.rows.length-1);
        }

        // 상세등록영역 숨기기(저장 후 오픈됨)
        $("#tblProd").css("display", "none");
        $("#tblPromotionStore").css("display", "none");
        $("#divDetailReg").css("display", "none");
        
    };

    // 권한, 프로모션기간, 환경설정값에 따른 버튼 Visible 설정
    $scope.setButtonVisible = function(type){

        if(type === "N"){ // 일부 버튼 hidden 처리

            // 저장 버튼
            $("#btnSave").css("display", "none");

            // 적용상품/분류 grid 버튼
            $("#btnProdAdd").css("display", "none");
            $("#btnClassAdd").css("display", "none");
            $("#btnProdSave").css("display", "none");
            $("#btnProdDel").css("display", "none");

            // 적용매장 grid 버튼
            $("#btnStoreExcelDown").css("display", "none");
            $("#btnStoreAdd").css("display", "none");
            $("#btnStoreDel").css("display", "none");

        }else{ // 모든 버튼 보이게 처리

            // 저장 버튼
            $("#btnSave").css("display", "");

            // 적용상품/분류 grid 버튼
            $("#btnProdAdd").css("display", "");
            $("#btnClassAdd").css("display", "");
            $("#btnProdSave").css("display", "");
            $("#btnProdDel").css("display", "");

            // 적용매장 grid 버튼
            $("#btnStoreExcelDown").css("display", "");
            $("#btnStoreAdd").css("display", "");
            $("#btnStoreDel").css("display", "");
        }
    };

    //  프로모션타입구분 선택에 따른 dcSet 명칭(할인율, 할인금액) 변경, 적용대상 변경
    $scope.setDcSet = function (s) {
        if(s.selectedValue === "1"){
            $("#lblDcSet").text("");
            $("#dcSet").css("display", "none");
            $("#lblProdTypeFg").text(messages["artiseePromotion.prodTypeFg"]);
            $("#divProdTypeFg").css("display", "");
        }else if(s.selectedValue === "2"){
            $("#lblDcSet").text(messages["artiseePromotion.dcSet"]);
            $("#dcSet").css("display", "");
            $("#lblProdTypeFg").text("");
            $("#divProdTypeFg").css("display", "none");
        }else if(s.selectedValue === "3"){
            $("#lblDcSet").text(messages["artiseePromotion.dcSetAmt"]);
            $("#dcSet").css("display", "");
            $("#lblProdTypeFg").text("");
            $("#divProdTypeFg").css("display", "none");
        }else if(s.selectedValue === "4"){
            $("#lblDcSet").text(messages["artiseePromotion.dcSet"]);
            $("#dcSet").css("display", "");
            $("#lblProdTypeFg").text(messages["artiseePromotion.prodTypeFg"]);
            $("#divProdTypeFg").css("display", "");
        }else if(s.selectedValue === "5"){
            $("#lblDcSet").text(messages["artiseePromotion.dcSetAmt"]);
            $("#dcSet").css("display", "");
            $("#lblProdTypeFg").text(messages["artiseePromotion.prodTypeFg"]);
            $("#divProdTypeFg").css("display", "");
        }
    };

    // 적용기간 입력 사용/미사용 체크박스
    $scope.isChkPeriod = function () {
        if($scope.isCheckedPeriod){
            $("#divChkPeriod").css("display", "");
        }else{
            $("#divChkPeriod").css("display", "none");
        }
    };


}]);

/**
 * 아티제전용프로모션 적용상품 그리드
 */
app.controller('artiseePromotionSelectProdGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('artiseePromotionSelectProdGridCtrl', $scope, $http, $timeout, true));

    $scope.$on("artiseePromotionSelectProdGridCtrl", function(event, data) {

        // 적용상품/분류 목록 조회
        $scope.getPromotionProdList();
        event.preventDefault();
    });

    // 적용상품/분류 목록 조회
    $scope.getPromotionProdList = function(){

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.prodTypeFg = $("#hdProdTypeFg").val(); // 적용대상

        // 적용대상 값이 있는경우만 목록 조회
        if(params.prodTypeFg !== "" && params.prodTypeFg !== null && params.prodTypeFg !== undefined){
            $scope._inquirySub("/base/promotion/artiseePromotion/getPromotionProdList.sb", params, function () {

            });
        }
    };

    // 적용상품 추가 팝업
    $scope.prodAdd = function () {
        $scope.artiseePromotionProdRegLayer.show(true);
        $scope._broadcast('artiseePromotionProdRegCtrl');
    };

    // 적용분류 추가 팝업
    $scope.classAdd = function () {
        $scope.artiseePromotionClassRegLayer.show(true);
        $scope._broadcast('artiseePromotionClassRegCtrl');
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
            $scope._popMsg("저장할 " + $("#lblProd").text() + "의 체크박스" + messages["artiseePromotion.chk.item"]); // 저장할 적용상품/적용분류의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flexSelectProdGrid.collectionView.itemsEdited.length; i++) {

            var item = $scope.flexSelectProdGrid.collectionView.itemsEdited[i];

            if (item.gChk === true && (item.giftQty === null || item.giftQty === "" || item.giftQty === "0" || item.giftQty === 0)) {
                $scope._popMsg(messages["artiseePromotion.chk.giftQty"]); // 선택한 항목의 조건수량을 반드시 입력하세요.
                return false;
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "U";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;
                obj.giftQty = item.giftQty;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/artiseePromotion/savePromotionProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.saveSucc"]); // 저장 되었습니다.

                // 적용상품/분류 목록 재조회
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
            $scope._popMsg("삭제할 " + $("#lblProd").text() + "의 체크박스" + messages["artiseePromotion.chk.item"]); //  삭제할 적용상품/적용분류의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i=0; i< $scope.flexSelectProdGrid.collectionView.items.length; i++) {

            var item =  $scope.flexSelectProdGrid.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "D";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;

                params.push(obj);
            }
        }

        $.postJSONArray("/base/promotion/artiseePromotion/savePromotionProd.sb", params, function (result) {
            if (result.status === "OK") {
                $scope._popMsg(messages["cmm.delSucc"]); // 삭제 되었습니다.

                // 적용상품/분류 목록 재조회
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
}]);

/**
 * 아티제전용프로모션 적용매장 그리드
 */
app.controller('artiseePromotionSelectStoreGridCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
    
    // 상위 객체 상속 : T/F 는 picker
   angular.extend(this, new RootController('artiseePromotionSelectStoreGridCtrl', $scope, $http, $timeout, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("artiseePromotionSelectStoreGridCtrl", function(event, data) {

        // 적용매장 목록 조회
        $scope.getPromotionStoreList();
        event.preventDefault();
    });

    // 적용매장 목록 조회
    $scope.getPromotionStoreList = function(){

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();

        $scope._inquirySub("/base/promotion/artiseePromotion/getPromotionStoreList.sb", params, function () {});

    };

    // 적용매장 추가 팝업
    $scope.storeAdd = function () {
        $scope.artiseePromotionStoreRegLayer.show(true);
        $scope._broadcast('artiseePromotionStoreRegCtrl');
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
            $scope._popMsg("삭제할 " + messages["artiseePromotion.store"] + "의 체크박스" + messages["artiseePromotion.chk.item"]); // 삭제할 매장의 체크박스을(를) 선택하세요.
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

        $.postJSONArray("/base/promotion/artiseePromotion/savePromotionStore.sb", params, function (result) {
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
            }, '아티제전용프로모션[' + $("#promotionNm").val() + ']_적용매장_' + getToday() + '.xlsx', function () {
                $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    // 체크박스 컬럼 다시 보이게 처리
                    $scope.flexSelectStoreGrid.columns[0].visible = true;
                }, 10);
            });
        }, 10);
    };
}]);

