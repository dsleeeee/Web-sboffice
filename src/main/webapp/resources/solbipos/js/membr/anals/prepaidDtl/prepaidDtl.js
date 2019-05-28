/****************************************************************
 *
 * 파일명 : prepaidDtl.js
 * 설  명 : 선불금 충전/사용내역 상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2019.05.27     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  후불회원 그리드 생성
 */
app.controller('prepaidDtlCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prepaidDtlCtrl', $scope, $http, true));

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
    $scope.prepaidFgDataMap = new wijmo.grid.DataMap(prepaidFgData, 'value', 'name');
  };

  $scope.$on("prepaidDtlCtrl", function(event, data) {
    $scope.searchPrepaidDtl();
    event.preventDefault();
  });

  // 후불회원상세 그리드 조회
  $scope.searchPrepaidDtl = function(){

    var params      = {};
    params.storeCds = $("#storeCd").val();
    /*params.startDate = '20190501';
    params.endDate = '20190506';*/
    params.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');;
    params.endDate = wijmo.Globalize.format(endDate.value, 'yyyyMMdd');;

    $scope._inquiryMain("/membr/anals/prepaidDtl/prepaidDtl/getPrepaidDtlMemberList.sb", params, function() {}, false);
  };

  // 매장선택 모듈 팝업 사용시 정의 (매장찾기)
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.storeShow = function () {
    $scope._broadcast('storeCtrl');
  };

}]);
