/****************************************************************
 *
 * 파일명 : postpaidDtl.js
 * 설  명 : 외상입금/후불 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.05.21     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  후불회원 그리드 생성
 */
app.controller('postpaidDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidDtlCtrl', $scope, $http, false));

  // 접속사용자의 권한(H : 본사, S: 매장)
  $scope.orgnFg = gvOrgnFg;

  // 매장권한으로 로그인 한 경우, 본인매장만 내역 조회가능.
  if($scope.orgnFg === 'S') {
    $scope.storeCds = gvStoreCd;
  }

  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

  // todo 검색조건에 매장, 조회일자 넣고 회원번호, 회원명 like검색

  // comboBox 초기화
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.postpaidFgDataMap = new wijmo.grid.DataMap(postpaidFgData, 'value', 'name');
  };

  $scope.$on("postpaidDtlCtrl", function(event, data) {
    $scope.searchPostpaidDtl();
    event.preventDefault();
  });

  // 후불회원상세 그리드 조회
  $scope.searchPostpaidDtl = function(){
    var params = {};
    params.storeCds = $("#storeCd").val();
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

    $scope._inquiryMain("/membr/anals/postpaidDtl/postpaidDtl/getPostpaidDtlMemberList.sb", params, function() {}, false);
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.storeCds = $("#storeCd").val();
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');

    $scope._broadcast('postpaidDtlExcelCtrl', params);
  };

}]);


/**
 *  후불회원 엑셀 그리드 생성
 */
app.controller('postpaidDtlExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('postpaidDtlExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.postpaidFgDataMap = new wijmo.grid.DataMap(postpaidFgData, 'value', 'name');
  };

  $scope.$on("postpaidDtlExcelCtrl", function(event, data) {
    $scope.excelDownload(data);
    event.preventDefault();
  });

  // 엑셀 다운로드
  $scope.excelDownload = function (params) {
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/membr/anals/postpaidDtl/postpaidDtl/getPostpaidDtlMemberExcelList.sb", params, function (){
      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 열기
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        },  '외상발생/입금내역 상세_'+ getToday() + '.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);
