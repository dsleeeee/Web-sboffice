<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2>Password Find</h2>
  
  <f:form method="post" action="/user/pwdFind.sb" modelAttribute="user">
    
    <div class="writeInfo">
      
      <input type="text" name="userId" id="userId" placeholder="아이디" value="" maxlength="20" />
      <span id="userIderrors"></span>
      <f:errors path="userId"/>
      
      <input type="text" name="empNm" id="empNm" placeholder="담당자 이름" value="" maxlength="10" />
      <span id="empNmerrors"></span>
      <f:errors path="empNm"/>
      
      <input type="text" name="authNumber" id="authNumber" placeholder="인증번호입력" value="" maxlength="4" />
      <span id="authNumerrors"></span>
      <f:errors path="authNumber"/>
        
<!--       <input type="button" value="확인" onClick="javascript:sendNum();" /> -->
      <input type="submit" value="확인" />
    </div>
    
  </f:form>
  
  <input id="otpBtn" type="button" value="인증번호 받기" onClick="javascript:authNum();"/>
  <span id="authNmerrors"></span>
  
  <div id="timer"></div>
  
</div>


<script>


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
		  startTimer();
		  
		}
		else if(result.status === "FAIL") {
		  $("#empNm").attr("disabled", false);
		  $("#userId").attr("disabled", false);
		  $("#empNmerrors").text(result.data.empNm != undefined ? result.data.empNm : "");
		  $("#userIderrors").text(result.data.userId != undefined ? result.data.userId : "");
		  $("#authNmerrors").text(result.data.authNmerrors != undefined ? result.data.authNmerrors : "");
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
    var msg = "현재 남은 시간은 <font color='red'>" + m + "</font> 입니다.";
		document.all.timer.innerHTML = msg;
		SetTime--;					// 1초씩 감소
		if (SetTime < 0) {			// 시간이 종료 되었으면..
		  
		  console.log("타이머 끝");
		  $("#empNm").attr("disabled", false);
		  $("#userId").attr("disabled", false);
		  $("#otpBtn").show();
		  clearInterval(tid);
		  SetTime = ttt;
		  document.all.timer.innerHTML = "";
		  $("#authNmerrors").text("");
		}
	}
  
</script>


















