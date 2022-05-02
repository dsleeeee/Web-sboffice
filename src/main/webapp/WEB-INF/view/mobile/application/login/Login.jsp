<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String mobile_url = "";
System.out.println(request.getRequestURL());
if     (request.getRequestURL().indexOf("://192") > 0 || request.getRequestURL().indexOf("://localhost") > 0 )  { mobile_url = "http://192.168.0.85:10001/mobile/auth/login.sb"; }
else if(request.getRequestURL().indexOf("://neo.solbipos.com") > 0  )                                           { mobile_url = "http://neo.solbipos.com/mobile/auth/login.sb"; }
%>

<div class="loginArea">
  <h2>Welcome Login</h2>

  <f:form class="loginF" modelAttribute="sessionInfo" method="post" action="/mobile/auth/login.sb">

    <%--     <s:eval expression="@env['login.check.id.save']" var="idField"/> --%>

    <c:if test="${userId == '' || userId ne null}">
      <c:set var="cid" value="${userId}" />
    </c:if>
    <c:if test="${userId eq null}">
      <c:set var="cid" value="${cookie.saveid.value}" />
    </c:if>

    <c:if test="${cookie.sb_login_auto_serial.value != '' && cookie.sb_login_auto_serial.value ne null}">
      <c:set var="cLoginAuto" value="${cookie.sb_login_auto_serial.value}" />
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
          placeholder="<s:message code="login.userPasswd"/>" maxlength="25" /><label
          for="userPwd"></label>
        <f:errors path="userPwd" id="userPwdError" class="errorMsg" />
      </div>
    </div>

    <div class="idsave">
      <button class="btn_login">
        <s:message code="login.submit" />
      </button>
      <span> <input type="checkbox" id="chk" name="chk" onclick="idSaveClick()"
        ${empty cid ? '' : 'checked="checked"' } />
        <label for="chk">
          <s:message code="login.rememberId" />
        </label>
      </span>
      <span> <input type="checkbox" id="chkLoginAuto" name="chkLoginAuto" onclick="loginAutoClick()"
        ${empty cLoginAuto ? '' : 'checked="checked"' } />
          <label for="chkLoginAuto">
            <s:message code="login.loginAuto" />
          </label>
      </span>
    </div>
  </f:form>

  <div class="linkArea">
    <span class="find">
      <a href="/mobile/user/idFind.sb" class="fdId">
        <s:message code="login.find.id" />
      </a>
      <a href="/mobile/user/pwdFind.sb" class="fdPw">
        <s:message code="login.find.pw" />
      </a>
    </span>
    <a href="#" id="termsOfUse" style="margin-right: 0px; line-height:0px; font-size:0.875em; text-decoration:underline;">이용약관</a>
  </div>

</div>

<c:import url="/WEB-INF/view/mobile/application/layer/alert.jsp">
</c:import>

<c:if test="${type == 'pwChg' || type == 'pwExpire'}">
  <c:import url="/WEB-INF/view/application/layer/pwChgPop.jsp">
    <c:param name="type" value="${type}" />
  </c:import>
</c:if>

<%-- 이용약관 레이어 팝업 가져오기 --%>
<c:import url="/WEB-INF/view/mobile/application/layer/mobileTermsOfUsePop.jsp">
</c:import>

<script>
	genEventSingle($("#userId"));
  	genEventSingle($("#userPwd"));
  	<c:if test="${type == 'pwChg' || type == 'pwExpire'}">
  		var id = "${cid}";
  		$("#labelUserId").text(id);
  		$("#pwdUserId").val(id);
  		$("#fullDimmedPw").show();
      	$("#layerpw").show();
	</c:if>

  // 자동로그인이 체크되어 있을 때 아이디저장 체크 해제 시, 자동로그인도 함께 체크 해제
  function idSaveClick(){
    if(!$('#chk').is(":checked")){
      $("input:checkbox[id='chkLoginAuto']").prop("checked", false);
    }
  }

  // 자동로그인 체크박스 클릭 시 아이디 저장도 함께 체크
  function loginAutoClick(){
    $("input:checkbox[id='chk']").prop("checked", true);
  }



  // 이용약관
  $("#termsOfUse").bind("click", function () {
    $("#fullDimmedMobileTermsOfUsePop").show();
    $("#layerMobileTermsOfUsePop").show();
    return false;
  });
</script>



















