/**
 * get application
 */
var app = agrid.getApp();

/** 당일매출종합현황 그리드 controller */
app.controller('tableMonthCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('tableMonthCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

		// 테이블 리스트  콤보박스 데이터 받기
	    $scope.getStoreTableList();
		// 리스트 스케일 콤보박스 데이터 받기
		$scope._setComboData("listScaleBox", gvListScaleBoxData);
	    // picker 사용시 호출 : 미사용시 호출안함
	    $scope._makePickColumns("tableMonthCtrl");

	    // 그리드 링크 효과
	    s.formatItem.addHandler(function (s, e) {
	      if (e.panel === s.cells) {
	        var col = s.columns[e.col];
	        if (col.binding === "saleYn") { // 구분
	          var item = s.rows[e.row].dataItem;

	          // 구분이 반품이면 글씨색을 red 로 변경한다.
	          if (item.saleYn === 'Y') {
	            wijmo.addClass(e.cell, 'wijLink');
	            wijmo.addClass(e.cell, 'wj-custom-readonly');
	          } else if (item.saleYn === 'N') {
	            wijmo.addClass(e.cell, 'wijLink');
	            wijmo.addClass(e.cell, 'red');
	          }
	        }

	        if (col.format === "date") {
	          e.cell.innerHTML = getFormatDate(e.cell.innerText);
	        } else if (col.format === "dateTime") {
	          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
	        }
	      }
	    });

	    // 그리드 클릭 이벤트
	    s.addEventListener(s.hostElement, 'mousedown', function (e) {
	      var ht = s.hitTest(e);
	      if (ht.cellType === wijmo.grid.CellType.Cell) {
	        var col         = ht.panel.columns[ht.col];
	        var selectedRow = s.rows[ht.row].dataItem;
	        if (col.binding === "saleYn") { // 전표번호
	          var params       = {};
	          params.saleYn    = selectedRow.saleYn;
	          params.startDate = $scope.searchedStartDate;
	          params.storeCd   = $scope.searchedStoreCd;
	          params.posNo     = $scope.searchedPosNo;
	          $scope._broadcast('tableMonthDetailCtrl', params);
	        }
	      }
	    });

	    // add the new GroupRow to the grid's 'columnFooters' panel
	    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
	    // add a sigma to the header to show that this is a summary row
	    s.bottomLeftCells.setCellData(0, 0, '합계');

	    // 헤더머지
	    s.allowMerging = 2;
	    s.columnHeaders.rows.push(new wijmo.grid.Row());

	    // 첫째줄 헤더 생성
	    var dataItem         = {};
	    dataItem.saleYm    = messages["tableMonth.saleYm"];
	    dataItem.totRealSaleAmt    = messages["tableDay.totRealSaleAmt"];
	    dataItem.totRealSaleCnt  = messages["tableDay.totRealSaleCnt"];
	    dataItem.totGuestCnt    = messages["tableDay.totGuestCnt"];

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
	  };


	  // 다른 컨트롤러의 broadcast 받기
	  $scope.$on("tableMonthCtrl", function (event, data) {
		 
	    $scope.searchTableDayList();
	    // 기능수행 종료 : 반드시 추가
	    event.preventDefault();
	  });


	  // 테이블별 월별 리스트 조회
	  $scope.searchTableDayList = function () {
	    if ($("#tableMonthSelectStoreCd").val() === '') {
	      $scope._popMsg(messages["todayDtl.require.selectStore"]); // 매장을 선택해주세요.
	      return false;
	    }
	    
	 // 파라미터
		var params       = {};
		params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
		params.endDate = wijmo.Globalize.format($scope.endDate, 'yyyyMMdd');
		params.storeCd   = $("#tableMonthSelectStoreCd").val();
		params.tblCd = $scope.isChecked? "" : $scope.tableNo;
		params.listScale = $scope.listScale;
		
		if(params.startDate > params.endDate){
	   	 	$scope._popMsg(messages["prodsale.dateChk"]); // 조회종료일자가 조회시작일자보다 빠릅니다.
	   	 	return false;
	    }

		$scope.arrTblCol = [];

		$http({
			method: 'POST',
			url: '/sale/status/table/month/list.sb',
			params : params, /* 파라메터로 보낼 데이터 */
		    headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
		}).then(function success(response){
			$scope.arrTblCol = response.data.data.page.arrTblCol;

			var grid = wijmo.Control.getControl("#tableMonthGrid");
			
			if($scope.arrTblCol != "" || $scope.arrTblCol != null){
				
				while(grid.columns.length > 4){
					grid.columns.removeAt(grid.columns.length-1);
				}
				
				for(var i = 0; i < $scope.arrTblCol.length; i++){
			    	grid.columns.setAt(4+(i*3), new wijmo.grid.Column({header: messages["tableMonth.realSaleAmt"], binding: 'realSaleAmtT'+i, width: 80, align: 'right', isReadOnly: 'true', aggregate: 'Sum'}));
			        grid.columns.setAt(5+(i*3), new wijmo.grid.Column({header: messages["tableMonth.realSaleCnt"], binding: 'realSaleCntT'+i, width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
			        grid.columns.setAt(6+(i*3), new wijmo.grid.Column({header: messages["tableMonth.guestCnt"], binding: 'guestCnt1T'+i, width: 80, align: 'center', isReadOnly: 'true', aggregate: 'Sum'}));
			        grid.columnHeaders.setCellData(0, 4+(i*3), $scope.arrTblCol[i]);
			        grid.columnHeaders.setCellData(0, 5+(i*3), $scope.arrTblCol[i]);
			        grid.columnHeaders.setCellData(0, 6+(i*3), $scope.arrTblCol[i]);
			    }
			}

			// 그리드 링크 효과
			grid.formatItem.addHandler(function (s, e) {
				if (e.panel === s.cells) {
					var col = s.columns[e.col];
					if (col.binding.substring(0, 11) === "realSaleAmt") { // 실매출
						wijmo.addClass(e.cell, 'wijLink');
					}
				}
			});
			
			// 그리드 클릭 이벤트
			grid.addEventListener(grid.hostElement, 'mousedown', function (e) {
			   	var ht = grid.hitTest(e);
			   	if (ht.cellType === wijmo.grid.CellType.Cell) {
			   		var col         = ht.panel.columns[ht.col];
			   		var selectedRow = grid.rows[ht.row].dataItem;
			   		var params       = {};
			   		params.tblCd   = $scope.arrTblCol[Math.floor(ht.col/3) - 1];
			   		params.storeCd   = $("#tableMonthSelectStoreCd").val();
			   		params.startDate = wijmo.Globalize.format($scope.startDate, 'yyyyMMdd');
		    		params.endDate = getLastDate($scope.endDate);
		    		
			    		
			   		if (col.binding.substring(0, 11) === "realSaleAmt") { //실매출 클릭
			   			$scope._broadcast('saleComTableCtrl', params);
			   		}
			   	}
			});	
		}); // end then();
		
	    // 조회 수행 : 조회URL, 파라미터, 콜백함수
	    $scope._inquirySub("/sale/status/table/month/list.sb", params);
		$scope.flex.refresh();
	  };

	  // 매장선택 모듈 팝업 사용시 정의
	  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
	  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
	  $scope.tableMonthSelectStoreShow = function () {
	    $scope._broadcast('tableMonthSelectStoreCtrl');
	  };

	  //전체 테이블 체크박스 클릭이벤트
	  $scope.isChkDt = function() {
		  $scope.tableNo = wijmo.Control.getControl("#srchTableMonthTableNo");
		  $scope.tableNo.isReadOnly = $scope.isChecked;
	  };

	  // 매장의 테이블 리스트 조회
	  $scope.getStoreTableList = function () {
	    var url             = '/sale/status/table/day/tableNmList.sb';
	    var comboParams     = {};
	    comboParams.storeCd = $("#tableMonthSelectStoreCd").val();
	    // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
	    $scope._queryCombo("combo", "srchTableMonthTableNo", null, url, comboParams, "A", null); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
	  };


	  // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
	  // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
	  // comboId : combo 생성할 ID
	  // gridMapId : grid 에서 사용할 Map ID
	  // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
	  // params : 데이터 조회할 url에 보낼 파라미터
	  // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
	  // callback : queryCombo 후 callback 할 함수
	  $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
	    var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
	    if (url) {
	      comboUrl = url;
	    }

	    // ajax 통신 설정
	    $http({
	      method : 'POST', //방식
	      url    : comboUrl, /* 통신할 URL */
	      params : params, /* 파라메터로 보낼 데이터 */
	      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
	    }).then(function successCallback(response) {
	      if ($scope._httpStatusCheck(response, true)) {
	        if (!$.isEmptyObject(response.data.data.list)) {
	          var list       = response.data.data.list;
	          var comboArray = [];
	          var comboData  = {};

	          if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
	            comboArray = [];
	            if (option === "A") {
	              comboData.name  = messages["cmm.all"];
	              comboData.value = "";
	              comboArray.push(comboData);
	            } else if (option === "S") {
	              comboData.name  = messages["cmm.select"];
	              comboData.value = "";
	              comboArray.push(comboData);
	            }

	            for (var i = 0; i < list.length; i++) {
	              comboData       = {};
	              comboData.name  = list[i].nmcodeNm;
	              comboData.value = list[i].nmcodeCd;
	              comboArray.push(comboData);
	            }
	            $scope._setComboData(comboId, comboArray);
	          }

	          if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
	            comboArray = [];
	            for (var i = 0; i < list.length; i++) {
	              comboData      = {};
	              comboData.id   = list[i].nmcodeCd;
	              comboData.name = list[i].nmcodeNm;
	              comboArray.push(comboData);
	            }
	            $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
	          }
	        }
	      }
	    }, function errorCallback(response) {
	      $scope._popMsg(messages["cmm.error"]);
	      return false;
	    }).then(function () {
	      if (typeof callback === 'function') {
	        $timeout(function () {
	          callback();
	        }, 10);
	      }
	    });
	  };
	  
	  var getLastDate = function(item) {
		  var year = wijmo.Globalize.format(item, 'yyyy');
		  var month = wijmo.Globalize.format(item, 'MM');
		  var date = new Date(year, month, 0).getDate();
		  return year+month+date;
	  }
	  
	//엑셀 다운로드
	  $scope.excelDownloadTableMonth = function () {
	    if ($scope.flex.rows.length <= 0) {
	      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
	      return false;
	    }

	    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
	    $timeout(function () {
	      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
	        includeColumnHeaders: true,
	        includeCellStyles   : false,
	        includeColumns      : function (column) {
	          return column.visible;
	        }
	      }, 'tableMonth.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };
	  
	}]);

