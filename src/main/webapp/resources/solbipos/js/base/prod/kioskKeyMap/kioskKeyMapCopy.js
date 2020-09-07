/****************************************************************
 *
 * 파일명 : kioskKeyMapCopy.js
 * 설  명 : 키오스크키맵복사 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.09.03     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('kioskKeyMapCopyCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('kioskKeyMapCopyCtrl', $scope, $http, true));

}]);