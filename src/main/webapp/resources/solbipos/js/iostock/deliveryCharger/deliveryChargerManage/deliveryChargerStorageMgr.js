/** 배송기사 관리 창고 추가 그리드 controller */
app.controller('dlvrStorageMgrCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrStorageMgrCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };


  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dlvrStorageMgrCtrl", function (event, data) {
    $scope.dlvrCd = data.dlvrCd;
    $scope.dlvrNm = data.dlvrNm;

    // 배송기사 창고 추가 팝업 오픈
    $scope.wjDlvrStorageMgrLayer.show(true);
    // 타이틀의 배송기사 명칭 세팅
    $("#storageMgrTitleDlvrNm").html("[" + $scope.dlvrNm + "]");

    $scope.searchStorageMgrList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 배송기사 관리 창고 추가 그리드 조회
  $scope.searchStorageMgrList = function () {
    // 파라미터
    var params    = {};
    params.dlvrCd = $scope.dlvrCd;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/iostock/deliveryCharger/deliveryChargerManage/dlvrStorageMgr/list.sb", params, "", false);
  };


  // 담당창고 추가 저장
  $scope.saveAddStore = function () {
    // 파라미터 설정
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];

      item.status = "U";
      item.dlvrCd = $scope.dlvrCd;
      params.push($scope.flex.collectionView.itemsEdited[i]);
    }

    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/iostock/deliveryCharger/deliveryChargerManage/dlvrStorageMgr/save.sb", params, function () {
      $scope.callbackSearch();
    });
  };


  // 저장 후 그리드 재조회
  $scope.callbackSearch = function () {
    $scope.searchStorageMgrList();

    // 배송기사 상세페이지 담당창고 그리드 조회
    var dlvrRegistScope = agrid.getScope('dlvrRegistCtrl');
    dlvrRegistScope.searchDlvrChgrStorageList();
  };

}]);
