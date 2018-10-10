/****************************************************************
 *
 * 파일명 : store.js
 * 설  명 : 매장등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.04     김지은      1.0
 *
 * **************************************************************/

$(document).ready(function(){

  var regStoreGridHeader =
      [
        {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
        {binding:"hqOfficeCd", header:messages["regist.membr.hqOfficeCd"], width:"*", isReadOnly:true, visible:false},
        {binding:"creditStoreCd", header:messages["regist.membr.storeCd"], maxLength:3, width:"*", isReadOnly:true},
        {binding:"creditStoreNm", header:messages["regist.membr.storeNm"], maxLength:20, width:"*", isReadOnly:true}
      ];

  var noRegStoreGridHeader =
      [
        {binding:"gChk", header:messages["cmm.chk"], dataType:wijmo.DataType.Boolean, width:40},
        {binding:"hqOfficeCd", header:messages["regist.membr.hqOfficeCd"], width:"*", isReadOnly:true, visible:false},
        {binding:"creditStoreCd", header:messages["regist.membr.storeCd"], maxLength:3, width:"*", isReadOnly:true},
        {binding:"creditStoreNm", header:messages["regist.membr.storeNm"], maxLength:20, width:"*", isReadOnly:true}
      ];

  var regStoreGrid = wgrid.genGrid("#regStoreGrid", regStoreGridHeader);
  var noRegStoreGrid = wgrid.genGrid("#noRegStoreGrid", noRegStoreGridHeader);

  regStoreGrid.itemsSource =  new wijmo.collections.CollectionView([]);
  noRegStoreGrid.itemsSource = new wijmo.collections.CollectionView([]);

  regStoreGrid.isReadOnly = false;
  noRegStoreGrid.isReadOnly = false;

  // 매장 조회 버튼 클릭
  $("#btnStoreSearch").click(function(){

    $("#storeLayerSubTitle").text("[" + selectData.membrNo + "]  " + selectData.membrNm);

    var params = {};
    params.hqOfficeCd = selectData.membrOrgnCd;
    params.memberNo = selectData.membrNo;
    params.storeCd = $("#srchStoreCd").val();
    params.storeNm = $("#srchStoreNm").val();

    $.postJSON("/membr/info/view/credit/getCreditStoreList.sb", params,
        function(result) {

          if(result.status === "FAIL") {
            s_alert.pop(result.message);

            regStoreGrid.itemsSource = new wijmo.collections.CollectionView([]);
            noRegStoreGrid.itemsSource = new wijmo.collections.CollectionView([]);

            return;
          }

          var regStoreList = result.data.regStoreList;
          var noRegStoreList = result.data.noRegStoreList;

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
        noRegStoreGrid.itemsSource.items[i].memberNo = selectData.membrNo;
        paramArr.push(noRegStoreGrid.itemsSource.items[i]);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/membr/info/view/credit/saveCreditStore.sb", paramArr,
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
        regStoreGrid.itemsSource.items[i].memberNo = selectData.membrNo;
        paramArr.push(regStoreGrid.itemsSource.items[i]);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop(messages["cmm.not.modify"]);
      return;
    }

    $.postJSONArray("/membr/info/view/credit/saveCreditStore.sb", paramArr,
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

function showStoreLayer() {
  $("#storeDim").show();
  $("#storeLayer").show();

  $("#storeLayerSubTitle").text("[" + selectData.membrNo + "]  " + selectData.membrNm);
}
