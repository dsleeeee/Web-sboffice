/**
 * get application
 */
var app = agrid.getApp();

app.controller('storeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.todayGnrlzSelectStoreShow = function () {
    $scope._broadcast('todayGnrlzSelectStoreCtrl');
  };
}]);

/** 당일매출종합현황 그리드 controller */
app.controller('todayGnrlzCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayGnrlzCtrl', $scope, $http, true));

  $scope.srchStartDate = wcombo.genDateVal("#srchStartDate", gvStartDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayGnrlzCtrl");

  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayGnrlzCtrl", function (event, data) {
    $scope.searchTodayGnrlzList();

    var params       = {};
    params.startDate = $scope.srchStartDate;
    params.storeCd   = $("#todayGnrlzSelectStoreCd").val();
    // 결제수단별 조회
    $scope._broadcast('todayGnrlzPayCtrl', params);
    // 회원 Point 적립/사용 조회
    $scope._broadcast('todayGnrlzMemberCtrl', params);
    // 상품별 매출현황 조회
    $scope._broadcast('todayGnrlzProdCtrl', params);

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 매출종합 리스트 조회
  $scope.searchTodayGnrlzList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.storeCd   = $("#todayGnrlzSelectStoreCd").val();

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/today/todayGnrlz/todayGnrlz/list.sb", params, null, false);
  };


  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.todayGnrlzSelectStoreShow = function () {
    $scope._broadcast('todayGnrlzSelectStoreCtrl');
  };

  // 매출종합 엑셀 다운로드
  $scope.excelDownloadGnrlz = function () {
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
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayGnrlz.todaySaleTotal"] + '_' + messages["todayGnrlz.subTitleGnrlz"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);


/** 결제수단별 매출 그리드 controller */
app.controller('todayGnrlzPayCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayGnrlzPayCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayGnrlzPayCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayGnrlzPayCtrl", function (event, data) {
    $scope.srchStartDate = data.startDate;
    $scope.storeCd       = data.storeCd;
    $scope.searchTodayGnrlzPayList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 결제수단별 매출 리스트 조회
  $scope.searchTodayGnrlzPayList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/today/todayGnrlz/todayGnrlzPay/list.sb", params, null, false);
  };

  // 결제수단별 엑셀 다운로드
  $scope.excelDownloadPay = function () {
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
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayGnrlz.todaySaleTotal"] + '_' + messages["todayGnrlz.subTitlePay"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);


/** 회원 Point 적립/사용 그리드 controller */
app.controller('todayGnrlzMemberCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayGnrlzMemberCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayGnrlzMemberCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayGnrlzMemberCtrl", function (event, data) {
    $scope.srchStartDate = data.startDate;
    $scope.storeCd       = data.storeCd;
    $scope.searchTodayGnrlzMemberList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 회원 Point 적립/사용 리스트 조회
  $scope.searchTodayGnrlzMemberList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/today/todayGnrlz/todayGnrlzMember/list.sb", params, null, false);
  };

  // 회원 Point 적립/사용 엑셀 다운로드
  $scope.excelDownloadMember = function () {
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
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayGnrlz.todaySaleTotal"] + '_' + messages["todayGnrlz.subTitleMember"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);


/** 상품별 매출현황 그리드 controller */
app.controller('todayGnrlzProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('todayGnrlzProdCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // picker 사용시 호출 : 미사용시 호출안함
    $scope._makePickColumns("todayGnrlzProdCtrl");

    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("todayGnrlzProdCtrl", function (event, data) {
    $scope.srchStartDate = data.startDate;
    $scope.storeCd       = data.storeCd;
    $scope.searchTodayGnrlzProdList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품별 매출현황 리스트 조회
  $scope.searchTodayGnrlzProdList = function () {
    // 파라미터
    var params       = {};
    params.startDate = wijmo.Globalize.format($scope.srchStartDate.value, 'yyyyMMdd');
    params.storeCd   = $scope.storeCd;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/sale/today/todayGnrlz/todayGnrlzProd/list.sb", params, null, false);
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
          messages["todayGnrlz.todayGnrlz"] + '(' + messages["todayGnrlz.todaySaleTotal"] + '_' + messages["todayGnrlz.subTitleProd"] + ')_' + getCurDateTime() +'.xlsx', function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
            }, 10);
          });
    }, 10);
  };

}]);
