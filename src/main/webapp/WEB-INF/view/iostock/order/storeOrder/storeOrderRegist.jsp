<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrderRegist/"/>

<wj-popup id="wjStoreOrderRegistLayer" control="wjStoreOrderRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="storeOrderRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeOrderRegistCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="outstockReqDate.specificDate" /> &nbsp;<s:message code="cmm.new.add" />
            <a href="javascript:;" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="storeOrder.dtl.prodCd" /></th>
                    <td><input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/></td>
                    <%-- 상품코드 --%>
                    <th><s:message code="storeOrder.dtl.prodNm" /></th>
                    <td><input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/></td>
                </tr>
                <tr>
                    <th><s:message code="storeOrder.dtl.barCode"/></th>
                    <td><input type="text" id="srchBarCode" name="srchBarCode" ng-model="barCode" class="sb-input w100" maxlength="40"/></td>
                    <th><s:message code="storeOrder.dtl.prodClass"/></th>
                    <td><input type="text" id="srchProdClass" name="prodClass" ng-model="prodClass" class="sb-input w100" maxlength="40"/></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 pdb20 oh bb">
                <%-- 조회 --%>
                <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchStoreOrderRegistList();"><s:message code="cmm.search" /></button>
            </div>

            <div class="wj-TblWrap ml20 mr20 pdb20">
                <div class="mt20 oh sb-select">
                    <span id="spanStoreLoanInfo" class="fl"></span>
                    <div class="tr">
                        <%-- 창고추가 --%>
                        <button type="button" class="btn_skyblue ml5" id="btnSave" ng-click="saveStoreOrderRegist()"><s:message code="cmm.save" /></button>
                    </div>
                </div>

                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 250px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                         binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodCd"/>"           binding="prodCd"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prodNm"/>"           binding="prodNm"           width="150" align="left"   is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderSplyUprc"/>"    binding="orderSplyUprc"    width="70"  align="right"  is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderUnitQty"/>" binding="prevOrderUnitQty" width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderEtcQty"/>"  binding="prevOrderEtcQty"  width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderUnitQty"/>"     binding="orderUnitQty"     width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderEtcQty"/>"      binding="orderEtcQty"      width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTotQty"/>"      binding="orderTotQty"      width="70"  align="right"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderAmt"/>"         binding="orderAmt"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderVat"/>"         binding="orderVat"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTot"/>"         binding="orderTot"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.saleUprc"/>"         binding="saleUprc"         width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitFg"/>"         binding="poUnitFg"         width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitQty"/>"        binding="poUnitQty"        width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.safeStock"/>"        binding="safeStock"        width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.storeStockQty"/>"    binding="storeStockQty"    width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.remark"/>"           binding="remark"           width="70"  align="left"   max-length=300></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poMinQty"/>"         binding="poMinQty"         width="70"  align="right" visible="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.vatFg"/>"            binding="vatFg01"          width="70"  align="right" visible="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.envst0011"/>"        binding="envst0011"        width="70"  align="right" visible="true"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>

            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">

    /** 주문등록 상세 그리드 controller */
    app.controller('storeOrderRegistCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeOrderRegistCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {

            // s.allowMerging = wijmo.grid.AllowMerging.AllHeaders;

            s.cellEditEnded.addHandler(function (s, e) {
                if (e.panel == s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 주문수량 수정시
                        var item = s.rows[e.row].dataItem;
                        var orderSplyUprc = parseInt(item.orderSplyUprc);
                        var poUnitQty     = parseInt(item.poUnitQty);
                        var vat01         = parseInt(item.vatFg01);
                        var envst0011     = parseInt(item.envst0011);

                        var unitQty = (parseInt(nvl(item.prevOrderUnitQty,0)) + parseInt(nvl(item.orderUnitQty,0))) * parseInt(item.poUnitQty);
                        var etcQty  = parseInt(nvl(item.prevOrderEtcQty,0)) + parseInt(nvl(item.orderEtcQty,0));
                        var totQty  = parseInt(unitQty + etcQty);
                        var tempOrderAmt = Math.round(totQty * orderSplyUprc / poUnitQty);
                        var orderAmt = tempOrderAmt - Math.round(tempOrderAmt * vat01 * envst0011 / 11);
                        var orderVat = Math.round(tempOrderAmt * vat01 / (10 + envst0011));
                        var orderTot = parseInt(orderAmt + orderVat);

                        item.orderTotQty = totQty;   // 총주문수량
                        item.orderAmt    = orderAmt; // 금액
                        item.orderVat    = orderVat; // VAT
                        item.orderTot    = orderTot; // 합계

                    }
                }

                s.collectionView.commitEdit();
            });

            // add the new GroupRow to the grid's 'columnFooters' panel
            // s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            // s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeOrderRegistCtrl", function(event, data) {
            $scope.reqDate = data.reqDate;
            $scope.slipFg  = data.slipFg;

            var today = getCurDate();
            if(parseInt(today) > parseInt($scope.reqDate)) {
                $scope._popMsg('<s:message code="storeOrder.dtl.not.prevDateOrder"/>');
                return;
            }
            $scope.storeCloseCheck();

            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 매장마감여부 체크
        $scope.storeCloseCheck = function() {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/storeCloseCheck.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if(response.data.status === "OK") {
                    if (response.data.data.length > 0 && response.data.data[0].orderCloseFg == "Y") {
                        $scope._popMsg('<s:message code="storeOrder.dtl.orderClose"/>');
                        return;
                    }
                    else {
                        $scope.orderProcFgCheck();
                    }
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

        // 주문진행구분 체크
        $scope.orderProcFgCheck = function() {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if(response.data.status === "OK") {
                    if (response.data.data.length > 0 && response.data.data[0].procFg != "0") {
                        $scope._popMsg('<s:message code="storeOrder.dtl.not.orderProcEnd"/>');
                        return;
                    }
                    else {
                        $scope.searchStoreLoan();
                    }
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

        // 매장여신 조회
        $scope.searchStoreLoan = function() {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/storeLoan.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if(response.data.status === "OK") {
                    if(response.data.data.length > 0) {
                        if(response.data.data[0].orderCloseYn == "Y") {
                            $scope._popMsg('<s:message code="storeOrder.dtl.orderClose"/>');
                            return;
                        }
                        else {
                            $scope.prevOrderTot      = response.data.data[0].prevOrderTot;
                            $scope.limitLoanAmt      = response.data.data[0].limitLoanAmt;
                            $scope.currLoanAmt       = response.data.data[0].currLoanAmt;
                            $scope.maxOrderAmt       = response.data.data[0].maxOrderAmt;
                            $scope.noOutstockAmtFg   = response.data.data[0].noOutstockAmtFg;
                            $scope.availableOrderAmt = response.data.data[0].availableOrderAmt;

                            //미출고금액 고려여부 사용인 경우
                            if($scope.noOutstockAmtFg == "Y") {
                                if($scope.availableOrderAmt <= ($scope.currLoanAmt - $scope.prevOrderTot)) {
                                }
                                else if($scope.availableOrderAmt >= ($scope.currLoanAmt - $scope.prevOrderTot) && $scope.maxOrderAmt != 0) {
                                    $scope.availableOrderAmt = $scope.currLoanAmt - $scope.prevOrderTot;
                                }
                                else {
                                    $scope.availableOrderAmt = $scope.availableOrderAmt - $scope.prevOrderTot;
                                }
                            }

                            $("#spanStoreLoanInfo").html("1회주문한도액 : "+$scope.maxOrderAmt+" 여신잔액 : "+$scope.currLoanAmt+" 미출고액 : "+$scope.prevOrderTot+" 주문가능액 : "+$scope.availableOrderAmt);
                        }
                    }
                }
                else if(response.data.status === "FAIL") {
                    $scope._popMsg("Ajax Fail By HTTP Request");
                    return;
                }
                else if(response.data.status === "SESSION_EXFIRE") {
                    $scope._popMsg(response.data.message, function() {
                        location.href = response.data.url;
                    });
                    return;
                }
                else if(response.data.status === "SERVER_ERROR") {
                    $scope._popMsg(response.data.message);
                    return;
                }
                else {
                    var msg = response.data.status + " : " + response.data.message;
                    $scope._popMsg(msg);
                    return;
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return;
            }).then(function () {
                // "complete" code here
                $scope.wjStoreOrderRegistLayer.show(true);
            });
        };

        $scope.searchStoreOrderRegistList = function() {
            // 파라미터
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/storeOrder/storeOrderRegist/list.sb", params, function () {
                $scope.prodOrderCheck();
            });
        };

        $scope.prodOrderCheck = function() {
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                // console.log("==item==");
                // console.log(item);
                if(item.poUnitQty == 1) {
                    // console.log("==$scope.flex==");
                    // console.log($scope.flex);

                    // 2018.09.11 안동관. 주석처리 해놓은 7이라고 하드코딩하기 싫어서 for문 돌도록 해놨는데 느린듯 싶으면 걍 하드코딩으로 해야할듯...
                    // $scope.flex.rows[i].grid.columns[7].isReadOnly = true;
                    for(var k = 0; k < $scope.flex.rows[i].grid.columns.length; k++) {
                        var columns = $scope.flex.rows[i].grid.columns[k];
                        if(columns.binding == "orderEtcQty") {
                            columns.isReadOnly = true;
                            break;
                        }
                    }

                }
            }
            // console.log($scope.flex.collectionView.items);
        };

        $scope.saveStoreOrderRegist = function () {
            var params = new Array();
            var orderTot = 0;
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                item.status  = "U";
                item.reqDate = $scope.reqDate;
                item.slipFg  = $scope.slipFg;
                item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
                orderTot += parseInt(item.orderTot);
                params.push(item);
            }

            if($scope.availableOrderAmt != null) {
                console.log("orderTot = "+orderTot);
                if(parseInt($scope.availableOrderAmt) < parseInt(orderTot)) {
                    $scope._popMsg('<s:message code="storeOrder.dtl.orderTotOver"/>');
                    return;
                }
            }

            $scope._save("/iostock/order/storeOrder/storeOrderRegist/save.sb", params, function() { $scope.searchStoreOrderRegistList() });

        };

    }]);

    function nvl(value1,value2) {
        if (value1 == null) return value2;
        return value1;
    }
</script>
