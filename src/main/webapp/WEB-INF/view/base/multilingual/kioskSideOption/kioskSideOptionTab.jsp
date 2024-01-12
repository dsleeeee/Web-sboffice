<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="kioskSideOptionTabCtrl" ng-init="init()">
        <ul>
            <%-- 키오스크(카테고리명) 탭 --%>
            <li>
                <a id="kioskCategoryTab" href="#" class="on" ng-click="kioskCategoryShow()"><s:message code="kioskSideOption.kioskCategory"/></a>
            </li>
            <%-- 사이드(선택그룹명) 탭 --%>
            <li>
                <a id="sideSdselGrpTab" href="#" ng-click="sideSdselGrpShow()"><s:message code="kioskSideOption.sideSdselGrp"/></a>
            </li>
            <%-- 사이드(선택분류명) 탭 --%>
            <li>
                <a id="sideSdselClassTab" href="#" ng-click="sideSdselClassShow()"><s:message code="kioskSideOption.sideSdselClass"/></a>
            </li>
            <%-- 옵션(그룹명) 탭 --%>
            <li>
                <a id="optionGrpTab" href="#" ng-click="optionGrpShow()"><s:message code="kioskSideOption.optionGrp"/></a>
            </li>
            <%-- 옵션(옵션명) 탭 --%>
            <li>
                <a id="optionValTab" href="#" ng-click="optionValShow()"><s:message code="kioskSideOption.optionVal"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/multilingual/kioskSideOption/kioskSideOptionTab.js?ver=20240111.01" charset="utf-8"></script>

<%-- excelfile read js --%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.14.3/xlsx.full.min.js"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 키오스크(카테고리명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/kioskCategory.jsp">
</c:import>

<%-- 사이드(선택그룹명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/sideSdselGrp.jsp">
</c:import>

<%-- 사이드(선택분류명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/sideSdselClass.jsp">
</c:import>

<%-- 옵션(그룹명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/optionGrp.jsp">
</c:import>

<%-- 옵션(옵션명) 탭 --%>
<c:import url="/WEB-INF/view/base/multilingual/kioskSideOption/optionVal.jsp">
</c:import>

<%-- 탭페이지 레이어 끝 --%>