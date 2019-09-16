<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="membrProdCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('membrProdCtrl')">
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
                <%-- 조회기간 --%>
                <th>
                    <s:message code="membrProd.srchDate" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 회원명 --%>
                <th>
                    <s:message code="membrProd.srchMembrNm" />
                </th>
                <td colspan="3">
                    <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
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
                item-formatter="_itemFormatter"
                is-read-only="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="membrProd.membrNo"/>" binding="membrNo" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.membrNm"/>" binding="membrNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.prodCd"/>" binding="prodCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.saleQty"/>" binding="saleQty" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.saleAmt"/>" binding="saleAmt" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.dcAmt"/>" binding="dcAmt" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrProd.realSaleAmt"/>" binding="realSaleAmt" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/membrProd/membrProd.js?ver=2019052801.05" charset="utf-8"></script>

<%-- 회원 상세정보 --%>
<c:import url="/WEB-INF/view/membr/anals/dayMembr/dayMembrDetailView.jsp">
</c:import>