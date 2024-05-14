<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="chgCostPriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 원가임의변경 탭 --%>
            <li>
                <a id="chgCostPriceTab" href="#" class="on" ng-click="chgCostPriceShow()"><s:message code="chgCostPrice.chgCostPrice"/></a>
            </li>
            <%-- 원가임의변경 엑셀업로드 탭 --%>
            <li>
                <a id="chgCostPriceExcelUploadTab" href="#" ng-click="chgCostPriceExcelUploadShow()"><s:message code="chgCostPrice.excelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/chgCostPrice/chgCostPriceTab.js?ver=20240513.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 원가임의변경 레이어 --%>
<c:import url="/WEB-INF/view/base/price/chgCostPrice/chgCostPrice.jsp">
</c:import>

<%-- 원가임의변경 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/chgCostPrice/chgCostPriceExcelUpload.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>