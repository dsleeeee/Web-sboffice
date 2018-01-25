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
  
  <f:form id="frm" method="post" action="/user/pwdChg.sb" modelAttribute="user" class="loginF">
<%--    <f:form class="loginF"> --%>
    
    <div class="writeInfo">
      
      <div>
        <input type="text" name="userId" id="userId" placeholder="<s:message code="label.login.userId" />" value="" maxlength="20" class="id" /><label for="userId"></label>
        <span path="userId" id="userIdError" class="errorMsg" style="display:none"></span>
      </div>
      
      <div>
        <input type="text" name="empNm" id="empNm" placeholder="<s:message code="label.cmm.emp" />&nbsp;<s:message code="label.cmm.name" />" value="" maxlength="10" class="name" /><label for="empNm"></label>
        <span path="empNm" id="empNmError" class="errorMsg" style="display:none"></span>
      </div>
      
      <div class="Area_crtNum">
        
        <input type="tel" name="authNumber" id="authNumber" placeholder="<s:message code="label.pw.find.auth.number" /><s:message code="label.cmm.input" />" value="" maxlength="4" class="crtNum"/><label for="authNumber"></label>
        <span path="authNumber" id="authNumberError" class="errorMsg" style="display:none"></span>
        
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
        
        <input id="uuid" name="uuid" style="display:none"/>
        
      </div>
    </div>
    <button type="button" onClick="javascript:sendNum();" class="btn_bluew100" ><s:message code="label.cmm.confirm" /></button>
  </f:form>
</div>


<script>
genEvent($("#userId"), $("#userIdError"));
genEvent($("#empNm"), $("#empNmError"));
genEvent($("#authNumber"), $("#authNumberError"));

  function sendNum() {
    var param = {};
    param.userId = $("#userId").val();
    param.empNm = $("#empNm").val();
    param.authNumber = $("#authNumber").val();
    
    $.postJSON("/user/pwdFind.sb", param, function(result) {
      console.log(result.status);
      console.log(result.data);
      
      if(result.status === "OK") {
        document.forms.frm.elements.uuid.value = result.data.uuid;
        document.forms.frm.submit();
      }
      else if(result.status === "FAIL") {
        processError(result.data);
      }
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
      console.log(result.status);
      console.log(result.data);
		if(result.status === "OK") {
		  startTimer();
		  $("#otpBtn").hide();
		  $("#timer").show();
		  
		  $("#userIdError").hide();
		  $("#empNmError").hide();
			
		  if(result.data.msg != undefined) {
		    alert(result.data.msg);
		  }
		}
		else if(result.status === "FAIL") {
		  processError(result.data);
		}
    }).fail(function() {
      alert("Ajax Fail");
    });
  }
  
  function processError(data) {
    if(data.userId != undefined) {
      $("#userIdError").text(data.userId != undefined ? data.userId : "");
      $("#userIdError").show();
    }
    if(data.empNm != undefined) {
      $("#empNmError").text(data.empNm != undefined ? data.empNm : "");
      $("#empNmError").show();
    }
    if(data.authNumber != undefined) {
      $("#authNumberError").text(data.authNumber != undefined ? data.authNumber : "");
      $("#authNumberError").show();
    }
    if(data.msg != undefined) {
      $("#userIdError").hide();
      $("#empNmError").hide();
      alert(data.msg);
    }
  }
	
  var tid;
  function startTimer() {
    msg_time();
    tid = setInterval(function () {
      msg_time();
    }, 1000);
  }
  
  var ttt = 60*${otpLimit};
  
  // 최초 설정 시간(기본 : 초)
  var SetTime = ttt;

  // 1초씩 카운트
  function msg_time() {
    // 남은 시간 계산
    m = Math.floor(SetTime / 60) + "분 " + (SetTime % 60) + "초";
    // div 영역에 보여줌 
    var msg = m;
		document.all.time.innerHTML = msg;
		SetTime--;					// 1초씩 감소
		if (SetTime < 0) {			// 시간이 종료 되었으면..
		  
		  console.log("타이머 끝");
		  $("#otpBtn").show();
		  $("#timer").hide();
		  clearInterval(tid);
		  SetTime = ttt;
		  document.all.time.innerHTML = "";
		}
	}
  
</script>


















