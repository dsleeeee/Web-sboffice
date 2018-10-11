<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbReq/dstbReq/"/>

<wj-popup id="wjDstbReqDtlLayer" control="wjDstbReqDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="dstbReqDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbReqDtlCtrl">
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
                    <th><s:message code="dstbReq.remark"/></th>
                    <td colspan="3"><input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/></td>
                </tr>
                </tbody>
            </table>

            <ul class="txtSty3 mt10">
                <li class="red">기준 공급가 : 마스터상의 출고단가.</li>
                <li class="red">분배완료여부를 체크한 후 저장하시면 분배 자료를 생성하며, 더이상 수정이 불가능합니다.</li>
            </ul>

            <div class="tr mt20">
                <p id="dtlAvailableOrderAmt" class="fl s14 bk lh30"></p>
                <div id="dstbBtnLayer" style="display: none;">
                    <span id="spanDstbConfirmFg" class="chk pdb5 txtIn" style="top: 0px;"><input type="checkbox" name="dstbConfirmFg" id="dstbConfirmFg" value="Y"/>
                        <label for="dstbConfirmFg" ><s:message code="dstbReq.dtl.dstbConfirmFg" /></label>
                    </span>
                    <%-- 공급가 및 수량적용 --%>
                    <button type="button" id="btnDtlApply" class="btn_skyblue ml5" ng-click="setQtyApply()"><s:message code="dstbReq.dtl.qtyApply" /></button>
                    <%-- 저장 --%>
                    <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveValueCheck()"><s:message code="cmm.save" /></button>
                </div>
            </div>
            <div style="clear: both;"></div>

            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 400px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="false"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                      binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.storeCd"/>"          binding="storeCd"          width="0"   align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prodCd"/>"           binding="prodCd"           width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prodNm"/>"           binding="prodNm"           width="150" align="left"   is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.poUnitFg"/>"         binding="poUnitFg"         width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.poUnitQty"/>"        binding="poUnitQty"        width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.splyUprc"/>"         binding="splyUprc"         width="70"  align="right"  is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderUnitQty"/>"     binding="orderUnitQty"     width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderEtcQty"/>"      binding="orderEtcQty"      width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.orderTotQty"/>"      binding="orderTotQty"      width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdSplyUprc"/>"       binding="mdSplyUprc"       width="70"  align="right"  is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdUnitQty"/>"        binding="mdUnitQty"        width="70"  align="right"  is-read-only="false" max-length=8  data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdEtcQty"/>"         binding="mdEtcQty"         width="70"  align="right"  is-read-only="false" max-length=8  data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdTotQty"/>"         binding="mdTotQty"         width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.prevMdTotQty"/>"     binding="prevMdTotQty"     width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdAmt"/>"            binding="mdAmt"            width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdVat"/>"            binding="mdVat"            width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.mdTot"/>"            binding="mdTot"            width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.remark"/>"           binding="remark"           width="200" align="left"   is-read-only="false" max-length=300></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.vatFg"/>"            binding="vatFg01"          width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbReq.dtl.envst0011"/>"        binding="envst0011"        width="70"  align="right"  is-read-only="true" visible="false"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>

            </div>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">

    /** 주문등록 상세 그리드 controller */
    app.controller('dstbReqDtlCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('dstbReqDtlCtrl', $scope, $http, true));

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 그리드 포맷 핸들러
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    var item = s.rows[e.row].dataItem;
                    if(col.binding === "mdEtcQty") { // 입수에 따라 분배수량 컬럼 readonly 컨트롤
                        // console.log(item);
                        if(item.poUnitQty === 1) {
                            wijmo.addClass(e.cell, 'wj-custom-readonly');
                            wijmo.setAttribute(e.cell, 'aria-readonly', true);

                            // Attribute 의 변경사항을 적용.
                            e.cell.outerHTML = e.cell.outerHTML;
                        }
                    }
                }
            });

            s.cellEditEnded.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "mdSplyUprc" || col.binding === "mdUnitQty" || col.binding === "mdEtcQty") { // 분배수량 수정시
                        var item = s.rows[e.row].dataItem;
                        $scope.calcAmt(item);
                    }
                }

                s.collectionView.commitEdit();
            });

            // add the new GroupRow to the grid's 'columnFooters' panel
            s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 금액 계산
        $scope.calcAmt = function (item) {
            var mdSplyUprc    = parseInt(item.mdSplyUprc);
            var poUnitQty     = parseInt(item.poUnitQty);
            var vat01         = parseInt(item.vatFg01);
            var envst0011     = parseInt(item.envst0011);

            var unitQty = parseInt(nvl(item.mdUnitQty,0)) * parseInt(item.poUnitQty);
            var etcQty  = parseInt(nvl(item.mdEtcQty,0));
            var totQty  = parseInt(unitQty + etcQty);
            var tempOrderAmt = Math.round(totQty * mdSplyUprc / poUnitQty);
            var mdAmt = tempOrderAmt - Math.round(tempOrderAmt * vat01 * envst0011 / 11);
            var mdVat = Math.round(tempOrderAmt * vat01 / (10 + envst0011));
            var mdTot = parseInt(mdAmt + mdVat);

            item.mdTotQty = totQty;// 총분배수량
            item.mdAmt    = mdAmt; // 금액
            item.mdVat    = mdVat; // VAT
            item.mdTot    = mdTot; // 합계
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("dstbReqDtlCtrl", function(event, data) {
            $scope.reqDate     = data.reqDate;
            $scope.storeCd     = data.storeCd;
            $scope.storeNm     = data.storeNm;
            $scope.slipFg      = data.slipFg;
            $scope.procFg      = data.procFg;

            $("#spanDtlTitle").html('[<s:message code="dstbReq.dtl.order"/>] '+'['+$scope.storeCd+'] '+$scope.storeNm);
            $scope.orderProcFgCheck();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 주문진행구분 체크 및 HD 비고 조회
        $scope.orderProcFgCheck = function() {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            params.storeCd = $scope.storeCd;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if($scope.httpStatusCheck(response)) {
                    // 진행구분이 주문등록이 아니면 상품추가/변경 불가
                    if(!$.isEmptyObject(response.data.data)) {
                        $scope.procFg   = response.data.data.procFg;
                        $scope.hdRemark = response.data.data.remark;
                    }
                    $scope.searchStoreLoan();
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

        // 매장여신 조회
        $scope.searchStoreLoan = function() {
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;
            params.storeCd = $scope.storeCd;

            // ajax 통신 설정
            $http({
                method: 'POST', //방식
                url: '/iostock/order/storeOrder/storeOrderRegist/storeLoan.sb', /* 통신할 URL */
                params: params, /* 파라메터로 보낼 데이터 */
                headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
            }).then(function successCallback(response) {
                if($scope.httpStatusCheck(response)) {
                    if(!$.isEmptyObject(response.data.data)) {
                        $scope.prevOrderTot      = response.data.data.prevOrderTot;
                        $scope.limitLoanAmt      = response.data.data.limitLoanAmt;
                        $scope.currLoanAmt       = response.data.data.currLoanAmt;
                        $scope.maxOrderAmt       = response.data.data.maxOrderAmt;
                        $scope.noOutstockAmtFg   = response.data.data.noOutstockAmtFg;
                        $scope.availableOrderAmt = response.data.data.availableOrderAmt;

                        //미출고금액 고려여부 사용인 경우
                        if($scope.noOutstockAmtFg === "Y") {
                            if($scope.availableOrderAmt <= ($scope.currLoanAmt - $scope.prevOrderTot)) {
                              // 해당 조건에는 조회해 온 주문가능액 그대로 사용
                            }
                            else if($scope.availableOrderAmt >= ($scope.currLoanAmt - $scope.prevOrderTot) && $scope.maxOrderAmt != 0) {
                                $scope.availableOrderAmt = $scope.currLoanAmt - $scope.prevOrderTot;
                            }
                            else {
                                $scope.availableOrderAmt = $scope.availableOrderAmt - $scope.prevOrderTot;
                            }
                        }

                        $("#dtlAvailableOrderAmt").html("주문가능액 : "+addComma($scope.availableOrderAmt));
                    }
                }
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope._popMsg(messages["cmm.saveFail"]);
                return false;
            }).then(function () {
                // "complete" code here
                $scope.wjDstbReqDtlLayer.show(true);
                $("#dstbConfirmFg").prop("checked", false);

                // 주문내역이 분배완료가 아닌 경우 분배 저장버튼 show
                if($scope.procFg !== "20") {
                    $("#dstbBtnLayer").show();
                }
                else {
                    $("#dstbBtnLayer").hide();
                }

                $scope.searchDstbReqDtlList();
            });
        };

        // 주문등록 상세내역 리스트 조회
        $scope.searchDstbReqDtlList = function() {
            // 파라미터
            var params = {};
            params.reqDate = $scope.reqDate;
            params.storeCd = $scope.storeCd;
            params.slipFg  = $scope.slipFg;
            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquirySub("/iostock/order/dstbReq/dstbReqDtl/list.sb", params, function () {
            });
        };

        // 공급가 및 수량적용
        $scope.setQtyApply = function () {
            for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
                var item = $scope.flex.collectionView.items[i];
                $scope.flex.collectionView.editItem(item);

                item.mdSplyUprc = item.splyUprc;
                item.mdUnitQty  = item.orderUnitQty;
                item.mdEtcQty   = item.orderEtcQty;
                item.mdTotQty   = item.splyUprc;
                $scope.calcAmt(item);

                $scope.flex.collectionView.commitEdit();
            }
        };

        // 저장 전 값 체크
        $scope.saveValueCheck = function () {
            var params = new Array();
            var mdTot = 0;

            // 분배완료여부가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
            if($("#dstbConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
                var item = $scope.flex.collectionView.items[0];
                if(item === null) return false;

                $scope.flex.collectionView.editItem(item);
                item.status = "U";
                $scope.flex.collectionView.commitEdit();
            }

            for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
                var item = $scope.flex.collectionView.itemsEdited[i];

                if(item.mdUnitQty === null && item.mdEtcQty === null) {
                    $scope._popMsg(messages["dstbReq.dtl.require.mdQty"]); // 분배수량을 입력해주세요.
                    return false;
                }
                if(item.mdEtcQty !== null && (parseInt(item.mdEtcQty) >= parseInt(item.poUnitQty))) {
                    $scope._popMsg(messages["dstbReq.dtl.not.mdEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
                    return false;
                }
                if(item.mdTot !== null && (parseInt(item.mdTot) > 9999999999)) {
                    $scope._popMsg(messages["dstbReq.dtl.not.overMdTot"]); // 분배금액이 너무 큽니다.
                    return false;
                }

                item.status = "U";
                item.reqDate = $scope.reqDate;
                item.slipFg = $scope.slipFg;
                item.empNo = "0000";
                item.storageCd = "001";
                item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
                item.dstbConfirmFg = ($("#dstbConfirmFg").is(":checked") ? $("#dstbConfirmFg").val() : "");
                item.hdRemark  = $scope.hdRemark;
                mdTot += parseInt(item.mdTot);
                params.push(item);
            }

            if ($scope.availableOrderAmt != null && parseInt($scope.availableOrderAmt) < parseInt(mdTot)) {
                var msg = "<s:message code='dstbReq.dtl.mdTotOver'/>"; //분배금액이 주문가능액을 초과하였습니다. 계속 진행 하시겠습니까?
                var id = s_alert.randomString(5);
                var pop = $("#_layerConf").clone(true).attr("id", id).appendTo(document.body);
                pop.find("p").text(msg);

                // 확인 클릭
                pop.find("a.btn_blue.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();
                    $scope.saveDstbReqDtl(params);
                });

                // 취소 클릭
                pop.find("a.btn_gray.conf").bind("click", function () {
                    $("#_alertTent").hide();
                    pop.remove();
                    // 그리드 초기화
                    var cv = new wijmo.collections.CollectionView([]);
                    cv.trackChanges = true;
                    $scope.data = cv;
                    return false;
                });

                $("#_alertTent").show();
                pop.show();
            }
            else {
                $scope.saveDstbReqDtl(params);
            }
        };

        // 분배 저장
        $scope.saveDstbReqDtl = function (params) {
            $scope._save("/iostock/order/dstbReq/dstbReqDtl/save.sb", params, function() { $scope.saveDstbReqDtlCallback() });
        };

        // 저장 후 콜백 함수
        $scope.saveDstbReqDtlCallback = function () {
            var dstbReqScope = agrid.getScope('dstbReqCtrl');
            dstbReqScope.searchDstbReqList();

            $scope.wjDstbReqDtlLayer.hide(true);
        };

        // http 조회 후 status 체크
        $scope.httpStatusCheck = function (res) {
            if(res.data.status === "OK") {
                return true;
            }
            else if(res.data.status === "FAIL") {
                $scope._popMsg("Ajax Fail By HTTP Request");
                return false;
            }
            else if(res.data.status === "SESSION_EXFIRE") {
                $scope._popMsg(res.data.message, function() {
                    location.href = res.data.url;
                });
                return false;
            }
            else if(res.data.status === "SERVER_ERROR") {
                $scope._popMsg(res.data.message);
                return false;
            }
            else {
                var msg = res.data.status + " : " + res.data.message;
                $scope._popMsg(msg);
                return false;
            }
        };

    }]);
</script>
