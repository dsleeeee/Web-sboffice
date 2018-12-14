<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- 선불 충전 -->
<wj-popup control="chargeLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:900px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prepaid.regist.charge" />
      <a href="javascript:" class="wj-hide btn_close"></a>
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
          <th><s:message code="prepaid.storeCd"/></th>
          <td><input type="text" ng-model="storeCd" id="searchChargeStoreCd"/></td>
          <th><s:message code="prepaid.storeNm"/></th>
          <td><input type="text" ng-model="storeNm" id="searchChargeStoreNm"/></td>
        </tr>
        <tr>
          <th><s:message code="prepaid.membrNo"/></th>
          <td><input type="text" ng-model="memberNo" id="searchChargeMemberNo"/></td>
          <th><s:message code="prepaid.membrNm"/></th>
          <td><input type="text" ng-model="memberNm" id="searchChargeMemberNm" /></td>
        </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('chargeCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt40">
        <%--- 적용매장 그리드 --%>
        <div class="wj-TblWrap mr10" style="height:350px;" ng-controller="chargeCtrl">

          <%-- 충전금액 --%>
          <div class="updownSet oh mb10">
            <span class="fl"><s:message code='prepaid.charge.member' /></span>
            <span class="fr">
              <s:message code='prepaid.prepaidAmt' /> :
              <input type="number" class="sb-input w40 pdl20" id="prepaidAmt"/>
            </span>
          </div>

          <%-- 그리드 --%>
          <div id="regStoreGrid" style="height: 270px;">
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
              <wj-flex-grid-column header="<s:message code="prepaid.storeCd"/>" binding="storeCd" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="prepaid.storeNm"/>" binding="storeNm" width="*" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="prepaid.membrNo"/>" binding="membrNo" width="*"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="prepaid.membrNm"/>" binding="membrNm" width="*" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
          <!--페이지 리스트-->
          <!--//페이지 리스트-->
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/prepaid/charge.js?ver=2018081701" charset="utf-8"></script>
