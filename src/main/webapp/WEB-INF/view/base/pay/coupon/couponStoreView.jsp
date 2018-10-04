<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<wj-popup control="couponStoreLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:900px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="coupon.regist.store" />
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
          <th><s:message code="coupon.hqOfficeCd"/></th>
          <td><input type="text" id="srchHqOfficeCd" ng-model="hqOfficeCd" /></td>
          <th><s:message code="coupon.hqOfficeNm"/></th>
          <td><input type="text" id="srchHqOfficeNm" ng-model="hqOfficeNm" /></td>
        </tr>
        <tr>
          <th><s:message code="coupon.storeCd"/></th>
          <td><input type="text" id="srchStoreCd" ng-model="storeCd" /></td>
          <th><s:message code="coupon.storeNm"/></th>
          <td><input type="text" id="srchStoreNm" ng-model="storeNm" /></td>
        </tr>
        </tbody>
      </table>
      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('regStoreCtrl')" ><s:message code="cmm.search" /></button>
      </div>
      <div class="oh mt40">
        <%--- 적용매장 그리드 --%>
        <div class="w50 fl">
          <div class="wj-TblWrap mr10" style="height:350px;" ng-controller="regStoreCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="coupon.regStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="delete()"><s:message code="cmm.del" /></a></span>
            </div>
            <div id="regStoreGrid" style="height: 270px;">
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
                <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" width="*" isReadOnly="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.storeNm"/>" binding="storeNm" width="*" isReadOnly="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
            <!--페이지 리스트-->
            <!--//페이지 리스트-->
          </div>

        </div>

        <%--- 미적용매장 그리드 --%>
        <div class="w50 fr">
          <div class="wj-TblWrap ml10" style="height:350px;" ng-controller="noRegStoreCtrl">
            <div class="oh mb10">
              <span class="fl bk lh20 s14"><s:message code="coupon.noRegStore"/></span>
              <span class="fr"><a href="#" class="btn_grayS2" ng-click="regist()" ><s:message code="coupon.regist"/></a></span>
            </div>
            <div id="noRegStoreGrid" style="height: 270px;">
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
                <wj-flex-grid-column header="<s:message code="coupon.storeCd"/>" binding="storeCd" width="*" isReadOnly="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="coupon.storeNm"/>" binding="storeNm" width="*" isReadOnly="true" ></wj-flex-grid-column>
              </wj-flex-grid>
            </div>
            <!--페이지 리스트-->
            <!--//페이지 리스트-->
          </div>

        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/base/pay/coupon/couponStore.js?ver=20180817" charset="utf-8"></script>
