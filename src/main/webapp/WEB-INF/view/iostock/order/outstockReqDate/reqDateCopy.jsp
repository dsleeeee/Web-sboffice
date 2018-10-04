<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockReqDate/reqDateCopy/"/>


<div id="reqDateCopyView" class="subCon" style="display: none;">
    <div ng-controller="reqDateCopyCtrl">
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
                <%-- 매장 --%>
                <th><s:message code="outstockReqDate.store"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 싱글 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
                        <jsp:param name="targetId" value="targetSelectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
                </td>
            </tr>
            <tr>
                <%-- 요청일 복사매장 --%>
                <th><s:message code="outstockReqDate.copyStore"/></th>
                <td colspan="3">
                    <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
                    <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopM.jsp" flush="true">
                        <jsp:param name="targetId" value="copySelectStore"/>
                    </jsp:include>
                    <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
                    <%-- 복사 --%>
                    <a href="#" class="btn_grayS" ng-click="reqDateCopy()"><s:message code="cmm.save" /></a>
                    <%--<button class="btn_blue" id="btnSave" ng-click="reqDateCopy();"><s:message code="cmm.save" /></button>--%>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="mt10 pdb20 oh bb">
            <%-- 조회 --%>
            <button class="btn_blue fr" id="btnSearch" ng-click="search();"><s:message code="cmm.search" /></button>
        </div>
    </div>

    <div class="w100" ng-controller="reqDateCopyDaysCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap mt10" style="height: 100px;" >
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
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
                <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>

    <div class="w100" ng-controller="reqDateCopySpecificCtrl">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap mt10" style="height: 300px;" >
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                            binding="gChk"               width="40"  align="center"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.storeCd"/>"            binding="storeCd"            width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.storeNm"/>"            binding="storeNm"            width="*"   align="left"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="cmm.owner.nm"/>"                       binding="ownerNm"            width="60"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.sysStatFg"/>"          binding="sysStatFg"          width="50"  align="center" data-map="sysStatFgMap" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDate"/>"       binding="specificDate"       width="100" align="center" format="cDate" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.specificDateRemark"/>" binding="specificDateRemark" width="*"   align="left"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockReqDate.outstockReqYn"/>"      binding="outstockReqYn"      width="70"  align="center" data-map="outstockReqYnMap" is-read-only="true"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="reqDateCopyCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>


<script type="text/javascript">

    app.controller('reqDateCopyCtrl', ['$scope', '$http', function ($scope, $http) {
        // angular.extend(this, new RootController('reqDateCopyCtrl', $scope, $http, true));

        // $scope.storeCd = "DS00001";
        $scope.search = function () {
            // 매장을 선택해주세요.
            if($("#targetSelectStoreCd").val() == "") {
                s_alert.pop("<s:message code='outstockReqDate.require.selectStore'/>");
                return;
            }
            // 요일별 그리드 조회
            $scope._broadcast('reqDateCopyDaysCtrl', {proc:"search"});
            // 특정일 그리드 조회
            $scope._broadcast('reqDateCopySpecificCtrl', {proc:"search"});
        };
        // 복사를 실행하기 위해 저장버튼 클릭. 우선 특정일 먼저 복사하도록 broadcast 날림.
        $scope.reqDateCopy = function () {
            // 매장을 선택해주세요.
            if($("#targetSelectStoreCd").val() == "") {
                s_alert.pop("<s:message code='outstockReqDate.require.selectStore'/>");
                return;
            }
            // 복사할 매장을 선택해주세요.
            if($("#copySelectStoreCd").val() == "") {
                s_alert.pop("<s:message code='outstockReqDate.require.selectCopyStore'/>");
                return;
            }

            // 특정일 복사 요청
            var msg = "<s:message code='outstockReqDate.confirm.copyMsg'/>"; //출고가능요일과 선택한 특정일이 복사됩니다. 진행하시겠습니까?
            s_alert.popConf(msg, function(){
                $scope._broadcast('reqDateCopySpecificCtrl', {proc:"copy"});
            });
        };
        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("reqDateCopyCtrl", function(event, data) {
            $scope._broadcast('reqDateCopyDaysCtrl', {proc:"copy"});
        });

        // 매장선택 모듈 팝업 사용시 정의
        // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
        // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
        $scope.targetSelectStoreShow = function () {
            $scope._broadcast('targetSelectStoreCtrl');
        };

        // 매장선택 모듈 팝업 사용시 정의
        // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
        // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
        $scope.copySelectStoreShow = function () {
            $scope._broadcast('copySelectStoreCtrl');
        };
    }]);

    /** 요일별 그리드 controller */
    app.controller('reqDateCopyDaysCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('reqDateCopyDaysCtrl', $scope, $http, true));

        var sysStatFg = ${ccu.getCommCode("005")};

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("reqDateCopyDaysCtrl");

            // 그리드 DataMap 설정
            $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
            $scope.orderCloseYnMap = new wijmo.grid.DataMap([
                {id: "Y", name: "<s:message code='outstockReqDate.orderCloseYnY'/>"},
                {id: "N", name: "<s:message code='outstockReqDate.orderCloseYnN'/>"},
            ], 'id', 'name');

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "storeCd") {
                        var item = s.rows[e.row].dataItem;
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
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
        $scope.$on("reqDateCopyDaysCtrl", function(event, data) {
            if(data.proc == "search") {
                $scope.searchReqDateDaysList();
            }
            else if(data.proc == "copy") {
                $scope.daysCopy();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 요일별 그리드 조회
        $scope.searchReqDateDaysList = function() {
            // 파라미터
            var params = {};
            params.storeCd = $("#targetSelectStoreCd").val();
            params.listScale = 1000;
            params.curr = 1;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/outstockReqDate/days/list.sb", params);
        };

        // 요일별 복사
        $scope.daysCopy = function () {

            var params = {};
            params.storeCd = $("#targetSelectStoreCd").val();
            params.copyStoreCd = $("#copySelectStoreCd").val();

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/outstockReqDate/days/copy.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if(response.data.status === "OK") {
                    // this callback will be called asynchronously
                    // when the response is available
                    $scope._popMsg(messages["cmm.saveSucc"]);
                }
                else if(response.data.status === "FAIL") {
                    $scope._popMsg("Ajax Fail By HTTP Request");
                }
                else if(response.data.status === "SESSION_EXFIRE") {
                    $scope._popMsg(response.data.message, function() {
                        location.href = response.data.url;
                    });
                }
                else if(response.data.status === "SERVER_ERROR") {
                    $scope._popMsg(response.data.message);
                }
                else {
                    var msg = response.data.status + " : " + response.data.message;
                    $scope._popMsg(msg);
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return;
            }).then(function () {
                // "complete" code here
            });
        };
    }]);

    /** 특정일 그리드 controller */
    app.controller('reqDateCopySpecificCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('reqDateCopySpecificCtrl', $scope, $http, true));

        var sysStatFg = ${ccu.getCommCode("005")};

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("reqDateCopySpecificCtrl");

            // 그리드 DataMap 설정
            $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
            $scope.outstockReqYnMap = new wijmo.grid.DataMap([
                {id: "Y", name: "<s:message code='outstockReqDate.outstockReqYnY'/>"},
                {id: "N", name: "<s:message code='outstockReqDate.outstockReqYnN'/>"},
            ], 'id', 'name');
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("reqDateCopySpecificCtrl", function(event, data) {
            if(data.proc == "search") {
                $scope.searchReqDateSpecificList();
            }
            else if(data.proc == "copy") {
                $scope.specificCopy();
            }
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchReqDateSpecificList = function() {
            // 파라미터
            var params = {};
            params.storeCd = $("#targetSelectStoreCd").val();
            params.listScale = 1000;
            params.curr = 1;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/outstockReqDate/specificDate/list.sb", params, function() { $scope.searchEnd() });
        };

        // 특정일 그리드 조회 완료 후 호출 함수. gChk 를 모두 체크상태로 변경한다.
        $scope.searchEnd = function () {
            var flex = $scope.flex;
            if(flex) {
                for(var i=0; i < flex.rows.length; i++) {
                    flex.setCellData(i, 0, true);
                }
                flex.collectionView.commitEdit();
            }
        };

        // 특정일 복사
        $scope.specificCopy = function () {
            var params = new Array();
            var flex = $scope.flex;
            for (var i = 0; i < flex.rows.length; i++) {
                if(flex.getCellData(i, 0)) {
                    // 타겟매장과 복사할 매장이 동일합니다.
                    if(flex.rows[i]._data.storeCd == $("#copySelectStoreCd").val()) {
                        s_alert.pop("<s:message code='outstockReqDate.duplicate.targetSelectStore'/>");
                        return;
                    }
                    flex.rows[i]._data.copyStoreCd = $("#copySelectStoreCd").val();
                    params.push(flex.rows[i]._data);
                }
            }

            // 길이체크
            if (params.length <= 0) {
                // 특정일은 복사할 내용이 없습니다. 출고가능요일을 복사하시겠습니까?
                var msg = "<s:message code='outstockReqDate.not.copySpecificDate'/> <s:message code='outstockReqDate.copyDays'/>";
                s_alert.popConf(msg, function(){
                    $scope._broadcast('reqDateCopyDaysCtrl', {proc:"copy"});
                });

                <%--// 저장할 내용이 없습니다.--%>
                <%--s_alert.pop("<s:message code='outstockReqDate.not.save'/>");--%>
                return;
            }

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/outstockReqDate/specificDate/copy.sb', /* 통신할 URL */
                data: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if(response.data.status === "OK") {
                    $scope._broadcast('reqDateCopyDaysCtrl', {proc:"copy"});
                }
                else if(response.data.status === "FAIL") {
                    $scope._popMsg("Ajax Fail By HTTP Request");
                }
                else if(response.data.status === "SESSION_EXFIRE") {
                    $scope._popMsg(response.data.message, function() {
                        location.href = response.data.url;
                    });
                }
                else if(response.data.status === "SERVER_ERROR") {
                    $scope._popMsg(response.data.message);
                }
                else {
                    var msg = response.data.status + " : " + response.data.message;
                    $scope._popMsg(msg);
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return;
            }).then(function () {
            });
        }
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


