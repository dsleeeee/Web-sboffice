<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileTimeMonthSaleCtrl">

    <div class="searchBar">
        <%-- 시간대별(월별) --%>
        <a href="#" class="fl"><s:message code="mobile.timeMonthSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileTimeMonthSaleCtrl', 1)">
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
            <%-- 조회월 --%>
            <th><s:message code="mobile.cmm.search.month"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startMonth" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endMonth" name="endDate" class="w110px" /></span>
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
                        <jsp:param name="targetId" value="mobileTimeMonthSaleStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileTimeMonthSaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 시간대 --%>
            <th><s:message code="mobile.timeMonthSale.saleTime"/></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w30">
                        <wj-combo-box
                                id="startTime"
                                ng-model="startTime"
                                items-source="_getComboData('startTimeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="startTimeCombo"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                    <div class="fl pd5">
                        <label>~</label>
                    </div>
                    <div class="sb-select fl w30">
                        <wj-combo-box
                                id="endTime"
                                ng-model="endTime"
                                items-source="_getComboData('endTimeCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="endTimeCombo"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 월-시간대별 --%>
    <div class="gridBar mt10" id="mobileTimeMonthSaleDateTime" onclick="girdFldUnfld('mobileTimeMonthSaleDateTime')">
        <a href="#" class="open"><s:message code="mobile.timeMonthSale.dateTime"/></a>
    </div>
    <div class="w100" id="mobileTimeMonthSaleDateTimeGrid" ng-controller="mobileTimeMonthSaleDateTimeCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileTimeMonthSaleDateTime"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjGridMobileTimeMonthSaleDateTimeList"
                    frozen-columns="1">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.saleYm"/>" binding="saleYm" width="55" align="center" is-read-only="true"></wj-flex-grid-column>
                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="${i}<s:message code="mobile.timeMonthSale.T"/>" binding="realSaleAmtT${i}" width="70" align="right" is-read-only="true" aggregate="Sum" visible="true"></wj-flex-grid-column>
                </c:forEach>

                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileTimeMonthSaleDateTimeMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //월-시간대별 --%>

    <%-- 시간대별 --%>
    <div class="gridBar mt10" id="mobileTimeMonthSaleTime" onclick="girdFldUnfld('mobileTimeMonthSaleTime')">
        <a href="#" class="open"><s:message code="mobile.timeMonthSale.time"/></a>
    </div>
    <div class="w100" id="mobileTimeMonthSaleTimeGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileTimeMonthSaleTime"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.saleTime"/>" binding="saleTime" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.amtCnt"/>" binding="amtCnt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileTimeMonthSaleTimeMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
        <%-- 시간대별 차트 --%>
        <div ng-controller="mobileTimeMonthSaleTimeChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileTimeMonthSaleTimeChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.saleTime"/>" binding="saleTime" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.timeMonthSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileTimeMonthSaleTimeBarChart"
                        name="barChart1"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleTime">

                    <wj-flex-chart-series name="<s:message code="mobile.timeMonthSale.realSaleAmt"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- //시간대별 차트 --%>
    </div>
    <%-- //시간대별 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/timeMonthSale/mobileTimeMonthSale.js?ver=20210609.02" charset="utf-8"></script>