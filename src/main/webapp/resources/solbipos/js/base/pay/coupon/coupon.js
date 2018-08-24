/****************************************************************
 *
 * 파일명 : coupon.js
 * 설  명 : 쿠폰등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.08.13     김지은      1.0
 *
 * **************************************************************/
$(document).ready(function(){

  // 쿠폰적용매장 등록 (본사에서만 보임)
  if(orgnFg == "HQ") {
    $("#couponStoreTab").show();
  } else {
    $("#couponStoreTab").hide();
  }

  var selectedClass;

  var useYnDataMap        = new wijmo.grid.DataMap(useYn, 'value', 'name');
  var coupnDcFgDataMap    = new wijmo.grid.DataMap(coupnDcFg, 'value', 'name');
  var coupnApplyFgDataMap = new wijmo.grid.DataMap(coupnApplyFg, 'value', 'name');

  // ========================================================================= 쿠폰분류 그리드 초기화

  var classGridHeader =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:messages["coupon.hqOfficeCd"], visible:false},
      {binding:"payTypeFg", header:messages["coupon.payTypeFg"], visible:false},
      {binding:"payClassCd", header:messages["coupon.payClassCd"], maxLength:3, width:"*"},
      {binding:"payClassNm", header:messages["coupon.payClassNm"], maxLength:20, width:"*"},
      {binding:"serNoYn", header:messages["coupon.serNoYn"], dataMap:useYnDataMap, width:"*"},
      {binding:"useYn", header:messages["cmm.useYn"], dataMap:useYnDataMap, width:"*"},
      {binding:"regId", header:messages["cmm.regId"], visible:false}
    ];

  var couponClassGrid = wgrid.genGrid("#couponClassGrid", classGridHeader);
  couponClassGrid.isReadOnly = false;

  // 쿠폰 분류 그리드 포맷
  couponClassGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "payClassCd" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  // 쿠폰 분류 그리드 선택 이벤트
  couponClassGrid.addEventListener(couponClassGrid.hostElement, 'mousedown', function(e) {
    var ht = couponClassGrid.hitTest(e);
    var row = ht.row;
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "payClassCd") {
        var col = ht.panel.columns[ht.col];
        if(couponClassGrid.rows[ht.row].dataItem.status == "I" ){
          e.cancel = false;
        }else{
          searchCouponList(couponClassGrid.rows[ht.row].dataItem); // 기등록된 데이터는 코드 클릭시 쿠폰 목록 조회
        }
      }
    }
  });

  // ========================================================================= 쿠폰 그리드 초기화

  var couponGridHeader =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:messages["coupon.hqOfficeCd"], visible:false},
      {binding:"coupnCd", header:messages["coupon.coupnCd"], maxLength:4, width:"*"},
      {binding:"coupnNm", header:messages["coupon.coupnNm"], maxLength:15, width:"*"},
      {binding:"payClassCd", header:messages["coupon.payClassCd"], visible:false},
      {binding:"coupnDcFg", header:messages["coupon.coupnDcFg"], dataMap:coupnDcFgDataMap, width:"*"},
      {binding:"coupnDcRate", header:messages["coupon.coupnDcRate"], width:"*"},
      {binding:"coupnDcAmt", header:messages["coupon.coupnDcAmt"], maxLength:5, width:"*"},
      //{binding:"targetCnt", header:messages["coupon.targetCnt"], dataMap:targetFgDataMap, width:"*"},
      {binding:"coupnApplyFg", header:messages["coupon.coupnApplyFg"], dataMap:coupnApplyFgDataMap, width:"*"},
      {binding:"prodCnt", header:messages["coupon.prodCnt"], isReadOnly:true, width:"*"},
      {binding:"storeCnt", header:messages["coupon.storeCnt"], isReadOnly:true, width:"*"},
      {binding:"useYn", header:messages["cmm.useYn"], dataMap:useYnDataMap, width:"*"},
      {binding:"regId", header:messages["cmm.regId"], visible:false}
    ];

  var couponGrid = wgrid.genGrid("#couponGrid", couponGridHeader);
  couponGrid.isReadOnly = false;

  // 쿠폰 그리드 선택 이벤트
  couponGrid.addEventListener(couponGrid.hostElement, 'mousedown', function(e) {
    var ht = couponGrid.hitTest(e);
    var row = ht.row;
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "coupnCd") {
        var col = ht.panel.columns[ht.col];
        if(couponGrid.rows[ht.row].dataItem.status != "I" ){
          e.cancel = false;
        }
      }
    }
  });

  // 쿠폰 그리드 데이터 수정시 코드는 수정 불가
  couponGrid.beginningEdit.addHandler(function(s, e) {

    // 조회된 데이터는 코드 수정 불가능
    if(couponGrid.rows[e.row].dataItem.status == "I"){
      if(e.col != 1){

      }else{
        e.cancel = true;
      }
    }else{
      e.cancel = false;
    }

    // 할인구분 => 금액 관련이면, 할인율은 입력못함
    if(e.col == 6) {
      if(couponGrid.rows[e.row].dataItem.coupnDcFg == "3" || couponGrid.rows[e.row].dataItem.coupnDcFg == "4"|| couponGrid.rows[e.row].dataItem.coupnDcFg == "6") {
        e.cancel = true;
      } else {
        e.cancel = false;
      }
    }
    // 할인구분 => % 관련이면, 할인금액 입력못함
    if(e.col == 7) {
      if(couponGrid.rows[e.row].dataItem.coupnDcFg == "1" || couponGrid.rows[e.row].dataItem.coupnDcFg == "2") {
        e.cancel = true;
      } else {
        e.cancel = false;
      }
    }
  });

  // ========================================================================= 쿠폰분류 관련 함수

  getCouponClassList();

  // 쿠폰 분류 조회
  function getCouponClassList(){

    var param = {};
    param.coupnEnvstVal = coupnEnvstVal;

    console.log(param);

    $.postJSON(baseUrl+"/class/getCouponClassList.sb", param, function(result) {
      console.log(result);

      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }

      var list = result.data.list;
      couponClassGrid.itemsSource = new wijmo.collections.CollectionView(list);
      couponClassGrid.itemsSource.trackChanges = true;
      couponGrid.itemsSource = [];
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  }

  // 분류 추가 버튼 클릭
  $("#btnClassAdd").click(function(){
    couponClassGrid.collectionView.trackChanges = true;
    var newItem = couponClassGrid.collectionView.addNew();
    newItem.status = "I";
    newItem.gChk = false;
    newItem.serNoYn = "Y";
    newItem.useYn = "Y";

    couponClassGrid.collectionView.commitNew();
    couponClassGrid.select(couponGrid.rows.length, 1);
  });

  // 분류 삭제 버튼 클릭
  $("#btnClassDel").click(function(){
    for(var i = couponClassGrid.itemsSource.itemCount-1; i >= 0; i-- ){
      var item = couponClassGrid.itemsSource.items[i];
      if(item.gChk){
        couponClassGrid.itemsSource.removeAt(i);
      }
    }
  });

  // 분류 저장 버튼 클릭
  $("#btnClassSave").click(function(){

    var paramArr = new Array();

    //TODO validation 체크

    for(var i=0; i<couponClassGrid.collectionView.itemsAdded.length; i++){
      couponClassGrid.collectionView.itemsAdded[i].status = "I";
      // couponClassGrid.collectionView.itemsAdded[i].coupnEnvstVal = "HQ";
      couponClassGrid.collectionView.itemsAdded[i].coupnEnvstVal = coupnEnvstVal;
      paramArr.push(couponClassGrid.collectionView.itemsAdded[i]);
    }
    for(var i=0; i<couponClassGrid.collectionView.itemsEdited.length; i++){
      couponClassGrid.collectionView.itemsEdited[i].status = "U";
      // couponClassGrid.collectionView.itemsAdded[i].coupnEnvstVal = "HQ";
      couponClassGrid.collectionView.itemsEdited[i].coupnEnvstVal = coupnEnvstVal;
      paramArr.push(couponClassGrid.collectionView.itemsEdited[i]);
    }
    for(var i=0; i<couponClassGrid.collectionView.itemsRemoved.length; i++){
      couponClassGrid.collectionView.itemsRemoved[i].status = "D";
      // couponClassGrid.collectionView.itemsRemoved[i].coupnEnvstVal = "HQ";
      couponClassGrid.collectionView.itemsRemoved[i].coupnEnvstVal = coupnEnvstVal;
      paramArr.push(couponClassGrid.collectionView.itemsRemoved[i]);
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    console.log(paramArr);

    $.postJSONArray(baseUrl + "/class/saveCouponClassList.sb", paramArr, function(result) {
      s_alert.pop(messages["cmm.saveSucc"]);
      couponClassGrid.collectionView.clearChanges();
      getCouponClassList();
    },
    function(result) {
      s_alert.pop(result.data.msg);
    });
  });

  // ========================================================================= 쿠폰 관련 함수

  // 쿠폰 데이터 조회
  function searchCouponList(data) {
    console.log(data);

    selectedClass = data;

    var param = {};
    param.payClassCd = selectedClass.payClassCd;
    param.coupnEnvstVal = coupnEnvstVal;

    $.postJSON(baseUrl+"/class/getCouponList.sb", param, function(result) {

      console.log(result);

      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }

      var list = result.data.list;
      couponGrid.itemsSource = new wijmo.collections.CollectionView(list);
      couponGrid.itemsSource.trackChanges = true;
    }
    ,function(){
      s_alert.pop("Ajax Fail");
    });
  }

  // 쿠폰 추가 버튼 클릭
  $("#btnCouponAdd").click(function(){

    couponGrid.collectionView.trackChanges = true;
    var newItem = couponGrid.collectionView.addNew();
    newItem.status = "I";
    newItem.gChk = false;
    newItem.coupnDcFg = 1;
    //newItem.targetCnt = 1;
    newItem.coupnApplyFg = 1;
    newItem.useYn = "Y";
    couponGrid.collectionView.commitNew();
    couponGrid.select(couponGrid.rows.length, 1);

  });

  // 쿠폰 삭제 버튼 클릭
  $("#btnCouponDel").click(function(){
    for(var i = couponGrid.itemsSource.itemCount-1; i >= 0; i-- ){
      var item = couponGrid.itemsSource.items[i];
      if(item.gChk){
        couponGrid.removeAt(i);
      }
    }
  });

  // 쿠폰 저장 버튼 클릭
  $("#btnCouponSave").click(function(){
    for(var i = 0; i < couponGrid.collectionView.itemCount; i ++) {
      console.log("coupnDcFg : "+ couponGrid.collectionView.items[i].coupnDcFg);

      // 할인구분 금액관련일때 할인액 입력 필요
      if((couponGrid.collectionView.items[i].coupnDcFg == "1" || couponGrid.collectionView.items[i].coupnDcFg == "2"|| couponGrid.collectionView.items[i].coupnDcFg == "6")
          && (couponGrid.collectionView.items[i].coupnDcRate == "" )) {
        s_alert.pop(messages["coupon.require.coupnDcRate"]);
        return;
      }
      // 할인구분 %관련일때 할인율 입력 필요
      else if((couponGrid.collectionView.items[i].coupnDcFg == "3" || couponGrid.collectionView.items[i].coupnDcFg == "4" )
          && (couponGrid.collectionView.items[i].coupnDcAmt == "" )) {
        s_alert.pop(messages["coupon.require.coupnDcAmt"]);
        return;
      }
      //TODO 5번 케이스는 어떻게 처리??
    }

    var paramArr = new Array();

    for(var i=0; i<couponGrid.collectionView.itemsEdited.length; i++){
      couponGrid.collectionView.itemsEdited[i].status = "U";
      paramArr.push(couponGrid.collectionView.itemsEdited[i]);
    }
    for(var i=0; i<couponGrid.collectionView.itemsAdded.length; i++){
      couponGrid.collectionView.itemsAdded[i].status = "I";
      couponGrid.collectionView.itemsAdded[i].payClassCd = selectedClass.payClassCd;
      paramArr.push(couponGrid.collectionView.itemsAdded[i]);
    }
    for(var i=0; i<couponGrid.collectionView.itemsRemoved.length; i++){
      couponGrid.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(couponGrid.collectionView.itemsRemoved[i]);
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    console.log(paramArr);

    $.postJSONArray(baseUrl + "/class/saveCouponList.sb", paramArr, function(result) {
      s_alert.pop(messages["cmm.saveSucc"]);
      couponGrid.collectionView.clearChanges();
    },
    function(result) {
      s_alert.pop(result.data.msg);
    });
  });

  //TODO 탭 클릭
  $("#couponProdTab").click(function(){
    location.href = "/base/pay/coupon/prod/couponProdView.sb";
  });

  $("#couponStoreTab").click(function(){
    location.href = "/base/pay/coupon/store/couponStoreView.sb";
  });

});
