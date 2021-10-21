<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="smsChargeHistView" class="subCon" style="display: none;">
    <div ng-controller="smsChargeHistCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="smsChargeHist.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('smsChargeHistCtrl',1)">
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
                <%-- 충전일자 --%>
                <th>
                    <s:message code="smsChargeHist.chargeDate"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <%-- 결제수단 --%>
                <th>
                    <s:message code="smsChargeHist.pgresource" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchPgresourceCombo"
                                ng-model="pgresource"
                                items-source="_getComboData('pgresourceCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 성공여부 --%>
                <th>
                    <s:message code="smsChargeHist.successYn" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchSuccessYnCombo"
                                ng-model="successYn"
                                items-source="_getComboData('successYnCombo')"
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

        <div class="mt10 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBox"
                    ng-model="listScale"
                    items-source="_getComboData('listScaleBox')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
            <%--<c:if test="${orgnFg eq 'MASTER'}">--%>
                <%-- SMS임의충전 --%>
                <button class="btn_skyblue ml5 fr" id="btnSmsChargeRegist" ng-click="smsChargeRegist()"><s:message code="smsChargeHist.smsChargeRegist" /></button>
            <%--</c:if>--%>
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
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.orgn"/>" binding="orgn" width="300" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeDate"/>" binding="chargeDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeTime"/>" binding="chargeTime" width="70" is-read-only="true" align="center" format="time"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.pgresource"/>" binding="pgresource" data-map="pgresourceDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.controlno"/>" binding="controlno" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.baseChargeAmt"/>" binding="baseChargeAmt" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeAmt"/>" binding="chargeAmt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.successYn"/>" binding="successYn" data-map="successYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>

                    <%--조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeIdNm"/>" binding="chargeIdNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.resultmessage"/>" binding="resultmessage" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.orgnNm"/>" binding="orgnNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.approvalnum"/>" binding="approvalnum" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="smsChargeHistCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsChargeHist/smsChargeHist.js?ver=20211021.01" charset="utf-8"></script>

<%-- SMS임의충전 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/smsChargeRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- SMS결제상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/smsChargeDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>