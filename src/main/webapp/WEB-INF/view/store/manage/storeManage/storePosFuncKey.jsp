<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 포스 환경 설정 --%>
<div id="posFuncKeyArea" style="display:none;" ng-controller="funcKeyCtrl">

  <%-- 매장환경 분류 탭 --%>
  <c:import url="/WEB-INF/view/store/manage/storeManage/storeInfoTab.jsp">
  </c:import>

  <div class="tl oh">
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
    <div class="sb-select dkbr s12 fl w98px mr10">
      <wj-combo-box
        id="posNo"
        ng-model="fnkeyFg"
        control="fnkeyFgCombo"
        items-source="_getComboData('fnkeyFg')"
        display-member-path="fnkeyFgNm"
        selected-value-path="fnkeyFg"
        is-editable="false"
        initialized="_initComboBox(s)"
        selected-index-changed="changeFnkeyFg(s,e)">
      </wj-combo-box>
    </div>
  </div>

  <div style="height: 200px;">
    <wj-flex-grid
      autoGenerateColumns="false"
      control="flex"
      initialized="initGrid(s,e)"
      sticky-headers="true"
      selection-mode="ListBox"
      items-source="data"
      item-formatter="_itemFormatter"
      allow-dragging="None">

      <!-- define columns -->
      <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNo"/>" binding="fnkeyNo" width="100" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="posFunc.grid.fnkeyNm"/>" binding="fnkeyNm" width="*" is-read-only="true"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="posFunc.grid.imgFileNm"/>" binding="imgFileNm" visible="false"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="posFunc.grid.useYn"/>" binding="fnkeyUsed" width="70" is-read-only="true" visible="false"></wj-flex-grid-column>

    </wj-flex-grid>
  </div>
  <div class="updownSet oh mt10">
    <button class="btn_skyblue" id="btnFuncDelete"><s:message code="cmm.delete" /></button>
  </div>
  <div id="funcKeyGraph" class="funcKeyWrap funcKeyLine2 mt10"></div>

  <%-- 저장 --%>
  <div class="tc mt10">
    <button type="button" id="btnFuncSave" class="btn_blue" ng-click="save()" ><s:message code="cmm.save" /></button>
  </div>

</div>
<script type="text/javascript">
  // Default resources are included in grapheditor resources
  mxLoadResources = false;

  // urlParams is null when used for embedding
  window.urlParams = window.urlParams || {};

  window.MAX_REQUEST_SIZE = window.MAX_REQUEST_SIZE  || 10485760;

  window.RESOURCES_PATH = window.RESOURCES_PATH || '/resource/graph/resources';
  window.RESOURCE_BASE = window.RESOURCE_BASE || window.RESOURCES_PATH + '/message';
  window.STYLE_PATH = window.STYLE_PATH || '/resource/graph/styles';
  window.CSS_PATH = window.CSS_PATH || '/resource/graph/styles';
  window.IMAGE_PATH = window.IMAGE_PATH || '/resource/graph/images';

  window.FUNCKEY_OPEN_URL = window.FUNCKEY_OPEN_URL || '/base/store/posfunc/use/getFuncKeyXml.sb';
  window.FUNCKEY_SAVE_URL = window.FUNCKEY_SAVE_URL || '/base/store/posfunc/use/saveFuncKey.sb';

  window.mxBasePath = window.mxBasePath || '/resource/vendor/mxgraph/src';
  window.mxLanguage = window.mxLanguage || urlParams['lang'];
  window.mxLanguages = window.mxLanguages || ['ko'];

  var selectedRow;

</script>
<script type="text/javascript" src="/resource/vendor/mxgraph/mxClient.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/sanitizer/sanitizer.min.js" charset="utf-8"></script>
<script type="text/javascript" src="/resource/vendor/wijmo/js/grid/wijmo.grid.filter.min.js?ver=520182500" charset="utf-8"></script>
<script type="text/javascript" src="/resource/graph/js/posFuncUseManage.js?ver=2018110702" charset="utf-8"></script>
