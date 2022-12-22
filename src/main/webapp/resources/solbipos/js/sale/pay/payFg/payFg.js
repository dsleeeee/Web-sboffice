/**
 * get application
 */
var app = agrid.getApp();

var optionData = [
  {"name":"전체","value":"all"},
  {"name":"지사별","value":"branch"},
  {"name":"매장별","value":"store"}
];

/** controller */
app.controller('payFgCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('payFgCtrl', $scope, $http, true));

  var startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  var endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  $scope._setComboData("option", optionData);
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

    $scope.payCdDataMap = new wijmo.grid.DataMap(payCd, 'value', 'name');

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("payFgCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("payFgCtrl", function (event, data) {
    $scope.searchPayFgList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchPayFgList = function () {

    var startDt = new Date(wijmo.Globalize.format(startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format(endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }

    // 조회일자 최대 1달(31일) 제한
    if (diffDay > 30) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    if(orgnFg === "STORE"){
      $scope.option = "store";
    }
    if($scope.option === "store"){
      params.storeCds   = $("#payFgStoreCd").val();
    } else {
      params.storeCds = '';
    }
    params.payCol = payCol;
    params.option = $scope.option;
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
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/pay/payFg/payFg/getPayFgList.sb", params, function (){

      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridList");
      var columns = grid.columns;

      var columnsCnt = 5;
      // 옵션에 따라 매장정보 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if($scope.option === "all"){
          if(columns[j].binding == "branchCd" || columns[j].binding == "branchNm" || columns[j].binding == "storeCd" || columns[j].binding == "storeNm") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "branch"){
          if(columns[j].binding == "storeCd" || columns[j].binding == "storeNm") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "store"){
          columns[j].visible = true;
        }
      }
      // <-- 그리드 visible -->
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

  // 옵션(지사별/매장별)에 따라 매장선택 숨김/보임
  $scope.changeOption = function (s){
    if(s.selectedValue === "all" || s.selectedValue === "branch"){
      $(".payFgStore").hide();
    } else if(s.selectedValue === "store"){
      $(".payFgStore").show();
    }
  }

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.payFgStoreShow = function () {
    $scope._broadcast('payFgStoreCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate   = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    if($scope.option === "store"){
      params.storeCds   = $("#payFgStoreCd").val();
    } else {
      params.storeCds = '';
    }
    params.payCol = payCol;
    params.option = $scope.option;
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
    $scope._broadcast('payFgExcelCtrl',params);
  }
}]);


app.controller('payFgExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('payFgExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    $scope.payCdDataMap = new wijmo.grid.DataMap(payCd, 'value', 'name');

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("payFgExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (data) {
    // 파라미터
    var params       = {};
    params.startDate = data.startDate;
    params.endDate = data.endDate;
    if(data.option === "store"){
      params.storeCds   = $("#payFgStoreCd").val();
    } else {
      params.storeCds = '';
    }
    params.payCol = data.payCol;
    params.option = data.option;
    params.prodHqBrandCd = data.prodHqBrandCd;
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
    $scope._inquiryMain("/sale/pay/payFg/payFg/getPayFgExcelList.sb", params, function() {
      if ($scope.flex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridExcelList");
      var columns = grid.columns;

      var columnsCnt = 5;
      // 옵션에 따라 매장정보 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if(params.option === "all"){
          if(columns[j].binding == "branchCd" || columns[j].binding == "branchNm" || columns[j].binding == "storeCd" || columns[j].binding == "storeNm") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if(params.option === "branch"){
          if(columns[j].binding == "storeCd" || columns[j].binding == "storeNm") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "store"){
          columns[j].visible = true;
        }
      }
      // <-- 그리드 visible -->

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, messages["payFgMoms.payFgMoms"]+getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);