<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileMultiStoreSaleCtrl">

    <div class="searchBar">
        <%-- 다중매장매출현황 --%>
        <a href="#" class="fl"><s:message code="mobile.multiStoreSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileMultiStoreSaleCtrl', 1)">
            <s:message code="cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="mobile.cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <c:if test="${multiStoreFg ne 0}">
            <tr>
                <%-- (다중)매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                    <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileMultiStoreSaleStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileMultiStoreSaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 다중매장매출현황 --%>
    <div class="gridBar mt10" id="mobileMultiStoreSale" onclick="girdFldUnfld('mobileMultiStoreSale')">
        <a href="#" class="open"><s:message code="mobile.multiStoreSale"/></a>
    </div>
    <div class="w100" id="mobileMultiStoreSaleGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.storeNm"/>" binding="storeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.saleCnt"/>" binding="saleCnt" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.shopRealSaleAmt"/>" binding="shopRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dlvrRealSaleAmt"/>" binding="dlvrRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.packRealSaleAmt"/>" binding="packRealSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.amtRate"/>" binding="amtRate" width="110" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.shopAmtRate"/>" binding="shopAmtRate" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dlvrAmtRate"/>" binding="dlvrAmtRate" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.packAmtRate"/>" binding="packAmtRate" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <div ng-controller="mobileMultiStoreSale2Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.storeNm"/>" binding="storeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totSaleAmt"/>" binding="totSaleAmt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.saleCnt"/>" binding="saleCnt" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dayAvrSale"/>" binding="dayAvrSale" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="billCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="billUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totGuestCnt"/>" binding="totGuestCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.guestUprc"/>" binding="guestUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 내점 --%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="shopRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.avrSale"/>" binding="shopAvrSale" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="shopBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="shopBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 배달 --%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="dlvrRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.avrSale"/>" binding="dlvrAvrSale" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="dlvrBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="dlvrBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 포장 --%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="packRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.avrSale"/>" binding="packAvrSale" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="packBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="packBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 결제수단 --%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.cardAmt"/>" binding="cardAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.cashAmt"/>" binding="cashAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.etcAmt"/>" binding="etcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <%-- 할인 --%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.dcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.coupnDcAmt"/>" binding="coupnDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.etcDcAmt"/>" binding="etcDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 다중매장매출현황 차트 --%>
        <div ng-controller="mobileMultiStoreSaleChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileMultiStoreSaleChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.storeNm"/>" binding="storeNm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileMultiStoreSaleBarChart"
                        name="barChart1"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:230px; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="storeNm">

                    <wj-flex-chart-series name="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
    </div>
    <%-- //다중매장매출현황 --%>

    <%-- 일자-매장별 매출현황 --%>
    <div class="gridBar mt10" id="mobileMultiStoreSaleDayStore" onclick="girdFldUnfld('mobileMultiStoreSaleDayStore')">
        <a href="#" class="open"><s:message code="mobile.multiStoreSale.dayStore"/></a>
    </div>
    <div class="w100" id="mobileMultiStoreSaleDayStoreGrid" ng-controller="mobileMultiStoreSaleDayStoreCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.saleDate"/>" binding="saleDate" width="70" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.storeNm"/>" binding="storeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totSaleAmt"/>" binding="totSaleAmt" width="80" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totDcAmt"/>" binding="totDcAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="billCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="billUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.totGuestCnt"/>" binding="totGuestCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.guestUprc"/>" binding="guestUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 내점 --%>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="shopRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="shopBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="shopBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 배달 --%>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="dlvrRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="dlvrBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="dlvrBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 포장 --%>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.realSaleAmt"/>" binding="packRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billCnt"/>" binding="packBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.billUprc"/>" binding="packBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 결제수단 --%>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.cardAmt"/>" binding="cardAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.cashAmt"/>" binding="cashAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.pay.etcAmt"/>" binding="etcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 할인 --%>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.dcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.coupnDcAmt"/>" binding="coupnDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.multiStoreSale.dc.etcDcAmt"/>" binding="etcDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //일자-매장별 매출현황 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/multiStoreSale/mobileMultiStoreSale.js?ver=20210520.01" charset="utf-8"></script>