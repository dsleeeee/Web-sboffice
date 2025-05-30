<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="couponExceptProdLayer" control="couponExceptProdLayer" show-trigger="Click" hide-trigger="Click" style="width:1200px;">

    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="coupon.regist.exceptProdCnt" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">

            <div  ng-controller="regExceptProdCtrl">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <label for="payClasssCd"><input id="payClasssCd"></label>
                    <tbody>
                    <tr>
                        <th>적용대상쿠폰</th>
                        <td colspan="3" id="couponExceptProdTitle"></td>
                    </tr>
                    <tr>
                        <th><s:message code="coupon.prodCd"/></th>
                        <td><input type="text" id="srchExceptProdCd" ng-model="prodCd" /></td>
                        <th><s:message code="coupon.prodNm"/></th>
                        <td><input type="text" id="srchExceptProdNm" ng-model="prodNm" /></td>
                    </tr>
                    <tr>
                        <%-- 분류조회 --%>
                        <th><s:message code="cmm.prodClass"/></th>
                        <td>
                            <input type="text" class="sb-input" id="srchExceptClassCd" ng-model="prodClassNm" ng-click="popUpProdClass()" style="float: left;  width: 300px;" placeholder="<s:message code="cmm.prodClass" /> 선택" readonly/>
                            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled/>
                            <button type="button" class="btn_skyblue fl mr5" id="btnCancelExceptClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
                        </td>
                        <%-- 바코드 --%>
                        <th><s:message code="coupon.barCd"/></th>
                        <td>
                            <input type="text" id="srchExceptBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 사용여부 --%>
                        <th><s:message code="coupon.useYn"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchExceptUseYn"
                                        ng-model="useYn"
                                        control="useYnCombo"
                                        items-source="_getComboData('useYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                        <%-- 상품유형 --%>
                        <th><s:message code="coupon.prodTypeFg"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box
                                        id="srchExceptProdTypeFg"
                                        ng-model="prodTypeFg"
                                        control="prodTypeFgCombo"
                                        items-source="_getComboData('prodTypeFg')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false"
                                        initialized="_initComboBox(s)">
                                </wj-combo-box>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <%-- 조회 --%>
                <div class="mt10 tr">
                    <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regExceptProdCtrl', 1)" ><s:message code="cmm.search" /></button>
                </div>

                <div class="mt20">
                    <%--- 등록상품 그리드 --%>
                    <div class="w50 fl mb20">
                        <div class="wj-TblWrap mr10" style="height:405px; overflow-y: hidden;">
                            <div class="oh mb10">
                                <span class="fl bk lh20 s14"><s:message code="coupon.regProd"/></span>
                                <%--<c:if test="${coupnEnvstVal == 1}">--%>
                                <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
                                <%--</c:if>--%>
                            </div>
                            <div id="regProdClsGrid" style="height: 370px;">
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
                                    <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.prodClassCd"/>" binding="prodClassCd" width="90" is-read-only="true" align="center" visible="false"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.prodClassNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.prodNm"/>" binding="prodNm" width="120" is-read-only="true"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.costUprc"/>" binding="costUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.barCd"/>" binding="barcdCd" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.useYn"/>" binding="useYn" data-map="useYnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                    <wj-flex-grid-column header="<s:message code="coupon.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                                </wj-flex-grid>
                            </div>
                        </div>

                    </div>
                </div>

            </div>

            <%--- 미등록상품 그리드 --%>
            <div class="w50 fr">
                <div class="wj-TblWrap ml10" style="height:405px; overflow-y: hidden;" ng-controller="noRegExceptProdCtrl">
                    <div class="oh mb10">
                        <span class="fl bk lh20 s14"><s:message code="coupon.noRegProd" /></span>
                        <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="coupon.regist" /></a></span>
                    </div>
                    <div id="noRegExceptProdGrid" style="height: 370px;">
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
                            <wj-flex-grid-column header="<s:message code="coupon.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.prodClassCd"/>" binding="prodClassCd" width="90" is-read-only="true" align="center" visible="false" ></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.prodClassNm"/>" binding="prodClassNm" width="120" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.prodCd"/>" binding="prodCd" width="90" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.prodNm"/>" binding="prodNm" width="120" is-read-only="true"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.costUprc"/>" binding="costUprc" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.barCd"/>" binding="barcdCd" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.useYn"/>" binding="useYn" data-map="useYnDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="coupon.prodTypeFg"/>" binding="prodTypeFg" data-map="prodTypeFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>

            </div>

        </div>
    </div>
</wj-popup>


<script type="text/javascript">
    var coupnEnvstVal = "${coupnEnvstVal}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/couponExceptProd.js?ver=20250523.02" charset="utf-8"></script>
