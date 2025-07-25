<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="dayTimeMrpizzaCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dayTimeMrpizza.dayTimeMrpizza"/></a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('dayTimeMrpizzaCtrl')">
                <s:message code="cmm.search"/>
            </button>
        </div>
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
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 옵션(기간별/일자별) --%>
            <th><s:message code="dayTimeMrpizza.optionFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="option"
                            ng-model="option"
                            items-source="_getComboData('option')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="optionCombo"
                            selected-index-changed="changeOption(s)">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 옵션(시간대/시간대분류) --%>
            <th><s:message code="dayTimeMrpizza.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
            <%-- 옵션에 따른 조회조건 --%>
            <th><s:message code="dayTimeMrpizza.time"/></th>
            <td>
                <div id="timeOption">
                    <div class="sb-select fl w200px">
                        <div class="sb-slect fl" style="width:65px;">
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
                        <div class="fl pd5" style="padding-right: 15px;">
                            <label> ~ </label>
                        </div>
                        <div class="sb-select fl" style="width:65px;">
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
                </div>
                <div id="timeSlotOption" style="display: none">
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
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장선택 --%>
                <th style="border-left:1px solid #ccc"><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dayTimeMrpizzaStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dayTimeMrpizzaStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                     id="wjGridList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.prodSaleQty"/>" binding="saleQty" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.rtnSaleCnt"/>" binding="rtnSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleCnt"/>" binding="realSaleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.billUprc"/>" binding="billUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>

                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleCnt"/>" binding="realSaleCnt${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.prodSaleQty"/>" binding="saleQty${i}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleAmt"/>" binding="realSaleAmt${i}" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.rate"/>" binding="rate${i}" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대분류 컬럼 생성--%>
               <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                   <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleCnt"/>" binding="realSaleCnt${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.prodSaleQty"/>" binding="saleQty${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.realSaleAmt"/>" binding="realSaleAmt${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="dayTimeMrpizza.rate"/>" binding="rate${timeSlotCol.value.replace("~","")}" width="80" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

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

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayTimeMrpizza/dayTimeMrpizza.js?ver=20250610.01" charset="utf-8"></script>