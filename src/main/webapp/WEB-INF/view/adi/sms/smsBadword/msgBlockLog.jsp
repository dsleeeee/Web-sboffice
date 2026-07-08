<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="msgBlockLogView" name="msgBlockLogView" class="subCon" ng-controller="msgBlockLogCtrl" style="padding: 10px 20px 40px;">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="smsBadwordTab.msgBlockLog"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('msgBlockLogCtrl')" id="nxBtnSearch1">
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
                <%-- 탐지일시 --%>
                <th>
                    <s:message code="smsBadwordTab.regDt"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 차단 원인 유형 --%>
                <th>
                    <s:message code="smsBadwordTab.blockType"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="blockType"
                                ng-model="blockType"
                                items-source="_getComboData('blockType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="blockTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 금칙어 --%>
                <th>
                    <s:message code="smsBadwordTab.keyword"/>
                </th>
                <td>
                    <input type="text" id="triggeredKeyword" ng-model="triggeredKeyword" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- URL --%>
                <th>
                    <s:message code="smsBadwordTab.url"/>
                </th>
                <td>
                    <input type="text" id="triggeredUrl" ng-model="triggeredUrl" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 메시지 상태 --%>
                <th>
                    <s:message code="smsBadwordTab.msgStatus"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="msgStatus"
                                ng-model="msgStatus"
                                items-source="_getComboData('msgStatus')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="msgStatusCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
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
        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 탐지/차단 결과 로그 그리드 --%>
    <div class="w100 mt10">
        <wj-flex-grid
                id="msgBlockLogGrid"
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                is-read-only="false"
                item-formatter="_itemFormatter">
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.id"/>" binding="msgBlockId" width="40" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.blockType"/>" binding="blockType" width="100" is-read-only="true" align="center" data-map="blockTypeDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.badwordId"/>" binding="badwordId" width="60" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.keyword"/>" binding="keyword" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.urlCheckId"/>" binding="urlCheckId" width="60" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.url"/>" binding="url" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.msgStatus"/>" binding="msgStatus" width="80" is-read-only="true" align="center" data-map="msgStatusDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.msgContent"/>" binding="msgContent" is-read-only="true" width="400"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.regDt"/>" binding="regDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.callback"/>" binding="callback" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.sOgnCd"/>" binding="sOgnCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.sUserId"/>" binding="sUserId" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="msgBlockLogCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsBadword/msgBlockLog.js?ver=20260707.01" charset="utf-8"></script>