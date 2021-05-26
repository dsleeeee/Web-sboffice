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

app.controller('regProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분
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
    });

    // 등록 상품 조회
    $scope.searchRegProd = function(){
        var params        = {};
        params.prodCd    = '';
        params.prodNm    = '';
        params.hqOfficeCd = $("#hdHqOfficeCd").val();
        params.storeCd     = $("#hdStoreCd").val();
        params.prodRegFg = 'Y';

        $scope._inquirySub("/base/prod/prod/prod/getStoreProdBatchList.sb", params, function() {
            // 미등록상품 조회
            var noRegProdGrid = agrid.getScope("noRegProdCtrl");
            noRegProdGrid._pageView('noRegProdCtrl', 1);
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
                var saleUprcB = $scope.flex.collectionView.items[i].saleUprcB; // 기존 판매가
                var saleUprc = $scope.flex.collectionView.items[i].saleUprc; // 현재 판매가

                if(saleUprc !== saleUprcB){
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
        var noRegProdGrid = agrid.getScope("noRegProdCtrl");
        noRegProdGrid.searchNoRegProd();
    };

}]);


/**
 *  상품 미적용 매장 그리드 생성
 */
app.controller('noRegProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

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

        $scope._inquirySub("/base/prod/prod/prod/getStoreProdBatchList.sb", params, function() {}, false);
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
        $scope.searchNoRegProd();
        var regProdGrid = agrid.getScope("regProdCtrl");
        regProdGrid.searchRegProd();
    };

}]);