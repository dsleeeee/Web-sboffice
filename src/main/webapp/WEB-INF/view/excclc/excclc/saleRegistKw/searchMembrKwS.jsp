<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="<c:out value="${param.targetId}No"/>" />
<input type="text" id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5 w100" style="cursor:pointer;" <c:if test="${empty param.modiFg}"> ng-click="<c:out value="${param.targetId}"/>Show()" </c:if> readonly/>
<c:if test="${empty param.modiFg}">
</c:if>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerS" control="wj<c:out value="${param.targetId}"/>LayerS" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.store.select"/>
      <a href="#" class="wj-hide btn_close" ng-click="closePopup()"></a>
    </div>
    <div class="wj-dialog-body">
      <div class="w100">

        <%-- 조회 조건 --%>
        <table class="tblType01 mt5">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="saleRegistKw.membrNo"/></th>
            <td><input type="text" id="srchMembrNo" ng-model="membrNo"/></td>
            <th><s:message code="saleRegistKw.membrNm"/></th>
            <td><input type="text" id="srchMembrNm" ng-model="membrNm"/></td>
          </tr>
          </tbody>
        </table>
        <%-- 조회 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" id="btnSearch" ng-click="getMembrList();" ><s:message code="cmm.search" /></button>
        </div>


        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="saleRegistKw.membrNo"/>" binding="membrNo" width="120" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="saleRegistKw.membrNm"/>" binding="membrNm" width="*" align="left"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>

      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="<c:out value="${param.targetId}"/>CtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 매장선택 controller */
  app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

    $scope.targetId = "${param.targetId}";
    $("#"+$scope.targetId+"Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController($scope.targetId + 'Ctrl', $scope, $http, true));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel == s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "membrNo") {
            var item = s.rows[e.row].dataItem;
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
        }
      });

      // 그리드 클릭 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
          var col         = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if (col.binding === "membrNo") {
            $("#" + $scope.targetId + "No").val(selectedRow.membrNo);
            $("#" + $scope.targetId + "Nm").val("[" + selectedRow.membrNo + "] " + selectedRow.membrNm);
            eval('$scope.wj' + $scope.targetId + 'LayerS.hide(true)');
            $scope.closePopup();
          }
        }
      });
    };

    $scope.searchFg = "N"; // 조회 했는지 여부
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on($scope.targetId + 'Ctrl', function (event, paramObj) {
      // 매장선택 팝업 오픈
      eval('$scope.wj' + $scope.targetId + 'LayerS.show(true)');
      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + $scope.targetId + 'LayerS').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
          eval('$scope.${param.closeFunc}()');
        }
      });

      // if ($scope.searchFg == "N") {
        $scope.searchMembr();
      // }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 조회버튼 클릭 시 매장목록 조회
    $scope.getMembrList= function () {
      $scope._pageView( $scope.targetId + "Ctrl", 1);
    };

    $scope.searchMembr = function () {
      // 파라미터
      var params = {};
      params.membrNo = $scope.membrNo;
      params.membrNm = $scope.membrNm;
      $scope._inquirySub("/excclc/excclc/saleRegistKw/saleRegistKw/getMembrKwList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };

    // 닫을때 초기화 로직 추가
    $scope.closePopup = function(){
        $scope.membrNo = "";
        $scope.membrNm = "";
    };

  }]);


</script>
