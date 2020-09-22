/**
 * get application
 */
var app = agrid.getApp();

/** 재고현황 팝업 controller */
app.controller('cmmQtyDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cmmQtyDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("cmmQtyDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("cmmQtyDtlCtrl", function (event, data) {

	$scope.prodCd			= data.prodCd;
    $scope.prodNm 			= data.prodNm;
    $scope.poUnitQty 		= data.poUnitQty;
    $scope.qtyFg			= data.qtyFg;

    $scope.cmmQtyDtlLayer.show(true);
    $scope.searchCmmQtyDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 재고현황- 재고현황 리스트 조회
  $scope.searchCmmQtyDtlList = function () {
    // 파라미터
    var params      	= {};
    params.prodCd			= $scope.prodCd;
    params.prodNm 			= $scope.prodNm;
    params.poUnitQty 		= $scope.poUnitQty;
    params.qtyFg			= $scope.qtyFg;

//    $scope.displayChg($scope.qtyFg);
  // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._postJSONQuery.withOutPopUp("/stock/com/popup/cmmQtyDtl/getCmmQtyDtlList.sb", params, function(response) {
    	var length = response.data.data.list.length;
   	 	var grid = wijmo.Control.getControl("#qtyDtlGrid");

   	  if(length != "" || length != null){

			while(grid.columns.length > 4){
				grid.columns.removeAt(grid.columns.length-1);
			}
	   	 	if($scope.qtyFg == "ioOccrQty03"){ // 매장입고
	   	 		$scope.popNm = messages["cmmQtyDtl.accStoreIn"];
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.instockDate"], binding: 'instockDate', align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeCd"], 	binding: 'hqOfficeCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeNm"], 	binding: 'hqOfficeNm', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
	   	 	}else if($scope.qtyFg == "ioOccrQty12"){ // 매장반품
	   	 		$scope.popNm = messages["cmmQtyDtl.accStoreOut"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.outDate"], 	binding: 'outDate', align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeCd"], 	binding: 'hqOfficeCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeNm"], 	binding: 'hqOfficeNm', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
	   	 	}else if($scope.qtyFg == "ioOccrQty06"){ // 사입입고
	   	 		$scope.popNm = messages["cmmQtyDtl.accPurchsIn"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.instockDate"], binding: 'instockDate', align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.vendrCd"], 	binding: 'vendrCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.vendrNm"], 	binding: 'vendrNm', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty18"){ // 사입반품
		   		$scope.popNm = messages["cmmQtyDtl.accPurchsOut"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.outDate"], 	binding: 'outDate', align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.vendrCd"], 	binding: 'vendrCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.vendrNm"], 	binding: 'vendrNm', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty11"){ // 매장판매
		   		$scope.popNm = messages["cmmQtyDtl.accStoreSale"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.saleDate"], 		binding: 'saleDate', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.storeCd"], 	binding: 'storeCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.storeNm"], 	binding: 'storeNm', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty04"){ // 매장이입
		   		$scope.popNm = messages["cmmQtyDtl.accStoreMoveIn"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.moveInDate"], 	binding: 'moveInDate', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeCd"], 	binding: 'hqOfficeCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeNm"], 	binding: 'hqOfficeNm', 	align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty14"){ // 매장이출
		   		$scope.popNm = messages["cmmQtyDtl.accStoreMoveOut"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.moveOutDate"], binding: 'moveOutDate', align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeCd"], 	binding: 'hqOfficeCd', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.hqOfficeNm"], 	binding: 'hqOfficeNm', 	align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty17"){ // 재고폐기
		   		$scope.popNm = messages["cmmQtyDtl.accDisuse"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.disuseDate"], 	binding: 'disuseDate', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.seqNo"], 		binding: 'seqNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.title"], 		binding: 'title', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.fg"], 			binding: 'fg', 			align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty21"){ // 재고조정
		   		$scope.popNm = messages["cmmQtyDtl.accAdj"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.adjDate"], 	binding: 'adjDate', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.seqNo"], 		binding: 'seqNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.title"], 		binding: 'title', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.fg"], 			binding: 'fg', 			align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}else if($scope.qtyFg == "ioOccrQty22"){ // 세트생성
		   		$scope.popNm = messages["cmmQtyDtl.accSetIn"];
		   	 	grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.slipNo"], 		binding: 'slipNo', 		align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.createDate"], 	binding: 'createDate', 	align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.fg"], 			binding: 'fg', 			align: "center" , isReadOnly: "true"}));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.qty"], 		binding: 'qty', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   		grid.columns.push(new wijmo.grid.Column({header: messages["cmmQtyDtl.amt"], 		binding: 'amt', 		align: "right" , isReadOnly: "true", aggregate: "Sum" }));
		   	}
		}else{
			$scope._popMsg(response.data.message);
		}

    });

    // 주문대비 입출고현황 그리드 조회 후 상세내역 그리드 초기화
//    var orderDtlScope = agrid.getScope('cmmQtyDtlCtrl');
//    orderDtlScope.dtlGridDefault();
  };

  //그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
  };
}]);

