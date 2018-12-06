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
          <span class="txtIn w150px">
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
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행구분 --%>
      <th><s:message code="storeOrder.procFg"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
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
          <span class="txtIn"><input id="reqDate" class="w150px" ng-model="rtnStoreOrder.reqDate"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click="newReqOrder()"><s:message code="rtnStoreOrder.reqRegist"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
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
  app.controller('rtnStoreOrderCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
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

    <%--$scope._setComboData("srchProcFg", [--%>
      <%--{"name": "<s:message code='rtnStoreOrder.procFgAll'/>", "value": ""},--%>
      <%--{"name": "<s:message code='rtnStoreOrder.procFgReg'/>", "value": "10"},--%>
      <%--{"name": "<s:message code='rtnStoreOrder.procFgDstb'/>", "value": "20"},--%>
      <%--{"name": "<s:message code='rtnStoreOrder.procFgDstbCompt'/>", "value": "30"}--%>
    <%--]);--%>

    <%--$scope.procFgMap = new wijmo.grid.DataMap([--%>
      <%--{id: "10", name: "<s:message code='rtnStoreOrder.procFgReg'/>"},--%>
      <%--{id: "20", name: "<s:message code='rtnStoreOrder.procFgDstb'/>"},--%>
      <%--{id: "30", name: "<s:message code='rtnStoreOrder.procFgDstbCompt'/>"}--%>
    <%--], 'id', 'name');--%>

    // 출고가능일자 세팅
    $scope.reqDate.value = new Date(getFormatDate("${reqDate}", "-"));
    // 출고요청일자 선택가능여부에 따라 출고요청일자 선택여부 처리
    if ("${envst1044}" === "N") {
      $scope.reqDate.isReadOnly = true;
    }

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "083";
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
      $scope._queryCombo("combo,map", "srchProcFg", 'procFgMap', null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

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


    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    // comboId : combo 생성할 ID
    // gridMapId : grid 에서 사용할 Map ID
    // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    // params : 데이터 조회할 url에 보낼 파라미터
    // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    // callback : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
      var comboUrl = "/iostock/volmErr/volmErr/volmErr/getCombo.sb";
      if (url) {
        comboUrl = url;
      }

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : comboUrl, /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if (response.data.status === "OK") {
          // this callback will be called asynchronously
          // when the response is available
          if (!$.isEmptyObject(response.data.data.list)) {
            var list       = response.data.data.list;
            var comboArray = [];
            var comboData  = {};

            if (comboFg.indexOf("combo") >= 0 && nvl(comboId,'') !== '') {
              comboArray = [];
              if (option === "A") {
                comboData.name  = messages["cmm.all"];
                comboData.value = "";
                comboArray.push(comboData);
              }
              else if (option === "S") {
                comboData.name  = messages["cmm.select"];
                comboData.value = "";
                comboArray.push(comboData);
              }

              for (var i = 0; i < list.length; i++) {
                comboData       = {};
                comboData.name  = list[i].nmcodeNm;
                comboData.value = list[i].nmcodeCd;
                comboArray.push(comboData);
              }
              $scope._setComboData(comboId, comboArray);
            }

            if (comboFg.indexOf("map") >= 0 && nvl(gridMapId,'') !== '') {
              comboArray = [];
              for (var i = 0; i < list.length; i++) {
                comboData      = {};
                comboData.id   = list[i].nmcodeCd;
                comboData.name = list[i].nmcodeNm;
                comboArray.push(comboData);
              }
              $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
            }
          }
        }
        else if (response.data.status === "FAIL") {
          $scope._popMsg("Ajax Fail By HTTP Request");
        }
        else if (response.data.status === "SESSION_EXFIRE") {
          $scope._popMsg(response.data.message, function () {
            location.href = response.data.url;
          });
        }
        else if (response.data.status === "SERVER_ERROR") {
          $scope._popMsg(response.data.message);
        }
        else {
          var msg = response.data.status + " : " + response.data.message;
          $scope._popMsg(msg);
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.error"]);
        return false;
      }).then(function () {
        if (typeof callback === 'function') {
          $timeout(function () {
            callback();
          }, 10);
        }
      });
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
