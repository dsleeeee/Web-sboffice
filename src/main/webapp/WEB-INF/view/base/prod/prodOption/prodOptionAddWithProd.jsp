<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="prodOptionAddWithProdLayer" control="prodOptionAddWithProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
    <div class="wj-dialog wj-dialog-columns" ng-controller="prodOptionAddWithProdCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prodOption.addWithProd"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                    <tr>
                       <%--상품코드--%>
                       <th><s:message code="prodOption.prodCd"/></th>
                       <td><input type="text" id="srchProdCd" ng-model="prodCd"/></td>
                       <%--상품명--%>
                       <th><s:message code="prodOption.prodNm"/></th>
                       <td><input type="text" id="srchProdNm" ng-model="prodNm"/></td>
                    </tr>
                    <tr>
                        <%--상품분류--%>
                        <th><s:message code="prodOption.prodClassNm"/></th>
                        <td>
                            <input type="text" class="sb-input w50" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;" placeholder="<s:message code="cmm.all"/>" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
                        </td>
                        <td>
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                        </td>
                    </tr>
                    <tr id="trProdHqBrand" style="display: none;">
                        <%--상품브랜드--%>
                        <th><s:message code="prodOption.prodHqBrand" /></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                  id="srchProdHqBrandCd"
                                  ng-model="prodHqBrandCd"
                                  items-source="_getComboData('srchProdHqBrandCd')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  control="srchProdHqBrandCdCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                </tbody>
            </table>

            <div class="mt10 tr">
                <div class="oh sb-select dkbr">
                    <%-- 페이지 스케일  --%>
                    <wj-combo-box
                        class="w100px fl"
                        id="listScaleBox"
                        ng-model="listScale"
                        control="listScaleCombo"
                        items-source="_getComboData('listScaleBox')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        initialized="_initComboBox(s)">
                    </wj-combo-box>
                    <button class="btn_skyblue fr" id="btnSearch" ng-click="_pageView('prodOptionAddWithProdCtrl',1)" ><s:message code="cmm.search" /></button>
                </div>
            </div>

            <div class="oh mt10">
                <%--- 상품 그리드 --%>
                <div class="w100 fr">
                    <div class="h365">
                        <wj-flex-grid
                            autoGenerateColumns="false"
                            control="flex"
                            initialized="initGrid(s,e)"
                            sticky-headers="true"
                            selection-mode="Row"
                            items-source="data"
                            item-formatter="_itemFormatter">

                            <!-- define columns -->
                            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodOption.prodClassNm"/>" binding="prodClassNm" width="200" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodOption.prodCd"/>" binding="prodCd" width="*" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="prodOption.prodNm"/>" binding="prodNm" width="300" is-read-only="true"></wj-flex-grid-column>
                        </wj-flex-grid>
                    </div>
                </div>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum2 mt20">
                <%-- id --%>
                <ul id="prodOptionAddWithProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>

            <div class="wj-dialog-footer">
                <%--상품선택 버튼--%>
                <button class="btn {{itemChecked ? 'wj-hide-apply' : ''}} btn_blue" ng-click="selProdConfirm();"><s:message code="prodOption.selProd"/></button>
                <%--닫기 버튼--%>
                <button class="btn wj-hide btn_gray"><s:message code="cmm.close"/></button>
            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodOption/prodOptionAddWithProd.js?ver=20230215.01" charset="utf-8"></script>
