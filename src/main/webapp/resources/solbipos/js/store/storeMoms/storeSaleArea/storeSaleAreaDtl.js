/****************************************************************
 *
 * 파일명 : storeSaleAreaDtl.js
 * 설  명 : 점포영업지역관리 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.11.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

var nullStore = [
    {"name":"매장 선택","value":""}
];

app.controller('storeSaleAreaDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSaleAreaDtlCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchDtlBranchCd", branchCombo);

    $scope.$on("storeSaleAreaDtlCtrl", function(event, data) {

        if(data !== null && data !== undefined && data !== "") {
            $scope.srchDtlBranchCdCombo.selectedValue = data.branchCd;

            setTimeout(function() {
                $scope.srchDtlStoreCdCombo.selectedValue = data.storeCd;
            }, 300)

        }else{
            $scope.srchDtlBranchCdCombo.selectedIndex = 0;
            $scope.srchDtlStoreCdCombo.selectedIndex = 0;

            // 매장 좌표 없을때, 기본 맘스터치 본사
            init_Map_dtl(127.1232256 , 37.5290411);
        }
    });

    // 지사선택에 따른 매장 리스트 set
    $scope.setDtlStore = function (s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {
            var params = {};
            params.branchCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreCombo.sb", params, function (response) {
                $scope._setComboData("srchDtlStoreCd", response.data.data.list);
            });
        }else{
            $scope._setComboData("srchDtlStoreCd", nullStore);
        }
    };

    // 영업지역 조회
    $scope.setDtlStoreSaleArea = function (s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {

            var params = [];
            params.storeCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreSaleArea.sb", params, function (response) {

                var data = response.data.data;

                if(data !== null && data !== undefined){

                    // 매장위치
                    if(data.latitude !== "" && data.longitude != "" &&
                        data.latitude !== null && data.longitude != null &&
                        data.latitude !== undefined && data.longitude != undefined) {
                        init_Map_dtl(data.longitude, data.latitude);
                    }else{
                        // 매장 좌표 없을때, 기본 맘스터치 본사
                        init_Map_dtl(127.1232256 , 37.5290411);
                    }

                    // 영업지역
                    if(data.saleArea !== "" && data.saleArea !== null && data.saleArea !== undefined){
                        var saleArea = data.saleArea;
                        var arrSaleArea = saleArea.split(';');
                        var paths = new Array();

                        for( var i = 0 ; i < arrSaleArea.length ; i++ ) {
                            paths[i] = new naver.maps.LatLng(arrSaleArea[i].split(',')[1] ,arrSaleArea[i].split(',')[0]);
                        }

                        // 영업지역 표시
                        searchregion_dtl(paths);
                    }

                }else{
                    // 매장 좌표 없을때, 기본 맘스터치 본사
                    init_Map_dtl(127.1232256 , 37.5290411);
                }
            });
        }
    };

    // 영업지역 수정 팝업
    $scope.modStoreSaleArea = function () {

        if($scope.srchDtlStoreCdCombo.selectedValue === null || $scope.srchDtlStoreCdCombo.selectedValue === undefined || $scope.srchDtlStoreCdCombo.selectedValue === ""){
            $scope._popMsg(messages["storeSaleArea.require.selectStore"] ); // 매장을 선택하세요.
            return false;
        }

        // 영업지역을 수정하시겠습니까?
        $scope._popConfirm(messages["storeSaleArea.area.modify.msg"], function() {
            var params = {};
            params.branchCd = $scope.srchDtlBranchCdCombo.selectedValue;
            params.storeCd = $scope.srchDtlStoreCdCombo.selectedValue;

            $scope.wjStoreSaleAreaRegLayer.show(true);
            $scope._broadcast('storeSaleAreaRegCtrl', params);

            // 상세 닫기
            $scope.wjStoreSaleAreaDtlLayer.hide();
            $scope.closeDtl();
        });
    };

    // 서울, 경기 매장 영업지역 조회
    $scope.metropolitanSaleArea = function () {

        if($scope.srchDtlStoreCdCombo.selectedValue === null || $scope.srchDtlStoreCdCombo.selectedValue === undefined || $scope.srchDtlStoreCdCombo.selectedValue === ""){
            $scope._popMsg(messages["storeSaleArea.require.selectStore"] ); // 매장을 선택하세요.
            return false;
        }

        var params = {};
        params.storeCd = $scope.srchDtlStoreCdCombo.selectedValue; // 현재 선택한 매장은 제외하고 조회한다.

        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getMetropolitanSaleArea.sb", params, function (response) {

            var list = response.data.data.list;

            if(list.length > 0) {
                for(var i=0; i < list.length; i++) {

                    // 영업지역
                    if(list[i].saleArea !== "" && list[i].saleArea !== null && list[i].saleArea !== undefined) {
                        var saleArea = list[i].saleArea;
                        var arrSaleArea = saleArea.split(';');
                        var paths = new Array();

                        for(var j=0; j<arrSaleArea.length; j++) {
                            paths[j] = new naver.maps.LatLng(arrSaleArea[j].split(',')[1] ,arrSaleArea[j].split(',')[0]);
                        }

                        // 영업지역 표시(수도권매장 전체표시)
                        searchregion_metropolitan(paths, list[i].storeNm, list[i].latitude, list[i].longitude);
                    }
                }
            }
        });
    };

    // 상세 닫기
    $scope.closeDtl = function () {
        $scope.srchDtlBranchCdCombo.selectedIndex = 0;
        $scope.srchDtlStoreCdCombo.selectedIndex = 0;

        $("#STORE_MAP_DTL").html("");
        oMap_dtl = null;
        oPoint_dtl = null;
        oStore_dtl = null;
    };

}]);