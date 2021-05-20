<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="popUpNoTouchKeyLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <h3 class="" style="line-height:50px;"><s:message code="touchKey.noTouchKey"/></h3>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="popUpNoTouchKeyCtrl">
            <span class="s14 lh20 fl mb5"><s:message code="touchKey.noTouchKeyMsg"/></span>
            <div class="wj-dialog-content" style="height:400px;">
                <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.classCd"/>" binding="tukeyClassCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.classNm"/>" binding="tukeyClassNm" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.pageNo"/>" binding="pageNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.prodNm"/>" binding="prodNm" width="90" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.prodInfo"/>" binding="prodInfo" data-map="prodInfoDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.saleProdYn"/>" binding="saleProdYn" data-map="saleProdYnDataMap" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="touchKey.layer.useYn"/>" binding="useYn" data-map="useYnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide btn_gray"><s:message code="cmm.close" /></button>
        </div>
    </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/touchKey/popUpNoTouchKey.js?ver=20190114.01" charset="utf-8"></script>
