<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
    /*.cusWrap {width: 100%; min-height: 768px; height: 100%; display: table;}*/
    .cusWrap {width: 760px; min-height: 590px; height: 100%; display: table;}
    .content-middle {width: 100%; height: 100%; display: table-cell; vertical-align: middle;}
    .cusTitle {display:block; width:100%; height:100%; line-height:45px; color:#337dde;  padding:0 15px; font-size:0.875em; position:relative;}
</style>

<body ng-app="rootApp" ng-controller="rootCtrl">
<div class="cusWrap">
  <div class="content-middle">
    <div class="subCon" ng-controller="productCtrl">
      <div class="searchBar flddUnfld">
        <a href="#" class="open fl"><s:message code="application.pos.excpForward"/></a>
        <%-- 조회 --%>
        <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
          <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('productCtrl')">
            <s:message code="cmm.search" />
          </button>
        </div>
      </div>

      <table class="searchTbl">
        <colgroup>
          <col class="w45" />
          <col class="w55" />
        </colgroup>
        <tbody>
        <tr>
          <%-- 분류 --%>
          <th><s:message code="application.pos.excpForward.prodClass" /></th>
          <td>
            <input type="text" id="srchProdClassNm" class="sb-input w100"  ng-model="prodClassNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="application.pos.excpForward.prodClass" /> 선택" readonly/>
            <input type="hidden" id="srchProdClasscd" class="sb-input w100"  ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        </tbody>
      </table>

      <%-- 예외출고 상품 목록 --%>
      <div class="mb40" >
        <%-- 예외출고 상품 그리드 --%>
        <div id="productGrid" class="wj-gridWrap" style="height:370px; overflow-y: hidden; overflow-x: hidden;">
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
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.saleDate"/>" binding="saleDate" align="center" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.storeCd"/>" binding="storeCd" align="center" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodClassCd"/>" binding="prodClassCd" align="center" width="100" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodClassNm"/>" binding="prodClassNm" width="200" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodCd"/>" binding="prodCd" align="center" width="120" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.prodNm"/>" binding="prodNm" width="*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.inQty"/>" binding="inQty" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.totSaleQty"/>" binding="totSaleQty" align="center" width="70" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.qtIo"/>" binding="qtIo" align="center" width="80" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.stockQty"/>" binding="stockQty" align="center" width="90" is-read-only="true" visible="false" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="application.pos.excpForward.regist"/>" binding="regist" align="center" width="90" is-read-only="true"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="productCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>

    </div>
  </div>
  <script type="text/javascript" src="/resource/solbipos/js/application/pos/excpForward/excpForward.js?ver=20181218.01" charset="utf-8"></script></div>
</body>

<%-- 상품분류 조회 팝업 --%>
<c:import url="/WEB-INF/view/application/pos/excpForward/searchExcpProdClassCd.jsp">
</c:import>


<%-- 예외출고 수량 등록 팝업 --%>
<c:import url="/WEB-INF/view/application/pos/excpForward/excpForwradRegist.jsp">
</c:import>
