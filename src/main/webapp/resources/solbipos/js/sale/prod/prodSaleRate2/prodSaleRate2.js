/**
 * get application
 */
var app = agrid.getApp();

app.controller('prodSaleRate2Ctrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodSaleRate2Ctrl', $scope, $http, true));

  $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.srchEndDate    = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // 그리드 매출구분
  $scope.saleFgMap = new wijmo.grid.DataMap([
    {id: "1", name: messages["todayBillSaleDtl.saleY"]},
    {id: "-1", name: messages["todayBillSaleDtl.saleN"]}
  ], 'id', 'name');

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("hqBrandCd", hqBrandList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("prodSaleRate2Ctrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더3줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.startDate  = messages["prodSaleRate2.startDate"];
    dataItem.endDate    = messages["prodSaleRate2.endDate"];
    dataItem.pathNm     = messages["prodSaleRate2.prodClassNm"];
    dataItem.prodNm     = messages["prodSaleRate2.prodNm"];

    dataItem.stinQty    = messages["prodSaleRate2.sum"];
    dataItem.stinAmt    = messages["prodSaleRate2.sum"];
    dataItem.dlvrQty    = messages["prodSaleRate2.sum"];
    dataItem.dlvrAmt    = messages["prodSaleRate2.sum"];
    dataItem.packQty    = messages["prodSaleRate2.sum"];
    dataItem.packAmt    = messages["prodSaleRate2.sum"];

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem['stinQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.stin"];
      dataItem['stinAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.stin"];
      dataItem['dlvrQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.dlvr"];
      dataItem['dlvrAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.dlvr"];
      dataItem['packQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.pack"];
      dataItem['packAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.pack"];
    }

    s.columnHeaders.rows[0].dataItem = dataItem;

    // 둘째줄 헤더 생성
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    var dataItem1 = {};
    dataItem1.startDate = messages["prodSaleRate2.startDate"];
    dataItem1.endDate   = messages["prodSaleRate2.endDate"];
    dataItem1.pathNm    = messages["prodSaleRate2.prodClassNm"];
    dataItem1.prodNm    = messages["prodSaleRate2.prodNm"];

    dataItem1.stinQty   = messages["prodSaleRate2.stin"];
    dataItem1.stinAmt   = messages["prodSaleRate2.stin"];
    dataItem1.dlvrQty   = messages["prodSaleRate2.dlvr"];
    dataItem1.dlvrAmt   = messages["prodSaleRate2.dlvr"];
    dataItem1.packQty   = messages["prodSaleRate2.pack"];
    dataItem1.packAmt   = messages["prodSaleRate2.pack"];

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem1['stinQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['stinAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['dlvrQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['dlvrAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['packQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['packAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
    }

    s.columnHeaders.rows[1].dataItem = dataItem1;

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

        if ((panel.grid.columnHeaders.rows.length - 1) === r) {
          // 헤더의 전체선택 클릭 로직
          var flex   = panel.grid;
          var column = flex.columns[c];
          // check that this is a boolean column
          if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
            // prevent sorting on click
            column.allowSorting = false;
            // count true values to initialize checkbox
            var cnt             = 0;
            for (var i = 0; i < flex.rows.length; i++) {
              if (flex.getCellData(i, c) === true) {
                cnt++;
              }
            }
            // create and initialize checkbox
            if (column.format === 'checkBoxText') {
              cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                  + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
            } else {
              cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
            }
            var cb           = cell.firstChild;
            cb.checked       = cnt > 0;
            cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
            // apply checkbox value to cells
            cb.addEventListener('click', function (e) {
              flex.beginUpdate();
              for (var i = 0; i < flex.rows.length; i++) {
                if(!flex.rows[i].isReadOnly) {
                  flex.setCellData(i, c, cb.checked);
                }
              }
              flex.endUpdate();
            });
          }
        }
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
    // <-- //그리드 헤더3줄 -->
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodSaleRate2Ctrl", function (event, data) {
    $scope.searchprodSaleRate2List();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchprodSaleRate2List = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 7일 제한
    if (diffDay > 7) {
      $scope._popMsg(messages['cmm.dateOver.7day.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCds   = $("#prodSaleRate2StoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.hqBrandCd = $scope.hqBrandCd;
    params.dlvrInFgCol = dlvrInFgCol;
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/prodSaleRate2/prodSaleRate2/getProdSaleRate2List.sb", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prodSaleRate2StoreShow = function () {
    $scope._broadcast('prodSaleRate2StoreCtrl');
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
    // 조회일자 최대 7일 제한
    if (diffDay > 7) {
      $scope._popMsg(messages['cmm.dateOver.7day.error']);
      return false;
    }

    var params = {};
    params.storeCds   = $("#prodSaleRate2StoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodClassCd = $scope.prodClassCd;
    params.hqBrandCd = $scope.hqBrandCd;
    params.dlvrInFgCol = dlvrInFgCol;

    $scope._broadcast('prodSaleRate2ExcelCtrl',params);
  }
}]);


app.controller('prodSaleRate2ExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodSaleRate2ExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더3줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem = {};
    dataItem.startDate  = messages["prodSaleRate2.startDate"];
    dataItem.endDate    = messages["prodSaleRate2.endDate"];
    dataItem.pathNm     = messages["prodSaleRate2.prodClassNm"];
    dataItem.prodNm     = messages["prodSaleRate2.prodNm"];

    dataItem.stinQty    = messages["prodSaleRate2.stin"];
    dataItem.stinAmt    = messages["prodSaleRate2.stin"];
    dataItem.dlvrQty    = messages["prodSaleRate2.dlvr"];
    dataItem.dlvrAmt    = messages["prodSaleRate2.dlvr"];
    dataItem.packQty    = messages["prodSaleRate2.pack"];
    dataItem.packAmt    = messages["prodSaleRate2.pack"];

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem['stinQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.stin"];
      dataItem['stinAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.stin"];
      dataItem['dlvrQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.dlvr"];
      dataItem['dlvrAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.dlvr"];
      dataItem['packQty' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.pack"];
      dataItem['packAmt' + arrDlvrInFgCol[i]] = messages["prodSaleRate2.pack"];
    }

    s.columnHeaders.rows[0].dataItem = dataItem;

    //둘째줄 헤더
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    var dataItem1 = {};
    dataItem1.startDate = messages["prodSaleRate2.startDate"];
    dataItem1.endDate   = messages["prodSaleRate2.endDate"];
    dataItem1.pathNm    = messages["prodSaleRate2.prodClassNm"];
    dataItem1.prodNm    = messages["prodSaleRate2.prodNm"];

    dataItem1.stinQty   = messages["prodSaleRate2.stin"];
    dataItem1.stinAmt   = messages["prodSaleRate2.stin"];
    dataItem1.dlvrQty   = messages["prodSaleRate2.dlvr"];
    dataItem1.dlvrAmt   = messages["prodSaleRate2.dlvr"];
    dataItem1.packQty   = messages["prodSaleRate2.pack"];
    dataItem1.packAmt   = messages["prodSaleRate2.pack"];

    // 주문채널별 header 셋팅
    for (var i = 0; i < arrDlvrInFgCol.length; i++) {
      dataItem1['stinQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['stinAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['dlvrQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['dlvrAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['packQty' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
      dataItem1['packAmt' + arrDlvrInFgCol[i]] = dlvrInFgColList[i].dlvrInFgNm;
    }

    s.columnHeaders.rows[1].dataItem = dataItem1;

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

        if ((panel.grid.columnHeaders.rows.length - 1) === r) {
          // 헤더의 전체선택 클릭 로직
          var flex   = panel.grid;
          var column = flex.columns[c];
          // check that this is a boolean column
          if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
            // prevent sorting on click
            column.allowSorting = false;
            // count true values to initialize checkbox
            var cnt             = 0;
            for (var i = 0; i < flex.rows.length; i++) {
              if (flex.getCellData(i, c) === true) {
                cnt++;
              }
            }
            // create and initialize checkbox
            if (column.format === 'checkBoxText') {
              cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                  + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
            } else {
              cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
            }
            var cb           = cell.firstChild;
            cb.checked       = cnt > 0;
            cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
            // apply checkbox value to cells
            cb.addEventListener('click', function (e) {
              flex.beginUpdate();
              for (var i = 0; i < flex.rows.length; i++) {
                if(!flex.rows[i].isReadOnly) {
                  flex.setCellData(i, c, cb.checked);
                }
              }
              flex.endUpdate();
            });
          }
        }
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
    // <-- //그리드 헤더3줄 -->

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("prodSaleRate2ExcelCtrl", function (event, data) {
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
    params.prodCd = data.prodCd;
    params.prodNm = data.prodNm;
    params.prodClassCd = data.prodClassCd;
    params.hqBrandCd = data.hqBrandCd;
    params.dlvrInFgCol = data.dlvrInFgCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/prodSaleRate2/prodSaleRate2/getProdSaleRate2ExcelList.sb", params, function() {
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
        }, messages["prodSaleRate2Moms.prodSaleRate2Moms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);
