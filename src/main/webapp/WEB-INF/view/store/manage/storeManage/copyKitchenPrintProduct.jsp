<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="copyKitchenPrintProductLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:600px; height:620px;">
  <div class="wj-dialog wj-dialog-columns title" ng-controller="copyKitchenPrintProductCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeManage.copyKitchenPrintProduct" />
      <a href="" class="wj-hide btn_close" ng-click="close()"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" style="height:570px; overflow-y: hidden;">

      <%-- 섹션 안내바 --%>
      <div class="searchBar_s">
        <p class="noFolding"><s:message code="storeManage.origPrter" /> <s:message code="cmm.select" /></p>
      </div>

      <%-- 기준매장 / 기준프린터 선택영역 --%>
      <div class="tblBr mb20">
        <table class="tblType01">
          <colgroup>
            <col class="w30" />
            <col class="w70" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 기준매장 (매장선택 모듈, 같은 본사내 매장으로 제한) --%>
            <th><s:message code="storeManage.origStore" /></th>
            <td>
              <jsp:include page="/WEB-INF/view/application/layer/searchStoreS.jsp" flush="true">
                <jsp:param name="targetId" value="orgStore"/>
              </jsp:include>
            </td>
          </tr>
          <tr>
            <%-- 기준프린터 (input+버튼 → 프린터선택 팝업) --%>
            <th><s:message code="storeManage.origPrter" /></th>
            <td>
              <input type="hidden" id="orgPrterNo" />
              <input type="text" id="orgPrterNm" class="sb-input" style="cursor:pointer; width:200px;" ng-click="orgPrterShow()" readonly/>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <p class="s12 bk lh20">
                * [기준매장]-[기준프린터]의 출력상품을 현재매장 체크한 프린터에 덮어쓰기 복사합니다.
              </p>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <%-- 현재매장 주방프린터 목록 (체크박스, 복사 대상) --%>
      <div class="updownSet oh mb5">
        <span class="fl bk lh30"><s:message code="storeManage.targetPrter" /></span>
      </div>
      <div class="theGrid" style="height: 260px; overflow-y: hidden; overflow-x: hidden;">
        <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="targetData"
                control="targetFlex"
                initialized="initTargetGrid(s,e)"
                item-formatter="_itemFormatter">
          <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeManage.prterNo"/>" binding="prterNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeManage.prterNm"/>" binding="prterNm" width="2*" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeManage.product.cnt"/>" binding="cnt" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
          <wj-flex-grid-column header="storeCd" binding="storeCd" visible="false"></wj-flex-grid-column>
        </wj-flex-grid>
      </div>

      <div class="btnSet2">
        <%-- 복사 --%>
        <span><a href="#" class="btn_blue" ng-click="copy()"><s:message code="cmm.copy" /></a></span>
        <%-- 닫기 --%>
        <span><a href="#" class="btn_gray" ng-click="close()"><s:message code="cmm.close" /></a></span>
      </div>

    </div>
    <%-- //body --%>

    <%-- 기준프린터 선택 팝업 --%>
    <wj-popup control="orgPrterLayer" show-trigger="Click" hide-trigger="Click" style="display:none; width:400px;">
      <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
          <s:message code="storeManage.origPrter" />
          <a href="" class="wj-hide btn_close" ng-click="orgPrterClose()"></a>
        </div>
        <div class="wj-dialog-body" style="height:300px; overflow-y: auto;">
          <div class="theGrid" style="height: 250px; overflow-y: hidden; overflow-x: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="orgPrterData"
                    control="orgPrterFlex"
                    initialized="initOrgPrterGrid(s,e)">
              <wj-flex-grid-column header="<s:message code="storeManage.prterNo"/>" binding="prterNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeManage.prterNm"/>" binding="prterNm" width="2*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="storeManage.product.cnt"/>" binding="cnt" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="storeCd" binding="storeCd" visible="false"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
      </div>
    </wj-popup>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/copyKitchenPrintProduct.js?ver=20260904.01" charset="utf-8"></script>
