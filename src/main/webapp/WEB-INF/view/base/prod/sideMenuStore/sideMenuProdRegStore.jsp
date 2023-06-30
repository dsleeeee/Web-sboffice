<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div id="sideMenuProdRegStoreView" class="subCon" style="display: none;">
    <div ng-controller="sideMenuProdRegStoreCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="sideMenuStoreTab.sideMenuProdRegStore"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('sideMenuProdRegStoreCtrl',1)" id="nxBtnSearch6">
                    <s:message code="cmm.search" />
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
                <%-- 선택그룹코드 --%>
                <th><s:message code="sideMenuStore.sdselGrpCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselGrpCd" ng-model="sdselGrpCd" onkeyup="fnNxBtnSearch(6);"/>
                </td>
                <%-- 선택그룹코명 --%>
                <th><s:message code="sideMenuStore.sdselGrpNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselGrpNm" ng-model="sdselGrpNm" onkeyup="fnNxBtnSearch(6);"/>
                </td>
            </tr>
            <tr>
                <%-- 선택분류코드 --%>
                <th><s:message code="sideMenuStore.sdselClassCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassCd" ng-model="sdselClassCd" onkeyup="fnNxBtnSearch(6);"/>
                </td>
                <%-- 선택분류명 --%>
                <th><s:message code="sideMenuStore.sdselClassNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselClassNm" ng-model="sdselClassNm" onkeyup="fnNxBtnSearch(6);"/>
                </td>
            </tr>
            <tr>
                <%-- 상품분류 --%>
                <th><s:message code="sideMenuStore.prodClass" /></th>
                <td>
                    <input type="text" id="srchProdClassNm" ng-model="prodClassNm" class="sb-input w70" ng-click="popUpProdClass()" style="float: left;" placeholder="선택" readonly />
                    <input type="hidden" id="srchProdClassCd" ng-model="prodClassCd" disabled/>
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                </td>
                <c:if test="${brandUseFg == '1'}">
                    <%-- 상품브랜드 --%>
                    <th><s:message code="sideMenuStore.prodHqBrand"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchProdHqBrandCd"
                                    items-source="_getComboData('srchProdHqBrandCd')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    control="srchProdHqBrandCdCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <c:if test="${brandUseFg == '0'}">
                    <td></td>
                    <td></td>
                </c:if>
            </tr>
            <tr>
                <%-- 선택상품코드 --%>
                <th><s:message code="sideMenuStore.sdselProdCd"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselProdCd" ng-model="sdselProdCd" onkeyup="fnNxBtnSearch(6);"/>
                </td>
                <%-- 선택상품명 --%>
                <th><s:message code="sideMenuStore.sdselProdNm"/></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchSdselProdNm" ng-model="sdselProdNm" onkeyup="fnNxBtnSearch(6);"/>
                </td>
            </tr>
            <tr>
                <%-- 적용매장구분 --%>
                <th><s:message code="sideMenuStore.regStoreFg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegStoreFgCombo"
                                ng-model="regStoreFg"
                                items-source="_getComboData('regStoreFgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchRegStoreFgCombo">
                        </wj-combo-box>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 oh sb-select dkbr">
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
                <%-- 적용매장구분 --%>
                <th><s:message code="sideMenuStore.regStoreFg"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchRegStoreFgChgCombo"
                                ng-model="regStoreFgChg"
                                items-source="_getComboData('regStoreFgChgCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <%-- 일괄적용 --%>
                <td>
                    <a href="#" class="btn_grayS ml10" ng-click="batchChange('regStoreFgChg')"><s:message code="cmm.batchChange" /></a>
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
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.requireYn"/>" binding="requireYn" data-map="requireYnDataMap" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselProdCd"/>" binding="sdselProdCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.sdselProdNm"/>" binding="sdselProdNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.fixProdFg"/>" binding="fixProdFg" data-map="fixProdFgDataMap" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="sideMenuStore.regStoreFg"/>" binding="regStoreFg" data-map="regStoreFgDataMap" width="85" align="center"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
            <%-- id --%>
            <ul id="sideMenuProdRegStoreCtrlPager" data-size="10">
            </ul>
        </div>
        <%--//페이지 리스트--%>

    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenuStore/sideMenuProdRegStore.js?ver=20230622.02" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>