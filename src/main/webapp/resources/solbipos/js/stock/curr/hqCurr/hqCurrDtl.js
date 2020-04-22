/**
 * get application
 */
var app = agrid.getApp();

/** 재고현황 팝업 controller */
app.controller('hqCurrDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqCurrDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("hqCurrDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("hqCurrDtlCtrl", function (event, data) {
	
	$scope.prodCd		= data.prodCd;
	$scope.prodNm 		= data.prodNm;
	$scope.hqOfficeCd 	= data.hqOfficeCd;
	$scope.storeCd 		= data.storeCd;
	
	
	// $scope에 변경된 값 뷰에 반영
	$scope.$apply();

    $scope.hqCurrDtlLayer.show(true);
    // 초기에 데이터 조회
    $scope.searchhqCurrDtlList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });
  
//재고현황- 재고현황 리스트 조회
  $scope.searchhqCurrDtlList = function () {
    // 파라미터
    var params      	= {};
        params.prodCd		= $scope.prodCd;
        params.hqOfficeCd	= $scope.hqOfficeCd;
        params.storeCd		= $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/stock/curr/hqCurr/hqCurr/getHqCurrDtlList.sb", params);
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
			}, $(menuNm).selector + '_' + messages["hqCurr.hqCurrDtl"]+'_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};
}]);

