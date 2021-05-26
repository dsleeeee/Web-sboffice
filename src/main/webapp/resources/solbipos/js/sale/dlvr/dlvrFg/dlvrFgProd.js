/**
 * get application
 */
var app = agrid.getApp();

app.controller('dlvrFgProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrFgProdCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  $scope.startDate = wcombo.genDateVal("#startDate", gvStartDate);
  $scope.endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // 콤보박스 데이터
  $scope._setComboData("packFg", packFg);

  if(hqOfficeCd === 'A0001'){ //배달경로(상품별-배달포장구분 hq/ms 113)
    $scope._setComboData("dlvrInFg", comboFgData);
  } else {// 배달경로(상품별-배달포장구분 CM 112)
    $scope._setComboData("dlvrInFg", dlvrInFg);
    dlvrInFg.unshift({name: "CID", value: "1"});
    dlvrInFg.unshift({name: "일반", value: "0"});
    dlvrInFg.unshift({name: "전체", value: ""});
  };

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // <-- 검색 호출 -->
  $scope.$on("dlvrFgProdCtrl", function(event, data) {
    if(!$scope.valueCheck()) {
      return false;
    }

    $scope._broadcast('dlvrFgProdOrderFgCtrl');
    $scope._broadcast('dlvrFgProdProdCtrl');
    $scope._broadcast('dlvrFgProdPProdCtrl');
    $scope._broadcast('dlvrFgProdProdDtlCtrl');
    event.preventDefault();
  });

  // 검색 기간 체크
  $scope.valueCheck = function () {
    // 최대 3달까지 선택가능합니다.
    var msg = messages['dlvrFg.dateError'];
    var date1 = new Date(wijmo.Globalize.format($scope.startDate.value, 'yyyy-MM-dd'));
    var date2 = new Date(wijmo.Globalize.format($scope.endDate.value, 'yyyy-MM-dd'));

    var diffDay = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
    if (diffDay > 93) {
      $scope._popMsg(msg);
      return false;
    }
    return true;
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
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.dlvrFgProdStoreShow = function () {
    $scope._broadcast('dlvrFgProdStoreCtrl');
  };
}]);

/** 유형별 그리드 controller */
app.controller('dlvrFgProdOrderFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrFgProdOrderFgCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.packFgDataMap = new wijmo.grid.DataMap(packFg, 'value', 'name');
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dlvrFgProdOrderFgCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem          = {};
    dataItem.dlvrPackFg   = messages["dlvrFg.dlvrFg"];
    dataItem.totSaleAmt   = messages["dlvrFg.sale"];
    dataItem.totDcAmt     = messages["dlvrFg.sale"];
    dataItem.realSaleAmt  = messages["dlvrFg.sale"];
    dataItem.totGaAmt     = messages["dlvrFg.sale"];
    dataItem.totVatAmt    = messages["dlvrFg.sale"];
    dataItem.totTipAmt    = messages["dlvrFg.totTipAmt"];
    dataItem.totEtcAmt    = messages["dlvrFg.totEtcAmt"];
    dataItem.totPayAmt    = messages["dlvrFg.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["dlvrFg.payMethod"];
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
  $scope.$on("dlvrFgProdOrderFgCtrl", function (event, data) {
    $scope.searchOrderFg();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 유형별 리스트 조회
  $scope.searchOrderFg = function () {
    var params          = {};
    params.startDate    = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate      = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.storeCd      = $("#dlvrFgProdStoreCd").val();
    params.prodCd       = $scope.prodCd;
    params.prodNm       = $scope.prodNm;
    params.prodClassCd  = $scope.prodClassCd;
    params.dlvrPackFg   = $scope.packFg;
    params.dlvrInFg     = $scope.dlvrInFg;
    params.payCol       = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/dlvr/dlvrFg/dlvrFgProd/getOrderFg.sb", params);
  };

  // 유형별 엑셀 다운로드
  $scope.excelDownloadOrderFg = function () {
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
          messages["dlvrFg.dlvrOrderFg"] + '(' + messages["dlvrFg.prod"] + '_' + messages["dlvrFg.prod.orderFg"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };
}]);

/** 상품별 그리드 controller */
app.controller('dlvrFgProdProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrFgProdProdCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.packFgDataMap = new wijmo.grid.DataMap(packFg, 'value', 'name');
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dlvrFgProdProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem          = {};
    dataItem.dlvrPackFg   = messages["dlvrFg.dlvrFg"];
    dataItem.prodCd       = messages["dlvrFg.prodCd"];
    dataItem.prodNm       = messages["dlvrFg.prodNm"];
    dataItem.barCd        = messages["dlvrFg.barCd"];
    dataItem.saleQty      = messages["dlvrFg.saleQty"];
    dataItem.totSaleAmt   = messages["dlvrFg.sale"];
    dataItem.totDcAmt     = messages["dlvrFg.sale"];
    dataItem.realSaleAmt  = messages["dlvrFg.sale"];
    dataItem.totGaAmt     = messages["dlvrFg.sale"];
    dataItem.totVatAmt    = messages["dlvrFg.sale"];
    dataItem.totTipAmt    = messages["dlvrFg.totTipAmt"];
    dataItem.totEtcAmt    = messages["dlvrFg.totEtcAmt"];
    dataItem.totPayAmt    = messages["dlvrFg.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["dlvrFg.payMethod"];
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
  $scope.$on("dlvrFgProdProdCtrl", function (event, data) {
    $scope.searchProd();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품별 리스트 조회
  $scope.searchProd = function () {
    var params          = {};
    params.startDate    = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate      = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.storeCd      = $("#dlvrFgProdStoreCd").val();
    params.prodCd       = $scope.prodCd;
    params.prodNm       = $scope.prodNm;
    params.prodClassCd  = $scope.prodClassCd;
    params.dlvrPackFg   = $scope.packFg;
    params.dlvrInFg     = $scope.dlvrInFg;
    params.payCol       = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/dlvr/dlvrFg/dlvrFgProd/getProd.sb", params);
  };

  // 상품별 엑셀 다운로드
  $scope.excelDownloadProd = function () {
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
          messages["dlvrFg.dlvrOrderFg"] + '(' + messages["dlvrFg.prod"] + '_' + messages["dlvrFg.prod.prod"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };
}]);

/** 상품별(상세) 그리드 controller */
app.controller('dlvrFgProdProdDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrFgProdProdDtlCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
// 그리드 DataMap 설정
    $scope.packFgDataMap = new wijmo.grid.DataMap(packFg, 'value', 'name');

    if(hqOfficeCd === 'A0001'){ //배달경로(상품별-배달포장구분 hq/ms 113)
      $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(comboFgData, 'value', 'name');
    } else {
      $scope.dlvrInFgDataMap = new wijmo.grid.DataMap(dlvrInFg, 'value', 'name');  // 배달경로(상품별-배달포장구분 CM 112)
    };

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("dlvrFgProdProdDtlCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // <-- 그리드 헤더2줄 -->
    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    // 첫째줄 헤더 생성
    var dataItem          = {};
    dataItem.dlvrPackFg   = messages["dlvrFg.dlvrFg"];
    dataItem.dlvrInFg     = messages["dlvrFg.dlvrInFg"];
    dataItem.prodCd       = messages["dlvrFg.prodCd"];
    dataItem.prodNm       = messages["dlvrFg.prodNm"];
    dataItem.barCd        = messages["dlvrFg.barCd"];
    dataItem.saleQty      = messages["dlvrFg.saleQty"];
    dataItem.totSaleAmt   = messages["dlvrFg.sale"];
    dataItem.totDcAmt     = messages["dlvrFg.sale"];
    dataItem.realSaleAmt  = messages["dlvrFg.sale"];
    dataItem.totGaAmt     = messages["dlvrFg.sale"];
    dataItem.totVatAmt    = messages["dlvrFg.sale"];
    dataItem.totTipAmt    = messages["dlvrFg.totTipAmt"];
    dataItem.totEtcAmt    = messages["dlvrFg.totEtcAmt"];
    dataItem.totPayAmt    = messages["dlvrFg.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["dlvrFg.payMethod"];
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
  $scope.$on("dlvrFgProdProdDtlCtrl", function (event, data) {
    $scope.searchProdDtl();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품별(상세) 리스트 조회
  $scope.searchProdDtl = function () {
    var params          = {};
    params.startDate    = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate      = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.storeCd      = $("#dlvrFgProdStoreCd").val();
    params.prodCd       = $scope.prodCd;
    params.prodNm       = $scope.prodNm;
    params.prodClassCd  = $scope.prodClassCd;
    params.dlvrPackFg   = $scope.packFg;
    params.dlvrInFg     = $scope.dlvrInFg;
    params.payCol       = payCol;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/dlvr/dlvrFg/dlvrFgProd/getProdDtl.sb", params);
  };

  // 상품별(상세) 엑셀 다운로드
  $scope.excelDownloadProdDtl = function () {
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
          messages["dlvrFg.dlvrOrderFg"] + '(' + messages["dlvrFg.prod"] + '_' + messages["dlvrFg.prod.prodDtl"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };
}]);