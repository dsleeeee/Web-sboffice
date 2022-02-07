<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/standMove/standMoveRegist/"/>

<wj-popup id="wjStandMoveRegistLayer" control="wjStandMoveRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="standMoveRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="standMoveRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="standMove.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="storeMove.reg.moveDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="regMoveDate" class="w150px" ng-model="moveDate"></span>
            </div>
          </td>
          <%-- 이동구분 --%>
          <th><s:message code="standMove.moveFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchRegDlvrFg"
                  ng-model="regDlvrFg"
                  items-source="_getComboData('srchRegDlvrFg')"
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
          <%-- 상품코드 --%>
          <th><s:message code="storeMove.add.prodCd"/></th>
          <td>
            <input type="text" id="srchStandMoveRegProdCd" name="srchStandMoveRegProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="storeMove.add.prodNm"/></th>
          <td>
            <input type="text" id="srchStandMoveRegProdNm" name="srchStandMoveRegProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li><s:message code="storeMove.reg.txt1"/></li>
<%--         <li><s:message code="storeMove.reg.txt2"/></li> --%>
<%--         <li><s:message code="storeMove.reg.txt3"/></li> --%>
      </ul>

      <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchStandMoveRegistList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="tr mt20 fr">
        <%-- 저장 --%>
        <button type="button" id="btnRegSave" class="btn_skyblue ml5 fl" ng-click="save()">
          <s:message code="cmm.save"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()">
          <s:message code="storeMove.reg.confirmBtn"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeMove.reg.prodCd"/>" 			binding="prodCd" 	width="100" align="center" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.prodNm"/>" 			binding="prodNm" 	width="150" align="left" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.poUnitFg"/>" 		binding="poUnitFg"  width="70" 	align="right" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeMove.reg.poUnitQty"/>" 		binding="poUnitQty" width="70" align="right" 	is-read-only="true"></wj-flex-grid-column>
           
            <wj-flex-grid-column header="<s:message code="standMove.safeStockQty"/>" 		binding="safeStockQty" 		width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrQty"/>"    	binding="totCurrQty" 		width="70" align="right" aggregate="Sum" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standTotQty"/>" 		binding="standTotQty" 		width="70" align="right" visible="false" max-length=8 data-type="Number" format="n0" is-read-only="true" value="0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrUnitQty"/>" 	binding="standCurrUnitQty" 	width="70" align="right" max-length=8 data-type="Number" format="n0" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="standMove.standCurrEtcQty"/>" 	binding="standCurrEtcQty" 	width="70" align="right" max-length=8 data-type="Number" format="n0" is-read-only="true"></wj-flex-grid-column>
                        
            <wj-flex-grid-column header=""              binding="arrStorageCd"  	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrStorageNm"  	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			
			<wj-flex-grid-column header=""              binding="arrCurrQty"  		width="200" align="left"   	is-read-only="true"     visible="false" 		 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrUnitQty"  		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrEtcQty"   		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>
			<wj-flex-grid-column header=""              binding="arrTotQty"   		width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column>

<!-- 			<wj-flex-grid-column header=""              binding="arrOrderAmt"      	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column> -->
<!-- 			<wj-flex-grid-column header=""              binding="arrOrderVat"      	width="200" align="left"   	is-read-only="true"     visible="false"          ></wj-flex-grid-column> -->
<!-- 			<wj-flex-grid-column header=""              binding="arrOrderTot"      	width="200" align="left"   	is-read-only="true"     visible="false"  		 ></wj-flex-grid-column> -->
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/move/standMove/standMoveRegist.js?ver=20181224.01" charset="utf-8"></script>
