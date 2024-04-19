<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="runCopyCntView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="runCopyCntCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="runStore.runCopyCnt" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('runCopyCntCtrl',1)"  id="nxBtnSearch2">
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
            <%-- 조회 월 --%>
            <th>
                <s:message code="runStore.runSchMonth" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="startMonth2" ng-model="srchStartMonth2" class="w110px"></span>
                    <span class="chk ml10" style="display:none">
                        <input type="checkbox" id="chkDt" ng-model="isChecked" />
                        <label for="chkDt">
                            <s:message code="oper.noSaleStore" />
                        </label>
                    </span>
                </div>
            </td>
        </tr>
        <%--<tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >--%>
        <tr>
            <%-- 관리밴사 --%>
            <th>
                <s:message code="runStore.manageVan" />
            </th>
            <td>
                <input type="text" id="manageVanNmCopy" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchManageVanCopy()" style="float: left;">
                <input type="hidden" id="ssl_srchManageVanCdCopy" ng-hide="true">
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelManageVanCdCopy" style="margin-left: 5px;" ng-click="delManageVanCdCopy()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${orgnFg == 'MASTER'}">
                <%-- 관리업체 --%>
                <th>
                    <s:message code="runStore.agency" />
                </th>
                <td>
                    <input type="text" id="agencyNmCopy" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchAgencyCopy()" style="float: left;">
                    <input type="hidden" id="ssl_srchAgencyCdCopy" ng-hide="true">
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelAgencyCdCopy" style="margin-left: 5px;" ng-click="delAgencyCdCopy()"><s:message code="cmm.selectCancel"/></button>
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
                <s:message code="runStore.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="copyHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="runStore.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="copyHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="runStore.storeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="copyStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('2');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="runStore.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="copyStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('2');"/>
            </td>
        </tr>
        <tr>
            <th>
                <s:message code="runStore.includeFg" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchIncludeFg"
                            ng-model="includeFg2"
                            items-source="_getComboData('includeFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchIncludeFgCombo">
                    </wj-combo-box>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 tr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5" ng-click="excelDownloadInfo()"><s:message code="cmm.excel.down"/></button>
        <div class="tooltipBtn fl">설명
            <span class="tooltiptext tooltip-right"style="width:430px">
          * <s:message code="runStore.txt1"/><br/>
          * <s:message code="runStore.txt2"/><br/>
          * <s:message code="runStore.txt3"/><br/>
        </span>
        </div>
        <p id="storePosCnt" class="fl s14 bk pdl10 pdt10"></p>
    </div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="runCopyCntGrid"
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
                    <wj-flex-grid-column header="<s:message code="runStore.hqOfficeCd"/>        " binding="hqOfficeCd"      width=" 70" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.hqOfficeNm"/>        " binding="hqOfficeNm"      width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.storeCd"/>           " binding="storeCd"         width=" 80" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.storeNm"/>           " binding="storeNm"         width="150" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.runFg"/>             " binding="runFg"           width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.runDist"/>           " binding="dist"            width="150" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.runAgency"/>         " binding="agencyNm"        width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="runStore.progFg"/>            " binding="progFg"          width=" 90" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.posCnt"/>            " binding="posCnt"          width=" 60" is-read-only="true" align="center" aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pSaleCnt"/>          " binding="pSaleCnt"        width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pRtnSaleCnt"/>       " binding="pRtnSaleCnt"     width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pRealSaleCnt"/>      " binding="pRealSaleCnt"    width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pCashCnt"/>          " binding="pCashCnt"        width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pCashApprCnt"/>      " binding="pCashApprCnt"    width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.pCardCnt"/>          " binding="pCardCnt"        width="120" is-read-only="true" align="right"  aggregate="Sum"> </wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="runStore.instInsDt"/>         " binding="instInsDt"       width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.regDate"/>           " binding="regDate"         width="100" is-read-only="true" align="center" visible="false"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.sysOpenDate"/>       " binding="sysOpenDate"     width="100" is-read-only="true" align="center"> </wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="runStore.maxSaleDate"/>       " binding="pMaxSaleDate"    width="100" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.lastSaleDate"/>      " binding="lastSaleDate"    width="100" is-read-only="true" align="center" visible="false"> </wj-flex-grid-column>

                    <wj-flex-grid-column header="<s:message code="runStore.sysStatNm"/>         " binding="sysStatNm"       width=" 50" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.bizNo"/>             " binding="bizNo"           width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.runVendorTermnlNo"/> " binding="vendorTermnlNo"  width="100" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.vanNm"/>             " binding="vanNm"           width=" 80" is-read-only="true" align="left"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.posVerNo"/>          " binding="posVerNo"        width="120" is-read-only="true" align="left"  > </wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

</div>

<script type="text/javascript">
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/runStore/runCopyCnt.js?ver=20240419.01" charset="utf-8"></script>

