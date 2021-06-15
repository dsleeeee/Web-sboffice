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

app.controller('kioskKeyMapStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskKeyMapStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchSysStatFg", sysStatFg);
    $scope._setComboData("applyTuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                // 체크박스
                if (col.binding === "gChk") {
                    var item = s.rows[e.row].dataItem;

                    // 값이 있으면 링크 효과
                    if (item[("kioskPosCnt")] === 0) {
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                        wijmo.setAttribute(e.cell, 'aria-readonly', true);
                        item[("gChk")] = false; // 전체 체크시 오류

                        // Attribute 의 변경사항을 적용.
                        e.cell.outerHTML = e.cell.outerHTML;
                    }
                }
            }
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

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreList.sb", params, function () {});
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
        $scope._popConfirm("'" + $scope.applyTuClsTypeCombo.selectedValue + "' " + messages["kioskKeyMap.keyMapStoreReg.msg"], function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];

                if (item.gChk === true) {
                    var obj = {};
                    obj.storeCd = item.storeCd;
                    obj.tuClsType = $scope.applyTuClsTypeCombo.selectedValue;

                    params.push(obj);
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
    }

}]);
