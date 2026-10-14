<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  html, body {height:100%;}
  body {margin:0; background:#f3f5f7; font-family:"Malgun Gothic","Apple SD Gothic Neo",sans-serif;}
  .pvWait {min-height:100vh; display:flex; flex-direction:column; color:#333;}

  .pvBody {flex:1; display:flex; align-items:center; justify-content:center; padding:24px; background:rgba(23,26,33,0.08);}
  .pvModal {width:380px; max-width:100%; padding:30px; box-sizing:border-box; background:#fff; border:1px solid #e0e4e8; border-radius:8px; box-shadow:0 18px 45px rgba(0,0,0,0.18);}
  .pvIcon {width:48px; height:48px; margin:0 auto 16px; display:flex; align-items:center; justify-content:center; border-radius:50%; background:#fdecea; color:#d3413c; font-size:22px;}
  .pvTitle {margin:0; text-align:center; font-size:18px; line-height:26px; font-weight:700;}
  .pvDesc {margin:8px 0 14px; text-align:center; color:#777; font-size:12px; line-height:19px;}
  .pvUser {margin-bottom:12px; text-align:center; color:#555; font-size:12px;}
  .pvUser b {color:#1a5276;}
  .pvCodeRow {display:flex; gap:6px; margin-bottom:8px;}
  .pvCodeImg {flex:1; height:64px; border:1px solid #d9dfe5; border-radius:4px; background:#f4f7fb; display:flex; align-items:center; justify-content:center; overflow:hidden;}
  .pvCodeImg img {width:200px; height:64px; user-select:none; -webkit-user-drag:none;}
  .pvRenewBtn {flex-shrink:0; width:66px; border:1px solid #d9dfe5; border-radius:4px; background:#fff; color:#555; font-size:12px; cursor:pointer; line-height:18px;}
  .pvRenewBtn:hover {background:#f5f6f7;}
  .pvRenewBtn:disabled {color:#aaa; cursor:default;}
  .pvInput {width:100%; height:46px; padding:0 12px; box-sizing:border-box; border:1px solid #d9dfe5; border-radius:4px; background:#f8fafc; color:#333; font-size:20px; font-weight:700; text-align:center; letter-spacing:8px; outline:none;}
  .pvInput:focus {border-color:#3b82d9; background:#fff;}
  .pvInput.error {border-color:#d3413c; background:#fcebea;}
  .pvMeta {margin:8px 0 17px; color:#999; font-size:11px; line-height:17px;}
  .pvMeta strong {color:#d3413c;}
  .pvMessage {display:none; margin-bottom:14px; padding:9px 10px; border-radius:4px; background:#edf6ef; color:#2f7c44; font-size:12px; line-height:18px; white-space:pre-line;}
  .pvMessage.show {display:block;}
  .pvMessage.error {background:#fcebea; color:#c43631;}
  .pvConfirmBtn {width:100%; height:46px; border:0; border-radius:4px; background:#3b82d9; color:#fff; font-size:15px; font-weight:700; cursor:pointer;}
  .pvConfirmBtn:hover {background:#2e6fc0;}
  .pvConfirmBtn:disabled {background:#9ca5ad; cursor:default;}
  .pvNote {margin-top:12px; color:#999; text-align:center; font-size:11px; line-height:17px;}
  .pvBackBtn {width:100%; height:40px; margin-top:14px; border:1px solid #d8dde2; border-radius:4px; background:#fff; color:#555; font-size:13px; cursor:pointer;}
  .pvBackBtn:hover {background:#f5f6f7;}
  @media (max-width:480px) {

    .pvBody {padding:16px;}
    .pvModal {padding:26px 20px;}
  }
</style>

<div class="pvWait">
  <main class="pvBody">
    <section class="pvModal" aria-labelledby="pvTitle">
      <div class="pvIcon"><i class="fa fa-lock" aria-hidden="true"></i></div>
      <h1 class="pvTitle" id="pvTitle">로그인 일시 제한</h1>
      <p class="pvDesc">평소와 다른 이용이 감지되어 계정 보호를 위해<br />로그인이 일시 제한되었습니다.<br />아래 숫자를 입력하면 바로 해제됩니다.</p>
      <div class="pvUser">아이디 <b><c:out value="${pauseVfcUserId}" /></b></div>

      <div class="pvCodeRow">
        <div class="pvCodeImg"><img id="pvCodeImg" src="" alt="확인 숫자" /></div>
        <button type="button" class="pvRenewBtn" id="pvRenewBtn"><i class="fa fa-refresh" aria-hidden="true"></i><br />새 숫자</button>
      </div>
      <input type="text" class="pvInput" id="pvNo" placeholder="숫자 4자리" maxlength="4" inputmode="numeric" autocomplete="off" />
      <div class="pvMeta"><strong id="pvRemainTime">02:00</strong> 이내로 입력해주세요.</div>

      <div class="pvMessage" id="pvMessage" role="alert"></div>
      <button type="button" class="pvConfirmBtn" id="pvConfirmBtn">확인</button>
      <div class="pvNote">해제 후 다시 로그인해 주세요.<br />2분 안에 입력하지 않거나 5회 잘못 입력하면 로그인 화면으로 이동합니다.<br />입력하지 않으면 30분 후 자동으로 해제됩니다.</div>
      <button type="button" class="pvBackBtn" id="pvBackBtn">로그인 화면으로 이동</button>
    </section>
  </main>
</div>

<script>
  var pvVerifyInProgress = false; // 확인 AJAX 중복 요청 방지
  var pvRenewInProgress  = false; // 새 숫자 AJAX 중복 요청 방지
  var pvExpired = false;          // 입력시간(2분) 만료 여부
  var pvExpireTimer = null;       // 남은 시간 표시 타이머

  /** 서버 메시지의 \n 문자열을 실제 줄바꿈으로 변환 */
  function pvNormalize(message) {
    return (message || "").replace(/\\n/g, "\n");
  }

  /** 숫자 이미지 다시 불러오기 (캐시 방지) */
  function pvReloadImg() {
    $("#pvCodeImg").attr("src", "/auth/pauseVfcImg.sb?t=" + new Date().getTime());
  }

  /** 남은 시간 타이머 — 0이 되면 로그인 화면으로 */
  function pvStartTimer(seconds) {
    if (pvExpireTimer !== null) { clearInterval(pvExpireTimer); pvExpireTimer = null; }
    seconds = Math.max(0, parseInt(seconds, 10) || 0);
    pvExpired = seconds <= 0;
    pvUpdateRemain(seconds);
    if (seconds > 0) {
      pvExpireTimer = setInterval(function() {
        seconds--;
        pvUpdateRemain(seconds);
        if (seconds <= 0) {
          pvExpired = true;
          clearInterval(pvExpireTimer);
          pvExpireTimer = null;
          pvGoLogin({}, "입력시간(2분)이 지났습니다.\n다시 로그인해 주세요.");
        }
      }, 1000);
    }
  }

  function pvUpdateRemain(seconds) {
    var m = Math.floor(seconds / 60), s = seconds % 60;
    $("#pvRemainTime").text((m < 10 ? "0" : "") + m + ":" + (s < 10 ? "0" : "") + s);
  }

  function pvShowMessage(message, isError) {
    $("#pvMessage").toggleClass("error", isError === true).addClass("show").text(pvNormalize(message));
  }

  function pvClearMessage() {
    $("#pvMessage").removeClass("show error").text("");
  }

  /** 안내 후 로그인 화면으로 이동 (성공·실패 공통) */
  function pvGoLogin(data, defaultMessage) {
    alert(pvNormalize(data.message || defaultMessage));
    location.href = data.url || "/auth/login.sb";
  }

  // 새 숫자
  $("#pvRenewBtn").on("click", function() {
    if (pvRenewInProgress) return;
    pvRenewInProgress = true;
    $(this).prop("disabled", true);
    pvClearMessage();

    var req = $.postJSON("/auth/pauseVfcRenew.sb", {}, function(result) {
      var data = result.data || {};
      if (data.forceLogout) { pvGoLogin(data, "다시 로그인해 주세요."); return; }
      pvReloadImg();
      pvStartTimer(data.expireSeconds);
      $("#pvNo").val("").removeClass("error").focus();
      pvShowMessage(data.message, false);
    }, function() {
      pvShowMessage("새 숫자 요청 중 오류가 발생했습니다.", true);
    });

    req.always(function() {
      pvRenewInProgress = false;
      $("#pvRenewBtn").prop("disabled", false);
    });
  });

  // 확인
  $("#pvConfirmBtn").on("click", function() {
    var no = $.trim($("#pvNo").val());
    if (pvExpired) {
      pvGoLogin({}, "입력시간(2분)이 지났습니다.\n다시 로그인해 주세요.");
      return;
    }
    if (!/^\d{4}$/.test(no)) {
      pvShowMessage("숫자 4자리를 입력해주세요.", true);
      $("#pvNo").addClass("error").focus();
      return;
    }
    if (pvVerifyInProgress) return;

    pvVerifyInProgress = true;
    $(this).prop("disabled", true);
    pvClearMessage();

    var req = $.postJSON("/auth/pauseVfcVerify.sb", {pauseVfcNo: no}, function(result) {
      var data = result.data || {};
      // 해제 성공 → "일시정지가 해제되었습니다. 다시 로그인해 주세요." 후 로그인 화면
      if (data.verified)    { pvGoLogin(data, "일시정지가 해제되었습니다.\n다시 로그인해 주세요."); return; }
      if (data.forceLogout) { pvGoLogin(data, "다시 로그인해 주세요."); return; }

      if (data.renew) {
        pvReloadImg();
        pvStartTimer(data.expireSeconds);
      }
      pvShowMessage(data.message, true);
      $("#pvNo").val("").addClass("error").focus();
    }, function() {
      pvShowMessage("확인 처리 중 오류가 발생했습니다.", true);
    });

    req.always(function() {
      pvVerifyInProgress = false;
      $("#pvConfirmBtn").prop("disabled", false);
    });
  });

  $("#pvNo").on("input", function() {
    this.value = this.value.replace(/\D/g, "");
    $(this).removeClass("error");
  }).on("keydown", function(event) {
    if (event.keyCode === 13) $("#pvConfirmBtn").click();
  });

  $("#pvBackBtn").on("click", function() {
    location.href = "/auth/login.sb";
  });

  // 최초 표시
  pvReloadImg();
  pvStartTimer(${pauseVfcExpireSeconds});
  $("#pvNo").focus();
</script>