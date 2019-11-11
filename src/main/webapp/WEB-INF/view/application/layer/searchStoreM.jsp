<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text" id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5" style="cursor:pointer; width:100%;" value="전체" ng-click="<c:out value="${param.targetId}"/>Show()" readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerM" control="wj<c:out value="${param.targetId}"/>LayerM" show-trigger="Click" hide-trigger="Click" style="display:none;width:600px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.store.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
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
            <th><s:message code="cmm.storeCd"/></th>
            <td><input type="text" id="srchStoreCd" ng-model="storeCd" maxlength="5" /></td>
            <th><s:message code="cmm.storeNm"/></th>
            <td><input type="text" id="srchStoreNm" ng-model="storeNm" maxlength="16" /></td>
          </tr>
          </tbody>
        </table>
        <%-- 조회 --%>
        <%--<div class="mt10 tr">
          <button class="btn_skyblue" id="btnSearch" ng-click="getStoreList();" ><s:message code="cmm.search" /></button>
        </div>--%>

        <div class="mt20 oh sb-select dkbr">
          <%-- 페이지 스케일  --%>
            <wj-combo-box
                    class="w100px fl"
                    id="listScaleBoxM"
                    ng-model="listScale"
                    items-source="_getComboData('listScaleBoxM')"
                    display-member-path="name"
                    selected-value-path="value"
                    is-editable="false"
                    initialized="initComboBox(s)">
            </wj-combo-box>
          <%--// 페이지 스케일  --%>
          <%-- 선택 --%>
          &nbsp;
          <button class="btn_skyblue" ng-click="storeSelected()"><s:message code="cmm.chk"/></button>
          <button class="btn_skyblue fr" id="btnSearch" ng-click="getStoreList()" ><s:message code="cmm.search" /></button>
        </div>
        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
          <%--control="storeGridM"--%>
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

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
    var targetId = '${param.targetId}';
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, true));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBoxM", gvListScaleBoxData2);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on(targetId + 'Ctrl', function (event, paramObj) {

      // 매장선택 팝업 오픈
      eval('$scope.wj' + targetId + 'LayerM.show(true)');

      $scope.searchStore();
      event.preventDefault();
    });

    $scope.searchStore = function () {
      // 파라미터
      var params = {};
      params.storeCd = $scope.storeCd;
      params.storeNm = $scope.storeNm;
      params.listScale  = $scope.listScale;
      $scope._inquirySub("/popup/getStoreList.sb", params, function () {
      });
    };

    $scope.storeSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;

      var arrStoreCd = new Array();
      var strStoreCd = "";
      var strStoreNm = "";
      var cnt        = 0;

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strStoreCd = flex[i].storeCd;

            strStoreNm = flex[i].storeNm;
          }
          arrStoreCd.push(flex[i].storeCd);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrStoreCd.join());
      if (cnt == 0) {
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strStoreNm + " "+messages["cmm.except"]+" " + (cnt - 1) + messages["cmm.cntStore"]);
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };

    // 조회버튼 클릭 시 매장목록 조회
    $scope.getStoreList= function () {
      $scope.searchStore();
    };

  }]);
</script>
