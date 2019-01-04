<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstmn/rtnDstmn/"/>

<div class="subCon" ng-controller="rtnDstmnCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
      <th><s:message code="rtnDstmn.outDate"/></th>
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
      <th><s:message code="rtnDstmn.procFg"/></th>
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
      <th><s:message code="rtnDstmn.slipKind"/></th>
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
      <th><s:message code="rtnDstmn.dlvrNm"/></th>
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
      <th><s:message code="rtnDstmn.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="rtnDstmn.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    <tr>
      <%-- 분배지시서 --%>
      <th><s:message code="rtnDstmn.dstbDrctn"/></th>
      <td colspan="3">
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.prodPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.prodStorePrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.storeProdPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.dlvrPrint"/></a>
      </td>
    </tr>
    <tr>
      <%-- 거래명세표 --%>
      <th><s:message code="rtnDstmn.stmtAcct"/></th>
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
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.stmtAcctPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.stmtAcctExcel"/></a>
      </td>
    </tr>
    <tr>
      <%-- 작성연월일 --%>
      <th><s:message code="rtnDstmn.writtenDateMonth"/></th>
      <td colspan="3">
        <div class="sb-select fl mr5">
          <span class="txtIn"><input id="writtenDate" class="w120px"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnDstmn.taxBillIssue"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('rtnDstmnCtrl')"><s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10 fr">
    <p class="s14 bk fl mr10 lh30"><s:message code="rtnDstmn.reqNoConfirm"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="reqNoConfirmCnt"></p>
    <p class="s14 bk fl mr10 lh30"><s:message code="rtnDstmn.outstockNoCreate"/> : </p>
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
        <wj-flex-grid-column header="<s:message code="rtnDstmn.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.slipFg"/>" binding="slipFg" width="60" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.slipKind"/>" binding="slipKind" width="70" align="center" is-read-only="true" data-map="slipKindMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.dlvrNm"/>" binding="dlvrCd" width="70" align="center" is-read-only="true" data-map="dlvrMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.reqDate"/>" binding="reqDate" width="90" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.orderTot"/>" binding="orderTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.mgrTot"/>" binding="mgrTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.outTot"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.inTot"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.penaltyAmt"/>" binding="penaltyAmt" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.hqRemark"/>" binding="hqRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.outDt"/>" binding="outDt" width="150" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstmn.outNm"/>" binding="outNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnDstmnCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnDstmn/rtnDstmn.js?ver=20181224.01" charset="utf-8"></script>

<%-- 출고자료생성 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstmn/rtnDstmnDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
