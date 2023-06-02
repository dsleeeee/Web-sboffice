/****************************************************************
 *
 * 파일명 : sdselClassRegStore.js
 * 설  명 : 선택분류 적용매장등록 팝업 JavaScript
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

// 선택분류
var regStoreSdselClassComboData = [
    {"name":"선택","value":""}
];

/**
 *  선택분류 적용매장등록 팝업 조회 그리드 생성
 */
app.controller('sdselClassRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselClassRegStoreCtrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("regStoreSdselClassCombo", regStoreSdselClassComboData); // 선택분류
    $scope._setComboData("srchStoreHqBrandCd", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselClassRegStoreCtrl', function(event, data) {
        // 선택그룹
        $("#srchClassRegStoreGroup").val(data.sdselGrpCdNm);
        $("#srchClassRegStoreGroupCd").val(data.sdselGrpCd);

        // 선택분류 조회
        $scope.categoryCode();

        event.preventDefault();
    });

    $scope.searchSdselClassRegStore = function(){
        if($scope.regStoreSdselClassCombo == ""){
            $scope._popMsg(messages["sideMenu.sdselClassRegStore.sdselClassNullAlert"]); // 선택분류를 선택해주세요. </br> (선택분류는 적용매장구분이 '제외매장', '허용매장'만 가능합니다.)
            return false;
        }

        var params = {};
        params.sdselGrpCd = $("#srchClassRegStoreGroupCd").val();
        params.sdselClassCd = $scope.regStoreSdselClassCombo;
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
        }

        var storeScope = agrid.getScope('sdselClassYesRegStoreCtrl');
        storeScope._broadcast('sdselClassYesRegStoreCtrl', params);

        var storeScope2 = agrid.getScope('sdselClassNoRegStoreCtrl');
        storeScope2._broadcast('sdselClassNoRegStoreCtrl', params);
    };
    // <-- //검색 호출 -->

    // 선택분류 조회
    $scope.categoryCode = function() {
        var params = {};
        params.sdselGrpCd = $("#srchClassRegStoreGroupCd").val();

        $scope._postJSONQuery.withOutPopUp('/base/prod/sideMenu/sdselClassRegStore/getSdselClassCodeComboList.sb', params, function (response) {
            if (response.data.data.list.length > 0) {
                var sdselClassCodeList = response.data.data.list;
                $scope._setComboData("regStoreSdselClassCombo", sdselClassCodeList); // 선택분류
            } else {
                $scope._setComboData("regStoreSdselClassCombo", regStoreSdselClassComboData); // 선택분류
            }
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChange = function(){
        if( $("#tblSearchAddShow").css("display") === 'none') {
            $("#tblSearchAddShow").show();
        } else {
            $("#tblSearchAddShow").hide();
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

        // 그리드 초기화
        var storeScope = agrid.getScope('sdselClassYesRegStoreCtrl');
        storeScope._gridDataInit();
        var storeScope2 = agrid.getScope('sdselClassNoRegStoreCtrl');
        storeScope2._gridDataInit();

        $scope.wjSdselClassRegStoreLayer.hide();
    };
}]);


/**
 *  적용매장 조회 그리드 생성
 */
app.controller('sdselClassYesRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselClassYesRegStoreCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselClassYesRegStoreCtrl', function(event, data) {
        $scope.searchSdselClassRegStore(data);
        event.preventDefault();
    });

    $scope.searchSdselClassRegStore = function(params){

        $scope._inquiryMain("/base/prod/sideMenu/sdselClassRegStore/getSdselClassRegStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 적용매장 저장
    $scope.regSave = function(){
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "D";
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/sideMenu/sdselClassRegStore/getSdselClassRegStoreSave.sb", params, function(){
            var storeScope = agrid.getScope('sdselClassRegStoreCtrl');
            storeScope.searchSdselClassRegStore();
        });
    };
}]);


/**
 *  미적용 매장 조회 그리드 생성
 */
app.controller('sdselClassNoRegStoreCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sdselClassNoRegStoreCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on('sdselClassNoRegStoreCtrl', function(event, data) {
        $scope.searchSdselClassNoRegStore(data);
        event.preventDefault();
    });

    $scope.searchSdselClassNoRegStore = function(params){

        $scope._inquirySub("/base/prod/sideMenu/sdselClassRegStore/getSdselClassNoRegStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 미적용매장 저장
    $scope.noRegSave = function(){
        // 파라미터 설정
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].status = "I";
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/sideMenu/sdselClassRegStore/getSdselClassRegStoreSave.sb", params, function(){
            var storeScope = agrid.getScope('sdselClassRegStoreCtrl');
            storeScope.searchSdselClassRegStore();
        });
    };
}]);