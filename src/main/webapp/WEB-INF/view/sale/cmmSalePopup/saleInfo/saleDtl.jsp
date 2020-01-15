<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/sale/cmmSalePopup/saleInfo/saleDtl/"/>

<wj-popup control="wjSaleDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;" fade-in="false" fade-out="false">
    <div ng-controller="saleDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleDtl.info"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <%--매장정보--%>
            <h2 class="h2_tit mt5"><s:message code="saleDtl.storeInfo" /></h2>
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
                            <div class="impWrap" align="center"><s:message code="saleDtl.storeCd" /></div>
                        </th>
                        <%-- 매장명 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.storeNm" /></div>
                        </th>
                        <%-- 매출일자 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.saleDate" /></div>
                        </th>
                        <%-- POS번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.posNo" /></div>
                        </th>
                        <%-- 영수증번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.billNo" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 매장코드 --%>
                        <td align="center">{{saleDtl.storeCd}}</td>
                        <%-- 매장명 --%>
                        <td align="center">{{saleDtl.storeNm}}</td>
                        <%-- 매출일자 --%>
                        <td align="center">{{saleDtl.saleDate}}</td>
                        <%-- POS번호 --%>
                        <td align="center">{{saleDtl.posNo}}</td>
                        <%-- 영수증번호 --%>
                        <td align="center">{{saleDtl.billNo}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--매출종합내역--%>
            <h2 class="h2_tit mt5"><s:message code="saleDtl.saleInfo" /></h2>
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
                            <div class="impWrap" align="center"><s:message code="saleDtl.totSaleAmt" /></div>
                        </th>
                        <%-- 총할인금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.totDcAmt" /></div>
                        </th>
                        <%-- 실매출금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.realSaleAmt" /></div>
                        </th>
                        <%-- 순매출금액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.netSaleAmt" /></div>
                        </th>
                        <%-- 면세매출액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.noTaxSaleAmt" /></div>
                        </th>
                        <%-- 과세매출액 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.taxSaleAmt" /></div>
                        </th>
                        <%-- 부가세 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.vatAmt" /></div>
                        </th>
                        <%-- 봉사료 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.totTipAmt" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 총매출금액 --%>
                        <td align="center">{{saleDtl.totSaleAmt}}</td>
                        <%-- 총할인금액 --%>
                        <td align="center">{{saleDtl.totDcAmt}}</td>
                        <%-- 실매출금액 --%>
                        <td align="center">{{saleDtl.realSaleAmt}}</td>
                        <%-- 순매출금액 --%>
                        <td align="center">{{saleDtl.netSaleAmt}}</td>
                        <%-- 면세매출액 --%>
                        <td align="center">{{saleDtl.noTaxSaleAmt}}</td>
                        <%-- 과세매출액 --%>
                        <td align="center">{{saleDtl.taxSaleAmt}}</td>
                        <%-- 부가세 --%>
                        <td align="center">{{saleDtl.vatAmt}}</td>
                        <%-- 봉사료 --%>
                        <td align="center">{{saleDtl.totTipAmt}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--결제내역--%>
            <h2 class="h2_tit mt5"><s:message code="saleDtl.payInfo" /></h2>
            <div style="height: 215px; overflow-y: auto;">
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
                            <div class="impWrap" align="center"><s:message code="saleDtl.card" /></div>
                        </th>
                        <%-- 현금 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.cash" /></div>
                        </th>
                        <%-- 페이코 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.payco" /></div>
                        </th>
                        <%-- VMEM 포인트 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.vPoint" /></div>
                        </th>
                        <%-- VMEM 전자상품권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.vCharge" /></div>
                        </th>
                        <%-- 모바일페이 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.mPay" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 신용카드 --%>
                        <td align="center">{{saleDtl.pay01}}</td>
                        <%-- 현금 --%>
                        <td align="center">{{saleDtl.pay02}}</td>
                        <%-- 페이코 --%>
                        <td align="center">{{saleDtl.pay03}}</td>
                        <%-- VMEM 포인트 --%>
                        <td align="center">{{saleDtl.pay04}}</td>
                        <%-- VMEM 전자상품권 --%>
                        <td align="center">{{saleDtl.pay06}}</td>
                        <%-- 모바일페이 --%>
                        <td align="center">{{saleDtl.pay07}}</td>
                    </tr>
                    <tr>
                        <%-- 모바일쿠폰 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.mCoupn" /></div>
                        </th>
                        <%-- 포인트 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.membr" /></div>
                        </th>
                        <%-- 선불 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.prepaid" /></div>
                        </th>
                        <%-- 후불 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.postpaid" /></div>
                        </th>
                        <%-- 상품권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.gift" /></div>
                        </th>
                        <%-- 식권 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.fstmp" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 모바일쿠폰 --%>
                        <td align="center">{{saleDtl.pay08}}</td>
                        <%-- 포인트 --%>
                        <td align="center">{{saleDtl.pay09}}</td>
                        <%-- 선불 --%>
                        <td align="center">{{saleDtl.pay10}}</td>
                        <%-- 후불 --%>
                        <td align="center">{{saleDtl.pay11}}</td>
                        <%-- 상품권 --%>
                        <td align="center">{{saleDtl.pay13}}</td>
                        <%-- 식권 --%>
                        <td align="center">{{saleDtl.pay14}}</td>
                    </tr>
                    <tr>
                        <%-- 제휴할인 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.partner" /></div>
                        </th>
                        <%-- OK캐쉬백 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.okcsb" /></div>
                        </th>
                        <%-- 사원카드 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.empCard" /></div>
                        </th>
                        <%-- 가승인 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.temporary" /></div>
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
                        <td align="center">{{saleDtl.pay15}}</td>
                        <%-- OK캐쉬백 --%>
                        <td align="center">{{saleDtl.pay16}}</td>
                        <%-- 사원카드 --%>
                        <td align="center">{{saleDtl.pay17}}</td>
                        <%-- 가승인 --%>
                        <td align="center">{{saleDtl.pay18}}</td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%--회원정보--%>
            <h2 class="h2_tit mt5"><s:message code="saleDtl.membrInfo" /></h2>
            <div style="height: 80px; overflow-y: auto;">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                        <col class="w15" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 회원번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.membrNo" /></div>
                        </th>
                        <%-- 회원명 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.membrNm" /></div>
                        </th>
                        <%-- 회원카드번호 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.membrCardNo" /></div>
                        </th>
                        <%-- 발생포인트 --%>
                        <th>
                            <div class="impWrap" align="center"><s:message code="saleDtl.saleSavePoint" /></div>
                        </th>
                    </tr>
                    <tr>
                        <%-- 회원번호 --%>
                        <td align="center">{{saleDtl.membrNo}}</td>
                        <%-- 회원명 --%>
                        <td align="center">{{saleDtl.membrNm}}</td>
                        <%-- 회원카드번호 --%>
                        <td align="center">{{saleDtl.membrCardNo}}</td>
                        <%-- 발생포인트 --%>
                        <td align="center">{{saleDtl.saleSavePoint}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div ng-controller="saleCardDtlCtrl">

        <%--신용카드 결제내역--%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <h2 class="h2_tit mt5"><s:message code="saleDtl.cardInfo" /></h2>
            <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="saleDtl.cornrNm"/>" binding="cornrNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprFg"/>" binding="apprFg" data-map="apprFgDataMap" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprProcFg"/>" binding="apprFg" data-map="apprProcFgDataMap" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.cardNo"/>" binding="cardNo" width="120" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.instCnt"/>" binding="instCnt" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprAmt"/>" binding="apprAmt" width="80" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprDt"/>" binding="apprDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprNo"/>" binding="apprNo" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.vanNm"/>" binding="vanNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.issueNm"/>" binding="issueNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.acquireNm"/>" binding="acquireNm" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.orgApprDt"/>" binding="orgApprDt" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.orgApprNo"/>" binding="orgApprNo" width="100" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div ng-controller="saleCashDtlCtrl">

        <%--현금영수증 결제내역--%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <h2 class="h2_tit mt5"><s:message code="saleDtl.cashInfo" /></h2>
            <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="saleDtl.cornrNm"/>" binding="cornrNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprProcFg"/>" binding="apprProcFg" data-map="apprProcFgDataMap" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.cashBillCardNo"/>" binding="cashBillCardNo" width="120" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprNo"/>" binding="apprNo" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprAmt"/>" binding="apprAmt" width="130" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.apprDt"/>" binding="apprDt" width="130" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.vanNm"/>" binding="vanNm" width="130" is-read-only="true" align="center"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
            </div>
        </div>
    </div>

    <div ng-controller="saleProdDtlCtrl">

        <%--상품내역--%>
        <div class="wj-dialog-body sc2" style="overflow-y: hidden;">
            <h2 class="h2_tit mt5"><s:message code="saleDtl.prodInfo" /></h2>
            <div class="wj-gridWrap" style="height:150px; overflow-y: hidden; overflow-x: hidden;">
                <div class="row">
                    <wj-flex-grid
                        autoGenerateColumns="false"
                        control="flex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        is-read-only="true">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="saleDtl.prodNm"/>" binding="prodNm" width="175" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.saleUprc"/>" binding="saleUprc" width="100" is-read-only="true" align="right"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.saleAmt"/>" binding="saleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.dcAmt"/>" binding="dcAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.realSaleAmt"/>" binding="realSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.taxSaleAmt"/>" binding="taxSaleAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="saleDtl.vatAmt"/>" binding="vatAmt" width="100" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
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
    // 결제수단
    var payColList = [];
    <%--javascript에서 사용할 결제수단 json 데이터 생성--%>
    <c:forEach var="payCol" items="${payColList}">
        var payParam       = {};
        payParam.payCd     = "${payCol.payCd}";
        payParam.payMethod = "${payCol.payMethod}";
        payColList.push(payParam);
    </c:forEach>

    var payCol    = '${payCol}';
    var arrPayCol = payCol.split(',');
</script>

<script type="text/javascript" src="/resource/solbipos/js/sale/cmmSalePopup/saleInfo/saleDtl.js?ver=20200114" charset="utf-8"></script>