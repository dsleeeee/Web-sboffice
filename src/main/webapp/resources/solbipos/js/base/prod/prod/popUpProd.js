/****************************************************************
 *
 * 파일명 : popUpProd.js
 * 설  명 : 상품정보관리 팝업 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.24     노현수      1.0
 *
 * **************************************************************/
/**
 * 팝업 그리드 생성
 */
app.controller('prodDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('prodDetailCtrl', $scope, $http, true));


}]);




/*
$(document).ready(function(){

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

  // 엑셀 다운로드
  $("#excelBtn").click(function( e ) {
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });

});
*/
