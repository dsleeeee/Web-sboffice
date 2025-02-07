<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<wj-popup control="zeusDataMappingStoreLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:700px;" fade-in="false" fade-out="false">

    <div ng-controller="zeusDataMappingStoreCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="migDataMapping.zeus.selectStore"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="w50 fl">

            <%-- body --%>
            <div class="wj-dialog-body">
                <div class="tblBr" style="border-top: 1px solid #ccc;">
                    <table class="tblType01">
                        <colgroup>
                            <col class="w25"/>
                            <col class="w25"/>
                            <col class="w30"/>
                            <col class="w20"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th colspan="4">
                                <s:message code="migDataMapping.zeus.selectStore"/>
                            </th>
                        </tr>
                        <tr>
                            <%-- 검색조건1 --%>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchZeusFg"
                                            ng-model="zeusFg"
                                            items-source="_getComboData('zeusFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchZeusFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 검색조건2 --%>
                            <td>
                                <div class="sb-select">
                                    <wj-combo-box
                                            id="srchHqStoreFg"
                                            ng-model="hqStoreFg"
                                            items-source="_getComboData('hqStoreFgCombo')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="srchHqStoreFgCombo">
                                    </wj-combo-box>
                                </div>
                            </td>
                            <%-- 검색조건3 --%>
                            <td>
                                <input type="text" class="sb-input w100" id="hqStoreCd" ng-model="hqStoreCd"/>
                            </td>
                            <td>
                                <button class="btn_blue fr" id="btnSearchStore" ng-click="_pageView('zeusDataMappingStoreCtrl', 1)">
                                    <s:message code="cmm.search" />
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="mt10 oh">
                    <%-- 저장 --%>
                    <button class="btn_blue ml5 fr" id="btnSaveMapping" ng-click="regStoreMapping()">
                        <s:message code="cmm.save" />
                    </button>
                </div>

                <%-- 그리드 --%>
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns.="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter"
                                id="noRegStoreGrid">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>
            <%-- //body --%>
        </div>
    </div>

    <div ng-controller="zeusDataMappingSelectStoreCtrl" class="w50 fr">
        <div class="wj-dialog-body">
            <div class="tblBr" style="border-top: 1px solid #ccc;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w25"/>
                        <col class="w25"/>
                        <col class="w30"/>
                        <col class="w20"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th colspan="4">
                            <s:message code="migDataMapping.zeus.chkStoreInfo"/>
                        </th>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10">
                <h3 class="h3_tbl"style="min-width: 0px; border-top: 1px solid #ccc;"><s:message code="migDataMapping.zeus.storeInfo"/></h3>
                <div class="wj-gridWrap" style="height:220px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="zeusRegStoreGrid">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMapping.remark"/>" binding="remark" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div ng-controller="lynkDataMappingSelectStoreCtrl" class="w50 fr">
        <div class="wj-dialog-body" style="padding-top: 0">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <h3 class="h3_tbl" style="min-width: 0px; border-top: 1px solid #ccc;">
                    <s:message code="migDataMapping.zeus.lynkStoreInfo"/></h3>
                <div class="wj-gridWrap" style="height:220px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            id="lynkRegStoreGrid">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeCd"/>" binding="storeCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMapping.remark"/>" binding="remark" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <div class="tr mt20">
                <button id="funcSave" class="btn_blue" ng-click="storeMappingReg()">
                    <s:message code="migDataMapping.zeus.storeMappingReg" />
                </button>
            </div>
        </div>
    </div>

</wj-popup>

<script>

</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/zeusDataMappingStore.js?ver=20250204.01" charset="utf-8"></script>