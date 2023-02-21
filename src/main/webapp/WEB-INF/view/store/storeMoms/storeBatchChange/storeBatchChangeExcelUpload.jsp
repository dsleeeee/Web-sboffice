<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" id ="storeBatchChangeExcelUploadView" style="display: none">
    <div ng-controller="storeBatchChangeExcelUploadCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="storeBatchChange.storeBatchChangeExcelUpload" /></a>
        </div>

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '매장정보'가 업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '검증성공'인 매장만 저장됩니다.</p>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
           <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
        </div>

        <div class="wj-gridWrap" style="display: none">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="excelFlex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    ime-enabled="true">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeBatchChange.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.branchCd"/>" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsTeam"/>" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsAcShop"/>" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsAreaFg"/>" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsCommercial"/>" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsShopType"/>" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeBatchChange.momsStoreManageType"/>" width="90" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 그리드 --%>
    <div ng-controller="storeExcelUploadCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="storeExcelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" ng-click="delete()">
                <s:message code="cmm.del" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnStoreExcelUploadSave" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
        </div>
        <%-- 저장 --%>
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
                    <wj-flex-grid-column header="<s:message code="simpleProd.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.storeCd"/>" binding="storeCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.branchCd"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeBatchChange/storeBatchChangeExcelUpload.js?ver=20230118.01" charset="utf-8"></script>

<%-- 상품엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeBatchChange/storeBatchChangeExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
