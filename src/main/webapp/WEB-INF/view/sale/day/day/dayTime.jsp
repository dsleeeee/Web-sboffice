<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayTime/"/>

<div id="dayTimeView" name="dayView" class="subCon" style="display: none;" ng-controller="dayTimeCtrl">
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
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchTimeStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchTimeEndDate" class="w110px"></span>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장코드 --%>
                <th><s:message code="day.time.store"/></th>
                <td colspan="3">
                        <%-- 매장선택 모듈 싱글 선택 사용시 include
                             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수
                        --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="dayTimeSelectStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dayTimeSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <tr>
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
        </tbody>
    </table>
    <div style="clear: both;"></div>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
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
<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayTime.js?ver=20190625" charset="utf-8"></script>
