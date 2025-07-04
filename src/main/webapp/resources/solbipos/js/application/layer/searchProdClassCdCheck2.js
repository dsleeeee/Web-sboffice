/****************************************************************
 *
 * 파일명 : searchProdClassCdCheck2.js
 * 설  명 : 상품정보관리 분류선택 팝업2(체크박스) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.06.27     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */
app.controller('prodClassCheckPopUpCtrl2', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassCheckPopUpCtrl2', $scope, $http, false));

    // 상품분류 선택 정보
    $scope.selectedClass = "";
    $scope.setSelectedClass = function (data) {
        $scope.selectedClass = data;
    };
    $scope.getSelectedClass = function () {
        return $scope.selectedClass;
    };

    $scope.$on('prodClassCheckPopUpCtrl2', function (event, data) {

        setTimeout(function () {
            var treeHost = document.getElementById("treeProdClassCheck2");
            if (!treeHost) return;

            // TreeView가 없으면 생성
            if (!$scope.treeCheck2) {
                $scope.treeCheck2 = new wijmo.nav.TreeView(treeHost, {
                    displayMemberPath: 'prodClassNm',
                    childItemsPath: 'items',
                    expandOnClick: true,
                    isReadOnly: true,
                    showCheckboxes: true
                });

                $scope.treeCheck2.loadedItems.addHandler(function (s, e) {
                    s.collapseToLevel(0);
                });

                // 선택 버튼 클릭
                $("#btnSelectCheck2").off('click').on('click', function () {
                    var arr = [];
                    for (var i = 0; i < $scope.treeCheck2.checkedItems.length; i++) {
                        arr.push($scope.treeCheck2.checkedItems[i].prodClassCd);
                    }

                    // 체크된 값 기억
                    $scope.checkedItems3_1 = $scope.treeCheck2.checkedItems.slice();

                    $scope.setSelectedClass(arr);
                });

                // 최초 1회만 트리 데이터 조회
                var param = {};
                $.postJSON("/treePopupTwo/getProdClassTreeCheck2.sb", param, function (result) {
                    $scope.treeCheck2.itemsSource = result.data.list;

                }, function (result) {
                    s_alert.pop(result.message);
                });
            } else {
                // 트리뷰가 이미 있을 경우, 이전 체크된 값 복원
                // setTimeout(function () {
                //     $scope.treeCheck2.checkedItems = $scope.checkedItems3_1 || [];
                // }, 100);
            }

            // 선택 취소 버튼 클릭 시
            if (data.selectCancelFg2 === 'Y') {
                if ($scope.treeCheck2) {
                    $scope.treeCheck2.checkedItems = [];
                }
                $scope.checkedItems3_1 = [];
                // 선택 취소 값 초기화
                $("#_selectCancelFg2").val('');
            }

        }, 100); // 렌더링 보장용 딜레이
    });

}]);