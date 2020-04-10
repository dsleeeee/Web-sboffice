/**
 * get application
 */

var app = agrid.getApp();

app.controller('storePeriodQtyDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	  // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('storePeriodQtyDtlCtrl', $scope, $http, true));
	  
	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("storePeriodQtyDtlCtrl");
		
		// 총매출열에 CSS 추가
		wijmo.addClass(s.columns[2], 'wijLink');
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');
	  };

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("storePeriodQtyDtlCtrl", function (event, data) {

		$scope.slipNo			= data.slipNo;
	    $scope.startDate 		= data.startDate;
	    $scope.endDate			= data.endDate;
	    $scope.prodClassCd    	= data.prodClassCd;
	    $scope.storeCd  		= data.storeCd;
	    $scope.prodCd    		= data.prodCd; // 상품코드
	    $scope.prodNm 			= data.prodNm; // 상품명
	    $scope.orgnFg     		= data.orgnFg;
	    $scope.colQty			= data.colQty; // 수량 구분
	    $scope.poUnitQty		= data.poUnitQty; // 입수

	    $scope.storePeriodQtyDtlLayer.show(true);
	    $scope.searchStorePeriodQtyDtlList();
	    
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });
	  
	  $scope.searchStorePeriodQtyDtlList = function() {
		  var params      	= {};
		    params.prodCd			= $scope.prodCd;
		    params.prodNm 			= $scope.prodNm;
		    params.poUnitQty 		= $scope.poUnitQty;
		    params.qtyFg			= $scope.qtyFg;
		    
		    // 조회 수행 : 조회URL, 파라미터, 콜백함수
		    $scope._postJSONQuery.withOutPopUp("/stock/status/storeperiod/prod/storeperiodQtyDtlList.sb", params, function(response) {
		    	var list = response.data.data.list;
		    	var length = response.data.data.list.length;
		    	var grid = wijmo.Control.getControl("#storePeriodQtyDtlGrid");
		    	
		    	if(length != "" || length != null){
		    		while(grid.columns.length > 0){
		    			grid.columns.removeAt(grid.columns.length-1);
		    		}
		    		
		    		//첫째줄 헤더 생성
		    		if($scope.colQty == "03") { // 매장입고
						// 입고일, 본사코드, 본사명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.instockDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.hqOfficeCd"], binding: "hqOfficeCd", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.hqOfficeNm"], binding: "hqOfficeNm", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "12") { // 매장반품
						// 반품일, 본사코드, 본사명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.carryOutDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.hqOfficeCd"], binding: "hqOfficeCd", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.hqOfficeNm"], binding: "hqOfficeNm", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "06") { // 사입입고
						// 입고일, 거래처코드, 거래처명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.instockDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.vendrCd"], binding: "vendrCd", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.vendrNm"], binding: "vendrNm", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "18") { // 사입반품
						// 반품일, 거래처코드, 거래처명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.carryOutDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.vendrCd"], binding: "vendrCd", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.vendrNm"], binding: "vendrNm", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "11") { // 매장판매
						// 영업일자, 배달포장구분
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.saleDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.dlvrPackFg"], binding: "dlvrPackFg", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "04") { // 매장이입
						// 이입일, 매장코드, 매장명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.moveInDate"], binding: "saleDate",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.storeCd"], binding: "storeCd",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.storeNm"], binding: "storeNm",	width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "14") { // 매장이출
						// 이출일, 매장코드, 매장명
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.moveOutDate"], binding: "saleDate",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.storeCd"], binding: "storeCd",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.storeNm"], binding: "storeNm",	width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "17") { // 재고폐기
						// 폐기일, 차수, 제목, 구분
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.disuseDate"], binding: "saleDate",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.seqNo"], binding: "seqNo",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.title"], binding: "title",	width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.fg"], binding: "fg",	width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "21") { // 재고조정
						// 조정일, 차수, 제목, 구분
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.adjDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.seqNo"], binding: "seqNo", width:"*" , lign: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.title"], binding: "title", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.fg"], binding: "fg", width:"*" , align: "center" , isReadOnly: "true"}));
					} else if($scope.colQty == "22") { // 세트생성
						// 생성일, 구분
						grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.createDate"], binding: "saleDate", width:"*" , align: "center" , isReadOnly: "true"}));
				   		grid.columns.push(new wijmo.grid.Column({header: messages["storePeriod.fg"], binding: "fg", width:"*" , align: "center" , isReadOnly: "true"}));
					}
		    		
		    		// 데이터 뿌리기
		    		var data = new wijmo.collections.CollectionView(list);
		    		data.trackChanges = true;
		            $scope.data = data;
		    	} else{
					$scope._popMsg(response.data.message);
				}
		    });
	  }
}]);