<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchProdVendrLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:460px;" fade-in="false" fade-out="false">

    <div class="wj-dialog wj-dialog-columns">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.searchProdVendr.info"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <label id="lblChkVendrCd" style="display: none;"></label>
            <label id="lblChkVendrNm" style="display: none;"></label>
            <label id="lblProdCd" style="display: none;"></label>
            <%-- 그리드 --%>
            <%--- 적용 상품 거래처 그리드 --%>
            <div class="oh">
                <div class="w50 fl" ng-controller="searchProdVendrCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <div class="oh" style="height: 30px;"></div>
                        <div class="oh mt10">
                            <%-- 적용 상품 거래처 --%>
                            <span class="s14 bk fl lh25 mr10" >
                                <s:message code="prod.searchProdVendr.prodVendr"/>
                            </span>
                            <%-- 미적용 상품 이동 --%>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="deleteVendr()" ><s:message code="prod.searchProdVendr.del"/></a>
                            </span>
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
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.searchProdVendr.vendrCd"/>" binding="vendrCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.searchProdVendr.vendrNm"/>" binding="vendrNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
                <%--- 미적용상품 그리드 --%>
                <div class="w50 fr" ng-controller="searchNoProdVendrCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <%-- 적용 버튼 --%>
                        <div class="tr">
                            <button id="funcApply" class="btn_blue">
                                <s:message code="cmm.apply" />
                            </button>
                        </div>
                        <div class="oh mt10">
                            <%-- 미적용 상품 거래처 --%>
                             <span class="s14 bk fl lh25 mr10" >
                                <s:message code="prod.searchProdVendr.noProdVendr"/>
                            </span>
                            <%-- 적용 상품 이동 --%>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="registVendr()" ><s:message code="prod.searchProdVendr.regist"/></a>
                            </span>
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
                                    item-formatter="_itemFormatter">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.searchProdVendr.vendrCd"/>" binding="vendrCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="prod.searchProdVendr.vendrNm"/>" binding="vendrNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

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

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prod/searchProdVendr.js?ver=20201224.03" charset="utf-8"></script>