<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMCoupnProdMappingExcelUploadResultLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:850px;height:520px;" fade-in="false" fade-out="false">
    <div ng-controller="mCoupnProdMappingExcelUploadResultCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mCoupnProdMappingExcelUploadResult.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="mt10 tr" style="display: none;">
                <div class="oh sb-select dkbr">
                    <%-- 조회 --%>
                    <button class="btn_skyblue ml5 fr" id="nxBtnSearch2" ng-click="_broadcast('mCoupnProdMappingExcelUploadResultCtrl', 1)"><s:message code="cmm.search" /></button>
                </div>
            </div>

            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns.="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.resultGubun"/>" binding="resultGubun" data-map="resultGubunDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.prodCd"/>" binding="prodCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="cmm.prodNm"/>" binding="prodNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.saleUprc"/>" binding="saleUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.mcoupnNm"/>" binding="mcoupnNm" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.mappingCd"/>" binding="mcoupnProdCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.remark"/>" binding="remark" width="120" is-read-only="true" align="center"></wj-flex-grid-column>

                        <%--저장시 필요--%>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.seq"/>" binding="seq" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mCoupnProdMappingExcelUploadResult.mcoupnCd"/>" binding="mcoupnCd" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <div class="tc mt20">
                <%-- 저장 버튼 --%>
                <button id="funcSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
                <%-- 닫기 버튼 --%>
                <button id="funcClose" class="btn_blue">
                    <s:message code="cmm.close" />
                </button>
            </div>

        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/pay/mCoupnProdMapping/mCoupnProdMappingExcelUploadResult.js?ver=20250819.01" charset="utf-8"></script>