<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="storeRecvDtlLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:770px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verRecv.storerecv" />
      <span id="storeTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="storeRecvDtlCtrl">

      <%--- 그리드 --%>
      <div class="oh">
        <div class="wj-TblWrap mr10" style="height:395px; overflow-y:hidden;">
          <div id="regStoreGrid" class="mt10" style="height: 370px; overflow-y: hidden;">
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
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNo"/>" binding="verSerNo" width="110" is-read-only="true"  align="center"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verSerNm"/>" binding="verSerNm" width="*" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verRecvFg"/>" binding="verRecvFg" data-map="verRecvFgDatMap" width="90" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.regDt"/>" binding="regDt" width="120" aling="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="verRecv.verRecvDt"/>" binding="verRecvDt" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
      </div>

      <div class="btnSet2">
        <span><a href="#" id="btnClose" class="btn_gray" ng-click="close()"><s:message code="cmm.close"/></a></span>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verRecv/storeRecvDtl.js?ver=20190108.01" charset="utf-8"></script>
