<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<wj-popup id="memberMappingLayer" control="memberMappingLayer" show-trigger="Click" hide-trigger="Click" style="display: none; width:800px;">
  <div class="wj-dialog wj-dialog-columns title">

    <%-- header --%>
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="regist.membr.mappingCd" />
      <a href="#" class="wj-hide btn_close"></a>
    </div>

    <%-- body --%>
    <div class="wj-dialog-body" ng-controller="memberMappingCtrl">

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
          <th><s:message code="regist.membr.ptn.cdCompany"/></th>
          <td><input type="text" id="srchCdCompany" ng-model="cdCompany" maxlength="7" /></td>
          <th></th>
          <td></td>
        </tr>
        <tr>
          <th><s:message code="regist.membr.ptn.cdPartner"/></th>
          <td><input type="text" id="srchCdPartner" ng-model="cdPartner" maxlength="20" /></td>
          <th><s:message code="regist.membr.ptn.lnPartner"/></th>
          <td><input type="text" id="srchLnPartner" ng-model="lnPartner" maxlength="30" /></td>
        </tr>
        <tr>
          <th><s:message code="regist.membr.ptn.noCompany"/></th>
          <td><input type="text" id="srchNoCompany" ng-model="noCompany" maxlength="20" /></td>
          <th><s:message code="regist.membr.ptn.nmCeo"/></th>
          <td><input type="text" id="srchnmCeo" ng-model="nmCeo" maxlength="30" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="searchMappingCd()" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt10">

        <%--- 거래처 매핑 그리드 --%>
        <div class="w100">
          <div class="wj-TblWrap mr10" style="height:373px; overflow-y: hidden;">
            <div id="agencyGrid" style="height: 370px;">
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
                <wj-flex-grid-column header="<s:message code="regist.membr.ptn.cdCompany"/>" binding="cdCompany" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.ptn.cdPartner"/>" binding="cdPartner" width="*" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.ptn.lnPartner"/>" binding="lnPartner" width="*" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.ptn.noCompany"/>" binding="noCompany" width="*" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="regist.membr.ptn.nmCeo"/>" binding="nmCeo" width="*" align="center"></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/info/view/memberMapping.js?ver=20181210.01" charset="utf-8"></script>
