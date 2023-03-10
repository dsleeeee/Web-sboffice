/****************************************************************
 *
 * 파일명 : envstHqMsMng.js
 * 설  명 : 환경설정사용설정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.03.06     권지현      1.0           
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  버전목록 그리드
 **********************************************************************/
app.controller('regEnvstCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('regEnvstCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {

  };

  // 조회 버튼 클릭
  $scope.$on("regEnvstCtrl", function(event, data) {
    $scope.getVersionList();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionList = function(){
    if($("#selectHqStoreCd").val() !== ""){
      var params = {};
      params.orgnCd = $("#selectHqStoreCd").val();
      params.orgnFg = $("#selectHqStoreFg").val();
      console.log(params);

      $scope._inquiryMain("/sys/cd/envstHqMsMng/envstHqMsMng/getRegEnvstList.sb", params, function() {
        $scope._broadcast("noRegEnvstCtrl");
      });
    } else {
      $scope._popMsg(messages["envstHqMsMng.orgn.none.msg"]);
      return false;
    }
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.selectHqStoreShow = function () {
    $scope._broadcast('selectHqStoreCtrl');
  };

  // 미등록 환경설정으로 등록(등록 환경설정 테이블에서 삭제)
  $scope.del = function (){

    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/cd/envstHqMsMng/envstHqMsMng/deleteEnvstHqMsMng.sb", params, function(){
      $scope.getVersionList();
      $scope._broadcast("noRegEnvstCtrl");
    });

  }
}]);


/**********************************************************************
 *  버전수신매장 그리드
 **********************************************************************/
app.controller('noRegEnvstCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('noRegEnvstCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 조회 버튼 클릭
  $scope.$on("noRegEnvstCtrl", function(event, data) {

    $scope.getVersionStoreList();
    event.preventDefault();
  });

  // 버전수신정보 목록 조회
  $scope.getVersionStoreList = function(){
    var params = {};
    params.orgnCd = $("#selectHqStoreCd").val();
    params.orgnFg = $("#selectHqStoreFg").val();
    console.log("no");
    console.log(params);

    $scope._inquiryMain("/sys/cd/envstHqMsMng/envstHqMsMng/getNoRegEnvstList.sb", params, function() {
    });
  };

  // 등록 환경설정으로 등록(등록 환경설정 테이블에 추가)
  $scope.save = function (){

    var params = [];

    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/sys/cd/envstHqMsMng/envstHqMsMng/saveEnvstHqMsMng.sb", params, function(){
      $scope._broadcast("regEnvstCtrl");
      $scope.getVersionStoreList();
    });

  }
}]);
