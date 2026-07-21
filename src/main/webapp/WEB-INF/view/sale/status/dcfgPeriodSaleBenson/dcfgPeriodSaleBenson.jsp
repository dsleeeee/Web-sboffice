<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="baseUrl" value="/sale/satus/dc/" />

<div id="dcfgPeriodSaleBensonView" class="subCon" ng-controller="dcfgPeriodSaleBensonCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dcfgPeriodSaleBenson.dcfgPeriodSaleBenson" /></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dcfgPeriodSaleBensonCtrl')">
            <s:message code="cmm.search" />
        </button>
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
            <th><s:message code="cmm.search.date" /></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <%-- 조회옵션 --%>
            <th><s:message code="dcfgPeriodSaleBenson.srchOption" /></th>
            <td>
		        <span class="chk ml10">
                    <input type="checkbox" ng-model="ChkProdClassDisplay" ng-init="ChkProdClassDisplay = true" ng-change="isChkProdClassDisplay()" />
                    <label>
                        <s:message code="dcfgPeriodSaleBenson.prodClassDisplay" />
                    </label>
                </span>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                    <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                        <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dcfgPeriodSaleBensonStore" />
                        <jsp:param name="closeFunc" value="resetDcfg"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dcfgPeriodSaleBensonStoreCd" value="${sessionInfo.storeCd}" />
        </c:if>
        </tbody>
    </table>

    <div class="mt10 oh">
        <p class="tl s14 mt5 lh15 red">※ 중복할인이 적용된 경우, 할인금액을 제외한 수량 및 금액 항목이 2줄로 표시될 수 있습니다.</p>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    control="flex"
                    id="wjGridDcfgPeriodSaleBenson"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="seq" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.dcfgCd"/>" binding="dcNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.dcfg"/>" binding="dcdtlDcNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassLCd"/>" binding="lv1Cd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassLNm"/>" binding="lv1Nm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassMCd"/>" binding="lv2Cd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassMNm"/>" binding="lv2Nm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassSCd"/>" binding="lv3Cd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodClassSNm"/>" binding="lv3Nm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodCd"/>" binding="prodCd" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.saleQty"/>" binding="saleQty" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.totSaleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.dcAmt"/>" binding="dcAmt" width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.totDcAmt"/>" binding="totDcAmt" width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSaleBenson.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>

        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dcfgPeriodSaleBenson/dcfgPeriodSaleBenson.js?ver=20260721.01" charset="utf-8"></script>

<%-- 소계, 합계 Title 정렬 CSS --%>
<style>
    .itemAlignment {
        text-align:left !important;
    }
</style>
