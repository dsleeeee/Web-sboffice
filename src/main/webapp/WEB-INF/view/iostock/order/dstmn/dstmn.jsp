<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/dstmn/"/>

<div class="subCon" ng-controller="dstmnCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dstmnCtrl')"><s:message code="cmm.search"/></button>
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
      <%-- 출고일자 --%>
      <th><s:message code="dstmn.outDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행 --%>
      <th><s:message code="dstmn.procFg"/></th>
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
      <%-- 종류 --%>
      <th><s:message code="dstmn.slipKind"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchSlipKind"
            ng-model="slipKind"
            items-source="_getComboData('srchSlipKind')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
    </tr>
    <tr>
      <%-- 기사 --%>
      <th><s:message code="dstmn.dlvrNm"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchDlvrCd"
            ng-model="dlvrCd"
            items-source="_getComboData('srchDlvrCd')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
      <th></th>
      <td></td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="dstmn.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="dstmn.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    <tr>
      <%-- 분배지시서 --%>
      <th><s:message code="dstmn.dstbDrctn"/></th>
      <td colspan="3">
        <a href="#" class="btn_grayS" ng-click="report('prod')"><s:message code="dstmn.prodPrint"/></a>
        <a href="#" class="btn_grayS" ng-click="report('prodStore')"><s:message code="dstmn.prodStorePrint"/></a>
        <a href="#" class="btn_grayS" ng-click="report('storeProd')"><s:message code="dstmn.storeProdPrint"/></a>
        <a href="#" class="btn_grayS" ng-click="report('dlvr')"><s:message code="dstmn.dlvrPrint"/></a>
      </td>
    </tr>
    <tr>
      <%-- 거래명세표 --%>
      <th><s:message code="dstmn.stmtAcct"/></th>
      <td colspan="3">
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="stmtAcctFg"
            ng-model="stmtAcctFg"
            items-source="_getComboData('stmtAcctFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('trans')"><s:message code="dstmn.stmtAcctPrint"/></a>
        <%--<a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.stmtAcctExcel"/></a>--%>
      </td>
    </tr>
    <tr>
      <%-- 작성연월일 --%>
      <th><s:message code="dstmn.writtenDateMonth"/></th>
      <td colspan="3">
        <div class="sb-select fl mr5">
          <span class="txtIn"><input id="writtenDate" class="w120px"></span>
        </div>
        <span class="txtIn w80px sb-select fl mr5">
          <wj-combo-box
            id="billFg"
            ng-model="billFg"
            items-source="_getComboData('billFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <span class="txtIn w120px sb-select fl mr5">
          <wj-combo-box
            id="taxFg"
            ng-model="taxFg"
            items-source="_getComboData('taxFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click="report('tax')"><s:message code="dstmn.taxBillIssue"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="tr mt10 fr">
    <p class="s14 bk fl mr10 lh30"><s:message code="dstmn.reqNoConfirm"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="reqNoConfirmCnt"></p>
    <p class="s14 bk fl mr10 lh30"><s:message code="dstmn.outstockNoCreate"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="outstockNoCreateCnt"></p>
  </div>
  <div style="clear: both;"></div>

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
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipFg"/>" binding="slipFg" width="60" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipKind"/>" binding="slipKind" width="70" align="center" is-read-only="true" data-map="slipKindMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.dlvrNm"/>" binding="dlvrCd" width="70" align="center" is-read-only="true" data-map="dlvrMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.reqDate"/>" binding="reqDate" width="90" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.orderTot"/>" binding="orderTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.mgrTot"/>" binding="mgrTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outTot"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.inTot"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.hqRemark"/>" binding="hqRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outDt"/>" binding="outDt" width="150" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outNm"/>" binding="outNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="dstmnCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstmn/dstmn.js?ver=20181224.01" charset="utf-8"></script>

<%-- 거래명세표 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstmnDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 세금계산서 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/taxReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 거래명세표 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/transReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(상품) 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbProdReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(상품-매장) 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbProdStoreReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(매장-상품) 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbStoreProdReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(기사별) 배송기사 리스트 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbDlvr.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(기사별) 배송기사 상품-매장 리포트 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbDlvrProdStoreReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배지시서(기사별) 배송기사 매장-상품 리포트 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstbDlvrStoreProdReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
