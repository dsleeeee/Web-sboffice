<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="membrRecomendrCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('membrRecomendrCtrl')">
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
            <%-- 조회기간 --%>
            <th>
                <s:message code="recomendr.srchDate"/>
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startDate" name="startDate" class="w200px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endDate" name="endDate" class="w200px"/></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 사원번호 --%>
            <th>
                <s:message code="recomendr.empNo"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchEmpNo" ng-model="empNo"/>
            </td>
            <%-- 사원명 --%>
            <th>
                <s:message code="recomendr.empNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchEmpNm" ng-model="empNm"/>
            </td>
        </tr>
        <tr>
            <%-- 회원번호 --%>
            <th>
                <s:message code="recomendr.membrNo"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchMembrNo" ng-model="membrNo"/>
            </td>
            <%-- 회원명 --%>
            <th>
                <s:message code="recomendr.membrNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchMembrNm" ng-model="membrNm"/>
            </td>
        </tr>
        <tr>
            <%-- 소속 --%>
            <th>
                <s:message code="recomendr.orgn"/>
            </th>
            <td colspan="3">
                <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                <jsp:include page="/WEB-INF/view/application/layer/searchStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="store"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>

    <%--<div class="mt20 oh sb-select dkbr">--%>
        <%--&lt;%&ndash; 페이지 스케일  &ndash;%&gt;--%>
        <%--<wj-combo-box--%>
                <%--class="w100px fl"--%>
                <%--id="listScaleBox"--%>
                <%--ng-model="listScale"--%>
                <%--items-source="_getComboData('listScaleBox')"--%>
                <%--display-member-path="name"--%>
                <%--selected-value-path="value"--%>
                <%--is-editable="false"--%>
                <%--initialized="initComboBox(s)">--%>
        <%--</wj-combo-box>--%>
    <%--</div>--%>

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
                <wj-flex-grid-column header="<s:message code="recomendr.orgnCd"/>" binding="orgnCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.orgnNm"/>" binding="orgnNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.empNo"/>" binding="empNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.empNm"/>" binding="empNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.membrNo"/>" binding="membrNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.membrNm"/>" binding="membrNm"  width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.prodCd"/>" binding="prodCd"  width="80" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.total.sale"/>" binding="totalSale" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.total.discount"/>" binding="totalDiscount" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="recomendr.real.sale"/>" binding="realSale"  width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
    <%-- 페이지 리스트 --%>
    <%--<div class="pageNum mt20">--%>
        <%--&lt;%&ndash; id &ndash;%&gt;--%>
        <%--<ul id="membrPointCtrlPager" data-size="10">--%>
        <%--</ul>--%>
    <%--</div>--%>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/membrRecomendr/membrRecomendr.js?ver=2020062901.11" charset="utf-8"></script>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/application/layer/store.jsp">
</c:import>