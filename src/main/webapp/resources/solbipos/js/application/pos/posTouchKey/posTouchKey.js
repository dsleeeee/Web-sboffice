/****************************************************************
 *
 * 파일명 : posTouchKey.js
 * 설  명 : 판매터치키등록(포스용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.28     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('posTouchKeyCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.init = function () {
    $("#touchKeyView").show();

    var scope = agrid.getScope("touchKeyCtrl");
    scope._broadcast('touchKeyCtrl');
  };
}]);