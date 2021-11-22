<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSearchUserHqBrandLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;height:460px;" fade-in="false" fade-out="false">

    <div class="wj-dialog wj-dialog-columns">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="hqEmp.searchUserHqBrand.info"/>
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <label id="lblChkHqBrandCd" style="display: none;"></label>
            <label id="lblChkHqBrandNm" style="display: none;"></label>
            <%-- 그리드 --%>
            <%--- 적용 브랜드 그리드 --%>
            <div class="oh">
                <div class="w50 fl" ng-controller="searchUserHqBrandCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <div class="oh" style="height: 30px;"></div>
                        <div class="oh mt10">
                            <%-- 적용 브랜드 --%>
                            <span class="s14 bk fl lh25 mr10" >
                                <s:message code="hqEmp.searchUserHqBrand.hqBrand"/>
                            </span>
                            <%-- 미적용 브랜드 이동 --%>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="deleteHqBrand()" ><s:message code="hqEmp.searchUserHqBrand.del"/></a>
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
                                    <wj-flex-grid-column header="<s:message code="hqEmp.searchUserHqBrand.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="hqEmp.searchUserHqBrand.hqBrandNm"/>" binding="hqBrandNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
                <%--- 미적용 브랜드 그리드 --%>
                <div class="w50 fr" ng-controller="searchNoUserHqBrandCtrl">
                    <div class="wj-TblWrap mr10" style="height:370px; overflow-y: hidden;">
                        <%-- 적용 버튼 --%>
                        <div class="tr">
                            <button id="funcApply" class="btn_blue">
                                <s:message code="cmm.apply" />
                            </button>
                        </div>
                        <div class="oh mt10">
                            <%-- 미적용 브랜드 --%>
                            <span class="s14 bk fl lh25 mr10" >
                                <s:message code="hqEmp.searchUserHqBrand.noHqBrand"/>
                            </span>
                            <%-- 적용 브랜드 이동 --%>
                            <span class="fr">
                                <a href="#" class="btn_grayS2" ng-click="registHqBrand()" ><s:message code="hqEmp.searchUserHqBrand.regist"/></a>
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
                                    <wj-flex-grid-column header="<s:message code="hqEmp.searchUserHqBrand.hqBrandCd"/>" binding="hqBrandCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="hqEmp.searchUserHqBrand.hqBrandNm"/>" binding="hqBrandNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

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

<script type="text/javascript" src="/resource/solbipos/js/base/store/emp/searchUserHqBrand.js?ver=20211122.07" charset="utf-8"></script>