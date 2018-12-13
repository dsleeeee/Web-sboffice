<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="vanConfigLayer" show-trigger="Click" hide-trigger="Click" style="width:650px;height:450px;" fade-in="false" fade-out="false">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeView.vanConfig.title" />
      <span id="storeViewInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 코너사용여부 선택 --%>
      <wj-combo-box
              ng-model="cornerUseYnFg"
              id="storeCornerUserYnFg"
              items-source="_getComboData('cornerUseYnFg')"
              control="cornerUseYnCombo"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)"
              ng-hide="true">
      </wj-combo-box>

      <div  ng-controller="posTerminalCtrl" ng-if="cornerUseYnVal !== '2'">
        <div class="mt10" style="height:270px; overflow-y: auto;"  >
          <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;" >
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="storeView.posNo"/>" binding="posNo" align="center" width="60" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorFgNm"/>" binding="vendorFgNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorNm"/>" binding="vendorNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorTermnlNo"/>" binding="vendorTermnlNo" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorSerNo"/>" binding="vendorSerNo" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>

        <div class="btnSet">
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>

      <div ng-controller="cornerTerminalCtrl" ng-if="cornerUseYnVal == '2'">
        <div class="mt10" style="height:270px; overflow-y: auto;" >
          <div class="wj-gridWrap" style="overflow-x: hidden; overflow-y: hidden;" >
            <wj-flex-grid
                    control="flex"
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    initialized="initGrid(s,e)"
                    items-source="data"
                    item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="storeView.cornrCd"/>" binding="cornrCd" width="*" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorFgNm"/>" binding="vendorFgNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorNm"/>" binding="vendorNm" align="center" width="100" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorTermnlNo"/>" binding="vendorTermnlNo" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeView.vendorSerNo"/>" binding="vendorSerNo" align="center" width="*" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
        <div class="btnSet">
          <%-- 닫기 --%>
          <span><a href="#" class="btn_blue pd20" ng-click="close()"><s:message code="cmm.close" /></a></span>
        </div>
      </div>

    </div>
  </div>
</wj-popup>
<script>
var cornerUseFg = ${cnv.getEnvCodeExcpAll("2028")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/store/view/vanConfig.js?ver=20181210.01" charset="utf-8"></script>
