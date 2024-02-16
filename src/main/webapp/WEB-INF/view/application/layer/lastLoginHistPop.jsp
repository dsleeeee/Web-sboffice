<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="fullDimmedLastLoginHistPop" class="fullDimmed" style="display: none;"></div>
<div id="layerLastLoginHistPop" class="layer" style="display: none; position:absolute;">
    <div class="layer_inner">
        <!--layerContent-->
        <div class="title" style="width: 460px; height: 440px;">
            <%-- 타이틀 --%>
            <p class="tit">
                <s:message code="login.layer.lastLoginHist.title" />
            </p>
            <a href="#" class="btn_close"></a>
            <%-- //타이틀 --%>

            <%-- 내용 --%>
            <div ng-controller="lastLoginHistCtrl">
                <div class="w100 mt10 mb20">
                    <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                        <%--<div class="row">--%>
                            <wj-flex-grid
                                    autoGenerateColumns.="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.userId"/>" binding="userId" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginOrgn"/>" binding="loginOrgn" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginIp"/>" binding="loginIp" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginDt"/>" binding="loginDt" width="*" is-read-only="true" align="center"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    <%--</div>--%>
                </div>
            </div>
            <%-- //내용 --%>
        </div>
        <!--//layerContent-->
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/application/layer/lastLoginHistPop.js?ver=20240216.01" charset="utf-8"></script>