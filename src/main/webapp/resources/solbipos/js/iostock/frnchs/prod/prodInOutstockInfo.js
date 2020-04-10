/** 상품 입고현황 그리드 controller */
app.controller('prodInOutstockInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodInOutstockInfoCtrl', $scope, $http, true));

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
      outDt     	: messages["prodInOutstockInfo.outDate"],
      inDt   	  	: messages["prodInOutstockInfo.inDate"],
      storeNm  		: messages["prodInOutstockInfo.storeNm"],
      slipFgNm 		: messages["prodInOutstockInfo.slipFg"],
      slipKindNm  	: messages["prodInOutstockInfo.slipKind"],
      procFgNm		: messages["prodInOutstockInfo.procFg"],
      splyUprc      : messages["prodInOutstockInfo.mdSplyUprc"],
      outTotQty    	: messages["prodInOutstockInfo.out"],
      outAmt      	: messages["prodInOutstockInfo.out"],
      outVat 		: messages["prodInOutstockInfo.out"],
      outTot     	: messages["prodInOutstockInfo.out"],
      inTotQty    	: messages["prodInOutstockInfo.in"],
      inAmt     	: messages["prodInOutstockInfo.in"],
      inVat 		: messages["prodInOutstockInfo.in"],
      inTot     	: messages["prodInOutstockInfo.in"],
      penaltyAmt    : messages["prodInOutstockInfo.penaltyAmt"],
      remark    	: messages["prodInOutstockInfo.remark"],
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
  
  $scope.displayChg = function () {
	  var check   = $('input[name=displayFgPop]:checked').val();
	  var grid    = wijmo.Control.getControl("#prodInOutstockInfoGrid");
      var columns = grid.columns;
      var length  = grid.columns.length;
      
      if(check == 'all'){
    	  for(var i=0; i<length; i++){
    		  columns[i].visible = true;
          }
      }else if(check == 'cntSum'){
    	  for(var i=0; i<length; i++){
    		  if((columns[i].binding !== 'penaltyAmt' && columns[i].binding.substr(-3,3) == 'Amt')
    				  ||columns[i].binding.substr(-3,3) == 'Vat'){
    			  columns[i].visible = false;
    		  }
          }
      }
  }
  
  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodInOutstockInfoCtrl", function (event, data) {
    // 그리드 초기화
    var cv          = new wijmo.collections.CollectionView([]);
    cv.trackChanges = true;
    $scope.data     = cv;

    $scope.prodCd       = data.prodCd;
    $scope.prodNm       = data.prodNm;
    $scope.startDate    = data.startDate;
    $scope.endDate      = data.endDate;
    $scope.storeCd      = data.storeCd;
    
    $scope.wjProdInOutstockInfoLayer.show(true);
    
    var subTitleText = '&nbsp' + messages["prodInOutstockInfo.title"] + '&nbsp('
    					+ messages["prodInOutstockInfo.prodCd"] + ' : ' + $scope.prodCd + "&nbsp"
    					+ messages["prodInOutstockInfo.prodNm"] + ' : ' + $scope.prodNm  + ')';
    $('#subTitle').html(subTitleText);
    
    $scope.searchProdInOutstockInfoList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  
  // 상품 입고 리스트 조회
  $scope.searchProdInOutstockInfoList = function () {
    // 파라미터
    var params       = {};
    params.prodCd    = $scope.prodCd;
    params.prodNm    = $scope.prodNm;
    params.startDate = $scope.startDate;
    params.endDate   = $scope.endDate;
    params.storeCd   = $scope.storeCd;
    
    var storeCdChk = $scope.storeCd;

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params['sid'] = document.getElementsByName('sessionId')[0].value;
    }
        
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    // 매장 1개일 때 매장명 컬럼 보이지 않게 함
    if ((storeCdChk == "undefined" || storeCdChk == null || storeCdChk == "") || (storeCdChk.indexOf(',') !== -1)) {
    	$scope._inquirySub("/iostock/frnchs/prod/prodInOutstockInfo/list.sb", params);
    } else {
    	$scope._inquirySub("/iostock/frnchs/prod/prodInOutstockInfo/list.sb", params, $scope.invisibleColumns);
    }
    
  };

  //매장명 컬럼 보이지 않게 함
  $scope.invisibleColumns = function() {
	  var grid    = wijmo.Control.getControl("#prodInOutstockInfoGrid");
	  var columns = grid.columns;
	  var length  = grid.columns.length;
	  
	  for (var i=0; i<length; i++) {
		  if(columns[i].binding == "storeNm") {
			  columns[i].visible = false;
		  }
	  }
  }

  // 엑셀 다운로드
  $scope.excelDownloadProdInOutstockInfo = function () {
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
      }, '상품별입출고내역_상품입출고현황_'+getToday()+'.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  
}]);
