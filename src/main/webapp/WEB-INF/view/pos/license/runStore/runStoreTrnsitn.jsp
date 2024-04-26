<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="runStoreTrnsitnView" class="subCon" style="display: none;padding: 10px 20px 40px;" ng-controller="runStoreTrnsitnCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="runStore.runStoreTrnsitn" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('runStoreTrnsitnCtrl',1)"  id="nxBtnSearch3">
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
            <%-- 시스템등록 일자 --%>
            <th>
                <s:message code="runStore.runSchMonth" />
            </th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="startMonth3" ng-model="srchStartMonth3" class="w110px"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"> <input id="endMonth3" ng-model="endMonth3" class="w110px" /></span>
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
                <input type="text" id="manageVanNmTrnsitn" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchManageVanTrnsitn()" style="float: left;">
                <input type="hidden" id="ssl_srchManageVanCdTrnsitn" ng-hide="true">
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelManageVanCdTrnsitn" style="margin-left: 5px;" ng-click="delManageVanCdTrnsitn()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${orgnFg == 'MASTER'}">
                <%-- 관리업체 --%>
                <th>
                    <s:message code="runStore.agency" />
                </th>
                <td>
                    <input type="text" id="agencyNmTrnsitn" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchAgencyTrnsitn()" style="float: left;">
                    <input type="hidden" id="ssl_srchAgencyCdTrnsitn" ng-hide="true">
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelAgencyCdTrnsitn" style="margin-left: 5px;" ng-click="delAgencyCdTrnsitn()"><s:message code="cmm.selectCancel"/></button>
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
                <input type="text" class="sb-input w100" id="trnHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="runStore.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="trnHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch('3');"/>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="runStore.storeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="trnStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch('3');"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="runStore.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="trnStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch('3');"/>
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
    </div>


    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:320px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                        id="runStoreTrnsitnGrid"
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="runStore.saleMonth"/>     " binding="saleMonth"     width="60" is-read-only="true" align="center"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.runStoreCnt"/>   " binding="runStoreCnt"   width="80" is-read-only="true" align="right"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.rusPosCnt"/>     " binding="runPosCnt"     width="80" is-read-only="true" align="right"> </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.openStorecnt"/>  " binding="openStoreCnt"  width="80" is-read-only="true" align="right"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.openPosCnt"/>    " binding="openPosCnt"    width="80" is-read-only="true" align="right"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.closeStoreCnt"/> " binding="closeStoreCnt" width="80" is-read-only="true" align="right"  > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.closePosCnt"/>   " binding="closePosCnt"   width="80" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.incStoreCnt"/>   " binding="incStoreCnt"   width="140" is-read-only="true" align="right" > </wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="runStore.incPosCnt"/>     " binding="incPosCnt"     width="140" is-read-only="true" align="right" > </wj-flex-grid-column>


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

<script type="text/javascript" src="/resource/solbipos/js/pos/license/runStore/runStoreTrnsitn.js?ver=20240415.01" charset="utf-8"></script>

