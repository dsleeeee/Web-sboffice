<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<div class="loginArea">
  <h2 class="sTit">
    <s:message code="label.pw.find.ok" />
  </h2>
  <p class="h2_txt">
    <s:message code="label.pw.find.h2.1" />
    <br />
    <s:message code="label.pw.find.h2.2" />
  </p>
  <div class="lineArea">
    <a href="/auth/login.sb" class="btn_bluew100"><s:message code="label.login.submit" /></a>
  </div>
</div>


