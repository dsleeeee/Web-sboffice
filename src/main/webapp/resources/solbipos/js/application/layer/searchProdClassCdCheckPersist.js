/****************************************************************
 *
 * 파일명 : searchProdClassCdCheckPersist.js
 * 설  명 : 상품정보관리 분류선택 팝업(체크박스 유지) JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2025.07.02     김유승      1.0
 *
 * **************************************************************/
/**
 * get application
 */

// 수동 체크 항목 저장
var manuallyCheckedItems = new Set();

// TreeView.checkedItems 업데이트 함수 (필요 시)
function updateTreeCheckedItems() {
    var treeElement = document.getElementById("treeProdClassCheckPersist");

    var tree = wijmo.Control.getControl(treeElement);
    tree.checkedItems = Array.from(manuallyCheckedItems);
}

// 체크박스 변경 처리 함수
function handleCheckboxChange(item, checked) {
    // 체크 선택 시
    if (checked) {

        // 현재 항목을 수동 체크 항목 목록에 추가
        manuallyCheckedItems.add(item);

        // 하위 항목(자식 노드)이 존재하는 경우
        if(item.items.length !== 0) {

            // 모든 항목이 체크되어 있는지 확인 true면 모두 포함, false면 하나라도 미포함
            var containsAll = item.items.every(childItem => manuallyCheckedItems.has(childItem));

            // 자식 항목 중 하나라도 미포함 시
            if (!containsAll) {
                item.items.forEach(childItem => {
                    // 모든 노드 탐색
                    document.querySelectorAll('.wj-node').forEach(node => {
                        const textSpan = node.querySelector('.wj-node-text');
                        // wj-node-text 값들과 하위분류 분류명 같을 시
                        if (textSpan && textSpan.textContent.trim() === childItem.prodClassNm) {
                            const checkbox = node.querySelector('.customCheckbox');
                            if (checkbox) {
                                checkbox.checked = true;
                                // checkbox.dispatchEvent(new Event('change', {bubbles: true}));
                                // 체크 항목 저장
                                manuallyCheckedItems.add(childItem);
                            }
                        }
                    });
                    // 하위 분류 -> 하위분류가 있을 시
                    if(childItem.items.length !== 0){
                        childItem.items.forEach(childrenItem => {
                            document.querySelectorAll('.wj-node').forEach(node => {
                                const textSpan = node.querySelector('.wj-node-text');
                                if (textSpan && textSpan.textContent.trim() === childrenItem.prodClassNm) {
                                    const checkbox = node.querySelector('.customCheckbox');
                                    if (checkbox) {
                                        checkbox.checked = true;
                                        // checkbox.dispatchEvent(new Event('change', {bubbles: true}));
                                        manuallyCheckedItems.add(childrenItem);
                                    }
                                }
                            });
                        });
                    }
                });
            }
        }
    } else { // 체크 해제 시
        // 하위 항목(자식 노드)이 존재하는 경우
        if(item.items.length !== 0) {

            // 모든 항목이 체크되어 있는지 확인 true면 모두 포함, false면 하나라도 미포함
            var containsAll = item.items.every(childItem => manuallyCheckedItems.has(childItem));

            // 하위분류가 전체 체크일 시
            if (containsAll) {

                // 현재 항목 다시 체크(체크 유지하기 위해)
                document.querySelectorAll('.wj-node').forEach(node => {
                    const textSpan = node.querySelector('.wj-node-text');
                    if (textSpan && textSpan.textContent.trim() === item.prodClassNm) {
                        const checkbox = node.querySelector('.customCheckbox');
                        if (checkbox) {
                            checkbox.checked = true;
                            // checkbox.dispatchEvent(new Event('change', {bubbles: true}));
                            manuallyCheckedItems.add(item);
                        }
                    }
                });

                // 모든 자식 항목 체크 해제제
               item.items.forEach(childItem => {
                    document.querySelectorAll('.wj-node').forEach(node => {
                        const textSpan = node.querySelector('.wj-node-text');
                        if (textSpan && textSpan.textContent.trim() === childItem.prodClassNm) {
                            const checkbox = node.querySelector('.customCheckbox');
                            if (checkbox) {
                                checkbox.checked = false;
                                // checkbox.dispatchEvent(new Event('change', {bubbles: false}));
                                manuallyCheckedItems.delete(childItem);
                            }
                        }
                    });
                   // 자식 항목의 자식 항목도 체크 해제 (2단계)
                    if(childItem.items.length !== 0){
                        childItem.items.forEach(childrenItem => {
                            document.querySelectorAll('.wj-node').forEach(node => {
                                const textSpan = node.querySelector('.wj-node-text');
                                if (textSpan && textSpan.textContent.trim() === childrenItem.prodClassNm) {
                                    const checkbox = node.querySelector('.customCheckbox');
                                    if (checkbox) {
                                        checkbox.checked = false;
                                        // checkbox.dispatchEvent(new Event('change', {bubbles: false}));
                                        manuallyCheckedItems.delete(childrenItem);
                                    }
                                }
                            });
                        });
                    }
                });
            }else{
                // 자식이 다 체크되지 않은 경우는 현재 항목만 수동 체크 목록에서 제거
                manuallyCheckedItems.delete(item);
            }
        }else{
            // 하위 항목이 없는 경우 단순히 항목 제거
            manuallyCheckedItems.delete(item);
        }
    }
    // 체크값 적용
    updateTreeCheckedItems();
}

app.controller('prodClassCheckPersistPopUpCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('prodClassCheckPersistPopUpCtrl', $scope, $http, false));

    // 상품분류 선택 정보
    $scope.selectedClass = "";
    $scope.setSelectedClass = function (data) {
        $scope.selectedClass = data;
    };
    $scope.getSelectedClass = function () {
        return $scope.selectedClass;
    };

    $scope.$on('prodClassCheckPersistPopUpCtrl', function (event, data) {
        // 화면에서 선택취소 클릭 후 진입 시
        setTimeout(function () {
            var treeHost = document.getElementById("treeProdClassCheckPersist");
            if (!treeHost) return;

            // TreeView가 없으면 생성
            if (!$scope.treeCheckPersist) {
                $scope.treeCheckPersist = new wijmo.nav.TreeView('#treeProdClassCheckPersist', {
                    displayMemberPath: 'prodClassNm',
                    childItemsPath: 'items',
                    expandOnClick: true,
                    isReadOnly: true,
                    showCheckboxes: true,
                    formatItem: function (s, e) {
                        // 기본 체크박스 숨김
                        const wijmoCheckbox = e.element.querySelector('input[type="checkbox"]');
                        if (wijmoCheckbox) {
                            wijmoCheckbox.style.display = 'none';
                        }

                        // 커스텀 체크박스 추가 (중복 방지)
                        if (!e.element.querySelector('.customCheckbox')) {
                            e.element.lastChild.insertAdjacentHTML(
                                'beforebegin',
                                '<input class="customCheckbox" type="checkbox"/> '
                            );

                            const customCheckbox = e.element.querySelector('.customCheckbox');

                            // 초기 체크 상태 반영
                            customCheckbox.checked = manuallyCheckedItems.has(e.dataItem);

                            // 체크박스 클릭 이벤트
                            customCheckbox.addEventListener('change', function () {
                                handleCheckboxChange(e.dataItem, customCheckbox.checked);
                            });
                        }
                    }
                });

                // collapse
                $scope.treeCheckPersist.loadedItems.addHandler(function (s, e) {
                    s.collapseToLevel(0);
                });

                // 선택 버튼 클릭
                $("#btnSelectCheckPersist").off('click').on('click', function () {
                    var arr = Array.from(manuallyCheckedItems).map(item => item.prodClassCd);
                    $scope.setSelectedClass(arr);
                });

                // 최초 1회만 트리 데이터 조회
                var param = {};
                $.postJSON("/treePopup/getProdClassTreeCheck.sb", param, function (result) {
                    $scope.treeCheckPersist.itemsSource = result.data.list;

                }, function (result) {
                    s_alert.pop(result.message);
                });
            } else {
                // 트리뷰가 이미 있을 경우, 이전 체크된 값 복원
                // setTimeout(function () {
                //     $scope.treeCheckPersist.checkedItems = $scope.checkedItems3_1 || [];
                // }, 100);
            }

            // 선택 취소 버튼 클릭 시
            if (data.selectCancelFgCheckPersist === 'Y') {
                if ($scope.treeCheckPersist) {

                    // TreeView안에 체크박스 전체 해제
                    document.querySelectorAll('#treeProdClassCheckPersist input[type="checkbox"]').forEach(function(checkbox) {
                        checkbox.checked = false;
                    });
                    // 수동체크항목 초기화
                    manuallyCheckedItems.clear();
                    $scope.treeCheckPersist.refresh();
                    // 선택 취소 값 초기화
                    $("#__selectCancelFgCheckPersist").val('');
                }
            }

        }, 100); // 렌더링 보장용 딜레이
    });

}]);