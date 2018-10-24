<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProd/"/>

<div class="subCon" ng-controller="rtnDstbCloseProdCtrl">
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
      <%-- 진행구분 --%>
      <th><s:message code="rtnDstbCloseProd.procFg"/></th>
      <td colspan="3">
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
    <tr>
      <%-- 반품요청일자 --%>
      <th><s:message code="rtnDstbCloseProd.reqDate"/></th>
      <td colspan="3">
        <div class="sb-select fl mr10">
          <span class="txtIn"><input id="reqDate" class="w150"></span>
        </div>
        <a href="#" class="btn_grayS" ng-click="add()"><s:message code="rtnDstbCloseProd.addRegist"/></a>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('rtnDstbCloseProdCtrl')"><s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10">
    <%-- 확정 --%>
    <button type="button" id="btnConfirm" class="btn_skyblue ml5" ng-click="saveConfirm()"><s:message code="rtnDstbCloseProd.confirm"/></button>
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.reqDate"/>" binding="reqDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.prodCd"/>" binding="prodCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrUnitQty"/>" binding="mgrUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrEtcQty"/>" binding="mgrEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrTotQty"/>" binding="mgrTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrAmt"/>" binding="mgrAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrVat"/>" binding="mgrVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.mgrTot"/>" binding="mgrTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.dtlCnt"/>" binding="dtlCnt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseProd.slipFg"/>" binding="slipFg" width="70" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnDstbCloseProdCtrl"/>
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

  /** 분배마감 그리드 controller */
  app.controller('rtnDstbCloseProdCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnDstbCloseProdCtrl', $scope, $http, true));

    $scope.slipFg     = -1;
    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.startDate}");
    var reqDate       = wcombo.genDateVal("#reqDate", "${sessionScope.sessionInfo.startDate}");

    $scope._setComboData("srchDateFg", [
      {"name": messages["rtnDstbCloseProd.reqDate"], "value": "req"},
      {"name": messages["rtnDstbCloseProd.regDate"], "value": "reg"},
      {"name": messages["rtnDstbCloseProd.modDate"], "value": "mod"}
    ]);

    $scope._setComboData("srchProcFg", [
      {"name": "<s:message code='rtnDstbCloseProd.procFgAll'/>", "value": ""},
      {"name": "<s:message code='rtnDstbCloseProd.procFgReg'/>", "value": "00"},
      {"name": "<s:message code='rtnDstbCloseProd.procFgMd'/>", "value": "10"},
      {"name": "<s:message code='rtnDstbCloseProd.procFgDstbClose'/>", "value": "20"},
      {"name": "<s:message code='rtnDstbCloseProd.procFgSlip'/>", "value": "30"}
    ]);
    $scope.procFg = "10"; // 진행구분 기본값 세팅

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "00", name: "<s:message code='rtnDstbCloseProd.procFgReg'/>"},
      {id: "10", name: "<s:message code='rtnDstbCloseProd.procFgMd'/>"},
      {id: "20", name: "<s:message code='rtnDstbCloseProd.procFgDstbClose'/>"},
      {id: "30", name: "<s:message code='rtnDstbCloseProd.procFgSlip'/>"}
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("rtnDstbCloseProdCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "prodCd") { // 상품코드
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
          else if (col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
            var item = s.rows[e.row].dataItem;
            if (item.procFg !== "10") {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              s.rows[e.row].isReadOnly = true;
            }
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
          if (col.binding === "prodCd") { // 상품코드 클릭
            var params     = {};
            params.reqDate = selectedRow.reqDate;
            params.prodCd  = selectedRow.prodCd;
            params.prodNm  = selectedRow.prodNm;
            params.slipFg  = selectedRow.slipFg;
            params.procFg  = selectedRow.procFg;
            $scope._broadcast('rtnDstbCloseProdDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnDstbCloseProdCtrl", function (event, data) {
      $scope.searchRtnDstbCloseProdList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 주문 리스트 조회
    $scope.searchRtnDstbCloseProdList = function () {
      // 파라미터
      var params       = {};
      params.dateFg    = $scope.dateFg;
      params.slipFg    = $scope.slipFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProd/list.sb", params);
    };

    $scope.saveConfirm = function () {
      // 선택하신 자료를 반품마감으로 확정합니다. 확정하시겠습니까?
      var msg = messages["rtnDstbCloseProd.confirmText"];
      s_alert.popConf(msg, function () {
        var params = new Array();
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];

          if (item.gChk === true) {
            item.status    = "U";
            item.empNo     = "0000";
            item.storageCd = "001";
            item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
            params.push(item);
          }
        }
        $scope._save("/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProd/saveConfirm.sb", params, function () {
          $scope.searchRtnDstbCloseProdList()
        });
      });
    };

    $scope.add = function () {
      var params     = {};
      params.reqDate = wijmo.Globalize.format(reqDate.value, 'yyyyMMdd');
      params.slipFg  = $scope.slipFg;
      $scope._broadcast('rtnDstbCloseProdAddProdCtrl', params);
    };
  }]);
</script>

<%-- 분배마감 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배마감 추가등록 상품조회 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddProd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 분배마감 추가등록 분배등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstbCloseProd/rtnDstbCloseProdAddRegist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
