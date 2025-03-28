<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="statusVanView" class="subCon" style="display: none;padding: 10px 20px 40px;">

    <div ng-controller="statusVanCtrl">
        <%-- 조회조건 --%>
        <div class="searchBar flddUnfld">
            <a href="#" class="open fl"><s:message code="storeStatus.van" /></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('statusVanCtrl', 1)" id="nxBtnSearch3">
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
                    <%-- VAN사 코드 --%>
                    <th>
                        <s:message code="statusVan.srchVanCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchVanCd" ng-model="vanCd" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                    <%-- VAN사명 --%>
                    <th>
                        <s:message code="statusVan.srchVanNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchVanNm" ng-model="vanNm" onkeyup="fnNxBtnSearch('3');"/>
                    </td>
                </tr>
                <tr>
                    <%-- 용도 --%>
                    <th>
                        <s:message code="statusVan.srchClsFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchClsFg"
                                ng-model="clsFg"
                                items-source="_getComboData('clsFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                    <%-- 상태 --%>
                    <th>
                        <s:message code="statusVan.srchSysStatFg" />
                    </th>
                    <td>
                        <div class="sb-select">
                            <wj-combo-box
                                id="srchSysStatFg"
                                ng-model="sysStatFg"
                                items-source="_getComboData('sysStatFg')"
                                display-member-path="name"
                                selected-value-path="value"
                                is-editable="false"
                                initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </tr>
                <c:if test="${orgnFg == 'AGENCY'}">
                    <%-- 업체구분 --%>
                    <th>
                        <s:message code="statusAgency.srchAgencyFg" />
                    </th>
                    <td>
                        <div class="sb-select" style="width:300px;">
                            <wj-combo-box
                                    id="srchAgencyFg"
                                    ng-model="agencyFg"
                                    items-source="_getComboData('agencyFg')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)">
                            </wj-combo-box>
                        </div>
                    </td>
                </c:if>
                <c:if test="${orgnFg != 'AGENCY'}">
                    <td></td>
                    <td></td>
                </c:if>
                </tr>
            </tbody>
        </table>

        <%--left--%>
        <div class="wj-TblWrap mt10 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:470px;">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="statusVan.vanCd"/>" binding="vanCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="statusVan.vanNm"/>" binding="vanNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="statusVan.storeCnt"/>" binding="storeCnt" width="75" is-read-only="true" align="center"></wj-flex-grid-column>

                            <%--상세 조회시 필요--%>
                            <wj-flex-grid-column header="<s:message code="statusVan.clsFg"/>" binding="clsFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="statusVan.sysStatFg"/>" binding="sysStatFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--right--%>
    <div class="wj-TblWrap mt10 mb20 w70 fr" ng-controller="statusVanDetailCtrl">
        <div class="wj-TblWrapBr pd10" style="height:470px; overflow-y: hidden;">
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="statusVan.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.hqOfficeNm"/>" binding="hqOfficeNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.storeNm"/>" binding="storeNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.minSaleDate"/>" binding="minSaleDate" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.maxSaleDate"/>" binding="maxSaleDate" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.posCnt"/>" binding="posCnt" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.agencyCd"/>" binding="agencyCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="statusVan.agencyNm"/>" binding="agencyNm" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="statusVanDetailCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    <%-- 용도구분 --%>
    var clsFgData = ${ccu.getCommCodeExcpAll("001")};
    <%-- 상태구분 --%>
    var sysStatFgData = ${ccu.getCommCodeExcpAll("005")};

    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusVan.js?ver=20250224.01" charset="utf-8"></script>