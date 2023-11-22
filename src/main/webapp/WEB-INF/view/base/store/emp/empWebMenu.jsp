<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div class="subCon">

    <div ng-controller="empWebMenuCtrl">
        <%-- 제목 및 조회버튼  --%>
        <div class="searchBar">
            <a href="#" class="open fl">${menuNm}</a>
            <button class="btn_blue fr mt5 mr10" id="nxBtnSearch" ng-click="searchWebMenu()"><s:message code="cmm.search"/></button>
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
                <th><s:message code="empWebMenu.sMenuNm" /></th>
                <td>
                    <input type="text" id="srchSMenuNm" ng-model="sMenuNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <th><s:message code="outstockReqDate.empNo" /></th>
                <td>
                    <input type="text" id="srchEmpNo" ng-model="srchEmpNo" class="sb-input w100" />
                </td>
                <th><s:message code="outstockReqDate.empNm" /></th>
                <td>
                    <input type="text" id="srchEmpNm" ng-model="srchEmpNm" class="sb-input w100" />
                </td>
            </tr>
            <tr>
                <th><s:message code="outstockReqDate.userId" /></th>
                <td>
                    <input type="text" id="srchUserId" ng-model="srchUserId" class="sb-input w100" />
                </td>
                <th><s:message code="outstockReqDate.mpNo" /></th>
                <td>
                    <input type="text" id="srchMpNo" ng-model="srchMpNo" class="sb-input w100" />
                </td>
            </tr>
            <tr>
                <%-- 관리브랜드 --%>
                <th><s:message code="outstockReqDate.userHqBrand" /></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchUserHqBrandCdCombo"
                                ng-model="userHqBrandCd"
                                items-source="_getComboData('userHqBrandCdCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchUserHqBrandCdCombo">
                        </wj-combo-box>
                    </div>
                </td>
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
            </tr>
            <c:if test="${momsEnvstVal == '1'}">
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
                <c:if test="${sessionScope.sessionInfo.userId == 'ds021' or sessionScope.sessionInfo.userId == 'ds034' or sessionScope.sessionInfo.userId == 'h0393'}">
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
                        <td></td>
                        <td></td>
                    </tr>
                </c:if>
            </c:if>
            </tbody>
        </table>
        <%-- left (웹메뉴 보여주는 그리드) --%>
        <div class="wj-TblWrap mt10 mb20 w45 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:535px;">
                <div class="wj-gridWrap" style="height:500px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            id="webMenuGrid"
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            allow-merging="Cells">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="empWebMenu.lMenuCd"/>" binding="lMenuCd" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.lMenuNm"/>" binding="lMenuNm" width="80" align="center" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.mMenuCd"/>" binding="mMenuCd" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.mMenuNm"/>" binding="mMenuNm" width="90" align="center" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.sMenuCd"/>" binding="sMenuCd" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.sMenuNm"/>" binding="sMenuNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <input type="hidden" id="sMenuCd" />
        </div>
    </div>

    <%-- right - 사원리스트 --%>
    <div class="wj-TblWrap w55">
        <%-- 사용사원 grid --%>
        <div class="wj-TblWrap mt10 w100 fl" ng-controller="useEmpCtrl">
            <div class="wj-TblWrapBr pd10 " style="height:260px;">
                <div class="ml5">
                    <span class="bk"><s:message code='empWebMenu.useEmp' /></span>
                    <span class="bk lh30" id="lblMenu"></span>
                </div>
                <div class="updownSet oh mb10 pd5">
                    <button class="btn_skyblue fr" id="btnDelEmp" ng-click="delEmp()">
                        <s:message code="empWebMenu.unused" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:170px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.empNo"/>" binding="empNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.empNm"/>" binding="empNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.userId"/>" binding="userId" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.mpNo"/>" binding="mpNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.userHqBrand"/>" binding="userHqBrand" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="branchCd" width="70" align="left" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

        <%-- 미사용 사원 grid --%>
        <div class="wj-TblWrap mt10 w100 fr" ng-controller="unusedEmpCtrl">
            <div class="wj-TblWrapBr pd10 " style="height:260px;">
                <div class="ml5">
                    <span class="bk"><s:message code='empWebMenu.unusedEmp' /></span>
                </div>
                <div class="updownSet oh mb10 pd5">
                    <button class="btn_skyblue fr" id="btnSaveEmp" ng-click="saveEmp()">
                        <s:message code="empWebMenu.use" />
                    </button>
                </div>
                <div class="wj-gridWrap" style="height:180px; overflow-x: hidden; overflow-y: hidden;">

                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="32"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.empNo"/>" binding="empNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empWebMenu.empNm"/>" binding="empNm" width="100" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.userId"/>" binding="userId" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.mpNo"/>" binding="mpNo" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.userHqBrand"/>" binding="userHqBrand" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.moms.branch"/>" binding="branchCd" width="70" align="left" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${momsEnvstVal == '1'}">
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsTeam"/>" binding="momsTeam" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAcShop"/>" binding="momsAcShop" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsAreaFg"/>" binding="momsAreaFg" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsCommercial"/>" binding="momsCommercial" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsShopType"/>" binding="momsShopType" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="cmm.moms.momsStoreManageType"/>" binding="momsStoreManageType" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        </c:if>
                    </wj-flex-grid>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    // [1250 맘스터치] 환경설정값
    var momsEnvstVal = "${momsEnvstVal}";

    // List 형식("" 안붙임)
    var userHqBrandCdComboList = ${userHqBrandCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/empWebMenu.js?ver=20231101.01" charset="utf-8"></script>
