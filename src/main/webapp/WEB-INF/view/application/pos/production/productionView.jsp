<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
    .cusWrap {width: 100%; min-height: 768px; height: 100%; display: table;}
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    .cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}
</style>

<body ng-app="rootApp" ng-controller="rootCtrl">
<div class="cusWrap">
    <div class="content-middle">
        <div class="subCon">
            <div class="searchBar flddUnfld">
                <span class="cusTitle"><s:message code="application.pos.production"/></span>
            </div>
            <%-- 예외출고 상품 목록 --%>
            <div class="mb40" ng-controller="productCtrl">
                <div class="wj-TblWrapBr mr10 pd20" style="height:350px;">
                    <%-- 예외출고 상품 그리드 --%>
                    <div id="productGrid" class="wj-gridWrap" style="height:310px; overflow-y: hidden;">
                        <wj-flex-grid
                                autoGenerateColumns="false"
                                control="flex"
                                initialized="initGrid(s,e)"
                                sticky-headers="true"
                                selection-mode="Row"
                                items-source="data"
                                item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.storeCd"/>" binding="storeCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.prodClassCd"/>" binding="prodClassCd" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.prodClassNm"/>" binding="prodClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.prodCd"/>" binding="prodCd" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="application.pos.production.regProd"/>" binding="regProd" width="100" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/production/production.js?ver=2018091401" charset="utf-8"></script>

<%-- 예외출고 수량 등록 팝업 --%>
<c:import url="/WEB-INF/view/application/pos/production/productionRegist.jsp">
</c:import>
