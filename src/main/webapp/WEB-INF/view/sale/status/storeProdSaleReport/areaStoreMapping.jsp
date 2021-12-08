<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />

<div id="areaStoreMappingView" class="subCon" style="display: none;">
    <div ng-controller="areaStoreMappingCtrl">

        <%-- 조회조건 --%>
        <div class="searchBar">
            <a href="#" class="open fl"><s:message code="storeProdSaleReportTab.areaStoreMapping"/></a>
            <%-- 조회 --%>
            <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                <button class="btn_blue fr" ng-click="_broadcast('areaStoreMappingCtrl',1)">
                    <s:message code="cmm.search" />
                </button>
            </div>
        </div>

        <%-- left --%>
        <div class="wj-TblWrap mt20 mb20 w30 fl">
            <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
                <div class="updownSet oh mb10">
                    <span class="fl bk lh30">
                        <s:message code="areaStoreMapping.area"/>
                    </span>
                </div>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <div class="row">
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="areaStoreMapping.branchCd"/>" binding="branchCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="areaStoreMapping.branchNm"/>" binding="branchNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="areaStoreMapping.areaCd"/>" binding="areaCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="areaStoreMapping.areaNm"/>" binding="areaNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--left--%>
    </div>

    <%--center--%>
    <div class="wj-TblWrap mt20 mb20 w30 fl" ng-controller="areaStoreMappingDetailCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:450px; overflow-y: hidden;">
            <div class="updownSet oh mb10">
                <span class="fl bk lh30">
                    <s:message code="areaStoreMapping.store"/>
                    <label id="lblAreaCd"></label>
                    <label id="lblAreaNm"></label>
                </span>
                <button class="btn_skyblue" id="btnAreaStoreMappingDetailDel" ng-click="del()"><s:message code='cmm.del' /></button>
            </div>
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
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--삭제시 필요--%>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.branchCd"/>" binding="branchCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.areaCd"/>" binding="areaCd" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--center--%>

    <%--right--%>
    <div class="wj-TblWrap mt20 mb20 w40 fr" ng-controller="areaStoreMappingStoreCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height:450px; overflow-y: hidden;">
            <table class="tblType01">
                <colgroup>
                    <col class="w25" />
                    <col class="w25" />
                    <col class="w25" />
                    <col class="w25" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="areaStoreMapping.storeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="storeCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="areaStoreMapping.storeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="storeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 사업자번호 --%>
                    <th>
                        <s:message code="areaStoreMapping.bizNo" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" ng-model="bizNo" />
                    </td>
                    <%-- 매장상태구분 --%>
                    <th>
                        <s:message code="areaStoreMapping.sysStatFg" />
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
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>
                        <%-- 조회 --%>
                        <button class="btn_skyblue fr" id="btnSearch" ng-click="search()"><s:message code="cmm.search" /></button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="updownSet oh mb10 mt10">
                <button class="btn_skyblue" id="btnAreaStoreMappingStoreSave" ng-click="save()"><s:message code='cmm.save' /></button>
            </div>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:290px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.bizNo"/>" binding="bizNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="areaStoreMapping.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
    <%--right--%>

</div>

<script type="text/javascript">
    <%-- 매장상태구분 --%>
    var sysStatFgComboData = ${ccu.getCommCode("005")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/storeProdSaleReport/areaStoreMapping.js?ver=20211207.02" charset="utf-8"></script>