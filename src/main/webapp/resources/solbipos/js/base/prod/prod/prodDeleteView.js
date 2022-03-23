/****************************************************************
 *
 * 파일명 : prodDeleteView.js
 * 설  명 : 상품삭제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.03.08     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  상품삭제 팝업생성
 */
app.controller('prodDeleteCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodDeleteCtrl', $scope, $http, true));

    // 선택상품
    $scope.selectedProd;
    $scope.setSelectedProd = function(prod) {
        $scope.selectedProd = prod;
    };
    $scope.getSelectedProd = function(){
        return $scope.selectedProd;
    };
    
    $scope.$on("prodDeleteCtrl", function(event, data) {
        // 부모창에서 선택한 상품정보 변수에 셋팅
        $scope.setSelectedProd(data);
        event.preventDefault();
    });

    // 선택상품삭제
    $scope.selectProdDelete = function () {

        if($scope.getSelectedProd().length <= 0) {
            $scope._popMsg(messages["prod.unSelected.msg"]); // 선택한 상품이 없습니다.
            return false;
        }

        // 선택한 상품을 삭제하시겠습니까?
        $scope._popConfirm(messages["prod.selectDelete.msg"], function() {

            var params = $scope.getSelectedProd();

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/base/prod/prod/prod/selectProdDelete.sb", params, function () {

                // 부모창 재조회 및 팝업닫기
                var scope = agrid.getScope('prodCtrl');
                scope.searchProdList();
                $scope.prodDeleteLayer.hide(true);
            });
        });

    };

    // 전체상품삭제
    $scope.allProdDelete = function () {

        // 모든 상품을 삭제하시겠습니까?
        $scope._popConfirm(messages["prod.allDelete.msg"], function() {

            var params = {};

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONSave.withPopUp("/base/prod/prod/prod/allProdDelete.sb", params, function () {

                // 부모창 재조회 및 팝업닫기
                var scope = agrid.getScope('prodCtrl');
                scope.searchProdList();
                $scope.prodDeleteLayer.hide(true);
            });
        });
    };

}]);