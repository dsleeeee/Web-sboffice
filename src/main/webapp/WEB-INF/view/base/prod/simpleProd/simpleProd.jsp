<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="contents" id ="divSimpleProdAuth" style="display: none">
    <div class="elseCon">
        <p class="lgTxt">죄송합니다.<br />요청하신 페이지에 권한이 없습니다.</p>
        <p class="smTxt mt20">
            본사 환경설정 (0042 : 상품생성설정) 이 <label id="lblSimpleProdAuth"></label> 입니다.<br />
            해당 페이지에 접근할 수 없습니다.<br />
            ${sessionScope.sessionInfo.currentMenu.resrceCd}
        </p>
    </div>
</div>

<div class="subCon" id ="divSimpleProd" style="display: none">
    <div ng-controller="simpleProdCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl">${menuNm}</a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:none;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('simpleProdCtrl',1)">
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
                <%-- 분류조회 --%>
                <th>
                    <s:message code="simpleProd.srchClass" />
                </th>
                <td>
                    <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;"
                           placeholder="<s:message code="simpleProd.srchClass" /> 선택" readonly/>
                    <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <td>
                    <input type="checkbox" id="chkProdNm" ng-model="isChecked" />
                    <label for="chkProdNm">
                        <s:message code="simpleProd.chkProdNm" />
                    </label>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnProdSave" ng-click="save()"><s:message code="cmm.save" /></button>
            <%-- 초기화 --%>
            <button class="btn_skyblue ml5 fr" id="btnProdClear" ng-click="clear()"><s:message code="simpleProd.clear" /></button>
        </div>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:610px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="simpleProd.result"/>" binding="result" width="170" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.brandNm"/>" binding="hqBrandCd" data-map="brandDataMap" width="85" align="center" <c:if test="${brandUseFg == '0'}">visible="false"</c:if> ></wj-flex-grid-column>
                    <c:if test="${prodNoEnvFg == 'MANUAL'}">
                        <wj-flex-grid-column header="<s:message code="simpleProd.prodCd"/>" binding="prodCd" width="100" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="simpleProd.prodNm"/>" binding="prodNm" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.saleUprc"/>" binding="saleUprc" max-length="10" width="80" align="center"></wj-flex-grid-column>
                    <c:if test="${subPriceFg == '1'}">
                        <wj-flex-grid-column header="<s:message code="simpleProd.stinSaleUprc"/>" binding="stinSaleUprc" max-length="10" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="simpleProd.dlvrSaleUprc"/>" binding="dlvrSaleUprc" max-length="10" width="80" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="simpleProd.packSaleUprc"/>" binding="packSaleUprc" max-length="10" width="80" align="center"></wj-flex-grid-column>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="simpleProd.vendrCd"/>" binding="vendrCd" data-map="vendrCdDataMap" width="75" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="85" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.poProdFg"/>" binding="poProdFg" data-map="poProdFgDataMap" width="125" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.splyUprc"/>" binding="splyUprc" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.costUprc"/>" binding="costUprc" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.vatFg"/>" binding="vatFg" data-map="vatFgDataMap" width="80" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.barCd"/>" binding="barCd" width="100" align="center"></wj-flex-grid-column>
                    <c:if test="${orgnFg == 'HQ'}">
                        <wj-flex-grid-column header="<s:message code="simpleProd.prcCtrlFg"/>" binding="prcCtrlFg" data-map="prcCtrlFgDataMap" width="85" align="center"></wj-flex-grid-column>
                    </c:if>

                    <%--상품 저장시 필요--%>
                    <wj-flex-grid-column header="<s:message code="simpleProd.chkProdNm"/>" binding="chkProdNm" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.prodClassCd"/>" binding="prodClassCd" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.pointSaveYn"/>" binding="pointSaveYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.prodTipYn"/>" binding="prodTipYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.saleProdYn"/>" binding="saleProdYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.stockProdYn"/>" binding="stockProdYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.sideProdYn"/>" binding="sideProdYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.setProdFg"/>" binding="setProdFg" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.lastCostUprc"/>" binding="lastCostUprc" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.splyUprcUseYn"/>" binding="splyUprcUseYn" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.poUnitFg"/>" binding="poUnitFg" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.poUnitQty"/>" binding="poUnitQty" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.poMinQty"/>" binding="poMinQty" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.safeStockQty"/>" binding="safeStockQty" width="100" align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="simpleProd.useYn"/>" binding="useYn" width="100" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
</div>

<script>
    // String 형식
    var prodNoEnvFg = "${prodNoEnvFg}";
    var prodAuthEnvstVal = "${prodAuthEnvstVal}";
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";

    // List 형식("" 안붙임)
    var vendrComboList = ${vendrComboList};

    <%-- 상품유형 구분 --%>
    var prodTypeFgData = ${ccu.getCommCodeExcpAll("008")};
    <%-- 발주상품 구분 --%>
    var poProdFgData = ${ccu.getCommCodeExcpAll("092")};
    <%-- 과세여부 구분 --%>
    var vatFgData = ${ccu.getCommCodeExcpAll("039")};
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
    <%-- 내점/배달/포장 가격관리 사용여부 --%>
    var subPriceFg = "${subPriceFg}";
    <%-- (상품관리)브랜드사용여부 --%>
    var brandUseFg = "${brandUseFg}";
    <%-- 브랜드 --%>
    var brandList = ${brandList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/simpleProd/simpleProd.js?ver=20211111.03" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>