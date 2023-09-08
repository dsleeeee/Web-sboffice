/****************************************************************
 *
 * 파일명 : storeSideMenu.js
 * 설  명 : 매장구성세트상품 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.26     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeSideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  $scope.init = function () {
    // $("#sideMenuView").();
    $("#prodView").show();
    $("#touchKeyView").hide();
    $("#setProdView").hide();
  };

  // 사이드메뉴관리 탭 보이기
  $scope.sideMenuShow = function () {
    $("#sideMenuTab").addClass("on");
    $("#prodTab").removeClass("on");
    $("#touchKeyTab").removeClass("on");
    $("#setProdTab").removeClass("on");

    $("#sideMenuView").show();
    $("#prodView").hide();
    $("#touchKeyView").hide();
    $("#setProdView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("sideMenuCtrl");
  };

  // 상품정보관리 탭 보이기
  $scope.prodShow = function () {
    // $("#sideMenuTab").removeClass("on");
    $("#prodTab").addClass("on");
    $("#setProdTab").removeClass("on");
    $("#touchKeyTab").removeClass("on");

    // $("#sideMenuView").hide();
    $("#prodView").show();
    $("#setProdView").hide();
    $("#touchKeyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("prodCtrl");
    scope.flex.refresh();
  };

  // 세트메뉴구성(BBQ전용) 탭 보이기
  $scope.setProdShow = function () {
    // $("#sideMenuTab").removeClass("on");
    $("#prodTab").removeClass("on");
    $("#setProdTab").addClass("on");
    $("#touchKeyTab").removeClass("on");

    // $("#sideMenuView").hide();
    $("#prodView").hide();
    $("#setProdView").show();
    $("#touchKeyView").hide();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("setProdCtrl");
    scope._broadcast('setProdCtrl');
  };

  // 판매터치키등록 탭 보이기
  $scope.touchKeyShow = function () {
    // $("#sideMenuTab").removeClass("on");
    $("#prodTab").removeClass("on");
    $("#setProdTab").removeClass("on");
    $("#touchKeyTab").addClass("on");

    // $("#sideMenuView").hide();
    $("#prodView").hide();
    $("#setProdView").hide();
    $("#touchKeyView").show();

    // angular 그리드 hide 시 깨지므로 refresh()
    var scope = agrid.getScope("touchKeyCtrl");
    scope._broadcast('touchKeyCtrl');
  };

}]);