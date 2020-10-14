<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="posFuncUseXmlLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:580px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="xmlTitle"></label>
            <span id="storeTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body" ng-controller="posFuncUseXmlCtrl">

            <%--- 그리드 --%>
            <div class="oh">
                <div class="wj-TblWrap mr10" style="height:470px; overflow-y:hidden;">
                    <%-- 기능키영역 --%>
                    <div id="funcKeyDiv">
                        <div style="height: 290px;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="ListBox"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    allow-dragging="None">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNo"/>" binding="fnkeyNo" width="100" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNm"/>" binding="fnkeyNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posFunc.grid.imgFileNm"/>" binding="imgFileNm" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="posFunc.grid.useYn"/>" binding="fnkeyUsed" width="70" is-read-only="true" visible="false"></wj-flex-grid-column>

                            </wj-flex-grid>
                        </div>
                        <div class="updownSet oh mt10">
                            <button class="btn_skyblue" id="btnFuncDelete"><s:message code="cmm.delete" /></button>
                            <button class="btn_skyblue" id="btnFuncSave"><s:message code="cmm.save" /></button>
                        </div>
                        <div style="overflow-x: auto;">
                            <div id="funcKeyGraph" class="funcKeyWrap funcKeyLine2 mt10"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</wj-popup>

<script type="text/javascript">
    // Default resources are included in grapheditor resources
    mxLoadResources = false;

    // urlParams is null when used for embedding
    window.urlParams = window.urlParams || {};

    window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

    window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
    window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
    window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
    window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
    window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';

    window.FUNCKEY_OPEN_URL = window.FUNCKEY_OPEN_URL || '/base/store/posfunc/use/getFuncKeyXml.sb';
    window.FUNCKEY_SAVE_URL = window.FUNCKEY_SAVE_URL || '/base/store/posfunc/use/saveFuncKey.sb';

    window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
    window.mxLanguage = window.mxLanguage || urlParams['lang'];
    window.mxLanguages = window.mxLanguages || ['ko'];

</script>

<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.filter.min.js?ver=520182500" charset="utf-8"></script>
<script type="text/javascript" src="/resource/solbipos/js/base/store/posFunc/posFuncUseXml.js?ver=20201013.01" charset="utf-8"></script>