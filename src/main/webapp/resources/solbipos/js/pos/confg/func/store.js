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

function showStoreLayer() {
  $("#storeDim").show();
  $("#storeLayer").show();

  $("#storeLayerSubTitle").text("[" + selectedFnkey.fnkeyNo + "]  " + selectedFnkey.fnkeyNm);

}
$(document).ready(function(){

  var srchClsFgCb     = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFgCb = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  var clsFgDataMap        = new wijmo.grid.DataMap(clsFg, 'value', 'name');
  var sysStatFgDataMap    = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

  var regStoreGridHeader =
    [
      {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:messages["func.hqOfficeCd"], width:"*", isReadOnly:true, visible:false},
      {binding:"hqOfficeNm", header:messages["func.hqOfficeNm"], width:"*", isReadOnly:true, visible:false},
      {binding:"storeCd", header:messages["func.storeCd"], maxLength:3, width:"*", isReadOnly:true},
      {binding:"storeNm", header:messages["func.storeNm"], maxLength:20, width:"*", isReadOnly:true},
      // {binding:"clsFg", header:messages["func.clsFg"], dataMap:clsFgDataMap, width:"*", isReadOnly:true},
      // {binding:"sysStatFg", header:messages["func.sysStatFg"], dataMap:sysStatFgDataMap, width:"*", isReadOnly:true}
    ];

  var noRegStoreGridHeader =
      [
        {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
        {binding:"hqOfficeCd", header:messages["func.hqOfficeCd"], width:"*", isReadOnly:true, visible:false},
        {binding:"hqOfficeNm", header:messages["func.hqOfficeNm"], width:"*", isReadOnly:true, visible:false},
        {binding:"storeCd", header:messages["func.storeCd"], maxLength:3, width:"*", isReadOnly:true},
        {binding:"storeNm", header:messages["func.storeNm"], maxLength:20, width:"*", isReadOnly:true},
        // {binding:"clsFg", header:messages["func.clsFg"], dataMap:clsFgDataMap, width:"*", isReadOnly:true},
        // {binding:"sysStatFg", header:messages["func.sysStatFg"], dataMap:sysStatFgDataMap, width:"*", isReadOnly:true}
      ];

  var regStoreGrid = wgrid.genGrid("#regStoreGrid", regStoreGridHeader);
  var noRegStoreGrid = wgrid.genGrid("#noRegStoreGrid", noRegStoreGridHeader);

  regStoreGrid.itemsSource =  new wijmo.collections.CollectionView();
  noRegStoreGrid.itemsSource = new wijmo.collections.CollectionView();

  regStoreGrid.isReadOnly = false;
  noRegStoreGrid.isReadOnly = false;

  // 매장 조회 버튼 클릭
  $("#btnStoreSearch").click(function(){

    $("#storeLayerSubTitle").text("[" + selectedFnkey.fnkeyNo + "]  " + selectedFnkey.fnkeyNm);

    var param = {};
    param.fnkeyFg = selectedFnkey.fnkeyFg;
    param.fnkeyNo = selectedFnkey.fnkeyNo;
    param.hqOfficeCd = $("#srchHqOfficeCd").val();
    param.hqOfficeNm = $("#srchHqOfficeNm").val();;
    param.storeCd = $("#srchStoreCd").val();;
    param.storeNm = $("#srchStoreNm").val();;
    param.clsFg = srchClsFgCb.selectedValue;
    param.sysStatFg = srchSysStatFgCb.selectedValue;

    $.postJSON(baseUrl+"getFuncStoreList.sb", param,
      function(result) {
        console.log(result);

        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }

        var regStoreList = result.data.list.regStoreList;
        var noRegStoreList = result.data.list.noRegStoreList;

        regStoreGrid.itemsSource = new wijmo.collections.CollectionView(regStoreList);
        noRegStoreGrid.itemsSource = new wijmo.collections.CollectionView(noRegStoreList);

        regStoreGrid.itemsSource.trackChanges = true;
        noRegStoreGrid.itemsSource.trackChanges = true;

      },
      function(){
        s_alert.pop("Ajax Fail");
      });
  });


  // 등록 버튼 클릭
  $("#btnStoreReg").click(function(){

    var paramArr = new Array();

    for(var i=0; i<noRegStoreGrid.itemsSource.items.length; i++){
      if(noRegStoreGrid.itemsSource.items[i].gChk){
        noRegStoreGrid.itemsSource.items[i].status = "I";
        noRegStoreGrid.itemsSource.items[i].fnkeyFg = selectedFnkey.fnkeyFg;
        noRegStoreGrid.itemsSource.items[i].fnkeyNo = selectedFnkey.fnkeyNo;
        paramArr.push(noRegStoreGrid.itemsSource.items[i]);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }
    console.log(paramArr);

    $.postJSONArray(baseUrl + "saveFuncStore.sb", paramArr,
        function(result) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $("#btnStoreSearch").click();
        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
    );
  });


  // 삭제 버튼 클릭
  $("#btnStoreDel").click(function(){

    var paramArr = new Array();

    for(var i=0; i<regStoreGrid.itemsSource.items.length; i++){
      if(regStoreGrid.itemsSource.items[i].gChk){
        regStoreGrid.itemsSource.items[i].status = "D";
        paramArr.push(regStoreGrid.itemsSource.items[i]);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }
    console.log(paramArr);

    $.postJSONArray(baseUrl + "saveFuncStore.sb", paramArr,
        function(result) {
          s_alert.pop(messages["cmm.saveSucc"]);
          $("#btnStoreSearch").click();

        },
        function(result) {
          s_alert.pop(result.data.msg);
        }
    );
  });


  $("#storeLayer .btn_close").click(function(){
    $("#storeDim").hide();
    $("#storeLayer").hide();

    regStoreGrid.itemsSource =  new wijmo.collections.CollectionView([]);
    noRegStoreGrid.itemsSource = new wijmo.collections.CollectionView([]);

  });


});
