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
    $scope._setComboData("branchCdCombo", branchCdComboList); // 그룹
    $scope._setComboData("momsStoreFg01Combo", momsStoreFg01ComboList); // 매장그룹
    $scope._setComboData("momsStoreFg02Combo", momsStoreFg02ComboList); // 매장그룹2
    $scope._setComboData("momsStoreFg03Combo", momsStoreFg03ComboList); // 매장그룹3
    $scope._setComboData("momsStoreFg04Combo", momsStoreFg04ComboList); // 매장그룹4
    $scope._setComboData("momsStoreFg05Combo", momsStoreFg05ComboList); // 매장그룹5

    $scope._setComboData("momsTeamChgCombo", momsTeamComboList3); // 팀별
    $scope._setComboData("momsAcShopChgCombo", momsAcShopComboList3); // AC점포별
    $scope._setComboData("momsAreaFgChgCombo", momsAreaFgComboList3); // 지역구분
    $scope._setComboData("momsCommercialChgCombo", momsCommercialComboList3); // 상권
    $scope._setComboData("momsShopTypeChgCombo", momsShopTypeComboList3); // 점포유형
    $scope._setComboData("momsStoreManageTypeChgCombo", momsStoreManageTypeComboList3); // 매장관리타입
    $scope._setComboData("branchCdChgCombo", branchCdComboList3); // 그룹
    $scope._setComboData("momsStoreFg01ChgCombo", momsStoreFg01ComboList3); // 매장그룹
    $scope._setComboData("momsStoreFg02ChgCombo", momsStoreFg02ComboList3); // 매장그룹2
    $scope._setComboData("momsStoreFg03ChgCombo", momsStoreFg03ComboList3); // 매장그룹3
    $scope._setComboData("momsStoreFg04ChgCombo", momsStoreFg04ComboList3); // 매장그룹4
    $scope._setComboData("momsStoreFg05ChgCombo", momsStoreFg05ComboList3); // 매장그룹5

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.branchCdDataMap = new wijmo.grid.DataMap(branchCdComboList2, 'value', 'name'); // 그룹
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList2, 'value', 'name'); // AC점포별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형
        $scope.momsStoreManageTypeDataMap = new wijmo.grid.DataMap(momsStoreManageTypeComboList2, 'value', 'name'); // 매장관리타입
        $scope.momsStoreFg01DataMap = new wijmo.grid.DataMap(momsStoreFg01ComboList3, 'value', 'name'); // 매장그룹
        $scope.momsStoreFg02DataMap = new wijmo.grid.DataMap(momsStoreFg02ComboList3, 'value', 'name'); // 매장그룹2
        $scope.momsStoreFg03DataMap = new wijmo.grid.DataMap(momsStoreFg03ComboList3, 'value', 'name'); // 매장그룹3
        $scope.momsStoreFg04DataMap = new wijmo.grid.DataMap(momsStoreFg04ComboList3, 'value', 'name'); // 매장그룹4
        $scope.momsStoreFg05DataMap = new wijmo.grid.DataMap(momsStoreFg05ComboList3, 'value', 'name'); // 매장그룹5

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });


        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                // 가격 변경시 체크박스 체크
                if (col.binding === "branchCd" || col.binding === "momsTeam" || col.binding === "momsAcShop"
                    || col.binding === "momsAreaFg" || col.binding === "momsCommercial" || col.binding === "momsShopType"
                    || col.binding === "momsStoreManageType" || col.binding === "momsStoreFg01"
                    || col.binding === "momsStoreFg02" || col.binding === "momsStoreFg03" || col.binding === "momsStoreFg04" || col.binding === "momsStoreFg05") {
                    $scope.checked(item);
                }
            }
            s.collectionView.commitEdit();
        });
    };

    //수정시 체크박스 체크
    $scope.checked = function (item){
        item.gChk = true;
    }

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
        if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) { // 확인완료 1992
            var momsHqBrandCd = "";
            for(var i=0; i < momsHqBrandCdComboList.length; i++){
                if(momsHqBrandCdComboList[i].value !== null) {
                    momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
                }
            }
            params.userBrands = momsHqBrandCd;
        }
        params.momsStoreFg01 = $scope.momsStoreFg01;
        params.momsStoreFg02 = $scope.momsStoreFg02;
        params.momsStoreFg03 = $scope.momsStoreFg03;
        params.momsStoreFg04 = $scope.momsStoreFg04;
        params.momsStoreFg05 = $scope.momsStoreFg05;
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
                // 그룹코드
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
                // 매장그룹
                else if(chgGubun == "momsStoreFg01Chg") {
                    $scope.flex.collectionView.items[i].momsStoreFg01 = $scope.momsStoreFg01Chg;
                }
                // 매장그룹2
                else if(chgGubun == "momsStoreFg02Chg") {
                    $scope.flex.collectionView.items[i].momsStoreFg02 = $scope.momsStoreFg02Chg;
                }
                // 매장그룹3
                else if(chgGubun == "momsStoreFg03Chg") {
                    $scope.flex.collectionView.items[i].momsStoreFg03 = $scope.momsStoreFg03Chg;
                }
                // 매장그룹4
                else if(chgGubun == "momsStoreFg04Chg") {
                    $scope.flex.collectionView.items[i].momsStoreFg04 = $scope.momsStoreFg04Chg;
                }
                // 매장그룹5
                else if(chgGubun == "momsStoreFg05Chg") {
                    $scope.flex.collectionView.items[i].momsStoreFg05 = $scope.momsStoreFg05Chg;
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

                    if(nvl($scope.flex.collectionView.items[i].branchCd,'') !== nvl($scope.flex.collectionView.items[i].oldBranchCd,'')
                        || nvl($scope.flex.collectionView.items[i].momsTeam,'') !== nvl($scope.flex.collectionView.items[i].oldMomsTeam,'')
                        || nvl($scope.flex.collectionView.items[i].momsAcShop,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAcShop,'')
                        || nvl($scope.flex.collectionView.items[i].momsAreaFg,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAreaFg,'')
                        || nvl($scope.flex.collectionView.items[i].momsCommercial,'') !== nvl($scope.flex.collectionView.items[i].oldMomsCommercial,'')
                        || nvl($scope.flex.collectionView.items[i].momsShopType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsShopType,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreManageType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreManageType,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreFg01,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg01,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreFg02,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg02,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreFg03,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg03,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreFg04,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg04,'')
                        || nvl($scope.flex.collectionView.items[i].momsStoreFg05,'') !== nvl($scope.flex.collectionView.items[i].oldMomsStoreFg05,'')) {

                        $scope.flex.collectionView.items[i].status = "U";
                        params.push($scope.flex.collectionView.items[i]);
                    }
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