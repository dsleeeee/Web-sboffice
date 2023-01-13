<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="touchKeyGrp" value="${touchKeyGrp}" />

<div class="con">
    <div class="tabType1" ng-controller="prodInfoSearchCtrl" ng-init="init()">
        <ul>
            <%-- 상품분류 탭 --%>
            <li>
                <a id="prodClassTab" href="#" class="on" ng-click="prodClassShow()"><s:message code="prodInfoSearch.prodClass"/></a>
            </li>
            <%-- 사이드-속성 탭 --%>
            <li>
                <a id="sideAttrTab" href="#" ng-click="sideAttrShow()"><s:message code="prodInfoSearch.sideAttr"/></a>
            </li>
            <%-- 사이드-선택메뉴 탭 --%>
            <li>
                <a id="sideMenuTab" href="#" ng-click="sideMenuShow()"><s:message code="prodInfoSearch.sideMenu"/></a>
            </li>
            <%-- 옵션 탭 --%>
            <li>
                <a id="optionTab" href="#" ng-click="optionShow()"><s:message code="prodInfoSearch.option"/></a>
            </li>
            <%-- 상품-속성/선택메뉴/옵션 탭 --%>
            <li>
                <a id="prodInfoTab" href="#" ng-click="prodInfoShow()"><s:message code="prodInfoSearch.prodInfo"/></a>
            </li>
            <%-- 상품-원산지 탭 --%>
            <li>
                <a id="orgplceTab" href="#" ng-click="orgplceShow()"><s:message code="prodInfoSearch.orgplce"/></a>
            </li>
            <%-- 상품-알레르기 탭 --%>
            <li>
                <a id="allergyTab" href="#" ng-click="allergyShow()"><s:message code="prodInfoSearch.allergy"/></a>
            </li>
            <%-- 판매터치키 탭 --%>
            <li>
                <a id="touchKeyTab" href="#" ng-click="touchKeyShow()"><s:message code="prodInfoSearch.touchKey"/></a>
            </li>
            <%-- 키오스크키맵 탭 --%>
            <li>
                <a id="kioskKeyMapTab" href="#" ng-click="kioskKeyMapShow()"><s:message code="prodInfoSearch.kioskKeyMap"/></a>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};

    //터치키그룹
    var touchKeyGrpData = ${touchKeyGrp};
    touchKeyGrpData.unshift({name: "전체", value: ""});

    // 키오스크용 포스 목록
    var kioskPosList = ${kioskPosList};

    // 키오스크 키맵그룹
    var kioskTuClsTypeList = ${kioskTuClsTypeList};
    kioskTuClsTypeList.unshift({name: "전체", value: ""});
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodInfoSearch/prodInfoSearchTab.js?ver=20221223.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 상품분류 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/prodClass.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사이드-속성 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/sideAttr.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사이드-선택메뉴 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/sideMenu.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 옵션 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/option.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품-속성/선택메뉴/옵션 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/prodInfo.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 원산지 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/orgplce.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 알레르기 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/allergy.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 판매터치키 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/touchKey.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 키오스크키맵 레이어 --%>
<c:import url="/WEB-INF/view/base/prod/prodInfoSearch/kioskKeyMap.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- //탭페이지 레이어 --%>
