<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="instlDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:810px;height:490px;" fade-in="false" fade-out="false">
    <div ng-controller="instlDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="instl.instlInfo" />
            <label id="lblStoreCdInstlDtl"></label>
            <label id="lblStoreNmInstlDtl"></label>
            <label id="lblPosNoInstlDtl"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:380px; overflow-y: hidden; overflow-x: hidden;">
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
                        <wj-flex-grid-column header="<s:message code="instl.instFg"/>" binding="instFg" data-map="instFgDataMap" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instReqDt"/>" binding="instReqDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instInsDt"/>" binding="instInsDt" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.operation.agencyNm"/>" binding="agencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instlAgencyNm"/>" binding="instlAgencyNm" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="instl.instInsId"/>" binding="instInsId" width="115" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/license/instlManage/instlDtl.js?ver=2019052801.05" charset="utf-8"></script>