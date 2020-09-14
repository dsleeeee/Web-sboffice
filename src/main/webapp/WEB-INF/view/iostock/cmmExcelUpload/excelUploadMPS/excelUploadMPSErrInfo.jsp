<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPSErrInfo/"/>

<wj-popup id="wjExcelUploadMPSErrInfoLayer" control="wjExcelUploadMPSErrInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div id="excelUploadMPSErrInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="excelUploadMPSErrInfoCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="uploadMPSErrInfo.title"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="height: 450px;">
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 390px;">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.prodStatus"/>" binding="prodStatus" width="90" align="center" data-map="prodStatusMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.vendrProdStatus"/>" binding="vendrProdStatus" width="90" align="center" data-map="vendrProdStatusMap" visible="{{vendrProdStatusVisibleFg}}"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.stockProdYn"/>" binding="stockProdYn" width="90" align="center" data-map="stockProdYnMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.poProdFg"/>" binding="poProdFg" width="90" align="center" data-map="poProdFgMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.useYn"/>" binding="useYn" width="60" align="center" data-map="useYnMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.prodBarcdCd"/>" binding="prodBarcdCd" width="150" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.unitQty"/>" binding="unitQty" width="70" align="right" data-type="Number" format="n0" visible="{{unitQtyVisibleFg}}"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.etcQty"/>" binding="etcQty" width="70" align="right" data-type="Number" format="n0" visible="{{etcQtyVisibleFg}}"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.qty"/>" binding="qty" width="70" align="right" data-type="Number" format="n0" visible="{{qtyVisibleFg}}"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.uprc"/>" binding="uprc" width="70" align="right" visible="{{uprcVisibleFg}}"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="excelUploadMPSErrInfo.remark"/>" binding="remark" width="70" align="left" visible="{{remarkVisibleFg}}"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPSErrInfo.js?ver=20200910.01" charset="utf-8"></script>