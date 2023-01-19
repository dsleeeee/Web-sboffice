<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="saleDtlChannelTabCtrl" ng-init="init()">
        <c:if test="${orgnFg == 'HQ'}">
            <ul>
                <%-- 엑셀다운로드 탭 --%>
                <li>
                    <a id="saleDtlChannelExcelTab" href="#" class="on" ng-click="saleDtlChannelExcelShow()"><s:message code="saleDtlChannelExcel.saleDtlChannelExcel"/></a>
                </li>
                <%-- 조회 탭 --%>
                <li>
                    <a id="saleDtlChannelTab" href="#" ng-click="saleDtlChannelShow()"><s:message code="saleDtlChannel.saleDtlChannel"/></a>
                </li>
            </ul>
        </c:if>
    </div>
</div>


<script type="text/javascript">

    var orgnFg = "${orgnFg}";
    var hqOfficeCd = "${hqOfficeCd}";
    var storeCd = "${storeCd}";

    // 콤보박스 데이터
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};

    // 채널
    var dlvrInFgColList = [];

    <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
    <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
    var param = {};
    param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
    param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
    dlvrInFgColList.push(param);
    </c:forEach>

    var dlvrInFgCol = '${dlvrInFgCol}';
    var dlvrInFgColNm = '${dlvrInFgColNm}';
    var arrDlvrInFgCol = dlvrInFgCol.split(',');
    var arrDlvrInFgColNm = dlvrInFgColNm.split(',');

    // header값 셋팅을 위한 변수
    var vDlvrOrderFg = ["stin", "dlvr", "pack"];

    var orgnFg = '${orgnFg}';
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/saleDtlChannel/saleDtlChannelTab.js?ver=20230106.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 엑셀다운로드 레이어 --%>
<c:import url="/WEB-INF/view/sale/prod/saleDtlChannel/saleDtlChannelExcel.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 조회 레이어 --%>
<c:import url="/WEB-INF/view/sale/prod/saleDtlChannel/saleDtlChannel.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<%-- //탭페이지 레이어 --%>

<script>

    onload = function()
    {

        var excelScope = agrid.getScope("saleDtlChannelExcelCtrl");
        excelScope.prodClassPopUpLayer.show();
        excelScope.prodClassPopUpLayer.hide();

        var scope = agrid.getScope("saleDtlChannelCtrl");
        scope.prodClassPopUpLayer.show();
        scope.prodClassPopUpLayer.hide();

    }
</script>
