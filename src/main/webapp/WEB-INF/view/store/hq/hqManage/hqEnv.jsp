<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="hqEnvLayer" show-trigger="Click" hide-trigger="Click" style="width:700px;height:620px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title" >

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqManage.envSetting" />
      <span id="storeEnvTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="hqEnvCtrl">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 매장정보 --%>
        <li><a id="hqInfoTab" href="#" ng-click="showHqInfo();"><s:message code="hqManage.hqInfo" /></a></li>
        <%-- 매장환경 --%>
        <li><a id="envSettingTab" href="#" class="on" ><s:message code="hqManage.envSetting" /></a></li>
        <%-- 메뉴관리 --%>
        <%--<li><a id="menuSettingTab" href="#" ng-click="showMenuSetting();"><s:message code="hqManage.menuSetting" /></a></li>--%>
      </ul>

      <%-- 환경설정 컨텐츠 --%>
      <div class="mt30" style="height:395px; overflow-y: auto;">
        <div id="configContent" class="mt10" ></div>
      </div>

      <div class="btnSet mb10">
        <%-- 저장 --%>
        <span><a href="#" class="btn_blue pd20" id="btnSave" ng-click="save()"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript">
var envstGrp = ${ccu.getCommCodeExcpAll("004")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/hqManage/hqEnv.js?ver=20181106.01" charset="utf-8"></script>

