<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockOrderSlip/"/>

<wj-popup id="wjVendrInstockOrderSlipLayer" control="wjVendrInstockOrderSlipLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div id="vendrInstockOrderSlipLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInstockOrderSlipCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrInstock.slip.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 500px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w85"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 발주일자 --%>
          <th><s:message code="vendrInstock.slip.orderDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="srchSlipStartDate" class="w120px"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchSlipEndDate" class="w120px"></span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 거래처 --%>
          <th><s:message code="vendrInstock.slip.vendr"/></th>
          <td>
            <%-- 거래처선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrS.jsp" flush="true">
              <jsp:param name="targetId" value="vendrInstockOrderSlipSelectVendr"/>
            </jsp:include>
            <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchVendrInstockOrderSlipList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 250px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.slipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.orderDate"/>" binding="orderDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.slip.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">

  /** 발주번호 그리드 controller */
  app.controller('vendrInstockOrderSlipCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInstockOrderSlipCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchSlipStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchSlipEndDate", "${sessionScope.sessionInfo.endDate}");

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });

      // 그리드 클릭 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
          var col         = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if (col.binding === "slipNo") { // 전표번호 클릭
            $scope.$apply(function () {
              var vendrInstockDtlScope                  = agrid.getScope('vendrInstockDtlCtrl');
              vendrInstockDtlScope.slipInfo.orderSlipNo = selectedRow.slipNo;
              vendrInstockDtlScope.slipInfo.vendrCd     = selectedRow.vendrCd;
              vendrInstockDtlScope.slipInfo.vendrNm     = selectedRow.vendrNm;
              $scope.popupClose();
            });
          }
        }
      });
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrInstockOrderSlipCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.wjVendrInstockOrderSlipLayer.show(true);

      $scope.searchVendrInstockOrderSlipList();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 발주번호 리스트 조회
    $scope.searchVendrInstockOrderSlipList = function () {
      // 파라미터
      var params     = {};
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
      params.vendrCd = $("#vendrInstockOrderSlipSelectVendrCd").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockOrderSlip/list.sb", params);
    };


    $scope.popupClose = function () {
      $scope.wjVendrInstockOrderSlipLayer.hide(true);
    };


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.vendrInstockOrderSlipSelectVendrShow = function () {
      $scope._broadcast('vendrInstockOrderSlipSelectVendrCtrl');
    };


  }]);

</script>
