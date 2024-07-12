<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="wjRtnInstockConfmStoreDtlLayer" control="wjRtnInstockConfmStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px; height: 750px;">

    <div id="rtnInstockConfmStoreDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnInstockConfmStoreDtlCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblTitle"></label>
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
                    <th><s:message code="rtnInstockConfmStore.dtl.remark"/></th>
                    <td colspan="3">
                        <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="300"/>
                    </td>
                </tr>
                <tr style="display: none;">
                    <th><s:message code="rtnInstockConfmStore.dtl.dlvrNm"/></th>
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
                    <th><s:message code="rtnInstockConfmStore.dtl.stmtAcct"/></th>
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
                        <a href="#" class="btn_grayS" ng-click="reportTrans()"><s:message code="rtnInstockConfmStore.dtl.stmtAcctPrint"/></a>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="tooltipBtn mt20 fl">설명
                <span class="tooltiptext tooltip-right">
                * <s:message code="rtnInstockConfmStore.dtl.txt1"/><br/>
                * <s:message code="rtnInstockConfmStore.dtl.txt2"/><br/>
                * <s:message code="rtnInstockConfmStore.dtl.txt3"/><br/>
                </span>
            </div>

            <div style="clear: both;"></div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 450px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter"
                            frozen-columns="6"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.slipNo"/>" binding="slipNo" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.slipFg"/>" binding="slipFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.barcdCd"/>" binding="barcdCd" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.outSplyUprc"/>" binding="outSplyUprc" width="60" align="right" is-read-only="true" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.unitQty"/>" binding="outUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum" max-length=8></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.etcQty"/>" binding="outEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum" max-length=8></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.unitQty"/>" binding="inUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum" max-length=8></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.etcQty"/>" binding="inEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum" max-length=8></wj-flex-grid-column>

                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.inTotQty"/>" binding="inTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.inAmt"/>" binding="inAmt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.inVat"/>" binding="inVat" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.inTot"/>" binding="inTot" width="60" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.remark"/>" binding="remark" width="150" align="left" is-read-only="true" max-length=200></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="rtnInstockConfmStore.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/orderReturn/rtnInstockConfmStore/rtnInstockConfmStoreDtl.js?ver=20240711.01" charset="utf-8"></script>