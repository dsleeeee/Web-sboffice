<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="envConfgBatchChangeCommCodeView" class="subCon" ng-controller="envConfgBatchChangeCommCodeCtrl" style="display: none;padding: 10px 20px 40px;">

    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="envConfgBatchChange.commCode"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('envConfgBatchChangeCommCodeCtrl')">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <div>
        <%-- 조회조건 --%>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
                <tr>
                    <%-- 공통코드 --%>
                    <th><s:message code="envConfgBatchChange.commCode.commCode" /></th>
                    <td>
                        <input type="text" class="sb-input w70" id="nmcodeNm" ng-model="nmcodeNm" ng-click="popUpCommCode()" style="float: left;"
                               placeholder="<s:message code="envConfgBatchChange.commCode.commCode" /> 선택" readonly/>
                        <button type="button" class="btn_skyblue fl mr5" id="btnCancelCommCode" style="margin-left: 5px;" ng-click="delCommCode()"><s:message code="cmm.selectCancel"/></button>

                    </td>
                    <th></th>
                    <td>
                    </td>
                </tr>
                <tr>
                    <%-- 본사코드 --%>
                    <th><s:message code="envConfgBatchChange.commCode.hqOfficeCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd"/>
                    </td>
                    <%-- 본사명 --%>
                    <th><s:message code="envConfgBatchChange.commCode.hqOfficeNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th><s:message code="envConfgBatchChange.commCode.storeCd"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd"/>
                    </td>
                    <%-- 매장명 --%>
                    <th><s:message code="envConfgBatchChange.commCode.storeNm"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="mt10 oh">
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveCommCode()">
                <s:message code="cmm.save"/>
            </button>
            <%--시스템패스워드--%>
            <div class="sb-select dkbr ml5 fr">
                <span class="s12">※ 관리자화면입니다. 패스워드 규칙 다름</span>
                <s:message code="envConfgBatchChange.commCode.systemPw"/>
                <input type="password" class="sb-input w200px" id="srchSystemPw" ng-model="systemPw"/>
            </div>
        </div>
        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        id="wjGridEnvConfgBatchChangeCommCodeList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.hqOfficeNm"/>" binding="hqOfficeNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.storeNm"/>" binding="storeNm" width="250" is-read-only="true" align="left"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/envConfgBatchChangeCommCode.js?ver=20241120.01" charset="utf-8"></script>