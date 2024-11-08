<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="pAgencyCd" value="${sessionScope.sessionInfo.pAgencyCd}"/>

<wj-popup control="nxMigDataMappingInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:600px;" fade-in="false" fade-out="false">

    <div ng-controller="nxMigDataMappingInfoCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="migDataMappingInfo.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 조회조건 --%>
            <div class="searchBar flddUnfld">
                <a href="#" class="open fl"></a>
                <%-- 조회 --%>
                <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                    <button class="btn_blue fr" ng-click="_pageView('nxMigDataMappingInfoCtrl', 1)">
                        <s:message code="cmm.search" />
                    </button>
                </div>
            </div>
            <table class="searchTbl">
                <colgroup>
                    <col class="w15"/>
                    <col class="w35"/>
                    <col class="w15"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 본사코드 --%>
                    <th>
                        <s:message code="migDataMappingInfo.hqOfficeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNxHqOfficeCd" ng-model="srchNxHqOfficeCd" />
                    </td>
                    <%-- 본사명 --%>
                    <th>
                        <s:message code="migDataMappingInfo.hqOfficeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNxHqOfficeNm" ng-model="srchNxHqOfficeNm" />
                    </td>
                </tr>
                <tr>
                    <%-- 매장코드 --%>
                    <th>
                        <s:message code="migDataMappingInfo.storeCd" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNxStoreCd" ng-model="srchNxStoreCd" />
                    </td>
                    <%-- 매장명 --%>
                    <th>
                        <s:message code="migDataMappingInfo.storeNm" />
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchNxStoreNm" ng-model="srchNxStoreNm" />
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:300px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqOfficeNm"/>" binding="hqOfficeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.bizNo"/>" binding="bizNo" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="migDataMappingInfo.storeSysStatFg"/>" binding="sysStatFg" width="80" data-map="sysStatFgDataMap" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
                <%-- 페이지 리스트 --%>
                <div class="pageNum mt20">
                    <%-- id --%>
                    <ul id="nxMigDataMappingInfoCtrlPager" data-size="10">
                    </ul>
                </div>
                <%--//페이지 리스트--%>
                <%-- 저장 버튼 --%>
                <div class="tc mt20">
                    <button id="funcSave" class="btn_blue" ng-click="nxMigDataMappingInfoSave()">
                        <s:message code="migDataMappingInfo.save" />
                    </button>
                </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script>
    var hqList = ${ccu.getHqOfficeList()};
    var orgnFg = "${orgnFg}";
    var orgnCd = "${orgnCd}";
    var pAgencyCd = "${pAgencyCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/migDataMapping/nxMigDataMappingInfo.js?ver=20241105.01" charset="utf-8"></script>