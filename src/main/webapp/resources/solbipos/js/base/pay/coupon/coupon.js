/****************************************************************
 *
 * 파일명 : coupon.js
 * 설  명 : 쿠폰등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();


/**
 *  쿠폰분류등록 그리드 생성
 */
app.controller('couponClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('couponClassCtrl', $scope, $http, true));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    console.log('couponClassCtrl init')
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.coupnDcFgDataMap = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
    $scope.coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "payClassCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });

    // 쿠폰분류 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if ( col.binding === "payClassCd" && selectedRow.status != "I") {
          if(selectedRow.status == "I") {
            e.cancel = false;
          } else {
            $("#couponSubTitle").text(" [" + selectedRow.payClassNm+ "]");
            $scope._broadcast('couponCtrl', selectedRow);
          }
        }
      }
    });
    // 조회 이벤트 발생시킴.
    setTimeout(function() {
      $scope._broadcast('couponClassCtrl', true);
    }, 100)
  };

  $scope.$on("couponClassCtrl", function(event, data) {
    $scope.searchCouponClass();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 쿠폰 분류 그리드 조회
  $scope.searchCouponClass = function(){
    // 파라미터
    var params = {};
    params.coupnEnvstVal = coupnEnvstVal;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getCouponClassList.sb", params, function() {}, true);
  };

  // 쿠폰 분류 그리드 행 추가
  $scope.addRow = function() {
    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.serNoYn = "Y";
    params.useYn = "Y";

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 쿠폰분류 그리드 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      $scope.flex.collectionView.itemsRemoved[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveCouponClassList.sb", params);
  }
}]);


/**
 * 쿠폰 그리드 생성
 */
app.controller('couponCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('couponCtrl', $scope, $http, false));
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    console.log('couponCtrl init')
    // 그리드 DataMap 설정
    $scope.useYnDataMap = new wijmo.grid.DataMap(useYn, 'value', 'name');
    $scope.coupnDcFgDataMap = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
    $scope.coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');

    // ReadOnly 효과설정
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "coupnCd" || col.binding === "prodCnt" || col.binding === "storeCnt") {
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        else if (col.binding === "prodCnt" || col.binding === "storeCnt") {
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });
    // 쿠폰 그리드 에디팅 방지
    s.beginningEdit.addHandler(function (s, e) {
      var col = s.columns[e.col];
      var dataItem = s.rows[e.row].dataItem;

      if (col.binding === "coupnCd") {
        if (nvl(dataItem.status, "") == "" && dataItem.status != "I") {
          e.cancel = true;
        }
      }

      // 할인구분 => 금액 관련이면, 할인율은 입력못함
      if(e.col == 6) {
        if(dataItem.coupnDcFg == "3" || dataItem.coupnDcFg == "4" || dataItem.coupnDcFg == "6") {
          e.cancel = true;
        } else {
          e.cancel = false;
        }
      }
      // 할인구분 => % 관련이면, 할인금액 입력못함
      if(e.col == 7) {
        if(dataItem.coupnDcFg == "1" || dataItem.coupnDcFg == "2") {
          e.cancel = true;
        } else {
          e.cancel = false;
        }
      }
      // 상품수량과 적용매장수는 수정할 수 없음.
      if(e.col == 9 || e.col == 10) {
        e.cancel = true;
      }
    });

    // 쿠폰 그리드 선택 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function(e) {
      var ht = s.hitTest(e);
      if( ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;

        if ( col.binding === "prodCnt" && selectedRow.status != "I") {
          // 상품 등록 팝업
          var popup = $scope.couponPordLayer;
          popup.show(true, function (s) {});
        }
        else if ( col.binding === "storeCnt" && selectedRow.status != "I") {
          // 매장 등록 팝업
          var popup = $scope.couponStoreLayer;
          popup.show(true, function (s) {});
        }
      }
    });
  };

  $scope.$on("couponCtrl", function(event, data) {
    $scope.searchCoupon(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 쿠폰 그리드 조회
  $scope.searchCoupon = function(data){

    console.log('searchCoupon')
    var params = {};
    params.coupnEnvstVal = coupnEnvstVal;
    params.payClassCd = data.payClassCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquirySub(baseUrl + "class/getCouponList.sb", params, function(){}, false);
  };

  // 쿠폰 그리드 행 추가
  $scope.addRow = function() {
    var gridRepresent = agrid.getScope("couponClassCtrl");
    var selectedRow = gridRepresent.flex.selectedRows[0]._data;

    // 파라미터 설정
    var params = {};
    params.status = "I";
    params.gChk = true;
    params.coupnDcFg = "1";
    params.coupnApplyFg = "1";
    params.useYn = "Y";
    params.prodCnt = "0";
    params.storeCnt = "0";
    params.payClassCd = selectedRow.payClassCd;

    // 추가기능 수행 : 파라미터
    $scope._addRow(params);
  };

  // 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      $scope.flex.collectionView.itemsEdited[i].status = "U";
      $scope.flex.collectionView.itemsEdited[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
      $scope.flex.collectionView.itemsAdded[i].status = "I";
      $scope.flex.collectionView.itemsAdded[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsAdded[i]);
    }

    for (var i = 0; i < $scope.flex.collectionView.itemsRemoved.length; i++) {
      $scope.flex.collectionView.itemsRemoved[i].status = "D";
      $scope.flex.collectionView.itemsRemoved[i].coupnEnvstVal = coupnEnvstVal;
      params.push($scope.flex.collectionView.itemsRemoved[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save(baseUrl + "class/saveCouponList.sb", params);
  }
}]);

// 탭 클릭
$("#couponStoreTab").click(function(){
  location.href = "/base/pay/coupon/store/couponStoreView.sb";
});


