<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/orderStockInfo/prodInstockInfo/"/>

<wj-popup id="wjProdInstockInfoLayer" control="wjProdInstockInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div id="prodInstockInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="prodInstockInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="prodInstockInfo.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 450px;">
      <div class="txtIn">
        <p id="subTitle" class="s14 bk fl" ng-bind-html="prodCdNm"></p>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.instockDate"/>" binding="instockDate" width="*" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.slipFg"/>" binding="slipFg" width="50" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inTotQty"/>" binding="inTotQty" width="50" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="prodInstockInfo.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 상품 입고현황 그리드 controller */
  app.controller('prodInstockInfoCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodInstockInfoCtrl', $scope, $http, true));

    // 그리드 전표구분
    $scope.slipFgMap = new wijmo.grid.DataMap([
      {id: "1", name: "<s:message code='prodInstockInfo.slipFgIn'/>"},
      {id: "-1", name: "<s:message code='prodInstockInfo.slipFgRtn'/>"}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("prodInstockInfoCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.prodCd    = data.prodCd;
      $scope.prodNm    = data.prodNm;
      $scope.slipNo    = nvl(data.slipNo, '');
      $scope.startDate = nvl(data.startDate, '');
      $scope.endDate   = nvl(data.endDate, '');

      $scope.wjProdInstockInfoLayer.show(true);
      $scope.prodCdNm = '[' + $scope.prodCd + '] ' + $scope.prodNm;
      // $("#subTitle").html(messages["prodInstockInfo.prodCd"] + ' : ' + $scope.prodCd + messages["prodInstockInfo.prodNm"] + ' : ' + $scope.prodNm);

      $scope.searchProdInstockInfoList();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 상품 입고 리스트 조회
    $scope.searchProdInstockInfoList = function () {
      // 파라미터
      var params       = {};
      params.prodCd    = $scope.prodCd;
      params.slipNo    = $scope.slipNo;
      params.startDate = $scope.startDate;
      params.endDate   = $scope.endDate;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/vendr/orderStockInfo/prodInstockInfo/list.sb", params);
    };

  }]);

</script>
