<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/iostock/cmmExcelUpload/excelUpload/excelUploadErrInfo/"/>

<wj-popup id="wjExcelUploadErrInfoLayer" control="wjExcelUploadErrInfoLayer" show-trigger="Click" hide-trigger="Click" style="display:none;width:700px;">
  <div id="excelUploadErrInfoLayer" class="wj-dialog wj-dialog-columns" ng-controller="excelUploadErrInfoCtrl">
    <div class="wj-dialog-header wj-dialog-header-font">
      <s:message code="excelUploadErrInfo.title"/>
      <a href="#" class="wj-hide btn_close"></a>
    </div>
    <div class="wj-dialog-body sc2" style="height: 450px;">
      <div class="w100 mt10 mb20">
        <%--위즈모 테이블--%>
        <div class="wj-gridWrap" style="height: 350px;">
          <wj-flex-grid
            autoGenerateColumns="false"
            selection-mode="Row"
            items-source="data"
            control="flex"
            initialized="initGrid(s,e)"
            is-read-only="true"
            item-formatter="_itemFormatter">

            <!-- define columns -->
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.prodStatus"/>" binding="prodStatus" width="90" align="center" data-map="prodStatusMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.vendrProdStatus"/>" binding="vendrProdStatus" width="90" align="center" data-map="vendrProdStatusMap" visible="{{vendrProdStatusVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.stockProdYn"/>" binding="stockProdYn" width="90" align="center" data-map="stockProdYnMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.poProdFg"/>" binding="poProdFg" width="90" align="center" data-map="poProdFgMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.useYn"/>" binding="useYn" width="60" align="center" data-map="useYnMap"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.prodBarcdCd"/>" binding="prodBarcdCd" width="150" align="center"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.unitQty"/>" binding="unitQty" width="70" align="right" data-type="Number" format="n0" visible="{{unitQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.etcQty"/>" binding="etcQty" width="70" align="right" data-type="Number" format="n0" visible="{{etcQtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.qty"/>" binding="qty" width="70" align="right" data-type="Number" format="n0" visible="{{qtyVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.uprc"/>" binding="uprc" width="70" align="right" visible="{{uprcVisibleFg}}"></wj-flex-grid-column>
            <wj-flex-grid-column header="<s:message code="excelUploadErrInfo.remark"/>" binding="remark" width="70" align="left" visible="{{remarkVisibleFg}}"></wj-flex-grid-column>

          </wj-flex-grid>
        </div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
  </div>
</wj-popup>


<script type="text/javascript">

  /** 상품 입고현황 그리드 controller */
  app.controller('excelUploadErrInfoCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('excelUploadErrInfoCtrl', $scope, $http, true));

    // 그리드 상품상태
    $scope.prodStatusMap = new wijmo.grid.DataMap([
      {id: "0", name: "<s:message code='excelUploadErrInfo.prodStatusNoReg'/>"},
      {id: "1", name: "<s:message code='excelUploadErrInfo.prodStatusReg'/>"}
    ], 'id', 'name');

    // 그리드 상품거래처취급여부
    $scope.vendrProdStatusMap = new wijmo.grid.DataMap([
      {id: "0", name: "<s:message code='excelUploadErrInfo.vendrProdStatusNoReg'/>"},
      {id: "1", name: "<s:message code='excelUploadErrInfo.vendrProdStatusReg'/>"}
    ], 'id', 'name');

    // 그리드 재고상품여부
    $scope.stockProdYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: "<s:message code='excelUploadErrInfo.stockProdY'/>"},
      {id: "N", name: "<s:message code='excelUploadErrInfo.stockProdN'/>"}
    ], 'id', 'name');

    // 그리드 사용여부
    $scope.useYnMap = new wijmo.grid.DataMap([
      {id: "Y", name: "<s:message code='excelUploadErrInfo.useY'/>"},
      {id: "N", name: "<s:message code='excelUploadErrInfo.useN'/>"}
    ], 'id', 'name');


    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {
      var comboParams         = {};
      comboParams.nmcodeGrpCd = "092";
      $scope._queryCombo("map", null, 'poProdFgMap', null, comboParams, "A"); // 명칭관리 조회시 url 없이 그룹코드만 넘긴다.

      // 헤더 사이즈 크기 조정
      // $scope.flex.columnHeaders.rows.defaultSize = 45;

      // 그리드 링크 효과
      s.formatItem.addHandler(function (s, e) {
        if (e.panel === s.cells) {
          var col = s.columns[e.col];

          if (col.format === "date") {
            e.cell.innerHTML = getFormatDate(e.cell.innerText);
          } else if (col.format === "dateTime") {
            e.cell.innerHTML = getFormatDateTime(e.cell.innerText);
          }
        }
      });

    };


    // 다른 컨트롤러의 broadcast 받기
    $scope.$on("excelUploadErrInfoCtrl", function (event, data) {
      // 그리드 초기화
      var cv          = new wijmo.collections.CollectionView([]);
      cv.trackChanges = true;
      $scope.data     = cv;

      $scope.uploadFg = data.uploadFg;
      $scope.wjExcelUploadErrInfoLayer.show(true);
      $scope.gridVisible();
      $scope.searchExcelUploadErrInfoList();

      // 기능수행 종료 : 반드시 추가
      event.preventDefault();
    });


    // 그리드 컬럼 visible 초기화
    $scope.gridVisibleDefault = function () {
      $scope.vendrProdStatusVisibleFg = true; // 상품거래처취급여부
      $scope.unitQtyVisibleFg         = true; // 단위수량
      $scope.etcQtyVisibleFg          = true; // 낱개수량
      $scope.qtyVisibleFg             = true; // 수량
      $scope.uprcVisibleFg            = true; // 단가
      $scope.remarkVisibleFg          = true; // 비고
    };


    // 그리드 컬럼 visible 컨트롤
    $scope.gridVisible = function () {
      $scope.gridVisibleDefault();
      // 주문등록, 반품등록
      if ($scope.uploadFg === 'order') {
        $scope.vendrProdStatusVisibleFg = false; // 상품거래처취급여부
        $scope.unitQtyVisibleFg         = true;  // 단위수량
        $scope.etcQtyVisibleFg          = true;  // 낱개수량
        $scope.qtyVisibleFg             = false; // 수량
        $scope.uprcVisibleFg            = false; // 단가
        $scope.remarkVisibleFg          = false; // 비고
      }
      // 분배마감, 반품마감
      else if ($scope.uploadFg === 'dstbCloseStore') {
        $scope.vendrProdStatusVisibleFg = false; // 상품거래처취급여부
        $scope.unitQtyVisibleFg         = true;  // 단위수량
        $scope.etcQtyVisibleFg          = true;  // 낱개수량
        $scope.qtyVisibleFg             = false; // 수량
        $scope.uprcVisibleFg            = true;  // 단가
        $scope.remarkVisibleFg          = false; // 비고
      }
      // 실사,조정,폐기
      else if ($scope.uploadFg === 'acins' || $scope.uploadFg === 'adj' || $scope.uploadFg === 'disuse') {
        $scope.vendrProdStatusVisibleFg = false; // 상품거래처취급여부
        $scope.unitQtyVisibleFg         = false; // 단위수량
        $scope.etcQtyVisibleFg          = false; // 낱개수량
        $scope.qtyVisibleFg             = true;  // 수량
        $scope.uprcVisibleFg            = true;  // 단가
        $scope.remarkVisibleFg          = true;  // 비고
      }
      // 거래처 발주, 거래처 입고
      else if ($scope.uploadFg === 'vendr') {
        $scope.vendrProdStatusVisibleFg = true;  // 상품거래처취급여부
        $scope.unitQtyVisibleFg         = true;  // 단위수량
        $scope.etcQtyVisibleFg          = true;  // 낱개수량
        $scope.qtyVisibleFg             = false; // 수량
        $scope.uprcVisibleFg            = true;  // 단가
        $scope.remarkVisibleFg          = false; // 비고
      }
    };


    // 엑셀업로드 에러내역 리스트 조회
    $scope.searchExcelUploadErrInfoList = function () {
      // 파라미터
      var params = {};

      // 조회 수행 : 조회URL, 파라미터, 콜백함수
      $scope._inquiryMain("/iostock/cmmExcelUpload/excelUpload/excelUploadErrInfo/list.sb", params);
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
