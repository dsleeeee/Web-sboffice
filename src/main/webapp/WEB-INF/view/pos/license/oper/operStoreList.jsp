<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="operStoreListView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="operStoreListCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="oper.operStoreList" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('operStoreListCtrl',1)"  id="nxBtnSearch3">
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
            <%-- 시스템 오픈일자 --%>
            <th>
                <s:message code="oper.sysOpenDt" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                    value="startDate"
                                    ng-model="startDate"
                                    control="startDateCombo"
                                    min="2018-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                    <span class="rg">~</span>
                    <span class="txtIn w110px">
                            <wj-input-date
                                    value="endDate"
                                    ng-model="endDate"
                                    control="endDateCombo"
                                    min="2018-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                    <span class="chk ml10" style="display:none">
                          <input type="checkbox" id="chkDt" ng-model="isChecked" />
                          <label for="chkDt">
                            <s:message code="oper.noSaleStore" />
                          </label>
                        </span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 관리밴사 --%>
            <th>
                <s:message code="oper.manageVan" />
            </th>
            <td>
                <input type="text" id="manageVanNmOper" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchManageVanOper()" style="float: left;">
                <input type="hidden" id="manageVanCdOper" ng-hide="true">
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelManageVanCdOper" style="margin-left: 5px;" ng-click="delManageVanCdOper()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${orgnFg == 'MASTER'}">
                <%-- 관리업체 --%>
                <th>
                    <s:message code="oper.agency" />
                </th>
                <td>
                    <input type="text" id="agencyNmOper" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchAgencyOper()" style="float: left;">
                    <input type="hidden" id="agencyCdOper" ng-hide="true">
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelAgencyCdOper" style="margin-left: 5px;" ng-click="delAgencyCdOper()"><s:message code="cmm.selectCancel"/></button>
                </td>
            </c:if>
            <c:if test="${orgnFg == 'AGENCY'}">
                <th></th>
                <td></td>
            </c:if>
        </tr>
        <tr>
            <%-- 본사코드 --%>
            <th>
                <s:message code="oper.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="oper.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="oper.storeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="oper.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <th><s:message code="oper.posUsage"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchPosUsage"
                            ng-model="posUsage"
                            items-source="_getComboData('posUsageCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchPosUsageCombo">
                    </wj-combo-box>
                </div>
            </td>
            <th><s:message code="oper.useOs"/></th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchUseOs"
                            ng-model="useOs"
                            items-source="_getComboData('useOsCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchUseOsCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
    </div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="operStoreListGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        frozen-columns="4"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeCd"/>" binding="hqOfficeCd"     width=" 70" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.hqOfficeNm"/>" binding="hqOfficeNm"     width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeCd"/>" binding="storeCd"        width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.storeNm"/>" binding="storeNm"        width="150" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.runDist"/>" binding="dist"           width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.runAgency"/>" binding="agencyNm"       width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.progFg"/>" binding="envstVal"       width=" 90" is-read-only="true" align="center" data-map="posFgDataMap"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.posNo"/>" binding="posNo"          width=" 70" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.posUsage"/>" binding="subVal"         width=" 90" is-read-only="true" align="center" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysStatNm"/>" binding="sysStatFg"      width=" 50" is-read-only="true" align="center" data-map="sysStatFgDataMap"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.useOs"/>" binding="winEdtNo"       width="150" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.posVerNo"/>" binding="posVerNo"       width="120" is-read-only="true" align="center"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.vanNm"/>" binding="vanNm"          width=" 80" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.minInstInsDt"/>" binding="minInstInsDt"   width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.maxInstInsDt"/>" binding="maxInstInsDt"   width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="oper.sysOpenDate"/>" binding="openDt"         width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var posFgData = ${ccu.getCommCodeExcpAll("059")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";

    <%-- 포스용도 --%>
    var posUsage = ${posUsage};
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/oper/operStoreList.js?ver=20240503.01" charset="utf-8"></script>

