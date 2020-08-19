/****************************************************************
 *
 * 파일명 : useMenu.js
 * 설  명 : 사용메뉴 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.28     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  사용메뉴 그리드 생성
 */
app.controller('useMenuCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('useMenuCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 합계
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // <-- 검색 호출 -->
    $scope.$on("useMenuCtrl", function(event, data) {
        $scope.setSelectedUseMenu(data);
        $scope.searchUseMenu();
        event.preventDefault();
    });

    $scope.searchUseMenu = function(){
        var params = {};
        params.startDate = $scope.selectedUseMenu.startDate;
        params.endDate = $scope.selectedUseMenu.endDate;
        params.resrceNm = $scope.selectedUseMenu.resrceNm;
        params.userId = $scope.selectedUseMenu.userId;
        params.hqOfficeCd = $scope.selectedUseMenu.hqOfficeCd;
        params.storeCd = $scope.selectedUseMenu.storeCd;
        params.level = $scope.selectedUseMenu.level;

        $scope._inquiryMain("/sys/stats/userBase/useMenu/getUseMenuList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedUseMenu;
    $scope.setSelectedUseMenu = function(useMenu) {
        $scope.selectedUseMenu = useMenu;
    };
    $scope.getSelectedUseMenu = function(){
        return $scope.selectedUseMenu;
    };

    // 사용자 탭
    $scope.userTab = function() {

        var storeScope = agrid.getScope('useMenuCtrl');

        if($.isEmptyObject(storeScope.getSelectedUseMenu()) ) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }

        $("#userLayer").show();
        $("#useMenuLayer").hide();

        $scope._broadcast('userCtrl', $scope.getSelectedUseMenu());
    };

}]);