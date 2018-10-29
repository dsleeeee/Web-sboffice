<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqStoreMove/hqStoreMove/"/>

<div class="subCon" ng-controller="hqStoreMoveCtrl">
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
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 배송구분 --%>
      <th><s:message code="hqStoreMove.dlvrFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchDlvrFg"
              ng-model="dlvrFg"
              items-source="_getComboData('srchDlvrFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <%-- 진행 --%>
      <th><s:message code="hqStoreMove.procFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
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
    <tr>
      <%-- 출고매장 --%>
      <th><s:message code="hqStoreMove.outStoreCd"/></th>
      <td colspan="3">
        <%-- 매장선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
          <jsp:param name="targetId" value="hqStoreMoveOutSelectStore"/>
          <jsp:param name="displayNm" value="${selectStoreDisplayNmAll}"/>
        </jsp:include>
        <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    <tr>
      <%-- 입고매장 --%>
      <th><s:message code="hqStoreMove.inStoreCd"/></th>
      <td colspan="3">
        <%-- 매장선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
          <jsp:param name="targetId" value="hqStoreMoveInSelectStore"/>
          <jsp:param name="displayNm" value="${selectStoreDisplayNmAll}"/>
        </jsp:include>
        <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('hqStoreMoveCtrl')"><s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10">
    <%-- 신규 --%>
    <button type="button" id="btnRegist" class="btn_skyblue ml5" ng-click="newRegist()"><s:message code="hqStoreMove.newRegist"/></button>
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
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqStoreMove.moveDate"/>" binding="moveDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.dlvrFg"/>" binding="dlvrFg" width="60" align="center" is-read-only="true" data-map="dlvrFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.dtlCnt"/>" binding="dtlCnt" width="60" align="right" is-read-only="true" data-type="Number" format="n0" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.storeCd"/>" binding="outStoreCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.storeNm"/>" binding="outStoreNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.totAmt"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" ></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.storeCd"/>" binding="inStoreCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.storeNm"/>" binding="inStoreNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqStoreMove.totAmt"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" ></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqStoreMoveCtrl"/>
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

  /** 매장이동관리 그리드 controller */
  app.controller('hqStoreMoveCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqStoreMoveCtrl', $scope, $http, true));

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");

    $scope.dlvrFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["hqStoreMove.dlvrFg0"]},
      {id: "1", name: messages["hqStoreMove.dlvrFg1"]},
      {id: "2", name: messages["hqStoreMove.dlvrFg2"]}
    ], 'id', 'name');

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["hqStoreMove.procFg0"]},
      {id: "1", name: messages["hqStoreMove.procFg1"]},
      {id: "2", name: messages["hqStoreMove.procFg2"]},
      {id: "3", name: messages["hqStoreMove.procFg3"]}
    ], 'id', 'name');

    $scope._setComboData("srchDlvrFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["hqStoreMove.dlvrFg0"], "value": "0"},
      {"name": messages["hqStoreMove.dlvrFg1"], "value": "1"},
      {"name": messages["hqStoreMove.dlvrFg2"], "value": "2"}
    ]);

    $scope._setComboData("srchProcFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["hqStoreMove.procFg0"], "value": "0"},
      {"name": messages["hqStoreMove.procFg1"], "value": "1"},
      {"name": messages["hqStoreMove.procFg2"], "value": "2"},
      {"name": messages["hqStoreMove.procFg3"], "value": "3"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("hqStoreMoveCtrl");

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
            $scope._broadcast('hqStoreMoveDtlCtrl', params);
          }
        }
      });

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqStoreMoveCtrl", function (event, data) {
      $scope.searchHqStoreMoveList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 매장이동관리 리스트 조회
    $scope.searchHqStoreMoveList = function () {
      // 파라미터
      var params       = {};
      params.startDate  = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate    = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
      params.outStoreCd = $("#hqStoreMoveOutSelectStoreCd").val();
      params.inStoreCd  = $("#hqStoreMoveInSelectStoreCd").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/move/hqStoreMove/hqStoreMove/list.sb", params);
    };

    // 신규등록
    $scope.newRegist = function () {
      var params    = {};
      $scope._broadcast("hqStoreMoveRegistCtrl", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.hqStoreMoveOutSelectStoreShow = function () {
      $scope._broadcast('hqStoreMoveOutSelectStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.hqStoreMoveInSelectStoreShow = function () {
      $scope._broadcast('hqStoreMoveInSelectStoreCtrl');
    };

  }]);
</script>

<%-- 매장이동관리 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqStoreMove/hqStoreMoveDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장이동관리 신규등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqStoreMove/hqStoreMoveRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 매장이동관리 상품추가 레이어 --%>
<c:import url="/WEB-INF/view/iostock/move/hqStoreMove/hqStoreMoveAddProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
