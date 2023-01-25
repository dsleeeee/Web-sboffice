/****************************************************************
 *
 * 파일명 : sysConnect.js
 * 설  명 : 정산시스템접속 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.22     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  예약현황 목록 생성
 */
app.controller('sysConnectCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('sysConnectCtrl', $scope, $http, false));

    $scope.init = function (s, e) {
        $scope._broadcast('sysConnectCtrl');
    };

    // <-- 검색 호출 -->
    $scope.$on("sysConnectCtrl", function(event, data) {
        $scope.sysConnect();
        event.preventDefault();
    });
    
    // 시스템 접속
    $scope.sysConnect = function(){
        console.log(envst1260);
        if(orgnFg === "HQ"){
            window.open(envst1260 + hqOfficeCd);
        } else if(orgnFg === "STORE"){
            window.open(envst1260 + storeCd);
        }
    };

}]);