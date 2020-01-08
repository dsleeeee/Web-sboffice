<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeek/dayOfWeekTable/"/>

<div id="dayOfWeekTableView" name="dayOfWeekView" class="subCon" style="display: none;" ng-controller="dayOfWeekTableCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="dayofweek.tableSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('dayOfWeekTableCtrl',1)">
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
                <%-- 조회일자 --%>
                <th>
                    <s:message code="dayofweek.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDateDayOfWeekTable" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDateDayOfWeekTable" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 테이블표시 --%>
                <th>
                    <s:message code="dayofweek.tableList" />
                </th>
                <td colspan="3">
                    <div class="sb-select" style="width:150px;">
                        <wj-combo-box
                            id="srchTableCd"
                            ng-model="dayOfWeekTableCd"
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
                    id="wjGridDayofweekTableList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayofweek.yoil"/>" binding="yoil" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.totTblCnt"/>" binding="totTblCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.table.totGuestCnt"/>" binding="totGuestCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 외식테이블 컬럼 생성--%>
                    <c:forEach var="tableCol" items="${tableColList}">
                        <wj-flex-grid-column header="<s:message code="dayofweek.realSaleAmt"/>" binding="tbl${tableCol.tblCd}RealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayofweek.tblCnt"/>" binding="tbl${tableCol.tblCd}TblCnt" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayofweek.totGuestCnt"/>" binding="tbl${tableCol.tblCd}GuestCnt" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayOfWeek/dayOfWeekTable.js?ver=20200107.03" charset="utf-8"></script>