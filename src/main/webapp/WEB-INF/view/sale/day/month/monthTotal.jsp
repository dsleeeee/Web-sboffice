<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/sale/day/month/monthTotal/"/>

<div id="monthTotalView" name="monthView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="monthTotalCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"> <s:message code="month.total" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('monthTotalCtrl',1)">
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
                    <s:message code="cmm.search.month" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonthMonthTotal" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonthMonthTotal" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="monthTotalStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
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
        <div class="wj-gridWrap" style="height:450px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="month.yearMonth"/>" binding="yearMonth" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.storeCnt"/>" binding="storeCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.saleCnt"/>" binding="saleCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totSaleAmt"/>" binding="totSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totDcAmt"/>" binding="totDcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.billCnt"/>" binding="billCnt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.billUprc"/>" binding="billUprc" width="70" is-read-only="true" align="right" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.gaAmt"/>" binding="gaAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totTipAmt"/>" binding="totTipAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totEtcAmt"/>" binding="totEtcAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.cupAmt"/>" binding="cupAmt" width="70" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.totPayAmt"/>" binding="totPayAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 결제수단 컬럼 생성--%>
                    <c:forEach var="payCol" items="${payColList}">
                        <wj-flex-grid-column header="${payCol.payNm}" binding="pay${payCol.payCd}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </c:forEach>
                    <wj-flex-grid-column header="<s:message code="month.genRealSaleAmt"/>" binding="genRealSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.genRealSaleAmtPer"/>" binding="genRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.dlvrRealSaleAmt"/>" binding="dlvrRealSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.dlvrRealSaleAmtPer"/>" binding="dlvrRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.packRealSaleAmt"/>" binding="packRealSaleAmt" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.packRealSaleAmtPer"/>" binding="packRealSaleRate" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="monthTotalCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthTotal.js?ver=20250415.01" charset="utf-8"></script>
