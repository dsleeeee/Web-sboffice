/****************************************************************
 *
 * 파일명 : storePosAdd.js
 * 설  명 : 매장정보관리 > 포스추가 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.11.05     이다솜      1.0
 *
 * **************************************************************/
app.controller('storePosAddCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storePosAddCtrl', $scope, $http, false));

    $scope._setComboData("spa_addPosCombo", [
        {"name": "1", "value": "1"},
        {"name": "2", "value": "2"},
        {"name": "3", "value": "3"},
        {"name": "4", "value": "4"}
    ]);

    $scope.$on("storePosAddCtrl", function(event, data) {

        $scope.storePosAddLayer.show(true);

        $("#spa_storeCd").text(data.storeCd);
        $("#spa_storeNm").text(data.storeNm);
        $("#spa_posCnt").text(data.posCnt);
    });

    /*********************************************************
     * 설치포스수 추가
     * *******************************************************/
    $scope.savePosCnt = function(){

        var params = {};
        params.storeCd =  $("#spa_storeCd").text();
        params.installPosCnt = $scope.spa_addPosCombo;

        $scope.$broadcast('loadingPopupActive');
        $scope._postJSONSave.withOutPopUp( "/store/manage/storeManage/storeManage/savePosCnt.sb", params, function () {
            $scope.$broadcast('loadingPopupInactive');
            $scope._popMsg(messages["cmm.saveSucc"]);

            $scope.storePosAddLayer.hide();

            // 재조회
            $scope._broadcast('storeInfoCtrl');
        });
    };

}]);