<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="hqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}" />
<c:set var="baseUrl" value="/adi/mony/accntManage/" />

<div class="subCon">

  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <%-- 입금계정 --%>
  <div id="depositGrid" class="w50 fl mt10" style="width: 50%" ng-controller="depositViewCtrl">
    <div class="wj-TblWrapBr mr10 pd10" style="height: 600px;">
      <div class="updownSet oh mb10" id="depositBtnArea" style="display: none;">
        <span class="fl bk lh30"><s:message code='accntManage.deposit' /></span>
        <button class="btn_skyblue"  ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
        <button class="btn_skyblue" ng-click="batch()" <c:if test="${orgnFg ne 'HQ'}">style="display: none"</c:if>>
          <s:message code="accntManage.batch" />
        </button>
      </div>
      <div class="wj-gridWrap" style="height:520px">
        <div class="row">
          <wj-flex-grid
                  autoGenerateColumns="false"
                  control="flex"
                  initialized="initGrid(s,e)"
                  sticky-headers="true"
                  selection-mode="Row"
                  items-source="data"
                  frozen-columns="3"
                  item-formatter="_itemFormatter"
                  ime-enabled="true">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.accntCd"/>" binding="accntCd" width="*" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.accntNm"/>" binding="accntNm" width="*"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.accntFg"/>" binding="accntFg" visible="false" ></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="accntManage.useYn"/>" binding="useYn" data-map="useYnDataMap" width="*"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
      </div>
    </div>
  </div>

  <%-- 출금계정 --%>
  <div id="withdrawGrid" class="w50 fr mt10" style="width: 50%" ng-controller="withdrawCtrl">
    <div class="wj-TblWrapBr pd10" style="height: 600px;">
      <div class="updownSet oh mb10" id="withdrawBtnArea" style="display: none;">
        <span class="fl bk lh30"><s:message code='accntManage.withdraw' /></span>
        <button class="btn_skyblue" ng-click="addRow()">
          <s:message code="cmm.add" />
        </button>
        <button class="btn_skyblue" ng-click="delete()">
          <s:message code="cmm.delete" />
        </button>
        <button class="btn_skyblue" ng-click="save()">
          <s:message code="cmm.save" />
        </button>
        <button class="btn_skyblue" ng-click="batch()" <c:if test="${orgnFg ne 'HQ'}">style="display: none"</c:if>>
          <s:message code="accntManage.batch" />
        </button>
      </div>
      <div class="wj-gridWrap" style="height:520px">
        <wj-flex-grid
                autoGenerateColumns="false"
                control="flex"
                initialized="initGrid(s,e)"
                sticky-headers="true"
                selection-mode="Row"
                items-source="data"
                item-formatter="_itemFormatter"
                frozen-columns="2"
                sorted-column="toggleFreeze(false)"
                ime-enabled="true">

          <!-- define columns -->
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.accntCd"/>" binding="accntCd" width="*" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.accntNm"/>" binding="accntNm" width="*"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.accntFg"/>" binding="accntFg" visible="false" ></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="accntManage.useYn"/>" binding="useYn" data-map="useYnDataMap" width="*"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
var orgnFg = "${orgnFg}";
var hqOfficeCd = "${hqOfficeCd}";
var baseUrl = "${baseUrl}";
var useYnFg = ${ccu.getCommCodeExcpAll("067")};
</script>
<script type="text/javascript" src="/resource/solbipos/js/adi/mony/accntManage/deposit.js?ver=20240621.01" charset="utf-8"></script>
