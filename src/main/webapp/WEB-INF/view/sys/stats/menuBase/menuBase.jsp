<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="menuBaseCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('menuBaseCtrl',1)">
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
                        <s:message code="menuBase.date" />
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
                    <%-- 메뉴명 --%>
                    <th>
                        <s:message code="menuBase.resrceNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchResrceNm" ng-model="resrceNm" />
                    </td>
                    <%-- 사용환경 --%>
                    <th>
                        <s:message code="menuBase.useEnv" />
                    </th>
                    <td>
                        <div class="sb-select fl w200px">
                            <wj-combo-box
                                id="srchUseEnv"
                                ng-model="useEnv"
                                items-source="_getComboData('useEnvCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 사용자ID --%>
                    <th>
                        <s:message code="menuBase.userId" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                    </td>
                    <%-- 사용자명 --%>
                    <th>
                        <s:message code="menuBase.userNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 본사코드 --%>
                    <th>
                        <s:message code="menuBase.hqOfficeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                    </td>
                    <%-- 본사명 --%>
                    <th>
                        <s:message code="menuBase.hqOfficeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="menuBase.storeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="menuBase.storeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="mt40 oh sb-select dkbr">
            <%-- 대,중,소메뉴  --%>
            <wj-combo-box
                class="w180px fl"
                id="level"
                ng-model="level"
                items-source="_getComboData('levelCombo')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
            </wj-combo-box>
        </div>

        <%--left--%>
        <div class="wj-TblWrap mt20 mb20 w40 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridMenuBaseList"
                            allow-merging="Cells">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="menuBase.level1Nm"/>" binding="level1Nm" width="*" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="menuBase.level2Nm"/>" binding="level2Nm" width="*" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="menuBase.level3Nm"/>" binding="level3Nm" width="*" is-read-only="true" align="center" allow-merging="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="menuBase.useCnt"/>" binding="useCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                            <%--상세 조회시 필요--%>
                            <wj-flex-grid-column header="<s:message code="menuBase.level1"/>" binding="level1" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="menuBase.level2"/>" binding="level2" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="menuBase.level3"/>" binding="level3" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="menuBaseDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="menuBase.hqOfficeCd"/>" binding="hqOfficeCd" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.hqOfficeNm"/>" binding="hqOfficeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.storeNm"/>" binding="storeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.orgnFg"/>" binding="orgnFg" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.userId"/>" binding="userId" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.userNm"/>" binding="userNm" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="menuBase.useCnt"/>" binding="useCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/menuBase/menuBase.js?ver=20200527.08" charset="utf-8"></script>