<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon" ng-controller="resveInfoCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('resveInfoCtrl',1)">
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
            <%-- 등록 일자 --%>
            <tr>
                <th><s:message code="resveInfo.regDate" /></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                id="srchStartDate"
                                value="startDate"
                                ng-model="startDate"
                                control="startDateCombo"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn w110px">
                            <wj-input-date
                                id="srchEndDate"
                                value="endDate"
                                ng-model="endDate"
                                control="endDateCombo"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <%--전체기간--%>
                        <span class="chk ml10">
                            <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                            <label for="chkDt"><s:message code="cmm.all.day" /></label>
                        </span>
                    </div>
                </td>
            </tr>
            <%-- 예약 일자 --%>
            <tr>
                <th><s:message code="resveInfo.resveDate" /></th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                    id="resveStartDate"
                                    value="resveStartDate"
                                    ng-model="resveStartDate"
                                    control="resveStartDateCombo"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn w110px">
                            <wj-input-date
                                    id="resveEndDate"
                                    value="resveEndDate"
                                    ng-model="resveEndDate"
                                    control="resveEndDateCombo"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <%--전체기간--%>
                        <span class="chk ml10">
                            <input type="checkbox" id="chkResveDt" ng-model="isResveChecked" ng-change="isResveChkDt()" />
                            <label for="chkDt"><s:message code="cmm.all.day" /></label>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 예약번호 --%>
                <th><s:message code="resveInfo.resveNo" /></th>
                <td><input type="text"  class="sb-input w100" id="resveNo" ng-model="resveNo" /></td>
                <%-- 예약자명 --%>
                <th><s:message code="resveInfo.resveGuestNm" /></th>
                <td><input type="text"  class="sb-input w100" id="resveGuestNm" ng-model="resveGuestNm" /></td>
            </tr>
            <%-- 매장선택 --%>
            <c:if test="${orgnFg == 'HQ'}">
                <tr>
                    <th><s:message code="cmm.store.select" /></th>
                    <td>
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                            <jsp:param name="targetId" value="resveInfoStore"/>
                        </jsp:include>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
            </c:if>
        </tbody>
    </table>

    <div class="mt20 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScaleBox"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                <!-- define columns -->
                <c:if test="${orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="resveInfo.storeCd"/>" binding="storeCd" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="resveInfo.storeNm"/>" binding="storeNm" width="100" align="left"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="resveInfo.regDate"/>" binding="regDt" width="130" align="center" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveInFg"/>" binding="resveInFg" width="70" align="center" data-map="inFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.posNo"/>" binding="posNo" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveNo"/>" binding="resveNo" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveDate"/>" binding="resveDate" width="80" align="center" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveTime"/>" binding="resveTime" width="70" align="center" format="time"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveGuestNm"/>" binding="resveGuestNm" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveGuestTelNo"/>" binding="resveGuestTelNo" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveGuestCnt"/>" binding="resveGuestCnt" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="resveInfo.resveMemo"/>" binding="resveMemo" width="100"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="resveInfoCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/resve/resveInfo/resveInfo.js?ver=20210520.01" charset="utf-8"></script>
