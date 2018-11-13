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
  angular.extend(this, new RootController('prodModifyCtrl', $scope, $http, false));
  // 사용여부 콤보는 별도 조회하지 않고 고정적으로 사용
  var useYnComboData = [
    {"name": "선택", "value": ""},
    {"name": "미사용", "value": "N"},
    {"name": "사용", "value": "Y"}
  ];
  // 사용여부를 쓰는 콤보박스의 데이터
  $scope._setComboData('useYnComboData', useYnComboData);
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
  // 품절여부 콤보박스
  $scope._getComboDataQuery('094', 'soldOutYnComboData');
  // 세트상품구분 콤보박스
  $scope._getComboDataQuery('095', 'setProdFgComboData');
  // 환급적용여부 콤보박스
  // $scope._getComboDataQuery('090', 'refApplyYnComboData');
  // 봉사료포함여부 콤보박스
  $scope._getComboDataQuery('058', 'prodTipYnComboData');


  // 상품정보
  $scope.prodModifyInfo = {};
  $scope.setProdModifyInfo = function(data){
    $scope.prodModifyInfo = data;
  };
  $scope.getProdModifyInfo = function(){
    return $scope.prodModifyInfo;
  };
  // 상품분류 선택 정보
  $scope.selectedClass = {};
  $scope.setSelectedClass = function(data){
    $scope.selectedClass = data;
  };
  $scope.getSelectedClass = function(){
    return $scope.selectedClass;
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
    var popUp = $scope.prodClassLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var params = {};
        params.prodClassCd = $scope.getSelectedClass();
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/base/prod/prod/prod/getProdClassCdNm.sb", params,
          function(response){
            var prodInfo = $scope.getProdModifyInfo();
            prodInfo.prodClassCd = $scope.getSelectedClass();
            prodInfo.prodClassCdNm = response.data.data;
            $scope.setProdModifyInfo(prodInfo);
          }
        );
      }
    });
    event.preventDefault();
  };

  // 화면 ready 된 후 설정
  angular.element(document).ready(function () {
    var popUp = $scope.prodClassLayer;
    // 상품분류 팝업 핸들러 추가
    popUp.shown.addHandler(function (s) {
      setTimeout(function () {
        // 트리데이터 조회
        $scope._broadcast('prodClassCtrl');
      }, 50);
    });
  });

}]);
