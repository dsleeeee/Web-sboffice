/****************************************************************
 *
 * 파일명 : prodImgDelete.js
 * 설  명 : 상품이미지삭제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.18     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

var imgFg = [
    {"name":"상품이미지","value":"001"},
    {"name":"KIOSK이미지","value":"002"},
    {"name":"DID이미지","value":"003"},
    {"name":"#2이미지","value":"004"}
];
app.controller('prodImgDeleteCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('prodImgDeleteCtrl', $scope, $http, false));
    var gubun;
    $scope._setComboData("imgFg", imgFg);

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("prodImgDeleteCtrl", function(event, data) {
        event.preventDefault();
    });

    // 적용
    $scope.btnDelete = function () {

        $scope._popConfirm( $scope.imgFgCombo.text + "를 삭제하시겠습니까?", function() {

            var params = {};
            params.imgFg = $scope.imgFgCombo.selectedValue;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withPopUp("/base/prod/prodImg/prodImg/prodImgDeleteAll.sb", params, function () {

                $scope.imgFgCombo.selectedIndex = 0;

                $scope.prodImgDeleteLayer.hide();

                var scope = agrid.getScope('prodImgCtrl');
                scope.searchProdList();

            });
        });
    };

    // 닫기
    $scope.close = function () {
        $scope.imgFgCombo.selectedIndex = 0;
    };

}]);