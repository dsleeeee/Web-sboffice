<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/move/hqStoreMove/hqStoreMoveRegist/"/>

<wj-popup id="wjHqStoreMoveRegistLayer" control="wjHqStoreMoveRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqStoreMoveRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqStoreMoveRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqStoreMove.reg.registTitle"/>
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
              <span class="txtIn"><input id="regMoveDate" class="w150px" ng-model="moveDate"></span>
            </div>
          </td>
          <%-- 배송구분 --%>
          <th><s:message code="hqStoreMove.reg.dlvrFg"/></th>
          <td>
            <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchRegDlvrFg"
                  ng-model="regDlvrFg"
                  items-source="_getComboData('srchRegDlvrFg')"
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
          <th><s:message code="hqStoreMove.reg.outStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 멀티 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
              <jsp:param name="targetId" value="hqStoreMoveRegistOutSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <%-- 입고매장 --%>
          <th><s:message code="hqStoreMove.reg.inStoreCd"/></th>
          <td colspan="3">
            <%-- 매장선택 모듈 싱글 선택 사용시 include
                 param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                              displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                              modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                              closeFunc - 팝업 닫기시 호출할 함수
            --%>
            <jsp:include page="/WEB-INF/view/iostock/order/outstockReqDate/selectShopS.jsp" flush="true">
              <jsp:param name="targetId" value="hqStoreMoveRegistInSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqStoreMove.reg.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="hqStoreMove.add.prodCd"/></th>
          <td>
            <input type="text" id="srchRegProdCd" name="srchRegProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="hqStoreMove.add.prodNm"/></th>
          <td>
            <input type="text" id="srchRegProdNm" name="srchRegProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li><s:message code="hqStoreMove.reg.txt1"/></li>
        <li><s:message code="hqStoreMove.reg.txt2"/></li>
        <li><s:message code="hqStoreMove.reg.txt3"/></li>
      </ul>

      <div class="mt10 pdb20 oh bb">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchHqStoreMoveRegistList();">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="tr mt20 fr">
        <%-- 저장 --%>
        <button type="button" id="btnRegSave" class="btn_skyblue ml5 fl" ng-click="save()">
          <s:message code="cmm.save"/></button>
        <%-- 저장 --%>
        <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="confirm()">
          <s:message code="hqStoreMove.reg.confirmBtn"/></button>
      </div>
      <div style="clear: both;"></div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap oh" style="height: 400px;">
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
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.totQty"/>" binding="outTotQty" width="70" align="right" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outSplyUprc"/>" binding="outSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outAmt"/>" binding="outAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outVat"/>" binding="outVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.outTot"/>" binding="outTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inSplyUprc"/>" binding="inSplyUprc" width="70" align="right" max-length=8 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inAmt"/>" binding="inAmt" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inVat"/>" binding="inVat" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.inTot"/>" binding="inTot" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>" binding="outEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqStoreMove.reg.envst0011"/>" binding="inEnvst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 매장이동관리 신규등록 그리드 controller */
  app.controller('hqStoreMoveRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqStoreMoveRegistCtrl', $scope, $http, true));

    $scope.moveDate = wcombo.genDate("#regMoveDate");

    $scope._setComboData("srchRegDlvrFg", [
      {"name": messages["hqStoreMove.dlvrFg0"], "value": "0"},
      {"name": messages["hqStoreMove.dlvrFg1"], "value": "1"},
      {"name": messages["hqStoreMove.dlvrFg2"], "value": "2"}
    ]);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // 그리드 포맷 핸들러
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col  = s.columns[e.col];
          var item = s.rows[e.row].dataItem;
          if (col.binding === "outEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
            if (item.poUnitQty === 1) {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              wijmo.setAttribute(e.cell, 'aria-readonly', true);

              // Attribute 의 변경사항을 적용.
              var html         = e.cell.outerHTML;
              e.cell.outerHTML = html;
            }
          }
        }
      });

      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          // 주문수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "outUnitQty" || col.binding === "outEtcQty" || col.binding === "outSplyUprc" || col.binding === "inSplyUprc") {
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

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      s.columnHeaders.rows[0].dataItem = {
        prodCd     : messages["hqStoreMove.reg.prodCd"],
        prodNm     : messages["hqStoreMove.reg.prodNm"],
        poUnitFg   : messages["hqStoreMove.reg.poUnitFg"],
        poUnitQty  : messages["hqStoreMove.reg.poUnitQty"],
        outUnitQty : messages["hqStoreMove.reg.qty"],
        outEtcQty  : messages["hqStoreMove.reg.qty"],
        outTotQty  : messages["hqStoreMove.reg.totQty"],
        outSplyUprc: messages["hqStoreMove.reg.out"],
        outAmt     : messages["hqStoreMove.reg.out"],
        outVat     : messages["hqStoreMove.reg.out"],
        outTot     : messages["hqStoreMove.reg.out"],
        inSplyUprc : messages["hqStoreMove.reg.in"],
        inAmt      : messages["hqStoreMove.reg.in"],
        inVat      : messages["hqStoreMove.reg.in"],
        inTot      : messages["hqStoreMove.reg.in"],
        vatFg      : messages["hqStoreMove.reg.vatFg"],
        envst0011  : messages["hqStoreMove.reg.envst0011"],
        envst0011  : messages["hqStoreMove.reg.envst0011"],
      };

      s.itemFormatter = function(panel, r, c, cell) {
        if (panel.cellType === wijmo.grid.CellType.ColumnHeader) {
          //align in center horizontally and vertically
          panel.rows[r].allowMerging    = true;
          panel.columns[c].allowMerging = true;
          wijmo.setCss(cell, {
            display    : 'table',
            tableLayout: 'fixed'
          });
          cell.innerHTML = '<div class=\"wj-header\">' + cell.innerHTML + '</div>';
          wijmo.setCss(cell.children[0], {
            display      : 'table-cell',
            verticalAlign: 'middle',
            textAlign    : 'center'
          });
        }
        // 로우헤더 의 RowNum 표시 ( 페이징/비페이징 구분 )
        else if (panel.cellType === wijmo.grid.CellType.RowHeader) {
          // GroupRow 인 경우에는 표시하지 않는다.
          if (panel.rows[r] instanceof wijmo.grid.GroupRow) {
            cell.textContent = '';
          } else {
            if (!isEmpty(panel._rows[r]._data.rnum)) {
              cell.textContent = (panel._rows[r]._data.rnum).toString();
            } else {
              cell.textContent = (r + 1).toString();
            }
          }
        }
        // readOnly 배경색 표시
        else if (panel.cellType === wijmo.grid.CellType.Cell) {
          var col = panel.columns[c];
          if (col.isReadOnly) {
            wijmo.addClass(cell, 'wj-custom-readonly');
          }
        }
      }
    };

    $scope.calcAmt = function (item) {
      <%-- 수량이 없는 경우 계산하지 않음.
      null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 --%>
      if (nvl(item.outUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.outEtcQty, null) === null)) return false;

      var outSplyUprc  = parseInt(nvl(item.outSplyUprc, 0));
      var inSplyUprc   = parseInt(nvl(item.inSplyUprc, 0));
      var poUnitQty    = parseInt(item.poUnitQty);
      var vat01        = parseInt(item.vatFg01);
      var outEnvst0011 = parseInt(item.outEnvst0011);
      var inEnvst0011  = parseInt(item.inEnvst0011);

      var unitQty    = parseInt(nvl(item.outUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty     = parseInt(nvl(item.outEtcQty, 0));
      var totQty     = parseInt(unitQty + etcQty);
      var tempOutAmt = Math.round(totQty * outSplyUprc / poUnitQty);
      var tempInAmt  = Math.round(totQty * inSplyUprc / poUnitQty);
      var outAmt     = tempOutAmt - Math.round(tempOutAmt * vat01 * outEnvst0011 / 11);
      var outVat     = Math.round(tempOutAmt * vat01 / (10 + outEnvst0011));
      var outTot     = parseInt(outAmt + outVat);
      var inAmt      = tempInAmt - Math.round(tempInAmt * vat01 * inEnvst0011 / 11);
      var inVat      = Math.round(tempInAmt * vat01 / (10 + inEnvst0011));
      var inTot      = parseInt(inAmt + inVat);

      item.outTotQty = totQty; // 총수량
      item.outAmt    = outAmt; // 이출금액
      item.outVat    = outVat; // 이출VAT
      item.outTot    = outTot; // 이출합계
      item.inAmt     = inAmt; // 이입금액
      item.inVat     = inVat; // 이입VAT
      item.inTot     = inTot; // 이입합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqStoreMoveRegistCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.wjHqStoreMoveRegistLayer.show(true);

      // 이출,이입매장 초기화
      $("#hqStoreMoveRegistOutSelectStoreCd").val("");
      $("#hqStoreMoveRegistInSelectStoreCd").val("");
      $("#hqStoreMoveRegistOutSelectStoreNm").val(messages["cmm.select"]);
      $("#hqStoreMoveRegistInSelectStoreNm").val(messages["cmm.select"]);

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 매장이동관리 신규등록 상품 리스트 조회
    $scope.searchHqStoreMoveRegistList = function () {
      if ($("#hqStoreMoveRegistOutSelectStoreCd").val() === "") {
        $scope._popMsg(messages["hqStoreMove.reg.require.selectOutStore"]); // 이출매장을 선택해주세요.
        return false;
      }
      if ($("#hqStoreMoveRegistInSelectStoreCd").val() === "") {
        $scope._popMsg(messages["hqStoreMove.reg.require.selectInStore"]); // 이입매장을 선택해주세요.
        return false;
      }
      if ($("#hqStoreMoveRegistOutSelectStoreCd").val() === $("#hqStoreMoveRegistInSelectStoreCd").val()) {
        $scope._popMsg(messages["hqStoreMove.reg.not.OutInStore"]); // 이출매장과 이입매장은 동일할 수 없습니다.
        return false;
      }

      $scope.outStoreCd = $("#hqStoreMoveRegistOutSelectStoreCd").val();
      $scope.inStoreCd  = $("#hqStoreMoveRegistInSelectStoreCd").val();

      // 파라미터
      var params        = {};
      params.outStoreCd = $scope.outStoreCd;
      params.inStoreCd  = $scope.inStoreCd;
      params.prodCd     = $scope.prodCd;
      params.prodNm     = $scope.prodNm;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/move/hqStoreMove/hqStoreMoveRegist/list.sb", params, function () {
      });
    };


    // 저장
    $scope.save = function (confirmFg) {
      var params = [];

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.outEtcQty !== null && (parseInt(item.outEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["hqStoreMove.reg.not.etcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.outTot !== null && (parseInt(item.outTot) > 9999999999)) {
          $scope._popMsg(messages["hqStoreMove.reg.not.overOutTot"]); // 이출금액이 너무 큽니다.
          return false;
        }
        if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
          $scope._popMsg(messages["hqStoreMove.reg.not.overInTot"]); // 이입금액이 너무 큽니다.
          return false;
        }

        item.status     = "U";
        item.moveDate   = wijmo.Globalize.format($scope.moveDate.value, 'yyyyMMdd');
        item.outStoreCd = $scope.outStoreCd;
        item.inStoreCd  = $scope.inStoreCd;
        item.dlvrFg     = $scope.regDlvrFg;
        item.remark     = $scope.hdRemark;
        item.storageCd  = "001";
        item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.confirmFg  = confirmFg;

        params.push(item);
      }

      $scope._save("/iostock/move/hqStoreMove/hqStoreMoveRegist/save.sb", params, function () {
        $scope.saveHqStoreMoveRegistCallback()
      });
    };


    $scope.confirm = function () {
      var msg = messages["hqStoreMove.reg.confirmMsg"]; // 현전표를 확정하시겠습니까?
      s_alert.popConf(msg, function () {
        $scope.save('Y');
      });
    };


    $scope.saveHqStoreMoveRegistCallback = function () {
      $scope.wjHqStoreMoveRegistLayer.hide(true);

      var hqStoreMoveScope = agrid.getScope('hqStoreMoveCtrl');
      hqStoreMoveScope.searchHqStoreMoveList();
    };


    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.hqStoreMoveRegistOutSelectStoreShow = function () {
      $scope._broadcast('hqStoreMoveRegistOutSelectStoreCtrl');
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.hqStoreMoveRegistInSelectStoreShow = function () {
      $scope._broadcast('hqStoreMoveRegistInSelectStoreCtrl');
    };


  }]);
</script>
