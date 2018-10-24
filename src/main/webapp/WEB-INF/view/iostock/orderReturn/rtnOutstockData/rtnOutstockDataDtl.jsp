<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnOutstockData/rtnOutstockDataDtl/"/>

<wj-popup id="wjRtnOutstockDataDtlLayer" control="wjRtnOutstockDataDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnOutstockDataDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnOutstockDataDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">

      <ul class="txtSty3">
        <li class="red"><s:message code="rtnOutstockData.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnOutstockData.dtl.txt2"/></li>
      </ul>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbSplyUprc"/>" binding="dstbSplyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbUnitQty"/>" binding="dstbUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbEtcQty"/>" binding="dstbEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbAmt"/>" binding="dstbAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbVat"/>" binding="dstbVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.dstbTot"/>" binding="dstbTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnOutstockData.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 출고자료생성 상세 그리드 controller */
  app.controller('rtnOutstockDataDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnOutstockDataDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnOutstockDataDtlCtrl", function (event, data) {
      $scope.dateFg    = data.dateFg;
      $scope.startDate = data.startDate;
      $scope.endDate   = data.endDate;
      $scope.storeCd   = data.storeCd;
      $scope.storeNm   = data.storeNm;
      $scope.slipFg    = data.slipFg;

      $scope.wjRtnOutstockDataDtlLayer.show(true);
      $("#spanDtlTitle").html('['+messages["rtnOutstockData.dtl.orderReturn"]+'] ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);

      $scope.searchRtnOutstockDataDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 출고자료생성 상세내역 리스트 조회
    $scope.searchRtnOutstockDataDtlList = function () {
      // 파라미터
      var params       = {};
      params.dateFg    = $scope.dateFg;
      params.startDate = $scope.startDate;
      params.endDate   = $scope.endDate;
      params.storeCd   = $scope.storeCd;
      params.slipFg    = $scope.slipFg;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/orderReturn/rtnOutstockData/rtnOutstockDataDtl/list.sb", params, function () {
      });
    };
  }]);
</script>
