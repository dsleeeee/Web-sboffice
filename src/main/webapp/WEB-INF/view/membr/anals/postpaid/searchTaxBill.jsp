<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- 외상발생/입금내역 (후불) 입금 등록 -->
<wj-popup control="searchTaxBillLayer" style="display: none;width:700px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="postpaid.regist.deposit" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="searchTaxBillCtrl">

        <%-- 페이지 스케일  --%>
        <wj-combo-box
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)"
                ng-hide="true">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>

      <div class="oh mt10">
        <%--- 적용매장 그리드 --%>
        <div class="wj-TblWrap mr10" style="height:250px;" >
          <div class="wj-gridWrap" id="regStoreGrid" style="height: 200px;overflow-y: hidden;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    control="flex"
                    initialized="initGrid(s,e)"
                    sticky-headers="true"
                    selection-mode="Row"
                    items-source="data"
                    item-formatter="_itemFormatter"
                    is-read-only="true">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.billDate"/>" binding="billDate" width="*"  align="center" is-read-only="true" visible="false" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.billSeq"/>" binding="billSeq" width="*" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.billNo"/>" binding="billNo" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.membrNo"/>" binding="membrNo" width="*"  align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.requestDt"/>" binding="requestDt" width="*" align="center" is-read-only="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.taxBill.requsetAmt"/>" binding="requestAmt" width="*" align="right" is-read-only="true" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt10">
        <%-- id --%>
        <ul id="searchTaxBillCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/searchTaxBill.js?ver=20181214.01" charset="utf-8"></script>
