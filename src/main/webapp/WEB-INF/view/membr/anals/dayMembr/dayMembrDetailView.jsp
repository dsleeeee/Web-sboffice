<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="dayMembrDetailViewLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:1000px;height:570px;" fade-in="false" fade-out="false">
    <div ng-controller="dayMembrDetailCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dayMembrDetail.info" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <div style="height:470px; overflow-y: auto;">
                <%-- 회원정보---%>
                <h3 class="h3_tbl"><s:message code="dayMembrDetail.info" /> / {{membrInfo.membrNm}}</h3>
                <table class="searchTbl">
                    <colgroup>
                        <col class="w15" />
                        <col class="w35" />
                        <col class="w15" />
                        <col class="w35" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <%-- 회원번호---%>
                            <th><s:message code="dayMembrDetail.membrNo" /></th>
                            <td>{{membrInfo.membrNo}}</td>
                            <%-- 회원명---%>
                            <th><s:message code="dayMembrDetail.membrNm" /></th>
                            <td>{{membrInfo.membrNm}}</td>
                        </tr>
                        <tr>
                            <%-- 최초방문일---%>
                            <th><s:message code="dayMembrDetail.minSaleDate" /></th>
                            <td>{{membrInfo.minSaleDate}}</td>
                            <%-- 최종방문일---%>
                            <th><s:message code="dayMembrDetail.maxSaleDate" /></th>
                            <td>{{membrInfo.maxSaleDate}}</td>
                        </tr>
                        <tr>
                            <%-- 등록매장---%>
                            <th><s:message code="dayMembrDetail.storeNm" /></th>
                            <td>{{membrInfo.storeNm}}</td>
                            <%-- 회원등급분류---%>
                            <th><s:message code="dayMembrDetail.membrClassCd" /></th>
                            <td>{{membrInfo.membrClassCd}}</td>
                        </tr>
                        <tr>
                            <%-- 회원카드번호---%>
                            <th><s:message code="dayMembrDetail.membrCardNo" /></th>
                            <td>{{membrInfo.membrCardNo}}</td>
                            <%-- 카드사용구분---%>
                            <th></th>
                            <td></td>
                        </tr>
                        <tr>
                            <%-- 카드발급횟수---%>
                            <th></th>
                            <td></td>
                            <%-- 성별---%>
                            <th><s:message code="dayMembrDetail.gendrFg" /></th>
                            <td>{{membrInfo.gendrFg}}</td>
                        </tr>
                        <tr>
                            <%-- 결혼유무---%>
                            <th><s:message code="dayMembrDetail.weddingYn" /></th>
                            <td>
                                <wj-combo-box
                                        ng-model="membrInfo.weddingYn"
                                        ng-hide="true"
                                        text="_weddingYn"
                                        items-source="_getComboData('rWeddingYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false">
                                </wj-combo-box>
                                {{_weddingYn}}
                            </td>
                            <%-- 생일---%>
                            <th><s:message code="dayMembrDetail.birthday" /></th>
                            <td>{{membrInfo.birthday}}</td>
                        </tr>
                        <tr>
                            <%-- 전화번호---%>
                            <th><s:message code="dayMembrDetail.telNo" /></th>
                            <td>{{membrInfo.telNo}}</td>
                            <%-- E-mail---%>
                            <th><s:message code="dayMembrDetail.emailAddr" /></th>
                            <td>{{membrInfo.emailAddr}}</td>
                        </tr>
                        <tr>
                            <%-- 주소---%>
                            <th><s:message code="dayMembrDetail.addr" /></th>
                            <td colspan="3">{{membrInfo.addr}}</td>
                        </tr>
                        <tr>
                            <%-- 이메일수신---%>
                            <th><s:message code="dayMembrDetail.emailRecvYn" /></th>
                            <td>
                                <wj-combo-box
                                        ng-model="membrInfo.emailRecvYn"
                                        ng-hide="true"
                                        text="_emailRecvYn"
                                        items-source="_getComboData('rEmailRecvYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false">
                                </wj-combo-box>
                                {{_emailRecvYn}}
                            </td>
                            <%-- SMS수신---%>
                            <th><s:message code="dayMembrDetail.smsRecvYn" /></th>
                            <td>
                                <wj-combo-box
                                        ng-model="membrInfo.smsRecvYn"
                                        ng-hide="true"
                                        text="_smsRecvYn"
                                        items-source="_getComboData('rSmsRecvYn')"
                                        display-member-path="name"
                                        selected-value-path="value"
                                        is-editable="false">
                                </wj-combo-box>
                                {{_smsRecvYn}}
                            </td>
                        </tr>
                        <tr>
                            <%-- 비고---%>
                            <th><s:message code="dayMembrDetail.remark" /></th>
                            <td colspan="3">{{membrInfo.remark}}</td>
                        </tr>
                    </tbody>
                </table>

                <%-- 구매내역---%>
                <%--<div class="w100 mt10 mb20">--%>
                <div class="w100 mt10">
                    <h3 class="h3_tbl"><s:message code="dayMembrDetail.purchInfo" /></h3>
                    <div class="wj-gridWrap" style="height:400px; overflow-y: hidden; overflow-x: hidden;">
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
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleDate"/>" binding="saleDate" width="115" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.storeNm"/>" binding="storeNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.prodNm"/>" binding="prodNm" width="150" is-read-only="true" align="center"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleQty"/>" binding="saleQty" width="100" is-read-only="true" align="center" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.saleAmt"/>" binding="saleAmt" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.membrSavePoint"/>" binding="membrSavePoint" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>
                            <wj-flex-grid-column header="<s:message code="dayMembrPurchs.membrUsePoint"/>" binding="membrUsePoint" width="115" is-read-only="true" align="right" aggregate="Sum"></wj-flex-grid-column>

                        </wj-flex-grid>
                    </div>
                </div>

            </div>
        </div>

    </div>
</wj-popup>

<script type="text/javascript">
    <%--결혼유무--%>
    var weddingYnDataMap  = ${ccu.getCommCodeExcpAll("076")};
    <%--이메일수신--%>
    var emailRecvYnDataMap  = ${ccu.getCommCodeExcpAll("072")};
    <%--SMS수신--%>
    var smsRecvYnDataMap  = ${ccu.getCommCodeExcpAll("072")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/dayMembr/dayMembrDetail.js?ver=2019052801.31" charset="utf-8"></script>