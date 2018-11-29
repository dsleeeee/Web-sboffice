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


<script type="text/javascript">
  /** 입고/반출 팝업 controller */
  app.controller('vendrInstockPopCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInstockPopCtrl', $scope, $http, true));

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrInstockPopCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.slipFg = data.slipFg;
      $scope.vendrCd = (nvl(data.vendrCd, '') === '' ? '' : data.vendrCd);

      // 신규등록인 경우 입고/반출정보 탭만 활성화
      if($scope.slipNo === '') {
        $scope.dtlShowFg = true;
        $scope.prodShowFg = false;
        $scope.reportShowFg = false;
      }
      // 신규등록이 아닌 경우 모든 탭 활성화
      else {
        $scope.dtlShowFg = true;
        $scope.prodShowFg = true;
        if($scope.slipFg === -1) {
          $scope.reportShowFg = true;
        }
      }

      $scope.wjVendrInstockPopLayer.show(true);

      // 입고
      if($scope.slipFg === 1) {
        $("#popTitle").html(messages["vendrInstock.pop.inTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
        $scope.dtlTab = messages["vendrInstock.pop.vendrInstockDtl"];
        $scope.prodTab = messages["vendrInstock.pop.vendrInstockProd"];
      }
      //반출
      else if($scope.slipFg === -1) {
        $("#popTitle").html(messages["vendrInstock.pop.rtnTitle"]); // 현재 팝업이 같은 scope 가 아니라서 jquery 형태로 text 부여
        $scope.dtlTab = messages["vendrInstock.pop.vendrRtnDtl"];
        $scope.prodTab = messages["vendrInstock.pop.vendrRtnProd"];
        $scope.reportTab = messages["vendrInstock.pop.vendrRtnReport"];
      }

      // 입고/반출정보 탭 show
      $scope.dtlShow();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 입고/반출정보 탭 보이기
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
      $scope._broadcast('vendrInstockDtlCtrl', params);
    };


    // 입고/반출상품 탭 보이기
    $scope.prodShow    = function () {
      if($scope.slipNo === null) {
        $scope._popMsg(messages["vendrInstock.pop.not.slip"]);
        return false;
      }

      $("#dtlTab").removeClass("on");
      $("#prodTab").addClass("on");
      $("#reportTab").removeClass("on");

      $("#dtlView").hide();
      $("#prodView").show();
      $("#reportView").hide();

      // angular 그리드 hide 시 깨지므로 refresh()
      var scope = agrid.getScope("vendrInstockProdCtrl");
      scope.flex.refresh();

      var params = {};
      params.slipNo = $scope.slipNo;
      params.slipFg = $scope.slipFg;
      params.vendrCd = $scope.vendrCd;
      $scope._broadcast('vendrInstockProdCtrl', params);
    };


    // 반출서 탭 보이기
    $scope.reportShow = function () {
      if($scope.slipNo === null) {
        $scope._popMsg(messages["vendrInstock.pop.not.slip"]);
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
<%--<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockReport.jsp">--%>
  <%--<c:param name="menuCd" value="${menuCd}"/>--%>
  <%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>

    </div>
  </div>
</wj-popup>
