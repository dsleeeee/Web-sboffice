<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

  <%-- 환경설정 분류 탭 --%>
  <div class="subTab2 mt20 mb10">
    <ul id="envGroupTab">
      <%-- 매장환경 --%>
      <li><a href="#" id="storeEnvFg" envstFg="00" class="on" ng-click="changeTabSrch();changeEnvGroup('00')"><s:message code="storeManage.storeEnv" /></a></li>
      <%-- 외식환경 --%>
      <li><a href="#" id="foodEnvFg" envstFg="01" ng-click="changeTabSrch();changeEnvGroup('01')"><s:message code="storeManage.foodEnv" /></a></li>
      <%-- 유통환경  //TODO 추후 추가 -%>
      <%-- <li><a href="#" id="distributionEnvFg" envstFg="02" ng-click="changeEnvGroup('02')"><s:message code="storeManage.distributionEnv" /></a></li> --%>
      <%-- 포스환경 --%>
      <li><a href="#" id="posEnvFg" envstFg="03" ng-click="changeTabSrch();changeEnvGroup('03')"><s:message code="storeManage.posEnv" /></a></li>
      <%-- 포스기능키 --%>
      <%--<li><a href="#" id="posFuncFg" envstFg="10" ng-click="changeEnvGroup('10')"><s:message code="storeManage.posFuncKey" /></a></li>--%>
      <%-- 주방프린터 --%>
      <li><a href="#" id="printEnvFg" envstFg="98" ng-click="changeTabSrch();changeEnvGroup('98')"><s:message code="storeManage.kitchenPrint" /></a></li>
      <%-- 주방프린터 상품연결--%>
      <li><a href="#" id="printProductEnvFg" envstFg="99" ng-click="changeTabSrch();changeEnvGroup('99')"><s:message code="storeManage.kitchenPrintProduct" /></a></li>
    </ul>
  </div>
