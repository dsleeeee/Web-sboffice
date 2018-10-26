<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnStoreOrder/rtnStoreOrder/"/>

<div class="subCon" ng-controller="rtnStoreOrderCtrl">
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
      <td>
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
      <%-- 반품요청일자 --%>
      <th><s:message code="rtnStoreOrder.reqDate"/></th>
      <td>
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
          <%-- 매장선택 모듈 싱글 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                            displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                            modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                            closeFunc - 팝업 닫기시 호출할 함수
                            cd - 로딩시 세팅할 매장코드
                            nm - 로딩시 세팅할 매장명
          --%>
          <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
            <jsp:param name="targetId" value="rtnStoreOrderSelectStore"/>
          </jsp:include>
          <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
        </c:if>
        <c:if test="${sessionInfo.orgnFg == 'STORE'}">
          <input type="hidden" id="rtnStoreOrderSelectStoreCd" value="${sessionInfo.storeCd}"/>
        </c:if>
        <div class="sb-select fl mr10">
          <span class="txtIn"><input id="reqDate" class="w150" ng-model="rtnStoreOrder.reqDate"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click="newReqOrder()"><s:message code="rtnStoreOrder.reqRegist"/></a>
      </td>
    </tr>
    <tr>
      <%-- 진행구분 --%>
      <th><s:message code="storeOrder.procFg"/></th>
      <td>
        <span class="txtIn w150 sb-select fl mr5">
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
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    envst1042 : ${envst1042} &nbsp;&nbsp;envst1044 : ${envst1044}
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('rtnStoreOrderCtrl')">
      <s:message code="cmm.search"/></button>
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
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.reqDate"/>" binding="reqDate" width="100" align="center" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.slipFg"/>" binding="slipFg" width="70" align="center" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.procFg"/>" binding="procFg" width="70" align="center" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.dtlCnt"/>" binding="dtlCnt" width="70" align="right" data-type="Number" format="n0"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderAmt"/>" binding="orderAmt" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderVat"/>" binding="orderVat" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.orderTot"/>" binding="orderTot" width="70" align="right" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnStoreOrder.remark"/>" binding="remark" width="*" align="left"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnStoreOrderCtrl"/>
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

  /** 반품등록 그리드 controller */
  app.controller('rtnStoreOrderCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnStoreOrderCtrl', $scope, $http, true));

    $scope.slipFg        = -1;
    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");
    $scope.reqDate       = wcombo.genDate("#reqDate");

    $scope._setComboData("srchDateFg", [
      {"name": messages["rtnStoreOrder.reqDate"], "value": "req"},
      {"name": messages["rtnStoreOrder.regDate"], "value": "reg"},
      {"name": messages["rtnStoreOrder.modDate"], "value": "mod"}
    ]);

    $scope._setComboData("srchProcFg", [
      {"name": "<s:message code='rtnStoreOrder.procFgAll'/>", "value": ""},
      {"name": "<s:message code='rtnStoreOrder.procFgReg'/>", "value": "10"},
      {"name": "<s:message code='rtnStoreOrder.procFgDstb'/>", "value": "20"},
      {"name": "<s:message code='rtnStoreOrder.procFgDstbCompt'/>", "value": "30"}
    ]);

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "10", name: "<s:message code='rtnStoreOrder.procFgReg'/>"},
      {id: "20", name: "<s:message code='rtnStoreOrder.procFgDstb'/>"},
      {id: "30", name: "<s:message code='rtnStoreOrder.procFgDstbCompt'/>"}
    ], 'id', 'name');

    // 출고가능일자 세팅
    $scope.reqDate.value = new Date(getFormatDate("${reqDate}", "-"));
    // 출고요청일자 선택가능여부에 따라 출고요청일자 선택여부 처리
    if ("${envst1044}" === "Y") {
      $scope.reqDate.isReadOnly = true;
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("rtnStoreOrderCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "reqDate") { // 출고요청일자
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
          if (col.binding === "reqDate") { // 출고요청일자 클릭
            var params      = {};
            params.reqDate  = selectedRow.reqDate;
            params.slipFg   = selectedRow.slipFg;
            params.procFg   = selectedRow.procFg;
            params.hdRemark = selectedRow.remark;
            params.storeCd  = $scope.searchedStoreCd;
            $scope._broadcast('rtnStoreOrderDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnStoreOrderCtrl", function (event, data) {
      $scope.searchRtnStoreOrderList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 반품 리스트 조회
    $scope.searchRtnStoreOrderList = function () {
      if ($("#rtnStoreOrderSelectStoreCd").val() === "") {
        $scope._popMsg(messages["rtnStoreOrder.dtl.require.selectStore"]); // 매장을 선택해 주세요.
        return false;
      }
      $scope.searchedStoreCd = $("#rtnStoreOrderSelectStoreCd").val(); // 반품요청일자를 클릭하여 상세내역을 봐야하므로 조회할 당시 매장코드를 담아둔다.
      // 파라미터
      var params       = {};
      params.slipFg    = $scope.slipFg;
      params.dateFg    = $scope.dateFg;
      params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
      params.storeCd   = $scope.searchedStoreCd;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/orderReturn/rtnStoreOrder/rtnStoreOrder/list.sb", params);
    };

    // 신규 요청등록
    $scope.newReqOrder = function () {
      if ($("#rtnStoreOrderSelectStoreCd").val() === "") {
        $scope._popMsg(messages["rtnStoreOrder.dtl.require.selectStore"]); // 매장을 선택해 주세요.
        return false;
      }
      var params        = {};
      params.callParent = "rtnStoreOrder";
      params.reqDate    = wijmo.Globalize.format($scope.reqDate.value, 'yyyyMMdd');
      params.slipFg     = $scope.slipFg;
      params.hdRemark   = "";
      params.storeCd    = $("#rtnStoreOrderSelectStoreCd").val();

      $scope._broadcast("rtnStoreOrderRegistCtrl", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.rtnStoreOrderSelectStoreShow = function () {
      $scope._broadcast('rtnStoreOrderSelectStoreCtrl');
    };

  }]);
</script>

<%-- 반품등록 상품 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 반품등록 상품 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
