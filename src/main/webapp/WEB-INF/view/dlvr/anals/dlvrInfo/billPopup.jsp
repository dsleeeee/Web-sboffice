<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}"/>

<wj-popup id="wjDlvrInfoLayer" control="wjDlvrInfoLayer" show-trigger="Click" hide-trigger="Click"
          style="display:none;width:1000px;">
    <div id="dlvrInfoLayer" class="wj-dialog wj-dialog-columns title" ng-controller="popDlvrInfoCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dlvrInfo.info"/>
            <span id="dlvrInfoTitle" class="ml20"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <%-- 종합내역 --%>
            <div class="mt10 mb10 oh sb-select dkbr">
                <s:message code="dlvrInfo.pop.tot"/>
            </div>
            <div class="w100">
                <table class="tblType01" style="position: relative;">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 총매출액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.amt"/></th>
                        <td>
                            <input type="text" readonly id="totSaleAmt" name="totSaleAmt"
                                   ng-model="searchModel.totSaleAmt"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 총할인액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.discount"/></th>
                        <td>
                            <input type="text" readonly id="totDcAmt" name="totDcAmt" ng-model="searchModel.totDcAmt"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 실매출액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.realAmt"/></th>
                        <td>
                            <input type="text" readonly id="realSaleAmt" name="realSaleAmt"
                                   ng-model="searchModel.realSaleAmt"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 순매출액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.pureAmt"/></th>
                        <td>
                            <input type="text" readonly id="netSaleAmt" name="netSaleAmt"
                                   ng-model="searchModel.netSaleAmt"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 면세매출액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.nonTexAmt"/></th>
                        <td>
                            <input type="text" readonly id="noTaxSaleAmt" name="noTaxSaleAmt"
                                   ng-model="searchModel.noTaxSaleAmt"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 과세매출액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.texAmt"/></th>
                        <td>
                            <input type="text" readonly class="sb-input w100" id="taxSaleAmt"
                                   ng-model="searchModel.taxSaleAmt"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 부가세액 --%>
                        <th><s:message code="dlvrInfo.pop.tot.tex"/></th>
                        <td>
                            <input type="text" readonly id="tex" name="vatAmt" ng-model="searchModel.vatAmt"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 봉사료 --%>
                        <th><s:message code="dlvrInfo.pop.tot.service"/></th>
                        <td>
                            <input type="text" readonly class="sb-input w100" id="totTipAmt"
                                   ng-model="searchModel.totTipAmt"
                            />
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 결재내역 --%>
            <div class="mt20 mb10 oh sb-select dkbr">
                <s:message code="dlvrInfo.pop.payment"/>
            </div>
            <div class="w100">

                <table class="tblType01" style="position: relative;">
                    <colgroup>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 신용카드 --%>
                        <th><s:message code="dlvrInfo.pop.payment.card"/></th>
                        <td>
                            <input type="text" id="card" name="card" readonly ng-model="searchModel.card"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 현금 --%>
                        <th><s:message code="dlvrInfo.pop.payment.cash"/></th>
                        <td>
                            <input type="text" id="cash" name="cash" readonly ng-model="searchModel.cash"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 페이코 --%>
                        <th><s:message code="dlvrInfo.pop.payment.payco"/></th>
                        <td>
                            <input type="text" id="payco" name="payco" readonly ng-model="searchModel.payco"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- VMEM 포인트 --%>
                        <th><s:message code="dlvrInfo.pop.payment.vpoint"/></th>
                        <td>
                            <input type="text" id="vpoint" name="vpoint" readonly ng-model="searchModel.vpoint"
                                   class="sb-input w100"/>
                        </td>
                        <%-- VMEM 쿠폰 --%>
                        <th><s:message code="dlvrInfo.pop.payment.vcoupn"/></th>
                        <td>
                            <input type="text" id="vcoupn" name="vcoupn" readonly ng-model="searchModel.vcoupn"
                                   class="sb-input w100"/>
                        </td>
                        <%-- VMEM 전자상품권 --%>
                        <th><s:message code="dlvrInfo.pop.payment.vcharge"/></th>
                        <td>
                            <input type="text" class="sb-input w100" id="vcharge" readonly
                                   ng-model="searchModel.vcharge"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 모바일페이 --%>
                        <th><s:message code="dlvrInfo.pop.payment.mpay"/></th>
                        <td>
                            <input type="text" id="mpay" name="mpay"
                                   readonly ng-model="searchModel.mpay"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 모바일쿠폰 --%>
                        <th><s:message code="dlvrInfo.pop.payment.mcoupn"/></th>
                        <td>
                            <input type="text" class="sb-input w100" id="mcoupn"
                                   readonly ng-model="searchModel.mcoupn"/>
                        </td>
                        <%-- 포인트 --%>
                        <th><s:message code="dlvrInfo.pop.payment.membr"/></th>
                        <td>
                            <input type="text" class="sb-input w100" id="membr"
                                   readonly ng-model="searchModel.membr"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 선불 --%>
                        <th><s:message code="dlvrInfo.pop.payment.prepaid"/></th>
                        <td>
                            <input type="text" id="prepaid" name="prepaid" readonly ng-model="searchModel.prepaid"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 후불 --%>
                        <th><s:message code="dlvrInfo.pop.payment.postpaid"/></th>
                        <td>
                            <input type="text" id="postpaid" name="postpaid" readonly ng-model="searchModel.postpaid"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 상품권 --%>
                        <th><s:message code="dlvrInfo.pop.payment.gift"/></th>
                        <td>
                            <input type="text" id="gift" name="gift" readonly ng-model="searchModel.gift"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 식권 --%>
                        <th><s:message code="dlvrInfo.pop.payment.fstmp"/></th>
                        <td>
                            <input type="text" id="fstmp" name="fstmp" readonly ng-model="searchModel.fstmp"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 제휴할인 --%>
                        <th><s:message code="dlvrInfo.pop.payment.partner"/></th>
                        <td>
                            <input type="text" id="partner" name="partner" readonly ng-model="searchModel.partner"
                                   class="sb-input w100"/>
                        </td>
                        <%-- OK캐쉬백 --%>
                        <th><s:message code="dlvrInfo.pop.payment.okcsb"/></th>
                        <td>
                            <input type="text" id="okcsb" name="okcsb" readonly ng-model="searchModel.okcsb"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 사원카드 --%>
                        <th><s:message code="dlvrInfo.pop.payment.empCard"/></th>
                        <td>
                            <input type="text" id="empCard" name="empCard" readonly ng-model="searchModel.empCard"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 가승인 --%>
                        <th><s:message code="dlvrInfo.pop.payment.partner"/></th>
                        <td>
                            <input type="text" id="temporary" name="temporary" readonly ng-model="searchModel.temporary"
                                   class="sb-input w100"/>
                        </td>
                        <%-- 스마트오더 --%>
                        <th><s:message code="dlvrInfo.pop.payment.smartOrder"/></th>
                        <td>
                            <input type="text" id="smartOrder" name="smartOrder" readonly
                                   ng-model="searchModel.smartOrder"
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <%-- 방문인원 --%>
            <div class="mt20 mb10 oh sb-select dkbr">
                <s:message code="dlvrInfo.pop.visit"/>
            </div>
            <div class="w100">
                <table class="tblType01" style="position: relative;">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 남 --%>
                        <th><s:message code="dlvrInfo.pop.visit.male"/></th>
                        <td>
                            <input type="text" id="male" name="male" ng-model="searchModel.male" readonly
                                   class="sb-input w100"/>
                        </td>
                        <%-- 여 --%>
                        <th><s:message code="dlvrInfo.pop.visit.female"/></th>
                        <td>
                            <input type="text" id="female" name="female" ng-model="searchModel.female" readonly
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%-- 회원정보 --%>
            <div class="mt20 mb10 oh sb-select dkbr">
                <s:message code="dlvrInfo.pop.member"/>
            </div>
            <div class="w100">
                <table class="tblType01" style="position: relative;">
                    <colgroup>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                        <col class="w10"/>
                        <col class="w20"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 회원번호 --%>
                        <th><s:message code="dlvrInfo.pop.member.no"/></th>
                        <td>
                            <input type="text" id="membrNo" name="membrNo" ng-model="searchModel.membrNo" readonly
                                   class="sb-input w100"/>
                        </td>
                        <%-- 회원명 --%>
                        <th><s:message code="dlvrInfo.pop.member.name"/></th>
                        <td>
                            <input type="text" id="membrNm" name="membrNm" ng-model="searchModel.membrNm" readonly
                                   class="sb-input w100"/>
                        </td>
                        <%-- 발생포인트 --%>
                        <th><s:message code="dlvrInfo.pop.member.point"/></th>
                        <td>
                            <input type="text" id="saleSavePoint" name="saleSavePoint" ng-model="searchModel.saleSavePoint" readonly
                                   class="sb-input w100"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%-- 상품내역 --%>


            <div class="mt20 oh sb-select dkbr">
                <s:message code="dlvrInfo.pop.prod"/>
                <%-- 엑셀 --%>
                <button class="btn_skyblue ml5 fr" id="save" ng-click="excelDownload()">
                    <s:message code="cmm.excel.down"/>
                </button>
            </div>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px; overflow-x: hidden; overflow-y: hidden;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <%--                        <wj-flex-grid-column header="<s:message code="cmm.no"/>" binding="no" width="30"--%>
                        <%--                                             align="center"></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.prod.prodNm"/>" binding="prodCd"
                                             width="200" visible="false"
                                             align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.prod.prodNm"/>" binding="prodNm"
                                             width="300"
                                             align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.prod.prodCnt"/>" binding="saleQty"
                                             width="90" align="center" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.prod.uprc"/>" binding="saleUprc"
                                             width="90" align="right" is-read-only="true"  aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.tot.amt"/>" binding="saleAmt"
                                             width="90" align="right" data-type="Number" format="n0"
                                             is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.tot.discount"/>" binding="dcAmt"
                                             width="90" align="right" data-type="Number" format="n0"
                                             is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.tot.realAmt"/>" binding="realSaleAmt"
                                             width="90" align="right" data-type="Number" format="n0"
                                             is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.tot.supply"/>" binding="supplyAmt"
                                             width="90" align="right" data-type="Number" format="n0"
                                             is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dlvrInfo.pop.tot.tex"/>" binding="vatAmt"
                                             width="90" align="right" data-type="Number" format="n0"
                                             is-read-only="true" aggregate="Sum"></wj-flex-grid-column>


                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/anals/dlvrInfo/billPopup.js?ver=2019052801.12"
        charset="utf-8"></script>
