/****************************************************************
 *
 * 파일명 : popUpTouchKeyEnv.js
 * 설  명 : 터치키 그룹코드 적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.08     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('popUpTouchKeyEnvCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('popUpTouchKeyEnvCtrl', $scope, $http, false));

    $scope._setComboData("srchEnvSysStatFg", sysStatFg);
    $scope._setComboData("touchKeyEnvCombo", touchKeyGrpData);

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("popUpTouchKeyEnvCtrl", function(event, key) {

        // 매장 포스 터치키 그룹 코드 조회
        $scope.searchPos();
        event.preventDefault();

    });

    // 매장 포스 터치키 그룹 코드 조회
    $scope.searchPos = function(){

        var params = {};
        params.storeCd = $("#srchEnvStoreCd").val();
        params.storeNm = $("#srchEnvStoreNm").val();
        params.momsEnvstVal = momsEnvstVal;
        if(momsEnvstVal === "1" && orgnFg === "HQ") {
            params.momsTeam = $scope.momsTeam;
            params.momsAcShop = $scope.momsAcShop;
            params.momsAreaFg = $scope.momsAreaFg;
            params.momsCommercial = $scope.momsCommercial;
            params.momsShopType = $scope.momsShopType;
            params.momsStoreManageType = $scope.momsStoreManageType;
            params.branchCd = $scope.branchCd;
            params.storeHqBrandCd = $scope.storeHqBrandCd;
            // '전체' 일때
            if (params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
                var momsHqBrandCd = "";
                for (var i = 0; i < brandList.length; i++) {
                    if (brandList[i].value !== null) {
                        momsHqBrandCd += brandList[i].value + ","
                    }
                }
                params.userBrands = momsHqBrandCd;
            }
        } else if(brandUseFg === "1" && orgnFg === "HQ"){
            params.storeHqBrandCd = $scope.storeHqBrandCd;
        }
        console.log(params);

        $scope._inquirySub("/base/prod/touchKey/touchKey/getTouchKeyEnv.sb", params, function () {});
    };

    // 적용
    $scope.btnInsertEnv = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        // 키맵그룹이 없습니다.
        if ($scope.touchKeyEnvCombo.selectedValue === "" || $scope.touchKeyEnvCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }
        // 키맵그룹이 없습니다.
        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if (item.gChk === true) {
                if(item.posNo !== null && item.posNo !== "") {
                    item.envstVal = $scope.touchKeyEnvCombo.selectedValue;
                    params.push(item);
                }
            }
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/touchKey/touchKey/saveTouchKeyEnv.sb", params, function () {
            $scope.popUpTouchKeyEnvLayer.hide(true);
        });

    };

    // 닫기
    $scope.close = function () {
        $("#srchEnvStoreCd").val("");
        $("#srchEnvStoreNm").val("");
        $scope.srchEnvSysStatFgCombo.selectedIndex = 0;
        $scope.touchKeyEnvCombo.selectedIndex = 0;
    };

    // 확장조회 숨김/보임
    $scope.searchAddShowChangeEnv = function(){
        if( $("#tblSearchAddShowEnv").css("display") === 'none') {
            $("#tblSearchAddShowEnv").show();
        } else {
            $("#tblSearchAddShowEnv").hide();
        }
    };
}]);