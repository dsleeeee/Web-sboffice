<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="inStockReportByAcquireCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('inStockReportByAcquireCtrl', 1)"><s:message code="cmm.search"/></button>
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
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"> <input id="srchStartDate" name="srchStartDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="srchEndDate" name="srchEndDate" class="w110px" /></span>
                        </span>
                    </div>
                </td>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="inStockReportByAcquireStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 tr">
        <%-- 출력 --%>
        <button type="button" class="btn_skyblue ml5" ng-click="print()">
            <s:message code="inStockReportByAcquire.print"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.vendrNm"/>"    binding="prodClassNm"   width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.vendrNm"/>"    binding="vendrNm"       width="100" align="center"  is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.cashAmt"/>"    binding="cashAmt"       width="90" align="right"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.arAmt"/>"      binding="arAmt"         width="90" align="right"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.rtnAmt"/>"     binding="rtnAmt"        width="90" align="right"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.taxAmt"/>"     binding="taxAmt"        width="90" align="right"    is-read-only="true"> </wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.tfAmt"/>"      binding="tfAmt"         width="90" align="right"    is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="inStockReportByAcquire.totAmt"/>"     binding="totAmt"        width="90" align="right"    is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="inStockReportByAcquireCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/inStockReportByAcquire/inStockReportByAcquire.js?ver=20251202.01" charset="utf-8"></script>

<%-- 매입처별 입고내역서 팝업 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/acquire/inStockReportByAcquire/inStockReportByAcquireReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
