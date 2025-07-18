<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="remainAmtChkCtrl">

    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button id="nxBtnSearch" class="btn_blue fr"  ng-click="_pageView('remainAmtChkCtrl',1)">
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
                <s:message code="remainAmtChk.regDt"/>
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="remainAmtChkStartDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="remainAmtChkEndDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 소속코드 --%>
            <th><s:message code="remainAmtChk.orgnCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchOrgnCd" ng-model="srchOrgnCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 소속명 --%>
            <th><s:message code="remainAmtChk.orgnNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchOrgnNm" ng-model="srchOrgnNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <p class="tl s14 mt5 lh15 fl">예약 전송 건이 있을 시 금액이 상이할 수 있습니다. 전송일자 범위를 늘려서 조회해주세요.<br/>
            ex) 예약일자 - 16일 전송일자 - 17일</p>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div id="grid" class="w100">
        <div class="wj-gridWrap mt10" style="height:450px; overflow-y: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="remainAmtChkGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.preRemainAmt"/>" binding="preRemainAmt" width="90" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.chargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.chargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="totSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="totWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="totSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="totFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="totSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="smsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="smsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="smsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="smsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="smsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="lmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="lmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="lmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="lmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="lmsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="mmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="mmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="mmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="mmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="mmsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="alkSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="alkWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="alkSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="alkFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="alkSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="alkSmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="alkSmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="alkSmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="alkSmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="alkSmsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="alkLmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="alkLmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="alkLmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="alkLmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="alkLmsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.sendQty"/>" binding="alkMmsSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.waitQty"/>" binding="alkMmsWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.successQty"/>" binding="alkMmsSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.failQty"/>" binding="alkMmsFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.saleAmt"/>" binding="alkMmsSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.calcRemainAmt"/>" binding="calcRemainAmt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.remainAmt"/>" binding="remainAmt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.calcCurrRemainAmt"/>" binding="calcCurrRemainAmt" width="110" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.currRemainAmt"/>" binding="currRemainAmt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.remainAmtFg"/>" binding="remainAmtFg" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="remainAmtChk.currRemainAmtFg"/>" binding="currRemainAmtFg" width="100" is-read-only="true" align="center"></wj-flex-grid-column>


                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
<%--                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">--%>
<%--                    <jsp:param name="pickerTarget" value="remainAmtChkCtrl"/>--%>
<%--                </jsp:include>--%>
                <%--// ColumnPicker 사용시 include --%>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/remainAmtChk/remainAmtChk.js?ver=20250610.01" charset="utf-8"></script>

<%-- 충전/사용내역 레이어 --%>
<c:import url="/WEB-INF/view/adi/sms/remainAmtChk/remainAmtHist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>