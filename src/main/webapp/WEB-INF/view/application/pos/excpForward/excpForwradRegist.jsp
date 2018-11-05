<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="excpForwardRegistLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.pos.excpForward" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="registCtrl">

      <table class="tblType01 mt20">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="application.pos.excpForward.count"/></th>
          <td><input type="text" id="forwardCnt" ng-model="forwardCnt" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt5 mb20 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('registCtrl')" ><s:message code="application.pos.excpForward.regProd" /></button>
      </div>
    </div>
  </div>

</wj-popup>


<script type="text/javascript" src="/resource/solbipos/js/application/pos/excpForward/excpForwardRegist.js?ver=20180914.01" charset="utf-8"></script>
