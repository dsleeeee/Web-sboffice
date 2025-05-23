/****************************************************************
 *
 * 파일명 : simpleProd.js
 * 설  명 : 간편상품등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.08.26     김설아      1.0
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
var vatIncldYnData = [
    {"name":"별도","value":"N"},
    {"name":"포함","value":"Y"}
];

/**
 *  상품목록 조회 그리드 생성
 */
app.controller('simpleProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('simpleProdCtrl', $scope, $http, false));

    // 상품코드 채번방식
    $scope.prodNoEnvFg = prodNoEnvFg;

    $("#divSimpleProd").css("display", "none");
    $("#divSimpleProdAuth").css("display", "block");
    // 단독매장
    if(hqOfficeCd == "00000") {
        $("#divSimpleProd").css("display", "block");
        $("#divSimpleProdAuth").css("display", "none");
        // 프랜 본사,매장
    } else {
        if((prodAuthEnvstVal== "ALL") || (orgnFg === 'HQ' && prodAuthEnvstVal== "HQ") || (orgnFg === 'STORE' && prodAuthEnvstVal== "STORE")) {
            $("#divSimpleProd").css("display", "block");
            $("#divSimpleProdAuth").css("display", "none");
        }
    }
    if(prodAuthEnvstVal === 'ALL') {
        $("#lblSimpleProdAuth").text("'본사/매장생성'");
    } else if(prodAuthEnvstVal === 'HQ') {
        $("#lblSimpleProdAuth").text("'본사생성'");
    } else if(prodAuthEnvstVal === 'STORE') {
        $("#lblSimpleProdAuth").text("'매장생성'");
    }

    // 상품명 중복체크
    $scope.isChecked = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.brandDataMap = new wijmo.grid.DataMap(brandList, 'value', 'name'); // 브랜드
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFgData, 'value', 'name'); // 상품유형구분
        $scope.poProdFgDataMap = new wijmo.grid.DataMap(poProdFgData, 'value', 'name'); // 발주상품구분
        $scope.vatFgDataMap = new wijmo.grid.DataMap(vatFgData, 'value', 'name'); // 과세여부
        $scope.vatIncldYnDataMap = new wijmo.grid.DataMap(vatIncldYnData, 'value', 'name'); // 부가세포함여부
        $scope.vendrCdDataMap = new wijmo.grid.DataMap(vendrComboList, 'value', 'name'); // 거래처
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
        $scope.depositCupFgDataMap = new wijmo.grid.DataMap(depositCupFgData, 'value', 'name'); // 보증금상품유형
        $scope.pointUseYnDataMap = new wijmo.grid.DataMap(pointUseYnData, 'value', 'name'); // 재고관리여부
        $scope.dcYnDataMap = new wijmo.grid.DataMap(dcYnData, 'value', 'name'); // 재고관리여부
        $scope.cornerDatamap = new wijmo.grid.DataMap(cornerList, 'value', 'name'); // 코너

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

        // 전체삭제
        $scope.delAll();

        // 그리드 셋팅
        $scope.searchSimpleProdDefault(25);
    };

    // <-- 검색 호출 -->
    $scope.$on("simpleProdCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 그리드 셋팅
    $scope.searchSimpleProdDefault = function(rowsCount) {
        // 파라미터 설정
        var params = {};
        params.result = "검증전";
        params.hqBrandCd = brandList[0].value;
        if($scope.prodNoEnvFg === "MANUAL") {
            params.prodCd = "";
        }
        params.prodNm = "";
        params.saleUprc = "";
        params.stinSaleUprc = "";
        params.dlvrSaleUprc = "";
        params.packSaleUprc = "";
        params.vendrCd = "";
        params.prodTypeFg = "1";
        if(orgnFg === "HQ"){
            params.poProdFg = "1";
        } else if(orgnFg === "STORE"){
            params.poProdFg = "4";
        } else{
            params.poProdFg = "9";
        }
        params.splyUprc = "0";
        params.costUprc = "0";
        params.vatFg = "1";
        params.vatIncldYn = "Y";
        params.barCd = "";
        // 가격관리구분
        if(orgnFg == "HQ") {
            params.prcCtrlFg = "H";
        } else {
            params.prcCtrlFg = "S";
        }
        params.depositCupFg = "선택";
        params.pointUseYn="Y";
        params.dcYn="Y";
        params.cornrCd = "00";

        for(var i = 0; i < rowsCount; i++) {
            // 추가기능 수행 : 파라미터
            $scope._addRow(params);
        }
    };

    // 전체삭제
    $scope.delAll = function() {
        var params = {};

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/simpleProd/simpleProd/getSimpleProdCheckDeleteAll.sb", params, function(){});
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer;
        popUp.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassCdNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassCdNm = "";
    };

    // 초기화
    $scope.clear = function() {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].result = "검증전";
            $scope.flex.collectionView.items[i].hqBrandCd = brandList[0].value;
            if($scope.prodNoEnvFg === "MANUAL") {
                $scope.flex.collectionView.items[i].prodCd = "";
            }
            $scope.flex.collectionView.items[i].prodNm = "";
            $scope.flex.collectionView.items[i].saleUprc = "";
            $scope.flex.collectionView.items[i].stinSaleUprc = "";
            $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
            $scope.flex.collectionView.items[i].packSaleUprc = "";
            $scope.flex.collectionView.items[i].vendrCd = "";
            $scope.flex.collectionView.items[i].prodTypeFg = "1";
            if(orgnFg == "HQ") {
                $scope.flex.collectionView.items[i].poProdFg = "1";
            }else{
                $scope.flex.collectionView.items[i].poProdFg = "4";
            }
            $scope.flex.collectionView.items[i].splyUprc = "0";
            $scope.flex.collectionView.items[i].costUprc = "0";
            $scope.flex.collectionView.items[i].vatFg = "1";
            $scope.flex.collectionView.items[i].vatIncldYn = "Y";
            $scope.flex.collectionView.items[i].barCd = "";
            if(orgnFg == "HQ") {
                $scope.flex.collectionView.items[i].prcCtrlFg = "H";
            } else {
                $scope.flex.collectionView.items[i].prcCtrlFg = "S";
            }
            $scope.flex.collectionView.items[i].depositCupFg = "";
            $scope.flex.collectionView.items[i].pointUseYn = "N";
            $scope.flex.collectionView.items[i].dcYn = "N";
            $scope.flex.collectionView.items[i].cornrCd = "00";
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.save = function() {
        // 전체삭제
        $scope.delAll();

        if (isNull($scope.prodClassCd)) {
            $scope._popMsg(messages["simpleProd.prodClassCdBlank"]);
            return false;
        }

        // 상품명 앞뒤 공백 및 엔터값 제거
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.prodNoEnvFg === "MANUAL") {
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
            if($scope.prodNoEnvFg === "MANUAL") { prodNoEnv = "1"; } else { prodNoEnv = "0"; } // 0 : 자동채번, 1 : 수동채번
            $scope.flex.collectionView.items[i].prodNoEnv = prodNoEnv;

            // 상품명 중복체크
            $scope.flex.collectionView.items[i].chkProdNm = $scope.isChecked;

            // 상품분류코드
            $scope.flex.collectionView.items[i].prodClassCd = $scope.prodClassCd;

            // 브랜드사용여부
            $scope.flex.collectionView.items[i].brandUseFg = $scope.brandUseFg;

            // <-- 검증 -->
            var result = "";

            // 상품유형 보증금일때
            if($scope.flex.collectionView.items[i].prodTypeFg === "4"){
                $scope.flex.collectionView.items[i].vatFg = "2";
                $scope.flex.collectionView.items[i].pointUseYn = "N";
                $scope.flex.collectionView.items[i].dcYn = "N";
                if($scope.flex.collectionView.items[i].depositCupFg === "" || $scope.flex.collectionView.items[i].depositCupFg === null || $scope.flex.collectionView.items[i].depositCupFg === "선택"){
                    result = messages["simpleProd.depositCupFg.None"];
                }
            } else {
                if($scope.flex.collectionView.items[i].saleUprc !== "" && $scope.flex.collectionView.items[i].saleUprc !== null){
                    console.log($scope.flex.collectionView.items[i].depositCupFg);
                    if($scope.flex.collectionView.items[i].depositCupFg !== ""  && $scope.flex.collectionView.items[i].depositCupFg !== null && $scope.flex.collectionView.items[i].depositCupFg !== "선택"){
                        result = messages["simpleProd.depositCupFg.Chk"];
                    }
                }
            }

            // 바코드
            if($scope.flex.collectionView.items[i].barCd === "" || $scope.flex.collectionView.items[i].barCd === null) {
            } else {
                // 숫자/영문만 입력
                var numChkexp = /[^A-Za-z0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].barCd)) { result = messages["simpleProd.barCdInChk"]; } // 바코드 숫자/영문만 입력해주세요.

                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].barCd, '').getByteLengthForOracle() > 40) { result = messages["simpleProd.barCdLengthChk"]; } // 바코드 길이가 너무 깁니다.

                //  바코드 중복체크
                for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                    if(i !== j) {
                        if($scope.flex.collectionView.items[j].barCd === $scope.flex.collectionView.items[i].barCd) { result = messages["simpleProd.barCdChk"]; } // 바코드가 중복됩니다.
                    }
                }
            }

            // 과세여부
            if($scope.flex.collectionView.items[i].vatFg === "" || $scope.flex.collectionView.items[i].vatFg === null) { result = messages["simpleProd.vatFgBlank"]; } // 과세여부를 선택하세요.

            // 부가세포함여부
            if($scope.flex.collectionView.items[i].vatIncldYn === "" || $scope.flex.collectionView.items[i].vatIncldYn === null) { result = messages["simpleProd.vatIncldYn"] + messages["cmm.require.select"]; } // 부가세포함여부(을)를 선택해주세요.

            // 원가단가
            if($scope.flex.collectionView.items[i].costUprc === "" || $scope.flex.collectionView.items[i].costUprc === null) {
                result = messages["simpleProd.costUprcBlank"]; // 원가단가를 입력하세요.
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].costUprc)) {
                    $scope.flex.collectionView.items[i].costUprc = "";
                    result = messages["simpleProd.costUprcInChk"]; // 원가단가 숫자만 입력해주세요.
                }
            }

            // 공급단가
            if($scope.flex.collectionView.items[i].splyUprc === "" || $scope.flex.collectionView.items[i].splyUprc === null) {
                result = messages["simpleProd.splyUprcBlank"]; // 공급단가를 입력하세요.
            } else {
                // 숫자만 입력
                var numChkexp = /[^0-9]/g;
                if (numChkexp.test($scope.flex.collectionView.items[i].splyUprc)) {
                    $scope.flex.collectionView.items[i].splyUprc = "";
                    result = messages["simpleProd.splyUprcInChk"]; // 공급단가 숫자만 입력해주세요.
                }
            }

            var numchkexp = /[^0-9]/; // 숫자가 아닌 값 체크
            var numchkexp2 = /^-?[0-9]+$/;

            // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
            Number.isInteger = Number.isInteger || function(value) {
                return typeof value === "number" &&
                    isFinite(value) &&
                    Math.floor(value) === value;
            };

            if(subPriceFg === "0"){
                $scope.flex.collectionView.items[i].stinSaleUprc = "";
                $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                $scope.flex.collectionView.items[i].packSaleUprc = "";
            }

            // 판매단가
            if($scope.flex.collectionView.items[i].saleUprc === "" || $scope.flex.collectionView.items[i].saleUprc === null) {
                result = messages["simpleProd.saleUprcBlank"]; // 판매단가를 입력하세요.
            } else {
                // 판매단가 - 소수점 입력 불가
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].saleUprc)) == false){ // 소수점있으면 거름
                    $scope.flex.collectionView.items[i].saleUprc = "";
                    result = messages["simpleProd.saleUprcInChk"];
                } else {

                    // 판매단가 - 마이너스(-)외에 다른 문자 입력 불가
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
                // 변경내점-판매가 - 소수점 입력 불가
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].stinSaleUprc)) == false){
                    $scope.flex.collectionView.items[i].stinSaleUprc = "";
                    result = messages["simpleProd.stinSaleUprc"] + messages["simpleProd.uprcChk.msg"];
                } else {
                    // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].stinSaleUprc)) {
                        if ((numchkexp2.test($scope.flex.collectionView.items[i].stinSaleUprc) == false)) {
                            $scope.flex.collectionView.items[i].stinSaleUprc = "";
                            result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }

                    // 변경내점-판매가 - 1000000000 이상 입력 불가
                    if ($scope.flex.collectionView.items[i].stinSaleUprc >= 1000000000) {
                        $scope.flex.collectionView.items[i].stinSaleUprc = "";
                        result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                    // 변경내점-판매가 - -1000000000 이하 입력 불가
                    if ($scope.flex.collectionView.items[i].stinSaleUprc <= -1000000000) {
                        $scope.flex.collectionView.items[i].stinSaleUprc = "";
                        result = messages["salePrice.stinSaleUprcInChk"]; // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                }
            }

            // 배달가
            if($scope.flex.collectionView.items[i].dlvrSaleUprc !== "" && $scope.flex.collectionView.items[i].dlvrSaleUprc !== null) {
                // 변경배달-판매가 - 소수점 입력 불가
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].dlvrSaleUprc)) == false){
                    $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                    result = messages["simpleProd.dlvrSaleUprc"] + messages["simpleProd.uprcChk.msg"];
                } else {
                    // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].dlvrSaleUprc)) {
                        if ((numchkexp2.test($scope.flex.collectionView.items[i].dlvrSaleUprc) == false)) {
                            $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                            result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                    // 변경배달-판매가 - 1000000000 이상 입력 불가
                    if ($scope.flex.collectionView.items[i].dlvrSaleUprc >= 1000000000) {
                        $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                        result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                    // 변경배달-판매가 - -1000000000 이하 입력 불가
                    if ($scope.flex.collectionView.items[i].dlvrSaleUprc <= -1000000000) {
                        $scope.flex.collectionView.items[i].dlvrSaleUprc = "";
                        result = messages["salePrice.dlvrSaleUprcInChk"]; // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                }
            }

            // 포장가
            if($scope.flex.collectionView.items[i].packSaleUprc !== "" && $scope.flex.collectionView.items[i].packSaleUprc !== null) {
                // 변경포장-판매가 - 소수점 입력 불가
                if(Number.isInteger(parseFloat($scope.flex.collectionView.items[i].packSaleUprc)) == false){
                    $scope.flex.collectionView.items[i].packSaleUprc = "";
                    result = messages["simpleProd.packSaleUprc"] + messages["simpleProd.uprcChk.msg"];
                } else {
                    // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test($scope.flex.collectionView.items[i].packSaleUprc)) {
                        if ((numchkexp2.test($scope.flex.collectionView.items[i].packSaleUprc) == false)) {
                            $scope.flex.collectionView.items[i].packSaleUprc = "";
                            result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                        }
                    }
                    // 변경포장-판매가 - 1000000000 이상 입력 불가
                    if ($scope.flex.collectionView.items[i].packSaleUprc >= 1000000000) {
                        $scope.flex.collectionView.items[i].packSaleUprc = "";
                        result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                    // 변경포장-판매가 - -1000000000 이하 입력 불가
                    if ($scope.flex.collectionView.items[i].packSaleUprc <= -1000000000) {
                        $scope.flex.collectionView.items[i].packSaleUprc = "";
                        result = messages["salePrice.packSaleUprcInChk"]; // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                    }
                }
            }

            // 상품명
            if($scope.flex.collectionView.items[i].prodNm === "" || $scope.flex.collectionView.items[i].prodNm === null) {
                result = messages["simpleProd.prodNmBlank"]; // 상품명을 입력하세요.
            } else {
                // 최대길이 체크
                if(nvl($scope.flex.collectionView.items[i].prodNm, '').getByteLengthForOracle() > 100) { result = messages["simpleProd.prodNmLengthChk"]; } // 상품명 길이가 너무 깁니다.

                // 상품명 중복체크
                if($scope.isChecked === true) {
                    for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                        if(i !== j) {
                            if($scope.flex.collectionView.items[j].prodNm !== "" && $scope.flex.collectionView.items[j].prodNm !== null) {
                                if($scope.flex.collectionView.items[j].prodNm === $scope.flex.collectionView.items[i].prodNm) { result = messages["simpleProd.prodNmChk"]; } // 상품명이 중복됩니다.
                            }
                        }
                    }
                }

                // 큰따옴표(") 입력 불가
                if ($scope.flex.collectionView.items[i].prodNm.indexOf("\"") >= 0) { result = messages["simpleProd.prodNmTextChk"]; } // 상품명에 큰따옴표(")를 입력할 수 없습니다.
            }

            // 상품코드
            // 수동채번일때만 체크
            if(prodNoEnv === "1") {
                if ($scope.flex.collectionView.items[i].prodCd === "" || $scope.flex.collectionView.items[i].prodCd === null) {
                    result = messages["simpleProd.prodCdBlank"]; // 상품코드를 입력하세요.
                } else {
                    // 숫자/영문만 입력
                    var numChkexp = /[^A-Za-z0-9]/g;
                    if (numChkexp.test($scope.flex.collectionView.items[i].prodCd)) { result = messages["simpleProd.prodCdInChk"]; } // 상품코드 숫자/영문만 입력해주세요.

                    // 최대길이 체크
                    if (nvl($scope.flex.collectionView.items[i].prodCd, '').getByteLengthForOracle() > 13) { result = messages["simpleProd.prodCdLengthChk"]; } // 상품코드 길이가 너무 깁니다.

                    // 상품코드 중복체크
                    for (var j = 0; j < $scope.flex.collectionView.items.length; j++) {
                        if (i !== j) {
                            if($scope.flex.collectionView.items[j].prodCd !== "" && $scope.flex.collectionView.items[j].prodCd !== null) {
                                if ($scope.flex.collectionView.items[j].prodCd === $scope.flex.collectionView.items[i].prodCd) { result = messages["simpleProd.prodCdChk"]; } // 상품코드가 중복됩니다.
                            }
                        }
                    }
                }
            }

            // 포인트사용여부
            if($scope.flex.collectionView.items[i].pointUseYn === "" || $scope.flex.collectionView.items[i].pointUseYn === null) { result = messages["simpleProd.pointUseYn"] + messages["cmm.require.select"]; } // 포인트사용여부(을)를 선택해주세요.
            
            // 할인여부
            if($scope.flex.collectionView.items[i].dcYn === "" || $scope.flex.collectionView.items[i].dcYn === null) { result = messages["simpleProd.dcYn"] + messages["cmm.require.select"]; } // 할인여부(을)를 선택해주세요.

            if (orgnFg == "STORE") {
                // 코너
                if($scope.flex.collectionView.items[i].cornrCd === "" || $scope.flex.collectionView.items[i].cornrCd === null) { result = messages["simpleProd.corner"] + messages["cmm.require.select"]; } // 코너(을)를 선택해주세요.
            }

            $scope.flex.collectionView.items[i].result = result;
            // <-- //검증 -->


            params.push($scope.flex.collectionView.items[i]);
        }

        // 검증결과 저장
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._postJSONSave.withOutPopUp("/base/prod/simpleProd/simpleProd/getSimpleProdCheckSave.sb", params, function(){
            // 검증결과 조회
            $scope.searchSimpleProdCheckList("select");
            $scope._popConfirm(messages["simpleProd.saveConfirm"], function() {
                // 상품등록 저장
                $scope.SimpleProdSave();
            });
        });
    };

    // 검증결과 조회
    $scope.searchSimpleProdCheckList = function(data) {
        var gubun = data;

        var params = {};

        $scope._inquirySub("/base/prod/simpleProd/simpleProd/getSimpleProdList.sb", params, function() {
            if(gubun === "save") {
                var rowsCount = 25 - $scope.flex.collectionView.items.length;
                // 그리드 셋팅
                $scope.searchSimpleProdDefault(rowsCount);
            }
        }, false);
    };

    // 상품등록 저장
    $scope.SimpleProdSave = function() {
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            // 상품코드 채번방식
            var prodNoEnv = "";
            if($scope.prodNoEnvFg === "MANUAL") { prodNoEnv = "1"; } else { prodNoEnv = "0"; } // 0 : 자동채번, 1 : 수동채번
            $scope.flex.collectionView.items[i].prodNoEnv = prodNoEnv;
            $scope.flex.collectionView.items[i].gubun = "simpleProd";
            params.push($scope.flex.collectionView.items[i]);
        }

        $scope._save("/base/prod/simpleProd/simpleProd/getSimpleProdSave.sb", params, function(){
            // 검증결과 조회
            $scope.searchSimpleProdCheckList("save");
        });
    };

}]);