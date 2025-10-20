<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<div class="subCon" ng-controller="saleAnalysisByVendrCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('saleAnalysisByVendrCtrl',1)">
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
            <%-- 조회일자 --%>
            <th>
                <s:message code="cmm.search.date"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px" /></span>
                </div>
            </td>
            <%-- 매장선택 --%>
            <th><s:message code="cmm.store.select"/></th>
            <td>
                <%-- 매장선택 모듈 사용시 include --%>
                <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                    <jsp:param name="targetTypeFg" value="S"/>
                    <jsp:param name="targetId" value="saleAnalysisByVendrStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 사용시 include --%>
            </td>
        </tr>
        <tr>
            <%-- 매입처코드 --%>
            <th>
                <s:message code="saleAnalysisByVendr.acquireCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchAcquireCd" ng-model="acquireCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매입처명 --%>
            <th>
                <s:message code="saleAnalysisByVendr.acquireNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchAcquireNm" ng-model="acquireNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 지점코드 --%>
            <th>
                <s:message code="saleAnalysisByVendr.branchCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchCd" ng-model="branchCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 지점명 --%>
            <th>
                <s:message code="saleAnalysisByVendr.branchNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchNm" ng-model="branchNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 분류조회 --%>
            <th><s:message code="prod.prodClass" /></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <input type="hidden" id="_selectCancelFg" name="selectCancelFg" ng-model="selectCancelFg" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="saleAnalysisByVendr.print" /></button>
        <button class="btn_skyblue fr mr5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <%--                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>--%>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.acquireCd"/>"     binding="vendrCd"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.acquireNm"/>"     binding="vendrNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.prodCd"/>"        binding="prodCd"        width="110" align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.prodNm"/>"        binding="prodNm"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.sClsNm"/>"        binding="prodClassNm"   width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.vendrFg"/>"       binding="vendrFg"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.saleUprc"/>"      binding="saleUprc"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.acquireUprc"/>"   binding="splyUprc"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.totSaleQty"/>"    binding="totSaleQty"    width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.totSaleAmt"/>"    binding="totSaleAmt"    width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.totDcAmt"/>"      binding="totDcAmt"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.realSaleAmt"/>"   binding="realSaleAmt"   width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.cogs"/>"          binding="cogs"          width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByVendr.salesProfit"/>"   binding="salesProfit"   width="120" align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/saleAnalysis/saleAnalysisByVendr/saleAnalysisByVendr.js?ver=20251020.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCdCheck.jsp">
</c:import>

<%-- 매입처별 매출분석 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/saleAnalysis/saleAnalysisByVendr/saleAnalysisByVendrReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>