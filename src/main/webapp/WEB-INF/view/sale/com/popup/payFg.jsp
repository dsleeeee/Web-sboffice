<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="payFgLayer" control="payFgLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleComPayFgCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
        결제수단별 매출현황 - {{dialogHd}}
      <span id="spanDtlTitle"></span>
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
	          <wj-flex-grid-column header="<s:message code="prodpayfg.realSaleDate"/>" 	binding="saleDate" 		width="150" align="center"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.storeCd"/>" 		binding="storeCd" 		width="150" align="center" visible="false"></wj-flex-grid-column>
	          <c:if test="${sessionInfo.orgnFg == 'HQ'}">
	          <wj-flex-grid-column header="<s:message code="prodpayfg.storeNm"/>" 		binding="storeNm" 		width="150" align="center"></wj-flex-grid-column>
	          </c:if>
	          <c:if test="${sessionInfo.orgnFg == 'STORE'}">
	          <wj-flex-grid-column header="<s:message code="prodpayfg.storeNm"/>" 		binding="storeNm" 		width="150" align="center" visible="false"></wj-flex-grid-column>
	          </c:if>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.saleFg"/>" 		binding="saleFg" 		width="150" align="center" visible="false"></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="prodpayfg.posNm"/>" 		binding="posNo" 		width="140" align="center"></wj-flex-grid-column>
          	  <wj-flex-grid-column header="<s:message code="prodpayfg.billNo"/>" 		binding="billNo" 		width="140" align="center"></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.billDt"/>" 		binding="billDt" 		width="*" align="center""></wj-flex-grid-column>
	          <wj-flex-grid-column header="<s:message code="prodpayfg.payAmt"/>" 		binding="payAmt" 		width="150" align="right" aggregate="Sum"></wj-flex-grid-column>
	      </div>
	    </div>
	    <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/payFg.js?ver=20190207.01" charset="utf-8"></script>

<%-- 영수증 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/billSalePop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<c:import url="/WEB-INF/view/sale/com/popup/billRtnPop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>