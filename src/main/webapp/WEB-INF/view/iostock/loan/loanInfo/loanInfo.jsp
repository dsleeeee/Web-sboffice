<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/loanInfo/loanInfo/"/>

<div class="subCon" ng-controller="loanInfoCtrl">
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
      <th><s:message code="loanInfo.searchDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn w150px fl">
              <wj-combo-box
                id="srchDateFg"
                ng-model="dateFg"
                items-source="_getComboData('srchDateFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)"
                selected-index-changed="selectedIndexChanged(s, e)"
              >
              </wj-combo-box>
          </span>
          <div id="dateLayer" class="sb-select fl ml5" style="display: none;">
            <span class="txtIn"><input id="srchStartDate" class="w120px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w120px"></span>
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="searchLoanInfo()"><s:message code="cmm.search"/></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 엑셀 다운로드 --%>
    <%--<button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>--%>
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
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="loanInfo.loanDate"/>" binding="loanDate" width="100" align="center" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loanInfo.outAmt"/>" binding="outAmt" width="100" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loanInfo.inAmt"/>" binding="inAmt" width="100" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loanInfo.currLoanAmt"/>" binding="currLoanAmt" width="100" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="loanInfo.remark"/>" binding="remark" width="*" align="left"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="loanInfoCtrl"/>
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

  /** 여신현황 그리드 controller */
  app.controller('loanInfoCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('loanInfoCtrl', $scope, $http, true));

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");

    $scope._setComboData("srchDateFg", [
      {"name": messages["loanInfo.all"], "value": ""},
      {"name": messages["loanInfo.searchDate"], "value": "date"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("loanInfoCtrl");

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

    // 리스트 조회
    $scope.searchLoanInfo = function () {
      // 파라미터
      var params       = {};
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/loan/loanInfo/loanInfo/list.sb", params);
    };

    // 조회일자 값 변경 이벤트 함수
    $scope.selectedIndexChanged = function (s, e) {
      if (s.selectedValue === "") {
        $("#dateLayer").hide();
      }
      else {
        $("#dateLayer").show();
      }
    };

    $scope.excelDown = function () {
      var name = "${menuNm}";
      name     = name + " 테스트";
      wexcel.down($scope.flex, name, name + ".xlsx");
    };

  }]);

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
