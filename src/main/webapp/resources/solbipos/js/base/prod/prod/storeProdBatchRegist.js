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

    $scope.storeSaleUprcApply = true;

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
    };

    // 상품분류정보 팝업
    $scope.popUpStoreProdClass = function() {
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
                        $("#_storeProdClassCd").val(prodClassCd);
                        // $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delStoreProdClass = function(){
        $("#_storeProdClassCd").val("");
        // $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

    $scope.setHqBrandCd = function (){
        $("#_srchHqBrand").val($scope.hqBrandCd);
    }
}]);

app.controller('regProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
        params.prodClassCd = $("#_storeProdClassCd").val();

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