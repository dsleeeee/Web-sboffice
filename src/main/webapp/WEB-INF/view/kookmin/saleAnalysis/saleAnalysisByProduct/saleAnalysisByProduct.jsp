<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="saleAnalysisByProductCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('saleAnalysisByProductCtrl',1)">
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
                        <jsp:param name="targetId" value="saleAnalysisByProductStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
        </tr>
        <tr>
            <%-- 매입처코드 --%>
            <th>
                <s:message code="saleAnalysisByProduct.acquireCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id=srchAcquireCd ng-model="acquireCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매입처명 --%>
            <th>
                <s:message code="saleAnalysisByProduct.acquireNm"/>
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
                <s:message code="saleAnalysisByProduct.branchCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchCd" ng-model="branchCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 지점명 --%>
            <th>
                <s:message code="saleAnalysisByProduct.branchNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchBranchNm" ng-model="branchNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="saleAnalysisByProduct.prodCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="saleAnalysisByProduct.prodNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="saleAnalysisByProduct.print" /></button>
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
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.storeCd"/>"     binding="storeCd"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.storeNm"/>"     binding="storeNm"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.prodCd"/>"      binding="prodCd"        width="100"  align="center" is-read-only="true" format="d"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.prodNm"/>"      binding="prodNm"        width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.mClsNPubNm"/>"  binding="mClsNPubNm"    width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.sClsNAuthor"/>" binding="sClsNAuthor"   width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.acquireCd"/>"   binding="acquireCd"     width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.acquireNm"/>"   binding="acquireNm"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.acquireFg"/>"   binding="acquireFg"     width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.saleUprc"/>"    binding="saleAmt"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.acquireUprc"/>" binding="acquireUprc"   width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.acquireRate"/>" binding="acquireRate"   width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.saleQty"/>"     binding="totSaleQty"    width="70"  align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.totSaleAmt"/>"  binding="totSaleAmt"    width="70"  align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.totDcAmt"/>"    binding="totDcAmt"      width="70"  align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="saleAnalysisByProduct.realSaleAmt"/>" binding="realSaleAmt"   width="70"  align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/saleAnalysis/saleAnalysisByProduct/saleAnalysisByProduct.js?ver=20250929.01" charset="utf-8"></script>

<%-- 상품별 매출분석 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/saleAnalysis/saleAnalysisByProduct/saleAnalysisByProductReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>