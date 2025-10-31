/****************************************************************
 *
 * 파일명 : orderkit.js
 * 설  명 : 오더킷 > 오더킷 > 오더킷 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.10.30     이다솜      1.0
 *
 * **************************************************************/

/**
 * get application
 */
var app = agrid.getApp();

/** 오더킷 controller */
app.controller('orderkitCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('orderkitCtrl', $scope, $http, false));

}]);