<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeClose/storeClose/"/>

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
            <%-- 조회월 --%>
            <th><s:message code="cmm.search.month"/></th>
            <td>
                <div class="sb-select">
                    <span class="txtIn"><input id="srchMonth" class="w200"></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('storeCloseCtrl')"><s:message code="cmm.search" /></button>
    </div>

    <div class="w40 fl" style="width: 35%" ng-controller="storeCloseCtrl">
        <div class="wj-TblWrapBr mr10 pd20" style="height: 400px;">
            <div class="oh sb-select mb10">
                <span class="fl bk lh30"><s:message code='storeClose.closeMonthSubTitle' /></span>
            </div>

            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 315px;" >
                <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="true"
                    item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="storeClose.closeMonth"/>"   binding="closeMonth"   width="0"  align="center" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.closeMonthNm"/>" binding="closeMonthNm" width="70" align="center" ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.dateCnt"/>"      binding="dateCnt"      width="70" align="right"  ></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.closeDateCnt"/>" binding="closeDateCnt" width="*"  align="right"  ></wj-flex-grid-column>

                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="storeCloseCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>


    <div class="w60 fr" style="width: 65%" ng-controller="storeCloseDtlCtrl">
        <div class="wj-TblWrapBr ml10 pd20" style="height: 400px;">

            <div class="oh sb-select mb10">
                <span class="fl bk lh30"><s:message code='storeClose.closeDateSubTitle' /></span>
                <div class="tr">
                <%-- 저장 --%>
                <button class="btn_skyblue" ng-click="saveStoreClose()"><s:message code="cmm.save" /></button>
                </div>
            </div>

            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height: 315px;" >
                <wj-flex-grid
                        autoGenerateColumns="false"
                        selection-mode="Row"
                        items-source="data"
                        control="flex"
                        initialized="initGrid(s,e)"
                        is-read-only="false"
                        item-formatter="_itemFormatter">

                    <!-- define columns -->
                    <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                 binding="gChk"          width="40" align="center"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.closeDate"/>"    binding="closeDate"     width="0"  align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.closeDateNm"/>"  binding="closeDateNm"   width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.orderCloseFg"/>"  binding="orderCloseFg" width="80"  align="center" format="checkbox" is-read-only="false"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.orderCloseDt"/>"  binding="orderCloseDt" width="*" align="center" is-read-only="true"></wj-flex-grid-column>
                    <wj-flex-grid-column header="<s:message code="storeClose.orderCloseNm"/>"  binding="orderCloseNm" width="100" align="center" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                    <jsp:param name="pickerTarget" value="storeCloseDtlCtrl"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </div>
            <%--//위즈모 테이블--%>
        </div>
    </div>
</div>



<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 마감월 그리드 controller */
    app.controller('storeCloseCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeCloseCtrl', $scope, $http, true));

        // $scope._setComboData("listScaleBox", gvListScaleBoxData);
        var searchMonth = new wijmo.input.InputDate('#srchMonth', {
            format: "yyyy-MM"
        });

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("storeCloseCtrl");

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "closeMonthNm") { // 마감월 클릭
                        let item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }
            });

            // 그리드 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "closeMonthNm") {
                        $scope._broadcast('storeCloseDtlCtrl', selectedRow.closeMonth);
                    }
                }
            });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeCloseCtrl", function(event, data) {
            $scope.searchStoreCloseList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchStoreCloseList = function() {
            // 파라미터
            var params = {};
            params.closeMonth = wijmo.Globalize.format(searchMonth.value, 'yyyyMM');
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/storeClose/storeClose/list.sb", params, function () {
                $scope._broadcast("storeCloseDtlCtrl", wijmo.Globalize.format(searchMonth.value, 'yyyyMM'));
            });
        };
    }]);


    /** 마감월 상세 그리드 controller */
    app.controller('storeCloseDtlCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeCloseDtlCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("storeCloseDtlCtrl");

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel.cellType === wijmo.grid.CellType.ColumnHeader) {
                    var flex = e.panel.grid;
                    var col = s.columns[e.col];

                    // check that this is a boolean column
                    if (col.format === "checkbox") { // 여기에 해당하는 컬럼명 바인딩 바꿔줄 것.
                        // prevent sorting on click
                        col.allowSorting = false;
                        // count true values to initialize checkbox
                        var cnt = 0;
                        for (var i = 0; i < flex.rows.length; i++) {
                            if (flex.getCellData(i, col._idx) == true) cnt++;
                        }
                        // create and initialize checkbox
                        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check" />'+col.header;
                        var cb = e.cell.firstChild;
                        cb.checked = cnt > 0;
                        cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
                        // apply checkbox value to cells
                        cb.addEventListener('click', function (e) {
                            flex.beginUpdate();
                            for (var i = 0; i < flex.rows.length; i++) {
                                flex.setCellData(i, col._idx, cb.checked);
                            }
                            flex.endUpdate();
                        });
                    }
                }
            });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeCloseDtlCtrl", function(event, data) {
            $scope.searchStoreCloseDtlList(data);
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        var searchDtlMonth = ""; // DTL 조회월
        $scope.searchStoreCloseDtlList = function(month) {
            searchDtlMonth = month;
            // 파라미터
            var params = {};
            params.closeMonth = month;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/storeClose/storeClose/dtlList.sb", params);
        };

        $scope.saveStoreClose = function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            $scope._save("/iostock/order/storeClose/storeClose/save.sb", params, function() { $scope.searchStoreCloseDtlList(searchDtlMonth) });

        };

    }]);
</script>
