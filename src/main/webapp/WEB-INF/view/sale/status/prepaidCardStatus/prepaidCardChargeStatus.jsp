<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="subCon" id="prepaidCardChargeStatusView" style="padding: 10px 20px 40px;" ng-controller="prepaidCardChargeStatusCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="prepaidCardStatus.prepaidCardChargeStatus"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('prepaidCardChargeStatusCtrl', 1)">
                <s:message code="cmm.search"/>
            </button>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <c:if test="${momsEnvstVal == '1'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                        <s:message code="cmm.search.addShow"/>
                    </button>
                </c:if>
            </c:if>
        </div>
    </div>

    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 조회일자 --%>
            <th><s:message code="mobile.cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartChargeDate" class="w110px"/></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndChargeDate" class="w110px"/></span>
                </div>
            </td>
            <%-- 충전구분 --%>
            <th><s:message code="prepaidCardStatus.chargeFg"/></th>
            <td>
                <div class="sb-select w50">
                    <wj-combo-box
                            id="chargeFg"
                            ng-model="chargeFg"
                            items-source="_getComboData('chargeFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            control="chargeFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
            <tr>
                <%-- 매장브랜드 --%>
                <th><s:message code="cmm.moms.storeHqBrand"/></th>
                <td>
                    <div class="sb-select w50">
                        <wj-combo-box
                                id="srchStoreHqBrandCd"
                                ng-model="storeHqBrandCd"
                                items-source="_getComboData('srchStoreHqBrandCd')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td colspan="3">
                        <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="prepaidCardChargeStatusStore"/>
                    </jsp:include>
                        <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
            <input type="hidden" id="prepaidCardChargeStatusStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        </tbody>
    </table>

    <%-- 확장조회 --%>
    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <c:if test="${momsEnvstVal == '1'}">
            <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                        <%-- 팀별 --%>
                    <th><s:message code="cmm.moms.momsTeam"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsTeamCombo"
                                    ng-model="momsTeam"
                                    items-source="_getComboData('momsTeamCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsTeamCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- AC점포별 --%>
                    <th><s:message code="cmm.moms.momsAcShop"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAcShopCombo"
                                    ng-model="momsAcShop"
                                    items-source="_getComboData('momsAcShopCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsAcShopCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 지역구분 --%>
                    <th><s:message code="cmm.moms.momsAreaFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsAreaFgCombo"
                                    ng-model="momsAreaFg"
                                    items-source="_getComboData('momsAreaFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsAreaFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 상권 --%>
                    <th><s:message code="cmm.moms.momsCommercial"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsCommercialCombo"
                                    ng-model="momsCommercial"
                                    items-source="_getComboData('momsCommercialCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsCommercialCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 점포유형 --%>
                    <th><s:message code="cmm.moms.momsShopType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsShopTypeCombo"
                                    ng-model="momsShopType"
                                    items-source="_getComboData('momsShopTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsShopTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장관리타입 --%>
                    <th><s:message code="cmm.moms.momsStoreManageType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsStoreManageTypeCombo"
                                    ng-model="momsStoreManageType"
                                    items-source="_getComboData('momsStoreManageTypeCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsStoreManageTypeCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                        <%-- 그룹 --%>
                    <th><s:message code="cmm.moms.branch"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchBranchCdCombo"
                                    ng-model="branchCd"
                                    items-source="_getComboData('branchCdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchBranchCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 매장그룹 --%>
                    <th><s:message code="cmm.moms.momsStoreFg01"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsStoreFg01Combo"
                                    ng-model="momsStoreFg01"
                                    items-source="_getComboData('momsStoreFg01Combo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMomsStoreFg01Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                    <tr>
                            <%-- 매장그룹2 --%>
                        <th><s:message code="cmm.moms.momsStoreFg02"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsStoreFg02Combo"
                                        ng-model="momsStoreFg02"
                                        items-source="_getComboData('momsStoreFg02Combo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsStoreFg02Combo">
                                </wj-combo-box>
                            </div>
                        </td>
                            <%-- 매장그룹3 --%>
                        <th><s:message code="cmm.moms.momsStoreFg03"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsStoreFg03Combo"
                                        ng-model="momsStoreFg03"
                                        items-source="_getComboData('momsStoreFg03Combo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsStoreFg03Combo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                            <%-- 매장그룹4 --%>
                        <th><s:message code="cmm.moms.momsStoreFg04"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsStoreFg04Combo"
                                        ng-model="momsStoreFg04"
                                        items-source="_getComboData('momsStoreFg04Combo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsStoreFg04Combo">
                                </wj-combo-box>
                            </div>
                        </td>
                            <%-- 매장그룹5 --%>
                        <th><s:message code="cmm.moms.momsStoreFg05"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchMomsStoreFg05Combo"
                                        ng-model="momsStoreFg05"
                                        items-source="_getComboData('momsStoreFg05Combo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchMomsStoreFg05Combo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </c:if>
    </c:if>

    <div class="w100 mt10">
        <%--선불카드 충전 현황 테이블--%>
        <div class="wj-gridWrap" style="overflow-y: hidden; overflow-x: hidden; height:500px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">
                <!-- define columns -->
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.branchCd"/>" binding="branchCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.branchNm"/>" binding="branchNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.storeNm"/>" binding="storeNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="giftCalc.brandCd"/>" binding="brandCd" data-map="brandCdDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="prepaidCardStatus.saleDate"/>" binding="saleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                </c:if>
                <wj-flex-grid-column header="<s:message code="prepaidCardStatus.chargeCnt"/>" binding="chargeCnt" width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prepaidCardStatus.totChargeAmt"/>" binding="totChargeAmt" width="120" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prepaidCardStatus.chargeCancelCnt"/>" binding="chargeCancelCnt" width="100" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="prepaidCardStatus.totChargeCancelAmt"/>" binding="totChargeCancelAmt" width="120" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--선불카드 충전 현황 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="prepaidCardChargeStatusCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>


<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/prepaidCardStatus/prepaidCardChargeStatus.js?ver=20260416.01" charset="utf-8"></script>

<%--선불카드 충전 현황 상세--%>
<c:import url="/WEB-INF/view/sale/status/prepaidCardStatus/prepaidCardChargeStatusDtl.jsp">
</c:import>