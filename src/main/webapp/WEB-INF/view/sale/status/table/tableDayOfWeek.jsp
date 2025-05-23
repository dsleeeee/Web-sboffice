<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="tableDayOfWeekView" class="subCon"  ng-controller="tableDayOfWeekCtrl" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="tableDayOfWeek.tableDayOfWeekSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnTableDayOfWeekSearch" ng-click="_broadcast('tableDayOfWeekCtrlSrch')">
            <s:message code="cmm.search"/>
        </button>
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
            <th>
                <s:message code="cmm.search.month" />
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTableDayOfWeekStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTableDayOfWeekEndDate" class="w110px"></span>
                    <span class="chk ml10" style="display: none;">
							<input type="checkbox" ng-model="isChecked" ng-change="isChkDt()" />
							<label for="chkDt">
								<s:message code="cmm.all.day" />
							</label>
						</span>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="hidden" id="tableDayOfWeekSelectStoreCd" value=""/>
            <tr>
                    <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                        <%-- [NXPOS-1648,1699] 매장선택 모듈 통합 / 추후작업예정 --%>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/sale/com/popup/selectStoreS.jsp" flush="true">
                        <jsp:param name="targetId" value="tableDayOfWeekSelectStore"/>
                        <jsp:param name="subTargetId" value="tableDayOfWeekSelectTable"/>
                        <jsp:param name="closeFunc" value="closeSelectStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="tableDayOfWeekSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>

        <input type="hidden" id="tableDayOfWeekSelectTableCd" value=""/>
        <input type="hidden" id="tableDayOfWeekSelectTableName" value=""/>
        <input type="hidden" id="tableDayOfWeekSelectTableCdOrg" value=""/>
        <tr>
            <%-- 테이블선택 --%>
            <th><s:message code="cmm.table.select" /></th>
            <td>
                <%-- 테이블 선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectTable.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="M"/>
                    <jsp:param name="targetId" value="tableDayOfWeekSelectTable"/>
                    <jsp:param name="targetStoreId" value="tableDayOfWeekSelectStore"/>
                    <jsp:param name="closeFunc" value="closeSelectTable"/>
                </jsp:include>
                <%--// 테이블 선택 모듈 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <input type="text" id="tableDayOfWeekSelectStoreStoreNum" ng-model="storeNum">
        </c:if>
        <%-- 엑셀 다운로드 //TODO --%>
        <button class="btn_skyblue fr" ng-click="excelDownloadDay()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10" id="wjWrapType3">
        <div class="wj-gridWrap">
            <wj-flex-grid
                    id="tableDayOfWeekGrid"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    frozen-columns="4"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="tableDayOfWeek.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="tableDayOfWeek.saleDay"/>" binding="yoil" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totRealSaleAmt"/>" binding="totRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totSaleCnt"/>" binding="totSaleCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="tableDayOfWeek.totGuestCnt"/>" binding="totGuestCnt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="tableDayOfWeekCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/table/tableDayOfWeek.js?ver=20250415.01" charset="utf-8"></script>

