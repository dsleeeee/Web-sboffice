/****************************************************************
 *
 * 파일명 : kitchenPrintProductCtrl.js
 * 설  명 : 매장정보관리 > 매장환경설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('kitchenPrintProductCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kitchenPrintProductCtrl', $scope, $http, false));

  // 조회한 포스 목록
  $scope.posList;
  $scope.setPosList = function(list) {
    $scope.posList = list;
  };
  $scope.getPosList = function(){
    return $scope.posList;
  };

  // 선택한 프린터
  $scope.selectedPrter;
  $scope.setSelectedPrter = function(prter){
    $scope.selectedPrter = prter;
  };
  $scope.getSelectedPrter = function(){
    return $scope.selectedPrter;
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "prterNo") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 주방프린터 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "prterNo" ) {
          console.log(selectedRow);
          $scope.setSelectedPrter(selectedRow);
          $scope.getProductList();
        }
      }
    });
  };

  // 주방프린터 영역 보여줌
  $scope.$on("kitchenPrintProductCtrl", function(event, data) {
    event.preventDefault();
  });

  $scope.changeTabSrch = function (){
    $("#srchConfig").val('');
  };

  /*********************************************************
   * 환경변수 탭 변경
   * *******************************************************/
  $scope.changeEnvGroup = function(envGroupCd){
    // 탭 변경
    $("#envGroupTab li a").each(function(index, item){
      if($(this).attr("envstFg") === envGroupCd) {
        $(this).attr("class", "on");
      } else {
        $(this).removeAttr("class");
      }
    });

    if(envGroupCd === "00" || envGroupCd === "01" || envGroupCd === "02") { // 매장환경, 외식환경 유통환경 환경변수

      var cmmEnvScope = agrid.getScope('cmmEnvCtrl');
      cmmEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "03") { // 포스환경

      var posEnvScope = agrid.getScope('posEnvCtrl');
      posEnvScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "10") { // 포스기능키

      var posFuncKeyScope = agrid.getScope('funcKeyCtrl');
      posFuncKeyScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "98") { // 주방프린터

      var kitchenPrintScope = agrid.getScope('kitchenPrintCtrl');
      kitchenPrintScope.changeEnvGroup(envGroupCd);

    } else if(envGroupCd === "99") { // 주방프린터 상품연결

      $("#cmmEnvArea").hide();
      $("#posEnvArea").hide();
      $("#posFuncKeyArea").hide();
      $("#kitchenPrintArea").hide();
      $("#kitchenPrintProductArea").show();

      // $scope.setDataMap();
      $scope.getKitchenPrintList();
    }
  };

  // 상품명 조회
  $scope.searchProdNm = function(){
    $scope.getKitchenPrintList();
    if($("#srchProdNm").val() != ''){
      $scope.getProductList();
    }
  };

  /*********************************************************
   * 주방프린터 목록 조회
   * *******************************************************/
  $scope.getKitchenPrintList = function(){

    var params        = {};
    var storeScope    = agrid.getScope('storeManageCtrl');
    params.hqOfficeCd = storeScope.getSelectedStore().hqOfficeCd;
    params.storeCd    = storeScope.getSelectedStore().storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params,
      function() {
        // $scope.getProductList();
      }, false);
  };

  /*********************************************************
   * 주방프린터 상품 조회
   * *******************************************************/
  $scope.getProductList = function(){

    var params      = {};
    params.storeCd  = $scope.getSelectedPrter().storeCd;
    params.prterNo  = $scope.getSelectedPrter().prterNo;
    params.prodNm   = $("#srchProdNm").val();

    // console.log(params);

    $scope.$broadcast('loadingPopupActive');
    $scope._postJSONQuery.withPopUp( '/store/manage/storeManage/storeManage/getKitchenPrintProductInfo.sb', params, function(response){
      if (!$.isEmptyObject(response.data)) {

        $scope.$broadcast('loadingPopupInactive');

        var printProductList = response.data.data.list.printProductList;
        var noPrintProductList = response.data.data.list.noPrintProductList;

        $scope.regProductTreeCtrl.itemsSource   = printProductList;
        $scope.noRegProductTreeCtrl.itemsSource = noPrintProductList;
      }
    });
  };

  /*********************************************************
   * 주방프린터 연결상품 삭제
   * *******************************************************/
  $scope.delete = function(){

    var params = $scope.regProductTreeCtrl.checkedItems;

    if(params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    for(var i in params) {
      params[i].status = 'D';
    }


    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/store/manage/storeManage/storeManage/saveKitchenPrintProduct.sb", params,
        function(){
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.getKitchenPrintList();
          //재로딩
          $scope.getProductList();
        }
    );
  };

  /*********************************************************
   * 주방프린터 연결상품 추가
   * *******************************************************/
  $scope.add = function(){

    var params = $scope.noRegProductTreeCtrl.checkedItems;

    if(params.length <= 0) {
      $scope._popMsg(messages["cmm.not.modify"]);
      return false;
    }

    for(var i in params) {
      params[i].status = 'I';
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/store/manage/storeManage/storeManage/saveKitchenPrintProduct.sb", params,
      function(){
        $scope._popMsg(messages["cmm.saveSucc"]);

        $scope.getKitchenPrintList();
        //재로딩
        $scope.getProductList();
      }
    );
  };
}]);

