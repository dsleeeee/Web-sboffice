<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="storeSaleKmuCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('storeSaleKmuCtrl', 1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
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
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <%-- 팀코드 --%>
            <th><s:message code="storeSaleKmu.teamCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchTeamCd" ng-model="teamCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 팀명 --%>
            <th><s:message code="storeSaleKmu.teamNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchTeamNm" ng-model="teamNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 지점코드 --%>
            <th><s:message code="storeSaleKmu.branchCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBranchCd" ng-model="branchCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 지점명 --%>
            <th><s:message code="storeSaleKmu.branchNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBranchNm" ng-model="branchNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="storeSaleKmuStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                    <input type="hidden" id="storeSaleKmuStoreCdNm"/>
                </td>
            </c:if>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                <input type="hidden" id="storeSaleKmuStoreCd" value="${sessionInfo.storeCd}"/>
                <td></td>
                <td></td>
            </c:if>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 출력 --%>
        <button class="btn_skyblue ml5 fr" ng-click="report()"><s:message code="cmm.report" /></button>
        <%-- 현재화면 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.teamCd"/>" binding="teamCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.teamNm"/>" binding="teamNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.branchCd"/>" binding="branchCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.branchNm"/>" binding="branchNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" binding="storeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.totSaleAmt"/>" binding="totSaleAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.totDcAmt"/>" binding="totDcAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.realSaleAmt"/>" binding="realSaleAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.totEtcAmt"/>" binding="totEtcAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.postpaidAmt"/>" binding="postpaidAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.giftAmt"/>" binding="giftAmt" width="120" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.cardAmt"/>" binding="cardAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeSaleKmu.cashAmt"/>" binding="cashAmt" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/storeSaleKmu/storeSaleKmu.js?ver=20251001.01" charset="utf-8"></script>

<%-- 점소별매출일보 출력 레이어 --%>
<c:import url="/WEB-INF/view/sale/status/storeSaleKmu/storeSaleKmuReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>