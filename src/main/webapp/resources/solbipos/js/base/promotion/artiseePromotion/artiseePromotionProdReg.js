/****************************************************************
 *
 * 파일명 : artiseePromotionProdReg.js
 * 설  명 : 아티제전용프로모션 상품추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.17     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('artiseePromotionProdRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('artiseePromotionProdRegCtrl', $scope, $http, false));

    $scope._setComboData("prodUseYnAll", useYnAllFgData); // 사용여부

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.prodUseYnFgDataMap = new wijmo.grid.DataMap(useYnFgData, 'value', 'name'); //사용여부
    };

    // 팝업 오픈 시, 상품리스트 조회
    $scope.$on("artiseePromotionProdRegCtrl", function(event, data) {

        // 상품조회
        $scope.searchProd();
        event.preventDefault();

    });

    // 상품조회
    $scope.searchProd = function () {

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.prodCd = $("#srchProdCd").val();
        params.prodNm = $("#srchProdNm").val();
        params.useYn = $scope.srchProdUseYnCombo.selectedValue;

        $scope._inquirySub("/base/promotion/artiseePromotion/getProdList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchProd = function(){

        // 상품조회
        $scope.searchProd();
    };

    // 추가
    $scope.btnInsertProd = function () {

        // 파라미터 설정
        var params = new Array();

        // 조건수량이 수정된 내역이 있는지 체크
        if ($scope.flex.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
        }

        // 선택한 상품 또는 분류가 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("추가할 " + messages["artiseePromotion.product"] + "의 체크박스" + messages["artiseePromotion.chk.item"]); // 추가할 상품의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {

            var item = $scope.flex.collectionView.itemsEdited[i];

            if (item.gChk === true && (item.giftQty === null || item.giftQty === "" || item.giftQty === "0" || item.giftQty === 0)) {
                $scope._popMsg(messages["artiseePromotion.chk.giftQty"]); // 선택한 항목의 조건수량을 반드시 입력하세요.
                return false;
            }

            if(item.gChk === true) {
                var obj = {};
                obj.status = "I";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.prodCd = item.prodCd;
                obj.giftQty = item.giftQty;

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/artiseePromotion/savePromotionProd.sb", params, function () {

            $scope.closeProd();
            $scope.artiseePromotionProdRegLayer.hide(true);

            // 적용상품/분류 목록 재조회
            $scope._pageView('artiseePromotionSelectProdGridCtrl', 1);

        });
    };

    // 닫기 시 초기화
    $scope.closeProd = function () {
        $("#srchProdCd").val("");
        $("#srchProdNm").val("");
        $scope.srchProdUseYnCombo.selectedIndex = 0;
    }

}]);