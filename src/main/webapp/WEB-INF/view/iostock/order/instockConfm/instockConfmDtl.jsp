<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/instockConfm/instockConfmDtl/"/>

<wj-popup id="wjInstockConfmDtlLayer" control="wjInstockConfmDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="instockConfmDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="instockConfmDtlCtrl">
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
          <th><s:message code="instockConfm.dtl.hdRemark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="200"/>
          </td>
        </tr>
        <tr style="display: none;">
          <th><s:message code="instockConfm.dtl.dlvrNm"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
              					id					="srchDtlDlvrCd"
								ng-model			="dlvrCd"
								items-source		="_getComboData('srchDtlDlvrCd')"
								display-member-path	="name"
								selected-value-path	="value"
								is-editable			="false"
								initialized			="_initComboBox(s)">
              </wj-combo-box>
            </span>
          </td>
        </tr>
        <tr>
          <%-- 거래명세표 --%>
          <th><s:message code="instockConfm.dtl.stmtAcct"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
				                id					="stmtAcctFg"
				                ng-model			="stmtAcctFg"
				                items-source		="_getComboData('stmtAcctFg')"
				                display-member-path	="name"
				                selected-value-path	="value"
				                is-editable			="false"
				                initialized			="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <a href="#" class="btn_grayS" ng-click="reportTrans()"><s:message code="instockConfm.dtl.stmtAcctPrint"/></a>
            <%--<a href="#" class="btn_grayS" ng-click=""><s:message code="instockConfm.dtl.stmtAcctExcel"/></a>--%>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="instockConfm.dtl.txt1"/></li>
        <li class="red"><s:message code="instockConfm.dtl.txt2"/></li>
        <li class="red"><s:message code="instockConfm.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="instockBtnLayer" style="display: none;">
			<%--입고창고 --%>
          	<p class="s14 bk fl mr5 lh30"><s:message code="instockConfm.dtl.outStorage"/></p>
          	<span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="saveDtlInStorageCd"
                ng-model="save.dtl.inStorageCd"
                items-source="_getComboData('saveDtlInStorageCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s)"
                >
              </wj-combo-box>
            </span>        	
        
          <%-- 입고확정 체크박스 --%>
          <span id="spanInstockConfirmFg" class="chk pdb5 txtIn fl" style="top: 0px;" ng-if="spanInstockConfirmFg">
            <input type="checkbox" name="instockConfirmFg" id="instockConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="instockConfirmFg"><s:message code="instockConfm.dtl.confirmFg"/></label>
          </span>
          <%-- 입고일자 --%>
          <div id="divDtlInDate" class="sb-select ml10 fl" style="display: none;">
            <span class="txtIn"><input id="dtlInDate" class="w120px"></span>
          </div>
          <%-- 출고내역으로 입고내역 세팅 --%>
          <button type="button" id="btnSetOutToIn" class="btn_skyblue ml5 fl" ng-click="setOutToIn()" ng-if="btnSetOutToIn"><s:message code="instockConfm.dtl.setOutToIn"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
		  <%-- 엑셀 다운로드 --%>
		  <button type="button"                 class="btn_skyblue ml5 fl" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 280px; overflow-x: hidden; overflow-y: hidden;">	<%-- 400px -> 280px --%>
          <wj-flex-grid
            autoGenerateColumns	="false"
            selection-mode		="Row"
            items-source		="data"
            control				="flex"
            initialized			="initGrid(s,e)"
            is-read-only		="false"
            item-formatter		="_itemFormatter"
            frozen-columns		="7"
            ime-enabled="true">	<%-- allowMerging		="Cells" --%>

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.slipNo"      />" binding="slipNo"        width="0"   align="center"  is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.slipFg"      />" binding="slipFg"        width="0"   align="center"  is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.slipFg"		/>"	binding="slipKind" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.seq"         />" binding="seq"           width="0"   align="center"  is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.storeCd"     />" binding="storeCd"       width="0"   align="center"  is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.prodCd"      />" binding="prodCd"        width="100" align="center"  is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.prodNm"      />" binding="prodNm"        width="100" align="left"    is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.barcdCd"     />" binding="barcdCd"       width="100" align="left"    is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.poUnitFg"    />" binding="poUnitFg"      width="60"  align="center"  is-read-only="true"     data-map="poUnitFgMap"											></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.poUnitQty"   />" binding="poUnitQty"     width="60"  align="right"   is-read-only="true"                                     aggregate="Sum"                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.outSplyUprc" />" binding="outSplyUprc"   width="60"  align="right"   is-read-only="true"     data-type="Number" format="n0"	aggregate="Sum" max-length=10   ></wj-flex-grid-column>

        	<%--
			instockConfm.dtl.outUnitQty=출고수량
			instockConfm.dtl.outEtcQty=	출고수량

			instockConfm.dtl.inUnitQty=	입고수량
			instockConfm.dtl.inEtcQty=	입고수량

			instockConfm.dtl.unitQty=주문단위
			instockConfm.dtl.etcQty=나머지

			<wj-flex-grid-column header="<s:message code="instockConfm.dtl.outUnitQty"  />" binding="outUnitQty"    width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.outUnitQty"	/>" binding="outEtcQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inUnitQty"   />" binding="inUnitQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inUnitQty"	/>" binding="inEtcQty"      width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>

			<wj-flex-grid-column header="" 													binding="outUnitQty"    width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="" 													binding="outEtcQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="" 													binding="inUnitQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="" 													binding="inEtcQty"      width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>

			<wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"  	/>" binding="outUnitQty"    width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>" binding="outEtcQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"   	/>" binding="inUnitQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>" binding="inEtcQty"      width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>

        	--%>
			<wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"  	/>" binding="outUnitQty"    width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>" binding="outEtcQty"     width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.unitQty"   	/>" binding="inUnitQty"     width="70"  align="right"   is-read-only="false"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.etcQty"		/>" binding="inEtcQty"      width="70"  align="right"   is-read-only="false"     data-type="Number" format="n0" aggregate="Sum"  max-length=8    ></wj-flex-grid-column>

            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inTotQty"    />" binding="inTotQty"      width="0"   align="right"   is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inAmt"       />" binding="inAmt"         width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inVat"       />" binding="inVat"         width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.inTot"       />" binding="inTot"         width="70"  align="right"   is-read-only="true"     data-type="Number" format="n0" aggregate="Sum"                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.remark"      />" binding="remark"        width="100" align="left"    is-read-only="false"                                                    max-length=200  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="instockConfm.dtl.vatFg"       />" binding="vatFg01"       width="70"  align="right"   is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
<%--
			<wj-flex-grid-column header=""                                                  binding="arrStorageCd"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrStorageNm"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  binding="arrInUnitQty"  width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInEtcQty"   width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInTotQty"   width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>

			<wj-flex-grid-column header=""                                                  binding="arrInAmt"      width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInVat"      width="200" align="left"   	is-read-only="true"     visible="false"                                                 ></wj-flex-grid-column>
			<wj-flex-grid-column header=""                                                  binding="arrInTot"      width="200" align="left"   	is-read-only="true"     visible="false"                                                	></wj-flex-grid-column>
 --%>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/instockConfm/instockConfmDtl.js?ver=20200804.01" charset="utf-8"></script>
