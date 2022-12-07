<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div id="dayEmpCardView" name="dayView"  class="subCon" style="display: none;" ng-controller="dayEmpCardCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"> <s:message code="day.empCard" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('dayEmpCardCtrl',1)">
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
                <s:message code="cmm.search.date" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"> <input id="startDateDayEmpCard" name="startDate" class="w110px" /></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endDateDayEmpCard" name="endDate" class="w110px" /></span>
                </div>
            </td>
        </tr>
        <tr <c:if test="${orgnFg == 'STORE'}">style="display: none;"</c:if> >
            <%-- 매장코드 --%>
            <th><s:message code="day.dayTotal.store"/></th>
            <td colspan="3">
                <%-- 매장선택 모듈 싱글 선택 사용시 include
                     param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                  displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                  modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                  closeFunc - 팝업 닫기시 호출할 함수
                --%>
                <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
                    <jsp:param name="targetId" value="dayEmpCardStore"/>
                </jsp:include>
                <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadDayEmpCard()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
                    <wj-flex-grid-column header="<s:message code="day.empCard.saleDate"/>" binding="saleDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.yoil"/>" binding="yoil" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.officeNm"/>" binding="officeNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.officeDeptNm"/>" binding="officeDeptNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.officeEmpNo"/>" binding="officeEmpNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.officeEmpNm"/>" binding="officeEmpNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.prodNm"/>" binding="prodNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.saleAmt"/>" binding="saleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="day.empCard.employeeNo"/>" binding="employeeNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.employeeNm"/>" binding="employeeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.divNm"/>" binding="divNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.deptNm"/>" binding="deptNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="day.empCard.positionNm"/>" binding="positionNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>

                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="dayEmpCardCtrl"/>
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

<script type="text/javascript" src="/resource/solbipos/js/sale/day/day/dayEmpCard.js?ver=20211220.01" charset="utf-8"></script>
