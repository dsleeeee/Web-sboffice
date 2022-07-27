<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/dstmnDtl/"/>

<wj-popup id="wjDstmnDtlLayer" control="wjDstmnDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="dstmnDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstmnDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="outstockConfm.dtl.title"/>
      <label id="lblTitle"></label>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 700px;">
      <table class="tblType01" style="display: none;">
        <colgroup>
          <col class="w25"/>
          <col class="w25"/>
          <col class="w25"/>
          <col class="w25"/>
        </colgroup>
        <thead>
        <tr>
          <th><s:message code="outstockConfm.dtl.slipNo"/></th>
          <th><s:message code="outstockConfm.dtl.store"/></th>
          <th id="thOutDate"></th>
          <th><s:message code="outstockConfm.dtl.inDate"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td class="tc"><span id="infoSlipNo" class="pd5 txtIn s12"></span></td>
          <td class="tc"><span id="infoStoreNm" class="pd5 txtIn s12"></span></td>
          <td class="tc"><span id="infoOutDate" class="pd5 txtIn s12"></span></td>
          <td class="tc"><span id="infoInDate" class="pd5 txtIn s12"></span></td>
        </tr>
        </tbody>
      </table>

      <table class="tblType01 mt10">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 비고 --%>
          <th><s:message code="outstockConfm.dtl.hdRemark"/></th>
          <td>
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="300"/>
          </td>
          <%-- 본사비고(매장열람불가) --%>
          <th><s:message code="outstockConfm.dtl.hqRemark"/></th>
          <td>
            <input type="text" id="hqRemark" name="hqRemark" ng-model="hqRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr style="display: none;">
          <%-- 배송기사 --%>
          <th><s:message code="outstockConfm.dtl.dlvrNm"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="srchDtlDlvrCd"
                ng-model="dlvrCd"
                items-source="_getComboData('srchDtlDlvrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
          </td>
        </tr>
        <tr>
          <%-- 거래명세표 --%>
          <th><s:message code="outstockConfm.dtl.stmtAcct"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="stmtAcctFg"
                ng-model="stmtAcctFg"
                items-source="_getComboData('stmtAcctFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <a href="#" class="btn_grayS" ng-click="reportTrans()"><s:message code="outstockConfm.dtl.stmtAcctPrint"/></a>
            <%--<a href="#" class="btn_grayS" ng-click=""><s:message code="outstockConfm.dtl.stmtAcctExcel"/></a>--%>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="tooltipBtn mt20 fl">설명
        <span class="tooltiptext tooltip-right">
        * <s:message code="outstockConfm.dtl.txt1"/><br/>
        * <s:message code="outstockConfm.dtl.txt2"/><br/>
        * <s:message code="outstockConfm.dtl.txt3"/><br/>
        </span>
      </div>

      <div class="tr mt10 fr">
        <div id="outstockBtnLayer" style="display: none;">
          <%-- 출고확정 체크박스 --%>
          <span id="spanOutstockConfirmFg" class="chk pdb5 txtIn fl" style="top: 0px;" ng-if="spanOutstockConfirmFg">
            <input type="checkbox" name="outstockConfirmFg" id="outstockConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="outstockConfirmFg"><s:message code="outstockConfm.dtl.confirmFg"/></label>
          </span>
          <%-- 출고일자 --%>
          <div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">
            <span class="txtIn"><input id="dtlOutDate" class="w120px"></span>
          </div>
          <%-- 출고내역으로 입고내역 세팅 --%>
          <button type="button" id="btnSetOutToIn" class="btn_skyblue ml5 fl" ng-click="setOutToIn()" ng-if="btnSetOutToIn"><s:message code="outstockConfm.dtl.setOutToIn"/></button>          
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave">
            <s:message code="cmm.save"/></button>
        </div>
        <%-- 출고 후 저장 --%>
        <button type="button" id="btnOutstockAfterDtlSave" class="btn_skyblue ml5 fl" ng-click="saveOutstockAfter()" ng-if="btnOutstockAfterDtlSave">
          <s:message code="cmm.save"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
			frozen-columns		="7">	<%-- allowMerging		="Cells" --%>            

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.slipNo"/>" binding="slipNo" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.slipFg"/>" binding="slipFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.slipFg"/>" binding="slipKind" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.barcdCd"/>" binding="barcdCd" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.poUnitQty"/>" binding="poUnitQty" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.outSplyUprc"/>" binding="outSplyUprc" width="60" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            
            <%--
            outstockConfm.dtl.outUnitQty=출고수량
            outstockConfm.dtl.outEtcQty=출고수량
                        
            --%>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"/>" binding="outUnitQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"/>" binding="outEtcQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.outUnitQty"/>" binding="outTotQty" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.outAmt"/>" binding="outAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.outVat"/>" binding="outVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.outTot"/>" binding="outTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.remark"/>" binding="remark" width="150" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.vatFg"/>" binding="vatFg01" width="60" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockConfm.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  binding="arrStorageCd"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrStorageNm"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  binding="arrCurrQty"  	width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInUnitQty"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInEtcQty"   width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInTotQty"   width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  binding="arrInAmt"      width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInVat"      width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInTot"      width="200" align="left"   	is-read-only="true"     visible="false"                                                	></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstmn/dstmnDtl.js?ver=20220726.02" charset="utf-8"></script>
