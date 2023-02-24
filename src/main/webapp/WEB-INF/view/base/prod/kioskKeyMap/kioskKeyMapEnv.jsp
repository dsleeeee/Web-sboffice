<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="kioskKeyMapEnvLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="kioskKeyMapEnvCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="popTitle"></label>
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
                        <input type="text" id="srchEnvStoreCd"/>
                    </td>
                    <th><s:message code="kioskKeyMap.storeNm" /></th>
                    <td>
                        <input type="text" id="srchEnvStoreNm"/>
                    </td>
                </tr>
                <tr>
                    <%-- 매장상태구분 --%>
                    <th><s:message code="kioskKeyMap.sysStatFg" /></th>
                    <td>
                        <div class="sb-select w100">
                            <wj-combo-box
                                    id="srchEnvSysStatFg"
                                    items-source="_getComboData('srchEnvSysStatFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchEnvSysStatFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- KIOSK중분류사용 --%>
                    <th><s:message code="kioskKeyMap.kioskTuMClsFg" /></th>
                    <td>
                        <div class="sb-select w100 mr5">
                            <wj-combo-box
                                    id="tuMClsFgMapEnv"
                                    ng-model="tuMClsFgMapEnv"
                                    items-source="_getComboData('tuMClsFgMapEnv')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="tuMClsFgMapEnvCombo">
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
                            <th></th>
                            <td></td>
                        </tr>
                    </c:if>
                </c:if>
                </tbody>
            </table>
            <c:if test="${momsEnvstVal == '1'}">
                    <table class="searchTbl" id="tblSearchAddShowEnv" style="display: none;">
                        <colgroup>
                            <col class="w20"/>
                            <col class="w30"/>
                            <col class="w20"/>
                            <col class="w30"/>
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
            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <c:if test="${momsEnvstVal == '1'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeEnv()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue" id="btnSearchPos" ng-click="btnSearchPos()"><s:message code="cmm.search" /></button>
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
                          id="wjGridList">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.storeNm"/>" binding="storeNm" width="160" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.sysStatFg"/>" binding="sysStatFg" width="85"  data-map="sysStatFgDataMap" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskPosNo"/>" binding="posNo" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.kioskTuMClsFg"/>" binding="tuMClsFg" data-map="tuMClsFgDataMap" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.envstVal"/>" binding="env4068" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMap.envstVal"/>" binding="env4069" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
                <!-- 적용할 키맵그룹 선택 -->
                <div style="padding-top:20px;">
                    <table class="tblType01">
                        <colgroup>
                            <col width="w20" />
                            <col width="w80" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th><s:message code="kioskKeyMap.tuClsType" /></th>
                            <td>
                                <div class="sb-select" style="width:150px; float:left;">
                                    <wj-combo-box
                                            id="envTuClsType"
                                            ng-model="envTuClsType"
                                            items-source="_getComboData('envTuClsType')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="envTuClsTypeCombo"
                                            selected-index-changed="setEnvTuClsTypeCombo(s)">
                                    </wj-combo-box>
                                </div>
                                <div class="fl pd5" style="padding-right: 15px;">
                                    <div style="float: left;"><input type="checkbox" id="chkTuClsTypeStore" checked="checked"/></div>
                                    <div style="padding-top: 3px; padding-left: 20px;"><s:message code="kioskKeyMap.tuClsTypeStore" /></div>
                                </div>
                                <div class="sb-select" style="width:170px; float:left; padding-top:7px;">
                                    <%-- KIOSK중분류사용 --%>
                                    <label id="lblTuMClsFgMapEnv"></label>
                                    <input type="hidden" id="hdTuMClsFgMapEnv" />
                                </div>
                                <button class="btn_blue ml5 fl" id="btnInsertEnv" ng-click="btnInsertEnv()"><s:message code="cmm.apply"/></button>
                                <input type="hidden" id="hdEnvstCd"/>
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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMap/kioskKeyMapEnv.js?ver=20220823.02" charset="utf-8"></script>