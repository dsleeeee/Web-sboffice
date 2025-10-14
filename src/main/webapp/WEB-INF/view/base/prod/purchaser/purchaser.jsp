<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon" ng-controller="purchaserCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('purchaserCtrl', 1)" id="nxBtnSearch">
                <s:message code="cmm.search" />
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
        <tr>
            <%-- 매입처코드 --%>
            <th><s:message code="purchaser.vendrCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchVendrCd" ng-model="vendrCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 매입처명 --%>
            <th><s:message code="purchaser.vendrNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchVendrNm" ng-model="vendrNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 팀코드 --%>
            <th><s:message code="purchaser.teamCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchTeamCd" ng-model="teamCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 팀명 --%>
            <th><s:message code="purchaser.teamNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchTeamNm" ng-model="teamNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 지점코드 --%>
            <th><s:message code="purchaser.branchCd"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBranchCd" ng-model="branchCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 지점명 --%>
            <th><s:message code="purchaser.branchNm"/></th>
            <td>
                <input type="text" class="sb-input w100" id="srchBranchNm" ng-model="branchNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 현재화면 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent" /></button>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="purchaser.vendrCd"/>" binding="vendrCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.vendrNm"/>" binding="vendrNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.telNo"/>" binding="telNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.ownerNm"/>" binding="ownerNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.ownerTelNo"/>" binding="ownerTelNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.managerNm"/>" binding="managerNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.managerTelNo"/>" binding="managerTelNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.faxNo"/>" binding="faxNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="purchaser.bizNo"/>" binding="bizNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/purchaser/purchaser.js?ver=20251010.01" charset="utf-8"></script>