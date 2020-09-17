<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<wj-popup id="wjDlvrStorageMgrLayer" control="wjDlvrStorageMgrLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:600px;">
  <div id="dlvrStorageMgrLayer" class="wj-dialog wj-dialog-columns" ng-controller="dlvrStorageMgrCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="deliveryCharger.storageMgrPopTitle"/>
      <span id="storageMgrTitleDlvrNm" class="ml5"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div id="grid" class="w100 pdl20 pdr20 pdb20">
      <div class="mt20 oh sb-select dkbr">
        <div class="tr">
          <%-- 창고추가 --%>
          <button type="button" class="btn_skyblue ml5" ng-click="saveAddStore()">
            <s:message code="deliveryCharger.addStorage"/></button>
        </div>
      </div>

      <!--위즈모 테이블-->
      <div class="wj-gridWrap mt10" style="height: 300px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="deliveryCharger.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryCharger.storageCd"/>" binding="storageCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="deliveryCharger.storageNm"/>" binding="storageNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          <!-- enable column filtering-->
          <wj-flex-grid-filter></wj-flex-grid-filter>
        </wj-flex-grid>
      </div>
      <!--//위즈모 테이블-->
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerStorageMgr.js?ver=20181224.05" charset="utf-8"></script>
