/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('saleComPayFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleComPayFgCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
    
    // 그리드 링크
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        
        if (col.binding === "billNo") { // 영수증 링크
          	if(item.payAmt < 0) {
          		wijmo.addClass(e.cell, 'wijLink-red');
          	} else {
          		wijmo.addClass(e.cell, 'wijLink');
          	}
        }
        
        if ((item.payAmt < 0) && (col.binding !== 'saleDate')) {  // 결제취소 글자색 변경
        	wijmo.addClass(e.cell, 'red');
        } else {
        	wijmo.addClass(e.cell, 'bk');
        }
      }
  	  
    });
    
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("saleComPayFgCtrl", function (event, data) {

	$scope.dialogHd     = data.dialogHd;
	$scope.storeCd  	= data.storeCd;
	$scope.prodCd  	    = data.prodCd;
	$scope.startDate  	= data.startDate;
	$scope.endDate 	    = data.endDate; 
	$scope.payCd 	    = data.payCd; 

	console.log($scope.dialogHd);
	
    $scope.payFgLayer.show(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
    
    $scope.saleComPayFgList();
    
  });

  // 테이블별 리스트 조회
  $scope.saleComPayFgList = function () {
    // 파라미터
    var params       = {};
    params.storeCd   = $scope.storeCd;
    params.prodCd    = $scope.prodCd;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.payCd     = $scope.payCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/com/popup/payFg/view.sb", params);
  };
  
}]);
