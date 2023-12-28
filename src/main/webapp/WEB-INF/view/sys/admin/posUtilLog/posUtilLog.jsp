<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="posUtilLogCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('posUtilLogCtrl',1)" id="nxBtnSearch">
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
        <%-- 조회 일자 --%>
        <tr>
            <th><s:message code="cmm.search.date" /></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" ng-model="startDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" ng-model="endDate" class="w110px"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 본사코드 --%>
            <th><s:message code="posUtilLog.hqOfficeCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="hqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 본사명 --%>
            <th><s:message code="posUtilLog.hqOfficeNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="hqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();" />
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="posUtilLog.storeCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="storeCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();" />
            </td>
            <%-- 매장명 --%>
            <th><s:message code="posUtilLog.storeNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="storeNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();" />
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
    </div>

    <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
        <div class="row">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="posUtilLog.srchDate"/>" binding="srchDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.storeNm"/>" binding="storeNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.userNm"/>" binding="userName" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.logDt"/>" binding="utilInsDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="posUtilLog.logMsg"/>" binding="utilInsMsg" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/posUtilLog/posUtilLog.js?ver=20231221.01" charset="utf-8"></script>