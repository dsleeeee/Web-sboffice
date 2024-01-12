<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="sideSdselClassCtrl" id="sideSdselClassView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="kioskSideOption.sideSdselClass"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('sideSdselClassCtrl', 1)">
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
            <th><s:message code="kioskSideOption.sdselTypeFg"/></th>
            <td colspan="3">
                <div class="sb-select w30">
                    <wj-combo-box
                        id="sdselTypeFg2"
                        ng-model="sdselTypeFg"
                        items-source="_getComboData('sdselTypeFg2')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchSdselTypeFg2Combo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <th><s:message code="kioskSideOption.sdselGrpCd"/></th>
            <td><input type="text" id="srchSdselGrpCd2" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
            <th><s:message code="kioskSideOption.sdselGrpNm"/></th>
            <td><input type="text" id="srchSdselGrpNm2" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
        </tr>
        <tr>
            <th><s:message code="kioskSideOption.sdselClassCd"/></th>
            <td><input type="text" id="srchSdselClassCd" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
            <th><s:message code="kioskSideOption.sdselClassNm"/></th>
            <td><input type="text" id="srchSdselClassNm" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
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
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" align="left" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselGrpCd"/>" binding="sdselGrpCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselGrpNm"/>" binding="sdselGrpNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassCd"/>" binding="sdselClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>" binding="sdselClassNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.en"/>)" binding="sdselClassEnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.cn"/>)" binding="sdselClassCnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.jp"/>)" binding="sdselClassJpNm" align="left" width="150"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="sideSdselClassExcelCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselTypeFg"/>" binding="sdselTypeFg" data-map="sdselTypeFgDataMap" align="left" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselGrpCd"/>" binding="sdselGrpCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselGrpNm"/>" binding="sdselGrpNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassCd"/>" binding="sdselClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>" binding="sdselClassNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.en"/>)" binding="sdselClassEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.cn"/>)" binding="sdselClassCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.sdselClassNm"/>(<s:message code="kioskSideOption.jp"/>)" binding="sdselClassJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="classExcelUpFile"
                ng-model="excelUpFile"
                onchange="angular.element(this).scope().excelFileChanged()"
                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/kioskSideOption/sideSdselClass.js?ver=20240111.01" charset="utf-8"></script>