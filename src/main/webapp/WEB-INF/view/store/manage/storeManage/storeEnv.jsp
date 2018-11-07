<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="storeEnvLayer" show-trigger="Click" hide-trigger="Click" style="width:800px;height:600px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" >

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.storeInfo" />
      <span id="storeEnvTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <div ng-controller="storeEnvCtrl">
        <ul class="subTab">
          <%-- 매장정보 --%>
          <li><a id="storeInfo" href="#" ng-click="changeTab();"><s:message code="storeManage.storeInfo" /></a></li>
          <%-- 매장환경 --%>
          <li><a id="storeEnv" href="#" class="on"><s:message code="storeManage.storeEnv" /></a></li>
        </ul>
      </div>

      <%-- 매장환경, 외식환경, 유통환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storeCmmEnv.jsp">
      </c:import>

      <%-- 포스환경 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storePosEnv.jsp">
      </c:import>

      <%-- 포스기능키 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/storePosFuncKey.jsp">
      </c:import>

      <%-- 주방프린터 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrint.jsp">
      </c:import>

      <%-- 주방프린터 상품연결 --%>
      <c:import url="/WEB-INF/view/store/manage/storeManage/kitchenPrintProduct.jsp">
      </c:import>

    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeEnv.js?ver=20181107.01" charset="utf-8"></script>

