<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="kioskKeyMapStoreRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskKeyMapStoreRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="kioskKeyMap.storeReg" />
            <a href="" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w20" />
                    <col class="w30" />
                    <col class="w20" />
                    <col class="w30" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="kioskKeyMap.storeCd" /></th>
                    <td>
                        <input type="text" id="srchStoreCd"/>
                    </td>
                    <th><s:message code="kioskKeyMap.storeNm" /></th>
                    <td>
                        <input type="text" id="srchStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="kioskKeyMap.sysStatFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchSysStatFg"
                                    items-source="_getComboData('srchSysStatFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- KIOSK중분류사용 --%>
                    <th><s:message code="kioskKeyMap.kioskTuMClsFg" /></th>
                    <td>
                        <div class="sb-select w100 mr5">
                            <wj-combo-box
                                    id="tuMClsFgStoreRegist"
                                    ng-model="tuMClsFgStoreRegist"
                                    items-source="_getComboData('tuMClsFgStoreRegist')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="tuMClsFgStoreRegistCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <c:if test="${brandUseFg == '1'}">
                  <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                      <tr>
                          <%-- 매장브랜드 --%>
                          <th><s:message code="kioskKeyMap.storeHqBrand" /></th>
                          <td>
                            <div class="sb-select">
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
                          <td></td>
                          <td></td>
                      </tr>
                  </c:if>
                </c:if>
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
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <c:if test="${momsEnvstVal == '1'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue" id="btnSearchStore" ng-click="btnSearchStore()"><s:message code="cmm.search" /></button>
            </div>
            <%-- 그리드 영역 --%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="wjGridKioskKeyMapStoreReg">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeNm"/>" binding="storeNm" width="160" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.sysStatFg"/>" binding="sysStatFg" width="85"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <%--<wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosCnt"/>" binding="kioskPosCnt" width="100" align="center" is-read-only="true"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosNo"/>" binding="posNo" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskTuMClsFg"/>" binding="tuMClsFg" data-map="tuMClsFgDataMap" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
                <!-- 적용할 키맵그룹 선택 -->
                <div style="padding-top:20px;">
                    <table class="tblType01">
                        <colgroup>
                            <col width="20%" />
                            <col width="80%" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="kioskKeyMap.tuClsType" /></th>
                            <td colspan="3">
                                <div class="sb-select" style="width:150px; float:left;">
                                    <wj-combo-box
                                            id="applyTuClsType"
                                            ng-model="applyTuClsType"
                                            items-source="_getComboData('applyTuClsType')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="applyTuClsTypeCombo"
                                            selected-index-changed="setApplyTuClsTypeCombo(s)">
                                    </wj-combo-box>
                                </div>
                                <div class="sb-select" style="width:170px; float:left; padding-top:7px;">
                                    <%-- KIOSK중분류사용 --%>
                                    <label id="lblTuMClsFgStoreRegist"></label>
                                    <input type="hidden" id="hdTuMClsFgStoreRegist" />
                                </div>
                               <button class="btn_blue ml10 fl" id="btnInsertStore" ng-click="btnInsertStore()"><s:message code="cmm.apply"/></button>
                           </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                * 키맵그룹 적용은 '선택한 키맵그룹에 KIOSK중분류사용' 과 '체크한 매장에 KIOSK중분류사용' 가 같은 매장만 적용됩니다.
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapStoreRegist.js?ver=20231101.01" charset="utf-8"></script>