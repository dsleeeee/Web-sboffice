/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleAnalsMonthlyPayFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleAnalsMonthlyPayFgCtrl', $scope, $http, true));
  $scope.orgnFg = gvOrgnFg;
  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
	  
	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("saleAnalsMonthlyPayFgCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 그리드 링크
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        
        if ((item.payAmt < 0) && (col.binding !== 'saleDate')) {  // 결제취소 글자색 변경
        	wijmo.addClass(e.cell, 'red');
        } else {
        	wijmo.addClass(e.cell, 'bk');
        }
      }
  	  
    });
    
    // 그리드 클릭 이벤트-------------------------------------------------------------------------------------------------
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
      }
    });
    
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleAnalsMonthlyPayFgCtrl", function (event, data) {

	$scope.storeCd  	= data.storeCd;
	$scope.saleDate  	= data.saleDate;
	
    $scope.monthlyPayFgLayer.show(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
    
    $scope.saleAnalsMonthlyPayFgList();
    
  });

  // 테이블별 리스트 조회
  $scope.saleAnalsMonthlyPayFgList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.saleDate = $scope.saleDate;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수/sale/anals/monthly/SaleAnalsMonthly/list.sb
    $scope._inquiryMain("/sale/anals/monthly/monthlyPopup/payFg/view.sb", params);
  };
  
}]);
