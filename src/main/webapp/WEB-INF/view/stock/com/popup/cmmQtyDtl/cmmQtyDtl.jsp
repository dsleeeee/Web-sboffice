<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/com/popup/cmmQtyDtl/"/>
<c:set var="qtyFg" value="{{qtyFg}}"/>

<wj-popup id="cmmQtyDtlLayer" control="cmmQtyDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="cmmQtyDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      {{popNm}}<s:message code="cmmQtyDtl.info"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 400px;">
    <%-- 상품코드, 상품명 --%>
    <table class="searchTbl" style="text-align: center;">
        <colgroup>
            <col class="w33"/>
            <col class="w33"/>
            <col class="w33"/>
        </colgroup>
        <tbody>
            <tr style="border-top: 1px solid #ccc;">
                <th style="text-align: center;"><s:message code="cmmQtyDtl.prodCd"/></th>
                <th style="text-align: center;"><s:message code="cmmQtyDtl.prodNm"/></th>
                <th style="text-align: center;"><s:message code="cmmQtyDtl.poUnitQty"/></th>
            </tr>
            <tr>
                <td style="border-left: 1px solid #ccc;">{{prodCd}}</td>
                <td>{{prodNm}}</td>
                <td>{{poUnitQty}}</td>
            </tr>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadCmmQtyDtl()"><s:message code="cmm.excel.down" />
        </button>
    </div>

      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 300px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            id="qtyDtlGrid"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.slipNo"/>"              binding="slipNo"            width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.instockDate"/>"         binding="instockDate"       width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.hqOfficeCd"/>"          binding="hqOfficeCd"        width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.hqOfficeNm"/>"          binding="hqOfficeNm"        width="*"     align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.createDate"/>"          binding="createDate"        width="*"     align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmmQtyDtl.fg"/>"                  binding="fg"                width="*"     align="left"   is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
	      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
	        <jsp:param name="pickerTarget" value="cmmQtyDtlCtrl"/>
	      </jsp:include>
	      <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/com/popup/cmmQtyDtl/cmmQtyDtl.js?ver=20190207.01" charset="utf-8"></script>
