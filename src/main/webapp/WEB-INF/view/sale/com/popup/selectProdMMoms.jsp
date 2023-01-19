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
      <s:message code="cmm.prod.select"/>
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
            <th><s:message code="outstockReqDate.prodCd" /></th>
            <td>
              <input type="text" id="srchProdCd" ng-model="srchProdCd"/>
            </td>
            <th><s:message code="outstockReqDate.prodNm" /></th>
            <td>
              <input type="text" id="srchProdNm" ng-model="srchProdNm"/>
            </td>
          </tr>
          <tr>
            <c:if test="${sessionInfo.orgnFg == 'HQ'}">
               <%-- 상품브랜드 --%>
              <th><s:message code="outstockReqDate.prodHqBrand" /></th>
              <td>
                <div class="sb-select">
                  <wj-combo-box
                          id="srchPopProdHqBrandCombo"
                          ng-model="popProdHqBrandCd"
                          items-source="_getComboData('popProdHqBrandCdCombo')"
                          display-member-path="name"
                          selected-value-path="value"
                          is-editable="false"
                          control="srchPopProdHqBrandCombo">
                  </wj-combo-box>
                </div>
              </td>
            </c:if>
            <%-- 분류조회 --%>
            <th><s:message code="outstockReqDate.prodClass" /></th>
            <td>
              <input type="text" class="sb-input w70" id="srchPopProdClassCd" ng-model="popProdClassNm" ng-click="popUpProdClass()" style="float: left;"
                     placeholder="<s:message code="outstockReqDate.prodClass" /> 선택" readonly/>
              <input type="hidden" id="_popProdClassCd" name="popProdClassCd" ng-model="popProdClassCd" disabled />
              <button type="button" class="btn_skyblue fl mr5" id="btnCancelPopProdClassCd" style="margin-left: 5px;" ng-click="delProdClass()"><s:message code="cmm.selectCancel"/></button>
            </td>
            <c:if test="${sessionInfo.orgnFg == 'STORE'}">
              <td></td>
              <td></td>
            </c:if>
          </tr>
          </tbody>
        </table>

        <%-- 버튼영역 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" ng-click="searchProd()"><s:message code="cmm.search" /></button>
          <button class="btn_skyblue" ng-click="prodSelected()"><s:message code="cmm.chk"/></button>
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
            <wj-flex-grid-column header="<s:message code="outstockReqDate.prodCd"/>" binding="prodCd" width="150" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="outstockReqDate.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<script type="text/javascript">
  /**
   * get application
   */
  var app = agrid.getApp();

  // 상품브랜드 콤보박스 항목 저장시 쓰려고
  var momsHqBrandCdComboList;

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
      // 상품브랜드
      var params = {};
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBrandMomsList.sb', params, function (response) {
        if (response.data.data.list.length > 0) {
          var list = response.data.data.list;
          $scope._setComboData("popProdHqBrandCdCombo", list);
          // 상품브랜드 콤보박스 항목 저장시 쓰려고
          momsHqBrandCdComboList = list;
        }
      });
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
        $scope.srchPopProdHqBrandCombo.selectedIndex = 0;
      });

      if ($scope.searchFg == "N") {
        $scope.searchProd();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.searchProd = function () {
      // 파라미터
      var params = {};
      params.prodCd = $scope.srchProdCd;
      params.prodNm = $scope.srchProdNm;
      params.prodHqBrandCd = $scope.popProdHqBrandCd;
      params.prodClassCd = $scope.popProdClassCd;
      // '전체' 일때
      if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
        var momsHqBrandCd = "";
        for(var i=0; i < momsHqBrandCdComboList.length; i++){
          if(momsHqBrandCdComboList[i].value !== null) {
            momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
          }
        }
        params.userBrands = momsHqBrandCd;
      }

      $scope._inquirySub("/iostock/cmm/iostockCmm/selectProdMomsList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };

    $scope.prodSelected = function () {
      var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
      var arrProdCd = [];
      var strProdCd = "";
      var strProdNm = "";
      var cnt        = 0;

      for (var i = 0; i < flex.length; i++) {
        if (flex[i].gChk) {
          if (cnt == 0) {
            strProdCd = flex[i].prodCd;
            strProdNm = flex[i].prodNm;
          }
          arrProdCd.push(flex[i].prodCd);
          cnt++;
        }
      }

      $("#" + targetId + "Cd").val(arrProdCd.join());
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
        $("#" + targetId +"ProdNum").val("");
      }
      else if (cnt == 1) {
        $("#" + targetId + "Nm").val("[" + strProdCd + "] " + strProdNm);
        $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
        $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
        $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
        $("#" + targetId +"ProdNum").val(" 상품 : "+cnt+" 개");
      }
      else if (cnt > 1) {
        $("#" + targetId + "Nm").val(strProdNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["outstockReqDate.cntProd"]);
        $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
        $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
        $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
        $("#" + targetId +"ProdNum").val(" 상품 : "+cnt+" 개");
      }
      eval('$scope.wj' + targetId + 'LayerM.hide(true)');
    };

    $scope.popUpProdClass = function() {
      var popUp = $scope.prodClassPopUpLayer;
      popUp.show(true, function (s) {
        // 선택 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope = agrid.getScope('prodClassPopUpCtrl');
          var prodClassCd = scope.getSelectedClass();
          var params = {};
          params.prodClassCd = prodClassCd;
          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                  function(response){
                    $scope.popProdClassCd = prodClassCd;
                    $scope.popProdClassNm = response.data.data;
                  }
          );
        }
      });
    };

    // 상품분류정보 선택취소
    $scope.delProdClass = function(){
      $scope.popProdClassCd = "";
      $scope.popProdClassNm = "";
    };

  }]);
</script>
