/****************************************************************
 *
 * 파일명 : user.js
 * 설  명 : 사용자 탭 JavaScript
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
 *  사용자 그리드 생성
 */
app.controller('userCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('userCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // 그리드 링크 효과
        s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
                var col = s.columns[e.col];

                if (col.format === "date") {
                    e.cell.innerHTML = getFormatDate(e.cell.innerText);
                } else if (col.format === "dateTime") {
                    e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                } else if (col.format === "time") {
                    e.cell.innerHTML = getFormatTime(e.cell.innerText, 'hms');
                }
            }
        });
    };

    // <-- 검색 호출 -->
    $scope.$on("userCtrl", function(event, data) {
        $scope.setSelectedUser(data);
        $scope.searchUser();
        event.preventDefault();
    });

    $scope.searchUser = function(){
        var params = {};
        params.startDate = $scope.selectedUser.startDate;
        params.endDate = $scope.selectedUser.endDate;
        params.resrceNm = $scope.selectedUser.resrceNm;
        params.userId = $scope.selectedUser.userId;
        params.hqOfficeCd = $scope.selectedUser.hqOfficeCd;
        params.storeCd = $scope.selectedUser.storeCd;
        params.level = $scope.selectedUser.level;

        $scope._inquiryMain("/sys/stats/userBase/user/getUserList.sb", params, function() {}, false);
    };
    // <-- //검색 호출 -->

    // 선택 매장
    $scope.selectedUser;
    $scope.setSelectedUser = function(user) {
        $scope.selectedUser = user;
    };
    $scope.getSelectedUser = function(){
        return $scope.selectedUser;
    };

    // 사용메뉴 탭
    $scope.useMenuTab = function() {

        var storeScope = agrid.getScope('userCtrl');

        if($.isEmptyObject(storeScope.getSelectedUser()) ) {
            $scope._popMsg(messages["cmm.not.select"]);
            return false;
        }

        $("#userLayer").hide();
        $("#useMenuLayer").show();

        $scope._broadcast('useMenuCtrl', $scope.getSelectedUser());
    };

}]);