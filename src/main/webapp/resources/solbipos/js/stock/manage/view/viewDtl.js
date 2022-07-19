/**
 * get application
 */
var app = agrid.getApp();

app.controller('viewDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	  // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('viewDtlCtrl', $scope, $http, true));
	  
	  // 접속사용자의 권한(H : 본사, S: 매장)
	  //$scope.orgnFg = gvOrgnFg;

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("viewDtlCtrl");
		
		// 총매출열에 CSS 추가
		wijmo.addClass(s.columns[2], 'wijLink');
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');

	  };

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("viewDtlCtrl", function (event, data) {
	    $scope.totDate 		= data.totDate; // 날짜
	    $scope.seqNo		= data.seqNo; // 차수
	    $scope.hqGbn    	= data.hqGbn; // 상태
	    $scope.hqGbnNm    	= data.hqGbnNm; // 상태명
	    $scope.title    	= data.title; // 제목
	    $scope.reasonNm    	= data.reasonNm; // 사유
	    $scope.orgnFg     	= data.orgnFg;
	    $scope.hqOfficeCd	= data.hqOfficeCd;
	    $scope.storeCd		= data.storeCd;

	    $scope.viewDtlLayer.show(true);

	    $scope.searchviewDtlList();
	    
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });
	  
	  $scope.searchviewDtlList = function(){
		  var params			={};
		  	  params.totDate	= $scope.totDate.replace(/-/gi, "");
		  	  params.hqGbn		= $scope.hqGbn;
		  	  params.seqNo		= $scope.seqNo;
		  	  params.hqOfficeCd	= $scope.hqOfficeCd;
		  	  params.storeCd	= $scope.storeCd;
		  	  
		  	var columns = $scope.flex.columns;
			for(var i=0; i<columns.length; i++){
				if(columns[i].binding === 'acinsQty'){
					params.hqGbn === '1' ? columns[i].visible = true : columns[i].visible = false;
				}
				if(columns[i].binding === 'adjQty'){
					params.hqGbn === '1' || params.hqGbn === '2' ? columns[i].visible = true : columns[i].visible = false;
				}
				if(columns[i].binding === 'disuseQty'){
					params.hqGbn === '3' ? columns[i].visible = true : columns[i].visible = false;
				}
			}
		  
		  $scope._inquirySub("/stock/com/popup/cmmQtyDtl/getCmmViewDtlList.sb", params);
	  }
}]);