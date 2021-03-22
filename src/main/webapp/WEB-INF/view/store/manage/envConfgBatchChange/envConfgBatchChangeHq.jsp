<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div id="envConfgBatchChangeHqView" class="subCon" ng-controller="envConfgBatchChangeHqCtrl" style="display: none;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="envConfgBatchChange.hq"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('envConfgBatchChangeHqCtrl',1)">
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
            <%-- 환경설정 --%>
            <th>
                <s:message code="envConfgBatchChange.hq.envst" />
            </th>
            <td>
                <input type="text" class="sb-input w70" id="envstNm" ng-model="envstNm" ng-click="popUpEnvstCd()" style="float: left;"
                       placeholder="<s:message code="envConfgBatchChange.hq.envst" /> 선택" readonly/>
                <input type="hidden" id="envstCd" name="envstCd" ng-model="envstCd" disabled />
                <input type="hidden" id="dirctInYn" name="dirctInYn" ng-model="dirctInYn" disabled />
                <input type="hidden" id="targtFg" name="targtFg" ng-model="targtFg" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelEnvstCd" style="margin-left: 5px;" ng-click="delEnvstCd()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 등록여부 --%>
            <th>
                <s:message code="envConfgBatchChange.hq.useYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseYnCombo"
                            ng-model="useYn"
                            items-source="_getComboData('useYnCombo')"
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
                <s:message code="envConfgBatchChange.hq.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="envConfgBatchChange.hq.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt20 oh">
        <label id="lblEnvstCd"></label>
        <label id="lblEnvstNm"></label>
    </div>
    <div class="mt20 oh">
        <%--환경변수값--%>
        <div class="sb-select dkbr ml5 fl" id="divComboEnvstVal">
            <wj-combo-box
                    class="w150px fl"
                    id="srchEnvstValCombo"
                    ng-model="envstValCombo"
                    items-source="_getComboData('envstValCombo')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
            </wj-combo-box>
        </div>
        <%--환경변수값--%>
        <div class="sb-select dkbr ml5 fl" id="divTextEnvstVal" style="display: none">
            <input type="text" class="sb-input w150px" id="srchEnvstVal" ng-model="envstVal" />
        </div>
        <%-- 일괄변경 --%>
        <button class="btn_skyblue ml5 fl" id="btnSave" ng-click="batchChange()">
            <s:message code="envConfgBatchChange.hq.batchChange" />
        </button>
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
            <s:message code="cmm.save" />
        </button>
        <%--시스템패스워드--%>
        <div class="sb-select dkbr ml5 fr">
            <s:message code="envConfgBatchChange.hq.systemPw" />
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
                id="wjGridEnvConfgBatchChangeHqList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.hqOfficeNm"/>" binding="hqOfficeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <%-- 텍스트일때 / DIRCT_IN_YN = Y --%>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.envstVal"/>" binding="envstVal" width="100" align="center" visible="false"></wj-flex-grid-column>
                <%-- 콤보박스일떄 / DIRCT_IN_YN = N --%>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.envstVal"/>" binding="envstVal" data-map="envstValCdDataMap" width="100" align="center" visible="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.totStoreCnt"/>" binding="totStoreCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="envConfgBatchChange.hq.storeCnt"/>" binding="storeCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    var userId = "${userId}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/envConfgBatchChangeHq.js?ver=20210322.10" charset="utf-8"></script>