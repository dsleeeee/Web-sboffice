<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="con">
    <div class="tabType1" ng-controller="storeKioskSideOptionTabCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크(카테고리명) 탭 --%>
            <li>
                <a id="storeKioskCategoryTab" href="#" class="on" ng-click="storeKioskCategoryShow()"><s:message code="storeKioskSideOption.kioskCategory"/></a>
            </li>
            <%-- 키오스크중분류(카테고리명) 탭 --%>
            <li <c:if test="${hqOfficeCd == 'DS021' or hqOfficeCd == 'DS034' or hqOfficeCd == 'H0393'}">style="display: none;"</c:if>>
                <a id="storeKioskMClsTab" href="#" ng-click="storeKioskMClsShow()"><s:message code="storeKioskSideOption.kioskMCls"/></a>
            </li>
            <%-- 사이드(선택그룹명) 탭 --%>
            <li>
                <a id="storeSideSdselGrpTab" href="#" ng-click="storeSideSdselGrpShow()"><s:message code="storeKioskSideOption.sideSdselGrp"/></a>
            </li>
            <%-- 사이드(선택분류명) 탭 --%>
            <li>
                <a id="storeSideSdselClassTab" href="#" ng-click="storeSideSdselClassShow()"><s:message code="storeKioskSideOption.sideSdselClass"/></a>
            </li>
            <%-- 옵션(그룹명) 탭 --%>
            <li>
                <a id="storeOptionGrpTab" href="#" ng-click="storeOptionGrpShow()"><s:message code="storeKioskSideOption.optionGrp"/></a>
            </li>
            <%-- 옵션(옵션명) 탭 --%>
            <li>
                <a id="storeOptionValTab" href="#" ng-click="storeOptionValShow()"><s:message code="storeKioskSideOption.optionVal"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};

    // 키오스크용 포스 목록(중분류 사용 포스만 조회)
    var kioskPos2List = ${kioskPos2List};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/storeKioskSideOption/storeKioskSideOptionTab.js?ver=20250925.01" charset="utf-8"></script>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크(카테고리명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeKioskCategory.jsp">
</c:import>

<%-- 키오스크중분류(카테고리명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeKioskMCls.jsp">
</c:import>

<%-- 사이드(선택그룹명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeSideSdselGrp.jsp">
</c:import>

<%-- 사이드(선택분류명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeSideSdselClass.jsp">
</c:import>

<%-- 옵션(그룹명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeOptionGrp.jsp">
</c:import>

<%-- 옵션(옵션명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/storeKioskSideOption/storeOptionVal.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>