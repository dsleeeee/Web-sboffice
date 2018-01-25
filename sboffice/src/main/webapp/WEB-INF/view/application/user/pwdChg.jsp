<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="label.login.userPasswd" />
    &nbsp;
    <s:message code="label.cmm.find" />
  </h2>
  <p class="h2_txt">
    <s:message code="label.pw.find.new.h2" />
  </p>

  <div class="idInfo">
    <p>
      <span><s:message code="label.login.userId" /></span> <span>${userId}</span>
    </p>
  </div>

  <div class="lineArea"></div>
  <f:form class="loginF" method="post" modelAttribute="pwdChg" action="/user/pwdChgOk.sb" >
    <div class="writeInfo">
      <div>
        <input type="password" id="newPw" name="newPw" placeholder="<s:message code="label.pw.chg.new" />" class="pw" /><label for="pw"></label>
        <f:errors path="newPw" id="newPwError" class="errorMsg" />
      </div>
      <div>
        <input type="password" id="newPwConf" name="newPwConf" placeholder="<s:message code="label.pw.chg.new" />&nbsp;<s:message code="label.cmm.confirm" />" class="pw" /><label for="pw"></label>
        <f:errors path="newPwConf" id="newPwConfError" class="errorMsg" />
      </div>
    </div>

    <input id="uuid" name="uuid" style="display: none" value="${uuid}" />
    <input id="halfId" name="halfId" style="display: none" value="${userId}" />

    <button class="btn_bluew100">
      <s:message code="label.cmm.confirm" />
    </button>
  </f:form>

</div>


<script>
genEvent($("#newPw"), $("#newPwError"));
genEvent($("#newPwConf"), $("#newPwConfError"));
</script>