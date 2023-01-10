<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup id="sideMenuProdLayer" control="sideMenuProdLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:800px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="sideMenuProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="sideMenu.selectMenu.selProd" />
      <label id="lblsdselClassCd" style="display:none"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <div class="wj-dialog-body">
      <table class="tblType01 mt20">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="sideMenu.selectMenu.prodCd"/></th>
          <td><input type="text" id="srchProdCd" ng-model="prodCd" /></td>
          <th><s:message code="sideMenu.selectMenu.prodNm"/></th>
          <td><input type="text" id="srchProdNm" ng-model="prodNm" /></td>
        </tr>
        <tr>
          <th><s:message code="sideMenu.selectMenu.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input w50" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()" style="float: left;" placeholder="<s:message code="cmm.all"/>" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" ng-model="prodClassCd" disabled />
          </td>
          <td>
            <button type="button" class="btn_skyblue fl mr5" id="btnCancelProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
          </td>
          <td></td>
        </tr>
        <c:if test="${brandUseFg == '1'}">
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
              <tr>
                <%-- 상품브랜드 --%>
                <th><s:message code="sideMenu.selectMenu.prodHqBrand" /></th>
                <td>
                  <div class="sb-select">
                    <wj-combo-box
                      id="srchProdHqBrandCd"
                      ng-model="storeHqBrandCd"
                      items-source="_getComboData('srchProdHqBrandCd')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="srchProdHqBrandCdCombo">
                    </wj-combo-box>
                  </div>
                </td>
                <th></th>
                <td></td>
              </tr>
            </c:if>
          </c:if>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <div class="oh sb-select dkbr">
          <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="listScaleBox"
            ng-model="listScale"
            control="listScaleCombo"
            items-source="_getComboData('listScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
          <%-- 엑셀 다운로드 //TODO --%>
          <%--<button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>--%>
          <button class="btn_skyblue fr" id="btnSearch" ng-click="_pageView('sideMenuProdCtrl',1)" ><s:message code="cmm.search" /></button>
        </div>
      </div>
      <div class="oh mt10">
        <%--- 상품 그리드 --%>
        <div class="w100 fr">
          <div id="sideMenuProdGrid" class="h365">
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
              <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodClassNm"/>" binding="prodClassNm" width="200" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodCd"/>" binding="prodCd" width="*" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="sideMenu.selectMenu.prodNm"/>" binding="prodNm" width="300" is-read-only="true"></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
      </div>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum2 mt20">
      <%-- id --%>
      <ul id="sideMenuProdCtrlPager" data-size="10">
      </ul>
    </div>
    <%--//페이지 리스트--%>

    <div class="wj-dialog-footer">
      <button class="btn {{itemChecked ? 'wj-hide-apply' : ''}} btn_blue" ng-click="selProdConfirm();"><s:message code="sideMenu.selectMenu.selProd"/></button>
      <button class="btn wj-hide btn_gray"><s:message code="cmm.close"/></button>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">
  var gvListScaleBoxData = ${ccu.getListScale()};
  var gubun = "${param.gubun}";
  // 브랜드 사용여부
  var brandUseFg = "${brandUseFg}";
  // 사용자 브랜드
  var userHqBrandCdComboList = ${userHqBrandCdComboList};
</script>
<script type="text/javascript" src="/resource/solbipos/js/base/prod/sideMenu/sideMenuProdView.js?ver=2018112106" charset="utf-8"></script>