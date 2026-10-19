/**
 * get application
 */
var app = agrid.getApp();

app.controller('goalPopCtrl', ['$scope', '$http', '$timeout',  function ($scope, $http, $timeout) {

	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('goalPopCtrl', $scope, $http, $timeout, true));
		  	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("goalPopCtrl", function (event, data) {

		$scope.goalPopLayer.show(true);
		
		//매장조회
		$scope.searchStoreList();
		  
	});
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("goalPopCtrlSrch", function (event, data) {
		  
		  //매장조회
		  $scope.searchStoreList();
		  
		  // 기능수행 종료 : 반드시 추가
		  event.preventDefault();
		  
	});
	
	//매장조회
	$scope.searchStoreList = function () {
		// 파라미터
	    var params       	= {};
	    	params.orgnFg   = gvOrgnFg;
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquiryMain("/sale/anals/goal/store/list.sb", params);
	    
	    //메인그리드 조회후 상세그리드 조회.
		$scope.loadedRows = function(sender, args){

			var rows = sender.rows;

			var params		 = {};
			if(rows.length != 0) {
				params.storeCd   = rows[0].dataItem.storeCd;
				params.storeNm   = rows[0].dataItem.storeNm;
				params.saleDate  = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
		    }
			
			$scope._broadcast("goalQryCtrl", params);
       	 	$scope._broadcast("goalDtl1Ctrl", params);
       	 	$scope._broadcast("goalDtl2Ctrl", params);
		}
	};
    
}]);

/** 매출목표등록 controller */
app.controller('goalRgsrCtrl', ['$scope', '$http', '$timeout', '$filter', function ($scope, $http, $timeout, $filter) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('goalRgsrCtrl', $scope, $http, $timeout, true));
	$scope.change = function() {

	    $scope.data = $filter('number')($("#saleGoalAmt").val());

	}
	  
	$scope.change();
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleGoalRgsr", function (event, data) {

		  //매출목표등록
		  $scope.saveSaleGoalRgsr();
		  
		  // 기능수행 종료 : 반드시 추가
		  event.preventDefault();
		  
	});
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleGoalRgsrDetl", function (event, data) {

		// 동시에 호출 시 매출목표금액합계가 상세 저장 전 금액으로 계산될 수 있으므로
		// 상세그리드1 저장 → (성공 시) 상세그리드2 저장 → (성공 시) 매출목표금액합계 저장 순서로 변경
    	//매출목표상세그리드1 저장
		//$scope._broadcast("saleGoalRgsrDetl1");

		//매출목표상세그리드2 저장
		//$scope._broadcast("saleGoalRgsrDetl2");

		//매출목표금액합계 저장
		//$scope.saleGoalAmtTotal();

		var dtl1Scope = agrid.getScope('goalDtl1Ctrl');
		var dtl2Scope = agrid.getScope('goalDtl2Ctrl');

		// 저장 실패 시 처리 (로딩바 hide 및 메시지)
		var fnFail = function (msg) {
			$scope.$broadcast('loadingPopupInactive');
			if (msg) {
				$scope._popMsg(msg);
			}
		};

		// 로딩바 show
		$scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

		//매출목표상세그리드1 저장
		dtl1Scope.saleGoalRgsrDetl1(function () {
			//매출목표상세그리드2 저장
			dtl2Scope.saleGoalRgsrDetl2(function () {
				//매출목표금액합계 저장
				$scope.saleGoalAmtTotal(function () {
					// 로딩바 hide
					$scope.$broadcast('loadingPopupInactive');
				}, fnFail);
			}, fnFail);
		}, fnFail);

		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});
	
	//매출목표등록
	$scope.saveSaleGoalRgsr = function () {

		// 요일별 매출 가중치 체크 (0~999 정수, 하나 이상 0보다 커야 함)
		var weightSum = 0;
		for (var i = 1; i <= 7; i++) {
			var weight = $.trim($("#saleGoalWeight" + i).val());
			if (!/^\d{1,3}$/.test(weight)) {
				$scope._popMsg(messages["goalPop.weightInteger"]); // 요일별 매출 가중치는 0~999 사이의 정수만 입력 가능합니다.
				return false;
			}
			weightSum += parseInt(weight, 10);
		}
		if (weightSum <= 0) {
			$scope._popMsg(messages["goalPop.weightZero"]); // 요일별 매출 가중치는 하나 이상 0보다 커야 합니다.
			return false;
		}

		// 매출목표금액 필수 체크
		if (!/^\d+$/.test($.trim($("#saleGoalAmt").val()))) {
			$scope._popMsg(messages["goalPop.saleGoalAmtBlank"]); // 매출목표금액을 입력해주세요.
			return false;
		}

		// 로딩바 show
		$scope.$broadcast('loadingPopupActive', messages["cmm.saving"]);

		// 파라미터
		var params = {};
			params.storeCd 		= $("#storeCd").val();
			params.storeNm 		= $("#storeNm").val();
			params.saleGoalYm 	= wijmo.Globalize.format($scope.startDate, 'yyyyMM');
			
		    params.saleGoalWeight1 = $.trim($("#saleGoalWeight1").val());
		    params.saleGoalWeight2 = $.trim($("#saleGoalWeight2").val());
		    params.saleGoalWeight3 = $.trim($("#saleGoalWeight3").val());
		    params.saleGoalWeight4 = $.trim($("#saleGoalWeight4").val());
		    params.saleGoalWeight5 = $.trim($("#saleGoalWeight5").val());
		    params.saleGoalWeight6 = $.trim($("#saleGoalWeight6").val());
		    params.saleGoalWeight7 = $.trim($("#saleGoalWeight7").val());
		    
		    params.saleGoalAmt = $.trim($("#saleGoalAmt").val());

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._postJSONSave.withOutPopUp("/sale/anals/goal/goal/save.sb", params, function(response) {

			$scope.$broadcast('loadingPopupInactive');
			
            var result = response.data.data;

            if(result < 1){
              $scope._popMsg(messages["cmm.registFail"]);

            }else{
              $scope._popMsg(messages["cmm.saveSucc"]);
            }
             
	         var params       = {};
        	 params.storeCd   = $("#storeCd").val();
            
	       	 $scope._broadcast("goalDtl1Ctrl", params);
	    	 $scope._broadcast("goalDtl2Ctrl", params);
		 });
	};  		
	
	//매출목표합계 저장
	// successCallback : 저장 응답 시 호출, failCallback : 서버 오류 시 호출
	$scope.saleGoalAmtTotal = function (successCallback, failCallback) {

		// 파라미터
		var params = {};
			params.storeCd 		= $("#storeCd").val();
			params.storeNm 		= $("#storeNm").val();
			params.saleGoalYm 	= wijmo.Globalize.format($scope.startDate, 'yyyyMM');

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._postJSONSave.withOutPopUp("/sale/anals/goal/goalAmtTot/save.sb", params, function(response) {

			if (typeof successCallback === 'function') {
				successCallback();
			}

            var result = response.data.data;

            if(result < 1){
              $scope._popMsg(messages["cmm.registFail"]);

            }else{
              $scope._popMsg(messages["cmm.saveSucc"]);
            }
            
    		//저장후 조회
            var storeCd		 = $("#storeCd").val();
            var storeNm		 = $("#storeNm").val();
            
            var params       = {};
            	params.storeCd = storeCd;
            	params.storeNm = storeNm;
            	params.saleDate  = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
            	
			$scope._postJSONQuery.withOutPopUp("/sale/anals/goal/goal/list.sb", params, function(response) {
				 
				 var length = response.data.data.list.length;

				 $("#storeCd").val(params.storeCd);
				 $("#storeNm").val(params.storeNm);
				 
				 $("#saleGoalWeight1").val(response.data.data.list[0].saleGoalWeight1);
				 $("#saleGoalWeight2").val(response.data.data.list[0].saleGoalWeight2);
				 $("#saleGoalWeight3").val(response.data.data.list[0].saleGoalWeight3);
				 $("#saleGoalWeight4").val(response.data.data.list[0].saleGoalWeight4);
				 $("#saleGoalWeight5").val(response.data.data.list[0].saleGoalWeight5);
				 $("#saleGoalWeight6").val(response.data.data.list[0].saleGoalWeight6);
				 $("#saleGoalWeight7").val(response.data.data.list[0].saleGoalWeight7);
				 
				 $("#saleGoalAmt").val(response.data.data.list[0].saleGoalAmt);

			 });
            
    		// 기능수행 종료 : 반드시 추가
    		event.preventDefault();
		 }, function() {
			if (typeof failCallback === 'function') {
				failCallback(messages["cmm.saveFail"]);
			}
		 }, function() {});
	};
}]);

/** 매출목표조회 controller */
app.controller('goalQryCtrl', ['$scope', '$http','$timeout', function ($scope, $http, $timeout) {
	 //Main은 어디로??
	 // 상위 객체 상속 : T/F 는 picker
	 angular.extend(this, new RootController('goalQryCtrl', $scope, $http, $timeout, true));
	 
	 // grid 초기화 : 생성되기전 초기화되면서 생성된다
	 $scope.initGrid = function (s, e) {

		 // picker 사용시 호출 : 미사용시 호출안함
		 $scope._makePickColumns("goalQryCtrl");

		 // 그리드 링크 효과
		 s.formatItem.addHandler(function (s, e) {
			 if (e.panel === s.cells) {
		     var col = s.columns[e.col];

			     if (col.binding === "storeNm") { 
			    	 var item = s.rows[e.row].dataItem;
		          	 wijmo.addClass(e.cell, 'wijLink');
		          	 wijmo.addClass(e.cell, 'wj-custom-readonly');
			     }
		     }
		 });	  
		    
		 // 그리드 클릭 이벤트
		 s.addEventListener(s.hostElement, 'mousedown', function (e) {
			 var ht = s.hitTest(e);

		     if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
		    	 var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
		  		 if (rng && rng.columnSpan > 1) {
		  			 e.preventDefault();
		  		 }
		     }
		      
		     if (ht.cellType === wijmo.grid.CellType.Cell) {
		    	 var col         = ht.panel.columns[ht.col];
		         var selectedRow = s.rows[ht.row].dataItem;
		         var storeCd		 = selectedRow.storeCd;
		         var storeNm		 = selectedRow.storeNm;
		         
		         var params       = {};
		        	 params.storeCd = storeCd;
		        	 params.storeNm = storeNm;
		        
		         if (col.binding === "storeNm") { 
		        	 
		        	 $scope.searchGoalList(params);
		        	 $scope._broadcast("goalDtl1Ctrl", params);
		        	 $scope._broadcast("goalDtl2Ctrl", params);
			     }
		     }
		 });
		 
		// 다른 컨트롤러의 broadcast 받기
		$scope.$on("goalQryCtrl", function (event, data) {
			if(data != undefined){
				$scope.storeCd = data.storeCd;
				$scope.saleDate  = data.saleDate;
			}
			
		    $scope.searchGoalList(data);
		    // 기능수행 종료 : 반드시 추가
		    event.preventDefault();
		});
		 
		 //매출목표조회
		 $scope.searchGoalList = function (params) {

			 // 파라미터
			 var param       = {};
			 	 param.storeCd	  = params.storeCd;
			 	 param.saleDate  = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
			 // 조회 수행 : 조회URL, 파라미터, 콜백함수
			 $scope._postJSONQuery.withOutPopUp("/sale/anals/goal/goal/list.sb", param, function(response) {
				 
				 var length = response.data.data.list.length;
				 $("#storeCd").val(params.storeCd);
				 $("#storeNm").val(params.storeNm);
				 
				 if(length == 0){
					 $("#saleGoalWeight1").val(1);
					 $("#saleGoalWeight2").val(1);
					 $("#saleGoalWeight3").val(1);
					 $("#saleGoalWeight4").val(1);
					 $("#saleGoalWeight5").val(1);
					 $("#saleGoalWeight6").val(1);
					 $("#saleGoalWeight7").val(1);
					 
					 $("#saleGoalAmt").val(0);
				 }else{
					 $("#saleGoalWeight1").val(response.data.data.list[0].saleGoalWeight1);
					 $("#saleGoalWeight2").val(response.data.data.list[0].saleGoalWeight2);
					 $("#saleGoalWeight3").val(response.data.data.list[0].saleGoalWeight3);
					 $("#saleGoalWeight4").val(response.data.data.list[0].saleGoalWeight4);
					 $("#saleGoalWeight5").val(response.data.data.list[0].saleGoalWeight5);
					 $("#saleGoalWeight6").val(response.data.data.list[0].saleGoalWeight6);
					 $("#saleGoalWeight7").val(response.data.data.list[0].saleGoalWeight7);
					 
					 $("#saleGoalAmt").val(response.data.data.list[0].saleGoalAmt);
					 
				 }
			 });
		 };
	 };	 
}]);

/** 매출목표등록 controller */
app.controller('goalDtl1Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('goalDtl1Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("goalDtl1Ctrl");

	};
	  
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("goalDtl1Ctrl", function (event, data) {
		if(data != undefined){
			$scope.storeCd = data.storeCd;
			$scope.saleDate  = data.saleDate;
		}

	    $scope.searchGoalDtl1List(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleGoalRgsrDetl1", function (event, data) {
		if(data != undefined){
			$scope.storeCd = data.storeCd;
		}

	    $scope.saleGoalRgsrDetl1(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});
	
	//매출목표상세그리드1
	$scope.searchGoalDtl1List = function () {

		// 파라미터
		var params       = {};
		 	params.storeCd	  = $scope.storeCd;
		 	params.saleDate  = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquirySub("/sale/anals/goal/goal/detail1.sb", params);
			 
	};  
	
	//매출목표등록1
	// successCallback : 저장 성공 시 호출(다음 저장 진행), failCallback : 저장 실패 시 호출
	$scope.saleGoalRgsrDetl1 = function (successCallback, failCallback) {

    	if($scope.flex.rows.length == 0){
    		$scope._popMsg(messages["goalPop.nonlist"]);	//매출목표를 먼저 등록 하십시요.
			if (typeof failCallback === 'function') {
				failCallback();
			}
            return false;
    	}

        var params = new Array();

        for(var i=0; i<$scope.flex.collectionView.items.length; i++){
            var item = $scope.flex.collectionView.items[i];

            params.push(item);
        }

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._postJSONSave.withOutPopUp("/sale/anals/goal/goalDeatil/save.sb", params, function(response) {


            var result = response.data.data;

            if(result < 1){
              //$scope._popMsg(messages["cmm.registFail"]);
              if (typeof failCallback === 'function') {
                failCallback(messages["cmm.registFail"]);
              } else {
                $scope._popMsg(messages["cmm.registFail"]);
              }
              return false;

            }else{
              // 연속 저장 중에는 마지막(매출목표금액합계) 저장 후에만 메시지 표시
              if (typeof successCallback !== 'function') {
                $scope._popMsg(messages["cmm.saveSucc"]);
              }
            }

	         var params       = {};
        	 params.storeCd   = $("#storeCd").val();

	       	 $scope._broadcast("goalDtl1Ctrl", params);

			if (typeof successCallback === 'function') {
				successCallback();
			}
		 }, function() {
			if (typeof failCallback === 'function') {
				failCallback(messages["cmm.saveFail"]);
			}
		 }, function() {});
	};
}]);

/** 매출목표등록 controller */
app.controller('goalDtl2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('goalDtl2Ctrl', $scope, $http, $timeout, true));
	
	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		
	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');
		
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("goalDtl2Ctrl");

	};
	  
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("goalDtl2Ctrl", function (event, data) {
		if(data != undefined){
			$scope.storeCd = data.storeCd;
			$scope.saleDate  = data.saleDate;
		}

		$scope.searchGoalDtl2List(true);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});
	
	// 다른 컨트롤러의 broadcast 받기
	$scope.$on("saleGoalRgsrDetl2", function (event, data) {
		if(data != undefined){
			$scope.storeCd = data.storeCd;
		}

	    $scope.saleGoalRgsrDetl2(true);
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	});
	
	//매출목표상세그리드2
	$scope.searchGoalDtl2List = function () {

		// 파라미터
		var params       = {};
			params.storeCd	  = $scope.storeCd;
			params.saleDate  = wijmo.Globalize.format($scope.startDate, 'yyyyMM');
		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquirySub("/sale/anals/goal/goal/detail2.sb", params);
				 
	};
	
	//매출목표등록2
	// successCallback : 저장 성공 시 호출(다음 저장 진행), failCallback : 저장 실패 시 호출
	$scope.saleGoalRgsrDetl2 = function (successCallback, failCallback) {

        var params = new Array();

        for(var i=0; i<$scope.flex.collectionView.items.length; i++){
            var item = $scope.flex.collectionView.items[i];

            params.push(item);
        }

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._postJSONSave.withOutPopUp("/sale/anals/goal/goalDeatil/save.sb", params, function(response) {

            var result = response.data.data;

            if(result < 1){
              //$scope._popMsg(messages["cmm.registFail"]);
              if (typeof failCallback === 'function') {
                failCallback(messages["cmm.registFail"]);
              } else {
                $scope._popMsg(messages["cmm.registFail"]);
              }
              return false;

            }else{
              // 연속 저장 중에는 마지막(매출목표금액합계) 저장 후에만 메시지 표시
              if (typeof successCallback !== 'function') {
                $scope._popMsg(messages["cmm.saveSucc"]);
              }
            }

	         var params       = {};
        	 params.storeCd   = $("#storeCd").val();

	    	 $scope._broadcast("goalDtl2Ctrl", params);

			if (typeof successCallback === 'function') {
				successCallback();
			}
		 }, function() {
			if (typeof failCallback === 'function') {
				failCallback(messages["cmm.saveFail"]);
			}
		 }, function() {});
	};
	
}]);