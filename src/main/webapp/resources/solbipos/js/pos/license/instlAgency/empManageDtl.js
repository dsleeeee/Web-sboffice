/****************************************************************
 *
 * 파일명 : empManageDtl.js
 * 설  명 : 설치업체 사원정보 상세조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019. 10. 24     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 설치업체 사원정보 상세조회
 */
app.controller('empManageDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('empManageDtlCtrl', $scope, $http, false));

    // _broadcast
    $scope.$on("empManageDtlCtrl", function(event, data) {
        event.preventDefault();
    });

}]);