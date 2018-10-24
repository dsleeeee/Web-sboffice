<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstmn/dstmnDtl/"/>

<wj-popup id="wjDstmnDtlLayer" control="wjDstmnDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dstmnDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstmnDtlCtrl">
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
          <th><s:message code="dstmn.dtl.hdRemark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="dstmn.dtl.hqRemark"/></th>
          <td colspan="3">
            <input type="text" id="hqRemark" name="hqRemark" ng-model="hqRemark" class="sb-input w100" maxlength="300"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="dstmn.dtl.dlvrNm"/></th>
          <td colspan="3"></td>
        </tr>
        <tr>
          <%-- 거래명세표 --%>
          <th><s:message code="dstmn.dtl.stmtAcct"/></th>
          <td colspan="3">
            <span class="txtIn w150 sb-select fl mr5">
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
            <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.dtl.stmtAcctPrint"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="dstmn.dtl.stmtAcctExcel"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="dstmn.dtl.txt1"/></li>
        <li class="red"><s:message code="dstmn.dtl.txt2"/></li>
        <li class="red"><s:message code="dstmn.dtl.txt3"/></li>
      </ul>

      <div class="tr mt20 fr">
        <div id="outstockBtnLayer" style="display: none;">
          <span id="spanOutstockConfirmFg" class="chk pdb5 txtIn fl" style="top: 0px;">
            <input type="checkbox" name="outstockConfirmFg" id="outstockConfirmFg" value="Y" ng-click="fnConfirm()"/>
            <label for="outstockConfirmFg"><s:message code="dstmn.dtl.confirmFg"/></label>
          </span>
          <div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">
            <span class="txtIn"><input id="dtlOutDate" class="w120"></span>
          </div>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()"><s:message code="cmm.save"/></button>
        </div>
        <div id="outstockAfterBtnLayer" style="display: none;">
          <%-- 출고 후 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="saveOutstockAfter()"><s:message code="cmm.save"/></button>
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
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.slipNo"/>" binding="slipNo" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.slipFg"/>" binding="slipFg" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.barcdCd"/>" binding="barcdCd" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outSplyUprc"/>" binding="outSplyUprc" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outUnitQty"/>" binding="outUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outEtcQty"/>" binding="outEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outTotQty"/>" binding="outTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outAmt"/>" binding="outAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outVat"/>" binding="outVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.outTot"/>" binding="outTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstmn.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 거래명세표 상세 그리드 controller */
  app.controller('dstmnDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dstmnDtlCtrl', $scope, $http, true));

    $scope.dtlOutDate = wcombo.genDateVal("#dtlOutDate", "${sessionScope.sessionInfo.startDate}");

    $scope._setComboData("stmtAcctFg", [
      {"name": "<s:message code='dstmn.dtl.stmtAcctAll'/>", "value": ""},
      {"name": "<s:message code='dstmn.dtl.stmtAcctSplr'/>", "value": "1"},
      {"name": "<s:message code='dstmn.dtl.stmtAcctSplrRcpnt'/>", "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "outEtcQty") { // 입수에 따라 출고수량 컬럼 readonly 컨트롤
            // console.log(item);
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
          if (col.binding === "outSplyUprc" || col.binding === "outUnitQty" || col.binding === "outEtcQty") { // 출고수량 수정시
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

      var unitQty = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.outEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * outSplyUprc / poUnitQty);
      var outAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var outVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
      var outTot  = parseInt(outAmt + outVat);

      item.outTotQty = totQty; // 총출고수량
      item.outAmt    = outAmt; // 금액
      item.outVat    = outVat; // VAT
      item.outTot    = outTot; // 합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dstmnDtlCtrl", function (event, data) {
      $scope.slipNo = data.slipNo;
      $scope.wjDstmnDtlLayer.show(true);

      $scope.getSlipNoInfo();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    $scope.getSlipNoInfo = function () {
      var params    = {};
      params.slipNo = $scope.slipNo;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/order/dstmn/dstmnDtl/getSlipNoInfo.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope.httpStatusCheck(response)) {
          if (!$.isEmptyObject(response.data.data)) {

            $scope.dtlOutDate.value = new Date(getFormatDate(response.data.data.outDate, "-"));
            $scope.outDate          = response.data.data.outDate;
            $scope.inDate           = response.data.data.inDate;
            $scope.slipFg           = response.data.data.slipFg;
            $scope.slipKind         = response.data.data.slipKind;
            $scope.slipKindNm       = response.data.data.slipKindNm;
            $scope.procFg           = response.data.data.procFg;
            $scope.storeCd          = response.data.data.storeCd;
            $scope.storeNm          = response.data.data.storeNm;
            $scope.hdRemark         = response.data.data.remark;
            $scope.hqRemark         = response.data.data.hqRemark;
            $scope.dlvrCd           = response.data.data.dlvrCd;
            $scope.dlvrNm           = response.data.data.dlvrNm;

            // 수주확정
            if ($scope.procFg === "10") {
              $("#spanDtlTitle").html('<s:message code="dstmn.dtl.slipNo"/> : ' + $scope.slipNo + ' <s:message code="dstmn.dtl.store"/> : ' + $scope.storeNm + ' <s:message code="dstmn.dtl.reqDate"/> : ' + getFormatDate($scope.outDate));
              $("#spanOutstockConfirmFg").show();
              $("#outstockBtnLayer").show();
              $("#outstockAfterBtnLayer").hide();
              $scope.flex.isReadOnly = false;
            }
            // 출고확정 또는 입고확정
            else if ($scope.procFg === "20" || $scope.procFg === "30") {
              $("#spanOutstockConfirmFg").hide();
              $("#outstockBtnLayer").hide();
              $("#outstockAfterBtnLayer").show();
              $scope.flex.isReadOnly = true;

              // 출고확정
              if ($scope.procFg === "20") {
                $("#spanDtlTitle").html('<s:message code="dstmn.dtl.slipNo"/> : ' + $scope.slipNo + ' <s:message code="dstmn.dtl.store"/> : ' + $scope.storeNm + ' <s:message code="dstmn.dtl.outDate"/> : ' + getFormatDate($scope.outDate));
              }
              // 입고확정
              else if ($scope.procFg === "30") {
                $("#spanDtlTitle").html('<s:message code="dstmn.dtl.slipNo"/> : ' + $scope.slipNo + ' <s:message code="dstmn.dtl.store"/> : ' + $scope.storeNm + ' <s:message code="dstmn.dtl.outDate"/> : ' + getFormatDate($scope.outDate) + ' <s:message code="dstmn.dtl.inDate"/> : ' + getFormatDate($scope.inDate));
              }
            }

            $scope.searchDstmnDtlList();
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

    // 거래명세표 상세내역 리스트 조회
    $scope.searchDstmnDtlList = function () {
      // 파라미터
      var params    = {};
      params.slipNo = $scope.slipNo;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/order/dstmn/dstmnDtl/list.sb", params, function () {
      });
    };

    // 저장
    $scope.save = function () {
      var params = new Array();

      // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
      if ($("#outstockConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
        var item = $scope.flex.collectionView.items[0];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.outUnitQty === null && item.outEtcQty === null) {
          $scope._popMsg(messages["dstmn.dtl.require.outQty"]); // 출고수량을 입력해주세요.
          return false;
        }
        if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["dstmn.dtl.not.outEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
          $scope._popMsg(messages["dstmn.dtl.not.overOutTot"]); // 출고금액이 너무 큽니다.
          return false;
        }

        item.status    = "U";
        item.outDate   = wijmo.Globalize.format($scope.dtlOutDate.value, 'yyyyMMdd');
        item.hdRemark  = $scope.hdRemark;
        item.hqRemark  = $scope.hqRemark;
        item.dlvrCd    = $scope.dlvrCd;
        item.confirmFg = ($("#outstockConfirmFg").is(":checked") ? $("#outstockConfirmFg").val() : "");

        params.push(item);
      }

      $scope._save("/iostock/order/dstmn/dstmnDtl/save.sb", params, function () {
        $scope.saveDstmnDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveDstmnDtlCallback = function () {
      var dstmnScope = agrid.getScope('dstmnCtrl');
      dstmnScope.searchDstmnList();

      $scope.wjDstmnDtlLayer.hide(true);
    };

    // 출고확정 이후 저장. 비고, 본사비고, 배송기사를 저장한다.
    $scope.saveOutstockAfter = function () {
      // 파라미터
      var params      = {};
      params.slipNo   = $scope.slipNo;
      params.hdRemark = $scope.hdRemark;
      params.hqRemark = $scope.hqRemark;
      params.dlvrCd   = $scope.dlvrCd;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/order/dstmn/dstmnDtl/saveOutstockAfter.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope.httpStatusCheck(response)) {
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.flex.collectionView.clearChanges();
          $scope.saveDstmnDtlCallback();
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

    $scope.fnConfirm = function () {
      if ($("#outstockConfirmFg").prop("checked")) {
        $("#divDtlOutDate").show();
      }
      else {
        $("#divDtlOutDate").hide();
      }
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
