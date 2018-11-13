/****************************************************************
 *
 * 파일명 : popUpProdClass.js
 * 설  명 : 상품정보관리 분류선택 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.12     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodClassCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodClassCtrl', $scope, $http, false));
  // 트리데이터
  $scope.items = [];
  // 상품분류 트리 조회
  $scope.$on('prodClassCtrl', function (event, data) {
    // data 조회하지 않고 상세정보와 동일하므로 파라미터로 처리
    $scope.$broadcast('loadingPopupActive');
    var params = {};
    $scope._postJSONQuery.withOutPopUp('/base/prod/prod/prod/getProdClassTree.sb', params, function (response) {
      if (response.data.status === 'OK') {
        if (response.data.data.length > 0) {
          // 트리형 데이터로 변환
          var list = response.data.data;
          var rootNodes = [];
          var traverse = function (nodes, item, index) {
            if (nodes instanceof Array) {
              return nodes.some(function (node) {
                if (node.prodClassCd === item.pProdClassCd) {
                  node.children = node.children || [];
                  return node.children.push(list.splice(index, 1)[0]);
                }

                return traverse(node.children, item, index);
              });
            }
          };
          while (list.length > 0) {
            list.some(function (item, index) {
              if (item.pProdClassCd === '00000') {
                return rootNodes.push(list.splice(index, 1)[0]);
              }
              return traverse(rootNodes, item, index);
            });
          }
          // $scope.items = JSON.parse(JSON.stringify(rootNodes, null, ''));
          $scope.items = JSON.parse(JSON.stringify(rootNodes, null, ''));
        }
      }
      $scope.$broadcast('loadingPopupInactive');
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  $scope.navTo = function(treeView) {
    var scope = agrid.getScope("prodModifyCtrl");
    scope.setSelectedClass(treeView.selectedItem.prodClassCd);
  };

}]);
