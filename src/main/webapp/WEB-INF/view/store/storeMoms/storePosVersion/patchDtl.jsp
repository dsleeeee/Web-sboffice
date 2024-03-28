<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/storeMoms/storePosVersion/posDtl/"/>

<wj-popup control="patchDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:870px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="patchDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storePosVersion.patchDtl"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
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
                        <wj-flex-grid-column header="<s:message code="storePosVersion.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.hqOfficeNm"/>" binding="hqOfficeNm" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.storeNm"/>" binding="storeNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.posNo"/>" binding="posNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchDate"/>" binding="patchDate" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchSeq"/>" binding="patchSeq" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchDt"/>" binding="patchDt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchCd"/>" binding="patchCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchBfVer"/>" binding="posVerNo" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchAfVer"/>" binding="patchVerNo" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.scriptNm"/>" binding="scriptNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.patchErrMsg"/>" binding="patchErrMsg" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.regDt"/>" binding="regDt" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.regId"/>" binding="regId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.modDt"/>" binding="modDt" width="110" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.modId"/>" binding="modId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.posDef1"/>" binding="posDef1" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.posDef2"/>" binding="posDef2" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.posDef3"/>" binding="posDef3" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storePosVersion.fileDesc"/>" binding="fileDesc" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/storeMoms/storePosVersion/patchDtl.js?ver=20240312.01" charset="utf-8"></script>