<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>

<div class="subCon" ng-controller="storeInfoBatchChangeCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl">${menuNm}</a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('storeInfoBatchChangeCtrl',1)">
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
            <%-- 본사코드 --%>
            <th>
                <s:message code="storeInfoBatchChange.hqOfficeCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
            </td>
            <%-- 본사명 --%>
            <th>
                <s:message code="storeInfoBatchChange.hqOfficeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th>
                <s:message code="storeInfoBatchChange.storeCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
            </td>
            <%-- 매장명 --%>
            <th>
                <s:message code="storeInfoBatchChange.storeNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
            </td>
        </tr>
        <tr>
            <%-- 대리점코드 --%>
            <th>
                <s:message code="storeInfoBatchChange.agencyCd"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" />
            </td>
            <%-- 대리점명 --%>
            <th>
                <s:message code="storeInfoBatchChange.agencyNm"/>
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" />
            </td>
        </tr>
        <tr>
            <%-- 매장상태구분 --%>
            <th>
                <s:message code="storeInfoBatchChange.sysStatFg" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchSysStatFgCombo"
                            ng-model="sysStatFg"
                            items-source="_getComboData('sysStatFgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)"
                            control="srchSysStatFgCombo">
                    </wj-combo-box>
                </div>
            </td>
            <td></td>
            <td></td>
        </tr>
        </tbody>
    </table>

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
            <%-- 매장명 --%>
            <th>
                <s:message code="storeInfoBatchChange.storeNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchStoreNmChg" ng-model="storeNmChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('storeNmChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 대리점 --%>
            <th>
                <s:message code="storeInfoBatchChange.agency" />
            </th>
            <td>
                <input type="text" name="agencyNmChg" id="srchAgencyNmChg" ng-model="agencyNmChg" class="sb-input w100" readonly="readonly" ng-click="searchAgency()">
                <input type="hidden" name="agencyCdChg" id="srchAgencyCdChg" ng-model="agencyCdChg">
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('agencyCdChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        <tr>
            <%-- 상호명 --%>
            <th>
                <s:message code="storeInfoBatchChange.bizStoreNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchBizStoreNmChg" ng-model="bizStoreNmChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('bizStoreNmChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 대표자명 --%>
            <th>
                <s:message code="storeInfoBatchChange.ownerNm" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchOwnerNmChg" ng-model="ownerNmChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('ownerNmChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        <tr>
            <%-- 날씨표시지역 --%>
            <th>
                <s:message code="storeInfoBatchChange.areaCd" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchAreaCdChg"
                            ng-model="areaCdChg"
                            items-source="_getComboData('areaCdChgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('areaCdChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 직영구분 --%>
            <th>
                <s:message code="storeInfoBatchChange.directManageYn" />
            </th>
            <td>
                <div class="sb-select">
                    <wj-combo-box
                            id="srchDirectManageYnChg"
                            ng-model="directManageYnChg"
                            items-source="_getComboData('directManageYnChgCombo')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                </div>
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('directManageYnChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        <tr>
            <%-- 전화번호 --%>
            <th>
                <s:message code="storeInfoBatchChange.telNo" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchTelNoChg" ng-model="telNoChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('telNoChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 팩스번호 --%>
            <th>
                <s:message code="storeInfoBatchChange.faxNo" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchFaxNoChg" ng-model="faxNoChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('faxNoChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        <tr>
            <%-- 시스템비고 --%>
            <th>
                <s:message code="storeInfoBatchChange.sysRemark" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchSysRemarkChg" ng-model="sysRemarkChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('sysRemarkChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 본사비고 --%>
            <th>
                <s:message code="storeInfoBatchChange.hdRemark" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchHdRemarkChg" ng-model="hdRemarkChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('hdRemarkChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        <tr>
            <%-- 특이사항 --%>
            <th>
                <s:message code="storeInfoBatchChange.remark" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchRemarkChg" ng-model="remarkChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('remarkChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
            <%-- 매핑매장코드 --%>
            <th>
                <s:message code="storeInfoBatchChange.mapStoreCd" />
            </th>
            <td>
                <input type="text" class="sb-input w100" id="srchMapStoreCdChg" ng-model="mapStoreCdChg" />
            </td>
            <%-- 일괄적용 --%>
            <td>
                <a href="#" class="btn_grayS ml10" ng-click="batchChange('mapStoreCdChg')"><s:message code="storeInfoBatchChange.batchChange" /></a>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt20 oh">
        <%-- 저장 --%>
        <button class="btn_skyblue ml5 fr" id="btnSave" ng-click="save()">
            <s:message code="cmm.save" />
        </button>
        <%--시스템패스워드--%>
        <div class="sb-select dkbr ml5 fr">
            <s:message code="storeInfoBatchChange.systemPw" />
            <input type="password" class="sb-input w200px" id="srchSystemPw" ng-model="systemPw" />
        </div>
    </div>

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
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.hqOfficeNm"/>" binding="hqOfficeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.storeNm"/>" binding="storeNm" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.agency"/>" binding="agencyCd" data-map="agencyCdDataMap" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.bizStoreNm"/>" binding="bizStoreNm" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.ownerNm"/>" binding="ownerNm" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.areaCd"/>" binding="areaCd" data-map="areaCdDataMap" width="90" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.directManageYn"/>" binding="directManageYn" data-map="directManageYnDataMap" width="80" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.telNo"/>" binding="telNo" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.faxNo"/>" binding="faxNo" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.vanCd"/>" binding="vanCd" data-map="vanCdDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.sysRemark"/>" binding="sysRemark" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.hdRemark"/>" binding="hdRemark" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.remark"/>" binding="remark" width="100" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeInfoBatchChange.mapStoreCd"/>" binding="mapStoreCd" width="100" align="center"></wj-flex-grid-column>
            </wj-flex-grid>
        </div>
    </div>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var pAgencyCd = "${pAgencyCd}";
    var userId = "${userId}";

    <%-- 매장상태구분 --%>
    var sysStatFgComboData = ${ccu.getCommCode("005")};
    <%-- 용도구분 --%>
    var clsFgComboData = ${ccu.getCommCode("001")};
    <%-- 날씨표시지역 --%>
    var areaCdComboData = ${ccu.getCommCode("061")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeInfoBatchChange/storeInfoBatchChange.js?ver=20220808.02" charset="utf-8"></script>

<%-- 대리점 조회 --%>
<c:import url="/WEB-INF/view/application/layer/searchAgency.jsp">
</c:import>