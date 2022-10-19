/**
 * get application
 */
var app = agrid.getApp();

// 시간대 DropBoxDataMap
var vSaleTime = [
  {"name":"00시","value":"00"},
  {"name":"01시","value":"01"},
  {"name":"02시","value":"02"},
  {"name":"03시","value":"03"},
  {"name":"04시","value":"04"},
  {"name":"05시","value":"05"},
  {"name":"06시","value":"06"},
  {"name":"07시","value":"07"},
  {"name":"08시","value":"08"},
  {"name":"09시","value":"09"},
  {"name":"10시","value":"10"},
  {"name":"11시","value":"11"},
  {"name":"12시","value":"12"},
  {"name":"13시","value":"13"},
  {"name":"14시","value":"14"},
  {"name":"15시","value":"15"},
  {"name":"16시","value":"16"},
  {"name":"17시","value":"17"},
  {"name":"18시","value":"18"},
  {"name":"19시","value":"19"},
  {"name":"20시","value":"20"},
  {"name":"21시","value":"21"},
  {"name":"22시","value":"22"},
  {"name":"23시","value":"23"}
];

/** 현금영수증 승인 controller */
app.controller('monthSalesVolumeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthSalesVolumeCtrl', $scope, $http, true));

  // 검색조건에 조회기간
  var startMonth = new wijmo.input.InputDate('#startMonth', {
    format       : "yyyy-MM",
    selectionMode: "2" // 달력 선택 모드(1:day 2:month)
  });
  $scope._setComboData("startTimeCombo", vSaleTime);
  $scope._setComboData("endTimeCombo", vSaleTime);

  // 브랜드 콤보박스 셋팅
  $scope._setComboData("hqBrandCd", hqBrandList);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.endTimeCombo.selectedValue = '23';

    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("monthSalesVolumeCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("monthSalesVolumeCtrl", function (event, data) {

    $scope.searchCashBillList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchCashBillList = function () {

    // 파라미터
    var params       = {};
    params.storeCds   = $("#monthSalesVolumeStoreCd").val();
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); // 조회기간
    params.startTime  = $scope.startTime;
    params.endTime    = $scope.endTime;
    params.hqBrandCd = $scope.hqBrandCd;
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/monthSalesVolume/monthSalesVolume/getMonthSalesVolumeList.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.monthSalesVolumeStoreShow = function () {
    $scope._broadcast('monthSalesVolumeStoreCtrl');
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
    params.storeCds   = $("#monthSalesVolumeStoreCd").val();
    params.startMonth = wijmo.Globalize.format(startMonth.value, 'yyyyMM'); // 조회기간
    params.startTime  = $scope.startTime;
    params.endTime    = $scope.endTime;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodClassCd = $scope.prodClassCd;
    params.hqBrandCd = $scope.hqBrandCd;

    $scope._broadcast('monthSalesVolumeExcelCtrl',params);
  }
}]);


app.controller('monthSalesVolumeExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('monthSalesVolumeExcelCtrl', $scope, $http, true));

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
  $scope.$on("monthSalesVolumeExcelCtrl", function (event, data) {
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
    params.startTime  = data.startTime;
    params.endTime    = data.endTime;
    params.prodCd = data.prodCd;
    params.prodNm = data.prodNm;
    params.prodClassCd = data.prodClassCd;
    params.hqBrandCd = data.hqBrandCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/prod/monthSalesVolume/monthSalesVolume/getMonthSalesVolumeExcelList.sb", params, function() {
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
        }, messages["monthSalesVolumeMoms.monthSalesVolumeMoms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);
