<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div id="dimTrtMnt" class="fullDimmed" style="display:none;"></div>
<div id="layerTrtMnt" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w800">
      <%-- 타이틀 --%>
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <%-- 거래처등록, 취급상품 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 거래처등록 탭 --%>
            <li><a id="vendrTab" href="#"><s:message code="vendr.regst" /></a></li>
            <%-- 취급상품 탭 --%>
            <li><a id="trtMntTab" href="#" class="on"><s:message code="vendr.trtMnt" /></a></li>
          </ul>
        </div>

        <table class="tblType01">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%-- 상품코드 --%>
              <th>
                <s:message code="vendr.prodCd" />
              </th>
              <td>
                <div class="sb-select">
                  <div id="sProdCd"></div>
                </div>
              </td>
              <%-- 상품명 --%>
              <th>
                <s:message code="vendr.prodNm" />
              </th>
              <td>
                <div class="sb-select">
                  <div id="sProdNm"></div>
                </div>
              </td>
            </tr>
            <tr>
              <%-- 분류조회 --%>
              <th>
                <s:message code="vendr.productClass" />
              </th>
              <td>
                <div class="sb-select">
                  <div id="sProductClass"></div>
                </div>
              </td>
              <th>&nbsp;</th>
              <td>&nbsp;</td>
            </tr>
          </tbody>
        </table>
        <%-- 조회버튼 --%>
        <div class="mt10 pdb20 oh bb">
          <button class="btn_blue fr" id="searchTrt">
            <s:message code="cmm.search" />
          </button>
        </div>
        <div class="wj-TblWrap mt20">
          <!--left-->
          <div class="w50 fl">
            <div class="wj-TblWrapBr mr10 pd10" style="height:200px;">
              <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:300px; position:relative;">
                <s:message code="vendr.trtmnt.yes" />
                <span class="fr" style="font-size:1em;" id="btnDelete">
                  <a href="#" class="btn_grayS"><s:message code="cmm.delete" /></a>
                </span>
              </h3>
              <div style="height:410px;" id="theGrid1"></div>
            </div>
          </div>
          <!--//left-->

          <!--right-->
          <div class="w50 fr">
            <div class="wj-TblWrapBr ml10 pd10" style="height:200px;">
              <h3 class="lh30" style="font-size:0.75em; border:1px solid #ccc; background:#e8e8e8; padding:5px 15px; color:#222; min-width:300px; position:relative;">
                <s:message code="vendr.trtmnt.no" />
                <span class="fr" style="font-size:1em;" id="btnRegist" >
                  <a href="#" class="btn_grayS"><s:message code="vendr.regist" /></a>
                </span>
              </h3>
              <div style="height:350px;" id="theGrid2"></div>
            </div>
          </div>
          <!--//right-->
        </div>
        <!--//2단-->
      </div>
    </div>
  </div>
</div>
<script>
  var selectData = "";
  var selectCnt = 0;

  var sVendrCd       = "";
  var sProdCd        = wcombo.genInput("#sProdCd");
  var sProdNm        = wcombo.genInput("#sProdNm");
  var sProductClass  = wcombo.genInput("#sProductClass");

  <%-- CollectionView --%>
  var hData =
    [
      {binding:"gChk", header:"<s:message code='func.chk' />", allowMerging:true,dataType:wijmo.DataType.Boolean,width:40},
      {binding:"prodCd", header:"<s:message code='vendr.prodCd' />",width:"*"},
      {binding:"prodNm", header:"<s:message code='vendr.prodNm' />",width:"*"},
      {binding:"splyUprc", header:"<s:message code='vendr.splyUprc' />",width:"*"}
    ];

  var grid1 = wgrid.genGrid("#theGrid1", hData);
  var grid2 = wgrid.genGrid("#theGrid2", hData);

  grid1.isReadOnly    = false;
  grid2.isReadOnly    = false;


  <%-- 조회버튼 --%>
  function searchTrtMnt() {
    var param = {};

    param.vendrCd = sVendrCd;
    param.prodCd  = sProdCd.text;
    param.prodNm  = sProdNm.text;

    $.postJSON("/base/prod/vendr/trtMnt/list.sb", param, function(result) {
        var list1 = result.data.dateSelList1;
        var list2 = result.data.dateSelList2;

        grid1.itemsSource = list1;
        grid2.itemsSource = list2;
    });
  }
  <%-- 등록버튼 --%>
  $("#btnRegist").click(function( e ){

    <%-- 거래처코드를 확인해주세요. --%>
    var msg = "<s:message code='vendr.vendrCd'/> <s:message code='cmm.require.check'/>";
    if(sVendrCd == "") {
      s_alert.pop(msg);
      return;
    }

    var paramArr = new Array();
    for(var i = 0; i < grid2.collectionView.itemCount; i++ ){
      var item = grid2.collectionView.items[i];
      if(item.gChk){
        item.vendrCd = sVendrCd;
        paramArr.push(item);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop("<s:message code='cmm.not.select'/>");
      return;
    }

    $.postJSONArray("/base/prod/vendr/trtMnt/save.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        $("#searchTrt").click();
      },
      function(result) {
        s_alert.pop(result.message);
      });
  });


  <%-- 삭제버튼 --%>
  $("#btnDelete").click(function( e ){

    <%-- 거래처코드를 확인해주세요. --%>
    var msg = "<s:message code='vendr.vendrCd'/> <s:message code='cmm.require.check'/>";
    if(sVendrCd == "") {
      s_alert.pop(msg);
      return;
    }

    var paramArr = new Array();
    for(var i = 0; i < grid1.collectionView.itemCount; i++ ){
      var item = grid1.collectionView.items[i];
      if(item.gChk){
        item.vendrCd = sVendrCd;
        paramArr.push(item);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop("<s:message code='cmm.not.select'/>");
      return;
    }

    $.postJSONArray("/base/prod/vendr/trtMnt/delete.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        $("#searchTrt").click();
      },
      function(result) {
        s_alert.pop(result.message);
      });
  });

  <%-- 리스트 조회 --%>
  $("#searchTrt").click(function( e ){
      searchTrtMnt();
  });

  <%-- 거래처등록 탭 클릭 --%>
  $("#layerTrtMnt #vendrTab").click(function(e){
    openRegistLayer(sVendrCd);
    hideTrtMnt();
    prodInit();
  });

  <%-- 취급상품 노출 셋팅 --%>
  function showTrtMntSet(item){
    sVendrCd = item;
    hideVendr();
    searchTrtMnt();

    $("#layerTrtMnt #popTitle").text("<s:message code='vendr.layer.trtmnt.title' />");

    $("#dimTrtMnt").show();
    $("#layerTrtMnt").show();
  }

  <%-- 취급상품 레이어 닫기 --%>
  $("#layerTrtMnt .btn_close").click(function(){
      infoInit();
      hideTrtMnt();
  });

  <%-- 취급상품 숨기기 --%>
  function hideTrtMnt(){
    $("#dimTrtMnt").hide();
    $("#layerTrtMnt").hide();
  }

  <%--취급상품 탭 초기화--%>
  function prodInit() {
    var inputArr = [
        sProdCd, sProdNm, sProductClass
    ].forEach(function(element){element.text="";});
  }

</script>
