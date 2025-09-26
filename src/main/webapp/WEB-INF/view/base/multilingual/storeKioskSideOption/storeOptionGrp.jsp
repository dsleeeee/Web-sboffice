<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="storeOptionGrpCtrl" id="storeOptionGrpView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeKioskSideOption.optionGrp"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch3" ng-click="_pageView('storeOptionGrpCtrl', 1)">
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
            <th><s:message code="storeKioskSideOption.optionGrpCd"/></th>
            <td><input type="text" id="srchOptionGrpCd" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/></td>
            <th><s:message code="storeKioskSideOption.optionGrpNm"/></th>
            <td><input type="text" id="srchOptionGrpNm" class="sb-input w100" onkeyup="fnNxBtnSearch('3');"/></td>
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
                <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpCd"/>" binding="optionGrpCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>" binding="optionGrpNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.en"/>)" binding="optionGrpEnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.cn"/>)" binding="optionGrpCnNm" align="left" width="150"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.jp"/>)" binding="optionGrpJpNm" align="left" width="150"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="storeOptionGrpExcelCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpCd"/>" binding="optionGrpCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>" binding="optionGrpNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.en"/>)" binding="optionGrpEnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.cn"/>)" binding="optionGrpCnNm" align="left" width="150"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeKioskSideOption.optionGrpNm"/>(<s:message code="storeKioskSideOption.jp"/>)" binding="optionGrpJpNm" align="left" width="150"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="optionGrpExcelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/storeKioskSideOption/storeOptionGrp.js?ver=20250925.01" charset="utf-8"></script>