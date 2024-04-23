<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="storeSplyPriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 상품별 공급가관리 탭 --%>
            <li>
                <a id="byProdSplyPriceTab" href="#" class="on" ng-click="byProdSplyPriceShow()"><s:message code="storeSplyPrice.byProdSplyPrice"/></a>
            </li>
            <%-- 매장별 공급가관리 탭 --%>
            <li>
                <a id="byStoreSplyPriceTab" href="#" ng-click="byStoreSplyPriceShow()"><s:message code="storeSplyPrice.byStoreSplyPrice"/></a>
            </li>
            <%-- 매장공급가복사 탭 --%>
            <li>
                <a id="storeSplyPriceCopyTab" href="#" ng-click="storeSplyPriceCopyShow()"><s:message code="storeSplyPrice.storeSplyPriceCopy"/></a>
            </li>
            <%-- 매장공급가관리 엑셀업로드 탭 --%>
            <li>
                <a id="storeSplyPriceExcelUploadTab" href="#" ng-click="storeSplyPriceExcelUploadShow()"><s:message code="storeSplyPrice.excelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};

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

    $(function () {
        $("input:text[numberOnly]").on("keyup", function () {
            $(this).val($(this).val().replace(/[^-|^0-9]/g, ""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/storeSplyPrice/storeSplyPriceTab.js?ver=20240423.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품별 공급가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeSplyPrice/byProdSplyPrice.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장별 공급가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeSplyPrice/byStoreSplyPrice.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장공급가복사 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeSplyPrice/storeSplyPriceCopy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장공급가관리 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/storeSplyPrice/storeSplyPriceExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>