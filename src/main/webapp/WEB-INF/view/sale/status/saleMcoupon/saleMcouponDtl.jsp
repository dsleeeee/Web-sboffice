<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjSaleMcouponDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:660px;height:500px;" fade-in="false" fade-out="false">
    <div ng-controller="saleMcouponDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleMcouponDtl.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></button>
            </div>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.storeNm"/>" binding="storeNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="saleMcouponDtl.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.saleCnt"/>" binding="saleCnt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.billNo"/>" binding="billNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.lineNo"/>" binding="lineNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.lineSeqNo"/>" binding="lineSeqNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="saleMcouponDtl.saleYn"/>" binding="saleYn" data-map="saleYnDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        </c:if>
                        <wj-flex-grid-column header="<s:message code="saleMcouponDtl.saleAmt"/>" binding="saleAmt" width="90" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/status/saleMcoupon/saleMcouponDtl.js?ver=20230412.01" charset="utf-8"></script>