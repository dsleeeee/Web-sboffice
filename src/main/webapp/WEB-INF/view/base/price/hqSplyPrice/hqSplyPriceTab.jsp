<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="hqSplyPriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 본사공급가관리 탭 --%>
            <li>
                <a id="hqSplyPriceTab" href="#" class="on" ng-click="hqSplyPriceShow()"><s:message code="hqSplyPrice.hqSplyPrice"/></a>
            </li>
            <%-- 본사공급가관리 엑셀업로드 탭 --%>
            <li>
                <a id="hqSplyPriceExcelUploadTab" href="#" ng-click="hqSplyPriceExcelUploadShow()"><s:message code="hqSplyPrice.splyPriceExcelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/hqSplyPrice/hqSplyPriceTab.js?ver=20240411.02" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 본사공급가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/hqSplyPrice/hqSplyPrice.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 본사공급가관리 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/hqSplyPrice/hqSplyPriceExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>