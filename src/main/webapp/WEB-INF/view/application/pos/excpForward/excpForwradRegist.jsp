<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="excpForwardRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:450px;">
  <div class="wj-dialog wj-dialog-columns"  ng-controller="registCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.pos.excpForward" />
      <span> [{{prod.prodCd}}] {{prod.prodNm}} </span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">

      <table class="tblType01 mt10">
        <colgroup>
          <col class="w45" />
          <col class="w55" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="application.pos.excpForward.inQty"/></th>
          <td><input type="text" ng-model="prod.inQty" ng-readonly="true"/></td>
        </tr>
        <tr>
          <th><s:message code="application.pos.excpForward.totSaleQty"/></th>
          <td><input type="text" ng-model="prod.totSaleQty" ng-readonly="true"/></td>
        </tr>
        <tr>
          <th><s:message code="application.pos.excpForward.qtIo"/></th>
          <td><input type="text" ng-model="prod.qtIo" /></td>
          <td><input type="hidden" ng-model="prod.prevQtIo" /></td>
        </tr>
        </tbody>
      </table>

      <div class="mt5 mb20 tr">
        <%-- 등록 --%>
        <button class="btn_skyblue" id="btnSearch" ng-click="save()" >
          <s:message code="application.pos.excpForward.regProd" />
        </button>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/application/pos/excpForward/excpForwardRegist.js?ver=2018091401" charset="utf-8"></script>
