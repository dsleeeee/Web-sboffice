<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="<c:out value="${param.targetId}Cd"/>" />
<input type="text" id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5" style="cursor:pointer; width:200px;" <c:if test="${empty param.modiFg}"> ng-click="<c:out value="${param.targetId}"/>Show()" </c:if> readonly/>
<c:if test="${empty param.modiFg}">
<button type="button" class="btn_skyblue fl mr5" id="<c:out value="${param.targetId}SelectCancelBtn"/>">
  <s:message code="outstockReqDate.selectCancel"/></button>
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
            <th><s:message code="cmm.storeCd"/></th>
            <td><input type="text" id="srchStoreCd" ng-model="storeCd"/></td>
            <th><s:message code="cmm.storeNm"/></th>
            <td><input type="text" id="srchStoreNm" ng-model="storeNm"/></td>
          </tr>
          <tr id="trSStoreHqBrand">
            <%-- 매장브랜드 --%>
            <th><s:message code="outstockReqDate.storeHqBrand" /></th>
            <td>
              <div class="sb-select">
                <wj-combo-box
                        id="srchPopSStoreHqBrandCdCombo"
                        ng-model="popStoreHqBrandCd"
                        items-source="_getComboData('popSStoreHqBrandCdCombo')"
                        display-member-path="name"
                        selected-value-path="value"
                        is-editable="false"
                        control="srchPopSStoreHqBrandCdCombo">
                </wj-combo-box>
              </div>
            </td>
            <th></th>
            <td></td>
          </tr>
          </tbody>
        </table>
        <%-- 조회 --%>
        <div class="mt10 tr">
          <button class="btn_skyblue" id="btnSearch" ng-click="getStoreList();" ><s:message code="cmm.search" /></button>
        </div>


        <%--위즈모 테이블--%>
        <div class="theGrid mt10" style="height: 400px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
          <%--control="storeGridS"--%>
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.storeCd"/>" binding="storeCd" width="70" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="cmm.storeNm"/>" binding="storeNm" width="*" align="left"></wj-flex-grid-column>

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
  // 브랜드 사용여부
  var brandUseFg = "";
  // 사용자 브랜드
  var userHqBrandCdComboList = "";

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

      // 브랜드 사용여부
      var params = {};
      params.envstCd = "1114";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/getHqEnvSt.sb', params, function (response) {

        brandUseFg = response.data.data ;

        if(brandUseFg === "1"){

          // 매장브랜드
          params = {};
          $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBrandMomsList.sb', params, function (response) {
              if (response.data.data.list.length > 0) {
                  var list = response.data.data.list;
                  $scope._setComboData("popSStoreHqBrandCdCombo", list);

                  // 매장브랜드 콤보박스 항목 저장시 쓰려고
                  userHqBrandCdComboList = list;

                  // 매장브랜드 show
                  $("#trSStoreHqBrand").css("display", "");
              }
          });

        }else{
          // 매장브랜드 hidden
          $("#trSStoreHqBrand").css("display", "none");
        }

      });

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel == s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "storeCd") {
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
          if (col.binding === "storeCd") {
            $("#" + $scope.targetId + "Cd").val(selectedRow.storeCd);
            $("#" + $scope.targetId + "Nm").val("[" + selectedRow.storeCd + "] " + selectedRow.storeNm);
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
        $scope.searchStore();
      // }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 조회버튼 클릭 시 매장목록 조회
    $scope.getStoreList= function () {
      $scope._pageView( $scope.targetId + "Ctrl", 1);
    };

    $scope.searchStore = function () {
      // 파라미터
      var params = {};
      params.storeCd = $scope.storeCd;
      params.storeNm = $scope.storeNm;

      if(brandUseFg === "1" && orgnFg === "HQ"){

          // 선택한 매장브랜드가 있을 때
          params.storeHqBrandCd = $scope.srchPopSStoreHqBrandCdCombo.selectedValue;

          // 선택한 매장브랜드가 없을 때('전체' 일때)
          if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
              var userHqBrandCd = "";
              for(var i=0; i < userHqBrandCdComboList.length; i++){
                  if(userHqBrandCdComboList[i].value !== null) {
                      userHqBrandCd += userHqBrandCdComboList[i].value + ","
                  }
              }
              params.userBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
          }
      }

      $scope._inquirySub("/popup/getStoreList.sb", params, function () {
        $scope.searchFg = "Y";
      });
    };

    // 닫을때 초기화 로직 추가
    $scope.closePopup = function(){
        $scope.storeCd = "";
        $scope.storeNm = "";
    };

  }]);

  $(document).ready(function () {
    <%-- 선택취소 버튼 클릭 --%>
    $("#${param.targetId}SelectCancelBtn").click(function () {
      $("#${param.targetId}Cd").val("");
      $("#${param.targetId}Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));
    });
  });

</script>
