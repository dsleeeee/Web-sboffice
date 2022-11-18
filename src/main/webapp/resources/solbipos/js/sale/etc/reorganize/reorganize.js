/****************************************************************
 *
 * 파일명 : storeSideMenu.js
 * 설  명 : 매출현황구성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.12.20
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeSideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
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

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("sideMenuCtrl");
  };

  // 상품정보관리 탭 보이기
  $scope.prodShow = function () {
    $("#sideMenuTab").removeClass("on");
    $("#prodTab").addClass("on");
    $("#touchKeyTab").removeClass("on");

    $("#sideMenuView").hide();
    $("#prodView").show();
    $("#touchKeyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("prodCtrl");
    scope.flex.refresh();
  };

  // 판매터치키등록 탭 보이기
  $scope.touchKeyShow = function () {
    $("#sideMenuTab").removeClass("on");
    $("#prodTab").removeClass("on");
    $("#touchKeyTab").addClass("on");

    $("#sideMenuView").hide();
    $("#prodView").hide();
    $("#touchKeyView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("touchKeyCtrl");
    scope._broadcast('touchKeyCtrl');
  };

}]);