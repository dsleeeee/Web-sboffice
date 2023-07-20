<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

 <wj-popup id="saleAmtFgRemark3PopupLayer" control="saleAmtFgRemark3PopupLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dayProd.saleAmtFg"/>3
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">
            <div ng-controller="saleAmtFgRemarkPopupCtrl3">
                선택된 구성상품은 세트상품으로 집계
                <button class="btn_skyblue fr mb5" ng-click="getSaleAmtFgRemarkList3()">
                    <s:message code="cmm.search" />
                </button>
                <button class="btn_skyblue fr mr5 mb5" ng-click="add()">
                    <s:message code="cmm.add" />
                </button>
                <table class="tblType01" >
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="saleRegist.prodClass"/></th>
                        <td>
                            <input type="text" class="sb-input w70" id="srchProdClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;"
                                   placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                        </td>
                        <th><s:message code="dayProd.brand"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchProdHqBrandCombo"
                                        ng-model="prodHqBrandCd"
                                        items-source="_getComboData('prodHqBrandCdCombo')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        control="srchProdHqBrandCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th><s:message code="saleRegist.prodCd"/></th>
                        <td><input type="text" id="srchProdCd" ng-model="prodCd" /></td>
                        <th><s:message code="saleRegist.prodNm"/></th>
                        <td><input type="text" id="srchProdNm" ng-model="prodNm" /></td>
                    </tr>
                    <tr>
                        <th><s:message code="prodInfoSearch.prodInfo.sdselProdCd"/></th>
                        <td><input type="text" id="srchSdselProdCd" ng-model="sdselProdCd" /></td>
                        <th><s:message code="prodInfoSearch.prodInfo.sdselProdNm"/></th>
                        <td><input type="text" id="srchSdselProdNm" ng-model="sdselProdNm" /></td>
                    </tr>
                    </tbody>
                </table>

                <%-- 그리드 --%>
                <div class="wj-gridWrap" style="height:200px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            item-formatter="_itemFormatter"
                            is-read-only="false"
                            frozen-columns="1">
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.hqBrandNm"/>" binding="hqBrandNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.requireYn"/>" binding="requireYn" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselProdCd"/>" binding="sdselProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselRemark"/>" binding="sdselRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselProdNm"/>" binding="sdselProdNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.addProdUprc"/>" binding="addProdUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.fixProdFg"/>" binding="fixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>

            <div ng-controller="sdselMomsModPopupCtrl">
                <div class="updownSet oh mb10 pd5">
                    <button class="btn_skyblue fr" ng-click="delete()">
                        <s:message code="cmm.del" />
                    </button>
                </div>
                <%-- 그리드 --%>
                <div class="wj-gridWrap" style="height:200px; overflow-y: hidden; overflow-x: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            item-formatter="_itemFormatter"
                            is-read-only="false"
                            frozen-columns="1">
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.hqBrandNm"/>" binding="hqBrandNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.lClassNm"/>" binding="lClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.mClassNm"/>" binding="mClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sClassNm"/>" binding="sClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.remark"/>" binding="remark" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.prodNm"/>" binding="prodNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselGrpNm"/>" binding="sdselGrpNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselClassNm"/>" binding="sdselClassNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.sideMenu.requireYn"/>" binding="requireYn" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselProdCd"/>" binding="sdselProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselRemark"/>" binding="sdselRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.sdselProdNm"/>" binding="sdselProdNm" width="100" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.addProdUprc"/>" binding="addProdUprc" width="100" align="right" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prodInfoSearch.prodInfo.fixProdFg"/>" binding="fixProdFg" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/sale/prod/dayProd/saleAmtFgRemark3.js?ver=20230718.01" charset="utf-8"></script>

<%--<c:import url="/WEB-INF/view/application/layer/searchProdClassCd2.jsp">--%>
<%--</c:import>--%>