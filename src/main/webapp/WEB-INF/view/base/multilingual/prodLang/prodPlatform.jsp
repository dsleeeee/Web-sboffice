<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="prodPlatformCtrl" id="prodPlatformView" style="display: none;">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prodLang.platform"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="nxBtnSearch3" ng-click="_pageView('prodPlatformCtrl', 1)">
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
            <%-- 상품코드 --%>
            <th><s:message code="cmm.prodCd" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdCd3" ng-model="prodCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="cmm.prodNm" /></th>
            <td>
                <input type="text" class="sb-input w100" id="srchProdNm3" ng-model="prodNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 상품분류 --%>
            <th><s:message code="prodLang.prodClass"/></th>
            <td>
                <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float:left"
                       placeholder="<s:message code="prodLang.prodClass" /> 선택" readonly/>
                <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <%-- 사용여부 --%>
            <th><s:message code="prodLang.useYn" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="useYn3"
                            ng-model="useYn"
                            items-source="_getComboData('useYn3')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchUseYnCombo3">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 플랫폼조회 --%>
            <th><s:message code="prodLang.platformFg" /></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="platformFg3"
                            ng-model="platformFg"
                            items-source="_getComboData('platformFg3')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="srchPlatformFgCombo3">
                    </wj-combo-box>
                </div>
            </td>
            <th></th>
            <td></td>
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
                <wj-flex-grid-column header="<s:message code="prodLang.hqBrandNm"/>" binding="hqBrandNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.prodClassNm"/>" binding="prodClassNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1" binding="prodKoNmDisp1" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2" binding="prodKoNmDisp2" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.en"/>)" binding="prodEnNmDisp1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.en"/>)" binding="prodEnNmDisp2" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.cn"/>)" binding="prodCnNmDisp1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.cn"/>)" binding="prodCnNmDisp2" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.jp"/>)" binding="prodJpNmDisp1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.jp"/>)" binding="prodJpNmDisp2" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1" binding="prodKoNmDispKiosk1" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2" binding="prodKoNmDispKiosk2" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.en"/>)" binding="prodEnNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.en"/>)" binding="prodEnNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.cn"/>)" binding="prodCnNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.cn"/>)" binding="prodCnNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.jp"/>)" binding="prodJpNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.jp"/>)" binding="prodJpNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
    </div>
</div>

<%-- 양식다운로드/엑셀업로드 관련 --%>
<div style="display: none;" ng-controller="prodPlatformExcelCtrl">

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
            <wj-flex-grid-column header="<s:message code="prodLang.hqBrandNm"/>" binding="hqBrandNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.prodClassNm"/>" binding="prodClassNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1" binding="prodKoNmDisp1" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2" binding="prodKoNmDisp2" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.en"/>)" binding="prodEnNmDisp1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.en"/>)" binding="prodEnNmDisp2" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.cn"/>)" binding="prodCnNmDisp1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.cn"/>)" binding="prodCnNmDisp2" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.jp"/>)" binding="prodJpNmDisp1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.pos"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.jp"/>)" binding="prodJpNmDisp2" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1" binding="prodKoNmDispKiosk1" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2" binding="prodKoNmDispKiosk2" align="left" width="200" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.en"/>)" binding="prodEnNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.en"/>)" binding="prodEnNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.cn"/>)" binding="prodCnNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.cn"/>)" binding="prodCnNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>1(<s:message code="prodLang.jp"/>)" binding="prodJpNmDispKiosk1" align="left" width="200"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodLang.kiosk"/><s:message code="cmm.prodNm"/>2(<s:message code="prodLang.jp"/>)" binding="prodJpNmDispKiosk2" align="left" width="200"></wj-flex-grid-column>

        </wj-flex-grid>
    </div>

    <input type="file" class="form-control" id="prodPlatformExcelUpFile"
           ng-model="excelUpFile"
           onchange="angular.element(this).scope().excelFileChanged()"
           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/prodLang/prodPlatform.js?ver=20260728.01" charset="utf-8"></script>
