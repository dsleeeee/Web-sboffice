<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />

<div class="subCon" ng-controller="timeSlotCtrl">

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
  </div>

  <div class="mt20 updownSet oh">
    <div class="txtIn">
      <button class="btn_skyblue" id="addBtn" style="display: none;" ng-click="addRow()">
        <s:message code="cmm.add" />
      </button>
      <button class="btn_skyblue" id="deleteBtn" style="display: none;" ng-click="delete()">
        <s:message code="cmm.delete" />
      </button>
      <button class="btn_skyblue" id="saveBtn" style="display: none;" ng-click="timeChk()">
        <s:message code="cmm.save" />
      </button>
    </div>
  </div>

  <div id="grid" class="w100">
    <div class="wj-gridWrap mt10" style="height:370px; overflow-y: hidden;">
      <div class="row">
        <wj-flex-grid
          autoGenerateColumns="false"
          control="flex"
          initialized="initGrid(s,e)"
          sticky-headers="true"
          selection-mode="Row"
          items-source="data"
          item-formatter="_itemFormatter"
          ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="timeSlot.timeSlotNm"/>" binding="nmcodeNm" maxLength="30" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="timeSlot.startTime"/>" binding="startTime" data-map="timeDataMap" width="*" format="n2"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="timeSlot.endTime"/>" binding="endTime" data-map="timeDataMap" width="*" format="n2"></wj-flex-grid-column>

        </wj-flex-grid>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  var orgnFg      = "${orgnFg}";
  var hqOfficeCd  = "${hqOfficeCd}";
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/store/timeSlot/timeSlot.js?ver=20220520.01" charset="utf-8"></script>

