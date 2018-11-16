/****************************************************************
 *
 * 파일명 : storeKitchenPrintProductCtrl.js
 * 설  명 : 매장정보관리 > 매장에서 해당 매장의 주방프린터에 상품을 연결함 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.15     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  매장 목록 그리드
 **********************************************************************/
app.controller('storeKitchenPrintProductCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeKitchenPrintProductCtrl', $scope, $http, false));


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
  $scope.$on("storeKitchenPrintProductCtrl", function(event, data) {
    $scope.getKitchenPrintList();
    event.preventDefault();
  });


  /*********************************************************
   * 주방프린터 목록 조회
   * *******************************************************/
  $scope.getKitchenPrintList = function(){

    var params        = {};
    params.hqOfficeCd = gvHqOfficeCd;
    params.storeCd    = gvStoreCd;

    console.log(params)

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub("/store/manage/storeManage/storeManage/getKitchenPrintInfo.sb", params, function() {}, false);
  };

  /*********************************************************
   * 주방프린터 상품 조회
   * *******************************************************/
  $scope.getProductList = function(){

    var params      = {};
    params.storeCd  = gvStoreCd
    params.prterNo  = $scope.getSelectedPrter().prterNo;

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
          //재로딩
          $scope.getProductList();
        }
    );
  };

}]);
