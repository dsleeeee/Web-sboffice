/**
 * get application
 */
var app = agrid.getApp();

/** 신용카드 승인 controller */
app.controller('cardCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cardCtrl', $scope, $http, true));

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
    $scope._makePickColumns("cardCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("cardCtrl", function (event, data) {
    $scope.searchCardList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 영수증별매출상세현황 리스트 조회
  $scope.searchCardList = function () {

    var startDt = new Date(wijmo.Globalize.format($scope.srchStartDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.srchEndDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 6달(93일) 제한
    if (diffDay > 186) {
      $scope._popMsg(messages['cmm.dateOver.6month.error']);
      return false;
    }

    if( isEmptyObject( $("#cardStoreCd").val()) ) {
      $scope._popMsg("매장을 선택해주세요.");
      return false;
    }

    // 파라미터
    var params       = {};
    params.storeCds   = $("#cardStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.hqBrandCd = $scope.hqBrandCd;
    params.listScale = 500;

    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/appr/card/card/getCardList.sb", params);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.cardStoreShow = function () {
    $scope._broadcast('cardStoreCtrl');
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
    // 조회일자 최대 6달(93일) 제한
    if (diffDay > 186) {
      $scope._popMsg(messages['cmm.dateOver.6month.error']);
      return false;
    }

    if( isEmptyObject( $("#cardStoreCd").val()) ) {
      $scope._popMsg("매장을 선택해주세요.");
      return false;
    }

    var params = {};
    params.storeCds   = $("#cardStoreCd").val();
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.srchEndDate.value, 'yyyyMMdd');
    params.hqBrandCd = $scope.hqBrandCd;

    $scope._broadcast('cardExcelCtrl',params);
  }
}]);

app.controller('cardExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('cardExcelCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("cardExcelCtrl", function (event, data) {
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
    params.hqBrandCd = data.hqBrandCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sale/appr/card/card/getCardExcelList.sb", params, function() {
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
        }, messages["cardMoms.cardMoms"]+getToday()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);