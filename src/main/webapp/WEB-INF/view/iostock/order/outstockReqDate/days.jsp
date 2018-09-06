<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/days/"/>


<div id="daysView" class="subCon" style="display: none;" ng-controller="daysCtrl">
    <div class="searchBar">
        <a href="javascript:;" class="open">${menuNm}</a>
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
            <%-- 매장코드 --%>
            <th><s:message code="outstockReqDate.store"/></th>
            <td>
                <%-- ColumnPicker 사용시 include --%>
                <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
                    <jsp:param name="targetId" value="daysSelectStore"/>
                </jsp:include>
                <%--// ColumnPicker 사용시 include --%>
            </td>
            <%--<td><input type="text" id="daysSearchStoreCd" name="daysSearchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/></td>--%>
            <%--&lt;%&ndash; 매장명 &ndash;%&gt;--%>
            <%--<th><s:message code="outstockReqDate.storeNm"/></th>--%>
            <%--<td><input type="text" id="daysSearchStoreNm" name="daysSearchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16" /></td>--%>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('daysCtrl')"><s:message code="cmm.search" /></button>
    </div>

    <div class="w100">
        <div class="mt20 oh sb-select dkbr">
            <%-- 페이지 스케일  --%>
            <wj-combo-box
                class="w150 fl"
                id="listScaleBox"
                ng-model="listScale"
                items-source="_getComboData('listScaleBox')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="initComboBox(s)">
            </wj-combo-box>
            <%--// 페이지 스케일  --%>
                <div class="tr">
                <%-- 신규등록 --%>
                <button class="btn_skyblue" ng-click="saveDays()"><s:message code="cmm.save" /></button>
            </div>
        </div>

        <%--위즈모 테이블--%>
        <div class="wj-gridWrap mt10" style="height: 400px;" >
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                      binding="gChk"         width="40" align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>"      binding="storeCd"      width="70" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>"      binding="storeNm"      width="*"  align="left"   format="cDate" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>"                 binding="ownerNm"      width="60" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>"    binding="sysStatFg"    width="50" align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.orderCloseYn"/>" binding="orderCloseYn" width="80" align="center" data-map="orderCloseYnMap" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.sun"/>"          binding="sun"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.mon"/>"          binding="mon"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.tue"/>"          binding="tue"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.wed"/>"          binding="wed"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.thu"/>"          binding="thu"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.fri"/>"          binding="fri"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.sat"/>"          binding="sat"          width="50" align="center" format="checkbox"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.remark"/>"       binding="daysRemark"   width="*" align="left" is-read-only="false"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="daysCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <%-- 페이지 리스트 --%>
    <div class="pageNum mt20">
        <%-- id --%>
        <ul id="daysCtrlPager" data-size="10">
        </ul>
    </div>
    <%--//페이지 리스트--%>
</div>



<script type="text/javascript">

    /** 요일별 그리드 controller */
    app.controller('daysCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('daysCtrl', $scope, $http, true));

        $scope._setComboData("listScaleBox", gvListScaleBoxData);
        var sysStatFg = ${ccu.getCommCode("005")};

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("daysCtrl");

            // 그리드 DataMap 설정
            $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
            $scope.orderCloseYnMap = new wijmo.grid.DataMap([
                {id: "Y", name: "<s:message code='outstockReqDate.orderCloseYnY'/>"},
                {id: "N", name: "<s:message code='outstockReqDate.orderCloseYnN'/>"},
            ], 'id', 'name');

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    let col = s.columns[e.col];
                    if (col.binding === "storeCd") {
                        let item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }
                }

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

            // 그리드 매장코드 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "storeCd") {
                        var params = {};
                        params.storeCd = selectedRow.storeCd;
                        params.storeNm = selectedRow.storeNm;
                        // storeVO.setStoreCd(selectedRow.storeCd);
                        // storeVO.setStoreNm(selectedRow.storeNm);
                        // $scope._broadcast('dlvrInfoCtrl', params);
                    }
                }
            });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("daysCtrl", function(event, data) {
            $scope.searchDaysList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchDaysList = function() {
            // 파라미터
            var params = {};
            params.storeCd = $("#daysSelectStoreCd").val();
            // params.storeNm = $("#daysSelectStoreNm").val();
            // params.listScale = 15;
            // params.listScale = $scope.listScaleBoxDays.selectedValue;
            // params.curr = 1;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/outstockReqDate/days/list.sb", params);
        };

        // 요청일 저장
        $scope.saveDays = function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            $scope._save("/iostock/order/outstockReqDate/days/save.sb", params, function() { $scope.searchDaysList() });
        };

        // 매장선택 모듈 팝업 사용시 정의
        // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
        // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
        $scope.daysSelectStoreShow = function () {
            $scope._broadcast('daysSelectStoreCtrl');
        };

    }]);

    $(document).ready(function () {
        <%-- 엑셀 다운로드 버튼 클릭 --%>
        $("#btnExcel").click(function(){
            var name = "${menuNm}";
            name = name+" 테스트";
            // wexcel.down(gridStoreLoan, name, name + ".xlsx");
        });
    });

</script>
<%--<script type="text/javascript" src="/resource/solbipos/js/iostock/loan/storeLoan.js?ver=2018082101" charset="utf-8"></script>--%>


