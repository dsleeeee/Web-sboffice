<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sale/anals/monthly/monthlyPop/"/>

<wj-popup id="monthlyPayFgLayer" control="monthlyPayFgLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleAnalsMonthlyPayFgCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"><s:message code="saleAnalsMonthly.saleAnalsMonthly" /></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
	    <div class="w100 mt10">
	      <div class="wj-gridWrap" style="height: 340px;">
	        <wj-flex-grid
	          autoGenerateColumns="false"
	          control="flex"
	          initialized="initGrid(s,e)"
	          sticky-headers="true"
	          selection-mode="Row"
	          items-source="data"
	          is-read-only="true"
	          item-formatter="_itemFormatter">

	          <!-- define columns -->
	          <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.saleDate"/>" 		binding="saleDate" 		width="0" 	align="center"	visible="false"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.posNo"/>" 			binding="posNo" 		width="150" align="center"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.billNo"/>" 		binding="billNo" 		width="150" align="center"></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.payCd"/>" 			binding="payCd" 		width="0" 	align="center"	visible="false"></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.payCdNm"/>" 		binding="payCdNm" 		width="140" align="center"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="saleAnalsMonthly.payAmt"/>" 		binding="payAmt" 		width="130" align="right" aggregate="Sum"></wj-flex-grid-column>
	      </div>
	      <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="saleAnalsMonthlyPayFgCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
	    </div>
	    <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/monthly/monthlyPop/saleAnalsMonthlyPayFg.js" charset="utf-8"></script>

<%-- 영수증 팝업 상세 레이어 --%>
<%-- <c:import url="/WEB-INF/view/sale/com/popup/billSalePop.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>
<%-- <c:import url="/WEB-INF/view/sale/com/popup/billRtnPop.jsp"> --%>
<%--   <c:param name="menuCd" value="${menuCd}"/> --%>
<%--   <c:param name="menuNm" value="${menuNm}"/> --%>
<%-- </c:import> --%>