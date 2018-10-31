<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="kitchenPrintProductArea" style="display:none;" ng-controller="kitchenPrintProductCtrl">

  <%-- 환경설정 분류 탭 --%>
  <div class="subTab2 mt20 mb10">
    <ul id="envGroupTab">
      <%-- 매장환경 --%>
      <li><a href="#" id="storeEnvFg" envstFg="00" class="on" ng-click="changeEnvGroup('00')"><s:message code="storeManage.storeEnv" /></a></li>
      <%-- 외식환경 --%>
      <li><a href="#" id="foodEnvFg" envstFg="01" ng-click="changeEnvGroup('01')"><s:message code="storeManage.foodEnv" /></a></li>
      <%-- 유통환경  //TODO 추후 추가 -%>
      <%-- <li><a href="#" id="distributionEnvFg" envstFg="02" ng-click="changeEnvGroup('02')"><s:message code="storeManage.distributionEnv" /></a></li> --%>
      <%-- 포스환경 --%>
      <li><a href="#" id="posEnvFg" envstFg="03" ng-click="changeEnvGroup('03')"><s:message code="storeManage.posEnv" /></a></li>
      <%-- 주방프린터 --%>
      <li><a href="#" id="printEnvFg" envstFg="98" ng-click="changeEnvGroup('98')"><s:message code="storeManage.kitchenPrint" /></a></li>
      <%-- 주방프린터 상품연결--%>
      <li><a href="#" id="printProductEnvFg" envstFg="99" ng-click="changeEnvGroup('99')"><s:message code="storeManage.kitchenPrintProduct" /></a></li>
    </ul>
  </div>

  <div class="wj-TblWrap mt20 mb40">
    <%-- 주방프린터 --%>
    <div class="w25 fl">
      <div class="wj-TblWrapBr pd20" style="height:380px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.kitchenPrint" /></span>
        </div>
        <%--<div id="kpProductGrid" style="height:400px;"></div>--%>
        <div id="kitchenProductGrid" style="height: 300px; ">
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
            <wj-flex-grid-column header="<s:message code="cmm.no"/>" binding="no" width="40" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.storeCd"/>" binding="storeCd" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.prterNo"/>" binding="prterNo" width="*" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.prterNm"/>" binding="prterNm" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.product.cnt"/>" binding="cnt" width="*"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>

      </div>
    </div>

    <%-- 출력상품 --%>
    <div class="w35 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:380px; overflow-y: auto;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.print.product" /></span>
          <!-- <button class="btn_skyblue">펼침</button> -->
          <%-- 삭제 --%>
          <button type="button" id="btnProdDel" class="btn_skyblue" ng-click="delete()">
            <s:message code="cmm.delete" />
          </button>
        </div>
        <wj-tree-view control="regProductTreeCtrl"
                      items-source="items"
                      display-member-path="'prodNm'"
                      child-items-path="'items'"
                      show-checkboxes="true"
                      checked-items-changed="checkedItems(tvChk)">
        </wj-tree-view>
      </div>
    </div>

    <%-- 미출력상품 --%>
    <div class="w35 fl">
      <div class="wj-TblWrapBr ml10 pd20" style="height:380px; overflow-y: auto;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.no.print.product" /></span>
          <%-- 추가 --%>
          <button type="button" id="btnProdAdd" class="btn_skyblue" ng-click="add()">
            <s:message code="cmm.add" />
          </button>
        </div>
        <wj-tree-view control="noRegProductTreeCtrl"
                      items-source="items"
                      display-member-path="'prodNm'"
                      child-items-path="'items'"
                      show-checkboxes="true"
                      checked-items-changed="checkedItems(tvChk)">
        </wj-tree-view>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/kitchenPrintProduct.js?ver=2018102301" charset="utf-8"></script>
