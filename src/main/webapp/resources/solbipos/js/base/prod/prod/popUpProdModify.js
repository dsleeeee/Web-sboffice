/****************************************************************
 *
 * 파일명 : popUpProdModify.js
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
  angular.extend(this, new RootController('prodModifyCtrl', $scope, $http, true));
  // 단가구분 콤보박스
  $scope._getComboDataQuery('008', 'prodTypeFgComboData');
  // 판매상품여부 콤보박스
  $scope._getComboDataQuery('091', 'saleProdYnComboData');
  // 주문상품구분 콤보박스
  $scope._getComboDataQuery('092', 'poProdFgComboData');
  // 주문단위 콤보박스
  $scope._getComboDataQuery('093', 'poUnitFgComboData');
  // 과세여부 콤보박스
  $scope._getComboDataQuery('039', 'vatFgComboData');
  // 재고관리여부 콤보박스
  $scope._getComboDataQuery('067', 'stockProdYnComboData');
  // 사용여부 콤보박스
  $scope._getComboDataQuery('067', 'useYnComboData');
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 환급적용여부 콤보박스
  $scope._getComboDataQuery('090', 'refApplyYnComboData');

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

}]);
