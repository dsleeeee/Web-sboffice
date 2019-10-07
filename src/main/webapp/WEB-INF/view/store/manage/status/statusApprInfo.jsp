<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="statusApprInfoLayer" control="statusApprInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;">

    <div class="wj-dialog wj-dialog-columns" ng-controller="statusApprInfoCtrl">

        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="storeStatus.saleDtlInfo"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <%--매장정보--%>
            <h2 class="h2_tit mt5"><s:message code="storeStatus.storeInfo" /></h2>
            <div style="height: 80px; overflow-y: auto;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 매장코드 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.storeCd" /></div>
                        </th>
                        <%-- 매장명 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.storeNm" /></div>
                        </th>
                        <%-- 매출일자 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.saleDate" /></div>
                        </th>
                        <%-- POS번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.posNo" /></div>
                        </th>
                        <%-- 영수증번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.billNo" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 매장코드 --%>
                        <td align="center">{{storeStatus.storeCd}}</td>
                        <%-- 매장명 --%>
                        <td align="center">{{storeStatus.storeNm}}</td>
                        <%-- 매출일자 --%>
                        <td align="center">{{storeStatus.saleDate}}</td>
                        <%-- POS번호 --%>
                        <td align="center">{{storeStatus.posNo}}</td>
                        <%-- 영수증번호 --%>
                        <td align="center">{{storeStatus.billNo}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--매출내역--%>
            <h2 class="h2_tit mt5"><s:message code="storeStatus.saleInfo" /></h2>
            <div style="height: 80px; overflow-y: auto;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                        <col class="w10" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 총매출금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.saleAmt" /></div>
                        </th>
                        <%-- 총할인금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.dcAmt" /></div>
                        </th>
                        <%-- 실매출금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.realsaleAmt" /></div>
                        </th>
                        <%-- 순매출금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.netSaleAmt" /></div>
                        </th>
                        <%-- 면세매출액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.noTaxSaleAmt" /></div>
                        </th>
                        <%-- 과세매출액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.taxSaleAmt" /></div>
                        </th>
                        <%-- 부가세 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.vatAmt" /></div>
                        </th>
                        <%-- 봉사료 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.tipAmt" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 총매출금액 --%>
                        <td align="center">{{storeStatus.totSaleAmt}}</td>
                        <%-- 총할인금액 --%>
                        <td align="center">{{storeStatus.totDcAmt}}</td>
                        <%-- 실매출금액 --%>
                        <td align="center">{{storeStatus.realSaleAmt}}</td>
                        <%-- 순매출금액 --%>
                        <td align="center">{{storeStatus.netSaleAmt}}</td>
                        <%-- 면세매출액 --%>
                        <td align="center">{{storeStatus.noTaxSaleAmt}}</td>
                        <%-- 과세매출액 --%>
                        <td align="center">{{storeStatus.taxSaleAmt}}</td>
                        <%-- 부가세 --%>
                        <td align="center">{{storeStatus.vatAmt}}</td>
                        <%-- 봉사료 --%>
                        <td align="center">{{storeStatus.totTipAmt}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--결제내역--%>
            <h2 class="h2_tit mt5"><s:message code="storeStatus.payInfo" /></h2>
            <div style="height: 200px; overflow-y: auto;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 신용카드 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.card" /></div>
                        </th>
                        <%-- 현금 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.cash" /></div>
                        </th>
                        <%-- 페이코 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.payco" /></div>
                        </th>
                        <%-- VMEM 포인트 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.vPoint" /></div>
                        </th>
                        <%-- VMEM 전자상품권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.vCharge" /></div>
                        </th>
                        <%-- 모바일페이 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.mPay" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 신용카드 --%>
                        <td align="center">{{storeStatus.pay01}}</td>
                        <%-- 현금 --%>
                        <td align="center">{{storeStatus.pay02}}</td>
                        <%-- 페이코 --%>
                        <td align="center">{{storeStatus.pay03}}</td>
                        <%-- VMEM 포인트 --%>
                        <td align="center">{{storeStatus.pay04}}</td>
                        <%-- VMEM 전자상품권 --%>
                        <td align="center">{{storeStatus.pay06}}</td>
                        <%-- 모바일페이 --%>
                        <td align="center">{{storeStatus.pay07}}</td>
                    </tr>
                    <tr>
                        <%-- 모바일쿠폰 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.mCoupn" /></div>
                        </th>
                        <%-- 포인트 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.membr" /></div>
                        </th>
                        <%-- 선불 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.prepaid" /></div>
                        </th>
                        <%-- 후불 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.postpaid" /></div>
                        </th>
                        <%-- 상품권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.gift" /></div>
                        </th>
                        <%-- 식권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.fstmp" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 모바일쿠폰 --%>
                        <td align="center">{{storeStatus.pay08}}</td>
                        <%-- 포인트 --%>
                        <td align="center">{{storeStatus.pay09}}</td>
                        <%-- 선불 --%>
                        <td align="center">{{storeStatus.pay10}}</td>
                        <%-- 후불 --%>
                        <td align="center">{{storeStatus.pay11}}</td>
                        <%-- 상품권 --%>
                        <td align="center">{{storeStatus.pay13}}</td>
                        <%-- 식권 --%>
                        <td align="center">{{storeStatus.pay14}}</td>
                    </tr>
                    <tr>
                        <%-- 제휴할인 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.partner" /></div>
                        </th>
                        <%-- OK캐쉬백 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.okcsb" /></div>
                        </th>
                        <%-- 사원카드 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.empCard" /></div>
                        </th>
                        <%-- 가승인 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="storeStatus.temporary" /></div>
                        </th>
                        <th>
                            <div class="impWrap" align="center"></div>
                        </th>
                        <th>
                            <div class="impWrap" align="center"></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 제휴할인 --%>
                        <td align="center">{{storeStatus.pay15}}</td>
                        <%-- OK캐쉬백 --%>
                        <td align="center">{{storeStatus.pay16}}</td>
                        <%-- 사원카드 --%>
                        <td align="center">{{storeStatus.pay17}}</td>
                        <%-- 가승인 --%>
                        <td align="center">{{storeStatus.pay18}}</td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="wj-dialog wj-dialog-columns" ng-controller="statusCardPayInfoCtrl">

        <%--신용카드 결제내역--%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <h2 class="h2_tit mt5"><%--<s:message code="storeStatus.cardPayInfo" />--%><span id="spanPayTitle"></span></h2>
            <div class="wj-gridWrap" style="height: 90px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter"
                    id="wjApprCardList">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="storeStatus.cornrNm"/>" binding="cornrCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.apprProcFg"/>" binding="apprFg" data-map="apprProcFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.cardNo"/>" binding="cardNo" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.validDt"/>" binding="" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.instCnt"/>" binding="instCnt" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.apprAmt"/>" binding="apprAmt" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.apprDt"/>" binding="apprDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.apprNo"/>" binding="apprNo" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.van"/>" binding="vanCd" width="80" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.issueNm"/>" binding="issueNm" width="80" align="center" is-read-only="true"visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.acquireNm"/>" binding="acquireNm" width="80" align="center" is-read-only="true"visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.orgApprDt"/>" binding="orgApprDt" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeStatus.orgApprNo"/>" binding="orgApprNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
            </div>
        </div>

    </div>

    <div class="wj-dialog wj-dialog-columns" ng-controller="statusProductInfoCtrl">

        <%--상품내역--%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <h2 class="h2_tit mt5"><s:message code="storeStatus.productInfo" /></h2>
            <div class="wj-gridWrap" style="height: 150px;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="true"
                        item-formatter="_itemFormatter"
                        id="wjApprProdList">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeStatus.prodNm"/>" binding="prodNm" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.saleQty"/>" binding="saleQty" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.saleUprc"/>" binding="saleUprc" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.saleAmt"/>" binding="saleAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.dcAmt"/>" binding="dcAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.realsaleAmt"/>" binding="realSaleAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.taxSaleAmt"/>" binding="taxSaleAmt" width="80" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeStatus.vatAmt"/>" binding="vatAmt" width="100" align="center" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                </wj-flex-grid>
            </div>
        </div>
        <div class="wj-dialog-footer">
            <div class="btnSet">
                <%-- 닫기 --%>
                <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
            </div>
        </div>

    </div>

</wj-popup>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/status/statusApprInfo.js?ver=20191002.01" charset="utf-8"></script>
