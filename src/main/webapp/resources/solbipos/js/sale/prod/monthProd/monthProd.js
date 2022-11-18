/**
 * get application
 */
var app = agrid.getApp();

var optionData = [
  {"name":"단품/세트","value":"1"},
  {"name":"단품/구성","value":"2"},
  {"name":"단품/세트/구성","value":"3"},
  {"name":"모두표시","value":"4"}
];
/** controller */
app.controller('monthProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthProdCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startMonth = new wijmo.input.InputDate('#startMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });
  var endMonth = new wijmo.input.InputDate('#endMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("hqBrandCd", hqBrandList);
  $scope._setComboData("option", optionData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("monthProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("monthProdCtrl", function (event, data) {
    $scope.searchCashBillList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchCashBillList = function () {

    var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
    var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
    var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['mobile.cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1년(12개월) 제한
    if (diffMonth > 12) {
      $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCds   = $("#monthProdStoreCd").val();
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); // 조회기간
    params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');// 조회기간
    params.hqBrandCd = $scope.hqBrandCd;
    params.option = $scope.option;
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/monthProd/monthProd/getMonthProdList.sb", params, function (){

      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 16;

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if($scope.option === "1"){  // 단품+세트
          if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "2"){  // 단품+구성
          if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "3"){  // 단품+세트+구성
          if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "4"){  // 모두 표시
          columns[j].visible = true;
        }
      }
      // <-- //그리드 visible -->
    });
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.monthProdStoreShow = function () {
    $scope._broadcast('monthProdStoreCtrl');
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

    var startDt = new Date(wijmo.Globalize.format(startMonth.value, 'yyyy-MM'));
    var endDt = new Date(wijmo.Globalize.format(endMonth.value, 'yyyy-MM'));
    var diffMonth = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000 * 30); // 시 * 분 * 초 * 밀리세컨 * 월

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['mobile.cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 1년(12개월) 제한
    if (diffMonth > 12) {
      $scope._popMsg(messages['mobile.cmm.dateOver.1year.error']);
      return false;
    }

    var params = {};
    params.storeCds   = $("#monthProdStoreCd").val();
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); // 조회기간
    params.endMonth = wijmo.Globalize.format(endMonth.value, 'yyyyMM');// 조회기간
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodClassCd = $scope.prodClassCd;
    params.hqBrandCd = $scope.hqBrandCd;
    params.option = $scope.option;

    $scope._broadcast('monthProdExcelCtrl',params);
  }
}]);


app.controller('monthProdExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthProdExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("monthProdExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (data) {
    // 파라미터
    var params       = {};
    params.storeCds = data.storeCds;
    params.startMonth = data.startMonth;
    params.endMonth = data.endMonth;
    params.prodCd = data.prodCd;
    params.prodNm = data.prodNm;
    params.prodClassCd = data.prodClassCd;
    params.hqBrandCd = data.hqBrandCd;
    params.option = data.option;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/monthProd/monthProd/getMonthProdExcelList.sb", params, function() {
      if ($scope.flex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      // <-- 그리드 visible -->
      // 선택한 테이블에 따른 리스트 항목 visible
      var grid = wijmo.Control.getControl("#wjGridExcelList");
      var columns = grid.columns;

      // 컬럼 총갯수
      var columnsCnt = 16;

      // 합계가 0이면 해당 컬럼 숨기기
      for (var j = 0; j < columnsCnt; j++) {
        if($scope.option === "1"){  // 단품+세트
          if(columns[j].binding == "saleQty2" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt2" || columns[j].binding == "realSaleAmt3") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "2"){  // 단품+구성
          if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty3" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt3") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "3"){  // 단품+세트+구성
          if(columns[j].binding == "saleQty1" || columns[j].binding == "saleQty2" || columns[j].binding == "realSaleAmt1" || columns[j].binding == "realSaleAmt2") {
            columns[j].visible = false;
          } else {
            columns[j].visible = true;
          }
        } else if($scope.option === "4"){  // 모두 표시
          columns[j].visible = true;
        }
      }
      // <-- //그리드 visible -->

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, messages["monthProdMoms.monthProdMoms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);
