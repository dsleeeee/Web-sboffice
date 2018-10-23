<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/volmErr/volmErr/volmErr/"/>

<div class="subCon" ng-controller="volmErrCtrl">
  <div class="searchBar">
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
          <span class="txtIn w150">
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
          <span class="txtIn"><input id="srchStartDate" class="w150"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="volmErr.storeCd"/></th>
      <td colspan="3">
        <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
        <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopM.jsp" flush="true">
          <jsp:param name="targetId" value="volmErrSelectStore"/>
        </jsp:include>
        <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
      </td>
    </tr>
    <tr>
      <%-- 구분 --%>
      <th><s:message code="volmErr.slipFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150">
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
      <%-- 진행 --%>
      <th><s:message code="volmErr.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150">
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
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('volmErrCtrl')"><s:message code="cmm.search"/></button>
  </div>
  <div style="clear: both;"></div>

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
        <wj-flex-grid-column header="<s:message code="volmErr.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.slipFg"/>" binding="slipFg" width="60" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outUnitQty"/>" binding="outUnitQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.outEtcQty"/>" binding="outEtcQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inUnitQty"/>" binding="inUnitQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.inEtcQty"/>" binding="inEtcQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.newSlipNo"/>" binding="newSlipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="volmErr.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="volmErrCtrl"/>
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

  /** 물량오류관리 그리드 controller */
  app.controller('volmErrCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('volmErrCtrl', $scope, $http, true));

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDt}");

    $scope.slipFgMap = new wijmo.grid.DataMap([
      {id: "1", name: messages["volmErr.orderSlipFg"]},
      {id: "-1", name: messages["volmErr.rtnSlipFg"]},
    ], 'id', 'name');

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["volmErr.reg"]},
      {id: "1", name: messages["volmErr.confirm"]},
    ], 'id', 'name');

    $scope._setComboData("srchSlipFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["volmErr.orderSlipFg"], "value": "1"},
      {"name": messages["volmErr.rtnSlipFg"], "value": "-1"}
    ]);

    $scope._setComboData("srchDateFg", [
      {"name": messages["volmErr.outDate"], "value": "out"},
      {"name": messages["volmErr.inDate"], "value": "in"}
    ]);

    $scope._setComboData("srchProcFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["volmErr.reg"], "value": "0"},
      {"name": messages["volmErr.confirm"], "value": "1"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("volmErrCtrl");

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
          }
          else if (col.format === "dateTime") {
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
            var params    = {};
            params.slipNo = selectedRow.slipNo;
            params.slipFg = selectedRow.slipFg;
            params.procFg = selectedRow.procFg;
            params.storeCd = selectedRow.storeCd;
            params.storeNm = selectedRow.storeNm;
            params.hdRemark = selectedRow.remark;
            $scope._broadcast('volmErrDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("volmErrCtrl", function (event, data) {
      $scope.searchVolmErrList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 물량오류 리스트 조회
    $scope.searchVolmErrList = function () {
      // 파라미터
      var params       = {};
      params.dateFg    = $scope.dateFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
      params.storeCd   = $("#volmErrSelectStoreCd").val();
      params.slipFg    = $scope.slipFg;
      params.procFg    = $scope.procFg;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/volmErr/volmErr/volmErr/list.sb", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.volmErrSelectStoreShow = function () {
      $scope._broadcast('volmErrSelectStoreCtrl');
    };

  }]);
</script>

<%-- 물량오류 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/volmErr/volmErr/volmErrDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
