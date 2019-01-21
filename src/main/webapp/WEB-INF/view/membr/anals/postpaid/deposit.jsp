<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!-- 외상발생/입금내역 (후불) 입금 등록 -->
<wj-popup control="depositLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:700px;">

  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="postpaid.regist.deposit" />
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
          <th><s:message code="postpaid.storeCd"/></th>
          <td><input type="text" ng-model="storeCd" id="searchDepositStoreCd"/></td>
          <th><s:message code="postpaid.storeNm"/></th>
          <td><input type="text" ng-model="storeNm" id="searchDepositStoreNm"/></td>
        </tr>
        <tr>
          <th><s:message code="postpaid.membrNo"/></th>
          <td><input type="text" ng-model="membrNo" id="searchDepositMemberNo"/></td>
          <th><s:message code="postpaid.membrNm"/></th>
          <td><input type="text" ng-model="membrNm" id="searchDepositMemberNm" /></td>
        </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 tr">
        <button class="btn_skyblue" id="btnSearch" ng-click="_broadcast('depositCtrl')" ><s:message code="cmm.search" /></button>
      </div>

      <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
                class="w100px fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
      </div>

      <div class="oh mt10">
        <%--- 적용매장 그리드 --%>
        <div class="wj-TblWrap mr10" style="height:370px;" ng-controller="depositCtrl">
          <div class="wj-gridWrap" id="regStoreGrid" style="height: 200px;overflow-y: hidden;">
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
              <wj-flex-grid-column header="<s:message code="postpaid.deposit.storeCd"/>" binding="storeCd" width="*" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.deposit.storeNm"/>" binding="storeNm" width="*" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.membrNo"/>" binding="membrNo" width="*" ></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="postpaid.membrNm"/>" binding="membrNm" width="*" ></wj-flex-grid-column>
            </wj-flex-grid>
          </div>
        </div>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt10">
        <%-- id --%>
        <ul id="depositCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/membr/anals/postpaid/deposit.js?ver=2018081701" charset="utf-8"></script>
