<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>

<wj-popup control="wjPatchStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:870px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="patchStoreDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeVerPatch.storeInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:350px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.posVerNo"/>" binding="posVerNo" width="120" is-read-only="true" align="center" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.lastLoginDt"/>" binding="lastLoginDt" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeVerPatch.lastSaleDate"/>" binding="lastSaleDate" width="120" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storeVerPatch/patchStoreDtl.js?ver=20250526.01" charset="utf-8"></script>