/****************************************************************
 *
 * 파일명 : prod.js
 * 설  명 : 상품정보관리 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 출력코드구성 그리드 생성
 */
app.controller('prodCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodCtrl', $scope, $http, true));

  // 상품정보관리 그리드 조회
  $scope.$on("prodCtrl", function(event, data) {
    // 파라미터
    var params = {};
    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prod/prod/list.sb", params, function() {
      // 버튼 Show
      $("#btnAdd").show();
      $("#btnDel").show();
      $("#btnSave").show();

      $scope.autoSizeVisibleRows($scope.flex);
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

}]);




/*
$(document).ready(function(){
  var rdata =
    [
      {"binding":"prodClassNm","header":"<s:message code='prod.prodClass' />","width":"*"},
      {"binding":"prodCd","header":"<s:message code='prod.prodCd' />","width":"*"},
      {"binding":"prodNm","header":"<s:message code='prod.prodNm' />","width":"*"},
      {"binding":"costUprc","header":"<s:message code='prod.costUprc' />","width":"*"},
      {"binding":"splyUprc","header":"<s:message code='prod.splyUprc' />","width":"*"},
      {"binding":"saleUprc","header":"<s:message code='prod.saleUprc' />","width":"*"},
      {"binding":"orgplceCd","header":"<s:message code='prod.orgplceCd' />","width":"*"},
      {"binding":"poUnitFg","header":"<s:message code='prod.poUnitFg' />","width":"*"}
    ];

  var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var prodCd       = wcombo.genInput("#prodCd");
  var prodNm       = wcombo.genInput("#prodNm");
  var barCd        = wcombo.genInput("#barCd");
  // var prodClassCd  = wcombo.genInput("#prodClassCd");
  var startDate    = wcombo.genDateVal("#startDate", "${sessionScope.sessionInfo.startDate}");
  var endDate      = wcombo.genDateVal("#endDate", "${sessionScope.sessionInfo.endDate}");
  // var ldata        = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);

  function search(index) {
    var param = {};

    param.startDate = getDate(startDate);
    param.endDate = getDate(endDate);
    param.chkDt = $('#chkDt').is(":checked");
    param.prodCd = prodCd.text;
    param.prodNm = prodNm.text;
    param.barCd = barCd.text;
    // param.prodClassCd = prodClassCd.text;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/base/prod/prod/prod/list.sb", param, function(result) {
        var list = result.data.list;

        if(list.length === undefined || list.length === 0) {
          s_alert.pop(result.message);
        }

        grid.itemsSource = list;
        page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result) {
        s_alert.pop(result.message);
      });
  }

  // 그리드 포맷
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel === s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding === "prodCd") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  // 그리드 선택 이벤트
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType === wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding === "prodCd") {
        searchProdDetail(grid.rows[ht.row].dataItem);
      }
    }
  });

  function searchProdDetail(item) {
    var param = {};
    param.prodCd = item.prodCd;

    $.postJSON("/base/prod/prod/prod/view.sb", param, function(result) {
        showProdLayer(result.data);
      }
      ,function(result){
        s_alert.pop(result.message);
      });
  }

  // 레이어 팝업 오픈
  function showProdLayer(item) {

    // var vatFg        = ${ccu.getCommCodeExcpAll("039")};
    // var poProdFg     = ${ccu.getCommCodeExcpAll("064")}; //TODO 발주, 주문상품 구분 => 코드 추가 필요
    // var setProdFg    = ${ccu.getCommCodeExcpAll("009")};
    // var useYn        = ${ccu.getCommCodeExcpAll("067")};
    var vatFgDataMap = new wijmo.grid.DataMap(vatFg, 'value', 'name');
    var poProdFgDataMap  = new wijmo.grid.DataMap(poProdFg, 'value', 'name');
    var setProdFgDataMap = new wijmo.grid.DataMap(setProdFg, 'value', 'name');
    var useYnDataMap     = new wijmo.grid.DataMap(useYn, 'value', 'name');

    $("#_prodCd").text(item.prodCd);
    $("#_prodNm").text(item.prodNm);
    $("#_prodClassNm").text(item.prodClassNm);
    $("#_orgplceCd").text(item.orgplceCd);
    $("#_saleProdYn").text(item.saleProdYn);
    $("#_costUprc").text(comma(item.costUprc));
    $("#_saleUprc").text(comma(item.saleUprc));
    $("#_poProdFg").text(poProdFgDataMap.getDisplayValue(item.poProdFg));
    $("#_poUnitFg").text(item.poUnitFg);
    $("#_poMinQty").text(item.poMinQty);

    $("#_vatFg").text(vatFgDataMap.getDisplayValue(item.vatFg));
    $("#_stockProdYn").text(item.stockProdYn);
    $("#_useYn").text(useYnDataMap.getDisplayValue(item.useYn));
    $("#_safeStockQty").text(item.safeStockQty);
    $("#_setProdFg").text(setProdFgDataMap.getDisplayValue(item.setProdFg));
    $("#_remark").text(nvl(item.remark, ''));

    var list = item.list;
    var unitstData = "";

    if( list.length > 0 ) {
      $.each(list, function() {
        unitstData += "<tr><th><div class='impWrap'><s:message code='prod.prodCd' /><em class='imp'>*</em></div></th>"
          + "<td><a href='#' class='link'>" + this.unitProdCd + "</a></td></tr>";
      });
    }
    else {
      unitstData += "<tr><th colspan='2'><s:message code='cmm.empty.data' /></th></tr>";
    }

    $("#_unitstData").html(unitstData);

    $("#prodFullDimmed").show();
    $("#prodDetailLayer").show();
  }

  // 레이어 팝업 닫기
  $(".btn_close, #btnClose").click(function() {
    $("#prodFullDimmed").hide();
    $("#prodDetailLayer").hide();
  });

  // 리스트 조회
  $("#searchBtn").click(function( e ) {
    search(1);
  });

  // 엑셀 다운로드
  $("#excelBtn").click(function( e ) {
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });

  // 페이징
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  // 전체기간 체크박스
  $(document).on("click", "#chkDt", function() {
    var chkDt = $('#chkDt').is(":checked");
    startDate.isDisabled = chkDt;
    endDate.isDisabled = chkDt;
  });

});
*/
