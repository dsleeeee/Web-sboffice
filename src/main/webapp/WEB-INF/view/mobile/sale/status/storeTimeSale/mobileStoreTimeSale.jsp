<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileStoreTimeSaleCtrl">

    <div class="searchBar">
        <%-- 요일별 --%>
        <a href="#" class="fl"><s:message code="mobile.storeTimeSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileStoreTimeSaleCtrl', 1)">
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
                </div>
            </td>
        </tr>
        <tr>
            <%-- 정렬방식 --%>
            <th><s:message code="mobile.storeTimeSale.array" /></th>
            <td>
                <span class="sb-radio"><input type="radio" id="arrayS" name="array" value="S" checked /><label for="arrayS">매장별</label></span>
                <span class="sb-radio"><input type="radio" id="arrayD" name="array" value="D" /><label for="arrayD">날짜별</label></span>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="mobile.cmm.search.store"/></th>
            <td>
                <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetId" value="mobileStoreTimeSaleStore"/>
                </jsp:include>
                <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
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

    <%-- 요일별 --%>
    <div class="gridBar mt10" id="mobileStoreTimeSale" onclick="girdFldUnfld('mobileStoreDayOfWeekSale')">
        <a href="#" class="open"><s:message code="mobile.storeTimeSale"/></a>
    </div>
    <div class="w100" id="mobileStoreTimeSaleGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:80px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileStoreTimeSale"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.storeTimeSale.storeCd"/>" binding="storeCd" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeTimeSale.storeNm"/>" binding="storeNm" width="1.*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeTimeSale.saleHour"/>"    binding="saleHour" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeTimeSale.billCnt"/>" binding="saleCnt" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeTimeSale.realSaleAmt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileStoreTimeSaleMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //결제수단 --%>

</div>
<script type="text/javascript">

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
<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/storeTimeSale/mobileStoreTimeSale.js?ver=20220427.02" charset="utf-8"></script>
