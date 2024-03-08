/****************************************************************
 *
 * 파일명 : promotionMomsStoreReg.js
 * 설  명 : 프로모션 적용매장 팝업(맘스터치) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.01     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('promotionMomsStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('promotionMomsStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchMomsStoreHqBrand", momsStoreHqBrand);       // 매장브랜드
    $scope._setComboData("srchMomsBranch", momsBranch);                   // 그룹
    $scope._setComboData("srchMomsTeam", momsTeam);                       // 팀별
    $scope._setComboData("srchMomsAcShop", momsAcShop);                   // AC점포별
    $scope._setComboData("srchMomsAreaFg", momsAreaFg);                   // 지역구분
    $scope._setComboData("srchMomsCommercial", momsCommercial);           // 상권
    $scope._setComboData("srchMomsShopType", momsShopType);               // 점포유형
    $scope._setComboData("srchMomsStoreManageType", momsStoreManageType); // 매장관리타입
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05); // 매장그룹5

    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("promotionMomsStoreRegCtrl", function(event, data) {

        // 매장조회
        $scope.searcMomshStore();
        event.preventDefault();
    });

    // 매장조회
    $scope.searcMomshStore = function () {

        // 파라미터
        var params = {};
        params.promotionCd = $("#hdPromotionCd").val();
        params.storeCd = $("#srchMomsStoreCd").val();
        params.storeNm = $("#srchMomsStoreNm").val();
        params.storeHqBrandCd = $scope.srchMomsStoreHqBrandCombo.selectedValue;
        params.branchCd = $scope.srchMomsBranchCombo.selectedValue;
        params.momsTeam = $scope.srchMomsTeamCombo.selectedValue;
        params.momsAcShop = $scope.srchMomsAcShopCombo.selectedValue;
        params.momsAreaFg = $scope.srchMomsAreaFgCombo.selectedValue;
        params.momsCommercial = $scope.srchMomsCommercialCombo.selectedValue;
        params.momsShopType = $scope.srchMomsShopTypeCombo.selectedValue;
        params.momsStoreManageType = $scope.srchMomsStoreManageTypeCombo.selectedValue;
        // 매장브랜드 '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsStoreHqBrand.length; i++){
                if(momsStoreHqBrand[i].value !== null) {
                    momsHqBrandCd += momsStoreHqBrand[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;

        $scope._inquirySub("/base/promotion/promotion/getMomsStoreList.sb", params, function () {});
    };

    // 조회
    $scope.btnMomsSearchStore = function () {
        // 매장조회
        $scope.searcMomshStore();
    };

    // 추가
    $scope.btnMomsInsertStore = function () {

        var storeSelectExceptFg = agrid.getScope("promotionRegCtrl").storeSelectExceptFgCombo.selectedValue; // 매장등록구분

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("추가할 " + messages["promotion.store"] + "의 체크박스" + messages["promotion.chk.item"]); // 추가할 매장의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            var item = $scope.flex.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "I";
                obj.promotionCd = $("#hdPromotionCd").val();
                obj.storeCd = item.storeCd;
                obj.verSerNo = $("#hdFileNo").val();
                obj.storeSelectExceptFg = storeSelectExceptFg;

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/promotion/savePromotionStore.sb", params, function () {

            $scope.promotionMomsStoreRegLayer.hide(true);

            // 적용상품 목록 재조회
            $scope._pageView('promotionSelectStoreGridCtrl', 1);

        });
    };

    // 전매장적용
    $scope.btnMomsInsertStoreAll = function () {

        var storeSelectExceptFg = agrid.getScope("promotionRegCtrl").storeSelectExceptFgCombo.selectedValue; // 매장등록구분
        var msg = messages["promotion.chk.setAllStore"]; // 해당 프로모션을 전매장에 적용하시겠습니까?

        if(storeSelectExceptFg === "1"){
            msg = messages["promotion.chk.setAllStoreExcept"]; // 해당 프로모션을 전매장에서 제외하시겠습니까?
        }

        $scope._popConfirm(msg, function() {
            var params = {};
            params.promotionCd = $("#hdPromotionCd").val();
            params.verSerNo = $("#hdFileNo").val();
            params.storeSelectExceptFg = storeSelectExceptFg;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/promotion/promotion/insertPromotionStoreAll.sb", params, function () {

                $scope.promotionMomsStoreRegLayer.hide(true);

                // 적용상품 목록 재조회
                $scope._pageView('promotionSelectStoreGridCtrl', 1);

            });
        });
    };
    
    // 닫기
    $scope.closeMomsStoreReg = function () {
        $("#srchMomsStoreCd").val("");
        $("#srchMomsStoreNm").val("");
        $scope.srchMomsStoreHqBrandCombo.selectedIndex = 0;
        $scope.srchMomsBranchCombo.selectedIndex = 0;
        $scope.srchMomsTeamCombo.selectedIndex = 0;
        $scope.srchMomsAcShopCombo.selectedIndex = 0;
        $scope.srchMomsAreaFgCombo.selectedIndex = 0;
        $scope.srchMomsCommercialCombo.selectedIndex = 0;
        $scope.srchMomsShopTypeCombo.selectedIndex = 0;
        $scope.srchMomsStoreManageTypeCombo.selectedIndex = 0;
        $scope.srchMomsStoreFg01Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg02Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg03Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg04Combo.selectedIndex = 0;
        $scope.srchMomsStoreFg05Combo.selectedIndex = 0;
    };

}]);