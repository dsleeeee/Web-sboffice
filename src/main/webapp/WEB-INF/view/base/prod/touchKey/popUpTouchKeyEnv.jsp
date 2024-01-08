<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="popUpTouchKeyEnvLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="popUpTouchKeyEnvCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKey.storeUseTouchKey" />
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
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <tr>
                    <th><s:message code="kioskKeyMap.storeCd" /></th>
                    <td>
                        <input type="text" id="srchEnvStoreCd" ng-model="storeCd"/>
                    </td>
                    <th><s:message code="kioskKeyMap.storeNm" /></th>
                    <td>
                        <input type="text" id="srchEnvStoreNm" ng-model="storeNm"/>
                    </td>
                </tr>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                    </c:if>
                    <%-- 터치키 --%>
                    <th><s:message code="touchKey.grp"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="tukeyGrpCombo2"
                                    ng-model="tukeyGrp"
                                    control="tukeyGrpCombo2"
                                    items-source="_getComboData('tukeyGrpCombo2')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
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

            <%-- 버튼영역 --%>
            <div class="mt10 tr">
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeEnv()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue mr5" id="btnSearchPos" ng-click="searchPos()">
                    <s:message code="cmm.search" />
                </button>
                <button class="btn_blue" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
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
                        <wj-flex-grid-column header="<s:message code="touchKey.posNo"/>" binding="posNo" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.grp"/>" binding="env4038" width="85" align="center" is-read-only="true" data-map="grpDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKey.modDt"/>" binding="modDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
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
                            <th><s:message code="touchKey.grp" /></th>
                            <td>
                                <div class="sb-select" style="width:150px; float:left;">
                                    <wj-combo-box
                                            id="touchKeyEnvCombo"
                                            ng-model="touchKeyEnv"
                                            items-source="_getComboData('touchKeyEnvCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            control="touchKeyEnvCombo"
                                            initialized="_initComboBox(s)">
                                    </wj-combo-box>
                                </div>
                                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                                    <div class="fl pd5" style="padding-right: 15px;">
                                        <div style="float: left;"><input type="checkbox" id="chkApplyStore" checked="checked"/></div>
                                        <div style="padding-top: 3px; padding-left: 20px;"><s:message code="touchKey.applyStore" /></div>
                                    </div>
                                </c:if>
                                <button class="btn_blue ml5 fl" id="btnInsertEnv" ng-click="btnInsertEnv()"><s:message code="cmm.apply"/></button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- //적용할 키맵그룹 선택 -->
            </div>
        </div>

    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpTouchKeyEnv.js?ver=20240105.01" charset="utf-8"></script>