/****************************************************************
 *
 * 파일명 : sideMenu.js
 * 설  명 : 사이드메뉴 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사이드메뉴 그리드 생성
 */
app.controller('sideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuCtrl', $scope, $http, false));


}]);
