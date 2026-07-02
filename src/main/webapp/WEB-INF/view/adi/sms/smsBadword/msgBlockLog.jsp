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
                    <s:message code="smsBadwordTab.createdAt"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- 차단원인유형 --%>
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
                <%-- 탐지금칙어 --%>
                <th>
                    <s:message code="smsBadwordTab.triggeredKeyword"/>
                </th>
                <td>
                    <input type="text" id="triggeredKeyword" ng-model="triggeredKeyword" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 악성판정 URL --%>
                <th>
                    <s:message code="smsBadwordTab.triggeredUrl"/>
                </th>
                <td>
                    <input type="text" id="triggeredUrl" ng-model="triggeredUrl" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            <tr>
                <%-- 처리결과 --%>
                <th>
                    <s:message code="smsBadwordTab.status"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="status"
                                ng-model="status"
                                items-source="_getComboData('status')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="statusCombo">
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
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.id"/>" binding="id" width="60" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.accountId"/>" binding="accountId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.messageId"/>" binding="messageId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.smsSendSeq"/>" binding="smsSendSeq" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.blockType"/>" binding="blockType" width="100" is-read-only="true" align="center" data-map="blockTypeDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.triggeredKeywordId"/>" binding="triggeredKeywordId" width="60" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.triggeredKeyword"/>" binding="triggeredKeyword" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.triggeredUrl"/>" binding="triggeredUrl" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.urlCheckSource"/>" binding="urlCheckSource" width="200" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.status"/>" binding="status" width="80" is-read-only="true" align="center" data-map="statusDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.adminReviewId"/>" binding="adminReviewId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.messageSnippet"/>" binding="messageSnippet" is-read-only="true" width="300"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.createdAt"/>" binding="createdAt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsBadword/msgBlockLog.js?ver=20260702.01" charset="utf-8"></script>