/****************************************************************
 *
 * 파일명 : storeProdBatchRegist.js
 * 설  명 : 매장 상품 일괄적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.02.13     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('srchProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('srchProdCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData('useYnAllComboData', useYnAllComboData);

    $scope.storeSaleUprcApply = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
    };

    $scope.setHqBrandCd = function (){
        $("#_srchHqBrand").val($scope.hqBrandCd);
    }

    $scope.setBatchUseYn = function () {

        if ($scope.useYn === undefined) {
            if (hqOfficeCd != "A0001") {
                $scope.useYn = "";
            }
            if (hqOfficeCd == "A0001") {
                $scope.useYn = "Y";
            }
        }

        $("#_srchBatchUseYn").val($scope.useYn);
    }

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.originalStoreShow = function () {
        $scope._broadcast('originalStoreCtrl', $("#hdStoreCd").val());
    };
}]);

app.controller('regProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "saleUprc" || col.binding === "stinSaleUprc" || col.binding === "dlvrSaleUprc" || col.binding === "packSaleUprc") {
                    item.gChk = true;
                }
                // 판매가 변경시 다른 컬럼값도 변경
                if (col.binding === "saleUprc") {
                    if($("#storeSaleUprcApply").prop("checked")){
                        item.stinSaleUprc = item.saleUprc;
                        item.dlvrSaleUprc = item.saleUprc;
                        item.packSaleUprc = item.saleUprc;
                    }
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // 다른 컨트롤러의 broadcast 받기
    // 등록 상품 그리드 조회
    $scope.$on("regProdCtrl", function(event, data) {
        if(data !== undefined){
            // 매장명
            $("#storeTitle").text('['+data.storeCd +'] '+data.storeNm);
            $("#hdHqOfficeCd").val(data.hqOfficeCd);
            $("#hdStoreCd").val(data.storeCd);
            $("#hdStoreNm").val(data.storeNm);

            // 이전 검색조건 초기화
            $("#srchBatProdCd").val("");
            $("#srchBatProdNm").val("");
        }

        // 등록상품조회
        $scope.searchRegProd();
        event.preventDefault();
    });

    // 등록 상품 조회
    $scope.searchRegProd = function(){
        var params        = {};
        params.prodCd    = '';
        params.prodNm    = '';
        params.hqOfficeCd = $("#hdHqOfficeCd").val();
        params.storeCd     = $("#hdStoreCd").val();
        params.prodRegFg = 'Y';
        params.hqBrandCd = '';
        params.prodClassCd = '';
        params.originalStore = '';
        params.useYn = '';

        $scope._inquirySub("/base/prod/prod/prod/getStoreProdBatchList.sb", params, function() {
            // 미등록상품 조회
            $scope._broadcast("noRegProdCtrl");
        });
    };

    // 판매가 변경
    $scope.changeSaleUprc = function(){
        // 그리드 변경된 건 커밋
        $scope.flex.collectionView.commitEdit();

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // PRC_CTRL_FG 가격관리구분 H인 상품만 수정가능
                if($scope.flex.collectionView.items[i].prcCtrlFg === "S") {
                    $scope._popMsg(messages["prod.prcCtrlFgStoreBlank"]); // 가격관리구분이 '매장'인 상품은 수정할 수 없습니다.
                    return false;
                }
            }
        }

        var params = new Array();
        var numchkexp = /[^0-9]/; // 숫자가 아닌 값 체크
        var numchkexp2 = /^-?[0-9]+$/;

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                var saleUprcB       = $scope.flex.collectionView.items[i].saleUprcB;        // 기존 판매가
                var saleUprc        = $scope.flex.collectionView.items[i].saleUprc;         // 현재 판매가
                var stinSaleUprcB   = $scope.flex.collectionView.items[i].stinSaleUprcB;    // 기존 내점가
                var stinSaleUprc    = $scope.flex.collectionView.items[i].stinSaleUprc;     // 현재 내점가
                var dlvrSaleUprcB   = $scope.flex.collectionView.items[i].dlvrSaleUprcB;    // 기존 배달가
                var dlvrSaleUprc    = $scope.flex.collectionView.items[i].dlvrSaleUprc;     // 현재 배달가
                var packSaleUprcB   = $scope.flex.collectionView.items[i].packSaleUprcB;    // 기존 포장가
                var packSaleUprc    = $scope.flex.collectionView.items[i].packSaleUprc;     // 현재 포장가

                if(saleUprc !== saleUprcB || stinSaleUprc !== stinSaleUprcB || dlvrSaleUprc !== dlvrSaleUprcB || packSaleUprc !== packSaleUprcB){

                    // isInteger는 es6 임. ie 11 에서는 안되므로 함수 만듬.
                    Number.isInteger = Number.isInteger || function(value) {
                        return typeof value === "number" &&
                            isFinite(value) &&
                            Math.floor(value) === value;
                    };

                    // 변경판매가 - 소수점 입력 불가
                    if(Number.isInteger(parseFloat(saleUprc)) == false){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 변경판매가 - 마이너스(-)외에 다른 문자 입력 불가
                    if (numchkexp.test(saleUprc)) {
                        if((numchkexp2.test(saleUprc) == false)){
                            $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                            return false;
                        }
                    }

                    // 변경판매가 - 1000000000 이상 입력 불가
                    if(saleUprc >= 1000000000){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }
                    // 변경판매가 - -1000000000 이하 입력 불가
                    if(saleUprc <= -1000000000){
                        $scope._popMsg(messages["salePrice.saleUprcInChk"]); // 변경판매가는 숫자만(정수9자리) 입력해주세요.
                        return false;
                    }

                    // 내점/배달/포장 판매가 사용 시
                    if(subPriceFg === "1") {

                        // 변경내점-판매가 입력했을 경우 체크
                        if ($scope.flex.collectionView.items[i].stinSaleUprc !== "" && stinSaleUprc !== null) {

                            // 변경내점-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat(stinSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경내점-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test(stinSaleUprc)) {
                                if ((numchkexp2.test(stinSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경내점-판매가 - 1000000000 이상 입력 불가
                            if (stinSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                            // 변경내점-판매가 - -1000000000 이하 입력 불가
                            if (stinSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.stinSaleUprcInChk"]); // 변경내점-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경배달-판매가 입력했을 경우 체크
                        if (dlvrSaleUprc !== "" && dlvrSaleUprc !== null) {

                            // 변경배달-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat(dlvrSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경배달-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test(dlvrSaleUprc)) {
                                if ((numchkexp2.test(dlvrSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경배달-판매가 - 1000000000 이상 입력 불가
                            if (dlvrSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                            // 변경배달-판매가 - -1000000000 이하 입력 불가
                            if (dlvrSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.dlvrSaleUprcInChk"]); // 변경배달-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }

                        // 변경포장-판매가 입력했을 경우 체크
                        if (packSaleUprc !== "" && packSaleUprc !== null) {

                            // 변경포장-판매가 - 소수점 입력 불가
                            if (Number.isInteger(parseFloat(packSaleUprc)) == false) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }

                            // 변경포장-판매가 - 마이너스(-)외에 다른 문자 입력 불가
                            if (numchkexp.test(packSaleUprc)) {
                                if ((numchkexp2.test(packSaleUprc) == false)) {
                                    $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                    return false;
                                }
                            }

                            // 변경포장-판매가 - 1000000000 이상 입력 불가
                            if (packSaleUprc >= 1000000000) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                            // 변경포장-판매가 - -1000000000 이하 입력 불가
                            if (packSaleUprc <= -1000000000) {
                                $scope._popMsg(messages["salePrice.packSaleUprcInChk"]); // 변경포장-판매가는 숫자만(정수9자리) 입력해주세요.
                                return false;
                            }
                        }
                    }else{
                        $scope.flex.collectionView.items[i].stinSaleUprc = saleUprc;
                        $scope.flex.collectionView.items[i].dlvrSaleUprc = saleUprc;
                        $scope.flex.collectionView.items[i].packSaleUprc = saleUprc;
                    }

                    $scope.flex.collectionView.items[i].hqOfficeCd = $("#hdHqOfficeCd").val();
                    $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }
        }
        $scope._save("/base/prod/prod/prod/updateStoreSaleUprc.sb", params, function(){ $scope.allSearch() });
    };

    // 상품삭제
    $scope.delete = function(){
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].hqOfficeCd = $("#hdHqOfficeCd").val();
                $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/deleteProdStore.sb", params, function(){ $scope.allSearch() });
    };

    // 매장 삭제 완료 후처리
    $scope.allSearch = function () {
        $scope.searchRegProd();
    };
}]);


/**
 *  상품 미적용 매장 그리드 생성
 */
app.controller('noRegProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.useYnFgDataMap = new wijmo.grid.DataMap(useYnComboData, 'value', 'name'); // 사용여부
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
    };

    // 미등록 매장 그리드 조회
    $scope.$on("noRegProdCtrl", function(event, data) {
        $scope.searchNoRegProd();
        event.preventDefault();
    });

    // 미등록 매장 조회
    $scope.searchNoRegProd = function(){
        var params        = {};
        params.prodCd    = $("#srchBatProdCd").val();
        params.prodNm    = $("#srchBatProdNm").val();
        params.hqOfficeCd = $("#hdHqOfficeCd").val();
        params.storeCd     = $("#hdStoreCd").val();
        params.prodRegFg = 'N';
        params.hqBrandCd = $("#_srchHqBrand").val();
        params.prodClassCd = $("#storeProdBatchRegistSelectClassCd").val();
        params.originalStore = $("#originalStoreCd").val();
        params.useYn = $("#_srchBatchUseYn").val();

        $scope._inquirySub("/base/prod/prod/prod/getStoreProdBatchList.sb", params);
    };

    // 상품등록
    $scope.regist = function(){
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].hqOfficeCd = $("#hdHqOfficeCd").val();
                $scope.flex.collectionView.items[i].storeCd = $("#hdStoreCd").val();
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/prod/prod/insertStoreProdBatch.sb", params, function(){ $scope.allSearch() });
    };

    // 매장 등록 완료 후처리
    $scope.allSearch = function () {
        var scope = agrid.getScope("regProdCtrl");
        scope.searchRegProd();
    };

}]);