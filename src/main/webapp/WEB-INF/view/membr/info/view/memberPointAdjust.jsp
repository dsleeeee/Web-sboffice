<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="wjMemberPointAdjustLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:450px;height:300px;" fade-in="false" fade-out="false">

    <div ng-controller="memberPointAdjustCtrl">
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
                        <input type="text" class="sb-input w70" id="srchMembrNm" ng-model="membrNm" ng-click="popUpMemberClass()" style="float: left;"
                               placeholder="<s:message code="regist.memberPointAdjust.member" /> 선택" readonly/>
                        <input type="hidden" id="_membrNo" name="membrNo" ng-model="membrNo" disabled />
                        <input type="hidden" id="_pointSaveFg" name="pointSaveFg" ng-model="pointSaveFg" disabled />
                        <input type="hidden" id="_memberCash" name="memberCash" ng-model="memberCash" disabled />
                        <input type="hidden" id="_memberCard" name="memberCard" ng-model="memberCard" disabled />
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
                        <input type="text" class="sb-input w100" id="srchcCash" ng-model="cash" ng-blur="searchCashKeyEvt()" />
                    </td>
                    <td colspan="2">
                        <label id="lblCash"></label>
                    </td>
                </tr>
                <tr>
                    <%-- 카드금액 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.card"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchCard" ng-model="card"  ng-blur="searchCardKeyEvt()"/>
                    </td>
                    <td colspan="2">
                        <label id="lblCard"></label>
                    </td>
                </tr>
                <tr>
                    <%-- 조정포인트 --%>
                    <th>
                        <s:message code="regist.memberPointAdjust.adjustPoint"/>
                    </th>
                    <td>
                        <input type="text" class="sb-input w100" id="srchAdjustPoint" ng-model="adjustPoint" />
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
                        <input type="text" class="sb-input w100" id="srchRemark" ng-model="remark" />
                    </td>
                </tr>
                </tbody>
            </table>

            <%-- 저장 버튼 --%>
            <div class="tc mt20">
                <button id="memberPointAdjustFuncSave" class="btn_blue">
                    <s:message code="cmm.save" />
                </button>
            </div>
        </div>
        <%-- //body --%>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberPointAdjust.js?ver=20210316.03" charset="utf-8"></script>