<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="kioskTabCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크(카테고리명) 탭 --%>
            <li>
                <a id="kioskCategoryTab" href="#" class="on" ng-click="kioskCategoryShow()"><s:message code="kioskSideOption.kioskCategory"/></a>
            </li>
            <%-- 사이드(선택그룹명) 탭 --%>
            <%--<li>
                <a id="sdselGrpTab" href="#" ng-click="sdselGrpShow()"><s:message code="kiosk.side"/></a>
            </li>--%>
            <%-- 사이드(선택분류명) 탭 --%>
            <%--<li>
                <a id="sdselClassTab" href="#" ng-click="sdselClassShow()"><s:message code="kiosk.option"/></a>
            </li>--%>
            <%-- 옵션(그룹명) --%>
            <%-- 옵션(옵션명) --%>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/kioskSideOption/kioskSideOptionTab.js?ver=20231123.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크(카테고리) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/kioskCategory.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>