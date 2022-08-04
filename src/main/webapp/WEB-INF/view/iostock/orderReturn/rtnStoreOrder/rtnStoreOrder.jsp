<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnStoreOrder/rtnStoreOrder/"/>

<div class="subCon" ng-controller="rtnStoreOrderCtrl">
  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('rtnStoreOrderCtrl')">
        <s:message code="cmm.search"/>
    </button>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn w150px">
          <wj-combo-box
            id="srchDateFg"
            ng-model="dateFg"
            items-source="_getComboData('srchDateFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
          </span>
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행구분 --%>
      <th><s:message code="storeOrder.procFg"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchProcFg"
            ng-model="procFg"
            items-source="_getComboData('srchProcFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
    </tr>
    <tr>
      <%-- 반품요청일자 --%>
      <th><s:message code="rtnStoreOrder.reqDate"/></th>
      <td colspan="3">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
            <jsp:param name="targetId" value="rtnStoreOrderSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="rtnStoreOrderSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <div class="sb-select fl mr10">
          <span class="txtIn"><input id="reqDate" class="w150px" ng-model="rtnStoreOrder.reqDate"></span>
        </div>
        <%-- 거래처 --%>
        <div class="sb-select fl w150px" <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>>
          <wj-combo-box
            id="vendrCd"
            ng-model="vendrCd"
            control="vendrCdCombo"
            items-source="_getComboData('vendrCd')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </div>
        <a href="#" class="btn_grayS" ng-click="newReqOrder()"><s:message code="rtnStoreOrder.reqRegist"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt20 oh sb-select dkbr">
      <button class="btn_skyblue fr" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>  <%-- 엑셀 다운로드 --%>
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
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.reqDate"/>"  binding="reqDate" 	width="100" align="center" 	is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.slipFg"/>"   binding="slipFg" 	width="70" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.procFg"/>"   binding="procFg" 	width="70" 	align="center" 	is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.vendr"/>"    binding="hqVendrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtlCnt"/>"   binding="dtlCnt" 	width="70" 	align="right" 	is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderAmt"/>" binding="orderAmt" width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderVat"/>" binding="orderVat" width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderTot"/>" binding="orderTot" width="130" align="right" 	is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.remark"/>"   binding="remark" 	width="*" 	align="left" 	is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.vendr"/>"    binding="hqVendrCd" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnStoreOrderCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>


<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var gReqDate = "${reqDate}";
  var gEnvst1044 = "${envst1044}";
  var gEnvst1042 	= '${envst1042}';
  var gEnvst1242  = '${envst1242}';
  /*
  console.log('gReqDate  : ' + gReqDate);
  console.log('gEnvst1044: ' + gEnvst1044);
  console.log('gEnvst1042: ' + gEnvst1042);
  console.log('gEnvst1242: ' + gEnvst1242);
  */

  <%-- 본사 거래처 콤보박스 --%>
  var vendrList = ${vendrList};
  var empVendrCd = '${empVendrCd}';

</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnStoreOrder/rtnStoreOrder.js?ver=20220804.01" charset="utf-8"></script>

<%-- 반품등록 상품 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 반품등록 상품 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
