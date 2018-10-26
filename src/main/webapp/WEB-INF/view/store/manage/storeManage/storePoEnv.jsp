<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 포스 환경 설정 --%>
<div id="posEnvArea" style="display:none;">
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
<%--
  <div class="mt20">
    &lt;%&ndash; 테이블 그룹설정 &ndash;%&gt;
    <button id="btnSetTabGrp" type="button" class="btn_skyblue" ng-click="settingTableGroup()"><s:message code="storeManage.setting.tableGroup" /></button>
    &lt;%&ndash; 포스 명칭설정 &ndash;%&gt;
    <button id="btnSetPosNm" type="button" class="btn_skyblue" ng-click="settingPosNm()"><s:message code="storeManage.setting.posName" /></button>
    &lt;%&ndash; 포스 설정복사 &ndash;%&gt;
    <button id="btnCopyPosSetting" type="button" class="btn_skyblue" ng-click="copyPosSetting()" ><s:message code="storeManage.copy.posSetting" /></button>
    &lt;%&ndash; 삭제 &ndash;%&gt;
    <button id="btnDeletePos" type="button" class="btn_skyblue" ng-click="deletePos()"><s:message code="cmm.delete" /></button>
  </div>
--%>

  <%-- 포스 환경설정 컨텐츠 --%>
  <div class="mt15" style="height:350px; overflow-y: auto;">
    <div id="posConfigContent" class="mt20" ></div>
    <%-- 저장 --%>
    <div class="tc mt10">
      <button type="button" id="btnSavePos"class="btn_blue" ng-click="save()" ><s:message code="cmm.save" /></button>
    </div>
  </div>
</div>
