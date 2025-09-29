<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="storeProdInfoCtrl" id="storeProdInfoView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="storeProdLang.prodInfo"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('storeProdInfoCtrl', 1)">
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
            <th><s:message code="storeProdLang.prodCd"/></th>
            <td><input type="text" id="srchProdCd2" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
            <th><s:message code="storeProdLang.prodNm"/></th>
            <td><input type="text" id="srchProdNm2" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/></td>
        </tr>
        <tr>
            <th><s:message code="storeProdLang.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                       placeholder="<s:message code="storeProdLang.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <th><s:message code="storeProdLang.useYn"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                        id="useYn2"
                        ng-model="useYn"
                        items-source="_getComboData('useYn2')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchUseYn2Combo">
                    </wj-combo-box>
                </div>
            </td>
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
                item-formatter="_itemFormatter"
                ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodClassNm"/>" binding="prodClassNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodCd"/>" binding="prodCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodNm"/>" binding="prodNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>" binding="prodInfo" align="left" width="260" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.en"/>)" binding="prodEnInfo" align="left" width="260"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.cn"/>)" binding="prodCnInfo" align="left" width="260"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.jp"/>)" binding="prodJpInfo" align="left" width="260"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>

</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="storeProdInfoExcelCtrl">

    <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodClassNm"/>" binding="prodClassNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodCd"/>" binding="prodCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodNm"/>" binding="prodNm" align="left" width="150" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>" binding="prodInfo" align="left" width="260" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.en"/>)" binding="prodEnInfo" align="left" width="260"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.cn"/>)" binding="prodCnInfo" align="left" width="260"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeProdLang.prodInfo"/>(<s:message code="storeProdLang.jp"/>)" binding="prodJpInfo" align="left" width="260"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="prodInfoExcelUpFile"
                ng-model="excelUpFile"
                onchange="angular.element(this).scope().excelFileChanged()"
                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/storeProdLang/storeProdInfo.js?ver=20250929.01" charset="utf-8"></script>