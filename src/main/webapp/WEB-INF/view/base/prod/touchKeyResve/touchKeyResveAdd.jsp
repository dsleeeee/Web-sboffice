<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="touchKeyResveAddLayer" control="touchKeyResveAddLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">

    <div ng-controller="touchKeyResveAddCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="touchKeyResve.touchKeyResve"/>&nbsp;<s:message code="cmm.add"/>
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
                        <th><s:message code="touchKeyResve.storeCd" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchAddStoreCd" ng-model="storeCd" />
                        </td>
                        <th><s:message code="touchKeyResve.storeNm" /></th>
                        <td>
                            <input type="text" class="sb-input w100" id="srchAddStoreNm" ng-model="storeNm" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th><s:message code="touchKeyResve.touchKeyGrp" /></th>
                    <td>
                        <div class="sb-select w100 fl">
                            <wj-combo-box
                                    id="touchKeyGrpAddCombo"
                                    ng-model="touchKeyGrp"
                                    control="touchKeyGrpCombo"
                                    items-source="_getComboData('touchKeyGrpCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <%-- 매장브랜드 --%>
                        <th><s:message code="dayProd.storeHqBrand"/></th>
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

            <table class="searchTbl mt10" id="tblAddChange" style="display: none;">
                <colgroup>
                    <col class="w13" />
                    <col class="w87" />
                </colgroup>
                <tbody>
                <tr class="brt">
                    <th>
                        <s:message code="touchKeyResve.touchKeyGrp" />
                    </th>
                    <th class="oh gr">
                        <div class="sb-select" style="width:120px; float:left;">
                            <wj-combo-box
                                    id="touchKeyGrpAddCombo2"
                                    ng-model="touchKeyGrpAddChange"
                                    control="touchKeyGrpAddCombo2"
                                    items-source="_getComboData('touchKeyGrpAddCombo2')"
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
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_pageView('touchKeyResveAddCtrl',1)"><s:message code="cmm.search" /></button>
                <c:if test="${momsEnvstVal == '1' and sessionInfo.orgnFg == 'HQ'}">
                    <%-- 확장조회 --%>
                    <button class="btn_blue fr mr5" id="btnSearchAddShow" ng-click="searchAddShowChangeEnv()">
                        <s:message code="cmm.search.addShow" />
                    </button>
                </c:if>
                <button class="btn_blue mr5 fr" id="btnShow" ng-click="changeAddShow()">
                    <s:message code="touchKeyResve.select.changeAll" />
                </button>
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
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.branchCd"/>" binding="branchCd" is-read-only="true" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.branchNm"/>" binding="branchNm" is-read-only="true" width="80" is-read-only="true" align="right" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="left"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.brand"/>" binding="brand" is-read-only="true" width="80" align="center" data-map="brandDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.momsTeam"/>" binding="momsTeam" is-read-only="true" width="80" align="center" data-map="momsTeamDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.momsAcShop"/>" binding="momsAcShop" width="80" align="center" data-map="momsAcShopDataMap" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.org"/>" binding="orgTouchKeyGrp" is-read-only="true" width="100" align="center" data-map="touchKeyGrpDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.modDt"/>" binding="modDt" is-read-only="true" width="130" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="touchKeyResve.update"/>" binding="touchKeyGrp" width="100" align="center" data-map="touchKeyGrpDataMap"></wj-flex-grid-column>
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
                        <th><s:message code="touchKeyResve.startDate"/></th>
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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKeyResve/touchKeyResveAdd.js?ver=20230327.01" charset="utf-8"></script>
