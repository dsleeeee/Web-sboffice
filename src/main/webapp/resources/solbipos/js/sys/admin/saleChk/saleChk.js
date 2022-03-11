/****************************************************************
 *
 * 파일명 : saleChk.js
 * 설  명 : 매출점검 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.07     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 매출구분 필터
var saleYnData = [
  {"name":"전체","value":""},
  {"name":"매출","value":"Y"},
  {"name":"반품","value":"N"}
];
// 체크항목 필터
var checkNmData = [
  {"name":"전체","value":""},
  {"name":"ERP오류","value":"ERP오류"},
];
// 오류타입 필터
var remarkData = [
  {"name":"전체","value":""},
  {"name":"반품데이터오류","value":"1"},
  {"name":"기타","value":"2"}
];
// 처리구분 필터
var procYnData = [
  {"name":"전체","value":""},
  {"name":"YEW","value":"Y"},
  {"name":"NO","value":"N"}
];
// 체크항목코드 필터
var checkCdData = [
  {"name":"전체","value":""},
  {"name":"영수증누락","value":"001"},
  {"name":"ERP생성누락","value":"002"},
  {"name":"NONSALE누락","value":"003"}
];

/**
 * 가상로그인 그리드 생성
 */
app.controller('saleChkCtrl',  ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('saleChkCtrl', $scope, $http, true));

  // 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("srchSaleYn", saleYnData);
  $scope._setComboData("srchCheckNm", checkNmData);
  $scope._setComboData("srchRemark", remarkData);
  $scope._setComboData("srchProcYn", procYnData);
  $scope._setComboData("srchCheckCd", checkCdData);

  // 매출일자 셋팅
  $scope.startDate = wcombo.genDateVal("#srchStartDate", gvStartDate);
  $scope.endDate   = wcombo.genDateVal("#srchEndDate", gvEndDate);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.saleYnDataMap = new wijmo.grid.DataMap(saleYnData, 'value', 'name');
    $scope.procYnDataMap = new wijmo.grid.DataMap(procYnData, 'value', 'name');
  };

  $scope.hqOfficeCd = "A0001";

  // 가상로그인 그리드 조회
  $scope.$on("saleChkCtrl", function(event, data) {
    $scope.getSaleList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.getSaleList = function() {

    var startDt = new Date(wijmo.Globalize.format($scope.startDate.value, 'yyyy-MM-dd'));
    var endDt = new Date(wijmo.Globalize.format($scope.endDate.value, 'yyyy-MM-dd'));
    var diffDay = (endDt.getTime() - startDt.getTime()) / (24 * 60 * 60 * 1000); // 시 * 분 * 초 * 밀리세컨

    // 시작일자가 종료일자보다 빠른지 확인
    if(startDt.getTime() > endDt.getTime()){
      s_alert.pop(messages['cmm.dateChk.error']);
      return false;
    }
    // 조회일자 최대 두달(62일) 제한
    if (diffDay > 62) {
      s_alert.pop(messages['cmm.dateOver.2month.error']);
      return false;
    }

    // 파라미터
    var params = {};
    params.startDate = wijmo.Globalize.format($scope.startDate.value, 'yyyyMMdd');
    params.endDate = wijmo.Globalize.format($scope.endDate.value, 'yyyyMMdd');

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/sys/admin/saleChk/saleChk/getSaleList.sb", params, function() {

    });
  }

  $scope.save = function() {

    if($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["cmm.empty.data"]);
      return false;
    }

    // 파라미터 설정
    var params = new Array();
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        params.push($scope.flex.collectionView.itemsEdited[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/admin/saleChk/saleChk/updateResultMsg.sb", params, function(){
      $scope.getSaleList();
    });
  }

}]);
