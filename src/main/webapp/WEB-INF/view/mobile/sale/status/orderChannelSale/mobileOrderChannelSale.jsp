<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileOrderChannelSaleCtrl">

    <div class="searchBar">
        <%-- 일별매출현황 --%>
        <a href="#" class="fl"><s:message code="mobile.orderChannelSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileOrderChannelSaleCtrl', 1)">
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
                        <jsp:param name="targetId" value="mobileOrderChannelSaleStore"/>
                    </jsp:include>
                        <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileOrderChannelSaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 주문채널별 --%>
    <div class="gridBar mt10" id="mobileOrderChannelPay" onclick="girdFldUnfld('mobileOrderChannelPay')">
        <a href="#" class="open"><s:message code="mobile.orderChannelSale.orderChannel"/></a>
    </div>
    <div class="w100" id="mobileOrderChannelPayGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileOrderChannelPay"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.dlvrInFg"/>" binding="dlvrInFgNm" width="2.*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.amtRate"/>" binding="realSalePer" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileOrderChannelPayMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
        <%-- 결제수단 차트 --%>
        <div ng-controller="mobileOrderChannelPayChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileOrderChannelPayChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.dlvrInFg"/>" binding="dlvrInFgNm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 -->
            <div id="divOrderChannelPayBarChart" style="display:none;">
                <wj-flex-chart
                        id="mobileOrderChannelPayBarChart"
                        name="barChart1"
                <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="dlvrInFgNm">

                    <wj-flex-chart-series name="<s:message code="mobile.orderChannelSale.realSaleAmt"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
    </div>
    <%-- //결제수단 --%>

    <%-- 일자별 매출현황 --%>
    <div class="gridBar mt10" id="mobileOrderChannelSaleDtl" onclick="girdFldUnfld('mobileOrderChannelSaleDtl')">
        <a href="#" class="open"><s:message code="mobile.orderChannelSale.saleDtl"/></a>
    </div>
    <div class="w100" id="mobileOrderChannelSaleDtlGrid" ng-controller="mobileOrderChannelSaleDtlCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;min-height:100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileOrderChannelSaleDtl"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    frozen-columns="1">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.saleDate"/>" binding="saleDate" width="70" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.totBillCnt"/>" binding="totBillCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.totRealSaleAmt"/>" binding="totRealSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <%-- 주문채널 구분자 컬럼 생성--%>
                <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
                    <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.billCnt"/>" binding="billCnt${dlvrInFgCol.dlvrInFg}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.orderChannelSale.realSaleAmt"/>" binding="realSaleAmt${dlvrInFgCol.dlvrInFg}" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                </c:forEach>

                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileOrderChannelSaleDtlMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //일자별 매출현황 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
    var dlvrInFgColList = [];

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
    var param = {};
    param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
    param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
    dlvrInFgColList.push(param);
    </c:forEach>

    var dlvrInFgCol = '${dlvrInFgCol}';
    var arrDlvrInFgCol = dlvrInFgCol.split(',');

</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/orderChannelSale/mobileOrderChannelSale.js?ver=20210903.01" charset="utf-8"></script>