<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<div id="statusAgencyView" class="subCon" style="display: none;">

    <%-- 조회조건 --%>
    <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="statusAgency.info" /></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_pageView('statusAgencyCtrl',1)">
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
            <%--<tr <c:if test="${orgnFg == 'AGENCY' and pAgencyCd != '00000'}">style="display: none;"</c:if> >--%>
            <tr <c:if test="${orgnFg == 'AGENCY'}">style="display: none;"</c:if> >
                <%-- 업체코드 --%>
                <th>
                    <s:message code="statusAgency.srchAgencyCd" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyCd" ng-model="agencyCd" />
                </td>
                <%-- 업체명 --%>
                <th>
                    <s:message code="statusAgency.srchAgencyNm" />
                </th>
                <td>
                    <input type="text" class="sb-input w100" id="srchAgencyNm" ng-model="agencyNm" />
                </td>
            </tr>
            <tr>
                <%-- 업체구분 --%>
                <th>
                    <s:message code="statusAgency.srchAgencyFg" />
                </th>
                <td>
                    <div class="sb-select">
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
                <%-- 용도 --%>
                <th>
                    <s:message code="statusAgency.srchClsFg" />
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
            </tr>
            <tr>
                <%-- 상태 --%>
                <th>
                    <s:message code="statusAgency.srchSysStatFg" />
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
                <th></th>
                <td></td>
            </tr>
        </tbody>
    </table>

        <%--left--%>
            <div class="wj-TblWrap mt20 mb20 w40 fl" ng-controller="statusAgencyCtrl">
                <div class="wj-TblWrapBr mr10 pd20" style="height:470px;">
                    <div class="w100 mt10 mb20">
                        <div class="wj-gridWrap" style="height:370px; overflow-x: hidden; overflow-y: hidden;">
                            <wj-flex-grid
                                    control="flex"
                                    autoGenerateColumns="false"
                                    selection-mode="Row"
                                    initialized="initGrid(s,e)"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="statusAgency.agencyCd"/>" binding="agencyCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.agencyNm"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.storeCnt"/>" binding="storeCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.totPosCnt"/>" binding="totPosCnt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                                <%--상세 조회시 필요--%>
                                <wj-flex-grid-column header="<s:message code="statusAgency.clsFg"/>" binding="clsFg" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.sysStatFg"/>" binding="sysStatFg" width="115" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        <%--left--%>

        <%--right--%>
            <div class="wj-TblWrap mt20 mb20 w60 fr" ng-controller="statusAgencyDetailCtrl">
                <div class="wj-TblWrapBr ml10 pd20" style="height:470px; overflow-y: hidden;">
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
                                <wj-flex-grid-column header="<s:message code="statusAgency.hqOfficeCd"/>" binding="hqOfficeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.hqOfficeNm"/>" binding="hqOfficeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.storeCd"/>" binding="storeCd" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.storeNm"/>" binding="storeNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.clsFg"/>" binding="clsFg" data-map="clsFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" idth="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.minSaleDate"/>" binding="minSaleDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.maxSaleDate"/>" binding="maxSaleDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="statusAgency.posCnt"/>" binding="posCnt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                    </div>
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

<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusAgency.js?ver=2019052802.16" charset="utf-8"></script>