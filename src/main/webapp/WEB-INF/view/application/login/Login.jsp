<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
  String mobile_url = "";
  System.out.println(request.getRequestURL());
  mobile_url = request.getRequestURL().toString().replace(request.getRequestURI(),"") + "/mobile/auth/login.sb";
//  if     (request.getRequestURL().indexOf("://192") > 0 || request.getRequestURL().indexOf("://localhost") > 0 )  { mobile_url = "http://192.168.0.85:10001/mobile/auth/login.sb"; }
//  else if(request.getRequestURL().indexOf("://neo.solbipos.com") > 0  )                                           { mobile_url = "http://neo.solbipos.com/mobile/auth/login.sb"; }
%>

<style>
  .smsOtpBlock {display:none; margin-top:14px; padding-top:14px; border-top:1px dashed #d8dadd;}
  .smsOtpBlock.show {display:block;}
  .smsOtpLabel {margin-bottom:8px; color:#666; font-size:12px; font-weight:600; line-height:18px;}
  .smsOtpRow {display:flex; align-items:stretch; gap:6px;}
  .smsOtpInput {min-width:0; flex:1; height:44px; padding:0 12px; border:1px solid #e0e1e2; border-radius:4px; background:#fff; font-size:13px; outline:none;}
  .smsOtpInput:focus {border-color:#1e88e5;}
  .smsOtpSendBtn {flex-shrink:0; height:44px; padding:0 12px; border:0; border-radius:4px; background:#3baa5c; color:#fff; font-size:12px; font-weight:600; cursor:pointer;}
  .smsOtpSendBtn:hover {background:#2f8e4b;}
  .smsOtpSendBtn:disabled {background:#a7afb5; cursor:default;}
  .smsOtpTimer {margin-top:7px; color:#999; font-size:11px; line-height:16px;}
  .smsOtpTimer strong {color:#d3413c;}
  .loginArea.smsOtpMode {padding-top:28px;}
  .loginArea.smsOtpMode h2 {margin-bottom:18px;}
  .loginArea.smsOtpMode .loginF {padding-bottom:18px;}
  .loginArea.smsOtpMode .linkArea {padding-top:20px;}
  .loginArea.smsOtpMode .linkArea .find {margin-bottom:24px;}
</style>

<div class="loginArea">
  <h2>Welcome Login</h2>

  <f:form id="loginForm" class="loginF" modelAttribute="sessionInfo" method="post" action="/auth/login.sb" onsubmit="return inputChk();">
    <input type='hidden' id='s_userId'  name='s_userId'  value='<%=request.getParameter("userId")%>'>
    <input type='hidden' id='s_userPwd' name='s_userPwd' value='nxposweb'>
    <input type='hidden' id='accessCd'  name='accessCd'  value='<%=request.getParameter("accessCd")%>'>
    <input type='hidden' id='resrceCd'  name='resrceCd'  value='<%=request.getParameter("resrceCd")%>'>

    <%--     <s:eval expression="@env['login.check.id.save']" var="idField"/> --%>

    <c:if test="${userId == '' || userId ne null}">
      <c:set var="cid" value="${userId}" />
    </c:if>
    <c:if test="${userId eq null}">
      <c:set var="cid" value="${cookie.saveid.value}" />
    </c:if>

    <div class="writeInfo">
      <div>
        <input class="id" type="text" id="userId" name="userId"
               placeholder="<s:message code="login.userId"/>" value="${cid}" maxlength="20" /><label
              for="userId"></label>
        <f:errors path="userId" id="userIdError" class="errorMsg" />
      </div>
      <div>
        <input class="pw" type="password" id="userPwd" name="userPwd"
               placeholder="<s:message code="login.userPasswd"/>" maxlength="100" /><label
              for="userPwd"></label>
        <f:errors path="userPwd" id="userPwdError" class="errorMsg" />
      </div>
    </div>

    <div class="idsave">
      <span> <input type="checkbox" id="chk" name="chk"
        ${empty cid ? '' : 'checked="checked"' } />
        <label for="chk">
          <s:message code="login.rememberId" />
        </label>
      </span>
      <button id="nxBtnSearchn" class="btn_login">
        <s:message code="login.submit" />
      </button>
    </div>

    <div class="smsOtpBlock" id="smsOtpBlock">
      <div class="smsOtpLabel">SMS 사용 등록된 계정입니다. 인증번호를 입력해주세요.</div>
      <div class="smsOtpRow">
        <input class="smsOtpInput" type="text" id="smsOtpNo" name="smsVfcNo" placeholder="인증번호" maxlength="6" inputmode="numeric" autocomplete="one-time-code" />
        <button class="smsOtpSendBtn" type="button" id="smsOtpSendBtn">인증번호 받기</button>
      </div>
      <div class="smsOtpTimer">인증번호를 요청하면 <strong id="smsOtpRemainTime">03:00</strong> 이내로 입력해주세요.</div>
    </div>

  </f:form>

  <div class="linkArea">
    <span class="find">
      <a href="/user/idFind.sb" class="fdId">
        <s:message code="login.find.id" />
      </a>
      <a href="/user/pwdFind.sb" class="fdPw">
        <s:message code="login.find.pw" />
      </a>
    </span>
    <a href="<%=mobile_url%>" class="btn_mobile_login">
      <s:message code="mobile.login.submit" />
    </a>
    <a href="http://www.solbipos.com" target="_blank" class="distributor">
      <s:message code="login.add.dist" />
    </a>
    <a href="http://www.solbipos.com" target="_blank" class="agency">
      <s:message code="login.add.agency" />
    </a>
    <a href="#" id="termsOfUse" style="margin-right: 45px; line-height:50px; font-size:0.875em; text-decoration:underline;">이용약관</a>
  </div>

</div>

<c:import url="/WEB-INF/view/application/layer/alert.jsp">
</c:import>

<c:if test="${type == 'pwChg' || type == 'pwExpire'}">
  <c:import url="/WEB-INF/view/application/layer/pwChgPop.jsp">
    <c:param name="type" value="${type}" />
  </c:import>
</c:if>

<%-- 이용약관 레이어 팝업 가져오기 --%>
<c:import url="/WEB-INF/view/application/layer/termsOfUsePop.jsp">
</c:import>

<script>
  var posAutoLogin = false;           // URL의 accessCd로 진입한 POS 자동로그인인지 구분하는 상태
  var smsUserCheckInProgress = false; // CHK 함수 호출 중 로그인 버튼 중복 처리를 막는 상태
  var smsCodeSendInProgress = false;  // C10 발송 AJAX의 중복 요청을 막는 처리 상태
  var smsLoginInProgress = false;     // SMS 인증이 포함된 로그인 AJAX의 중복 제출을 막는 상태
  var smsCodeSent = false;            // 최초 발송과 재발송의 버튼 문구를 구분하는 발송 여부
  var smsOtpExpired = false;          // 발송 후 3분 만료 시 로그인을 화면에서 차단하는 상태
  var smsOtpExpireTimer = null;       // 인증번호 유효시간 3분을 표시하는 setInterval 식별자
  var smsOtpResendTimer = null;       // 재전송 제한시간 30초를 표시하는 setInterval 식별자

  genEventSingle($("#userId"));
  genEventSingle($("#userPwd"));
  <c:if test="${type == 'pwChg' || type == 'pwExpire'}">
  var id = "${cid}";
  $("#labelUserId").text(id);
  $("#pwdUserId").val(id);
  $("#fullDimmedPw").show();
  $("#layerpw").show();
  </c:if>
  /////////////////////////////////////// window.onload ///////////////////////////////////////
  window.onload = function() { setTimeout("onloadFunctions()",0); }; function onloadFunctions()
  {
    // POS가 URL 파라미터로 전달한 자동로그인용 일회성 accessCd이다.
    var ACCESS_CD = document.getElementById("accessCd").value;
    if(typeof ACCESS_CD != "undefined" && ACCESS_CD != null && ACCESS_CD != "null" && ACCESS_CD != "")
    {
      posAutoLogin = true;
      document.getElementById("userPwd").style.display    = 'none';
      document.getElementById("userId").value             = document.getElementById("s_userId").value;
      document.getElementById("userPwd").value            = document.getElementById("accessCd").value;
      document.getElementById("accessCd").value           = "";
      document.getElementById("resrceCd").value           = document.getElementById("resrceCd").value;
      $('#nxBtnSearchn').click();
    }

    var setInterval_chk = 2;
    setInterval(function()
    {
      document.getElementById('id1').checked = false;
      document.getElementById('id2').checked = false;
      document.getElementById('id3').checked = false;
      if(setInterval_chk == 1) document.getElementById('id1').checked = true;
      if(setInterval_chk == 2) document.getElementById('id2').checked = true;
      if(setInterval_chk == 3) document.getElementById('id3').checked = true;

      if(setInterval_chk >= 3)    setInterval_chk = 1;
      else                        setInterval_chk++;

    }, 3000);
  }

  // input 값 체크
  function inputChk() {
    if(isEmptyObject($("#userId").val())){
      alert(messages["login.id.empty"]);
      return false;
    }

    if(isEmptyObject($("#userPwd").val())){
      alert(messages["login.pwd.empty"]);
      return false;
    } else {
      // SessionInfoVO / userPwd / min = 4, max = 100 정보 확인
      if($("#userPwd").val().length <= 3) {
        alert(messages["login.idpw.fail"]);
        return false;
      }
    }

    // POS 자동로그인은 기존 로그인 흐름을 유지한다.
    if (posAutoLogin) {
      return true;
    }

    if ($("#smsOtpBlock").hasClass("show")) {
      if (smsOtpExpired) {
        alert("인증번호 입력시간이 초과되었습니다. 인증번호를 다시 요청해주세요.");
        return false;
      }
      if (!/^\d{6}$/.test($("#smsOtpNo").val())) {
        alert("인증번호 6자리를 입력해주세요.");
        $("#smsOtpNo").focus();
        return false;
      }
      submitSmsLogin();
      return false;
    }

    checkSmsUserRegistYn();
    return false;
  }

  // 비밀번호 검증 전 USER_ID만으로 SMS 사용 등록 여부를 확인한다.
  function checkSmsUserRegistYn() {
    if (smsUserCheckInProgress) {
      return;
    }

    smsUserCheckInProgress = true;
    // CHK 확인 중 비활성화하여 중복 로그인을 막을 기존 로그인 버튼 객체이다.
    var loginButton = $("#nxBtnSearchn");
    loginButton.prop("disabled", true);

    // 성공·실패 후 처리 상태와 버튼을 복구하기 위한 CHK AJAX 요청 객체이다.
    var smsUserRequest = $.postJSON("/auth/loginSmsUserRegistYn.sb", {userId: $("#userId").val()}, function(result) {
      if (result.status !== "OK" || !result.data) {
        smsUserCheckInProgress = false;
        loginButton.prop("disabled", false);
        alert("SMS 사용자 확인 중 오류가 발생했습니다.");
        return;
      }

      if (result.data.smsUserRegistYn === "Y") {
        showSmsOtpBlock();
        smsUserCheckInProgress = false;
        loginButton.prop("disabled", false);
        return;
      }

      document.getElementById("loginForm").submit();
    }, function() {
      smsUserCheckInProgress = false;
      loginButton.prop("disabled", false);
      alert("SMS 사용자 확인 중 오류가 발생했습니다.");
    });
    smsUserRequest.fail(function() {
      smsUserCheckInProgress = false;
      loginButton.prop("disabled", false);
    });
    smsUserRequest.always(function() {
      smsUserCheckInProgress = false;
      loginButton.prop("disabled", false);
    });
  }

  /** SMS 등록 사용자에게 인증번호 입력 영역을 표시하고 입력란으로 포커스를 이동한다. */
  function showSmsOtpBlock() {
    $(".loginArea").addClass("smsOtpMode");
    $("#smsOtpBlock").addClass("show");
    $("#smsOtpNo").focus();
  }

  // SMS 로그인만 AJAX로 처리하여 실패 시 입력값과 인증 타이머를 유지한다.
  function submitSmsLogin() {
    if (smsLoginInProgress) {
      return;
    }

    smsLoginInProgress = true;
    // 로그인 성공 후 페이지 이동 여부로, 이동하지 않을 때만 로그인 버튼을 복구한다.
    var navigating = false;
    // SMS 로그인 AJAX 처리 중 비활성화할 기존 로그인 버튼 객체이다.
    var loginButton = $("#nxBtnSearchn");
    loginButton.prop("disabled", true);

    $.ajax({
      type: "POST",
      url: "/auth/login.sb",
      data: $("#loginForm").serialize(),
      dataType: "text",
      // 실패는 JSON, 성공은 기존 redirect 화면으로 응답받기 위한 Accept 값이다.
      headers: {"Accept": "text/html, application/json"},
      beforeSend: function() {
        $("#_loadTent, #_loading").show();
      },
      success: function(responseText, textStatus, xhr) {
        // JSON 실패 응답과 HTML 성공 응답을 구분할 서버 Content-Type 값이다.
        var contentType = xhr.getResponseHeader("Content-Type") || "";
        if (contentType.indexOf("application/json") > -1) {
          // 인증 실패 응답은 현재 화면에서 메시지만 표시하고 입력값은 유지한다.
          // JSON 문자열을 변환하여 상태, 메시지, 이동 URL을 확인할 응답 객체이다.
          var result;
          try {
            result = JSON.parse(responseText);
          } catch (e) {
            alert("로그인 처리 중 오류가 발생했습니다.");
            return;
          }

          if (result.status === "OK") {
            navigating = true;
            location.href = result.data && result.data.url ? result.data.url : "/main.sb";
            return;
          }
          if (result.status === "SESSION_EXFIRE" && result.url) {
            navigating = true;
            location.href = result.url;
            return;
          }

          // 서버 메시지가 없을 때 기본 문구를 사용하는 로그인 실패 안내문이다.
          var message = result.message || "로그인 정보가 올바르지 않습니다.";
          alert(message.replace(/\\n/g, "\n"));
          return;
        }

        // 로그인 성공 시 기존 redirect 응답을 받은 후 메인화면으로 이동한다.
        navigating = true;
        location.href = "/main.sb";
      },
      error: function() {
        alert("로그인 처리 중 오류가 발생했습니다.");
      },
      complete: function() {
        $("#_loadTent, #_loading").hide();
        smsLoginInProgress = false;
        if (!navigating) {
          loginButton.prop("disabled", false);
        }
      }
    });
  }

  // 최초·재발송 제한은 DB 함수(C10)에서 최종 처리한다.
  $("#smsOtpSendBtn").on("click", function() {
    // C10 함수로 인증번호를 발송할 로그인 화면의 USER_ID이다.
    var userId = $.trim($("#userId").val());
    if (!userId) {
      alert(messages["login.id.empty"]);
      $("#userId").focus();
      return;
    }
    if (smsCodeSendInProgress) {
      return;
    }

    smsCodeSendInProgress = true;
    $(this).prop("disabled", true);

    // 발송 완료 여부와 관계없이 버튼 처리 상태를 해제하기 위한 C10 AJAX 요청 객체이다.
    var smsSendRequest = $.postJSON("/auth/loginSmsVfcCodeSend.sb", {userId: userId}, function(result) {
      smsCodeSendInProgress = false;
      if (result.status !== "OK" || !result.data) {
        resetSmsSendButton();
        alert("인증번호 전송 중 오류가 발생했습니다.");
        return;
      }

      alert(result.data.message);
      if (result.data.sent) {
        smsCodeSent = true;
        startSmsOtpTimers(180, 30);
        $("#smsOtpNo").focus();
      } else {
        resetSmsSendButton();
      }
    }, function() {
      smsCodeSendInProgress = false;
      resetSmsSendButton();
      alert("인증번호 전송 중 오류가 발생했습니다.");
    });
    smsSendRequest.fail(function() {
      smsCodeSendInProgress = false;
      resetSmsSendButton();
    });
    smsSendRequest.always(function() {
      smsCodeSendInProgress = false;
      if (smsOtpResendTimer === null) {
        resetSmsSendButton();
      }
    });
  });

  /** 인증번호 유효시간과 재전송 제한시간을 서버가 전달한 남은 초부터 시작한다. */
  function startSmsOtpTimers(expireSeconds, resendSeconds) {
    clearSmsOtpTimers();

    expireSeconds = Math.max(0, parseInt(expireSeconds, 10) || 0);
    resendSeconds = Math.max(0, parseInt(resendSeconds, 10) || 0);
    smsCodeSent = true;
    smsOtpExpired = expireSeconds <= 0;
    updateSmsOtpRemainTime(expireSeconds);
    if (resendSeconds > 0) {
      updateSmsResendButton(resendSeconds);
    } else {
      resetSmsSendButton();
    }

    if (expireSeconds > 0) {
      smsOtpExpireTimer = setInterval(function() {
        expireSeconds--;
        updateSmsOtpRemainTime(expireSeconds);
        if (expireSeconds <= 0) {
          smsOtpExpired = true;
          clearInterval(smsOtpExpireTimer);
          smsOtpExpireTimer = null;
        }
      }, 1000);
    }

    if (resendSeconds > 0) {
      smsOtpResendTimer = setInterval(function() {
        resendSeconds--;
        if (resendSeconds <= 0) {
          clearInterval(smsOtpResendTimer);
          smsOtpResendTimer = null;
          resetSmsSendButton();
          return;
        }
        updateSmsResendButton(resendSeconds);
      }, 1000);
    }
  }

  /** 남은 인증 유효시간을 MM:SS 형식으로 화면에 표시한다. */
  function updateSmsOtpRemainTime(seconds) {
    // 전체 남은 시간을 화면 표시에 사용할 분과 초로 분리한다.
    var minutes = Math.floor(seconds / 60);
    var remainSeconds = seconds % 60;
    $("#smsOtpRemainTime").text((minutes < 10 ? "0" : "") + minutes + ":" + (remainSeconds < 10 ? "0" : "") + remainSeconds);
  }

  /** 재전송 제한 중 버튼을 비활성화하고 남은 초를 표시한다. */
  function updateSmsResendButton(seconds) {
    $("#smsOtpSendBtn").prop("disabled", true).text("재전송 (" + seconds + "초)");
  }

  /** 재전송 제한 종료 후 발송 버튼의 문구와 활성 상태를 복구한다. */
  function resetSmsSendButton() {
    // 발송 이력이 있으면 재발송 문구를, 없으면 최초 발송 문구를 사용한다.
    var buttonText = smsCodeSent ? "인증번호 다시 받기" : "인증번호 받기";
    $("#smsOtpSendBtn").prop("disabled", false).text(buttonText);
  }

  /** USER_ID 변경 또는 새 발송 시 기존 유효시간·재전송 타이머를 모두 해제한다. */
  function clearSmsOtpTimers() {
    if (smsOtpExpireTimer !== null) {
      clearInterval(smsOtpExpireTimer);
      smsOtpExpireTimer = null;
    }
    if (smsOtpResendTimer !== null) {
      clearInterval(smsOtpResendTimer);
      smsOtpResendTimer = null;
    }
  }

  $("#userId").on("input", function() {
    clearSmsOtpTimers();
    $(".loginArea").removeClass("smsOtpMode");
    $("#smsOtpBlock").removeClass("show");
    $("#smsOtpNo").val("");
    $("#smsOtpRemainTime").text("03:00");
    smsCodeSent = false;
    smsOtpExpired = false;
    resetSmsSendButton();
  });

  $("#smsOtpNo").on("input", function() {
    this.value = this.value.replace(/\D/g, "");
  });

  <c:if test="${smsAuth == 'Y'}">
  // 로그인 실패 후 돌아온 경우에도 SMS 인증 입력 영역을 유지한다.
  showSmsOtpBlock();
    <c:if test="${smsVfcRequestedYn == 'Y'}">
    // 서버 세션의 발송시각을 기준으로 3분/30초 타이머의 남은 시간을 복원한다.
    startSmsOtpTimers(${smsVfcExpireSeconds}, ${smsVfcResendSeconds});
    </c:if>
  </c:if>

  // 이용약관
  $("#termsOfUse").bind("click", function () {
    $("#fullDimmedTermsOfUsePop").show();
    $("#layerTermsOfUsePop").show();
    return false;
  });
</script>

















