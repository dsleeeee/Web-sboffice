<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="tableGroupLayer" show-trigger="Click" hide-trigger="Click" style="width:400px;height:315px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="tableGroupCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.setting.tableGroup" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <div>
        <table class="tblType01 moreDark">
          <colgroup>
            <col class="w15" />
            <col class="w15" />
            <col class="w45" />
          </colgroup>
          <thead>
          <tr>
            <th><s:message code="cmm.no" /></th>
            <th><s:message code="storeManage.posNo" /></th>
            <th><s:message code="storeManage.tableGroup" /></th>
          </tr>
          </thead>
          <tbody id="tabGrpContent" style="height:300px; overflow-y: auto;">
          </tbody>
        </table>
      </div>

      <%-- 저장 --%>
      <div class="btnSet">
        <span><a id="btnSaveTabGrp" href="#" class="btn_blue" ng-click="save()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/setTableGroup.js?ver=2018102501" charset="utf-8"></script>
