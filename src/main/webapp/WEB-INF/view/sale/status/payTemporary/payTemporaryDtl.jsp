<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<wj-popup id="wjPayTemporaryDtlLayer" control="wjPayTemporaryDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px; height:480px;">
  <div id="PayTemporaryDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="payTemporaryDtlCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="PayTemporary.PayTemporaryDtl"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2">
      <div class="oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="payTemporary.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <wj-flex-grid-column header="<s:message code="payTemporary.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <wj-flex-grid-column header="<s:message code="payTemporary.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="payTemporary.billNo"/>" binding="billNo" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="payTemporary.lineNo"/>" binding="lineNo" width="70" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="payTemporary.lineSeqNo"/>" binding="lineSeqNo" width="90" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="payTemporary.saleYn"/>" binding="saleYn" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
            </c:if>
            <wj-flex-grid-column header="<s:message code="payTemporary.saleAmt"/>" binding="saleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript">
  var orgnFg = '${orgnFg}';
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/status/payTemporary/payTemporaryDtl.js?ver=20230321.01" charset="utf-8"></script>