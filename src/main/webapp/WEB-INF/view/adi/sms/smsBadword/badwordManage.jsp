<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="badwordManageView" name="badwordManageView" class="subCon" ng-controller="badwordManageCtrl" style="padding: 10px 20px 40px;">

    <%-- 조회조건 --%>
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="smsBadwordTab.badwordManage"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('badwordManageCtrl')" id="nxBtnSearch">
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
                <%-- 금칙어 --%>
                <th>
                    <s:message code="smsBadwordTab.keyword"/>
                </th>
                <td>
                    <input type="text" id="keyword" ng-model="keyword" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 카테고리 --%>
                <th>
                    <s:message code="smsBadwordTab.category"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="category"
                                ng-model="category"
                                items-source="_getComboData('category')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="categoryCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매칭방식 --%>
                <th>
                    <s:message code="smsBadwordTab.matchType"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="matchType"
                                ng-model="matchType"
                                items-source="_getComboData('matchType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="matchTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 처리방식 --%>
                <th>
                    <s:message code="smsBadwordTab.severity"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="severity"
                                ng-model="severity"
                                items-source="_getComboData('severity')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="severityCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 사용여부 --%>
                <th>
                    <s:message code="smsBadwordTab.useYn"/>
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="useYn"
                                ng-model="useYn"
                                items-source="_getComboData('useYn')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="useYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <%--<wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>--%>
            <%-- 조회조건 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
            <button class="btn_skyblue ml5 fr" ng-click="del()"><s:message code='cmm.del'/></button>
            <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code='cmm.save'/></button>
            <button class="btn_skyblue ml5 fr" ng-click="addRow()"><s:message code='cmm.add'/></button>
    </div>

    <%-- 금칙어 목록 그리드 --%>
    <div class="w100 mt10">
        <wj-flex-grid
                id="badwordManageGrid"
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                is-read-only="false"
                item-formatter="_itemFormatter">
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.id"/>" binding="badwordId" width="40" is-read-only="true" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.keyword"/>" binding="keyword" width="200" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.keywordNormalized"/>" binding="keywordNormalized" width="200" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.category"/>" binding="category" width="150" is-read-only="false" align="center" data-map="categoryDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.matchType"/>" binding="matchType" width="100" is-read-only="false" align="center" data-map="matchTypeDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.severity"/>" binding="severity" width="100" is-read-only="false" align="center" data-map="severityDataMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.source"/>" binding="source" width="200" is-read-only="false" align="left"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="smsBadwordTab.useYn"/>" binding="useYn" width="100" is-read-only="false" align="center" data-map="useYnDataMap"></wj-flex-grid-column>
        </wj-flex-grid>
    </div>

    <%-- 페이지 리스트 --%>
    <%--<div class="pageNum mt20">
        &lt;%&ndash; id &ndash;%&gt;
        <ul id="badwordManageCtrlPager" data-size="10">
        </ul>
    </div>--%>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript" src="/resource/solbipos/js/adi/sms/smsBadword/badwordManage.js?ver=20260707.01" charset="utf-8"></script>