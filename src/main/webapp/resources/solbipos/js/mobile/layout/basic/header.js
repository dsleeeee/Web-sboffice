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

/** 서버 재시작 공지 폴링 */
function checkServerNotice() {
  var now = Date.now();
  // sessionStorage에 저장된 다음 허용 시각보다 이전이면 요청 생략 (탭 간 중복 호출 방지)
  var nextAllowedAt = Number(sessionStorage.getItem("noticeNextAllowedAt") || 0);
  if (now < nextAllowedAt) return;

  $.ajax({
    url: "/common/getDeployNoticeStatus.sb",
    type: "POST",
    dataType: "json",
    cache: false,
    success: function(result) {
      // 공지 유무와 무관하게 다음 허용 시각을 60초 후로 설정
      sessionStorage.setItem("noticeNextAllowedAt", String(now + 60000));
      // 공지 데이터가 있는 경우에만 배너 표시
      if (result.status === "OK" && result.data.noticeInfo != null) {
        // NMCODE_ITEM_2 값을 배너 텍스트로 표시 후 5초 뒤 숨김
        $("#serverNoticeBanner").text(result.data.noticeInfo.nmcodeItem2);
        $("#serverNoticeBanner").fadeIn(300);
        setTimeout(function() {
          $("#serverNoticeBanner").fadeOut(300);
        }, 5000);
      }
    }
  });
}
if (noticePollingEnabled === "1") {
  // 페이지 로드 시 즉시 1회 실행
  checkServerNotice();
  var _noticeNow = Date.now();
  var _noticeNextAllowedAt = Number(sessionStorage.getItem("noticeNextAllowedAt") || 0);
  // 이미 대기 중인 시각이 있으면 그 시각까지 남은 시간만큼 대기, 없으면 60초 후 시작
  var _noticeFirstDelay = _noticeNextAllowedAt > _noticeNow ? _noticeNextAllowedAt - _noticeNow : 60000;
  setTimeout(function() {
    checkServerNotice();
    // 이후 60초 간격으로 반복
    setInterval(checkServerNotice, 60000);
  }, _noticeFirstDelay);
}
/** //서버 재시작 공지 폴링 */

// 이용약관
$("#termsOfUse").bind("click", function () {
  $("#fullDimmedMobileTermsOfUsePop").show();
  $("#layerMobileTermsOfUsePop").show();

  $(".userLayer").toggle();
  return false;
});


// 최근접속이력
$("#lastLoginHist").bind("click", function () {
  $("#fullDimmedMobileLastLoginHistPop").show();
  $("#layerMobileLastLoginHistPop").show();

  // 조회
  var scope = agrid.getScope("mobileLastLoginHistCtrl");
  scope.searchMobileLastLoginHist();

  $(".userLayer").toggle();
  return false;
});
