<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:200px;"
       value=<s:message code="cmm.all"/>
       ng-click="<c:out value="${param.targetId}"/>Show()"
       readonly/>
<input type="hidden" id="<c:out value="${param.targetId}Check"/>"/>
<button type="button" class="btn_skyblue fl" id="<c:out value="${param.targetId}"/>btnCancelPosCd" style="margin-left: 5px;" onclick="delPos('<c:out value="${param.targetId}"/>')"><s:message code="cmm.selectCancel"/></button>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerM" control="wj<c:out value="${param.targetId}"/>LayerM" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.pos.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
      <div class="w100">
        <div class="oh sb-select dkbr">
          <button class="btn_skyblue fr" ng-click="posSelected()">
            <s:message code="cmm.chk"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px;">
          <wj-flex-grid
            id="wjGridPos${param.targetId}"
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="posGridData"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="status.store.nm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="verManage.store.posNm"/>" binding="posNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
			<wj-flex-grid-column header="" binding="posCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  // 포스선택 초기화
  function delPos(targetId){
    $("#" + targetId+ "Cd").val("");
    $("#" + targetId + "Nm").val(messages["cmm.all"]);
    // 선택취소 클릭 시 값 저장
    $("#" + targetId + "Check").val('Cancel');
  }

  /**
   * get application
   */
  var app = agrid.getApp();

  /** 포스선택 controller */
  app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {
    var targetId = '${param.targetId}';

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.searchFg = "";
    // 다른 컨트롤러의 broadcast 받기

    $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
      // 포스선택 팝업 오픈
      if($("#${param.targetStoreId}Cd").val() !== ""){
    	  eval('$scope.wj' + targetId + 'LayerM.show(true)');
      }

      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + targetId + 'LayerM').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
          eval('$scope.${param.closeFunc}()');
        }
      });

      // 화면에서 선택취소 클릭 후 진입 시
      if($("#" + targetId + "Check").val() === 'Cancel'){
        var grid = wijmo.Control.getControl("#wjGridPos" + targetId);

        if(grid.rows.length > 0){

          for (var i = 0; i < grid.rows.length; i++) {
            grid.rows[i].dataItem.gChk = false;
          }
          grid.refresh();
        }
        $("#" + targetId + "Check").val('');
      }

      $scope.searchPos();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchPos = function () {
      // 파라미터
      var params = {};
      params.storeCd = $("#${param.targetStoreId}Cd").val();

      if(params.storeCd !== "" && params.storeCd !== $scope.searchFg){
	      $.postJSON("/sale/status/pos/pos/posNmList.sb", params, function (response) {
	        $scope.searchFg = params.storeCd;
            var grid = wijmo.Control.getControl("#wjGridPos" + targetId);
            var columns = grid.columns;

            if(response.status === "FAIL") {
              s_alert.pop(response.message);
              grid.itemsSource = new wijmo.collections.CollectionView([]);
              return;
            }
            grid.itemsSource = response.data.list;
            grid.itemsSource.trackChanges = true;
	      }, function (response) {
            s_alert.pop(response.message);
            var grid = wijmo.Control.getControl("#wjGridPos" + targetId);
            grid.itemsSource = new wijmo.collections.CollectionView([]);
	      });
      }else if(params.storeCd === ""){
          $scope._popMsg(messages["cmm.require.selectStore"]);
      }
    };

    $scope.posSelected = function () {
      var grid = wijmo.Control.getControl("#wjGridPos" + targetId);

      var arrPosCd = [];
      var arrPosNm = [];
      var strPosCd = "";
      var strStoreNm = "";
      var strPosNm = "";
      var cnt        = 0;

      $("#" + targetId + "Cd").val("");
      $("#" + targetId + "Name").val("");

      for (var i = 0; i < grid.rows.length; i++) {
        var item = grid.rows[i].dataItem;
        if (item && item.gChk) {
          if (cnt == 0) {
            strPosCd = item.posCd;
            strStoreNm = item.storeNm;
            strPosNm = item.posNm;
          }
          arrPosCd.push(item.posCd);
          arrPosNm.push(item.storeNm + "||" + item.posNm);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrPosCd.join());
      $("#" + targetId + "Name").val(arrPosNm.join());

      if (cnt == 0) {
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val(strPosNm);
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strPosNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["pos.posSelect"]);
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };


    // 매장 선택취소 버튼 클릭 시 포스 값도 초기화 (selectStore.jsp)
    if('${param.targetStoreId}' !== '' && $("#${param.targetStoreId}btnCancelStoreCd").length > 0) {
        $("#${param.targetStoreId}btnCancelStoreCd").on("click", function() {
        $("#" + targetId + "Cd").val("");
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
        $scope.searchFg = "";
      });
    }

    // 매장 선택취소 버튼 클릭 시 포스 값도 초기화 (selectStoreS.jsp)
    if('${param.targetStoreId}' !== '' && $("#${param.targetStoreId}SelectCancelBtn").length > 0) {
      $("#${param.targetStoreId}SelectCancelBtn").on("click", function() {
        $("#" + targetId + "Cd").val("");
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
        $scope.searchFg = "";
      });
    }

    // 매장 선택 시 포스 값 초기화 (selectStore.jsp)
    if('${param.targetStoreId}' !== '' && $("#wj${param.targetStoreId}Layer").length > 0) {
      var storePopup = wijmo.Control.getControl("#wj${param.targetStoreId}Layer");
      if(storePopup) {
        storePopup.hidden.addHandler(function() {
          var currentStoreCd = $("#${param.targetStoreId}Cd").val();
          // 매장이 변경되었으면 포스 값 초기화
          if(currentStoreCd !== $scope.searchFg) {
            console.log(0);
            $("#" + targetId + "Cd").val("");
            $("#" + targetId + "Nm").val(messages["cmm.all"]);
            $scope.searchFg = "";
          }
        });
      }
    }



  }]);
</script>
