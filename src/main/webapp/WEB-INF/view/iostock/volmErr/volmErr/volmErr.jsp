<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="baseUrl" value="/iostock/volmErr/volmErr/volmErr/"/>

<div class="subCon" ng-controller="volmErrCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('volmErrCtrl')"><s:message code="cmm.search"/></button>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td <c:if test="${sessionInfo.orgnFg == 'STORE'}"> colspan="3"</c:if> >
        <div class="sb-select">
          <span class="txtIn w120px">
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
          <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
        </div>
      </td>
      <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
	      <td colspan="3">
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="volmErrSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
	      </td>
      </c:if>
    </tr>
    <tr>
      <%-- 구분 --%>
      <th><s:message code="volmErr.slipFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchSlipFg"
              ng-model="slipFg"
              items-source="_getComboData('srchSlipFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <%-- 진행 --%>
      <th><s:message code="volmErr.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
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
        </div>
      </td>
    </tr>
    <tr <c:if test="${envst1242 == '0' or orgnFg == 'STORE'}">style="display: none;"</c:if>>
      <%-- 거래처 --%>
      <th><s:message code="dstbReq.vender"/></th>
      <td>
        <div class="sb-select fl w150px">
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
      </td>
    </tr>
    </tbody>
  </table>

	<div class="mt10 oh sb-select dkbr">
		<button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>	<%-- 엑셀 다운로드 --%>
	</div>

  <div style="clear: both;"></div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="volmErr.slipNo"/>" 		binding="slipNo" 		width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.slipFg"/>" 		binding="slipFg" 		width="60" 	align="center"  is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.procFg"/>" 		binding="procFg" 		width="70"  align="center"  is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.storeCd"/>" 		binding="storeCd" 		width="0"	align="center"  is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.storeNm"/>" 		binding="storeNm" 		width="150" align="left"    is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outDate"/>" 		binding="outDate" 		width="90" 	align="center"  is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inDate"/>" 		binding="inDate" 		width="90" 	align="center"  is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outUnitQty"/>" 	binding="outUnitQty"	width="80" 	align="right"   is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outEtcQty"/>" 	binding="outEtcQty" 	width="80" 	align="right"   is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inUnitQty"/>" 	binding="inUnitQty" 	width="80" 	align="right"   is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inEtcQty"/>" 		binding="inEtcQty" 		width="80" 	align="right"   is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.newSlipNo"/>" 	binding="newSlipNo" 	width="100" align="center" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.remark"/>" 		binding="remark" 		width="150" align="left" 	is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="volmErrCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript">
  var orgnFg = '${orgnFg}';

  var gEnvst1242  = '${envst1242}';
  var empVendrCd = '${empVendrCd}';

  <%-- 본사 거래처 콤보박스 --%>
  var vendrList = ${vendrList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/volmErr/volmErr/volmErr.js?ver=20240611.01" charset="utf-8"></script>

<%-- 물량오류 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/volmErr/volmErr/volmErrDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
