/**
 * get application
 */
var app = agrid.getApp();


app.controller('payTemporaryCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('payTemporaryCtrl', $scope, $http, true));

   var startDate = wcombo.genDateVal("#srchDayStartDate", gvStartDate);
   var endDate   = wcombo.genDateVal("#srchDayEndDate", gvEndDate);
  $scope.orgnFg        = gvOrgnFg;

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("storeHqBrandCdCombo", momsHqBrandCdComboList); // 매장브랜드
  $scope._setComboData("momsTeamCombo", momsTeamComboList); // 팀별
  $scope._setComboData("momsAcShopCombo", momsAcShopComboList); // AC점포별
  $scope._setComboData("momsAreaFgCombo", momsAreaFgComboList); // 지역구분
  $scope._setComboData("momsCommercialCombo", momsCommercialComboList); // 상권
  $scope._setComboData("momsShopTypeCombo", momsShopTypeComboList); // 점포유형
  $scope._setComboData("momsStoreManageTypeCombo", momsStoreManageTypeComboList); // 매장관리타입
  $scope._setComboData("branchCdCombo", branchCdComboList); // 지사

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
    $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
    $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("payTemporaryCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');

    // 그리드 링크 효과
    s.formatItem.addHandler(function (s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        if (col.binding === "storeCd" || col.binding === "saleDate") {
          var item = s.rows[e.row].dataItem;
          wijmo.addClass(e.cell, 'wijLink');
          wijmo.addClass(e.cell, 'wj-custom-readonly');
        }
      }
    });

    // 그리드 클릭 이벤트
    s.addEventListener(s.hostElement, 'mousedown', function (e) {
      var ht = s.hitTest(e);
      if (ht.cellType === wijmo.grid.CellType.Cell) {
        var col         = ht.panel.columns[ht.col];
        var selectedRow = s.rows[ht.row].dataItem;
        if (col.binding === "storeCd" || col.binding === "saleDate") {
          $scope.wjPayTemporaryDtlLayer.show(true);
          $scope._broadcast('payTemporaryDtlCtrl', selectedRow);
        }
      }
    });
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("payTemporaryCtrl", function (event, data) {
    $scope.searchDayList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 리스트 조회
  $scope.searchDayList = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }

    // 조회일자 최대 7일 제한
    if (diffDay > 6) {
      $scope._popMsg(messages['cmm.dateOver.7day.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    params.storeCds   = $("#payTemporaryStoreCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
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
    params.listScale = 500; //-페이지 스케일 갯수
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/status/payTemporary/payTemporary/getPayTemporaryList.sb", params, function (){
    });
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
  $scope.payTemporaryStoreShow = function () {
    $scope._broadcast('payTemporaryStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownloadInfo = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }

    // 조회일자 최대 7일 제한
    if (diffDay > 6) {
      $scope._popMsg(messages['cmm.dateOver.7day.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    params.storeCds   = $("#payTemporaryStoreCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
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

    $scope._broadcast('payTemporaryExcelCtrl', params);

  };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('payTemporaryExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('payTemporaryExcelCtrl', $scope, $http, false));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      $scope.brandDataMap = new wijmo.grid.DataMap(momsHqBrandCdComboList, 'value', 'name');
      $scope.momsTeamDataMap = new wijmo.grid.DataMap(momsTeamComboList, 'value', 'name');
      $scope.momsAcShopDataMap = new wijmo.grid.DataMap(momsAcShopComboList, 'value', 'name');

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');

    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("payTemporaryExcelCtrl", function (event, data) {
        $scope.searchExcelList(data);
        // 기능수행 종료 : 반드시 추가
        event.preventDefault();
    });

    // 엑셀 리스트 조회
    $scope.searchExcelList = function (params) {

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/sale/status/payTemporary/payTemporary/getPayTemporaryExcelList.sb", params, function (){

            if ($scope.excelFlex.rows.length <= 0) {
                $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
                return false;
            }

          $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
          $timeout(function () {
            wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
              includeColumnHeaders: true,
              includeCellStyles: true,
              includeColumns: function (column) {
                return column.visible;
              }
            },
                messages["payTemporary.payTemporary"] + '_' + getCurDateTime() +'.xlsx', function () {
                  $timeout(function () {
                    $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                  }, 10);
                });
          }, 10);
        });
    };
}]);