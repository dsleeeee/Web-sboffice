<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon" ng-controller="virtualAccountInfoCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('virtualAccountInfoCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>

    <div class="mt10 oh">
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" ng-click="save()"><s:message code="cmm.save"/></button>
        <c:if test="${sessionInfo.orgnFg == 'MASTER'}">
            <%-- 추가 --%>
            <button class="btn_skyblue ml5 fr" ng-click="add()"><s:message code="cmm.add"/></button>
            <%-- 본사 --%>
            <div class="ml5 fr">
                <input type="text" id="virtualAccountInfoHqOfficeNm" class="sb-input w70" ng-model="hqOfficeNm" readonly="readonly" ng-click="searchHq()" style="float: left;"/>
                <input type="hidden" id="virtualAccountInfoHqOfficeCd" ng-model="hqOfficeCd" disabled />
                <button type="button" class="btn_skyblue fl mr5" id="btnDelHq" style="margin-left: 5px;" ng-click="delHq()"><s:message code="cmm.selectCancel"/></button>
            </div>
            <div class="sb-select dkbr ml5 fr">
                <%-- 본사선택 --%>
                <p class="tl s14 mt5 lh15"><s:message code="cmm.hq.select"/> : </p>
            </div>
        </c:if>
    </div>

    <div class="w100 mt10">
        <div class="wj-gridWrap" style="height: 370px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    <%--is-read-only="true"--%>
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.hqOfficeCd"/>" binding="hqOfficeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.hqOfficeNm"/>" binding="hqOfficeNm" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccountInfo.siteCd"/>" binding="siteCd" width="165" align="center" is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccountInfo.kcpCertInfo"/>" binding="kcpCertInfo" width="*" align="center" is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="virtualAccountInfo.kcpPrivateKey"/>" binding="kcpPrivateKey" width="*" align="center" is-read-only="false"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/virtualAccountInfo/virtualAccountInfo.js?ver=20240806.01" charset="utf-8"></script>

<%-- 본사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchHq.jsp">
</c:import>