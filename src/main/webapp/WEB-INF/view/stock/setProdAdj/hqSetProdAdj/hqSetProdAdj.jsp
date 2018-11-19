<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/setProdAdj/hqSetProdAdj/hqSetProdAdj/"/>

<div class="subCon" ng-controller="hqSetProdAdjCtrl">
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
      <%-- 구성일자 --%>
      <th><s:message code="hqSetProdAdj.setDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 상품코드 --%>
      <th><s:message code="hqSetProdAdj.prodCd"/></th>
      <td>
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="hqSetProdAdj.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('hqSetProdAdjCtrl', 1)">
      <s:message code="cmm.search"/></button>
  </div>

  <div class="mt20 tr">
    <%-- 세트구성/해체 --%>
    <button type="button" class="btn_skyblue ml5" id="btnNewHqSetProdAdj" ng-click="newHqSetProdAdj()">
      <s:message code="hqSetProdAdj.newHqSetProdAdj"/></button>
    <%-- 삭제 --%>
    <button type="button" class="btn_skyblue ml5" id="btnDelete" ng-click="deleteHqSetProdAdj()">
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
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.setDate"/>" binding="setDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.prodClassNm"/>" binding="prodClassNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.prodNm"/>" binding="prodNm" width="200" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.setMakeFg"/>" binding="setMakeFg" width="100" align="center" is-read-only="true" data-map="setMakeFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.setProdQty"/>" binding="setProdQty" width="60" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.setProdAmt"/>" binding="setProdAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.seqNo"/>" binding="seqNo" width="40" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqSetProdAdj.procFg"/>" binding="procFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqSetProdAdjCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>
  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="hqSetProdAdjCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 세트재고조정 그리드 controller */
  app.controller('hqSetProdAdjCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSetProdAdjCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");

    $scope.setMakeFgMap = new wijmo.grid.DataMap([
      {id: "1", name: messages["hqSetProdAdj.setMakeFg1"]},
      {id: "2", name: messages["hqSetProdAdj.setMakeFg2"]}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("hqSetProdAdjCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          }
          else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });

    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqSetProdAdjCtrl", function (event, data) {
      $scope.searchHqSetProdAdjList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 리스트 조회
    $scope.searchHqSetProdAdjList = function () {
      // 파라미터
      var params       = {};
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/stock/setProdAdj/hqSetProdAdj/hqSetProdAdj/list.sb", params);
    };


    // 세트 삭제
    $scope.deleteHqSetProdAdj = function () {
      <%-- 선택하신 자료를 삭제하시겠습니까? --%>
      var msg = messages["hqSetProdAdj.delMsg"];
      s_alert.popConf(msg, function () {
        var params = [];

        if ($scope.flex.collectionView.itemsEdited.length <= 0) {
          $scope._popMsg(messages["cmm.not.modify"]);
          return false;
        }

        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];
          if (item.gChk === true) {
            item.status = "U";
            params.push(item);
          }
        }

        $scope._save("/stock/setProdAdj/hqSetProdAdj/hqSetProdAdj/delete.sb", params, function () {
          $scope.searchHqSetProdAdjList();
        });
      });
    };


    // 세트 구성/해체 등록
    $scope.newHqSetProdAdj = function () {
      var params = {};
      $scope._broadcast('hqSetProdAdjRegistCtrl', params);
    };


  }]);
</script>

<%-- 조정 등록 레이어 --%>
<c:import url="/WEB-INF/view/stock/setProdAdj/hqSetProdAdj/hqSetProdAdjRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
