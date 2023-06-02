<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSdselClassRegStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:760px;height:660px;" fade-in="false" fade-out="false">

    <div ng-controller="sdselClassRegStoreCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="sideMenu.sdselClassRegStore.info"/>
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
                    <%-- 선택그룹 --%>
                    <th>
                        <s:message code="sideMenu.sdselClassRegStore.sdselGrp"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchClassRegStoreGroupCd" ng-model="groupCd" readonly style="display: none" />
                        <input type="text" class="sb-input w100" id="srchClassRegStoreGroup" ng-model="group" readonly />
                    </td>
                    <%-- 선택분류 (적용매장구분) --%>
                    <th>
                        <s:message code="sideMenu.sdselClassRegStore.sdselClass"/>
                    </th>
                    <td>
                        <div class="sb-select dkbr">
                            <wj-combo-box
                                    class="w160px fl"
                                    id="srchRegStoreSdselClassCombo"
                                    ng-model="regStoreSdselClassCombo"
                                    items-source="_getComboData('regStoreSdselClassCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchRegStoreSdselClassCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="sideMenu.sdselClassRegStore.storeCd"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch(3);" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="sideMenu.sdselClassRegStore.storeNm"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch(3);" />
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="tblType01" id="tblSearchAddShow" style="display: none;">
                <colgroup>
                    <col class="w20"/>
                    <col class="w30"/>
                    <col class="w20"/>
                    <col class="w30"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 팀별 --%>
                    <th><s:message code="sideMenu.sdselClassRegStore.momsTeam"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.momsAcShop"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.momsAreaFg"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.momsCommercial"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.momsShopType"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.momsStoreManageType"/></th>
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
                    <th><s:message code="sideMenu.sdselClassRegStore.branchCd"/></th>
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
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 선택분류 --%>
                    <span class="fl bk lh30"><label id="lblClassRegStoreClass"/></span>
                    <%-- 조회 --%>
                    <button class="btn_blue fr" id="nxBtnSearch3" ng-click="searchSdselClassRegStore()"><s:message code="cmm.search" /></button>
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
        <div class="w50 fl" ng-controller="sdselClassYesRegStoreCtrl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:320px;">
                <div class="updownSet oh mb10">
                    <%-- 적용매장 --%>
                    <span class="fl bk lh30"><s:message code="sideMenu.sdselClassRegStore.regStore"/></span>
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
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <%-- 저장시 필요 --%>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%-- //좌측 --%>
        <%-- 우측 --%>
        <div class="w50 fr" ng-controller="sdselClassNoRegStoreCtrl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:320px;">
                <div class="updownSet oh mb10">
                    <%-- 미적용매장 --%>
                    <span class="fl bk lh30"><s:message code="sideMenu.sdselClassRegStore.noRegStore"/></span>
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
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                                <%-- 저장시 필요 --%>
                                <wj-flex-grid-column header="<s:message code="sideMenu.sdselClassRegStore.sdselClassCd"/>" binding="sdselClassCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sdselClassRegStore.js?ver=20230602.012" charset="utf-8"></script>