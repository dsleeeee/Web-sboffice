/**
 * get application
 */
var app = agrid.getApp();

/** 상품별 입출고내역 그리드 controller */
app.controller('unusualCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('unusualCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchUnusualStartDate", getToday());
  $scope.srchEndDate   = wcombo.genDateVal("#srchUnusualEndDate", getToday());

  //조회조건 콤보박스
  $scope._setComboData('unusualMainListScaleBox', gvListScaleBoxData);
  $scope._setComboData('unusualDtlListScaleBox', gvListScaleBoxData);

  //조회조건 출고일자 데이터 Set
  $scope._setComboData("srchUnusualOutDateFgDisplay", [
    {"name": messages["unusualStockInfo.outDate"], "value": "1"}, // 출고일자
    {"name": messages["unusualStockInfo.inDate"], "value": "2"} // 입고일자
  ]);

  //조회조건 전표구분 데이터 Set
  $scope._setComboData("srchUnusualSlipFgDisplay", [
    {"name": messages["cmm.all"], "value": ""}, // 전체
    {"name": messages["unusualStockInfo.order"], "value": "1"}, // 주문
    {"name": messages["unusualStockInfo.orderReturn"], "value": "-1"}, // 반품
  ]);

  //매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.unusualSelectStoreShow = function () {
	  $scope._broadcast('unusualSelectStoreCtrl');
  };

  //상품분류정보 팝업
  $scope.popUpProdClass = function () {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd    = scope.getSelectedClass();
        var params         = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function (response) {
            $scope.prodClassCd   = prodClassCd;
            $scope.prodClassCdNm = response.data.data;
          }
        );
      }
    });
  };
}]);

/** 상품별 입출고내역 그리드 controller */
app.controller('unusualMainCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('unusualMainCtrl', $scope, $http, true));

  $scope.excelFg = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    var comboParams         = {};
    comboParams.nmcodeGrpCd = "097";
    var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
    // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
    $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("unusualMainCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        // 구분이 반출이면 글씨색을 red 로 변경한다.
        if (col.binding === "slipFgNm") {
          var item = s.rows[e.row].dataItem;
          if (item.slipFg === -1) {
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
        if (col.binding === "slipNo") { // 상품코드
          var params       = {};
          params.slipNo    = selectedRow.slipNo;
//          params.unusualCd = selectedRow.unusualCd;
//          params.unusualNm = selectedRow.unusualNm;
          params.startDate = $scope.searchedStartDate;
          params.endDate   = $scope.searchedEndDate;
          params.excelFg = true;
          $scope._broadcast('unusualDtlCtrlSrch', params);
          $('#dtlSlipNo').text('상품상세 (전표번호 : '+selectedRow.slipNo+')');
        }
      }
    });

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
  $scope.$on("unusualMainCtrl", function (event, data) {
    $scope.searchUnusualList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("unusualMainCtrlSrch", function (event, data) {
    $scope.searchUnusualList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 상품별 입출고내역 리스트 조회
  $scope.searchUnusualList = function (isPageChk) {
    $scope.searchedStartDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    $scope.searchedEndDate   = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');

    //상세그리드에 넘길 날짜 저장
    $scope.searchedStartDateForDtl = $scope.searchedStartDate;
    $scope.searchedEndDateForDtl   = $scope.searchedEndDate;

    // 파라미터
    var params       = {};
    params.startDate = $scope.searchedStartDate;
    params.endDate   = $scope.searchedEndDate;
    params.slipNo 	 = $scope.slipNoModel;
    params.slipFg	 = $scope.slipFgModel;
    params.outDateFg = $scope.outDateFgModel;
    params.storeCd	 = $("#unusualSelectStoreCd").val();
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;

    $scope.startDate = params.startDate;
    $scope.endDate   = params.endDate;
    $scope.searchedSlipNo = params.slipNo;
    $scope.searchedSlipFg = params.slipFg;
    $scope.searchedOutDateFg = params.outDateFg;
    $scope.storeCd = params.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/frnchs/unusual/unUsual/list.sb", params, function () {});

    //메인그리드 조회후 상세그리드 조회.
	$scope.loadedRows = function(sender, args){
        var rows = sender.rows;
        var params       = {};
        if(rows.length > 0){
        	params.slipNo    = rows[0].dataItem.slipNo;
        	params.startDate = $scope.searchedStartDateForDtl;
        	params.endDate   = $scope.searchedEndDateForDtl;
        	params.excelFg = true;
        	// 특이사항 임출고내역 상세조회.
            // $scope._broadcast("unusualDtlCtrlSrch", params);
            $('#dtlSlipNo').text('상품상세 (전표번호 : '+rows[0].dataItem.slipNo+')');
        }else{
        	// 주문대비 입출고현황 상세내역 그리드 초기화
        	$('#dtlSlipNo').text('');
        	params.slipNo = -1;
        	params.excelFg = false;
            var unusualDtlScope = agrid.getScope('unusualDtlCtrl');
            unusualDtlScope.dtlGridDefault();
        }

        $scope._broadcast("unusualDtlCtrlSrch", params);
    }

	$scope.excelFg = true;
  };


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.unusualSelectVendrShow = function () {
    $scope._broadcast('unusualSelectVendrCtrl');
  };
  // 엑셀 다운로드
  $scope.excelDownloadunusual = function () {
	// 파라미터
	var params = {};
	params.startDate 	= $scope.startDate;
    params.endDate   	= $scope.endDate;
    params.slipNo 		= $scope.searchedSlipNo;
    params.slipFg 		= $scope.searchedSlipFg;
    params.outDateFg 	= $scope.searchedOutDateFg;
    params.storeCd 		= $scope.storeCd;
  	params.excelFg   	= $scope.excelFg;

	$scope._broadcast('unusualExcelCtrl',params);
  };

  // 상품분류정보 팝업
  $scope.popUpUnusualClass = function () {
    var popUp = $scope.unusualClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('unusualClassPopUpCtrl');
        var unusualClassCd    = scope.getSelectedClass();
        var params         = {};
        params.unusualClassCd = unusualClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getUnusualClassCdNm.sb", params,
          function (response) {
            $scope.unusualClassCd   = unusualClassCd;
            $scope.unusualClassCdNm = response.data.data;
          }
        );
      }
    });
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

}]);


/** 상품별 입출고내역 엑셀리스트 controller */
app.controller('unusualExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('unusualExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "slipNo") { // 전표번호
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
        // 구분이 반출이면 글씨색을 red 로 변경한다.
        if (col.binding === "slipFgNm") {
          var item = s.rows[e.row].dataItem;
          if (item.slipFg === -1) {
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


  //다른 컨트롤러의 broadcast 받기
  $scope.$on("unusualExcelCtrl", function (event, data) {
	  if(data != undefined && data.excelFg) {
			if(data.startDate > data.endDate){
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			 	return false;
			}

			$scope.startDate 	= data.startDate;
		    $scope.endDate   	= data.endDate;
		    $scope.slipNo 		= data.slipNo;
		    $scope.slipFg 		= data.slipFg;
		    $scope.outDateFg 	= data.outDateFg;
		    $scope.storeCd 		= data.storeCd;
		    $scope.excelFg     = data.excelFg;

			$scope.searchUnusualList(false);
		}else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 상품별 입출고내역 리스트 조회
  $scope.searchUnusualList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.slipNo 	 = $scope.slipNo;
    params.slipFg	 = $scope.slipFg;
    params.outDateFg = $scope.outDateFg;
    params.storeCd	 = $scope.storeCd;
    params.isPageChk = isPageChk;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/frnchs/unusual/unUsual/excelList.sb", params, function() {
		var flex = $scope.excelFlex;
		//row수가 0이면
		if (flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, '본사-매장간 입출고현황_특이사항 입출고내역_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	});
  };


  // 거래처선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.unusualSelectVendrShow = function () {
    $scope._broadcast('unusualSelectVendrCtrl');
  };
  // 엑셀 다운로드
  $scope.excelDownloadunusual = function () {
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
	      }, '본사-매장간 입출고현황_특이사항 입출고내역_'+getToday()+'.xlsx', function () {
	        $timeout(function () {
	          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
	        }, 10);
	      });
	    }, 10);
	  };

  // 상품분류정보 팝업
  $scope.popUpUnusualClass = function () {
    var popUp = $scope.unusualClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope          = agrid.getScope('unusualClassPopUpCtrl');
        var unusualClassCd    = scope.getSelectedClass();
        var params         = {};
        params.unusualClassCd = unusualClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getUnusualClassCdNm.sb", params,
          function (response) {
            $scope.unusualClassCd   = unusualClassCd;
            $scope.unusualClassCdNm = response.data.data;
          }
        );
      }
    });
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

}]);


/** 전표별 입출고내역 상세 그리드 controller */
app.controller('unusualDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('unusualDtlCtrl', $scope, $http, true));

  $scope.excelFg = false;

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("unusualDtlCtrl");

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

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

  	  if (ht.panel == s.columnHeaders && !ht.edgeRight && !e['dataTransfer']) {
  	  	var rng = s.getMergedRange(ht.panel, ht.row, ht.col);
  	  	if (rng && rng.columnSpan > 1) {
  	  		e.preventDefault();
  	  	}
  	  }
    }, true);

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    s.columnHeaders.rows[0].dataItem = {
      prodCd    : messages["slipStockInfo.dtl.prodCd"],
      prodNm    : messages["slipStockInfo.dtl.prodNm"],
      poUnitFgNm: messages["slipStockInfo.dtl.poUnitFg"],
      poUnitQty : messages["slipStockInfo.dtl.poUnitQty"],
      splyUprc  : messages["slipStockInfo.dtl.costUprc"],
      mdTotQty  : messages["slipStockInfo.dtl.dstb"],
      mdAmt     : messages["slipStockInfo.dtl.dstb"],
      mdVat     : messages["slipStockInfo.dtl.dstb"],
      mdTot     : messages["slipStockInfo.dtl.dstb"],
      outTotQty : messages["slipStockInfo.dtl.out"],
      outAmt    : messages["slipStockInfo.dtl.out"],
      outVat    : messages["slipStockInfo.dtl.out"],
      outTot    : messages["slipStockInfo.dtl.out"],
      inTotQty  : messages["slipStockInfo.dtl.in"],
      inAmt     : messages["slipStockInfo.dtl.in"],
      inVat     : messages["slipStockInfo.dtl.in"],
      inTot     : messages["slipStockInfo.dtl.in"],
      penaltyAmt: messages["slipStockInfo.dtl.penaltyAmt"],
      remark    : messages["slipStockInfo.dtl.remark"],
    };

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
  $scope.$on("unusualDtlCtrl", function (event, data) {
    $scope.searchSlipDtlList(true);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  //다른 컨트롤러의 broadcast 받기
  $scope.$on("unusualDtlCtrlSrch", function (event, data) {
    $scope.slipNo    = data.slipNo;
    $scope.startDate = data.startDate;
    $scope.endDate   = data.endDate;

    $scope.searchSlipDtlList(false);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.displayChg = function () {
	  var check = $('input[name=displayFg]:checked').val();
	  var grid = wijmo.Control.getControl("#unusualDtlGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;

      if(check == 'all'){
    	  for(var i=0; i<length; i++){
    		  if(columns[i].binding != 'poUnitFg'){
    			  columns[i].visible = true;
    		  }
          }
      }else if(check == 'cntSum'){
    	  for(var i=0; i<length; i++){
    		  var colLength = columns[i].binding.length;
    		  if(columns[i].binding != 'penaltyAmt'){
	    		  if(columns[i].binding.substring(colLength,colLength-3) == 'Amt'||columns[i].binding.substring(colLength,colLength-3) == 'Vat'){
	    			  columns[i].visible = false;
	    		  }
    		  }
          }
      }
  }

  // 전표별 입출고내역 상세 리스트 조회
  $scope.searchSlipDtlList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.slipNo    = $scope.slipNo;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.listScale = $scope.conListScale.text; //-페이지 스케일 갯수
    params.isPageChk = isPageChk;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/slip/ioStockDtl/list.sb", params);

    $scope.excelFg = true;
  };


  // 상세 그리드 초기화
  $scope.dtlGridDefault = function () {
    $timeout(function () {
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;
      $scope.flex.refresh();
    }, 10);
  };

  //엑셀 다운로드
  $scope.excelDownloadUnusualDtl = function () {
	  // 파라미터
	  var params = {};
	  params.checked   = $('input[name=displayFg]:checked').val();
	  params.slipNo    = $scope.slipNo;
	  params.startDate = $scope.startDate;
	  params.endDate   = $scope.endDate;
	  params.excelFg   = $scope.excelFg;

	  $scope._broadcast('unusualDtlExcelCtrl',params);
  };

}]);


/** 전표별 입출고내역 상세 그리드 controller */
app.controller('unusualDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('unusualDtlExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];

        if (col.format === "date") {
          e.cell.innerHTML = getFormatDate(e.cell.innerText);
        } else if (col.format === "dateTime") {
          e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
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
    s.columnHeaders.rows[0].dataItem = {
      prodCd    : messages["slipStockInfo.dtl.prodCd"],
      prodNm    : messages["slipStockInfo.dtl.prodNm"],
      poUnitFgNm: messages["slipStockInfo.dtl.poUnitFg"],
      poUnitQty : messages["slipStockInfo.dtl.poUnitQty"],
      splyUprc  : messages["slipStockInfo.dtl.costUprc"],
      mdTotQty  : messages["slipStockInfo.dtl.dstb"],
      mdAmt     : messages["slipStockInfo.dtl.dstb"],
      mdVat     : messages["slipStockInfo.dtl.dstb"],
      mdTot     : messages["slipStockInfo.dtl.dstb"],
      outTotQty : messages["slipStockInfo.dtl.out"],
      outAmt    : messages["slipStockInfo.dtl.out"],
      outVat    : messages["slipStockInfo.dtl.out"],
      outTot    : messages["slipStockInfo.dtl.out"],
      inTotQty  : messages["slipStockInfo.dtl.in"],
      inAmt     : messages["slipStockInfo.dtl.in"],
      inVat     : messages["slipStockInfo.dtl.in"],
      inTot     : messages["slipStockInfo.dtl.in"],
      penaltyAmt: messages["slipStockInfo.dtl.penaltyAmt"],
      remark    : messages["slipStockInfo.dtl.remark"],
    };

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



  //다른 컨트롤러의 broadcast 받기
  $scope.$on("unusualDtlExcelCtrl", function (event, data) {
	  if(data != undefined && data.excelFg) {
			if(data.startDate > data.endDate){
				$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			 	return false;
			}

			$scope.slipNo    = data.slipNo;
			$scope.checked   = data.checked;
		    $scope.startDate = data.startDate;
		    $scope.endDate   = data.endDate;
		    $scope.excelFg   = data.excelFg;

		    $scope.searchSlipDtlList(false);
		}else{
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.displayChg = function () {
	  var check = $scope.checked;
	  var grid = wijmo.Control.getControl("#unusualDtlExcelGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;

      if(check == 'all'){
    	  for(var i=0; i<length; i++){
    		  if(columns[i].binding != 'poUnitFg'){
    			  columns[i].visible = true;
    		  }
          }
      }else if(check == 'cntSum'){
    	  for(var i=0; i<length; i++){
    		  var colLength = columns[i].binding.length;
    		  if(columns[i].binding != 'penaltyAmt'){
	    		  if(columns[i].binding.substring(colLength,colLength-3) == 'Amt'||columns[i].binding.substring(colLength,colLength-3) == 'Vat'){
	    			  columns[i].visible = false;
	    		  }
    		  }
          }
      }
  }

  // 전표별 입출고내역 상세 리스트 조회
  $scope.searchSlipDtlList = function (isPageChk) {
    // 파라미터
    var params       = {};
    params.slipNo    = $scope.slipNo;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.isPageChk = isPageChk;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/frnchs/slip/ioStockDtl/excelList.sb", params, function() {

		var flex = $scope.excelFlex;
		//row수가 0이면
		if (flex.rows.length <= 0) {
			$scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
			return false;
		}

		$scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
		$timeout(function () {
			wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(flex, {
				includeColumnHeaders: true,
				includeCellStyles   : true,
				includeColumns      : function (column) {
					return column.visible;
				}
			}, '본사-매장간 입출고현황_특이사항 입출고내역 상세_'+getToday()+'.xlsx', function () {
				$timeout(function () {
					$scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
				}, 10);
			});
		}, 10);
	});
    $scope.displayChg();
  };
}]);


