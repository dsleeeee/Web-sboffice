<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMobilePartnerLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:90%;height:85%;" fade-in="false" fade-out="false">
    <div ng-controller="mobilePartnerCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="mobile.payInfo.partner.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <div class="oh sb-select dkbr">
                <%-- 엑셀다운로드 --%>
                <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down" />
                </button>
            </div>
            <div class="w100">
                <%--위즈모 테이블--%>
                <div class="popGrid mt5" style="height:550px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.storeCd"/>" binding="storeCd" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.storeNm"/>" binding="storeNm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.partnerNm"/>" binding="partnerNm" width="1.*" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.saleCnt"/>" binding="saleCnt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.saleAmt"/>" binding="saleAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.dcAmt"/>" binding="dcAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.cancelCnt"/>" binding="cancelCnt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="mobile.payInfo.partner.cancelAmt"/>" binding="cancelAmt" width="1.*" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/mobile/sale/cmmSalePopup/payInfo/mobilePartner.js?ver=20230921.01" charset="utf-8"></script>