<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/vendrOrder/vendrOrderPop/"/>
<wj-popup id="wjVendrOrderPopLayer" control="wjVendrOrderPopLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrOrderPopLayer" class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrOrder.pop.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div ng-controller="vendrOrderPopCtrl">
        <%-- 발주정보, 발주상품, 발주서 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 발주정보 탭 --%>
            <li>
              <a id="dtlTab" href="#" class="on" ng-click="dtlShow()" ng-if="dtlShowFg"><s:message code="vendrOrder.pop.vendrOrderDtl"/></a>
            </li>
            <%-- 발주상품 탭 --%>
            <li>
              <a id="prodTab" href="#" ng-click="prodShow()" ng-if="prodShowFg"><s:message code="vendrOrder.pop.vendrOrderProd"/></a>
            </li>
            <%-- 발주서 탭 --%>
            <li>
              <a id="reportTab" href="#" ng-click="reportShow()" ng-if="reportShowFg"><s:message code="vendrOrder.pop.vendrOrderReport"/></a>
            </li>
          </ul>
        </div>
      </div>


<script type="text/javascript">
  /** 발주 팝업 controller */
  app.controller('vendrOrderPopCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrOrderPopCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrOrderPopCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.slipFg = data.slipFg;
      $scope.vendrCd = (nvl(data.vendrCd, '') === '' ? '' : data.vendrCd);

      // 신규등록인 경우 발주정보 탭만 활성화
      if($scope.slipNo === '') {
        $scope.dtlShowFg = true;
        $scope.prodShowFg = false;
        $scope.reportShowFg = false;
      }
      // 신규등록이 아닌 경우 모든 탭 활성화
      else {
        $scope.dtlShowFg = true;
        $scope.prodShowFg = true;
        $scope.reportShowFg = true;
      }

      $scope.wjVendrOrderPopLayer.show(true);

      // 발주정보 탭 show
      $scope.dtlShow();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 발주정보 탭 보이기
    $scope.dtlShow        = function () {
      $("#dtlTab").addClass("on");
      $("#prodTab").removeClass("on");
      $("#reportTab").removeClass("on");

      $("#dtlView").show();
      $("#prodView").hide();
      $("#reportView").hide();

      var params = {};
      params.slipNo = $scope.slipNo;
      params.slipFg = $scope.slipFg;
      $scope._broadcast('vendrOrderDtlCtrl', params);
    };


    // 발주상품 탭 보이기
    $scope.prodShow    = function () {
      if($scope.slipNo === null) {
        $scope._popMsg(messages["vendrOrder.pop.not.slip"]);
        return false;
      }

      $("#dtlTab").removeClass("on");
      $("#prodTab").addClass("on");
      $("#reportTab").removeClass("on");

      $("#dtlView").hide();
      $("#prodView").show();
      $("#reportView").hide();

      // angular 그리드 hide 시 깨지므로 refresh()
      var scope = agrid.getScope("vendrOrderProdCtrl");
      scope.flex.refresh();

      var params = {};
      params.slipNo = $scope.slipNo;
      params.slipFg = $scope.slipFg;
      params.vendrCd = $scope.vendrCd;
      $scope._broadcast('vendrOrderProdCtrl', params);
    };


    // 발주서 탭 보이기
    $scope.reportShow = function () {
      if($scope.slipNo === null) {
        $scope._popMsg(messages["vendrOrder.pop.not.slip"]);
        return false;
      }

      $("#dtlTab").removeClass("on");
      $("#prodTab").removeClass("on");
      $("#reportTab").addClass("on");

      $("#dtlView").hide();
      $("#prodView").hide();
      $("#reportView").show();
    };

  }]);
</script>

<%-- 발주정보 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 발주상품 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 발주서 레이어 --%>
<%--<c:import url="/WEB-INF/view/iostock/vendr/vendrOrder/vendrOrderReport.jsp">--%>
  <%--<c:param name="menuCd" value="${menuCd}"/>--%>
  <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

    </div>
  </div>
</wj-popup>
