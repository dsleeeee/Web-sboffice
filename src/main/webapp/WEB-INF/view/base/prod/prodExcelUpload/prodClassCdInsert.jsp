<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="prodClassCdInsertLayer" control="prodClassCdInsertLayer" show-trigger="Click" hide-trigger="Click" style="width:600px;height:600px;display:none;">
  <div ng-controller="prodClassCdInsertCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prodExcelUpload.prodClassCd" />
      <span id="prodClassCdInsertTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 상품분류 --%>
        <li><a id="pProdClassTab" href="#" class="on"><s:message code="prodExcelUpload.prodClassCd" /></a></li>
        <%-- 거래처 --%>
        <li><a id="pVendrTab" href="#" ng-if="btnDis" ng-click="changeVendrCdTab();"><s:message code="prodExcelUpload.vendrCd" /></a></li>
     </ul>
        <div class="wj-TblWrap mt20 mb20 fl w100">
          <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
            <div class="updownSet oh mb10 pd5" id="divBtnLevel1">
              <button class="btn_skyblue" id="btnSaveLevel1" ng-click="prodClassCdSave()">
                <s:message code="cmm.save" />
              </button>
            </div>
            <div class="w100 mt10 mb20">
              <div class="wj-gridWrap" style="height:350px; overflow-x: hidden; overflow-y: auto;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="prodClassFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true"
                        allow-merging="Cells">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.level1"/>" binding="level1" width="100" align="center" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.level2"/>" binding="level2" width="100" align="center" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.level3"/>" binding="level3" width="100" align="center" is-read-only="true" allow-merging="true"></wj-flex-grid-column>
                  <wj-flex-grid-column binding="level12" width="100" align="center" visible="false"></wj-flex-grid-column>
                  <wj-flex-grid-column binding="level123" width="100" align="center" visible="false"></wj-flex-grid-column>

                </wj-flex-grid>
              </div>
            </div>
            <input type="hidden" id="hdLevel1" />
        </div>
    </div>

  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodExcelUpload/prodClassCdInsert.js?ver=20220323.02" charset="utf-8"></script>
