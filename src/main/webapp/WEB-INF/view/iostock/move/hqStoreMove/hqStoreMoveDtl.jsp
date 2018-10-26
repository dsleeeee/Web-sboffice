<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqStoreMove/hqStoreMoveDtl/"/>

<wj-popup id="wjHqStoreMoveDtlLayer" control="wjHqStoreMoveDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqStoreMoveDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqStoreMoveDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="storeOrder.dtl.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="hqStoreMove.reg.moveDate"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn"><input id="srchStartDate" class="w150"></span>
              <span class="rg">~</span>
              <span class="txtIn"><input id="srchEndDate" class="w150"></span>
            </div>
          </td>
          <%-- 배송구분 --%>
          <th><s:message code="hqStoreMove.dlvrFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150">
                <wj-combo-box
                  id="srchDlvrFg"
                  ng-model="dlvrFg"
                  items-source="_getComboData('srchDlvrFg')"
                  display-member-path="name"
                  selected-value-path="value"
                  is-editable="false"
                  initialized="_initComboBox(s)">
                </wj-combo-box>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <%-- 출고매장 --%>
          <th><s:message code="hqStoreMove.outStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 멀티 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
                              cd - 로딩시 세팅할 매장코드
                              nm - 로딩시 세팅할 매장명
            --%>
            <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
              <jsp:param name="targetId" value="hqStoreMoveDtlOutSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 입고매장 --%>
          <th><s:message code="hqStoreMove.inStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
                              cd - 로딩시 세팅할 매장코드
                              nm - 로딩시 세팅할 매장명
            --%>
            <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
              <jsp:param name="targetId" value="hqStoreMoveDtlInSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 싱글 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqStoreMove.reg.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <table class="tblType01 mt10">
        <colgroup>
          <col class="w20"/>
          <col class="w30"/>
          <col class="w20"/>
          <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
          <th><s:message code="hqStoreMove.reg.insDt"/></th>
          <td><p id="insDt" class="s12"></p></td>
          <th><s:message code="hqStoreMove.reg.outConfmDt"/></th>
          <td><p id="outConfmDt" class="s12"></p></td>
        </tr>
        <tr>
          <th><s:message code="hqStoreMove.reg.inConfmDt"/></th>
          <td><p id="inConfmDt" class="s12"></p></td>
          <th><s:message code="hqStoreMove.reg.hqConfmDt"/></th>
          <td><p id="hqConfmDt" class="s12"></p></td>
        </tr>        <tr>
          <th><s:message code="hqStoreMove.reg.outSlipNo"/></th>
          <td><p id="outSlipNo" class="s12"></p></td>
          <th><s:message code="hqStoreMove.reg.inSlipNo"/></th>
          <td><p id="inSlipNo" class="s12"></p></td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="hqStoreMove.reg.txt1"/></li>
        <li class="red"><s:message code="hqStoreMove.reg.txt2"/></li>
        <li class="red"><s:message code="hqStoreMove.reg.txt3"/></li>
        <li class="red"><s:message code="hqStoreMove.reg.txt4"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="hqStoreMoveBtnLayer" style="display: none;">
          <%-- 저장 --%>
          <button type="button" id="btnAddProd" class="btn_skyblue ml5 fl" ng-click="addRow()">
            <s:message code="cmm.save"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnRegSave" class="btn_skyblue ml5 fl" ng-click="save()">
            <s:message code="cmm.save"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()">
            <s:message code="cmm.save"/></button>
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
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.unitQty"/>" binding="outUnitQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.etcQty"/>" binding="outEtcQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.splyUprc"/>" binding="outSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.amt"/>" binding="outAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vat"/>" binding="outVat" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.tot"/>" binding="outTot" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.splyUprc"/>" binding="inSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.amt"/>" binding="inAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vat"/>" binding="inVat" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.tot"/>" binding="inTot" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vatFg"/>" binding="outVatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vatFg"/>" binding="inVatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 물량오류 상세 그리드 controller */
  app.controller('hqStoreMoveDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqStoreMoveDtlCtrl', $scope, $http, true));

    $scope.outDate = wcombo.genDate("#dtlOutDate");

    $scope.errFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["hqStoreMove.reg"]},
      {id: "1", name: messages["hqStoreMove.confirm"]},
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqStoreMoveDtlCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.wjHqStoreMoveDtlLayer.show(true);

      if ($scope.procFg === "0") {
        $("#hqStoreMoveBtnLayer").show();
      }
      else {
        $("#hqStoreMoveBtnLayer").hide();
      }
      $scope.searchHqStoreMoveDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 매장이동관리 상세내역 리스트 조회
    $scope.searchHqStoreMoveDtlList = function () {
      // 파라미터
      var params    = {};
      params.slipNo = $scope.slipNo;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/move/hqStoreMove/hqStoreMoveDtl/list.sb", params, function () {
      });
    };


    // 저장
    $scope.save = function () {
      var params = new Array();
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.errFg === null || item.errFg === "") {
          $scope._popMsg(messages["hqStoreMove.reg.require.selectErrFg"]); // 처리구분을 선택해 주세요.
          return false;
        }

        item.status           = "U";
        item.slipNo           = $scope.slipNo;
        item.slipFg           = $scope.slipFg;
        item.storeCd          = $scope.storeCd;
        item.hdRemark         = $scope.hdRemark;
        item.outDate          = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
        item.confirmFg        = ($("#hqStoreMoveConfirmFg").is(":checked") ? $("#hqStoreMoveConfirmFg").val() : "");
        item.procFg           = $scope.procFg;
        item.newSlipNoFg      = newSlipNoFg;
        item.hqNewAdjustFg    = hqNewAdjustFg;
        item.storeNewAdjustFg = storeNewAdjustFg;

        params.push(item);
      }

      $scope._save("/iostock/move/hqStoreMove/hqStoreMoveDtl/save.sb", params, function () {
        $scope.saveHqStoreMoveDtlCallback()
      });
    };

    $scope.saveHqStoreMoveDtlCallback = function () {
      $scope.wjHqStoreMoveDtlLayer.hide(true);

      var hqStoreMoveScope = agrid.getScope('hqStoreMoveCtrl');
      hqStoreMoveScope.searchHqStoreMoveList();

    };

    // 확정체크시 값 체크
    $scope.fnConfirmChk = function () {
      if ($("#hqStoreMoveConfirmFg").is(":checked")) {
        var showDate = "N";
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.errFg === null || item.errFg === "") {
            $scope._popMsg(messages["hqStoreMove.reg.require.selectErrFg"]); // 처리구분을 선택해 주세요.
            $("#hqStoreMoveConfirmFg").prop("checked", false);
            return false;
          }
          if (showDate === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
            showDate = "Y";
          }
        }

        if (showDate === "Y") {
          $("#divDtlOutDate").show();
        }
      }
      else {
        $("#divDtlOutDate").hide();
      }
    };

  }]);
</script>
