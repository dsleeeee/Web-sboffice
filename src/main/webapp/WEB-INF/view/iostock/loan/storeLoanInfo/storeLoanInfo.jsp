<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/loan/storeLoanInfo/storeLoanInfo/"/>

<div class="subCon" ng-controller="storeLoanInfoCtrl">
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
            <th><s:message code="storeLoanInfo.searchDate"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn"><input id="srchStartDate" class="w120"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w120"></span>
                </div>
            </td>
        </tr>
        <tr>
            <%-- 매장코드 --%>
            <th><s:message code="storeLoanInfo.store"/></th>
            <td colspan="3">
              <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
              <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopM.jsp" flush="true">
                <jsp:param name="targetId" value="storeLoanInfoSelectStore"/>
              </jsp:include>
              <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
            </td>
        </tr>
        </tbody>
    </table>

    <%-- 조회 --%>
    <div class="mt10 pdb20 oh bb">
        <button class="btn_blue fr" id="btnSearch" ng-click="searchStoreLoanInfo()"><s:message code="cmm.search"/></button>
    </div>

    <div class="mt20 oh sb-select dkbr">
        <%-- 엑셀 다운로드 --%>
        <button id="btnExcel" class="btn_skyblue fr" ng-click="excelDown()"><s:message code="cmm.excel.down"/></button>
    </div>

    <div class="w100 mt10" >
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
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.storeCd"/>"     binding="storeCd"     width="70"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.storeNm"/>"     binding="storeNm"     width="150" align="left"  ></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.loanDate"/>"    binding="loanDate"    width="80"  align="center" format="date"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.outAmt"/>"      binding="outAmt"      width="70"  align="right" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.inAmt"/>"       binding="inAmt"       width="70"  align="right" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.currLoanAmt"/>" binding="currLoanAmt" width="70"  align="right" data-type="Number" format="n0"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="storeLoanInfo.remark"/>"      binding="remark"      width="*"   align="left"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="storeLoanInfoCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>

<script type="text/javascript">

  /** 여신상세현황 그리드 controller */
  app.controller('storeLoanInfoCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeLoanInfoCtrl', $scope, $http, true));

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDt}");

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("storeLoanInfoCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];

          if(col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          }
        }
      });
    };

    // 리스트 조회
    $scope.searchStoreLoanInfo = function() {
      // 파라미터
      var params = {};
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value  , 'yyyyMMdd');
      params.storeCd   = $("#storeLoanInfoSelectStoreCd").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/loan/storeLoanInfo/storeLoanInfo/list.sb", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeLoanInfoSelectStoreShow = function () {
      $scope._broadcast('storeLoanInfoSelectStoreCtrl');
    };

    $scope.excelDown = function () {
      var name = "${menuNm}";
      name = name+" 테스트";
      wexcel.down($scope.flex, name, name + ".xlsx");
    };

  }]);

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>
