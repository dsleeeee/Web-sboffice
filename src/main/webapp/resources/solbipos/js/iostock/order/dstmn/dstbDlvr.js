/** 배송기사 controller */
app.controller('dstbDlvrCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dstbDlvrCtrl', $scope, $http, true));

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("dstbDlvrCtrl", function (event, data) {
    $scope.strSlipNo = data.strSlipNo;
    $scope.strDlvrCd = data.strDlvrCd;

    $scope.wjDstbDlvrLayer.show(true);

    $scope.searchDlvrList();

    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });


  // 배송기사 리스트 조회
  $scope.searchDlvrList = function () {
    // 파라미터
    var params    = {};
    params.slipNo = $scope.strSlipNo;
    params.dlvrCd = $scope.strDlvrCd;
    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
    	params.sid = document.getElementsByName('sessionId')[0].value;
    }
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/iostock/order/dstmn/dstbDlvr/list.sb", params);
  };


  // 배송기사별 분배지시서 인쇄
  $scope.dlvrReport = function (reportFg) {
    var strDlvrCd = '';
    if (!$scope.flex.collectionView) {
      $scope.flex.itemsSource = new wijmo.collections.CollectionView();
    }
    for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
      var item = $scope.flex.collectionView.itemsEdited[i];
      if (item.gChk === true) {
        strDlvrCd += (strDlvrCd === '' ? '' : ',') + item.dlvrCd;
      }
    }

    var params       = {};
    params.strSlipNo = $scope.strSlipNo;
    params.strDlvrCd = strDlvrCd;

    // 배송기사별 상품-매장 인쇄
    if(reportFg === 'prodStore') {
      $scope._broadcast('dstbDlvrProdStoreReportCtrl', params);
    }
    // 배송기사별 매장-상품 인쇄
    else if(reportFg === 'storeProd') {
      $scope._broadcast('dstbDlvrStoreProdReportCtrl', params);
    }
  }


}]);
