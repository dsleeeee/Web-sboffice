/****************************************************************
 *
 * 파일명 : searchProdClassCdCheck.js
 * 설  명 : 상품정보관리 분류선택 팝업(체크박스) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.04.18     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
app.controller('prodClassCheckPopUpCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassCheckPopUpCtrl', $scope, $http, false));

    // 상품분류 선택 정보
    $scope.selectedClass = "";
    $scope.setSelectedClass = function (data) {
        $scope.selectedClass = data;
    };
    $scope.getSelectedClass = function () {
        return $scope.selectedClass;
    };

    $scope.$on('prodClassCheckPopUpCtrl', function (event, data) {
        // 선택 취소 버튼 클릭 시
        if(data.selectCancelFg !== undefined && data.selectCancelFg === 'Y'){
            // TreeView가 초기화된 후 체크 해제
            setTimeout(function () {
                var tree = wijmo.Control.getControl(document.getElementById("treeProdClassCheck"));
                if (tree) {
                    tree.checkedItems = [];
                }
                // 선택취소 값 초기화
                $("#_selectCancelFg").val('');
            }, 100); // 트리가 그려지는 시간 확보용 딜레이
        }
    });

}]);