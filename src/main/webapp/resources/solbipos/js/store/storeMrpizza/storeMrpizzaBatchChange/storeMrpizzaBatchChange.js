/****************************************************************
 *
 * 파일명 : storeMrpizzaBatchChange.js
 * 설  명 : 매장정보일괄변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.08.18     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  매장정보일괄변경 그리드 생성
 */
app.controller('storeMrpizzaBatchChangeCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeMrpizzaBatchChangeCtrl', $scope, $http, false));

    // 브랜드 콤보박스 셋팅
    $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
    $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
    $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
    $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형

    $scope._setComboData("momsTeamChgCombo", momsTeamComboList3); // 팀별
    $scope._setComboData("momsAreaFgChgCombo", momsAreaFgComboList3); // 지역구분
    $scope._setComboData("momsCommercialChgCombo", momsCommercialComboList3); // 상권
    $scope._setComboData("momsShopTypeChgCombo", momsShopTypeComboList3); // 점포유형

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList2, 'value', 'name'); // 팀별
        $scope.momsAreaFgDataMap = new wijmo.grid.DataMap(momsAreaFgComboList2, 'value', 'name'); // 지역구분
        $scope.momsCommercialDataMap = new wijmo.grid.DataMap(momsCommercialComboList2, 'value', 'name'); // 상권
        $scope.momsShopTypeDataMap = new wijmo.grid.DataMap(momsShopTypeComboList2, 'value', 'name'); // 점포유형

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
                if (col.binding === "momsTeam" || col.binding === "momsAreaFg"
                    || col.binding === "momsCommercial" || col.binding === "momsShopType") {
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

    // <-- 검색 호출 -->
    $scope.$on("storeMrpizzaBatchChangeCtrl", function(event, data) {
        $scope.searchstoreMrpizzaBatchChange();
        event.preventDefault();
    });

    $scope.searchstoreMrpizzaBatchChange = function(){
        var params = {};
        params.listScale = 500;
        console.log(params);

        $scope._inquiryMain("/store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChange/getStoreList.sb", params);
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
                // 팀별
                if(chgGubun == "momsTeamChg") {
                    $scope.flex.collectionView.items[i].momsTeam = $scope.momsTeamChg;
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

                    if(nvl($scope.flex.collectionView.items[i].branchCd,'') !== nvl($scope.flex.collectionView.items[i].oldBranchCd,'')){
                        $scope.flex.collectionView.items[i].storeFg = '1';
                    }

                    if(nvl($scope.flex.collectionView.items[i].momsTeam,'') !== nvl($scope.flex.collectionView.items[i].oldMomsTeam,'')
                        || nvl($scope.flex.collectionView.items[i].momsAreaFg,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAreaFg,'')
                        || nvl($scope.flex.collectionView.items[i].momsCommercial,'') !== nvl($scope.flex.collectionView.items[i].oldMomsCommercial,'')
                        || nvl($scope.flex.collectionView.items[i].momsShopType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsShopType,'')) {

                        $scope.flex.collectionView.items[i].storeInfoFg = '1';
                    }

                    if(nvl($scope.flex.collectionView.items[i].momsTeam,'') !== nvl($scope.flex.collectionView.items[i].oldMomsTeam,'')
                        || nvl($scope.flex.collectionView.items[i].momsAreaFg,'') !== nvl($scope.flex.collectionView.items[i].oldMomsAreaFg,'')
                        || nvl($scope.flex.collectionView.items[i].momsCommercial,'') !== nvl($scope.flex.collectionView.items[i].oldMomsCommercial,'')
                        || nvl($scope.flex.collectionView.items[i].momsShopType,'') !== nvl($scope.flex.collectionView.items[i].oldMomsShopType,'')) {

                        $scope.flex.collectionView.items[i].status = "U";
                        params.push($scope.flex.collectionView.items[i]);
                    }
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChange/getStoreBatchChangeSave.sb", params, function(){
                $scope.searchstoreMrpizzaBatchChange();
            });
        });
    };
    // <-- //그리드 저장 -->

}]);