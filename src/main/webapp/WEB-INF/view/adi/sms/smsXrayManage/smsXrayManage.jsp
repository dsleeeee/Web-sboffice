<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div id="smsXrayManageView" name="smsXrayManageView" class="subCon" ng-controller="smsXrayManageCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="smsXrayTab.smsXrayManage"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('smsXrayManageCtrl',1)" id="nxBtnSearch">
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
                <%-- 소속코드 --%>
                <th><s:message code="smsXrayManage.orgnCd"/></th>
                <td>
                    <input type="text" id="orgnCd" ng-model="orgnCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 소속명 --%>
                <th><s:message code="smsXrayManage.orgnNm"/></th>
                <td>
                    <input type="text" id="orgnNm" ng-model="orgnNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 요청ID --%>
                <th><s:message code="smsXrayManage.reqId"/></th>
                <td>
                    <input type="text" id="reqId" ng-model="reqId" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 요청자명 --%>
                <th><s:message code="smsXrayManage.reqNm"/></th>
                <td>
                    <input type="text" id="reqNm" ng-model="reqNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            <tr>
                <%-- 승인여부 --%>
                <th><s:message code="smsXrayManage.apprFg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="apprFg"
                                ng-model="apprFg"
                                items-source="_getComboData('apprFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="apprFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code='cmm.save'/></button>
    </div>

    <%-- SMS Xray 목록 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="smsXrayManageGrid"
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    is-read-only="false"
                    item-formatter="_itemFormatter">
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.orgnCd"/>" binding="orgnCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.orgnNm"/>" binding="orgnNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.reqId"/>" binding="userId" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.reqNm"/>" binding="userNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.telNo"/>" binding="telNo" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.sendUrl"/>" binding="sendUrl" width="220" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.apprFg"/>" binding="apprFg" width="80" is-read-only="false" align="center" data-map="apprFgDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.usePeriod"/>" binding="usePeriod" width="180" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.apprNm"/>" binding="apprNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.apprId"/>" binding="apprId" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.apprReason"/>" binding="apprReason" width="150" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.regDt"/>" binding="regDt" width="140" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="smsXrayManage.useStartDate"/>" binding="useStartDate" width="140" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="smsXrayManageCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsXrayManage/smsXrayManage.js?ver=20260723.01" charset="utf-8"></script>
