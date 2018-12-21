<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<wj-popup control="excpProdClassPopUpLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="excpProdClassPopUpCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.layer.prodClass"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 300px;">
      <%-- 상품분류 --%>
      <wj-tree-view control="prodClassTree"
        items-source="items"
        initialized="initTreeView(s)"
        display-member-path="'prodClassNm'"
        child-items-path="'children'"
        item-clicked="navTo(prodClassTree)">
      </wj-tree-view>
    </div>
    <div class="wj-dialog-footer">
      <button class="btn wj-hide-apply btn_blue"><s:message code="cmm.chk"/></button>
      <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
    </div>
  </div>
</wj-popup>
<script type="text/javascript" src="/resource/solbipos/js/application/pos/excpForward/searchExcpProdClassCd.js?ver=20181220.01" charset="utf-8"></script>
