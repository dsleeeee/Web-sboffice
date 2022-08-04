<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddProd/"/>

<wj-popup id="wjRtnDstbCloseProdAddProdLayer" control="wjRtnDstbCloseProdAddProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="rtnDstbCloseProdAddProdLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbCloseProdAddProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="rtnDstbCloseProd.add.title"/> -
      <s:message code="rtnDstbCloseProd.add.addProdSubTitle"/>
      <label id="addProdSubTitle"></label>
      <label id="orderFgSubTitle"></label>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
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
          <th><s:message code="rtnDstbCloseProd.add.store"/></th>
          <td>
            <%-- 매장선택 모듈 싱글 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreM.jsp" flush="true">
              <jsp:param name="targetId" value="rtnDstbCloseProdAddProdSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
          </td>
          <td></td>
          <td></td>
<%--           <td colspan="2"><p class="s14"><s:message code="rtnDstbCloseProd.add.txt1"/></p></td> --%>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="rtnDstbCloseProd.add.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품코드 --%>
          <th><s:message code="rtnDstbCloseProd.add.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="rtnDstbCloseProd.add.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 분류 --%>
          <th><s:message code="rtnDstbCloseProd.add.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        <tr>
          <%-- 거래처 --%>
          <th <c:if test="${envst1242 == '0'}">style="display: none;"</c:if>><s:message code="rtnDstbCloseProd.dtl.vender"/></th>
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

      <div class="mt20 oh sb-select dkbr">
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
<%--         <button type="button" class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveDstbCloseProdAddProd()"><s:message code="cmm.save"/></button> --%>
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="_pageView('rtnDstbCloseProdAddProdCtrl',1);"><s:message code="cmm.search"/></button>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
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
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.prodClassNm"/>" binding="prodClassNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.add.splyUprc"/>" binding="splyUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="rtnDstbCloseProdAddProdCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddProd.js?ver=20220804.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
