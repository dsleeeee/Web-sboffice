/****************************************************************
 *
 * 파일명 : salePriceHistory.js
 * 설  명 : 판매가변경이력 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.29     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var procFgData = [
    {"name": "입력", "value": "I"},
    {"name": "수정", "value": "U"},
    {"name": "삭제", "value": "D"}
];
var app = agrid.getApp();

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('salePriceHistoryCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('salePriceHistoryCtrl', $scope, $http, false));

  //페이지 스케일 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // 등록일자 셋팅
  $scope.startDate = wcombo.genDateVal("#startDate", gvStartDate);
  $scope.endDate   = wcombo.genDateVal("#endDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
      // 그리드 DataMap 설정
      $scope.procFgDataMap = new wijmo.grid.DataMap(procFgData, 'value', 'name'); // 열람구분

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      // 첫째줄 헤더 생성
      var dataItem              = {};
      dataItem.prodCd           = messages["salePriceHistory.prodCd"];
      dataItem.prodNm           = messages["salePriceHistory.prodNm"];
      dataItem.bSaleUprc        = messages["salePriceHistory.saleUprc"];
      dataItem.saleUprc         = messages["salePriceHistory.saleUprc"];
      dataItem.startDate        = messages["salePriceHistory.saleUprc"];
      dataItem.endDate          = messages["salePriceHistory.saleUprc"];
      dataItem.bStinSaleUprc    = messages["salePriceHistory.stinSaleUprc"];
      dataItem.stinSaleUprc     = messages["salePriceHistory.stinSaleUprc"];
      dataItem.bDlvrSaleUprc    = messages["salePriceHistory.dlvrSaleUprc"];
      dataItem.dlvrSaleUprc     = messages["salePriceHistory.dlvrSaleUprc"];
      dataItem.bPackSaleUprc    = messages["salePriceHistory.packSaleUprc"];
      dataItem.packSaleUprc     = messages["salePriceHistory.packSaleUprc"];
      dataItem.procFg           = messages["salePriceHistory.procFg"];
      dataItem.procDt           = messages["salePriceHistory.procDt"];
      dataItem.modId            = messages["salePriceHistory.modId"];

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

  // <-- 검색 호출 -->
  // 상품정보관리 그리드 조회
  $scope.$on("salePriceHistoryCtrl", function(event, data) {
    $scope.searchSalePriceList();
    event.preventDefault();
  });

  // 판매가 그리드 조회
  $scope.searchSalePriceList = function(){
    var params = {};
	params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.listScale = $scope.listScale;;

    $scope._inquiryMain('/base/price/salePriceHistory/salePriceHistory/getSalePriceHistoryList.sb', params);
  };
  // <-- //검색 호출 -->

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer.show(true, function (s) {
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

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품분류 팝업 핸들러 추가
    $scope.prodClassPopUpLayer.shown.addHandler(function (s) {
    });
  });

    // 엑셀 다운로드
    $scope.excelDownload = function () {
        var params = {};
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
        params.endDate   = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');

        $scope._broadcast('excelCtrl',params);
    }
}]);


app.controller('excelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('excelCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
        // add the new GroupRow to the grid's 'columnFooters' panel
        s.columnFooters.rows.push(new wijmo.grid.GroupRow());
        // add a sigma to the header to show that this is a summary row
        s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("excelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 상품매출순위 리스트 조회
    $scope.searchExcelList = function (data) {
        // 파라미터
        var params       = {};
        params.prodClassCd = data.prodClassCd;
        params.prodCd = data.prodCd;
        params.prodNm = data.prodNm;
        params.startDate = data.startDate;
        params.endDate = data.endDate;

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/salePriceHistory/salePriceHistory/getSalePriceHistoryExcelList.sb", params, function() {
            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

            $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
            $timeout(function () {
                wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
                    includeColumnHeaders: true,
                    includeCellStyles   : true,
                    includeColumns      : function (column) {
                        return column.visible;
                    }
                }, messages["salePriceHistory.salePriceHistory"]+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);