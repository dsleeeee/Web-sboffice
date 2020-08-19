/****************************************************************
 *
 * 파일명 : hqEmpAuth.js
 * 설  명 : 사원관리 > 사원정보관리 > 사원메뉴권한 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.05.26     이다솜      1.0
 *
 * **************************************************************/

app.controller('hqEmpAuthCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqEmpAuthCtrl', $scope, $http, false));

    // 팝업 오픈시 사원메뉴권한 조회
    $scope.$on("hqEmpAuthCtrl", function(event, data) {

        // 제목
        $("#empInfo").text(" [" + data.empNo + "]" + data.empNm );

        $scope._broadcast('hqEmpWebMenuCtrl', data);

    });

    // 탭변경
    $scope.changeTab = function() {

        $scope.hqEmpDetailLayer.show(true);
        $scope.hqEmpAuthLayer.hide();

        event.preventDefault();
    };


}]);