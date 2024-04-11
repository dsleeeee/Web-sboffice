<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="con">
    <div class="tabType1" ng-controller="splyPriceTabCtrl" ng-init="init()">
        <ul>
            <%-- 공급가관리 탭 --%>
            <li>
                <a id="splyPriceTab" href="#" class="on" ng-click="splyPriceShow()"><s:message code="splyPrice.splyPrice"/></a>
            </li>
            <%-- 공급가관리 엑셀업로드 탭 --%>
            <li>
                <a id="splyPriceExcelUploadTab" href="#" ng-click="splyPriceExcelUploadShow()"><s:message code="splyPrice.splyPriceExcelUpload"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    <%-- 가격관리구분 --%>
    var prcCtrlFgData = ${ccu.getCommCodeExcpAll("045")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/price/splyPrice/splyPriceTab.js?ver=20240411.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 공급가관리 레이어 --%>
<c:import url="/WEB-INF/view/base/price/splyPrice/splyPrice.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 공급가관리 엑셀업로드 레이어 --%>
<c:import url="/WEB-INF/view/base/price/splyPrice/splyPriceExcelUpload.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- 탭페이지 레이어 끝 --%>