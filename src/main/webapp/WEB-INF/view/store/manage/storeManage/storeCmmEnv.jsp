<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 매장환경, 외식환경, 유통환경 환경변수 설정 --%>
<div id="cmmEnvArea" style="height:400px; overflow-y: auto;">
  <div id="storeConfigContent"></div>
  <%-- 저장 --%>
  <div class="tc mt10">
    <button type="button" id="btnSaveStore"class="btn_blue" ng-click="save()" ><s:message code="cmm.save" /></button>
  </div>
</div>

<script>
var envstGrp = ${ccu.getCommCodeExcpAll("004")};
</script>
