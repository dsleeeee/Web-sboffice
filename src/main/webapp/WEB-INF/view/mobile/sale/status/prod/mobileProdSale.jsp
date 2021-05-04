<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="mobileProdSaleView" class="subCon">
    <div ng-controller="mobileProdSaleCtrl">
        <div class="searchBar">
            <a href="#" class="fl"><s:message code="mobile.ProdSale.prodSale"/></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_pageView('mobileProdSaleCtrl')">
                <s:message code="cmm.search"/>
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
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
            </tr>
            <c:if test="${multiStoreFg ne 0}">
                <tr>
                        <%-- (다중)매장코드 --%>
                    <th><s:message code="mobile.ProdSale.store"/></th>
                    <td>
                            <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectMultiStore.jsp" flush="true">
                            <jsp:param name="targetId" value="mobileProdSaleStore"/>
                        </jsp:include>
                            <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
            </c:if>
            <c:if test="${multiStoreFg eq 0}">
                <input type="hidden" id="mobileProdSaleStoreCd" value="${sessionInfo.storeCd}"/>
            </c:if>
            </tbody>
        </table>

        <!-- 당일매출 Best3 -->
        <div class="gridBar mt10" id="todayBest3" onclick="girdFldUnfld('todayBest3')">
            <a href="#" class="open"><s:message code="mobile.ProdSale.todaySaleBest3"/></a>
        </div>
        <div class="w100" id="todayBest3Grid" ng-controller="todayBest3Ctrl">
            <div class="wj-gridWrap" style="height:100px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        control="flexTodayBest3"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.menu"/>" binding="prodNm" width="2.*" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.cnt"/>" binding="totSaleQty" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.amt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.per"/>" binding="realSalePer" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <!-- 조회 결과가 없을 때, msg 띄우기 -->
                    <div class="gridMsg" id="todayBest3Msg" style="line-height: 100px; display: none;"><s:message code="cmm.search.result.empty"/></div>
                </wj-flex-grid>
            </div>
        </div>

        <!-- 상품별매출현황 -->
        <div class="gridBar mt10" id="prodSale" onclick="girdFldUnfld('prodSale')">
            <a href="#" class="open"><s:message code="mobile.ProdSale.prodSale"/></a>
        </div>
        <div class="w100" id="prodSaleGrid">
            <div class="wj-gridWrap" style="height:300px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        control="flexProdSale"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
                        is-read-only="false"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.menu"/>" binding="prodNm" width="2.*" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.cnt"/>" binding="totSaleQty" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.amt"/>" binding="realSaleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="mobile.ProdSale.per"/>" binding="realSalePer" width="1.*" align="right" is-read-only="true"></wj-flex-grid-column>
                    <!-- 조회 결과가 없을 때, msg 띄우기 -->
                    <div class="gridMsg" id="prodSaleMsg" style="line-height: 300px; display: none;"><s:message code="cmm.search.result.empty"/></div>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var multiStoreFg 	= '${multiStoreFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/status/prod/mobileProdSale.js?ver=20210504.01" charset="utf-8"></script>