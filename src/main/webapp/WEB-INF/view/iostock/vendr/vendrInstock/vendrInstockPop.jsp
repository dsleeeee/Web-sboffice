<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/vendrInstock/vendrInstockPop/"/>
<wj-popup id="wjVendrInstockPopLayer" control="wjVendrInstockPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrInstockPopLayer" class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="popTitle" class="s16 txtIn"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div ng-controller="vendrInstockPopCtrl">
        <%-- 입고/반출정보, 입고/반출상품, 반출서 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 입고/반출정보 탭 --%>
            <li>
              <a id="dtlTab" href="#" class="on" ng-click="dtlShow()" ng-if="dtlShowFg" ng-bind-html="dtlTab"></a>
            </li>
            <%-- 입고/반출상품 탭 --%>
            <li>
              <a id="prodTab" href="#" ng-click="prodShow()" ng-if="prodShowFg" ng-bind-html="prodTab"></a>
            </li>
            <%-- 반출서 탭 --%>
            <li>
              <a id="reportTab" href="#" ng-click="reportShow()" ng-if="reportShowFg" ng-bind-html="reportTab"></a>
            </li>
          </ul>
        </div>
      </div>


<script type="text/javascript" src="/resource/solbipos/js/iostock/vendr/vendrInstock/vendrInstockPop.js?ver=20181224.01" charset="utf-8"></script>

<%-- 입고/반출정보 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 상품 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 반출서 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockReport.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

    </div>
  </div>
</wj-popup>
