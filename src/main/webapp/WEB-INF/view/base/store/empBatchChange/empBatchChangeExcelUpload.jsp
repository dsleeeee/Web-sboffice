<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" id ="empBatchChangeExcelUploadView" style="display: none">
    <div ng-controller="empBatchChangeExcelUploadCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="empBatchChange.empBatchChangeExcelUpload" /></a>
        </div>

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '사원정보'가 업로드되면 '저장'을 클릭하여 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 업로드된 검증결과가 전체 '검증성공'일때만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15 red">※ 검증결과가 실패인 경우</p>
            <p class="tl s14 mt5 lh15 red">- 아래 해당 정보를 수정하신 후에 엑셀다운로드 하여 다시 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15 red">※ 업로드시 '사원코드, 지사, 팀별, AC점포별, 지역구분, 상권, 정포유형, 매장관리타입' 기준으로 처리됩니다.</p>
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
                <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.empNm"/>" binding="empNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.branchCd"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="empBatchChange.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
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
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNm"/>" binding="empNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.branchCd"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empBatchChange/empBatchChangeExcelUpload.js?ver=20230327.02" charset="utf-8"></script>

<%-- 상품엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/store/empBatchChange/empBatchChangeExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>