<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/com/popup/"/>

<wj-popup id="tableLayer" control="tableLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="saleComTableCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="saleComPopup.table"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 360px;">

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter"
            id="wjGridSaleComTableList">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="saleComPopup.storeCd"/>" 		binding="storeCd" 		width="100" 	align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.storeNm"/>" 		binding="storeNm" 		width="100" 	align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.saleDate"/>" 	binding="saleDate" 		width="70" 	align="center" is-read-only="true" visible="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.posNo"/>" 		binding="posNo" 		width="60" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.billNo"/>" 		binding="billNo"		width="70" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.billDt"/>" 		binding="billDt"		width="70" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.saleFg"/>" 		binding="saleFg" 		width="60" 	align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.totSaleAmt"/>" 	binding="totSaleAmt" 	width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.realSaleAmt"/>" 	binding="realSaleAmt" 	width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.netSaleAmt"/>" 	binding="netSaleAmt" 	width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.vatAmt"/>" 		binding="vatAmt" 		width="100" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.payTot"/>" 		binding="payTot"		width="100" 	align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.pay01"/>" 		binding="pay01" 		width="100" 	align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.pay02"/>" 		binding="pay02" 		width="100" 	align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.pay03"/>" 		binding="pay03" 		width="100" 	align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleComPopup.guestTot"/>" 	binding="guestTot" 		width="70" 	align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <%-- 객수 컬럼 생성--%>
            <c:forEach var="guestCol" items="${guestColList}">
              <wj-flex-grid-column header="${guestCol.guestNm}" binding="guest${guestCol.guestCd}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </c:forEach>
            <wj-flex-grid-column header="<s:message code="saleComPopup.guestAmt"/>" 	binding="guestAmt" 		width="70" 	align="right"  is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
          
            <%-- ColumnPicker 사용시 include --%>
			<jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
				<jsp:param name="pickerTarget" value="saleComTableCtrl"/>
			</jsp:include>
			<%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/com/table.js?ver=20210706.01" charset="utf-8"></script>

<%-- 영수증 팝업 상세 레이어 --%>
<c:import url="/WEB-INF/view/sale/com/popup/billSalePop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<c:import url="/WEB-INF/view/sale/com/popup/billRtnPop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>