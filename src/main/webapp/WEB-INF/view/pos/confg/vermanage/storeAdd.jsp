<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장추가 레이어 --%>
<wj-popup control="storeAddLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:1160px;height:700px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="verManage.store.registed" />
      <span id="versionDetailTitle" class="ml20"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" >

      <%-- 탭 --%>
      <ul class="subTab">
        <%-- 버전정보 --%>
        <li><a id="storeInfo" href="#" onclick="changeTab()"><s:message code="verManage.verInfo" /></a></li>
        <%-- 적용매장 --%>
        <li><a id="storeEnv" href="#" class="on"><s:message code="verManage.store.registed" /></a></li>
      </ul>

      <div>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <%-- 본사코드 --%>
            <th><s:message code="verManage.store.hqOfficeCd" /></th>
            <td>
              <input type="text" id="srchHqOfficeCd" class="sb-input w100" maxlength="5" ng-value=""/>
            </td>
            <%-- 본사명 --%>
            <th><s:message code="verManage.store.hqOfficeNm" /></th>
            <td>
              <input type="text" id="srchHqOfficeNm" class="sb-input w100" maxlength="20" ng-value=""/>
            </td>
          </tr>
          <tr>
            <%-- 매장코드 --%>
            <th><s:message code="verManage.store.storeCd" /></th>
            <td>
              <input type="text" id="srchStoreCd" class="sb-input w100" maxlength="7" ng-value=""/>
            </td>
            <%-- 매장명 --%>
            <th><s:message code="verManage.store.storeNm" /></th>
            <td>
              <input type="text" id="srchStoreNm" class="sb-input w100" maxlength="20" ng-value=""/>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="mt10 tr">
          <%-- 조회 --%>
          <button id="btnSearchStore" class="btn_skyblue" onclick="search()"><s:message code="cmm.search" /></button>
        </div>
      </div>

      <div class="oh mt40">
        <%-- 등록매장 그리드 --%>
        <div class="w50 fl">
          <div class="wj-TblWrap mr10" style="height:395px; overflow-y: hidden;" ng-controller="addStoreCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="verManage.store.registed"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>
            <div id="regProdGrid" style="height: 370px;">
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
                <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="55" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeCd"/>" binding="storeCd" align="center" width="80" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.posCnt"/>" binding="posCnt"  width="80" align="center" is-read-only="true" ></wj-flex-grid-column>

              </wj-flex-grid>
            </div>
          </div>
        </div>

        <%--- 미등록매장 그리드 --%>
        <div class="w50 fr">
          <div class="wj-TblWrap ml10" style="height:395px; overflow-y: hidden;" ng-controller="allStoreCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="verManage.store.noRegisted" /></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="save()" ><s:message code="verManage.regist.new" /></a></span>
            </div>
            <div id="noRegProdGrid" style="height: 370px;">
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
                <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeCd"/>" binding="hqOfficeCd" align="center" width="66" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.hqOfficeNm"/>" binding="hqOfficeNm" align="left" width="80" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeCd"/>" binding="storeCd" align="center" width="80" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.storeNm"/>" binding="storeNm" align="left" width="*" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.sysStatFg"/>" binding="sysStatFg" data-map="sysStatFgDataMap" width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="verManage.store.posCnt"/>" binding="posCnt"  width="80" align="center" is-read-only="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/pos/confg/verManage/storeAdd.js?ver=20190110.01" charset="utf-8"></script>
