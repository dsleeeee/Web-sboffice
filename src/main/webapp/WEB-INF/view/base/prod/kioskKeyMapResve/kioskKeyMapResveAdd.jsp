<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="kioskKeyMapResveAddLayer" control="kioskKeyMapResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">

    <div ng-controller="kioskKeyMapResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="kioskKeyMapResve.kioskKeyMapResve"/>&nbsp;<s:message code="cmm.add"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="kioskKeyMapResve.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchAddStoreCd" ng-model="storeCd" />
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="kioskKeyMapResve.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchAddStoreNm" ng-model="storeNm" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                        <%-- POS번호 --%>
                        <th><s:message code="kioskKeyMapResve.posNo" /></th>
                        <td>
                            <div class="sb-select w100 fl">
                                <wj-combo-box
                                        id="posNoAddCombo"
                                        ng-model="posNo"
                                        control="posNoAddCombo"
                                        items-source="_getComboData('posNoAddCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        selected-index-changed="setAddTuClsType(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </c:if>
                    <%-- 예약키맵그룹 --%>
                    <th><s:message code="kioskKeyMapResve.orgTuClsType" /></th>
                    <td>
                        <div class="sb-select w100 fl">
                            <wj-combo-box
                                    id="tuClsTypeAddCombo"
                                    ng-model="tuClsTypeAdd"
                                    control="tuClsTypeAddCombo"
                                    items-source="_getComboData('tuClsTypeAddCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                        <%-- 매장브랜드 --%>
                        <th><s:message code="cmm.moms.storeHqBrand"/></th>
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
                    </c:if>
                </tr>
                </tbody>
            </table>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <table class="searchTbl" id="tblSearchAddShowAdd" style="display: none;">
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

            <table class="searchTbl mt10" id="tblAddChange" style="display: none;">
                <colgroup>
                    <col class="w13" />
                    <col class="w87" />
                </colgroup>
                <tbody>
                <tr class="brt">
                    <th>
                        <s:message code="kioskKeyMapResve.resveTuClsType" />
                    </th>
                    <th class="oh gr">
                        <div class="sb-select" style="width:120px; float:left;">
                            <wj-combo-box
                                    id="tuClsTypeAddCombo2"
                                    ng-model="tuClsTypeAddChange"
                                    control="tuClsTypeAddCombo2"
                                    items-source="_getComboData('tuClsTypeAddCombo2')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                        <a href="#" class="btn_grayS ml10" ng-click="changeAdd()">일괄적용</a>
                    </th>
                </tr>
                <tr>
                    <th colspan="2">
                        <p class="s12 bk lh20">
                            체크박스에서 선택된 항목만 일괄적용 됩니다.
                        </p>
                    </th>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh sb-select dkbr">
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="searchKioskKeyMapResveAddList()"><s:message code="cmm.search" /></button>
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeEnv()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                    <%-- 일괄변경 --%>
                    <button class="btn_blue mr5 fr" id="btnShow" ng-click="changeAddShow()">
                        <s:message code="kioskKeyMapResve.select.changeAll" />
                    </button>
                </c:if>
             </div>

            <%-- 그리드 영역 --%>
            <div class="w100 mt10">
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
                            id="wjGridHqSalePricePop">

                        <!-- define columns -->
                        <wj-flex-grid-column header="" binding="gChk" width="35"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.branchCd"/>" binding="branchCd" is-read-only="true" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.branchNm"/>" binding="branchNm" is-read-only="true" width="80" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.posNo"/>" binding="posNo" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.envstCd"/>" binding="envstCd" width="70" is-read-only="true" align="center" data-map="envstDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.brand"/>" binding="brand" is-read-only="true" width="80" align="center" data-map="brandDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" is-read-only="true" width="80" align="center" data-map="momsTeamDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" width="80" align="center" data-map="momsAcShopDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.orgTuClsType"/>" binding="orgTuClsType" is-read-only="true" width="80" align="center" data-map="tuClsTypeDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.orgModDt"/>" binding="modDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="kioskKeyMapResve.resveTuClsType"/>" binding="tuClsType" width="90" align="center" data-map="tuClsTypeDataMap"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>

            <div>
                <table class="tblType01">
                    <colgroup>
                        <col class="w20"/>
                        <col class="w30"/>
                        <col class="w20"/>
                        <col class="w30"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="kioskKeyMapResve.startDate"/></th>
                        <td colspan="3">
                            <div class="sb-select">
                                <span class="txtIn w110px">
                                  <wj-input-date
                                          value="startDate"
                                          ng-model="startDate"
                                          control="startDateCombo"
                                          format="yyyy/MM/dd"
                                          min="${tomorrowDate}"
                                          max="9999-12-31"
                                          initialized="_initDateBox(s)">
                                    </wj-input-date>
                                </span>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="wj-dialog-footer">
                <div class="btnSet2">
                    <button class="btn_blue" ng-click="saveProdPrice2()"><s:message code="cmm.save" /></button>
                </div>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/kioskKeyMapResve/kioskKeyMapResveAdd.js?ver=20240221.01" charset="utf-8"></script>