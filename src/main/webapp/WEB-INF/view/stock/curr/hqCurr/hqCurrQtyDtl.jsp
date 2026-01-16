<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="baseUrl" value="/stock/curr/hqCurr/"/>

<wj-popup id="hqCurrQtyDtlLayer" control="hqCurrQtyDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="cardLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqCurrQtyDtlCtrl">
    <div id="spanPopDtlTitle" class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqCurr.hqCurrQtyDtl"/>
      <label id="dtlProdCd"></label>
      <label id="dtlProdNm"></label>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 450px; padding-top: 0px; ">
      <table class="tblType01 mt10">
        <colgroup>
          <col class="w25"/>
          <col class="w25"/>
          <col class="w25"/>
          <col class="w25"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 조회일자 --%>
          <th>
            <s:message code="cmm.search.date" />
          </th>
          <td colspan="4">
            <div class="sb-select">
              <span class="txtIn"> <input id="srchStartDate" name="startDate" class="w110px" /></span>
              <span class="rg">~</span>
              <span class="txtIn"> <input id="srchEndDate" name="endDate" class="w110px" /></span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 단위구분 --%>
          <th><s:message code="hqCurr.unitFg"/></th>
          <td>
            <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
                    id="srchUnitFgDtl"
                    ng-model="unitFgDtl"
                    items-source="_getComboData('srchUnitFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

      <%-- 조회버튼 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="getSearchHqCurrQtyDtlList()" ><s:message code="cmm.search" /></button>
      </div>

      <input type="hidden" id="hqOfficeCode" value="${sessionInfo.hqOfficeCd}"/>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 250px; overflow-y: hidden; overflow-x: hidden" >
          <wj-flex-grid
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  items-source="data"
                  control="flex"
                  initialized="initGrid(s,e)"
                  is-read-only="false"
                  item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="hqCurr.hqCurrQtyDtl.iostockDate"/>"		binding="iostockDate"   width="*"		align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.hqCurrQtyDtl.iostockFg"/>"			binding="iostockFg"		width="*"		align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.hqCurrQtyDtl.iostockQty"/>"		binding="iostockQty"	width="*"		align="center"	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqCurr.hqCurrQtyDtl.accIostockQty"/>"		binding="accIostockQty"	width="*"		align="center"	is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/stock/curr/hqCurr/hqCurrQtyDtl.js?ver=20200413.01" charset="utf-8"></script>
