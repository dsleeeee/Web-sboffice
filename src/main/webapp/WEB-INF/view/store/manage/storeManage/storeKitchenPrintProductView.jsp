<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>

  <div class="wj-TblWrap mt20 mb40" ng-controller="storeKitchenPrintProductCtrl" ng-init="_broadcast('storeKitchenPrintProductCtrl')">
    <%-- 주방프린터 --%>
    <div class="w25 fl">
      <div class="wj-TblWrapBr pd20" style="height:380px;">
        <div class="updownSet oh mb10">
          <span class="fl bk lh30"><s:message code="storeManage.kitchenPrint" /></span>
        </div>
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
        <wj-tree-view
                control="regProductTreeCtrl"
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
        <wj-tree-view
                control="noRegProductTreeCtrl"
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
<script>

</script>
<script type="text/javascript" src="/resource/solbipos/js/store/manage/storeManage/storeKitchenPrintProduct.js?ver=20181115.01" charset="utf-8"></script>
