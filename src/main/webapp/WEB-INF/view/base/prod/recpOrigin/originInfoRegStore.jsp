<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="wjOriginInfoRegStoreLayer" control="wjOriginInfoRegStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:630px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="cmm.store.select"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="originInfoRegStoreCtrl">
            <div class="w100">
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th><s:message code="selectStore.storeCd" /></th>
                        <td>
                            <input type="text" id="srchStoreCd" ng-model="srchStoreCd"/>
                        </td>
                        <%-- 매장명 --%>
                        <th><s:message code="selectStore.storeNm" /></th>
                        <td>
                            <input type="text" id="srchStoreNm" ng-model="srchStoreNm"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 구분(판매가변경제한매장) --%>
                        <th><s:message code="selectStore.gubun"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchStoreChgNotCombo"
                                        ng-model="storeChgNot"
                                        items-source="_getComboData('storeChgNotCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchStoreChgNotCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장브랜드 --%>
                        <th><s:message code="cmm.moms.storeHqBrand" /></th>
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
                        <%-- 그룹 --%>
                        <th><s:message code="cmm.moms.branch" /></th>
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
                    </tr>
                    <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                    </c:if>
                    <tr>
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
                        <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
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
                        </c:if>
                        <c:if test="${sessionScope.sessionInfo.userId != 'ds021' and sessionScope.sessionInfo.userId != 'ds034' and sessionScope.sessionInfo.userId != 'h0393'}">
                            <td></td>
                            <td></td>
                        </c:if>
                    </tr>
                    <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
                        <tr>
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
                        </tr>
                        <tr>
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
                            <td></td>
                            <td></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>

                <%-- 조회조건 --%>
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <td colspan="4">
                            <%-- 버튼영역 --%>
                            <div class="tr">
                                <div>
                                    <%-- 조회 --%>
                                    <button class="btn_skyblue ml5 fr" ng-click="searchStore()"><s:message code="cmm.search" /></button>
                                    <%-- 적용 --%>
                                    <button id="funcSave" class="btn_skyblue ml10 fr" ng-click="originInfoRegStore()"><s:message code="cmm.apply" /></button>
                                    <%-- 관리코드리스트 --%>
                                    <div class="sb-select fr" style="width:150px;">
                                        <wj-combo-box
                                                id="srchOriginCdListCombo"
                                                ng-model="originCdList"
                                                items-source="_getComboData('originCdListCombo')"
                                                display-member-path="name"
                                                selected-value-path="value"
                                                is-editable="false"
                                                initialized="_initComboBox(s)"
                                                control="srchOriginCdListCombo">
                                        </wj-combo-box>
                                    </div>
                                </div>

                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%--위즈모 테이블--%>
                <div class="theGrid mt10" style="height: 400px;">
                    <wj-flex-grid
                            id="wjGrid"
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectStore.storeChgNot"/>" binding="storeChgNot" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOriginInfo.originCd"/>" binding="originCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOriginInfo.originNm"/>" binding="originNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOriginInfo.lastModDt"/>" binding="modDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="recpOriginInfo.lastModId"/>" binding="modId" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script>
    var sysStatFg = ${ccu.getCommCode("005")};
    var momsHqBrandCdComboList;
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/recpOrigin/originInfoRegStore.js?ver=20250203.01" charset="utf-8"></script>