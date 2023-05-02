<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="kioskKeyMapCopyView" name="kioskKeyMapCopyView" class="subCon" style="display: none;">

    <%-- body --%>
    <div ng-controller="kioskKeyMapCopyCtrl">
        <%-- 기준매장포스 조회 조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="kioskKeyMap.original.storeCd" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch1" ng-click="_pageView('kioskKeyMapCopyCtrl',1)"><s:message code="cmm.search" /></button>
            <c:if test="${momsEnvstVal == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeCopy()">
                      <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
            </c:if>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <c:if test="${brandUseFg == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                        <th><s:message code="kioskKeyMap.storeHqBrand" /></th>
                        <td>
                            <div class="sb-select">
                              <wj-combo-box
                                id="srchStoreHqBrandCd1"
                                ng-model="storeHqBrandCd1"
                                items-source="_getComboData('srchStoreHqBrandCd1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCd1Combo">
                              </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                </c:if>
            </c:if>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="kioskKeyMap.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd1" onkeyup="fnNxBtnSearch('1');"/>
                </td>
                <%-- 매장명 --%>
                <th><s:message code="kioskKeyMap.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm1" onkeyup="fnNxBtnSearch('1');"/>
                </td>
            </tr>
            </tbody>
        </table>
        <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
            <table class="searchTbl" id="tblSearchAddShowCopy" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 팀별 --%>
                    <th><s:message code="kioskKeyMap.team"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsTeam1"
                                ng-model="momsTeam1"
                                items-source="_getComboData('srchMomsTeam1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsTeam1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- AC점포별 --%>
                    <th><s:message code="kioskKeyMap.acShop"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsAcShop1"
                                ng-model="momsAcShop1"
                                items-source="_getComboData('srchMomsAcShop1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAcShop1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 지역구분 --%>
                    <th><s:message code="kioskKeyMap.areaFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsAreaFg1"
                                ng-model="momsAreaFg1"
                                items-source="_getComboData('srchMomsAreaFg1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAreaFg1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상권 --%>
                    <th><s:message code="kioskKeyMap.commercial"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsCommercial1"
                                ng-model="momsCommercial1"
                                items-source="_getComboData('srchMomsCommercial1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsCommercial1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 점포유형 --%>
                    <th><s:message code="kioskKeyMap.shopType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsShopType1"
                                ng-model="momsShopType1"
                                items-source="_getComboData('srchMomsShopType1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsShopType1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장관리타입 --%>
                    <th><s:message code="kioskKeyMap.storeManageType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsStoreManageType1"
                                ng-model="momsStoreManageType1"
                                items-source="_getComboData('srchMomsStoreManageType1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreManageType1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 그룹 --%>
                    <th><s:message code="kioskKeyMap.branchCd"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchBranchCd1"
                                ng-model="branchCd1"
                                items-source="_getComboData('srchBranchCd1')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchBranchCd1Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <%-- 기준매장포스 그리드 --%>
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
                            <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeHqBrand"/>" binding="brand" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        </c:if>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.sysStatFg"/>" binding="sysStatFg" width="110"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosNo"/>" binding="posNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskTuMClsFg"/>" binding="tuMClsFg" data-map="tuMClsFgDataMap" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="[<s:message code="kioskKeymap.store"/>]<s:message code="kioskKeyMap.envstVal"/>" binding="env4068" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="[<s:message code="kioskKeyMap.pack"/>]<s:message code="kioskKeyMap.envstVal"/>" binding="env4069" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

    <div ng-controller="kioskKeyMapCopy2Ctrl">

        <%-- 선택한 기준매장포스의 키맵정보 --%>
        <div class="mb20">
            <table class="tblType01">
                <colgroup>
                  <col class="w15" />
                  <col class="w25" />
                  <col class="w15" />
                  <col class="w15" />
                  <col class="w15" />
                  <col class="w15" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="kioskKeyMap.original.storeCd"/></th>
                    <td>
                        <label id="selStore"></label>
                        <input type="hidden" id="hdOrgStorecd"/>
                        <input type="hidden" id="hdOrgPosNo" />
                    </td>
                    <th><s:message code="kioskKeyMap.kioskPosNo"/></th>
                    <td><label id="selKioskPosNo"></label></td>
                    <th><s:message code="kioskKeyMap.kioskTuMClsFg"/></th>
                    <td>
                        <label id="lblTuMClsFg"></label>
                        <input type="hidden" id="hdTuMClsFg" />
                    </td>
                </tr>
                <tr>
                    <th>[<s:message code="kioskKeymap.store"/>]<s:message code="kioskKeyMap.envstVal"/></th>
                    <td>
                        <label id="selEnv4068"></label>
                    </td>
                    <th>[<s:message code="kioskKeyMap.pack"/>]<s:message code="kioskKeyMap.envstVal"/></th>
                    <td>
                        <label id="selEnv4069"></label>
                    </td>
                    <th></th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="6">
                        * 키맵매장복사는 '기준매장포스의 KIOSK중분류사용'과 '적용대상매장포스의 KIOSK중분류사용'이 같은 경우만 복사됩니다.
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <%-- 적용대상매장포스 조회 조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="kioskKeyMap.target.storeCd" /></a>
            <%-- 조회 --%>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch2" ng-click="_pageView('kioskKeyMapCopy2Ctrl',1)"><s:message code="cmm.search" /></button>
            <c:if test="${momsEnvstVal == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mt5 mr5" id="btnSearchAddShow2" ng-click="searchAddShowChangeCopy2()">
                      <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
            </c:if>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
            <c:if test="${brandUseFg == '1'}">
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                        <th><s:message code="kioskKeyMap.storeHqBrand" /></th>
                        <td>
                            <div class="sb-select">
                              <wj-combo-box
                                id="srchStoreHqBrandCd2"
                                ng-model="storeHqBrandCd2"
                                items-source="_getComboData('srchStoreHqBrandCd2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchStoreHqBrandCd2Combo">
                              </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                </c:if>
            </c:if>
            <tr>
                <%-- 매장코드 --%>
                <th><s:message code="kioskKeyMap.storeCd" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd2" onkeyup="fnNxBtnSearch('2');"/>
                </td>
                <%-- 매장명 --%>
                <th><s:message code="kioskKeyMap.storeNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm2" onkeyup="fnNxBtnSearch('2');"/>
                </td>
            </tr>
            </tbody>
        </table>
        <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
            <table class="searchTbl" id="tblSearchAddShowCopy2" style="display: none;">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 팀별 --%>
                    <th><s:message code="kioskKeyMap.team"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsTeam2"
                                ng-model="momsTeam2"
                                items-source="_getComboData('srchMomsTeam2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsTeam2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- AC점포별 --%>
                    <th><s:message code="kioskKeyMap.acShop"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsAcShop2"
                                ng-model="momsAcShop2"
                                items-source="_getComboData('srchMomsAcShop2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAcShop2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 지역구분 --%>
                    <th><s:message code="kioskKeyMap.areaFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsAreaFg2"
                                ng-model="momsAreaFg2"
                                items-source="_getComboData('srchMomsAreaFg2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAreaFg2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상권 --%>
                    <th><s:message code="kioskKeyMap.commercial"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsCommercial2"
                                ng-model="momsCommercial2"
                                items-source="_getComboData('srchMomsCommercial2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsCommercial2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 점포유형 --%>
                    <th><s:message code="kioskKeyMap.shopType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsShopType2"
                                ng-model="momsShopType2"
                                items-source="_getComboData('srchMomsShopType2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsShopType2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 매장관리타입 --%>
                    <th><s:message code="kioskKeyMap.storeManageType"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchMomsStoreManageType2"
                                ng-model="momsStoreManageType2"
                                items-source="_getComboData('srchMomsStoreManageType2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreManageType2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 그룹 --%>
                    <th><s:message code="kioskKeyMap.branchCd"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchBranchCd2"
                                ng-model="branchCd2"
                                items-source="_getComboData('srchBranchCd2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchBranchCd2Combo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <%-- 적용대상매장포스 그리드 --%>
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
                            <wj-flex-grid-column header="<s:message code="storeView.storeHqBrand"/>" binding="brand" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        </c:if>
                    </c:if>
                    <wj-flex-grid-column header="<s:message code="storeView.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeView.storeNm"/>" binding="storeNm" width="200" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.sysStatFg"/>" binding="sysStatFg" width="110"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosNo"/>" binding="posNo" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskTuMClsFg"/>" binding="tuMClsFg" data-map="tuMClsFgDataMap" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="[<s:message code="kioskKeymap.store"/>]<s:message code="kioskKeyMap.envstVal"/>" binding="env4068" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="[<s:message code="kioskKeyMap.pack"/>]<s:message code="kioskKeyMap.envstVal"/>" binding="env4069" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                 </wj-flex-grid>
            </div>
         </div>

        <%-- 복사 버튼 --%>
        <div class="tc mt20">
            <button class="btn_blue" id="btnSave" ng-click="saveCopyStoreKioskKeyMap()">
                <s:message code="cmm.copy" />
            </button>
        </div>
    </div>

</div>
<script>

</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapCopy.js?ver=20230502.02" charset="utf-8"></script>