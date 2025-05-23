/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleAnalsMonthlyMomsStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleAnalsMonthlyMomsStoreCtrl', $scope, $http, true));
  $scope.orgnFg = gvOrgnFg;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	  
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("saleAnalsMonthlyMomsStoreCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 그리드 링크
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if( col.binding === "storeCd") {
          wijmo.addClass(e.cell, 'wijLink');
        }
      }
    });
    
    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd") {
          var params       = {};
          params.storeCd	= selectedRow.storeCd;
          params.storeNm	= selectedRow.storeNm;
          params.saleDate	= selectedRow.saleDate;
          params.hqOfficeCd = gvHqOfficeCd;
          $scope._broadcast('saleAnalsMonthlyMomsStoreDtlCtrl', params);
            event.preventDefault();
          }
        }
    });
    
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleAnalsMonthlyMomsStoreCtrl", function (event, data) {

	$scope.storeCd  	= data.storeCd;
	$scope.saleDate  	= data.saleDate;
	$scope.hqOfficeCd  	= data.hqOfficeCd;

    $scope.monthlyMomsStoreLayer.show(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
    
    $scope.saleAnalsMonthlyMomsStoreList();
    
  });

  // 조회
  $scope.saleAnalsMonthlyMomsStoreList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.saleDate = $scope.saleDate;
    params.hqOfficeCd = $scope.hqOfficeCd;
    $("#spanTitle").text($scope.saleDate + " 매출현황");

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/anals/monthlyMoms/monthlyMoms/getSaleAnalsMonthlyMomsStoreList.sb", params);
  };
  
}]);
