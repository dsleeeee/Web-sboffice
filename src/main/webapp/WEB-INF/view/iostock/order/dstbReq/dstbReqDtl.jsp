<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbReq/dstbReqDtl/"/>

<wj-popup id="wjDstbReqDtlLayer" control="wjDstbReqDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
  <div id="dstbReqDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbReqDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
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
          <th><s:message code="dstbReq.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="tr mt20">
        <p id="dtlAvailableOrderAmt" class="fl s14 bk lh30"></p>
        <div id="dstbBtnLayer" ng-if="dstbBtnLayer">
          <span id="spanDstbConfirmFg" class="chk pdb5 txtIn" style="top: 0px;"><input type="checkbox" name="dstbConfirmFg" id="dstbConfirmFg" value="Y"/>
              <label for="dstbConfirmFg"><s:message code="dstbReq.dtl.dstbConfirmFg"/></label>
          </span>
          <%-- 공급가 및 수량적용 --%>
          <button type="button" id="btnDtlApply" class="btn_skyblue ml5" ng-click="setQtyApply()"><s:message code="dstbReq.dtl.qtyApply"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveValueCheck()"><s:message code="cmm.save"/></button>
          <%-- 엑셀 다운로드 --%>
          <button class="btn_skyblue ml5" ng-click="excelDownload()"><s:message code="cmm.excel.down" /></button>
          <div class="tooltip fl">?
           <span class="tooltiptext tooltip-right"">
             * <s:message code="dstbReq.dtl.txt1"/><br/>
             * <s:message code="dstbReq.dtl.txt2"/><br/>
           </span>
          </div>
        </div>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 450px; overflow-x: hidden; overflow-y: hidden;">
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
        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"               	binding="gChk"         	width="40"  align="center"></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.storeCd"/>"       binding="storeCd"       width="0"   align="center"  is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prodCd"/>"        binding="prodCd"        width="100" align="center"  is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prodNm"/>"        binding="prodNm"        width="150" align="left"    is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.poUnitFg"/>"      binding="poUnitFg"      width="65"  align="center"  is-read-only="true"     data-map="poUnitFgMap"                                          ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.poUnitQty"/>"     binding="poUnitQty"     width="50"  align="right"   is-read-only="true"                                                                     ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.splyUprc"/>"      binding="splyUprc"      width="75"  align="right"   is-read-only="true"     data-type="Number" format="n0"                                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderUnitQty"/>"  binding="orderUnitQty"  width="45"  align="right"   is-read-only="true"     data-type="Number" format="n0"                  aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderEtcQty"/>"   binding="orderEtcQty"   width="45"  align="right"   is-read-only="true"     data-type="Number" format="n0"                  aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderTotQty"/>"   binding="orderTotQty"   width="45"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdSplyUprc"/>"    binding="mdSplyUprc"    width="60"  align="right"   is-read-only="false"    data-type="Number" format="n0"  max-length=10                   ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdUnitQty"/>"     binding="mdUnitQty"     width="45"  align="right"   is-read-only="false"    data-type="Number" format="n0"  max-length=8    aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdEtcQty"/>"      binding="mdEtcQty"      width="45"  align="right"   is-read-only="false"    data-type="Number" format="n0"  max-length=8    aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdTotQty"/>"      binding="mdTotQty"      width="45"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prevMdTotQty"/>"  binding="prevMdTotQty"  width="60"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdAmt"/>"         binding="mdAmt"         width="60"  align="right"   is-read-only="true"     data-type="Number" format="n0"                  aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdVat"/>"         binding="mdVat"         width="60"  align="right"   is-read-only="true"     data-type="Number" format="n0"                  aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdTot"/>"         binding="mdTot"         width="60"  align="right"   is-read-only="true"     data-type="Number" format="n0"                  aggregate="Sum" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.remark"/>"        binding="remark"        width="150" align="left"    is-read-only="false"                                    max-length=300                  ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.vatFg"/>"         binding="vatFg01"       width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbReq.dtl.envst0011"/>"     binding="envst0011"     width="70"  align="right"   is-read-only="true"                                                     visible="false" ></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/order/dstbReq/dstbReqDtl.js?ver=20220722.01" charset="utf-8"></script>
