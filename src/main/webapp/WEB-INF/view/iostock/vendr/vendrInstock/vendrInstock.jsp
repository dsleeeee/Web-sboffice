<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstock/"/>

<div class="subCon" ng-controller="vendrInstockCtrl">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15"/>
      <col class="w35"/>
      <col class="w15"/>
      <col class="w35"/>
    </colgroup>
    <tbody>
    <tr>
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn w100px">
            <wj-combo-box
              id="srchDateFg"
              ng-model="dateFg"
              items-source="_getComboData('srchDateFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
          <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 전표번호 --%>
      <th><s:message code="vendrInstock.slipNo"/></th>
      <td>
        <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNo" class="sb-input w100" maxlength="8"/>
      </td>
      <%-- 발주전표번호 --%>
      <th><s:message code="vendrInstock.orderSlipNo"/></th>
      <td>
        <input type="text" id="srchOrderSlipNo" name="srchOrderSlipNo" ng-model="orderSlipNo" class="sb-input w100" maxlength="8"/>
      </td>
    </tr>
    <tr>
      <%-- 전표구분 --%>
      <th><s:message code="vendrInstock.slipFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchSlipFg"
              ng-model="slipFg"
              items-source="_getComboData('srchSlipFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <%-- 진행구분 --%>
      <th><s:message code="vendrInstock.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w120px">
            <wj-combo-box
              id="srchProcFg"
              ng-model="procFg"
              items-source="_getComboData('srchProcFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('vendrInstockCtrl', 1)">
      <s:message code="cmm.search"/></button>
  </div>

  <div class="mt20 tr">
    <%-- 입고신규등록 --%>
    <button type="button" class="btn_skyblue ml5" id="btnInRegist" ng-click="newVendrInstock(1)">
      <s:message code="vendrInstock.inRegist"/></button>
    <%-- 반출신규등록 --%>
    <button type="button" class="btn_skyblue ml5" id="btnOutRegist" ng-click="newVendrInstock(-1)">
      <s:message code="vendrInstock.rtnRegist"/></button>
  </div>

  <div class="w100 mt10">
    <%--위즈모 테이블--%>
    <div class="wj-gridWrap" style="height: 350px;">
      <wj-flex-grid
        autoGenerateColumns="false"
        selection-mode="Row"
        items-source="data"
        control="flex"
        initialized="initGrid(s,e)"
        is-read-only="false"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="vendrInstock.slipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.slipFg"/>" binding="slipFg" width="60" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.vendr"/>" binding="vendrCd" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.procFg"/>" binding="procFg" width="60" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.instockType"/>" binding="instockType" width="80" align="center" is-read-only="true" data-map="instockTypeMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="vendrInstock.instockDate"/>" binding="instockDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="vendrInstockCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
</div>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 입고/반출 그리드 controller */
  app.controller('vendrInstockCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInstockCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");

    $scope._setComboData("srchDateFg", [
      {"name": messages["vendrInstock.regDate"], "value": "reg"},
      {"name": messages["vendrInstock.instockDate"], "value": "in"}
    ]);

    // 조회조건 전표구분
    $scope._setComboData("srchSlipFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["vendrInstock.slipFgIn"], "value": "1"},
      {"name": messages["vendrInstock.slipFgRtn"], "value": "-1"}
    ]);

    // 조회조건 진행구분
    $scope._setComboData("srchProcFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["vendrInstock.procFg0"], "value": "0"},
      {"name": messages["vendrInstock.procFg1"], "value": "1"}
    ]);

    // 그리드 전표구분
    $scope.slipFgMap = new wijmo.grid.DataMap([
      {id: "1", name: "<s:message code='vendrInstock.slipFgIn'/>"},
      {id: "-1", name: "<s:message code='vendrInstock.slipFgRtn'/>"}
    ], 'id', 'name');

    // 그리드 진행구분
    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "0", name: "<s:message code='vendrInstock.procFg0'/>"},
      {id: "1", name: "<s:message code='vendrInstock.procFg1'/>"}
    ], 'id', 'name');

    // 그리드 발주/무발주 구분
    $scope.instockTypeMap = new wijmo.grid.DataMap([
      {id: "Y", name: "<s:message code='vendrInstock.orderInstock'/>"},
      {id: "N", name: "<s:message code='vendrInstock.notOrderInstock'/>"}
    ], 'id', 'name');


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("vendrInstockCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }

          // 전표구분이 반출이면 글씨색을 red 로 변경한다.
          if (col.binding === "slipFg") {
            var item = s.rows[e.row].dataItem;
            if (item.slipFg === -1) {
              wijmo.addClass(e.cell, 'red');
            }
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
            var params     = {};
            params.slipNo  = selectedRow.slipNo;
            params.slipFg  = selectedRow.slipFg;
            params.vendrCd = selectedRow.vendrCd;
            $scope._broadcast('vendrInstockPopCtrl', params);
          }
        }
      });
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrInstockCtrl", function (event, data) {
      $scope.searchVendrInstockList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 입고/반출 리스트 조회
    $scope.searchVendrInstockList = function () {
      // 파라미터
      var params       = {};
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstock/list.sb", params);
    };


    // 입고/반출 신규등록 (입고 - slipFg : 1, 반출 - slipFg : -1)
    $scope.newVendrInstock = function (slipFg) {
      var params     = {};
      params.slipNo  = '';
      params.slipFg  = slipFg;
      params.vendrCd = '';
      $scope._broadcast('vendrInstockPopCtrl', params);
    };


  }]);
</script>

<%-- 입고/반출 상세 팝업전용 레이어 --%>
<c:import url="/WEB-INF/view/iostock/vendr/vendrInstock/vendrInstockPop.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
