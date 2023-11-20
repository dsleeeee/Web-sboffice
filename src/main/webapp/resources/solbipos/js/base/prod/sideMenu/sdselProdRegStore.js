/****************************************************************
 *
 * 파일명 : sdselProdRegStore.js
 * 설  명 : 선택상품 적용매장등록 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.05.31     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 선택상품
var regStoreSdselProdComboData = [
    {"name":"선택","value":""}
];

/**
 *  선택상품 적용매장등록 팝업 조회 그리드 생성
 */
app.controller('sdselProdRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdRegStoreCtrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("regStoreSdselProdCombo", regStoreSdselProdComboData); // 선택상품
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdRegStoreCtrl', function(event, data) {
        // 선택그룹
        $("#srchProdRegStoreClassCd").val(data.sdselClassCd);
        $("#srchProdRegStoreClass").val(data.sdselClassCdNm);

        // 선택상품 조회
        $scope.categoryCode();

        event.preventDefault();
    });

    $scope.searchSdselProdRegStore = function(){
        if($scope.regStoreSdselProdCombo == ""){
            $scope._popMsg(messages["sideMenu.sdselProdRegStore.sdselProdNullAlert"]); // 선택상품을 선택해주세요. </br> (선택상품는 적용매장구분이 '제외매장', '허용매장'만 가능합니다.)
            return false;
        }

        // 선택상품
        $("#lblProdRegStoreProd").text("선택상품 : [" + $scope.regStoreSdselProdCombo + "] " + $scope.srchRegStoreSdselProdCombo.text);

        var params = {};
        params.sdselClassCd = $("#srchProdRegStoreClassCd").val();
        params.prodCd = $scope.regStoreSdselProdCombo;
        params.storeCd = $scope.storeCd;
        params.storeNm = $scope.storeNm;
        if(momsEnvstVal == "1") {
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
            params.momsStoreFg01 = $scope.momsStoreFg01;
        }

        var storeScope = agrid.getScope('sdselProdYesRegStoreCtrl');
        storeScope._broadcast('sdselProdYesRegStoreCtrl', params);

        var storeScope2 = agrid.getScope('sdselProdNoRegStoreCtrl');
        storeScope2._broadcast('sdselProdNoRegStoreCtrl', params);
    };
    // <-- //검색 호출 -->

    // 선택상품 조회
    $scope.categoryCode = function() {
        var params = {};
        params.sdselClassCd = $("#srchProdRegStoreClassCd").val();

        $scope._postJSONQuery.withOutPopUp('/base/prod/sideMenu/sdselProdRegStore/getSdselProdCodeComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var sdselProdCodeList = response.data.data.list;
                $scope._setComboData("regStoreSdselProdCombo", sdselProdCodeList); // 선택상품
            } else {
                $scope._setComboData("regStoreSdselProdCombo", regStoreSdselProdComboData); // 선택상품
            }
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow2").css("display") === 'none') {
            $("#tblSearchAddShow2").show();
        } else {
            $("#tblSearchAddShow2").hide();
        }
    };

    // 팝업 닫기
    $scope.close = function(){
        $scope.storeCd = "";
        $scope.storeNm = "";
        $scope.srchMomsTeamCombo.selectedIndex = 0;
        $scope.srchMomsAcShopCombo.selectedIndex = 0;
        $scope.srchMomsAreaFgCombo.selectedIndex = 0;
        $scope.srchMomsCommercialCombo.selectedIndex = 0;
        $scope.srchMomsShopTypeCombo.selectedIndex = 0;
        $scope.srchMomsStoreManageTypeCombo.selectedIndex = 0;
        $scope.srchBranchCdCombo.selectedIndex = 0;
        $scope.srchMomsStoreFg01Combo.selectedIndex = 0;

        // 선택상품
        $("#lblProdRegStoreProd").text("");

        // 그리드 초기화
        var storeScope = agrid.getScope('sdselProdYesRegStoreCtrl');
        storeScope._gridDataInit();
        var storeScope2 = agrid.getScope('sdselProdNoRegStoreCtrl');
        storeScope2._gridDataInit();

        $scope.wjSdselProdRegStoreLayer.hide();
    };
}]);


/**
 *  적용매장 조회 그리드 생성
 */
app.controller('sdselProdYesRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdYesRegStoreCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdYesRegStoreCtrl', function(event, data) {
        $scope.searchSdselProdYesRegStore(data);
        event.preventDefault();
    });

    $scope.searchSdselProdYesRegStore = function(params){

        $scope._inquiryMain("/base/prod/sideMenu/sdselProdRegStore/getSdselProdRegStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 적용매장 저장
    $scope.regSave = function(){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "D";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/sideMenu/sdselProdRegStore/getSdselProdRegStoreSave.sb", params, function(){
                var storeScope = agrid.getScope('sdselProdRegStoreCtrl');
                storeScope.searchSdselProdRegStore();
            });
        });
    };
}]);


/**
 *  미적용 매장 조회 그리드 생성
 */
app.controller('sdselProdNoRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselProdNoRegStoreCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselProdNoRegStoreCtrl', function(event, data) {
        $scope.searchSdselProdNoRegStore(data);
        event.preventDefault();
    });

    $scope.searchSdselProdNoRegStore = function(params){

        $scope._inquirySub("/base/prod/sideMenu/sdselProdRegStore/getSdselProdNoRegStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 미적용매장 저장
    $scope.noRegSave = function(){
        $scope._popConfirm(messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "I";
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/sideMenu/sdselProdRegStore/getSdselProdRegStoreSave.sb", params, function(){
                var storeScope = agrid.getScope('sdselProdRegStoreCtrl');
                storeScope.searchSdselProdRegStore();
            });
        });
    };
}]);