<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="membrPointCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('membrPointCtrl')">
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
                    <s:message code="membrPoint.srchDate" />
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
                <%-- 매장 --%>
                <th>
                    <s:message code="membrPoint.storeNm" />
                </th>
                <td>
                    <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                        <jsp:param name="targetId" value="store"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
                <%-- 회원번호 --%>
                <th>
                    <s:message code="membrPoint.membrNo" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="membrNo" />
                </td>
            </tr>
            <tr>
                <%-- 회원명 --%>
                <th>
                    <s:message code="membrPoint.membrNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm" />
                </td>
                <%-- 정렬 --%>
                <th>
                    <s:message code="membrPoint.array" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchArray"
                            ng-model="array"
                            items-source="_getComboData('array')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
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
                <wj-flex-grid-column header="<s:message code="membrPoint.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.regDt"/>" binding="regDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.membrNo"/>" binding="membrNo" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.membrNm"/>" binding="membrNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.membrClassNm"/>" binding="membrClassNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.membrSavePoint"/>" binding="membrSavePoint" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.membrUsePoint"/>" binding="membrUsePoint" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.saleAmt"/>" binding="saleAmt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.dcAmt"/>" binding="dcAmt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="membrPoint.realSaleAmt"/>" binding="realSaleAmt" width="115" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/membrPoint/membrPoint.js?ver=2019052801.11" charset="utf-8"></script>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>