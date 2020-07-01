/****************************************************************
 *
 * 파일명 : storeEmpAuth.js
 * 설  명 : 기초관리 > 매장관리 > 매장사원정보관리 > 사원메뉴권한 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.22     이다솜      1.0
 *
 * **************************************************************/

app.controller('storeEmpAuthCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeEmpAuthCtrl', $scope, $http, false));

    // 팝업 오픈시 사원메뉴권한 조회
    $scope.$on("storeEmpAuthCtrl", function(event, data) {

        // 제목
        $("#empInfo").text(" [" + data.empNo + "]" + data.empNm );

        $scope._broadcast('storeEmpWebMenuCtrl', data);

    });

    // 탭변경
    $scope.changeTab = function() {

        $scope.storeEmpDetailLayer.show(true);
        $scope.storeEmpAuthLayer.hide();

        event.preventDefault();
    };


}]);