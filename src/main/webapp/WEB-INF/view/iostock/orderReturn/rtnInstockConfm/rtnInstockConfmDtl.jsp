<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/"/>

<wj-popup id="wjRtnInstockConfmDtlLayer" control="wjRtnInstockConfmDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnInstockConfmDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnInstockConfmDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
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
          <th><s:message code="rtnInstockConfm.dtl.hdRemark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="rtnInstockConfm.dtl.dlvrNm"/></th>
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
          <th><s:message code="rtnInstockConfm.dtl.stmtAcct"/></th>
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
            <a href="#" class="btn_grayS" ng-click="reportTrans()"><s:message code="rtnInstockConfm.dtl.stmtAcctPrint"/></a>
            <%--<a href="#" class="btn_grayS" ng-click=""><s:message code="rtnInstockConfm.dtl.stmtAcctExcel"/></a>--%>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt2"/></li>
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="instockBtnLayer" style="display: none;">
        	<%--출고창고 --%>
          	<p class="s14 bk fl mr5 lh30" <c:if test="${storageEnvstVal == '0'}">style="display: none;"</c:if> >
              <s:message code="outstockConfm.dtl.outStorage"/>
            </p>
          	<span class="txtIn w150px sb-select fl mr5" <c:if test="${storageEnvstVal == '0'}">style="display: none;"</c:if> >
              <wj-combo-box
                id="saveDtlOutStorageCd"
                ng-model="save.dtl.outStorageCd"
                items-source="_getComboData('saveDtlOutStorageCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s)"
                >
              </wj-combo-box>
            </span>
          	<%--배송기사 --%>    
          	<p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.dtl.dlvrNm"/></p>     
        	<span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="saveDtlDlvrCd"
                ng-model="save.dtl.dlvrCd"
                items-source="_getComboData('saveDtlDlvrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
          <%-- 입고확정 체크박스 --%>
          <span id="spanInstockConfirmFg" class="chk pdb5 txtIn fl" style="top: 0px;" ng-if="spanInstockConfirmFg">
            <input type="checkbox" name="instockConfirmFg" id="instockConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="instockConfirmFg"><s:message code="rtnInstockConfm.dtl.confirmFg"/></label>
          </span>
          <%-- 입고일자 --%>
          <div id="divDtlInDate" class="sb-select ml10 fl" style="display: none;">
            <span class="txtIn"><input id="dtlInDate" class="w120px"></span>
          </div>
          <%-- 출고내역으로 입고내역 세팅 --%>
          <button type="button" id="btnSetOutToIn" class="btn_skyblue ml5 fl" ng-click="setOutToIn()" ng-if="btnSetOutToIn"><s:message code="rtnInstockConfm.dtl.setOutToIn"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 280px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter"
            frozen-columns="6"
            ime-enabled="true">	<%-- allowMerging		="Cells" --%>

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.slipNo"/>" 	  binding="slipNo" 		width="0" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.slipFg"/>" 	  binding="slipFg" 		width="0" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.seq"/>" 		  binding="seq" 		width="0" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.storeCd"/>" 	  binding="storeCd" 	width="0" 	align="center" 	is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.prodCd"/>" 	  binding="prodCd" 		width="100" align="center" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.prodNm"/>" 	  binding="prodNm" 		width="100" align="left" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.barcdCd"/>" 	  binding="barcdCd" 	width="100" align="left" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.poUnitFg"/>" 	  binding="poUnitFg" 	width="60" 	align="center" 	is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.poUnitQty"/>"   binding="poUnitQty" 	width="60" 	align="right" 	is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outSplyUprc"/>" binding="outSplyUprc" width="60" 	align="right" 	is-read-only="true" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"  	/>"   binding="outUnitQty"  width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>"   binding="outEtcQty"   width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"   	/>"   binding="inUnitQty"   width="70"  align="right"   is-read-only="false"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>"   binding="inEtcQty"    width="70"  align="right"   is-read-only="false"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inTotQty"    />"   binding="inTotQty"    width="0"   align="right"   is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inAmt"       />"   binding="inAmt"       width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inVat"       />"   binding="inVat"       width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inTot"       />"   binding="inTot"       width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.remark"      />"   binding="remark"      width="100" align="left"    is-read-only="false"                                                    max-length=200  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.vatFg"       />"   binding="vatFg01"     width="70"  align="right"   is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            
            
            
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outUnitQty"/>" binding="outUnitQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outEtcQty"/>" binding="outEtcQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inUnitQty"/>" binding="inUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inEtcQty"/>" binding="inEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inTotQty"/>" binding="inTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.penaltyAmt"/>" binding="penaltyAmt" width="70" align="right" is-read-only="false" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column> --%>
<%--             <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column> --%>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  // [1241 창고사용여부] 환경설정값
  var storageEnvstVal = "${storageEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl.js?ver=20220714.03" charset="utf-8"></script>
