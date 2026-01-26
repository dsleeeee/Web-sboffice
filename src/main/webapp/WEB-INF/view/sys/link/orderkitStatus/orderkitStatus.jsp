<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="orderkitStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="orderkitStatus.orderkitStatus"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('orderkitStatusCtrl')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
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
            <%-- 구분 --%>
            <th><s:message code="orderkitStatus.viewFg"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="viewFg"
                            ng-model="viewFg"
                            items-source="_getComboData('viewFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="viewFgCombo"
                            selected-index-changed="setSrchDate(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 조회기간 --%>
            <th id="thSrchDate"><s:message code="orderkitStatus.srchDate"/></th>
            <td id="tdSrchDate">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" name="startDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" name="endDate" class="w110px"/></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="w100 mt10">
        <%--사용자현황 테이블--%>
        <div class="wj-gridWrap" id="wjGridUser" style="height: 380px; overflow-x: hidden; overflow-y: hidden; display: block;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="orderkitStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.agencyUseYn"/>" binding="agencyUseYn" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.lastResponse"/>" binding="lastResponse" width="600" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.lastResponseDt"/>" binding="lastResponseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//사용자현황 테이블--%>

        <%--접속현황 테이블--%>
        <div class="wj-gridWrap" id="wjGridConnect" style="height: 380px; overflow-x: hidden; overflow-y: hidden; display: none;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="orderkitStatus.level1Nm"/>" binding="level1Nm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.level2Nm"/>" binding="level2Nm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.level3Nm"/>" binding="level3Nm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.orgnFg"/>" binding="orgnFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.userId"/>" binding="userId" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.userNm"/>" binding="userNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="orderkitStatus.useCnt"/>" binding="useCnt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//접속현황 테이블--%>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/orderkitStatus/orderkitStatus.js?ver=20260126.01" charset="utf-8"></script>