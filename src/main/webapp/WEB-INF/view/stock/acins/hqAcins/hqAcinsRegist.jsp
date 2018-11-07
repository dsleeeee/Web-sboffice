<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/acins/hqAcins/hqAcinsRegist/"/>

<wj-popup id="wjHqAcinsRegistLayer" control="wjHqAcinsRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="hqAcinsRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="hqAcinsRegistCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="hqAcins.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p id="registSubTitle" class="s14 bk mb5 fl"></p>

      <table class="tblType01" style="position: relative;">
        <colgroup>
          <col class="w15"/>
          <col class="w35"/>
          <col class="w15"/>
          <col class="w35"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드 --%>
          <th><s:message code="hqAcins.reg.prodCd"/></th>
          <td>
            <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
          </td>
          <%-- 상품명 --%>
          <th><s:message code="hqAcins.reg.prodNm"/></th>
          <td>
            <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqAcins.reg.barcd"/></th>
          <td>
            <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
          </td>
          <th><s:message code="hqAcins.reg.prodClass"/></th>
          <td>
            <input type="text" id="srchProdClass" name="prodClass" ng-model="prodClass" class="sb-input w100" maxlength="40"/>
          </td>
        </tr>
        <tr>
          <th><s:message code="hqAcins.reg.acinsTitle"/><em class="imp">*</em></th>
          <td colspan="3">
            <input type="text" id="acinsTitle" name="acinsTitle" ng-model="acinsTitle" class="sb-input w100" maxlength="33"/>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a href="#" class="btn_grayS" ng-click=""><s:message code="hqAcins.reg.excelFormDownload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="hqAcins.reg.excelFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="hqAcins.reg.textFormUpload"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="cmm.excel.down"/></a>
            <a href="#" class="btn_grayS" ng-click=""><s:message code="hqAcins.reg.excelFormUploadErrorInfo"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="searchHqAcinsRegistList();">
          <s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="hqAcins.reg.txt1"/></li>
        <li class="red"><s:message code="hqAcins.reg.txt2"/></li>
        <li class="red"><s:message code="hqAcins.reg.txt3"/></li>
      </ul>

      <table class="tblType01 mt10 tc" style="position: relative;">
        <colgroup>
          <col class="w70"/>
          <col class="w30"/>
        </colgroup>
        <tbody>
        <tr>
          <%-- 상품코드/바코드 --%>
          <th class="tc">
            <s:message code="hqAcins.reg.prodCd"/>/<s:message code="hqAcins.reg.barcd"/></th>
          <%-- 추가수량 --%>
          <th class="tc"><s:message code="hqAcins.reg.addQty"/></th>
        </tr>
        <tr>
          <td>
            <input type="text" id="srchProdBarcdCd" name="srchProdBarcdCd" ng-model="prodBarcdCd" class="sb-input tc" maxlength="40" style="width: 250px;" ng-keydown="fnKeySearchProd($event)"/>
            <%-- 찾기 --%>
            <a href="#" class="btn_grayS" ng-click="prodFind()"><s:message code="hqAcins.reg.prodFind"/></a>
            <span class="chk txtIn lh30 ml5" style="top: -2px;">
              <input type="checkbox" name="autoAdd" id="autoAdd" ng-model="autoAdd"/>
              <label for="autoAdd"><s:message code="hqAcins.reg.autoAdd"/></label>
            </span>
          </td>
          <td>
            <input type="text" id="addQty" name="addQty" ng-model="addQty" class="sb-input tc" maxlength="10" style="width: 100px;" ng-keydown="fnKeyAddQty($event)"/>
            <%-- 추가 --%>
            <a href="#" class="btn_grayS" ng-click="addAcinsQty()"><s:message code="hqAcins.reg.add"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt20 tr">
        <%-- 현재고적용 --%>
        <button type="button" class="btn_skyblue ml5" id="btnCurrToAcins" ng-click="setCurrToAcins()">
          <s:message code="hqAcins.reg.CurrToAcins"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnRegSave" ng-click="saveHqAcinsRegist()">
          <s:message code="cmm.save"/></button>
      </div>

      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 500px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="false"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.cmptCurrQty"/>" binding="cmptCurrQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.acinsQty"/>" binding="acinsQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.adjQty"/>" binding="adjQty" width="70" align="right" is-read-only="true" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.acinsAmt"/>" binding="acinsAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="hqAcins.reg.acinsProdStatus"/>" binding="acinsProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="hqAcinsRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 실사관리 등록 그리드 controller */
  app.controller('hqAcinsRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('hqAcinsRegistCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          // 실사수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "acinsQty") {
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


    $scope.calcAmt = function (item) {
      var costUprc    = parseInt(item.costUprc);
      var acinsQty    = parseInt(nvl(item.acinsQty, 0));
      var cmptCurrQty = parseInt(nvl(item.cmptCurrQty, 0));
      var adjQty      = parseInt(acinsQty) - parseInt(cmptCurrQty);
      var acinsAmt    = parseInt(acinsQty) * parseInt(costUprc);
      var adjAmt      = parseInt(adjQty) * parseInt(costUprc);

      item.adjQty   = adjQty;   // 조정수량
      item.acinsAmt = acinsAmt; // 실사금액
      item.adjAmt   = adjAmt; // 조정금액
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("hqAcinsRegistCtrl", function (event, data) {

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      if (!$.isEmptyObject(data)) {
        $scope.acinsDate  = data.acinsDate;
        $scope.seqNo      = data.seqNo;
        $scope.callParent = data.callParent;

        // 변수값들 초기화
        $scope.addQty      = '';
        $scope.prodBarcdCd = '';
        $scope.autoAdd     = false;
        $scope.procFgCheck(); // 실사진행구분 체크
      }
      else { // 페이징처리에서 broadcast 호출시
        $scope.searchHqAcinsRegistList();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 실사진행구분 체크 및 실사제목 조회
    $scope.procFgCheck = function () {
      var params       = {};
      params.acinsDate = $scope.acinsDate;
      params.seqNo     = $scope.seqNo;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/stock/acins/hqAcins/hqAcinsRegist/procFgCheck.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response)) {
          // 진행구분이 실사등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            if (response.data.data.procFg != "" && response.data.data.procFg != "0") {
              $scope._popMsg(messages["hqAcins.reg.not.procEnd"]);
              return false;
            }
            $scope.acinsTitle = response.data.data.acinsTitle;
          }
        }
      }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        if (response.data.message) {
          $scope._popMsg(response.data.message);
        } else {
          $scope._popMsg(messages['cmm.error']);
        }
        return false;
      }).then(function () {
        // "complete" code here
        $scope.wjHqAcinsRegistLayer.show(true);
        $("#registSubTitle").html(messages["hqAcins.reg.acinsDate"] + ' : ' + getFormatDate($scope.acinsDate, '-'));
      });
    };


    // 실사상품 리스트 조회
    $scope.searchHqAcinsRegistList = function () {
      // 파라미터
      var params       = {};
      params.acinsDate = $scope.acinsDate;
      params.seqNo     = $scope.seqNo;
      params.prodCd    = $scope.prodCd;
      params.prodNm    = $scope.prodNm;
      params.barcdCd   = $scope.barcdCd;
      params.listScale = 500;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/stock/acins/hqAcins/hqAcinsRegist/list.sb", params);
    };


    // 실사 상품 저장
    $scope.saveHqAcinsRegist = function () {
      if (nvl($scope.acinsTitle, '') === '') {
        var msg = messages["hqAcins.reg.acinsTitle"] + messages["cmm.require.text"];
        $scope._popMsg(msg);
        return false;
      }
      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.gChk === true && item.acinsProdStatus === 'U') {
          item.status = "D";
        }
        else {
          item.status = "U";
        }
        item.acinsDate  = $scope.acinsDate;
        item.seqNo      = $scope.seqNo;
        item.acinsTitle = $scope.acinsTitle;
        item.storageCd  = "001";
        item.hqBrandCd  = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }

      $scope._save("/stock/acins/hqAcins/hqAcinsRegist/save.sb", params, function () {
        $scope.saveRegistCallback()
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function () {
      // $scope.searchHqAcinsRegistList();

      // 신규 요청등록인 경우
      if ($scope.callParent === "hqAcins") {
        var hqAcinsScope = agrid.getScope('hqAcinsCtrl');
        hqAcinsScope.searchHqAcinsList();
      }
      // 주문 상품상세내역 페이지에서 호출한 경우
      else if ($scope.callParent === "hqAcinsDtl") {
        var hqAcinsScope = agrid.getScope('hqAcinsCtrl');
        hqAcinsScope.searchHqAcinsList();

        var hqAcinsDtlScope = agrid.getScope('hqAcinsDtlCtrl');
        hqAcinsDtlScope.searchHqAcinsDtlList();
      }

      $scope.wjHqAcinsRegistLayer.hide(true);
    };


    // 현재고 수량적용.
    $scope.setCurrToAcins = function () {
      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]);
      // 데이터 처리중 팝업 띄우기위해 setTimeout 사용.
      setTimeout(function () {
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.cmptCurrQty !== null) {
            $scope.flex.collectionView.editItem(item);

            if (nvl(item.cmptCurrQty, 0) > 0) {
              item.acinsQty = parseInt(item.cmptCurrQty);
            }
            else {
              item.acinsQty = 0;
            }
            $scope.calcAmt(item);
            $scope.flex.collectionView.commitEdit();
          }
        }
        $scope.$broadcast('loadingPopupInactive');
      }, 100);
    };


    // 상품코드/바코드 input 박스에서 keyDown시
    $scope.fnKeySearchProd = function (event) {
      if (event.keyCode === 13) {
        var searchFg = true;

        // 조회된 상품중에 해당 상품코드/바코드가 있는지 검색
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
            searchFg = false;
          }
        }

        if (searchFg) {
          // 파라미터
          var params         = {};
          params.acinsDate   = $scope.acinsDate;
          params.seqNo       = $scope.seqNo;
          params.prodBarcdCd = $scope.prodBarcdCd;

          var url = "/stock/acins/hqAcins/hqAcinsRegist/getProdInfo.sb";
          $scope._postJSONQuery.withOutPopUp(url, params, function (response) {
            if ($.isEmptyObject(response.data.data)) {
              $scope._popMsg(messages["cmm.empty.data"]);
            }
            else {
              $scope.addRow(response.data.data);
              if ($("#autoAdd").prop("checked")) {
                $scope.addAcinsQty();
              }
              else {
                // setTimeout(function () {
                  $scope.addQty = 1;
                  $("#addQty").select();
                // }, 50);
              }
            }
          });

          // 조회 수행 : 조회URL, 파라미터, 콜백함수
          // $scope._inquirySub("/stock/acins/hqAcins/hqAcinsRegist/list.sb", params, function () {
          //   if ($("#autoAdd").prop("checked")) {
          //     $scope.addAcinsQty();
          //   }
          //   else {
          //     $scope.addQty = 1;
          //     $("#addQty").select();
          //   }
          // });
        }
        else {
          $scope.addQty = 1;
          $("#addQty").select();
        }
      }
    };


    // 그리드의 상품을 찾아서 실사수 수정
    $scope.addAcinsQty = function () {
      var addQty = $scope.addQty;

      if ($("#autoAdd").prop("checked")) {
        addQty = 1;
      }

      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
          $scope.flex.collectionView.editItem(item);

          item.acinsQty = parseInt(nvl(item.acinsQty, 0)) + parseInt(addQty);

          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }
    };


    // 추가수량 input 박스에서 keyDown시
    $scope.fnKeyAddQty = function (event) {
      if (event.keyCode === 13) {
        $scope.addAcinsQty();
      }
    };


    $scope.addRow = function (params) {
      var flex = $scope.flex;
      if (!flex.collectionView) {
        flex.itemsSource = new wijmo.collections.CollectionView();
      }
      var newRow = flex.collectionView.addNew();
      newRow.status = 'I';
      // newRow.gChk = true;
      for (var prop in params) {
        newRow[prop] = params[prop];
      }
      flex.collectionView.trackChanges = true;
      flex.collectionView.commitNew();
      // 추가된 Row 선택
      // setTimeout(function () {
      //   flex.scrollIntoView(flex.rows.length - 1, 0);
      //   flex.select(flex.rows.length - 1, 1);
      //   flex.focus();
      //   flex.startEditing(true, flex.rows.length - 1, (pos === null ? 0 : pos), true);
      // }, 50);
    }
  }]);

</script>
