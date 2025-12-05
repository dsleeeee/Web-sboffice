<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div id="inOutStockDtlView" style="display: none;" ng-controller="inOutStockDtlCtrl">

    <form id="slipForm" name="slipForm" ng-submit="submitForm()">

        <h3 class="h3_tbl pdt5 lh30">
            <s:message code="acquireSlipRegist.dtl.slipRegInfo"/>
            <div class="fr">
                <%-- 저장 --%>
                <button type="submit" class="btn_skyblue mr5" id="btnSave" ng-if="btnSaveShowFg">
                    <s:message code="cmm.save"/></button>
                <%-- 삭제 --%>
                <button type="button" class="btn_skyblue " id="btnDel" ng-click="delete()" ng-if="btnDelShowFg">
                    <s:message code="cmm.delete"/></button>
            </div>
        </h3>

        <table class="tblType01">
            <colgroup>
                <col class="w20"/>
                <col class="w80"/>
            </colgroup>
            <tbody>
            <%-- 입고시 --%>
            <tr ng-if="instockLayerIfFg">
                <%-- 발주/무발주 입고 --%>
                <th><s:message code="acquireSlipRegist.dtl.inOutStockType"/></th>
                <td colspan="3">
                    <div class="sb-select fl" style="display:none;">
                        <span class="txtIn w120px">
                            <wj-combo-box
                                  id="instockType"
                                  ng-model="slipInfo.instockType"
                                  ng-disabled="instockTypeDisabledFg"
                                  items-source="_getComboData('inOutStockType')"
                                  display-member-path="name"
                                  selected-value-path="value"
                                  is-editable="false"
                                  initialized="_initComboBox(s)"
                                  selected-index-changed="selectedIndexChanged(s, e)">
                            </wj-combo-box>
                        </span>
                    </div>
                    <div class="fl ml5" ng-show="orderLayerShowFg">
                        <span class="txtIn s12">
                            <input type="text" id="orderSlipNo" name="orderSlipNo" ng-model="slipInfo.orderSlipNo" class="sb-input tc" style="width: 100px;" maxlength="8" readonly/>
                            <a href="#" class="btn_grayS " ng-click="selectOrderSlip()" ng-if="selectOrderSlipShowFg"><s:message code="acquireSlipRegist.dtl.selectOrderSlipNo"/></a>
                            <input type="hidden" id="vendrCd" name="vendrCd" ng-model="slipInfo.vendrCd" readonly/>
                            <input type="text" id="vendrNm" name="vendrNm" ng-model="slipInfo.vendrNm" class="sb-input tc" style="width: 250px;" readonly/>
                        </span>
                    </div>
                    <div class="fl s12 ml5" ng-show="notOrderLayerShowFg">
                        <%-- 거래처선택 모듈 싱글 선택 사용시 include
                             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                          closeFunc - 팝업 닫기시 호출할 함수
                        --%>
                        <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectVendr.jsp" flush="true">
                            <jsp:param name="targetId" value="instockDtlSelectVendr"/>
                            <jsp:param name="vendrFg" value="1"/>
                        </jsp:include>
                        <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
                    </div>
                </td>
            </tr>
            <%-- 반출시 --%>
            <tr ng-if="rtnLayerIfFg">
                <th><s:message code="acquireSlipRegist.dtl.notOrderRtn"/></th>
                <td colspan="3">
                    <%-- 거래처선택 모듈 싱글 선택 사용시 include
                         param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                                      displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                                      modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                                      closeFunc - 팝업 닫기시 호출할 함수
                    --%>
                    <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectVendr.jsp" flush="true">
                        <jsp:param name="targetId" value="outstockDtlSelectVendr"/>
                        <jsp:param name="vendrFg" value="1"/>
                    </jsp:include>
                    <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 입고/반출일자 --%>
                <th ng-bind-html="instockDateTxt"></th>
                <td>
                    <div class="sb-select">
                        <span class="txtIn w120px">
                            <wj-input-date
                                    value="instockDate"
                                    ng-model="slipInfo.instockDate"
                                    control="orderDateCombo"
                                    min="2000-01-01"
                                    max="2099-12-31"
                                    initialized="_initDateBox(s)">
                            </wj-input-date>
                      </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 비고 --%>
                <th><s:message code="acquireSlipRegist.dtl.remark"/></th>
                <td>
                    <div>
                        <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="slipInfo.remark"></textarea>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 매장선택 --%>
                <th><s:message code="cmm.store.select"/></th>
                <td>
                    <%-- 매장선택 모듈 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/kookmin/acquire/acquireSlipRegist/acquireSelectStore.jsp" flush="true">
                        <jsp:param name="targetTypeFg" value="S"/>
                        <jsp:param name="targetId" value="inOutStockDtlStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 매입구분 --%>
                <th>
                    <s:message code="acquireSlipRegist.dtl.tradeFg"/>
                </th>
                <td>
                    <div class="sb-select fl">
                        <span class="txtIn w120px">
                            <wj-combo-box
                                    id="tradeFg"
                                    ng-model="slipInfo.tradeFg"
                                    control="tradeFgCombo"
                                    items-source="_getComboData('tradeFgComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="selectedIndexChanged(s, e)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <%-- 구분 --%>
                <th>
                    <s:message code="acquireSlipRegist.dtl.tradeForm"/>
                </th>
                <td>
                    <div class="sb-select fl">
                        <span class="txtIn w120px">
                            <wj-combo-box
                                    id="tradeForm"
                                    ng-model="slipInfo.tradeForm"
                                    control="tradeFormCombo"
                                    items-source="_getComboData('tradeFormComboData')"
                                    display-member-path="name"
                                    selected-value-path="value"
                                    is-editable="false"
                                    initialized="_initComboBox(s)"
                                    selected-index-changed="selectedIndexChanged(s, e)">
                            </wj-combo-box>
                        </span>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>

    <h3 class="h3_tbl pdt5 lh30 mt10" >
        <s:message code="acquireSlipRegist.dtl.slipDtlInfo"/>
        <div class="fr" ng-if="procLayerIfFg">
            <%--출고창고 --%>
            <span class="txtIn w150px sb-select fl mr5" style="<c:if test="${storageEnvstVal == '0'}">=display: none;</c:if> visibility:hidden;" >
                <wj-combo-box
                     id="saveVendrDtlOutStorageCd"
                     ng-model="slipInfo.outStorageCd"
                     ng-disabled="outStorageCdDisabledFg"
                     items-source="_getComboData('saveVendrDtlOutStorageCd')"
                     display-member-path="name"
                     selected-value-path="value"
                     is-editable="false"
                     initialized="_initComboBox(s)"
                     control="outStorageCdCombo">
                </wj-combo-box>
            </span>
            <p class="s12 bk lh30 fl"><s:message code="acquireSlipRegist.dtl.procFg"/>: [</p>
            <p class="s12 bk lh30 fl red" ng-bind="procNm"></p>
            <p class="s12 bk lh30 fl">]</p>
            <p class="s12 bk lh30 fl ml10"><s:message code="acquireSlipRegist.dtl.dtlCnt"/>: [</p>
            <p class="s12 bk lh30 fl red" ng-bind="slipInfo.dtlCnt"></p>
            <p class="s12 bk lh30 fl mr10">]</p>
            <button type="button" id="btnDtlConfirm" class="btn_skyblue ml5" ng-click="confirm('1')" ng-if="btnDtlConfirmShowFg">
                <s:message code="acquireSlipRegist.dtl.confirm"/></button>
            <button type="button" id="btnDtlConfirmCancel" class="btn_skyblue ml5" ng-click="confirm('0')" ng-if="btnDtlConfirmCancelShowFg">
                <s:message code="acquireSlipRegist.dtl.confirmCancel"/></button>
        </div>
    </h3>
    <table class="tblType01">
        <colgroup>
            <col class="w20"/>
            <col class="w30"/>
            <col class="w20"/>
            <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 등록일시 --%>
            <th><s:message code="acquireSlipRegist.dtl.regDt"/></th>
            <td><span id="slipDtlRegDt" class="pd5 txtIn s12" ng-bind="slipInfo.regDt"></span>
            </td>
            <%-- 등록자 --%>
            <th><s:message code="acquireSlipRegist.dtl.regNm"/></th>
            <td><span id="slipDtlRegNm" class="pd5 txtIn s12" ng-bind="slipInfo.regNm"></span>
            </td>
        </tr>
        <tr>
            <%-- 확정일시 --%>
            <th><s:message code="acquireSlipRegist.dtl.confmDt"/></th>
            <td><span id="slipDtlConfmDt" class="pd5 txtIn s12" ng-bind="slipInfo.confmDt"></span></td>
            <%-- 확정자 --%>
            <th><s:message code="acquireSlipRegist.dtl.confmNm"/></th>
            <td><span id="slipDtlConfmNm" class="pd5 txtIn s12" ng-bind="slipInfo.confmNm"></span></td>
        </tr>
        <tr>
            <%-- 입고총수량 --%>
            <th ng-bind-html="instockTotQtyTxt"></th>
            <%--       <th><s:message code="acquireSlipRegist.dtl.inTotQty"/></th> --%>
            <td><span id="slipDtlInTotQty" class="pd5 txtIn s12" ng-bind="slipInfo.inTotQty"></span>
            </td>
            <%-- 입고총금액 --%>
            <th ng-bind-html="instockTotAmtTxt"></th>
            <%--       <th><s:message code="acquireSlipRegist.dtl.inTotAmt"/></th> --%>
            <td><span id="slipDtlInTotAmt" class="pd5 txtIn s12" ng-bind="slipInfo.inTot"></span>
            </td>
        </tr>
        </tbody>
    </table>

</div>

<script type="text/javascript">
    // [1241 창고사용여부] 환경설정값
    var storageEnvstVal = "${storageEnvstVal}";
</script>

<script type="text/javascript" src="/resource/solbipos/js/kookmin/acquire/acquireSlipRegist/inOutStockDtl.js?ver=20220714.03" charset="utf-8"></script>

