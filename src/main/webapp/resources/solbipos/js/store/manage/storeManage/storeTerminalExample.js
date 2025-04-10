/****************************************************************
 *
 * 파일명 : storeTerminalExample.js
 * 설  명 : [매장정보]탭 터미널정보 입력 안내 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.04.09     이다솜      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

app.controller('storeTerminalExampleCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('storeTerminalExampleCtrl', $scope, $http, false));

    // [매장정보]탭 터미널정보 입력 안내 팝업 오픈
    $scope.$on("storeTerminalExampleCtrl", function (event, data) {

    });

}]);