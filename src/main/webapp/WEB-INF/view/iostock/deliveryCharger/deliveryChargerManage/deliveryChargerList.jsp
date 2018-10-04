<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList/"/>

<div class="subCon">

    <div class="searchBar flddUnfld">
        <a href="#" class="open">${menuNm}</a>
    </div>
    <table class="searchTbl">
        <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
            <%-- 기사코드 --%>
            <th><s:message code="deliveryCharger.dlvrCd"/></th>
            <td><input type="text" id="searchDlvrCd" name="searchDlvrCd" ng-model="dlvrCd" class="sb-input w100" maxlength="4"/></td>
            <%-- 기사명 --%>
            <th><s:message code="deliveryCharger.dlvrNm"/></th>
            <td><input type="text" id="searchDlvrNm" name="searchDlvrNm" ng-model="dlvrNm" class="sb-input w100" maxlength="12" /></td>
        </tr>
        <tr>
            <%-- 차량번호 --%>
            <th><s:message code="deliveryCharger.carNo"/></th>
            <td><input type="text" id="searchCarNo" name="searchCarNo" ng-model="carNo" class="sb-input w100" maxlength="14" /></td>
            <th></th>
            <td></td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('dlvrChgrCtrl')"><s:message code="cmm.search" /></button>
    </div>

    <div id="grid" class="w100" ng-controller="dlvrChgrCtrl">
        <div class="mt20 oh sb-select dkbr">
            <%--페이지 스케일 --%>
            <div id="listScaleBoxDlvrChgr" class="w130 fl"></div>
            <div class="tr">
                <%-- 신규등록 --%>
                <button class="btn_skyblue" ng-click="openPopNewRegist()"><s:message code="deliveryCharger.new" /></button>
            </div>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap mt10" style="height: 350px;" >
            <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="true"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="deliveryCharger.dlvrCd"/>" binding="dlvrCd" width="70" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="deliveryCharger.dlvrNm"/>" binding="dlvrNm" width="*"  align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="deliveryCharger.carNo"/>"  binding="carNo"  width="*"  align="left"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="deliveryCharger.useYn"/>"  binding="useYn"  width="70" align="center" data-map="useYnMap"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="dlvrChgrCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dlvrChgrCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>

<script type="text/javascript">
    var listScaleBoxDlvrChgr;

    /**
     * get application
     */
    var app = agrid.getApp();
    app.service('dlvrVO', function () {
        var g_dlvrCd = "";
        var g_dlvrNm = "";
        this.setDlvrCd = function (dlvrCd) {
            this.g_dlvrCd = dlvrCd;
        };
        this.getDlvrCd = function () {
            return this.g_dlvrCd;
        };
        this.setDlvrNm = function (dlvrNm) {
            this.g_dlvrNm = dlvrNm;
        };
        this.getDlvrNm = function () {
            return this.g_dlvrNm;
        };
    });

    /** 배송기사 그리드 controller */
    app.controller('dlvrChgrCtrl', ['$scope', '$http', 'dlvrVO', function ($scope, $http, dlvrVO) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('dlvrChgrCtrl', $scope, $http, true));
        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("dlvrChgrCtrl");

            // 그리드 DataMap 설정
            $scope.useYnMap = new wijmo.grid.DataMap([
                {id: "Y", name: "<s:message code='deliveryCharger.useYnY'/>"},
                {id: "N", name: "<s:message code='deliveryCharger.useYnN'/>"},
            ], 'id', 'name');

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "dlvrCd") {
                        var item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            });

            // 기사코드 그리드 선택 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "dlvrCd") {
                        var params = {};
                        params.dlvrCd = selectedRow.dlvrCd;
                        params.dlvrNm = selectedRow.dlvrNm;
                        dlvrVO.setDlvrCd(selectedRow.dlvrCd);
                        dlvrVO.setDlvrNm(selectedRow.dlvrNm);
                        $scope._broadcast('dlvrInfoCtrl', params);
                    }
                }
            });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("dlvrChgrCtrl", function(event, data) {
            $scope.searchDlvrList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchDlvrList = function() {
            // 파라미터
            var params = {};
            // params.listScale = 2;
            params.listScale = listScaleBoxDlvrChgr.selectedValue;

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerList/list.sb", params);
        }

        // 신규 등록
        $scope.openPopNewRegist = function () {
            dlvrVO.setDlvrCd('');
            dlvrVO.setDlvrNm('');
            $scope._broadcast('dlvrInfoCtrl');
        }
    }]);

    $(document).ready(function () {
        listScaleBoxDlvrChgr = wcombo.genCommonBox("#listScaleBoxDlvrChgr", gvListScaleBoxData); //listScaleBoxData 는 공통으로 빼둠. (commonVariables.jsp)

        <%-- 엑셀 다운로드 버튼 클릭 --%>
        $("#btnExcel").click(function(){
            var name = "${menuNm}";
            name = name+" 테스트";
            // wexcel.down(gridStoreLoan, name, name + ".xlsx");
        });
    });

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>

<%-- 배송기사 등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerRegist.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 배송기사 관리 창고 추가 레이어 --%>
<c:import url="/WEB-INF/view/iostock/deliveryCharger/deliveryChargerManage/deliveryChargerStorageMgr.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
