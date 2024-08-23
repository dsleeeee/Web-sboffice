<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="couponProdClsLayer" control="couponProdClsLayer" show-trigger="Click" hide-trigger="Click" style="width:1200px;">

    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="coupon.regist.prodClsCnt" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body">

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
                    <td colspan="3" id="couponProdClsTitle"></td>
                </tr>
                <tr>
                    <th><s:message code="coupon.prodClassCdLv3"/></th>
                    <td><input type="text" id="srchProdClsCd" ng-model="prodCd" /></td>
                    <th><s:message code="coupon.prodClassNmLv3"/></th>
                    <td><input type="text" id="srchProdClsNm" ng-model="prodNm" /></td>
                </tr>
                </tbody>
            </table>
            <%-- 조회 --%>
            <div class="mt10 tr">
                <button class="btn_skyblue" id="btnSearch" ng-click="_pageView('regProdClsCtrl', 1)" ><s:message code="cmm.search" /></button>
            </div>
            <div class="oh mt40">

                <%--- 등록상품 그리드 --%>
                <div class="w50 fl">
                    <div class="wj-TblWrap mr10" style="height:395px; overflow-y: hidden;" ng-controller="regProdClsCtrl">
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
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv1"/>" binding="lClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv1"/>" binding="lClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv2"/>" binding="mClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv2"/>" binding="mClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv3"/>" binding="sClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv3"/>" binding="sClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>

                </div>

                <%--- 미등록상품 그리드 --%>
                <div class="w50 fr">
                    <div class="wj-TblWrap ml10" style="height:400px; overflow-y: hidden;" ng-controller="noRegProdClsCtrl">
                        <div class="oh mb10">
                            <span class="fl bk lh20 s14"><s:message code="coupon.noRegProd" /></span>
                            <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="coupon.regist" /></a></span>
                        </div>
                        <div id="noRegProdClsGrid" style="height: 370px;">
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
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv1"/>" binding="lClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv1"/>" binding="lClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv2"/>" binding="mClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv2"/>" binding="mClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassCdLv3"/>" binding="sClassCd" width="*" is-read-only="true" visible="false"></wj-flex-grid-column>
                                <wj-flex-grid-column header="<s:message code="coupon.prodClassNmLv3"/>" binding="sClassNm" width="*" is-read-only="true"></wj-flex-grid-column>
                            </wj-flex-grid>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">
    var coupnEnvstVal = "${coupnEnvstVal}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/couponProdCls.js?ver=20240814.01" charset="utf-8"></script>
