<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="copyAuthorExceptView" class="subCon">
    <div ng-controller="copyAuthorExceptCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="empBatchChange.copyAuthorExcept" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_pageView('copyAuthorExceptCtrl',1)" id="nxBtnSearch1">
                    <s:message code="cmm.search" />
                </button>
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
            </div>
        </div>
        <%-- 기준사원 조회 조건 --%>
        <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
<%--                <tr>--%>
<%--                    <th><s:message code="touchKey.layer.brand" /></th>--%>
<%--                    <td>--%>
<%--                        <div class="sb-select">--%>
<%--                            <wj-combo-box--%>
<%--                                    id="srchStoreHqBrandCdCombo"--%>
<%--                                    ng-model="storeHqBrandCd"--%>
<%--                                    items-source="_getComboData('storeHqBrandCdCombo')"--%>
<%--                                    display-member-path="name"--%>
<%--                                    selected-value-path="value"--%>
<%--                                    is-editable="false"--%>
<%--                                    control="srchStoreHqBrandCdCombo">--%>
<%--                            </wj-combo-box>--%>
<%--                        </div>--%>
<%--                    </td>--%>
<%--                </tr>--%>
                <tr>
                    <%-- 사원번호 --%>
                    <th><s:message code="empBatchChange.empNo" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchEmpNo1"/>
                    </td>
                    <%-- 사원명 --%>
                    <th><s:message code="empBatchChange.empNm" /></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchEmpNm1"/>
                    </td>
                </tr>
            </tbody>
        </table>
        <table class="searchTbl" id="tblSearchAddShowCopy" style="display: none;">
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
                    <%-- 사원관리타입 --%>
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

        <%-- 기준사원 그리드 --%>
        <div class="w100 mt10 mb10">
                <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="empBatchChange.empNm"/>" binding="empNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
    </div>

    <div ng-controller="copyAuthorExcept2Ctrl">

        <p class="mb5" style="font-size: small">- <s:message code="empBatchChange.target.emp" /> -</p>
        <p id="orgEmp" class="mb5" style="font-size: small"></p>
        <input type="text" class="sb-input w100" style="display:none;" id="orgEmpNo"/>
        <%-- 적용대상사원 조회 조건 --%>
        <table class="searchTbl">
            <colgroup>
                <col class="w15"/>
                <col class="w35"/>
                <col class="w15"/>
                <col class="w35"/>
            </colgroup>
            <tbody>
<%--            <tr>--%>
<%--                <th><s:message code="touchKey.layer.brand" /></th>--%>
<%--                <td>--%>
<%--                    <div class="sb-select">--%>
<%--                        <wj-combo-box--%>
<%--                                id="srchStoreHqBrandCdCombo2"--%>
<%--                                ng-model="storeHqBrandCd2"--%>
<%--                                items-source="_getComboData('storeHqBrandCdCombo2')"--%>
<%--                                display-member-path="name"--%>
<%--                                selected-value-path="value"--%>
<%--                                is-editable="false"--%>
<%--                                control="srchStoreHqBrandCdCombo2">--%>
<%--                        </wj-combo-box>--%>
<%--                    </div>--%>
<%--                </td>--%>
<%--            </tr>--%>
            <tr>
                <%-- 사원코드 --%>
                <th><s:message code="empBatchChange.empNo" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchEmpNo2"/>
                </td>
                <%-- 사원명 --%>
                <th><s:message code="empBatchChange.empNm" /></th>
                <td>
                    <input type="text" class="sb-input w100" id="srchEmpNm2"/>
                </td>
            </tr>
            </tbody>
        </table>
        <table class="searchTbl" id="tblSearchAddShowCopy2" style="display: none;">
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
                                id="srchMomsTeamCombo2"
                                ng-model="momsTeam2"
                                items-source="_getComboData('momsTeamCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsTeamCombo2">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- AC점포별 --%>
                <th><s:message code="dayProd.momsAcShop"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsAcShopCombo2"
                                ng-model="momsAcShop2"
                                items-source="_getComboData('momsAcShopCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAcShopCombo2">
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
                                id="srchMomsAreaFgCombo2"
                                ng-model="momsAreaFg2"
                                items-source="_getComboData('momsAreaFgCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsAreaFgCombo2">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 상권 --%>
                <th><s:message code="dayProd.momsCommercial"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsCommercialCombo2"
                                ng-model="momsCommercial2"
                                items-source="_getComboData('momsCommercialCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsCommercialCombo2">
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
                                id="srchMomsShopTypeCombo2"
                                ng-model="momsShopType2"
                                items-source="_getComboData('momsShopTypeCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsShopTypeCombo2">
                        </wj-combo-box>
                    </div>
                </td>
                    <%-- 사원관리타입 --%>
                <th><s:message code="dayProd.momsStoreManageType"/></th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                                id="srchMomsStoreManageTypeCombo2"
                                ng-model="momsStoreManageType2"
                                items-source="_getComboData('momsStoreManageTypeCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchMomsStoreManageTypeCombo2">
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
                                id="srchBranchCdComboo2"
                                ng-model="branchCd2"
                                items-source="_getComboData('branchCdCombo2')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)"
                                control="srchBranchCdComboo2">
                        </wj-combo-box>
                    </div>
                </td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
        </table>

        <%-- 적용대상사원 그리드 --%>
        <div class="w100 mt10 mb10">
            <div class="wj-gridWrap" style="height:120px; overflow-y: hidden; overflow-x: hidden;">
                <wj-flex-grid
                        autoGenerateColumns.="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="35"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNo"/>" binding="empNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="empBatchChange.empNm"/>" binding="empNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>

        <%-- 복사 버튼 --%>
        <div class="tc mt20">
            <button class="btn_blue" id="btnSave" ng-click="saveCopyAuthorExcept()">
                <s:message code="cmm.copy" />
            </button>
        </div>
    </div>

</div>

<script type="text/javascript" src="/resource/solbipos/js/base/store/empBatchChange/copyAuthorExcept.js?ver=20230221.01" charset="utf-8"></script>