<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="alimtalkPeriodSendStatusView" class="subCon" style="display: none;">
    <div ng-controller="alimtalkPeriodSendStatusCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="alimtalkPeriodSendStatus.info"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('alimtalkPeriodSendStatusCtrl',1)">
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
                    <s:message code="alimtalkPeriodSendStatus.smsDate"/>
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"><input id="alimtalkPeriodSendStatusStartDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="alimtalkPeriodSendStatusEndDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <c:if test="${orgnFg != 'HQ'}">
                <tr>
                    <%-- 본사코드 --%>
                    <th>
                        <s:message code="alimtalkPeriodSendStatus.hqOfficeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                    </td>
                    <%-- 본사명 --%>
                    <th>
                        <s:message code="alimtalkPeriodSendStatus.hqOfficeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                    </td>
                </tr>
            </c:if>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="alimtalkPeriodSendStatus.storeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="alimtalkPeriodSendStatus.storeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                </td>
            </tr>
            </tbody>
        </table>

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
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.orgnCd"/>" binding="orgnCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.smsChargeAmt"/>" binding="smsChargeAmt" width="80" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.smsChargeCnt"/>" binding="smsChargeCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.sendQty"/>" binding="alkSendQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.waitQty"/>" binding="alkWaitQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.successQty"/>" binding="alkSuccessQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.failQty"/>" binding="alkFailQty" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="alimtalkPeriodSendStatus.saleAmt"/>" binding="alkSaleAmt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/alimtalk/alimtalkSendStatus/alimtalkPeriodSendStatus.js?ver=20220401.01" charset="utf-8"></script>