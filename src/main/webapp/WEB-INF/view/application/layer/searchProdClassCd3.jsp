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
<wj-popup control="<c:out value="${param.targetId}"/>Layer3" show-trigger="Click" hide-trigger="Click" style="display: none;width:400px;" onload="window.self.focus();">
    <div class="wj-dialog wj-dialog-columns" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.prodClass"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 300px;">
            <%-- 상품분류 트리 --%>
            <div class="theTreeAll_cls" id="treeProdClass${param.targetId}" style="height:auto;overflow: hidden; "></div>
        </div>
        <div class="wj-dialog-footer">
            <button class="btn wj-hide-apply btn_blue" id="btnSelect"><s:message code="cmm.chk"/></button>
            <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
        </div>
    </div>
</wj-popup>
<script>

    <%-- 상품분류v2.1 --%>


    $(document).ready(function () {

        <%-- 메뉴 트리 생성 --%>
        var tree = new wijmo.nav.TreeView('#treeProdClass${param.targetId}', {
            displayMemberPath: 'prodClassNm',
            childItemsPath: 'items',
            expandOnClick: true,
            isReadOnly: true,
            showCheckboxes: true
        });

        var view = new wijmo.collections.CollectionView();

        <%-- 트리 체크박스 초기화 --%>
        tree.loadedItems.addHandler(function (s, e) {
            s.collapseToLevel(0);
            //view = new wijmo.collections.CollectionView(tree.checkedItems);
        });

        <%-- 트리에 아이템 체크 상태가 바뀌었을 때 CollectionView에 반영 --%>
        /*tree.checkedItemsChanged.addHandler(function (s, e) {

            view.itemsAdded.clear();
            view.itemsRemoved.clear();

            for (var i = 0; i < tree.checkedItems.length; i++) {
                if (!view.contains(tree.checkedItems[i])) {
                    view.itemsAdded.push(tree.checkedItems[i]);
                }
            }

            var viewNew = new wijmo.collections.CollectionView(tree.checkedItems);

            for (var i = 0; i < view.items.length; i++) {
                if (!viewNew.contains(view.items[i])) {
                    view.itemsRemoved.push(view.items[i]);
                }
            }
        });*/

        // 초기화
        tree.itemsSource = new Array();
        tree.refresh();

        // 트리 데이터 조회
        var param = {};
        $.postJSON("/popup/getProdClassTree3.sb", param, function (result) {
                tree.itemsSource = result.data.list;
            },
            function (result) {
                s_alert.pop(result.message);
            });

        // 선택 버튼 클릭
        $("#btnSelect").click(function (e) {

            var arr = new Array();

            for (var i = 0; i < tree.checkedItems.length; i++) {
                console.log(tree.checkedItems[i].prodClassCd);
                arr.push(tree.checkedItems[i].prodClassCd);
            }

            // set data
            var vScope = agrid.getScope('${param.targetId}Ctrl');
            vScope.setSelectedClass(arr);
        });

        // 선택취소 버튼 클릭
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

            var popUp = $scope[targetId + 'Layer3'];
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