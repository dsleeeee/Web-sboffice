<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

<div class="subCon">
    <div ng-controller="naverPlaceEasyLinkCtrl">
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="naverPlaceEasyLink.naverPlaceEasyLink"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('naverPlaceEasyLinkCtrl')" id="nxBtnSearch">
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
                <%-- 구분 --%>
                <th><s:message code="naverPlaceEasyLink.inType"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="inType"
                                ng-model="inType"
                                items-source="_getComboData('inType')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="inTypeCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 사업자번호 --%>
                <th><s:message code="naverPlaceEasyLink.businessNumber"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchBizNo" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
        </div>
        <div class="w100 mt10">
            <div class="wj-gridWrap" id="wjGridUser" style="height: 300px; overflow-x: hidden; overflow-y: hidden; display: block;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessNumber"/>" binding="bizNo" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.inType"/>" binding="inType" data-map="inTypeDataMap" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.uniqueId"/>" binding="uniqueId" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.lastResponseDt"/>" binding="lastResponseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.mpNo"/>" binding="mpNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>01" binding="etc01" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>02" binding="etc02" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>03" binding="etc03" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>04" binding="etc04" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>05" binding="etc05" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>06" binding="etc06" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>07" binding="etc07" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>08" binding="etc08" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>09" binding="etc09" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.etc"/>10" binding="etc10" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.naverStoreNm"/>" binding="naverStoreNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.naverPlaceId"/>" binding="naverPlaceId" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.naverLinkDt"/>" binding="naverLinkDt" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.agreementLastResponseDt"/>" binding="agreementLastResponseDt" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.agreementType"/>" binding="agreementType" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.orderUniqueId"/>" binding="orderUniqueId" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.orderBusinessId"/>" binding="orderBusinessId" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.orderChannelServiceIdTable"/>" binding="orderChannelServiceIdTable" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.orderChannelServiceIdPickup"/>" binding="orderChannelServiceIdPickup" width="210" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <div ng-controller="naverPlaceEasyLinkExcelSampleCtrl">
        <div class="mt10 oh">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4.  업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '정상'인 데이터만 저장됩니다.</p>
            <%-- 간편연동업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()"><s:message code="naverPlaceEasyLink.easyLinkUpload" /></button>
            <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelSampleDownload()"><s:message code="cmm.excel.sampleDown" /></button>
        </div>
        <%-- 양식 샘플 미리보기 --%>
        <div class="w100 mt10">
            <div class="wj-gridWrap" style="height:60px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="place_id[<s:message code="naverPlaceEasyLink.naverPlaceId"/>]" binding="placeId" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="business_name[<s:message code="naverPlaceEasyLink.businessName"/>]" binding="businessName" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="business_number[<s:message code="naverPlaceEasyLink.businessNumber"/>]" binding="businessNumber" width="250" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <div ng-controller="naverPlaceEasyLinkExcelCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" ng-click="delete()"><s:message code="cmm.del" /></button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.save" /></button>
        </div>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:310px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        frozen-columns="2">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.naverPlaceId"/>" binding="placeId" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessName"/>" binding="businessName" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessNumber"/>" binding="businessNumber" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.lastSaleDate"/>" binding="lastSaleDate" width="200" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <div style="display: none" ng-controller="naverPlaceEasyLinkExcelUploadAddCtrl">
        <input type="file" class="form-control" id="excelUpFile"
               ng-model="excelUpFile"
               onchange="angular.element(this).scope().excelFileChanged()"
               accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel.sheet.macroEnabled.12"/>

        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:50px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.naverPlaceId"/>" binding="placeId" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessName"/>" binding="businessName" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.businessNumber"/>" binding="businessNumber" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeCd"/>" binding="hqOfficeCd" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.hqOfficeNm"/>" binding="hqOfficeNm" width="150" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeCd"/>" binding="storeCd" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.storeNm"/>" binding="storeNm" width="150" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.sysStatFg"/>" binding="sysStatFg" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="naverPlaceEasyLink.lastSaleDate"/>" binding="lastSaleDate" width="200" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    var sysStatFg = ${ccu.getCommCodeSelect("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/naverPlaceEasyLink/naverPlaceEasyLink.js?ver=20260911.02" charset="utf-8"></script>
