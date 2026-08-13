<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    System.out.println("WEB_SMS >>> 결제상세 >>> request.getRequestURL() : " + request.getRequestURL());

    String pay_site_url = "";
    if(request.getRequestURL().indexOf("://192") > 0 || request.getRequestURL().indexOf("://localhost") > 0) {
        pay_site_url = "https://testadmin8.kcp.co.kr"; // 테스트(로컬)
    }
    else if(request.getRequestURL().indexOf("://neo.solbipos.com") > 0) {
        pay_site_url = "http://admin8.kcp.co.kr"; // 운영
    }
%>

<div id="smsChargeStatusView">
    <div class="subCon" ng-controller="smsChargeStatusCtrl" ng-init="init()">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="smsUserStatus.smsChargeStatus"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch3" ng-click="_pageView('smsChargeStatusCtrl',1)">
                    <s:message code="cmm.search"/>
                </button>
            </div>
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
                <%-- 충전일자 --%>
                <th>
                    <s:message code="smsUserStatus.chargeDate" />
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="startDate3" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="endDate3" name="endDate" class="w110px" /></span>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
            <tr>
                <%-- 소속코드 --%>
                <th><s:message code="smsUserStatus.orgnCd"/></th>
                <td>
                    <input type="text" id="orgnCd3" ng-model="orgnCd" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/>
                </td>
                <%-- 소속명 --%>
                <th><s:message code="smsUserStatus.orgnNm"/></th>
                <td>
                    <input type="text" id="orgnNm3" ng-model="orgnNm" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/>
                </td>
            </tr>
            <tr>
                <%-- 사용자ID --%>
                <th><s:message code="smsUserStatus.userId"/></th>
                <td>
                    <input type="text" id="userId3" ng-model="userId" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/>
                </td>
                <%-- 사용자명 --%>
                <th><s:message code="smsUserStatus.userNm"/></th>
                <td>
                    <input type="text" id="userNm3" ng-model="userNm" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/>
                </td>
            </tr>
            <tr style="display: none;">
                <%--SMS사용등록 구분--%>
                <th><s:message code="smsUserStatus.fg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="smsUseRegFg3"
                                ng-model="smsUseRegFg"
                                control="smsUseRegFgCombo"
                                items-source="_getComboData('smsUseRegFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                selected-index="1">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        </div>

        <%-- 충전내역 목록 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="smsUserGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        is-read-only="false"
                        item-formatter="_itemFormatter">
                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.orgn"/>" binding="orgn" width="300" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.chargeDate"/>" binding="chargeDate" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.chargeTime"/>" binding="chargeTime" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.pgresource"/>" binding="pgresource" data-map="pgresourceDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.controlno"/>" binding="controlno" width="100" is-read-only="true" align="center" format="d"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.baseChargeAmt"/>" binding="baseChargeAmt" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.chargeAmt"/>" binding="chargeAmt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.vatAmt"/>" binding="vatAmt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.chargeTot"/>" binding="chargeTot" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.successYn"/>" binding="successYn" data-map="successYnDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsUserStatus.resultmessage"/>" binding="resultmessage" width="200" align="left" is-read-only="true"></wj-flex-grid-column>

                    <%--조회시 필요--%>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.chargeIdNm"/>" binding="chargeIdNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.orgnCd"/>" binding="orgnCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.orgnNm"/>" binding="orgnNm" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="smsChargeHist.approvalnum"/>" binding="approvalnum" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var siteUrl = '<%=pay_site_url%>'; // 결제상세 URL
</script>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsUserStatus/smsChargeStatus.js?ver=20260807.01" charset="utf-8"></script>

<%-- SMS임의충전 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/smsChargeRegist.jsp">
</c:import>

<%-- SMS결제상세 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/smsChargeDtl.jsp">
</c:import>

<%-- 비고(결제메시지) 팝업 --%>
<c:import url="/WEB-INF/view/adi/sms/smsChargeHist/resultmessageEdit.jsp">
</c:import>