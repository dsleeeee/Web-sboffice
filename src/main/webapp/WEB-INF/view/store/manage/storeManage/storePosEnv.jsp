<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 포스 환경 설정 --%>
<div id="posEnvArea" style="display:none;" ng-controller="posEnvCtrl">

  <%-- 매장환경 분류 탭 --%>
  <c:import url="/WEB-INF/view/store/manage/storeManage/storeInfoTab.jsp">
  </c:import>

  <div class="tl oh">
    <span class="bk fl lh30 mr10">
      <s:message code="storeManage.posNm" /> :
    </span>

    <div class="sb-select dkbr s12 fl w98px mr10">
      <wj-combo-box
        id="posNo"
        ng-model="posNo"
        control="posNoCombo"
        items-source="_getComboData('posNo')"
        display-member-path="posCdNm"
        selected-value-path="posNo"
        is-editable="false"
        initialized="_initComboBox(s)"
        selected-index-changed="changePosNo(s,e)">
      </wj-combo-box>
    </div>
    <%-- 기본값으로 설정 --%>
    <a href="#" id="btnDefault" class="btn_grayS mr10"><s:message code="storeManage.setting.default.env" /></a>
    <%-- 테이블 그룹설정 --%>
    <button id="btnSetTabGrp" type="button" class="btn_skyblue" ng-click="settingTableGroup()"><s:message code="storeManage.setting.tableGroup" /></button>
    <%-- 포스 명칭설정 --%>
    <button id="btnSetPosNm" type="button" class="btn_skyblue" ng-click="settingPosNm()"><s:message code="storeManage.setting.posName" /></button>
    <%-- 포스 설정복사 --%>
    <button id="btnCopyPosSetting" type="button" class="btn_skyblue" ng-click="copyPosSetting()" ><s:message code="storeManage.copy.posSetting" /></button>
    <%-- 삭제 --%>
    <button id="btnDeletePos" type="button" class="btn_skyblue" ng-click="deletePos()"><s:message code="cmm.delete" /></button>
  </div>

  <%-- 포스 환경설정 컨텐츠 --%>
  <div class="mt30" style="height:310px; overflow-y: auto;">
    <div id="posConfigContent" class="mt20" ></div>
  </div>

  <%-- 저장 --%>
  <div class="tc mt10">
    <button type="button" id="btnSavePos"class="btn_blue" ng-click="save()" ><s:message code="cmm.save" /></button>
  </div>

</div>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storePosEnv.js?ver=20181107.09" charset="utf-8"></script>

<%-- 테이블 그룹설정 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/setTableGroup.jsp">
</c:import>

<%-- 포스 명칭 설정 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/setPosNm.jsp">
</c:import>

<%-- 포스 설정복사 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/copyPosEnv.jsp">
</c:import>
