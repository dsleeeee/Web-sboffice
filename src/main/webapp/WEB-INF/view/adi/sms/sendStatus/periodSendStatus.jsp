<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="periodSendStatusView" class="subCon" style="display: none;">
    <div ng-controller="periodSendStatusCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="periodSendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('periodSendStatusCtrl',1)">
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
                <%-- 전송일자 --%>
                <th>
                    <s:message code="periodSendStatus.smsDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="periodSendStatusStartDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="periodSendStatusEndDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <c:if test="${orgnFg != 'HQ'}">
            <tr>
                <%-- 본사코드 --%>
                <th><s:message code="periodSendStatus.hqOfficeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 본사명 --%>
                <th><s:message code="periodSendStatus.hqOfficeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            </c:if>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="periodSendStatus.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
                </td>
                <%-- 매장명 --%>
                <th><s:message code="periodSendStatus.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="periodSendStatusListScale"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
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
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.orgnCd"/>" binding="ognCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.smsChargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.smsChargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.sendQty"/>" binding="totSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.waitQty"/>" binding="totWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.successQty"/>" binding="totSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.failQty"/>" binding="totFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.sendQty"/>" binding="smsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.waitQty"/>" binding="smsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.successQty"/>" binding="smsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.failQty"/>" binding="smsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.sendQty"/>" binding="lmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.waitQty"/>" binding="lmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.successQty"/>" binding="lmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.failQty"/>" binding="lmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.sendQty"/>" binding="mmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.waitQty"/>" binding="mmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.successQty"/>" binding="mmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="periodSendStatus.failQty"/>" binding="mmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/sendStatus/periodSendStatus.js?ver=20211021.011" charset="utf-8"></script>