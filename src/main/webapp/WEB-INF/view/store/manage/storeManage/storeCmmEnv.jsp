<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 매장환경, 외식환경, 유통환경 환경변수 설정 --%>
<div id="cmmEnvArea"  ng-controller="cmmEnvCtrl">

  <%-- 매장환경 분류 탭 --%>
  <c:import url="/WEB-INF/view/store/manage/storeManage/storeInfoTab.jsp">
  </c:import>

  <div id="storeConfigContent" style="height:370px; overflow-y: auto;"></div>
  <%-- 저장 --%>
  <div class="tc mt10">
    <button type="button" id="btnSaveStore"class="btn_blue" ng-click="save()" >
      <s:message code="cmm.save" />
    </button>
  </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeCmmEnv.js?ver=20220104.03" charset="utf-8"></script>

<script>
var envstGrp = ${ccu.getCommCodeExcpAll("004")};
</script>
