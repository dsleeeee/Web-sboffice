/****************************************************************
 *
 * 파일명 : prodModifyView.js
 * 설  명 : 상품정보관리 수정 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodModifyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodModifyCtrl', $scope, $http, false));
  // 상품정보
  $scope.prodModifyInfo = {};
  $scope.setProdModifyInfo = function(data){
    $scope.prodModifyInfo = data;
  };
  $scope.getProdModifyInfo = function(){
    return $scope.prodModifyInfo;
  };
  // 상품정보 조회
  $scope.$on("prodModifyCtrl", function(event, data) {
    // data 조회하지 않고 상세정보와 동일하므로 파라미터로 처리
    $scope.$broadcast('loadingPopupActive');
    // 상품정보 set
    $scope.setProdModifyInfo(data);
    // 메시지창 닫기
    setTimeout(function() {
      $scope.$broadcast('loadingPopupInactive');
    }, 30);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
          function(response){
            var prodInfo = $scope.getProdModifyInfo();
            prodInfo.prodClassCd = prodClassCd;
            prodInfo.prodClassCdNm = response.data.data;
            $scope.setProdModifyInfo(prodInfo);
          }
        );
      }
    });
  };

}]);
