<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}"/>
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}"/>

<wj-popup id="wjStoreIndexNoLayer" control="wjStoreIndexNoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px; height:480px;">
  <div id="StoreIndexNoLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeIndexNoCtrl">

    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="store.storeIndexNo"/>
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body sc2">
      <div class="updownSet oh mb10">
        <button class="btn_up" id="btnUpAttr" ng-click="rowMoveUp()" >
          <s:message code="cmm.up" />
        </button>
        <button class="btn_down" id="btnDownAttr" ng-click="rowMoveDown()" >
          <s:message code="cmm.down" />
        </button>
        <button class="btn_skyblue ml5" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
      </div>
      <div class="w100 mt10">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px; overflow-y: hidden; overflow-x: hidden;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeIndexNo.storeCd"/>" binding="storeCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeIndexNo.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeIndexNo.indexNo"/>" binding="indexNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

  </div>
</wj-popup>

<script>
  var orgnFg = "${orgnFg}";
  var storeCd = "${storeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/sale/anals/store/rank/storeIndexNo.js?ver=20220420.01" charset="utf-8"></script>
