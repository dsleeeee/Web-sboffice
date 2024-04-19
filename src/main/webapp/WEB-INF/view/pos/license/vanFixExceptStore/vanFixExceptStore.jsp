<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<div id="vanFixExcept" class="subCon" ng-controller="vanFixExceptCtrl">

    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"> <s:message code="vanFixExceptStore.vanFixExceptStore" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('vanFixExceptCtrl',1)"  id="nxBtnSearch">
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
        <%--<tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >--%>
        <tr>
            <%-- 관리밴사 --%>
            <th>
                <s:message code="vanFixExceptStore.manageVan" />
            </th>
            <td>
                <input type="text" id="manageVanNm" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchManageVan()" style="float: left;">
                <input type="hidden" id="ssl_srchManageVanCd" ng-hide="true">
                <button type="button" class="btn_skyblue fl mr5" id="btnCancelManageVanCdRun" style="margin-left: 5px;" ng-click="delManageVanCd()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${orgnFg == 'MASTER'}">
                <%-- 관리업체 --%>
                <th>
                    <s:message code="vanFixExceptStore.agency" />
                </th>
                <td>
                    <input type="text" id="agencyNm" value=<s:message code="cmm.all"/> class="sb-input w75" ng-readonly="true" ng-click="searchAgency()" style="float: left;">
                    <input type="hidden" id="ssl_srchAgencyCd" ng-hide="true">
                    <button type="button" class="btn_skyblue fl mr5" id="btnCancelAgencyCdRun" style="margin-left: 5px;" ng-click="delAgencyCd()"><s:message code="cmm.selectCancel"/></button>
                </td>
            </c:if>
        </tr>
        <tr>
            <%-- 본사코드 --%>
            <th>
                <s:message code="vanFixExceptStore.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="vanFixExceptStore.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="vanFixExceptStore.storeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" onkeyup="fnNxBtnSearch();"/>
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="vanFixExceptStore.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" onkeyup="fnNxBtnSearch();"/>
            </td>
        </tr>
        <tr>
            <th>
                <s:message code="vanFixExceptStore.includeFg" />
            </th>
            <td>
            <div class="sb-select">
                <wj-combo-box
                        id="srchIncludeFg"
                        ng-model="includeFg"
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


    <div id="gridRepresent" class="w50 fl mt10" style="width: 50%" ng-controller="vanFixExceptCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr mr10 pd10" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='vanFixExceptStore.fixExceptStore' /></span>
                <button class="btn_skyblue ml5 fr" id="btnDel" ng-click="delete()">
                    <s:message code="cmm.delete" />
                </button>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%-- 개발시 높이 조절해서 사용--%>
            <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <div class="row" >
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            frozen-columns="1"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.vanCd"/>" binding="vanCd" width="100" align="center" is-read-only="true" data-map="vanCdDataMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="vanFixExceptStore.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <div id="gridDetail" class="w50 fr mt10 mb10" style="width: 50%" ng-controller="vanStoreCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-TblWrapBr pd10" style="height: 480px;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30"><s:message code='vanFixExceptStore.store' /></span>
                <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
                    <s:message code="cmm.save" />
                </button>
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="wj-gridWrap" style="height:370px; overflow-y: hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        frozen-columns="1"
                        sorted-column="toggleFreeze(false)"
                        ime-enabled="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.hqOfficeCd"/>" binding="hqOfficeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.hqOfficeNm"/>" binding="hqOfficeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.agencyNm"/>" binding="agencyNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.vanCd"/>" binding="vanCd" width="100" align="center" is-read-only="true" data-map="vanCdDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="vanFixExceptStore.remark"/>" binding="remark" width="100" align="left"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript">
    var vanComboList = ${vanComboList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/license/vanFixExceptStore/vanFixExceptStore.js?ver=20240409.01" charset="utf-8"></script>

<%-- 관리밴사 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchVan.jsp">
</c:import>

<%-- 관리업체 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>
