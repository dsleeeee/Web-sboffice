/****************************************************************
 *
 * 파일명 : prodExcelUpload.js
 * 설  명 : 상품엑셀업로드 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.09     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 보증금상품유형
var depositCupFgData = [
    {"name": "선택", "value": ""},
    // {"name": "일반", "value": "0"},
    {"name": "종이", "value": "1"},
    {"name": "플라스틱", "value": "2"},
    {"name": "다회용", "value": "3"},
    {"name": "보증컵기타", "value": "4"}
];

// 부가세포함여부
var vatIncldYnDataMap2 = [
    {"name":"별도","value":"N"},
    {"name":"포함","value":"Y"}
];

// 식권구분
var ticketFgComboData = [
    {"name": "NO", "value": "N"},
    {"name": "YES", "value": "Y"}
];

// 매입VAT 포함여부
var acquireVatComboData = [
    {"name": "포함", "value": "Y"},
    {"name": "별도", "value": "N"}
];

// ISBN 구분 데이터
var isbnComboData = [
    {"name": "ISBN", "value": "ISBN"},
    {"name": "ISSN", "value": "ISSN"}
];

// 특정관리 선택
var spcManageComboData = [
    {"name": "안함", "value": "N"},
    {"name": "미정", "value": ""},
    {"name": "미정", "value": ""}
];

// 상품등록구분 데이터
var orgProdFgComboData = [
    {"name": "", "value": "00"},
    {"name": "일반상품", "value": "10"},
    {"name": "도서상품", "value": "20"},
    {"name": "OMS", "value": "30"}
];

/**
 *  상품목록 샘플양식 조회 그리드 생성
 */
app.controller('prodExcelUploadCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodExcelUploadCtrl', $scope, $http, true));

    // 상품코드 채번방식
    $scope.prodNoEnvFg = prodNoEnvFg;

    $("#divProdExcelUpload").css("display", "none");
    $("#divProdExcelUploadAuth").css("display", "block");
    // 단독매장
    if(hqOfficeCd == "00000") {
        $("#divProdExcelUpload").css("display", "block");
        $("#divProdExcelUploadAuth").css("display", "none");
        // 프랜 본사,매장
    } else {
        if((prodAuthEnvstVal== "ALL") || (orgnFg === 'HQ' && prodAuthEnvstVal== "HQ") || (orgnFg === 'STORE' && prodAuthEnvstVal== "STORE")) {
            $("#divProdExcelUpload").css("display", "block");
            $("#divProdExcelUploadAuth").css("display", "none");
        }
    }
    if(prodAuthEnvstVal === 'ALL') {
        $("#lblProdExcelUploadAuth").text("'본사/매장생성'");
    } else if(prodAuthEnvstVal === 'HQ') {
        $("#lblProdExcelUploadAuth").text("'본사생성'");
    } else if(prodAuthEnvstVal === 'STORE') {
        $("#lblProdExcelUploadAuth").text("'매장생성'");
    }

    // 커스텀콤보
    // $scope._getComboDataQueryCustom('getSideMenuSdselGrpCdCombo', 'sdselGrpCdComboData', 'S');
    // 발주단위구분 콤보박스와 data-map
    // $scope._getComboDataQueryByAuth('093', '', 'poUnitFgDataMap');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 발주단위구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부
        $scope.vatIncldYnDataMap = new wijmo.grid.DataMap(vatIncldYnDataMap2, 'value', 'name'); // 부가세포함여부
        $scope.stockProdYnDataMap = new wijmo.grid.DataMap(stockProdYnData, 'value', 'name'); // 재고관리여부
        $scope.vendrCdDataMap = new wijmo.grid.DataMap(vendrComboList, 'value', 'name'); // 거래처
        $scope.prodClassCdDataMap = new wijmo.grid.DataMap(prodClassComboList, 'value', 'name'); // 상품분류
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.depositCupFgDataMap = new wijmo.grid.DataMap(depositCupFgData, 'value', 'name'); // 보증금상품유형
        $scope.pointUseYnDataMap = new wijmo.grid.DataMap(pointUseYnData, 'value', 'name'); // 재고관리여부
        $scope.dcYnDataMap = new wijmo.grid.DataMap(dcYnData, 'value', 'name'); // 재고관리여부
        $scope.cornerDatamap = new wijmo.grid.DataMap(cornerList, 'value', 'name'); // 코너
        $scope.ticketFgMap = new wijmo.grid.DataMap(ticketFgComboData, 'value', 'name'); // 식권구분
        $scope.acquireVatMap = new wijmo.grid.DataMap(acquireVatComboData, 'value', 'name'); // 매입VAT
        $scope.spcManageMap = new wijmo.grid.DataMap(spcManageComboData, 'value', 'name'); // 특정관리
        $scope.isbnMap = new wijmo.grid.DataMap(isbnComboData, 'value', 'name'); // ISBN
        $scope.orgProdFgMap = new wijmo.grid.DataMap(orgProdFgComboData, 'value', 'name'); // 상품등록주체


        // 전체삭제
        $scope.delAll();

        // 그리드 셋팅
        $scope.searchProdExcelUploadDefault();
    };

    // <-- 검색 호출 -->
    $scope.$on("prodExcelUploadCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 그리드 셋팅
    $scope.searchProdExcelUploadDefault = function() {
        // 파라미터 설정
        var params = {};
        params.hqBrandCd = brandList[0].value;
        if ($scope.prodNoEnvFg === "MANUAL") {
            params.prodCd = "00001";
        }
        params.prodNm = "아메리카노";
        params.prodClassCd = prodClassComboList[0].name;
        params.prodTypeFg = "1";
        params.saleProdYn = "Y";
        params.saleUprc = "1000";
        params.stinSaleUprc = "1000";
        params.dlvrSaleUprc = "900";
        params.packSaleUprc = "900";
        params.vendrCd = vendrComboList[0].name;
        params.splyUprc = "1000";
        if(orgnFg === "HQ"){
            params.poProdFg = "1";
        } else if(orgnFg === "STORE"){
            params.poProdFg = "4";
        } else{
            params.poProdFg = "9";
        }
        params.poUnitFg = "1";
        params.poUnitQty = "1";
        params.poMinQty = "1";
        params.barCd = "";
        params.vatFg = "1";
        params.vatIncldYn = "Y";
        params.stockProdYn = "Y";
        params.costUprc = "0";
        params.safeStockQty = "0";
        params.startStockQty = "0";
        // 가격관리구분
        if(orgnFg == "HQ") {
            params.prcCtrlFg = "H";
        } else {
            params.prcCtrlFg = "S";
        }
        params.depositCupFg="";
        params.pointUseYn="Y";
        params.dcYn="Y";
        params.cornrCd = "00";
        params.remark = "";
        if(urlProdFg === '1'){
            params.shPAlias = '상품약칭';
            params.shPTicketFg = 'N';
            params.shPMakerNm = '제조사';
            params.shPAcquireVat = 'Y';
            params.shPPointSaveRate = '0';
            params.shPSpec = '0';
            params.shPSpecialManage = 'N';
            params.shPSingleProdCd = '';
        }else if(urlProdFg === '2'){
            params.shBAlias = '도서약칭';
            params.shBPublishNm = '출판사';
            params.shBAuthor1 = '저자1';
            params.shBAuthor2 = '저자2';
            params.shBTranslator1 = '역자1';
            params.shBTranslator2 = '역자2';
            params.shBPubDate = '20250101';
            params.shBDiscRate = '0';
            params.shBAcquireVat = 'Y';
            params.shBPointSaveRate = '0';
            params.shBSpec = '0';
            params.shBSpcManage = 'N';
            params.shBIsbnFg = 'ISBN';
            params.shBIsbnCode = '';
        }

        // 추가기능 수행 : 파라미터
        $scope._addRow(params);
    };

    // 전체삭제
    $scope.delAll = function() {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", params, function(){});
    };

    // <-- 양식다운로드 -->
    $scope.excelDownload = function(){
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        var excelTitle = '상품엑셀업로드'
        if(urlProdFg === '1') {
            excelTitle = '일반상품엑셀업로드'
        }else if(urlProdFg === '2'){
            excelTitle = '도서상품엑셀업로드'
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        return column.visible;
                    }
                },
                excelTitle + '_' +getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //양식다운로드 -->

    // <-- 엑셀업로드 -->
    $scope.excelUpload = function(){
        // 상품엑셀업로드 팝업
        $("#prodExcelUpFile").val('');
        $("#prodExcelUpFile").trigger('click');
    };
    // <-- //엑셀업로드 -->

}]);

/**
 *  상품목록 조회 그리드 생성
 */
app.controller('prodExcelUploadProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodExcelUploadProdCtrl', $scope, $http, true));

    // 상품명 중복체크
    $scope.isChecked = true;

    // 커스텀콤보
    // $scope._getComboDataQueryCustom('getSideMenuSdselGrpCdCombo', 'sdselGrpCdComboData', 'S');
    // 발주단위구분 콤보박스와 data-map
    // $scope._getComboDataQueryByAuth('093', '', 'poUnitFgDataMap');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 발주단위구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부
        $scope.vatIncldYnDataMap = new wijmo.grid.DataMap(vatIncldYnDataMap2, 'value', 'name'); // 부가세포함여부
        $scope.stockProdYnDataMap = new wijmo.grid.DataMap(stockProdYnData, 'value', 'name'); // 재고관리여부
        $scope.vendrCdDataMap = new wijmo.grid.DataMap(vendrComboList, 'value', 'name'); // 거래처
        $scope.prodClassCdDataMap = new wijmo.grid.DataMap(prodClassComboList, 'value', 'name'); // 상품분류
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.depositCupFgDataMap = new wijmo.grid.DataMap(depositCupFgData, 'value', 'name'); // 보증금상품유형
        $scope.pointUseYnDataMap = new wijmo.grid.DataMap(pointUseYnData, 'value', 'name'); // 재고관리여부
        $scope.dcYnDataMap = new wijmo.grid.DataMap(dcYnData, 'value', 'name'); // 재고관리여부
        $scope.cornerDatamap = new wijmo.grid.DataMap(cornerList, 'value', 'name'); // 코너
        $scope.ticketFgMap = new wijmo.grid.DataMap(ticketFgComboData, 'value', 'name'); // 식권구분
        $scope.acquireVatMap = new wijmo.grid.DataMap(acquireVatComboData, 'value', 'name'); // 매입VAT
        $scope.spcManageMap = new wijmo.grid.DataMap(spcManageComboData, 'value', 'name'); // 특정관리
        $scope.isbnMap = new wijmo.grid.DataMap(isbnComboData, 'value', 'name'); // ISBN
        $scope.orgProdFgMap = new wijmo.grid.DataMap(orgProdFgComboData, 'value', 'name'); // 상품등록주체

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 검증결과
                if (col.binding === "result") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (item[("result")] !== '검증전' && item[("result")] !== '검증성공') {
                        wijmo.addClass(e.cell, 'wij_gridText-red');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            }
        });

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "prodTypeFg") {
                    if(item.prodTypeFg === "4"){
                        item.vatFg = "2";
                        item.pointUseYn = "N";
                        item.dcYn = "N";
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("prodExcelUploadProdCtrl", function(event, data) {
        $scope.searchProdExcelUploadProd();
        event.preventDefault();
    });

    // 검증결과 조회
    $scope.searchProdExcelUploadProd = function() {
        var params = {};

        $scope._inquiryMain("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 저장
    $scope.save = function() {
        // 전체삭제
        // var storeScope = agrid.getScope('prodExcelUploadCtrl', null);
        // storeScope.delAll();

        // 전체삭제
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckDeleteAll.sb", params, function(){

            $scope.stepCnt = 100;   // 한번에 DB에 저장할 숫자 세팅
            $scope.progressCnt = 0; // 처리된 숫자

            if ($scope.flex.rows.length <= 0) {
               $scope._popMsg(messages["prodExcelUpload.saveBlank"]);
               return false;
            }

            // 상품명 앞뒤 공백 및 엔터값 제거
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if(prodNoEnvFg === "MANUAL") {
                    if ($scope.flex.collectionView.items[i].prodCd !== "" && $scope.flex.collectionView.items[i].prodCd !== null) {
                        $scope.flex.collectionView.items[i].prodCd = $scope.flex.collectionView.items[i].prodCd.trim().removeEnter();
                    }
                }

                if($scope.flex.collectionView.items[i].prodNm !== "" && $scope.flex.collectionView.items[i].prodNm !== null) {
                    $scope.flex.collectionView.items[i].prodNm = $scope.flex.collectionView.items[i].prodNm.trim().removeEnter();
                }
            }

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                // 상품코드 채번방식
                var prodNoEnv = "";
                if(prodNoEnvFg === "MANUAL") { prodNoEnv = "1"; } else { prodNoEnv = "0"; } // 0 : 자동채번, 1 : 수동채번
                $scope.flex.collectionView.items[i].prodNoEnv = prodNoEnv;

                // 상품명 중복체크
                $scope.flex.collectionView.items[i].chkProdNm = $scope.isChecked;

                // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
                Number.isInteger = Number.isInteger || function(value) {
                    return typeof value === "number" &&
                        isFinite(value) &&
                        Math.floor(value) === value;
                };

                // 브랜드사용여부
                $scope.flex.collectionView.items[i].brandUseFg = $scope.brandUseFg;

                // <-- 검증 -->
                var result = "";

                // 비고
                if($scope.flex.collectionView.items[i].remark === "" || $scope.flex.collectionView.items[i].remark === null) {
                } else {
                    // 최대길이 체크
                    if(nvl($scope.flex.collectionView.items[i].remark, '').getByteLengthForOracle() > 500) { result = messages["prodExcelUpload.remarkLengthChk"]; } // 비고 길이가 너무 깁니다.
                }

                // 상품유형 보증금일때
                if($scope.flex.collectionView.items[i].prodTypeFg === "4"){
                    $scope.flex.collectionView.items[i].vatFg = "2";
                    $scope.flex.collectionView.items[i].pointUseYn = "N";
                    $scope.flex.collectionView.items[i].dcYn = "N";
                    if($scope.flex.collectionView.items[i].depositCupFg === "" || $scope.flex.collectionView.items[i].depositCupFg === null || $scope.flex.collectionView.items[i].depositCupFg === "선택"){
                        result = messages["prodExcelUpload.depositCupFg.None"];
                    }
                } else {
                    console.log($scope.flex.collectionView.items[i].depositCupFg);
                    if($scope.flex.collectionView.items[i].depositCupFg !== "" && $scope.flex.collectionView.items[i].depositCupFg !== null && $scope.flex.collectionView.items[i].depositCupFg !== undefined && $scope.flex.collectionView.items[i].depositCupFg !== "선택"){
                        result = messages["prodExcelUpload.depositCupFg.Chk"];
                    }
                }

                // 바코드
                if($scope.flex.collectionView.items[i].barCd === "" || $scope.flex.collectionView.items[i].barCd === null) {
                } else {
                    // 숫자/영문만 입력
                    var numChkexp = /[^A-Za-z0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].barCd)) { result = messages["prodExcelUpload.barCdInChk"]; } // 상품코드 숫자/영문만 입력해주세요.

                    // 최대길이 체크
                    if(nvl($scope.flex.collectionView.items[i].barCd, '').getByteLengthForOracle() > 40) { result = messages["prodExcelUpload.barCdLengthChk"]; } // 바코드 길이가 너무 깁니다.

                    //  바코드 중복체크
                    for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                        if(i !== j) {
                            if($scope.flex.collectionView.items[j].barCd === $scope.flex.collectionView.items[i].barCd) { result = messages["prodExcelUpload.barCdChk"]; } // 바코드가 중복됩니다.
                        }
                    }
                }

                // 원가단가
                if($scope.flex.collectionView.items[i].costUprc === "" || $scope.flex.collectionView.items[i].costUprc === null) {
                    result = messages["prodExcelUpload.costUprcBlank"]; // 원가단가를 입력하세요.
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].costUprc)) {
                        $scope.flex.collectionView.items[i].costUprc = "";
                        result = messages["prodExcelUpload.costUprcInChk"]; // 원가단가 숫자만 입력해주세요.
                    }
                }

                // 최소발주수량
                if($scope.flex.collectionView.items[i].poMinQty === "" || $scope.flex.collectionView.items[i].poMinQty === null) {
                    result = messages["prodExcelUpload.poMinQtyBlank"]; // 최소발주수량를 입력하세요.
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].poMinQty)) {
                        $scope.flex.collectionView.items[i].poMinQty = "";
                        result = messages["prodExcelUpload.poMinQtyInChk"]; // 최소발주수량은 숫자만 입력해주세요.
                    }
                }

                // 발주단위수량
                if($scope.flex.collectionView.items[i].poUnitQty === "" || $scope.flex.collectionView.items[i].poUnitQty === null) {
                    result = messages["prodExcelUpload.poUnitQtyBlank"]; // 발주단위수량을 입력하세요.
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].poUnitQty)) {
                        $scope.flex.collectionView.items[i].poUnitQty = "";
                        result = messages["prodExcelUpload.poUnitQtyInChk"]; // 발주단위수량은 숫자만 입력해주세요.
                    }
                }

                // 공급단가
                if($scope.flex.collectionView.items[i].splyUprc === "" || $scope.flex.collectionView.items[i].splyUprc === null) {
                    result = messages["prodExcelUpload.splyUprcBlank"]; // 공급단가를 입력하세요.
                } else {
                    // 숫자만 입력
                    var numChkexp = /[^0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].splyUprc)) {
                        $scope.flex.collectionView.items[i].splyUprc = "";
                        result = messages["prodExcelUpload.splyUprcInChk"]; // 공급단가 숫자만 입력해주세요.
                    }
                }

                var numchkexp = /[^0-9]/; // 숫자가 아닌 값 체크
                var numchkexp2 = /^-?[0-9]+$/;

                if(subPriceFg === "0"){
                    $scope.flex.collectionView.items[i].stinSaleUprc = "";
                    $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                    $scope.flex.collectionView.items[i].packSaleUprc = "";
                }

                // 판매단가
                if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                    result = messages["prodExcelUpload.saleUprcBlank"]; // 판매단가를 입력하세요.
                } else {
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){ // 소수점있으면 거름
                        $scope.flex.collectionView.items[i].saleUprc = "";
                        result = messages["prodExcelUpload.saleUprcInChk"];
                    } else {
                        // 판매단가 - 마이너스(-)외에 다른 문자 입력 불가z
                        if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) {
                            if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                                $scope.flex.collectionView.items[i].saleUprc = "";
                                result = messages["salePrice.saleUprcInChk"]; // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            }
                        }
                        // 판매단가 - 1000000000 이상 입력 불가
                        if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){
                            $scope.flex.collectionView.items[i].saleUprc = "";
                            result = messages["salePrice.saleUprcInChk"]; // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                        // 판매단가 - -1000000000 이하 입력 불가
                        if($scope.flex.collectionView.items[i].saleUprc <= -1000000000){
                            $scope.flex.collectionView.items[i].saleUprc = "";
                            result = messages["salePrice.saleUprcInChk"]; // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                }

                // 내점가
                if($scope.flex.collectionView.items[i].stinSaleUprc !== "" && $scope.flex.collectionView.items[i].stinSaleUprc !== null) {
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false){ // 소수점있으면 거름
                        //$scope.flex.collectionView.items[i].stinSaleUprc = "";
                        result = messages["prodExcelUpload.stinSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                    } else {
                        // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)) {
                                // $scope.flex.collectionView.items[i].stinSaleUprc = "";
                                result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                            }
                        }

                        // 변경내점-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000) {
                            // $scope.flex.collectionView.items[i].stinSaleUprc = "";
                            result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                        // 변경내점-판매가 - -1000000000 이하 입력 불가
                        if ($scope.flex.collectionView.items[i].stinSaleUprc <= -1000000000) {
                            // $scope.flex.collectionView.items[i].stinSaleUprc = "";
                            result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                }

                // 배달가
                if($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false){ // 소수점있으면 거름
                        //$scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                        result = messages["prodExcelUpload.dlvrSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                    } else {
                        // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)) {
                                // $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                                result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                            }
                        }
                        // 변경배달-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000) {
                            // $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                            result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                        // 변경배달-판매가 - -1000000000 이하 입력 불가
                        if ($scope.flex.collectionView.items[i].dlvrSaleUprc <= -1000000000) {
                            // $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                            result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                }

                // 포장가
                if($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {
                    if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false){ // 소수점있으면 거름
                        //$scope.flex.collectionView.items[i].packSaleUprc = "";
                        result = messages["prodExcelUpload.packSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                    } else {
                        // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                        if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                            if ((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)) {
                                // $scope.flex.collectionView.items[i].packSaleUprc = "";
                                result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                            }
                        }
                        // 변경포장-판매가 - 1000000000 이상 입력 불가
                        if ($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000) {
                            // $scope.flex.collectionView.items[i].packSaleUprc = "";
                            result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                        // 변경포장-판매가 - -1000000000 이하 입력 불가
                        if ($scope.flex.collectionView.items[i].packSaleUprc <= -1000000000) {
                            // $scope.flex.collectionView.items[i].packSaleUprc = "";
                            result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                }

                // 상품명
                if($scope.flex.collectionView.items[i].prodNm === "" || $scope.flex.collectionView.items[i].prodNm === null) {
                    result = messages["prodExcelUpload.prodNmBlank"]; // 상품명을 입력하세요.
                } else {
                    // 최대길이 체크
                    if(nvl($scope.flex.collectionView.items[i].prodNm, '').getByteLengthForOracle() > 100) { result = messages["prodExcelUpload.prodNmLengthChk"]; } // 상품명 길이가 너무 깁니다.

                    // 상품명 중복체크
                    if($scope.isChecked === true) {
                        for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                            if(i !== j) {
                                if($scope.flex.collectionView.items[j].prodNm !== "" && $scope.flex.collectionView.items[j].prodNm !== null){
                                    if($scope.flex.collectionView.items[j].prodNm === $scope.flex.collectionView.items[i].prodNm) { result = messages["prodExcelUpload.prodNmChk"]; } // 상품명이 중복됩니다.
                                }
                            }
                        }
                    }

                    // 큰따옴표(") 입력 불가
                    if ($scope.flex.collectionView.items[i].prodNm.indexOf("\"") >= 0) { result = messages["prodExcelUpload.prodNmTextChk"]; } // 상품명에 큰따옴표(")를 입력할 수 없습니다.
                }

                // 상품코드
                // 수동채번일때만 체크
                if(prodNoEnv === "1") {
                    if ($scope.flex.collectionView.items[i].prodCd === "" || $scope.flex.collectionView.items[i].prodCd === null) {
                        result = messages["prodExcelUpload.prodCdBlank"]; // 상품코드를 입력하세요.
                    } else {
                        // 숫자/영문만 입력
                        var numChkexp = /[^A-Za-z0-9]/g;
                        if (numChkexp.test($scope.flex.collectionView.items[i].prodCd)) { result = messages["prodExcelUpload.prodCdInChk"]; } // 상품코드 숫자/영문만 입력해주세요.

                        // 최대길이 체크
                        if (nvl($scope.flex.collectionView.items[i].prodCd, '').getByteLengthForOracle() > 13) { result = messages["prodExcelUpload.prodCdLengthChk"]; } // 상품코드 길이가 너무 깁니다.

                        // 상품코드 중복체크
                        for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                            if (i !== j) {
                                if($scope.flex.collectionView.items[j].prodCd !== "" && $scope.flex.collectionView.items[j].prodCd !== null) {
                                    if ($scope.flex.collectionView.items[j].prodCd === $scope.flex.collectionView.items[i].prodCd) { result = messages["prodExcelUpload.prodCdChk"]; } // 상품코드가 중복됩니다.
                                }
                            }
                        }
                    }
                }

                $scope.flex.collectionView.items[i].result = result;
                // <-- //검증 -->


                params.push($scope.flex.collectionView.items[i]);
            }


            // 저장
            $scope.saveSave(params);
        });
    };

    // 저장
    $scope.saveSave = function(jsonData) {

        $scope.totalRows = jsonData.length;
        var params = [];
        var msg = '';

        // 저장 시작이면 업로드 중 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            var item = jsonData[i];

            item.progressCnt = $scope.progressCnt;

            params.push(item);
        }

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckSaveAdd.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
                    // 검증결과 조회
                    $scope.searchProdExcelUploadProd();

                    $scope._popConfirm(messages["prodExcelUpload.saveConfirm"], function() {
                        // 상품등록 저장
                        $scope.prodExcelUploadSave();
                    });
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 업로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.saveSave(jsonData);
            }
        });
    };

    // 상품등록 저장
    $scope.prodExcelUploadSave = function() {
        $scope.stepCnt = 100;    // 한번에 DB에 저장할 숫자 세팅
        $scope.progressCnt = 0; // 처리된 숫자

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            // 상품코드 채번방식
            var prodNoEnv = "";
            if(prodNoEnvFg === "MANUAL") { prodNoEnv = "1"; } else { prodNoEnv = "0"; } // 0 : 자동채번, 1 : 수동채번
            $scope.flex.collectionView.items[i].prodNoEnv = prodNoEnv;
            $scope.flex.collectionView.items[i].gubun = "prodExcelUpload";
            params.push($scope.flex.collectionView.items[i]);
        }

        $timeout(function () {
            setTimeout(function () {
                // 저장
                $scope.save2(params);
            }, 500);
        }, 10);
    };

    // 저장
    $scope.save2 = function(orgParams) {
        $scope.totalRows = orgParams.length;    // 체크수
        var params = [];

        // 저장 시작이면 작업내역 로딩 팝업 오픈
        if ($scope.progressCnt === 0) {
            $timeout(function () {
                $scope.excelUploadingPopup(true);
                $("#progressCnt").html($scope.progressCnt);
                $("#totalRows").html($scope.totalRows);
            }, 10);
        }

        // stepCnt 만큼 데이터 DB에 저장
        var loopCnt = (parseInt($scope.progressCnt) + parseInt($scope.stepCnt) > parseInt($scope.totalRows) ? parseInt($scope.totalRows) : parseInt($scope.progressCnt) + parseInt($scope.stepCnt));
        for (var i = $scope.progressCnt; i < loopCnt; i++) {
            params.push(orgParams[i]);
        }

        console.log("총 갯수 :" + $scope.totalRows);
        console.log("진행 갯수 :" + $scope.progressCnt + "~" + (loopCnt - 1));
        console.log("---------------------------------------------------------------------");

        //가상로그인 session 설정
        var sParam = {};
        if(document.getElementsByName('sessionId')[0]){
            sParam['sid'] = document.getElementsByName('sessionId')[0].value;
        }

        // ajax 통신 설정
        $http({
            method : 'POST', //방식
            url    : '/base/prod/simpleProd/simpleProd/getSimpleProdSave.sb', /* 통신할 URL */
            data   : params, /* 파라메터로 보낼 데이터 : @requestBody */
            params : sParam,
            headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
        }).then(function successCallback(response) {
            if ($scope._httpStatusCheck(response, true)) {
                if (parseInt($scope.progressCnt) >= parseInt($scope.totalRows)) {
                    // 작업내역 로딩 팝업 닫기
                    $scope.excelUploadingPopup(false);
                    $scope._gridDataInit();
                    // 저장되었습니다.
                    $scope._popMsg(messages["cmm.saveSucc"]);
                    // 검증결과 조회
                    $scope.searchProdExcelUploadProd();
                }
            }
        }, function errorCallback(response) {
            $scope.excelUploadingPopup(false); // 작업내역 로딩 팝업 닫기
            if (response.data.message) {
                $scope._popMsg(response.data.message);
            } else {
                $scope._popMsg(messages['cmm.saveFail']);
            }
            return false;
        }).then(function () {
            // 'complete' code here
            // 처리 된 숫자가 총 업로드할 수보다 작은 경우 다시 save 함수 호출
            if (parseInt($scope.progressCnt) < parseInt($scope.totalRows)) {
                // 처리된 숫자 변경
                $scope.progressCnt = loopCnt;
                // 팝업의 progressCnt 값 변경
                $("#progressCnt").html($scope.progressCnt);
                $scope.save2(orgParams);
            }
        });
    };

    // 작업내역 로딩 팝업
    $scope.excelUploadingPopup = function (showFg) {
        if (showFg) {
            // 팝업내용 동적 생성
            var innerHtml = '<div class=\"wj-popup-loading\"><p class=\"bk\">' + messages['cmm.progress'] + '</p>';
            innerHtml += '<div class="mt5 txtIn"><span class="bk" id="progressCnt">0</span>/<span class="bk" id="totalRows">0</span> 개 진행 중...</div>';
            innerHtml += '<p><img src=\"/resource/solbipos/css/img/loading.gif\" alt=\"\" /></p></div>';
            // html 적용
            $scope._loadingPopup.content.innerHTML = innerHtml;
            // 팝업 show
            $scope._loadingPopup.show(true);
        } else {
            $scope._loadingPopup.hide(true);
        }
    };

    // <-- 엑셀다운로드 -->
    $scope.prodExcelDownload = function(){
        var column_binding;

        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["excelUpload.not.downloadData"]);	//다운로드 할 데이터가 없습니다.
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
        $timeout(function()	{
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
                {
                    includeColumnHeaders: 	true,
                    includeCellStyles	: 	false,
                    includeColumns      :	function (column) {
                        // return column.visible;
                        return column.binding != 'gChk';
                    }
                },
                '상품엑셀업로드_'+getCurDate()+'.xlsx',
                function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
                    }, 10);
                }
            );
        }, 10);
    };
    // <-- //양식다운로드 -->

    // <-- 그리드 행 삭제 -->
    $scope.delete = function(){
        $scope._popConfirm(messages["prodExcelUpload.delConfirm"], function() {
            for(var i = $scope.flex.collectionView.items.length-1; i >= 0; i-- ){
                var item = $scope.flex.collectionView.items[i];

                if(item.gChk) {
                    $scope.flex.collectionView.removeAt(i);
                }
            }

            // 삭제
            $scope.deleteSave();
        });
    };

    $scope.deleteSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
            $scope.flex.collectionView.itemsRemoved[i].status = "D";
            params.push($scope.flex.collectionView.itemsRemoved[i]);
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckDelete.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
        });
    };
    // <-- //그리드 행 삭제 -->


    // 기초마스터등록
    $scope.masterInsert = function() {

        // 엑셀업로드 이후
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["prodExcelUpload.masterInsert.None"]);
            return false;
        }

        var params = {};
        $scope._postJSONQuery.withOutPopUp('/base/prod/prodExcelUpload/prodExcelUpload/getMasterChk.sb', params, function (response) {
            var masterChkList = response.data.data.result;
            $scope.masterChkList = masterChkList;
            console.log($scope.masterChkList);
            if ($scope.masterChkList.prodClassCnt == 0 && $scope.masterChkList.vendrCnt == 0) {
                $scope.prodClassCdInsertLayer.show(true);
                $scope._broadcast('prodClassCdInsertCtrl', true);
            } else if ($scope.masterChkList.prodClassCnt == 0) {
                $scope.prodClassCdInsertLayer.show(true);
                $scope._broadcast('prodClassCdInsertCtrl', false);
            } else if ($scope.masterChkList.vendrCnt == 0) {
                $scope.vendrCdInsertLayer.show(true);
                $scope._broadcast('vendrCdInsertCtrl', false);
            } else {
                $scope._popMsg(messages["prodExcelUpload.masterInsert.Chk"]);
            }

        });

    }
}]);