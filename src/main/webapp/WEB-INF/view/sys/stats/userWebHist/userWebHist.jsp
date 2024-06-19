<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="userWebHistCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('userWebHistCtrl',1)" id="nxBtnSearch">
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
                        <span class="txtIn"> <input id="startDate" name="startDate" class="w110px" /></span>
                        <span class="rg">~</span>
                        <span class="txtIn"> <input id="endDate" name="endDate" class="w110px" /></span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 사용자ID --%>
                <th>
                    <s:message code="userWebHist.userId" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 사용자명 --%>
                <th>
                    <s:message code="userWebHist.userNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 본사코드 --%>
                <th>
                    <s:message code="userWebHist.hqOfficeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 본사명 --%>
                <th>
                    <s:message code="userWebHist.hqOfficeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="userWebHist.storeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="userWebHist.storeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%--  가상로그인아이디 --%>
                <th>
                    <s:message code="userWebHist.vUserId" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchVUserId" ng-model="vUserId" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 접속IP --%>
                <th>
                    <s:message code="userWebHist.loginIp" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchLoginIpm" ng-model="loginIp" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select">
            <%-- 현재화면 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent" /></button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height: 370px; overflow-x: hidden; overflow-y: hidden;">
                <div class="row">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="userWebHist.date"/>" binding="hDate" width="80" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.dateTime"/>" binding="hDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.userId"/>" binding="userId" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.userNm"/>" binding="userNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.hqOfficeNm"/>" binding="hqOfficeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.lmFg"/>" binding="lmFg" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.statCd"/>" binding="statCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.loginOrgn"/>" binding="loginOrgn" data-map="loginOrgnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.vUserId"/>" binding="vUserId" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.vUserNm"/>" binding="vUserNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.level1Nm"/>" binding="level1Nm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.level2Nm"/>" binding="level2Nm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.level3Nm"/>" binding="level3Nm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="userWebHist.loginIp"/>" binding="loginIp" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userWebHist/userWebHist.js?ver=20240619.01" charset="utf-8"></script>