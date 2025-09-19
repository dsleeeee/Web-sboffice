<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="workHistoryByWorkStudentCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workHistoryByWorkStudentCtrl',1)">
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
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="workHistoryByWorkStudent.storeCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="workHistoryByWorkStudent.storeNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStoreNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 학번 --%>
            <th>
                <s:message code="workHistoryByWorkStudent.studentNo"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStudentNo" ng-model="studentNo" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 성명 --%>
            <th>
                <s:message code="workHistoryByWorkStudent.studentNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStudentNm" ng-model="studentNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="workHistoryByWorkStudent.print" /></button>
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
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.termYear"/>"         binding="termYear"      width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.termFg"/>"           binding="termFg"        width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.workSchCode"/>"      binding="workSchCode"   width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.workStudent"/>"      binding="studentNm"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.workDate"/>"         binding="workDate"      width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.workPlace"/>"        binding="storeNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.realStartTime"/>"    binding="realStartTime" width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.realEndTime"/>"      binding="realEndTime"   width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.workTime"/>"         binding="baseWorkTime"  width="80"  align="center" is-read-only="true" format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistoryByWorkStudent.remark"/>"           binding="remark"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workHistoryByWorkStudent/workHistoryByWorkStudent.js?ver=20250919.01" charset="utf-8"></script>

<%-- 근로장학금 지급내역 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/workStudent/workHistoryByWorkStudent/workHistoryByWorkStudentReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>