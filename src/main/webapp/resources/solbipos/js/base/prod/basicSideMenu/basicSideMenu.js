/****************************************************************
 *
 * 파일명 : basicSideMenu.js
 * 설  명 : (기준)사이드메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.08.07     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * (기준)사이드메뉴 생성
 */
app.controller('basicSideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('basicSideMenuCtrl', $scope, $http, false));

    // 선택메뉴 탭
    $scope.isSelectMenuTab = false;
    // 선택메뉴(싱글) 탭
    $scope.isSelectMenuSingleTab = true;

    // 탭변경
    $scope.changeTab = function (type) {

        // 선택메뉴 탭
        if (type === 'C') {
            $("#sideMenuSelectMenu").addClass("on");
            $("#sideMenuSelectMenuSingle").removeClass("on");
            $scope.isSelectMenuTab = false;
            $scope.isSelectMenuSingleTab = true;
            // 선택그룹 조회
            $scope._broadcast("basicSideMenuSelectGroupCtrl");
            // 그리드 refresh
            setTimeout(function () {
                $scope._broadcast("selectMenuRefresh");
            }, 10);
            // 선택메뉴(싱글) 탭
        } else if (type === 'S') {
            $("#sideMenuSelectMenu").removeClass("on");
            $("#sideMenuSelectMenuSingle").addClass("on");
            $scope.isSelectMenuTab = true;
            $scope.isSelectMenuSingleTab = false;
            // 선택그룹(싱글) 조회
            $scope._broadcast("basicSideMenuSelectGroupSingleCtrl");
            // 그리드 refresh
            setTimeout(function () {
                $scope._broadcast("selectMenuSingleRefresh");
            }, 10);
        }
    };

    // 탭 조회
    $scope.queryTab = function () {

        if (!$scope.isSelectMenuTab) {

            $("#sideSelectGroupTitle").html("");
            var attrScope = agrid.getScope('basicSideMenuSelectClassCtrl');
            attrScope._gridDataInit();   // 선택분류 그리드 초기화

            $("#sideClassTitle").html("");
            var prodScope = agrid.getScope('basicSideMenuSelectProdCtrl');
            prodScope._gridDataInit();   // 선택상품 그리드 초기화

            // 선택그룹 조회
            $scope._broadcast("basicSideMenuSelectGroupCtrl");

        } else if (!$scope.isSelectMenuSingleTab) {

            $("#sideSelectGroupSingleTitle").html("");
            var attrScope = agrid.getScope('basicSideMenuSelectClassSingleCtrl');
            attrScope._gridDataInit();   // 선택분류(싱글) 그리드 초기화

            $("#sideClassSingleTitle").html("");
            var prodScope = agrid.getScope('basicSideMenuSelectProdSingleCtrl');
            prodScope._gridDataInit();   // 선택상품(싱글) 그리드 초기화

            // 선택그룹(싱글) 조회
            $scope._broadcast("basicSideMenuSelectGroupSingleCtrl");

        }
    };
}]);