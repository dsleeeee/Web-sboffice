<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="label.login.userId" />
    &nbsp;
    <s:message code="label.cmm.find" />
  </h2>
  <p class="h2_txt">
    <s:message code="label.id.find.info" />
  </p>

  <c:if test="${!empty msg}">
    <h2 class="sTit">${msg}</h2>
  </c:if>

  <f:form method="post" modelAttribute="user" action="/user/idFind.sb" class="loginF">
    <div class="writeInfo">

      <div>
        <input type="text" id="empNm" name="empNm" placeholder="<s:message code="label.cmm.emp"/>&nbsp;<s:message code="label.cmm.name"/>" value="${user.empNm}" maxlength="10" class="name" /><label
          for="empNm"></label>
        <f:errors path="empNm" id="empNmError" class="errorMsg" />
      </div>

      <div>
        <input type="tel" id="mpNo" name="mpNo" placeholder="<s:message code="label.id.find.mp.holder"/>" value="${user.mpNo}" maxlength="15" class="tel" /><label for="pw"></label>
        <f:errors path="mpNo" id="mpNoError" class="errorMsg" />
      </div>

    </div>
    <button class="btn_bluew100">
      <s:message code="label.cmm.confirm" />
    </button>
  </f:form>

  <div class="linkArea">
    <span class="find"><a href="/auth/login.sb" class="login_gray">
        <s:message code="label.login.go" />
      </a></span>
  </div>
</div>
<script>
  genEvent($("#empNm"), $("#empNmError"));
  genEvent($("#mpNo"), $("#mpNoError"));
</script>

