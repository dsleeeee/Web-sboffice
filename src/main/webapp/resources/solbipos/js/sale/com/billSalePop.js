/**
 * get application
 */
var app = agrid.getApp();

app.controller('billSalePopCtrl', ['$scope', '$http', '$timeout',  function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSalePopCtrl', $scope, $http, $timeout, true));
    
	var param       	 = {};
	//결제수단 컬럼 생성
	$scope._postJSONQuery.withOutPopUp("/sale/anals/store/payFg/list.sb", param, function(response) {
		var length = response.data.data.list.length;
		 	var grid = wijmo.Control.getControl("#billSale3Grid");
		 	
		while(grid.columns.length > 0){
			grid.columns.removeAt(grid.columns.length-1);
		}
		 	
		//첫째줄 헤더 생성
		 	for(var i=0; i<length; i++){
	
	   		grid.columns.push(new wijmo.grid.Column({header: response.data.data.list[i].payNm, binding: 'pay'+response.data.data.list[i].payCd, align: "right" , isReadOnly: "true" }));		   		
		 	}
	}); 	
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSalePopCtrl", function (event, data) {
		
		$scope.billSalePopLayer.show(true);
		
		var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;	
	    //결제수단 조회
    	// 조회 수행 : 조회URL, 파라미터, 콜백함수
    	$scope._postJSONQuery.withOutPopUp("/sale/com/popup/billSalePop3/view.sb", params, function(response) {    

    		if(response.data.data.list[0].payCd === "01"){
    			$("#card").show(true);
    			//신용카드결제내역 조회
        		$scope._broadcast('billSale5CardCtrl', params); 
    		}else{
    			$("#card").hide(true);
    		}
    		if(response.data.data.list[0].payCd === "02"){
    			$("#cash").show(true);
    			//현금결제내역 조회
        		$scope._broadcast('billSale5CashCtrl', params); 
    		}else{
    			$("#cash").hide(true);
    		}
    		
    		//매장정보 조회
    		$scope._broadcast('billSale1Ctrl', params);
    		//매출종합내역 조회
    		$scope._broadcast('billSale2Ctrl', params);
    		//결제내역 조회
    		$scope._broadcast('billSale3Ctrl', params); 
    		//회원정보 조회
    		$scope._broadcast('billSale4Ctrl', params); 				
    		//상품내역 조회
    		$scope._broadcast('billSale6Ctrl', params); 
    	});

	});	
}]);

/** 매장정보 controller */
app.controller('billSale1Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale1Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale1Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale1Ctrl", function (event, data) {
		
		//매장정보,매출종합내역 조회
		$scope.searchBillSale1List(data);
		  
	});
	
	//매장정보,매출종합내역 조회
	$scope.searchBillSale1List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop1/view.sb", params);	    
	};	
}]);

/** 매출종합내역 controller */
app.controller('billSale2Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale2Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale2Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale2Ctrl", function (event, data) {
		
		//매출종합내역 조회
		$scope.searchBillSale2List(data);
		  
	});
	
	//매출종합내역 조회
	$scope.searchBillSale2List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop1/view.sb", params);	    
	};	
}]);

/** 결제내역 controller */
app.controller('billSale3Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale3Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale3Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale3Ctrl", function (event, data) {
		
		//결제내역 조회
		$scope.searchBillSale3List(data);
		  
	});
	
	//결제내역 조회
	$scope.searchBillSale3List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;

	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop3/view.sb", params);	    
	};	
}]);

/** 회원정보 controller */
app.controller('billSale4Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale4Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale4Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale4Ctrl", function (event, data) {
		
		//회원정보 조회
		$scope.searchBillSale4List(data);
		  
	});
	
	//회원정보 조회
	$scope.searchBillSale4List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop4/view.sb", params);	    
	};	
}]);

/** 신용카드 결제내역 controller */
app.controller('billSale5CardCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale5CardCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale5CardCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale5CardCtrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillSaleCardList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillSaleCardList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "01";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop5/view.sb", params);	    
	};	
}]);

/** 현금 결제내역 controller */
app.controller('billSale5CashCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale5CashCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale5CashCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale5CashCtrl", function (event, data) {
		
		//현금 결제내역
		$scope.searchBillSaleCashList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillSaleCashList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "02";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop5/view.sb", params);	    
	};	
}]);

/** 상품내역 controller */
app.controller('billSale6Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billSale6Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billSale6Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billSale6Ctrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillSale6List(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillSale6List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop6/view.sb", params);	    
	};	
}]);