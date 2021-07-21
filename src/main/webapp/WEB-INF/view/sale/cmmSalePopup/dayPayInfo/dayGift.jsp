<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/dayPayInfo/dayGift/"/>

<wj-popup id="wjDayGiftLayer" control="wjDayGiftLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px; height:480px;">
  <div id="dayGiftLayer" class="wj-dialog wj-dialog-columns" ng-controller="dayGiftCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dayGift.dayGiftPay"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2">
      <div class="oh sb-select dkbr">
        <%-- 엑셀다운로드 --%>
        <button class="btn_skyblue ml5 fr" ng-click="excelDownload()">
          <s:message code="cmm.excel.down" />
        </button>
      </div>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dayGift.saleDate"/>" binding="saleDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.storeNm"/>" binding="storeNm" width="140" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.posNo"/>" binding="posNo" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.billNo"/>" binding="billNo" width="75" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.saleYn"/>" binding="saleYn" width="70" align="center" is-read-only="true" data-map="saleYnMap"></wj-flex-grid-column>
            <%--<wj-flex-grid-column header="<s:message code="dayGift.giftSeq"/>" binding="giftSeq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
            <%--<wj-flex-grid-column header="<s:message code="dayGift.giftCd"/>" binding="giftCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dayGift.giftNm"/>" binding="giftNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.giftProcFgNm"/>" binding="giftProcFgNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.giftSerNo"/>" binding="giftSerNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.giftUprc"/>" binding="giftUprc" width="90" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.giftQty"/>" binding="giftQty" width="90" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.giftTotAmt"/>" binding="giftTotAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dayGift.rtnPayAmt"/>" binding="rtnPayAmt" width="90" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/dayPayInfo/dayGift.js?ver=20210719.01" charset="utf-8"></script>