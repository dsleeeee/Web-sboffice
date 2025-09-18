<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="subCon" ng-controller="omsLinkSampleCtrl">
    <div class="searchBar">
        <a href="#" class="open fl"><s:message code="omsLinkSample.omsLinkSample"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
            <button class="btn_blue fr" ng-click="_broadcast('omsLinkSampleCtrl')" id="nxBtnSearch">
                <s:message code="cmm.search"/>
            </button>
        </div>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w12"/>
            <col class=""/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 매장등록(001) --%>
            <th><s:message code="omsLinkSample.regStore"/>(001)</th>
            <td>
                <input type="text" class="sb-input" id="regStore_shopName" placeholder="<s:message code="omsLinkSample.shopName"/>[shopName]"/>
                <input type="text" class="sb-input" id="regStore_taxNo" placeholder="<s:message code="omsLinkSample.taxNo"/>[taxNo]"/>
                <input type="text" class="sb-input" id="regStore_ceoName" placeholder="<s:message code="omsLinkSample.ceoName"/>[ceoName]"/>
                <input type="text" class="sb-input" id="regStore_postNo" placeholder="<s:message code="omsLinkSample.postNo"/>[postNo]"/>
                <input type="text" class="sb-input" id="regStore_addrBase" placeholder="<s:message code="omsLinkSample.addrBase"/>[addrBase]"/>
                <input type="text" class="sb-input" id="regStore_addrDetail" placeholder="<s:message code="omsLinkSample.addrDetail"/>[addrDetail]"/>
                <input type="text" class="sb-input" id="regStore_posType" placeholder="<s:message code="omsLinkSample.posType"/>[posType]"/>
                <input type="text" class="sb-input" id="regStore_posShopId" placeholder="<s:message code="omsLinkSample.posShopId"/>[posShopId]"/>
                <button class="btn_skyblue ml5" ng-click="regStore('test')"><s:message code="omsLinkSample.testSearch" /></button>
                <button class="btn_skyblue ml5" ng-click="regStore('real')"><s:message code="omsLinkSample.realSearch" /></button>
            </td>
        </tr>
        <tr>
            <%-- 매장 배대사 등록(002) --%>
            <th><s:message code="omsLinkSample.regStoreAgency"/>(002)</th>
            <td>
                <button class="btn_skyblue ml5" ng-click="regStoreAgency('test')"><s:message code="omsLinkSample.testSearch" /></button>
                <button class="btn_skyblue ml5" ng-click="regStoreAgency('real')"><s:message code="omsLinkSample.realSearch" /></button>
            </td>
        <tr>
            <%-- 배대사 코드 조회(003) --%>
            <th><s:message code="omsLinkSample.searchAgencyCode"/>(003)</th>
            <td>
                <button class="btn_skyblue ml5" ng-click="searchAgencyCode('test')"><s:message code="omsLinkSample.testSearch" /></button>
                <button class="btn_skyblue ml5" ng-click="searchAgencyCode('real')"><s:message code="omsLinkSample.realSearch" /></button>
            </td>
        </tr>
        <tr>
            <%-- 배대사 매장 조회(004) --%>
            <th><s:message code="omsLinkSample.searchAgencyStore"/>(004)</th>
            <td>
                <input type="text" class="sb-input" id="searchAgencyStore_posShopId" placeholder="<s:message code="omsLinkSample.posShopId"/>[posShopId]"/>
                <input type="text" class="sb-input" id="searchAgencyStore_agencyCode" placeholder="<s:message code="omsLinkSample.agencyCode"/>[agencyCode]"/>
                <input type="text" class="sb-input" id="searchAgencyStore_subAgencyCode" placeholder="<s:message code="omsLinkSample.subAgencyCode"/>[subAgencyCode]"/>
                <button class="btn_skyblue ml5" ng-click="searchAgencyStore('test')"><s:message code="omsLinkSample.testSearch"/></button>
                <button class="btn_skyblue ml5" ng-click="searchAgencyStore('real')"><s:message code="omsLinkSample.realSearch"/></button>
            </td>
        </tr>
        <tr>
            <%-- 배대사 매핑 등록(005) --%>
            <th><s:message code="omsLinkSample.regAgencyMapping"/>(005)</th>
            <td>
                <input type="text" class="sb-input" id="regAgencyMapping_posShopId" placeholder="<s:message code="omsLinkSample.posShopId"/>[posShopId]"/>
                <input type="text" class="sb-input" id="regAgencyMapping_agencyCode" placeholder="<s:message code="omsLinkSample.agencyCode"/>[agencyCode]"/>
                <input type="text" class="sb-input" id="regAgencyMapping_subAgencyCode" placeholder="<s:message code="omsLinkSample.subAgencyCode"/>[subAgencyCode]"/>
                <input type="text" class="sb-input" id="regAgencyMapping_storeCode" placeholder="<s:message code="omsLinkSample.storeCode"/>[storeCode]"/>
                <input type="text" class="sb-input" id="regAgencyMapping_isB2BContract" placeholder="<s:message code="omsLinkSample.isB2BContract"/>[isB2BContract]"/>
                <button class="btn_skyblue ml5" ng-click="regAgencyMapping('test')"><s:message code="omsLinkSample.testSearch"/></button>
                <button class="btn_skyblue ml5" ng-click="regAgencyMapping('real')"><s:message code="omsLinkSample.realSearch"/></button>
            </td>
        </tr>
        <tr>
            <%-- 배대사 매핑 삭제(006) --%>
            <th><s:message code="omsLinkSample.delAgencyMapping"/>(006)</th>
            <td>
                <input type="text" class="sb-input" id="delAgencyMapping_posShopId" placeholder="<s:message code="omsLinkSample.posShopId"/>[posShopId]"/>
                <input type="text" class="sb-input" id="delAgencyMapping_mappingSequence" placeholder="<s:message code="omsLinkSample.mappingSequence"/>[mappingSequence]"/>
                <button class="btn_skyblue ml5" ng-click="delAgencyMapping('test')"><s:message code="omsLinkSample.testSearch"/></button>
                <button class="btn_skyblue ml5" ng-click="delAgencyMapping('real')"><s:message code="omsLinkSample.realSearch"/></button>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="omsLinkSample.seq"/>" binding="seq" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.linkType"/>" binding="linkType" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.url"/>" binding="url" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.method"/>" binding="requestMethod" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.request"/>" binding="request" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.response"/>" binding="response" width="250" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.requestDt"/>" binding="requestDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.responseDt"/>" binding="responseDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.statusCode"/>" binding="statusCode" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.regDt"/>" binding="regDt" width="130" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="omsLinkSample.regId"/>" binding="regId" width="100" align="left" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
    </div>

</div>

<script type="text/javascript">

</script>

<script type="text/javascript" src="/resource/solbipos/js/sys/link/omsLinkSample/omsLinkSample.js?ver=20250917.01" charset="utf-8"></script>