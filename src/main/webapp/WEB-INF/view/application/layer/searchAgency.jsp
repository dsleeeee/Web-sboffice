<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup id="agencyLayer" control="agencyLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:500px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="application.agency.title" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="searchAgencyCtrl">

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
          <th><s:message code="application.agency.search.agencyCd"/></th>
          <td><input type="text" id="srchAgencyCd" ng-model="agencyCd" maxlength="5" /></td>
          <th><s:message code="application.agency.search.agencyCd"/></th>
          <td><input type="text" id="srchAgencyNm" ng-model="agencyNm" maxlength="16" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('searchAgencyCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt10">

        <%--- 대리점 그리드 --%>
        <div class="w100">
          <div class="wj-TblWrap mr10" style="height:150px; overflow-y: hidden;">
            <div id="agencyGrid" style="height: 270px;">
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
                <wj-flex-grid-column header="<s:message code="application.agency.search.agencyCd"/>" binding="agencyCd" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="application.agency.search.agencyNm"/>" binding="agencyNm" width="*" align="center"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchAgency.js?ver=2018102301" charset="utf-8"></script>
