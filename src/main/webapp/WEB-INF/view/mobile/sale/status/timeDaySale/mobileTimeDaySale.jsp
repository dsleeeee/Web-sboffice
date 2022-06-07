<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileTimeDaySaleCtrl">

    <div class="searchBar">
        <%-- 시간대별(일자별) --%>
        <a href="#" class="fl"><s:message code="mobile.timeDaySale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileTimeDaySaleCtrl', 1)">
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
                        <jsp:param name="targetId" value="mobileTimeDaySaleStore"/>
                    </jsp:include>
                    <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${multiStoreFg eq 0}">
            <input type="hidden" id="mobileTimeDaySaleStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
            <%-- 옵션 --%>
            <th><s:message code="month.time.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
        </tr>
        <tr id="timeOption">
            <%-- 시간대 --%>
            <th><s:message code="mobile.timeDaySale.saleTime"/></th>
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
        <tr id="timeSlotOption" style="display: none">
            <th><s:message code="day.time.time"/></th>
            <td colspan="3">
                <div class="sb-select fl w120px" >
                    <wj-combo-box
                            id="timeSlotCombo"
                            ng-model="timeSlot"
                            control="timeSlotCombo"
                            items-source="_getComboData('timeSlotCombo')"
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

    <%-- 일자-시간대별 --%>
    <div class="gridBar mt10" id="mobileTimeDaySaleDateTime" onclick="girdFldUnfld('mobileTimeDaySaleDateTime')">
        <a href="#" class="open"><s:message code="mobile.timeDaySale.dateTime"/></a>
    </div>
    <div class="w100" id="mobileTimeDaySaleDateTimeGrid" ng-controller="mobileTimeDaySaleDateTimeCtrl">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileTimeDaySaleDateTime"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjGridMobileTimeDaySaleDateTimeList"
                    frozen-columns="1">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.saleDate"/>" binding="saleDate" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="${i}<s:message code="mobile.timeDaySale.T"/>" binding="realSaleAmtT${i}" width="70" align="right" is-read-only="true" aggregate="Sum" visible="true"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대분류 컬럼 생성--%>
                <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                    <wj-flex-grid-column header="${timeSlotCol.name}(${timeSlotCol.value})" binding="realSaleAmtT${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </c:forEach>

                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileTimeDaySaleDateTimeMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //일자-시간대별 --%>

    <%-- 시간대별 --%>
    <div class="gridBar mt10" id="mobileTimeDaySaleTime" onclick="girdFldUnfld('mobileTimeDaySaleTime')">
        <a href="#" class="open"><s:message code="mobile.timeDaySale.time"/></a>
    </div>
    <div class="w100" id="mobileTimeDaySaleTimeGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileTimeDaySaleTime"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.saleTime"/>" binding="saleTime" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.amtCnt"/>" binding="amtCnt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.amtRate"/>" binding="amtRate" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileTimeDaySaleTimeMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
        <%-- 시간대별 차트 --%>
        <div ng-controller="mobileTimeDaySaleTimeChartCtrl">
            <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; display:none;">
                <wj-flex-grid
                        id="mobileTimeDaySaleTimeChartGrid"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <%--<!-- define columns -->--%>
                    <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.saleTime"/>" binding="saleTime" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.timeDaySale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <!-- 막대 차트 샘플 -->
            <div>
                <wj-flex-chart
                        id="mobileTimeDaySaleTimeBarChart"
                        name="barChart1"
                        <%--class="custom-flex-chart"--%>
                        style="width:100%; height:auto; font-size:10px;"
                        initialized="initChart(s,e)"
                        items-source="data"
                        chart-type="1" <%-- 세로차트형(Bar) --%>
                        binding-x="saleTime">

                    <wj-flex-chart-series name="<s:message code="mobile.timeDaySale.realSaleAmt"/>" binding="realSaleAmt"></wj-flex-chart-series>
                </wj-flex-chart>
            </div>
        </div>
        <%-- //시간대별 차트 --%>
    </div>
    <%-- //시간대별 --%>

</div>

<script type="text/javascript">
    var multiStoreFg = '${multiStoreFg}';

    // 시간대분류
    var timeSlotColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="timeSlotCol" items="${timeSlotColList}">
    var timeSlotParam   = {};
    timeSlotParam.name  = "${timeSlotCol.name}";
    timeSlotParam.value = "${timeSlotCol.value}";
    timeSlotColList.push(timeSlotParam);
    </c:forEach>

    var timeSlotCol    = '${timeSlotCol}';
    var arrTimeSlotCol = timeSlotCol.split(',');

</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/timeDaySale/mobileTimeDaySale.js?ver=20210609.03" charset="utf-8"></script>