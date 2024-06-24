/****************************************************************
 *
 * 파일명 : artiseePromotionClassReg.js
 * 설  명 : 아티제전용프로모션 분류추가 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.17     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('artiseePromotionClassRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('artiseePromotionClassRegCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈 시, 상품리스트 조회
    $scope.$on("artiseePromotionClassRegCtrl", function(event, data) {

        // 분류조회
        $scope.searchClass();
        event.preventDefault();
    });

    // 분류조회
    $scope.searchClass = function () {

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.prodClassCd = $("#srchClassCd").val();
        params.prodClassNm = $("#srchClassNm").val();

        $scope._inquirySub("/base/promotion/artiseePromotion/getClassList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchClass = function(){

        // 분류조회
        $scope.searchClass();
    };

    // 추가
    $scope.btnInsertClass = function () {

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
            $scope._popMsg("추가할 " + messages["artiseePromotion.class"] + "의 체크박스" + messages["artiseePromotion.chk.item"]); // 추가할 분류의 체크박스를을(를) 선택하세요.
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
                obj.prodCd = item.prodClassCd;
                obj.giftQty = item.giftQty;

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/artiseePromotion/savePromotionProd.sb", params, function () {

            $scope.closeClass();
            $scope.artiseePromotionClassRegLayer.hide(true);

            // 적용상품/분류 목록 재조회
            $scope._pageView('artiseePromotionSelectProdGridCtrl', 1);

        });
    };

    // 닫기 시 초기화
    $scope.closeClass = function () {
        $("#srchClassCd").val("");
        $("#srchClassNm").val("");
    }

}]);