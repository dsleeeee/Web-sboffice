<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--right-->
<div class="contents">
  <div class="elseCon">
    <p class="lgTxt">
      해당 메뉴 사용 권한이 없습니다.
    </p>
    <p class="smTxt mt20">
      <c:if test="${codeType == 'HQ_ENV'}">
        <s:message code="cmm.require.hqEnv.setting" />
        <br>
        <s:message code="cmm.hqEnv" /> &nbsp; ${codeStr}
      </c:if>
      <c:if test="${codeType == 'STORE_ENV'}">
        <s:message code="cmm.require.storeEnv.setting" />
        <br>
        <s:message code="cmm.storeEnv" /> &nbsp;& ${codeStr}
      </c:if>
    </p>
  </div>
</div>
<!--//right-->
