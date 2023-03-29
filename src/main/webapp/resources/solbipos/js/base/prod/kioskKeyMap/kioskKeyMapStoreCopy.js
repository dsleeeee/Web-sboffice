/****************************************************************
 *
 * 파일명 : kioskKeyMapStoreCopy.js
 * 설  명 : 키오스크 키맵매장복사 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.23     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgMapEnvComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

// 팝업 닫기
function closePop(){
    var scope = agrid.getScope('kioskKeyMapStoreCopy2Ctrl');
    scope.closePop();
}

// 기준매장포스
app.controller('kioskKeyMapStoreCopyCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapStoreCopyCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd1", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("srchMomsTeam1", momsTeamComboList); // 팀별
    $scope._setComboData("srchMomsAcShop1", momsAcShopComboList); // AC점포별
    $scope._setComboData("srchMomsAreaFg1", momsAreaFgComboList); // 지역구분
    $scope._setComboData("srchMomsCommercial1", momsCommercialComboList); // 상권
    $scope._setComboData("srchMomsShopType1", momsShopTypeComboList); // 점포유형
    $scope._setComboData("srchMomsStoreManageType1", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd1", branchCdComboList); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgMapEnvComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.binding === "storeCd") {
                    wijmo.addClass(e.cell, 'wijLink');
                    wijmo.addClass(e.cell, 'wj-custom-readonly');
                }
            }
        });

        // 그리드 선택 이벤트
        s.addEventListener(s.hostElement, 'mousedown', function(e) {
            var ht = s.hitTest(e);
            if( ht.cellType === wijmo.grid.CellType.Cell) {
                var col = ht.panel.columns[ht.col];

                // 기준매장포스 매장코드 클릭
                if ( col.binding === "storeCd") {
                    var selectedRow = s.rows[ht.row].dataItem;
                    var scope = agrid.getScope('kioskKeyMapStoreCopy2Ctrl');

                    // 선택한 기준매장 키오스크 포스 정보 셋팅
                    scope.setOrgStoreInfo(selectedRow);

                    // 적용대상매장포스 조회
                    scope.searchTargetStoreList();
                    event.preventDefault();
                }
            }
        });

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 기준매장 키오스크 포스 조회
    $scope.$on("kioskKeyMapStoreCopyCtrl", function(event, data) {
        // 기준매장 키오스크 포스 조회
        $scope.searchOrgStoreList();
        event.preventDefault();
    });

    // 기준매장 키오스크 포스 조회
    $scope.searchOrgStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd1").val();
        params.storeNm = $("#srchStoreNm1").val();
        params.originalStoreCd = "";
        params.orgPosNo = "";
        if(momsEnvstVal === "1") {
            params.momsTeam = $scope.srchMomsTeam1Combo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShop1Combo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFg1Combo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercial1Combo.selectedValue;
            params.momsShopType = $scope.srchMomsShopType1Combo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageType1Combo.selectedValue;
            params.branchCd = $scope.srchBranchCd1Combo.selectedValue;

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd1Combo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        } else if(brandUseFg === "1"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd1Combo.selectedValue;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreCopyKioskPosList.sb", params, function () {
        });
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeCopy = function(){
        if( $("#tblSearchAddShowCopy").css("display") === 'none') {
            $("#tblSearchAddShowCopy").show();
        } else {
            $("#tblSearchAddShowCopy").hide();
        }
    };

}]);

// 적용대상매장포스
app.controller('kioskKeyMapStoreCopy2Ctrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapStoreCopy2Ctrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("srchStoreHqBrandCd2", userHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("srchMomsTeam2", momsTeamComboList); // 팀별
    $scope._setComboData("srchMomsAcShop2", momsAcShopComboList); // AC점포별
    $scope._setComboData("srchMomsAreaFg2", momsAreaFgComboList); // 지역구분
    $scope._setComboData("srchMomsCommercial2", momsCommercialComboList); // 상권
    $scope._setComboData("srchMomsShopType2", momsShopTypeComboList); // 점포유형
    $scope._setComboData("srchMomsStoreManageType2", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("srchBranchCd2", branchCdComboList); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgMapEnvComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 적용대상매장 키오스크 포스 조회
    $scope.$on("kioskKeyMapStoreCopy2Ctrl", function(event, data) {
        // 적용대상매장 키오스크 포스 조회
        $scope.searchTargetStoreList();
        event.preventDefault();
    });

    // 적용대상매장 키오스크 포스 조회
    $scope.searchTargetStoreList = function () {

        // 파라미터
        var params = {};
        params.storeCd = $("#srchStoreCd2").val();
        params.storeNm = $("#srchStoreNm2").val();
        params.originalStoreCd = $("#hdOrgStorecd").val();
        params.orgPosNo = $("#hdOrgPosNo").val();
        if(momsEnvstVal === "1") {
            params.momsTeam = $scope.srchMomsTeam2Combo.selectedValue;
            params.momsAcShop = $scope.srchMomsAcShop2Combo.selectedValue;
            params.momsAreaFg = $scope.srchMomsAreaFg2Combo.selectedValue;
            params.momsCommercial = $scope.srchMomsCommercial2Combo.selectedValue;
            params.momsShopType = $scope.srchMomsShopType2Combo.selectedValue;
            params.momsStoreManageType = $scope.srchMomsStoreManageType2Combo.selectedValue;
            params.branchCd = $scope.srchBranchCd2Combo.selectedValue;

            // 선택한 매장브랜드가 있을 때
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd2Combo.selectedValue;
            // 선택한 매장브랜드가 없을 때('전체' 일때)
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        } else if(brandUseFg === "1"){
            params.storeHqBrandCd = $scope.srchStoreHqBrandCd2Combo.selectedValue;
        }

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreCopyKioskPosList.sb", params, function () {
        });
    };

    // 선택한 기준매장포스 정보 셋팅
    $scope.setOrgStoreInfo = function(data){
        $("#selStore").text("[" + data.storeCd + "] " + data.storeNm);
        $("#hdOrgStorecd").val(data.storeCd);
        $("#hdOrgPosNo").val(data.posNo);
        $("#selKioskPosNo").text(data.posNo);
        $("#lblTuMClsFg").text(data.tuMClsFg === "0" ? "미사용" : "중분류사용");
        $("#hdTuMClsFg").val(data.tuMClsFg);
        $("#selEnv4068").text(data.env4068);
        $("#selEnv4069").text(data.env4069);
    };

    // 복사
    $scope.saveCopyStoreKioskKeyMap = function () {

        if($("#hdOrgStorecd").val() === ""){
            $scope._popMsg(messages["kioskKeyMap.original.storeCd"] + messages["cmm.require.select"]); // 기준매장을 선택해주세요.
            return false;
        }

        if($("#selEnv4068").text() === "" || $("#selEnv4069").text() === "" || $("#selEnv4068").text() === "*" || $("#selEnv4069").text() === "*" ){
            $scope._popMsg(messages["kioskKeyMap.no.keyMapGrp.msg"] ); // 기준매장에 등록된 [매장] 또는 [포장]설정키맵그룹이 없어, 복사가 불가합니다.
            return false;
        }

        var chkCount = 0;
        var chkTuMClsFg = 0;
        for (var i=0; i< $scope.flex.collectionView.itemCount; i++) {
            var item =  $scope.flex.collectionView.items[i];
            if(item.gChk) {
                chkCount++;
                if (item.tuMClsFg !== $("#hdTuMClsFg").val()){
                    chkTuMClsFg++;
                }
            }
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeyMap.target.storeCd"] + messages["cmm.require.select"]); // 적용대상매장을(를) 선택하세요.
            return false;
        }

        // 선택한 적용대상매장 포스중 'KIOSK중분류사용'이 기준매장의 'KIOSK중분류사용'과 다른 포스가 있습니다.</br>'KIOSK중분류사용'가 같은 경우만 복사됩니다.
        if(chkTuMClsFg > 0){
            $scope._popMsg(messages["kioskKeyMap.chk.tuMClsFg.msg"]);
            return false;
        }

        // 선택한 설정키맵그룹을 복사하시겠습니까?(적용대상매장의 기존 설정키맵그룹 정보는 모두 삭제됩니다.)
        $scope._popConfirm(messages["kioskKeyMap.copy.keyMapGrp.msg"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].originalStoreCd = $("#hdOrgStorecd").val();
                    $scope.flex.collectionView.items[i].orgPosNo = $("#hdOrgPosNo").val();
                    $scope.flex.collectionView.items[i].envstCd = "4068,4069";
                    $scope.flex.collectionView.items[i].envstVal = $("#selEnv4068").text() + "," + $("#selEnv4069").text();
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save('/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStoreCopy.sb', params, function() {
                // 팝업 닫기 시 초기화
                $scope.closePop();
            });

        });

    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeCopy2 = function(){
        if( $("#tblSearchAddShowCopy2").css("display") === 'none') {
            $("#tblSearchAddShowCopy2").show();
        } else {
            $("#tblSearchAddShowCopy2").hide();
        }
    };

    // 팝업 닫기 시 초기화
    $scope.closePop = function () {

        var VScope = agrid.getScope('kioskKeyMapStoreCopyCtrl');

        // 기준매장포스 리스트 영역 초기화
        VScope.srchStoreHqBrandCd1Combo.selectedIndex = 0;
        $("#srchStoreCd1").val("");
        $("#srchStoreNm1").val("");
        if(momsEnvstVal === "1") {
            VScope.srchMomsTeam1Combo.selectedIndex = 0;
            VScope.srchMomsAcShop1Combo.selectedIndex = 0;
            VScope.srchMomsAreaFg1Combo.selectedIndex = 0;
            VScope.srchMomsCommercial1Combo.selectedIndex = 0;
            VScope.srchMomsShopType1Combo.selectedIndex = 0;
            VScope.srchMomsStoreManageType1Combo.selectedIndex = 0;
            VScope.srchBranchCd1Combo.selectedIndex = 0;
        }
        $("#tblSearchAddShowCopy").hide();
        VScope._gridDataInit();

        // 기준매장포스 정보 영역 초기화
        $("#selStore").text("");
        $("#hdOrgStorecd").val("");
        $("#hdOrgPosNo").val("");
        $("#selKioskPosNo").text("");
        $("#lblTuMClsFg").text("");
        $("#hdTuMClsFg").text("");
        $("#selEnv4068").text("");
        $("#selEnv4069").text("");

        // 적용대상매장포스 리스트 영역 초기화
        $scope.srchStoreHqBrandCd2Combo.selectedIndex = 0;
        $("#srchStoreCd2").val("");
        $("#srchStoreNm2").val("");
        if(momsEnvstVal === "1") {
            $scope.srchMomsTeam2Combo.selectedIndex = 0;
            $scope.srchMomsAcShop2Combo.selectedIndex = 0;
            $scope.srchMomsAreaFg2Combo.selectedIndex = 0;
            $scope.srchMomsCommercial2Combo.selectedIndex = 0;
            $scope.srchMomsShopType2Combo.selectedIndex = 0;
            $scope.srchMomsStoreManageType2Combo.selectedIndex = 0;
            $scope.srchBranchCd2Combo.selectedIndex = 0;
        }
        $("#tblSearchAddShowCopy2").hide();
        $scope._gridDataInit();

        // 팝업 hide
        $scope.kioskKeyMapStoreCopyLayer.hide();
    }

}]);