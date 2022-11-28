<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="newRegistLayer" control="newRegistLayer" show-trigger="Click" hide-trigger="Click" style="overflow-x:auto; overflow-y: auto;display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleRegistKwu.newRegist"/>

            <a id="newRegistInfo" class="ml20">
                <span class="mr10">영업일자 : <span id="saleDate"></span></span>
                <span class="mr10">매장 : <span id="storeNm"></span><span id="storeCd" style="display: none"></span></span>
                <span class="mr10">포스번호 : <span id="posNo"></span></span>
                <span class="mr10">영수증번호 : <span id="billNo"></span></span>
            </a>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body" >
            <%-- body_위 --%>
            <div ng-controller="newRegistCtrl">
                <table class="searchTbl">
                    <colgroup>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                    </colgroup>
                    <tbody>
                    <tr class="brt">
                        <th>현금</th>
                        <td><input type="text" class="sb-input w100" id="cash" ng-model="cash" ng-change="changeCashAmt()" numberOnly/></td>
                        <th>신용카드</th>
                        <td><input type="text" class="sb-input w100" id="card" ng-model="card" ng-change="changeCardAmt()" numberOnly/></td>
                        <td colspan="4">
                            <span class="s12 fl lh15 bk mt5">상품을 삭제하시려면 수량에 0을 입력하세요</span>
                            <button class="btn_skyblue fr" id="btnSave" ng-click="billChk()" ><s:message code="cmm.save" /></button>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="saleRegistKwu.membr"/></th>
                        <td>
                            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                            <jsp:include page="/WEB-INF/view/excclc/excclc/saleRegistKwu/searchMembrSKwu.jsp" flush="true">
                                <jsp:param name="targetId" value="membr"/>
                            </jsp:include>
                            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                        </td>
                        <th><s:message code="saleRegistKwu.saleFg"/>
                        </th>
                        <td>
                            <div class="sb-select w100">
                                <span class="txtIn">
                                    <wj-combo-box
                                            id="saleFg"
                                            ng-model="saleFg"
                                            items-source="_getComboData('saleFg')"
                                            display-member-path="name"
                                            selected-value-path="value"
                                            is-editable="false"
                                            initialized="_initComboBox(s)"
                                            control="saleFgCdCombo">
                                    </wj-combo-box>
                                </span>
                            </div>
                        </td>
                        <th><s:message code="saleRegistKwu.depositAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="depositAmt" ng-model="depositAmt" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.postpaidAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="postpaidAmt" ng-model="postpaidAmt" numberOnly/></td>
                    </tr>
                    <tr>
                        <th><s:message code="saleRegistKwu.businessAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="businessAmt" ng-model="businessAmt" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.teacherAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="teacherAmt" ng-model="teacherAmt" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.teacherCnt"/></th>
                        <td><input type="text" class="sb-input w100" id="teacherCnt" ng-model="teacherCnt" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.transportAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="transportAmt" ng-model="transportAmt" numberOnly/></td>
                    </tr>
                    <tr>
                        <th><s:message code="saleRegistKwu.qty"/></th>
                        <td><input type="text" class="sb-input w100" id="qty" ng-model="qty" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.remainAmt"/></th>
                        <td><input type="text" class="sb-input w100" id="remainAmt" ng-model="remainAmt" numberOnly/></td>
                        <th><s:message code="saleRegistKwu.remark"/></th>
                        <td colspan="3">
                            <input type="text" class="sb-input w100" id="remark" ng-model="remark"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%--- 적용상품 그리드 --%>
                <div class="oh mt5">
                    <div class="w100 fl">
                        <div id="newRegistGrid" class="wj-gridWrap" style="height: 200px; overflow-y: hidden;">
                            <wj-flex-grid
                                    autoGenerateColumns="false"
                                    control="flex"
                                    initialized="initGrid(s,e)"
                                    sticky-headers="true"
                                    selection-mode="Row"
                                    items-source="data"
                                    item-formatter="_itemFormatter"
                                    ime-enabled="true">

                                <!-- define columns -->
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodCd"/>"     binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodNm"/>"     binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.saleUprc"/>"   binding="saleUprc" width="65" align="right" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.saleQty"/>"    binding="saleQty" width="65" align="center" max-length="4"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.saleAmt"/>"    binding="saleAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.dcAmt"/>"      binding="dcAmt" width="65" align="right" max-length="8" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.realSaleAmt"/>" binding="realSaleAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.vatAmt"/>"     binding="vatAmt" width="65" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.vatFg"/>"      binding="vatFg" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.vatFg"/>"      binding="prodVatFg" width="80" data-map="vatFgDataMap" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.saleFg"/>"     binding="prodSaleFg" width="80" data-map="saleFgDataMap" align="center"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodTypeFg"/>" binding="prodTypeFg" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>
                </div>
            </div>

            <%-- body_아래 --%>
            <div ng-controller="selectProdCtrl">
                <%-- 조회조건 --%>
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"><s:message code="saleRegistKwu.selectProd"/></a>
                    <%-- 조회 --%>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button class="btn_blue fr" ng-click="_pageView('selectProdCtrl',1)">
                            <s:message code="cmm.search" />
                        </button>
                    </div>
                </div>
                <table class="searchTbl" >
                    <colgroup>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                        <col class="w8"/>
                        <col class="w17"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="saleRegistKwu.prodCd"/></th>
                        <td><input type="text" class="sb-input w100" id="srchProdCd" ng-model="prodCd" /></td>
                        <th><s:message code="saleRegistKwu.prodNm"/></th>
                        <td><input type="text" class="sb-input w100" id="srchProdNm" ng-model="prodNm" /></td>
                        <th><s:message code="saleRegistKwu.prodClass"/></th>
                        <td>
                            <input type="text" class="sb-input w50" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                                   placeholder="<s:message code="prodBatchChange.srchClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                        </td>
                        <th><s:message code="saleRegistKwu.barCd"/></th>
                        <td><input type="text" class="sb-input w100" id="srchBarCd" ng-model="barCd" /></td>
                    </tr>
                    </tbody>
                </table>
                <%--- 적용상품 그리드 --%>
                <div class="oh mt5">
                    <div class="w100 fl">
                            <div id="regProdGrid" class="wj-gridWrap" style="height: 150px; overflow-y: hidden;">
                                <wj-flex-grid
                                        autoGenerateColumns="false"
                                        control="flex"
                                        initialized="initGrid(s,e)"
                                        sticky-headers="true"
                                        selection-mode="Row"
                                        items-source="data"
                                        item-formatter="_itemFormatter"
                                        ime-enabled="true"
                                        is-read-only="true">

                                    <!-- define columns -->
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.pathNm"/>"     binding="pathNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodCd"/>"     binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodNm"/>"     binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.barCd"/>"      binding="barCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.prodTypeFg"/>" binding="prodTypeFg" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.costUprc"/>"   binding="costUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.splyUprc"/>"   binding="splyUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.saleUprc"/>"   binding="saleUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegistKwu.vatFg"/>"      binding="vatFg" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript">
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/saleRegistKwu/newRegist.js?ver=20221123.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- //팝업 레이어 --%>
