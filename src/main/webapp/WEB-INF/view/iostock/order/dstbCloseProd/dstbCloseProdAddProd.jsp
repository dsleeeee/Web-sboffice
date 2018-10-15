<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdAddProd/"/>

<wj-popup id="wjDstbCloseProdAddProdLayer" control="wjDstbCloseProdAddProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
    <div id="dstbCloseProdAddProdLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdAddProdCtrl">
        <div class="wj-dialog-header wj-dialog-header-font">
            <s:message code="dstbCloseProd.add.title" />
            <a href="#" class="wj-hide btn_close"></a>
        </div>
        <div class="wj-dialog-body sc2" style="height: 600px;">
            <p class="s14 bk mb5 fl"><s:message code="dstbCloseProd.add.addProdSubTitle" /></p><p id="addProdSubTitle" class="s14 bk ml5 mb5 fl"></p><p id="orderFgSubTitle" class="s14 bk ml5 mb5 fl"></p>
            <table class="tblType01">
                <colgroup>
                    <col class="w15" />
                    <col class="w35" />
                    <col class="w15" />
                    <col class="w35" />
                </colgroup>
                <tbody>
                <tr>
                    <%-- 매장코드 --%>
                    <th><s:message code="dstbCloseProd.add.store"/></th>
                    <td>
                        <%-- 매장선택 모듈 싱글 선택 사용시 include --%>
                        <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopM.jsp" flush="true">
                            <jsp:param name="targetId" value="dstbCloseProdAddProdSelectStore"/>
                        </jsp:include>
                        <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
                    </td>
                    <td colspan="2"><p class="s14">등록하실 매장을 선택한 후 상품을 조회하십시오.</p></td>
                </tr>
                <tr>
                    <%-- 상품코드 --%>
                    <th><s:message code="dstbCloseProd.add.prodCd" /></th>
                    <td><input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/></td>
                    <%-- 상품코드 --%>
                    <th><s:message code="dstbCloseProd.add.prodNm" /></th>
                    <td><input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/></td>
                </tr>
                <tr>
                    <%-- 바코드 --%>
                    <th><s:message code="dstbCloseProd.add.barcd"/></th>
                    <td><input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/></td>
                    <%-- 분류 --%>
                    <th><s:message code="dstbCloseProd.add.prodClassNm"/></th>
                    <td><input type="text" id="srchProdClass" name="prodClass" ng-model="prodClass" class="sb-input w100" maxlength="40"/></td>
                </tr>
                </tbody>
            </table>

            <div class="mt10 oh">
                <%-- 조회 --%>
                <button type="button" class="btn_blue fr" id="btnSearch" ng-click="search();"><s:message code="cmm.search" /></button>
            </div>

            <div class="mt40 oh sb-select dkbr">
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
                <%-- 저장 --%>
                <button type="button" class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveDstbCloseProdAddProd()"><s:message code="cmm.save" /></button>
            </div>

            <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
            <div class="w100 mt10 mb20">
                <%--위즈모 테이블--%>
                <div class="wj-gridWrap" style="height: 500px;">
                    <wj-flex-grid
                            autoGenerateColumns="false"
                            selection-mode="Row"
                            items-source="data"
                            control="flex"
                            initialized="initGrid(s,e)"
                            is-read-only="true"
                            item-formatter="_itemFormatter">

                        <!-- define columns -->
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodClassNm"/>"      binding="prodClassNm"  width="150" align="left"   is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodCd"/>"           binding="prodCd"       width="120" align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodNm"/>"           binding="prodNm"       width="*"   align="left"   is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitFg"/>"         binding="poUnitFg"     width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitQty"/>"        binding="poUnitQty"    width="70"  align="right"  is-read-only="true"></wj-flex-grid-column>
                        <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.splyUprc"/>"         binding="splyUprc"     width="100" align="right"  is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

                    </wj-flex-grid>
                </div>
                <%--//위즈모 테이블--%>
            </div>
            <%-- 페이지 리스트 --%>
            <div class="pageNum mt20">
                <%-- id --%>
                <ul id="dstbCloseProdAddProdCtrlPager" data-size="10">
                </ul>
            </div>
            <%--//페이지 리스트--%>
        </div>
    </div>
</wj-popup>


<script type="text/javascript">

    /** 분배마감 추가등록 상품 그리드 controller */
    app.controller('dstbCloseProdAddProdCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('dstbCloseProdAddProdCtrl', $scope, $http, true));

        //페이지 스케일 콤보박스 데이터 Set
        $scope._setComboData("listScaleBox", gvListScaleBoxData);

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {
            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "prodCd") { // 상품코드
                        wijmo.addClass(e.cell, 'wijLink');
                        wijmo.addClass(e.cell, 'wj-custom-readonly');
                    }

                    if(col.format === "date") {
                        e.cell.innerHTML = getFormatDate(e.cell.innerText);
                    }
                    else if(col.format === "dateTime") {
                        e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
                    }
                }
            });

            // 그리드 클릭 이벤트
            s.addEventListener(s.hostElement, 'mousedown', function(e) {
                var ht = s.hitTest(e);
                if( ht.cellType === wijmo.grid.CellType.Cell) {
                    var col = ht.panel.columns[ht.col];
                    var selectedRow = s.rows[ht.row].dataItem;
                    if ( col.binding === "prodCd") { // 상품코드 클릭
                        var params = {};
                        params.reqDate  = $scope.reqDate;
                        params.prodCd   = selectedRow.prodCd;
                        params.prodNm   = selectedRow.prodNm;
                        params.slipFg   = $scope.slipFg;
                        params.storeCds = $("#dstbCloseProdAddProdSelectStoreCd").val();
                        $scope._broadcast('dstbCloseProdAddRegistCtrl', params);
                    }
                }
            });
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("dstbCloseProdAddProdCtrl", function(event, data) {

            // 그리드 초기화
            var cv = new wijmo.collections.CollectionView([]);
            cv.trackChanges = true;
            $scope.data = cv;

            if(!$.isEmptyObject(data)) {
                $scope.reqDate = data.reqDate;
                $scope.slipFg  = data.slipFg;
                $scope.wjDstbCloseProdAddProdLayer.show(true);
                $("#addProdSubTitle").html(' (<s:message code="dstbCloseProd.add.reqDate"/> : '+getFormatDate($scope.reqDate, '-')+')');
            }
            else { // 페이징처리에서 broadcast 호출시
                $scope.searchDstbCloseProdAddProdList();
            }

            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 조회
        $scope.search = function() {
            $scope.searchDstbCloseProdAddProdList();
        };

        // 분배가능상품 리스트 조회
        $scope.searchDstbCloseProdAddProdList = function() {
            // 파라미터
            var params = {};
            params.reqDate = $scope.reqDate;
            params.slipFg  = $scope.slipFg;

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/dstbCloseProd/dstbCloseProdAddProd/list.sb", params);
        };

        // 매장선택 모듈 팝업 사용시 정의
        // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
        // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
        $scope.dstbCloseProdAddProdSelectStoreShow = function () {
            $scope._broadcast('dstbCloseProdAddProdSelectStoreCtrl');
        };
    }]);

</script>
