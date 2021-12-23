<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="logSendCtrl">
    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('logSendCtrl', 1)">
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
            <%-- 본사코드 --%>
            <th><s:message code="cmm.hedofc.cd" /></th>
            <td>
                <input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
            </td>
            <%-- 본사명 --%>
            <th><s:message code="cmm.hedofc.nm" /></th>
            <td>
                <input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
            </td>
        </tr>
        <tr>
            <%-- 매장 코드 --%>
            <th><s:message code="cmm.mrhst.cd" /></th>
            <td>
                <input type="text" id="srchStoreCd" ng-model="storeCd" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
            </td>
            <%-- 매장명 --%>
            <th><s:message code="cmm.mrhst.nm" /></th>
            <td>
                <input type="text" id="srchStoreNm" ng-model="storeNm" class="sb-input w100" size="50" ng-value="" onkeyup="fnNxBtnSearch();">
            </td>
        </tr>
        <tr>
            <%-- 상태 --%>
            <th><s:message code="cmm.status" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchSysStatFg"
                            ng-model="sysStatFg"
                            items-source="_getComboData('sysStatFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">

        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>

        <%-- 전송 --%>
        <button class="btn_skyblue ml5 fr" id="btnSend" ng-click="logSend()"><s:message code="logSend.send" /></button>

    </div>

        <%-- POS 시스템 로그 송신 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                <wj-flex-grid
                        control="flex"
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        initialized="initGrid(s,e)"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.posNo"/>" binding="posNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.posVerNo"/>" binding="posVerNo" align="center" width="200" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="logSend.dbSendYn"/>" binding="dbSendYn" data-map="dbSendYnDataMap" align="center" width="100" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>

        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="logSendCtrlPager" data-size="10">
            </ul>
        </div>
    <%--//페이지 리스트--%>

</div>

<script>
    var sysStatFg   = ${ccu.getCommCodeExcpAll("005")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/sys/admin/logSend/logSend.js?ver=20200825.12" charset="utf-8"></script>