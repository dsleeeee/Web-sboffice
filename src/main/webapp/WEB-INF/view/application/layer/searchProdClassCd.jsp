<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="prodClassPopUpLayer" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="prodClassPopUpCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prod.layer.prodClass"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 300px;">
      <%-- 상품분류 --%>
      <wj-tree-view
        class="theTreeAll_cls"
        control="prodClassTree"
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
<script type="text/javascript" src="/resource/solbipos/js/application/layer/searchProdClassCd.js?ver=20181120.05" charset="utf-8"></script>
