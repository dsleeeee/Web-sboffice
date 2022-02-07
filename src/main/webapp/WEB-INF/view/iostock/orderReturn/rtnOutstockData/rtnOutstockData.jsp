<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnOutstockData/rtnOutstockData/"/>

<div class="subCon" ng-controller="rtnOutstockDataCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('rtnOutstockDataCtrl')"><s:message code="cmm.search"/></button>
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
      <td colspan="3">
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
    </tbody>
  </table>

  <div class="tr mt10 fr">
    <%-- 반품일자 --%>
    <p class="s14 bk fl mr10 lh30"><s:message code="rtnOutstockData.outDate"/></p>
    <div class="sb-select mr10 fl">
      <span class="txtIn"><input id="outDate" class="w150px"></span>
    </div>
    <%-- 반품자료생성 --%>
    <button type="button" id="btnDataCreate" class="btn_skyblue ml5 fl" ng-click="saveValueCheck()"><s:message code="rtnOutstockData.dataCreate"/></button>
  </div>
  <div style="clear: both;"></div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter"
        ime-enabled="true">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" 					binding="gChk" 		width="40" 	align="center" 	is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.storeCd"/>" 	binding="storeCd" 	width="70" 	align="center" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.storeNm"/>" 	binding="storeNm" 	width="180" align="left" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.reqDate"/>" 	binding="reqDate" 	width="0" 	align="left" 	is-read-only="true" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.sysStatFg"/>" binding="sysStatFg" width="70" 	align="center" 	is-read-only="true" data-map="sysStatFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.dstbAmt"/>" 	binding="dstbAmt" 	width="90" 	align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.dstbVat"/>" 	binding="dstbVat" 	width="90" 	align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.dstbTot"/>" 	binding="dstbTot" 	width="90" 	align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtlCnt"/>" 	binding="dtlCnt" 	width="90" 	align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.currLoanAmt"/>" 		binding="currLoanAmt" 		width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.availableOrderAmt"/>" binding="availableOrderAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.remark"/>" 	binding="remark" 	width="150" align="left" 	is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnOutstockData.hqRemark"/>" 	binding="hqRemark" 	width="150" align="left" 	is-read-only="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnOutstockDataCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript">
  var sysStatFg     = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnOutstockData/rtnOutstockData.js?ver=20181224.01" charset="utf-8"></script>

<%-- 반품자료생성 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnOutstockData/rtnOutstockDataDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
