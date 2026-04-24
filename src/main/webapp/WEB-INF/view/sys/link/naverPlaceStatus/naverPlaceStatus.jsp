<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>

<div class="subCon" ng-controller="naverPlaceStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="naverPlaceStatus.naverPlaceStatus"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('naverPlaceStatusCtrl')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
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
        <tbody>
        <tr>
            <%-- 구분 --%>
            <th><s:message code="naverPlaceStatus.viewFg"/></th>
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
            <th id="thSrchDate"><s:message code="naverPlaceStatus.srchDate"/></th>
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
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.reset"/>" binding="reset" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.uniqueId"/>" binding="uniqueId" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.lastResponseDt"/>" binding="lastResponseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.mpNo"/>" binding="mpNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>01" binding="etc01" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>02" binding="etc02" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>03" binding="etc03" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>04" binding="etc04" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>05" binding="etc05" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>06" binding="etc06" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>07" binding="etc07" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>08" binding="etc08" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>09" binding="etc09" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.etc"/>10" binding="etc10" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.naverStoreNm"/>" binding="naverStoreNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.naverPlaceId"/>" binding="naverPlaceId" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.naverLinkDt"/>" binding="naverLinkDt" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.agreementLastResponseDt"/>" binding="agreementLastResponseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.agreementType"/>" binding="agreementType" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
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
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.level1Nm"/>" binding="level1Nm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.level2Nm"/>" binding="level2Nm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.level3Nm"/>" binding="level3Nm" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.orgnFg"/>" binding="orgnFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.userId"/>" binding="userId" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.userNm"/>" binding="userNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="naverPlaceStatus.useCnt"/>" binding="useCnt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//접속현황 테이블--%>
    </div>
</div>

<script type="text/javascript">
    var menuCd = "${menuCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/naverPlaceStatus/naverPlaceStatus.js?ver=20260423.01" charset="utf-8"></script>

<%-- 네이버플레이스 연동 초기화 팝업 --%>
<c:import url="/WEB-INF/view/sys/link/naverPlaceStatus/naverPlaceStatusReset.jsp">
</c:import>