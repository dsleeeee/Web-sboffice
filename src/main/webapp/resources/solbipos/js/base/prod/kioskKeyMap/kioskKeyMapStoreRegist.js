/****************************************************************
 *
 * 파일명 : kioskKeyMapStoreReg.js
 * 설  명 : 키오스크 키맵매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.07     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

// KIOSK중분류사용
var tuMClsFgStoreRegistComboData = [
    {"name":"전체","value":""},
    {"name":"미사용","value":"0"},
    {"name":"중분류사용","value":"2"}
];

app.controller('kioskKeyMapStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchSysStatFg", sysStatFg);
    $scope._setComboData("applyTuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록
    $scope._setComboData("tuMClsFgStoreRegist", tuMClsFgStoreRegistComboData); // KIOSK중분류사용

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
        $scope.tuMClsFgDataMap = new wijmo.grid.DataMap(tuMClsFgStoreRegistComboData, 'value', 'name'); // KIOSK중분류사용

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskKeyMapStoreRegCtrl", function(event, data) {

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;
        params.tuMClsFg = $scope.tuMClsFgStoreRegistCombo.selectedValue;

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreList.sb", params, function () {

            // 키오스크포스가 없는 매장은 선택 불가
            var grid = wijmo.Control.getControl("#wjGridKioskKeyMapStoreReg");
            var rows = grid.rows;

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                if(item.kioskPosCnt === 0){
                    item.gChk = false;
                    rows[i].isReadOnly = true;
                }
            }
        });
    };

    // 조회
    $scope.btnSearchStore = function(){

        // 매장조회
        $scope.searchStore();
    };

    // 적용
    $scope.btnInsertStore = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        // 키맵그룹이 없습니다.
        if ($scope.applyTuClsTypeCombo.selectedValue === "" || $scope.applyTuClsTypeCombo.selectedValue === null) {
            s_alert.pop(messages["kioskKeyMap.require.applyTuClsType.msg"]);
            return;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        // '01' 키맵그룹을 매장에 적용하시겠습니까?
        // <br/> (중분류 사용 키맵그룹은 중분류 사용 키오스크에만 적용됩니다.)
        $scope._popConfirm("'" + $scope.applyTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreReg.msg"] + messages["kioskKeyMap.kioskTuMClsFg.msg"], function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true && item.kioskPosCnt > 0) {

                    if($("#hdTuMClsFgStoreRegist").val() === item.tuMClsFg) {

                        var obj = {};
                        obj.storeCd = item.storeCd;
                        obj.tuClsType = $scope.applyTuClsTypeCombo.selectedValue;
                        obj.posNo = item.posNo;
                        obj.tuMClsFg = $("#hdTuMClsFgStoreRegist").val(); // 매장 적용시에만 UPDATE / 매장 해당컬럼 사용안함 / 수정 기록 확인용 / 매장은 [4101 KIOSK중분류사용] 사용

                        params.push(obj);
                    }
                }
            }
            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveKioskKeyMapStore.sb", params, function () {

                $scope.kioskKeyMapStoreRegLayer.hide(true);

            });
        });

    };

    // 닫기
    $scope.close = function () {

        $("#srchStoreCd").val("");
        $("#srchStoreNm").val("");
        $scope.srchSysStatFgCombo.selectedIndex = 0;
        $scope.applyTuClsTypeCombo.selectedIndex = 0;
    };

    // 적용할 키맵그룹 선택 이벤트
    $scope.setApplyTuClsTypeCombo = function (s){
        var params = {};
        params.tuClsType = s.selectedValue;

        // 키맵그룹에 KIOSK중분류사용 조회
        $scope.kioskKeyMapGroupTuMClsFg(params);
    };

    // 키맵그룹에 KIOSK중분류사용 조회
    $scope.kioskKeyMapGroupTuMClsFg = function(params){
        $scope._postJSONQuery.withOutPopUp("/base/prod/kioskKeyMap/kioskKeyMap/getKioskKeyMapGroupTuMClsFg.sb", params, function(response) {
            var list = response.data.data.list;
            if(list.length > 0) {
                var tuMClsFgNm;
                if(list[0].tuMClsFg == 0) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 미사용)";
                } else if(list[0].tuMClsFg == 2) {
                    tuMClsFgNm = "(KIOSK중분류사용 : 중분류사용)";
                }
                $("#lblTuMClsFgStoreRegist").text(tuMClsFgNm);
                $("#hdTuMClsFgStoreRegist").val(list[0].tuMClsFg);
            }
        }, false);
    };

}]);
