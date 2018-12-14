<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="posNmLayer" show-trigger="Click" hide-trigger="Click" style="width:400px;height:315px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="posNmCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.setting.posName" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <div style="height:150px; overflow-y: auto;">
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
            <th><s:message code="storeManage.posNm" /></th>
          </tr>
          </thead>
          <tbody id="posNmContent">
          </tbody>
        </table>
      </div>

      <%-- 저장 --%>
      <div class="btnSet" class="mt10 mb10">
        <span><a id="btnSavePosNm" href="#" class="btn_blue" ng-click="savePosNm()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/setPosNm.js?ver=2018110601" charset="utf-8"></script>
