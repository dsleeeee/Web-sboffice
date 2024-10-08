<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:170px;"
       value=<s:message code="cmm.chk"/>
       ng-click="<c:out value="${param.targetId}"/>Show()"
       readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerM" control="wj<c:out value="${param.targetId}"/>LayerM" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="cmm.hq.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
      <div class="w100">

        <%-- 조회조건 --%>
        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="cmm.hedofc.cd" /></th>
            <td>
              <input type="text" id="srchHqOfficeCd" ng-model="srchHqOfficeCd"/>
            </td>
            <th><s:message code="cmm.hedofc.nm" /></th>
            <td>
              <input type="text" id="srchHqOfficeNm" ng-model="srchHqOfficeNm"/>
            </td>
          </tr>
          </tbody>
        </table>

        <%-- 버튼영역 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" ng-click="searchHqOffice()"><s:message code="cmm.search" /></button>
          <button class="btn_skyblue" ng-click="hqOfficeSelected()"><s:message code="cmm.chk"/></button>
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
            <wj-flex-grid-column header="<s:message code="cmm.hedofc.cd"/>" binding="hqOfficeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.hedofc.nm"/>" binding="hqOfficeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

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

  /** 본사선택 controller */
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
      // 본사선택 팝업 오픈
      eval('$scope.wj' + targetId + 'LayerM.show(true)');

      $scope.srchHqOfficeCd = '';
      $scope.srchHqOfficeNm = '';
      $scope.searchHqOffice();
      
      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + targetId + 'LayerM').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
			$scope.hqOfficeSelected();
//           eval('$scope.${param.closeFunc}()');
        }
      });

      if ($scope.searchFg == "N") {
        $scope.searchHqOffice();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchHqOffice = function () {
      // 파라미터
      var params = {};
      params.hqOfficeCd = $scope.srchHqOfficeCd;
      params.hqOfficeNm = $scope.srchHqOfficeNm;

      $scope._inquirySub("/iostock/cmm/iostockCmm/selectHqList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };

    $scope.hqOfficeSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
      // var flex = $scope.hqOfficeGridM;
      var arrHqOfficeCd = [];
      var strHqOfficeCd = "";
      var strHqOfficeNm = "";
      var cnt        = 0;

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strHqOfficeCd = flex[i].hqOfficeCd;
            // strHqOfficeNm = "["+flex[i].hqOfficeCd+"] "+flex[i].hqOfficeNm;
            strHqOfficeNm = flex[i].hqOfficeNm;
          }
          arrHqOfficeCd.push(flex[i].hqOfficeCd);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrHqOfficeCd.join());
      if (cnt == 0) {
        $("#" + targetId + "Nm").val(messages["cmm.chk"]);
        $("#" + targetId +"HqOfficeNum").val("");
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val("[" + strHqOfficeCd + "] " + strHqOfficeNm);
        $("#" + targetId +"HqOfficeNum").val(" 영업본사수 : "+cnt+" 개");
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strHqOfficeNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["outstockReqDate.cntHq"]);
        $("#" + targetId +"HqOfficeNum").val(" 영업본사수 : "+cnt+" 개");
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };
  }]);
</script>
