<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div class="subCon">

    <div ng-controller="userBaseCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('userBaseCtrl',1)">
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
                        <s:message code="userBase.date" />
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
                        <s:message code="userBase.resrceNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchResrceNm" ng-model="resrceNm" />
                    </td>
                    <%-- 사용환경 --%>
                    <th>
                        <s:message code="userBase.useEnv" />
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
                        <s:message code="userBase.userId" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserId" ng-model="userId" />
                    </td>
                    <%-- 사용자명 --%>
                    <th>
                        <s:message code="userBase.userNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchUserNm" ng-model="userNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 본사코드 --%>
                    <th>
                        <s:message code="userBase.hqOfficeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                    </td>
                    <%-- 본사명 --%>
                    <th>
                        <s:message code="userBase.hqOfficeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="userBase.storeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="userBase.storeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                    </td>
                </tr>
            </tbody>
        </table>

        <div class="mt40 oh sb-select dkbr">
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
        <div class="wj-TblWrap mt20 mb20 w50 fl">
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
                            id="wjGridUserBaseList">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="userBase.hqOfficeCd"/>" binding="hqOfficeCd" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="userBase.hqOfficeNm"/>" binding="hqOfficeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="userBase.storeCd"/>" binding="storeCd" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="userBase.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="userBase.userNm"/>" binding="userNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="userBase.useCnt"/>" binding="useCnt" width="60" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>

                            <%--상세 조회시 필요--%>
                            <wj-flex-grid-column header="<s:message code="userBase.userId"/>" binding="userId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w50 fr">
        <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
            <div class="w100 mt10 mb20">

                <%-- 사용자 팝업 레이어 --%>
                <c:import url="/WEB-INF/view/sys/stats/userBase/user.jsp">
                </c:import>
                <%-- 사용메뉴 팝업 레이어 --%>
                <c:import url="/WEB-INF/view/sys/stats/userBase/useMenu.jsp">
                </c:import>

            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/sys/stats/userBase/userBase.js?ver=20200529.22" charset="utf-8"></script>