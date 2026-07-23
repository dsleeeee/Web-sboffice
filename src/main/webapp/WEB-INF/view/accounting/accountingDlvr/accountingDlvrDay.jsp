<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="accountingDlvrDayView" class="subCon" ng-controller="accountingDlvrDayCtrl" style="display: none;padding: 10px 20px 40px;">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="accountingDlvr.dayTitle"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('accountingDlvrDayCtrl', 1)" id="accountingDlvrDayBtnSearch">
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
            <th><s:message code="cmm.search.date"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchAccountingDlvrDayStartDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchAccountingDlvrDayEndDate" class="w110px"/></span>
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="cmm.store.select"/></th>
            <td colspan="3">
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="accountingDlvrDayStore"/>
                </jsp:include>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadSearch()"><s:message code="cmm.excel.downCondition"/></button>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadCurrent()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:420px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    ime-enabled="true"
                    id="wjGridAccountingDlvrDayList">

                <wj-flex-grid-column header="<s:message code="accountingDlvr.saleDate"/>" binding="saleDate" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.storeCd"/>" binding="storeCd" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.storeNm"/>" binding="storeNm" width="140" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.dlvrNm"/>" binding="dlvrNm" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.realSaleAmtExceptDlvr"/>" binding="realSaleAmtExceptDlvr" width="140" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.gaAmtExceptDlvr"/>" binding="gaAmtExceptDlvr" width="120" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.vatAmtExceptDlvr"/>" binding="vatAmtExceptDlvr" width="130" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.gaAmt"/>" binding="gaAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.vatAmt"/>" binding="vatAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="accountingDlvr.dlvrAmt"/>" binding="dlvrAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <div class="pageNum mt20">
        <ul id="accountingDlvrDayCtrlPager" data-size="10"></ul>
    </div>

    <div class="w100 mt10" style="display:none;" ng-controller="accountingDlvrDayExcelCtrl">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                items-source="data"
                is-read-only="true"
                id="wjGridAccountingDlvrDayExcelList">

            <wj-flex-grid-column header="<s:message code="accountingDlvr.saleDate"/>" binding="saleDate" width="90" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.storeCd"/>" binding="storeCd" width="80" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.storeNm"/>" binding="storeNm" width="140" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.dlvrNm"/>" binding="dlvrNm" width="80" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.realSaleAmtExceptDlvr"/>" binding="realSaleAmtExceptDlvr" width="140" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.gaAmtExceptDlvr"/>" binding="gaAmtExceptDlvr" width="120" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.vatAmtExceptDlvr"/>" binding="vatAmtExceptDlvr" width="130" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.gaAmt"/>" binding="gaAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.vatAmt"/>" binding="vatAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accountingDlvr.dlvrAmt"/>" binding="dlvrAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/accounting/accountingDlvr/accountingDlvrDay.js?ver=20260716.01" charset="utf-8"></script>
