<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- 외상발생/입금내역 (후불) 입금 등록 -->
<wj-popup control="depositLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:900px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="credit.regist.deposit" />
      <a href="javascript:;" class="wj-hide btn_close"></a>
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
          <th><s:message code="credit.storeCd"/></th>
          <td><input type="text" ng-model="storeCd" id="searchDepositStoreCd"/></td>
          <th><s:message code="credit.storeNm"/></th>
          <td><input type="text" ng-model="storeNm" id="searchDepositStoreNm"/></td>
        </tr>
        <tr>
          <th><s:message code="credit.membrNo"/></th>
          <td><input type="text" ng-model="memberNo" id="searchDepositMemberNo"/></td>
          <th><s:message code="credit.membrNm"/></th>
          <td><input type="text" ng-model="memberNm" id="searchDepositMemberNm" /></td>
        </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('depositCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt40">
        <%--- 적용매장 그리드 --%>
        <div class="wj-TblWrap mr10" style="height:350px;" ng-controller="depositCtrl">
          <div class="updownSet oh mb10">
            <span class="fl"><s:message code='credit.deposit.member' /></span>
            <span class="fr">
              <s:message code='credit.creditAmt' /> :
              <input type="text" class="sb-input w40 pdl20" id="creditAmt"/>
            </span>
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
              <wj-flex-grid-column header="<s:message code="credit.storeCd"/>" binding="creditStoreCd" width="*" isReadOnly="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="credit.storeNm"/>" binding="creditStoreNm" width="*" isReadOnly="true" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="credit.membrNo"/>" binding="membrNo" width="*" isReadOnly="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="credit.membrNm"/>" binding="membrNm" width="*" isReadOnly="true" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
          <!--페이지 리스트-->
          <!--//페이지 리스트-->
        </div>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript" src="/resource/solbipos/js/membr/anals/credit/deposit.js?ver=20180817" charset="utf-8"></script>
