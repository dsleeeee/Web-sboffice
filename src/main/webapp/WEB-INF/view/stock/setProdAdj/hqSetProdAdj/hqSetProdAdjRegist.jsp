<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/setProdAdj/hqSetProdAdj/hqSetProdAdjRegist/"/>

<wj-popup id="wjHqSetProdAdjRegistLayer" control="wjHqSetProdAdjRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqSetProdAdjRegistLayer" class="wj-dialog wj-dialog-columns">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqSetProdAdj.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <div ng-controller="hqSetProdAdjRegistCtrl">
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
            <th><s:message code="hqSetProdAdj.reg.prodCd"/></th>
            <td>
              <input type="text" id="srchRegProdCd" name="srchRegProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="hqSetProdAdj.reg.prodNm"/></th>
            <td>
              <input type="text" id="srchRegProdNm" name="srchRegProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
            </td>
          </tr>
          </tbody>
        </table>

        <div class="mt10 oh">
          <%-- 조회 --%>
          <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchHqSetProdAdjRegistList();">
            <s:message code="cmm.search"/></button>
        </div>

        <ul class="txtSty3 mt10">
          <li class="red"><s:message code="hqSetProdAdj.reg.txt1"/></li>
          <li class="red"><s:message code="hqSetProdAdj.reg.txt2"/></li>
        </ul>

        <%-- 세트상품 --%>
        <div class="mt10">
          <div class="wj-TblWrapBr pd20">
            <div class="updownSet oh mb10">
              <span class="fl bk lh30"><s:message code='hqSetProdAdj.reg.setProdTitle'/></span>
              <div class="fr">
                <p class="s12 lh30 fl mr5"><s:message code='hqSetProdAdj.reg.setDate'/> : </p>
                <div class="sb-select mr5 fl">
                  <span class="txtIn"><input id="setDate" class="w120px"></span>
                </div>
                <%-- 구성등록 --%>
                <button type="button" class="btn_skyblue ml5 fl" id="btnCompstSave" ng-click="saveHqSetProdAdjRegist('1')">
                  <s:message code="hqSetProdAdj.reg.compstReg"/></button>
                <%-- 해체등록 --%>
                <button type="button" class="btn_skyblue ml5 fl" id="btnDsmntSave" ng-click="saveHqSetProdAdjRegist('2')">
                  <s:message code="hqSetProdAdj.reg.dsmntReg"/></button>
              </div>
            </div>

            <div class="w100 mt10">
              <%--위즈모 테이블--%>
              <div class="wj-gridWrap" style="height: 150px;">
                <wj-flex-grid
                  autoGenerateColumns="false"
                  selection-mode="Row"
                  items-source="data"
                  control="flex"
                  initialized="initGrid(s,e)"
                  is-read-only="false"
                  item-formatter="_itemFormatter">

                  <!-- define columns -->
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.prodNm"/>" binding="prodNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.setProdQty"/>" binding="setProdQty" width="70" align="right" is-read-only="false" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
                  <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.setProdAmt"/>" binding="setProdAmt" width="70" align="right" is-read-only="true"></wj-flex-grid-column>

                </wj-flex-grid>
              </div>
              <%--//위즈모 테이블--%>
            </div>
          </div>
        </div>
      </div>

      <%-- 세트구성상품  --%>
      <div class="mt20" ng-controller="hqSetProdAdjRegistCompstCtrl">
        <div class="wj-TblWrapBr pd20">
          <div class="updownSet oh mb10">
            <span class="fl bk lh30"><s:message code='hqSetProdAdj.reg.compstTitle'/></span>
          </div>

          <div class="w100 mt10">
            <%--위즈모 테이블--%>
            <div class="wj-gridWrap" style="height:150px;">
              <wj-flex-grid
                autoGenerateColumns="false"
                selection-mode="Row"
                items-source="data"
                control="flex"
                initialized="initGrid(s,e)"
                is-read-only="false"
                item-formatter="_itemFormatter">

                <!-- define columns -->
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.dispSeq"/>" binding="dispSeq" width="40" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.prodCd"/>" binding="unitProdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.prodNm"/>" binding="unitProdNm" width="*" align="left" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.unitProdQty"/>" binding="unitProdQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
                <wj-flex-grid-column header="<s:message code="hqSetProdAdj.reg.totCostUprc"/>" binding="totCostUprc" width="70" align="right" is-read-only="true" aggregate="Sum"></wj-flex-grid-column>

              </wj-flex-grid>

            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">
  /** 세트상품 그리드 controller */
  app.controller('hqSetProdAdjRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSetProdAdjRegistCtrl', $scope, $http, true));

    $scope.setDate = wcombo.genDate("#setDate");

    $scope._setComboData("regListScaleBox", gvListScaleBoxData);


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
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
            var params    = {};
            params.prodCd = selectedRow.prodCd;
            $scope._broadcast('hqSetProdAdjRegistCompstCtrl', params);
          }
        }
      });

      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          // 조정수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "setProdQty") {
            var item = s.rows[e.row].dataItem;
            $scope.calcAmt(item);
          }
        }

        s.collectionView.commitEdit();
      });

    };


    $scope.calcAmt = function (item) {
      var costUprc   = parseInt(item.costUprc);
      var setProdQty = parseInt(nvl(item.setProdQty, 0));
      var setProdAmt = parseInt(setProdQty) * parseInt(costUprc);

      item.setProdQty = setProdQty; // 등록수량
      item.setProdAmt = setProdAmt; // 원가금액
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqSetProdAdjRegistCtrl", function (event, data) {

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.wjHqSetProdAdjRegistLayer.show(true);
      $scope.searchHqSetProdAdjRegistList();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 세트상품 리스트 조회
    $scope.searchHqSetProdAdjRegistList = function () {
      // 파라미터
      var params = {};

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/stock/setProdAdj/hqSetProdAdj/hqSetProdAdjRegist/list.sb", params);
    };


    // 세트 구성/해체 저장
    $scope.saveHqSetProdAdjRegist = function (setMakeFg) {
      var params = [];

      // 수정된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.setProdQty !== null && parseInt(item.setProdQty) > 0) {
          item.status    = "U";
          item.setDate   = $scope.setDate;
          item.setMakeFg = setMakeFg;
          item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

          params.push(item);
        }
      }

      $scope._save("/stock/setProdAdj/hqSetProdAdj/hqSetProdAdjRegist/save.sb", params, function () {
        $scope.saveRegistCallback()
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function () {
      var hqSetProdAdjScope = agrid.getScope('hqSetProdAdjCtrl');
      hqSetProdAdjScope.searchHqSetProdAdjList();

      $scope.wjHqSetProdAdjRegistLayer.hide(true);
    };

  }]);


  /** 세트구성상품 그리드 controller */
  app.controller('hqSetProdAdjRegistCompstCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqSetProdAdjRegistCompstCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      // add the new GroupRow to the grid's 'columnFooters' panel
      s.columnFooters.rows.push(new wijmo.grid.GroupRow());
      // add a sigma to the header to show that this is a summary row
      s.bottomLeftCells.setCellData(0, 0, '합계');
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqSetProdAdjRegistCompstCtrl", function (event, data) {

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.prodCd = data.prodCd;
      $scope.searchHqSetProdAdjRegistCompstList();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 세트구성상품 리스트 조회
    $scope.searchHqSetProdAdjRegistCompstList = function () {
      // 파라미터
      var params    = {};
      params.prodCd = $scope.prodCd;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/stock/setProdAdj/hqSetProdAdj/hqSetProdAdjRegistCompst/list.sb", params);
    };

  }]);
</script>
