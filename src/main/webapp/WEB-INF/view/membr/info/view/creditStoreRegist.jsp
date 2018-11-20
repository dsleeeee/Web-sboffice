<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<wj-popup control="creditStoreRegistLayer" show-trigger="Click" hide-trigger="Click" style="width:750px;height:450px;">
  <div class="wj-dialog wj-dialog-columns title" >

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="regist.membr.store" />
      <span id="memberInfoTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body">
      <div class="oh">
        <%--- 적용매장 그리드 --%>
        <div class="w50 fl">
          <div class="wj-TblWrap mr10" style="height:350px; overflow-y: hidden;" ng-controller="creditStoreRegistCtrl">
            <div class="oh mb10">
              <%-- 페이지 스케일  --%>
              <wj-combo-box
                      ng-hide="true"
                      id="listScaleBox"
                      ng-model="listScale"
                      items-source="_getComboData('listScaleBox')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="initComboBox(s)">
              </wj-combo-box>
              <span class="fl bk lh20 s14"><s:message code="regist.membr.regStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>

            <div id="regStoreGrid" style="height: 220px; overflow-y: auto;">
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
                <wj-flex-grid-column header="<s:message code="regist.membr.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.storeCd"/>" binding="creditStoreCd" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.storeNm"/>" binding="creditStoreNm" width="*" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <%-- id --%>
              <ul id="regStoreCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>
          </div>
        </div>

        <%--- 미적용매장 그리드 --%>
        <div class="w50 fr">
          <div class="wj-TblWrap ml10" style="height:350px;overflow-y: hidden;" ng-controller="creditStoreNoRegistCtrl">
            <div class="oh mb10">
              <%-- 페이지 스케일  --%>
              <wj-combo-box
                      ng-hide="true"
                      id="listScaleBox"
                      ng-model="listScale"
                      items-source="_getComboData('listScaleBox')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="initComboBox(s)">
              </wj-combo-box>
              <span class="fl bk lh20 s14"><s:message code="regist.membr.noRegStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="func.regist"/></a></span>
            </div>
            <div id="noRegStoreGrid" style="height: 220px; overflow-y: auto;">
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
                <wj-flex-grid-column header="<s:message code="regist.membr.hqOfficeCd"/>" binding="hqOfficeCd" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.storeCd"/>" binding="creditStoreCd" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.storeNm"/>" binding="creditStoreNm" width="*" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>

            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
              <%-- id --%>
              <ul id="noRegStoreCtrlPager" data-size="10">
              </ul>
            </div>
            <%--//페이지 리스트--%>

          </div>

        </div>
      </div>

    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/creditStoreRegist.js?ver=20181109.01" charset="utf-8"></script>
