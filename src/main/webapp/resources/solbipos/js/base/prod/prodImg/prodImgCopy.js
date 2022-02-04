/****************************************************************
 *
 * 파일명 : prodImgCopy.js
 * 설  명 : 상품이미지복제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.01.13     권지현      1.0
 *
 * **************************************************************/

var app = agrid.getApp();

var imgFg = [
    {"name":"상품이미지","value":"001"},
    {"name":"KIOSK이미지","value":"002"},
    {"name":"DID이미지","value":"003"},
    {"name":"#2이미지","value":"004"}
];
app.controller('prodImgCopyCtrl', ['$scope', '$http', function ($scope, $http) {

    angular.extend(this, new RootController('prodImgCopyCtrl', $scope, $http, false));
    var gubun;
    $scope._setComboData("orgImgFg", imgFg);
    $scope._setComboData("imgFg", imgFg);

    // 팝업 오픈 시, 매장리스트 조회
    $scope.$on("prodImgCopyCtrl", function(event, data) {
        gubun = data;
        event.preventDefault();
    });

    // 적용
    $scope.btnCopy = function () {

        // 키맵그룹이 없습니다.
        if ($scope.orgImgFgCombo.selectedIndex === $scope.imgFgCombo.selectedIndex) {
            s_alert.pop(messages["prodImg.imgFg.mgs"]);
            return false;
        } else {
            $scope._popConfirm( $scope.orgImgFgCombo.text + "를 " +
                                $scope.imgFgCombo.text + "에 복사하시겠습니까?", function() {

                var params = {};
                params.orgImgFg = $scope.orgImgFgCombo.selectedValue;
                params.imgFg = $scope.imgFgCombo.selectedValue;
                params.gubun = gubun;
                if(gubun === "I"){
                    params.prodCd = $("#hdProdCd").val();
                }

                // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
                $scope._postJSONQuery.withPopUp("/base/prod/prodImg/prodImg/prodImgCopy.sb", params, function () {

                    $scope.orgImgFgCombo.selectedIndex = 0;
                    $scope.imgFgCombo.selectedIndex = 0;

                    $scope.prodImgCopyLayer.hide();

                    var scope = agrid.getScope('prodImgCtrl');
                    if(gubun === "I"){
                        scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());
                    } else {
                        scope.searchProdList();
                    }

                });
            });
        }
    };

    // 닫기
    $scope.close = function () {
        $scope.orgImgFgCombo.selectedIndex = 0;
        $scope.imgFgCombo.selectedIndex = 0;
    };

}]);