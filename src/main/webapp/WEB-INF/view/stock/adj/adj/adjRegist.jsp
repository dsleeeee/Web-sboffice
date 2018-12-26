<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/stock/adj/adj/adjRegist/"/>

<wj-popup id="wjAdjRegistLayer" control="wjAdjRegistLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:900px;">
  <div id="adjRegistLayer" class="wj-dialog wj-dialog-columns" ng-controller="adjRegistCtrl">
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
            <%-- 조정제목 --%>
            <th><s:message code="adj.reg.adjTitle"/><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="adjTitle" name="adjTitle" ng-model="adjTitle" class="sb-input w100" maxlength="33"
                     required
                     popover-enable="myForm.adjTitle.$invalid"
                     popover-placement="bottom-left"
                     popover-trigger="'mouseenter'"
                     uib-popover="<s:message code="adj.reg.adjTitle"/>은(는) 필수 입력항목 입니다."/>
            </td>
          </tr>
          <tr>
            <%-- 상품코드 --%>
            <th><s:message code="adj.reg.prodCd"/></th>
            <td>
              <input type="text" id="srchProdCd" name="srchProdCd" ng-model="prodCd" class="sb-input w100" maxlength="13"/>
            </td>
            <%-- 상품명 --%>
            <th><s:message code="adj.reg.prodNm"/></th>
            <td>
              <input type="text" id="srchProdNm" name="srchProdNm" ng-model="prodNm" class="sb-input w100" maxlength="50"/>
            </td>
          </tr>
          <tr>
            <%-- 바코드 --%>
            <th><s:message code="adj.reg.barcd"/></th>
            <td>
              <input type="text" id="srchBarcdCd" name="srchBarcdCd" ng-model="barcdCd" class="sb-input w100" maxlength="40"/>
            </td>
            <%-- 상품분류 --%>
            <th><s:message code="adj.reg.prodClass"/></th>
            <td>
              <input type="text" class="sb-input w100" id="srchProdClassCd" ng-model="prodClassCdNm" ng-click="popUpProdClass()"
                     placeholder="<s:message code="cmm.all" />" readonly/>
              <input type="hidden" id="_prodClassCd" name="prodClassCd" class="sb-input w100" ng-model="prodClassCd" disabled/>
            </td>
          </tr>
          <tr>
            <%-- 거래처 --%>
            <th><s:message code="adj.reg.vendr"/></th>
            <td>
              <%-- 거래처선택 모듈 멀티 선택 사용시 include
                   param 정의 : targetId - angular 콘트롤러 및 input 생성시 사용할 타켓id
              --%>
              <jsp:include page="/WEB-INF/view/iostock/cmm/selectVendrM.jsp" flush="true">
                <jsp:param name="targetId" value="adjRegistSelectVendr"/>
              </jsp:include>
              <%--// 거래처선택 모듈 싱글 선택 사용시 include --%>
            </td>
            <%-- 조정구분 --%>
            <th><s:message code="adj.reg.adjFg"/></th>
            <td>
              <div class="sb-select">
              <span class="txtIn w150px">
                <wj-combo-box
                  id="srchAdjFg"
                  ng-model="adjFg"
                  ng-disabled="readAdjFg"
                  items-source="_getComboData('srchAdjFg')"
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
            <td colspan="4">
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelFormDown')"><s:message code="adj.reg.excelFormDownload"/></a>
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
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('excelUp')"><s:message code="adj.reg.excelFormUpload"/></a>
              <a href="#" class="btn_grayS" ng-click="excelTextUpload('textUp')"><s:message code="adj.reg.textFormUpload"/></a>
              <a href="#" class="btn_grayS" ng-click="excelDownload()"><s:message code="cmm.excel.down"/></a>
              <a href="#" class="btn_grayS" ng-click="excelUploadErrInfo()"><s:message code="adj.reg.excelFormUploadErrorInfo"/></a>
            </td>
          </tr>
          </tbody>
        </table>
      </form>

      <div class="mt10 oh">
        <%-- 조회 --%>
        <button type="button" class="btn_blue fr" id="btnSearch" ng-click="fnSearch();">
          <s:message code="cmm.search"/></button>
      </div>

      <ul class="txtSty3 mt10">
        <li class="red"><s:message code="adj.reg.txt1"/></li>
        <li class="red"><s:message code="adj.reg.txt2"/></li>
        <li class="red"><s:message code="adj.reg.txt3"/></li>
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
            <s:message code="adj.reg.prodCd"/>/<s:message code="adj.reg.barcd"/></th>
          <%-- 추가수량 --%>
          <th class="tc"><s:message code="adj.reg.addQty"/></th>
        </tr>
        <tr>
          <td>
            <input type="text" id="prodBarcdCd" name="prodBarcdCd" ng-model="prodBarcdCd" class="sb-input tc" maxlength="40" style="width: 250px;" ng-keydown="searchProdKeyEvt($event)"/>
            <%-- 찾기 --%>
            <a href="#" class="btn_grayS" ng-click="prodFindPop()"><s:message code="adj.reg.prodFind"/></a>
            <span class="chk txtIn lh30 ml5" style="top: -2px;">
              <input type="checkbox" name="autoAddChk" id="autoAddChk" ng-model="autoAddChk"/>
              <label for="autoAddChk"><s:message code="adj.reg.autoAdd"/></label>
            </span>
          </td>
          <td>
            <input type="text" id="addQty" name="addQty" ng-model="addQty" class="sb-input tc" maxlength="10" style="width: 100px;" ng-keydown="addQtyKeyEvt($event)"/>
            <%-- 추가 --%>
            <a href="#" class="btn_grayS" ng-click="fnAddQty()"><s:message code="adj.reg.add"/></a>
          </td>
        </tr>
        </tbody>
      </table>

      <div class="mt20 tr">
        <div class="oh sb-select">
          <%-- 페이지 스케일  --%>
          <wj-combo-box
            class="w100px fl"
            id="regListScaleBox"
            ng-model="listScale"
            items-source="_getComboData('regListScaleBox')"
            display-member-path="name"
            selected-value-path="value"
            is-editable="false"
            initialized="_initComboBox(s)">
          </wj-combo-box>
          <%--// 페이지 스케일  --%>

          <%-- 저장 --%>
          <button type="button" class="btn_skyblue ml5" id="btnRegSave" ng-click="saveAdjRegist()">
            <s:message code="cmm.save"/></button>
        </div>
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
            <wj-flex-grid-column header="<s:message code="adj.reg.prodCd"/>" binding="prodCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.prodNm"/>" binding="prodNm" width="150" align="left" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.barcdCd"/>" binding="barcdCd" width="100" align="center" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.saleUprc"/>" binding="saleUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.costUprc"/>" binding="costUprc" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.poUnitQty"/>" binding="poUnitQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.currQty"/>" binding="currQty" width="70" align="right" is-read-only="true"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjQty"/>" binding="adjQty" width="70" align="right" max-length=8 data-type="Number" format="n0" aggregate="Sum"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjAmt"/>" binding="adjAmt" width="0" align="right" is-read-only="true" visible="false"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.remark"/>" binding="remark" width="200" align="left" max-length=300></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="adj.reg.adjProdStatus"/>" binding="adjProdStatus" width="0" align="center" is-read-only="true" visible="false"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <%-- id --%>
        <ul id="adjRegistCtrlPager" data-size="10">
        </ul>
      </div>
      <%--//페이지 리스트--%>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 조정관리 등록 그리드 controller */
  app.controller('adjRegistCtrl', ['$scope', '$http', function ($scope, $http) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('adjRegistCtrl', $scope, $http, true));

    $scope._setComboData("regListScaleBox", gvListScaleBoxData);

    $scope._setComboData("srchAdjFg", [
      {"name": messages["cmm.all"], "value": ""},
      {"name": messages["adj.reg.adjFgN"], "value": "N"},
      {"name": messages["adj.reg.adjFgY"], "value": "Y"},
    ]);

    $scope._setComboData("addQtyFg", [
      {"name": messages["adj.reg.addQtyFgApply"], "value": "apply"},
      {"name": messages["adj.reg.addQtyFgAdd"], "value": "add"}
    ]);

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
    $scope.$on("adjRegistCtrl", function (event, data) {

      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      if (!$.isEmptyObject(data)) {
        $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅

        $scope.adjDate    = data.adjDate;
        $scope.seqNo      = data.seqNo;
        $scope.callParent = data.callParent;

        // 상품분류 값 초기화
        $scope.prodClassCdNm = messages["cmm.all"];
        $scope.prodClassCd   = '';

        // 상품찾기 변수값들 초기화
        $scope.addQty      = '';
        $scope.prodBarcdCd = '';
        $scope.autoAddChk  = false;
        $scope.adjTitle    = '';

        // 신규등록이면 조정구분 disabled 시킨다.
        if ($scope.callParent === "adj") {
          $scope.readAdjFg = true;
          // 신규등록인 경우 진행구분 체크 필요없음으로 바로 팝업을 show 한다.
          $scope.layerShow();
        } else {
          $scope.readAdjFg = false;
          $scope.procFgCheck(); // 조정진행구분 체크
        }
      } else { // 페이징처리에서 broadcast 호출시
        $scope.searchAdjRegistList();
      }

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 조정진행구분 체크 및 조정제목 조회
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
            if (response.data.data.procFg != "" && response.data.data.procFg != "0") {
              $scope._popMsg(messages["adj.reg.not.procEnd"]);
              return false;
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
        $scope.layerShow();
      });
    };


    $scope.layerShow = function () {
      $scope.wjAdjRegistLayer.show(true);
      $("#registSubTitle").html(messages["adj.reg.adjDate"] + ' : ' + getFormatDate($scope.adjDate, '-'));
    };


    // 조정상품 리스트 조회
    $scope.searchAdjRegistList = function () {
      // 파라미터
      var params         = {};
      params.adjDate     = $scope.adjDate;
      params.seqNo       = $scope.seqNo;
      params.prodCd      = $scope.prodCd;
      params.prodNm      = $scope.prodNm;
      params.barcdCd     = $scope.barcdCd;
      params.prodClassCd = $scope.prodClassCd;
      params.adjFg       = $scope.adjFg;
      params.vendrCd     = $("#adjRegistSelectVendrCd").val();
      params.listScale   = $scope.listScale;

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquirySub("/stock/adj/adj/adjRegist/list.sb", params);
    };


    // 조회버튼으로 조회시
    $scope.fnSearch = function () {
      if ($scope.flex.collectionView.itemsEdited.length > 0 || $scope.flex.collectionView.itemsAdded.length > 0) {
        var msg = messages["adj.reg.searchMsg"]; // 저장되지 않은 자료가 있습니다. 조회하시겠습니까?
        s_alert.popConf(msg, function () {
          $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
          $scope.searchAdjRegistList();
        });
      } else {
        $scope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
        $scope.searchAdjRegistList();
      }
    };


    // 조정 상품 저장
    $scope.saveAdjRegist = function () {
      if (nvl($scope.adjTitle, '') === '') {
        var msg = messages["adj.reg.adjTitle"] + messages["cmm.require.text"]; // 조정제목을 입력하세요.
        $scope._popMsg(msg);
        return false;
      }
      var params = [];
      // 추가된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsAdded.length; i++) {
        var item = $scope.flex.collectionView.itemsAdded[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.gChk === true && item.adjProdStatus === 'U') {
          item.status = "D";
        } else {
          item.status = "U";
        }
        item.adjDate   = $scope.adjDate;
        item.seqNo     = $scope.seqNo;
        item.adjTitle  = $scope.adjTitle;
        item.storageCd = "001";
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }

      // 수정된 상품 가져오기
      for (var i = 0; i < $scope.flex.collectionView.itemsEdited.length; i++) {
        var item = $scope.flex.collectionView.itemsEdited[i];

        // 체크박스가 체크되어 있으면서 기존에 등록되어 있던 상품은 삭제한다.
        if (item.gChk === true && item.adjProdStatus === 'U') {
          item.status = "D";
        } else {
          item.status = "U";
        }
        item.adjDate   = $scope.adjDate;
        item.seqNo     = $scope.seqNo;
        item.adjTitle  = $scope.adjTitle;
        item.storageCd = "001";
        item.hqBrandCd = "00"; // TODO 브랜드코드 가져오는건 우선 하드코딩으로 처리. 2018-09-13 안동관

        params.push(item);
      }

      $scope._save("/stock/adj/adj/adjRegist/save.sb", params, function () {
        $scope.saveRegistCallback()
      });
    };


    // 저장 후 콜백 서치 함수
    $scope.saveRegistCallback = function () {
      // 신규등록인 경우
      if ($scope.callParent === "adj") {
        var adjScope = agrid.getScope('adjCtrl');
        adjScope.searchAdjList();
      }
      // 조정상세내역 페이지에서 호출한 경우
      else if ($scope.callParent === "adjDtl") {
        var adjScope = agrid.getScope('adjCtrl');
        adjScope.searchAdjList();

        var adjDtlScope = agrid.getScope('adjDtlCtrl');
        adjDtlScope._setPagingInfo('curr', 1); // 페이지번호 1로 세팅
        adjDtlScope.searchAdjDtlList();
      }

      $scope.wjAdjRegistLayer.hide(true);
    };


    // 상품코드/바코드 input 박스에서 keyDown시
    $scope.searchProdKeyEvt = function (event) {
      if (event.keyCode === 13) { // 이벤트가 enter 이면
        var searchFg = true;

        // 조회된 상품중에 해당 상품코드/바코드가 있는지 검색
        for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
          var item = $scope.flex.collectionView.items[i];
          if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
            searchFg = false; // 그리드에 이미 해당 상품이 있는 경우 서버 조회 하지않도록 변수값 false 로 변경
          }
        }

        if (searchFg) {
          // 파라미터
          var params         = {};
          params.adjDate     = $scope.adjDate;
          params.seqNo       = $scope.seqNo;
          params.prodBarcdCd = $scope.prodBarcdCd;
          params.listScale   = 1; // 상품 하나만 조회해야 하므로 listScale 1로 줌.
          params.curr        = 1;

          var url = "/stock/adj/adj/adjRegist/getProdInfo.sb";
          $scope._postJSONQuery.withOutPopUp(url, params, function (response) {
            if ($.isEmptyObject(response.data.data)) {
              $scope._popMsg(messages["cmm.empty.data"]);
            } else {
              $scope.addRow(response.data.data);
              if ($("#autoAddChk").prop("checked")) {
                $scope.modifyAdjQty(1);
              } else {
                $scope.addQty = 1;
                $("#addQty").select();
              }
            }
          });
        } else {
          if ($("#autoAddChk").prop("checked")) {
            $scope.modifyAdjQty(1);
          } else {
            $scope.addQty = 1;
            $("#addQty").select();
          }
        }
      }
    };


    // 그리드의 상품을 찾아서 조정수 수정
    $scope.modifyAdjQty = function (addQty) {
      for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
        var item = $scope.flex.collectionView.items[i];
        if (item.prodCd === $scope.prodBarcdCd || item.barcdCd === $scope.prodBarcdCd) {
          $scope.flex.collectionView.editItem(item);

          item.adjQty = parseInt(nvl(item.adjQty, 0)) + parseInt(addQty);

          $scope.calcAmt(item);
          $scope.flex.collectionView.commitEdit();
        }
      }

      // 자동추가가 체크되어 있는 경우 focus 를 계속 상품코드/바코드 입력하는곳에 둔다.
      if ($("#autoAddChk").prop("checked")) {
        $scope.addQty = '';
        $("#prodBarcdCd").select();
      }
    };


    // 추가버튼 클릭시
    $scope.fnAddQty = function () {
      var qty = $scope.addQty;
      $scope.modifyAdjQty(qty);
    };


    // 추가수량 input 박스에서 keyDown시
    $scope.addQtyKeyEvt = function (event) {
      if (event.keyCode === 13) {
        $scope.fnAddQty();
      }
    };


    // grid 의 row 추가
    $scope.addRow = function (params) {
      var flex = $scope.flex;
      if (!flex.collectionView) {
        flex.itemsSource = new wijmo.collections.CollectionView();
      }
      flex.collectionView.trackChanges = true;
      var newRow                       = flex.collectionView.addNew();
      newRow.status                    = 'U';
      for (var prop in params) {
        newRow[prop] = params[prop];
      }
      flex.collectionView.commitNew();
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
      if ($scope.flex.rows.length <= 0) {
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
      if (nvl($scope.adjTitle,'') === '' && prcsFg !== 'excelFormDown') {
        var msg = messages["adj.reg.adjTitle"] + messages["cmm.require.text"]; // 조정제목을 입력하세요.
        $scope._popMsg(msg);
        return false;
      }

      var excelUploadScope = agrid.getScope('excelUploadCtrl');
      <%-- 업로드 구분. 해당값에 따라 엑셀 양식이 달라짐. --%>
      var uploadFg = 'adj';

      // 엑셀 양식다운로드
      if (prcsFg === 'excelFormDown') {
        excelUploadScope.excelFormDownload(uploadFg);
      } else {
        var msg = messages["excelUpload.confmMsg"]; // 정상업로드 된 데이터는 자동저장됩니다. 업로드 하시겠습니까?
        s_alert.popConf(msg, function () {
          excelUploadScope.uploadFg = uploadFg;
          <%-- 부모컨트롤러 값을 넣으면 업로드가 완료된 후 uploadCallBack 이라는 함수를 호출해준다. --%>
          excelUploadScope.parentCtrl = 'adjRegistCtrl';
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
      params.date     = $scope.adjDate;
      params.seqNo    = $scope.seqNo;
      params.title    = $scope.adjTitle;
      params.addQtyFg = $scope.addQtyFg;

      var excelUploadScope = agrid.getScope('excelUploadCtrl');

      $http({
        method : 'POST', //방식
        url    : '/stock/adj/adj/adjRegist/excelUpload.sb', /* 통신할 URL */
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
      params.uploadFg = 'adj';
      $scope._broadcast('excelUploadErrInfoCtrl', params);
    };


    // 거래처선택 모듈 팝업 사용시 정의
    // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
    // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
    $scope.adjRegistSelectVendrShow = function () {
      $scope._broadcast('adjRegistSelectVendrCtrl');
    };

  }]);

</script>

<%-- 상품분류 팝업 --%>
<c:import url="/WEB-INF/view/application/layer/searchProdClassCd.jsp">
</c:import>

<%-- 수불 엑셀업로드 공통 팝업 --%>
<c:import url="/WEB-INF/view/iostock/cmmExcelUpload/excelUpload/excelUpload.jsp">
</c:import>
