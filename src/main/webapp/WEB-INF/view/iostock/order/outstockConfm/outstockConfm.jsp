<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/outstockConfm/outstockConfm/"/>

<div class="subCon" ng-controller="outstockConfmCtrl">
  <div class="searchBar flddUnfld">
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
      <%-- 출고일자 --%>
      <th><s:message code="outstockConfm.outDate"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 진행 --%>
      <th><s:message code="outstockConfm.procFg"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchProcFg"
            ng-model="procFg"
            items-source="_getComboData('srchProcFg')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
      <%-- 종류 --%>
      <th><s:message code="outstockConfm.slipKind"/></th>
      <td>
        <span class="txtIn w150px sb-select fl mr5">
          <wj-combo-box
            id="srchSlipKind"
            ng-model="slipKind"
            items-source="_getComboData('srchSlipKind')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
        </span>
      </td>
    </tr>
    <tr>
      <%-- 기사 --%>
      <th><s:message code="outstockConfm.dlvrNm"/></th>
        <td>
          <span class="txtIn w150px sb-select fl mr5">
            <wj-combo-box
              id="srchDlvrCd"
              ng-model="dlvrCd"
              items-source="_getComboData('srchDlvrCd')"
              display-member-path="name"
              selected-value-path="value"
              is-editable="false"
              initialized="_initComboBox(s)">
            </wj-combo-box>
          </span>
        </td>
      <%--TODO 거래처 로그인시 처리로직 필요 --%>
      <%-- 거래처 --%>
      <th><s:message code="outstockConfm.vendrNm"/></th>
      <td>
        <%-- 거래처선택 모듈 싱글 선택 사용시 include
             param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
                          displayNm - 로딩시 input 창에 보여질 명칭(변수 없을 경우 기본값 선택으로 표시)
                          modiFg - 수정여부(변수 없을 경우 기본값으로 수정가능)
                          closeFunc - 팝업 닫기시 호출할 함수
        --%>
        <jsp:include page="/WEB-INF/view/iostock/vendr/vendrOrder/selectVendrS.jsp" flush="true">
          <jsp:param name="targetId" value="outstockConfmSelectVendr"/>
          <jsp:param name="displayNm" value="전체"/>
          <jsp:param name="displayWidth" value="170px"/>
        </jsp:include>
        <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
      </td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="outstockConfm.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="outstockConfm.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('outstockConfmCtrl')">
      <s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10 fr">
    <p class="s14 bk fl mr10 lh30"><s:message code="outstockConfm.reqNoConfirm"/> : </p>
    <p class="s14 bk fl mr10 lh30 red" id="reqNoConfirmCnt"></p>
    <p class="s14 bk fl mr10 lh30"><s:message code="outstockConfm.outstockNoCreate"/> : </p>
    <p class="s14 bk fl mr20 lh30 red" id="outstockNoCreateCnt"></p>
    <%-- 출고일자 --%>
    <p class="s14 bk fl mr5 lh30"><s:message code="outstockConfm.outDate"/></p>
    <div class="sb-select fl">
      <span class="txtIn"><input id="outDate" class="w120px"></span>
    </div>
    <%-- 출고자료생성 --%>
    <button type="button" id="btnConfirm" class="btn_skyblue ml5 fl" ng-click="saveOutstockConfirm()">
      <s:message code="outstockConfm.outstockConfirm"/></button>
  </div>
  <div style="clear: both;"></div>

  <div class="w100 mt10">
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
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center" is-read-only="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipNo"/>" binding="slipNo" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipFg"/>" binding="slipFg" width="100" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.vendrNm"/>" binding="vendrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.slipKind"/>" binding="slipKind" width="70" align="center" is-read-only="true" data-map="slipKindMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.dlvrNm"/>" binding="dlvrNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.reqDate"/>" binding="reqDate" width="90" align="center" is-read-only="true" format="date" visible="false"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outDate"/>" binding="outDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.inDate"/>" binding="inDate" width="90" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.orderTot"/>" binding="orderTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.mgrTot"/>" binding="mgrTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outTot"/>" binding="outTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.inTot"/>" binding="inTot" width="80" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.remark"/>" binding="remark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.hqRemark"/>" binding="hqRemark" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outDt"/>" binding="outDt" width="150" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="outstockConfm.outNm"/>" binding="outNm" width="70" align="center" is-read-only="true"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="outstockConfmCtrl"/>
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

  /** 출고확정 그리드 controller */
  app.controller('outstockConfmCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('outstockConfmCtrl', $scope, $http, true));
    $scope.slipFg = 1;

    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");
    var outDate       = wcombo.genDate("#outDate");

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      $("#reqNoConfirmCnt").html("0");
      $("#outstockNoCreateCnt").html("0");

      $scope.setCombo();

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("outstockConfmCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "slipNo") { // 전표번호
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          } else if (col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
            var item = s.rows[e.row].dataItem;
            if (item.procFg !== "10") {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              s.rows[e.row].isReadOnly = true;
            }
          }

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });

      // 그리드 클릭 이벤트
      s.addEventListener(s.hostElement, 'mousedown', function (e) {
        var ht = s.hitTest(e);
        if (ht.cellType === wijmo.grid.CellType.Cell) {
          var col         = ht.panel.columns[ht.col];
          var selectedRow = s.rows[ht.row].dataItem;
          if (col.binding === "slipNo") { // 전표번호 클릭
            var params    = {};
            params.slipNo = selectedRow.slipNo;
            $scope._broadcast('outstockConfmDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };


    // combo 세팅
    $scope.setCombo = function () {
      // 진행구분
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "086";
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
      $scope._queryCombo("combo,map", "srchProcFg", "procFgMap", null, comboParams, "A", function () {
        $scope.procFg = "10"; // 진행구분 기본값 세팅
      }); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 전표종류
      comboParams             = {}; // 여러번 조회시 초기화를 해줘야함...
      comboParams.nmcodeGrpCd = "087";
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
      $scope._queryCombo("combo,map", "srchSlipKind", "slipKindMap", null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 배송기사
      comboParams             = {}; // 여러번 조회시 초기화를 해줘야함...
      var url = '/iostock/order/outstockConfm/outstockConfm/getDlvrCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option, callback)
      $scope._queryCombo("combo", "srchDlvrCd", null, url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("outstockConfmCtrl", function (event, data) {
      $scope.getReqNoConfirmCnt();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 매장요청 미확정건, 출고자료 미생성건 조회
    $scope.getReqNoConfirmCnt = function () {
      var params       = {};
      params.slipFg    = $scope.slipFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/iostock/order/outstockConfm/outstockConfm/reqNoConfirmCnt.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          if (!$.isEmptyObject(response.data.data)) {
            $("#reqNoConfirmCnt").html(response.data.data.reqNoConfirmCnt); //매장요청 미확정건
            $("#outstockNoCreateCnt").html(response.data.data.outstockNoCreateCnt); //출고자료 미생성건
            $scope.searchOutstockConfmList();
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

    // 출고확정 리스트 조회
    $scope.searchOutstockConfmList = function () {
      // 파라미터
      var params       = {};
      params.slipFg    = $scope.slipFg;
      // params.procFg    = "10";
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');
      params.vendrCd   = $("#outstockConfmSelectVendrCd").val();

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/order/outstockConfm/outstockConfm/list.sb", params);
    };

    $scope.saveOutstockConfirm = function () {
      var params = [];

      if ($scope.flex.collectionView.itemsEdited.length <= 0) {
        $scope._popMsg(messages["cmm.not.modify"]);
        return false;
      }

      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        if (item.gChk === true) {
          item.status    = "U";
          item.outDate   = wijmo.Globalize.format(outDate.value, 'yyyyMMdd');
          item.empNo     = "0000";
          item.storageCd = "001";
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
          params.push(item);
        }
      }

      $scope._save("/iostock/order/outstockConfm/outstockConfm/saveOutstockConfirm.sb", params, function () {
        $scope.getReqNoConfirmCnt()
      });
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
        } else if (response.data.status === "FAIL") {
          $scope._popMsg("Ajax Fail By HTTP Request");
        } else if (response.data.status === "SESSION_EXFIRE") {
          $scope._popMsg(response.data.message, function () {
            location.href = response.data.url;
          });
        } else if (response.data.status === "SERVER_ERROR") {
          $scope._popMsg(response.data.message);
        } else {
          var msg = response.data.status + " : " + response.data.message;
          $scope._popMsg(msg);
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
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


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.outstockConfmSelectVendrShow = function () {
      $scope._broadcast('outstockConfmSelectVendrCtrl');
    };

  }]);
</script>

<%-- 출고확정 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/order/outstockConfm/outstockConfmDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
