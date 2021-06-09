/****************************************************************
 *
 * 파일명 : posKitchenPrint.js
 * 설  명 : 판매터치키등록(포스용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.07     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('posKitchenPrintCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.init = function () {
    $("#kitchenPrintView").show();

    var scope = agrid.getScope("storeKitchenPrintProductCtrl");
    scope._broadcast('storeKitchenPrintProductCtrl');
  };
}]);