<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileDaySaleCtrl">

    <div class="searchBar">
        <%-- 일별매출현황 --%>
        <a href="#" class="fl"><s:message code="mobile.daySale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileDaySaleCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
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
                        <jsp:param name="targetId" value="mobileDaySaleStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileDaySaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 매출종합 --%>
    <div class="gridBar mt10" id="mobileDaySaleTotal" onclick="girdFldUnfld('mobileDaySaleTotal')">
        <a href="#" class="open"><s:message code="mobile.daySale.saleTotal"/></a>
    </div>
    <div class="wj-dialog-body sc2" id="mobileDaySaleTotalGrid" ng-controller="mobileDaySaleTotalCtrl">
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
                    <s:message code="mobile.daySale.realSaleCnt"/>
                </th>
                <%-- 반품건수 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.rtnSaleCnt"/>
                </th>
                <%-- 영수건수 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.billCnt"/>
                </th>
                <%-- 영업일수 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.saleCnt"/>
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
                    <s:message code="mobile.daySale.totSaleAmt"/>
                </th>
                <%-- 총할인액 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.totDcAmt"/>
                </th>
                <%-- 실매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.realSaleAmt"/>
                </th>
                <%-- 일평균매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.dayAvrSale"/>
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
                    <label id="lblDayAvrSale"></label>
                </td>
            </tr>
            <tr>
                <%-- 카드매출 --%>
                <th class="tc br bl">
                    <s:message code="mobile.daySale.cardAmt"/>
                </th>
                <%-- 현금매출 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.cashAmt"/>
                </th>
                <%-- 기타매출 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.etcAmt"/>
                </th>
                <%-- 영수단가 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.billUprc"/>
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
    <div class="gridBar mt10" id="mobileDaySalePay" onclick="girdFldUnfld('mobileDaySalePay')">
        <a href="#" class="open"><s:message code="mobile.daySale.salePay"/></a>
    </div>
    <div class="w100" id="mobileDaySalePayGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileDaySalePay"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.daySale.nm"/>" binding="nm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileDaySalePayMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //결제수단 --%>

    <%-- 할인내역 --%>
    <div class="gridBar mt10" id="mobileDaySaleDc" onclick="girdFldUnfld('mobileDaySaleDc')">
        <a href="#" class="open"><s:message code="mobile.daySale.saleDc"/></a>
    </div>
    <div class="w100" id="mobileDaySaleDcGrid" ng-controller="mobileDaySaleDcCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileDaySaleDc"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.daySale.nm"/>" binding="nm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.dcRealSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileDaySaleDcMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //할인내역 --%>

    <%-- 내점현황 --%>
    <div class="gridBar mt10" id="mobileDaySaleShop" onclick="girdFldUnfld('mobileDaySaleShop')">
        <a href="#" class="open"><s:message code="mobile.daySale.saleShop"/></a>
    </div>
    <div class="w100" id="mobileDaySaleShopGrid" ng-controller="mobileDaySaleShopCtrl">
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
                    <s:message code="mobile.daySale.realSaleAmt"/>
                </th>
                <%-- 일평균매출액 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.dayAvrSale"/>
                </th>
                <%-- 방문객수 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.totGuestCnt"/>
                </th>
                <%-- 객단가 --%>
                <th class="tc br">
                    <s:message code="mobile.daySale.guestUprc"/>
                </th>
            </tr>
            <tr>
                <td class="tr br bl">
                    <label id="lblShopRealSaleCnt"></label>
                </td>
                <td class="tr br">
                    <label id="lblShopDayAvrSale"></label>
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
    <div class="gridBar mt10" id="mobileDaySaleDlvr" onclick="girdFldUnfld('mobileDaySaleDlvr')">
        <a href="#" class="open"><s:message code="mobile.daySale.saleDlvr"/></a>
    </div>
    <div class="w100" id="mobileDaySaleDlvrGrid">
        <div ng-controller="mobileDaySaleDlvrCtrl">
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
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.nm"/>" binding="nm" width="1.1*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.realSaleAmt"/>" binding="realSaleAmt" width="1.6*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.dayAvrSale"/>" binding="dayAvrSale" width="1.7*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.billCnt"/>" binding="billCnt" width="1.2*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.billUprc"/>" binding="billUprc" width="1.4*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 내점/배달/포장 차트 --%>
        <div ng-controller="mobileDaySaleDlvrChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileDaySaleDlvrChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.saleDate"/>" binding="saleDate" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.shop"/>" binding="shopRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.dlvr"/>" binding="dlvrRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.pack"/>" binding="packRealSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileDaySaleDlvrBarChart"
                        name="barChart1"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleDate">

                    <wj-flex-chart-series name="<s:message code="mobile.daySale.shop"/>" binding="shopRealSaleAmt"></wj-flex-chart-series>
                    <wj-flex-chart-series name="<s:message code="mobile.daySale.dlvr"/>" binding="dlvrRealSaleAmt"></wj-flex-chart-series>
                    <wj-flex-chart-series name="<s:message code="mobile.daySale.pack"/>" binding="packRealSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 내점 차트 --%>
        <div ng-controller="mobileDaySaleDlvrChart2Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileDaySaleDlvrChart2Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.saleDate"/>" binding="saleDate" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.shop"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileDaySaleDlvrBarChart2"
                        name="barChart2"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleDate">

                    <wj-flex-chart-series name="<s:message code="mobile.daySale.shop"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 배달 차트 --%>
        <div ng-controller="mobileDaySaleDlvrChart3Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileDaySaleDlvrChart3Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.saleDate"/>" binding="saleDate" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.dlvr"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileDaySaleDlvrBarChart3"
                        name="barChart3"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleDate">

                    <wj-flex-chart-series name="<s:message code="mobile.daySale.dlvr"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- 포장 차트 --%>
        <div ng-controller="mobileDaySaleDlvrChart4Ctrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileDaySaleDlvrChart4Grid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.saleDate"/>" binding="saleDate" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.daySale.pack"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileDaySaleDlvrBarChart4"
                        name="barChart4"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleDate">

                    <wj-flex-chart-series name="<s:message code="mobile.daySale.pack"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
    </div>
    <%-- //내점/배달/포장 --%>

    <%-- 일자별 매출현황 --%>
    <div class="gridBar mt10" id="mobileDaySaleDtl" onclick="girdFldUnfld('mobileDaySaleDtl')">
        <a href="#" class="open"><s:message code="mobile.daySale.saleDtl"/></a>
    </div>
    <div class="w100" id="mobileDaySaleDtlGrid" ng-controller="mobileDaySaleDtlCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;min-height:100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileDaySaleDtl"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    frozen-columns="1">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.daySale.saleDate"/>" binding="saleDate" width="70" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.totSaleAmt"/>" binding="totSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.totDcAmt"/>" binding="totDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.totRealSaleAmt"/>" binding="totRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 내점 --%>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.realSaleAmt"/>" binding="shopRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billCnt"/>" binding="shopBillCnt" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billUprc"/>" binding="shopBillUprc" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 배달 --%>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.realSaleAmt"/>" binding="dlvrRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billCnt"/>" binding="dlvrBillCnt" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billUprc"/>" binding="dlvrBillUprc" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 포장 --%>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.realSaleAmt"/>" binding="packRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billCnt"/>" binding="packBillCnt" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.billUprc"/>" binding="packBillUprc" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <wj-flex-grid-column header="<s:message code="mobile.daySale.totGuestCnt"/>" binding="totGuestCnt" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.guestUprc"/>" binding="guestUprc" width="55" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 결제수단 --%>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.pay.cardAmt"/>" binding="cardAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.pay.cashAmt"/>" binding="cashAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.pay.etcAmt"/>" binding="etcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <%-- 할인 --%>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.dc.dcAmt"/>" binding="dcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.dc.coupnDcAmt"/>" binding="coupnDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.daySale.dc.etcDcAmt"/>" binding="etcDcAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileDaySaleDtlMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //일자별 매출현황 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/daySale/mobileDaySale.js?ver=20210524.01" charset="utf-8"></script>