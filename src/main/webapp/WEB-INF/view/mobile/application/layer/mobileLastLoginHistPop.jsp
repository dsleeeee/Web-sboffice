<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="fullDimmedMobileLastLoginHistPop" class="fullDimmed" style="display: none;"></div>
<div id="layerMobileLastLoginHistPop" class="layer" style="display: none;">
    <div class="layer_inner">
        <!--layerContent-->
        <div class="title" style="width: 410px; height: 450px;">
            <%-- 타이틀 --%>
            <p class="tit">
                <s:message code="login.layer.lastLoginHist.title" />
            </p>
            <%--<a href="#" class="btn_close"></a>--%>
            <%-- //타이틀 --%>

            <%-- 내용 --%>
            <div ng-controller="mobileLastLoginHistCtrl">
                <div class="w100 mt5">
                    <div class="wj-gridWrap" style="height:350px; overflow-y: hidden; overflow-x: hidden;">
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
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.userId"/>" binding="userId" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginOrgn"/>" binding="loginOrgn" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginIp"/>" binding="loginIp" width="85" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.loginDt"/>" binding="loginDt" width="125" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.statCd"/>" binding="statCd" width="50" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="login.layer.lastLoginHist.remark"/>" binding="remark" data-map="remarkDataMap" width="160" is-read-only="true" align="center"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
                <div class="btnSet">
                    <span><a href="#" class="btn_blue" ng-click="close()"><s:message code="cmm.close" /></a></span>
                </div>
            </div>
            <%-- //내용 --%>
        </div>
        <!--//layerContent-->
    </div>
</div>

<script type="text/javascript" src="/resource/solbipos/js/mobile/application/layer/mobileLastLoginHistPop.js?ver=20240226.01" charset="utf-8"></script>