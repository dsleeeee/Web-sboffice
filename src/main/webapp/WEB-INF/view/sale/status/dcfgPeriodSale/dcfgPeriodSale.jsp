<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}" />
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}" />
<c:set var="baseUrl" value="/sale/satus/dc/" />

<div id="dcfgPeriodSaleView" class="subCon" ng-controller="dcfgPeriodSaleCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="dcfgPeriodSale.dcfgPeriodSale" /></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('dcfgPeriodSaleCtrl')">
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
            <th><s:message code="dcfgPeriodSale.srchOption" /></th>
            <td>
		        <span class="chk ml10">
                    <input type="checkbox" ng-model="ChkProdClassDisplay" ng-change="isChkProdClassDisplay()" />
                    <label>
                        <s:message code="dcfgPeriodSale.prodClassDisplay" />
                    </label>
                </span>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="cmm.storeCd" /></th>
            <td colspan="3">
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="dcfgPeriodSaleStore" />
                </jsp:include>
            </td>
        </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="dcfgPeriodSaleStoreCd" value="${sessionInfo.storeCd}" />
        </c:if>
        <tr>
            <%-- 할인유형 --%>
            <th><s:message code="dcfgPeriodSale.dcfg" /></th>
            <td colspan="3">
                <jsp:include page="/WEB-INF/view/sale/status/dc/cmm/selectDcfgM.jsp" flush="true">
                    <jsp:param name="targetId" value="dcfgPeriodSaleDcfg" />
                    <jsp:param name="targetStoreId" value="dcfgPeriodSaleStore" />
                </jsp:include>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    control="flex"
                    id="wjGridDcfgPeriodSale"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="" binding="seq" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.prodClassLNm"/>" binding="lv1Nm" width="150" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.prodClassMNm"/>" binding="lv2Nm" width="150" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.prodClassSNm"/>" binding="lv3Nm" width="150" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.dcfg"/>" binding="dcNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.dcfg"/>" binding="dcdtlDcNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.saleQty"/>" binding="saleQty" width="60" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.totSaleAmt"/>" binding="saleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.dcAmt"/>" binding="dcAmt" width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.totDcAmt"/>" binding="totDcAmt" width="80"  align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="" visible="false"></wj-flex-grid-column><%-- row merge시 인접cell과 내용이 같으면 셀이 합쳐지므로, 셀 합침 방지를 위해 추가--%>
                <wj-flex-grid-column header="<s:message code="dcfgPeriodSale.realSaleAmt"/>" binding="realSaleAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>

        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>


<script type="text/javascript" src="/resource/solbipos/js/sale/status/dcfgPeriodSale/dcfgPeriodSale.js?ver=20220617.01" charset="utf-8"></script>

<%-- 소계, 합계 Title 정렬 CSS --%>
<style>
    .itemAlignment {
        text-align:left !important;
    }
</style>