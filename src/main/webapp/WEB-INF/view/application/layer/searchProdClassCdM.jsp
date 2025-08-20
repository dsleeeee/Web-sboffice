<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="text" class="sb-input w70" id="<c:out value="${param.targetId}ProdClassMCdNm"/>" ng-click="<c:out value="${param.targetId}"/>ProdClassMShow()" style="float: left;"
       placeholder="<s:message code="prod.prodClass" /> 선택" readonly/>
<input type="hidden" id="<c:out value="${param.targetId}ProdClassMCd"/>"/>
<button type="button" class="btn_skyblue fl mr5" style="margin-left: 5px;" id="<c:out value="${param.targetId}ProdClassMSelectCancelBtn"/>"><s:message code="cmm.selectCancel"/></button>

<%-- 팝업 부분 설정 - width 는 강제 해주어야함.. 해결방법? 확인 필요 : 20180829 노현수 --%>
<wj-popup id="wj<c:out value="${param.targetId}"/>ProdClassMLayer" control="wj<c:out value="${param.targetId}"/>ProdClassMLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:400px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.layer.prodClass"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
    </div>
    <div class="wj-dialog-body sc2" style="height: 300px;" ng-controller="<c:out value="${param.targetId}"/>ProdClassMCtrl">
        <%-- 상품분류 트리 --%>
        <div class="theTreeAll_cls" id="tree<c:out value="${param.targetId}"/>ProdClassMClsTree" style="height:auto;overflow: hidden; "></div>
    </div>
    <div class="wj-dialog-footer">
        <button class="btn wj-hide-apply btn_blue" id="<c:out value="${param.targetId}ProdClassMSelectBtn"/>"><s:message code="cmm.chk"/></button>
        <button class="btn wj-hide btn_blue"><s:message code="cmm.close"/></button>
    </div>
</wj-popup>

<script type="text/javascript">

    $(document).ready(function () {

        <%-- 분류 선택취소 버튼 클릭 --%>
        $("#${param.targetId}ProdClassMSelectCancelBtn").click(function () {
            $("#${param.targetId}ProdClassMCd").val("");
            $("#${param.targetId}ProdClassMCdNm").val("");
        });

        <%-- 분류선택 팝업 선택 버튼 클릭 --%>
        $("#${param.targetId}ProdClassMSelectBtn").click(function () {
            var vScope = agrid.getScope("${param.targetId}ProdClassMCtrl");
            // 선택 분류 부모창에 셋팅
            vScope.selectProdClassM();
        });

    });

    /**
     * get application
     */
    var app = agrid.getApp();

    app.controller('${param.targetId}ProdClassMCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.targetId = "${param.targetId}";
        $scope.loadYn = ""; // 분류 팝업 오픈 여부 구분자

        $scope.$on($scope.targetId + 'ProdClassMCtrl', function (event, data) {

            // 팝업 오픈
            eval('$scope.wj' + $scope.targetId + 'ProdClassMLayer.show(true)');

            // 분류 팝업 최초 오픈인 경우만 실행(트리뷰 셋팅은 최초에만 실행되게 하기 위해)
            if ($scope.loadYn === "") {

                // 분류 팝업 오픈됨 구분자 넣기
                $scope.loadYn = "Y";

                // 분류 트리뷰 생성
                $scope.setProdClassMClsTree();
            }

            // 선택한 분류가 없는 경우, 트리뷰 체크 해제
            if ($("#" + $scope.targetId + "ProdClassMCd").val() === "") {
                // TreeView가 초기화된 후 체크 해제
                setTimeout(function () {
                    var tree = wijmo.Control.getControl(document.getElementById("tree" + $scope.targetId + "ProdClassMClsTree"));
                    if (tree) {
                        tree.checkedItems = [];
                    }
                }, 100); // 트리가 그려지는 시간 확보용 딜레이
            }
        });

        // 분류 트리뷰 생성
        $scope.setProdClassMClsTree = function () {

            $scope.tree = new wijmo.nav.TreeView('#tree' + $scope.targetId + 'ProdClassMClsTree', {
                displayMemberPath: 'prodClassNm',
                childItemsPath: 'items',
                expandOnClick: true,
                isReadOnly: true,
                showCheckboxes: true
            });

            $scope.view = new wijmo.collections.CollectionView();

            <%-- 트리 접기 --%>
            $scope.tree.loadedItems.addHandler(function (s, e) {
                s.collapseToLevel(0);
            });

            // 초기화
            $scope.tree.itemsSource = [];
            $scope.tree.refresh();

            // 트리 데이터 조회
            var param = {};
            $.postJSON("/treePopup/getProdClassTreeCheck.sb", param, function (result) {
                $scope.tree.itemsSource = result.data.list;
            },
            function (result) {
                s_alert.pop(result.message);
            });
        };

        // 선택 분류 부모창에 셋팅
        $scope.selectProdClassM = function () {

            var arr = [];
            for (var i = 0; i < $scope.tree.checkedItems.length; i++) {
                arr.push($scope.tree.checkedItems[i].prodClassCd);
            }

            var prodClassCd = arr;
            var params = {};
            params.prodClassCd = prodClassCd[0];

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._postJSONQuery.withPopUp("/treePopup/getProdClassCdNmCheck.sb", params,
                function (response) {
                    $("#" + $scope.targetId + "ProdClassMCd").val(prodClassCd);
                    $("#" + $scope.targetId + "ProdClassMCdNm").val((isEmptyObject(response.data.data) ? "" : response.data.data) + (prodClassCd.length - 1 > 0 ? " 외 " + (prodClassCd.length - 1).toString() : ""));
                    //eval('$scope.wj' + $scope.targetId + 'ProdClassMLayer.hide(true)');
                }
            );
        };

    }]);
</script>
