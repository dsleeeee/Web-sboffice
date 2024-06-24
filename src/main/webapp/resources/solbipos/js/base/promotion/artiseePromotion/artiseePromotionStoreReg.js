/****************************************************************
 *
 * 파일명 : artiseePromotionStoreReg.js
 * 설  명 : 아티제전용프로모션 적용매장 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.06.18     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('artiseePromotionStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('artiseePromotionStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchSysStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("artiseePromotionStoreRegCtrl", function (event, data) {

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;

        $scope._inquirySub("/base/promotion/artiseePromotion/getStoreList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchStore = function(){

        // 매장조회
        $scope.searchStore();
    };

    // 추가
    $scope.btnInsertStore = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("추가할 " + messages["artiseePromotion.store"] + "의 체크박스" + messages["artiseePromotion.chk.item"]); // 추가할 매장의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            var item = $scope.flex.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "I";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.storeCd = item.storeCd;

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/artiseePromotion/savePromotionStore.sb", params, function () {

            $scope.artiseePromotionStoreRegLayer.hide(true);

            // 적용상품 목록 재조회
            $scope._pageView('artiseePromotionSelectStoreGridCtrl', 1);

        });
    };

    // 전매장적용
    $scope.btnInsertStoreAll = function () {

        var msg = messages["promotion.chk.setAllStore"]; // 해당 프로모션을 전매장에 적용하시겠습니까?

        $scope._popConfirm(msg, function() {
            var params = {};
            params.promotionCd = $("#hdPromotionCd").val();

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/promotion/artiseePromotion/insertPromotionStoreAll.sb", params, function () {

                $scope.artiseePromotionStoreRegLayer.hide(true);

                // 적용상품 목록 재조회
                $scope._pageView('artiseePromotionSelectStoreGridCtrl', 1);

            });
        });
    };

    // 닫기
    $scope.closeStoreReg = function () {
        $("#srchStoreCd").val("");
        $("#srchStoreNm").val("");
        $scope.srchSysStatFgCombo.selectedIndex = 0;
    };
}]);