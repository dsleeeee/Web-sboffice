<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockOrderSlip/"/>

<wj-popup id="wjVendrInstockOrderSlipLayer" control="wjVendrInstockOrderSlipLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div id="vendrInstockOrderSlipLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInstockOrderSlipCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrInstock.slip.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 500px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w85"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 발주일자 --%>
          <th><s:message code="vendrInstock.slip.orderDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="srchSlipStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchSlipEndDate" class="w120px"></span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 거래처 --%>
          <th><s:message code="vendrInstock.slip.vendr"/></th>
          <td>
            <%-- 거래처선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
              <jsp:param name="targetId" value="vendrInstockOrderSlipSelectVendr"/>
            </jsp:include>
            <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchVendrInstockOrderSlipList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 250px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.slipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.orderDate"/>" binding="orderDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrInstock/vendrInstockOrderSlip.js?ver=20181224.02" charset="utf-8"></script>
