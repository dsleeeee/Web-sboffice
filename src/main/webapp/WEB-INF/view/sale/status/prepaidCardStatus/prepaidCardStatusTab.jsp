<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="con">
    <div class="tabType1" ng-controller="prepaidCardStatusTabCtrl" ng-init="init()">
        <ul>
            <%-- 선불카드 충천 현황 탭 --%>
            <li>
                <a id="prepaidCardChargeStatusTab" href="#" class="on" ng-click="prepaidCardChargeStatusShow()"><s:message code="prepaidCardStatus.prepaidCardChargeStatus"/></a>
            </li>
            <%-- 선불카드 사용 현황 탭 --%>
            <li>
                <a id="prepaidCardUseStatusTab" href="#" ng-click="prepaidCardUseStatusShow()"><s:message code="prepaidCardStatus.prepaidCardUseStatus"/></a>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">

    var orgnFg = "${orgnFg}";

    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";

    // 사용자 브랜드(매장브랜드)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // 확장조회 관련
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prepaidCardStatus/prepaidCardStatusTab.js?ver=20260408.01" charset="utf-8"></script>

<%-- 탭페이지 레이어 시작 --%>
<%-- 선불카드 충전 현황 --%>
<c:import url="/WEB-INF/view/sale/status/prepaidCardStatus/prepaidCardChargeStatus.jsp">
</c:import>

<%-- 선불카드 사용 현황 --%>
<c:import url="/WEB-INF/view/sale/status/prepaidCardStatus/prepaidCardUseStatus.jsp">
</c:import>
<%-- 탭페이지 레이어 끝 --%>