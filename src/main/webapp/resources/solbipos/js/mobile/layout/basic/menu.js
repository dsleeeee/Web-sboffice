/****************************************************************
 *
 * 파일명 : menu.js
 * 설  명 : 메뉴생성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 메뉴 treeview 생성
 */
app.controller('menuCtrl', ['$scope', '$http', 'initMenu', 'pNode', function ($scope, $http, initMenu, pNode) {
  // 상위 객체 상속
  angular.extend(this, new MenuController('menuCtrl', "/mobile/menu/menuList.sb", $scope, $http));
}]);

/**
 * 즐겨찾기 메뉴 treeview 생성
 */
app.controller('bkmkCtrl', ['$scope', '$http', 'initMenu', 'pNode', function ($scope, $http, initMenu, pNode) {
  // 상위 객체 상속
  angular.extend(this, new MenuController('bkmkCtrl', "/mobile/menu/bkmkList.sb", $scope, $http));
}]);

$(document).ready(function () {

  // 단축 메뉴 생성
  $.ajax({
    type: "POST",
    cache: false,
    async: true,
    dataType: "json",
    url: "/mobile/menu/menuList.sb",
    data: {},
    success: function(result) {
      var menus = JSON.parse(JSON.stringify(result.data));
      $(menus).each(function (index) {
        if ( menus[index].pResrce === "000000") {
          $("#_smallMenuUl").append(wijmo.format('<li class="{iconNm}"><a href="#"></a></li>', this));
        }
      });
    }
  });

  // 전체메뉴 클릭
  $(".menuTab .all").click(function () {
    $("#_all").addClass("on");
    $("#_favorite").removeClass("on");
    $("#_bkmkTxt, #_faMenu").hide();
    $("#_theTreeAll").show();
    $("#_theTreeBkmk").hide();
  });

  // 즐겨찾기 메뉴 클릭
  $(".menuTab .favorite").click(function () {
    $("#_all").removeClass();
    $("#_favorite").addClass("on");
    $("#_faMenu").show();
    $("#_theTreeAll").hide();

    var scope = agrid.getScope("bkmkCtrl");
    scope.$apply(function(){
      if (scope.items.length <= 0) {
        $("#_bkmkTxt").show();
      }
    });
    $("#_theTreeBkmk").show();
  });

  // 접힌 메뉴 클릭 시 열린 메뉴 오픈
  $(document).on("click", "#_smallMenuUl li a", function () {
    $(".menuControl").trigger("click");
    var findClass = $(this).closest("li").attr("class");
    if (findClass != null) {
      var tree = agrid.getScope("menuCtrl");
      tree.$apply(function(){
        for (var node = tree.getFirstNode(); node; node = node.nextSibling()) {
          if (node.dataItem.iconNm === findClass) {
            tree.selectedItem = node.dataItem;
            node.isCollapsed = false;
          }
        }
      });
    }
  });

  // 접힌 메뉴(즐겨찾기) 클릭 시 열린 메뉴 오픈
  $(document).on("click", "#_favorite", function () {
    if ($("#_nav").attr("class") === "menuClose") {
      $(".menuControl").trigger("click");
      $(".menuTab .favorite").trigger("click");
    }
  });

});
