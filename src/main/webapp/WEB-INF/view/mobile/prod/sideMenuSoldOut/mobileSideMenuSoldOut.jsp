<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="mobileSideMenuSoldOutCtrl">

    <div class="searchBar">
        <a href="#" class="fl"><s:message code="mobile.sideMenuSoldOut"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('mobileSideMenuGrpSoldOutGrid', 1)">
            <s:message code="mobile.cmm.search"/>
        </button>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w20"/>
            <col class="w80"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 그룹명 --%>
            <th><s:message code="mobile.sideMenuSoldOut.sdselGrpNm"/></th>
            <td><input type="text"  class="sb-input w100" id="sdselGrpNm" ng-model="sdselGrpNm" /></td>
        </tr>
        <c:if test="${orgnFg == 'HQ'}">
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="mobile.cmm.search.store"/></th>
                <td>
                        <%-- 다중매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/mobile/sale/com/popup/selectSingleStore.jsp" flush="true">
                        <jsp:param name="targetId" value="mobileSideMenuSoldOutStore"/>
                    </jsp:include>
                        <%--// 다중매장선택 모듈 멀티 선택 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${orgnFg == 'STORE'}">
            <input type="hidden" id="mobileSideMenuSoldOutStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>
    <div class="mt5 oh sb-select dkbr">
        <button class="btn_blue fr" id="btnSave" ng-click="save()">
            <s:message code="mobile.cmm.save"/>
        </button>
    </div>

    <%-- 일괄적용 --%>
    <table class="searchTbl mt5 mb5">
        <colgroup>
            <col class="w20" />
            <col class="w50" />
            <col class="w30" />
        </colgroup>
        <tbody>
        <tr class="brt">
            <th><s:message code="mobile.sideMenuSoldOut.soldOutYn" /></th>
            <td>
                <div class="sb-select">
                    <div class="sb-select fl w100">
                        <wj-combo-box
                                id="soldOutYnChg"
                                ng-model="soldOutYnChg"
                                items-source="_getComboData('soldOutYnChg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </div>
            </td>
            <td><a href="#" class="btn_grayS ml10" ng-click="batchChange()"><s:message code="mobile.sideMenuSoldOut.batchChange" /></a></td>
        </tr>
        </tbody>
    </table>

    <%-- 선택그룹 --%>
    <div class="gridBar mt10" id="mobileSideMenuGrpSoldOut" onclick="girdFldUnfld('mobileSideMenuGrpSoldOut')">
        <a href="#" class="open"><s:message code="mobile.sideMenuSoldOut.sideMenuGrp"/></a>
    </div>
    <div class="w100" id="mobileSideMenuGrpSoldOutGrid" ng-controller="mobileSideMenuGrpSoldOutGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; min-height:100px; max-height: 100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileSideMenuGrpSoldOut"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.sdselGrpCd"/>" binding="sdselGrpCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.sdselGrpNm"/>" binding="sdselGrpNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileSideMenuGrpSoldOutMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 선택분류 --%>
    <div class="gridBar mt10" id="mobileSideMenuClassSoldOut" onclick="girdFldUnfld('mobileSideMenuClassSoldOut')">
        <a href="#" class="open" id="sideSelectGroupTitle"><s:message code="mobile.sideMenuSoldOut.sideMenuClass"/></a>
    </div>
    <div class="w100" id="mobileSideMenuClassSoldOutGrid" ng-controller="mobileSideMenuClassSoldOutGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; min-height:100px; max-height: 100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexMobileSideMenuClassSoldOut"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.sdselClassNm"/>" binding="sdselClassNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.qty"/>" binding="sdselQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileSideMenuClassSoldOutMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>

    <%-- 선택상품 --%>
    <div class="gridBar mt10" id="mobileSideMenuProdSoldOut" onclick="girdFldUnfld('mobileSideMenuProdSoldOut')">
        <a href="#" class="open" id="sideClassTitle"><s:message code="mobile.sideMenuSoldOut.sideMenuProd"/></a>
    </div>
    <div class="w100" id="mobileSideMenuProdSoldOutGrid" ng-controller="mobileSideMenuProdSoldOutGrid">
        <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden; min-height:100px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex" <%-- 저장 성공시 재조회를 위해 공통모듈에서 사용하는 이름사용 --%>
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.soldOutYn"/>" binding="soldOutYn" width="100" align="center" data-map="soldOutYnDataMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.addProdUprc"/>" binding="addProdUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="mobile.sideMenuSoldOut.addProdQty"/>" binding="addProdQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <!-- 조회 결과가 없을 때, msg 띄우기 -->
                <div class="gridMsg" id="mobileSideMenuProdSoldOutMsg" style="line-height: 100px; display: none;"><s:message code="mobile.cmm.search.result.empty"/></div>
            </wj-flex-grid>
        </div>
    </div>

</div>
<script>
    var orgnFg = "${orgnFg}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/mobile/prod/sideMenuSoldOut/mobileSideMenuSoldOut.js?ver=20220303.01" charset="utf-8"></script>