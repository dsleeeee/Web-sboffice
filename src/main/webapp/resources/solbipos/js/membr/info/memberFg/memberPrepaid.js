/****************************************************************
 *
 * 파일명 : memberPrepaid.js
 * 설  명 : 선불회원 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.05.13     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 선불회원 그리드 생성
 */
app.controller('memberPrepaidCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPrepaidCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("memberClass", memberClassList);

  // 그리드 조회
  $scope.$on("memberPrepaidCtrl", function(event, data) {
    var prepaidScope = agrid.getScope('memberPrepaidRegistCtrl'); // 선불회원 등록(선불)
    prepaidScope._broadcast('memberPrepaidRegistCtrl');
    var NoPrepaidScope = agrid.getScope('memberPrepaidNoRegistCtrl'); // 선불회원 미등록(후불)
    NoPrepaidScope._broadcast('memberPrepaidNoRegistCtrl');
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.prepaidStoreShow = function () {
    $scope._broadcast('prepaidStoreCtrl');
  };
}]);

/*
    적용 그리드
 */
app.controller('memberPrepaidRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPrepaidRegistCtrl', $scope, $http, false));

  // 그리드 조회
  $scope.$on("memberPrepaidRegistCtrl", function(event, data) {
    $scope.searchPrepaidList();
    event.preventDefault();
  });

  $scope.searchPrepaidList = function () {
    var scope = agrid.getScope('memberPrepaidCtrl');
    var params = {};
    params.membrNo = scope.membrNo;
    params.membrNm = scope.membrNm;
    params.membrClassCd = scope.membrClassCd;
    params.telNo = scope.telNo;
    params.storeCd = $("#prepaidStoreCd").val();
    params.listScale = 500;

    $scope._inquirySub("/membr/info/memberFg/memberFg/getMemberPrepaid.sb", params, function () {});
  };

  // 선불회원 등록 해제
  $scope.regPostpaid = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.collectionView.items[i].useYn='N';
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/memberFg/memberFg/regPrepaid.sb", params, function(){
      $scope._broadcast('memberPrepaidCtrl');
    });
  };
}]);
/*
    미적용 그리드
 */
app.controller('memberPrepaidNoRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPrepaidNoRegistCtrl', $scope, $http, false));

  // 그리드 조회
  $scope.$on("memberPrepaidNoRegistCtrl", function(event, data) {
    $scope.searchNoPrepaidList();
    event.preventDefault();
  });

  $scope.searchNoPrepaidList = function () {
    var scope = agrid.getScope('memberPrepaidCtrl');
    var params = {};
    params.membrNo = scope.membrNo;
    params.membrNm = scope.membrNm;
    params.membrClassCd = scope.membrClassCd;
    params.telNo = scope.telNo;
    params.storeCd = $("#prepaidStoreCd").val();
    params.listScale = 500;

    $scope._inquirySub("/membr/info/memberFg/memberFg/getMemberNoPrepaid.sb", params, function () {});
  };

  // 선불회원 등록
  $scope.regPrepaid = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.collectionView.items[i].useYn='Y';
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/memberFg/memberFg/regPrepaid.sb", params, function(){
      $scope._broadcast('memberPrepaidCtrl');
    });
  };
}]);