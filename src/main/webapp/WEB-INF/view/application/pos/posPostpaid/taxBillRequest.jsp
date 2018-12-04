<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="requestTaxBillLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="postpaid.request.taxBill" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="requestTaxBillCtrl">

      <table class="tblType01 mt20">
        <colgroup>
          <col class="w25" />
          <col class="w30" />
        </colgroup>
        <tbody>
        <tr>
          <%-- 회원정보 --%>
          <th><s:message code="postpaid.membrInfo"/></th>
          <td>{{memberInfo}}</td>
        </tr>
        <tr>
          <%-- 미수잔액 --%>
          <th><s:message code="postpaid.balAmt"/></th>
          <td>{{balance}}</td>
        </tr>
        <tr>
          <%-- 세금계산서 발행요청 금액 --%>
          <th><s:message code="postpaid.taxBillAmt"/></th>
          <td><input type="text" id="requestAmt" ng-model="requestAmt" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 요청 --%>
      <%--
      <div class="mt5 mb20 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('requestTaxBillCtrl')" ><s:message code="application.pos.production.regProd" /></button>
      </div>
      --%>
      <div class="wj-dialog-footer">
        <button class="btn btn_blue" ng-click="request()"><s:message code="postpaid.request"/></button>
        <button class="btn btn_gray" ng-click="close()"><s:message code="cmm.close"/></button>
      </div>
    </div>
  </div>

</wj-popup>


<script type="text/javascript" src="/resource/solbipos/js/application/pos/posPostpaid/taxBillRequest.js?ver=20180914.01" charset="utf-8"></script>
