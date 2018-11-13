/****************************************************************
 *
 * 파일명 : popUpProdDetail.js
 * 설  명 : 상품정보관리 상세 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodDetailCtrl', $scope, $http, false));
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
  // 상품상세정보
  $scope.prodDetail = {};
  // 상품정보관리 그리드 조회
  $scope.$on("prodDetailCtrl", function(event, data) {
    // 파라미터
    var params = {};
    params = data;
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._postJSONQuery.withPopUp("/base/prod/prod/prod/detail.sb", params,
      function(response){
        // 상품정보
        var prodDetail = response.data.data.list;
        // 상품상세정보 Set
        $scope.prodDetail = prodDetail;
        var scope = agrid.getScope("prodCtrl");
        scope.setProdInfo(prodDetail);
        // 연결상품정보
        var linkedProdList = prodDetail.linkedProdList;
        var linkedProdInfo = "";
        if( linkedProdList.length > 0 ) {
          $.each(linkedProdList, function() {
            linkedProdInfo += '<tr><th><div class=\"impWrap\">' + messages['prod.prodCd'] + '<em class=\"imp\">*</em></div></th>'
              + '<td><a href=\"#\" class=\"link\">' + this.unitProdCd + '</a></td></tr>';
          });
        } else {
          linkedProdInfo = '<tr><th colspan=\"2\">' + messages['cmm.empty.data'] + '</th></tr>';
        }
        $("#_linkedProdInfo").html(linkedProdInfo);
      }
    );
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);
