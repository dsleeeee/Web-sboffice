/****************************************************************
 *
 * 파일명 : posSmartOrderCon.js
 * 설  명 : 매출점검 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.06.20     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 주문접속타입 필터
var connectTypeData = [
  {"name":"전체","value":""},
  {"name":"로그인","value":"1"},
  {"name":"주문건수","value":"2"},
  {"name":"주문수신","value":"3"},
  {"name":"주문취소수신","value":"4"}
];
// 전송응답 필터
var resultCodeData = [
  {"name":"전체","value":""},
  {"name":"정상","value":"0"},
  {"name":"오류","value":"1"}
];

/**
 * 그리드 생성
 */
app.controller('posSmartOrderConCtrl',  ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('posSmartOrderConCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData("srchConnectType", connectTypeData);
  $scope._setComboData("srchResultCode", resultCodeData);

  // 매출일자 셋팅
  $scope.startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.connectTypeDataMap = new wijmo.grid.DataMap(connectTypeData, 'value', 'name');
  };

  // 가상로그인 그리드 조회
  $scope.$on("posSmartOrderConCtrl", function(event, data) {
    $scope.getPosSmartOrderConList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.getPosSmartOrderConList = function() {

    var startDt = new Date(wijmo.Globalize.format($scope.startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      $scope._popMsg(messages['cmm.dateChk.error']);
      return false;
    }

    // 조회일자 최대 한달(31일) 제한
    if (diffDay > 31) {
      $scope._popMsg(messages['cmm.dateOver.1month.error']);
      return false;
    }

    // 파라미터
    var params = {};
    params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');
    params.listScale=500;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/admin/posSmartOrderCon/posSmartOrderCon/getPosSmartOrderConList.sb", params, function() {});
  }

  // <-- 엑셀다운로드 -->
  $scope.excelDownload = function(){
    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]);	// 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function()	{
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync(	$scope.flex,
          {
            includeColumnHeaders: 	true,
            includeCellStyles	: 	false,
            includeColumns      :	function (column) {
              // return column.visible;
              return column.binding != 'gChk';
            }
          },
          messages["posSmartOrderCon.posSmartOrderCon"] + '_'+getCurDateTime()+'.xlsx',
          function () {
            $timeout(function () {
              $scope.$broadcast('loadingPopupInactive'); //데이터 처리중 메시지 팝업 닫기
            }, 10);
          }
      );
    }, 10);
  };
  // <-- //양식다운로드 -->

}]);
