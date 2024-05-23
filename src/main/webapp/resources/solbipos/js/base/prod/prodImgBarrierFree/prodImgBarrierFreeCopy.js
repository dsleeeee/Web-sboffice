/****************************************************************
 *
 * 파일명 : prodImgBarrierFreeCopy.js
 * 설  명 : 베리어프리-이미지관리 이미지복사 팝업 JavaScript
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
 *  베리어프리-이미지관리 이미지복사 팝업 조회 그리드 생성
 */
app.controller('prodImgBarrierFreeCopyCtrl', ['$scope', '$http', function ($scope, $http) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodImgBarrierFreeCopyCtrl', $scope, $http, false));

    var gubun;

    // 조회조건 콤보박스 데이터 Set
    $scope._setComboData("orgImgFg", imgFg);
    $scope._setComboData("imgFg", imgFg);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
    };

    // <-- 검색 호출 -->
    $scope.$on("prodImgBarrierFreeCopyCtrl", function(event, data) {
        gubun = data;
        event.preventDefault();
    });
    // <-- //검색 호출 -->

    // 적용
    $scope.btnCopy = function () {

        // 키맵그룹이 없습니다.
        if ($scope.orgImgFgCombo.selectedIndex === $scope.imgFgCombo.selectedIndex) {
            s_alert.pop(messages["prodImgBarrierFreeCopy.imgFg.mgs"]);
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
                $scope._postJSONQuery.withPopUp("/base/prod/prodImgBarrierFree/prodImgBarrierFreeCopy/getProdImgBarrierFreeCopySave.sb", params, function () {

                    // 팝업 닫기
                    $scope.close();

                    var scope = agrid.getScope('prodImgBarrierFreeCtrl');
                    if(gubun === "I"){
                        scope.getProdImg($("#hdProdCd").val(), $("#hdProdNm").val());
                    } else {
                        scope.searchProdImgBarrierFreeList();
                    }

                });
            });
        }
    };

    // 닫기
    $scope.close = function(){
        $scope.orgImgFgCombo.selectedIndex = 0;
        $scope.imgFgCombo.selectedIndex = 0;

        $scope.wjProdImgBarrierFreeCopyLayer.hide();
    };

}]);