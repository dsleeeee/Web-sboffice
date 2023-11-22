<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="kioskTabCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크(카테고리명) 탭 --%>
            <li>
                <a id="kioskCategoryTab" href="#" class="on" ng-click="kioskCategoryShow()"><s:message code="kiosk.kioskCategory"/></a>
            </li>
            <%-- 사이드 탭 --%>
            <%--<li>
                <a id="sideTab" href="#" ng-click="sideShow()"><s:message code="kiosk.side"/></a>
            </li>--%>
            <%-- 옵션 탭 --%>
            <%--<li>
                <a id="optionTab" href="#" ng-click="optionShow()"><s:message code="kiosk.option"/></a>
            </li>--%>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/kiosk/kioskTab.js?ver=20231122.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크(카테고리) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kiosk/kioskCategory.jsp">
</c:import>

<%-- 사이드 탭 --%>
<%--<c:import url="/WEB-INF/view/base/multilingual/kiosk/side.jsp">
</c:import>--%>

<%-- 옵션 탭 --%>
<%--<c:import url="/WEB-INF/view/base/multilingual/kiosk/option.jsp">
</c:import>--%>

<%-- 탭페이지 레이어 끝 --%>