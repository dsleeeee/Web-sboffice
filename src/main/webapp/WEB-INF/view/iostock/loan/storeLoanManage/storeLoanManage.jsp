<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanManage/storeLoanManage/"/>

<div class="subCon" ng-controller="storeLoanManageCtrl">
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
      <%-- 매장코드 --%>
      <th><s:message code="loan.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="loan.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="15"/>
      </td>
    </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="btnSearch" ng-click="searchStoreLoanManage()"><s:message code="cmm.search"/></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <wj-combo-box
      class="w100px fl"
      id="listScaleBox"
      ng-model="listScale"
      items-source="_getComboData('listScaleBox')"
      display-member-path="name"
      selected-value-path="value"
      is-editable="false"
      initialized="initComboBox(s)">
    </wj-combo-box>
    <%--// 페이지 스케일  --%>
    <%-- 엑셀 다운로드 --%>
    <%--<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>--%>
    <%-- 저장 --%>
    <button id="btnSave" class="btn_skyblue fr mr5" ng-click="save()"><s:message code="cmm.save"/></button>
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
        <wj-flex-grid-column header="<s:message code="loan.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.limitLoanAmt"/>" binding="limitLoanAmt" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.useLoanAmt"/>" binding="useLoanAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.currLoanAmt"/>" binding="currLoanAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.maxOrderAmt"/>" binding="maxOrderAmt" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.orderFg"/>" binding="orderFg" width="70" align="center" is-read-only="false" data-map="orderFg"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.availableOrderAmt"/>" binding="availableOrderAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.noOutstockAmtFg"/>" binding="noOutstockAmtFg" width="70" align="center" is-read-only="false" data-map="noOutstockAmtFg"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.orderCloseYn"/>" binding="orderCloseYn" width="70" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loan.remark"/>" binding="remark" width="150" align="left" is-read-only="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="storeLoanManageCtrl"/>
      </jsp:include>
      <%--// ColumnPicker 사용시 include --%>
    </div>
    <%--//위즈모 테이블--%>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="storeLoanManageCtrlPager" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 매장여신관리 그리드 controller */
  app.controller('storeLoanManageCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLoanManageCtrl', $scope, $http, true));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // 그리드 DataMap 설정
    $scope.orderFg = new wijmo.grid.DataMap([
      {id: "1", name: messages["loan.orderFg1"]},
      {id: "2", name: messages["loan.orderFg2"]},
      {id: "3", name: messages["loan.orderFg3"]}
    ], 'id', 'name');

    $scope.noOutstockAmtFg = new wijmo.grid.DataMap([
      {id: "N", name: messages["loan.noOutstockAmtFgN"]},
      {id: "Y", name: messages["loan.noOutstockAmtFgY"]}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("storeLoanManageCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "storeCd") { // 매장코드
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          }
        }
      });

      // 그리드 클릭 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
          var col         = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if (col.binding === "storeCd") { // 매장코드 클릭
            var params     = {};
            params.storeCd = selectedRow.storeCd;
            params.storeNm = selectedRow.storeNm;
            $scope._broadcast('storeLoanManageDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 리스트 조회
    $scope.searchStoreLoanManage = function () {
      // 파라미터
      var params = {};
      // param.storeCd = $("#srchStoreCd").val();
      // param.storeNm = $("#srchStoreNm").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/loan/storeLoanManage/storeLoanManage/list.sb", params);
    };

    // 저장
    $scope.save = function () {
      var params = [];

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.limitLoanAmt !== null && item.maxOrderAmt == null) {
          $scope._popMsg(messages["loan.maxOrderAmt"]+" "+messages["cmm.require.text"]); // 1회주문한도액을 입력해주세요.
          return false;
        }
        if (item.maxOrderAmt !== null && item.limitLoanAmt === null) {
          $scope._popMsg(messages["loan.limitLoanAmt"]+" "+messages["cmm.require.text"]); // 여신한도액을 입력해주세요.
          return false;
        }

        item.status = "U";
        params.push(item);
      }

      $scope._save("/iostock/loan/storeLoanManage/storeLoanManage/save.sb", params, function () {
        $scope.searchStoreLoanManage()
      });
    };

    $scope.excelDown = function () {
      var name = "${menuNm}";
      name     = name + " 테스트";
      wexcel.down($scope.flex, name, name + ".xlsx");
    };
  }]);
</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=20180821.01" charset="utf-8"></script>--%>

<%-- 매장여신관리 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/loan/storeLoanManage/storeLoanManageDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
