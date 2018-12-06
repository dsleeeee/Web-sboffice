<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/"/>

<wj-popup id="wjRtnInstockConfmDtlLayer" control="wjRtnInstockConfmDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnInstockConfmDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnInstockConfmDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
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
          <th><s:message code="rtnInstockConfm.dtl.hdRemark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="rtnInstockConfm.dtl.dlvrNm"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="srchDtlDlvrCd"
                ng-model="dlvrCd"
                items-source="_getComboData('srchDtlDlvrCd')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
          </td>
        </tr>
        <tr>
          <%-- 거래명세표 --%>
          <th><s:message code="rtnInstockConfm.dtl.stmtAcct"/></th>
          <td colspan="3">
            <span class="txtIn w150px sb-select fl mr5">
              <wj-combo-box
                id="stmtAcctFg"
                ng-model="stmtAcctFg"
                items-source="_getComboData('stmtAcctFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnInstockConfm.dtl.stmtAcctPrint"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="rtnInstockConfm.dtl.stmtAcctExcel"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt2"/></li>
        <li class="red"><s:message code="rtnInstockConfm.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="instockBtnLayer" style="display: none;">
          <%-- 입고확정 체크박스 --%>
          <span id="spanInstockConfirmFg" class="chk pdb5 txtIn fl" style="top: 0px;" ng-if="spanInstockConfirmFg">
            <input type="checkbox" name="instockConfirmFg" id="instockConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="instockConfirmFg"><s:message code="rtnInstockConfm.dtl.confirmFg"/></label>
          </span>
          <%-- 입고일자 --%>
          <div id="divDtlInDate" class="sb-select ml10 fl" style="display: none;">
            <span class="txtIn"><input id="dtlInDate" class="w120px"></span>
          </div>
          <%-- 출고내역으로 입고내역 세팅 --%>
          <button type="button" id="btnSetOutToIn" class="btn_skyblue ml5 fl" ng-click="setOutToIn()" ng-if="btnSetOutToIn"><s:message code="rtnInstockConfm.dtl.setOutToIn"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
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
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.slipNo"/>" binding="slipNo" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.slipFg"/>" binding="slipFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.barcdCd"/>" binding="barcdCd" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outSplyUprc"/>" binding="outSplyUprc" width="70" align="right" is-read-only="true" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outUnitQty"/>" binding="outUnitQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.outEtcQty"/>" binding="outEtcQty" width="70" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inUnitQty"/>" binding="inUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inEtcQty"/>" binding="inEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inTotQty"/>" binding="inTotQty" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.penaltyAmt"/>" binding="penaltyAmt" width="70" align="right" is-read-only="false" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnInstockConfm.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 입고확정 상세 그리드 controller */
  app.controller('rtnInstockConfmDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnInstockConfmDtlCtrl', $scope, $http, true));

    $scope.dtlInDate = wcombo.genDate("#dtlInDate");

    $scope._setComboData("stmtAcctFg", [
      {"name": messages["rtnInstockConfm.dtl.stmtAcctAll"], "value": ""},
      {"name": messages["rtnInstockConfm.dtl.stmtAcctSplr"], "value": "1"},
      {"name": messages["rtnInstockConfm.dtl.stmtAcctSplrRcpnt"], "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var rtnInstockConfmScope = agrid.getScope('rtnInstockConfmCtrl');
      // 배송기사
      var comboParams             = {};
      var url = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
      rtnInstockConfmScope._queryCombo("combo", "srchDtlDlvrCd", null, url, comboParams, "S"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "inEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
            if (item.poUnitQty === 1) {
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
          if (col.binding === "inUnitQty" || col.binding === "inEtcQty") { // 입고수량 수정시
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
      var outSplyUprc = parseInt(item.outSplyUprc);
      var poUnitQty   = parseInt(item.poUnitQty);
      var vat01       = parseInt(item.vatFg01);
      var envst0011   = parseInt(item.envst0011);

      var unitQty = parseInt(nvl(item.inUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.inEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * outSplyUprc / poUnitQty);
      var inAmt   = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var inVat   = Math.round(tempAmt * vat01 / (10 + envst0011));
      var inTot   = parseInt(inAmt + inVat);

      item.inTotQty = totQty; // 총입고수량
      item.inAmt    = inAmt; // 금액
      item.inVat    = inVat; // VAT
      item.inTot    = inTot; // 합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnInstockConfmDtlCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.wjRtnInstockConfmDtlLayer.show(true);

      $scope.getSlipNoInfo();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 전표상세 조회
    $scope.getSlipNoInfo = function () {
      var params    = {};
      params.slipNo = $scope.slipNo;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/getSlipNoInfo.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope.httpStatusCheck(response)) {
          if (!$.isEmptyObject(response.data.data)) {

            $scope.dtlInDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
            $scope.outDate         = response.data.data.outDate;
            $scope.inDate          = response.data.data.inDate;
            $scope.slipFg          = response.data.data.slipFg;
            $scope.slipKind        = response.data.data.slipKind;
            $scope.slipKindNm      = response.data.data.slipKindNm;
            $scope.procFg          = response.data.data.procFg;
            $scope.storeCd         = response.data.data.storeCd;
            $scope.storeNm         = response.data.data.storeNm;
            $scope.hdRemark        = response.data.data.remark;
            $scope.dlvrCd          = nvl(response.data.data.dlvrCd, '');
            $scope.dlvrNm          = response.data.data.dlvrNm;

            // 출고확정
            if ($scope.procFg === "20") {
              $("#spanDtlTitle").html(messages["rtnInstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnInstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnInstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate));
              $scope.instockBtnLayerDisplay(true);
            }
            // 수주확정 또는 입고확정
            else if ($scope.procFg === "10" || $scope.procFg === "30") {
              $scope.instockBtnLayerDisplay(false);
              $scope.flex.isReadOnly = true;

              // 수주확정
              if ($scope.procFg === "10") {
                $("#spanDtlTitle").html(messages["rtnInstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnInstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnInstockConfm.dtl.reqDate"]+' : ' + getFormatDate($scope.outDate));
              }
              // 입고확정
              else if ($scope.procFg === "30") {
                $("#spanDtlTitle").html(messages["rtnInstockConfm.dtl.slipNo"]+' : ' + $scope.slipNo + ', '+messages["rtnInstockConfm.dtl.store"]+' : ' + $scope.storeNm + ', '+messages["rtnInstockConfm.dtl.outDate"]+' : ' + getFormatDate($scope.outDate) + ', '+messages["rtnInstockConfm.dtl.inDate"]+' : ' + getFormatDate($scope.inDate));
              }
            }

            $scope.searchRtnInstockConfmDtlList();
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


    // 입고확정 상세내역 리스트 조회
    $scope.searchRtnInstockConfmDtlList = function () {
      // 파라미터
      var params    = {};
      params.slipNo = $scope.slipNo;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/list.sb", params, function () {
      });
    };


    // 저장
    $scope.save = function () {
      var params = [];

      // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
      if ($("#instockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
        var item = $scope.flex.collectionView.items[0];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.inUnitQty === null && item.inEtcQty === null) {
          $scope._popMsg(messages["rtnInstockConfm.dtl.require.inQty"]); // 입고수량을 입력해주세요.
          return false;
        }
        if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["rtnInstockConfm.dtl.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
          $scope._popMsg(messages["rtnInstockConfm.dtl.not.overInTot"]); // 입고금액이 너무 큽니다.
          return false;
        }

        item.status    = "U";
        item.inDate    = wijmo.Globalize.format($scope.dtlInDate.value, 'yyyyMMdd');
        item.hdRemark  = $scope.hdRemark;
        item.confirmFg = ($("#instockConfirmFg").is(":checked") ? $("#instockConfirmFg").val() : "");

        params.push(item);
      }

      $scope._save("/iostock/orderReturn/rtnInstockConfm/rtnInstockConfmDtl/save.sb", params, function () {
        $scope.saveRtnInstockConfmDtlCallback()
      });
    };


    // 저장 후 콜백 함수
    $scope.saveRtnInstockConfmDtlCallback = function () {
      var rtnInstockConfmScope = agrid.getScope('rtnInstockConfmCtrl');
      rtnInstockConfmScope.searchRtnInstockConfmList();

      $scope.wjRtnInstockConfmDtlLayer.hide(true);
    };


    $scope.fnConfirmChk = function () {
      if ($("#instockConfirmFg").prop("checked")) {
        $("#divDtlInDate").show();
      }
      else {
        $("#divDtlInDate").hide();
      }
    };


    // 출고내역으로 입고내역 세팅
    $scope.setOutToIn = function () {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        $scope.flex.collectionView.editItem(item);

        item.inUnitQty = item.outUnitQty;
        item.inEtcQty  = item.outEtcQty;
        $scope.calcAmt(item);

        $scope.flex.collectionView.commitEdit();
      }
    };


    $scope.instockBtnLayerDisplay = function(isVisible) {
      if(isVisible) {
        $("#instockBtnLayer").show();
      }
      else {
        $("#instockBtnLayer").hide();
      }

      $scope.spanInstockConfirmFg = isVisible;
      $scope.btnSetOutToIn = isVisible;
      $scope.btnDtlSave = isVisible;
    };


    // http 조회 후 status 체크
    $scope.httpStatusCheck = function (res) {
      if (res.data.status === "OK") {
        return true;
      }
      else if (res.data.status === "FAIL") {
        $scope._popMsg("Ajax Fail By HTTP Request");
        return false;
      }
      else if (res.data.status === "SESSION_EXFIRE") {
        $scope._popMsg(res.data.message, function () {
          location.href = res.data.url;
        });
        return false;
      }
      else if (res.data.status === "SERVER_ERROR") {
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
