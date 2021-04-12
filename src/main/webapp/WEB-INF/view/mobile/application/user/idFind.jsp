<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="login.find.id" />
  </h2>
  <p class="h2_txt">
    <s:message code="login.id.find.info" />
  </p>

  <f:form method="post" modelAttribute="user" action="/mobile/user/idFind.sb" class="loginF">
    <div class="writeInfo">

      <div>
        <input type="text" id="empNm" name="empNm" placeholder="<s:message code="cmm.emp"/>&nbsp;<s:message code="cmm.name"/>" value="${user.empNm}" maxlength="10" class="name" /><label
          for="empNm"></label>
        <f:errors path="empNm" id="empNmError" class="errorMsg" />
      </div>

      <div>
        <input type="tel" id="mpNo" name="mpNo" placeholder="<s:message code="login.id.find.mp.holder"/>" value="${user.mpNo}" maxlength="15" class="tel" /><label for="pw"></label>
        <f:errors path="mpNo" id="mpNoError" class="errorMsg" />
      </div>

    </div>
    <button class="btn_bluew100">
      <s:message code="cmm.confirm" />
    </button>
  </f:form>

  <div class="linkArea">
    <span class="find">
      <a href="/mobile/auth/login.sb" class="fdId">
        <s:message code="login.go" />
      </a>
      <a href="/mobile/user/pwdFind.sb" class="fdPw">
        <s:message code="login.find.pw" />
      </a>
    </span>
  </div>

</div>
<script>
var msg = "${msg}";
if(msg.length > 0) {
  alert(msg);  
}
genEvent($("#empNm"), $("#empNmError"));
genEvent($("#mpNo"), $("#mpNoError"));
</script>

