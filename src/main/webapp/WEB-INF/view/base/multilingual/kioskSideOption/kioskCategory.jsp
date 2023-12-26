<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="kioskCategoryCtrl" id="kioskCategoryView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="kioskSideOption.kioskCategory"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('kioskCategoryCtrl', 1)">
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
            <th><s:message code="kioskSideOption.keyMapGrp"/></th>
            <td colspan="3">
                <div class="sb-select w30">
                    <wj-combo-box
                        id="tuClsType"
                        ng-model="tuClsType"
                        items-source="_getComboData('tuClsType')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchTuClsTypeCombo"
                        selected-index-changed="getKioskCategoryList()">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="updownSet oh mt10">
        <%-- 저장 --%>
        <button class="btn_skyblue" id="btnSaveRow" ng-click="saveRow()" >
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
                <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpCd"/>" binding="tuClsType" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpNm"/>" binding="tuClsTypeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryCd"/>" binding="tuClsCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>" binding="tuClsNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.en"/>)" binding="tuClsEnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.cn"/>)" binding="tuClsCnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.jp"/>)" binding="tuClsJpNm" align="left" width="150"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<%-- 양식다운로드 관련 --%>
<div style="display: none;" ng-controller="kioskCategoryExcelDownCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpCd"/>" binding="tuClsType" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpNm"/>" binding="tuClsTypeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryCd"/>" binding="tuClsCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>" binding="tuClsNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.en"/>)" binding="tuClsEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.cn"/>)" binding="tuClsCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.jp"/>)" binding="tuClsJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<%-- 엑셀 업로드 관련 --%>
<div style="display: none;" ng-controller="kioskCategoryExcelUploadCtrl">
    <input type="file" class="form-control" id="excelUpFile"
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
            <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpCd"/>" binding="tuClsType" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.keyMapGrpNm"/>" binding="tuClsTypeNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryCd"/>" binding="tuClsCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>" binding="tuClsNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.en"/>)" binding="tuClsEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.cn"/>)" binding="tuClsCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="kioskSideOption.categoryNm"/>(<s:message code="kioskSideOption.jp"/>)" binding="tuClsJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>

<style>
    /* 중문, 일문 엑셀업로드 font */
    .chinese-excel-form {
        font-family: "Microsoft YaHei";
    }

    .japanese-excel-form {
        font-family: "Meiryo";
    }
</style>

<script type="text/javascript">
    // 키오스크 키맵그룹 목록
    var kioskTuClsTypeList = ${kioskTuClsTypeList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/kioskSideOption/kioskCategory.js?ver=20231222.01" charset="utf-8"></script>