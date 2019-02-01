<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 포스기능 적용 매장선택 레이어 팝업 --%>

<wj-popup control="funcStoreLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:1100px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="func.regStore" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">

      <div ng-controller="regStoreCtrl">

        <%-- 검색조건--%>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <th><s:message code="func.hqOffice"/></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchHqOffice"
                          ng-model="hqOfficeCd"
                          control="hqOfficeCombo"
                          items-source="_getComboData('hqOffice')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          initialized="_initComboBox(s)"
                          selected-index-changed="setSelectedHqOffice(s)">
                  </wj-combo-box>
                </div>
              </td>
              <td></td>
              <td></td>
            </tr>
            <tr>
              <th><s:message code="func.storeCd" /></th>
              <td><input type="text" id="srchStoreCd" maxlength="7"/></td>
              <th><s:message code="func.storeNm" /></th>
              <td><input type="text" id="srchStoreNm" maxlength="20"/></td>
            </tr>
          </tbody>
        </table>
        <%-- 조회버튼 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" id="btnStoreSearch" onclick="search()"><s:message code="cmm.search" /></button>
        </div>

        <%-- 등록 매장 그리드 --%>
        <div class="oh mt40 w50 fl"  >
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y: hidden; overflow-x: hidden" >
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="func.regStore" /></span>
                <span class="fr"><a href="#" class="btn_grayS2" id="btnStoreDel" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>
            <div style="height: 370px; overflow-y: hidden;">
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
                <wj-flex-grid-column header="<s:message code="func.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.hqOfficeNm"/>" binding="hqOfficeNm" width="90" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>

      <%-- 미등록매장 그리드 --%>
      <div class="oh mt40 w50">
        <div class=" "  ng-controller="noRegStoreCtrl" >
          <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden; overflow-x: hidden;">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="func.noRegStore" /></span>
                <span class="fr"><a href="#" class="btn_grayS2" id="btnStoreReg" ng-click="regist()"><s:message code="func.regist" /></a></span>
            </div>
            <div style="height: 370px; overflow-y: hidden;"/>
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
                <wj-flex-grid-column header="<s:message code="func.hqOfficeCd"/>" binding="hqOfficeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.hqOfficeNm"/>" binding="hqOfficeNm" width="90" is-read-only="true" align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.storeCd"/>" binding="storeCd" width="80" is-read-only="true" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="func.storeNm"/>" binding="storeNm" width="*" is-read-only="true" align="left"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script>
  var hqList = ${ccu.getHqOfficeList()};
</script>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/func/store.js?ver=20190122.01" charset="utf-8"></script>

