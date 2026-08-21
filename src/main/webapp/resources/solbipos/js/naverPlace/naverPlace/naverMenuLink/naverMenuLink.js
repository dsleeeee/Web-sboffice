/****************************************************************
 *
 * 파일명 : naverMenuLink.js
 * 설  명 : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2026.08.19     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('naverMenuLinkCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    angular.extend(this, new RootController('naverMenuLinkCtrl', $scope, $http, false));

    $scope.$on("naverMenuLinkCtrl", function (event, data) {

    });

}]);
