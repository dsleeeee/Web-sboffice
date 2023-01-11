<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<input type="hidden" id="<c:out value="${param.targetId}Cd"/>" />
<input type="text" id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5" style="cursor:pointer; width:100%;" <c:if test="${empty param.modiFg}"> ng-click="<c:out value="${param.targetId}"/>Show()" </c:if> readonly/>
<%--
<c:if test="${empty param.modiFg}">
<button type="button" class="btn_skyblue fl mr5" id="<c:out value="${param.targetId}SelectCancelBtn"/>">
  <s:message code="outstockReqDate.selectCancel"/></button>
</c:if>
--%>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerS" control="wj<c:out value="${param.targetId}"/>LayerS" show-trigger="Click" hide-trigger="Click" style="display:none;width:800px;">
  <div class="wj-dialog wj-dialog-columns" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="popup.product.select"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body">

      <%-- 조회 조건 --%>
      <table class="tblType01 mt20">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="popup.product.prodCd" /></th>
          <td><input type="text" id="${param.targetId}ProdCd" ng-model="prodCd"/></td>
          <%-- 상품명 --%>
          <th><s:message code="popup.product.prodNm" /></th>
          <td><input type="text" id="${param.targetId}ProdNm" ng-model="prodNm"/></td>
        </tr>
        <tr id="trSProdHqBrand">
          <%-- 상품브랜드 --%>
          <th><s:message code="popup.product.prodHqBrand" /></th>
          <td>
            <div class="sb-select">
              <wj-combo-box
                      id="srchPopSProdeHqBrandCdCombo"
                      ng-model="popStoreHqBrandCd"
                      items-source="_getComboData('popSProdeHqBrandCdCombo')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      control="srchPopSProdeHqBrandCdCombo">
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
        <button class="btn_skyblue" ng-click="searchProd()" ><s:message code="cmm.search" /></button>
      </div>

      <div class="oh mt40">

        <%-- 상품분류 영역 --%>
        <div class="w40 fl" style="overflow-x: auto;">
          <div >
            <div class="oh mb10">
              <span class="fl bk lh20 s14">상품 분류 선택</span>
            </div>
            <div class="wj-TblWrapBr ml10 mr10 mt10" style="height:420px;">
              <%-- 상품분류 트리 --%>
              <wj-tree-view control="prodClassTree"
                            items-source="items"
                            initialized="initTreeView(s)"
                            display-member-path="'prodClassNm'"
                            child-items-path="'children'"
                            item-clicked="navTo(prodClassTree)">
              </wj-tree-view>
            </div>

          </div>
        </div>

        <%-- 상품영역 --%>
        <div class="w60 fr">
          <div style="overflow-y: hidden;">
            <div class="oh mb10">
              <span class="fl bk lh20 s14">상품 선택</span>
              <%--<span class="fr"><a href="#" class="btn_grayS2">추가</a> <a href="#" class="btn_grayS2">삭제</a></span>--%>
            </div>
            <div class="wj-TblWrapBr ml10 mr10 mt10" style="height:420px; overflow-y: auto;">
              <%-- 페이지 스케일  --%>
              <wj-combo-box
                      class="w100px fl"
                      id="listScaleBox"
                      ng-model="listScale"
                      items-source="_getComboData('listScaleBox')"
                      display-member-path="name"
                      selected-value-path="value"
                      is-editable="false"
                      initialized="_initComboBox(s)"
                      ng-hide="true">
              </wj-combo-box>
              <%--// 페이지 스케일  --%>


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
                  <wj-flex-grid-column header="<s:message code="popup.product.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="popup.product.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

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
      </div>

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

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      // 브랜드 사용여부
      var params = {};
      params.envstCd = "1114";
      $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/getHqEnvSt.sb', params, function (response) {

        brandUseFg = response.data.data ;

        if(brandUseFg === "1"){

          // 상품브랜드
          params = {};
          $scope._postJSONQuery.withOutPopUp('/iostock/cmm/iostockCmm/selectBrandMomsList.sb', params, function (response) {
              if (response.data.data.list.length > 0) {
                  var list = response.data.data.list;
                  $scope._setComboData("popSProdeHqBrandCdCombo", list);

                  // 상품브랜드 콤보박스 항목 저장시 쓰려고
                  userHqBrandCdComboList = list;

                  // 상품브랜드 show
                  $("#trSProdHqBrand").css("display", "");
              }
          });

        }else{
          // 상품브랜드 hidden
          $("#trSProdHqBrand").css("display", "none");
        }

      });

      // ReadOnly 효과설정
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === 'prodCd') {
            var item = s.rows[e.row].dataItem;
            wijmo.addClass(e.cell, 'wijLink');
          }
        }
      });

      // 선택분류 그리드 선택 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function(e) {
        var ht = s.hitTest(e);
        if( ht.cellType === wijmo.grid.CellType.Cell) {
          var col = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if ( col.binding === 'prodCd' ) {
            // $scope._broadcast('sideMenuSelectProdCtrl', selectedRow.sdselClassCd);
            $("#" + $scope.targetId + "Cd").val(selectedRow.prodCd);
            $("#" + $scope.targetId + "Nm").val("[" + selectedRow.prodCd + "] " + selectedRow.prodNm);
            eval('$scope.wj' + $scope.targetId + 'LayerS.hide(true)');
          }
        }
      });
    };

    $scope.searchFg = "N";
    // 다른 컨트롤러의 broadcast 받기
    $scope.$on($scope.targetId + 'Ctrl', function (event, paramObj) {
      // 매장선택 팝업 오픈
      eval('$scope.wj' + $scope.targetId + 'LayerS.show(true)');

      if ($scope.searchFg == "N") {
        $scope.searchProdClass();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 상품 분류 조회
    $scope.searchProdClass = function(){
      var params = {};

      $scope._postJSONQuery.withOutPopUp('/popup/getProdClassTree.sb', params, function (response) {
        // console.log('tree response', response);
        if (response.data.status === 'OK') {
          if (response.data.data.length > 0) {
            // 트리형 데이터로 변환
            var list = response.data.data;
            var rootNodes = [];
            var traverse = function (nodes, item, index) {
              if (nodes instanceof Array) {
                return nodes.some(function (node) {
                  if (node.prodClassCd === item.pProdClassCd) {
                    node.children = node.children || [];
                    return node.children.push(list.splice(index, 1)[0]);
                  }

                  return traverse(node.children, item, index);
                });
              }
            };
            while (list.length > 0) {
              list.some(function (item, index) {
                if (item.pProdClassCd === '00000') {
                  return rootNodes.push(list.splice(index, 1)[0]);
                }
                return traverse(rootNodes, item, index);
              });
            }
            $scope.items = JSON.parse(JSON.stringify(rootNodes, null, ''));
          }
        }
      });
    };

    // 선택된 상품분류
    $scope.selectedClass = "";
    $scope.setSelectedClass = function(classCd){
      $scope.selectedClass = classCd;
    };
    $scope.getSelectedClass = function(){
      return $scope.selectedClass;
    };

    // 상품분류 선택
    $scope.navTo = function(treeView) {
      // console.log('navTo ',treeView.selectedItem.prodClassCd);
      $scope.setSelectedClass(treeView.selectedItem.prodClassCd);
      $scope.searchProdList();
    };

    $scope.searchProd = function(){

      if( (isEmptyObject($("#"+$scope.targetId+"ProdCd").val()) && isEmptyObject($("#"+$scope.targetId+"ProdNm").val() ) && isEmptyObject($scope.srchPopSProdeHqBrandCdCombo.selectedValue))
       || $("#"+$scope.targetId+"ProdNm").val() === '선택'){
        $scope._popMsg("검색조건을 입력해주세요");
        return false;
      }
      $scope.searchProdList();
    };

    // 상품 조회
    $scope.searchProdList = function(){
      var params = {};
      params.prodClassCd = $scope.getSelectedClass();
      params.prodCd = $("#"+$scope.targetId+"ProdCd").val();
      params.prodNm = $("#"+$scope.targetId+"ProdNm").val();

      if(brandUseFg === "1" && orgnFg === "HQ"){
        // 선택한 상품브랜드가 있을 때
        params.prodHqBrandCd = $scope.srchPopSProdeHqBrandCdCombo.selectedValue;

        // 선택한 상품브랜드가 없을 때('전체' 일때)
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }
      }

      console.log(params);

      // console.log('params', params);
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/popup/getProductList.sb", params);
    };

  }]);

</script>
