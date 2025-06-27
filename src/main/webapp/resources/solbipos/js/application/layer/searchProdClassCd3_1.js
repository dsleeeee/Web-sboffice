/****************************************************************
 *
 * 파일명 : searchProdClassCd3.js
 * 설  명 : 상품정보관리 분류선택 팝업3_1 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.27     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
// 미사용 - 로직 jsp로 옮김
app.controller('prodClassPopUp3_1Ctrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassPopUp3_1Ctrl', $scope, $http, false));

    // 상품분류 선택 정보
    $scope.selectedClass = "";
    $scope.setSelectedClass = function (data) {
        $scope.selectedClass = data;
    };
    $scope.getSelectedClass = function () {
        return $scope.selectedClass;
    };

    $scope.$on('prodClassPopUp3_1Ctrl', function (event, data) {
        if(data.selectCancelFg3_1 === 'Y'){
            // TreeView가 초기화된 후 체크 해제
            setTimeout(function () {
                var tree3_1 = wijmo.Control.getControl(document.getElementById("treeProdClassEx"));
                console.log(tree3_1);
                if (tree3_1) {
                    tree3_1.checkedItems = [];
                }
                $("#selectCancelFg3_1").val('');
            }, 100); // 트리가 그려지는 시간 확보용 딜레이
        }
    });

}]);