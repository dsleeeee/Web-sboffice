<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="sideMenuClassView" class="subCon" style="display: none;padding: 10px 20px 40px;">
    <div ng-controller="sideMenuClassCtrl">

        <%-- 조회조건 --%>
        <%--<div class="searchBar flddUnfld">--%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="sideMenuStoreTab.sideMenuClass"/></a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" ng-click="_broadcast('sideMenuClassCtrl',1)" id="nxBtnSearch3">
                    <s:message code="cmm.search" />
                </button>
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
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
                <%-- 선택분류 --%>
                <th><s:message code="sideMenuStore.sdselClass"/></th>
                <td>
                    <%-- 선택분류 선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectSdselClass.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="sideMenuClassSdselClass"/>
                    </jsp:include>
                    <%--// 선택분류 선택 모듈 사용시 include --%>
                </td>
                <%-- 등록구분 --%>
                <th><s:message code="sideMenuStore.regYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegYnCombo"
                                ng-model="regYn"
                                items-source="_getComboData('regYnCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchRegYnCombo">
                        </wj-combo-box>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="sideMenuClassClassStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>
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

        <div class="mt10 oh">
            <p class="tl s14 mt5 lh15">- '적용매장구분'이 '제외매장', '허용매장'인 경우에만 조회됩니다.</p>
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCurrent"/></button>
            <%-- 저장 --%>
            <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></button>
        </div>

        <%-- 일괄적용 --%>
        <table class="searchTbl mt10">
            <colgroup>
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
                <col class="w15" />
                <col class="w15" />
                <col class="w20" />
            </colgroup>
            <tbody>
            <tr class="brt">
                <%-- 등록구분 --%>
                <th><s:message code="sideMenuStore.regYn"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegYnChgCombo"
                                ng-model="regYnChg"
                                items-source="_getComboData('regYnChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('regYnChg')"><s:message code="cmm.batchChange" /></a>
                </td>
                <td colspan="3"></td>
            </tr>
            </tbody>
        </table>

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
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselGrpCd"/>" binding="sdselGrpCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselGrpNm"/>" binding="sdselGrpNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselClassCd"/>" binding="sdselClassCd" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselClassNm"/>" binding="sdselClassNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.storeNm"/>" binding="storeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.regYn"/>" binding="regYn" data-map="regYnDataMap" width="80" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenuStore/sideMenuClass.js?ver=20240605.01" charset="utf-8"></script>