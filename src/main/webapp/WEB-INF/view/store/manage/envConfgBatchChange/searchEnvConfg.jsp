<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchEnvConfgLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:350px;height:400px;">

    <div class="wj-dialog wj-dialog-columns" ng-controller="searchEnvConfgCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="lblTitle"></span>
            <s:message code="envConfgBatchChange.envst.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <%-- 그리드 --%>
            <div class="oh">
                <div class="w100">
                    <div class="wj-TblWrap" style="height:370px; overflow-y: hidden;">
                        <div class="mt10">
                            <div class="wj-TblWrap" style="height:300px; overflow-y:hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.envst.envstCd"/>" binding="envstCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.envst.envstNm"/>" binding="envstNm" width="*" is-read-only="true" align="center"></wj-flex-grid-column>

                                    <%--팝업 조회시 필요--%>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.envst.dirctInYn"/>" binding="dirctInYn" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="envConfgBatchChange.envst.targtFg"/>" binding="targtFg" width="100" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

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

<script type="text/javascript" src="/resource/solbipos/js/store/manage/envConfgBatchChange/searchEnvConfg.js?ver=20210316.01" charset="utf-8"></script>