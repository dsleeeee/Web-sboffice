<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="promotionReportBensonDtlLayer" control="promotionReportBensonDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">
    <div ng-controller="promotionReportBensonDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
          <s:message code="promotionReportBenson.promotionReportDtl"/>
          <a href="#" class="wj-hide btn_close"></a>
        </div>

         <div class="mt10 oh sb-select mr20">
           <%-- 엑셀 다운로드 --%>
           <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
         </div>

        <div class="w100 mb20 pd20">
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
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeCd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.storeNm"/>" binding="storeNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.posNo"/>" binding="posNo" width="65" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.billNo"/>" binding="billNo" width="85" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.billDtlNo"/>" binding="billDtlNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.dcSeq"/>" binding="dcSeq" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.saleFg"/>" binding="saleFg" data-map="saleFgDataMap" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.billDt"/>" binding="billDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.prodNm"/>" binding="prodNm" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.saleQty"/>" binding="saleQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.totSaleAmt"/>" binding="totSaleAmt" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.dcAmt"/>" binding="dcAmt" width="75" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="promotionReportBenson.realSaleAmt"/>" binding="realSaleAmt" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
        </div>
    </div>
</wj-popup>

<script>
  <%-- 판매구분 --%>
  var saleFgData = ${ccu.getCommCodeExcpAll("047")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/promotion/promotionReportBenson/promotionReportBensonDtl.js?ver=20261013.01" charset="utf-8"></script>
