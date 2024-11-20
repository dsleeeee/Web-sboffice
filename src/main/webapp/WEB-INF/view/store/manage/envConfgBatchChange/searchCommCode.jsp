<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchCommCodeLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;height:600px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="searchCommCodeCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="envConfgBatchChange.commCode.commCode"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>
        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="wj-TblWrap" style="height:500px; overflow-y:hidden;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.nmcodeGrpCd"/>" binding="nmcodeGrpCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.nmcodeGrpNm"/>" binding="nmcodeGrpNm" width="130" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.commCode"/>" binding="nmcodeCd" width="65" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.commCodeNm"/>" binding="nmcodeNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.useTargetFg"/>" binding="useTargetFg" width="100" is-read-only="true" align="center" data-map="useTargetFgDataMap"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.hqOfficeNm"/>" binding="hqOfficeNm" width="120" is-read-only="true" align="left"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.commCode.parentCd"/>" binding="parentCd" width="120" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
            <%--- //그리드 --%>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/searchCommCode.js?ver=20241120.01" charset="utf-8"></script>