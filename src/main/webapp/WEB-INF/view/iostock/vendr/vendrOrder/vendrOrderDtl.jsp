<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrOrder/vendrOrderDtl/"/>

<div id="dtlView" style="display: none;" ng-controller="vendrOrderDtlCtrl">

  <form id="slipForm" name="slipForm" ng-submit="submitForm()">

    <h3 class="h3_tbl pdt5 lh30">
      <s:message code="vendrOrder.dtl.slipRegInfo"/>
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
      <tr>
        <%-- 거래처 --%>
        <th><s:message code="vendrOrder.dtl.vendr"/></th>
        <td>
          <%-- 거래처선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
          --%>
          <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
            <jsp:param name="targetId" value="vendrOrderDtlSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
      </tr>
      <tr>
        <%-- 발주타입 --%>
        <th><s:message code="vendrOrder.dtl.orderType"/></th>
        <td>
          <div class="sb-select" style="float: left;">
            <span class="txtIn w150px">
              <wj-combo-box
                id="orderType"
                ng-model="slipInfo.orderType"
                items-source="_getComboData('orderType')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
          </div>
          <button type="button" id="btn" class="btn_skyblue ml5" ng-click="vendrOrderTypeRegPop()">
            <s:message code="vendrOrder.dtl.vendrOrderType"/>
          </button>
        </td>
      </tr>
      <tr>
        <%-- 발주일자 --%>
        <th><s:message code="vendrOrder.dtl.orderDate"/></th>
        <td>
          <div class="sb-select">
            <%--<span class="txtIn"><input id="orderDate" ng-model="slipInfo.orderDate" class="w120px"></span>--%>
            <span class="txtIn w120px">
            <wj-input-date
              value="orderDate"
              ng-model="slipInfo.orderDate"
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
        <%-- 입고요청일자 --%>
        <th><s:message code="vendrOrder.dtl.orderReqDate"/></th>
        <td>
          <div class="sb-select">
            <%--<span class="txtIn"><input id="orderReqDate" ng-model="slipInfo.orderReqDate" class="w120px"></span>--%>
            <span class="txtIn w120px">
            <wj-input-date
              value="orderReqDate"
              ng-model="slipInfo.orderReqDate"
              control="orderReqDateCombo"
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
        <th><s:message code="vendrOrder.dtl.remark"/></th>
        <td>
          <div>
            <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="slipInfo.remark"></textarea>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  </form>

  <h3 class="h3_tbl pdt5 lh30 mt10">
    <s:message code="vendrOrder.dtl.slipDtlInfo"/>
    <div class="fr" ng-if="procLayerIfFg">
      <p class="s12 bk lh30 fl"><s:message code="vendrOrder.dtl.procFg"/>: [</p>
      <p class="s12 bk lh30 fl red" ng-bind="slipInfo.procNm"></p>
      <p class="s12 bk lh30 fl">]</p>
      <p class="s12 bk lh30 fl ml10"><s:message code="vendrOrder.dtl.dtlCnt"/>: [</p>
      <p class="s12 bk lh30 fl red" ng-bind="slipInfo.dtlCnt"></p>
      <p class="s12 bk lh30 fl">]</p>
      <%--<div class="sb-select">--%>
      <span class="txtIn w100px ml10">
          <wj-combo-box
            id="procFg"
            ng-model="slipInfo.procFg"
            items-source="_getComboData('procFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)"
            ng-if="btnProcFgShowFg">
          </wj-combo-box>
        </span>
      <%--</div>--%>
      <%--<a href="#" class="btn_grayS2" ng-click=""><s:message code="vendrOrder.dtl.procFgUpdate"/></a>--%>
      <button type="button" id="btnDtlProcFgUpdate" class="btn_skyblue ml5" ng-click="procFgUpdate()" ng-if="btnProcFgShowFg">
        <s:message code="vendrOrder.dtl.procFgUpdate"/></button>
    </div>
  </h3>
  <table class="tblType01">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 등록일시 --%>
      <th><s:message code="vendrOrder.dtl.regDt"/></th>
      <td><span id="slipDtlRegDt" class="pd5 txtIn s12" ng-bind="slipInfo.orderRegDt"></span>
      </td>
      <%-- 등록자 --%>
      <th><s:message code="vendrOrder.dtl.regNm"/></th>
      <td><span id="slipDtlRegNm" class="pd5 txtIn s12" ng-bind="slipInfo.orderRegNm"></span>
      </td>
    </tr>
    <tr>
      <%-- 확정일시 --%>
      <th><s:message code="vendrOrder.dtl.confmDt"/></th>
      <td><span id="slipDtlConfmDt" class="pd5 txtIn s12" ng-bind="slipInfo.confmDt"></span></td>
      <%-- 확정자 --%>
      <th><s:message code="vendrOrder.dtl.confmNm"/></th>
      <td><span id="slipDtlConfmNm" class="pd5 txtIn s12" ng-bind="slipInfo.confmNm"></span></td>
    </tr>
    <tr>
      <%-- 완료일시 --%>
      <th><s:message code="vendrOrder.dtl.endDt"/></th>
      <td><span id="slipDtlEndDt" class="pd5 txtIn s12" ng-bind="slipInfo.endDt"></span></td>
      <%-- 완료자 --%>
      <th><s:message code="vendrOrder.dtl.endNm"/></th>
      <td><span id="slipDtlEndNm" class="pd5 txtIn s12" ng-bind="slipInfo.endNm"></span></td>
    </tr>
    <tr>
      <%-- 발주총수량 --%>
      <th><s:message code="vendrOrder.dtl.orderTotQty"/></th>
      <td><span id="slipDtlOrderTotQty" class="pd5 txtIn s12" ng-bind="slipInfo.orderTotQty"></span>
      </td>
      <%-- 발주총금액 --%>
      <th><s:message code="vendrOrder.dtl.orderTotAmt"/></th>
      <td><span id="slipDtlOrderTotAmt" class="pd5 txtIn s12" ng-bind="slipInfo.orderTot"></span>
      </td>
    </tr>
    <tr>
      <%-- 입고총수량 --%>
      <th><s:message code="vendrOrder.dtl.inTotQty"/></th>
      <td><span id="slipDtlInTotQty" class="pd5 txtIn s12" ng-bind="slipInfo.inTotQty"></span>
      </td>
      <%-- 입고총금액 --%>
      <th><s:message code="vendrOrder.dtl.inTotAmt"/></th>
      <td><span id="slipDtlInTotAmt" class="pd5 txtIn s12" ng-bind="slipInfo.inTotAmt"></span>
      </td>
    </tr>
    <tr>
      <%-- 최초입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inFirstDate"/></th>
      <td>
        <span id="slipDtlInFirstDate" class="pd5 txtIn s12" ng-bind="slipInfo.inFirstDate"></span>
      </td>
      <%-- 최종입고일자 --%>
      <th><s:message code="vendrOrder.dtl.inLastDate"/></th>
      <td>
        <span id="slipDtlInLastDate" class="pd5 txtIn s12" ng-bind="slipInfo.inLastDate"></span>
      </td>
    </tr>
    </tbody>
  </table>

</div>

<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrOrder/vendrOrderDtl.js?ver=20181224.05" charset="utf-8"></script>

<%-- 발주타입등록 팝업 레이어--%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderTypeRegPop.jsp">
</c:import>

