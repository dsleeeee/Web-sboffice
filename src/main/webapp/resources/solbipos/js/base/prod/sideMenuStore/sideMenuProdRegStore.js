/****************************************************************
 *
 * 파일명 : sideMenuProdRegStore.js
 * 설  명 : 선택상품(적용매장) 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.20     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  선택상품(적용매장) 조회 그리드 생성
 */
app.controller('sideMenuProdRegStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuProdRegStoreCtrl', $scope, $http, false));

    // 콤보박스 셋팅
    $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜드
    $scope._setComboData("regStoreFgCombo", regStoreFgAllData); // 적용매장구분
    $scope._setComboData("regStoreFgChgCombo", regStoreFgData); // 일괄변경 - 적용매장구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 DataMap 설정
        $scope.requireYnDataMap = new wijmo.grid.DataMap(requireYnData, 'value', 'name'); // 필수선택여부
        $scope.regStoreFgDataMap = new wijmo.grid.DataMap(regStoreFgData, 'value', 'name'); // 적용매장구분

        // 그리드 값 변경 시 체크박스 체크
        s.cellEditEnded.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                var item = s.rows[e.row].dataItem;
                if (col.binding === "regStoreFg") {
                    item.gChk = true;
                }
            }
            s.collectionView.commitEdit();
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("sideMenuProdRegStoreCtrl", function(event, data) {
        $scope.searchSideMenuProdRegStore();
        event.preventDefault();
    });

    $scope.searchSideMenuProdRegStore = function() {
        var params = {};
        if(brandUseFg === "1") {
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if (params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for (var i = 0; i < userHqBrandCdComboList.length; i++) {
                    if (userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }
        params.regStoreFg = $scope.regStoreFg;
        params.listScale = 500;

        $scope._inquiryMain("/base/prod/sideMenuStore/sideMenuProdRegStore/getSideMenuProdRegStoreList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 상품분류정보 팝업
    $scope.popUpProdClass = function() {
        var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
            // 선택 버튼 눌렀을때만
            if (s.dialogResult === "wj-hide-apply") {
                var scope = agrid.getScope('prodClassPopUpCtrl');
                var prodClassCd = scope.getSelectedClass();
                var params = {};
                params.prodClassCd = prodClassCd;
                // 조회 수행 : 조회URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                    function(response){
                        $scope.prodClassCd = prodClassCd;
                        $scope.prodClassNm = response.data.data;
                    }
                );
            }
        });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
        $scope.prodClassCd = "";
        $scope.prodClassNm = "";
    };

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
                // 등록구분
                if(chgGubun == "regStoreFgChg") {
                    $scope.flex.collectionView.items[i].regStoreFg = $scope.regStoreFgChg;
                }
            }
        }
        $scope.flex.refresh();
    };

    // 저장
    $scope.save = function() {
        if($scope.flex.rows.length <= 0) {
            $scope._popMsg(messages["cmm.empty.data"]);
            return false;
        }

        // 적용매장구분 변경된 경우 등록된 적용매장은 모두 삭제됩니다. 저장하시겠습니까?
        $scope._popConfirm(messages["sideMenu.selectMenu.sdselClassRegStoreAlert"] + " " +  messages["cmm.choo.save"], function() {
            // 파라미터 설정
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                if($scope.flex.collectionView.items[i].gChk) {
                    params.push($scope.flex.collectionView.items[i]);
                }
            }

            // 적용매장 전체 삭제
            $scope._postJSONSave.withOutPopUp("/base/prod/sideMenuStore/sideMenuProdRegStore/getSideMenuProdRegStoreDeleteAll.sb", params, function(){
                // 저장
                $scope.save2(params);
            });
        });
    };

    $scope.save2 = function(params) {
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/sideMenuStore/sideMenuProdRegStore/getSideMenuProdRegStoreSave.sb", params, function(){
            $scope.searchSideMenuProdRegStore();
        });
    };

}]);