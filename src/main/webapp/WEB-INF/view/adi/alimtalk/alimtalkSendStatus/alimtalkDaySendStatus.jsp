<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="alimtalkDaySendStatusView" class="subCon" style="display: none;">

    <div ng-controller="alimtalkDaySendStatusCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="alimtalkDaySendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('alimtalkDaySendStatusCtrl',1)" id="nxBtnSearch3">
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
                    <s:message code="alimtalkDaySendStatus.smsDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="alimtalkDaySendStatusStartDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="alimtalkDaySendStatusEndDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 소속코드 --%>
                <th><s:message code="daySendStatus.orgnCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="srchOrgnCd" onkeyup="fnNxBtnSearch('3');"/>
                </td>
                <%-- 소속명 --%>
                <th><s:message code="daySendStatus.orgnNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="srchOrgnNm" onkeyup="fnNxBtnSearch('3');"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="alimtalkDaySendStatusListScale"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsDate"/>" binding="smsDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsChargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsChargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkSmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkSmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkSmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkSmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkLmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkLmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkLmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkLmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkMmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkMmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkMmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkMmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="alimtalkDaySendStatusCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>

    <%-- 엑셀다운로드 그리드 --%>
    <div class="w100 mt10 mb20" style="display:none;" ng-controller="alimtalkDaySendStatusExcelCtrl">
        <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="alimtalkDaySendStatusExcelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsDate"/>" binding="smsDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsChargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.smsChargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkSmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkSmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkSmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkSmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkLmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkLmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkLmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkLmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.sendQty"/>" binding="alkMmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.waitQty"/>" binding="alkMmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.successQty"/>" binding="alkMmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="alimtalkDaySendStatus.failQty"/>" binding="alkMmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendStatus/alimtalkDaySendStatus.js?ver=20250617.01" charset="utf-8"></script>