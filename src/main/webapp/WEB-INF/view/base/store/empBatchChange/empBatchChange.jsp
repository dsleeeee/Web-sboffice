<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="empBatchChangeView" class="subCon" style="padding: 10px 20px 40px;">
    <div ng-controller="empBatchChangeCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="empBatchChange.empBatchChange" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('empBatchChangeCtrl',1)" id="nxBtnSearch1">
                    <s:message code="cmm.search" />
                </button>
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
            </colgroup>
            <tbody>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="empBatchChange.empNo"/></th>
                <td>
                    <input type="text" id="srchEmpNo" name="srchEmpNo" ng-model="empNo" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <%-- 매장명 --%>
                <th><s:message code="empBatchChange.empNm"/></th>
                <td>
                    <input type="text" id="srchEmpNm" name="srchEmpNm" ng-model="empNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
            </tr>
<%--            <tr>--%>
<%--                &lt;%&ndash; 매장브랜드 &ndash;%&gt;--%>
<%--                <th><s:message code="dayProd.storeHqBrand"/></th>--%>
<%--                <td>--%>
<%--                    <div class="sb-select">--%>
<%--                        <wj-combo-box--%>
<%--                                id="srchStoreHqBrandCdCombo"--%>
<%--                                ng-model="storeHqBrandCd"--%>
<%--                                items-source="_getComboData('storeHqBrandCdCombo')"--%>
<%--                                display-member-path="name"--%>
<%--                                selected-value-path="value"--%>
<%--                                is-editable="false"--%>
<%--                                control="srchStoreHqBrandCdCombo">--%>
<%--                        </wj-combo-box>--%>
<%--                    </div>--%>
<%--                </td>--%>
<%--                </td>--%>
<%--            </tr>--%>
            </tbody>
        </table>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
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
                    </c:if>
                    <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
                        <td></td>
                        <td></td>
                    </c:if>
                </tr>
                </tbody>
            </table>
        </c:if>

        <div class="mt10 oh sb-select dkbr">
            <%-- 저장 --%>
            <button class="btn_skyblue fr" id="btnProdSave" ng-click="save()"><s:message code="cmm.save" /></button>
        </div>

        <%-- 일괄적용 --%>
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 그룹 --%>
                <th>
                    <s:message code="cmm.moms.branch" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchBranchCdChg"
                                ng-model="branchCdChg"
                                items-source="_getComboData('branchCdChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('branchCdChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <%-- 팀별 --%>
                <th>
                    <s:message code="cmm.moms.momsTeam" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsTeamChg"
                                ng-model="momsTeamChg"
                                items-source="_getComboData('momsTeamChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsTeamChg')"><s:message code="cmm.batchChange" /></a>
                </td>
            </tr>
            <tr>
                <%-- AC점포별 --%>
                <th>
                    <s:message code="cmm.moms.momsAcShop" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAcShopChg"
                                ng-model="momsAcShopChg"
                                items-source="_getComboData('momsAcShopChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsAcShopChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <%-- 지역구분 --%>
                <th>
                    <s:message code="cmm.moms.momsAreaFg" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAreaFgChg"
                                ng-model="momsAreaFgChg"
                                items-source="_getComboData('momsAreaFgChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsAreaFgChg')"><s:message code="cmm.batchChange" /></a>
                </td>
            </tr>
            <tr>
                <%-- 상권 --%>
                <th>
                    <s:message code="cmm.moms.momsCommercial" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsCommercialChg"
                                ng-model="momsCommercialChg"
                                items-source="_getComboData('momsCommercialChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsCommercialChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <%-- 점포유형 --%>
                <th>
                    <s:message code="cmm.moms.momsShopType" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsShopTypeChg"
                                ng-model="momsShopTypeChg"
                                items-source="_getComboData('momsShopTypeChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsShopTypeChg')"><s:message code="cmm.batchChange" /></a>
                </td>
            </tr>
            <tr>
                <%-- 매장관리타입 --%>
                <th>
                    <s:message code="cmm.moms.momsStoreManageType" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreManageTypeChg"
                                ng-model="momsStoreManageTypeChg"
                                items-source="_getComboData('momsStoreManageTypeChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsStoreManageTypeChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                    <%-- 매장그룹 --%>
                    <th><s:message code="cmm.moms.momsStoreFg01"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMomsStoreFg01Chg"
                                    ng-model="momsStoreFg01Chg"
                                    items-source="_getComboData('momsStoreFg01ChgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 일괄적용 --%>
                    <td>
                        <a href="#" class="btn_grayS ml10" ng-click="batchChange('momsStoreFg01Chg')"><s:message code="cmm.batchChange" /></a>
                    </td>
                </c:if>
                <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
                    <td></td>
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            </tbody>
        </table>

        <%-- 그리드 --%>
        <div class="w100 mt10 mb20">
            <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        frozen-columns="4"
                        id="wjGridStoreEmpBatchChange">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNm"/>" binding="empNm" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.userId"/>" binding="userId" width="100" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="branchCd" data-map="branchCdDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" data-map="momsTeamDataMap" width="100" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" data-map="momsAcShopDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" data-map="momsAreaFgDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" data-map="momsCommercialDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" data-map="momsShopTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" data-map="momsStoreManageTypeDataMap" width="90" align="center"></wj-flex-grid-column>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreFg01"/>" binding="momsStoreFg01" data-map="momsStoreFg01DataMap" width="90" align="center"></wj-flex-grid-column>
                    </c:if>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="empBatchChangeCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empBatchChange/empBatchChange.js?ver=20231101.01" charset="utf-8"></script>
