<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/vendr/vendrInstock/vendrInstockProdReg/"/>

<wj-popup id="wjVendrInstockProdRegLayer" control="wjVendrInstockProdRegLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="vendrInstockProdRegLayer" class="wj-dialog wj-dialog-columns" ng-controller="vendrInstockProdRegCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="vendrInstock.reg.title"/>
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
          <%-- 상품코드 --%>
          <th><s:message code="vendrInstock.reg.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="vendrInstock.reg.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="vendrInstock.reg.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 상품분류 --%>
          <th><s:message code="vendrInstock.reg.prodClass"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="vendrInstock.reg.excelFormDownload"/></a>
            <span class="txtIn w120px" style="border:1px solid #e8e8e8;">
              <wj-combo-box
                id="addQtyFg"
                ng-model="addQtyFg"
                items-source="_getComboData('addQtyFg')"
                display-member-path="name"
                selected-value-path="value"
                is-editable="false"
                initialized="_initComboBox(s)">
              </wj-combo-box>
            </span>
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="vendrInstock.reg.excelFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="vendrInstock.reg.textFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="vendrInstock.reg.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="fnSearch();">
          <s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="vendrInstock.reg.txt1"/></li>
        <li class="red"><s:message code="vendrInstock.reg.txt2"/></li>
      </ul>

      <div class="mt20 tr">
        <c:if test="${sessionInfo.orgnFg == 'HQ'}">
        <span class="chk pdb5 txtIn" style="top: 0px;">
          <input type="checkbox" name="storeSplyChk" id="storeSplyChk" ng-model="storeSplyChk"/>
          <label for="storeSplyChk"><s:message code="vendrInstock.reg.storeSplyUprcCheck"/></label>
        </span>
        </c:if>
        <%-- 최종원가를 발주원가로 세팅 --%>
        <button type="button" class="btn_skyblue ml5" id="btnLastCostToCostUprc" ng-click="setLastCostToCostUprc()">
          <s:message code="vendrInstock.reg.lastCostToCostUprc"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnProdRegSave" ng-click="save()">
          <s:message code="cmm.save"/></button>
      </div>

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
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.lastCostUprc"/>" binding="lastCostUprc" width="60" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.poUnitFg"/>" binding="poUnitFg" width="60" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.poUnitQty"/>" binding="poUnitQty" width="50" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.prevInUnitQty"/>" binding="prevInUnitQty" width="40" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.prevInEtcQty"/>" binding="prevInEtcQty" width="40" align="right" is-read-only="true" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.costUprc"/>" binding="costUprc" width="110" align="right" is-read-only="false" max-length=8></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inUnitQty"/>" binding="inUnitQty" width="40" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inEtcQty"/>" binding="inEtcQty" width="40" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.prevInTotQty"/>" binding="prevInTotQty" width="0" align="left" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inTotQty"/>" binding="inTotQty" width="0" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inAmt"/>" binding="inAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inVat"/>" binding="inVat" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.inTot"/>" binding="inTot" width="85" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.vatFg"/>" binding="vatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.vendrVatFg01"/>" binding="vendrVatFg01" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="vendrInstock.reg.storeSplyUprc"/>" binding="splyUprc" width="70" align="right" is-read-only="false" visible="{{storeSplyChk}}" max-length=8></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="vendrInstockProdRegCtrlPager" data-size="10">
          </ul>
        </div>
        <%--//페이지 리스트--%>
      </div>
    </div>
  </div>
</wj-popup>

<script type="text/javascript">

  /** 입고/반출 상품 추가/변경 그리드 controller */
  app.controller('vendrInstockProdRegCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('vendrInstockProdRegCtrl', $scope, $http, true));

    $scope._setComboData("safeStockFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["vendrInstock.reg.safeStockY"], "value": "Y"}
    ]);

    $scope._setComboData("addQtyFg", [
      {"name": messages["vendrInstock.reg.addQtyFgApply"], "value": "apply"},
      {"name": messages["vendrInstock.reg.addQtyFgAdd"], "value": "add"}
    ]);

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
          if (col.binding === "inEtcQty") { // 입수에 따라 주문수량 컬럼 readonly 컨트롤
            // console.log(item);
            if (item.poUnitQty === 1) {
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
          // 원가 및 수량 수정시 합계 계산하여 보여준다.
          if (col.binding === "costUprc" || col.binding === "inUnitQty" || col.binding === "inEtcQty") {
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
      s.allowMerging  = 2;
      s.itemFormatter = function (panel, r, c, cell) {
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
          if (col.isReadOnly || panel.grid.isReadOnly) {
            wijmo.addClass(cell, 'wj-custom-readonly');
          }
        }
      }
    };


    $scope.calcAmt = function (item) {
      <%-- 수량이 없는 경우 계산하지 않음. null 또는 undefined 가 나올수 있으므로 확실하게 확인하기 위해 nvl 처리로 null 로 바꿔서 비교 --%>
      if (nvl(item.inUnitQty, null) === null && (item.poUnitQty !== 1 && nvl(item.inEtcQty, null) === null)) return false;

      var costUprc     = parseFloat(item.costUprc);
      var poUnitQty    = parseInt(item.poUnitQty);
      var vat01        = parseInt(item.vatFg01);
      var vendrVatFg01 = parseInt(item.vendrVatFg01);

      var unitQty = (parseInt(nvl(item.prevOrderUnitQty, 0)) + parseInt(nvl(item.inUnitQty, 0))) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.prevOrderEtcQty, 0)) + parseInt(nvl(item.inEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * costUprc / poUnitQty);
      var inAmt   = tempAmt - Math.round(tempAmt * vat01 * vendrVatFg01 / 11);
      var inVat   = Math.round(tempAmt * vat01 / (10 + vendrVatFg01));
      var inTot   = parseInt(inAmt + inVat);

      item.inTotQty = totQty;   // 총주문수량
      item.inAmt    = inAmt; // 금액
      item.inVat    = inVat; // VAT
      item.inTot    = inTot; // 합계
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("vendrInstockProdRegCtrl", function (event, data) {

      if (!$.isEmptyObject(data)) {
        // 그리드 초기화
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;

        $scope.slipNo  = data.slipNo;
        $scope.slipFg  = data.slipFg;
        $scope.vendrCd = data.vendrCd;

        // 값 초기화
        $scope.prodClassCdNm = messages["cmm.all"];
        $scope.prodClassCd   = '';
        $scope.storeSplyChk  = false;

        $scope.wjVendrInstockProdRegLayer.show(true);
      }
      // 페이징처리에서 broadcast 호출시
      else {
        $scope.searchVendrInstockRegList();
      }
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 조회버튼 클릭으로 조회시
    $scope.fnSearch = function () {
      $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
      $scope.searchVendrInstockRegList();
    };


    // 상품 리스트 조회
    $scope.searchVendrInstockRegList = function () {
      // 파라미터
      var params       = {};
      params.slipNo    = $scope.slipNo;
      params.slipFg    = $scope.slipFg;
      params.vendrCd   = $scope.vendrCd;
      params.listScale = 50;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/vendr/vendrInstock/vendrInstockProdReg/list.sb", params);
    };


    // 상품 저장
    $scope.save = function () {
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 이전 주문수량이 없으면서 주문수량 0인 경우 저장하지 않는다.
        if (item.prevInTotQty === null && item.inTotQty === 0) {
          continue;
        }
        if (item.inEtcQty !== null && (parseInt(item.inEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["vendrInstock.reg.not.inEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.inTot !== null && (parseInt(item.inTot) > 9999999999)) {
          $scope._popMsg(messages["vendrInstock.reg.not.overInTot"]); // 주문금액이 너무 큽니다.
          return false;
        }

        item.status      = "U";
        item.slipNo      = $scope.slipNo;
        item.slipFg      = $scope.slipFg;
        item.storageCd   = "001";
        item.hqBrandCd   = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.storeSplyFg = ($scope.storeSplyChk === true ? 'Y' : 'N');

        params.push(item);
      }

      $scope._save("/iostock/vendr/vendrInstock/vendrInstockProdReg/save.sb", params, function () {
        $scope.saveRegistCallback()
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function () {
      $scope.searchVendrInstockRegList();

      var vendrInstockProdScope = agrid.getScope('vendrInstockProdCtrl');
      vendrInstockProdScope.procFgCheck();
    };


    $scope.setLastCostToCostUprc = function () {
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
      // 데이터 처리중 팝업 띄우기위해 $timeout 사용.
      $timeout(function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.lastCostUprc !== null) {
            $scope.flex.collectionView.editItem(item);

            if (nvl(item.lastCostUprc, 0) > 0) {
              item.costUprc = parseInt(item.lastCostUprc);
            }
            $scope.calcAmt(item);
            $scope.flex.collectionView.commitEdit();
          }
        }
        $scope.$broadcast('loadingPopupInactive');
      }, 100);
    };


    // 상품분류정보 팝업
    $scope.popUpProdClass = function () {
      var popUp = $scope.prodClassPopUpLayer;
      popUp.show(true, function (s) {
        // 선택 버튼 눌렀을때만
        if (s.dialogResult === "wj-hide-apply") {
          var scope          = agrid.getScope('prodClassPopUpCtrl');
          var prodClassCd    = scope.getSelectedClass();
          var params         = {};
          params.prodClassCd = prodClassCd;
          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function (response) {
              $scope.prodClassCd   = prodClassCd;
              $scope.prodClassCdNm = response.data.data;
            }
          );
        }
      });
    };


    // 엑셀 다운로드
    $scope.excelDownload = function () {
      if($scope.flex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
          includeColumnHeaders: true,
          includeCellStyles   : false,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, 'excel.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    };


    <%-- 엑셀업로드 관련 공통 함수 --%>
    $scope.excelTextUpload = function (prcsFg) {
      var excelUploadScope = agrid.getScope('excelUploadCtrl');
      <%-- 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. --%>
      var uploadFg = 'vendr';

      // 엑셀 양식다운로드
      if (prcsFg === 'excelFormDown') {
        excelUploadScope.excelFormDownload(uploadFg);
      }
      else{
        var msg = messages["excelUpload.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
        s_alert.popConf(msg, function () {
          excelUploadScope.uploadFg   = uploadFg;
          <%-- 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. --%>
          excelUploadScope.parentCtrl = 'vendrInstockProdRegCtrl';
          // 엑셀 업로드
          if (prcsFg === 'excelUp') {
            $("#excelUpFile").val('');
            $("#excelUpFile").trigger('click');
          }
          // 텍스트 업로드
          else if (prcsFg === 'textUp') {
            $("#textUpFile").val('');
            $("#textUpFile").trigger('click');
          }
        });
      }
    };


    <%-- 업로드 완료 후 callback 함수. 업로드 이후 로직 작성. --%>
    $scope.uploadCallBack = function () {
      var params      = {};
      params.slipNo   = $scope.slipNo;
      params.slipFg   = $scope.slipFg;
      params.vendrCd  = $scope.vendrCd;
      params.addQtyFg = $scope.addQtyFg;

      var excelUploadScope = agrid.getScope('excelUploadCtrl');

      $http({
        method : 'POST', //방식
        url    : '/iostock/vendr/vendrInstock/vendrInstockProdReg/excelUpload.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          // 엑셀 에러내역 팝업 호출
          $scope.excelUploadErrInfo();

          // 등록 그리드, 부모 그리드 조회
          $scope.saveRegistCallback();
        }
      }, function errorCallback(response) {
        $scope._popMsg(response.data.message);
        return false;
      }).then(function () {
        excelUploadScope.excelUploadingPopup(false); // 업로딩 팝업 닫기
      });
    };


    // 에러내역 팝업 호출
    $scope.excelUploadErrInfo = function () {
      var params      = {};
      params.uploadFg = 'vendr';
      params.vendrCd  = $scope.vendrCd;
      $scope._broadcast('excelUploadErrInfoCtrl', params);
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

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>
