/****************************************************************
 *
 * 파일명 : hqEmpWebMenu.js
 * 설  명 : 사원관리 > 사원정보관리 > 사원메뉴권한 > 웹사이트 메뉴 Tab JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.29     이다솜      1.0
 *
 * **************************************************************/

// 모바일 메뉴 탭 클릭
function changeMobileTab(){
    var scope = agrid.getScope('webMenuCtrl');
    scope.changeMobileTab();
}

app.controller('webMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('webMenuCtrl', $scope, $http, false));

    // 본사 콤보박스 셋팅
    $scope._setComboData("empNoCombo", authHqEmpList);

    // 팝업 오픈시 사원메뉴권한 조회
    $scope.$on("webMenuCtrl", function(event, data) {

    });

    //
    $scope.changeMobileTab = function(){
        s_alert.pop("준비중인 메뉴입니다.");
        return;
    }

}]);

app.controller('notUseWebMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('notUseWebMenuCtrl', $scope, $http, false));

    //
    $scope.initGrid = function (s, e) {

    };

    // 팝업 오픈시 매장정보 조회
    $scope.$on("notUseWebMenuCtrl", function (event, data) {

        // 미사용메뉴 리스트 조회
        $scope.notUsemenuList();

    });

    // 미사용메뉴 리스트 조회
    $scope.notUsemenuList = function () {

        // 사용메뉴 조회
        var params = [];
        params.storeCd = vStoreCd;
        $scope._inquirySub("/store/manage/storeManage/storeManage/beUseMenu.sb", params, function() {

        });
    };

    // 사용등록
    $scope.useReg = function(){

        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
            if($scope.flex.collectionView.items[i].gChk) {
                $scope.flex.collectionView.items[i].storeCd = vStoreCd;
                $scope.flex.collectionView.items[i].resrceCd = $scope.flex.collectionView.items[i].resrceCdSmall;
                params.push($scope.flex.collectionView.items[i]);
            }
        }

        if(params.length <= 0) {
            $scope._popMsg(messages["storeManage.require.chkUseMenu"]);
            return false;
        }

        // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
        $scope._save('/store/manage/storeManage/storeManage/addAuth.sb', params, function() {
            // 저장 후 재조회
            $scope._broadcast('notUseWebMenuCtrl');
            $scope._broadcast('webMenuCtrl');
        });
    };

}]);