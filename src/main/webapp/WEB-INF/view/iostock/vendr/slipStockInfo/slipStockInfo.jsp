<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/slipStockInfo/slipStockInfo/"/>

<div class="subCon">
  <div ng-controller="slipStockInfoCtrl">
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
        <th><s:message code="slipStockInfo.instockDate"/></th>
        <td>
          <div class="sb-select">
            <span class="txtIn"><input id="srchStartDate" class="w110px"></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="srchEndDate" class="w110px"></span>
          </div>
        </td>
        <%-- 전표구분 --%>
        <th><s:message code="slipStockInfo.slipFg"/></th>
        <td>
          <div class="sb-select">
          <span class="txtIn w120px">
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
      </tr>
      <tr>
        <%-- 입고번호 --%>
        <th><s:message code="slipStockInfo.inSlipNo"/></th>
        <td>
          <input type="text" id="srchSlipNo" name="srchSlipNo" ng-model="slipNo" class="sb-input w100" maxlength="8"/>
        </td>
        <%-- 발주번호 --%>
        <th><s:message code="slipStockInfo.orderSlipNo"/></th>
        <td>
          <input type="text" id="srchOrderSlipNo" name="srchOrderSlipNo" ng-model="orderSlipNo" class="sb-input w100" maxlength="8"/>
        </td>
      </tr>
      <tr>
        <%-- 거래처 --%>
        <th><s:message code="slipStockInfo.vendr"/></th>
        <td>
          <%-- 거래처선택 모듈 멀티 선택 사용시 include
               param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
          --%>
          <jsp:include page="/WEB-INF/view/iostock/vendr/vendrOrder/selectVendrM.jsp" flush="true">
            <jsp:param name="targetId" value="slipStockInfoSelectVendr"/>
          </jsp:include>
          <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
        </td>
        <%-- 상품분류 --%>
        <th><s:message code="slipStockInfo.prodClass"/></th>
        <td>
          <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                 placeholder="<s:message code="cmm.all" />" readonly/>
          <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
        </td>
      </tr>
      <tr>
        <%-- 상품코드 --%>
        <th><s:message code="slipStockInfo.prodCd"/></th>
        <td>
          <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
        </td>
        <%-- 상품명 --%>
        <th><s:message code="slipStockInfo.prodNm"/></th>
        <td>
          <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="16"/>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="_pageView('slipStockInfoCtrl', 1)">
        <%--<button class="btn_blue fr" id="btnSearch" ng-click="fnSearch()">--%>
        <s:message code="cmm.search"/></button>
    </div>

    <div class="wj-TblWrap" style="height: 300px;">
      <div class="w100 mt10">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 250px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inSlipNo"/>" binding="slipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.instockDate"/>" binding="instockDate" width="80" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.vendr"/>" binding="vendrNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.orderSlipNo"/>" binding="orderSlipNo" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.slipFg"/>" binding="slipFg" width="70" align="center" is-read-only="true" data-map="slipFgMap"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtlCnt"/>" binding="dtlCnt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="slipStockInfoCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>

      <div style="clear: both"></div>

      <div class="w100 mt10" ng-controller="slipStockInfoDtlCtrl">
        <div class="wj-TblWrapBr">
          <%--위즈모 테이블--%>
          <div class="wj-gridWrap" style="height: 250px;">
            <wj-flex-grid
              autoGenerateColumns="false"
              selection-mode="Row"
              items-source="data"
              control="flex"
              initialized="initGrid(s,e)"
              is-read-only="true"
              item-formatter="_itemFormatter">

              <!-- define columns -->
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodCd"/>" binding="prodCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitFg"/>" binding="poUnitFg" width="50" align="center" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTotQty"/>" binding="inTotQty" width="40" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
              <wj-flex-grid-column header="<s:message code="slipStockInfo.dtl.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
              <jsp:param name="pickerTarget" value="slipStockInfoDtlCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
          </div>
          <%--//위즈모 테이블--%>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 전표별 입출고내역 그리드 controller */
  app.controller('slipStockInfoCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('slipStockInfoCtrl', $scope, $http, true));

    $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    $scope.srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");

    // 조회조건 전표구분
    $scope._setComboData("srchSlipFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["slipStockInfo.slipFgIn"], "value": "1"},
      {"name": messages["slipStockInfo.slipFgRtn"], "value": "-1"}
    ]);

    // 그리드 전표구분
    $scope.slipFgMap = new wijmo.grid.DataMap([
      {id: "1", name: "<s:message code='slipStockInfo.slipFgIn'/>"},
      {id: "-1", name: "<s:message code='slipStockInfo.slipFgRtn'/>"}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("slipStockInfoCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
          // 구분이 반출이면 글씨색을 red 로 변경한다.
          if (col.binding === "slipFg") {
            var item = s.rows[e.row].dataItem;
            if (item.slipFg === -1) {
              wijmo.addClass(e.cell, 'red');
            }
          }

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
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
          if (col.binding === "slipNo") { // 전표번호
            var params       = {};
            params.slipNo    = selectedRow.slipNo;
            params.startDate = $scope.searchedStartDate;
            params.endDate   = $scope.searchedEndDate;
            $scope._broadcast('slipStockInfoDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      s.columnHeaders.rows[0].dataItem = {
        slipNo     : messages["slipStockInfo.inSlipNo"],
        instockDate: messages["slipStockInfo.instockDate"],
        vendrNm    : messages["slipStockInfo.vendr"],
        orderSlipNo: messages["slipStockInfo.orderSlipNo"],
        slipFg     : messages["slipStockInfo.slipFg"],
        dtlCnt     : messages["slipStockInfo.dtlCnt"],
        inAmt      : messages["slipStockInfo.inOut"],
        inVat      : messages["slipStockInfo.inOut"],
        inTot      : messages["slipStockInfo.inOut"],
      };

      s.itemFormatter = function (panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
          //align in center horizontally and vertically
          panel.rows[r].allowMerging    = true;
          panel.columns[c].allowMerging = true;
          wijmo.setCss(cell, {
            display    : 'table',
            tableLayout: 'fixed'
          });
          cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
          wijmo.setCss(cell.children[0], {
            display      : 'table-cell',
            verticalAlign: 'middle',
            textAlign    : 'center'
          });
        }
        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
          // GroupRow 인 경우에는 표시하지 않는다.
          if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
            cell.textContent = '';
          } else {
            if (!isEmpty(panel._rows[r]._data.rnum)) {
              cell.textContent = (panel._rows[r]._data.rnum).toString();
            } else {
              cell.textContent = (r + 1).toString();
            }
          }
        }
        // readOnly 배경색 표시
        else if (panel.cellType === wijmo.grid.CellType.Cell) {
          var col = panel.columns[c];
          if (col.isReadOnly) {
            wijmo.addClass(cell, 'wj-custom-readonly');
          }
        }
      }
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("slipStockInfoCtrl", function (event, data) {
      $scope.searchSlipStockInfoList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 전표별 입출고내역 리스트 조회
    $scope.searchSlipStockInfoList = function () {
      $scope.searchedStartDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
      $scope.searchedEndDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

      // 파라미터
      var params       = {};
      params.startDate = $scope.searchedStartDate;
      params.endDate   = $scope.searchedEndDate;
      params.vendrCd   = $("#slipStockInfoSelectVendrCd").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/slipStockInfo/slipStockInfo/list.sb", params, function () {
        // 거래처별 정산 그리드 조회 후 상세내역 그리드 초기화
        var slipStockInfoDtlScope = agrid.getScope('slipStockInfoDtlCtrl');
        slipStockInfoDtlScope.dtlGridDefault();
      });
    };


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.slipStockInfoSelectVendrShow = function () {
      $scope._broadcast('slipStockInfoSelectVendrCtrl');
    };


    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
      var popUp = $scope.prodClassPopUpLayer;
      popUp.show(true, function (s) {
        // 선택 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope          = agrid.getScope('prodClassPopUpCtrl');
          var prodClassCd    = scope.getSelectedClass();
          var params         = {};
          params.prodClassCd = prodClassCd;
          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function (response) {
              $scope.prodClassCd   = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
          );
        }
      });
    };

  }]);


  /** 전표별 입출고내역 상세 그리드 controller */
  app.controller('slipStockInfoDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('slipStockInfoDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("slipStockInfoDtlCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });


      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      s.columnHeaders.rows[0].dataItem = {
        prodCd   : messages["slipStockInfo.dtl.prodCd"],
        prodNm   : messages["slipStockInfo.dtl.prodNm"],
        poUnitFg : messages["slipStockInfo.dtl.poUnitFg"],
        poUnitQty: messages["slipStockInfo.dtl.poUnitQty"],
        costUprc : messages["slipStockInfo.dtl.costUprc"],
        inTotQty : messages["slipStockInfo.dtl.inOut"],
        inAmt    : messages["slipStockInfo.dtl.inOut"],
        inVat    : messages["slipStockInfo.dtl.inOut"],
        inTot    : messages["slipStockInfo.dtl.inOut"],
      };

      s.itemFormatter = function (panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
          //align in center horizontally and vertically
          panel.rows[r].allowMerging    = true;
          panel.columns[c].allowMerging = true;
          wijmo.setCss(cell, {
            display    : 'table',
            tableLayout: 'fixed'
          });
          cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
          wijmo.setCss(cell.children[0], {
            display      : 'table-cell',
            verticalAlign: 'middle',
            textAlign    : 'center'
          });
        }
        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
          // GroupRow 인 경우에는 표시하지 않는다.
          if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
            cell.textContent = '';
          } else {
            if (!isEmpty(panel._rows[r]._data.rnum)) {
              cell.textContent = (panel._rows[r]._data.rnum).toString();
            } else {
              cell.textContent = (r + 1).toString();
            }
          }
        }
        // readOnly 배경색 표시
        else if (panel.cellType === wijmo.grid.CellType.Cell) {
          var col = panel.columns[c];
          if (col.isReadOnly) {
            wijmo.addClass(cell, 'wj-custom-readonly');
          }
        }
      }

    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("slipStockInfoDtlCtrl", function (event, data) {
      $scope.slipNo    = data.slipNo;
      $scope.startDate = data.startDate;
      $scope.endDate   = data.endDate;

      $scope.searchSlipStockInfoDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 전표별 입출고내역 상세 리스트 조회
    $scope.searchSlipStockInfoDtlList = function () {
      // 파라미터
      var params       = {};
      params.slipNo    = $scope.slipNo;
      params.startDate = $scope.startDate;
      params.endDate   = $scope.endDate;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/vendr/slipStockInfo/slipStockInfoDtl/list.sb", params);
    };


    // 상세 그리드 초기화
    $scope.dtlGridDefault = function () {
      $timeout(function () {
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;
        $scope.flex.refresh();
      }, 10);
    };

  }]);
</script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>
