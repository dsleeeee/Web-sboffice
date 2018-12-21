<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/adj/adj/adjDtl/"/>

<wj-popup id="wjAdjDtlLayer" control="wjAdjDtlLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="adjDtlLayer" class="wj-dialog wj-dialog-columns" ng-controller="adjDtlCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="adj.reg.registTitle"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 600px;">
      <p id="registSubTitle" class="s14 bk mb5 fl"></p>

      <form name="myForm" novalidate>
        <table class="tblType01" style="position: relative;">
          <colgroup>
            <col class="w15"/>
            <col class="w35"/>
            <col class="w15"/>
            <col class="w35"/>
          </colgroup>
          <tbody>
          <tr>
            <th><s:message code="adj.dtl.adjTitle"/><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="dtlAdjTitle" name="dtlAdjTitle" ng-model="adjTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.dtlAdjTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="adj.dtl.adjTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          </tbody>
        </table>
      </form>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="adj.dtl.txt1"/></li>
      </ul>

      <div class="mt20 tr">
        <%-- 상품추가 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlAddProd" ng-click="addProd()" ng-if="btnDtlAddProd">
          <s:message code="adj.dtl.addProd"/></button>
        <%-- 저장 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlSave" ng-click="saveAdjDtl('')" ng-if="btnDtlSave">
          <s:message code="cmm.save"/></button>
        <%-- 확정 --%>
        <button type="button" class="btn_skyblue ml5" id="btnDtlConfirm" ng-click="confirm()" ng-if="btnDtlConfirm">
          <s:message code="adj.dtl.confirm"/></button>
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
            <wj-flex-grid-column header="<s:message code="cmm.chk"/>" binding="gChk" width="30" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjQty"/>" binding="adjQty" width="70" align="right" is-read-only="false" data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjProdStatus"/>" binding="adjProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      `
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="adjDtlCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 조정관리 상세 그리드 controller */
  app.controller('adjDtlCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('adjDtlCtrl', $scope, $http, true));

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

      s.cellEditEnded.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];
          // 조정수량 수정시 금액,VAT,합계 계산하여 보여준다.
          if (col.binding === "adjQty") {
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
      var costUprc = parseInt(item.costUprc);
      var adjQty   = parseInt(nvl(item.adjQty, 0));
      var adjAmt   = parseInt(adjQty) * parseInt(costUprc);

      item.adjQty = adjQty;   // 조정수량
      item.adjAmt = adjAmt; // 조정금액
    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("adjDtlCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      if (!$.isEmptyObject(data)) {
        $scope._setPagingInfo('curr', 1); // 페이징처리시 페이지번호 초기화

        $scope.adjDate = data.adjDate;
        $scope.seqNo   = data.seqNo;

        $scope.procFgCheck(); // 조정 진행구분 체크
      }
      else { // 페이징처리에서 broadcast 호출시
        $scope.searchAdjDtlList();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 조정 진행구분 체크 및 조정제목 조회
    $scope.procFgCheck = function () {
      var params     = {};
      params.adjDate = $scope.adjDate;
      params.seqNo   = $scope.seqNo;

      // ajax 통신 설정
      $http({
        method : 'POST', //방식
        url    : '/stock/adj/adj/adjRegist/procFgCheck.sb', /* 통신할 URL */
        params : params, /* 파라메터로 보낼 데이터 */
        headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
      }).then(function successCallback(response) {
        if ($scope._httpStatusCheck(response, true)) {
          // 진행구분이 조정등록이 아니면 상품추가/변경 불가
          if (!$.isEmptyObject(response.data.data)) {
            // 조정 등록 상태이면 버튼 show
            if (response.data.data.procFg !== "" && response.data.data.procFg === "0") {
              $scope.btnDtlAddProd = true;
              $scope.btnDtlSave    = true;
              $scope.btnDtlConfirm = true;
            }
            else {
              $scope.btnDtlAddProd = false;
              $scope.btnDtlSave    = false;
              $scope.btnDtlConfirm = false;
            }
            $scope.adjTitle = response.data.data.adjTitle;
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
        $scope.wjAdjDtlLayer.show(true);
        $("#registSubTitle").html(messages["adj.reg.adjDate"] + ' : ' + getFormatDate($scope.adjDate, '-'));
        $scope.searchAdjDtlList();
      });
    };


    // 조정상품 리스트 조회
    $scope.searchAdjDtlList = function () {
      // 파라미터
      var params       = {};
      params.adjDate   = $scope.adjDate;
      params.seqNo     = $scope.seqNo;
      params.listScale = 50;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/stock/adj/adj/adjDtl/list.sb", params);
    };


    // 조정 상품 저장
    $scope.saveAdjDtl = function (confirmFg) {
      if (nvl($scope.adjTitle, '') === '') {
        var msg = messages["adj.reg.adjTitle"] + messages["cmm.require.text"];
        $scope._popMsg(msg);
        return false;
      }

      // 확정처리가 체크 되어있으면서 그리드의 수정된 내역은 없는 경우 저장로직 태우기 위해 값 하나를 강제로 수정으로 변경한다.
      if (confirmFg === 'Y' && $scope.flex.collectionView.itemsEdited.length <= 0) {
        var item = $scope.flex.collectionView.items[0];
        if (item === null) return false;

        $scope.flex.collectionView.editItem(item);
        item.status = "U";
        $scope.flex.collectionView.commitEdit();
      }

      var params = [];
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 체크박스가 체크되어 있는 상품은 삭제한다.
        if (item.gChk === true && nvl(item.adjQty, '') !== '') {
          item.status = "D";
        }
        else {
          item.status = "U";
        }
        item.adjDate   = $scope.adjDate;
        item.seqNo     = $scope.seqNo;
        item.adjTitle  = $scope.adjTitle;
        item.storageCd = "001";
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관
        item.confirmFg = confirmFg;

        params.push(item);
      }

      $scope._save("/stock/adj/adj/adjDtl/save.sb", params, function () {
        $scope.saveRegistCallback(confirmFg)
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function (confirmFg) {
      $scope.searchAdjDtlList();

      var adjScope = agrid.getScope('adjCtrl');
      adjScope.searchAdjList();

      if (confirmFg === 'Y') {
        $scope.wjAdjDtlLayer.hide(true);
      }
    };


    // 확정
    $scope.confirm = function () {
      var msg = messages["adj.dtl.confirmMsg"]; // 확정하시겠습니까?
      s_alert.popConf(msg, function () {
        $scope.saveAdjDtl('Y');
        return false;
      });
    };


    // 상품추가
    $scope.addProd = function () {
      var params        = {};
      params.adjDate    = $scope.adjDate;
      params.seqNo      = $scope.seqNo;
      params.callParent = 'adjDtl';
      $scope._broadcast('adjRegistCtrl', params);
    };


  }]);

</script>
