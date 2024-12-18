<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchFnkeyLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;height:600px;">

    <div class="wj-dialog wj-dialog-columns" ng-controller="searchFnkeyCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="lblTitle"></span>
            <s:message code="envConfgBatchChange.fnkey.fnkey"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="oh">
                <div class="w100">
                    <div class="wj-TblWrap" style="height:580px; overflow-y: hidden;">
                        <div class="mt10">
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
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.fnkeyFg"/>" binding="fnkeyFg" width="95" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.fnkeyFgNm"/>" binding="fnkeyFgNm" width="95" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.fnkeyNo"/>" binding="fnkeyNo" width="75" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.fnkey.fnkeyNm"/>" binding="fnkeyNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>

                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--- //그리드 --%>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/searchFnkey.js?ver=20210316.01" charset="utf-8"></script>