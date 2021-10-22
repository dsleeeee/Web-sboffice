<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="wjDlvrEmpRegPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:650px;">

    <div class="wj-dialog wj-dialog-columns title" ng-controller="dlvrEmpRegCtrl">
        <form name="dlvrEmpForm">

            <%-- header --%>
            <div class="wj-dialog-header wj-dialog-header-font">
                <s:message code="dlvrEmp.dlvrEmpInfo"/>
                <a href="#" class="wj-hide btn_close"></a>
            </div>

            <%-- body --%>
            <div class="wj-dialog-body">
                <table class="tblType01">
                    <colgroup>
                        <col class="w15"/>
                        <col class="w35"/>
                        <col class="w15"/>
                        <col class="w35"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <%-- 배달사원번호 --%>
                        <th><s:message code="dlvrEmp.empNo"/></th>
                        <td>
                            <input type="text" id="dlvrEmpNo" name="dlvrEmpNo" class="sb-input w100" ng-model="dlvrEmpInfo.dlvrEmpNo" placeholder="배달사원번호는 자동생성 됩니다." disabled />
                        </td>
                        <%-- 배달사원명 --%>
                        <th><s:message code="dlvrEmp.empNm"/></th>
                        <td>
                            <input typ="text" id="dlvrEmpNm" name="dlvrEmpNm" class="sb-input w100"
                                   ng-model="dlvrEmpInfo.dlvrEmpNm"
                                   required
                                   maxlength="15"
                                   popover-enable="dlvrEmpForm.dlvrEmpNm.$invalid"
                                   popover-placement="bottom-left"
                                   popover-trigger="'mouseenter'"
                                   uib-popover="<s:message code="dlvrEmp.empNm" />은(는) 필수 입력항목 입니다."/>
                        </td>
                    </tr>
                    <tr>
                        <%-- 휴대폰번호 --%>
                        <th><s:message code="dlvrEmp.hpNo"/></th>
                        <td>
                            <input typ="text" id="hpNo" name="hpNo" class="sb-input w100"
                                   ng-model="dlvrEmpInfo.hpNo"
                                   required
                                   maxlength="15"
                                   popover-enable="dlvrEmpForm.hpNo.$invalid"
                                   popover-placement="bottom-left"
                                   popover-trigger="'mouseenter'"
                                   uib-popover="<s:message code="dlvrEmp.hpNo" />은(는) 필수 입력항목 입니다."
                                   onkeyup="this.value=this.value.replace(/[^0-9]/g,'');"/>
                        </td>
                        <%-- SMS수신여부 --%>
                       <th><s:message code="dlvrEmp.smsRecvYn"/></th>
                       <td>
                           <div class="sb-select">
                               <wj-combo-box id="smsRecvYn" name="smsRecvYn"
                                             ng-model="dlvrEmpInfo.smsRecvYn"
                                             items-source="_getComboData('smsRecvYn')"
                                             display-member-path="name"
                                             selected-value-path="value"
                                             is-editable="false"
                                             initialized="_initComboBox(s)"
                                             required
                                             popover-enable="dlvrEmpForm.smsRecvYn.$invalid"
                                             popover-placement="bottom-left"
                                             popover-trigger="'mouseenter'"
                                             uib-popover="<s:message code="dlvrEmp.smsRecvYn" />은(는) 필수 입력항목 입니다."
                                             control="smsRecvYnCombo">
                               </wj-combo-box>
                           </div>
                       </td>
                    </tr>
                    <tr>
                        <%-- 사용여부 --%>
                        <th><s:message code="dlvrEmp.useYn"/></th>
                        <td>
                            <div class="sb-select">
                                <wj-combo-box id="useYn" name="useYn"
                                              ng-model="dlvrEmpInfo.useYn"
                                              items-source="_getComboData('useYn')"
                                              display-member-path="name"
                                              selected-value-path="value"
                                              is-editable="false"
                                              initialized="_initComboBox(s)"
                                              required
                                              popover-enable="dlvrEmpForm.useYn.$invalid"
                                              popover-placement="bottom-left"
                                              popover-trigger="'mouseenter'"
                                              uib-popover="<s:message code="dlvrEmp.useYn" />은(는) 필수 입력항목 입니다."
                                              control="useYnCombo">
                                </wj-combo-box>
                            </div>
                        </td>
                        <th></th>
                        <td></td>
                    </tr>
                    <tr>
                        <%-- 비고 --%>
                        <th><s:message code="dlvrEmp.remark"/></th>
                        <td colspan="3">
                            <input type="text" id="remark" name="remark" class="sb-input w100" ng-model="dlvrEmpInfo.remark"/>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <%-- 버튼 영역 --%>
                <div class="wj-dialog-footer">
                    <%-- 신규 등록 --%>
                    <button class="btn btn_blue" id="btnRegist" ng-click="dlvrEmpForm.$valid && regist()"><s:message code="cmm.new.add"/></button>
                    <%-- 저장 --%>
                    <button class="btn btn_blue" id="btnSave" ng-click="dlvrEmpForm.$valid && save()"><s:message code="cmm.save"/></button>
                    <%-- 닫기 --%>
                    <button class="btn btn_blue" ng-click="closeReg()"><s:message code="cmm.close"/></button>
                </div>
            </div>

        </form>
    </div>

</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/dlvr/info/dlvrEmp/dlvrEmpReg.js?ver=20211020.02" charset="utf-8"></script>