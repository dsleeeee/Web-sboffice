<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/storeOrder/storeOrderDtl/"/>

<wj-popup id="wjStoreOrderDtlLayer" control="wjStoreOrderDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="storeOrderDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="storeOrderDtlCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="outstockReqDate.specificDate" /> &nbsp;<s:message code="cmm.new.add" />
            <a href="javascript:;" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body">
            <div class="wj-TblWrap ml20 mr20 pdb20">
                <div class="mt20 oh sb-select dkbr">
                    <span class="fl bk lh30"><s:message code='deliveryCharger.chargeStorage' /></span>
                    <div class="tr">
                        <%-- 상품추가/변경 --%>
                        <button type="button" id="btnAddProd" class="btn_skyblue ml5" ng-click="addProd()" style="display:none"><s:message code="storeOrder.addProd" /></button>
                        <%-- 저장 --%>
                        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveDtl()" style="display:none"><s:message code="cmm.save" /></button>
                    </div>
                </div>

                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 300px;">
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
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.prevOrderTotQty"/>"  binding="prevOrderTotQty"  width="70"  align="right"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderUnitQty"/>"     binding="orderUnitQty"     width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderEtcQty"/>"      binding="orderEtcQty"      width="70"  align="right"  max-length=8 data-type="Number" format="n0" aggregate="Sum" allow-merging="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTotQty"/>"      binding="orderTotQty"      width="70"  align="right"  visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderAmt"/>"         binding="orderAmt"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderVat"/>"         binding="orderVat"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.orderTot"/>"         binding="orderTot"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitFg"/>"         binding="poUnitFg"         width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="storeOrder.dtl.poUnitQty"/>"        binding="poUnitQty"        width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
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
    app.controller('storeOrderDtlCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('storeOrderDtlCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            s.cellEditEnded.addHandler(function (s, e) {
                if (e.panel == s.cells) {
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
            $scope.reqDate = data.reqDate;
            $scope.slipFg  = data.slipFg;
            $scope.procFg  = data.procFg;

            if($scope.procFg == "0") {
                $("#btnAddProd").show();
                $("#btnDtlSave").show();
            }

            $scope.wjStoreOrderDtlLayer.show(true);
            $scope.searchStoreOrderDtlList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        $scope.searchStoreOrderDtlList = function() {
            // 파라미터
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/storeOrder/storeOrderDtl/list.sb", params, function () {
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

        $scope.saveStoreOrderDtl = function () {
            var params = new Array();
            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                $scope.flex.collectionView.itemsEdited[i].status = "U";
                params.push($scope.flex.collectionView.itemsEdited[i]);
            }
            $scope._save("/iostock/order/storeClose/storeClose/save.sb", params, function() { $scope.searchStoreOrderDtlList() });

        };

    }]);
</script>
