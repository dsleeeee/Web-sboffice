<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="promotionReportDtlLayer" control="promotionReportDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">
    <div ng-controller="promotionReportDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
          <s:message code="promotionReport.promotionReportDtl"/>
          <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="height: 460px;">
          <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 400px; overflow-y: hidden; overflow-x: hidden;">
              <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="promotionReport.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.posNo"/>" binding="posNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.billNo"/>" binding="billNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.billDtlNo"/>" binding="billDtlNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.regSeq"/>" binding="regSeq" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.saleFg"/>" binding="saleFg" data-map="saleFgDataMap" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.billDt"/>" binding="billDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.saleQty"/>" binding="saleQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.totSaleAmt"/>" binding="totSaleAmt" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.dcAmt"/>" binding="dcAmt" width="75" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReport.realSaleAmt"/>" binding="realSaleAmt" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
            <%--//위즈모 테이블--%>
          </div>
        </div>
    </div>
</wj-popup>

<script>
  <%-- 판매구분 --%>
  var saleFgData = ${ccu.getCommCodeExcpAll("047")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotionReport/promotionReportDtl.js?ver=20230314.01" charset="utf-8"></script>