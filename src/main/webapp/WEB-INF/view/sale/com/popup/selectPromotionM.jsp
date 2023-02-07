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

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerM" control="wj<c:out value="${param.targetId}"/>LayerM" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      프로모션선택
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
            <th><s:message code="promotion.promotionCd" /></th>
            <td>
              <input type="text" id="srchPromotionCd" ng-model="srchPromotionCd"/>
            </td>
            <th><s:message code="promotion.promotionNm" /></th>
            <td>
              <input type="text" id="srchPromotionNm" ng-model="srchPromotionNm"/>
            </td>
          </tr>
        <%-- 버튼영역 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" ng-click="searchPromotion()"><s:message code="cmm.search" /></button>
          <button class="btn_skyblue" ng-click="promotionSelected()"><s:message code="cmm.chk"/></button>
        </div>
          </tbody>
        </table>

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
            <wj-flex-grid-column header="<s:message code="promotion.promotionCd"/>" binding="promotionCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promotion.promotionNm"/>" binding="promotionNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="promotion.depositYmd"/>" binding="depositYmd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

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
    var targetCornerId = '${param.targetCornerId}';
    var targetTableId = '${param.targetTableId}';
    var targetPosId = '${param.targetPosId}';
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    $scope.searchFg = "N";
    // 다른 컨트롤러의 broadcast 받기

    $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
      // 매장선택 팝업 오픈
      eval('$scope.wj' + targetId + 'LayerM.show(true)');

      // 팝업 닫힐시 이벤트
      eval('$scope.wj' + targetId + 'LayerM').hidden.addHandler(function () {
        if ('${param.closeFunc}' !== '') {
            if('${param.closeFunc}'.indexOf(',')>-1){
                var closeFunc = ('${param.closeFunc}').split(",");
                for(var i=0; i<closeFunc.length; i++){
                    eval('$scope.'+closeFunc[i]+'()');
                }
            }else{
                eval('$scope.${param.closeFunc}()');                            
            }
        }
      });

      if ($scope.searchFg == "N") {
        $scope.searchPromotion();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchPromotion = function () {
        // 파라미터
        var params = {};
        params.promotionCd = $scope.srchPromotionCd;
        params.promotionNm = $scope.srchPromotionNm;

        $scope._inquirySub("/iostock/cmm/iostockCmm/selectPromotionList.sb", params, function () {
            $scope.searchFg = "Y";
        });
    };

    $scope.promotionSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
      // var flex = $scope.promotionGridM;
      var arrPromotionCd = [];
      var strPromotionCd = "";
      var strPromotionNm = "";
      var cnt        = 0;

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strPromotionCd = flex[i].promotionCd;
            // strPromotionNm = "["+flex[i].promotionCd+"] "+flex[i].promotionNm;
            strPromotionNm = flex[i].promotionNm;
          }
          arrPromotionCd.push(flex[i].promotionCd);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrPromotionCd.join());
      $("#" + targetPosId + "Cd").val("");
      $("#" + targetPosId + "Name").val(messages["cmm.all"]);
      
      $("#" + targetCornerId + "Cd").val("");
      $("#" + targetCornerId + "Name").val(messages["cmm.all"]);

      $("#" + targetTableId + "Cd").val("");
      $("#" + targetTableId + "Name").val(messages["cmm.all"]);
      
      if (cnt == 0) {
        $("#" + targetId + "Nm").val(messages["cmm.all"]);
        $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
        $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
        $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
        $("#" + targetId +"PromotionNum").val("");
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val("[" + strPromotionCd + "] " + strPromotionNm);
        $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
        $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
        $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
        $("#" + targetId +"PromotionNum").val(" 영업매장 : "+cnt+" 개");
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strPromotionNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["outstockReqDate.cntPromotion"]);
        $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
        $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
        $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
        $("#" + targetId +"PromotionNum").val(" 영업매장 : "+cnt+" 개");
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };
  }]);
</script>
