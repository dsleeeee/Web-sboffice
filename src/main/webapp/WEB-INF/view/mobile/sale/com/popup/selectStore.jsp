<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />

<%--기본 본인 매장정보로 셋팅--%>
<input type="hidden" id="<c:out value="${param.targetId}Cd"/>" value="${sessionInfo.storeCd}"/>
<input type="text"
       id="<c:out value="${param.targetId}Nm"/>" class="sb-input fl mr5" style="cursor:pointer; width:245px;" ng-click="<c:out value="${param.targetId}"/>Show()"
       value=<s:message code="cmm.all"/> readonly/>

<wj-popup id="wj<c:out value="${param.targetId}"/>Layer" control="wj<c:out value="${param.targetId}"/>Layer" show-trigger="Click" hide-trigger="Click" style="display:none;width:85%;">
    <div class="wj-dialog wj-dialog-columns">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="cmm.store.select"/>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body" ng-controller="<c:out value="${param.targetId}"/>Ctrl">
            <div class="w100">
                <div class="oh sb-select dkbr">
                    <button class="btn_skyblue fr" ng-click="storeSelected()">
                        <s:message code="cmm.chk"/></button>
                </div>

                <%--위즈모 테이블--%>
                <div class="popGrid mt5" style="height:250px;">
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
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>" binding="storeNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">

    var storeCd = "${storeCd}";

    /**
     * get application
     */
    var app = agrid.getApp();

    /** 매장선택 controller */
    app.controller('${param.targetId}Ctrl', ['$scope', '$http', function ($scope, $http) {
        var targetId = '${param.targetId}';
        var targetCornerId = '${param.targetCornerId}';
        var targetTableId = '${param.targetTableId}';
        var targetPosId = '${param.targetPosId}';
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController(targetId + 'Ctrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
        };

        $scope.searchFg = "N";
        // 다른 컨트롤러의 broadcast 받기

        $scope.$on(targetId + 'Ctrl', function (event, paramObj) {
            // 매장선택 팝업 오픈
            eval('$scope.wj' + targetId + 'Layer.show(true)');

            // 팝업 닫힐시 이벤트
            eval('$scope.wj' + targetId + 'Layer').hidden.addHandler(function () {
                if ('${param.closeFunc}' !== '') {
                    if('${param.closeFunc}'.indexOf(',')>-1){
                        var closeFunc = ('${param.closeFunc}').split(",");
                        for(var i=0; i<closeFunc.length; i++){
                            eval('$scope.'+closeFunc[i]+'()');
                        }
                    }else{
                        eval('$scope.${param.closeFunc}()');
                    }
                }
            });

            if ($scope.searchFg == "N") {
                $scope.searchStore();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchStore = function () {
            // 파라미터
            var params = {};
            $scope._inquirySub("/mobile/sale/status/prod/prodSale/getStoreList.sb", params, function () {
                $scope.searchFg = "Y";

                // 팝업 처음 오픈 시, 기본 본인 매장정보로 셋팅
                var flex = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
                for (var i = 0; i < flex.length; i++) {
                    if (flex[i].storeCd === storeCd) {
                        flex[i].gChk = true;
                    }
                }
            });
        };

        $scope.storeSelected = function () {
            var flex       = agrid.getScope(targetId + 'Ctrl').data.sourceCollection;
            var arrStoreCd = [];
            var strStoreCd = "";
            var strStoreNm = "";
            var cnt        = 0;

            for (var i = 0; i < flex.length; i++) {
                if (flex[i].gChk) {
                    if (cnt == 0) {
                        strStoreCd = flex[i].storeCd;
                        strStoreNm = flex[i].storeNm;
                    }
                    arrStoreCd.push(flex[i].storeCd);
                    cnt++;
                }
            }

            // 매장 반드시 선택해야함.
            if(arrStoreCd.length === 0){
                s_alert.pop(messages["cmm.require.store"]);
                return;
            }

            $("#" + targetId + "Cd").val(arrStoreCd.join());
            $("#" + targetPosId + "Cd").val("");
            $("#" + targetPosId + "Name").val(messages["cmm.all"]);

            $("#" + targetCornerId + "Cd").val("");
            $("#" + targetCornerId + "Name").val(messages["cmm.all"]);

            $("#" + targetTableId + "Cd").val("");
            $("#" + targetTableId + "Name").val(messages["cmm.all"]);

            if (cnt == 0) {
                $("#" + targetId + "Nm").val(messages["cmm.all"]);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val("");
            }
            else if (cnt == 1) {
                $("#" + targetId + "Nm").val("[" + strStoreCd + "] " + strStoreNm);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
            }
            else if (cnt > 1) {
                $("#" + targetId + "Nm").val(strStoreNm + " "+messages["outstockReqDate.except"]+" " + (cnt - 1) + messages["outstockReqDate.cntStore"]);
                $("#" + targetCornerId + "Nm").val(messages["cmm.all"]);
                $("#" + targetTableId + "Nm").val(messages["cmm.all"]);
                $("#" + targetPosId + "Nm").val(messages["cmm.all"]);
                $("#" + targetId +"StoreNum").val(" 영업매장 : "+cnt+" 개");
            }
            eval('$scope.wj' + targetId + 'Layer.hide(true)');
        };
    }]);
</script>
