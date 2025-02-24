<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrder/"/>

<div class="subCon" ng-controller="storeOrderCtrl">
  <div class="searchBar">
    <a href="#" class="open fl"><s:message code="storeOrder.storeOrder" /></a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w85"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
          <wj-combo-box
            id="srchDateFg"
            ng-model="dateFg"
            items-source="_getComboData('srchDateFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
          </span>
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행구분 --%>
      <th><s:message code="storeOrder.procFg"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchProcFg"
            ng-model="procFg"
            items-source="_getComboData('srchProcFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
    </tr>
    <tr>
      <%-- 출고요청일자 --%>
      <th><s:message code="storeOrder.reqDate"/></th>
      <td>
        <div class="sb-select fl mr10">
          <span class="txtIn"><input id="reqDate" class="w150px" ng-model="reqDate"></span>
        </div>
        <%-- 거래처 --%>
        <div class="sb-select fl w150px" <c:if test="${envst1242 == '0' or envst1242 == '2'}">style="display: none;"</c:if>>
          <wj-combo-box
            id="vendrCd"
            ng-model="vendrCd"
            control="vendrCdCombo"
            items-source="_getComboData('vendrCd')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
        <a href="#" class="btn_grayS" ng-click="newReqOrder()"><s:message code="storeOrder.reqRegist"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb10 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeOrderCtrl')">
      <s:message code="cmm.search"/></button>
  </div>

	<div class="mt10 oh sb-select dkbr">
		<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>	<%-- 엑셀 다운로드 --%>
	</div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="storeOrder.reqDate"/>" binding="reqDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.orderSlipNo"/>" binding="orderSlipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.slipFg"/>" binding="slipFg" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.vendr"/>" binding="hqVendrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.dtlCnt"/>" binding="dtlCnt" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.orderAmt"/>" binding="orderAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.orderVat"/>" binding="orderVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.orderTot"/>" binding="orderTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.remark"/>" binding="remark" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="storeOrder.vendr"/>" binding="hqVendrCd" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="storeOrderCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript">
	var gReqDate 	= '${reqDate}';
	var gEnvst1044 	= '${envst1044}';
	var gEnvst1042 	= '${envst1042}';
	var gEnvst1242  = '${envst1242}';

	console.log('gReqDate  : ' + gReqDate);
	console.log('gEnvst1044: ' + gEnvst1044);
	console.log('gEnvst1042: ' + gEnvst1042);
    console.log('gEnvst1242: ' + gEnvst1242);

    <%-- 본사 거래처 콤보박스 --%>
    var vendrList = ${vendrList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/storeOrder/storeOrder.js?ver=20250103.01" charset="utf-8"></script>

<%-- 주문등록 상품 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/storeOrder/storeOrderDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 주문등록 상품 등록 팝업 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/storeOrder/storeOrderRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
