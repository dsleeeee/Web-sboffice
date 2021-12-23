<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div id="envConfgBatchChangeFnkeyView" class="subCon" ng-controller="envConfgBatchChangeFnkeyCtrl" style="display: none;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="envConfgBatchChange.fnkey"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('envConfgBatchChangeFnkeyCtrl',1)" id="nxBtnSearch4">
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
            <%-- 기능키 --%>
            <th>
                <s:message code="envConfgBatchChange.fnkey.fnkey" />
            </th>
            <td>
                <input type="text" class="sb-input w70" id="fnkeyNm" ng-model="fnkeyNm" ng-click="popUpFnkey()" style="float: left;"
                       placeholder="<s:message code="envConfgBatchChange.fnkey.fnkey" /> 선택" readonly/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelFnkey" style="margin-left: 5px;" ng-click="delFnkey()"><s:message code="cmm.selectCancel"/></button>
            </td>
        </tr>
        <tr>
            <%-- 본사코드 --%>
            <th>
                <s:message code="envConfgBatchChange.fnkey.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('4');"/>
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="envConfgBatchChange.fnkey.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('4');"/>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="envConfgBatchChange.fnkey.storeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('4');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="envConfgBatchChange.fnkey.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('4');"/>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt20 oh">
        <label id="lblFnkeyNo"></label>
        <label id="lblFnkeyNm"></label>
    </div>
    <div class="mt20 oh">
        <%--환경변수값--%>
        <div class="sb-select dkbr ml5 fl" id="divTextFnkeyVal">
            <input type="text" class="sb-input w150px" id="srchFnkeyVal" ng-model="fnkeyVal" />
        </div>
        <%-- 일괄변경 --%>
        <button class="btn_skyblue ml5 fl" id="btnSave" ng-click="batchChangeFnkey()">
            <s:message code="envConfgBatchChange.fnkey.batchChange" />
        </button>
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveFnkey()">
            <s:message code="cmm.save" />
        </button>
        <%--시스템패스워드--%>
        <div class="sb-select dkbr ml5 fr">
            <s:message code="envConfgBatchChange.fnkey.systemPw" />
            <input type="password" class="sb-input w200px" id="srchSystemPw" ng-model="systemPw" />
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
                    id="wjGridEnvConfgBatchChangeFnkeyList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.hqOfficeNm"/>" binding="hqOfficeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.fnkeyNm"/>" binding="fnkeyNm" width="100" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/envConfgBatchChangeFnkey.js?ver=20210914.01" charset="utf-8"></script>