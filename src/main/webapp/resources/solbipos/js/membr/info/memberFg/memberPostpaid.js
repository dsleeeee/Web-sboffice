/****************************************************************
 *
 * 파일명 : memberPostpaid.js
 * 설  명 : 후불회원 JavaScript
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
 * 후불회원 그리드 생성
 */
app.controller('memberPostpaidCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPostpaidCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);
  $scope._setComboData("memberClassCd", memberClassList);

  // 그리드 조회
  $scope.$on("memberPostpaidCtrl", function(event, data) {
    var postpaidScope = agrid.getScope('memberPostpaidRegistCtrl'); // 후불회원 등록(후불)
    postpaidScope._broadcast('memberPostpaidRegistCtrl');
    var noPostpaidScope = agrid.getScope('memberPostpaidNoRegistCtrl'); // 후불회원 미등록(선불)
    noPostpaidScope._broadcast('memberPostpaidNoRegistCtrl');
    event.preventDefault();
  });

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.postpaidStoreShow = function () {
    $scope._broadcast('postpaidStoreCtrl');
  };
}]);

/*
    적용 그리드
 */
app.controller('memberPostpaidRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPostpaidRegistCtrl', $scope, $http, false));

  // 그리드 조회
  $scope.$on("memberPostpaidRegistCtrl", function(event, data) {
    $scope.searchPostpaidList();
    event.preventDefault();
  });

  $scope.searchPostpaidList = function () {
    var scope = agrid.getScope('memberPostpaidCtrl');
    var params = {};
    params.membrNo = scope.membrNo;
    params.membrNm = scope.membrNm;
    params.membrClassCd = scope.membrClassCd;
    params.telNo = scope.telNo;
    params.storeCd = $("#postpaidStoreCd").val();
    params.listScale = 500;

    $scope._inquirySub("/membr/info/memberFg/memberFg/getMemberPostpaid.sb", params, function () {});
  };

  // 후불회원 등록 해제
  $scope.regPrepaid = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.collectionView.items[i].useYn='N';
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/memberFg/memberFg/regPostpaid.sb", params, function(){
      $scope._broadcast('memberPostpaidCtrl');
    });
  };
}]);
/*
    미적용 그리드
 */
app.controller('memberPostpaidNoRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberPostpaidNoRegistCtrl', $scope, $http, false));

  // 그리드 조회
  $scope.$on("memberPostpaidNoRegistCtrl", function(event, data) {
    $scope.searchNoPostpaidList();
    event.preventDefault();
  });

  $scope.searchNoPostpaidList = function () {
    var scope = agrid.getScope('memberPostpaidCtrl');
    var params = {};
    params.membrNo = scope.membrNo;
    params.membrNm = scope.membrNm;
    params.membrClassCd = scope.membrClassCd;
    params.telNo = scope.telNo;
    params.storeCd = $("#postpaidStoreCd").val();
    params.listScale = 500;

    $scope._inquirySub("/membr/info/memberFg/memberFg/getMemberNoPostpaid.sb", params, function () {});
  };

  // 후불회원 등록
  $scope.regPostpaid = function() {
    // 파라미터 설정
    var params = new Array();
    for (var i = $scope.flex.itemsSource.itemCount - 1; i >= 0; i--) {
      if ($scope.flex.collectionView.items[i].gChk === true) {
        $scope.flex.collectionView.items[i].useYn='Y';
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/membr/info/memberFg/memberFg/regPostpaid.sb", params, function(){
      $scope._broadcast('memberPostpaidCtrl');
    });
  };
}]);