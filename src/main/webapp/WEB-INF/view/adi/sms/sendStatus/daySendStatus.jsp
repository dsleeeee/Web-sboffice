<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="daySendStatusView" class="subCon" style="display: none;">
    <div ng-controller="daySendStatusCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="daySendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('daySendStatusCtrl',1)">
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
                    <s:message code="daySendStatus.smsDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="daySendStatusStartDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="daySendStatusEndDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="daySendStatusListScale"
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
                    <wj-flex-grid-column header="<s:message code="daySendStatus.orgnCd"/>" binding="ognCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.smsDate"/>" binding="smsDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.smsChargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.smsChargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.sendQty"/>" binding="totSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.waitQty"/>" binding="totWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.successQty"/>" binding="totSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.failQty"/>" binding="totFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.sendQty"/>" binding="smsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.waitQty"/>" binding="smsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.successQty"/>" binding="smsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.failQty"/>" binding="smsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.sendQty"/>" binding="lmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.waitQty"/>" binding="lmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.successQty"/>" binding="lmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.failQty"/>" binding="lmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.sendQty"/>" binding="mmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.waitQty"/>" binding="mmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.successQty"/>" binding="mmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="daySendStatus.failQty"/>" binding="mmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="daySendStatusCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/sendStatus/daySendStatus.js?ver=20211021.011" charset="utf-8"></script>