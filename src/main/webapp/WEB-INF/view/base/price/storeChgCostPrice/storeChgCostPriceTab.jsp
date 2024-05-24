<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="storeChgCostPriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품별 원가관리 탭 --%>
            <li>
                <a id="byProdChgCostPriceTab" href="#" class="on" ng-click="byProdChgCostPriceShow()"><s:message code="storeChgCostPrice.byProdChgCostPrice"/></a>
            </li>
            <%-- 매장별 원가관리 탭 --%>
            <li>
                <a id="byStoreChgCostPriceTab" href="#" ng-click="byStoreChgCostPriceShow()"><s:message code="storeChgCostPrice.byStoreChgCostPrice"/></a>
            </li>
            <%-- 매장원가임의변경 엑셀업로드 탭 --%>
            <li>
                <a id="storeChgCostPriceExcelUploadTab" href="#" ng-click="storeChgCostPriceExcelUploadShow()"><s:message code="storeChgCostPrice.excelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeChgCostPrice/storeChgCostPriceTab.js?ver=20240524.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 매장원가임의변경 상품별 원가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeChgCostPrice/byProdChgCostPrice.jsp">
</c:import>

<%-- 매장원가임의변경 매장별 원가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeChgCostPrice/byStoreChgCostPrice.jsp">
</c:import>

<%-- 매장원가임의변경 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeChgCostPrice/storeChgCostPriceExcelUpload.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>
