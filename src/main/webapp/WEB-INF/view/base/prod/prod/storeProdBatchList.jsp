<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="storeProdBatchListLayer" control="storeProdBatchListLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <div ng-controller="storeProdBatchListCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.title.storeList"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"><s:message code="prod.title.storeList"/></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_pageView('storeProdBatchListCtrl',1)">
                        <s:message code="cmm.search" />
                    </button>
                    <c:if test="${momsEnvstVal == '1'}">
                        <%-- 확장조회 --%>
                        <button class="btn_blue mr5" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                            <s:message code="cmm.search.addShow" />
                        </button>
                    </c:if>
                </div>
            </div>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 매장코드 --%>
                    <th><s:message code="prod.storeCd" /></th>
                    <td>
                        <input type="text" id="srchBatStoreCd" class="sb-input w100" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th><s:message code="prod.storeNm" /></th>
                    <td>
                        <input type="text" id="srchBatStoreNm" class="sb-input w100" ng-model="storeNm" />
                    </td>
                </tr>
                <c:if test="${brandUseFg == '1'}">
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                      <tr>
                        <%-- 매장브랜드 --%>
                        <th><s:message code="prod.storeHqBrand" /></th>
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
                    <table class="searchTbl" id="tblSearchAddShow" style="display: none;">
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

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                       <!-- define columns -->
                       <wj-flex-grid-column header="<s:message code="prod.hqOfficeCd"/>" binding="hqOfficeCd" is-read-only="true" align="center"></wj-flex-grid-column>
                       <wj-flex-grid-column header="<s:message code="prod.storeCd"/>" binding="storeCd" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                       <wj-flex-grid-column header="<s:message code="prod.storeNm"/>" binding="storeNm" width="350"is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
               </div>
           </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/storeProdBatchList.js?ver=20230110.02" charset="utf-8"></script>