/****************************************************************
 *
 * 파일명 : sideMenuClassRegStore.js
 * 설  명 : 선택분류(적용매장) 탭 JavaScript
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

// 적용매장구분
var regStoreFgAllData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"제외매장","value":"1"},
    {"name":"허용매장","value":"2"}
];

// 필수선택여부
var requireYnData = [
    {"name":"선택안함","value":"N"},
    {"name":"필수선택","value":"Y"}
];

/**
 *  선택분류(적용매장) 조회 그리드 생성
 */
app.controller('sideMenuClassRegStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sideMenuClassRegStoreCtrl', $scope, $http, false));

    // 콤보박스 셋팅
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
    $scope.$on("sideMenuClassRegStoreCtrl", function(event, data) {
        $scope.searchSideMenuClassRegStore();
        event.preventDefault();
    });

    $scope.searchSideMenuClassRegStore = function() {
        var params = {};
        params.regStoreFg = $scope.regStoreFg;
        params.listScale = 500;

        $scope._inquiryMain("/base/prod/sideMenuStore/sideMenuClassRegStore/getSideMenuClassRegStoreList.sb", params, function() {}, false);
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
                // 적용매장구분
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
            $scope._postJSONSave.withOutPopUp("/base/prod/sideMenuStore/sideMenuClassRegStore/getSideMenuClassRegStoreDeleteAll.sb", params, function(){
                // 저장
                $scope.save2(params);
            });
        });
    };

    $scope.save2 = function(params) {
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/prod/sideMenuStore/sideMenuClassRegStore/getSideMenuClassRegStoreSave.sb", params, function(){
            $scope.searchSideMenuClassRegStore();
        });
    };

}]);