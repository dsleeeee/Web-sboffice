/**
 * get application
 */

// 콤보박스 아무것도 선택하지 않았을 때 사용
var defaultComboData = [
	{"name": "전체", "value": ""}
];

var app = agrid.getApp();

app.controller('stockManageDtlViewCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
	// 상위 객체 상속 : T/F 는 picker
	angular.extend(this, new RootController('stockManageDtlViewCtrl', $scope, $http, true));

	$scope._setComboData("srchReason", defaultComboData);
	// 조회일자 세팅
	$scope.srchStartDate = wcombo.genDateVal("#srchClassStartDate", getToday());
	$scope.srchEndDate   = wcombo.genDateVal("#srchClassEndDate", getToday());
	$scope.orgnFg = gvOrgnFg; // ?
	
	$scope.isSearch = false;
	
	// 조회조건 콤보박스 listScale 세팅
	$scope._setComboData("stockManageDtlViewListScaleBox", gvListScaleBoxData);

	// 상태 세팅
	$scope._setComboData("srchStatus", [
	    {"name": messages["cmm.all"], "value": ""},
	    {"name": messages["stockManageDtlView.acins"], "value": "1"}, // 실사
	    {"name": messages["stockManageDtlView.adj"], "value": "2"}, // 조정
	    {"name": messages["stockManageDtlView.disuse"], "value": "3"} // 폐기
	]);

	// 진행 세팅
	$scope._setComboData("srchProcFg", [
	    {"name": messages["cmm.all"], "value": ""},
	    {"name": messages["stockManageDtlView.procFg0"], "value": "0"}, // 등록
	    {"name": messages["stockManageDtlView.procFg1"], "value": "1"} // 확정
	]);

	// grid 초기화 : 생성되기전 초기화되면서 생성된다
	$scope.initGrid = function (s, e) {
		// picker 사용시 호출 : 미사용시 호출안함
		$scope._makePickColumns("stockManageDtlViewCtrl");

		// 그리드 링크 효과
		s.formatItem.addHandler(function (s, e) {
			if (e.panel === s.cells) {
				var col = s.columns[e.col];
				var item = s.rows[e.row].dataItem;

				// 소계, 합계 row에 색상 넣기
				if (col.binding === "hqGbnNm" || col.binding === "totDate" || col.binding === "seqNo" || col.binding === "procFgNm" || col.binding === "title" ||
					col.binding === "reasonNm" || col.binding === "prodCd" || col.binding === "prodNm" || col.binding === "currQty" || col.binding === "acinsQty" ||
					col.binding === "adjQty" ||col.binding === "disuseQty" || col.binding === "modQty" || col.binding === "remark") {

					if (item[("seq")] === '2') {
						wijmo.addClass(e.cell, 'wij_gridBackground-yellow-bold');
					}
				}
			}
		});

		s.allowMerging = 'Cells';
		s.itemFormatter = function (panel, r, c, cell) {
			if (panel.cellType === wijmo.grid.CellType.Cell) {
				var col = panel.columns[c];
				var rows = panel.rows[r];

				// row가 소계 이거나 합계인 경우, row merging 가능
				if(rows.dataItem.seq == "2") {

					rows.allowMerging = true;

					// 소계, 합계 Title 왼쪽정렬
					if (col.binding === "hqGbnNm" || col.binding === "totDate" || col.binding === "seqNo" || col.binding === "procFgNm" || col.binding === "title" ||
							col.binding === "reasonNm" || col.binding === "prodCd" || col.binding === "prodNm" || col.binding === "currQty" || col.binding === "acinsQty") {
						wijmo.addClass(cell, 'itemAlignment');
					}
				}
			}
		};

		// 그리드 header 클릭시 정렬 이벤트 막기
		s.addEventListener(s.hostElement, 'mousedown', function (e) {
			var ht = s.hitTest(e);
			s.allowSorting = false;
		});

	};

	function formatDate(date) {
		var yyyy = date.substr(0,4);
	    var mm = date.substr(4,2);
	    var dd = date.substr(6,2);
		return [yyyy, mm, dd].join('-');
	}

	$scope.$on("stockManageDtlViewCtrl", function (event, data){

		$scope.searchStockManageDtlViewList(true);
		// 기능수행 종료 : 반드시 추가
		event.preventDefault();
	});

	//다른 컨트롤러의 broadcast 받기(페이징 초기화)
	$scope.$on("stockManageDtlViewCtrlSrch", function (event, data) {

		var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
		var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
		var diffDay = (endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24); // 시 * 분 * 초 * 밀리세컨

		// 조회일자 최대 한달(31일) 제한
		if (diffDay >= 31) {
			$scope._popMsg(messages['weight.date.error']);
			return false;
		}

		$scope.comboArray =  $scope.comboArrayForSrc; // ????????????
		$scope.searchStockManageDtlViewList(false);

		// 기능수행 종료 : 반드시 추가
    	event.preventDefault();
    });

	// 실사/조정/폐기 리스트 조회
	$scope.searchStockManageDtlViewList = function (isPageChk) {
		// 파라미터
		var params     = {};
		params.isPageChk = isPageChk;
		params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
	    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
	    params.hqGbn = $scope.hqGbnModel; // 상태
	    params.procFg = $scope.procFgModel; // 진행
	    
	    $scope.excelListScale = params.listScale;
	    $scope.excelStartDate = params.startDate;
	    $scope.excelEndDate = params.endDate;
	    $scope.excelHqGbn = params.hqGbn; // 상태
	    $scope.excelProcFg = params.procFg; // 진행
	    $scope.isSearch = true;

		if(params.startDate > params.endDate){
		 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		 	return false;
		}

		// 조회 수행 : 조회URL, 파라미터, 콜백함수
		$scope._inquiryMain("/stock/manage/dtlView/dtlView/stockManageDtlViewList.sb", params, function (){

			var rowCount = 0;     // 합계에 표시될 항목 총갯수

			for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
				var item = $scope.flex.collectionView.items[i];

				// 항목 총갯수 표시를 위해
				if(item.seq === "1") {
					rowCount++;
				}else {
					// 소계, 합계는 정보 없고 title 넣기
					var rowCountAllMsg = "[" + item.prodCd + "] " + item.prodNm + " " + "전체 : (" + rowCount + " 항목)";

					// cell 내용이 같으면 merge 됨(rows.allowMerging = true;)
					item.prodCd = rowCountAllMsg;
					item.prodNm = rowCountAllMsg;
					item.hqGbnNm = rowCountAllMsg;
					item.totDate = rowCountAllMsg;
					item.seqNo = rowCountAllMsg;
					item.procFgNm = rowCountAllMsg;
					item.title = rowCountAllMsg;
					item.reasonNm = rowCountAllMsg;

					rowCount = 0;
				}
			}
		});
	};

	//엑셀 다운로드
	$scope.excelDownload = function () {
		var params     = {};

		var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
		var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
		var diffDay = (endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24); // 시 * 분 * 초 * 밀리세컨

		// 조회일자 최대 한달(31일) 제한
		if (diffDay >= 31) {
			$scope._popMsg(messages['weight.date.error']);
			return false;
		}

		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			},  '실사/조정/폐기 상세_'+getCurDateTime()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	};

	// 상태에 따른 사유 조회
	$scope.setReason = function (s) {

		var params = {};

		if(s.selectedValue === "") {
			$scope._setComboData("srchReason", defaultComboData);

		}else {

			params.hqGbn = s.selectedValue;

			// 계정조회
			$scope._postJSONQuery.withOutPopUp("/stock/manage/dtlView/dtlView/getReason.sb", params, function (response) {
				if (response.data.data.list.length > 0) {
					var reasonList = response.data.data.list;
					$scope._setComboData("srchReason", reasonList);
				} else {
					$scope._setComboData("srchReason", defaultComboData);
				}
			});
		}
	};

	// 상품분류정보 팝업
	$scope.popUpProdClass = function() {
		var popUp = $scope.prodClassPopUpLayer;
		popUp.show(true, function (s) {
			// 선택 버튼 눌렀을때만
			if (s.dialogResult === "wj-hide-apply") {
				var scope = agrid.getScope('prodClassPopUpCtrl');
				var prodClassCd = scope.getSelectedClass();
				var params = {};
				params.prodClassCd = prodClassCd;
				// 조회 수행 : 조회URL, 파라미터, 콜백함수
				$scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
					function(response){
						$scope.prodClassCd = prodClassCd;
						$scope.prodClassCdNm = response.data.data;
					}
				);
			}
		});
	};

	// 상품분류정보 선택취소
	$scope.delProdClass = function(){
		$scope.prodClassCd = "";
		$scope.prodClassCdNm = "";
	}
}]);
