/****************************************************************
 *
 * 파일명 : couponExceptProd.js
 * 설  명 : 제외상품등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.14     김유승      1.0
 *
 * **************************************************************/


/**
 *  쿠폰 등록 상품 그리드 생성
 */
app.controller('regExceptProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('regExceptProdCtrl', $scope, $http, true));

    $scope._setComboData("useYn", useYnComboData);
    $scope._setComboData('prodTypeFg', prodTypeFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');           // 사용여부
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFg, 'value', 'name'); // 상품유형구분
    };

    // 쿠폰등록 상품 그리드 조회
    $scope.$on("regExceptProdCtrl", function(event, data) {
        if ($("#couponExceptProdTitle").text().substr(1,3) < 800 && orgnFg == "STORE") { // 매장이 본사에서 등록한 쿠폰에 상품등록X
            $("#couponExceptProdLayer .btn_grayS2").hide();
        } else {
            $("#couponExceptProdLayer .btn_grayS2").show();
        }

        $scope.searchRegExceptProd();
        // 등록상품 조회 후, 미등록상품 조회
        var noRegCouponGrid = agrid.getScope("noRegExceptProdCtrl");
        noRegCouponGrid._pageView('noRegExceptProdCtrl', 1);

        event.preventDefault();
    });

    // 등록된 상품 조회
    $scope.searchRegExceptProd = function(){
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
            $scope._inquirySub(baseUrl + "prod/getExceptProdList.sb", params, function() {}, false);
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
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "prod/deleteCouponProdExcept.sb", params, function(){ $scope.allSearch() });
    };

    // 상품 삭제 완료 후처리
    $scope.allSearch = function () {
        $scope.searchRegExceptProd();
        var noRegExceptProdGrid = agrid.getScope("noRegExceptProdCtrl");
        noRegExceptProdGrid._pageView('noRegExceptProdCtrl', 1);
    };

    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
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
                    function (response) {
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function () {
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };
}]);

/**
 *  쿠폰 미등록 상품 그리드 생성
 */
app.controller('noRegExceptProdCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('noRegExceptProdCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');           // 사용여부
        $scope.prodTypeFgDataMap = new wijmo.grid.DataMap(prodTypeFg, 'value', 'name'); // 상품유형구분
    };

    // 미등록 상품 그리드 조회
    $scope.$on("noRegExceptProdCtrl", function(event, data) {
        $scope.searchNoRegExceptProd();

        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 미등록 상품
    $scope.searchNoRegExceptProd = function(){
        if(selectedCoupon != null && selectedCoupon != ""){
            // 파라미터
            var params = {};

            // params.listScale = "10";
            params.prodCd = $("#srchExceptProdCd").val();
            params.prodNm = $("#srchExceptProdNm").val();
            params.prodClassCd = agrid.getScope('regExceptProdCtrl').prodClassCd;
            params.barCd = agrid.getScope('regExceptProdCtrl').barcdCd;
            params.strUseYn = agrid.getScope('regExceptProdCtrl').useYnCombo.selectedValue;
            params.prodTypeFg = agrid.getScope('regExceptProdCtrl').prodTypeFgCombo.selectedValue;
            params.payClassCd = selectedCouponClass.payClassCd;
            params.coupnCd = selectedCoupon.coupnCd;
            params.coupnEnvstVal = coupnEnvstVal;
            params.prodRegFg = "N";

            // console.log(params);
            // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
            $scope._inquirySub(baseUrl + "prod/getExceptProdList.sb", params, function() {}, false);
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
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save(baseUrl + "prod/registCouponProdExcept.sb", params, function(){
            $scope.allSearch()
        });
    };

    // 상품 등록 완료 후처리 (상품수량 변화)
    $scope.allSearch = function () {
        var regCouponGrid = agrid.getScope("regExceptProdCtrl");
        regCouponGrid._pageView('regExceptProdCtrl', 1);
    };
}]);

