/****************************************************************
 *
 * 파일명 : prodInfo.js
 * 설  명 : 상품-속성/선택메뉴/옵션 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.23     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

/** 그리드 생성 */
app.controller('prodInfoCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodInfoCtrl', $scope, $http, false));

  $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "gubun") {
          var item = s.rows[e.row].dataItem;
          if (item.gubun != "선택메뉴") {
            item.sdselQty = '';
            item.addProdUprc = '';
            item.addProdQty = '';
          }
        }
      }
    });

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    //첫째줄 Header 생성
    var dataItem = {};
    dataItem.hqBrandCd      = messages["prodInfoSearch.prodInfo.hqBrandCd"];
    dataItem.hqBrandNm      = messages["prodInfoSearch.prodInfo.hqBrandNm"];
    dataItem.prodClassCd    = messages["prodInfoSearch.prodInfo.prodClassCd"];
    dataItem.prodClassNm    = messages["prodInfoSearch.prodInfo.prodClassNm"];
    dataItem.prodCd         = messages["prodInfoSearch.prodInfo.prodCd"];
    dataItem.prodNm         = messages["prodInfoSearch.prodInfo.prodNm"];
    dataItem.gubun 	        = messages["prodInfoSearch.prodInfo.gubun"];
    dataItem.sdattrClassCd  = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrClassNm  = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrCd       = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrNm       = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdselGrpCd 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselGrpNm 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselClassCd   = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselClassNm   = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselQty       = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselProdCd    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselProdNm 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.addProdUprc	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.addProdQty	    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.fixProdFg	    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.optionGrpCd 	= messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionGrpNm    = messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionValCd    = messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionValNm    = messages["prodInfoSearch.prodInfo.option"];
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------

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
  $scope.$on("prodInfoCtrl", function(event, data) {
    $scope.searchProdInfoList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 옵션 목록 조회
  $scope.searchProdInfoList = function(){
    var params = {};
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodCds = $("#optionProdSelectCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
    // '전체' 일때
    if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.listScale = 500;
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodInfoSearch/prodInfo/getProdInfoList.sb", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prodInfoProdSelectShow = function () {
    $scope._broadcast('prodInfoProdSelectCtrl');
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
    var params = {};
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodCds = $("#orgplceProdSelectCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
    // '전체' 일때
    if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }

    $scope._broadcast('prodInfoExcelCtrl', params);
  };
  
}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('prodInfoExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodInfoExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    s.formatItem.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "gubun") {
          var item = s.rows[e.row].dataItem;
          if (item.gubun != "선택메뉴") {
            item.sdselQty = '';
            item.addProdUprc = '';
            item.addProdQty = '';
          }
        }
      }
    });

    //Grid Header 2줄 - START	----------------------------------------------------------------
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());

    //첫째줄 Header 생성
    var dataItem = {};
    dataItem.hqBrandCd      = messages["prodInfoSearch.prodInfo.hqBrandCd"];
    dataItem.hqBrandNm      = messages["prodInfoSearch.prodInfo.hqBrandNm"];
    dataItem.prodClassCd    = messages["prodInfoSearch.prodInfo.prodClassCd"];
    dataItem.prodClassNm    = messages["prodInfoSearch.prodInfo.prodClassNm"];
    dataItem.prodCd         = messages["prodInfoSearch.prodInfo.prodCd"];
    dataItem.prodNm         = messages["prodInfoSearch.prodInfo.prodNm"];
    dataItem.gubun 	        = messages["prodInfoSearch.prodInfo.gubun"];
    dataItem.sdattrClassCd  = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrClassNm  = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrCd       = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdattrNm       = messages["prodInfoSearch.prodInfo.sdattr"];
    dataItem.sdselGrpCd 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselGrpNm 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselClassCd   = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselClassNm   = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselQty       = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselProdCd    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.sdselProdNm 	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.addProdUprc	= messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.addProdQty	    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.fixProdFg	    = messages["prodInfoSearch.prodInfo.sdsel"];
    dataItem.optionGrpCd 	= messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionGrpNm    = messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionValCd    = messages["prodInfoSearch.prodInfo.option"];
    dataItem.optionValNm    = messages["prodInfoSearch.prodInfo.option"];
    s.columnHeaders.rows[0].dataItem = dataItem;
    //Grid Header 2줄 - END		----------------------------------------------------------------

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
  $scope.$on("prodInfoExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prodInfoSearch/prodInfo/getProdInfoExcelList.sb", params, function() {
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
        }, messages["prodInfoSearch.prodInfo"] + getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);