/****************************************************************
 *
 * 파일명 : couponProdCls.js
 * 설  명 : 적용대상소분류등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.14     김유승      1.0
 *
 * **************************************************************/


/**
 *  쿠폰 등록 상품 그리드 생성
 */
app.controller('regProdClsCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regProdClsCtrl', $scope, $http, true));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    // 쿠폰등록 상품 그리드 조회
    $scope.$on("regProdClsCtrl", function(event, data) {
        if ($("#couponProdClsTitle").text().substr(1,3) < 800 && orgnFg == "STORE") { // 매장이 본사에서 등록한 쿠폰에 상품등록X
            $("#couponProdClsLayer .btn_grayS2").hide();
        } else {
            $("#couponProdClsLayer .btn_grayS2").show();
        }

        $scope.searchRegProdCls();
        // 등록상품 조회 후, 미등록상품 조회
        var noRegCouponGrid = agrid.getScope("noRegProdClsCtrl");
        noRegCouponGrid._pageView('noRegProdClsCtrl', 1);

        event.preventDefault();
    });

    // 등록된 상품 조회
    $scope.searchRegProdCls = function(){
        if(selectedCoupon != null && selectedCoupon != ""){
            var params = {};

            // params.prodCd = $("#srchProdCd").val();
            // params.prodNm = $("#srchProdNm").val();
            params.prodCd = '';
            params.prodNm = '';

            params.payClassCd = selectedCouponClass.payClassCd;
            params.coupnCd = selectedCoupon.coupnCd;
            params.coupnEnvstVal = coupnEnvstVal;
            params.prodRegFg = "Y";

            // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
            $scope._inquirySub(baseUrl + "prod/getProdClsList.sb", params, function() {}, false);
        }
    };

    // 등록 상품 삭제
    $scope.delete = function(){

        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].payClassCd = selectedCouponClass.payClassCd;
                $scope.flex.collectionView.items[i].coupnCd = selectedCoupon.coupnCd;
                $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
                $scope.flex.collectionView.items[i].hqOfficeCd = hqOfficeCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "prod/deleteCouponProdCls.sb", params, function(){ $scope.allSearch() });
    };

    // 상품 삭제 완료 후처리
    $scope.allSearch = function () {
        $scope.searchRegProdCls();
        var noregProdClsGrid = agrid.getScope("noRegProdClsCtrl");
        noregProdClsGrid._pageView('noRegProdClsCtrl', 1);
    };
}]);

/**
 *  쿠폰 미등록 상품 그리드 생성
 */
app.controller('noRegProdClsCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegProdClsCtrl', $scope, $http, true));
    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 미등록 상품 그리드 조회
    $scope.$on("noRegProdClsCtrl", function(event, data) {
        $scope.searchNoRegProdCls();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 미등록 상품
    $scope.searchNoRegProdCls = function(){
        if(selectedCoupon != null && selectedCoupon != ""){
            // 파라미터
            var params = {};

            // params.listScale = "10";
            params.prodCd = $("#srchProdClsCd").val();
            params.prodNm = $("#srchProdClsNm").val();
            params.payClassCd = selectedCouponClass.payClassCd;
            params.coupnCd = selectedCoupon.coupnCd;
            params.coupnEnvstVal = coupnEnvstVal;
            params.prodRegFg = "N";

            // console.log(params);
            // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
            $scope._inquirySub(baseUrl + "prod/getProdClsList.sb", params, function() {}, false);
        }
    };

    // 상품 등록
    $scope.regist = function() {
        var couponClassGrid = agrid.getScope("couponClassCtrl");
        var couponGrid = agrid.getScope("couponCtrl");
        var selectedRow = couponGrid.flex.selectedRows[0]._data;

        var params = [];

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            $scope.flex.collectionView.items[i].payClassCd = couponClassGrid.flex.selectedItems[0].payClassCd;
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].coupnCd = selectedRow.coupnCd;
                $scope.flex.collectionView.items[i].coupnEnvstVal = coupnEnvstVal;
                $scope.flex.collectionView.items[i].hqOfficeCd = hqOfficeCd;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "prod/registCouponProdCls.sb", params, function(){
            $scope.allSearch()
        });
    };

    // 상품 등록 완료 후처리 (상품수량 변화)
    $scope.allSearch = function () {
        $scope.searchNoRegProdCls();
        var regProdClsGrid = agrid.getScope("regProdClsCtrl");
        regProdClsGrid._pageView('regProdClsCtrl',1);
    };
}]);

