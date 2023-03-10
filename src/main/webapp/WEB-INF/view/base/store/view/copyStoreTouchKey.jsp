<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyStoreTouchKeyLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:600px;overflow-y:auto;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font" ng-controller="copyStoreTouchKeyCtrl">
            <s:message code="storeView.copy.store.touchKey" />
            <a href="" class="wj-hide btn_close" ng-click="closePop()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" style="min-height:550px;">

            <div ng-controller="copyStoreTouchKeyCtrl">
                <p class="mb5" style="font-size: small">-<s:message code="storeView.original.storeCd" />-</p>

                <%-- 기준매장 조회 조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w10"/>
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
                                <td>
                                    <c:if test="${momsEnvstVal == '1'}">
                                        <%-- 확장조회 --%>
                                        <button class="btn_skyblue mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeCopy()">
                                            <s:message code="cmm.search.addShow" />
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="storeView.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreCd1" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="storeView.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm1" onkeyup="fnNxBtnSearch();"/>
                        </td>
                        <td>
                            <%--조회--%>
                            <button class="btn_skyblue fr" id="nxBtnSearch" ng-click="searchOrgStoreList()" ><s:message code="cmm.search" /></button>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <c:if test="${momsEnvstVal == '1'}">
                    <table class="searchTbl" id="tblSearchAddShowCopy" style="display: none;">
                        <colgroup>
                            <col class="w15"/>
                            <col class="w30"/>
                            <col class="w15"/>
                            <col class="w30"/>
                            <col class="w10"/>
                        </colgroup>
                        <tbody>
                        <tr>
                                <%-- 팀별 --%>
                            <th><s:message code="dayProd.momsTeam"/></th>
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
                            <th><s:message code="dayProd.momsAcShop"/></th>
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
                            <th><s:message code="dayProd.momsAreaFg"/></th>
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
                            <th><s:message code="dayProd.momsCommercial"/></th>
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
                            <th><s:message code="dayProd.momsShopType"/></th>
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
                            <th><s:message code="dayProd.momsStoreManageType"/></th>
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
                                <%-- 지사 --%>
                            <th><s:message code="dayProd.branchCd"/></th>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchBranchCdComboo"
                                            ng-model="branchCd"
                                            items-source="_getComboData('branchCdCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchBranchCdComboo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </c:if>

                <%-- 기준매장 그리드 --%>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <c:if test="${brandUseFg == '1'}">
                                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                    <wj-flex-grid-column header="<s:message code="storeView.storeHqBrand"/>" binding="brand" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                </c:if>
                            </c:if>
                            <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>
            </div>

            <div ng-controller="copyStoreTouchKey2Ctrl">

                <%-- 선택한 기준매장의 터치키그룹 --%>
                <div class="mb20">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w20"/>
                            <col class="w80"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="storeView.original.storeCd"/></th>
                            <td>
                                <label id="selStore"></label>
                                <input type="hidden" id="hdOrgStorecd"/>
                            </td>
                        </tr>
                        <tr>
                            <th><s:message code="storeView.touchKey.grp"/></th>
                            <td>
                                <div class="sb-select w150px" id="selTouchKeyGrp" style="display: none;">
                                    <wj-combo-box
                                            id="tuKeyGrpCombo"
                                            control="tuKeyGrpCombo"
                                            items-source="_getComboData('tuKeyGrpCombo')"
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
                </div>

                <p class="mb5" style="font-size: small">- <s:message code="storeView.target.storeCd" /> -</p>

                <%-- 적용대상매장 조회 조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w15"/>
                        <col class="w30"/>
                        <col class="w10"/>
                    </colgroup>
                    <tbody>
                    <c:if test="${brandUseFg == '1'}">
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <tr>
                                <th><s:message code="touchKey.layer.brand" /></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchStoreHqBrandCdCombo2"
                                                ng-model="storeHqBrandCd2"
                                                items-source="_getComboData('storeHqBrandCdCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                control="srchStoreHqBrandCdCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <td>
                                    <c:if test="${momsEnvstVal == '1'}">
                                        <%-- 확장조회 --%>
                                        <button class="btn_skyblue mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeCopy2()">
                                            <s:message code="cmm.search.addShow" />
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:if>
                    </c:if>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="storeView.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreCd2" onkeyup="fnNxBtnSearch('2');"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="storeView.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchStoreNm2" onkeyup="fnNxBtnSearch('2');"/>
                        </td>
                        <td>
                            <%--조회--%>
                            <button class="btn_skyblue fr" id="nxBtnSearch2" ng-click="searchTargetStoreList()" ><s:message code="cmm.search" /></button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                    <c:if test="${momsEnvstVal == '1'}">
                        <table class="searchTbl" id="tblSearchAddShowCopy2" style="display: none;">
                            <colgroup>
                                <col class="w15"/>
                                <col class="w30"/>
                                <col class="w15"/>
                                <col class="w30"/>
                                <col class="w10"/>
                            </colgroup>
                            <tbody>
                            <tr>
                                    <%-- 팀별 --%>
                                <th><s:message code="dayProd.momsTeam"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsTeamCombo2"
                                                ng-model="momsTeam2"
                                                items-source="_getComboData('momsTeamCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsTeamCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                    <%-- AC점포별 --%>
                                <th><s:message code="dayProd.momsAcShop"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsAcShopCombo2"
                                                ng-model="momsAcShop2"
                                                items-source="_getComboData('momsAcShopCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsAcShopCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                    <%-- 지역구분 --%>
                                <th><s:message code="dayProd.momsAreaFg"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsAreaFgCombo2"
                                                ng-model="momsAreaFg2"
                                                items-source="_getComboData('momsAreaFgCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsAreaFgCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                    <%-- 상권 --%>
                                <th><s:message code="dayProd.momsCommercial"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsCommercialCombo2"
                                                ng-model="momsCommercial2"
                                                items-source="_getComboData('momsCommercialCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsCommercialCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                    <%-- 점포유형 --%>
                                <th><s:message code="dayProd.momsShopType"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsShopTypeCombo2"
                                                ng-model="momsShopType2"
                                                items-source="_getComboData('momsShopTypeCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsShopTypeCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                    <%-- 매장관리타입 --%>
                                <th><s:message code="dayProd.momsStoreManageType"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchMomsStoreManageTypeCombo2"
                                                ng-model="momsStoreManageType2"
                                                items-source="_getComboData('momsStoreManageTypeCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchMomsStoreManageTypeCombo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                    <%-- 지사 --%>
                                <th><s:message code="dayProd.branchCd"/></th>
                                <td>
                                    <div class="sb-select">
                                        <wj-combo-box
                                                id="srchBranchCdComboo2"
                                                ng-model="branchCd2"
                                                items-source="_getComboData('branchCdCombo2')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchBranchCdComboo2">
                                        </wj-combo-box>
                                    </div>
                                </td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
            </c:if>

                <%-- 적용대상매장 그리드 --%>
                <div class="w100 mt10 mb10">
                    <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30"></wj-flex-grid-column>
                            <c:if test="${brandUseFg == '1'}">
                                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                    <wj-flex-grid-column header="<s:message code="storeView.storeHqBrand"/>" binding="brand" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                </c:if>
                            </c:if>
                            <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                         </wj-flex-grid>
                    </div>
                </div>

                <%-- 복사 버튼 --%>
                <div class="tc mt20">
                    <button class="btn_blue" id="btnSave" ng-click="saveCopyStoreTouchKey()">
                        <s:message code="cmm.copy" />
                    </button>
                </div>
            </div>

        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/view/copyStoreTouchKey.js?ver=20220504.03" charset="utf-8"></script>