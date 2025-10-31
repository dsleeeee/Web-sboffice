<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/day/day/dayStoreDc/"/>

<wj-popup control="wjCouponSaleDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;height:480px;" fade-in="false" fade-out="false">
    <div ng-controller="couponSaleDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="couponInfo.srchSaleCoupon"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
<%--            <div class="oh sb-select dkbr">--%>
<%--                &lt;%&ndash; 엑셀다운로드 &ndash;%&gt;--%>
<%--                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">--%>
<%--                    <s:message code="cmm.excel.down" />--%>
<%--                </button>--%>
<%--            </div>--%>
            <%-- 그리드 --%>
            <div class="w100 mt10 mb20">
                <div class="wj-gridWrap" style="height:350px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter"
                            is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="couponInfo.coupnCd"/>"         binding="mergeCoupnCd"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.coupnNm"/>"         binding="coupnNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.coupnSerNo"/>"      binding="coupnSerNo"        width="150"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.finalStatus"/>"     binding="finalStatus"       width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.detailFg"/>"        binding="detailFg"          width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.usePartCd"/>"       binding="coupnUsePart"      width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.usePartNm"/>"       binding="vendrNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.startUsePeriod"/>"  binding="startDate"         width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.endUsePeriod"/>"    binding="endDate"           width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.prodCd"/>"          binding="coupnProdCd"       width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.prodNm"/>"          binding="prodNm"            width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.saleUprc"/>"        binding="saleUprc"          width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.storeCd"/>"         binding="coupnStoreCd"      width="80"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.storeNm"/>"         binding="storeNm"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.issueDate"/>"       binding="coupnIssueDate"    width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.saleDate"/>"        binding="coupnUseDate"      width="90"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.lastModDt"/>"       binding="modDt"             width="130" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="couponInfo.lastModNm"/>"       binding="modNm"             width="90"  align="center" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/coupon/couponInfo/couponSaleDtl.js?ver=20251031.01" charset="utf-8"></script>