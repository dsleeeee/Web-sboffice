/****************************************************************
 *
 * 파일명 : empManage.js
 * 설  명 : 사원관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.10.23     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('empManageCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empManageCtrl', $scope, $http, true));

    $scope.$on("empManageCtrl", function(event, data) {

    });

}]);