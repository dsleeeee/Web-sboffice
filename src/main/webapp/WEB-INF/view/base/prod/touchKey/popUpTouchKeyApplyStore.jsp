<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="popUpApplyStoreLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:700px;">
    <div class="wj-dialog wj-dialog-columns">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.layer.title" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body" ng-controller="popUpApplyStoreCtrl">
            <div>
                <table class="tblType01">
                    <colgroup>
                        <col width="20%" />
                        <col width="30%" />
                        <col width="20%" />
                        <col width="30%" />
                    </colgroup>
                    <tbody>
                    <c:if test="${brandUseFg == '1'}">
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <tr>
                                <th><s:message code="touchKey.layer.brand" /></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchStoreHqBrandCdCombo"
                                                ng-model="storeHqBrandCd"
                                                items-source="_getComboData('storeHqBrandCdCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="srchStoreHqBrandCdCombo">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                    <tr>
                        <th><s:message code="touchKey.layer.srchStoreCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                        </td>
                        <th><s:message code="touchKey.layer.srchStoreNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="touchKey.layer.sysStatFg" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchSysStatFgCombo"
                                        ng-model="sysStatFg"
                                        items-source="_getComboData('srchSysStatFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th><s:message code="touchKey.layer.clsFg" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchClsFgCombo"
                                        ng-model="clsFg"
                                        items-source="_getComboData('srchClsFgCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <c:if test="${momsEnvstVal == '1'}">
                    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
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
            </div>

            <%-- 조회 --%>
            <div class="tr mt10 pdb20 oh bb">
                <c:if test="${momsEnvstVal == '1'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue" id="btnSrchApplyStore" ng-click="_broadcast('popUpApplyStoreCtrl')">
                    <s:message code="touchKey.layer.srchBtn" />
                </button>
                <button class="btn_blue" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>

            <div class="wj-dialog-content" style="height:393px;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flexLayer"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.brand"/>" binding="brand" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.storeNm"/>" binding="storeNm" width="*" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.sysStatFg"/>" binding="sysStatFgNm" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.clsFg"/>" binding="clsFgNm" width="65" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
            <!-- 적용할 터치키 그룹 선택 -->
            <div style="padding-top:20px;">
                <table class="tblType01">
                    <colgroup>
                        <col width="20%" />
                        <col width="30%" />
                        <col width="20%" />
                        <col width="30%" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="touchKey.grp" /></th>
                        <td colspan="3">
                            <div class="sb-select" style="width:300px; float:left;">
                                <wj-combo-box
                                        id="applyTouchKeyGrpCombo"
                                        ng-model="applyTouchKeyGrp"
                                        items-source="_getComboData('applyTouchKeyGrpCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index="0"
                                        control="applyTouchKeyGrpCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="wj-dialog-footer">
                <button class="btn_blue"  ng-click="btnTouchKeyApplyStore()"><s:message code="cmm.apply" /></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
  var sysStatFgComboData = ${ccu.getCommCode("005")};
  var clsFgComboData = ${ccu.getCommCode("001")};
  var hqOfficeCd = "${hqOfficeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpTouchKeyApplyStore.js?ver=20240221.01" charset="utf-8"></script>