<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="con">
    <div class="tabType1" ng-controller="kioskKeyMapStoreCtrl" ng-init="init()">
        <ul>
            <li>
                <a id="sideMenuTab" href="#" class="on"><s:message code="kioskKeyMap.kioskKeyMapRegist"/></a>
            </li>
        </ul>
    </div>

    <c:import url="/WEB-INF/view/base/prod/kioskKeyMap/kioskKeyMapRegist.jsp">
    </c:import>
</div>

<script>
    // 브랜드 사용여부
    var brandUseFg = "${brandUseFg}";
    // 사용자 브랜드
    var userHqBrandCdComboList = ${userHqBrandCdComboList};

    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    var subPriceFg = "${subPriceFg}";
    var coercionFg = "${coercionFg}";

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

    // 본사권한 [기초관리] - [매장관리] - [매장정보조회]의 판매터치키변경을 클릭하여 접속한 경우, 왼쪽 메뉴영역은 접어두기.
    var referrer = document.referrer;
    if(orgnFg === "STORE" && referrer.indexOf("/base/store/view/view/list.sb") > 0){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 최초 접속한 경우(접속하면서 session 생성), 왼쪽 메뉴영역은 접어두기.
    // 최초 접속시에는 이전 URL 인자값으로 판별가능
    if(referrer.indexOf("userId") > 0 && referrer.indexOf("resrceCd") > 0 && referrer.indexOf("accessCd") > 30 ){
        $(".menuControl").trigger("click");
    }

    // POS에서 해당 WEB 화면 재접속한 경우(이전 접속 session 그대로 존재), 'posLoginReconnect'값 판단하여 왼쪽 메뉴영역은 접어두기.
    // 재접속시에는 이전 URL 인자값이 없어, 로그인 여부 판별시에 특정 parameter 값을 보내 처리.
    if("${posLoginReconnect}" === "Y"){ // 직접입력한경우
        $(".menuControl").trigger("click");
    }

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapStore.js?ver=20250124.01" charset="utf-8"></script>
