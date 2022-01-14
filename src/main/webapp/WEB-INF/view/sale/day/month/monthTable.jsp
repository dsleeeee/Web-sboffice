<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/month/monthTable/"/>

<div id="monthTableView" name="monthView" class="subCon" style="display: none;" ng-controller="monthTableCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="month.tableSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('monthTableCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회월 --%>
            <th>
                <s:message code="month.month" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startMonthMonthTable" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonthMonthTable" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 테이블표시 --%>
            <th>
                <s:message code="month.tableList" />
            </th>
            <td colspan="3">
                <div class="sb-select" style="width:150px;">
                    <wj-combo-box
                        id="srchTableCd"
                        ng-model="monthTableCd"
                        items-source="_getComboData('tableCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjGridMonthTableList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totTblCnt"/>" binding="totTblCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.table.totGuestCnt"/>" binding="totGuestCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="monthTableCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthTable.js?ver=20220114.02" charset="utf-8"></script>
