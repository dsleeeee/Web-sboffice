<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/adj/hqAdj/hqAdj/"/>

<div class="subCon" ng-controller="hqAdjCtrl">
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
      <%-- 조정일자 --%>
      <th><s:message code="hqAdj.adjDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행 --%>
      <th><s:message code="hqAdj.procFg"/></th>
      <td colspan="3">
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
      <%-- 조정등록 --%>
      <th><s:message code="hqAdj.adjRegist"/></th>
      <td colspan="3">
        <div class="sb-select fl mr10">
          <span class="txtIn"><input id="adjDate" class="w120px" ng-model="adjDate"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click="newAdj()"><s:message code="hqAdj.adjRegist"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqAdjCtrl', 1)">
      <s:message code="cmm.search"/></button>
  </div>

  <div class="mt20 tr">
    <%-- 삭제 --%>
    <button type="button" class="btn_skyblue ml5" id="btnDelete" ng-click="deleteHqAdj()">
      <s:message code="cmm.delete"/></button>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.adjDate"/>" binding="adjDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.seqNo"/>" binding="seqNo" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.procFg"/>" binding="procFg" width="50" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.adjTitle"/>" binding="adjTitle" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.dtlCnt"/>" binding="dtlCnt" width="50" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.regDate"/>" binding="regDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqAdj.confmDate"/>" binding="confmDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqAdjCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="hqAdjCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 조정관리 그리드 controller */
  app.controller('hqAdjCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqAdjCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");
    $scope.adjDate       = wcombo.genDate("#adjDate");

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["hqAdj.procFg0"]},
      {id: "1", name: messages["hqAdj.procFg1"]}
    ], 'id', 'name');

    $scope._setComboData("srchProcFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["hqAdj.procFg0"], "value": "0"},
      {"name": messages["hqAdj.procFg1"], "value": "1"}
    ]);


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("hqAdjCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "adjTitle") { // 제목
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
          if (col.binding === "adjTitle") { // 제목 클릭
            var params     = {};
            params.adjDate = selectedRow.adjDate;
            params.seqNo   = selectedRow.seqNo;
            $scope._broadcast('hqAdjDtlCtrl', params);
          }
        }
      });
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqAdjCtrl", function (event, data) {
      $scope.searchHqAdjList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 주문 리스트 조회
    $scope.searchHqAdjList = function () {
      // 파라미터
      var params       = {};
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/stock/adj/hqAdj/hqAdj/list.sb", params);
    };


    // 조정 삭제
    $scope.deleteHqAdj = function () {
      <%-- 선택하신 자료를 삭제하시겠습니까? --%>
      var msg = messages["hqAdj.delMsg"];
      s_alert.popConf(msg, function () {
        var params = [];

        if ($scope.flex.collectionView.itemsEdited.length <= 0) {
          $scope._popMsg(messages["cmm.not.modify"]);
          return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];

          if (item.procFg !== '0') {
            $scope._popMsg(messages["hqAdj.not.delete"]); // 확정 된 자료는 삭제할 수 없습니다.
            return false;
          }

          if(item.gChk === true) {
            item.status = "U";
            params.push(item);
          }
        }

        $scope._save("/stock/adj/hqAdj/hqAdj/delete.sb", params, function () {
          $scope.searchHqAdjList();
        });
      });
    };


    // 조정 신규등록
    $scope.newAdj = function () {
      var params        = {};
      params.adjDate    = wijmo.Globalize.format($scope.adjDate.value, 'yyyyMMdd');
      params.seqNo      = '';
      params.callParent = 'hqAdj';
      $scope._broadcast('hqAdjRegistCtrl', params);
    };


  }]);
</script>

<%-- 조정 상세 레이어 --%>
<c:import url="/WEB-INF/view/stock/adj/hqAdj/hqAdjDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 조정 등록 레이어 --%>
<c:import url="/WEB-INF/view/stock/adj/hqAdj/hqAdjRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
