<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon" ng-controller="funcKeyCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('funcKeyCtrl',1)" id="nxBtnSearch">
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
                <%-- 기능키번호 --%>
                <th><s:message code="funcKey.keyNo" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchFnkeyNo" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 기능키명 --%>
                <th><s:message code="funcKey.keyNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchFnkeyNm" onkeyup="fnNxBtnSearch();" />
                </td>
            </tr>
            <tr>
                <%-- 기능구분 --%>
                <th><s:message code="funcKey.keyFg"/></th>
                <td>
                    <div class="sb-select">
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
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀업로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
            <s:message code="cmm.excel.excelUpload" />
        </button>
        <%-- 양식다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
            <s:message code="cmm.excel.sampleDown" />
        </button>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
            <s:message code="cmm.excel.down" />
        </button>
        <%-- 삭제 --%>
        <button class="btn_skyblue ml5 fr" ng-click="deleteRow()">
            <s:message code="cmm.delete" />
        </button>
        <%-- 저장 --%>
        <button class="btn_skyblue fr" ng-click="saveRow()">
            <s:message code="cmm.save" />
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
                <wj-flex-grid-column header="<s:message code="funcKey.fnkeyFg"/>" binding="fnkeyFg" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="funcKey.nmCodeNm"/>" binding="nmcodeNm" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="funcKey.keyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="funcKey.keyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="funcKey.version"/>" binding="fnkeyNoVersion" align="left" width="100" data-map="versionDataMap"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>


<%-- 양식다운로드 관련 --%>
<div style="display: none;" ng-controller="funcKeyExcelDownCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="funcKey.fnkeyFg"/>" binding="fnkeyFg" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.nmCodeNm"/>" binding="nmcodeNm" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.keyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.keyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.version"/>" binding="fnkeyNoVersion" align="left" width="100" data-map="versionDataMap"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>
<%-- 엑셀 업로드 관련 --%>
<div style="display: none;" ng-controller="funcKeyExcelUploadCtrl">
    <input type="file" class="form-control" id="funcKeyExcelUpFile"
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
            <wj-flex-grid-column header="<s:message code="funcKey.fnkeyFg"/>" binding="fnkeyFg" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.nmCodeNm"/>" binding="nmcodeNm" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.keyNo"/>" binding="fnkeyNo" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.keyNm"/>" binding="fnkeyNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="funcKey.version"/>" binding="fnkeyNoVersion" align="left" width="100" data-map="versionDataMap"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>
</div>
<script type="text/javascript">

    // 기능구분 콤보박스 셋팅
    var fnkeyFgData = ${ccu.getCommCode("026")};
    var nmcodeGrpCdList = ${nmcodeGrpCdList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/admin/funcKey/funcKey.js?ver=20240110.01" charset="utf-8"></script>
