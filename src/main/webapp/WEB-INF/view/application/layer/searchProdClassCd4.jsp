<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="text" class="sb-input w70" id="<c:out value="${param.targetId}CdNm"/>" style="float: left;" ng-click="_pageView('<c:out value="${param.targetId}"/>Ctrl', 1)"
       placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"  disabled />
<input type="hidden" id="<c:out value="${param.targetId}Check"/>"/>
<button type="button" class="btn_skyblue fl mr5" id="<c:out value="${param.targetId}"/>btnCancelClassCd" style="margin-left: 5px;"><s:message code="cmm.selectCancel"/></button>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup control="<c:out value="${param.targetId}"/>Layer4" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;" onload="window.self.focus();">
    <div class="wj-dialog wj-dialog-columns" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.prodClass"/>
            <span class="s13">
                (체크유지)
            </span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 300px;">
            <%-- 상품분류 트리 --%>
            <div class="theTreeAll_cls" id="treeProdClass${param.targetId}" style="height:auto;overflow: hidden; width:auto;"></div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue" id="btnSelect4"><s:message code="cmm.chk"/></button>
            <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>
<script>

    <%-- 상품분류v3.1 --%>

    $(document).ready(function () {

        var manuallyCheckedItems = new Set(); // 수동 체크 항목 저장

        // TreeView.checkedItems 업데이트 함수 (필요 시)
        function updateTreeCheckedItems() {
            tree.checkedItems = Array.from(manuallyCheckedItems);
        }

        // 체크박스 변경 처리 함수
        function handleCheckboxChange(item, checked) {
            if (checked) {

                manuallyCheckedItems.add(item);

                // 하위분류가 있을 시
                if(item.items.length !== 0) {

                    var containsAll = item.items.every(childItem => manuallyCheckedItems.has(childItem)); // true면 모두 포함, false면 하나라도 미포함

                    if (!containsAll) {
                        item.items.forEach(childItem => {
                            document.querySelectorAll('.wj-node').forEach(node => {
                                const textSpan = node.querySelector('.wj-node-text');
                                if (textSpan && textSpan.textContent.trim() === childItem.prodClassNm) {
                                    const checkbox = node.querySelector('.customCheckbox');
                                    if (checkbox) {
                                        checkbox.checked = true;
                                        // checkbox.dispatchEvent(new Event('change', {bubbles: true}));
                                        manuallyCheckedItems.add(childItem);
                                    }
                                }
                            });
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
            } else {
                // 하위분류가 있을 시
                if(item.items.length !== 0) {

                    var containsAll = item.items.every(childItem => manuallyCheckedItems.has(childItem)); // true면 모두 포함, false면 하나라도 미포함

                    // 하위분류가 전체 체크일 시
                    if (containsAll) {

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
                        manuallyCheckedItems.delete(item);
                    }
                }else{
                    manuallyCheckedItems.delete(item);
                }
                <%--console.log(`❌ 체크 해제: ${item.prodClassNm}`);--%>
            }
            // 체크값 적용
            updateTreeCheckedItems();
        }

        var tree = new wijmo.nav.TreeView('#treeProdClass${param.targetId}', {
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

        // 초기 트리 세팅
        tree.loadedItems.addHandler(function (s, e) {
            s.collapseToLevel(0);
        });

        // 초기화
        tree.itemsSource = [];
        tree.refresh();

        // 트리 데이터 불러오기
        var param = {};
        $.postJSON("/popup/getProdClassTree3.sb", param, function (result) {
                tree.itemsSource = result.data.list;
                // console.log(result.data.list);
            },
            function (result) {
                s_alert.pop(result.message);
            });

        // ✅ 선택 버튼 클릭 시 체크된 prodClassCd만 전송
        $("#btnSelect4").click(function (e) {
            var arr = Array.from(manuallyCheckedItems).map(item => item.prodClassCd);
            var vScope = agrid.getScope('${param.targetId}Ctrl');
            vScope.setSelectedClass(arr);
        });

        // 선택취소 버튼
        $("#${param.targetId}btnCancelClassCd").click(function (e) {
            $("#${param.targetId}Cd").val('');
            $("#${param.targetId}CdNm").val("분류조회 선택");
            $("#${param.targetId}Check").val('Cancel');
        });

    });

    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

        var targetId = '${param.targetId}';
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, false));

        // 상품분류 선택 정보
        $scope.selectedClass = "";
        $scope.setSelectedClass = function (data) {
            $scope.selectedClass = data;
        };
        $scope.getSelectedClass = function () {
            return $scope.selectedClass;
        };

        $scope.$on(targetId + 'Ctrl', function (event, data) {

            var popUp = $scope[targetId + 'Layer4'];
            popUp.show(true, function (s) {

                // 선택 버튼 눌렀을때만
                if (s.dialogResult === "wj-hide-apply") {
                    var prodClassCd = $scope.getSelectedClass();
                    var params = {};
                    params.prodClassCd = prodClassCd[0];
                    // 조회 수행 : 조회URL, 파라미터, 콜백함수
                    $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
                        function(response){
                            $("#${param.targetId}Cd").val(prodClassCd);
                            var srchProdClassCd = (isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : "");
                            $("#${param.targetId}CdNm").val(srchProdClassCd);
                        }
                    );
                }
            });

            // 화면에서 선택취소 클릭 후 진입 시
            if($("#${param.targetId}Check").val() === 'Cancel'){
                var treeElement = document.getElementById("treeProdClass${param.targetId}");

                // 2. Wijmo TreeView 인스턴스 가져오기
                var tree = wijmo.Control.getControl(treeElement);


                // 3. 체크 상태 해제
                if (tree) {
                    tree.itemsSource = [];
                    tree.refresh();

                    // 데이터를 다시 받아와서 트리 재로드
                    var param = {};
                    $.postJSON("/popup/getProdClassTree3.sb", param, function (result) {
                            tree.itemsSource =  result.data.list;  // 새 데이터로 트리 갱신
                            tree.refresh();
                        },
                        function (result) {
                            s_alert.pop(result.message);
                        });
                }

                // 선택취소 여부 초기화
                $("#${param.targetId}Check").val('');

            }

        });

    }]);



</script>