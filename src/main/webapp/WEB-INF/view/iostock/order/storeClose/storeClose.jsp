<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeClose/storeClose/"/>

<div class="subCon">
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
      <%-- 조회월 --%>
      <th><s:message code="cmm.search.month"/></th>
      <td>
        <div class="sb-select">
          <span class="txtIn"><input id="srchMonth" class="w120"></span>
        </div>
      </td>
    </tr>
    </tbody>
  </table>

  <div ng-controller="storeCloseCtrl">
    <div class="mt10 pdb20 oh">
      <%-- 조회 --%>
      <button class="btn_blue fr" id="btnSearch" ng-click="searchParam();">
        <s:message code="cmm.search"/></button>
    </div>

    <div class="w40 fl" style="width: 40%">
      <div class="wj-TblWrapBr mr10 pd10" style="height: 400px;">
        <div class="oh sb-select mb10">
          <span class="fl bk lh30"><s:message code='storeClose.closeMonthSubTitle'/></span>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 335px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="storeClose.closeMonth"/>" binding="closeMonth" width="0" align="center" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.closeMonthNm"/>" binding="closeMonthNm" width="60" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.dateCnt"/>" binding="dateCnt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeClose.closeDateCnt"/>" binding="closeDateCnt" width="*" align="right" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
          <%-- ColumnPicker 사용시 include --%>
          <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
            <jsp:param name="pickerTarget" value="storeCloseCtrl"/>
          </jsp:include>
          <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>

  <div class="w60 fr" style="width: 60%" ng-controller="storeCloseDtlCtrl">
    <div class="wj-TblWrapBr pd10" style="height: 400px;">

      <div class="oh sb-select mb10">
        <span class="fl bk lh30"><s:message code='storeClose.closeDateSubTitle'/></span>
        <div class="tr">
          <%-- 저장 --%>
          <button class="btn_skyblue" ng-click="saveStoreClose()">
            <s:message code="cmm.save"/></button>
        </div>
      </div>

      <%--위즈모 테이블--%>
      <div class="wj-gridWrap" style="height: 335px;">
        <wj-flex-grid
          autoGenerateColumns="false"
          selection-mode="Row"
          items-source="data"
          control="flex"
          initialized="initGrid(s,e)"
          is-read-only="false"
          item-formatter="_itemFormatter">

          <!-- define columns -->
          <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                  binding="gChk"         width="40" align="center"></wj-flex-grid-column>--%>
          <wj-flex-grid-column header="<s:message code="storeClose.closeDate"/>" binding="closeDate" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.closeDateNm"/>" binding="closeDateNm" width="60" align="center" is-read-only="true" format="userFormat"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseFg"/>" binding="prevOrderCloseFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseFg"/>" binding="orderCloseFg" width="80" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseDt"/>" binding="orderCloseDt" width="*" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
          <wj-flex-grid-column header="<s:message code="storeClose.orderCloseNm"/>" binding="orderCloseNm" width="80" align="center" is-read-only="true"></wj-flex-grid-column>

        </wj-flex-grid>
        <%-- ColumnPicker 사용시 include --%>
        <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
          <jsp:param name="pickerTarget" value="storeCloseDtlCtrl"/>
        </jsp:include>
        <%--// ColumnPicker 사용시 include --%>
      </div>
      <%--//위즈모 테이블--%>
    </div>
  </div>
</div>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 마감월 그리드 controller */
  app.controller('storeCloseCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeCloseCtrl', $scope, $http, true));

    $scope.srchMonth = new wijmo.input.InputDate('#srchMonth', {
      format       : "yyyy-MM",
      selectionMode: "2" // 달력 선택 모드(1:day 2:month)
    });

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("storeCloseCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "closeMonthNm") { // 마감월
            let item = s.rows[e.row].dataItem;
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
          if (col.binding === "closeMonthNm") { // 마감월
            $scope.searchMonth = selectedRow.closeMonth;
            $scope._broadcast('storeCloseDtlCtrl', selectedRow.closeMonth);
          }
        }
      });

      $scope.searchParam(); // 그리드 초기화 후 자동 조회
    };

    // 조회버튼 클릭시 조회월 값 세팅. 마감일자에서 마감여부 저장 후 마감월 그리드 조회해주기 위해 조회월 파라미터 세팅 분리함.
    $scope.searchParam = function () {
      $scope.searchMonth = wijmo.Globalize.format($scope.srchMonth.value, 'yyyyMM');
      $scope.searchStoreCloseList();
    };

    // 마감월 조회
    $scope.searchStoreCloseList = function () {
      // 파라미터
      var params        = {};
      params.closeMonth = $scope.searchMonth;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/order/storeClose/storeClose/list.sb", params, function () {
        $scope._broadcast("storeCloseDtlCtrl", $scope.searchMonth);
      });
    };
  }]);

  /** 마감월 상세 그리드 controller */
  app.controller('storeCloseDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('storeCloseDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("storeCloseDtlCtrl");

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
          else if (col.format === "userFormat") {
            var date  = e.cell.innerText;
            var month = date.substr(0, 2);
            var day   = date.substr(2, 2);

            e.cell.innerHTML = (month + "-" + day);
          }
        }
      });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("storeCloseDtlCtrl", function (event, data) {
      $scope.searchDtlMonth = data; // DTL 조회월
      $scope.searchStoreCloseDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 마감일자 조회
    $scope.searchStoreCloseDtlList = function () {
      // 파라미터
      var params        = {};
      params.closeMonth = $scope.searchDtlMonth;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/order/storeClose/storeClose/dtlList.sb", params);
    };

    // 저장
    $scope.saveStoreClose = function () {
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        $scope.flex.collectionView.itemsEdited[i].status = "U";
        params.push($scope.flex.collectionView.itemsEdited[i]);
      }
      $scope._save("/iostock/order/storeClose/storeClose/save.sb", params, function () {
        $scope.saveStoreCloseCallback();
      });
    };

    // 저장 후 콜백
    $scope.saveStoreCloseCallback = function () {
      var storeCloseScope = agrid.getScope('storeCloseCtrl');
      storeCloseScope.searchStoreCloseList();
    };

  }]);
</script>
