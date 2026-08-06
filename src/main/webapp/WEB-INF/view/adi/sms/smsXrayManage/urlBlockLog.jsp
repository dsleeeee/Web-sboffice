<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="urlBlockLogView" name="urlBlockLogView" class="subCon" ng-controller="urlBlockLogCtrl" style="padding: 10px 20px 40px;">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="smsXrayTab.urlBlockLog"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('urlBlockLogCtrl',1)" id="nxBtnSearch2">
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
                    <s:message code="smsXrayTab.regDt"/>
                </th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn"><input id="urlBlockLogSrchStartDate" class="w110px"></span>
                        <span class="rg">~</span>
                        <span class="txtIn"><input id="urlBlockLogSrchEndDate" class="w110px"></span>
                    </div>
                </td>
                <%-- URL 유형 --%>
                <th>
                    <s:message code="smsXrayTab.urlType"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="urlBlockLogUrlType"
                                ng-model="urlType"
                                items-source="_getComboData('urlBlockLogUrlType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="urlBlockLogUrlTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- URL --%>
                <th>
                    <s:message code="smsXrayTab.url"/>
                </th>
                <td>
                    <input type="text" id="urlBlockLogChkUrl" ng-model="chkUrl" class="sb-input w100"/>
                </td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 조회조건 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
    </div>

    <%-- 탐지/차단결과 로그 그리드 --%>
    <div class="w100 mt10">
        <wj-flex-grid
                id="urlBlockLogGrid"
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                is-read-only="true"
                item-formatter="_itemFormatter">
            <wj-flex-grid-column header="<s:message code="smsXrayTab.id"/>" binding="msgBlockId" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.urlType"/>" binding="urlType" width="90" is-read-only="true" align="center" data-map="urlTypeDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.xrayId"/>" binding="xrayId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.url"/>" binding="xrayUrl" width="220" is-read-only="true" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.msgStatus"/>" binding="msgStatus" width="80" is-read-only="true" align="center" data-map="msgStatusDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.msgContent"/>" binding="msgContent" is-read-only="true" width="350"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.regDt"/>" binding="regDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.callback"/>" binding="callback" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.sOgnCd"/>" binding="sOgnCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsXrayTab.sUserId"/>" binding="sUserId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="urlBlockLogCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsXrayManage/urlBlockLog.js?ver=20260804.01" charset="utf-8"></script>
