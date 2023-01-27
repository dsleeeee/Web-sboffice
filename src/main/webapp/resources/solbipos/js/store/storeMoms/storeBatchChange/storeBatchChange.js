/****************************************************************
 *
 * 파일명 : storeBatchChange.js
 * 설  명 : 매장정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.18     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장정보일괄변경 그리드 생성
 */
app.controller('storeBatchChangeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeBatchChangeCtrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
    $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
    $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

    $scope._setComboData("momsTeamChgCombo", momsTeamComboList2); // 팀별
    $scope._setComboData("momsAcShopChgCombo", momsAcShopComboList2); // AC점포별
    $scope._setComboData("momsAreaFgChgCombo", momsAreaFgComboList2); // 지역구분
    $scope._setComboData("momsCommercialChgCombo", momsCommercialComboList2); // 상권
    $scope._setComboData("momsShopTypeChgCombo", momsShopTypeComboList2); // 점포유형
    $scope._setComboData("momsStoreManageTypeChgCombo", momsStoreManageTypeComboList2); // 매장관리타입
    $scope._setComboData("branchCdChgCombo", branchCdComboList2); // 지사

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 지사
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
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

    // <-- 검색 호출 -->
    $scope.$on("storeBatchChangeCtrl", function(event, data) {
        $scope.searchStoreBatchChange();
        event.preventDefault();
    });

    $scope.searchStoreBatchChange = function(){
        var params = {};
        params.momsTeam = $scope.momsTeam;
        params.momsAcShop = $scope.momsAcShop;
        params.momsAreaFg = $scope.momsAreaFg;
        params.momsCommercial = $scope.momsCommercial;
        params.momsShopType = $scope.momsShopType;
        params.momsStoreManageType = $scope.momsStoreManageType;
        params.branchCd = $scope.branchCd;
        params.storeHqBrandCd = $scope.storeHqBrandCd;
        // '전체' 일때
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.listScale = 500;
        console.log(params);

        $scope._inquiryMain("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreList.sb", params);
    };
    // <-- //검색 호출 -->

    // 일괄적용
    $scope.batchChange = function(chgGubun) {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                params.push($scope.flex.collectionView.items[i]);
            }
        }
        if(params.length <= 0) {
            s_alert.pop(messages["cmm.not.select"]);
            return;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                // 지사코드
                if(chgGubun == "branchCdChg") {
                    $scope.flex.collectionView.items[i].branchCd = $scope.branchCdChg;
                }
                // 팀별
                else if(chgGubun == "momsTeamChg") {
                    $scope.flex.collectionView.items[i].momsTeam = $scope.momsTeamChg;
                }
                // AC점포별
                else if(chgGubun == "momsAcShopChg") {
                    $scope.flex.collectionView.items[i].momsAcShop = $scope.momsAcShopChg;
                }
                // 지역구분
                else if(chgGubun == "momsAreaFgChg") {
                    $scope.flex.collectionView.items[i].momsAreaFg = $scope.momsAreaFgChg;
                }
                // 상권
                else if(chgGubun == "momsCommercialChg") {
                    $scope.flex.collectionView.items[i].momsCommercial = $scope.momsCommercialChg;
                }
                // 점포유형
                else if(chgGubun == "momsShopTypeChg") {
                    $scope.flex.collectionView.items[i].momsShopType = $scope.momsShopTypeChg;
                }
                // 매장관리타입
                else if(chgGubun == "momsStoreManageTypeChg") {
                    $scope.flex.collectionView.items[i].momsStoreManageType = $scope.momsStoreManageTypeChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // <-- 그리드 저장 -->
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        $scope._popConfirm(messages["cmm.choo.save"], function() {

            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    $scope.flex.collectionView.items[i].status = "U";

                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/storeMoms/storeBatchChange/storeBatchChange/getStoreBatchChangeSave.sb", params, function(){
                $scope.searchStoreBatchChange();
            });
        });
    };
    // <-- //그리드 저장 -->

}]);