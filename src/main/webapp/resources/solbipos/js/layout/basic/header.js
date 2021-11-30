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
  callPostJson("/menu/delFixMenu.sb", $(this).data("value"));
  return false;
});

$(".histClose").click(function () {
  callPostJson("/menu/delHistMenu.sb", $(this).data("value"));
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



/** SMS전송(당일) */
// 매장인 경우만 실행
if(orgnFg == "STORE") {
    // [125 SMS전송현황표시]에 등록된 본사 하위 매장인지 조회
    var params = {};
    params.orgnCd = orgnCd;
    $.ajax({
        url: "/adi/sms/sendStatus/cmmMainTopSmsSend/getCmmMainTopStoreCount.sb",
        type: "POST",
        data: params,
        dataType: "json",
        cache: false,
        async: false,
        success: function(result) {
            // alert(result.status);
            // alert(result.data);
            // alert(result.data.result.storeCnt);
            if (result.status === "OK") {
                // 등록된 본사의 하의 매장이면
                if(result.data.result.storeCnt > 0) {
                    // 초마다 시간체크 후 변경
                    smsTodayCntChangeChk();
                }
            }
        }
    },function() {
    });
}

// 초마다 시간체크 후 변경
var tid;
function smsTodayCntChangeChk(){
  var time = 0;
  // 1초마다 확인
  tid = setInterval(function () {
    time = time + 1;
    smsTodayCntChange(time);
  }, 1000);
}

var openTime = 0;
var closeTime = 0;
var hiddenYn = false;
function smsTodayCntChange(time){
  // 5초마다 표시
  if(time%5 == 0) {
    openTime = time;
    closeTime = openTime + 3;

      // 오늘 SMS전송 건수 조회
      var params = {};
      params.orgnCd = orgnCd;
      $.ajax({
          url: "/adi/sms/sendStatus/cmmMainTopSmsSend/getCmmMainTopSmsSendCount.sb",
          type: "POST",
          data: params,
          dataType: "json",
          cache: false,
          async: false,
          success: function(result) {
              // alert(result.status);
              // alert(result.data);
              // alert(result.data.result.totSendQty);
              if (result.status === "OK") {
                  // 대기,전송,실패 수량이 있는 경우만
                  if(result.data.result.totQty > 0) {
                      hiddenYn = true;
                      $(".smsTodayLayer").toggle();
                      $("#lblSmsWaitCnt").text(result.data.result.totWaitQty.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                      $("#lblSmsSendCnt").text(result.data.result.totSendQty.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                      $("#lblSmsFailCnt").text(result.data.result.totFailQty.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
                  }
              }
          }
      },function() {
      });
  }

  if(hiddenYn == true) {
      // 표시 후 3초 뒤 숨김
      if(time == closeTime) {
          hiddenYn = false;
          $(".smsTodayLayer").toggle();
      }
  }
}
/** //SMS전송(당일) */