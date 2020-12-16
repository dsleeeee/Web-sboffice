<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="prodEnvstVal" value="${prodEnvstVal}" />
<c:set var="prodNoEnvFg" value="${prodNoEnvFg}" />

<div class="contents" id ="divProdExcelUploadAuth" style="display: none">
    <div class="elseCon">
        <p class="lgTxt">죄송합니다.<br />요청하신 페이지에 권한이 없습니다.</p>
        <p class="smTxt mt20">
            본사 환경설정 (20 : 본사통제구분-상품등록) 이 <label id="lblProdExcelUploadAuth"></label> 입니다.<br />
            해당 페이지에 접근할 수 없습니다.<br />
            ${sessionScope.sessionInfo.currentMenu.resrceCd}
        </p>
    </div>
</div>

<div class="subCon" id ="divProdExcelUpload" style="display: none">

    <div ng-controller="prodExcelUploadCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:none;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('prodExcelUploadCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <div class="mt10 oh sb-select dkbr">
            <p class="tl s14 mt5 lh15">1. '양식다운로드' 버튼을 클릭하여 양식을 다운받아주세요.</p>
            <p class="tl s14 mt5 lh15">2. 다운받은 양식을 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">- '양식 샘플 미리보기'를 꼭 참고해주세요.</p>
            <p class="tl s14 mt5 lh15">- 상품분류는 '▶'을 사용하여 단계별로 입력해주세요. (예시) [1단계] : 대분류 / [2단계] : 대분류▶중분류 / [3단계] : 대분류▶중분류▶소분류</p>
            <p class="tl s14 mt5 lh15">- 상품유형, 판매상품여부, 거래처, 발주상품유형, 발주단위, 과세여부, 재고관리여부는 '양식 샘플 미리보기'에 ▼ 클릭하여 입력해주세요.</p>
            <p class="tl s14 mt5 lh15">3. '엑셀업로드' 버튼을 클릭하여 업로드 해주세요.</p>
            <p class="tl s14 mt5 lh15">4. '상품내역'이 업로드되면 '저장'을 클릭하여 검증 및 저장을 해주세요.</p>
            <p class="tl s14 mt5 lh15">- 검증결과가 '검증성공'인 상품만 저장됩니다.</p>
            <p class="tl s14 mt5 lh15">- 상품은 5000개 이하로 업로드해주세요.</p>
            <%-- 엑셀업로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelUpload()">
                <s:message code="prodExcelUpload.excelUpload" />
            </button>
           <%-- 양식다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                <s:message code="prodExcelUpload.sampleDownload" />
            </button>
        </div>

        <%-- 그리드 --%>
        <%-- 양식 샘플 미리보기--%>
        <s:message code="prodExcelUpload.sample" />
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:70px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <c:if test="${prodNoEnvFg == 'MANUAL'}">
                        <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodCd"/>" binding="prodCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodNm"/>" binding="prodNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodClassCd"/>" binding="prodClassCd" data-map="prodClassCdDataMap" width="150" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleUprc"/>" binding="saleUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendrCd"/>" binding="vendrCd" data-map="vendrCdDataMap" width="75" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.splyUprc"/>" binding="splyUprc" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poProdFg"/>" binding="poProdFg" data-map="poProdFgDataMap" width="125" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitFg"/>" binding="poUnitFg" data-map="poUnitFgDataMap" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitQty"/>" binding="poUnitQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poMinQty"/>" binding="poMinQty" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.barCd"/>" binding="barCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="75" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.stockProdYn"/>" binding="stockProdYn" data-map="stockProdYnDataMap" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.costUprc"/>" binding="costUprc" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.safeStockQty"/>" binding="safeStockQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.startStockQty"/>" binding="startStockQty" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.remark"/>" binding="remark" width="60" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 그리드 --%>
    <div ng-controller="prodExcelUploadProdCtrl">
        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="prodExcelDownload()">
                <s:message code="cmm.excel.down" />
            </button>
            <%-- 삭제 --%>
            <button class="btn_skyblue ml5 fr" ng-click="delete()">
                <s:message code="cmm.del" />
            </button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnProdExcelUploadSave" ng-click="save()">
                <s:message code="cmm.save" />
            </button>
            <span class="s14 bk fr lh25 mr10" >
                <%-- 상품명 중복체크 --%>
                <input type="checkbox" id="chkProdNm" ng-model="isChecked" />
                <label for="chkProdNm">
                    <s:message code="prodExcelUpload.chkProdNm" />
                </label>
            </span>
        </div>
        <%-- 상품내역--%>
        <s:message code="prodExcelUpload.prodList" />
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
                    frozen-columns="2">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <c:if test="${prodNoEnvFg == 'MANUAL'}">
                        <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodCd"/>" binding="prodCd" width="70" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodNm"/>" binding="prodNm" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodClassCd"/>" binding="prodClassCd" data-map="prodClassCdDataMap" width="150" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.saleUprc"/>" binding="saleUprc" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendrCd"/>" binding="vendrCd" data-map="vendrCdDataMap" width="75" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.splyUprc"/>" binding="splyUprc" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poProdFg"/>" binding="poProdFg" data-map="poProdFgDataMap" width="125" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitFg"/>" binding="poUnitFg" data-map="poUnitFgDataMap" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poUnitQty"/>" binding="poUnitQty" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.poMinQty"/>" binding="poMinQty" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.barCd"/>" binding="barCd" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="75" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.stockProdYn"/>" binding="stockProdYn" data-map="stockProdYnDataMap" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.costUprc"/>" binding="costUprc" width="70" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.safeStockQty"/>" binding="safeStockQty" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.startStockQty"/>" binding="startStockQty" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.remark"/>" binding="remark" width="60"  align="center"></wj-flex-grid-column>

                    <%--상품 저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.chkProdNm"/>" binding="chkProdNm" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.pointSaveYn"/>" binding="pointSaveYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.prodTipYn"/>" binding="prodTipYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.sideProdYn"/>" binding="sideProdYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.setProdFg"/>" binding="setProdFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.lastCostUprc"/>" binding="lastCostUprc" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.splyUprcUseYn"/>" binding="splyUprcUseYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prodExcelUpload.useYn"/>" binding="useYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script>
    // String 형식
    var prodEnvstVal = "${prodEnvstVal}";
    var prodNoEnvFg = "${prodNoEnvFg}";

    // List 형식("" 안붙임)
    var vendrComboList = ${vendrComboList};
    var prodClassComboList = ${prodClassComboList};

    <%-- 상품유형 구분 --%>
    var prodTypeFgData = ${ccu.getCommCodeExcpAll("008")};
    <%-- 판매상품여부 구분 --%>
    var saleProdYnData = ${ccu.getCommCodeExcpAll("067")};
    <%-- 발주상품 구분 --%>
    var poProdFgData = ${ccu.getCommCodeExcpAll("092")};
    <%-- 발주단위 구분 --%>
    var poUnitFgData = ${ccu.getCommCodeExcpAll("093")};
    <%-- 과세여부 구분 --%>
    var vatFgData = ${ccu.getCommCodeExcpAll("039")};
    <%-- 재고관리여부 구분 --%>
    var stockProdYnData = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodExcelUpload/prodExcelUpload.js?ver=20201214.03" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 상품엑셀업로드 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prodExcelUpload/prodExcelUploadAdd.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>