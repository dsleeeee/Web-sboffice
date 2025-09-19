<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="workStudentScheduleStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workStudentScheduleStatusCtrl',1)">
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
                <s:message code="workStudentScheduleStatus.termYear"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchDate" ng-model="srchDate" class="w110px"></span>
                </div>
            </td>
                <%-- 학기구분 --%>
                <th>
                    <s:message code="workStudentScheduleStatus.termFg"/>
                </th>
                <td>
                    <span class="sb-radio"><input type="radio" id="termFg1" name="termFg" value="1" checked /><label for="termFg1">1학기</label></span>
                    <span class="sb-radio"><input type="radio" id="termFg2" name="termFg" value="2"/><label for="termFg2">하계방학</label></span>
                    <span class="sb-radio"><input type="radio" id="termFg3" name="termFg" value="3"/><label for="termFg3">2학기</label></span>
                    <span class="sb-radio"><input type="radio" id="termFg4" name="termFg" value="4"/><label for="termFg4">동계방학</label></span>
                </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="workStudentScheduleStatus.storeCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="workStudentScheduleStatus.storeNm"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStoreNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <button class="btn_skyblue fr mr5" ng-click="report()"><s:message code="workStudentScheduleStatus.print" /></button>
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
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.termYear"/>"    binding="termYear"      width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.termFg"/>"      binding="termFg"        width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.workSchCode"/>" binding="workSchCode"   width=""    align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.storeNm"/>"     binding="storeNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.workDay"/>"     binding="workDay"       width="140" align="left"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.workTime"/>"    binding="workTime"      width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.hourPay"/>"     binding="hourPay"       width="60"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.workFg"/>"      binding="workFg"        width="60"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.studentNo"/>"   binding="studentNo"     width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.studentNm"/>"   binding="studentNm"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.startDay"/>"    binding="startDay"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.endDay"/>"      binding="endDay"        width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workStudentScheduleStatus.remark"/>"      binding="remark"        width="120" align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workStudentScheduleStatus/workStudentScheduleStatus.js?ver=20250919.01" charset="utf-8"></script>

<%-- 근로장학금 지급내역 출력 레이어 --%>
<c:import url="/WEB-INF/view/kookmin/workStudent/workStudentScheduleStatus/workStudentScheduleStatusReport.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>