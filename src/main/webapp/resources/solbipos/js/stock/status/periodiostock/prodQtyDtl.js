/**
 * get application
 */

var app = agrid.getApp();

app.controller('prodQtyDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	  // 상위 객체 상속 : T/F 는 picker
	  angular.extend(this, new RootController('prodQtyDtlCtrl', $scope, $http, true));
	  
	// 접속사용자의 권한(H : 본사, S: 매장)
	  //$scope.orgnFg = gvOrgnFg;

	  // grid 초기화 : 생성되기전 초기화되면서 생성된다
	  $scope.initGrid = function (s, e) {

		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("prodQtyDtlCtrl");
		
		// 총매출열에 CSS 추가
		wijmo.addClass(s.columns[2], 'wijLink');
		// add the new GroupRow to the grid's 'columnFooters' panel
		s.columnFooters.rows.push(new wijmo.grid.GroupRow());
		// add a sigma to the header to show that this is a summary row
		s.bottomLeftCells.setCellData(0, 0, '합계');
	  };

	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("prodQtyDtlCtrl", function (event, data) {

		$scope.slipNo			= data.slipNo;
	    $scope.startDate 		= data.startDate;
	    $scope.endDate			= data.endDate;
	    $scope.prodClassCd    	= data.prodClassCd;
	    $scope.storeCd  		= data.storeCd; // 매장코드
	    $scope.prodCd    		= data.prodCd; // 상품코드
	    $scope.prodNm 			= data.prodNm; // 상품명
	    $scope.orgnFg     		= data.orgnFg;
	    $scope.colCode			= data.colCode; // 수량 구분
	    $scope.poUnitQty		= data.poUnitQty; // 입수
	    $scope.ioOccrFg			= data.ioOccrFg; // 헤더

	    $scope.prodQtyDtlLayer.show(true);
	    $scope.searchProdQtyDtlList();

	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });
	  
	  $scope.searchProdQtyDtlList = function() {
		  var params      	= {};
		    params.startDate 		= $scope.startDate;
		    params.endDate			= $scope.endDate;
		    params.prodCd			= $scope.prodCd;
		    params.prodNm 			= $scope.prodNm;
		    params.poUnitQty 		= $scope.poUnitQty;
		    params.qtyFg			= $scope.qtyFg;
		    params.colCode			= $scope.colCode;
		    params.storeCd			= $scope.storeCd;
		    params.hqOfficeCd		= $("#hqOfficeCode").val();
		    
		    // 조회 수행 : 조회URL, 파라미터, 콜백함수
		    $scope._postJSONQuery.withOutPopUp("/stock/com/popup/cmmQtyDtl/getCmmQtyDtlList.sb", params, function(response) {
		    	var list = response.data.data.list;
		    	var length = response.data.data.list.length;
		    	var grid = wijmo.Control.getControl("#prodQtyDtlGrid");
		    	
		    	if(length != "" || length != null){
		    		while(grid.columns.length > 0){
		    			grid.columns.removeAt(grid.columns.length-1);
		    		}
		    		
		    		$scope.changeColumns(grid);
		    		
		    		var data = new wijmo.collections.CollectionView(list);
		    		data.trackChanges = true;
		            $scope.data = data;
		    	} else{
					$scope._popMsg(response.data.message);
				}
		    });
	  };
	  
	  $scope.changeColumns = function(grid){
		  var arrBinding;
		  var nm, cd;
		  
		  ($scope.storeCd === null || $scope.storeCd === '') ? (cd="storeCd", nm = "storeNm") : (cd="hqOfficeCd", nm = "hqOfficeNm");
		  
		  switch($scope.colCode){
		  case "01" : {arrBinding=["slipNo", "instockDate", "vendrCd", "vendrNm", "inTotQty", "inTot"]; break;} // 본사입고(전표번호, 입고일, 거래처코드, 거래처명, 수량, 금액)
		  case "16" : {arrBinding=["slipNo", "rtnDate", "vendrCd", "vendrNm", "inTotQty", "inTot"]; break;} // 업체반출(전표번호, 반출일, 거래처코드, 거래처명, 수량, 금액)
		  case "13" : {arrBinding=["slipNo", "outDate", "storeCd", "storeNm", "outTotQty", "outTot"]; break;} // 본사출고(전표번호, 출고일, 매장코드, 매장명, 수량, 금액)
		  case "02" : {arrBinding=["slipNo", "carryInDate", "storeCd", "storeNm", "outTotQty", "outTot"]; break;} // 본사반입(전표번호, 반입일, 매장코드, 매장명, 수량, 금액)
		  
		  // 본사, 매장권한 둘다 씀
		  case "04" : {arrBinding=["slipNo", "inConfmDt", cd, nm, "inTotQty", "inTot"]; break;} // 매장이입(전표번호, 이입일, [매장코드, 매장명], [본사코드, 본사명] 수량, 금액)
		  case "14" : {arrBinding=["slipNo", "outConfmDt", cd, nm, "outTotQty", "outTot"]; break;} // 매장이출(전표번호, 이출일, [매장코드, 매장명], [본사코드, 본사명], 수량, 금액)
		  case "17" : {arrBinding=["disuseDate", "seqNo", "title", "prodFgNm", "disuseQty", "disuseAmt"]; break;} // 재고폐기(폐기일, 차수, 제목, 구분, 폐기수량, 폐기금액)
		  case "21" : {arrBinding=["adjDate", "seqNo", "title", "prodFgNm", "adjQty", "adjAmt"]; break;} // 재고조정(조정일, 차수, 제목, 구분, 조정수량, 조정금액)
		  case "22" : {arrBinding=["setDate", "seqNo", "prodFgNm", "setMakeFgNm", "compstProdCd", "compstProdQty"]; break;} //세트생성(생성일, 차수, 구분, 구성/해체구분, 세트구성상품코드, 세트구성상품수량)
		  
		  // !!!!!!!!!!!!!!!!!!!!!!!!!!! 아직 컬럼 정보 및 쿼리 안받음 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		  case "19" : {arrBinding=["saleDate", "vendrCd", "vendrNm"]; break;} // 거래처출고(출고일, 거래처코드, 거래처명)
		  case "33" : {arrBinding=["saleDate", "vendrCd", "vendrNm"]; break;} // 거래처반품(반품일, 거래처코드, 거래처명)
		  
		  
		  case "03" : {arrBinding=["slipNo", "instockDate", "hqOfficeCd", "hqOfficeNm", "inTotQty", "inTot"]; break;} // 매장입고(전표번호, 입고일, 본사코드, 본사명, 수량, 금액)
		  case "12" : {arrBinding=["slipNo", "carryOutDate", "hqOfficeCd", "hqOfficeNm", "inTotQty", "inTot"]; break;} // 매장반품(전표번호, 반품일, 본사코드, 본사명, 수량, 금액)
		  case "06" : {arrBinding=["slipNo", "instockDate", "vendrCd", "vendrNm", "inTotQty", "inTot"]; break;} // 사입입고(전표번호, 입고일, 거래처코드, 거래처명, 수량, 금액)
		  case "18" : {arrBinding=["slipNo", "carryOutDate", "vendrCd", "vendrNm", "inTotQty", "inTot"]; break;} // 사입반품(전표번호, 반품일, 거래처코드, 거래처명, 수량, 금액)
		  
		  // !!!!!!!!!!!!!!!!!!!!!!!!!!! 아직 컬럼 정보 및 쿼리 안받음 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		  case "11" : {arrBinding=["saleDate", "dlvrPackFg"]; break;} // 매장판매(영업일자, 배달포장구분)
		  }
		  
		  $scope.drawGrid(grid, arrBinding);
  		
	  };
	  
	  $scope.drawGrid = function(grid, arrBinding){
		  for(var i=0; i<arrBinding.length; i++){
			  if(arrBinding[i].indexOf('Qty') || arrBinding[i].indexOf('Tot')){
				  grid.columns.push(new wijmo.grid.Column({header: messages["periodIostock." + arrBinding[i]], binding: arrBinding[i],	width:"*" , align: (arrBinding[i].indexOf('Qty') ? "center" : "right") , isReadOnly: "true" , aggregate:"Sum"}));
			  } else{
				  grid.columns.push(new wijmo.grid.Column({header: messages["periodIostock." + arrBinding[i]], binding: arrBinding[i],	width:"*" , align: "center" , isReadOnly: "true"}));
			  }
		  }
	  }
	  
	  //엑셀 다운로드
	  $scope.excelDownload = function () {
	    if ($scope.flex.rows.length <= 0) {
	      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
	      return false;
	    }

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
	    $timeout(function () {
	      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
	        includeColumnHeaders: true,
	        includeCellStyles   : true,
	        includeColumns      : function (column) {
	          return column.visible;
	        }
	      }, $(menuNm).selector + '_' + $scope.ioOccrFg+'_'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
}]);