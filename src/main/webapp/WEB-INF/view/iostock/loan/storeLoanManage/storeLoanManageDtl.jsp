<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanManageDtl/storeLoanManageDtl/"/>

<wj-popup id="wjStoreLoanManageDtlLayer" control="wjStoreLoanManageDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="storeOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeLoanManageDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="loan.dtl.title"/><span id="spanDtlTitle" class="ml10"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 조회일자 --%>
          <th><s:message code="loan.dtl.searchDate"/></th>
          <td colspan="3">
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" class="w120"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" class="w120"></span>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

      <%-- 조회 --%>
      <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch" ng-click="searchStoreLoanManageDtl()"><s:message code="cmm.search"/></button>
      </div>

      <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀 다운로드 --%>
        <%--<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>--%>
      </div>

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
            <wj-flex-grid-column header="<s:message code="loan.dtl.loanDate"/>" binding="loanDate" width="120" align="center" format="date"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.outAmt"/>" binding="outAmt" width="120" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.inAmt"/>" binding="inAmt" width="120" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.currLoanAmt"/>" binding="currLoanAmt" width="120" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="loan.dtl.remark"/>" binding="remark" width="*" align="left"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>

    <script type="text/javascript">

      /** 여신상세현황 그리드 controller */
      app.controller('storeLoanManageDtlCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeLoanManageDtlCtrl', $scope, $http, true));

        var arrDate   = getCurDate("-").split("-");
        var date      = new Date(arrDate[0], arrDate[1], arrDate[2]);
        var year      = date.getFullYear();
        var month     = date.getMonth() - 1;
        var day       = date.getDate();
        month         = (month.length == 1 ? "0" : "") + month;
        var startDate = "" + year + month + day;

        $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", startDate);
        $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDt}");

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
          // picker 사용시 호출 : 미사용시 호출안함
          $scope._makePickColumns("storeLoanManageDtlCtrl");

          // 그리드 링크 효과
          s.formatItem.addHandler(function (s, e) {
            if (e.panel === s.cells) {
              var col = s.columns[e.col];

              if (col.format === "date") {
                e.cell.innerHTML = getFormatDate(e.cell.innerText);
              }
            }
          });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeLoanManageDtlCtrl", function (event, data) {
          $scope.storeCd = data.storeCd;
          $scope.storeNm = data.storeNm;

          $scope.wjStoreLoanManageDtlLayer.show(true);
          $("#spanDtlTitle").html('[' + $scope.storeCd + '] ' + $scope.storeNm);

          $scope.searchStoreLoanManageDtl();

          // 기능수행 종료 : 반드시 추가
          event.preventDefault();
        });

        // 리스트 조회
        $scope.searchStoreLoanManageDtl = function () {
          // 파라미터
          var params       = {};
          params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
          params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
          params.storeCd   = $scope.storeCd;

          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          $scope._inquirySub("/iostock/loan/storeLoanManage/storeLoanManageDtl/list.sb", params);
        };

        $scope.excelDown = function () {
          var name = "${menuNm}";
          name     = name + " 테스트";
          wexcel.down($scope.flex, name, name + ".xlsx");
        };

      }]);

    </script>
    <%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
