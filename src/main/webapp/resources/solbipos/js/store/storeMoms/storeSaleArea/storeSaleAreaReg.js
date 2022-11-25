/****************************************************************
 *
 * 파일명 : storeSaleAreaReg.js
 * 설  명 : 점포 영업 지역 관리 등록 JavaScript
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

app.controller('storeSaleAreaRegCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeSaleAreaRegCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchRegBranchCd", branchCombo);
    $scope._setComboData("srchMapBranchCd", branchCombo);

    $scope.$on("storeSaleAreaRegCtrl", function(event, data) {

        if(data !== null && data !== undefined && data !== "") {
            $scope.srchRegBranchCdCombo.selectedValue = data.branchCd;
            
            setTimeout(function() {
                $scope.srchRegStoreCdCombo.selectedValue = data.storeCd;

                // 처음 선택한 지사와 매장 갖고있기(초기화 클릭시 사용)
                $("#hdRegBranchCd").val(data.branchCd);
                $("#hdRegStoreCd").val(data.storeCd);
            }, 300);

        }else{
            $scope.srchRegBranchCdCombo.selectedIndex = 0;
            $scope.srchRegStoreCdCombo.selectedIndex = 0;
        }
    });

    // 지사선택에 따른 매장 리스트 set
    $scope.setRegStore = function (s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {
            var params = {};
            params.branchCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreCombo.sb", params, function (response) {
                $scope._setComboData("srchRegStoreCd", response.data.data.list);
            });
        }else{
            $scope._setComboData("srchRegStoreCd", nullStore);
        }
    };

    // 매장선택에 따른 영업지역 조회
    $scope.setRegStoreSaleArea = function (s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {
            // 영업지역 조회
            $scope.getStoreSaleArea(s.selectedValue);
        }
    };

    // 영업지역 조회
    $scope.getStoreSaleArea = function(storeCd){
        var params = [];
        params.storeCd = storeCd;

        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreSaleArea.sb", params, function (response) {

            var data = response.data.data;

            if(data !== null && data !== undefined){

                // 매장위치
                if(data.latitude !== "" && data.longitude != "" &&
                    data.latitude !== null && data.longitude != null &&
                    data.latitude !== undefined && data.longitude != undefined) {
                    init_Map(data.longitude, data.latitude);
                }else{
                    // 매장 좌표 없을때, 기본 맘스터치 본사
                    init_Map(127.1232256 , 37.5290411);
                }

                // 영업지역
                if(data.saleArea !== "" && data.saleArea !== null && data.saleArea !== undefined){
                    var saleArea = data.saleArea;
                    var arrSaleArea = saleArea.split(';');
                    var paths = new Array();

                    for(var i=0; i<arrSaleArea.length; i++) {
                        paths[i] = new naver.maps.LatLng(arrSaleArea[i].split(',')[1] ,arrSaleArea[i].split(',')[0]);
                    }

                    // 영업지역 표시
                    searchregion(paths);

                    // 좌표값 초기화
                    posRegion = null;
                    posRegion = new Array();
                }

            }else{
                // 매장 좌표 없을때, 기본 맘스터치 본사
                init_Map(127.1232256 , 37.5290411);
            }
        });
    };
    
    // 영업지역 저장
    $scope.saveStoreSaleArea = function () {

        if($scope.srchRegStoreCdCombo.selectedValue === null || $scope.srchRegStoreCdCombo.selectedValue === undefined || $scope.srchRegStoreCdCombo.selectedValue === ""){
            $scope._popMsg(messages["storeSaleArea.require.selectStore"] ); // 매장을 선택하세요.
            return false;
        }

        if(posRegion.length === 0){
            $scope._popMsg(messages["storeSaleArea.require.selectStoreArea"] ); // 영업지역을 설정하세요.
            return false;
        }
        
        // 영업지역을 등록하시겠습니까?
        $scope._popConfirm(messages["storeSaleArea.area.regist.msg"], function() {

            var strRegion = "";
            for(var i=0; i<posRegion.length; i++) {
                if(strRegion === ""){
                    strRegion += posRegion[i].x + "," + posRegion[i].y;
                }else {
                    strRegion += ";" + posRegion[i].x + "," + posRegion[i].y;
                }
            }

            console.log(strRegion);

            var params = {};
            params.storeCd = $scope.srchRegStoreCdCombo.selectedValue;
            params.saleArea = strRegion;

            $scope._postJSONSave.withPopUp("/store/storeMoms/storeSaleArea/saveStoreSaleArea.sb", params, function () {
                // 영업지역 조회(재조회)
                $scope.getStoreSaleArea(params.storeCd);
            });
        });
    };

    // 타매장 지역 매칭 - 지사선택에 따른 매장리스트 set
    $scope.setMapStore = function(s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {
            var params = {};
            params.branchCd = s.selectedValue;

            $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreCombo.sb", params, function (response) {
                $scope._setComboData("srchMapStoreCd", response.data.data.list);
            });
        }else{
            $scope._setComboData("srchMapStoreCd", nullStore);
        }
    };
    
    // 타매장 지역 매칭 - 매장선택에 따른 영업지역 조회
    $scope.setMapStoreSaleArea = function (s) {
        if(s.selectedValue !== null && s.selectedValue !== undefined && s.selectedValue !== "") {
            // 타매장 지역매칭 - 영업지역 조회
            $scope.getOtherStoreSaleArea(s.selectedValue);
        }
    };

    // 타매장 지역 매칭 - 영업지역 조회
    $scope.getOtherStoreSaleArea = function (storeCd) {
        var params = [];
        params.storeCd = storeCd;

        $scope._postJSONQuery.withOutPopUp("/store/storeMoms/storeSaleArea/getStoreSaleArea.sb", params, function (response) {

            var data = response.data.data;

            if(data !== null && data !== undefined){

                // 영업지역
                if(data.saleArea !== "" && data.saleArea !== null && data.saleArea !== undefined){
                    var saleArea = data.saleArea;
                    var arrSaleArea = saleArea.split(';');
                    var paths = new Array();

                    for( var i = 0 ; i < arrSaleArea.length ; i++ ) {
                        paths[i] = new naver.maps.LatLng(arrSaleArea[i].split(',')[1] ,arrSaleArea[i].split(',')[0]);
                    }

                    // 영업지역 표시
                    searchregion(paths);
                }
            }
        });
    };

    // 초기화
    $scope.resetStoreSaleArea = function (){

        if($("#hdRegBranchCd").val() !== null && $("#hdRegBranchCd").val() !== undefined && $("#hdRegBranchCd").val() !== "") {
            if ($("#hdRegStoreCd").val() !== null && $("#hdRegStoreCd").val() !== undefined && $("#hdRegStoreCd").val() !== "") {

                $scope.srchRegBranchCdCombo.selectedValue = $("#hdRegBranchCd").val();

                setTimeout(function() {
                    $scope.srchRegStoreCdCombo.selectedValue = $("#hdRegStoreCd").val();

                    // 영업지역 조회(재조회)
                    $scope.getStoreSaleArea($scope.srchRegStoreCdCombo.selectedValue);
                }, 300);
            }
        }
    };
    
    // 등록 닫기
    $scope.closeReg = function () {
        $scope.srchRegBranchCdCombo.selectedIndex = 0;
        $scope.srchRegStoreCdCombo.selectedIndex = 0;
        $scope.srchMapBranchCdCombo.selectedIndex = 0;
        $scope.srchMapStoreCdCombo.selectedIndex = 0;

        $("#STORE_MAP").html("");
        oMap = null;
        oPoint = null;
        oStore = null;

        // 좌표값 초기화
        posRegion = null;
        posRegion = new Array();

        $("#hdRegBranchCd").val("");
        $("#hdRegStoreCd").val("");
    };
    
}]);