<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/order/dstbCloseProd/dstbCloseProdAddProd/"/>

<wj-popup id="wjDstbCloseProdAddProdLayer" control="wjDstbCloseProdAddProdLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="dstbCloseProdAddProdLayer" class="wj-dialog wj-dialog-columns" ng-controller="dstbCloseProdAddProdCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="dstbCloseProd.add.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p class="s14 bk mb5 fl"><s:message code="dstbCloseProd.add.addProdSubTitle"/></p>
      <p id="addProdSubTitle" class="s14 bk ml5 mb5 fl"></p>
      <p id="orderFgSubTitle" class="s14 bk ml5 mb5 fl"></p>
      <table class="tblType01">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 매장코드 --%>
          <th><s:message code="dstbCloseProd.add.store"/></th>
          <td>
            <%-- 매장선택 모듈 멀티 선택 사용시 include --%>
            <jsp:include page="/WEB-INF/view/iostock/cmm/selectShopM.jsp" flush="true">
              <jsp:param name="targetId" value="dstbCloseProdAddProdSelectStore"/>
            </jsp:include>
            <%--// 매장선택 모듈 멀티 선택 사용시 include --%>
          </td>
          <td colspan="2"><p class="s14">등록하실 매장을 선택한 후 상품을 조회하십시오.</p></td>
        </tr>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="dstbCloseProd.add.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="dstbCloseProd.add.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <%-- 바코드 --%>
          <th><s:message code="dstbCloseProd.add.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <%-- 분류 --%>
          <th><s:message code="dstbCloseProd.add.prodClassNm"/></th>
          <td>
            <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                   placeholder="<s:message code="cmm.all" />" readonly/>
            <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="_pageView('dstbCloseProdAddProdCtrl',1);">
          <s:message code="cmm.search"/></button>
      </div>

      <div class="mt40 oh sb-select dkbr">
        <%-- 페이지 스케일  --%>
        <wj-combo-box
          class="w100px fl"
          id="listScaleBox"
          ng-model="listScale"
          items-source="_getComboData('listScaleBox')"
          display-member-path="name"
          selected-value-path="value"
          is-editable="false"
          initialized="_initComboBox(s)">
        </wj-combo-box>
        <%--// 페이지 스케일  --%>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5 fr" id="btnSave" ng-click="saveDstbCloseProdAddProd()">
          <s:message code="cmm.save"/></button>
      </div>

      <%--<div class="wj-TblWrap ml20 mr20 pdb20">--%>
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 500px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodClassNm"/>" binding="prodClassNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodCd"/>" binding="prodCd" width="120" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitFg"/>" binding="poUnitFg" width="70" align="center" is-read-only="true" data-map="poUnitFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="dstbCloseProd.add.splyUprc"/>" binding="splyUprc" width="100" align="right" is-read-only="true" data-type="Number" format="n0"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="dstbCloseProdAddProdCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 분배마감 추가등록 상품 그리드 controller */
  app.controller('dstbCloseProdAddProdCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('dstbCloseProdAddProdCtrl', $scope, $http, true));

    //페이지 스케일 콤보박스 데이터 Set
    $scope._setComboData("listScaleBox", gvListScaleBoxData);

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "097";
      var url = '/iostock/cmm/iostockCmm/getOrgnCombo.sb';
      // 파라미터 (comboFg, comboId, gridMapId, url, params, option)
      $scope._queryCombo("map", null, 'poUnitFgMap', url, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          if (col.binding === "prodCd") { // 상품코드
            wijmo.addClass(e.cell, 'wijLink');
            wijmo.addClass(e.cell, 'wj-custom-readonly');
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
          if (col.binding === "prodCd") { // 상품코드 클릭
            var params      = {};
            params.reqDate  = $scope.reqDate;
            params.prodCd   = selectedRow.prodCd;
            params.prodNm   = selectedRow.prodNm;
            params.slipFg   = $scope.slipFg;
            params.storeCds = $("#dstbCloseProdAddProdSelectStoreCd").val();
            $scope._broadcast('dstbCloseProdAddRegistCtrl', params);
          }
        }
      });
    };

    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("dstbCloseProdAddProdCtrl", function (event, data) {

      if (!$.isEmptyObject(data)) {
        // 그리드 초기화
        var cv          = new wijmo.collections.CollectionView([]);
        cv.trackChanges = true;
        $scope.data     = cv;

        $scope.reqDate = data.reqDate;
        $scope.slipFg  = data.slipFg;

        // 값 초기화
        $scope.prodClassCdNm = messages["cmm.all"];
        $scope.prodClassCd   = '';

        $scope.wjDstbCloseProdAddProdLayer.show(true);
        $("#addProdSubTitle").html(' ('+messages["dstbCloseProd.add.reqDate"]+' : ' + getFormatDate($scope.reqDate, '-') + ')');
      }
      else { // 페이징처리에서 broadcast 호출시
        $scope.searchDstbCloseProdAddProdList();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });

    // 분배가능상품 리스트 조회
    $scope.searchDstbCloseProdAddProdList = function () {
      // 파라미터
      var params     = {};
      params.reqDate = $scope.reqDate;
      params.slipFg  = $scope.slipFg;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/order/dstbCloseProd/dstbCloseProdAddProd/list.sb", params);
    };

    // 매장선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.dstbCloseProdAddProdSelectStoreShow = function () {
      $scope._broadcast('dstbCloseProdAddProdSelectStoreCtrl');
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
