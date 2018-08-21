/****************************************************************
 *
 * 파일명 : couponProd.js
 * 설  명 : 쿠폰 상품등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.17     김지은      1.0
 *
 * **************************************************************/
$(document).ready(function(){

  console.log("ssssssssssssssssssssssssssssss")

  var useYnDataMap        = new wijmo.grid.DataMap(useYn, 'value', 'name');
  var coupnDcFgDataMap    = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
  var coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');

  // ========================================================================= 쿠폰 그리드 초기화
  var couponGridHeader =
    [
      {binding:"hqOfficeCd", header:messages["coupon.hqOfficeCd"], visible:false},
      {binding:"storeCd", header:messages["coupon.storeCd"], visible:false},
      {binding:"coupnCd", header:messages["coupon.coupnCd"], maxLength:4, width:"*"},
      {binding:"coupnNm", header:messages["coupon.coupnNm"], maxLength:15, width:"*"},
      {binding:"payClassCd", header:messages["coupon.payClassCd"], width:"*"},
      {binding:"coupnDcFg", header:messages["coupon.coupnDcFg"], dataMap:coupnDcFgDataMap, width:"*"},
      {binding:"coupnDcRate", header:messages["coupon.coupnDcRate"], width:"*"},
      {binding:"coupnDcAmt", header:messages["coupon.coupnDcAmt"], maxLength:5, width:"*"},
      {binding:"coupnApplyFg", header:messages["coupon.coupnApplyFg"], dataMap:coupnApplyFgDataMap, width:"*"}
    ];

  var couponGrid = wgrid.genGrid("#couponGrid", couponGridHeader);
  couponGrid.isReadOnly = true;

  // 쿠폰 그리드 포맷
  couponGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "coupnCd" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  // 쿠폰 그리드 선택 이벤트
  couponGrid.addEventListener(couponGrid.hostElement, 'mousedown', function(e) {
    var ht = couponGrid.hitTest(e);
    var row = ht.row;
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "coupnCd") {
        var col = ht.panel.columns[ht.col];
        var selectedCoupn = couponGrid.rows[ht.row].dataItem;
        searchProdData(selectedCoupn);
      }
    }
  });

  // ========================================================================= 등록상품 그리드 초기화

  var regProdGridHeader =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:messages["coupon.hqOfficeCd"], visible:false},
      {binding:"storeCd", header:messages["coupon.storeCd"], visible:false},
      {binding:"coupnCd", header:messages["coupon.coupnCd"], visible:false},
      {binding:"prodCd", header:messages["coupon.prodCd"], isReadOnly:true},
      {binding:"prodNm", header:messages["coupon.prodNm"], isReadOnly:true}
    ];

  var gridRegistProd = wgrid.genGrid("#gridRegistProd", regProdGridHeader);
  gridRegistProd.isReadOnly = false;

  // ========================================================================= 미등록상품 그리드 초기화
  var noRegProdGridHeader =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:messages["coupon.hqOfficeCd"], visible:false},
      {binding:"storeCd", header:messages["coupon.storeCd"], visible:false},
      {binding:"coupnCd", header:messages["coupon.coupnCd"], visible:false},
      {binding:"prodCd", header:messages["coupon.prodCd"], isReadOnly:true},
      {binding:"prodNm", header:messages["coupon.prodNm"], isReadOnly:true}
    ];

  var gridNoRegistProd = wgrid.genGrid("#gridNoRegistProd", noRegProdGridHeader);
  gridNoRegistProd.isReadOnly = false;

  // =========================================================================

  searchCouponList();

  // 쿠폰 데이터 조회
  function searchCouponList(){

    var param = {};
    param.useYn = 'Y';

    console.log(param);

    $.postJSON(baseUrl+"/class/getCouponList.sb", param, function(result) {
      console.log(result);
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;
      couponGrid.itemsSource = new wijmo.collections.CollectionView(list);
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  }

  // 상품 데이터 조회
  function searchProdData(data){

    console.log(data);

    var param = {};
    param.hqOfficeCd = data.hqOfficeCd;
    param.storeCd = data.storeCd;
    param.coupnCd = data.coupnCd;

    $.postJSON(baseUrl+"/prod/getProdList.sb", param, function(result) {
      console.log(result);
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;
      couponGrid.itemsSource = new wijmo.collections.CollectionView(list);
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });

  }

  // 탭 클릭
  $("#couponProdTab").click(function(){
    location.href = "/base/pay/coupon/class/couponProdView.sb";
  });

  $("#couponStoreTab").click(function(){
    location.href = "/base/pay/coupon/class/couponStoreView.sb";
  });

});
