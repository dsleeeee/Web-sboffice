<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon">
  <div ng-controller="prodSaleRate2Ctrl">
    <div class="searchBar">
      <a href="#" class="open fl">${menuNm}</a>
      <%-- 조회 --%>
      <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('prodSaleRate2Ctrl')">
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
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="prodSaleRate2.prodCd" /></th>
        <td><input type="text" id="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13" onkeyup="fnNxBtnSearch('1');"/></td>
        <%-- 상품명 --%>
        <th><s:message code="prodSaleRate2.prodNm" /></th>
        <td><input type="text" id="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="100" onkeyup="fnNxBtnSearch('1');"/></td>
      </tr>
      <tr>
        <%-- 분류조회 --%>
        <th><s:message code="prod.prodClass" /></th>
        <td>
          <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                 placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
          <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
        </td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장선택 --%>
          <th><s:message code="cmm.store.select"/></th>
          <td>
            <%-- 매장선택 모듈 사용시 include --%>
            <jsp:include page="/WEB-INF/view/common/popup/selectStore.jsp" flush="true">
              <jsp:param name="targetTypeFg" value="M"/>
              <jsp:param name="targetId" value="prodSaleRate2Store"/>
            </jsp:include>
            <%--// 매장선택 모듈 사용시 include --%>
          </td>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="prodSaleRate2StoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
      </tr>
      <tr>
        <%-- 매장브랜드 --%>
        <th><s:message code="prodSaleRate2.hqBrand"/></th>
        <td>
          <div class="sb-select">
            <wj-combo-box
                    id="hqBrandCd"
                    ng-model="hqBrandCd"
                    items-source="_getComboData('hqBrandCd')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    control="hqBrandCdCombo">
            </wj-combo-box>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 oh sb-select dkbr">
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue ml5 fr" ng-click="excelDownload()"><s:message code="cmm.excel.downCondition"/></button>
    </div>

    <div class="w100 mt10">
      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 380px; overflow-x: hidden; overflow-y: hidden;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="true"
          item-formatter="_itemFormatter">

            <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.startDate"/>"   binding="startDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.endDate"/>"     binding="endDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.prodClassNm"/>" binding="pathNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.prodNm"/>"      binding="prodNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="stinQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="stinAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="dlvrQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="dlvrAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="packQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="packAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          <%-- 주문채널 구분자 컬럼 생성--%>
          <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="stinQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="stinAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="dlvrQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="dlvrAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
          <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="packQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="packAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          </c:forEach>
        </wj-flex-grid>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
  
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="prodSaleRate2CtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

  <%--엑셀 리스트--%>
  <div class="w100 mt10" style="display:none;" ng-controller="prodSaleRate2ExcelCtrl">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
      <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.startDate"/>"   binding="startDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.endDate"/>"     binding="endDate" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.prodClassNm"/>" binding="pathNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.prodNm"/>"      binding="prodNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

        <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="stinQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="stinAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="dlvrQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="dlvrAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="packQty" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="packAmt" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

      <%-- 주문채널 구분자 컬럼 생성--%>
        <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="stinQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="stinAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </c:forEach>
        <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="dlvrQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="dlvrAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </c:forEach>
        <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.saleQty"/>"     binding="packQty${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="prodSaleRate2.realSaleAmt"/>" binding="packAmt${dlvrInFgCol.dlvrInFg}" width="80" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
        </c:forEach>

      </wj-flex-grid>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%--//엑셀 리스트--%>
</div>

<script type="text/javascript">
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
  var hqBrandList = ${hqBrandList};

  var dlvrInFgColList = [];

  <%--javascript에서 사용할 주문채널 구분자 json 데이터 생성--%>
  <c:forEach var="dlvrInFgCol" items="${dlvrInFgColList}">
  var param = {};
  param.dlvrInFg = "${dlvrInFgCol.dlvrInFg}";
  param.dlvrInFgNm = "${dlvrInFgCol.dlvrInFgNm}";
  dlvrInFgColList.push(param);
  </c:forEach>

  var dlvrInFgCol = '${dlvrInFgCol}';
  var arrDlvrInFgCol = dlvrInFgCol.split(',');

</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/prod/prodSaleRate2/prodSaleRate2.js?ver=20246011.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>