<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<div class="subCon" ng-controller="productCtrl">
    <div class="searchBar">
        <a href="#" class="fl"><s:message code="product.product"/></a>
        <%-- 조회 --%>
        <button class="btn_blue fr mt5 mr10" id="btnSearch" ng-click="_broadcast('productCtrl', 1)">
            <s:message code="mobile.cmm.search"/>
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
          <%-- 생산일자 --%>
          <th><s:message code="product.productDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
            </div>
          </td>
          <%-- 생산구분 --%>
          <th><s:message code="product.productFg"/></th>
          <td>
              <div class="sb-select">
                <span class="txtIn w150px">
                  <wj-combo-box
                    id="srchProductFg"
                    ng-model="productFg"
                    items-source="_getComboData('srchProductFg')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="_initComboBox(s)">
                  </wj-combo-box>
                </span>
              </div>
          </td>
        </tr>
        <tr>
            <%-- 진행구분 --%>
            <th><s:message code="product.procFg"/></th>
            <td>
                <div class="sb-select">
                  <span class="txtIn w150px">
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
                </div>
            </td>
            <%-- 생산등록, 폐기등록 --%>
            <th><s:message code="product.procFg0"/></th>
            <td>
                <div class="sb-select fl mr10">
                  <span class="txtIn"><input id="productDate" class="w150px" ng-model="productDate"></span>
                </div>
                <a href="#" class="btn_grayS" ng-click="regProduct('0')"><s:message code="product.product"/></a>
                <a href="#" class="btn_grayS" ng-click="regProduct('1')"><s:message code="product.disuseReg"/></a>
            </td>
        </tr>
        </tbody>
      </table>

    <div class="mt20 tr">
        <%-- 삭제 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDelete" ng-click="deleteProduct()"><s:message code="cmm.delete"/></button>
    </div>

    <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.productDate"/>" binding="productDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.productFg"/>" binding="productFg" width="90" align="center" is-read-only="true" data-map="productFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.seqNo"/>" binding="seqNo" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.procFg"/>" binding="procFg" width="50" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.title"/>" binding="productTitle" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.dtlCnt"/>" binding="dtlCnt" width="60" align="center" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.regDate"/>" binding="regDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.confmDate"/>" binding="confmDate" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="product.storageCd"/>" binding="productStorageCd" width="0" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="productCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="productCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
    var orgnFg = "${orgnFg}";
    var storeCd = "${storeCd}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/stock/product/product/product.js?ver=20220706.01" charset="utf-8"></script>

<%-- 생산등록 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/product/product/productDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 생산등록 등록 레이어 --%>
<c:import url="/WEB-INF/view/stock/product/product/productRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>