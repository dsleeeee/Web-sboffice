<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMemberPointAdjustDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:300px;" fade-in="false" fade-out="false">

    <div ng-controller="memberPointAdjustDtlCtrl">
        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="regist.memberPointAdjust.info"/>
            <a href="#" class="wj-hide btn_close" ng-click="close()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w20"/>
                    <col class="w40"/>
                    <col class="w25"/>
                    <col class="w35"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 회원 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.member"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchMembrNmMemberPointAdjustDtl" ng-model="membrNm" />
                        <input type="hidden" id="_membrNo" ng-model="membrNo" disabled />
                        <input type="hidden" id="_pointSaveFg" name="pointSaveFg" ng-model="pointSaveFg" disabled />
                        <input type="hidden" id="_memberCash" name="memberCash" ng-model="memberCash" disabled />
                        <input type="hidden" id="_memberCard" name="memberCard" ng-model="memberCard" disabled />
                        <input type="hidden" id="_gubun" ng-model="gubun" disabled />
                        <input type="hidden" id="_chgDate" ng-model="chgDate" disabled />
                        <input type="hidden" id="_chgSeq" ng-model="chgSeq" disabled />
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 현금금액 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.cash"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchCashMemberPointAdjustDtl" ng-model="cash" ng-blur="searchCashKeyEvtMemberPointAdjustDtl()" />
                    </td>
                    <td colspan="2">
                        <label id="lblCashMemberPointAdjustDtl"></label>
                    </td>
                </tr>
                <tr>
                    <%-- 카드금액 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.card"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchCardMemberPointAdjustDtl" ng-model="card"  ng-blur="searchCardKeyEvtMemberPointAdjustDtl()"/>
                    </td>
                    <td colspan="2">
                        <label id="lblCardMemberPointAdjustDtl"></label>
                    </td>
                </tr>
                <tr>
                    <%-- 조정포인트 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.adjustPoint"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchAdjustPointMemberPointAdjustDtl" ng-model="adjustPoint" />
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.remark"/>
                    </th>
                    <td colspan="3">
                        <input type="text" class="sb-input w100" id="srchRemarkMemberPointAdjustDtl" ng-model="remark" />
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="memberPointAdjustDtlFuncSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPointAdjustDtl.js?ver=20210316.01" charset="utf-8"></script>

