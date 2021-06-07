/****************************************************************
 *
 * 파일명 : posSideMenu.js
 * 설  명 : 매장구성세트상품(포스용) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.27     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('posSideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.init = function () {
     $("#sideMenuView").show();
     $("#prodView").hide();
     $("#touchKeyView").hide();
  };

  // 사이드메뉴관리 탭 보이기
  $scope.sideMenuShow = function () {
    $("#sideMenuTab").addClass("on");
    $("#prodTab").removeClass("on");
    $("#touchKeyTab").removeClass("on");

    $("#sideMenuView").show();
    $("#prodView").hide();
    $("#touchKeyView").hide();

    var scope = agrid.getScope("sideMenuCtrl");
    event.preventDefault();
  };

  // 상품정보관리 탭 보이기
  $scope.prodShow = function () {
    $("#sideMenuTab").removeClass("on");
    $("#prodTab").addClass("on");
    $("#touchKeyTab").removeClass("on");

    $("#sideMenuView").hide();
    $("#prodView").show();
    $("#touchKeyView").hide();

    var scope = agrid.getScope("prodCtrl");
    scope.flex.refresh();
    event.preventDefault();
  };

  // 판매터치키등록 탭 보이기
  $scope.touchKeyShow = function () {
    $("#sideMenuTab").removeClass("on");
    $("#prodTab").removeClass("on");
    $("#touchKeyTab").addClass("on");

    $("#sideMenuView").hide();
    $("#prodView").hide();
    $("#touchKeyView").show();

    var scope = agrid.getScope("touchKeyCtrl");
    scope._broadcast('touchKeyCtrl');
  };

}]);