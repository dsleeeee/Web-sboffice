<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="statusPosInstallView" class="subCon" style="display: none;" ng-controller="statusPosInstallCtrl">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="storeStatus.posInstall"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('statusPosInstallCtrl',1)">
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
                <%-- 조회일자 --%>
                <th>
                    <s:message code="statusPosInstall.srchDate" />
                </th>
                <td colspan="3">
                    <div class="sb-select">
                        <span class="txtIn w110px">
                            <wj-input-date
                                id="srchTimeStartDate"
                                value="startDate"
                                ng-model="startDate"
                                control="startDateCombo"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="rg">~</span>
                        <span class="txtIn w110px">
                            <wj-input-date
                                id="srchTimeEndDate"
                                value="endDate"
                                ng-model="endDate"
                                control="endDateCombo"
                                min="2000-01-01"
                                max="2099-12-31"
                                initialized="_initDateBox(s)">
                            </wj-input-date>
                        </span>
                        <span class="chk ml10">
                            <input type="checkbox" id="chkDt" ng-model="isChecked" ng-change="isChkDt()" />
                            <label for="chkDt">
                                <s:message code="cmm.all.day" />
                            </label>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 본사코드 --%>
                <th>
                    <s:message code="statusPosInstall.srchHqOfficeCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeCd" ng-model="hqOfficeCd" />
                </td>
                <%-- 본사명 --%>
                <th>
                    <s:message code="statusPosInstall.srchHqOfficeNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchHqOfficeNm" ng-model="hqOfficeNm" />
                </td>
            </tr>
            <tr>
                <%-- 매장코드 --%>
                <th>
                    <s:message code="statusPosInstall.srchStoreCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreCd" ng-model="storeCd" />
                </td>
                <%-- 매장명 --%>
                <th>
                    <s:message code="statusPosInstall.srchStoreNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchStoreNm" ng-model="storeNm" />
                </td>
            </tr>
            <tr>
                <%-- 현재상태 --%>
                <th>
                    <s:message code="statusPosInstall.srchInstFg" />
                </th>
                <td>
                    <div class="sb-select">
                        <wj-combo-box
                            id="srchInstFg"
                            ng-model="instFg"
                            items-source="_getComboData('instFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                        </wj-combo-box>
                    </div>
                </td>
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

    <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScalePosInstall"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
    </div>

    <%-- 그리드 --%>
    <div class="w100 mt10 mb20">
        <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
            <div class="row">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.hqOfficeCd"/>" binding="hqOfficeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.hqOfficeNm"/>" binding="hqOfficeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.storeCd"/>" binding="storeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.posCnt"/>" binding="posCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.instInsDt"/>" binding="instInsDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.instFg"/>" binding="instFg" data-map="instFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.agencyNm"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.instAgencyNm"/>" binding="instAgencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="statusPosInstall.instInsId"/>" binding="instInsId" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="statusPosInstallCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusPosInstall.js?ver=2019052801.20" charset="utf-8"></script>