/**
 * get application
 */
var app = agrid.getApp();

/** 현금영수증 승인 controller */
app.controller('periodProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('periodProdCtrl', $scope, $http, true));

  $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate    = wcombo.genDateVal("#srchEndDate", gvEndDate);
  $scope.compStartDate  = wcombo.genDateVal("#compStartDate", gvStartDate);
  $scope.compEndDate    = wcombo.genDateVal("#compEndDate", gvEndDate);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("hqBrandCd", hqBrandList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("periodProdCtrl");

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    //첫째줄 Header 생성
    var dataItem = {};
    dataItem.prodNm           = messages["periodProd.prodNm"];
    dataItem.totSaleQty	      = messages["periodProd.srchDate"];
    dataItem.totSaleAmt	      = messages["periodProd.srchDate"];
    dataItem.realSaleAmt      = messages["periodProd.srchDate"];
    dataItem.rate	   	      = messages["periodProd.srchDate"];
    dataItem.totSaleQty1	  = messages["periodProd.compDate"];
    dataItem.totSaleAmt1	  = messages["periodProd.compDate"];
    dataItem.realSaleAmt1     = messages["periodProd.compDate"];
    dataItem.rate1	   	      = messages["periodProd.compDate"];
    dataItem.rateSaleQty      = messages["periodProd.ratePer"];
    dataItem.rateSaleAmt      = messages["periodProd.ratePer"];
    dataItem.rateRealSaleAmt  = messages["periodProd.ratePer"];
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------

    s.itemFormatter = function (panel, r, c, cell) {
      if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {		//align in center horizontally and vertically
        panel.rows   [r].allowMerging	= true;
        panel.columns[c].allowMerging	= true;

        wijmo.setCss(cell, {
              display    : 'table',
              tableLayout: 'fixed'
            }
        );

        cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';

        wijmo.setCss(cell.children[0], {
              display      : 'table-cell',
              verticalAlign: 'middle',
              textAlign    : 'center'
            }
        );
      }
      else if (panel.cellType === wijmo.grid.CellType.RowHeader) {	//Row헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        if (panel.rows[r] instanceof wijmo.grid.GroupRow) {			//GroupRow 인 경우는 표시하지 않음
          cell.textContent = '';
        } else {
          if (!isEmpty(panel._rows[r]._data.rnum)) {
            cell.textContent = (panel._rows[r]._data.rnum).toString();
          } else {
            cell.textContent = (r + 1).toString();
          }
        }
      }
      else if (panel.cellType === wijmo.grid.CellType.Cell) {			//readOnly 배경색 표시
        var col = panel.columns[c];
        if (col.isReadOnly) {
          wijmo.addClass(cell, 'wj-custom-readonly');
        }
      }
    }	//s.itemFormatter = function (panel, r, c, cell) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("periodProdCtrl", function (event, data) {
    $scope.searchCashBillList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchCashBillList = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (diffDay > 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    var compStartDt = new Date(wijmo.Globalize.format($scope.compStartDate.value, 'yyyy-MM-dd'));
    var compEndDt = new Date(wijmo.Globalize.format($scope.compEndDate.value, 'yyyy-MM-dd'));
    var compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(compStartDt.getTime() > compEndDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (compDiffDay > 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }
    
    // 파라미터
    var params       = {};
    params.storeCds   = $("#periodProdStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.compStartDate = wijmo.Globalize.format($scope.compStartDate.value, 'yyyyMMdd');
    params.compEndDate = wijmo.Globalize.format($scope.compEndDate.value, 'yyyyMMdd');
    params.hqBrandCd = $scope.hqBrandCd;
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/periodProd/periodProd/getPeriodProdList.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.periodProdStoreShow = function () {
    $scope._broadcast('periodProdStoreCtrl');
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.periodProdStoreShow = function () {
    $scope._broadcast('periodProdStoreCtrl');
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
              $scope.prodClassNm = response.data.data;
            }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdClass = function(){
    $scope.prodClassCd = "";
    $scope.prodClassNm = "";
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (diffDay > 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    var compStartDt = new Date(wijmo.Globalize.format($scope.compStartDate.value, 'yyyy-MM-dd'));
    var compEndDt = new Date(wijmo.Globalize.format($scope.compEndDate.value, 'yyyy-MM-dd'));
    var compDiffDay = (compEndDt.getTime() - compStartDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(compStartDt.getTime() > compEndDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1달(31일) 제한
    if (compDiffDay > 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    var params = {};
    params.storeCds   = $("#periodProdStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.compStartDate = wijmo.Globalize.format($scope.compStartDate.value, 'yyyyMMdd');
    params.compEndDate = wijmo.Globalize.format($scope.compEndDate.value, 'yyyyMMdd');
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodClassCd = $scope.prodClassCd;
    params.hqBrandCd = $scope.hqBrandCd;

    $scope._broadcast('periodProdExcelCtrl',params);
  }
}]);


app.controller('periodProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('periodProdExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "yoil") {
          if(item.yoil === "토") {
            wijmo.addClass(e.cell, 'blue');
          } else if(item.yoil === "일") {
            wijmo.addClass(e.cell, 'red');
          }
        }
      }
    });

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("periodProdExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (data) {
    // 파라미터
    var params       = {};
    params.storeCds = data.storeCds;
    params.startDate = data.startDate;
    params.endDate = data.endDate;
    params.compStartDate = data.compStartDate;
    params.compEndDate = data.compEndDate;
    params.prodCd = data.prodCd;
    params.prodNm = data.prodNm;
    params.prodClassCd = data.prodClassCd;
    params.hqBrandCd = data.hqBrandCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/periodProd/periodProd/getPeriodProdExcelList.sb", params, function() {
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
        }, messages["periodProdMoms.periodProdMoms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);
