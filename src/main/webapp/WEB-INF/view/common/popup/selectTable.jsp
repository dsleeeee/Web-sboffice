<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="gvHqOfficeCd" value="${sessionScope.sessionInfo.hqOfficeCd}"/>
<input type="hidden" id="HqOfficePopCd" name="HqOfficePopCd" ng-model="HqOfficeCd" value="${gvHqOfficeCd}"/>
<input type="hidden" id="<c:out value="${param.targetId}Cd"/>"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>"
       class="sb-input fl mr5"
       style="cursor:pointer; width:200px;"
       value=<s:message code="cmm.all"/>
               ng-click="<c:out value="${param.targetId}"/>Show()"
       readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:500px;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="cmm.table.select"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
            <div class="w100">
                <div class="oh sb-select dkbr">
                    <button class="btn_skyblue fr" ng-click="tableSelected()">
                        <s:message code="cmm.chk"/></button>
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
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectTable.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="selectTable.tableNm"/>" binding="tableNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="" binding="tableCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
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

    /** 테이블 선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {

        var targetId = '${param.targetId}';
        var targetTypeFg = '${param.targetTypeFg}'; // 매장선택 (S:싱글, M:멀티)

        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, false));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
        };

        $scope.searchFg = "";
        // 다른 컨트롤러의 broadcast 받기
        $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
            // 테이블선택 팝업 오픈
            if($("#${param.targetStoreId}Cd").val() !== ""){
                eval('$scope.wj' + targetId + 'Layer.show(true)');
            }
            // 팝업 닫힐시 이벤트
            eval('$scope.wj' + targetId + 'Layer').hidden.addHandler(function () {
                if ('${param.closeFunc}' !== '') {
                    $scope.tableSelected();
//           eval('$scope.${param.closeFunc}()');
                }
            });

            $scope.searchTable();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchTable = function () {
            // 파라미터
            var params = {};
            params.storeCd = $("#${param.targetStoreId}Cd").val();
            params.hqOfficeCd = $("#HqOfficePopCd").val();
            if(params.storeCd !== "" && params.storeCd !== $scope.searchFg){
                $scope._inquirySub("/common/popup/selectTable/getSelectTableList.sb", params, function () {
                    $scope.searchFg = params.storeCd;
                });
            }else if(params.storeCd === ""){
                $scope._popMsg(messages["cmm.require.selectStore"]);
            }
        };

        $scope.tableSelected = function () {
            var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;

            var arrTableCd = [];
            var arrTableCdOrg = [];
            var arrTableNm = [];
            var strTableCd = "";
            var strTableCdOrg = "";
            var strStoreNm = "";
            var strTableNm = "";
            var cnt        = 0;

            $("#" + targetId + "Cd").val("");
            $("#" + targetId + "Name").val("");
            $("#" + targetId + "CdOrg").val("");

            for (var i = 0; i < flex.length; i++) {
                if (flex[i].gChk) {
                    if (cnt == 0) {
                        strTableCd = flex[i].tableCd;
                        strTableCdOrg = flex[i].tableCdOrg;
                        strStoreNm = flex[i].storeNm;
                        strTableNm = flex[i].tableNm;
                    }
                    arrTableCd.push(flex[i].tableCd);
                    arrTableCdOrg.push(flex[i].tableCdOrg);
                    arrTableNm.push(flex[i].storeNm + "||" + flex[i].tableNm);
                    cnt++;
                }
            }

            $("#" + targetId + "Cd").val(arrTableCd.join());
            $("#" + targetId + "Name").val(arrTableNm.join());
            $("#" + targetId + "CdOrg").val(arrTableCdOrg.join());

            if (cnt == 0) {
                $("#" + targetId + "Nm").val(messages["cmm.all"]);
            }
            else if (cnt == 1) {
                var storeTable = strTableCd.split("||");
                $("#" + targetId + "Nm").val("[" + storeTable[1] + "] " + strTableNm);
            }
            else if (cnt > 1) {
                $("#" + targetId + "Nm").val(strTableNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["cmm.cntTable"]);
                $("#" + targetId +"StoreNum").val(" 영업매장수 : "+cnt+" 개");
            }
            eval('$scope.wj' + targetId + 'Layer.hide(true)');
        };
    }]);

</script>