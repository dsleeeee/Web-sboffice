<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<div id="posFuncManageArea" style="display:none;" ng-controller="funcKeyCtrl">
    <h2 class="h2_tit" id="posFuncManageTitle"></h2>

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
        <%-- 포스기능 사용관리 --%>
        <li><a id="posUseManage" href="#" class="on"><s:message code="posFunc.manage.posFunc" /></a></li>
        <%-- 포스기능 인증관리 --%>
        <li><a id="posAuthManage"  href="#"><s:message code="posFunc.auth.posFunc" /></a></li>
    </ul>

    <%-- 왼쪽  --%>
    <div class="wj-TblWrapBr oh mr10 pd20" style="height:560px;">
        <div class="updownSet mb10">
            <div class="sb-select w150px fl">
                <div id="posListBox"></div>
            </div>
            <div class="fr mb10">
                <%-- 기능복사버튼 --%>
                <button class="btn_skyblue" id="btnCopyFunc"><s:message code="posFunc.copy.func" /></button>
            </div>
        </div>
        <%-- 그리드 --%>
        <div id="posFuncGrid" style="height:500px; overflow-x: hidden;"></div>
    </div>
</div>
<script>

    var selectedRow;
    var posUsageEnv = ${cnv.getEnvCodeExcpAll("4019")};
    var posTypeEnv = ${cnv.getEnvCodeExcpAll("4020")};

    <%-- header --%>
    var posFuncHeader =
        [
            {binding: "fnkeyFg", header: "<s:message code='posFunc.fnkeyFg' />", visible: false, width: "*" },
            {binding: "fnkeyNm", header: "<s:message code='posFunc.fnkeyNm' />", width: 200},
            {binding: "array", header: "<s:message code='posFunc.array' />", width: "*", visible: false},
            {binding: "totCnt", header: "<s:message code='posFunc.func.cnt' />", width: 100},
            {binding: "regCnt", header: "<s:message code='posFunc.regist.cnt' />", width: 100}
        ];

    <%-- 그리드 생성 --%>
    var posFuncGrid = wgrid.genGrid("#posFuncGrid", posFuncHeader);
    posFuncGrid.isReadOnly = true;

    var posListBox = wcombo.genCommonBox("#posListBox", []);

    <%-- 그리드 포맷 --%>
    posFuncGrid.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
            var col = s.columns[e.col];
            var item = s.rows[e.row].dataItem;
            if (col.binding === "fnkeyNm") {
                wijmo.addClass(e.cell, 'wijLink');
            }
        }
    });

    <%-- 그리드 선택 이벤트 --%>
    posFuncGrid.addEventListener(posFuncGrid.hostElement, 'click', function (e) {
        var ht = posFuncGrid.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
            var col = ht.panel.columns[ht.col];
            if (col.binding === "fnkeyNm") {
                selectedRow = posFuncGrid.rows[ht.row].dataItem;
                <%-- 위치조정여부 'N'이면 버튼 보여주기 --%>
                /*if (selectedRow.posiAdjYn === "N") {
                    $("#btnAutoPosition").show();
                } else {
                    $("#btnAutoPosition").hide();
                }*/

                // 포스기능 키 목록 조회
                var scope = agrid.getScope("funcKeyCtrl");
                scope._broadcast('funcKeyCtrl' , selectedRow);
            }
        }
    });

    <%-- 매장의 포스 리스트 셋팅 --%>
    function getPosList() {
        var param = {};
        param.hqOfficeCd = selectedStore.hqOfficeCd;
        param.storeCd = selectedStore.storeCd;

        $.postJSON("/base/store/posfunc/use/getPosList.sb", param,
            function (result) {
                posList = result.data.list;
                posListBox.itemsSource = posList;

                if (posList.length === 0) {
                    //s_alert.pop("<s:message code='posFunc.no.regist.pos' />");
                    return;
                }

                // 가장 처음 포스의 포스 기능목록 바로 조회
                getPosConfList();
            },
            function (result) {
                s_alert.pop(result.message);

            }
        );
    }

    <%-- 포스 리스트 변경 시, 기능목록 조회  --%>
    posListBox.selectedIndexChanged.addHandler(function (s, e) {
        getPosConfList();
    });

    <%-- 포스 기능 목록 조회 --%>
    function getPosConfList() {

        var param = {};
        param.storeCd = selectedStore.storeCd;
        param.posNo = posListBox.selectedValue;

        $.postJSON("/base/store/posfunc/use/getPosFuncList.sb", param,
            function (result) {
                var list = result.data.list;
                posFuncGrid.itemsSource = new wijmo.collections.CollectionView(list);
                posFuncGrid.collectionView.trackChanges = true;
            },
            function (result) {
                s_alert.pop(result.message);

            }
        );
    }

    <%-- 기능복사 버튼 클릭 //TODO 작업중 --%>
    $("#posFuncManageArea #btnCopyFunc").click(function () {
        openCopyFuncLayer();
    });

    <%--//TODO 좌표자동세팅 버튼 클릭 --%>
    /*$("#posFuncManageArea #btnAutoPosition").click(function () {
    });*/

    <%-- 탭 클릭 --%>
    $("#posFuncManageArea #posAuthManage").click(function () {
        hidePosFuncList();
        showPosFuncAuth();
    });

    function hidePosFuncList() {
        $("#posFuncManageArea").hide();
    }

    <%-- 포스기능 사용관리 탭 화면 보여주기 --%>
    function showPosFuncList() {
        $("#posFuncAuthArea").hide();
        $("#posFuncManageArea").show();
        getPosList();

        <c:if test="${orgnFg != 'STORE'}">
        // 선택매장 표시
        $("#posFuncManageTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);
        $(".subTab").show();
        $("#btnCopyFunc").show();
        </c:if>
        <c:if test="${orgnFg == 'STORE'}">
        $(".subTab").hide();
        $("#btnCopyFunc").hide();
        </c:if>

        // angular 그리드 hide 시 깨지므로 refresh()
        /* var scope = agrid.getScope("funcKeyCtrl");
         scope.flex.refresh();*/

    }

</script>

<%-- 기능키 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseFnKey.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 기능키 XML 팝업 --%>
<c:import url="/WEB-INF/view/base/store/posFunc/posFuncUseXml.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 매장선택 controller */
    app.controller('funcKeyCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('funcKeyCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {};

        /**
         * 기능키영역은 화면이 로드된 후 그린다.
         * 기존에 화면 로드 시 수행됐으나, 팝업 형태로 바뀌면서 부모창에서 함수 호출하는 방식으로 변경.(한번만 호출되어야 함)
         */
        startFuncKeyInit();

        $scope.$on('funcKeyCtrl', function (event, data) {

            if (data.fnkeyFg === "6020" || data.fnkeyFg === "6021"|| data.fnkeyFg === "6022") {
                $scope._broadcast('posFuncUseXmlCtrl', data);
                funckeyGraph.format.openByGrid(true);

            }else{
                $scope._broadcast('posFuncUseFnKeyCtrl', data);
            }

            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

    }]);
</script>
