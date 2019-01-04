<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist/"/>

<wj-popup id="wjDlvrRegistLayer" control="wjDlvrRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
  <div id="dlvrRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="dlvrRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="deliveryCharger.registPopTitle"/>
      <span id="registTitleDlvrNm" class="ml5"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">
      <form id="dlvrForm" name="dlvrForm" ng-submit="submitForm()" novalidate>
        <table class="tblType01">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="deliveryCharger.dlvrCdNm"/><em class="imp">*</em></th>
            <td>
              <input type="text" id="dlvrCd" name="dlvrCd" class="sb-input" style="width:49%" ng-model="dlvr.dlvrCd" readonly/>
              <input type="text" id="dlvrNm" name="dlvrNm" class="sb-input" style="width:49%" ng-model="dlvr.dlvrNm" maxlength="18"
                     required
                     popover-enable="dlvrForm.dlvrNm.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="deliveryCharger.dlvrCdNm"/>은(는) 필수 입력항목 입니다."/>
            </td>
            <th><s:message code="deliveryCharger.carNo"/><em class="imp">*</em></th>
            <td>
              <input type="text" id="carNo" name="carNo" class="sb-input w100" maxlength="14" ng-model="dlvr.carNo"
                     required
                     popover-enable="dlvrForm.carNo.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="deliveryCharger.carNo"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.telNo"/></th>
            <td>
              <input type="text" id="telNo" class="sb-input w100" maxlength="15" ng-model="dlvr.telNo"/>
            </td>
            <th><s:message code="deliveryCharger.hpNo"/></th>
            <td>
              <input type="text" id="hpNo" class="sb-input w100" maxlength="15" ng-model="dlvr.hpNo"/>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.useYn"/></th>
            <td>
              <select id="useYn" ng-model="dlvr.useYn">
                <option value="Y"><s:message code="deliveryCharger.useYnY"/></option>
                <option value="N"><s:message code="deliveryCharger.useYnN"/></option>
              </select>
            </td>
          </tr>
          <tr>
            <th><s:message code="deliveryCharger.remark"/></th>
            <td colspan="3">
              <div>
                <textarea id="remark" class="w100 tArea1" style="height:100px;" ng-model="dlvr.remark"></textarea>
              </div>
            </td>
          </tr>
          </tbody>
        </table>

        <div class="w100 mt10 pdb20 oh bb">
          <div class="fr">
            <%-- 저장 --%>
            <button type="submit" id="btnSave" class="btn_blue mr5">
              <s:message code="cmm.save"/></button>
            <%-- 삭제 --%>
            <button type="button" id="btnDel" class="btn_blue mr5" ng-click="fnDlvrDel()" ng-if="btnDel">
              <s:message code="cmm.delete"/></button>
          </div>
        </div>
        <div style="clear: both;"></div>

      </form>

      <div class="tr mt20 mr10">
        <div class="mt20 oh sb-select dkbr">
          <span class="fl bk lh30 ml10"><s:message code='deliveryCharger.chargeStorage'/></span>
          <div class="tr fr">
            <%-- 창고추가 --%>
            <button type="button" id="btnAddStorage" class="btn_skyblue ml5" ng-click="openPopAddStorage()" ng-if="btnAddStorage">
              <s:message code="deliveryCharger.addStorage"/></button>
            <%-- 창고삭제 --%>
            <button type="button" id="btnDelStorage" class="btn_skyblue ml5" ng-click="delStorage()" ng-if="btnDelStorage">
              <s:message code="deliveryCharger.delStorage"/></button>
          </div>
        </div>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <!--위즈모 테이블-->
        <div class="wj-gridWrap" style="height: 200px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="deliveryCharger.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.storageCd"/>" binding="storageCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="deliveryCharger.storageNm"/>" binding="storageNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

            <!-- enable column filtering-->
            <wj-flex-grid-filter></wj-flex-grid-filter>
          </wj-flex-grid>
        </div>
        <!--//위즈모 테이블-->
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist.js?ver=20181224.01" charset="utf-8"></script>
