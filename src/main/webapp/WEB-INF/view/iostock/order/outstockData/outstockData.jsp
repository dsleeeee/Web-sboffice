<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockData/outstockData/"/>

<div class="subCon" ng-controller="outstockDataCtrl">
    <div class="searchBar">
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
            <%-- 조회일자 --%>
            <th><s:message code="cmm.search.date"/></th>
            <td colspan="3">
                <div class="sb-select">
                    <span class="txtIn w150">
                    <wj-combo-box
                            id="srchDateFg"
                            ng-model="dateFg"
                            items-source="_getComboData('srchDateFg')"
                            display-member-path="name"
                            selected-value-path="value"
                            is-editable="false"
                            initialized="_initComboBox(s)">
                    </wj-combo-box>
                    </span>
                    <span class="txtIn"><input id="srchStartDate" class="w150"></span>
                    <span class="rg">~</span>
                    <span class="txtIn"><input id="srchEndDate" class="w150"></span>
                </div>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('outstockDataCtrl')"><s:message code="cmm.search" /></button>
    </div>

    <div class="tr mt10 fr">
        <%-- 출고일자 --%>
        <p class="s14 bk fl mr10 lh30"><s:message code="outstockData.outDate"/></p>
        <div class="sb-select mr10 fl">
            <span class="txtIn"><input id="outDate" class="w150"></span>
        </div>
        <%-- 출고자료생성 --%>
        <button type="button" id="btnDataCreate" class="btn_skyblue ml5 fl" ng-click="saveValueCheck()"><s:message code="outstockData.dataCreate" /></button>
    </div>
    <div style="clear: both;"></div>

    <div class="w100 mt10" >
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
            <wj-flex-grid
                    autoGenerateColumns="false"
                    selection-mode="Row"
                    items-source="data"
                    control="flex"
                    initialized="initGrid(s,e)"
                    is-read-only="false"
                    item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="cmm.chk"/>"                           binding="gChk"                  width="40"  align="center" is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.storeCd"/>"              binding="storeCd"               width="70"  align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.storeNm"/>"              binding="storeNm"               width="150" align="left"   is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.sysStatFg"/>"            binding="sysStatFg"             width="70"  align="center" is-read-only="true" data-map="sysStatFgMap"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.dstbAmt"/>"              binding="dstbAmt"               width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.dstbVat"/>"              binding="dstbVat"               width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.dstbTot"/>"              binding="dstbTot"               width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.dtlCnt"/>"               binding="dtlCnt"                width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.currLoanAmt"/>"          binding="currLoanAmt"           width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.availableOrderAmt"/>"    binding="availableOrderAmt"     width="70"  align="right"  is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.remark"/>"               binding="remark"                width="150" align="left"   is-read-only="false"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="outstockData.hqRemark"/>"             binding="hqRemark"              width="150" align="left"   is-read-only="false"></wj-flex-grid-column>

            </wj-flex-grid>
            <%-- ColumnPicker 사용시 include --%>
            <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
                <jsp:param name="pickerTarget" value="outstockDataCtrl"/>
            </jsp:include>
            <%--// ColumnPicker 사용시 include --%>
        </div>
        <%--//위즈모 테이블--%>
    </div>
</div>



<script type="text/javascript">
    /**
     * get application
     */
    var app = agrid.getApp();

    /** 출고자료생성 그리드 controller */
    app.controller('outstockDataCtrl', ['$scope', '$http', function ($scope, $http) {
        // 상위 객체 상속 : T/F 는 picker
        angular.extend(this, new RootController('outstockDataCtrl', $scope, $http, true));

        $scope.slipFg = 1;
        var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDt}");
        var srchEndDate   = wcombo.genDateVal("#srchEndDate"  , "${sessionScope.sessionInfo.startDt}");
        var outDate       = wcombo.genDateVal("#outDate"      , "${sessionScope.sessionInfo.startDt}");
        var sysStatFg     = ${ccu.getCommCode("005")};

        // 그리드 DataMap 설정
        $scope.sysStatFgMap = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

      $scope._setComboData("srchDateFg", [
        {"name":"<s:message code='outstockData.reqDate'/>","value":"req"},
        {"name":"<s:message code='outstockData.regDate'/>","value":"reg"},
        {"name":"<s:message code='outstockData.modDate'/>","value":"mod"}
      ]);

        // grid 초기화 : 생성되기전 초기화되면서 생성된다
        $scope.initGrid = function (s, e) {

            // picker 사용시 호출 : 미사용시 호출안함
            $scope._makePickColumns("outstockDataCtrl");

            // 그리드 링크 효과
            s.formatItem.addHandler(function (s, e) {
                if (e.panel === s.cells) {
                    var col = s.columns[e.col];
                    if (col.binding === "storeCd") { // 매장코드
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
                    if ( col.binding === "storeCd") { // 매장코드 클릭
                        var params = {};
                        params.dateFg    = $scope.searchedDateFg;
                        params.startDate = $scope.searchedStartDate;
                        params.endDate   = $scope.searchedEndDate;
                        params.storeCd   = selectedRow.storeCd;
                        params.storeNm   = selectedRow.storeNm;
                        params.slipFg    = $scope.slipFg;
                        $scope._broadcast('outstockDataDtlCtrl', params);
                    }
                }
            });

            // add the new GroupRow to the grid's 'columnFooters' panel
            s.columnFooters.rows.push(new wijmo.grid.GroupRow());
            // add a sigma to the header to show that this is a summary row
            s.bottomLeftCells.setCellData(0, 0, '합계');
        };

        // 다른 컨트롤러의 broadcast 받기
        $scope.$on("outstockDataCtrl", function(event, data) {
            $scope.searchOutstockDataList();
            // 기능수행 종료 : 반드시 추가
            event.preventDefault();
        });

        // 주문 리스트 조회
        $scope.searchOutstockDataList = function() {
            // 조회 당시의 조회일값 세팅
            $scope.searchedDateFg    = $scope.dateFg;
            $scope.searchedStartDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
            $scope.searchedEndDate   = wijmo.Globalize.format(srchEndDate.value  , 'yyyyMMdd');

            // 파라미터
            var params = {};
            params.dateFg    = $scope.searchedDateFg;
            params.slipFg    = $scope.slipFg;
            params.startDate = $scope.searchedStartDate;
            params.endDate   = $scope.searchedEndDate;

            // 조회 수행 : 조회URL, 파라미터, 콜백함수
            $scope._inquiryMain("/iostock/order/outstockData/outstockData/list.sb", params);
        };

        $scope.saveValueCheck = function () {
          var params = new Array();
          var sysStatFgCheck = true;
          var loanCheck = true;

          if ($scope.flex.collectionView.itemsEdited.length <= 0) {
            $scope._popMsg(messages["cmm.not.modify"]);
            return false;
          }

          for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
            var item = $scope.flex.collectionView.itemsEdited[i];

            if(item.gChk === true) {
              if(sysStatFgCheck && item.sysStatFg !== "1") {
                sysStatFgCheck = false;
              }
              if(loanCheck && item.availableOrderAmt !== null && (parseInt(item.dstbTot) > parseInt(item.availableOrderAmt))) {
                loanCheck = false;
              }
              item.status    = "U";
              item.dateFg    = $scope.searchedDateFg;
              item.startDate = $scope.searchedStartDate;
              item.endDate   = $scope.searchedEndDate;
              item.outDate   = wijmo.Globalize.format(outDate.value, 'yyyyMMdd');
              item.empNo     = "0000";
              item.storageCd = "001";
              item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
              params.push(item);
            }
          }

          if(!sysStatFgCheck) {
            <%-- 선택하신 자료 중 매장상태가 오픈이 아닌 매장이 있습니다. 계속하시겠습니까? --%>
            var msg = "<s:message code='outstockData.sysStatFgCheck'/>";
            s_alert.popConf(msg, function(){ $scope.loanCheckConfirm(loanCheck, params); });
            return false;
          }
          else {
            $scope.loanCheckConfirm(loanCheck, params);
          }
        };

        // 주문가능액 confirm
        $scope.loanCheckConfirm = function (loanCheck, params) {
          if(!loanCheck) {
            <%-- 선택하신 자료중 출고합계금액이 주문가능액보다 큰 매장이 있습니다. 계속하시겠습니까? --%>
            var msg = "<s:message code='outstockData.loanCheck'/>";
            s_alert.popConf(msg, function(){ $scope.slipNoCreateConfirm(params); });
            return false;
          }
          else {
            $scope.slipNoCreateConfirm(params);
          }
        };

        // 전표생성 confirm
        $scope.slipNoCreateConfirm = function (params) {
          <%-- 선택하신 자료를 주문전표로 생성합니다. 계속하시겠습니까? --%>
          var msg = "<s:message code='outstockData.orderSlipNoCreate'/>";
          s_alert.popConf(msg, function(){ $scope.saveDataCreate(params); });
          return false;
        };

        // 출고자료생성
        $scope.saveDataCreate = function (params) {
          $scope._save("/iostock/order/outstockData/outstockData/saveDataCreate.sb", params, function() { $scope.searchOutstockDataList() });
        };
    }]);
</script>

<%-- 출고자료생성 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockData/outstockDataDtl.jsp">
    <c:param name="menuCd" value="${menuCd}"/>
    <c:param name="menuNm" value="${menuNm}"/>
</c:import>
