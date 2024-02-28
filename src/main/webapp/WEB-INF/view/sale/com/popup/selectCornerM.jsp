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

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerM" control="wj<c:out value="${param.targetId}"/>LayerM" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.corner.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
      <div class="w100">
        <div class="oh sb-select dkbr">
          <button class="btn_skyblue fr" ng-click="cornerSelected()">
            <s:message code="cmm.chk"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px;">
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
            <wj-flex-grid-column header="<s:message code="status.store.cd"/>" binding="storeCd" width="80" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="status.store.nm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.cornrCd"/>" binding="cornrCd2" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="storeManage.cornrNm"/>" binding="cornrNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="" binding="cornrCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
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
        	$scope.cornerSelected();
//           eval('$scope.${param.closeFunc}()');
        }
      });

      $scope.searchCorner();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchCorner = function () {
      // 파라미터
      var params = {};
      params.storeCd = $("#${param.targetStoreId}Cd").val();
      
      if(params.storeCd !== "" && params.storeCd !== $scope.searchFg){
	      $scope._inquiryMain("/sale/status/corner/corner/cornerNmList.sb", params, function () {
	        $scope.searchFg = params.storeCd;
	      });
      }else if(params.storeCd === ""){
          $scope._popMsg(messages["cmm.require.selectStore"]);
      }
    };

    $scope.cornerSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
      // var flex = $scope.posGridM;

      var arrCornrCd = [];
      var arrCornrCdOrg = [];
      var arrCornrNm = [];
      var strCornrCd = "";
      var strCornrCdOrg = "";
      var strStoreNm = "";
      var strCornrNm = "";
      var cnt        = 0;
      
      $("#" + targetId + "Cd").val("");
      $("#" + targetId + "Name").val("");
      $("#" + targetId + "CdOrg").val("");

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strCornrCd = flex[i].cornrCd;
            strCornrCdOrg = flex[i].cornrCdOrg;
            strStoreNm = flex[i].storeNm;
            strCornrNm = flex[i].cornrNm;
          }
          arrCornrCd.push(flex[i].cornrCd);
          arrCornrCdOrg.push(flex[i].cornrCdOrg);
          arrCornrNm.push(flex[i].storeNm + "||" + flex[i].cornrNm);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrCornrCd.join());
      $("#" + targetId + "Name").val(arrCornrNm.join());
      $("#" + targetId + "CdOrg").val(arrCornrCdOrg.join());
      
      if (cnt == 0) {
//     	$("#" + targetId + "Cd").val(arrCornrCd.join());
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
      }
      else if (cnt == 1) {
    	var strCornrCd = strCornrCd.split("||");
        $("#" + targetId + "Nm").val("[" + strCornrCd[0] + "] " + strCornrNm);
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strCornrNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["corner.cornerSelect"]);
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };
  }]);
</script>
