<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sesionScope.sesionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sesionScope.sesionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sesionScope.sesionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="storeMrpizzaBatchChangeTabCtrl" ng-init="init()">
        <ul>
            <%-- 엑셀다운로드 탭 --%>
            <li>
                <a id="storeMrpizzaBatchChangeTab" href="#" class="on" ng-click="storeMrpizzaBatchChangeShow()"><s:message code="storeMrpizzaBatchChange.storeMrpizzaBatchChange"/></a>
            </li>
            <%-- 조회 탭 --%>
            <li>
                <a id="storeMrpizzaBatchChangeExcelUploadTab" href="#" ng-click="storeMrpizzaBatchChangeExcelUploadShow()"><s:message code="storeMrpizzaBatchChange.storeMrpizzaBatchChangeExcelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 콤보박스 데이터(전체)
    var momsTeamComboList = ${momsTeamComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};

    // 콤보박스 데이터
    var momsTeamComboList2 = ${momsTeamComboList2};
    var momsAreaFgComboList2 = ${momsAreaFgComboList2};
    var momsCommercialComboList2 = ${momsCommercialComboList2};
    var momsShopTypeComboList2 = ${momsShopTypeComboList2};

    // 콤보박스 데이터(선택)
    var momsTeamComboList3 = ${momsTeamComboList3};
    var momsAreaFgComboList3 = ${momsAreaFgComboList3};
    var momsCommercialComboList3 = ${momsCommercialComboList3};
    var momsShopTypeComboList3 = ${momsShopTypeComboList3};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChangeTab.js?ver=20250818.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 일괄변경 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChange.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMrpizza/storeMrpizzaBatchChange/storeMrpizzaBatchChangeExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>