<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<div class="subCon" ng-controller="storeVerPatchCtrl">
    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="nxBtnSearch" ng-click="_broadcast('storeVerPatchCtrl')">
                <s:message code="cmm.search"/>
            </button>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <%-- 확장조회 --%>
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" ng-click="searchAddShowChange()">
                    <s:message code="cmm.search.addShow" />
                </button>
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
            <%-- 버전선택 --%>
            <th><s:message code="storeVerPatch.selectVer"/></th>
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
            <th><s:message code="storeVerPatch.patchDate"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn w100px">
                        <wj-combo-box
                                id="srchDayGubunCombo"
                                ng-model="dayGubun"
                                items-source="_getComboData('dayGubunCombo')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                control="srchDayGubunCombo"
                                selected-index-changed="setDayGubunCombo(s)">
                        </wj-combo-box>
                    </span>
                    <span class="txtIn"><input id="srchPatchDate" ng-model="patchDate" class="w120px"></span>
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
        <span class="s12 bk lh10">  0으로 시작되는 데모매장은 제외<br />
                                    매출기준 : 2주내에 매출이 있는매장</span>
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.downCurrent"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 480px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    id="wjGridList"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeVerPatch.version"/>" binding="version" width="110" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.regStoreCnt"/>" binding="mainRegStoreCntSale" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.com.patch"/>" binding="mainPatchStoreCntSale2" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.incom.patch"/>" binding="mainRegNotPatchStoreCntSale" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.regStoreCnt"/>" binding="regStoreCntSale" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.regPosCnt"/>" binding="regPosCntSale" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.patchStoreCnt"/>" binding="patchStoreCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.patchPosCnt"/>" binding="patchPosCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.not.patchStoreCnt"/>" binding="notPatchStoreCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.not.patchPosCnt"/>" binding="notPatchPosCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.main.patchStoreCnt"/>" binding="mainPatchStoreCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.main.not.patchStoreCnt"/>" binding="mainNotPatchStoreCntSale" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.regStoreCnt"/>" binding="regStoreCnt" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.regPosCnt"/>" binding="regPosCnt" width="100" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.patchStoreCnt"/>" binding="patchStoreCnt" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.patchPosCnt"/>" binding="patchPosCnt" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.not.patchStoreCnt"/>" binding="notPatchStoreCnt" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.not.patchPosCnt"/>" binding="notPatchPosCnt" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.main.patchStoreCnt"/>" binding="mainPatchStoreCnt" width="120" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeVerPatch.main.not.patchStoreCnt"/>" binding="mainNotPatchStoreCnt" width="120" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";

    var clsFg = ${ccu.getCommCodeSelect("001")};
    var sysStatFg = ${ccu.getCommCodeSelect("005")};
    var areaCd = ${ccu.getCommCodeSelect("061")};

    // List 형식("" 안붙임)
    var momsHqBrandCdComboList = ${momsHqBrandCdComboList};
    var branchCdComboList = ${branchCdComboList};
    var momsTeamComboList = ${momsTeamComboList};
    var momsAcShopComboList = ${momsAcShopComboList};
    var momsAreaFgComboList = ${momsAreaFgComboList};
    var momsCommercialComboList = ${momsCommercialComboList};
    var momsShopTypeComboList = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList = ${momsStoreFg05ComboList};
    var selectVerComboList = ${selectVerComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeVerPatch/storeVerPatch.js?ver=20250526.01" charset="utf-8"></script>

<%-- 매장정보 상세 팝업 레이어 --%>
<c:import url="/WEB-INF/view/store/storeMoms/storeVerPatch/patchStoreDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
