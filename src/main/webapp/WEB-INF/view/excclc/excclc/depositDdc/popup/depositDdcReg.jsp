<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="depositDdcRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
    <div class="wj-dialog wj-dialog-columns title" ng-controller="depositDdcRegCtrl">

        <%-- header --%>
        <div class="wj-dialog-header wj-dialog-header-font">
            <label id="lblTitle"></label>
            <a href="#" class="wj-hide btn_close" ng-click="closeDepositDdc()"></a>
        </div>

        <%-- body --%>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w10"/>
                    <col class="w35"/>
                    <col class="w10"/>
                    <col class="w45"/>
                </colgroup>
                <tbody>
                <tr>
                    <%-- 일자 --%>
                    <th><s:message code="depositDdc.date"/></th>
                    <td>
                        <div class="sb-select">
                            <span class="txtIn"> <input id="moneyDate" name="moneyDate" class="w150px" /></span>
                            </span>
                        </div>
                    </td>
                    <%-- 매장 --%>
                    <th><s:message code="depositDdc.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/iostock/cmm/selectStoreS.jsp" flush="true">
                            <jsp:param name="targetId" value="moneyStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    </td>
                </tr>
                <tr>
                    <%-- 계정 --%>
                    <th><s:message code="depositDdc.moneyFg"/></th>
                    <td>
                        <div class="sb-select" style="float: left;">
                            <span class="txtIn w150px">
                              <wj-combo-box
                                      id="moneyFg"
                                      items-source="_getComboData('moneyFg')"
                                      display-member-path="name"
                                      selected-value-path="value"
                                      is-editable="false"
                                      initialized="_initComboBox(s)"
                                      control="moneyFgCombo">
                              </wj-combo-box>
                            </span>
                        </div>
                        <button type="button" id="btn" class="btn_skyblue ml5" ng-click="moneyFgReg()">
                            <s:message code="depositDdc.moneyFgReg"/>
                        </button>
                    </td>
                    <%-- 금액 --%>
                    <th><s:message code="depositDdc.moneyAmt"/></th>
                    <td>
                        <input type="text" class="sb-input w100" id="moneyAmt" name="moneyAmt" maxlength="10" numberOnly />
                    </td>
                </tr>
                <tr>
                    <%-- 비고 --%>
                    <th><s:message code="depositDdc.remark"/></th>
                    <td colspan="3">
                        <input type="text" class="sb-input w100" id="moneyRemark" name="moneyRemark" maxlength="30"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <table class="searchTbl mt10">
                <colgroup>
                    <col class="w100" />
                </colgroup>
                <tbody>
                <tr class="brt">
                    <th class="oh gr">
                        <p class="bk mt5 mb5 lh20" style="font-size: 15px;">
                            <b>저장조건</b>
                        </p>
                        <p class="bk mt5 mb5 lh20" style="font-size: 13px;">
                            * 마감된 매장은 입금/공제내역을 추가할 수 없습니다.<br/>
                            * 마감은 일자가 포함된 월 기준입니다.<br/>
                            * 금액에 음수를 입력하실 수 있습니다.
                        </p>
                    </th>
                </tr>
                </tbody>
            </table>

            <%-- 버튼 영역 --%>
            <div class="wj-dialog-footer">
                <%-- 저장(등록,수정) --%>
                <button class="btn btn_blue" id="btnSave" ng-click="saveDepositDdc()"><s:message code="cmm.save"/></button>
                <%-- 삭제 --%>
                <button class="btn btn_blue" id="btnDel" ng-click="delDepositDdc()" ><s:message code="cmm.del"/></button>
                <%-- 닫기 --%>
                <button class="btn btn_blue" ng-click="closeDepositDdc()"><s:message code="cmm.close"/></button>
            </div>
            <input type="hidden" id="hdMoneyDate" />
            <input type="hidden" id="hdStoreCd" />
            <input type="hidden" id="hdSeqNo" />

        </div>
    </div>
</wj-popup>

<script type="text/javascript">

    // 정수만 입력 체크
    $(function(){
        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^-?[0-9]/g,""));
        });
    });

</script>

<script type="text/javascript" src="/resource/solbipos/js/excclc/excclc/depositDdc/popup/depositDdcReg.js?ver=20220421.01" charset="utf-8"></script>