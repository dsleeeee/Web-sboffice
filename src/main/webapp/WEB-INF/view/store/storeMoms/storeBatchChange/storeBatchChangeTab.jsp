<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sesionScope.sesionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sesionScope.sesionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sesionScope.sesionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="storeBatchChangeTabCtrl" ng-init="init()">
        <ul>
            <%-- 엑셀다운로드 탭 --%>
            <li>
                <a id="storeBatchChangeTab" href="#" class="on" ng-click="storeBatchChangeShow()"><s:message code="storeBatchChange.storeBatchChange"/></a>
            </li>
            <%-- 조회 탭 --%>
            <li>
                <a id="storeBatchChangeExcelUploadTab" href="#" ng-click="storeBatchChangeExcelUploadShow()"><s:message code="storeBatchChange.storeBatchChangeExcelUpload"/></a>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    // 콤보박스 데이터(전체)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

    // 콤보박스 데이터
    var branchCdComboList2 = ${branchCdComboList2};
    var momsTeamComboList2 = ${momsTeamComboList2};
    var momsAcShopComboList2 = ${momsAcShopComboList2};
    var momsAreaFgComboList2 = ${momsAreaFgComboList2};
    var momsCommercialComboList2 = ${momsCommercialComboList2};
    var momsShopTypeComboList2 = ${momsShopTypeComboList2};
    var momsStoreManageTypeComboList2 = ${momsStoreManageTypeComboList2};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeBatchChange/storeBatchChangeTab.js?ver=20230117.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일괄변경 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeBatchChange/storeBatchChange.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeBatchChange/storeBatchChangeExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>
