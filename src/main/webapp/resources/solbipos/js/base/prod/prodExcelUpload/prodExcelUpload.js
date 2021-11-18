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
    $scope._getComboDataQueryCustom('', '', '');
    // 발주단위구분 콤보박스와 data-map
    $scope._getComboDataQueryByAuth('093', '', 'poUnitFgDataMap');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        // $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 발주단위구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부
        $scope.stockProdYnDataMap = new wijmo.grid.DataMap(stockProdYnData, 'value', 'name'); // 재고관리여부
        $scope.vendrCdDataMap = new wijmo.grid.DataMap(vendrComboList, 'value', 'name'); // 거래처
        $scope.prodClassCdDataMap = new wijmo.grid.DataMap(prodClassComboList, 'value', 'name'); // 상품분류
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

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
        params.poProdFg = "1";
        params.poUnitFg = "1";
        params.poUnitQty = "1";
        params.poMinQty = "1";
        params.barCd = "";
        params.vatFg = "1";
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
        params.remark = "";

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
    $scope._getComboDataQueryCustom('', '', '');
    // 발주단위구분 콤보박스와 data-map
    $scope._getComboDataQueryByAuth('093', '', 'poUnitFgDataMap');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.saleProdYnDataMap = new wijmo.grid.DataMap(saleProdYnData, 'value', 'name'); // 판매상품여부
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        // $scope.poUnitFgDataMap = new wijmo.grid.DataMap(poUnitFgData, 'value', 'name'); // 발주단위구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부
        $scope.stockProdYnDataMap = new wijmo.grid.DataMap(stockProdYnData, 'value', 'name'); // 재고관리여부
        $scope.vendrCdDataMap = new wijmo.grid.DataMap(vendrComboList, 'value', 'name'); // 거래처
        $scope.prodClassCdDataMap = new wijmo.grid.DataMap(prodClassComboList, 'value', 'name'); // 상품분류
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

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
            // 저장
            $scope.saveSave();
        });
    };

    // 저장
    $scope.saveSave = function() {
        if ($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["prodExcelUpload.saveBlank"]);
            return false;
        }

        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈

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

            // <-- 검증 -->
            var result = "";

            // 비고
            if($scope.flex.collectionView.items[i].remark === "" || $scope.flex.collectionView.items[i].remark === null) {
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].remark, '').getByteLengthForOracle() > 500) { result = messages["prodExcelUpload.remarkLengthChk"]; } // 비고 길이가 너무 깁니다.
            }

            // 바코드
            if($scope.flex.collectionView.items[i].barCd === "" || $scope.flex.collectionView.items[i].barCd === null) {
            } else {
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

            // 판매단가
            if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                result = messages["prodExcelUpload.saleUprcBlank"]; // 판매단가를 입력하세요.
            } else {
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){ // 소수점있으면 거름
                    $scope.flex.collectionView.items[i].saleUprc = "";
                    result = messages["prodExcelUpload.saleUprcInChk"];
                } else {
                    // 숫자만 입력
                    var numchkexp = /[^0-9]/g;
                    if (numchkexp.test($scope.flex.collectionView.items[i].saleUprc)) { // 음수
                        var numchkexp2 = /^-[0-9]/g;
                        if (numchkexp2.test($scope.flex.collectionView.items[i].saleUprc)) {
                        } else if((numchkexp2.test($scope.flex.collectionView.items[i].saleUprc) == false)){
                            $scope.flex.collectionView.items[i].saleUprc = "";
                            result = messages["prodExcelUpload.saleUprcInChk"];
                        }
                    } else if($scope.flex.collectionView.items[i].saleUprc >= 1000000000){ // 양수 max값
                        $scope.flex.collectionView.items[i].saleUprc = "";
                        result = messages["prodExcelUpload.saleUprcInChk"];
                    }
                }
            }

            // 내점가
            if($scope.flex.collectionView.items[i].stinSaleUprc !== "" && $scope.flex.collectionView.items[i].stinSaleUprc !== null) {
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false){ // 소수점있으면 거름
                    //$scope.flex.collectionView.items[i].stinSaleUprc = "";
                    result = messages["prodExcelUpload.stinSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                } else {
                    // 숫자만 입력
                    var numchkexp = /[^0-9]/g;
                    if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) { // 음수
                        var numchkexp2 = /^-[0-9]/g;
                        if (numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                        } else if((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)){
                            //$scope.flex.collectionView.items[i].stinSaleUprc = "";
                            result = messages["prodExcelUpload.stinSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                        }
                    } else if($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000){ // 양수 max값
                        //$scope.flex.collectionView.items[i].stinSaleUprc = "";
                        result = messages["prodExcelUpload.stinSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                    }
                }
            }

            // 배달가
            if($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false){ // 소수점있으면 거름
                    //$scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                    result = messages["prodExcelUpload.dlvrSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                } else {
                    // 숫자만 입력
                    var numchkexp = /[^0-9]/g;
                    if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) { // 음수
                        var numchkexp2 = /^-[0-9]/g;
                        if (numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                        } else if((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)){
                            //$scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                            result = messages["prodExcelUpload.dlvrSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                        }
                    } else if($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000){ // 양수 max값
                        //$scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                        result = messages["prodExcelUpload.dlvrSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                    }
                }
            }

            // 포장가
            if($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false){ // 소수점있으면 거름
                    //$scope.flex.collectionView.items[i].packSaleUprc = "";
                    result = messages["prodExcelUpload.packSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                } else {
                    // 숫자만 입력
                    var numchkexp = /[^0-9]/g;
                    if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) { // 음수
                        var numchkexp2 = /^-[0-9]/g;
                        if (numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                        } else if((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)){
                            //$scope.flex.collectionView.items[i].packSaleUprc = "";
                            result = messages["prodExcelUpload.packSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
                        }
                    } else if($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000){ // 양수 max값
                        //$scope.flex.collectionView.items[i].packSaleUprc = "";
                        result = messages["prodExcelUpload.packSaleUprc"] + messages["prodExcelUpload.uprcChk.msg"];
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
            }

            // 상품코드
            // 수동채번일때만 체크
            if(prodNoEnv === "1") {
                if ($scope.flex.collectionView.items[i].prodCd === "" || $scope.flex.collectionView.items[i].prodCd === null) {
                    result = messages["prodExcelUpload.prodCdBlank"]; // 상품코드를 입력하세요.
                } else {
                    // 숫자/영문만 입력
                    //var numChkexp = /[^A-Za-z0-9]/g;
                    //if (numChkexp.test($scope.flex.collectionView.items[i].prodCd)) { result = messages["prodExcelUpload.prodCdInChk"]; } // 상품코드 숫자/영문만 입력해주세요.

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

        // 검증결과 저장
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/prodExcelUpload/prodExcelUpload/getProdExcelUploadCheckSaveAdd.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기

            // 검증결과 조회
            $scope.searchProdExcelUploadProd();

            $scope._popConfirm(messages["prodExcelUpload.saveConfirm"], function() {
                // 상품등록 저장
                $scope.prodExcelUploadSave();
            });
        });
    };

    // 상품등록 저장
    $scope.prodExcelUploadSave = function() {
        $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
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

        $scope._save("/base/prod/simpleProd/simpleProd/getSimpleProdSave.sb", params, function(){
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            // 검증결과 조회
            $scope.searchProdExcelUploadProd();
        });
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

}]);