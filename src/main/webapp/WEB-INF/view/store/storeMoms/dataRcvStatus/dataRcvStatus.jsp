<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>

<div class="subCon">

    <div class="searchBar">
        <a href="#" class="open fl">${menuNm}</a>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <%-- 조회 --%>
            <button class="btn_blue fr" ng-click="_broadcast('dataRcvStatusCtrl')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
            </button>
            <%-- 확장조회 --%>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                <button class="btn_blue mr5 fl" id="btnSearchAddShow" onclick="$('#tblSearchAddShow').toggle()">
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
            <%-- 조회기간 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
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
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="M"/>
                        <jsp:param name="targetId" value="dataRcvStatusStore"/>
                    </jsp:include>
                </td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <c:if test="${sessionInfo.orgnFg == 'STORE'}">
        <input type="hidden" id="dataRcvStatusStoreCd" value="${sessionInfo.storeCd}"/>
    </c:if>

    <%-- 확장조회 --%>
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

    <%-- 좌측 : 헤더 그리드 --%>
    <div class="w50 mt10 fl" ng-controller="dataRcvStatusCtrl">
        <div class="wj-TblWrapBr mr10 pd10" style="height: 560px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="dataRcvStatus.hdrTitle"/></span>
                <button class="btn_skyblue fr" ng-click="excelDownloadHdr()"><s:message code="cmm.excel.down"/></button>
            </div>
            <div class="wj-gridWrap" style="height: 490px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        id="wjGridDataRcvHdr">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.logDate"/>"     binding="logDate"    width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.regDt"/>"       binding="regDt"      width="130"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.yoil"/>"        binding="yoil"       width="40"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.branchCd"/>"    binding="branchCd"   width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.branchNm"/>"    binding="branchNm"   width="80"  align="left"   is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.storeCd"/>"     binding="storeCd"    width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.storeNm"/>"     binding="storeNm"    width="100" align="left"   is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.hqBrandCd"/>"   binding="hqBrandCd"  width="100"  align="center" is-read-only="true" data-map="hqBrandCdDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.momsTeam"/>"    binding="momsTeam"   width="60"  align="center" is-read-only="true" data-map="momsTeamDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.momsAcShop"/>"  binding="momsAcShop" width="80"  align="center" is-read-only="true" data-map="momsAcShopDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.posNo"/>"       binding="posNo"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.logSeq"/>"      binding="logSeq"     width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.posTelexId"/>"  binding="posTelexId" width="80"  align="center" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--// 좌측 헤더 그리드 --%>

    <%-- 우측 : 상세 그리드 --%>
    <div class="w50 mt10 fr" ng-controller="dataRcvStatusDtlCtrl">
        <div class="wj-TblWrapBr pd10" style="height: 560px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code="dataRcvStatus.dtlTitle"/></span>
                <button class="btn_skyblue fr" ng-click="excelDownloadDtl()"><s:message code="cmm.excel.down"/></button>
            </div>
            <div class="wj-gridWrap" style="height: 490px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        id="wjGridDataRcvDtl">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.logDate"/>"    binding="logDate"    width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.storeCd"/>"    binding="storeCd"    width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.storeNm"/>"    binding="storeNm"    width="100" align="left"   is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.posNo"/>"      binding="posNo"      width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.logSeq"/>"     binding="logSeq"     width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.msCd"/>"       binding="msCd"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.msCdNm"/>"     binding="msCdNm"     width="120" align="left"   is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.msCdEngNm"/>"  binding="msCdEngNm"  width="130" align="left"   is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.viewYn"/>"     binding="viewYn"     width="80"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.reqSeq"/>"     binding="reqSeq"     width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.resCnt"/>"     binding="resCnt"     width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.minProcSeq"/>" binding="minProcSeq" width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="dataRcvStatus.maxProcSeq"/>" binding="maxProcSeq" width="90"  align="center" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>
    <%--// 우측 상세 그리드 --%>

</div>

<script type="text/javascript">
    // List 형식("" 안붙임)
    var momsHqBrandCdComboList       = ${momsHqBrandCdComboList};
    var branchCdComboList            = ${branchCdComboList};
    var momsTeamComboList            = ${momsTeamComboList};
    var momsAcShopComboList          = ${momsAcShopComboList};
    var momsAreaFgComboList          = ${momsAreaFgComboList};
    var momsCommercialComboList      = ${momsCommercialComboList};
    var momsShopTypeComboList        = ${momsShopTypeComboList};
    var momsStoreManageTypeComboList = ${momsStoreManageTypeComboList};
    var momsStoreFg01ComboList       = ${momsStoreFg01ComboList};
    var momsStoreFg02ComboList       = ${momsStoreFg02ComboList};
    var momsStoreFg03ComboList       = ${momsStoreFg03ComboList};
    var momsStoreFg04ComboList       = ${momsStoreFg04ComboList};
    var momsStoreFg05ComboList       = ${momsStoreFg05ComboList};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/dataRcvStatus/dataRcvStatus.js?ver=20260703.01" charset="utf-8"></script>
