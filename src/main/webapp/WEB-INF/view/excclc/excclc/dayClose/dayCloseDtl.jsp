<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup id="dayCloseDtlLayer" control="dayCloseDtlLayer" show-trigger="Click" hide-trigger="Click" style="overflow-x:auto; overflow-y: auto;display:none;width:1000px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="dayCloseDtlCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="saleRegistKwu.newRegist"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <h3 class="h3_tbl brt" style="height: 35px;">
                <span id="dayCloseDtlTitle" class="fl"></span>
                <button class="btn_skyblue ml5 fr" id="closeCancel" ng-click="close('0')" ><s:message code="dayClose.closeCancel" /></button>
                <button class="btn_skyblue ml5 fr" id="close" ng-click="close('1')" ><s:message code="dayClose.close" /></button>
                <button class="btn_skyblue ml5 fr" id="btnSearch" ng-click="getDayCloseDtl()" ><s:message code="dayClose.closeDataRecv"/></button>
            </h3>
            <div class="tblBr">
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
                        <tr>
                            <%-- 개점일자 --%>
                            <th><s:message code="dayClose.openDate"/></th>
                            <td>
                                <div class="sb-select">
                                    <span class="txtIn"><input id="openDate" class="w110px" readonly></span>
                                </div>
                            </td>
                            <%-- 마감일자 --%>
                            <th><s:message code="dayClose.closeDate"/></th>
                            <td>
                                <div class="sb-select">
                                    <span class="txtIn"><input id="closeDate" class="w110px" readonly></span>
                                </div>
                            </td>
                            <%-- 결산이자 --%>
                            <th><s:message code="dayClose.interestAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="interestAmt" ng-model="interestAmt" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 월입금 누계 --%>
            <h3 class="h3_tbl"><s:message code="dayClose.monthInAmtSum"/></h3>
            <div class="tblBr">
                <table class="searchTbl">
                    <colgroup>
                        <col class="w16"/>
                        <col class="w34"/>
                        <col class="w16"/>
                        <col class="w34"/>
                    </colgroup>
                    <tbody>
                        <tr>
                            <th><s:message code="dayClose.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="inAmt" ng-model="inAmt" numberOnly/></td>
                            <th><s:message code="dayClose.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="outAmt" ng-model="outAmt" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 입금 관련 --%>
            <h3 class="h3_tbl"><s:message code="dayClose.inInfo"/></h3>
            <div class="tblBr">
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
                        <tr>
                            <th><s:message code="dayClose.groupAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="groupAmt" ng-model="groupAmt" numberOnly/></td>
                            <th><s:message code="dayClose.hockeyAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="hockeyAmt" ng-model="hockeyAmt" numberOnly/></td>
                            <th><s:message code="dayClose.etcAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="etcAmt" ng-model="etcAmt" numberOnly/></td>
                            <th><s:message code="dayClose.inDayAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="inDayAmt" ng-model="inDayAmt" numberOnly/></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.inSum"/></th>
                            <td><input type="text" class="sb-input w100" id="inSum" ng-model="inSum" numberOnly/></td>
                            <th><s:message code="dayClose.inMonthSum"/></th>
                            <td><input type="text" class="sb-input w100" id="inMonthSum" ng-model="inMonthSum" numberOnly/></td>
                            <th><s:message code="dayClose.inBMonthSum"/></th>
                            <td><input type="text" class="sb-input w100" id="inBMonthSum" ng-model="inBMonthSum" numberOnly/></td>
                            <th><s:message code="dayClose.inTotalSum"/></th>
                            <td><input type="text" class="sb-input w100" id="inTotalSum" ng-model="inTotalSum" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 출금 관련 --%>
            <h3 class="h3_tbl"><s:message code="dayClose.outInfo"/></h3>
            <div class="tblBr">
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
                        <tr>
                            <th><s:message code="dayClose.outSum"/></th>
                            <td><input type="text" class="sb-input w100" id="outSum" ng-model="outSum" numberOnly/></td>
                            <th><s:message code="dayClose.outMonthSum"/></th>
                            <td><input type="text" class="sb-input w100" id="outMonthSum" ng-model="outMonthSum" numberOnly/></td>
                            <th><s:message code="dayClose.outBMonthSum"/></th>
                            <td><input type="text" class="sb-input w100" id="outBMonthSum" ng-model="outBMonthSum" numberOnly/></td>
                            <th><s:message code="dayClose.outTotalSum"/></th>
                            <td><input type="text" class="sb-input w100" id="outTotalSum" ng-model="outTotalSum" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 출납현황 --%>
            <h3 class="h3_tbl"><s:message code="dayCloseDtl.status"/></h3>
            <div class="tblBr">
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
                        <tr>
                            <%-- 현금 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.cash"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCashInAmt" ng-model="statusCashInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCashOutAmt" ng-model="statusCashOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCashTotalAmt" ng-model="statusCashTotalAmt" numberOnly/></td>
                        </tr>
                        <tr>
                            <%-- 신용카드계좌입금 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.card"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCardInAmt" ng-model="statusCardInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCardOutAmt" ng-model="statusCardOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="statusCardTotalAmt" ng-model="statusCardTotalAmt" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 계좌현황 --%>
            <h3 class="h3_tbl"><s:message code="dayCloseDtl.accountStatus"/></h3>
            <div class="tblBr">
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
                        <tr>
                            <%-- MAIN-하나 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.mainHana"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusMainHanaInAmt" ng-model="accountStatusMainHanaInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusMainHanaOutAmt" ng-model="accountStatusMainHanaOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusMainHanaTotalAmt" ng-model="accountStatusMainHanaTotalAmt" numberOnly/></td>
                        </tr>
                        <tr>
                            <%-- 신용카드-하나 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.cardHana"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardHanaInAmt" ng-model="accountStatusCardHanaInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardHanaOutAmt" ng-model="accountStatusCardHanaOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardHanaTotalAmt" ng-model="accountStatusCardHanaTotalAmt" numberOnly/></td>
                        </tr>
                        <tr>
                            <%-- 신용카드-국민 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.cardKb"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardKbInAmt" ng-model="accountStatusCardKbInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardKbOutAmt" ng-model="accountStatusCardKbOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusCardKbTotalAmt" ng-model="accountStatusCardKbTotalAmt" numberOnly/></td>
                        </tr>
                        <tr>
                            <%-- 방학특강-하나 --%>
                            <th colspan="2"><s:message code="dayCloseDtl.spHana"/></th>
                            <%-- 입금 --%>
                            <th><s:message code="dayCloseDtl.inAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusSpHanaInAmt" ng-model="accountStatusSpHanaInAmt" numberOnly/></td>
                            <%-- 출금 --%>
                            <th><s:message code="dayCloseDtl.outAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusSpHanaOutAmt" ng-model="accountStatusSpHanaOutAmt" numberOnly/></td>
                            <%-- 잔액 --%>
                            <th><s:message code="dayCloseDtl.totalAmt"/></th>
                            <td><input type="text" class="sb-input w100" id="accountStatusSpHanaTotalAmt" ng-model="accountStatusSpHanaTotalAmt" numberOnly/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <%-- 비고 --%>
            <div class="tblBr">
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
                        <tr>
                            <th><s:message code="dayClose.remark1"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark1" ng-model="remark1"></textarea></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.remark2"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark2" ng-model="remark2"></textarea></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.remark3"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark3" ng-model="remark3"></textarea></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.remark4"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark4" ng-model="remark4"></textarea></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.remark5"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark5" ng-model="remark5"></textarea></td>
                        </tr>
                        <tr>
                            <th><s:message code="dayClose.remark6"/></th>
                            <td colspan="7"><textarea class="sb-input w100" style="height:70px;resize: none;" id="remark6" ng-model="remark6"></textarea></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%-- //body --%>

    </div>
</wj-popup>

<script type="text/javascript">
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^-|^0-9]/g,""));
            $(this).val($(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
        });
    });
</script>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/dayClose/dayCloseDtl.js?ver=20240722.01" charset="utf-8"></script>