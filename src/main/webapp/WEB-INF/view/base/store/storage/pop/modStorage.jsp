<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockData/outstockDataDtl/"/>

<wj-popup id="wjStorageModLayer" control="wjStorageModLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width: 500px; left: 442px; top: 85px;"
 class="ng-isolate-scope wj-control wj-content wj-popup wj-state-focus wj-state-focused">
  <div id="storageModLayer" class="wj-dialog wj-dialog-columns" ng-controller="storageModLayerCtrl">
  <form name="storageModForm">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"><s:message code="storageManage.storageInfo"/></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body ng-scope">
		<table class="tblType01">
    		<colgroup>
      			<col class="w15">
      			<col class="w35">
      		</colgroup>
      		<tbody>
      			<tr>
      				<th><s:message code="storageManage.storageCd"/></th>
      				<td>
      					<input type="text" id="modStorageCdAutoSet" class="sb-input w100" ng-model="modStorageCd" maxlength="3" disabled/>
      				</td>
      			</tr>
      			<tr>
      				<th><s:message code="storageManage.storageNm"/></th>
      				<td>
			      		<input type="text" id="modStorageNm" name="storageNm" class="sb-input w100" maxlength="100"
			                     ng-model="modStorageNm"
			                     required
			                     popover-enable="storageModForm.storageNm.$invalid"
			                     popover-placement="bottom-left"
			                     popover-trigger="'mouseenter'"
			                     uib-popover="<s:message code="storageManage.storageNm" />은 필수 입력항목 입니다."/>
      				</td>
      			</tr>
      			<tr>
      				<th><s:message code="storageManage.useYn"/></th>
      				<td>
      				<div class="sb-select">
			            <wj-combo-box
			                    id="modUseYn"
			                    ng-model="modUseYn"
			                    ng-disabled="stock"
			                    items-source="_getComboData('modUseYnComboData')"
			                    display-member-path="name"
			                    selected-value-path="value"
			                    is-editable="false"
			                    initialized="_initComboBox(s)">
			            </wj-combo-box>
			          </div>
				      <div ng-if="stock">
					      <ul class="txtSty2 mt10">
					        <li class="red"><s:message code="storageManage.modUseYnMsg"/></li>
					      </ul>
				      </div>
      				</td>
      			</tr>
      		</tbody>
		</table>
    </div>
    <div class="wj-dialog-footer">
		<button class="btn btn_blue" ng-click="storageModForm.$valid && modStorage()"><s:message code="cmm.edit"/></button>
		<button class="btn wj-hide btn_gray"><s:message code="cmm.close"/></button>
    </div>
  </form>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/store/storage/pop/modStorage.js?ver=20200325.01" charset="utf-8"></script>
