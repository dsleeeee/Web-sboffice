<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 매장환경, 외식환경, 유통환경 환경변수 설정 --%>
<div id="cmmEnvArea"  ng-controller="cmmEnvCtrl">

  <%-- 환경설정 분류 탭 --%>
  <div class="subTab2 mt20 mb10">
    <ul id="envGroupTab">
      <%-- 매장환경 --%>
      <li><a href="#" id="storeEnvFg" envstFg="00" class="on" ng-click="changeEnvGroup('00')"><s:message code="storeManage.storeEnv" /></a></li>
      <%-- 외식환경 --%>
      <li><a href="#" id="foodEnvFg" envstFg="01" ng-click="changeEnvGroup('01')"><s:message code="storeManage.foodEnv" /></a></li>
      <%-- 유통환경  //TODO 추후 추가 -%>
      <%-- <li><a href="#" id="distributionEnvFg" envstFg="02" ng-click="changeEnvGroup('02')"><s:message code="storeManage.distributionEnv" /></a></li> --%>
      <%-- 포스환경 --%>
      <li><a href="#" id="posEnvFg" envstFg="03" ng-click="changeEnvGroup('03')"><s:message code="storeManage.posEnv" /></a></li>
      <%-- 주방프린터 --%>
      <li><a href="#" id="printEnvFg" envstFg="98" ng-click="changeEnvGroup('98')"><s:message code="storeManage.kitchenPrint" /></a></li>
      <%-- 주방프린터 상품연결--%>
      <li><a href="#" id="printProductEnvFg" envstFg="99" ng-click="changeEnvGroup('99')"><s:message code="storeManage.kitchenPrintProduct" /></a></li>
    </ul>
  </div>

  <div id="storeConfigContent" style="height:370px; overflow-y: auto;"></div>
  <%-- 저장 --%>
  <div class="tc mt10">
    <button type="button" id="btnSaveStore"class="btn_blue" ng-click="save()" ><s:message code="cmm.save" /></button>
  </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeCmmEnv.js?ver=20181106.01" charset="utf-8"></script>

<script>
var envstGrp = ${ccu.getCommCodeExcpAll("004")};
</script>
