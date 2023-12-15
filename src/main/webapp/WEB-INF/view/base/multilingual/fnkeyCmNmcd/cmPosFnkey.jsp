<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="cmPosFnkeyCtrl" id="cmPosFnkeyView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="fnkeyCmNmcd.cmPosFnkey"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_pageView('cmPosFnkeyCtrl', 1)">
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
            <th><s:message code="fnkeyCmNmcd.fnkeyFg"/></th>
            <td colspan="3">
                <div class="sb-select w30">
                    <wj-combo-box
                        id="fnkeyFg"
                        ng-model="fnkeyFg"
                        items-source="_getComboData('fnkeyFg')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchFnkeyFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="fnkeyCmNmcd.fnkeyNo"/></th>
            <td><input type="text" id="srchFnkeyNo" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
            <th><s:message code="fnkeyCmNmcd.fnkeyNm"/></th>
            <td><input type="text" id="srchFnkeyNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/></td>
        </tr>
        </tbody>
    </table>

    <div class="updownSet oh mt10">
        <%-- 저장 --%>
        <button class="btn_skyblue" ng-click="saveRow()" >
            <s:message code="cmm.save" />
        </button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
        <%-- 양식다운로드 --%>
        <button class="btn_skyblue" ng-click="sampleDownload()">
            <s:message code="cmm.excel.sampleDown" />
        </button>
        <%-- 엑셀업로드 --%>
        <button class="btn_skyblue" ng-click="excelUpload()">
            <s:message code="cmm.excel.excelUpload" />
        </button>
    </div>

    <%-- 위즈모 테이블 --%>
    <div class="wj-TblWrap mt5">
        <div class="wj-gridWrap" style="height: 420px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                control="flex"
                autoGenerateColumns="false"
                selection-mode="Row"
                initialized="initGrid(s,e)"
                items-source="data"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>

<%-- 양식다운로드 관련 --%>
<div style="display: none;" ng-controller="cmPosFnkeyExcelDownCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<%-- 엑셀 업로드 관련 --%>
<div style="display: none;" ng-controller="cmPosFnkeyExcelUploadCtrl">
    <input type="file" class="form-control" id="cmPosFnkeyExcelUpFile"
            ng-model="excelUpFile"
            onchange="angular.element(this).scope().excelFileChanged()"
            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.cd"/>" binding="fnkeyFg" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyFg"/>" binding="fnkeyFgNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.en"/>)" binding="fnkeyEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.cn"/>)" binding="fnkeyCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="fnkeyCmNmcd.fnkeyNm"/>(<s:message code="fnkeyCmNmcd.jp"/>)" binding="fnkeyJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<script type="text/javascript">

    // 기능구분 콤보박스 셋팅
    var fnkeyFgData = ${ccu.getCommCode("026")};

    //
    var nmcodeGrpCdList = ${nmcodeGrpCdList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/fnkeyCmNmcd/cmPosFnkey.js?ver=20231215.01" charset="utf-8"></script>