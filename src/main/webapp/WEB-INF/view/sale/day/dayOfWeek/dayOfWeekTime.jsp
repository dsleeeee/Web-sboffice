<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/sale/day/dayOfWeek/dayOfWeekTime/"/>

<div id="dayOfWeekTimeView" name="dayOfWeekView" class="subCon" style="display: none;" ng-controller="dayOfWeekTimeCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="dayofweek.timeSale" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('dayOfWeekTimeCtrl',1)">
                <s:message code="cmm.search" />
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
        </colgroup>
        <tbody>
            <tr>
                <%-- 조회일자 --%>
                <th>
                    <s:message code="dayofweek.date" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDateDayOfWeekTime" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDateDayOfWeekTime" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                <%-- 매장코드 --%>
                <th><s:message code="dayofweek.store"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                    <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="dayofweekTimeStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <th><s:message code="dayofweek.srchTime"/></th>
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

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="wjGridDayofweekTimeList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayofweek.yoil"/>" binding="yoil" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.storeCnt"/>" binding="storeCnt" width="80" is-read-only="true" align="center" ng-if="orgnFg == 'H'"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.realSaleAmt"/>" binding="realSaleAmt" width="80" is-read-only="true" align="right" aggregate="Sum" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.saleCnt"/>" binding="saleCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayofweek.totGuestCnt"/>" binding="totGuestCnt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    <%-- 시간대 컬럼 생성--%>
                    <c:forEach var="i" begin="0" end="23">
                        <wj-flex-grid-column header="<s:message code="dayofweek.time.realSaleAmt"/>" binding="realSaleAmtT${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayofweek.time.saleCnt"/>" binding="saleCntT${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dayofweek.time.totGuestCnt"/>" binding="totGuestCntT${i}" width="80" align="right" is-read-only="true" aggregate="Sum" visible="false"></wj-flex-grid-column>
                    </c:forEach>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="dayOfWeekTimeCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/dayOfWeek/dayOfWeekTime.js?ver=20200117.03" charset="utf-8"></script>