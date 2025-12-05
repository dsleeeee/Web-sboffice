<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjVendrStoreRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:750px;height:450px;" fade-in="false" fade-out="false">

    <div class="wj-dialog wj-dialog-columns">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.vendrStoreRegist"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <label id="lblChkVendrCd" style="display: none;"></label>
            <label id="lblChkVendrNm" style="display: none;"></label>
            <label id="lblProdCd" style="display: none;"></label>
            <%-- 그리드 --%>
            <%--- 거래처 그리드 --%>
            <div class="oh">
                <div class="w65 fl" ng-controller="prodVendrRegistCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <div class="oh">
                            <button class="btn_skyblue fr" id="btnSaveProdVendr" ng-click="saveProdVendr()"><s:message code='cmm.save' /></button>
                            <button class="btn_skyblue fr mr5" id="btnAddVendr" ng-click="addVendr()"><s:message code='cmm.add' /></button>
                        </div>
                        <div class="mt10">
                            <div class="wj-TblWrap mr10" style="height:300px; overflow-y:hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter">

                                    <!-- define columns -->
<%--                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.vendrCd"/>"     binding="vendrCd"       width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.vendrNm"/>"     binding="vendrNm"       width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.tradeFg"/>"     binding="tradeFg"       width="60" align="center" data-map="tradeFgComboDataMap"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.tradeForm"/>"   binding="tradeForm"     width="60" align="center" data-map="tradeFormComboDataMap"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.acquireUprc"/>" binding="acquireUprc"   width="60" align="center" data-type="Number" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.acquireRate"/>" binding="acquireRate"   width="60" align="center" data-type="Number" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.currQty"/>"     binding="currQty"       width="60" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header=""     binding="seq" width="70"  align="center" visible="false"></wj-flex-grid-column>

                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
                <%--- 매장 그리드 --%>
                <div class="w35 fr" ng-controller="prodStoreRegistCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <div class="oh" style="height: 25px;">
                            <button class="btn_skyblue fr" id="btnSaveProdVendrStore" ng-click="saveProdVendrStore()"><s:message code='cmm.save' /></button>
                        </div>
                        <div class="mt10">
                            <div class="wj-TblWrap ml10" style="height:300px; overflow-y: hidden;" >
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        ime-enabled="true">

                                    <!-- define columns -->
<%--                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>--%>
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="selectFg" width="40" is-read-only="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.storeCd"/>" binding="storeCd" width="70" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.storeNm"/>" binding="storeNm" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.vendrStoreRegist.currQty"/>" binding="currQty" width="60" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="" binding="vendrCd"    width="" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="" binding="seq"        width="" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/vendrStoreRegist.js?ver=20251126.01" charset="utf-8"></script>

<%-- 거래처 추가 팝업 --%>
<c:import url="/WEB-INF/view/base/prod/prod/searchVendr.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
