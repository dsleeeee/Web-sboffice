<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="taxBillDepositLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="taxBillDepositCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="postpaid.taxBill.deposit" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body">
      <div><span>* 아래 [세금계산서 선택] 우측을 클릭하여, </span></div>
      <div class="ml10 mt10"><span>세금계산서 발행 요청건을 선택해주세요.</span></div>

      <table class="tblType01 mt20">
        <colgroup>
          <col class="w30" />
          <col class="w70" />
        </colgroup>
        <tbody>
        <%-- 세금계산서 선택 --%>
        <tr>
          <td><s:message code="postpaid.taxBill.no" /></td>
          <th>
            <input type="text" class="sb-input w100" ng-model="selectedTaxBill.billNo" readonly="readonly" ng-click="searchTaxBill()"/>
          </th>
        </tr>
        <%-- 입금액 --%>
        <tr>
          <td><s:message code="postpaid.depositAmt" /></td>
          <th>
            <input type="text" class="sb-input w100" ng-model="selectedTaxBill.requestAmt" readonly="readonly"/>
          </th>
        </tr>
        </tbody>
      </table>

      <%-- 세금계산서 발행 완료처리 --%>
      <div class="btnSet">
        <span><a id="btnCopy" href="#" class="btn_blue" ng-click="completeTaxBill()"><s:message code="postpaid.deposit.regist" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/taxBillDeposit.js?ver=20181214.01" charset="utf-8"></script>
