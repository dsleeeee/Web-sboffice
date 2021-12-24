<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="hidden" id="<c:out value="${param.targetId}NmOnly"/>"/>
<input type="text" id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5" style="cursor:pointer; width:70%;" value="전체" ng-click="<c:out value="${param.targetId}"/>Show()" readonly/>
<c:if test="${empty param.modiFg}">
    <button type="button" class="btn_skyblue fl mr5" id="<c:out value="${param.targetId}SelectCancelBtn"/>"><s:message code="cmm.selectCancel"/></button>
</c:if>

<wj-popup id="wj<c:out value="${param.targetId}"/>LayerS" control="wj<c:out value="${param.targetId}"/>LayerS" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="prod.sdselGrpCd"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
            <div class="w100">
                <%-- 조회 조건 --%>
                <table class="tblType01 mt5">
                    <colgroup>
                        <col class="w30" />
                        <col class="w70" />
                    </colgroup>
                    <tbody>
                    <tr>
                        <th><s:message code="prod.sdselGrpNm"/></th>
                        <td><input type="text" class="sb-input w100" id="searchSdselGrpNm" ng-model="searchSdselGrpNm" maxlength="10"/></td>
                    </tr>
                    </tbody>
                </table>

                <%-- 버튼영역 --%>
                <div class="mt10 tr">
                    <button class="btn_skyblue" ng-click="searchSdselGrp()"><s:message code="cmm.search" /></button>
                </div>

                <%--위즈모 테이블--%>
                <div class="theGrid mt10" style="height: 400px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="prod.sdselGrpCode"/>" binding="sdselGrpCd" width="100" is-read-only="true" align="center"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="prod.sdselGrpNm"/>" binding="sdselGrpNm" width="280" is-read-only="true" align="left"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>

        </div>
    </div>
</wj-popup>


<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 선택메뉴 선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

        $scope.targetId = "${param.targetId}";
        $("#"+$scope.targetId+"Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController($scope.targetId + 'Ctrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "sdselGrpCd") {
                        var item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            });

            // 그리드 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function (e) {
                var ht = s.hitTest(e);
                if (ht.cellType === wijmo.grid.CellType.Cell) {
                    var col         = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if (col.binding === "sdselGrpCd") {
                        $("#" + $scope.targetId + "Cd").val(selectedRow.sdselGrpCd);
                        $("#" + $scope.targetId + "Nm").val("[" + selectedRow.sdselGrpCd + "] " + selectedRow.sdselGrpNm);
                        $("#" + $scope.targetId + "NmOnly").val(selectedRow.sdselGrpNm);
                        eval('$scope.wj' + $scope.targetId + 'LayerS.hide(true)');
                    }
                }
            });
        };

        //$scope.searchFg = "N"; // 조회 했는지 여부
        // 다른 컨트롤러의 broadcast 받기
        $scope.$on($scope.targetId + 'Ctrl', function (event, paramObj) {
            // 매장선택 팝업 오픈
            eval('$scope.wj' + $scope.targetId + 'LayerS.show(true)');
            // 팝업 닫힐시 이벤트
            eval('$scope.wj' + $scope.targetId + 'LayerS').hidden.addHandler(function () {
                if ('${param.closeFunc}' !== '') {
                    eval('$scope.${param.closeFunc}()');
                }

                // 선택메뉴명 초기화
                $scope.searchSdselGrpNm = "";
            });

            //if ($scope.searchFg == "N") {
                $scope.searchSdselGrp();
            //}
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchSdselGrp = function () {
            // 파라미터
            var params = {};
            params.sdselGrpNm = $scope.searchSdselGrpNm;

            $scope._inquirySub("/base/prod/prod/prod/getSearchSdselGrpList.sb", params, function() {
                //$scope.searchFg = "Y";
            }, false);
        };

    }]);

    $(document).ready(function () {
        <%-- 선택취소 버튼 클릭 --%>
        $("#${param.targetId}SelectCancelBtn").click(function () {
            $("#${param.targetId}Cd").val("");
            $("#${param.targetId}Nm").val(("${param.displayNm}" === "" ? messages["cmm.select"] : "${param.displayNm}"));
        });
    });

</script>
