/****************************************************************
 *
 * 파일명 : prodImgBarrierFreeDelete.js
 * 설  명 : 베리어프리-이미지관리 이미지전체삭제 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2024.05.17     김설아      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 이미지구분 DropBoxDataMap
var imgFg = [
    {"name":"고대비-그린","value":"006"},
    {"name":"고대비-옐로우","value":"007"},
    {"name":"고대비-화이트","value":"008"}
];

/**
 *  베리어프리-이미지관리 이미지전체삭제 팝업 조회 그리드 생성
 */
app.controller('prodImgBarrierFreeDeleteCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodImgBarrierFreeDeleteCtrl', $scope, $http, false));

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("imgFg", imgFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("prodImgBarrierFreeDeleteCtrl", function(event, data) {
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 삭제
    $scope.btnDelete = function () {

        $scope._popConfirm( $scope.imgFgCombo.text + "를 삭제하시겠습니까?", function() {

            var params = {};
            params.imgFg = $scope.imgFgCombo.selectedValue;

            // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withPopUp("/base/prod/prodImgBarrierFree/prodImgBarrierFreeDelete/getProdImgBarrierFreeDeleteAll.sb", params, function () {

                // 닫기
                $scope.close();

                var scope = agrid.getScope('prodImgBarrierFreeCtrl');
                scope.searchProdImgBarrierFreeList();

            });
        });
    };

    // 닫기
    $scope.close = function(){
        $scope.imgFgCombo.selectedIndex = 0;

        $scope.wjProdImgBarrierFreeDeleteLayer.hide();
    };

}]);