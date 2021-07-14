/****************************************************************
 *
 * 파일명 : storeTypeApplyStore.js
 * 설  명 : 매장타입관리 - 매장타입 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.07.02     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매장타입관리 - 매장타입 매장적용 팝업
app.controller('storeTypeApplyStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeTypeApplyStoreCtrl', $scope, $http, true));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("srchPopStoreType", storeTypeList);
    $scope._setComboData("srchPopStoreGroup", storeGroupList);
    $scope._setComboData("srchPopSysStatFg", sysStatFg);

    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];
                if (col.binding === "storeGroupNms") {

                    var item = s.rows[e.row].dataItem;
                    if (item.storeGroupNms === "()") {
                        e.cell.innerHTML = "";
                    }
                }
            }
        });
    };

    $scope.$on("storeTypeApplyStoreCtrl", function(event, data) {

        // 매장 조회
        $scope.searchPopStore();
        event.preventDefault();
        
    });

    // 매장 조회
    $scope.searchPopStore = function () {

        var params = {};
        params.storeTypeCd = $scope.srchPopStoreTypeCombo.selectedValue;
        params.storeGroupCd = $scope.srchPopStoreGroupCombo.selectedValue;
        params.storeCd = $("#srchPopStoreCd").val();
        params.storeNm = $("#srchPopStoreNm").val();
        params.sysStatFg = $scope.srchPopSysStatFgCombo.selectedValue;

        $scope._inquirySub("/base/store/storeType/storeType/getStoreTypeApplyStoreList.sb", params, function () {

        });
    };

    // 매장타입 매장적용
    $scope.storeTypeApply = function () {

        $scope.flex.collectionView.commitEdit();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["storeType.storeChk.msg"]); // 적용할 매장을 선택하세요.
            return false;
        }

        var params = new Array();

        for (var i = 0; i < $scope.flex.collectionView.itemCount; i++) {

            var item =  $scope.flex.collectionView.items[i];

            if(item.gChk === true) {

                var obj = {};
                obj.storeCd = item.storeCd;
                obj.commentRemark = "TB_HQ_STORE_TYPE_APP 에 직접 등록";

                if(storeTypeApplyEnvstVal === "1") { // 매장타입판매가설정(1107) 미사용시, 기본 applyFg = 0

                    if ($("#chkSaleUprcApply").is(":checked")) {
                        obj.applyFg = "1";
                    } else {
                        obj.applyFg = "0";
                    }
                }else{
                    obj.applyFg = "0";
                }

                params.push(obj);
            }
        }

        $scope._popConfirm(messages["storeType.storeApply.msg"], function() {

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/store/storeType/storeType/saveStoreTypeApplyStore.sb", params, function () {

                $scope.storeTypeApplyStoreLayer.hide(true);
                $scope.close();

            });

        });

    };

    // 닫기
    $scope.close = function () {

        $scope.srchPopStoreTypeCombo.selectedIndex = 0;
        $scope.srchPopStoreGroupCombo.selectedIndex = 0;
        $("#srchPopStoreCd").val("");
        $("#srchPopStoreNm").val("");
        $scope.srchPopSysStatFgCombo.selectedIndex = 0;

        $("input:checkbox[id='chkSaleUprcApply']").prop("checked", false);

    };

}]);