<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" id ="storeBatchChangeExcelUploadView" style="display: none;padding: 10px 20px 40px;">
    <div ng-controller="storeBatchChangeExcelUploadCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="storeBatchChange.storeBatchChangeExcelUpload" /></a>
        </div>

        <div class="mt10 oh">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '매장정보'가 업로드되면 '저장'을 클릭하여 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 업로드된 검증결과가 전체 '검증성공'일때만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15 red">※ 검증결과가 실패인 경우</p>
            <p class="tl s14 mt5 lh15 red">- 아래 해당 정보를 수정하신 후에 엑셀다운로드 하여 다시 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15 red">※ 업로드시 '매장코드, 그룹, 팀별, AC점포별, 지역구분, 상권, 정포유형, 매장관리타입, 매장그룹
            <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                , 매장그룹2, 매장그룹3, 매장그룹4, 매장그룹5
            </c:if>
            ' 기준으로 처리됩니다.</p>

            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="cmm.excel.excelUpload" />
            </button>
           <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="sampleDownload()">
                <s:message code="cmm.excel.sampleDown" />
            </button>
        </div>

        <div class="wj-gridWrap" style="display: none;">
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
                <wj-flex-grid-column header="<s:message code="storeBatchChange.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg01"/>" binding="momsStoreFg01" data-map="momsStoreFg01DataMap" width="90" align="center"></wj-flex-grid-column>
                <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg02"/>" binding="momsStoreFg02" data-map="momsStoreFg02DataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg03"/>" binding="momsStoreFg03" data-map="momsStoreFg03DataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg04"/>" binding="momsStoreFg04" data-map="momsStoreFg04DataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg05"/>" binding="momsStoreFg05" data-map="momsStoreFg05DataMap" width="90" align="center"></wj-flex-grid-column>
                </c:if>
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
            <%--<button class="btn_skyblue ml5 fr" ng-click="delete()">
                <s:message code="cmm.del" />
            </button>--%>
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
                    <wj-flex-grid-column header="<s:message code="storeBatchChange.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg01"/>" binding="momsStoreFg01" data-map="momsStoreFg01DataMap" width="90" align="center"></wj-flex-grid-column>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg02"/>" binding="momsStoreFg02" data-map="momsStoreFg02DataMap" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg03"/>" binding="momsStoreFg03" data-map="momsStoreFg03DataMap" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg04"/>" binding="momsStoreFg04" data-map="momsStoreFg04DataMap" width="90" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg05"/>" binding="momsStoreFg05" data-map="momsStoreFg05DataMap" width="90" align="center"></wj-flex-grid-column>
                    </c:if>
                    <!-- 변경 확인용 -->
                    <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="oldBranchCd" data-map="branchCdDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="oldMomsTeam" data-map="momsTeamDataMap" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="oldMomsAcShop" data-map="momsAcShopDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="oldMomsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="oldMomsCommercial" data-map="momsCommercialDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="oldMomsShopType" data-map="momsShopTypeDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="oldMomsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg01"/>" binding="oldMomsStoreFg01" data-map="momsStoreFg01DataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg02"/>" binding="oldMomsStoreFg02" data-map="oldMomsStoreFg02DataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg03"/>" binding="oldMomsStoreFg03" data-map="oldMomsStoreFg03DataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg04"/>" binding="oldMomsStoreFg04" data-map="oldMomsStoreFg04DataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg05"/>" binding="oldMomsStoreFg05" data-map="oldMomsStoreFg05DataMap" width="90" align="center" visible="false"></wj-flex-grid-column>
                    </c:if>
                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeBatchChange/storeBatchChangeExcelUpload.js?ver=20241111.01" charset="utf-8"></script>

<%-- 상품엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeBatchChange/storeBatchChangeExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>