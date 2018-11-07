<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<div id="kitchenPrintArea" style="display:none;" ng-controller="kitchenPrintCtrl">

  <%-- 환경설정 분류 탭 --%>
  <div class="subTab2 mt20 mb10">
    <ul id="envGroupTab">
      <%-- 매장환경 --%>
      <li><a href="#" id="storeEnvFg" envstFg="00" class="on" ng-click="changeEnvGroup('00')"><s:message code="storeManage.storeEnv" /></a></li>
      <%-- 외식환경 --%>
      <li><a href="#" id="foodEnvFg" envstFg="01" ng-click="changeEnvGroup('01')"><s:message code="storeManage.foodEnv" /></a></li>
      <%-- 유통환경  //TODO 추후 추가 -%>
      <%-- <li><a href="#" id="distributionEnvFg" envstFg="02" ng-click="changeEnvGroup('02')"><s:message code="storeManage.distributionEnv" /></a></li> --%>
      <%-- 포스환경 --%>
      <li><a href="#" id="posEnvFg" envstFg="03" ng-click="changeEnvGroup('03')"><s:message code="storeManage.posEnv" /></a></li>
      <%-- 주방프린터 --%>
      <li><a href="#" id="printEnvFg" envstFg="98" ng-click="changeEnvGroup('98')"><s:message code="storeManage.kitchenPrint" /></a></li>
      <%-- 주방프린터 상품연결--%>
      <li><a href="#" id="printProductEnvFg" envstFg="99" ng-click="changeEnvGroup('99')"><s:message code="storeManage.kitchenPrintProduct" /></a></li>
    </ul>
  </div>

  <div class="wj-TblWrap mr10">
    <div class="oh mb10">
      <%-- 삭제 --%>
      <span class="fr ml5"><a id="btnkitchenPrintDel" href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.delete" /></a></span>
      <%-- 추가 --%>
      <span class="fr"><a id="btnkitchenPrintAdd" href="#" class="btn_grayS2" ng-click="addRow()"><s:message code="cmm.add" /></a></span>
    </div>
  </div>

  <%-- 주방프린터 그리드 --%>
  <div id="kitchenPrintGrid" style="height: 320px;">
    <wj-flex-grid
            autoGenerateColumns="false"
            control="flex"
            initialized="initGrid(s,e)"
            sticky-headers="true"
            selection-mode="Row"
            items-source="data"
            item-formatter="_itemFormatter">

      <!-- define columns -->
      <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.kitchenPrint.posNo"/>" binding="posNo" data-map="posDataMap" width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNo"/>" binding="prterNo" width="40" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNm"/>" binding="prterNm" ></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterKind"/>" binding="prterKindFg" data-map="prterKindDataMap" width="90" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterPort"/>" binding="prterPortFg" data-map="prterPortDataMap" width="90" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterSpeed"/>" binding="prterSpeedFg" data-map="prterSpeedDataMap" is-read-only="true" width="90" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterOutputQty"/>" binding="prterOutputQty" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetIp"/>" binding="prterNetIp" is-read-only="true" width="80"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetPort"/>" binding="prterNetPort" is-read-only="true" width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.useYn"/>" binding="useYn" is-read-only="true" data-map="useYnFgDataMap" width="80" align="center"></wj-flex-grid-column>
    </wj-flex-grid>
  </div>

  <%-- 저장버튼 --%>
  <div class="tc mt10">
    <button type="button" id="btnSaveKitchenPrint" class="btn_blue" ng-click="save()"><s:message code="cmm.save" /></button>
  </div>
</div>
<script>
var prterKind   = ${cnv.getEnvCodeExcpAll("4030")};
var prterPort   = ${cnv.getEnvCodeExcpAll("4031")};
var prterSpeed  = ${cnv.getEnvCodeExcpAll("4032")};
var useYn       = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/kitchenPrint.js?ver=2018102301" charset="utf-8"></script>
