<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<wj-popup id="wjInOutStockProdRegLayer" control="wjInOutStockProdRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:750px;">
    <div id="inOutStockProdRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="inOutStockProdRegCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="acquireSlipRegist.reg.title"/>
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
                    <%-- 상품코드 --%>
                    <th><s:message code="acquireSlipRegist.reg.prodCd"/></th>
                    <td>
                        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
                    </td>
                    <%-- 상품명 --%>
                    <th><s:message code="acquireSlipRegist.reg.prodNm"/></th>
                    <td>
                        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
                    </td>
                </tr>
                <tr>
                    <%-- 바코드 --%>
                    <th><s:message code="acquireSlipRegist.reg.barcd"/></th>
                    <td>
                        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
                    </td>
                    <%-- 상품분류 --%>
                    <th><s:message code="acquireSlipRegist.reg.prodClass"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                               placeholder="<s:message code="cmm.all" />" readonly/>
                        <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
                    </td>
                </tr>
<%--                <tr>--%>
<%--                    &lt;%&ndash; 매입구분 &ndash;%&gt;--%>
<%--                    <th>--%>
<%--                        <s:message code="acquireSlipRegist.dtl.tradeFg"/>--%>
<%--                    </th>--%>
<%--                    <td>--%>
<%--                        <div class="sb-select fl">--%>
<%--                        <span class="txtIn w120px">--%>
<%--                            <wj-combo-box--%>
<%--                                    id="prodRegTradeFg"--%>
<%--                                    ng-model="tradeFg"--%>
<%--                                    items-source="_getComboData('prodRegTradeFgComboData')"--%>
<%--                                    display-member-path="name"--%>
<%--                                    selected-value-path="value"--%>
<%--                                    is-editable="false"--%>
<%--                                    initialized="_initComboBox(s)"--%>
<%--                                    selected-index-changed="selectedIndexChanged(s, e)">--%>
<%--                            </wj-combo-box>--%>
<%--                        </span>--%>
<%--                        </div>--%>
<%--                    </td>--%>
<%--                        &lt;%&ndash; 구분 &ndash;%&gt;--%>
<%--                        <th>--%>
<%--                            <s:message code="acquireSlipRegist.dtl.tradeForm"/>--%>
<%--                        </th>--%>
<%--                        <td>--%>
<%--                            <div class="sb-select fl">--%>
<%--                        <span class="txtIn w120px">--%>
<%--                            <wj-combo-box--%>
<%--                                    id="prodRegTradeForm"--%>
<%--                                    ng-model="tradeForm"--%>
<%--                                    items-source="_getComboData('prodRegTradeFormComboData')"--%>
<%--                                    display-member-path="name"--%>
<%--                                    selected-value-path="value"--%>
<%--                                    is-editable="false"--%>
<%--                                    initialized="_initComboBox(s)"--%>
<%--                                    selected-index-changed="selectedIndexChanged(s, e)">--%>
<%--                            </wj-combo-box>--%>
<%--                        </span>--%>
<%--                            </div>--%>
<%--                        </td>--%>
<%--                </tr>--%>
                <tr>
                    <td colspan="4">
                        <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="acquireSlipRegist.reg.excelFormDownload"/></a>
                        <span class="txtIn w120px" style="border:1px solid #e8e8e8;">
              <wj-combo-box
                      id="addQtyFg"
                      ng-model="addQtyFg"
                      items-source="_getComboData('addQtyFg')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
                        <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="acquireSlipRegist.reg.excelFormUpload"/></a>
                        <%--             <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="acquireSlipRegist.reg.textFormUpload"/></a> --%>
                        <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
                        <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="acquireSlipRegist.reg.excelFormUploadErrorInfo"/></a>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh">
                <%-- 조회 --%>
                <button type="button" class="btn_blue fr" id="btnSearch" ng-click="fnSearch();">
                    <s:message code="cmm.search"/></button>
            </div>

            <div class="mt20 tr">
                <ul class="txtSty3">
                    <li class="red fl"><s:message code="acquireSlipRegist.reg.txt1"/></li>
                    <li class="red fl"><s:message code="acquireSlipRegist.reg.txt2"/></li>
                </ul>
                <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <span class="chk pdb5 txtIn" style="top: 0px; visibility: hidden">
          <input type="checkbox" name="storeSplyChk" id="storeSplyChk" ng-model="storeSplyChk"/>
          <label for="storeSplyChk"><s:message code="acquireSlipRegist.reg.storeSplyUprcCheck"/></label>
        </span>
                </c:if>
                <%--출고창고
                   <p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.dtl.outStorage"/></p>
                   <span class="txtIn w150px sb-select fl mr5">
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
                 --%>
                <%-- 최종원가를 발주원가로 세팅 --%>
                <button type="button" class="btn_skyblue ml5" id="btnLastCostToCostUprc" ng-click="setLastCostToCostUprc()">
                    <s:message code="acquireSlipRegist.reg.lastCostToCostUprc"/></button>
                <%-- 저장 --%>
                <button type="button" class="btn_skyblue ml5" id="btnProdRegSave" ng-click="save()">
                    <s:message code="cmm.save"/></button>
            </div>

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
                            frozen-columns="2"
                            ime-enabled="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.prodCd"/>" 		    binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.prodNm"/>" 		    binding="prodNm" width="120" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.lastCostUprc"/>"    binding="lastCostUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.poUnitFg"/>" 	    binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.poUnitQty"/>" 	    binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.unitQty"/>"		    binding="prevInUnitQty" width="60" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.etcQty"/>"		    binding="prevInEtcQty" width="60" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.acquireUprc"/>" 	binding="costUprc" width="80" align="right" is-read-only="false" max-length=8></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.unitQty"/>" 		binding="inUnitQty" width="60" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.etcQty"/>" 		    binding="inEtcQty" width="60" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.prevInTotQty"/>"    binding="prevInTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.inTotQty"/>" 	    binding="inTotQty" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.inAmt"/>" 		    binding="inAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.splyUprc"/>" 		binding="splyUprc" width="70" align="right" is-read-only="true" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.saleUprc"/>" 		binding="saleUprc" width="70" align="right" is-read-only="true" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.acquireRate"/>" 	binding="acquireRate" width="70" align="right" is-read-only="true" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.inVat"/>" 		    binding="inVat" width="70" align="right" is-read-only="true" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.inTot"/>" 		    binding="inTot" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.vatFg"/>" 		    binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.vendrVatFg01"/>"    binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.storeSplyUprc"/>"   binding="splyUprc" width="70" align="right" is-read-only="false" visible="{{storeSplyChk}}" max-length=8></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="acquireSlipRegist.reg.seq"/>"             binding="seq" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
                <%-- 페이지 리스트 --%>
                <div class="pageNum mt20">
                    <%-- id --%>
                    <ul id="inOutStockProdRegCtrlPager" data-size="10">
                    </ul>
                </div>
                <%--//페이지 리스트--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/acquireSlipRegist/inOutStockProdReg.js?ver=20251125.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 공통팝업 수불/재고 엑셀업로드 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUploadMPS/excelUploadMPS.jsp">
</c:import>
