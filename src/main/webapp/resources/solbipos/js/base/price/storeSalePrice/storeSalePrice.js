/****************************************************************
 *
 * 파일명 : storeSalePrice.js
 * 설  명 : 매장판매가현황 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.09.10     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 상품별 판매가관리 그리드 생성
 */
app.controller('storeSalePriceCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeSalePriceCtrl', $scope, $http, false));

  $scope._setComboData("srchProdHqBrandCd", userHqBrandCdComboList); // 상품브랜// 상품브랜드

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.prcCtrlFgDataMap = new wijmo.grid.DataMap(prcCtrlFgData, 'value', 'name'); // 가격관리구분

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      // 첫째줄 헤더 생성
      var dataItem                  = {};
      dataItem.prodCd               = messages["salePrice.prodCd"];
      dataItem.prodNm               = messages["salePrice.prodNm"];
      dataItem.storeCd              = messages["salePrice.storeCd"];
      dataItem.storeNm              = messages["salePrice.storeNm"];
      dataItem.hqSaleUprc           = messages["salePrice.salePrice"];
      dataItem.saleUprc             = messages["salePrice.salePrice"];
      dataItem.hqStinSaleUprc       = messages["salePrice.stinSaleUprc"];
      dataItem.stinSaleUprc         = messages["salePrice.stinSaleUprc"];
      dataItem.hqDlvrSaleUprc       = messages["salePrice.dlvrSaleUprc"];
      dataItem.dlvrSaleUprc         = messages["salePrice.dlvrSaleUprc"];
      dataItem.hqPackSaleUprc       = messages["salePrice.packSaleUprc"];
      dataItem.packSaleUprc         = messages["salePrice.packSaleUprc"];

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

  $scope.applyFg = true;
  $scope.saleUprcApply = true;
  
  // <-- 검색 호출 -->
  // 상품정보관리 그리드 조회
  $scope.$on("storeSalePriceCtrl", function(event, data) {
    $scope.searchSalePriceList();
    event.preventDefault();
  });

  // 판매가 그리드 조회
  $scope.searchSalePriceList = function(){
    var params = {};
    params.storeCd = $("#storeSalePriceStoreCd").val();
	params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.listScale = 5000;
    params.saleUprc = $("#saleUprc").prop("checked");
    params.stinSaleUprc = $("#stinSaleUprc").prop("checked");
    params.dlvrSaleUprc = $("#dlvrSaleUprc").prop("checked");
    params.packSaleUprc = $("#packSaleUprc").prop("checked");

    if(brandUseFg === "1" && orgnFg === "HQ"){
        // 선택한 상품브랜드가 있을 때
        params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

        // 선택한 상품브랜드가 없을 때('전체' 일때)
        if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
            var userHqBrandCd = "";
            for(var i=0; i < userHqBrandCdComboList.length; i++){
                if(userHqBrandCdComboList[i].value !== null) {
                    userHqBrandCd += userHqBrandCdComboList[i].value + ","
                }
            }
            params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
        }
    }

    $scope._inquiryMain('/base/price/storeSalePrice/storeSalePrice/getSalePriceList.sb', params);
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

  // 상품정보
  $scope.prodInfo;
  $scope.setProdInfo = function(data){
    $scope.prodInfo = data;
  };
  $scope.getProdInfo = function(){
    return $scope.prodInfo;
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.searchStoreShow = function () {
    $scope._broadcast('searchStoreCtrl');
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    // 상품분류 팝업 핸들러 추가
    $scope.prodClassPopUpLayer.shown.addHandler(function (s) {
    });
  });

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.storeSalePriceStoreShow = function () {
        $scope._broadcast('storeSalePriceStoreCtrl');
    };


    // 엑셀 다운로드
    $scope.excelDownload = function () {
        var params = {};
        params.storeCd = $("#storeSalePriceStoreCd").val();
        params.prodClassCd = $scope.prodClassCd;
        params.prodCd = $scope.prodCd;
        params.prodNm = $scope.prodNm;
        params.saleUprc = $("#saleUprc").prop("checked");
        params.stinSaleUprc = $("#stinSaleUprc").prop("checked");
        params.dlvrSaleUprc = $("#dlvrSaleUprc").prop("checked");
        params.packSaleUprc = $("#packSaleUprc").prop("checked");

        if(brandUseFg === "1" && orgnFg === "HQ"){
            // 선택한 상품브랜드가 있을 때
            params.prodHqBrandCd = $scope.srchProdHqBrandCdCombo.selectedValue;

            // 선택한 상품브랜드가 없을 때('전체' 일때)
            if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
                var userHqBrandCd = "";
                for(var i=0; i < userHqBrandCdComboList.length; i++){
                    if(userHqBrandCdComboList[i].value !== null) {
                        userHqBrandCd += userHqBrandCdComboList[i].value + ","
                    }
                }
                params.userProdBrands = userHqBrandCd; // 사용자별 관리브랜드만 조회(관리브랜드가 따로 없으면, 모든 브랜드 조회)
            }
        }

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
        var params       = data;
        /*params.storeCd = data.storeCd;
        params.prodClassCd = data.prodClassCd;
        params.prodCd = data.prodCd;
        params.prodNm = data.prodNm;
        params.saleUprc = data.saleUprc;
        params.stinSaleUprc = data.stinSaleUprc;
        params.dlvrSaleUprc = data.dlvrSaleUprc;
        params.packSaleUprc = data.packSaleUprc;*/

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/base/price/storeSalePrice/storeSalePrice/getSalePriceExcelList.sb", params, function() {
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
                }, messages["salePrice.storeSalePrice"]+getToday()+'.xlsx', function () {
                    $timeout(function () {
                        $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                    }, 10);
                });
            }, 10);
        });
    };

}]);