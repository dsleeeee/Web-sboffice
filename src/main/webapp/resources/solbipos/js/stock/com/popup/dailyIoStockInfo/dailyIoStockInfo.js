/**
 * get application
 */
var app = agrid.getApp();
var list;
/** 테이블별 매출현황 controller */
app.controller('dailyIoStockInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dailyIoStockInfoCtrl', $scope, $http, true));

  // 상품분류 항목표시 초기값 체크
  $scope.isChecked = true;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dailyIoStockInfoCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dailyIoStockInfoCtrl", function (event, data) {

	$scope.ioOccrDt			= data.ioOccrDt;
	$scope.ioOccrFg			= data.ioOccrFg;
	$scope.ioOccrNm			= data.ioOccrNm;
    $scope.orgnFg     		= data.orgnFg;

    $scope.dailyIoStockInfoLayer.show(true);

    $scope.searchDailyIoStockInfoList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 일자별 수불현황 - 일자별 수불현황  상세 리스트 조회
  $scope.searchDailyIoStockInfoList = function () {
    // 파라미터
    var params      	= {};
        params.orgnFg  	= $scope.orgnFg;
        params.saleDate 	= $scope.ioOccrDt;
        //params.slipFg 	= $scope.slipFg;
        params.ioOccrFg 	= $scope.ioOccrFg;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._postJSONQuery.withPopUp("/stock/com/popup/dailyIoStockInfo/getDailyIoStockInfoList.sb", params, function(response){

    	var list = response.data.data.list;
    	var length = response.data.data.list.length;
    	var grid = wijmo.Control.getControl("#storeDtlGrid");

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
	  var arrWidth = [100, 100, 100, 100, 150, 150, 150, 100, 100, 100, 80, 80];
	  var arrAlign = ["center", "center", "center", "center", "center", "center", "center", "center", "center", "center", "right", "right"];
	  var arrSum = ["", "", "", "", "", "", "", "", "", "", "Sum", "Sum"];

	  if($scope.orgnFg == "H") { // 본사 권한
		    switch($scope.ioOccrFg) {
		    case "01" : {arrBinding=["slipNo", "instockDate", "vendrCd", "vendrNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 본사입고(전표번호, 입고일, 거래처코드, 거래처명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "16" : {arrBinding=["slipNo", "rtnDate", "vendrCd", "vendrNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 업체반출(전표번호, 반출일, 거래처코드, 거래처명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "13" : {arrBinding=["slipNo", "outDate", "storeCd", "storeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "outTotQty", "outTot"]; break;} // 본사출고(전표번호, 출고일, 매장코드, 매장명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "02" : {arrBinding=["slipNo", "carryInDate", "storeCd", "storeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "outTotQty", "outTot"]; break;} // 본사반입(전표번호, 반입일, 매장코드, 매장명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "04" : {arrBinding=["slipNo", "inConfmDt", "storeCd", "storeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 매장이입(전표번호, 이입일, 매장코드, 매장명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "14" : {arrBinding=["slipNo", "outConfmDt", "storeCd", "storeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "outTotQty", "outTot"]; break;} // 매장이출(전표번호, 이출일, 매장코드, 매장명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
		    case "17" : {arrBinding=["disuseDate", "seqNo", "title", "prodFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "disuseQty", "disuseAmt"]; break;} // 재고폐기(폐기일, 차수, 제목, 구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 폐기수량, 폐기금액)
		    case "21" : {arrBinding=["adjDate", "seqNo", "title", "prodFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "adjQty", "adjAmt"]; break;} // 재고조정(조정일, 차수, 제목, 구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 조정수량, 조정금액)
		    case "22" : {arrBinding=["setDate", "seqNo", "prodFgNm", "setMakeFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "compstProdCd", "compstProdQty"]; break;} //세트생성(생성일, 차수, 구분, 구성/해체구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 세트구성상품코드, 세트구성상품수량)
//		    !!!!!!!!!!!!!!!!!!!!!!!!!!! 아직 컬럼 정보 및 쿼리 안받음 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		    case "19" : {arrBinding=["saleDate", "vendrCd", "vendrNm"]; break;} // 거래처출고(출고일, 거래처코드, 거래처명)
//			case "33" : {arrBinding=["saleDate", "vendrCd", "vendrNm"]; break;} // 거래처반품(반품일, 거래처코드, 거래처명)
		    }
	  } else if($scope.orgnFg == "S") { // 가맹점 권한
			switch($scope.ioOccrFg) {
			case "03" : {arrBinding=["slipNo", "instockDate", "hqOfficeCd", "hqOfficeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 매장입고(전표번호, 입고일, 본사코드, 본사명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
			case "12" : {arrBinding=["slipNo", "carryOutDate", "hqOfficeCd", "hqOfficeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 매장반품(전표번호, 반품일, 본사코드, 본사명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
			case "06" : {arrBinding=["slipNo", "instockDate", "vendrCd", "vendrNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 사입입고(전표번호, 입고일, 거래처코드, 거래처명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
			case "18" : {arrBinding=["slipNo", "rtnDate", "vendrCd", "vendrNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 사입반품(전표번호, 반품일, 거래처코드, 거래처명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
//			!!!!!!!!!!!!!!!!!!!!!!!!!!! 아직 컬럼 정보 및 쿼리 안받음 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//			case "11" : {arrBinding=["saleDate", "dlvrPackFg"]; break;} // 매장판매(영업일자, 배달포장구분)
			case "11" : {arrBinding=["saleDate", "storeCd", "storeNm", "qty", "amt"]; break;} // 매장판매(매출일자, 매장코드, 매장명, 수량, 금액)
			case "04" : {arrBinding=["slipNo", "inConfmDt", "hqOfficeCd", "hqOfficeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "inTotQty", "inTot"]; break;} // 매장이입(전표번호, 이입일, 본사코드, 본사명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
			case "14" : {arrBinding=["slipNo", "outConfmDt", "hqOfficeCd", "hqOfficeNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "outTotQty", "outTot"]; break;} // 매장이출(전표번호, 이출일, 본사코드, 본사명, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 수량, 금액)
			case "17" : {arrBinding=["disuseDate", "seqNo", "title", "prodFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "disuseQty", "disuseAmt"]; break;} // 재고폐기(폐기일, 차수, 제목, 구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 폐기수량, 폐기금액)
			case "21" : {arrBinding=["adjDate", "seqNo", "title", "prodFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "adjQty", "adjAmt"]; break;} // 재고조정(조정일, 차수, 제목, 구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 조정수량, 조정금액)
			case "22" : {arrBinding=["setDate", "seqNo", "prodFgNm", "setMakeFgNm", "lv1Nm", "lv2Nm", "lv3Nm", "prodCd", "prodNm", "barcdCd", "compstProdCd", "compstProdQty"]; break;} //세트생성(생성일, 차수, 구분, 구성/해체구분, 대분류, 중분류, 소분류, 상품코드, 상품명, 바코드, 세트구성상품코드, 세트구성상품수량)
			}
	  }
	  $scope.drawGrid(grid, arrBinding, arrWidth, arrAlign, arrSum);
  };

  $scope.drawGrid = function(grid, arrBinding, arrWidth, arrAlign, arrSum){
	  for(var i=0; i<arrBinding.length; i++){
		  var aggregate = arrSum[i];
		  if(aggregate == ""){
			  grid.columns.push(new wijmo.grid.Column({header: messages["dailyIostock." + arrBinding[i]], binding: arrBinding[i], width: arrWidth[i] , align: arrAlign[i], isReadOnly: "true", format: ((arrBinding[i].indexOf('barcdCd') >= 0 ||arrBinding[i].indexOf('prodCd') >= 0) ? "d" : "")}));
		  }else{
			  grid.columns.push(new wijmo.grid.Column({header: messages["dailyIostock." + arrBinding[i]], binding: arrBinding[i], width: arrWidth[i] , align: arrAlign[i], isReadOnly: "true", aggregate: "Sum"}));
		  }
	  }
  }

  // 상품분류 숨기기
  $scope.isChkDt = function() {
	 for(var item = 0; item < $scope.flex.columns.length; item++) {
		 var col = $scope.flex.columns[item];
		 if(col.binding === 'lv1Nm' || col.binding === 'lv2Nm' || col.binding === 'lv3Nm') {
			$scope.flex.columns[item].visible = $scope.isChecked? true: false;
		 }
	 }
	 $scope.flex.refresh();
  };

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
      }, '재고현황_일자별수불현황_' + $scope.ioOccrNm + '정보_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);

