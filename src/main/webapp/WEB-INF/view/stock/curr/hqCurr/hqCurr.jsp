<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/curr/hqCurr/hqCurr/"/>

<div class="subCon" ng-controller="hqCurrCtrl">
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
      <%-- 상품코드 --%>
      <th><s:message code="hqCurr.prodCd"/></th>
      <td>
        <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
      </td>
      <%-- 상품명 --%>
      <th><s:message code="hqCurr.prodNm"/></th>
      <td>
        <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
      </td>
    </tr>
    <tr>
      <%-- 바코드 --%>
      <th><s:message code="hqCurr.barcdNm"/></th>
      <td>
        <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
      </td>
      <%-- 단위구분 --%>
      <th><s:message code="hqCurr.unitFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchUnitFg"
              ng-model="unitFg"
              items-source="_getComboData('srchUnitFg')"
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
      <%-- 거래처 --%>
      <th><s:message code="hqCurr.vendrNm"/></th>
      <td>
      </td>
      <%-- 분류 --%>
      <th><s:message code="hqCurr.prodClass"/></th>
      <td>
      </td>
    </tr>
    <tr>
      <%-- 안전재고 --%>
      <th><s:message code="hqCurr.safeStockFg"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn w150px">
            <wj-combo-box
              id="srchSafeStockFg"
              ng-model="safeStockFg"
              items-source="_getComboData('srchSafeStockFg')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </div>
      </td>
      <c:choose>
        <c:when test="${envst008 != null && envst008 != '00'}">
          <%-- 상품구분 --%>
          <th><s:message code="storeCurr.weightFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchWeightFg"
                  ng-model="weightFg"
                  items-source="_getComboData('srchWeightFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </c:when>
        <c:otherwise>
          <th></th>
          <td></td>
        </c:otherwise>
      </c:choose>
    </tr>
    </tbody>
  </table>

  <ul class="txtSty3 mt10">
    <li><s:message code="hqCurr.txt1"/></li>
  </ul>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('hqCurrCtrl')">
      <s:message code="cmm.search"/></button>
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
        is-read-only="true"
        item-formatter="_itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="hqCurr.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.poUnitQty"/>" binding="poUnitQty" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrCd"/>" binding="vendrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.vendrNm"/>" binding="vendrNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.barcdNm"/>" binding="barcdNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.costUprc"/>" binding="costUprc" width="80" align="right" is-read-only="true" data-type="Number"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.safeStockQty"/>" binding="safeStockQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currQty"/>" binding="currQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.currAmt"/>" binding="currAmt" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accVendrInQty"/>" binding="accVendrInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accVendrOutQty"/>" binding="accVendrOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.hqOutQty"/>" binding="hqOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.hqInQty"/>" binding="hqInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveInQty"/>" binding="accStoreMoveInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accStoreMoveOutQty"/>" binding="accStoreMoveOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accDisuseQty"/>" binding="accDisuseQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accAdjQty"/>" binding="accAdjQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accSetInQty"/>" binding="accSetInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrOutQty"/>" binding="accSaleVendrOutQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.accSaleVendrInQty"/>" binding="accSaleVendrInQty" width="80" align="right" is-read-only="true" data-type="Number" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstVendrInDate"/>" binding="firstVendrInDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastVendrInDate"/>" binding="lastVendrInDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstHqOutDate"/>" binding="firstHqOutDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastHqOutDate"/>" binding="lastHqOutDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.firstSaleDate"/>" binding="firstSaleDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="hqCurr.lastSaleDate"/>" binding="lastSaleDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="hqCurrCtrl"/>
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

  /** 현재고현황 그리드 controller */
  app.controller('hqCurrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqCurrCtrl', $scope, $http, true));

    $scope._setComboData("srchUnitFg", [
      {"name": messages["hqCurr.unitStockFg"], "value": "stock"},
      {"name": messages["hqCurr.unitOrderFg"], "value": "order"}
    ]);

    $scope._setComboData("srchWeightFg", [
      {"name": messages["hqCurr.weightFg0"], "value": "0"},
      {"name": messages["hqCurr.weightFg1"], "value": "1"}
    ]);

    $scope._setComboData("srchSafeStockFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["hqCurr.safeStockFg0"], "value": "0"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "097";
      var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
      $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("hqCurrCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
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
          // if (col.binding === "currQty") { // 전표번호 클릭
          //   var params    = {};
          //   params.prodCd = selectedRow.prodCd;
          //   $scope._broadcast('hqCurrDtlCtrl', params);
          // }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqCurrCtrl", function (event, data) {
      $scope.searchHqCurrList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 현재고현황 리스트 조회
    $scope.searchHqCurrList = function () {
      // 파라미터
      var params = {};

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/stock/curr/hqCurr/hqCurr/list.sb", params);
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
      var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
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
        if ($scope._httpStatusCheck(response, true)) {
          if (!$.isEmptyObject(response.data.data.list)) {
            var list       = response.data.data.list;
            var comboArray = [];
            var comboData  = {};

            if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
              comboArray = [];
              if (option === "A") {
                comboData.name  = messages["cmm.all"];
                comboData.value = "";
                comboArray.push(comboData);
              } else if (option === "S") {
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

            if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
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
      }, function errorCallback(response) {
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

<%-- 현재고현황 상세 레이어 --%>
<%--<c:import url="/WEB-INF/view/stock/curr/hqCurr/hqCurrDtl.jsp">--%>
<%--<c:param name="menuCd" value="${menuCd}"/>--%>
<%--<c:param name="menuNm" value="${menuNm}"/>--%>
<%--</c:import>--%>
