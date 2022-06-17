<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="prodLayer" control="prodLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:860px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleComProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleComPopup.prod"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 360px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            id="prodPopGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="saleComPopup.prodClassNm"/>" 	binding="pathNm" 		width="300" align="left" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.prodCd"/>" 		binding="prodCd"		width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.prodNm"/>" 		binding="prodNm"		width="150" align="center" is-read-only="true" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.totSaleQty"/>" 	binding="totSaleQty" 	width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
          	<%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="saleComProdCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/prod.js?ver=20200121.02" charset="utf-8"></script>