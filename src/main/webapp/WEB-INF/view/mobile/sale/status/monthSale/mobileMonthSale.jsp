<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileMonthSaleCtrl">

    <div class="searchBar">
        <%-- 월별매출현황 --%>
        <a href="#" class="fl"><s:message code="mobile.monthSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileMonthSaleCtrl', 1)">
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
            <%-- 조회월 --%>
            <th><s:message code="cmm.search.month"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"> <input id="startMonth" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonth" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <c:if test="${multiStoreFg ne 0}">
            <tr>
                <%-- (다중)매장코드 --%>
                <th><s:message code="mobile.monthSale.store"/></th>
                <td>
                    <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileMonthSaleStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileMonthSaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 매출종합 --%>
    <div class="gridBar mt10" id="mobileMonthSaleTotal" onclick="girdFldUnfld('mobileMonthSaleTotal')">
        <a href="#" class="open"><s:message code="mobile.monthSale.saleTotal"/></a>
    </div>
    <div class="wj-dialog-body sc2" id="mobileMonthSaleTotalGrid" ng-controller="mobileMonthSaleTotalCtrl">
        <table class="tblType01">
            <colgroup>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 매출건수 --%>
                <th class="tc br bl">
                    <s:message code="mobile.monthSale.realSaleCnt"/>
                </th>
                <%-- 반품건수 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.rtnSaleCnt"/>
                </th>
                <%-- 영수건수 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.billCnt"/>
                </th>
                <%-- 영업월수 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.saleCnt"/>
                </th>
            </tr>
            <tr>
                <td class="tr br bl">
                    <label id="lblRealSaleCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblRtnSaleCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblBillCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblSaleCnt"></label>
                </td>
            </tr>
            <tr>
                <%-- 총매출액 --%>
                <th class="tc br bl">
                    <s:message code="mobile.monthSale.totSaleAmt"/>
                </th>
                <%-- 총할인액 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.totDcAmt"/>
                </th>
                <%-- 실매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.realSaleAmt"/>
                </th>
                <%-- 월평균매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.monthAvrSale"/>
                </th>
            </tr>
            <tr>
                <td class="tr br bl">
                    <label id="lblTotSaleAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblTotDcAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblRealSaleAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblMonthAvrSale"></label>
                </td>
            </tr>
            <tr>
                <%-- 카드매출 --%>
                <th class="tc br bl">
                    <s:message code="mobile.monthSale.cardAmt"/>
                </th>
                <%-- 현금매출 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.cashAmt"/>
                </th>
                <%-- 기타매출 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.etcAmt"/>
                </th>
                <%-- 영수단가 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.billUprc"/>
                </th>
            </tr>
            <tr>
                <td class="tr br bl">
                    <label id="lblCardAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblCashAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblEtcAmt"></label>
                </td>
                <td class="tr br">
                    <label id="lblBillUprc"></label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <%-- //매출종합 --%>

    <%-- 결제수단 --%>
    <div class="gridBar mt10" id="mobileMonthSalePay" onclick="girdFldUnfld('mobileMonthSalePay')">
        <a href="#" class="open"><s:message code="mobile.monthSale.salePay"/></a>
    </div>
    <div class="w100" id="mobileMonthSalePayGrid">
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
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.nm"/>" binding="nm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //결제수단 --%>

    <%-- 할인내역 --%>
    <div class="gridBar mt10" id="mobileMonthSaleDc" onclick="girdFldUnfld('mobileMonthSaleDc')">
        <a href="#" class="open"><s:message code="mobile.monthSale.saleDc"/></a>
    </div>
    <div class="w100" id="mobileMonthSaleDcGrid" ng-controller="mobileMonthSaleDcCtrl">
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
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.nm"/>" binding="nm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //할인내역 --%>

    <%-- 내점현황 --%>
    <div class="gridBar mt10" id="mobileMonthSaleShop" onclick="girdFldUnfld('mobileMonthSaleShop')">
        <a href="#" class="open"><s:message code="mobile.monthSale.saleShop"/></a>
    </div>
    <div class="w100" id="mobileMonthSaleShopGrid" ng-controller="mobileMonthSaleShopCtrl">
        <table class="tblType01">
            <colgroup>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
                <col class="w25"/>
            </colgroup>
            <tbody>
            <tr>
                <%-- 실매출액 --%>
                <th class="tc br bl">
                    <s:message code="mobile.monthSale.realSaleAmt"/>
                </th>
                <%-- 월평균매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.monthAvrSale"/>
                </th>
                <%-- 방문객수 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.totGuestCnt"/>
                </th>
                <%-- 객단가 --%>
                <th class="tc br">
                    <s:message code="mobile.monthSale.guestUprc"/>
                </th>
            </tr>
            <tr>
                <td class="tr br bl">
                    <label id="lblShopRealSaleCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblShopMonthAvrSale"></label>
                </td>
                <td class="tr br">
                    <label id="lblShopTotGuestCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblShopGuestUprc"></label>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <%-- //내점현황 --%>

    <%-- 내점/배달/포장 --%>
    <div class="gridBar mt10" id="mobileMonthSaleDlvr" onclick="girdFldUnfld('mobileMonthSaleDlvr')">
        <a href="#" class="open"><s:message code="mobile.monthSale.saleDlvr"/></a>
    </div>
    <div class="w100" id="mobileMonthSaleDlvrGrid">
        <div ng-controller="mobileMonthSaleDlvrCtrl">
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
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.nm"/>" binding="nm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.monthAvrSale"/>" binding="monthAvrSale" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.billCnt"/>" binding="billCnt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.billUprc"/>" binding="billUprc" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 내점/배달/포장 차트 --%>
        <div ng-controller="mobileMonthSaleDlvrChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileMonthSaleDlvrChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.saleYm"/>" binding="saleYm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.shop"/>" binding="shopRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.dlvr"/>" binding="dlvrRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.pack"/>" binding="packRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileMonthSaleDlvrBarChart"
                        name="barChart1"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:230px; font-size:14px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        binding-x="saleYm">

                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.shop"/>" binding="shopRealSaleAmt"></wj-flex-chart-series>
                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.dlvr"/>" binding="dlvrRealSaleAmt"></wj-flex-chart-series>
                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.pack"/>" binding="packRealSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 내점 차트 --%>
        <div ng-controller="mobileMonthSaleDlvrChart2Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileMonthSaleDlvrChart2Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.saleYm"/>" binding="saleYm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.shop"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileMonthSaleDlvrBarChart2"
                        name="barChart2"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:230px; font-size:14px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        binding-x="saleYm">

                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.shop"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 배달 차트 --%>
        <div ng-controller="mobileMonthSaleDlvrChart3Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileMonthSaleDlvrChart3Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.saleYm"/>" binding="saleYm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.dlvr"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileMonthSaleDlvrBarChart3"
                        name="barChart3"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:230px; font-size:14px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        binding-x="saleYm">

                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.dlvr"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 포장 차트 --%>
        <div ng-controller="mobileMonthSaleDlvrChart4Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileMonthSaleDlvrChart4Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.saleYm"/>" binding="saleYm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.monthSale.pack"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileMonthSaleDlvrBarChart4"
                        name="barChart4"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:230px; font-size:14px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        binding-x="saleYm">

                    <wj-flex-chart-series name="<s:message code="mobile.monthSale.pack"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
    </div>
    <%-- //내점/배달/포장 --%>

    <%-- 월자별 매출현황 --%>
    <div class="gridBar mt10" id="mobileMonthSaleDtl" onclick="girdFldUnfld('mobileMonthSaleDtl')">
        <a href="#" class="open"><s:message code="mobile.monthSale.saleDtl"/></a>
    </div>
    <div class="w100" id="mobileMonthSaleDtlGrid" ng-controller="mobileMonthSaleDtlCtrl">
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
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.saleYm"/>" binding="saleYm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.totRealSaleAmt"/>" binding="totRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 배달 --%>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="dlvrRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billCnt"/>" binding="dlvrBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billUprc"/>" binding="dlvrBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 포장 --%>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="packRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billCnt"/>" binding="packBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billUprc"/>" binding="packBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 내점 --%>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.realSaleAmt"/>" binding="shopRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billCnt"/>" binding="shopBillCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.billUprc"/>" binding="shopBillUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.totGuestCnt"/>" binding="totGuestCnt" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.guestUprc"/>" binding="guestUprc" width="60" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 결제수단 --%>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.pay.cardAmt"/>" binding="cardAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.pay.cashAmt"/>" binding="cashAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.pay.etcAmt"/>" binding="etcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 할인 --%>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.dc.dcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.dc.coupnDcAmt"/>" binding="coupnDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.monthSale.dc.etcDcAmt"/>" binding="etcDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //월자별 매출현황 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/monthSale/mobileMonthSale.js?ver=20210512.01" charset="utf-8"></script>