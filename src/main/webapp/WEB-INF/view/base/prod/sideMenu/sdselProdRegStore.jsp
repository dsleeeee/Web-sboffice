<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSdselProdRegStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:760px;height:660px;" fade-in="false" fade-out="false">

    <div ng-controller="sdselProdRegStoreCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="sideMenu.sdselProdRegStore.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 선택분류 --%>
                    <th>
                        <s:message code="sideMenu.sdselProdRegStore.sdselClass"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdRegStoreClassCd" ng-model="classCd" readonly style="display: none" />
                        <input type="text" class="sb-input w100" id="srchProdRegStoreClass" ng-model="class" readonly />
                    </td>
                    <%-- 선택상품 (적용매장구분) --%>
                    <th>
                        <s:message code="sideMenu.sdselProdRegStore.sdselProd"/>
                    </th>
                    <td>
                        <div class="sb-select dkbr">
                            <wj-combo-box
                                    class="w160px fl"
                                    id="srchRegStoreSdselProdCombo"
                                    ng-model="regStoreSdselProdCombo"
                                    items-source="_getComboData('regStoreSdselProdCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchRegStoreSdselProdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="sideMenu.sdselProdRegStore.storeCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch(4);" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="sideMenu.sdselProdRegStore.storeNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch(4);" />
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="tblType01" id="tblSearchAddShow2" style="display: none;">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
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
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 선택상품 --%>
                    <span class="fl bk lh30"><label id="lblProdRegStoreProd"/></span>
                    <%-- 조회 --%>
                    <button class="btn_blue fr" id="nxBtnSearch4" ng-click="searchSdselProdRegStore()"><s:message code="cmm.search" /></button>
                    <c:if test="${momsEnvstVal == '1'}">
                        <%-- 확장조회 --%>
                        <button class="btn_blue fr mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()"><s:message code="cmm.search.addShow" /></button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <%-- //body --%>

    <%-- body2 --%>
    <%--<div class="wj-dialog-body">--%>
    <div style="padding: 0px 30px;">
        <%-- 좌측 --%>
        <div class="w50 fl" ng-controller="sdselProdYesRegStoreCtrl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:320px;">
                <div class="updownSet oh mb10">
                    <%-- 적용매장 --%>
                    <span class="fl bk lh30"><s:message code="sideMenu.sdselProdRegStore.regStore"/></span>
                    <%-- 저장 --%>
                    <button class="btn_skyblue" id="btnRegSave" ng-click="regSave()"><s:message code='cmm.save' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:240px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <%-- 저장시 필요 --%>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- //좌측 --%>
        <%-- 우측 --%>
        <div class="w50 fr" ng-controller="sdselProdNoRegStoreCtrl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:320px;">
                <div class="updownSet oh mb10">
                    <%-- 미적용매장 --%>
                    <span class="fl bk lh30"><s:message code="sideMenu.sdselProdRegStore.noRegStore"/></span>
                    <%-- 저장 --%>
                    <button class="btn_skyblue" id="btnNoRegSave" ng-click="noRegSave()"><s:message code='cmm.save' /></button>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:240px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                                <%-- 저장시 필요 --%>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselProdRegStore.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- //우측 --%>
    </div>
    <%-- //body2 --%>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sdselProdRegStore.js?ver=20240221.01" charset="utf-8"></script>