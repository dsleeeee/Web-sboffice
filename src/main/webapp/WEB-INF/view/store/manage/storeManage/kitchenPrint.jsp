<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="kitchenPrintArea" style="display:none;" ng-controller="kitchenPrintCtrl">

  <%-- 매장환경 분류 탭 --%>
  <c:import url="/WEB-INF/view/store/manage/storeManage/storeInfoTab.jsp">
  </c:import>

  <div class="wj-TblWrap mt20 mr10">
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
      <wj-flex-grid-column header="<s:message code="storeManage.prterKind"/>" binding="prterKindFg" data-map="prterKindDataMap" width="110" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterPort"/>" binding="prterPortFg" data-map="prterPortDataMap" width="90" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterSpeed"/>" binding="prterSpeedFg" data-map="prterSpeedDataMap" width="90" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterOutputQty"/>" binding="prterOutputQty"  width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetIp"/>" binding="prterNetIp" width="80"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterNetPort"/>" binding="prterNetPort" width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.prterCheckYn"/>" binding="prterCheckYn" data-map="useYnFgDataMap" width="80" align="center"></wj-flex-grid-column>
      <wj-flex-grid-column header="<s:message code="storeManage.useYn"/>" binding="useYn" data-map="useYnFgDataMap" width="80" align="center"></wj-flex-grid-column>
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
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/kitchenPrint.js?ver=2018112701" charset="utf-8"></script>
