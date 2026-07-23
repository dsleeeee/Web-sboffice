<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="timeSaleBensonDayView" class="subCon" ng-controller="timeSaleBensonDayCtrl" style="display: none;padding: 10px 20px 40px;">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="timeSaleBenson.dayTitle"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('timeSaleBensonDayCtrl', 1)" id="timeSaleBensonDayBtnSearch">
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
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeSaleBensonDayStartDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeSaleBensonDayEndDate" class="w110px"/></span>
                </div>
            </td>
            <%-- 일자옵션 --%>
            <th><s:message code="timeSaleBenson.dayOption"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchTimeSaleBensonDayDateOptionCombo"
                            ng-model="dayOption"
                            items-source="_getComboData('dayOptionCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장옵션 --%>
            <th><s:message code="timeSaleBenson.storeOption"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchTimeSaleBensonDayStoreOptionCombo"
                            ng-model="storeOption"
                            items-source="_getComboData('dayStoreOptionCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            selected-index-changed="onStoreOptionChanged(s)"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 매장선택 --%>
            <th id="timeSaleBensonDayStoreSelectTh" style="display: none;"><s:message code="cmm.store.select"/></th>
            <td id="timeSaleBensonDayStoreSelectTd" style="display: none;">
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="timeSaleBensonDayStore"/>
                </jsp:include>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh">
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
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
                    id="wjGridTimeSaleBensonDayList">

                <wj-flex-grid-column header="<s:message code="timeSaleBenson.saleDate"/>" binding="saleDate" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.dayFrom"/>" binding="dayFrom" width="90" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.dayTo"/>" binding="dayTo" width="90" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.storeCd"/>" binding="storeCd" width="80" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.storeNm"/>" binding="storeNm" width="140" align="left" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.saleHour"/>" binding="saleHour" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.totSaleAmt"/>" binding="totSaleAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.totDcAmt"/>" binding="totDcAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.realSaleAmt"/>" binding="realSaleAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.realSaleCnt"/>" binding="realSaleCnt" width="90" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.billUprc"/>" binding="billUprc" width="90" align="right"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.gaAmt"/>" binding="gaAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="timeSaleBenson.vatAmt"/>" binding="vatAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <div class="pageNum mt20">
        <ul id="timeSaleBensonDayCtrlPager" data-size="10"></ul>
    </div>

    <div class="w100 mt10" style="display:none;" ng-controller="timeSaleBensonDayExcelCtrl">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="excelFlex"
                initialized="initGrid(s,e)"
                items-source="data"
                is-read-only="true"
                id="wjGridTimeSaleBensonDayExcelList">

            <wj-flex-grid-column header="<s:message code="timeSaleBenson.saleDate"/>" binding="saleDate" width="90" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.dayFrom"/>" binding="dayFrom" width="90" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.dayTo"/>" binding="dayTo" width="90" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.storeCd"/>" binding="storeCd" width="80" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.storeNm"/>" binding="storeNm" width="140" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.saleHour"/>" binding="saleHour" width="70" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.totSaleAmt"/>" binding="totSaleAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.totDcAmt"/>" binding="totDcAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.realSaleAmt"/>" binding="realSaleAmt" width="110" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.realSaleCnt"/>" binding="realSaleCnt" width="90" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.billUprc"/>" binding="billUprc" width="90" align="right"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.gaAmt"/>" binding="gaAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="timeSaleBenson.vatAmt"/>" binding="vatAmt" width="100" align="right" aggregate="Sum"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/anals/timeSaleBenson/timeSaleBensonDay.js?ver=20260720.01" charset="utf-8"></script>
