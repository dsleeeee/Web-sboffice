<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdAddProd/"/>

<wj-popup id="wjDstbCloseProdAddProdLayer" control="wjDstbCloseProdAddProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dstbCloseProdAddProdLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdAddProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dstbCloseProd.add.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p class="s14 bk mb5 fl"><s:message code="dstbCloseProd.add.addProdSubTitle"/></p>
      <p id="addProdSubTitle" class="s14 bk ml5 mb5 fl"></p>
      <p id="orderFgSubTitle" class="s14 bk ml5 mb5 fl"></p>
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 매장코드 --%>
          <th><s:message code="dstbCloseProd.add.store"/></th>
          <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
              <jsp:param name="targetId" value="dstbCloseProdAddProdSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
          <td colspan="2"><p class="s14">등록하실 매장을 선택한 후 상품을 조회하십시오.</p></td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="dstbCloseProd.add.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="dstbCloseProd.add.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="dstbCloseProd.add.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 분류 --%>
          <th><s:message code="dstbCloseProd.add.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        <tr>
          <%-- 거래처 --%>
          <th <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>><s:message code="dstbCloseProd.dtl.vender"/></th>
          <td <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>>
            <div class="sb-select fl w150px">
              <wj-combo-box
                id="dtlVendrCd"
                ng-model="vendrCd"
                control="dtlVendrCdCombo"
                items-source="_getComboData('dtlVendrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="_pageView('dstbCloseProdAddProdCtrl',1);">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="listScaleBox"
          ng-model="listScale"
          items-source="_getComboData('listScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          initialized="_initComboBox(s)"
          control="conListScale"
		  is-editable="true"
		  text-changed="_checkValidation(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
        <%-- 저장 --%>
        <%--<button type="button" class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveDstbCloseProdAddProd()">
          <s:message code="cmm.save"/></button>--%>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 270px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodClassNm"/>" binding="prodClassNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.splyUprc"/>" binding="splyUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dstbCloseProdAddProdCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbCloseProd/dstbCloseProdAddProd.js?ver=20220722.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
