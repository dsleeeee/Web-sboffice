<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup id="vendrCdInsertLayer" control="vendrCdInsertLayer" show-trigger="Click" hide-trigger="Click" style="width:600px;height:600px;display:none;">
  <div ng-controller="vendrCdInsertCtrl">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prodExcelUpload.vendrCd" />
      <span id="vendrCdInsertTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 상품분류 --%>
        <li><a id="vProdClassTab" href="#" ng-if="btnDis" ng-click="changeprodClassCdTab();"><s:message code="prodExcelUpload.prodClassCd" /></a></li>
        <%-- 거래처 --%>
        <li><a id="vVendrTab" href="#" class="on"><s:message code="prodExcelUpload.vendrCd" /></a></li>
     </ul>
        <div class="wj-TblWrap mt20 mb20 fl w100">
          <div class="wj-TblWrapBr mr10 pd20" style="height:450px;">
            <div class="updownSet oh mb10 pd5" id="divBtn">
              <button class="btn_skyblue" id="btnSave" ng-click="vendrCdSave()">
                <s:message code="cmm.save" />
              </button>
            </div>
            <div class="w100 mt10 mb20">
              <div class="wj-gridWrap" style="height:350px; overflow-x: hidden; overflow-y: auto;">
                <wj-flex-grid
                        autoGenerateColumns="false"
                        control="vendrFlex"
                        initialized="initGrid(s,e)"
                        sticky-headers="true"
                        selection-mode="Row"
                        items-source="data"
                        item-formatter="_itemFormatter"
                        ime-enabled="true">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendrNm"/>" binding="vendrNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.ownerNm"/>" binding="ownerNm" width="100" align="center"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.vendorFg"/>" binding="vendorFg" width="100" align="center" data-map="vendorFgDataMap"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="prodExcelUpload.vatIncldYn"/>" binding="vatIncldYn" width="100" align="center" data-map="vatIncldYnDataMap"></wj-flex-grid-column>

                </wj-flex-grid>
              </div>
            </div>
            <input type="hidden" id="hdLevel1" />
          </div>
        </div>
    </div>

  </div>
</wj-popup>


<script>
  var vendorFgData     = ${ccu.getCommCodeExcpAll("011")};
  var vatIncldYnData   = ${ccu.getCommCodeExcpAll("067")};
</script>

<script type="text/javascript" src="/resource/solbipos/js/base/prod/prodExcelUpload/vendrCdInsert.js?ver=20220323.01" charset="utf-8"></script>
