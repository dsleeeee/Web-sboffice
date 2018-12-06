<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore/"/>

<div class="subCon" ng-controller="rtnDstbCloseStoreCtrl">
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
      <%-- 조회일자 --%>
      <th><s:message code="cmm.search.date"/></th>
      <td colspan="3">
        <div class="sb-select">
          <span class="txtIn w150px">
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
          <span class="txtIn"><input id="srchStartDate" class="w150px"></span>
          <span class="rg">~</span>
          <span class="txtIn"><input id="srchEndDate" class="w150px"></span>
        </div>
      </td>
    </tr>
    <tr>
      <%-- 매장코드 --%>
      <th><s:message code="rtnDstbCloseStore.storeCd"/></th>
      <td>
        <input type="text" id="srchStoreCd" name="srchStoreCd" ng-model="storeCd" class="sb-input w100" maxlength="7"/>
      </td>
      <%-- 매장명 --%>
      <th><s:message code="rtnDstbCloseStore.storeNm"/></th>
      <td>
        <input type="text" id="srchStoreNm" name="srchStoreNm" ng-model="storeNm" class="sb-input w100" maxlength="16"/>
      </td>
    </tr>
    <tr>
      <%-- 진행구분 --%>
      <th><s:message code="rtnDstbCloseStore.procFg"/></th>
      <td colspan="3">
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
    </tr>
    <tr>
    <%-- 출고요청일자 --%>
    <th><s:message code="rtnDstbCloseStore.reqDate"/></th>
    <td colspan="3">
      <div class="sb-select fl mr10">
        <span class="txtIn"><input id="reqDate" class="w150px"></span>
      </div>
      <a href="#" class="btn_grayS" ng-click="add()"><s:message code="rtnDstbCloseStore.addRegist"/></a>
    </td>
    </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch" ng-click="_broadcast('rtnDstbCloseStoreCtrl')">
      <s:message code="cmm.search"/></button>
  </div>

  <div class="tr mt10">
    <%-- 확정 --%>
    <button type="button" id="btnConfirm" class="btn_skyblue ml5" ng-click="saveConfirm()"><s:message code="rtnDstbCloseStore.confirm"/></button>
  </div>

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
        item-formatter="itemFormatter">

        <!-- define columns -->
        <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="40" align="center"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.reqDate"/>" binding="reqDate" width="100" align="center" is-read-only="true" format="date"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.storeCd"/>" binding="storeCd" width="70" align="center" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.storeNm"/>" binding="storeNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.procFg"/>" binding="procFg" width="70" align="center" is-read-only="true" data-map="procFgMap"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.orderAmt"/>" binding="orderAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.orderVat"/>" binding="orderVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.orderTot"/>" binding="orderTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.mgrAmt"/>" binding="mgrAmt" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.mgrVat"/>" binding="mgrVat" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.mgrTot"/>" binding="mgrTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtlCntOrder"/>" binding="dtlCntOrder" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtlCntAdd"/>" binding="dtlCntAdd" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.dtlCntTot"/>" binding="dtlCntTot" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.modDt"/>" binding="modDt" width="130" align="center" is-read-only="true" format="dateTime"></wj-flex-grid-column>
        <wj-flex-grid-column header="<s:message code="rtnDstbCloseStore.slipFg"/>" binding="slipFg" width="70" align="left" is-read-only="true" visible="false"></wj-flex-grid-column>

      </wj-flex-grid>
      <%-- ColumnPicker 사용시 include --%>
      <jsp:include page="/WEB-INF/view/layout/columnPicker.jsp" flush="true">
        <jsp:param name="pickerTarget" value="rtnDstbCloseStoreCtrl"/>
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

  /** 반품마감 그리드 controller */
  app.controller('rtnDstbCloseStoreCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('rtnDstbCloseStoreCtrl', $scope, $http, true));

    $scope.slipFg     = -1;
    var srchStartDate = wcombo.genDateVal("#srchStartDate", "${sessionScope.sessionInfo.startDate}");
    var srchEndDate   = wcombo.genDateVal("#srchEndDate", "${sessionScope.sessionInfo.endDate}");
    var reqDate       = wcombo.genDate("#reqDate");

    $scope._setComboData("srchDateFg", [
      {"name": messages["rtnDstbCloseStore.reqDate"], "value": "req"},
      {"name": messages["rtnDstbCloseStore.regDate"], "value": "reg"},
      {"name": messages["rtnDstbCloseStore.modDate"], "value": "mod"}
    ]);

    <%--$scope._setComboData("srchProcFg", [--%>
      <%--{"name": "<s:message code='rtnDstbCloseStore.procFgAll'/>", "value": ""},--%>
      <%--{"name": "<s:message code='rtnDstbCloseStore.procFgReg'/>", "value": "00"},--%>
      <%--{"name": "<s:message code='rtnDstbCloseStore.procFgMd'/>", "value": "10"},--%>
      <%--{"name": "<s:message code='rtnDstbCloseStore.procFgDstbClose'/>", "value": "20"},--%>
      <%--{"name": "<s:message code='rtnDstbCloseStore.procFgSlip'/>", "value": "30"}--%>
    <%--]);--%>
    <%--$scope.procFg = "10"; // 진행구분 기본값 세팅--%>

    <%--$scope.procFgMap = new wijmo.grid.DataMap([--%>
      <%--{id: "00", name: "<s:message code='rtnDstbCloseStore.procFgReg'/>"},--%>
      <%--{id: "10", name: "<s:message code='rtnDstbCloseStore.procFgMd'/>"},--%>
      <%--{id: "20", name: "<s:message code='rtnDstbCloseStore.procFgDstbClose'/>"},--%>
      <%--{id: "30", name: "<s:message code='rtnDstbCloseStore.procFgSlip'/>"}--%>
    <%--], 'id', 'name');--%>

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "084";
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
      $scope._queryCombo("combo,map", "srchProcFg", "procFgMap", null, comboParams, "A", function () {
        $scope.procFg = "10"; // 진행구분 기본값 세팅
      }); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // picker 사용시 호출 : 미사용시 호출안함
      $scope._makePickColumns("rtnDstbCloseStoreCtrl");

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "storeCd") { // 매장코드
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
          }
          else if (col.binding === "gChk") { // 진행구분 따라 체크박스 컬럼 readonly 컨트롤
            var item = s.rows[e.row].dataItem;
            if (item.procFg !== "10") {
              wijmo.addClass(e.cell, 'wj-custom-readonly');
              s.rows[e.row].isReadOnly = true;
            }
          }

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          }
          else if (col.format === "dateTime") {
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
          if (col.binding === "storeCd") { // 매장코드 클릭
            var params     = {};
            params.reqDate = selectedRow.reqDate;
            params.storeCd = selectedRow.storeCd;
            params.storeNm = selectedRow.storeNm;
            params.slipFg  = selectedRow.slipFg;
            params.procFg  = selectedRow.procFg;
            $scope._broadcast('rtnDstbCloseStoreDtlCtrl', params);
          }
        }
      });

      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');

      // 헤더머지
      s.allowMerging = 2;
      s.columnHeaders.rows.push(new wijmo.grid.Row());
      s.columnHeaders.rows[0].dataItem = {
        gChk       : messages["cmm.chk"],
        reqDate    : messages["rtnDstbCloseStore.reqDate"],
        storeCd    : messages["rtnDstbCloseStore.storeCd"],
        storeNm    : messages["rtnDstbCloseStore.storeNm"],
        procFg     : messages["rtnDstbCloseStore.procFg"],
        orderAmt   : messages["rtnDstbCloseStore.order"],
        orderVat   : messages["rtnDstbCloseStore.order"],
        orderTot   : messages["rtnDstbCloseStore.order"],
        mgrAmt     : messages["rtnDstbCloseStore.mgr"],
        mgrVat     : messages["rtnDstbCloseStore.mgr"],
        mgrTot     : messages["rtnDstbCloseStore.mgr"],
        dtlCntOrder: messages["rtnDstbCloseStore.dtlCnt"],
        dtlCntAdd  : messages["rtnDstbCloseStore.dtlCnt"],
        dtlCntTot  : messages["rtnDstbCloseStore.dtlCnt"],
        modDt      : messages["rtnDstbCloseStore.modDt"],
        slipFg     : messages["rtnDstbCloseStore.slipFg"],
      };
    };


    <!-- 체크박스가 있는 헤더머지 때문에 현재 페이지에 재정의 한 itemFormatter 를 사용 -->
    $scope.itemFormatter = function (panel, r, c, cell) {
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

        if ((panel.grid.columnHeaders.rows.length - 1) === r) {
          // 헤더의 전체선택 클릭 로직
          var flex   = panel.grid;
          var column = flex.columns[c];
          // check that this is a boolean column
          if (column.binding === 'gChk' || column.format === 'checkBox' || column.format === 'checkBoxText') {
            // prevent sorting on click
            column.allowSorting = false;
            // count true values to initialize checkbox
            var cnt             = 0;
            for (var i = 0; i < flex.rows.length; i++) {
              if (flex.getCellData(i, c) === true) {
                cnt++;
              }
            }
            // create and initialize checkbox
            if (column.format === 'checkBoxText') {
              cell.innerHTML = '<input id=\"' + column.binding + '\" type=\"checkbox\" class=\"wj-cell-check\" />'
                + '<label for=\"' + column.binding + '\" class=\"wj-header-label\">' + cell.innerHTML + '</label>';
            } else {
              cell.innerHTML = '<input type=\"checkbox\" class=\"wj-cell-check\" />';
            }
            var cb           = cell.firstChild;
            cb.checked       = cnt > 0;
            cb.indeterminate = cnt > 0 && cnt < flex.rows.length;
            // apply checkbox value to cells
            cb.addEventListener('click', function (e) {
              flex.beginUpdate();
              for (var i = 0; i < flex.rows.length; i++) {
                var cell = flex.cells.getCellElement(i, c);

                // 활성화 및 readOnly 아닌 경우에만 체크되도록
                if (!cell.children[0].disabled) {
                  flex.setCellData(i, c, cb.checked);
                }
              }
              flex.endUpdate();
            });
          }
        }
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
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("rtnDstbCloseStoreCtrl", function (event, data) {
      $scope.searchRtnDstbCloseStoreList();
      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 주문 리스트 조회
    $scope.searchRtnDstbCloseStoreList = function () {
      // 파라미터
      var params       = {};
      params.dateFg    = $scope.dateFg;
      params.slipFg    = $scope.slipFg;
      params.startDate = wijmo.Globalize.format(srchStartDate.value, 'yyyyMMdd');
      params.endDate   = wijmo.Globalize.format(srchEndDate.value, 'yyyyMMdd');

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore/list.sb", params);
    };

    $scope.saveConfirm = function () {
      // 선택하신 자료를 반품마감으로 확정합니다. 확정하시겠습니까?
      var msg = messages["rtnDstbCloseStore.confirmText"];
      s_alert.popConf(msg, function () {
        var params = [];
        for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
          var item = $scope.flex.collectionView.itemsEdited[i];

          if (item.gChk === true) {
            item.status    = "U";
            item.empNo     = "0000";
            item.storageCd = "001";
            item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
            params.push(item);
          }
        }
        $scope._save("/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStore/saveConfirm.sb", params, function () {
          $scope.searchRtnDstbCloseStoreList()
        });
      });
    };

    $scope.add = function () {
      var params     = {};
      params.reqDate = wijmo.Globalize.format(reqDate.value, 'yyyyMMdd');
      params.slipFg  = $scope.slipFg;
      $scope._broadcast('rtnDstbCloseStoreAddCtrl', params);
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

            if (comboFg.indexOf("combo") >= 0 && nvl(comboId,'') !== '') {
              comboArray = [];
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
              $scope._setComboData(comboId, comboArray);
            }

            if (comboFg.indexOf("map") >= 0 && nvl(gridMapId,'') !== '') {
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
        if (typeof callback === 'function') {
          $timeout(function () {
            callback();
          }, 10);
        }
      });
    };

  }]);
</script>

<%-- 반품마감 상세 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreDtl.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 반품마감 추가등록 레이어 --%>
<c:import url="/WEB-INF/view/iostock/orderReturn/rtnDstbCloseStore/rtnDstbCloseStoreAdd.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
