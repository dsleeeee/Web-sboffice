<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayTime/"/>

<div id="dayTimeView" name="dayView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="dayTimeCtrl">
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="day.time"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dayTimeCtrl')">
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 옵션 --%>
            <th><s:message code="day.time.optionFg"/></th>
            <td>
                <span class="sb-radio"><input type="radio" id="optionFgTime" name="optionFg" value="time" checked /><label for="time">시간대</label></span>
                <span class="sb-radio"><input type="radio" id="optionFgTimeSlot" name="optionFg" value="timeSlot" /><label for="timeSlot">시간대분류</label></span>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dayTimeSelectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dayTimeSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr id="timeOption">
            <th><s:message code="day.time.time"/></th>
            <td colspan="3">
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
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 420px; overflow-y: hidden; overflow-x: hidden;">
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
                <wj-flex-grid-column header="<s:message code="day.time.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.time.yoil"/>" binding="yoil" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.time.storeCnt"/>" binding="storeCnt" width="80" align="center" is-read-only="true" ng-if="orgnFg == 'H'"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.time.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.time.saleCnt"/>" binding="saleCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="day.time.totGuestCnt"/>" binding="totGuestCnt" width="100" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

                <%-- 시간대 컬럼 생성--%>
                <c:forEach var="i" begin="0" end="23">
                    <wj-flex-grid-column header="<s:message code="day.time.realSaleAmt"/>" binding="realSaleAmtT${i}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.time.saleCnt"/>" binding="saleCntT${i}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.time.totGuestCnt"/>" binding="totGuestCntT${i}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </c:forEach>

                <%-- 시간대분류류 컬럼 생성--%>
               <c:forEach var="timeSlotCol" items="${timeSlotColList}">
                   <wj-flex-grid-column header="<s:message code="day.time.realSaleAmt"/>" binding="realSaleAmtT${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="day.time.saleCnt"/>" binding="saleCntT${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                   <wj-flex-grid-column header="<s:message code="day.time.totGuestCnt"/>" binding="totGuestCntT${timeSlotCol.value.replace("~","")}" width="100" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                </c:forEach>
            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="dayTimeCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript">

</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayTime.js?ver=20240605.01" charset="utf-8"></script>
