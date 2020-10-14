<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

  <div id="posFuncAuthArea" style="display:none;"  ng-controller="funcAuthCtrl">
    <h2 class="h2_tit" id="posFuncAuthTitle"></h2>

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
      <%-- 포스기능 사용관리 --%>
      <li><a id="posUseManage" href="#"><s:message code="posFunc.manage.posFunc" /></a></li>
      <%-- 포스기능 인증관리 --%>
      <li><a id="posAuthManage"  href="#" class="on"><s:message code="posFunc.auth.posFunc" /></a></li>
    </ul>

    <%-- 왼쪽  --%>
    <div class="wj-TblWrapBr oh mr10 pd20" style="height:560px;">
      <div class="updownSet mb10">
        <span class="fl bk lh30"><s:message code="posFunc.fnkeyFg" /></span>
      </div>
      <%-- 그리드 --%>
      <div id="funcListGrid" style="height:481px; overflow-x: hidden;"></div>
    </div>
  </div>

<%-- grid button --%>
<%--<div id="tplBtnEditMode" style="display:none">
  <button id="btnEnvSetting" class="btn btn-primary btn-sm">
    <span class="glyphicon glyphicon-ok"></span> <s:message code='posFunc.regist.auth' />
  </button>
</div>--%>

<script>

  var selectedFnkey;
  var selectedRow;

  <%-- header --%>
  var funcListHeader =
      [
        {binding:"nmcodeCd", header:"<s:message code='posFunc.fnkeyFg' />", visible:false, width:"*"},
        {binding:"nmcodeNm", header:"<s:message code='posFunc.fnkeyNm' />", width:300},
      ];

  <%-- 그리드 생성 --%>
  var funcListGrid = wgrid.genGrid("#funcListGrid", funcListHeader);

  funcListGrid.isReadOnly = true;

  <%-- 그리드 포맷 --%>
  funcListGrid.formatItem.addHandler(function(s, e) {
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding === "nmcodeNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  funcListGrid.addEventListener(funcListGrid.hostElement, 'click', function(e) {
    var ht = funcListGrid.hitTest(e);
    if ( ht.cellType === wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      if( col.binding === "nmcodeNm" ) {
        selectedRow = funcListGrid.rows[ht.row].dataItem;
        var params = {};
        params.storeCd = selectedStore.storeCd;
        params.nmcodeCd = selectedRow.nmcodeCd;
        params.nmcodeNm = selectedRow.nmcodeNm;

        // 포스기능 키 목록 조회
        var scope = agrid.getScope("funcAuthCtrl");
        scope._broadcast('funcAuthCtrl' , params );

      }
    }
  });

  <%-- 포스기능 인증관리 화면 보여주기 --%>
  function showPosFuncAuth(){
    $("#posFuncAuthArea").show();
    getPosAuthList();
    // 선택매장 표시
    $("#posFuncAuthTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);
  }

  <%-- 포스기능 인증 목록 조회 --%>
  function getPosAuthList() {

    var param = {};

    $.postJSON("/base/store/posfunc/auth/getPosFuncList.sb", param,
        function(result) {

          var list = result.data.list;
          funcListGrid.itemsSource = new wijmo.collections.CollectionView(list);
          //funcAuthGrid.itemsSource = new wijmo.collections.CollectionView([]);
          funcListGrid.collectionView.trackChanges = true;

          selectedRow = "";
        },
        function (result) {
          s_alert.pop(result.message);
          return;
        }
    );
  }

  function hidePosFuncAuth(){
    $("#posFuncAuthArea").hide();
  }

  <%-- 탭 클릭 --%>
  $("#posFuncAuthArea #posUseManage").click(function(){
    hidePosFuncAuth();
    showPosFuncList()
  });
</script>


<script>
  /**
   * get application
   */
  var app = agrid.getApp();

  /** 매장선택 controller */
  app.controller('funcAuthCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('funcAuthCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {};

    $scope.$on('funcAuthCtrl', function (event, data) {

      $scope._broadcast('posFuncAuthDtlCtrl', data);

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

  }]);
</script>
