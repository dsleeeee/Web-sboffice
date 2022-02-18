<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:200px;"
       value=<s:message code="cmm.chk"/>
               ng-click="<c:out value="${param.targetId}"/>Show()"
       readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.hqStore.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
      <div class="w100">

        <%-- 조회조건 --%>
        <table class="tblType01">
          <colgroup>
            <col class="w20" />
            <col class="w30" />
            <col class="w20" />
            <col class="w30" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="cmm.hqStoreCd" /></th>
            <td>
              <input type="text" id="srchStoreCd" ng-model="srchStoreCd"/>
            </td>
            <th><s:message code="cmm.hqStoreNm" /></th>
            <td>
              <input type="text" id="srchStoreNm" ng-model="srchStoreNm"/>
            </td>
          </tr>
          </tbody>
        </table>

        <%-- 버튼영역 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" ng-click="searchStore()"><s:message code="cmm.search" /></button>
          <button class="btn_skyblue" ng-click="storeSelected()"><s:message code="cmm.chk"/></button>
        </div>

        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px; overflow-x: hidden; overflow-y: hidden;">
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
            <c:if test="${orgnFg != 'HQ'}">
              <wj-flex-grid-column header="<s:message code="cmm.status"/>" binding="gubun" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            </c:if>
            <wj-flex-grid-column header="<s:message code="cmm.hqStoreCd"/>" binding="cd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.hqStoreNm"/>" binding="nm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

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

  /** 매장선택 controller */
  app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {
    var targetId = '${param.targetId}';
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.searchFg = "N";
    // 다른 컨트롤러의 broadcast 받기

    $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
      // 매장선택 팝업 오픈
      eval('$scope.wj' + targetId + 'Layer.show(true)');

      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + targetId + 'Layer').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
          $scope.storeSelected();
//           eval('$scope.${param.closeFunc}()');
        }
      });

      if ($scope.searchFg == "N") {
        $scope.searchStore();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchStore = function () {
      // 파라미터
      var params = {};
      params.storeCd = $scope.srchStoreCd;
      params.storeNm = $scope.srchStoreNm;

      $scope._inquirySub("/adi/board/board/board/selectHqStoreList.sb", params, function () {
        $scope.searchFg = "Y";

        // 팝업 처음 오픈 시, 기본 본인 매장정보로 셋팅
        var flex = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
        for (var i = 0; i < flex.length; i++) {
          if ($("#" + targetId + "Cd").val().includes(flex[i].cd)) {
            flex[i].gChk = true;
          }
        }
      });
    };

    $scope.storeSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
      // var flex = $scope.storeGridM;
      var arrStoreCd = [];
      var strStoreCd = "";
      var strStoreNm = "";
      var cnt        = 0;

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strStoreCd = flex[i].cd;
            // strStoreNm = "["+flex[i].storeCd+"] "+flex[i].storeNm;
            strStoreNm = flex[i].nm;
          }
          arrStoreCd.push(flex[i].cd);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrStoreCd.join());
      if (cnt == 0) {
        $("#" + targetId + "Nm").val(messages["cmm.chk"]);
        $("#" + targetId +"StoreNum").val("");
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
        $("#" + targetId +"StoreNum").val(" 영업매장수 : "+cnt+" 개");
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strStoreNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["outstockReqDate.cntStore"]);
        $("#" + targetId +"StoreNum").val(" 영업매장수 : "+cnt+" 개");
      }
      eval('$scope.wj' + targetId + 'Layer.hide(true)');
    };
  }]);
</script>
