/**
 * get application
 */
var app = agrid.getApp();

/** 승인 controller */
app.controller('todayMomsCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayMomsCtrl', $scope, $http, true));

  $scope.srchStartDate  = wcombo.genDateVal("#srchStartDate", gvStartDate);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

  // 팀별
  if(momsTeamComboList.length <= 1) {
    $("#srchMomsTeamCombo").css('background-color', '#F0F0F0');
    $("#srchMomsTeamCombo").attr("disabled", true);
  } else {
    $("#srchMomsTeamCombo").css('background-color', '#FFFFFF');
    $("#srchMomsTeamCombo").attr("disabled", false);
  }
  // AC점포별
  if(momsAcShopComboList.length <= 1) {
    $("#srchMomsAcShopCombo").css('background-color', '#F0F0F0');
    $("#srchMomsAcShopCombo").attr("disabled", true);
  } else {
    $("#srchMomsAcShopCombo").css('background-color', '#FFFFFF');
    $("#srchMomsAcShopCombo").attr("disabled", false);
  }
  // 지역구분
  if(momsAreaFgComboList.length <= 1) {
    $("#srchMomsAreaFgCombo").css('background-color', '#F0F0F0');
    $("#srchMomsAreaFgCombo").attr("disabled", true);
  } else {
    $("#srchMomsAreaFgCombo").css('background-color', '#FFFFFF');
    $("#srchMomsAreaFgCombo").attr("disabled", false);
  }
  // 상권
  if(momsCommercialComboList.length <= 1) {
    $("#srchMomsCommercialCombo").css('background-color', '#F0F0F0');
    $("#srchMomsCommercialCombo").attr("disabled", true);
  } else {
    $("#srchMomsCommercialCombo").css('background-color', '#FFFFFF');
    $("#srchMomsCommercialCombo").attr("disabled", false);
  }
  // 점포유형
  if(momsShopTypeComboList.length <= 1) {
    $("#srchMomsShopTypeCombo").css('background-color', '#F0F0F0');
    $("#srchMomsShopTypeCombo").attr("disabled", true);
  } else {
    $("#srchMomsShopTypeCombo").css('background-color', '#FFFFFF');
    $("#srchMomsShopTypeCombo").attr("disabled", false);
  }
  // 매장관리타입
  if(momsStoreManageTypeComboList.length <= 1) {
    $("#srchMomsStoreManageTypeCombo").css('background-color', '#F0F0F0');
    $("#srchMomsStoreManageTypeCombo").attr("disabled", true);
  } else {
    $("#srchMomsStoreManageTypeCombo").css('background-color', '#FFFFFF');
    $("#srchMomsStoreManageTypeCombo").attr("disabled", false);
  }
  // 지사
  if(branchCdComboList.length <= 1) {
    $("#srchBranchCdComboo").css('background-color', '#F0F0F0');
    $("#srchBranchCdComboo").attr("disabled", true);
  } else {
    $("#srchBranchCdComboo").css('background-color', '#FFFFFF');
    $("#srchBranchCdComboo").attr("disabled", false);
  }

  // 그리드 매출구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayDtl.saleY"]},
    {id: "N", name: messages["todayDtl.saleN"]}
  ], 'id', 'name');


  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayMomsCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 헤더머지
    s.allowMerging = 2;
    s.columnHeaders.rows.push(new wijmo.grid.Row());
    // 첫째줄 헤더 생성
    var dataItem         = {};
    dataItem.branchNm    = messages["todayMoms.branchNm"];
    dataItem.storeCd     = messages["todayMoms.storeCd"];
    dataItem.storeNm     = messages["todayMoms.storeNm"];
    dataItem.posNo       = messages["todayDtl.dtl.posNo"];
    dataItem.billNo      = messages["todayDtl.dtl.billNo"];
    dataItem.billDt      = messages["todayDtl.dtl.billDt"];
    dataItem.saleYn      = messages["todayDtl.dtl.saleYn"];
    dataItem.totSaleAmt  = messages["todayDtl.dtl.totSaleAmt"];
    dataItem.totDcAmt    = messages["todayDtl.dtl.totDcAmt"];
    dataItem.realSaleAmt = messages["todayDtl.dtl.realSaleAmt"];
    dataItem.gaAmt       = messages["todayDtl.dtl.gaAmt"];
    dataItem.vatAmt      = messages["todayDtl.dtl.vatAmt"];
    dataItem.totTipAmt   = messages["todayDtl.dtl.totTipAmt"];
    dataItem.totEtcAmt   = messages["todayDtl.dtl.totEtcAmt"];
    dataItem.cupAmt      = messages["todayDtl.dtl.cupAmt"];
    dataItem.totPayAmt   = messages["todayDtl.dtl.payMethod"];

    // 결제수단 헤더머지 컬럼 생성
    for (var i = 0; i < arrPayCol.length; i++) {
      dataItem['pay' + arrPayCol[i]] = messages["todayDtl.dtl.payMethod"];
    }
    // 할인구분 헤더머지 컬럼 생성
    for (var i = 0; i < arrDcCol.length; i++) {
      dataItem['dc' + arrDcCol[i]] = messages["todayDtl.dcInfo"];
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
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayMomsCtrl", function (event, data) {
    $scope.searchTodayMomsList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchTodayMomsList = function () {

    // 파라미터
    var params       = {};
    params.storeCds   = $("#todayMomsStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.payCol    = payCol;
    params.dcCol     = dcCol;
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
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

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/today/todayMoms/todayMoms/getTodayMomsList.sb", params);
  };

  // 확장조회 숨김/보임
  $scope.searchAddShowChange = function(){
    if( $("#tblSearchAddShow").css("display") === 'none') {
      $("#tblSearchAddShow").show();
    } else {
      $("#tblSearchAddShow").hide();
    }
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.todayMomsStoreShow = function () {
    $scope._broadcast('todayMomsStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.storeCds   = $("#todayMomsStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.payCol    = payCol;
    params.dcCol     = dcCol;
    params.momsTeam = $scope.momsTeam;
    params.momsAcShop = $scope.momsAcShop;
    params.momsAreaFg = $scope.momsAreaFg;
    params.momsCommercial = $scope.momsCommercial;
    params.momsShopType = $scope.momsShopType;
    params.momsStoreManageType = $scope.momsStoreManageType;
    params.branchCd = $scope.branchCd;
    params.storeHqBrandCd = $scope.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }

    $scope._broadcast('todayMomsExcelCtrl',params);
  }
}]);


app.controller('todayMomsExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayMomsExcelCtrl', $scope, $http, true));

  // 그리드 매출구분
  $scope.saleYnMap = new wijmo.grid.DataMap([
    {id: "Y", name: messages["todayDtl.saleY"]},
    {id: "N", name: messages["todayDtl.saleN"]}
  ], 'id', 'name');

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayMomsExcelCtrl", function (event, data) {
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
    params.payCol    = data.payCol;
    params.dcCol     = data.dcCol;
    params.momsTeam = data.momsTeam;
    params.momsAcShop = data.momsAcShop;
    params.momsAreaFg = data.momsAreaFg;
    params.momsCommercial = data.momsCommercial;
    params.momsShopType = data.momsShopType;
    params.momsStoreManageType = data.momsStoreManageType;
    params.branchCd = data.branchCd;
    params.storeHqBrandCd = data.storeHqBrandCd;
    // '전체' 일때
    if(params.storeHqBrandCd === "" || params.storeHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/today/todayMoms/todayMoms/getTodayMomsExcelList.sb", params, function() {
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
        }, messages["todayMomsMoms.todayMomsMoms"]+getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);