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
