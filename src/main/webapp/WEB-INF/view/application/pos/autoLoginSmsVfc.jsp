<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  html, body {height:100%;}
  body {margin:0; background:#f3f5f7; font-family:"Malgun Gothic","Apple SD Gothic Neo",sans-serif;}
  .posSmsWait {min-height:100vh; display:flex; flex-direction:column; color:#333;}
  .posSmsHeader {height:58px; padding:0 24px; display:flex; align-items:center; justify-content:space-between; background:#fff; border-bottom:1px solid #e1e5e9;}
  .posSmsLogo {width:180px; height:auto;}
  .posSmsLogout {border:1px solid #d8dde2; background:#fff; color:#666; height:34px; padding:0 12px; border-radius:4px; cursor:pointer; font-size:12px;}
  .posSmsLogout:hover {background:#f5f6f7;}
  .posSmsBody {flex:1; display:flex; align-items:center; justify-content:center; padding:24px; background:rgba(23,26,33,0.08);}
  .posSmsModal {width:380px; max-width:100%; padding:30px; box-sizing:border-box; background:#fff; border:1px solid #e0e4e8; border-radius:8px; box-shadow:0 18px 45px rgba(0,0,0,0.18);}
  .posSmsIcon {width:48px; height:48px; margin:0 auto 16px; display:flex; align-items:center; justify-content:center; border-radius:50%; background:#eaf1fb; color:#3b82d9; font-size:22px;}
  .posSmsTitle {margin:0; text-align:center; font-size:18px; line-height:26px; font-weight:700; letter-spacing:0;}
  .posSmsDesc {margin:8px 0 22px; text-align:center; color:#777; font-size:12px; line-height:19px; letter-spacing:0;}
  .posSmsInputRow {display:flex; gap:6px;}
  .posSmsInput {min-width:0; flex:1; height:46px; padding:0 12px; box-sizing:border-box; border:1px solid #d9dfe5; border-radius:4px; background:#f8fafc; color:#333; font-size:15px; font-weight:700; text-align:center; outline:none; letter-spacing:0;}
  .posSmsInput:focus {border-color:#3b82d9; background:#fff;}
  .posSmsInput.error {border-color:#d3413c; background:#fcebea;}
  .posSmsSendBtn {flex-shrink:0; height:46px; padding:0 13px; border:0; border-radius:4px; background:#3baa5c; color:#fff; font-size:12px; font-weight:700; cursor:pointer; white-space:nowrap;}
  .posSmsSendBtn:hover {background:#2f8e4b;}
  .posSmsSendBtn:disabled {background:#9ca5ad; cursor:default;}
  .posSmsMeta {margin:8px 0 17px; color:#999; font-size:11px; line-height:17px;}
  .posSmsMeta strong {color:#d3413c;}
  .posSmsMessage {display:none; margin-bottom:14px; padding:9px 10px; border-radius:4px; background:#edf6ef; color:#2f7c44; font-size:12px; line-height:18px; white-space:pre-line;}
  .posSmsMessage.show {display:block;}
  .posSmsMessage.error {background:#fcebea; color:#c43631;}
  .posSmsConfirmBtn {width:100%; height:46px; border:0; border-radius:4px; background:#3b82d9; color:#fff; font-size:15px; font-weight:700; cursor:pointer;}
  .posSmsConfirmBtn:hover {background:#2e6fc0;}
  .posSmsConfirmBtn:disabled {background:#9ca5ad; cursor:default;}
  .posSmsNote {margin-top:12px; color:#999; text-align:center; font-size:11px; line-height:17px;}
  @media (max-width:480px) {
    .posSmsHeader {padding:0 16px;}
    .posSmsLogo {width:150px;}
    .posSmsBody {padding:16px;}
    .posSmsModal {padding:26px 20px;}
    .posSmsInputRow {flex-direction:column;}
    .posSmsSendBtn {width:100%;}
  }
</style>

<div class="posSmsWait">
  <header class="posSmsHeader">
    <img class="posSmsLogo" src="/resource/solbipos/css/img/링크_BI_black.png?v=20210624" alt="링크" />
    <button type="button" class="posSmsLogout" id="posSmsLogoutBtn" title="로그아웃">
      <i class="fa fa-sign-out" aria-hidden="true"></i> 로그아웃
    </button>
  </header>

  <main class="posSmsBody">
    <section class="posSmsModal" aria-labelledby="posSmsTitle">
      <div class="posSmsIcon"><i class="fa fa-shield" aria-hidden="true"></i></div>
      <h1 class="posSmsTitle" id="posSmsTitle">인증번호 확인</h1>
      <p class="posSmsDesc">SMS 사용 등록된 계정입니다.<br />등록된 휴대폰으로 전송된 인증번호를 입력해주세요.</p>

      <div class="posSmsInputRow">
        <input type="text" class="posSmsInput" id="posSmsVfcNo" placeholder="인증번호" maxlength="6" inputmode="numeric" autocomplete="one-time-code" />
        <button type="button" class="posSmsSendBtn" id="posSmsSendBtn">인증번호 받기</button>
      </div>
      <div class="posSmsMeta">인증번호를 요청하면 <strong id="posSmsRemainTime">03:00</strong> 이내로 입력해주세요.</div>

      <div class="posSmsMessage" id="posSmsMessage" role="alert"></div>
      <button type="button" class="posSmsConfirmBtn" id="posSmsConfirmBtn">확인</button>
      <div class="posSmsNote">인증번호를 5회 이상 잘못 입력하면 자동으로 로그아웃됩니다.</div>
    </section>
  </main>
</div>

<script>
  var posSmsSendInProgress = false; // C10 발송 AJAX의 중복 요청을 막는 처리 상태
  var posSmsVerifyInProgress = false; // C11 검증 AJAX의 중복 요청을 막는 처리 상태
  var posSmsCodeSent = false; // 최초 발송과 재발송의 버튼 문구를 구분하는 발송 여부
  var posSmsExpired = false; // 발송 후 3분 만료 시 C11 요청을 화면에서 차단하는 상태
  var posSmsExpireTimer = null; // 인증번호 유효시간 3분을 표시하는 setInterval 식별자
  var posSmsResendTimer = null; // 재전송 제한시간 30초를 표시하는 setInterval 식별자

  $("#posSmsSendBtn").on("click", function() {
    if (posSmsSendInProgress) {
      return;
    }

    posSmsSendInProgress = true;
    $(this).prop("disabled", true);
    clearPosSmsMessage();

    // 발송 완료 여부와 관계없이 버튼 처리 상태를 해제하기 위한 C10 AJAX 요청 객체이다.
    var sendRequest = $.postJSON("/auth/posSmsVfcCodeSend.sb", {}, function(result) {
      if (!result.data) {
        showPosSmsMessage("인증번호 전송 중 오류가 발생했습니다.", true);
        return;
      }

      // 서버의 C10 결과 코드, 메시지, 발송 여부와 강제 로그아웃 여부이다.
      var data = result.data;
      if (data.forceLogout) {
        alert(normalizePosSmsMessage(data.message));
        location.href = data.url || "/auth/login.sb";
        return;
      }

      showPosSmsMessage(data.message, !data.sent);
      if (data.sent) {
        posSmsCodeSent = true;
        $("#posSmsVfcNo").val("").removeClass("error").focus();
        startPosSmsTimers(180, 30);
      } else {
        resetPosSmsSendButton();
      }
    }, function() {
      showPosSmsMessage("인증번호 전송 중 오류가 발생했습니다.", true);
      resetPosSmsSendButton();
    });

    sendRequest.always(function() {
      posSmsSendInProgress = false;
      if (posSmsResendTimer === null) {
        resetPosSmsSendButton();
      }
    });
  });

  $("#posSmsConfirmBtn").on("click", function() {
    // C11 함수에 전달할 사용자의 6자리 인증번호이다.
    var smsVfcNo = $.trim($("#posSmsVfcNo").val());
    if (posSmsExpired) {
      showPosSmsMessage("인증번호 입력시간이 초과되었습니다. 인증번호를 다시 요청해주세요.", true);
      return;
    }
    if (!/^\d{6}$/.test(smsVfcNo)) {
      showPosSmsMessage("인증번호 6자리를 입력해주세요.", true);
      $("#posSmsVfcNo").addClass("error").focus();
      return;
    }
    if (posSmsVerifyInProgress) {
      return;
    }

    posSmsVerifyInProgress = true;
    $(this).prop("disabled", true);
    clearPosSmsMessage();

    // 검증 완료 여부와 관계없이 확인 버튼을 복구하기 위한 C11 AJAX 요청 객체이다.
    var verifyRequest = $.postJSON("/auth/posSmsVfcCodeVerify.sb", {smsVfcNo: smsVfcNo}, function(result) {
      if (!result.data) {
        showPosSmsMessage("인증번호 확인 중 오류가 발생했습니다.", true);
        return;
      }

      // 서버의 C11 결과 코드, 인증 성공 여부, 이동 URL과 강제 로그아웃 여부이다.
      var data = result.data;
      if (data.forceLogout) {
        alert(normalizePosSmsMessage(data.message));
        location.href = data.url || "/auth/login.sb";
        return;
      }
      if (data.verified) {
        location.href = data.url || "/main.sb";
        return;
      }

      if (data.code === "04") {
        posSmsExpired = true;
      }
      showPosSmsMessage(data.message, true);
      $("#posSmsVfcNo").addClass("error").focus().select();
    }, function() {
      showPosSmsMessage("인증번호 확인 중 오류가 발생했습니다.", true);
    });

    verifyRequest.always(function() {
      posSmsVerifyInProgress = false;
      $("#posSmsConfirmBtn").prop("disabled", false);
    });
  });

  $("#posSmsVfcNo").on("input", function() {
    this.value = this.value.replace(/\D/g, "");
    $(this).removeClass("error");
  }).on("keydown", function(event) {
    if (event.keyCode === 13) {
      $("#posSmsConfirmBtn").click();
    }
  });

  $("#posSmsLogoutBtn").on("click", function() {
    location.href = "/auth/logout.sb";
  });

  /** 인증번호 유효시간과 재전송 제한시간을 서버가 전달한 남은 초부터 시작한다. */
  function startPosSmsTimers(expireSeconds, resendSeconds) {
    clearPosSmsTimers();

    expireSeconds = Math.max(0, parseInt(expireSeconds, 10) || 0);
    resendSeconds = Math.max(0, parseInt(resendSeconds, 10) || 0);
    posSmsCodeSent = true;
    posSmsExpired = expireSeconds <= 0;
    updatePosSmsRemainTime(expireSeconds);

    if (resendSeconds > 0) {
      updatePosSmsResendButton(resendSeconds);
    } else {
      resetPosSmsSendButton();
    }

    if (expireSeconds > 0) {
      posSmsExpireTimer = setInterval(function() {
        expireSeconds--;
        updatePosSmsRemainTime(expireSeconds);
        if (expireSeconds <= 0) {
          posSmsExpired = true;
          clearInterval(posSmsExpireTimer);
          posSmsExpireTimer = null;
        }
      }, 1000);
    }

    if (resendSeconds > 0) {
      posSmsResendTimer = setInterval(function() {
        resendSeconds--;
        if (resendSeconds <= 0) {
          clearInterval(posSmsResendTimer);
          posSmsResendTimer = null;
          resetPosSmsSendButton();
          return;
        }
        updatePosSmsResendButton(resendSeconds);
      }, 1000);
    }
  }

  /** 남은 인증 유효시간을 MM:SS 형식으로 화면에 표시한다. */
  function updatePosSmsRemainTime(seconds) {
    // 전체 남은 시간을 화면 표시에 사용할 분과 초로 분리한다.
    var minutes = Math.floor(seconds / 60);
    var remainSeconds = seconds % 60;
    $("#posSmsRemainTime").text((minutes < 10 ? "0" : "") + minutes + ":" + (remainSeconds < 10 ? "0" : "") + remainSeconds);
  }

  /** 재전송 제한 중 버튼을 비활성화하고 남은 초를 표시한다. */
  function updatePosSmsResendButton(seconds) {
    $("#posSmsSendBtn").prop("disabled", true).text("재전송 (" + seconds + "초)");
  }

  /** 재전송 제한 종료 후 발송 버튼의 문구와 활성 상태를 복구한다. */
  function resetPosSmsSendButton() {
    // 발송 이력이 있으면 재발송 문구를, 없으면 최초 발송 문구를 사용한다.
    var buttonText = posSmsCodeSent ? "인증번호 다시 받기" : "인증번호 받기";
    $("#posSmsSendBtn").prop("disabled", false).text(buttonText);
  }

  /** 새 발송 또는 화면 종료 시 기존 유효시간·재전송 타이머를 모두 해제한다. */
  function clearPosSmsTimers() {
    if (posSmsExpireTimer !== null) {
      clearInterval(posSmsExpireTimer);
      posSmsExpireTimer = null;
    }
    if (posSmsResendTimer !== null) {
      clearInterval(posSmsResendTimer);
      posSmsResendTimer = null;
    }
  }

  /** 서버 메시지를 성공 또는 오류 스타일로 인증 화면에 표시한다. */
  function showPosSmsMessage(message, isError) {
    $("#posSmsMessage")
      .toggleClass("error", isError === true)
      .addClass("show")
      .text(normalizePosSmsMessage(message));
  }

  /** 이전 발송·검증 결과 메시지와 오류 스타일을 제거한다. */
  function clearPosSmsMessage() {
    $("#posSmsMessage").removeClass("show error").text("");
  }

  /** DB 함수 메시지의 문자열 개행(\\n)을 실제 화면 개행으로 변환한다. */
  function normalizePosSmsMessage(message) {
    return (message || "").replace(/\\n/g, "\n");
  }

  <c:if test="${smsVfcRequestedYn == 'Y'}">
  // 새로고침 시 서버 세션에 저장된 발송시각을 기준으로 남은 시간을 복원한다.
  startPosSmsTimers(${smsVfcExpireSeconds}, ${smsVfcResendSeconds});
  </c:if>
</script>
