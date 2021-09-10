<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="mobileStoreProdSaleCtrl">

    <div class="searchBar">
        <%-- 일별매출 --%>
        <a href="#" class="fl"><s:message code="mobile.storeProdSale"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileStoreProdSaleCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="mobile.cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="startDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="endDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="mobile.cmm.search.store"/></th>
            <td>
                <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetId" value="mobileProdSaleStore"/>
                </jsp:include>
                <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 상품별 매출현황 --%>
    <div class="gridBar mt10" id="mobileStoreProdSaleDtl" onclick="girdFldUnfld('mobileStoreProdSaleDtl')">
        <a href="#" class="open"><s:message code="mobile.storeProdSale.saleDtl"/></a>
    </div>
    <div class="w100" ng-controller="mobileStoreProdSaleDtlCtrl">
<%--         id="mobileStoreProdSaleDtlGrid" ng-controller="mobileStoreProdSaleDtlCtrl">--%>
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;min-height:100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileStoreProdSaleDtl"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true"
                    id="mobileStoreProdSaleDtlGrid">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.prodCd"/>"       binding="prodCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.prodNm"/>"       binding="prodNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.store"/>"        binding="store" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.totSaleAmt"/>"   binding="totSaleQty" width="70" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.realSaleAmt"/>"  binding="realSaleAmt" width="70" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.storeProdSale.realSalePer"/>"  binding="realSalePer" width="70" align="right" is-read-only="true" aggregate="Sum" word-wrap="true" multi-line="true"></wj-flex-grid-column>
  <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileStoreProdSaleDtlMsg" style="line-height: 150px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>
    <%-- //일자별 매출현황 --%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/storeProdSale/mobileStoreProdSale.js?ver=20210907.02" charset="utf-8"></script>