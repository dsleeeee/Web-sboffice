/****************************************************************
 *
 * 파일명 : kioskRecmdStoreReg.js
 * 설  명 : 키오스크 추천메뉴 매장적용 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.18     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('kioskRecmdStoreRegCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('kioskRecmdStoreRegCtrl', $scope, $http, false));

    $scope._setComboData("srchRecmdSysStatFg", sysStatFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

        $scope.sysStatFgDataMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

        // 그리드 header 클릭시 정렬 이벤트 막기
        s.addEventListener(s.hostElement, 'mousedown', function (e) {
            var ht = s.hitTest(e);
            s.allowSorting = false;
        });
    };

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("kioskRecmdStoreRegCtrl", function(event, data) {

        // 매장조회
        $scope.searchRecmdStore();
        event.preventDefault();
    });
    
    // 매장조회
    $scope.searchRecmdStore = function () {

        var params = {};
        params.storeCd = $("#srchRecmdStoreCd").val();
        params.storeNm = $("#srchRecmdStoreNm").val();
        params.sysStatFg = $scope.srchRecmdSysStatFgCombo.selectedValue;

        $scope._inquirySub("/base/prod/kioskKeyMap/kioskKeyMap/getStoreList.sb", params, function () {

            // 키오스크포스가 없는 매장은 선택 불가
            var grid = wijmo.Control.getControl("#wjGridKioskRecmdStoreReg");
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
    $scope.btnRecmdSearchStore = function(){

        // 매장조회
        $scope.searchRecmdStore();
    };
    
    // 적용
    $scope.btnRecmdApplyStore = function(){

        // 파라미터 설정
        var params = new Array();

        // 선택한 매장이 있는지 체크
        var chkCount = 0;
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            var item = $scope.flex.collectionView.items[i];
            if(item.gChk === true) chkCount++;
        }

        if(chkCount === 0){
            $scope._popMsg(messages["kioskKeymap.store"] + messages["kioskKeymap.chk.item"]); // 매장을(를) 선택하세요.
            return false;
        }

        // 추천메뉴정보를 매장에 적용하시겠습니까?
        $scope._popConfirm(messages["kioskKeyMap.recmdStoreReg.msg"], function() {

            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {

                var item = $scope.flex.collectionView.items[i];
                if (item.gChk === true && item.kioskPosCnt > 0) {
                    var obj = {};
                    obj.storeCd = item.storeCd;

                    params.push(obj);
                }
            }

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._save("/base/prod/kioskKeyMap/kioskKeyMap/saveRecmdStore.sb", params, function () {

                $scope.kioskRecmdStoreRegLayer.hide(true);

            });
        });
    };

    // 닫기
    $scope.close = function () {

        $("#srchRecmdStoreCd").val("");
        $("#srchRecmdStoreNm").val("");
        $scope.srchRecmdSysStatFgCombo.selectedIndex = 0;
    }

}]);