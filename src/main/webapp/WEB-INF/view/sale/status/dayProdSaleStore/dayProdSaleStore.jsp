<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon">
    <div ng-controller="dayProdSaleStoreCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="dayProdSaleStore.dayProdSaleStore" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('dayProdSaleStoreCtrl', 1)">
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
                <%-- 조회일자 --%>
                <th><s:message code="cmm.search.date"/></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 매장선택 --%>
                    <th><s:message code="cmm.store.select"/></th>
                    <td>
                        <%-- 매장선택 모듈 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                            <jsp:param name="targetTypeFg" value="M"/>
                            <jsp:param name="targetId" value="dayProdSaleStoreStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                    </td>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                  <input type="hidden" id="dayProdSaleStoreStoreCd" value="${sessionInfo.storeCd}"/>
                </c:if>
            </tr>
            <tr>
                <%-- 상품분류 --%>
                <th><s:message code="dayProdSaleStore.prodClass" /></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                            placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <%-- 상품코드 --%>
                <th><s:message code="cmm.prodCd" /></th>
                <td>
                    <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 상품명 --%>
                <th><s:message code="cmm.prodNm" /></th>
                <td>
                    <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 사이드상품분류 --%>
                <th><s:message code="dayProdSaleStore.sideProdClass" /></th>
                <td>
                    <input type="text" class="sb-input w70" id="srchSideProdClassCd" ng-model="sideProdClassCdNm" ng-click="popUpSideProdClass()" style="float: left;"
                            placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                    <input type="hidden" id="_sideProdClassCd" name="sideProdClassCd" ng-model="sideProdClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelSideProdClassCd" style="margin-left: 5px;" ng-click="delSideProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <%-- 사이드상품코드 --%>
                <th><s:message code="dayProdSaleStore.sideProdCd" /></th>
                <td>
                    <input type="text" id="srchSideProdCd" ng-model="sideProdCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사이드상품명 --%>
                <th><s:message code="dayProdSaleStore.sideProdNm" /></th>
                <td>
                    <input type="text" id="srchSideProdNm" ng-model="sideProdNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
        </div>

        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        id="wjGridList"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.hqOfficeCd"/>" binding="hqOfficeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeNm"/>" binding="storeNm" width="140" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodClass"/>" binding="prodClassNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodNm"/>" binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdClass"/>" binding="sideProdClassNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdNm"/>" binding="sideProdNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.selTypeFg"/>" binding="selTypeFg" data-map="selTypeFgMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleQty"/>" binding="totSaleQty" width="55" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleAmt"/>" binding="totSaleAmt" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totDcAmt"/>" binding="totDcAmt" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dayProdSaleStore.realSaleAmt"/>" binding="realSaleAmt" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dayProdSaleStoreCtrlPager" data-size="10">
        </ul>
    </div>
    <%-- //페이지 리스트 --%>

    <%--엑셀 리스트--%>
    <div class="w100 mt10" style="display:none;" ng-controller="dayProdSaleStoreExcelCtrl">
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridExcelList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.hqOfficeCd"/>" binding="hqOfficeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.storeNm"/>" binding="storeNm" width="140" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodClass"/>" binding="prodClassNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.prodNm"/>" binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdClass"/>" binding="sideProdClassNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdCd"/>" binding="sideProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.sideProdNm"/>" binding="sideProdNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.selTypeFg"/>" binding="selTypeFg" data-map="selTypeFgMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleQty"/>" binding="totSaleQty" width="55" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totSaleAmt"/>" binding="totSaleAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.totDcAmt"/>" binding="totDcAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="dayProdSaleStore.realSaleAmt"/>" binding="realSaleAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>
    <%--//엑셀 리스트--%>

</div>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/dayProdSaleStore/dayProdSaleStore.js?ver=20250114.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상세 팝업 --%>
<c:import url="/WEB-INF/view/sale/status/dayProdSaleStore/dayProdSaleStoreDtl.jsp">
</c:import>