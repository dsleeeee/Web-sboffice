<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore/"/>

<wj-popup id="wjRtnDstbCloseStoreDtlLayer" control="wjRtnDstbCloseStoreDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="rtnDstbCloseStoreDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="rtnDstbCloseStoreDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <span id="spanDtlTitle"></span>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">

      <ul class="txtSty3">
        <li class="red"><s:message code="rtnDstbCloseStore.dtl.txt1"/></li>
        <li class="red"><s:message code="rtnDstbCloseStore.dtl.txt2"/></li>
        <li class="red"><s:message code="rtnDstbCloseStore.dtl.txt3"/></li>
        <li class="red"><s:message code="rtnDstbCloseStore.dtl.txt4"/></li>
      </ul>

      <div class="tr mt20">
        <%-- 저장 --%>
        <button type="button" id="btnDtlSave" class="btn_skyblue ml5" ng-click="save()" ng-if="btnDtlSave"><s:message code="cmm.save"/></button>
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
            <%--<wj-flex-grid-column header="<s:message code="cmm.chk"/>"                             binding="gChk"             width="40"  align="center" ></wj-flex-grid-column>--%>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.storeCd"/>" binding="storeCd" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.splyUprc"/>" binding="splyUprc" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrSplyUprc"/>" binding="mgrSplyUprc" width="70" align="right" is-read-only="false" max-length=10 data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.hqStock"/>" binding="hqUnitStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.hqStock"/>" binding="hqEtcStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.storeStock"/>" binding="storeUnitStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.storeStock"/>" binding="storeEtcStock" width="70" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrUnitQty"/>" binding="mgrUnitQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrEtcQty"/>" binding="mgrEtcQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrTotQty"/>" binding="mgrTotQty" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrAmt"/>" binding="mgrAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrVat"/>" binding="mgrVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.mgrTot"/>" binding="mgrTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.confirmYn"/>" binding="confirmYn" width="60" align="center" is-read-only="false" format="checkBoxText"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.vatFg"/>" binding="vatFg01" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtl.envst0011"/>" binding="envst0011" width="70" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 반품마감 상세 그리드 controller */
  app.controller('rtnDstbCloseStoreDtlCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnDstbCloseStoreDtlCtrl', $scope, $http, true));

    $scope.procFgMap = new wijmo.grid.DataMap([
      {id: "00", name: "<s:message code='rtnDstbCloseStore.dtl.procFgReg'/>"},
      {id: "10", name: "<s:message code='rtnDstbCloseStore.dtl.procFgMd'/>"},
      {id: "20", name: "<s:message code='rtnDstbCloseStore.dtl.procFgDstbClose'/>"},
      {id: "30", name: "<s:message code='rtnDstbCloseStore.dtl.procFgSlip'/>"}
    ], 'id', 'name');

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
          if (col.binding === "mgrEtcQty") { // 입수에 따라 반품수량 컬럼 readonly 컨트롤
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
          if (col.binding === "mgrSplyUprc" || col.binding === "mgrUnitQty" || col.binding === "mgrEtcQty") { // 반품수량 수정시
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
      var mgrSplyUprc = parseInt(item.mgrSplyUprc);
      var poUnitQty   = parseInt(item.poUnitQty);
      var vat01       = parseInt(item.vatFg01);
      var envst0011   = parseInt(item.envst0011);

      var unitQty = parseInt(nvl(item.mgrUnitQty, 0)) * parseInt(item.poUnitQty);
      var etcQty  = parseInt(nvl(item.mgrEtcQty, 0));
      var totQty  = parseInt(unitQty + etcQty);
      var tempAmt = Math.round(totQty * mgrSplyUprc / poUnitQty);
      var mgrAmt  = tempAmt - Math.round(tempAmt * vat01 * envst0011 / 11);
      var mgrVat  = Math.round(tempAmt * vat01 / (10 + envst0011));
      var mgrTot  = parseInt(mgrAmt + mgrVat);

      item.mgrTotQty = totQty; // 총반품수량
      item.mgrAmt    = mgrAmt; // 금액
      item.mgrVat    = mgrVat; // VAT
      item.mgrTot    = mgrTot; // 합계
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnDstbCloseStoreDtlCtrl", function (event, data) {
      $scope.reqDate = data.reqDate;
      $scope.storeCd = data.storeCd;
      $scope.storeNm = data.storeNm;
      $scope.slipFg  = data.slipFg;
      $scope.procFg  = data.procFg;

      $scope.wjRtnDstbCloseStoreDtlLayer.show(true);
      $("#spanDtlTitle").html('['+messages["rtnDstbCloseStore.dtl.orderReturn"]+'] ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);

      if (parseInt($scope.procFg) < 20) {
        $scope.btnDtlSave = true;
      }
      else {
        $scope.btnDtlSave = false;
      }
      $scope.searchRtnDstbCloseStoreDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 반품마감 상세내역 리스트 조회
    $scope.searchRtnDstbCloseStoreDtlList = function () {
      // 파라미터
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.storeCd = $scope.storeCd;
      params.slipFg  = $scope.slipFg;
      params.procFg  = $scope.procFg;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreDtl/list.sb", params, function () {
      });
    };

    // 저장
    $scope.save = function () {
      var params = [];

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.mgrUnitQty === null && item.mgrEtcQty === null) {
          $scope._popMsg(messages["rtnDstbCloseStore.dtl.require.mgrQty"]); // 반품수량을 입력해주세요.
          return false;
        }
        if (item.mgrEtcQty !== null && (parseInt(item.mgrEtcQty) >= parseInt(item.poUnitQty))) {
          $scope._popMsg(messages["rtnDstbCloseStore.dtl.not.mgrEtcQty"]); // 낱개수량은 입수량보다 작아야 합니다.
          return false;
        }
        if (item.mgrTot !== null && (parseInt(item.mgrTot) > 9999999999)) {
          $scope._popMsg(messages["rtnDstbCloseStore.dtl.not.overMgrTot"]); // 반품금액이 너무 큽니다.
          return false;
        }

        item.status  = "U";
        item.reqDate = $scope.reqDate;
        item.slipFg  = $scope.slipFg;
        params.push(item);
      }

      $scope._save("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreDtl/save.sb", params, function () {
        $scope.saveRtnDstbCloseStoreDtlCallback()
      });
    };

    // 저장 후 콜백 함수
    $scope.saveRtnDstbCloseStoreDtlCallback = function () {
      var rtnDstbCloseStoreScope = agrid.getScope('rtnDstbCloseStoreCtrl');
      rtnDstbCloseStoreScope.searchRtnDstbCloseStoreList();

      $scope.wjRtnDstbCloseStoreDtlLayer.hide(true);
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
