<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" id ="storePosVersionView" style="display: none;padding: 10px 20px 40px;">
    <div class="subCon" ng-controller="storePosVersionCtrl">
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <%-- 조회 --%>
                <button class="btn_blue fr" id="nxBtnSearch1" ng-click="_broadcast('storePosVersionCtrl')">
                    <s:message code="cmm.search"/>
                </button>
                <%-- 확장조회 --%>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                    <c:if test="${momsEnvstVal == '1'}">
                        <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                            <s:message code="cmm.search.addShow" />
                        </button>
                    </c:if>
                </c:if>
            </div>
        </div>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
                <tr>
                     <%-- 매장코드 --%>
                    <th><s:message code="storePosVersion.storeCd"/></th>
                    <td>
                        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                    <%-- 매장명 --%>
                    <th><s:message code="storePosVersion.storeNm"/></th>
                    <td>
                        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" onkeyup="fnNxBtnSearch('1');"/>
                    </td>
                </tr>
                <tr>
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
                    <%-- 적용매장 --%>
                    <th><s:message code="storePosVersion.registFg"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                              id="srchRegistFg"
                              ng-model="registFg"
                              items-source="_getComboData('registFgCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchRegistFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 버전선택 --%>
                    <th><s:message code="storePosVersion.selectVer"/></th>
                    <td colspan="3">
                        <div class="sb-select">
                            <wj-combo-box
                              id="srchSelectVer"
                              ng-model="selectVer"
                              items-source="_getComboData('selectVerCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchSelectVerCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 최종매출일 --%>
                    <th><s:message code="storePosVersion.lastSaleDate"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                              id="srchLastSale"
                              ng-model="lastSale"
                              items-source="_getComboData('lastSaleCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchLastSaleCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 포스로그인일자 --%>
                    <th><s:message code="storePosVersion.posLogDt"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchPosLogDt"
                                    ng-model="posLogDt"
                                    items-source="_getComboData('posLogDtCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchPosLogDtCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <tr>
                    <%-- 포스메인여부 --%>
                    <th><s:message code="storePosVersion.mainVal"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchMainVal"
                                    ng-model="mainVal"
                                    items-source="_getComboData('mainValCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchMainValCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                        <%-- 포스용도 --%>
                        <th><s:message code="storePosVersion.subVal"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchSubVal"
                                        ng-model="subVal"
                                        items-source="_getComboData('subValCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)"
                                        control="srchSubValCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                </tr>
                <tr>
                    <%-- 버전체크 --%>
                    <th><s:message code="storePosVersion.verChk"/></th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                              id="srchVerChk"
                              ng-model="verChk"
                              items-source="_getComboData('verChkCombo')"
                              display-member-path="name"
                              selected-value-path="value"
                              is-editable="false"
                              initialized="_initComboBox(s)"
                              control="srchVerChkCombo">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 패치여부 --%>
                    <th id="patchFgTh" style="display:none"><s:message code="storePosVersion.patchFg"/></th>
                    <td id="patchFgTd" style="display:none">
                        <div class="sb-select">
                            <wj-combo-box
                                    id="srchPatchFg"
                                    ng-model="patchFg"
                                    items-source="_getComboData('patchFgCombo')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    control="srchPatchFgCombo">
                            </wj-combo-box>
                        </div>
                    </td>
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

        <div class="mt10 oh sb-select dkbr">
            <%-- 엑셀다운로드 --%>
            <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCurrent"/></button>
        </div>

        <div class="w100 mt10">
        <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    id="wjGridPosVersionList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storePosVersion.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.posNo"/>" binding="posNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.posNm"/>" binding="posNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.verTypeFg"/>" binding="verTypeFg" data-map="verTypeFgDataMap" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.posVerNo1"/>" binding="posVerNo1" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.maxVerSerNo"/>" binding="maxVerSerNo" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.verSerAll"/>" binding="verSerAll" width="180" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.verChk"/>" binding="verChk" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.maxSaleDate"/>" binding="maxSaleDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.registFg"/>" binding="registFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.registFgStore"/>" binding="registFgStore" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.mainVal"/>" binding="mainVal" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.subVal"/>" binding="subVal" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.posLogDt"/>" binding="lastLoginDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.patchDate"/>" binding="patchDate" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.patchFg"/>" binding="patchFg" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.posVerNo2"/>" binding="posVerNo2" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.patchVerNo"/>" binding="patchVerNo" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.scriptNm"/>" binding="scriptNm" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storePosVersion.patchErrMsg"/>" binding="patchErrMsg" width="120" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
</div>


<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storePosVersion/storePosVersion.js?ver=20240312.01" charset="utf-8"></script>


