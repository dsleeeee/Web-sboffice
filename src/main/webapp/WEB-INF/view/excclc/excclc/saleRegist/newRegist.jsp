<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="newRegistLayer" control="newRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleRegist.newRegist"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body" >
            <%-- body_위 --%>
            <div ng-controller="selectProdCtrl">
                <%-- 조회조건 --%>
                <div class="searchBar flddUnfld">
                    <a href="#" class="open fl"><s:message code="saleRegist.selectProd"/></a>
                    <%-- 조회 --%>
                    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
                        <button class="btn_blue fr" ng-click="_pageView('selectProdCtrl',1)">
                            <s:message code="cmm.search" />
                        </button>
                    </div>
                </div>
                <table class="tblType01" >
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="saleRegist.prodCd"/></th>
                        <td><input type="text" id="srchProdCd" ng-model="prodCd" /></td>
                        <th><s:message code="saleRegist.prodNm"/></th>
                        <td><input type="text" id="srchProdNm" ng-model="prodNm" /></td>
                    </tr>
                    <tr>
                        <th><s:message code="saleRegist.prodClass"/></th>
                        <td>
                            <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                                   placeholder="<s:message code="prodBatchChange.srchClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                        </td>
                        <th><s:message code="saleRegist.barCd"/></th>
                        <td><input type="text" id="srchBarcdCd" ng-model="barcdCd" /></td>
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
                                    <wj-flex-grid-column header="<s:message code="saleRegist.pathNm"/>"     binding="pathNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.prodCd"/>"     binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.prodNm"/>"     binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.barCd"/>"      binding="barCd" width="80" align="left" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.costUprc"/>"   binding="costUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.splyUprc"/>"   binding="splyUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.saleUprc"/>"   binding="saleUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="saleRegist.vatFg"/>"      binding="vatFg" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>
                    </div>
                </div>

            <%-- body_아래 --%>
            <div ng-controller="newRegistCtrl" class="oh mt5">
            <%-- 조회조건 --%>
                <div class="searchBar flddUnfld">
                    <a id="newRegistInfo" href="#" class="open fl">
                        <span class="mr10">영업일자 : <span id="saleDate"></span></span>
                        <span class="mr10">매장 : <span id="storeNm"></span><span id="storeCd" style="display: none"></span></span>
                        <span class="mr10">포스번호 : <span id="posNo"></span></span>
                        <span class="mr10">영수증번호 : <span id="billNo"></span></span>
                    </a>
                </div>

                <table class="searchTbl">
                    <colgroup>
                        <col class="w40" />
                        <col class="w60" />
                    </colgroup>
                    <tbody>
                    <th>
                        상품을 삭제하시려면 수량에 0을 입력하세요
                    </th>
                    <th>
                        <button class="btn_skyblue fr" id="btnSave" ng-click="billChk()" ><s:message code="cmm.save" /></button>
                        <div class="sb-select w110px fr">
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
                        <div class="fr">
                            현금 <input type="text" class="sb-input w100px" id="cash" ng-model="cash" ng-change="changeCashAmt()" numberOnly/>
                            신용카드 <input type="text" class="sb-input w100px" id="card" ng-model="card" ng-change="changeCardAmt()" numberOnly/>
                        </div>
                    </th>
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
                                <wj-flex-grid-column header="<s:message code="saleRegist.prodCd"/>"     binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.prodNm"/>"     binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.saleUprc"/>"   binding="saleUprc" align="right" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.saleQty"/>"    binding="saleQty" width="80" align="center" max-length="4"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.saleAmt"/>"    binding="saleAmt" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.dcAmt"/>"      binding="dcAmt" align="right" aggregate="Sum" max-length="8"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.realSaleAmt"/>" binding="realSaleAmt" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.vatAmt"/>"     binding="vatAmt" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.vatFg"/>"      binding="vatFg" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="saleRegist.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
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

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/saleRegist/newRegist.js?ver=20221206.01" charset="utf-8"></script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
<%-- //팝업 레이어 --%>
