<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/dstmn/"/>

<div class="subCon" ng-controller="dstmnCtrl">
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
      <%-- 출고일자 --%>
      <th><s:message code="dstmn.outDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행 --%>
      <th><s:message code="dstmn.procFg"/></th>
      <td></td>
      <%-- 종류 --%>
      <th><s:message code="dstmn.slipKind"/></th>
      <td></td>
    </tr>
    <tr>
      <%-- 기사 --%>
      <th><s:message code="dstmn.dlvrNm"/></th>
      <td></td>
      <%--TODO 거래처 로그인시 처리로직 필요 --%>
      <%-- 거래처 --%>
      <th><s:message code="dstmn.vendrNm"/></th>
      <td></td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="dstmn.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="dstmn.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    <tr>
      <%-- 분배지시서 --%>
      <th><s:message code="dstmn.dstbDrctn"/></th>
      <td colspan="3">
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.prodPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.prodStorePrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.storeProdPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.dlvrPrint"/></a>
      </td>
    </tr>
    <tr>
      <%-- 거래명세표 --%>
      <th><s:message code="dstmn.stmtAcct"/></th>
      <td colspan="3">
        <span class="txtIn w150 sb-select fl mr5">
          <wj-combo-box
            id="stmtAcctFg"
            ng-model="stmtAcctFg"
            items-source="_getComboData('stmtAcctFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.stmtAcctPrint"/></a>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.stmtAcctExcel"/></a>
      </td>
    </tr>
    <tr>
      <%-- 작성연월일 --%>
      <th><s:message code="dstmn.writtenDateMonth"/></th>
      <td colspan="3">
        <div class="sb-select fl mr5">
          <span class="txtIn"><input id="writtenDate" class="w120"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.taxBillIssue"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('dstmnCtrl')"><s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10 fr">
    <p class="s14 bk fl mr10 lh30"><s:message code="dstmn.reqNoConfirm"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="reqNoConfirmCnt"></p>
    <p class="s14 bk fl mr10 lh30"><s:message code="dstmn.outstockNoCreate"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="outstockNoCreateCnt"></p>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipFg"/>" binding="slipFg" width="60" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.slipKind"/>" binding="slipKind" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.dlvrNm"/>" binding="dlvrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.reqDate"/>" binding="reqDate" width="90" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.orderTot"/>" binding="orderTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.mgrTot"/>" binding="mgrTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outTot"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.inTot"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.hqRemark"/>" binding="hqRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outDt"/>" binding="outDt" width="150" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="dstmn.outNm"/>" binding="outNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="dstmnCtrl"/>
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

  /** 거래명세표 그리드 controller */
  app.controller('dstmnCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dstmnCtrl', $scope, $http, true));
    $scope.slipFg = 1;

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDt}");
    var writtenDate   = wcombo.genDateVal("#writtenDate", "${sessionScope.sessionInfo.startDt}");

    $scope.slipFgMap = new wijmo.grid.DataMap([
      {id: "1", name: "<s:message code='dstmn.orderSlipFg'/>"},
      {id: "-1", name: "<s:message code='dstmn.rtnSlipFg'/>"},
    ], 'id', 'name');

    $scope._setComboData("stmtAcctFg", [
      {"name": "<s:message code='dstmn.stmtAcctAll'/>", "value": ""},
      {"name": "<s:message code='dstmn.stmtAcctSplr'/>", "value": "1"},
      {"name": "<s:message code='dstmn.stmtAcctSplrRcpnt'/>", "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      $("#reqNoConfirmCnt").html("0");
      $("#outstockNoCreateCnt").html("0");

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("dstmnCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
          // else if(col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
          //   var item = s.rows[e.row].dataItem;
          //   if(item.procFg !== "10") {
          //     wijmo.addClass(e.cell, 'wj-custom-readonly');
          //     s.rows[e.row].isReadOnly = true;
          //   }
          // }

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
            $scope._broadcast('dstmnDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dstmnCtrl", function (event, data) {
      $scope.getReqNoConfirmCnt();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 매장요청 미확정건, 출고자료 미생성건 조회
    $scope.getReqNoConfirmCnt = function () {
      var params       = {};
      params.slipFg    = $scope.slipFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/order/dstmn/dstmn/reqNoConfirmCnt.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope.httpStatusCheck(response)) {
          if (!$.isEmptyObject(response.data.data)) {
            $("#reqNoConfirmCnt").html(response.data.data.reqNoConfirmCnt); //매장요청 미확정건
            $("#outstockNoCreateCnt").html(response.data.data.outstockNoCreateCnt); //출고자료 미생성건
            $scope.searchDstmnList();
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.saveFail"]);
        return false;
      }).then(function () {
        // "complete" code here
      });
    };

    // 거래명세표 리스트 조회
    $scope.searchDstmnList = function () {
      // 파라미터
      var params       = {};
      params.slipFg    = $scope.slipFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/order/dstmn/dstmn/list.sb", params);
    };

    // http 조회 후 status 체크
    $scope.httpStatusCheck = function (res) {
      if (res.data.status === "OK") {
        return true;
      }
      else if (res.data.status === "FAIL") {
        $scope._popMsg("Ajax Fail By HTTP Request");
        return false;
      }
      else if (res.data.status === "SESSION_EXFIRE") {
        $scope._popMsg(res.data.message, function () {
          location.href = res.data.url;
        });
        return false;
      }
      else if (res.data.status === "SERVER_ERROR") {
        $scope._popMsg(res.data.message);
        return false;
      }
      else {
        var msg = res.data.status + " : " + res.data.message;
        $scope._popMsg(msg);
        return false;
      }
    };
  }]);
</script>

<%-- 출고자료생성 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/dstmn/dstmnDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
