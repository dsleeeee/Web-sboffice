<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="saleAnalysisByMonthlyCtrl">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="saleAnalysisByMonthly.saleAnalysisByMonthly"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('saleAnalysisByMonthlyCtrl',1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
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
            <%-- 조회월 --%>
            <th><s:message code="cmm.search.month"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="startMonth" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endMonth" class="w110px"></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="saleAnalysisByMonthly.print" /></button>
        <button class="btn_skyblue fr mr5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.type"/>" binding="branchNm" width="90" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.thisYear"/>" binding="totSaleAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.lastYear"/>" binding="bTotSaleAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.contrast"/>" binding="totSaleAmtContrast" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.percent"/>" binding="totSaleAmtPercent" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.thisYear"/>" binding="saleQty" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.lastYear"/>" binding="bSaleQty" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.contrast"/>" binding="saleQtyContrast" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.percent"/>" binding="saleQtyPercent" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.thisYear"/>" binding="totGuestCnt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.lastYear"/>" binding="bTotGuestCnt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.contrast"/>" binding="totGuestCntContrast" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.percent"/>" binding="totGuestCntPercent" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>


                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.thisYear.weekdays"/>" binding="weekdays" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.lastYear.weekdays"/>" binding="bWeekdays" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.contrast"/>" binding="weekdaysContrast" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.thisYear.weekend"/>" binding="weekend" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.lastYear.weekend"/>" binding="bWeekend" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByMonthly.contrast"/>" binding="weekendContrast" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>


            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/generalInfo/saleAnalysisByMonthly/saleAnalysisByMonthly.js?ver=20251023.01" charset="utf-8"></script>

<%-- 월별매출분석 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/generalInfo/saleAnalysisByMonthly/saleAnalysisByMonthlyReport.jsp">
</c:import>