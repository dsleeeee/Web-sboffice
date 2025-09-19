<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="workStudentPayHistoryCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workStudentPayHistoryCtrl',1)">
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
            <%-- 근무일자 --%>
            <th>
                <s:message code="workStudentPayHistory.srchYm"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchYm" ng-model="srchYm" class="w110px"></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="workStudentPayHistory.print" /></button>
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
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.termYear"/>"    binding="termYear"      width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.termFg"/>"      binding="termFg"        width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.workSchCode"/>" binding="workSchCode"   width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.studentNm"/>"   binding="studentNm"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.storeNm"/>"     binding="storeNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.department"/>"  binding="department"    width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.studentNo"/>"   binding="studentNo"     width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.storeCd"/>"     binding="storeCd"       width="70"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.workPeriod"/>"  binding="workPeriod"    width="160" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.workTime"/>"    binding="workTime"      width="70"  align="center" is-read-only="true" format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.payAmt"/>"      binding="payAmt"        width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentPayHistory.bankAccount"/>" binding="bankAccount"   width="170"  align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workStudentPayHistory/workStudentPayHistory.js?ver=20250918.01" charset="utf-8"></script>

<%-- 근로장학금 지급내역 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/workStudent/workStudentPayHistory/workStudentPayHistoryReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>