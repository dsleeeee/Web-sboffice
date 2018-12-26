<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbReq/rtnDstbReqDtl/"/>

<wj-popup id="wjRtnDstbReqDtlLayer" control="wjRtnDstbReqDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnDstbReqDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbReqDtlCtrl">
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
          <th><s:message code="rtnDstbReq.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="rtnDstbReq.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnDstbReq.dtl.txt2"/></li>
      </ul>

      <div class="tr mt20">
        <p id="dtlAvailableOrderAmt" class="fl s14 bk lh30"></p>
        <div id="dstbBtnLayer" ng-if="dstbBtnLayer">
          <span id="spanDstbConfirmFg" class="chk pdb5 txtIn" style="top: 0px;"><input type="checkbox" name="dstbConfirmFg" id="dstbConfirmFg" value="Y"/>
              <label for="dstbConfirmFg"><s:message code="rtnDstbReq.dtl.dstbConfirmFg"/></label>
          </span>
          <%-- 공급가 및 수량적용 --%>
          <button type="button" id="btnDtlApply" class="btn_skyblue ml5" ng-click="setQtyApply()"><s:message code="rtnDstbReq.dtl.qtyApply"/></button>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="saveValueCheck()"><s:message code="cmm.save"/></button>
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
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.splyUprc"/>" binding="splyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.orderUnitQty"/>" binding="orderUnitQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.orderEtcQty"/>" binding="orderEtcQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.orderTotQty"/>" binding="orderTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdSplyUprc"/>" binding="mdSplyUprc" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdUnitQty"/>" binding="mdUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdEtcQty"/>" binding="mdEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdTotQty"/>" binding="mdTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.prevMdTotQty"/>" binding="prevMdTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdAmt"/>" binding="mdAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdVat"/>" binding="mdVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.mdTot"/>" binding="mdTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbReq.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 반품등록(요청분) 상세 그리드 controller */
  app.controller('rtnDstbReqDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnDstbReqDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "097";
      var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
      $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "mdEtcQty") { // 입수에 따라 분배수량 컬럼 readonly 컨트롤
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
      var mdSplyUprc = parseInt(item.mdSplyUprc);
      var poUnitQty  = parseInt(item.poUnitQty);
      var vat01      = parseInt(item.vatFg01);
      var envst0011  = parseInt(item.envst0011);

      var unitQty = parseInt(nvl(item.mdUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.mdEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * mdSplyUprc / poUnitQty);
      var mdAmt   = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var mdVat   = Math.round(tempAmt * vat01 / (10 + envst0011));
      var mdTot   = parseInt(mdAmt + mdVat);

      item.mdTotQty = totQty;// 총분배수량
      item.mdAmt    = mdAmt; // 금액
      item.mdVat    = mdVat; // VAT
      item.mdTot    = mdTot; // 합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnDstbReqDtlCtrl", function (event, data) {
      $scope.reqDate = data.reqDate;
      $scope.storeCd = data.storeCd;
      $scope.storeNm = data.storeNm;
      $scope.slipFg  = data.slipFg;
      $scope.procFg  = data.procFg;

      $("#spanDtlTitle").html('['+messages["rtnDstbReq.dtl.orderReturn"]+'] ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);
      $scope.orderProcFgCheck();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 주문진행구분 체크 및 HD 비고 조회
    $scope.orderProcFgCheck = function () {
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;
      params.storeCd = $scope.storeCd;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/orderReturn/rtnStoreOrder/rtnStoreOrderRegist/orderProcFgCheck.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          if (!$.isEmptyObject(response.data.data)) {
            $scope.procFg   = response.data.data.procFg;
            $scope.hdRemark = response.data.data.remark;
          }
          $scope.wjRtnDstbReqDtlLayer.show(true);
          $scope.searchRtnDstbReqDtlList();
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

    // 분배요청 상세내역 리스트 조회
    $scope.searchRtnDstbReqDtlList = function () {
      // 파라미터
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.storeCd = $scope.storeCd;
      params.slipFg  = $scope.slipFg;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/orderReturn/rtnDstbReq/rtnDstbReqDtl/list.sb", params, function () {
        $("#dstbConfirmFg").prop("checked", false);

        // 주문내역이 분배완료가 아닌 경우 분배 저장버튼 show
        if($scope.procFg !== "20") {
          $scope.dstbBtnLayer = true;
        }
        else {
          $scope.dstbBtnLayer = false;
        }
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
        $scope.calcAmt(item);

        $scope.flex.collectionView.commitEdit();
      }
    };

    // 저장 전 값 체크
    $scope.saveValueCheck = function () {
      if ($("#dstbConfirmFg").is(":checked")) {
        // 분배완료를 체크하셨습니다. 분배자료를 생성하므로 주문내역의 자료를 수정하실 수 없습니다. 계속 하시겠습니까?
        var msg = messages["rtnDstbReq.dtl.confirmText"];
        s_alert.popConf(msg, function () {
          $scope.saveRtnDstbReqDtl();
        });
      }
      else {
        $scope.saveRtnDstbReqDtl();
      }
    };

    // 분배 저장
    $scope.saveRtnDstbReqDtl = function () {
      var params = [];
      // 분배완료여부가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
      if ($("#dstbConfirmFg").is(":checked") && $scope.flex.collectionView.itemsEdited.length <= 0) {
        var item = $scope.flex.collectionView.items[0];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.mdUnitQty === null && item.mdEtcQty === null) {
          $scope._popMsg(messages["rtnDstbReq.dtl.require.mdQty"]); // 분배수량을 입력해주세요.
          return false;
        }
        if (item.mdEtcQty !== null && (parseInt(item.mdEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["rtnDstbReq.dtl.not.mdEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.mdTot !== null && (parseInt(item.mdTot) > 9999999999)) {
          $scope._popMsg(messages["rtnDstbReq.dtl.not.overMdTot"]); // 분배금액이 너무 큽니다.
          return false;
        }

        item.status        = "U";
        item.reqDate       = $scope.reqDate;
        item.slipFg        = $scope.slipFg;
        item.empNo         = "0000";
        item.storageCd     = "001";
        item.hqBrandCd     = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.dstbConfirmFg = ($("#dstbConfirmFg").is(":checked") ? $("#dstbConfirmFg").val() : "");
        item.hdRemark      = $scope.hdRemark;
        params.push(item);
      }

      $scope._save("/iostock/orderReturn/rtnDstbReq/rtnDstbReqDtl/save.sb", params, function () {
        $scope.saveRtnDstbReqDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveRtnDstbReqDtlCallback = function () {
      var rtnDstbReqScope = agrid.getScope('rtnDstbReqCtrl');
      rtnDstbReqScope.searchRtnDstbReqList();

      $scope.wjRtnDstbReqDtlLayer.hide(true);
    };


    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성. 두가지 다 사용할경우 combo,map 으로 하면 둘 다 생성.
    // comboId : combo 생성할 ID
    // gridMapId : grid 에서 사용할 Map ID
    // url : 데이터 조회할 url 정보. 명칭관리 조회시에는 url 필요없음.
    // params : 데이터 조회할 url에 보낼 파라미터
    // option : A - combo 최상위에 전체라는 텍스트를 붙여준다. S - combo 최상위에 선택이라는 텍스트를 붙여준다. A 또는 S 가 아닌 경우는 데이터값만으로 생성
    // callback : queryCombo 후 callback 할 함수
    $scope._queryCombo = function (comboFg, comboId, gridMapId, url, params, option, callback) {
      var comboUrl = "/iostock/cmm/iostockCmm/getCombo.sb";
      if (url) {
        comboUrl = url;
      }

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : comboUrl, /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          if (!$.isEmptyObject(response.data.data.list)) {
            var list       = response.data.data.list;
            var comboArray = [];
            var comboData  = {};

            if (comboFg.indexOf("combo") >= 0 && nvl(comboId, '') !== '') {
              comboArray = [];
              if (option === "A") {
                comboData.name  = messages["cmm.all"];
                comboData.value = "";
                comboArray.push(comboData);
              } else if (option === "S") {
                comboData.name  = messages["cmm.select"];
                comboData.value = "";
                comboArray.push(comboData);
              }

              for (var i = 0; i < list.length; i++) {
                comboData       = {};
                comboData.name  = list[i].nmcodeNm;
                comboData.value = list[i].nmcodeCd;
                comboArray.push(comboData);
              }
              $scope._setComboData(comboId, comboArray);
            }

            if (comboFg.indexOf("map") >= 0 && nvl(gridMapId, '') !== '') {
              comboArray = [];
              for (var i = 0; i < list.length; i++) {
                comboData      = {};
                comboData.id   = list[i].nmcodeCd;
                comboData.name = list[i].nmcodeNm;
                comboArray.push(comboData);
              }
              $scope[gridMapId] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
            }
          }
        }
      }, function errorCallback(response) {
        $scope._popMsg(messages["cmm.error"]);
        return false;
      }).then(function () {
        if (typeof callback === 'function') {
          $timeout(function () {
            callback();
          }, 10);
        }
      });
    };

  }]);
</script>
