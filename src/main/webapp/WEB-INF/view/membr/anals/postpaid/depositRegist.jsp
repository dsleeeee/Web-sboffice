<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="depositRegistLayer" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="depositRegistCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="postpaid.deposit" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <table class="tblType01">
        <colgroup>
          <col class="w30" />
          <col class="w70" />
        </colgroup>
        <tbody>
        <tr>
          <td><s:message code="postpaid.depositAmt" /></td>
          <th>
            <input type="text" class="sb-input w100" id="postpaidAmt" ng-model="postpaidAmt"/>
          </th>
        </tr>
        </tbody>
      </table>

      <%-- 입금 --%>
      <div class="btnSet">
        <span><a id="btnCopy" href="#" class="btn_blue" ng-click="deposit()"><s:message code="postpaid.deposit.regist" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/depositRegist.js?ver=20181214.01" charset="utf-8"></script>
