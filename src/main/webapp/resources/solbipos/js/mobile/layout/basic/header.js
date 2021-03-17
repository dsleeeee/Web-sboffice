/****************************************************************
 *
 * 파일명 : header.js
 * 설  명 : header 용 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.03     노현수      1.0
 *
 * **************************************************************/
$("#pwchg").bind("click", function () {
  $("#fullDimmedPw").show();
  $("#layerpw").show();
  return false;
});

$(".userId").click(function () {
  $(".userLayer").toggle();
  return false;
});

$(".favClose").click(function () {
  callPostJson("/mobile/menu/delFixMenu.sb", $(this).data("value"));
  return false;
});

$(".histClose").click(function () {
  callPostJson("/mobile/menu/delHistMenu.sb", $(this).data("value"));
  return false;
});

function callPostJson(url, menuId) {
  var param = {};
  param.menuId = menuId;
  $.postJSON(url, param, function (result) {
      $("#" + menuId).remove();
    },
    function (result) {
      s_alert.pop(result.message);
    },
    function (result) {
      s_alert.pop(result.message);
      return false;
    });
}

// 고정메뉴영역 오른쪽 스크롤
$(".moveBtn .mR").click(function () {
  var basePos = $(".moveBtn").position().left;
  var width = 0;
  $("#_fixMenu li").filter(":visible").each(function (index) {
    width += $(this).width();
  });
  if (width > basePos) {
    $("#_fixMenu li:visible").first().hide();
  }
  return false;
});
// 고정메뉴영역 왼쪽 스크롤
$(".moveBtn .mL").click(function () {
  $("#_fixMenu li:hidden").last().show();
  return false;
});

//  메뉴 열고 닫기
$(".menuControl").click(function () {
  if ($("#_nav").attr("class") === "menuOpen") {
    $("#_faMenu").hide();
    $("#theTreeAll").hide();
    $("#smallMenu").show();
    $("#_favorite").removeClass("on");
    $("#_nav").removeClass("menuOpen").addClass("menuClose");
    $("#_arrow").removeClass("arrowOpen").addClass("arrowClose");
  } else {
    $(".menuTab .all").trigger("click");
    $("#theTreeAll").show();
    $("#smallMenu").hide();
    $("#_nav").removeClass("menuClose").addClass("menuOpen");
    $("#_arrow").removeClass("arrowClose").addClass("arrowOpen");
  }
  return false;
});
