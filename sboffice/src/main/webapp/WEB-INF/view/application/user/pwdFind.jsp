<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit"><s:message code="label.login.find.pw" /></h2>
  <p class="h2_txt">
      <s:message code="label.pw.find.h2.1" /><br /><s:message code="label.pw.find.h2.2" />
      <span>
      <s:message code="label.pw.find.h2.3" /><br />
      <s:message code="label.pw.find.h2.4" />
      </span>
  </p>
  
  <f:form method="post" action="/user/pwdFind.sb" modelAttribute="user" class="loginF">
    
    <div class="writeInfo">
      
      <div>
        <input type="text" name="userId" id="userId" placeholder="<s:message code="label.login.userId" />" value="" maxlength="20" class="id" /><label for="userId"></label>
        <f:errors path="userId" id="userIdError" class="errorMsg"/>
      </div>
      
      <div>
        <input type="text" name="empNm" id="empNm" placeholder="<s:message code="label.cmm.emp" />&nbsp;<s:message code="label.cmm.name" />" value="" maxlength="10" class="name" /><label for="empNm"></label>
        <f:errors path="empNm" id="empNmError" class="errorMsg"/>
      </div>
      
      <div class="Area_crtNum">
        
        <input type="tel" name="authNumber" id="authNumber" placeholder="<s:message code="label.pw.find.auth.number" /><s:message code="label.cmm.input" />" value="" maxlength="4" class="crtNum"/><label for="authNumber"></label>
        <f:errors path="authNumber" id="authNumberError" class="errorMsg"/>
        
        <!--인증번호받기--> 
        <div id="otpBtn">
            <button type="button" class="btn_crtNum" onClick="javascript:authNum();"><s:message code="label.pw.find.authnum.btn" /></button>
        </div>
        <!--//인증번호받기-->
        
        <!--인증 타이머-->
        <div id="timer" style="display:none">
            <span id="time"></span>
        </div>
        <!--//인증 타이머-->
      </div>
      
<!--       <input type="button" value="확인" onClick="javascript:sendNum();" /> -->
<!--       <input type="submit" value="확인" /> -->
    </div>
    <button type="button" class="btn_bluew100" onClick="javascript:sendNum();"><s:message code="label.cmm.confirm" /></button>
  </f:form>
  
  <span id="authNmerrors"></span>
  
<!--   <div id="timer"></div> -->
  
</div>


<script>
genEvent($("#userId"), $("#userIdError"));
genEvent($("#empNm"), $("#empNmError"));
genEvent($("#authNumber"), $("#authNumberError"));

function sendNum() {
  
  if($("#authNum").val() === "") {
    $("#authNumerrors").text("입력");
    return;
  }
  
  
  var param = {};
  param.userId = $("#userId").val();
  param.empNm = $("#empNm").val();
  param.authNumber = $("#authNumber").val();
  
  $.postJSON("/user/pwdFind.sb", param, function(result) {
    
  }).fail(function() {
    alert("Ajax Fail");
  });
}

/* 인증번호 받기 */
  function authNum() {
    var param = {};
    param.userId = $("#userId").val();
    param.empNm = $("#empNm").val();
    
    $.postJSON("/user/sendNum.sb", param, function(result) {
		if(result.status === "OK") {
		  $("#authNmerrors").text(result.data.msg != undefined ? result.data.msg : "");
		  $("#empNm").attr("disabled", true);
		  $("#userId").attr("disabled", true);
		  $("#otpBtn").hide();
		  $("#timer").show();
		  startTimer();
		  
		}
		else if(result.status === "FAIL") {
		  $("#empNm").attr("disabled", false);
		  $("#userId").attr("disabled", false);
		  $("#userIdError").text(result.data.userId != undefined ? result.data.userId : "");
		  $("#empNmError").text(result.data.empNm != undefined ? result.data.empNm : "");
		  $("#authNumberError").text(result.data.authNmerrors != undefined ? result.data.authNmerrors : "");
		}
    }).fail(function() {
      alert("Ajax Fail");
    });
  }
	
  var tid;
  function startTimer() {
    msg_time();
    tid = setInterval(function () {
      msg_time();
    }, 1000);
  }
  
  var ttt = 60*1;
  
  // 최초 설정 시간(기본 : 초)
  var SetTime = ttt;
  

  // 1초씩 카운트
  function msg_time() {
    // 남은 시간 계산
    m = Math.floor(SetTime / 60) + "분 " + (SetTime % 60) + "초";
    // div 영역에 보여줌 
//     var msg = "현재 남은 시간은 <font color='red'>" + m + "</font> 입니다.";
    var msg = m;
		document.all.time.innerHTML = msg;
		SetTime--;					// 1초씩 감소
		if (SetTime < 0) {			// 시간이 종료 되었으면..
		  
		  console.log("타이머 끝");
		  $("#empNm").attr("disabled", false);
		  $("#userId").attr("disabled", false);
		  $("#otpBtn").show();
		  $("#timer").hide();
		  clearInterval(tid);
		  SetTime = ttt;
		  document.all.time.innerHTML = "";
		  $("#authNmerrors").text("");
		}
	}
  
</script>


















