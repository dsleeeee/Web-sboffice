/**
 * get application
 */
var app = agrid.getApp();

// srchOption1 VALUE
var srchOption1 = [
	{"name":"전체","value":"0"},
	{"name":"당일매출제외","value":"1"},
];

/** 반품현황(조회조건) controller */
app.controller('rtnStatusBillCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('rtnStatusBillCtrl', $scope, $http, $timeout, true));

  $scope.srchrtnStatusBillStartDate = wcombo.genDateVal("#srchrtnStatusBillStartDate", getToday());
  $scope.srchrtnStatusBillEndDate   = wcombo.genDateVal("#srchrtnStatusBillEndDate", getToday());
  
  $scope.isDaySearch = false;
  $scope.isDayDtlSearch = false;
  $scope.isPosDtlSearch = false;

	comboData = {};
	comboData.name  = "현금영수증";
	comboData.value = "021";
	srchOption2.splice(3,0,comboData);

  //조회조건 콤보박스 데이터 Set
  $scope._setComboData("srchOption1", srchOption1);
  $scope._setComboData("srchOption2", srchOption2);

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.rtnStatusBillSelectStoreShow = function () {
    $scope._broadcast('rtnStatusBillSelectStoreCtrl');
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	s.refresh();

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("rtnStatusBillCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.binding === "billNo") { // 영수증번호
          var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }

		// 결제수단
		for (var i = 0; i < payColList.length; i++) {
			  if (col.binding === ("pay" + payColList[i].payCd)) {
				  var item = s.rows[e.row].dataItem;

				  // 값이 있으면 링크 효과
				  if (nvl(item[("pay" + payColList[i].payCd)], '') !== '') {
					  wijmo.addClass(e.cell, 'wijLink');
					  wijmo.addClass(e.cell, 'wj-custom-readonly');
				  }
			  }
		}

		// 할인
		for (var i = 0; i < dcColList.length; i++) {
		  if (col.binding === ("dc" + dcColList[i].dcCd)) {
		  	var item = s.rows[e.row].dataItem;

		  	// 07:포장할인, 08:현장할인 이 아닌 경우 링크효과
		  	if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
		  	  // 값이 있으면 링크 효과
		  	  if (nvl(item[("dc" + dcColList[i].dcCd)], '') !== '') {
		  		wijmo.addClass(e.cell, 'wijLink');
		  		wijmo.addClass(e.cell, 'wj-custom-readonly');
		  	  }
		  	}
		  }
		}
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);

      /* 머지된 헤더 셀 클릭시 정렬 비활성화
  	   * 헤더 cellType: 2 && 머지된 row 인덱스: 0, 1 && 동적 생성된 column 인덱스 4 초과
  	   * 머지영역 클릭시 소트 비활성화, 다른 영역 클릭시 소트 활성화
  	   */
      if(ht.cellType == 2 && ht.row < 1 && ht.col > 0) {
    	  s.allowSorting = false;
      } else {
    	  s.allowSorting = true;
	  }

      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        var params       = {};
        	params.storeCd  = selectedRow.storeCd;
        	params.storeNm  = selectedRow.storeNm;
			params.saleDate = selectedRow.saleDate.replaceAll('-', '');
			params.posNo    = selectedRow.posNo;
			params.billNo   = selectedRow.billNo;
			params.saleYn   = 'N';

		  if (col.binding === "billNo") { // 영수증번호 클릭
			  $scope._broadcast('billInfoCtrl', params);
		  }

		  // 결제수단
		  for (var i = 0; i < payColList.length; i++) {
			  if (col.binding === ("pay" + payColList[i].payCd)) {
				  // 값이 있으면 링크
				  if (nvl(selectedRow[("pay" + payColList[i].payCd)], '') !== '') {
					if(payColList[i].payCd == "021"){// 현금영수증 현금팝업
						$scope._broadcast('cashCtrl', params);
					} else {
						$scope._broadcast(payColList[i].payMethod.toLowerCase().replaceAll('_','') + 'Ctrl', params);
					}
				  }
			  }
		  }

		  // 할인
		  for (var i = 0; i < dcColList.length; i++) {
			  if (col.binding === ("dc" + dcColList[i].dcCd)) {
				  // var item = s.rows[e.row].dataItem;

				  // 07:포장할인, 08:현장할인이 아닌 경우
				  if (dcColList[i].dcCd !== '07' && dcColList[i].dcCd !== '08') {
					  // 값이 있으면 링크
					  if (nvl(selectedRow[("dc" + dcColList[i].dcCd)], '') !== '') {
						  params.dcCd = dcColList[i].dcCd;
						  $scope._broadcast(dcColList[i].dcMethod.toLowerCase() + 'DcCtrl', params);
					  }
				  }
			  }
		  }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem			= {};
    dataItem.storeCd		= messages["rtnStatus.storeCd"];
    dataItem.storeNm		= messages["rtnStatus.storeNm"];
    dataItem.saleDate		= messages["rtnStatus.info"];
    dataItem.posNo			= messages["rtnStatus.info"];
    dataItem.billNo			= messages["rtnStatus.info"];
    dataItem.orgSaleDate	= messages["rtnStatus.orgInfo"];
    dataItem.orgPosNo		= messages["rtnStatus.orgInfo"];
    dataItem.orgBillNo		= messages["rtnStatus.orgInfo"];
    dataItem.totSaleAmt		= messages["rtnStatus.totSaleAmt"];
    dataItem.totDcAmt  		= messages["rtnStatus.totDcAmt"];
    dataItem.realSaleAmt	= messages["rtnStatus.realSaleAmt"];
    dataItem.gaAmt     		= messages["rtnStatus.gaAmt"];
    dataItem.vatAmt    		= messages["rtnStatus.vatAmt"];
    dataItem.totTipAmt 		= messages["rtnStatus.totTipAmt"];
    dataItem.totEtcAmt 		= messages["rtnStatus.totEtcAmt"];

	  // 결제수단 헤더머지 컬럼 생성
	  for (var i = 0; i < arrPayCol.length; i++) {
		  dataItem['pay' + arrPayCol[i]] = messages["todayDtl.payMethod"];
	  }
	  // 할인구분 헤더머지 컬럼 생성
	  for (var i = 0; i < arrDcCol.length; i++) {
		  dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
	  }

    s.columnHeaders.rows[0].dataItem = dataItem;

    s.itemFormatter = function (panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
            //align in center horizontally and vertically
            panel.rows[r].allowMerging    = true;
            panel.columns[c].allowMerging = true;
            wijmo.setCss(cell, {
                display    : 'table',
                tableLayout: 'fixed'
            });
            cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
            wijmo.setCss(cell.children[0], {
                display      : 'table-cell',
                verticalAlign: 'middle',
                textAlign    : 'center'
            });
        }
        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
            // GroupRow 인 경우에는 표시하지 않는다.
            if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
                cell.textContent = '';
            } else {
                if (!isEmpty(panel._rows[r]._data.rnum)) {
                    cell.textContent = (panel._rows[r]._data.rnum).toString();
                } else {
                    cell.textContent = (r + 1).toString();
                }
            }
        }
        // readOnly 배경색 표시
        else if (panel.cellType === wijmo.grid.CellType.Cell) {
            var col = panel.columns[c];
            if (col.isReadOnly) {
                wijmo.addClass(cell, 'wj-custom-readonly');
            }
        }
    }
    // <-- //그리드 헤더2줄 -->
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("rtnStatusBillCtrl", function (event, data) {
    $scope.searchrtnStatusBillList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //  조회
  $scope.searchrtnStatusBillList = function (isPageChk) {

	  var date1 = new Date(wijmo.Globalize.format($scope.srchrtnStatusBillStartDate.value, 'yyyy-MM-dd'));
	  var date2 = new Date(wijmo.Globalize.format($scope.srchrtnStatusBillEndDate.value, 'yyyy-MM-dd'));
	  var diffDay = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨ㄴ

	  // 조회일자 최대 한달(31일) 제한
	  if (diffDay > 31) {
		  $scope._popMsg(messages["prodsale.date.error"]);
		  return false;
	  }
	  if(date1 > date2){
		  $scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
		  return false;
	  }
    // 파라미터
    var params       = {};
    params.storeCd   = $("#rtnStatusBillSelectStoreCd").val();
    params.isPageChk = isPageChk;
	params.startDate = wijmo.Globalize.format($scope.srchrtnStatusBillStartDate.value, 'yyyyMMdd');
	params.endDate 	 = wijmo.Globalize.format($scope.srchrtnStatusBillEndDate.value, 'yyyyMMdd');
	params.payCol	 = payCol;
	params.dcCol	 = dcCol;
	params.option1	 = $scope.srchOption1Combo.selectedValue;
	params.option2 	 = $scope.srchOption2Combo.selectedValue;

	$scope.excelDayStoreCd		= params.storeCd;
    $scope.excelDayListScale	= params.listScale;
	$scope.excelDayStartDate	= params.startDate;
	$scope.excelDayEndDate		= params.endDate;
    $scope.isDaySearch			= true;

	// 조회 수행 : 조회URL, 파라미터, 콜백함수
	$scope._inquiryMain("/sale/status/rtnStatus/bill/list.sb", params);

  };

  //엑셀 다운로드
  $scope.excelDownload = function () {
	  // 파라미터
	  var params     = {};
	  $scope._broadcast('rtnStatusBillExcelCtrl',params);
  };

	// 상품별 엑셀 다운로드
	$scope.excelDownload = function () {
		if ($scope.flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
				includeColumnHeaders: true,
				includeCellStyles: true,
				includeColumns: function (column) {
					return column.visible;
				}
			},
				'매출현황_반품현황_영수증별상세_'+getToday()+'.xlsx', function () {
					$timeout(function () {
						$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
					}, 10);
				});
		}, 10);
	};
}]);
