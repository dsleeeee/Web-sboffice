<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/sale/day/month/monthEmpCard/"/>

<div id="monthEmpCardView" name="monthView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="monthEmpCardCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="month.empCard" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('monthEmpCardCtrl',1)">
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
                <%-- 조회월 --%>
                <th>
                    <s:message code="cmm.search.month" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn"> <input id="startMonthMonthEmpCard" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endMonthMonthEmpCard" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="monthEmpCardStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:450px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
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
                    <wj-flex-grid-column header="<s:message code="month.emp.officeNm"/>" binding="officeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.officeDeptNm"/>" binding="officeDeptNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.officeEmpNo"/>" binding="officeEmpNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.officeEmpNm"/>" binding="officeEmpNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.saleAmt"/>" binding="saleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="month.emp.employeeNo"/>" binding="employeeNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.employeeNm"/>" binding="employeeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.divNm"/>" binding="divNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.deptNm"/>" binding="deptNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="month.emp.positionNm"/>" binding="positionNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="monthEmpCardCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>

            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/day/month/monthEmpCard.js?ver=20210714.02" charset="utf-8"></script>
