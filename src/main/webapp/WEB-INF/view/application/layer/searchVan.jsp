<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup id="vanLayer" control="vanLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:500px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.van.title" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="searchVanCtrl">

      <%-- 조회 조건 --%>
      <table class="tblType01 mt5">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="application.van.search.vanCd"/></th>
          <td><input type="text" id="srchVanCd" ng-model="vanCd" maxlength="3" /></td>
          <th><s:message code="application.van.search.vanNm"/></th>
          <td><input type="text" id="srchVanNm" ng-model="vanNm" maxlength="16" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('searchVanCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt10">

        <%--- 벤사 그리드 --%>
        <div class="w100">
          <div class="wj-TblWrap mr10" style="height:150px; overflow-y: hidden;">
            <div id="vanGrid" style="height: 270px;">
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
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" visible="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.van.search.vanCd"/>" binding="vanCd" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.van.search.vanNm"/>" binding="vanNm" width="*" align="center"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchVan.js?ver=2018102301" charset="utf-8"></script>
