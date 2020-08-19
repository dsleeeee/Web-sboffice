/**
 * get application
 */
var app = agrid.getApp();

app.controller('billRtnPopCtrl', ['$scope', '$http', '$timeout',  function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtnPopCtrl', $scope, $http, $timeout, true));
	
	var param       	 = {};
	//결제수단 컬럼 생성
	$scope._postJSONQuery.withOutPopUp("/sale/anals/store/payFg/list.sb", param, function(response) {
		var length = response.data.data.list.length;
		 	var grid = wijmo.Control.getControl("#billRtn3Grid");
		 	
		while(grid.columns.length > 0){
			grid.columns.removeAt(grid.columns.length-1);
		}
		 	
		//첫째줄 헤더 생성
		 	for(var i=0; i<length; i++){
	
	   		grid.columns.push(new wijmo.grid.Column({header: response.data.data.list[i].payNm, binding: 'pay'+response.data.data.list[i].payCd, align: "right" , isReadOnly: "true" }));		   		
		 	}
	}); 	
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtnPopCtrl", function (event, data) {
		
		$scope.billRtnPopLayer.show(true);
		
		var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    //결제수단 조회
    	// 조회 수행 : 조회URL, 파라미터, 콜백함수
    	$scope._postJSONQuery.withOutPopUp("/sale/com/popup/billSalePop3/view.sb", params, function(response) {    

    		if(response.data.data.list[0].payCd === "01"){
    			$("#rtnCard").show(true);
    			$("#realCard").show(true);
    			//신용카드결제내역 조회
        		$scope._broadcast('billRtn5CardCtrl', params); 
        		$scope._broadcast('billRealCardCtrl', params);        		
    		}else{
    			$("#rtnCcard").hide(true);
    			$("#realCard").hide(true);
    		}
    		if(response.data.data.list[0].payCd === "02"){
    			$("#rtnCash").show(true);
    			$("#realCash").show(true);
    			//현금결제내역 조회
        		$scope._broadcast('billRtn5CashCtrl', params); 
        		$scope._broadcast('billRealCashCtrl', params);
    		}else{
    			$("#rtnCash").hide(true);
    			$("#realCash").hide(true);
    		}
    		
    		//매장정보 조회
    		$scope._broadcast('billRtn1Ctrl', params);
    		//매출종합내역 조회
    		$scope._broadcast('billRtn2Ctrl', params);
    		//결제내역 조회
    		$scope._broadcast('billRtn3Ctrl', params); 				
    		//상품내역 조회
    		$scope._broadcast('billRtn6Ctrl', params); 
    		//원거래매출정보 조회
    		$scope._broadcast('billRtn7Ctrl', params); 
    	});

	});
    	
}]);

/** 매장정보 controller */
app.controller('billRtn1Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn1Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn1Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn1Ctrl", function (event, data) {
		
		//매장정보,매출종합내역 조회
		$scope.searchBillRtn1List(data);
		  
	});
	
	//매장정보,매출종합내역 조회
	$scope.searchBillRtn1List = function (data) {

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
app.controller('billRtn2Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn2Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn2Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn2Ctrl", function (event, data) {
		
		//매출종합내역 조회
		$scope.searchBillRtn2List(data);
		  
	});
	
	//매출종합내역 조회
	$scope.searchBillRtn2List = function (data) {

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
app.controller('billRtn3Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn3Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn3Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn3Ctrl", function (event, data) {
		
		//결제내역 조회
		$scope.searchBillRtn3List(data);
		  
	});
	
	//결제내역 조회
	$scope.searchBillRtn3List = function (data) {

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

/** 신용카드 결제내역 controller */
app.controller('billRtn5CardCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn5CardCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn5CardCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn5CardCtrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillRtnCardList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRtnCardList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "01";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billRtnPop5/view.sb", params);	    
	};	
}]);

/** 현금 결제내역 controller */
app.controller('billRtn5CashCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn5CashCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn5CashCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn5CashCtrl", function (event, data) {
		
		//현금 결제내역
		$scope.searchBillRtnCashList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRtnCashList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "02";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billRtnPop5/view.sb", params);	    
	};	
}]);

/** 상품내역 controller */
app.controller('billRtn6Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn6Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn6Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn6Ctrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillRtn6List(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRtn6List = function (data) {

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

/** 원거래매출정보 controller */
app.controller('billRtn7Ctrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRtn7Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
				
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRtn7Ctrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRtn7Ctrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillRtn7List(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRtn7List = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billSalePop7/view.sb", params);	    
	};	
}]);

/** 원신용카드 결제내역 controller */
app.controller('billRealCardCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRealCardCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRealCardCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRealCardCtrl", function (event, data) {
		
		//상품내역 조회
		$scope.searchBillRealCardPopList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRealCardPopList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "01";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billRealPop/view.sb", params);	    
	};	
}]);

/** 원현금 결제내역 controller */
app.controller('billRealCashCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('billRealCashCtrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("billRealCashCtrl");

	};
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("billRealCashCtrl", function (event, data) {
		
		//현금 결제내역
		$scope.searchBillRealCashPopList(data);
		  
	});
	
	//상품내역 조회
	$scope.searchBillRealCashPopList = function (data) {

		// 파라미터
	    var params       	 = {};
	    	params.storeCd   = data.storeCd;
	    	params.saleDate  = data.saleDate;
	    	params.billNo    = data.billNo;
	    	params.posNo     = data.posNo;
	    	params.payCd	 = "02";
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/com/popup/billRealPop/view.sb", params);	    
	};	
}]);