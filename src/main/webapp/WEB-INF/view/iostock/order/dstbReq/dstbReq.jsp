<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbReq/dstbReq/"/>

<div class="subCon" ng-controller="dstbReqCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dstbReqCtrl')"><s:message code="cmm.search"/></button>
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
    </tbody>
  </table>

  <div class="tr mt10">
    <button type="button" id="btnDstbConfirm" class="btn_skyblue ml5" ng-click="saveDstbConfirm()"><s:message code="dstbReq.dstbConfirm"/></button><%--분배완료--%>
    <button                                   class="btn_skyblue ml5" ng-click="excelDownload()"  ><s:message code="cmm.excel.down" 	/></button><%--엑셀 다운로드--%>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" 			binding="gChk" 		width="30" 	align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.reqDate"/>" 	binding="reqDate" 	width="100"	align="center" 	is-read-only="true" 					format="date"				></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.storeCd"/>" 	binding="storeCd" 	width="70" 	align="center" 	is-read-only="true"													></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.storeNm"/>" 	binding="storeNm" 	width="150" align="left" 	is-read-only="true"													></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.procFg"/>" 	binding="procFg" 	width="70" 	align="center" 	is-read-only="true" data-map="procFgMap"							></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.dtlCnt"/>" 	binding="dtlCnt" 	width="70" 	align="right" 	is-read-only="true" data-type="Number" 	format="n0"					></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.orderTot"/>" 	binding="orderTot" 	width="130"	align="right" 	is-read-only="true" data-type="Number" 	format="n0" aggregate="Sum"	></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.mdTot"/>" 	binding="mdTot" 	width="130"	align="right" 	is-read-only="true" data-type="Number" 	format="n0" aggregate="Sum"	></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.modDt"/>" 	binding="modDt" 	width="130" align="center" 	is-read-only="true" 					format="dateTime"			></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.remark"/>" 	binding="remark" 	width="200" align="left" 	is-read-only="true"													></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstbReq.slipFg"/>" 	binding="slipFg" 	width="70" 	align="left" 	is-read-only="true" visible="false"									></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="dstbReqCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbReq/dstbReq.js?ver=20181224.01" charset="utf-8"></script>

<%-- 분배등록 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstbReq/dstbReqDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
