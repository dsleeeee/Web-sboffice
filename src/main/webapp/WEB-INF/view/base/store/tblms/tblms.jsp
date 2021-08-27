<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="orgnCd" value="${sessionScope.sessionInfo.orgnCd}" />
<c:set var="userId" value="${sessionScope.sessionInfo.userId}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="baseUrl" value="/base/store/tblms/"/>
<c:set var="vUserIdChk" value="${sessionScope.sessionInfo.vUserId}"/>
<style type="text/css">
    @import url("http://mob.solbipos.com/mobile/css/button_style1.css");
</style>

<div class="subCon3" ng-controller="tblmsListCtrl">

  <div class="searchBar">
    <a href="#" class="open fl">${menuNm}</a>
  </div>

    <div style="margin-top:10px; text-align:center;">
        <span><button class="btn02-gradient green" style="cursor:pointer;display:none;" id="btnSetting" ng-click="fnTblms('setting');"><s:message code="tblms.setting" /></button></span>
        &nbsp;
        <span><button class="btn02-gradient green" style="cursor:pointer;"  id="btnExplainSetting" ng-click="fnTblms('explainSetting');"><s:message code="tblms.setting" /></button></span>
        &nbsp;
        <span><button class="btn02-gradient green" style="cursor:pointer;" id="btnChromeSetup" ng-click="fnTblms('chromeSetup');"><s:message code="tblms.chromeSetup" /></button></span>
    </div>

    <div style="margin-top:10px; text-align:center;">
        <span><a href="#" class="btn01 first01" id="tblmsOpn" ng-click="fnTblms('tblmsOpn');"><s:message code="tblms.open" /></a></span>
        <span><a href="#" class="btn01 first01" id="tblptOpn" ng-click="fnTblpt('tblptOpn');"><s:message code="tblpt.open" /></a></span>
        <span><a href="#" class="btn01 first01" id="tblptClr" ng-click="fnTblpt('tblptClr');"><s:message code="tblpt.clear" /></a></span>
    </div>

    <div style="margin-top:10px; text-align:center;display:none;">
        <button  class="btn02-gradient purple"   type='button' onclick="window.open('http://192.168.0.72:2020/login/login_check_tbl.jsp?AutoFg=M&user_id=a000005&user_pwd=&login_auto_serial=&appfg=web&tblfg=tblms', 'popup', '');" style='width:200px; height:50px;'>
        창 오픈 샘플
        </button>
    </div>

    <div style="margin-top:10px; text-align:left;display:none;">
        <span><button class="btn02-gradient orange" style="cursor:pointer;"  id="btnExplainSetting2" ng-click="fnTblms('explainSetting');"><s:message code="tblms.setting" /></button></span>
    </div>

    <div style="margin-top:10px; text-align:left;display:none;">
        1/4
    </div>
    <div id="viewStyleLayer1"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        2/4
    </div>
    <div id="viewStyleLayer2"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        3/4
    </div>
    <div id="viewStyleLayer3"   style="height: 406px; width: 924px;display:none;"></div>

    <div style="margin-top:10px; text-align:left;display:none;">
        4/4
    </div>
    <div id="viewStyleLayer4"   style="height: 406px; width: 924px;display:none;"></div>

</div>

<script type="text/javascript">
    var s_userId      = "${userId}";
    var s_storeCd     = "${storeCd}";
    var s_vUserIdChk  = "${vUserIdChk}";

    document.getElementById("viewStyleLayer1").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "01" + ".PNG" + "')";
    document.getElementById("viewStyleLayer2").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "02" + ".PNG" + "')";
    document.getElementById("viewStyleLayer3").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "03" + ".PNG" + "')";
    document.getElementById("viewStyleLayer4").style.backgroundImage = "url('" + "/resource/solbipos/css/img/tblms/" + "/ie_setting_" + "04" + ".PNG" + "')";

</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/tblms/tblms.js?ver=20210715.01" charset="utf-8"></script>

