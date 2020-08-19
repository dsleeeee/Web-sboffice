/****************************************************************
 *
 * 파일명 : storeAuth.js
 * 설  명 : 매장정보관리 > 매장메뉴권한 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.12     이다솜      1.0
 *
 * **************************************************************/

app.controller('storeAuthCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeAuthCtrl', $scope, $http, false));

    // 팝업 오픈시 매장메뉴권한 조회
    $scope.$on("storeAuthCtrl", function(event, data) {
        $scope.showWebMenu();
    });

    $scope.showWebMenu = function(){

        $("#webMenuArea").show();
        //$("#mobileMenuArea").hide();
        
        $scope._broadcast('webMenuCtrl');
    }

    /*********************************************************
     * 매장정보 탭 클릭
     * *******************************************************/
    $scope.changeInfoTab = function(){

        var storeScope = agrid.getScope('storeManageCtrl');

        if($.isEmptyObject(storeScope.getSelectedStore()) ) {
            $scope._popMsg(messages["storeManage.require.regist.store1"]);
            return false;
        }

        $scope.storeAuthLayer.hide();

        var infoPopup = $scope.storeInfoLayer;

        // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
        infoPopup.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('storeInfoCtrl');
            }, 50)
        });

        // 팝업 닫을때
        infoPopup.show(true, function (s) {
        });
    };

    /*********************************************************
     * 매장환경 탭 클릭
     * *******************************************************/
    $scope.changeEnvTab = function(){

        var storeScope = agrid.getScope('storeManageCtrl');

        if($.isEmptyObject(storeScope.getSelectedStore()) ) {
            $scope._popMsg(messages["storeManage.require.regist.store1"]);
            return false;
        }

        $scope.storeAuthLayer.hide();

        var envPopup = $scope.storeEnvLayer;

        // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
        envPopup.shown.addHandler(function (s) {
            setTimeout(function() {
                $scope._broadcast('storeEnvCtrl');
            }, 50)
        });

        // 팝업 닫을때
        envPopup.show(true, function (s) {
        });
    };

}]);