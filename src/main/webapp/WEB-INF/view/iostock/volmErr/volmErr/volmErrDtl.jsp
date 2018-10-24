<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/volmErr/volmErr/volmErrDtl/"/>

<wj-popup id="wjVolmErrDtlLayer" control="wjVolmErrDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="volmErrDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="volmErrDtlCtrl">
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
          <th><s:message code="volmErr.dtl.remark"/></th>
          <td colspan="3">
            <input type="text" id="hdRemark" name="hdRemark" ng-model="hdRemark" class="sb-input w100"/>
          </td>
        </tr>
        </tbody>
      </table>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="volmErr.dtl.txt1"/></li>
      </ul>

      <table class="tblType01 mt10">
        <colgroup>
          <col class="w20"/>
          <col class="w30"/>
          <col class="w25"/>
          <col class="w25"/>
        </colgroup>
        <tbody>
        <tr>
          <th class="tc"><s:message code="volmErr.dtl.tableTh1"/></th>
          <th class="tc"><s:message code="volmErr.dtl.tableTh2"/></th>
          <th class="tc"><s:message code="volmErr.dtl.tableTh3"/></th>
          <th class="tc"><s:message code="volmErr.dtl.tableTh4"/></th>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_1"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_1"/></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_2"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_2"/></td>
          <td><s:message code="volmErr.dtl.tableTd3_1"/></td>
          <td></td>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_3"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_2"/></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_4"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_2"/></td>
          <td><s:message code="volmErr.dtl.tableTd3_1"/></td>
          <td><s:message code="volmErr.dtl.tableTd4_1"/></td>
        </tr>
        <tr>
          <td><s:message code="volmErr.dtl.tableTd1_5"/></td>
          <td><s:message code="volmErr.dtl.tableTd2_2"/></td>
          <td></td>
          <td><s:message code="volmErr.dtl.tableTd4_1"/></td>
        </tr>
        </tbody>
      </table>


      <div class="tr mt20 fr">
        <div id="volmErrBtnLayer" style="display: none;">
          <span class="chk txtIn fl lh30" style="top: -2px;">
            <input type="checkbox" name="volmErrConfirmFg" id="volmErrConfirmFg" value="Y" ng-click="fnConfirmChk()"/>
            <label for="volmErrConfirmFg"><s:message code="volmErr.dtl.confirm"/></label>
          </span>
          <%-- 출고일자 --%>
          <div id="divDtlOutDate" class="sb-select ml10 fl" style="display: none;">
            <p class="s12 fl mr5 lh30"><s:message code="volmErr.dtl.outDate"/> : </p>
            <span class="txtIn"><input id="dtlOutDate" class="w120"></span>
          </div>
          <%-- 저장 --%>
          <button type="button" id="btnDtlSave" class="btn_skyblue ml5 fl" ng-click="save()">
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
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.seq"/>" binding="seq" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.outUnitQty"/>" binding="outUnitQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.outEtcQty"/>" binding="outEtcQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.inUnitQty"/>" binding="inUnitQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.inEtcQty"/>" binding="inEtcQty" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.errFg"/>" binding="errFg" width="100" align="center" is-read-only="false" data-map="errFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="volmErr.dtl.remark"/>" binding="remark" width="200" align="left" is-read-only="false" max-length=300></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>

      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 물량오류 상세 그리드 controller */
  app.controller('volmErrDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('volmErrDtlCtrl', $scope, $http, true));

    $scope.outDate = wcombo.genDateVal("#dtlOutDate", "${sessionScope.sessionInfo.startDt}");

    $scope.errFgMap = new wijmo.grid.DataMap([
      {id: "0", name: messages["volmErr.reg"]},
      {id: "1", name: messages["volmErr.confirm"]},
    ], 'id', 'name');

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("volmErrDtlCtrl", function (event, data) {
      $scope.slipNo   = data.slipNo;
      $scope.slipFg   = data.slipFg;
      $scope.procFg   = data.procFg;
      $scope.storeCd  = data.storeCd;
      $scope.storeNm  = data.storeNm;
      $scope.hdRemark = data.hdRemark;

      $scope.wjVolmErrDtlLayer.show(true);
      $("#spanDtlTitle").html(messages["volmErr.dtl.slipNo"] + ' : ' + $scope.slipNo + ', ' + messages["volmErr.dtl.store"] + ' : ' + '[' + $scope.storeCd + '] ' + $scope.storeNm);

      if ($scope.procFg === "0") {
        $("#volmErrBtnLayer").show();
      }
      else {
        $("#volmErrBtnLayer").hide();
      }

      $("#volmErrConfirmFg").prop("checked", false);
      $("#divDtlOutDate").hide(); //페이지 호출시 출고일자는 일단 무조건 hide 처리.

      // 물량오류 처리구분 콤보박스 조회 및 생성. slipFg 가 있어야 하므로 상세페이지를 호출할때 조회하도록 함.
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "089";
      comboParams.nmcodeItem1 = $scope.slipFg;
      $scope._queryCombo("map", "errFgMap", "/iostock/volmErr/volmErr/volmErr/getCombo.sb", comboParams);

      $scope.searchVolmErrDtlList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 물량오류 상세내역 리스트 조회
    $scope.searchVolmErrDtlList = function () {
      // 파라미터
      var params    = {};
      params.slipNo = $scope.slipNo;
      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/iostock/volmErr/volmErr/volmErrDtl/list.sb", params, function () {
      });
    };


    // 저장
    $scope.save = function () {
      if (!$("#volmErrConfirmFg").is(":checked")) {
        $scope._popMsg(messages["volmErr.dtl.require.confirmCheck"]); // 확정을 체크해 주세요.
        return false;
      }

      var params           = new Array();
      var newSlipNoFg      = "N";
      var hqNewAdjustFg    = "N";
      var storeNewAdjustFg = "N";

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.errFg === null || item.errFg === "") {
          $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
          return false;
        }

        if (newSlipNoFg === "N" && (item.errFg === "O2" || item.errFg === "O4" || item.errFg === "R2")) {
          newSlipNoFg = "Y";
        }
        if (hqNewAdjustFg === "N" && (item.errFg === "O4" || item.errFg === "O5" || item.errFg === "R4")) {
          hqNewAdjustFg = "Y";
        }
        if (storeNewAdjustFg === "N" && item.errFg === "R2") {
          storeNewAdjustFg = "Y";
        }

        item.status           = "U";
        item.slipNo           = $scope.slipNo;
        item.slipFg           = $scope.slipFg;
        item.storeCd          = $scope.storeCd;
        item.hdRemark         = $scope.hdRemark;
        item.outDate          = wijmo.Globalize.format($scope.outDate.value, 'yyyyMMdd');
        item.confirmFg        = ($("#volmErrConfirmFg").is(":checked") ? $("#volmErrConfirmFg").val() : "");
        item.procFg           = $scope.procFg;
        item.newSlipNoFg      = newSlipNoFg;
        item.hqNewAdjustFg    = hqNewAdjustFg;
        item.storeNewAdjustFg = storeNewAdjustFg;

        params.push(item);
      }

      $scope._save("/iostock/volmErr/volmErr/volmErrDtl/save.sb", params, function () {
        $scope.saveVolmErrDtlCallback()
      });
    };

    $scope.saveVolmErrDtlCallback = function () {
      $scope.wjVolmErrDtlLayer.hide(true);

      var volmErrScope = agrid.getScope('volmErrCtrl');
      volmErrScope.searchVolmErrList();

    };

    // 확정체크시 값 체크
    $scope.fnConfirmChk = function () {
      if ($("#volmErrConfirmFg").is(":checked")) {
        var showDate = "N";
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.errFg === null || item.errFg === "") {
            $scope._popMsg(messages["volmErr.dtl.require.selectErrFg"]); // 처리구분을 선택해 주세요.
            $("#volmErrConfirmFg").prop("checked", false);
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

    // DB 데이터를 조회해와서 그리드에서 사용할 Combo를 생성한다.
    // comboFg : map - 그리드에 사용할 Combo, combo - ComboBox 생성
    $scope._queryCombo = function (comboFg, id, url, params, option) {
      var comboUrl = "/iostock/volmErr/volmErr/volmErr/getCombo.sb";
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
        if (response.data.status === "OK") {
          // this callback will be called asynchronously
          // when the response is available
          if (!$.isEmptyObject(response.data.data.list)) {
            var list       = response.data.data.list;
            var comboArray = new Array();
            var comboData  = {};

            if (comboFg === "combo") {
              if (option === "A") {
                comboData.name  = messages["cmm.all"];
                comboData.value = "";
                comboArray.push(comboData);
              }
              else if (option === "S") {
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
              $scope._setComboData(id, comboArray);
            }
            else if (comboFg === "map") {
              for (var i = 0; i < list.length; i++) {
                comboData      = {};
                comboData.id   = list[i].nmcodeCd;
                comboData.name = list[i].nmcodeNm;
                comboArray.push(comboData);
              }
              $scope[id] = new wijmo.grid.DataMap(comboArray, 'id', 'name');
            }
          }
        }
        else if (response.data.status === "FAIL") {
          $scope._popMsg("Ajax Fail By HTTP Request");
        }
        else if (response.data.status === "SESSION_EXFIRE") {
          $scope._popMsg(response.data.message, function () {
            location.href = response.data.url;
          });
        }
        else if (response.data.status === "SERVER_ERROR") {
          $scope._popMsg(response.data.message);
        }
        else {
          var msg = response.data.status + " : " + response.data.message;
          $scope._popMsg(msg);
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        $scope._popMsg(messages["cmm.error"]);
        return false;
      }).then(function () {
      });
    };

  }]);
</script>
