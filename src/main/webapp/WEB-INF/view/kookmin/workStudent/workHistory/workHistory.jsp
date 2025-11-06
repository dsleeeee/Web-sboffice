<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />


<div class="subCon" ng-controller="workHistoryCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('workHistoryCtrl',1)">
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
                <s:message code="workHistory.workDate"/>
            </th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchWorkDate" ng-model="workDate" class="w110px"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="workHistory.storeCd"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="workHistory.storeNm"/>
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
                <s:message code="workHistory.studentNo"/>
            </th>
            <td>
                <div class="sb-select">
                    <input type="text" id="srchStudentNo" ng-model="studentNo" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </div>
            </td>
            <%-- 성명 --%>
            <th>
                <s:message code="workHistory.studentNm"/>
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
        <button class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save" /></button>
        <button class="btn_skyblue fr mr5" ng-click="regCommute()"><s:message code="workHistory.missCommuteReg" /></button>
    </div>

    <%--위즈모 테이블--%>
    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridWorkHistory"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.termYear"/>"      binding="termYear"      width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.termFg"/>"        binding="termFg"        width=""    align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.workDate"/>"      binding="workDate"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.workSchCode"/>"   binding="workSchCode"   width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.storeCd"/>"       binding="storeCd"       width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.storeNm"/>"       binding="storeNm"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.studentNo"/>"     binding="studentNo"     width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.workStudent"/>"   binding="studentNm"     width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.baseStartTime"/>" binding="baseStartTime" width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.baseEndTime"/>"   binding="baseEndTime"   width="70"  align="center" is-read-only="true"  data-map="workFgComboDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.baseWorkTime"/>"  binding="baseWorkTime"  width="70"  align="center" is-read-only="true" format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.realStartTime"/>" binding="realStartTime" width="70"  align="center" data-map="timeDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.realEndTime"/>"   binding="realEndTime"   width="70"  align="center" data-map="timeDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.realWorkTime"/>"  binding="workTime"      width="70"  align="center" is-read-only="true" format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.workStatus"/>"    binding="workStatus"    width="60"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.overTime"/>"      binding="overChk"       width="70"  align="center" data-type="Boolean"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.totWorkTime"/>"   binding="totWorkTime"      width="70"  align="center" is-read-only="true" format="n1"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.modReason"/>"     binding="modReason"     width="120" align="center" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="workHistory.modNm"/>"         binding="modNm"         width="80"  align="center" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/kookmin/workStudent/workHistory/workHistory.js?ver=20251106.01" charset="utf-8"></script>