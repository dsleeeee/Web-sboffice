/**
 * get application
 */
var app = agrid.getApp();

/** 테이블별 매출현황 controller */
app.controller('frnchsStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('frnchsStoreDtlCtrl', $scope, $http, true));

  var storeDtlWrittenDate   = wcombo.genDate("#storeDtlWrittenDate");

  //전표구분 grid data-map
  $scope.slipFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["dstmn.orderSlipFg"]},
    {id: "-1", name: messages["dstmn.rtnSlipFg"]},
  ], 'id', 'name');

  //거래명세표
  $scope._setComboData("storeDtlStmtAcctFg", [
    {"name": messages["dstmn.stmtAcctAll"], "value": ""},
    {"name": messages["dstmn.stmtAcctSplr"], "value": "1"},
    {"name": messages["dstmn.stmtAcctSplrRcpnt"], "value": "2"}
  ]);

  //세금계산서 청구, 영수 구분
  $scope._setComboData("storeDtlBillFg", [
    {"name": messages["dstmn.billFg0"], "value": "0"},
    {"name": messages["dstmn.billFg1"], "value": "1"}
  ]);

  // 세금계산서 일반, 과세/면세
  $scope._setComboData("storeDtlTaxFg", [
    {"name": messages["dstmn.taxFg0"], "value": "0"},
    {"name": messages["dstmn.taxFg1"], "value": "1"}
  ]);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

	// picker 사용시 호출 : 미사용시 호출안함
	$scope._makePickColumns("frnchsStoreDtlCtrl");
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

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

  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("frnchsStoreDtlCtrl", function (event, data) {

	$scope.slipNo			= data.slipNo;
    $scope.startDate 		= data.startDate;
    $scope.endDate			= data.endDate;
    $scope.prodClassCd    	= data.prodClassCd;
    $scope.storeCd  		= data.storeCd;
    $scope.prodCd    		= data.prodCd;
    $scope.prodNm 			= data.prodNm;
    $scope.orgnFg     		= data.orgnFg;

    $scope.frnchsStoreDtlLayer.show(true);

    $scope.searchFrnchsStoreInfoList();

    $scope.searchFrnchsStoreDtlList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매장별 입출고내역 상세 레이어- 매장별 입출고내역 매장상세 조회
  $scope.searchFrnchsStoreInfoList = function () {
	// 파라미터
    var params      	= {};
        params.storeCd  	= $scope.storeCd;
        params.startDate 	= $scope.startDate;
        params.endDate 		= $scope.endDate;
        params.hqOfficeCd 	= $("#storeHqOfficeCd").val();

	// ajax 통신 설정
	$http({
	  method : 'POST', //방식
	  url    : '/iostock/frnchs/store/store/frnchsStoreInfoList.sb', /* 통신할 URL */
	  params : params, /* 파라메터로 보낼 데이터 */
	  headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
	}).then(function successCallback(response) {
		if ($scope._httpStatusCheck(response, true)) {
	        if (!$.isEmptyObject(response.data.data.list)) {
	          var list       = response.data.data.list[0];
	          //$scope.startDate = list.startDate;
	          //$scope.endDate = list.endDate;
	          $scope.storeNm = list.storeNm;
	          $scope.storeCd = list.storeCd;
	          $scope.bizNo = list.bizNo;
	          $scope.bizStoreNm = list.bizStoreNm;
	          $scope.ownerNm = list.ownerNm;
	          $scope.postNo = list.postNo;
	          $scope.addr = list.addr;
	          $scope.bizType = list.bizType;
	          $scope.bizItem = list.bizItem;
	          $scope.email = list.email;
	          $scope.clsFgNm = list.clsFgNm; //업태
	        }
		}
	}, function errorCallback(response) {}
  )}

  // 매장별 입출고내역 상세 레이어- 매장별 입출고내역 상세 리스트 조회
  $scope.searchFrnchsStoreDtlList = function () {
    // 파라미터
    var params      	= {};
        params.storeCd  	= $scope.storeCd;
        params.startDate 	= $scope.startDate;
        params.endDate 		= $scope.endDate;
        params.slipNo    	= $scope.slipNo;
        params.hqOfficeCd 	= $("#storeHqOfficeCd").val();
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/store/store/frnchsStoreDtlList.sb", params);
  };

  //전체 or 수량합계 라디오 클릭이벤트
  $scope.totalOrNot = function() {
	 for(var item = 0; item < $scope.flex.columns.length; item++) {
		 var col = $scope.flex.columns[item];
		 if(col.binding === 'guestTot' || col.binding === 'guestCnt1') {
			$scope.flex.columns[item].visible = $scope.isCheckedSort == 1 ? true: false;
		 }
	 }
	 $scope.flex.refresh();
  };

  //리포트
  $scope.report = function (reportFg) {
    var strSlipNo = '';
    var strDlvrCd = '';
    if (!$scope.flex.collectionView) {
      $scope.flex.itemsSource = new wijmo.collections.CollectionView();
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      if (item.gChk === true) {
        //strSlipNo += (strSlipNo === '' ? '' : ',') + item.slipNo;
        if (nvl(item.dlvrCd, '') !== '' && strDlvrCd.indexOf(item.dlvrCd) < 0) {
          strDlvrCd += (strDlvrCd === '' ? '' : ',') + item.dlvrCd;
        }
      }
    }

//    if (strSlipNo === '') {
//      $scope._popMsg(messages['dstmn.require.slipNo']);
//      return false;
//    }

    var params       = {};
    params.slipFg    = $scope.slipFg;
    params.strSlipNo = $scope.slipNo;

    // 상품
    if (reportFg === 'prod') {
      $scope._broadcast('dstbProdReportCtrl', params);
    }
    // 상품-매장
    else if (reportFg === 'prodStore') {
      $scope._broadcast('dstbProdStoreReportCtrl', params);
    }
    // 매장-상품
    else if (reportFg === 'storeProd') {
      $scope._broadcast('dstbStoreProdReportCtrl', params);
    }
    // 기사별
    else if (reportFg === 'dlvr') {
      if (strDlvrCd === '') {
        $scope._popMsg(messages['dstmn.require.dlvr']);
        return false;
      }
      params.strDlvrCd = strDlvrCd;
      $scope._broadcast('dstbDlvrCtrl', params);
    }
    // 거래명세표
    else if (reportFg === 'trans') {
      params.stmtAcctFg = $scope.stmtAcctFg;
      $scope._broadcast('transReportCtrl', params);
    }
    // 세금계산서
    else if (reportFg === 'tax') {
      params.writtenDate = wijmo.Globalize.format(storeDtlWrittenDate.value, 'yyyyMMdd');
      params.billFg      = $scope.billFg;
      params.taxFg       = $scope.taxFg;
      $scope._broadcast('taxReportCtrl', params);
    }

  };

  //엑셀 다운로드
  $scope.excelDownloadClass = function () {
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
      }, '본사매장간입출고내역_매장별입출고내역_상품입출고현황'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };


}]);

