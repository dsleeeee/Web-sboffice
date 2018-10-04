<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrderDtl/"/>

<wj-popup id="wjStoreOrderDtlLayer" control="wjStoreOrderDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="storeOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeOrderDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <span id="spanDtlTitle"></span>
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <th><s:message code="storeOrder.remark"/></th>
                    <td colspan="3"><input type="text" id="dtlHdRemark" name="dtlHdRemark" ng-model="dtlHdRemark" class="sb-input w100"/></td>
                </tr>
                </tbody>
            </table>

            <div class="tr mt10">
                <p id="dtlStoreLoanInfo" class="fl s14 bk lh30"></p>
                <%-- 상품추가/변경 --%>
                <button type="button" id="btnAddProd" class="btn_skyblue ml5" ng-click="addProd()" style="display:none"><s:message code="storeOrder.addProd" /></button>
                <%-- 저장 --%>
                <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveStoreOrderDtl('save')" style="display:none"><s:message code="cmm.save" /></button>
                <%-- 확정 --%>
                <button type="button" id="btnConfirm" class="btn_skyblue ml5" ng-click="saveStoreOrderDtl('confirm')" style="display:none"><s:message code="cmm.save" /></button>
            </div>
            <div style="clear: both;"></div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 450px;">
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
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderTotQty"/>"  binding="prevOrderTotQty"  width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderUnitQty"/>"     binding="orderUnitQty"     width="70"  align="right"  is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderEtcQty"/>"      binding="orderEtcQty"      width="70"  align="right"  is-read-only="false"  max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTotQty"/>"      binding="orderTotQty"      width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderAmt"/>"         binding="orderAmt"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderVat"/>"         binding="orderVat"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTot"/>"         binding="orderTot"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitFg"/>"         binding="poUnitFg"         width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitQty"/>"        binding="poUnitQty"        width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.remark"/>"           binding="remark"           width="200" align="left"   max-length=300></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poMinQty"/>"         binding="poMinQty"         width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.vatFg"/>"            binding="vatFg01"          width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.envst0011"/>"        binding="envst0011"        width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>

            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">

    /** 주문등록 상세 그리드 controller */
    app.controller('storeOrderDtlCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeOrderDtlCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 그리드 포맷 핸들러
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    var item = s.rows[e.row].dataItem;
                    if(col.binding === "orderEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
                        // console.log(item);
                        if(item.poUnitQty === 1) {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });

            s.cellEditEnded.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "orderUnitQty" || col.binding === "orderEtcQty") { // 주문수량 수정시
                        var item = s.rows[e.row].dataItem;
                        var orderSplyUprc = parseInt(item.orderSplyUprc);
                        var poUnitQty     = parseInt(item.poUnitQty);
                        var vat01         = parseInt(item.vatFg01);
                        var envst0011     = parseInt(item.envst0011);

                        var unitQty = parseInt(nvl(item.orderUnitQty,0)) * parseInt(item.poUnitQty);
                        var etcQty  = parseInt(nvl(item.orderEtcQty,0));
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
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("storeOrderDtlCtrl", function(event, data) {
            $scope.reqDate     = data.reqDate;
            $scope.slipFg      = data.slipFg;
            $scope.procFg      = data.procFg;
            $scope.dtlHdRemark = data.hdRemark;

            $scope.wjStoreOrderDtlLayer.show(true);
            if($scope.procFg === "00") {
                $("#btnAddProd").show();
                $("#btnDtlSave").show();
            }
            else {
                $("#btnAddProd").hide();
                $("#btnDtlSave").hide();
            }
            if("${envst173}" === "1" || "${envst173}" === "2") {
                $("#btnConfirm").show();
            }
            else {
                $("#btnConfirm").hide();
            }

            $("#spanDtlTitle").html('<s:message code="storeOrder.reqDate"/> : '+getFormatDate($scope.reqDate, '-'));
            $scope.searchStoreLoan("Y");
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 매장여신 조회
        $scope.searchStoreLoan = function(popShowFg) {
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
                    if(!$.isEmptyObject(response.data.data)) {
                        if(response.data.data.orderCloseYn === "Y") {
                            $scope.flex.isReadOnly = true;
                            $scope._popMsg('<s:message code="storeOrder.dtl.orderClose"/>');
                        }
                        else {
                            $scope.flex.isReadOnly = false;
                        }

                        $scope.prevOrderTot      = response.data.data.prevOrderTot;
                        $scope.limitLoanAmt      = response.data.data.limitLoanAmt;
                        $scope.currLoanAmt       = response.data.data.currLoanAmt;
                        $scope.maxOrderAmt       = response.data.data.maxOrderAmt;
                        $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;
                        $scope.availableOrderAmt = response.data.data.availableOrderAmt;

                        //미출고금액 고려여부 사용인 경우
                        if($scope.noOutstockAmtFg === "Y") {
                            if($scope.availableOrderAmt <= ($scope.currLoanAmt - $scope.prevOrderTot)) {
                            }
                            else if($scope.availableOrderAmt >= ($scope.currLoanAmt - $scope.prevOrderTot) && $scope.maxOrderAmt != 0) {
                                $scope.availableOrderAmt = $scope.currLoanAmt - $scope.prevOrderTot;
                            }
                            else {
                                $scope.availableOrderAmt = $scope.availableOrderAmt - $scope.prevOrderTot;
                            }
                        }

                        $("#dtlStoreLoanInfo").html("1회주문한도액 : "+addComma($scope.maxOrderAmt)+" 여신잔액 : "+addComma($scope.currLoanAmt)+" 미출고액 : "+addComma($scope.prevOrderTot)+" 주문가능액 : "+addComma($scope.availableOrderAmt));
                    }
                }
                else if(response.data.status === "FAIL") {
                    $scope._popMsg("Ajax Fail By HTTP Request");
                    return false;
                }
                else if(response.data.status === "SESSION_EXFIRE") {
                    $scope._popMsg(response.data.message, function() {
                        location.href = response.data.url;
                    });
                    return false;
                }
                else if(response.data.status === "SERVER_ERROR") {
                    $scope._popMsg(response.data.message);
                    return false;
                }
                else {
                    var msg = response.data.status + " : " + response.data.message;
                    $scope._popMsg(msg);
                    return false;
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }).then(function () {
                // "complete" code here
                if(popShowFg === "Y") {
                    $scope.wjStoreOrderDtlLayer.show(true);
                    $scope.searchStoreOrderDtlList();
                }
            });
        };

        // 주문등록 상세내역 리스트 조회
        $scope.searchStoreOrderDtlList = function() {
            // 파라미터
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/storeOrder/storeOrderDtl/list.sb", params);
        };

        // 주문 상세 저장
        $scope.saveStoreOrderDtl = function (saveFg) {
            var params = new Array();
            var orderTot = 0;
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];
                item.status  = "U";
                item.reqDate = $scope.reqDate;
                item.slipFg  = $scope.slipFg;
                item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리.
                item.hdRemark  = $scope.dtlHdRemark;
                orderTot += parseInt(item.orderTot);
                params.push(item);
            }

            // 길이체크
            if (params.length <= 0) {
                $scope._popMsg(messages["cmm.not.modify"]);
                return false;
            } else {
                params = JSON.stringify(params);
            }

            if($scope.availableOrderAmt != null) {
                // console.log("orderTot = "+orderTot);
                if(parseInt($scope.availableOrderAmt) < parseInt(orderTot)) {
                    $scope._popMsg('<s:message code="storeOrder.dtl.orderTotOver"/>');
                    return false;
                }
            }

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/save.sb', /* 통신할 URL */
                data: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                var storeOrderRegistCtrlScope = agrid.getScope('storeOrderRegistCtrl');

                if(storeOrderRegistCtrlScope.httpStatusCheck(response)) {
                    if(saveFg === "confirm") {
                        $scope.confirm();
                    }
                    else if(saveFg === "save") {
                        $scope.saveOrderDtlCallback();
                    }
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }).then(function () {
                // "complete" code here
            });
        };

        // 주문확정
        $scope.confirm = function () {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            params.remark  = $scope.dtlHdRemark;

            // TODO 테이블 및 VO 등이 전혀 생성되어 있지 않아 분배등록 및 출고자료 생성까지 진행 후 확정 로직 처리하는게 좋을듯. 2018-09-17 안동관
            return false;

            $scope._save("/iostock/order/storeOrder/storeOrderDtl/confirm.sb", params, function() { $scope.saveOrderDtlCallback() });
        };

        // 저장 후 콜백 함수
        $scope.saveOrderDtlCallback = function () {
            $scope.searchStoreOrderDtlList();
            $scope.searchStoreLoan("N");

            var storeOrderScope = agrid.getScope('storeOrderCtrl');
            storeOrderScope.searchStoreOrderList();
        };

        // 상품추가/변경
        $scope.addProd = function () {
            var params = {};
            params.callParent = "storeOrderDtl";
            params.reqDate    = $scope.reqDate;
            params.slipFg     = $scope.slipFg;
            params.hdRemark   = $scope.dtlHdRemark
            $scope._broadcast("storeOrderRegistCtrl", params);
        }


    }]);
</script>
