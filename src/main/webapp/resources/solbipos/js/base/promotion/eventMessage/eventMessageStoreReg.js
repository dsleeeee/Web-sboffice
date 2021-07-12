/****************************************************************
 *
 * 파일명 : eventMessageStoreReg.js
 * 설  명 : 이벤트문구출력관리 적용매장 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.06     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('eventMessageStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('eventMessageStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchStoreType", storeTypeList);
    $scope._setComboData("srchStoreGroup", storeGroupList);
    $scope._setComboData("srchSysStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
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

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("eventMessageStoreRegCtrl", function(event, data) {

        // 매장조회
        $scope.searchStore();
        event.preventDefault();

    });

    // 매장조회
    $scope.searchStore = function () {

        var params = {};
        params.msgCd = $("#hdMsgCd").val();
        params.storeTypeCd = $scope.srchStoreTypeCombo.selectedValue;
        params.storeGroupCd = $scope.srchStoreGroupCombo.selectedValue;
        params.storeCd = $("#srchStoreCd").val();
        params.storeNm = $("#srchStoreNm").val();
        params.sysStatFg = $scope.srchSysStatFgCombo.selectedValue;

        $scope._inquirySub("/base/promotion/eventMessage/getStoreList.sb", params, function () {});
    };

    // 조회
    $scope.btnSearchStore = function(){

        // 매장조회
        $scope.searchStore();
    };

    // 추가
    $scope.btnInsertStore = function () {

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg("추가할 " + messages["eventMessage.store"] + "의 체크박스" + messages["eventMessage.chk.item"]); // 추가할 매장의 체크박스을(를) 선택하세요.
            return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

            var item = $scope.flex.collectionView.items[i];

            if(item.gChk === true) {
                var obj = {};
                obj.status = "I";
                obj.msgCd = $("#hdMsgCd").val();
                obj.storeCd = item.storeCd;

                params.push(obj);
            }
        }
        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save("/base/promotion/eventMessage/saveEventMessageStore.sb", params, function () {

            $scope.eventMessageStoreRegLayer.hide(true);

            // 적용상품 목록 재조회
            $scope._pageView('selectStoreGridCtrl', 1);

        });
    }

    // 전매장적용
    $scope.btnInsertStoreAll = function () {

        // 해당 이벤트문구를 전매장에 적용하시겠습니까?
        $scope._popConfirm(messages["eventMessage.chk.setAllStore"], function() {
            var params = {};
            params.msgCd = $("#hdMsgCd").val();

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/promotion/eventMessage/insertEventMessageStoreAll.sb", params, function () {

                $scope.eventMessageStoreRegLayer.hide(true);

                // 적용상품 목록 재조회
                $scope._pageView('selectStoreGridCtrl', 1);

            });
        });
    }

}]);