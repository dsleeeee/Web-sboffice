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
  angular.extend(this, new MenuController('menuCtrl', "/menu/menuList.sb", $scope, $http));
}]);

/**
 * 즐겨찾기 메뉴 treeview 생성
 */
app.controller('bkmkCtrl', ['$scope', '$http', 'initMenu', 'pNode', function ($scope, $http, initMenu, pNode) {
  // 상위 객체 상속
  angular.extend(this, new MenuController('bkmkCtrl', "/menu/bkmkList.sb", $scope, $http));
}]);

$(document).ready(function () {

  // 단축 메뉴 생성
  $.ajax({
    type: "POST",
    cache: false,
    async: true,
    dataType: "json",
    url: "/menu/menuList.sb",
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

  // 터치/마우스 드래그로 좌측 메뉴 스크롤 (POS 터치 환경에서 기본 터치 스크롤이 동작하지 않는 경우 대응)
  (function () {
    var menuTree = document.querySelector(".menuTree");
    if (!menuTree) { return; }

    var DRAG_THRESHOLD = 8; // 이 거리(px) 이상 움직여야 스크롤로 판단, 그 미만은 메뉴 클릭으로 처리
    var target = null;      // 스크롤 대상 트리
    var startY = 0;
    var startScroll = 0;
    var dragging = false;
    var suppressClick = false;

    function getTree(el) {
      while (el && el !== menuTree) {
        if (el.className && typeof el.className === "string" && el.className.indexOf("theTreeAll") > -1) { return el; }
        el = el.parentNode;
      }
      return null;
    }

    function onStart(e, y) {
      target = getTree(e.target);
      if (!target) { return; }
      startY = y;
      startScroll = target.scrollTop;
      dragging = false;
      suppressClick = false;
    }

    function onMove(e, y) {
      if (!target) { return; }
      var diff = startY - y;
      if (!dragging && Math.abs(diff) < DRAG_THRESHOLD) { return; }
      dragging = true;
      suppressClick = true;
      target.scrollTop = startScroll + diff;
      if (e.cancelable) { e.preventDefault(); }
    }

    function onEnd() {
      target = null;
      dragging = false;
      // 드래그 직후 발생하는 click 이 메뉴 선택으로 이어지지 않도록 잠시 후 해제
      setTimeout(function () { suppressClick = false; }, 100);
    }

    // 터치 (기본 스크롤과 중복되지 않도록 non-passive 로 등록 후 직접 처리)
    menuTree.addEventListener("touchstart", function (e) {
      if (e.touches.length === 1) { onStart(e, e.touches[0].clientY); }
    }, false);
    menuTree.addEventListener("touchmove", function (e) {
      if (e.touches.length === 1) { onMove(e, e.touches[0].clientY); }
    }, false);
    menuTree.addEventListener("touchend", onEnd, false);
    menuTree.addEventListener("touchcancel", onEnd, false);

    // 터치가 마우스 이벤트로 전달되는 환경
    menuTree.addEventListener("mousedown", function (e) {
      if (e.button === 0) { onStart(e, e.clientY); }
    }, false);
    document.addEventListener("mousemove", function (e) { onMove(e, e.clientY); }, false);
    document.addEventListener("mouseup", function () { if (target) { onEnd(); } }, false);

    // 드래그(스크롤) 후의 click 은 메뉴 선택으로 처리하지 않음
    menuTree.addEventListener("click", function (e) {
      if (suppressClick) {
        e.stopPropagation();
        e.preventDefault();
      }
    }, true);
  })();

});
