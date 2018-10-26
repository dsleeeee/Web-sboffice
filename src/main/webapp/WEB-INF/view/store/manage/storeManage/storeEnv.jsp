<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeEnvLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:800px;height:600px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="storeEnvCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeEnvTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 매장정보 --%>
        <li><a id="storeInfo" href="#" ng-click="changeTab();"><s:message code="storeManage.storeInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="storeEnv" href="#" class="on"><s:message code="storeManage.storeEnv" /></a></li>
      </ul>

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

      <%-- 매장환경, 외식환경, 유통환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storeCmmEnv.jsp">
      </c:import>

      <%-- 포스환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storePoEnv.jsp">
      </c:import>

    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeEnv.js?ver=2018102301" charset="utf-8"></script>

<%-- 테이블 그룹설정 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/setTableGroup.jsp">
</c:import>

<%-- 포스 명칭 설정 --%>
<%--
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
--%>

<%-- 포스 설정복사 --%>
<%--
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
--%>

<%-- 포스 삭제 --%>
<%--
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
--%>
