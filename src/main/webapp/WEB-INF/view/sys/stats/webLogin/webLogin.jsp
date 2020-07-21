<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="webLoginCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('webLoginCtrl',1)">
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
                        <s:message code="webLogin.date" />
                    </th>
                    <td colspan="3">
                        <div class="sb-select">
                            <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
                            <span class="rg">~</span>
                            <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 사용자ID --%>
                    <th>
                        <s:message code="webLogin.userId" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                    </td>
                    <%-- 사용자명 --%>
                    <th>
                        <s:message code="webLogin.userNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 로그인결과 --%>
                    <th>
                        <s:message code="webLogin.statCd" />
                    </th>
                    <td>
                        <div class="sb-select fl w200px">
                            <wj-combo-box
                                id="srchStatCd"
                                ng-model="statCd"
                                items-source="_getComboData('statCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 로그인방법 --%>
                    <th>
                        <s:message code="webLogin.loginOrgn" />
                    </th>
                    <td>
                        <div class="sb-select fl w200px">
                            <wj-combo-box
                                id="srchLoginOrgn"
                                ng-model="loginOrgn"
                                items-source="_getComboData('loginOrgnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 본사코드 --%>
                    <th>
                        <s:message code="webLogin.hqOfficeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                    </td>
                    <%-- 본사명 --%>
                    <th>
                        <s:message code="webLogin.hqOfficeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="webLogin.storeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="webLogin.storeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                    </td>
                </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="mt20">
            <div class="wj-TblWrapBr mr10 pd20" style="height:350px;">
                <s:message code="webLogin.user"/>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:270px; overflow-y: hidden; overflow-x: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="webLogin.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.userNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.statSucc"/>" binding="statSucc" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.statFail"/>" binding="statFail" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.hqOfficeNm"/>" binding="hqOfficeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.orgnFg"/>" binding="orgnFg" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.minLoginDt"/>" binding="minLoginDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="webLogin.maxLoginDt"/>" binding="maxLoginDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--left--%>
    <div class="wj-TblWrap mt20 mb20 w40 fl" ng-controller="webLoginDayDetailCtrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:350px;">
            <s:message code="webLogin.day"/>
            <label id="lblWebLoginDayDetail"></label>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:270px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="webLogin.loginDate"/>" binding="loginDate" width="100" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.yoil"/>" binding="yoil" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.statNm"/>" binding="statNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.loginCnt"/>" binding="loginCnt" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--left--%>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="webLoginLoginDetailCtrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height:350px;">
            <s:message code="webLogin.login"/>
            <label id="lblWebLoginLoginDetail"></label>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:270px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="webLogin.login.loginDate"/>" binding="loginDate" width="100" is-read-only="true" align="center" format="date"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.statNm"/>" binding="statNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.loginOrgn"/>" binding="loginOrgn" data-map="loginOrgnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="webLogin.userNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/webLogin/webLogin.js?ver=20200609.01" charset="utf-8"></script>