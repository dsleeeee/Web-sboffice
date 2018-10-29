<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장추가 레이어 --%>
<div id="dim2" class="fullDimmed" style="display:none;" ></div>
<div id="addStorelLayer" class="layer"  style="display:none; z-index:9999;">
  <div class="layer_inner">
    <div class="title w600px">
      <p class="tit"><s:message code="verManage.add.store" /></p>
      <a href="#" id="btnCloseSLayer" class="btn_close"></a>
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 본사코드 --%>
                <th><s:message code="verManage.store.hqOfficeCd" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_hqOfficeCd"></div>
                  </div>
                </td>
                <%-- 본사명 --%>
                <th><s:message code="verManage.store.hqOfficeNm" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_hqOfficeNm"></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 매장코드 --%>
                <th><s:message code="verManage.store.storeCd" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_storeCd"></div>
                  </div>
                </td>
                <%-- 매장명 --%>
                <th><s:message code="verManage.store.storeNm" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_storeNm"></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 최종버전 --%>
                <th><s:message code="verManage.store.lastVer" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_lastVer"></div>
                  </div>
                </td>
                <%-- 벤사 --%>
                <th><s:message code="verManage.store.van" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_vanCd"></div>
                  </div>
                </td>
              </tr>
              <tr>
                <%-- 용도 --%>
                <th><s:message code="verManage.store.clsFg" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_clsFg"></div>
                  </div>
                </td>
                <%-- 상태 --%>
                <th><s:message code="verManage.store.sysStatFg" /></th>
                <td>
                  <div class="sb-select">
                    <div id="s_sysStatFg"></div>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="mt10 tr">
            <%-- 조회 --%>
            <button id="btnSearchStore" class="btn_skyblue"><s:message code="cmm.search" /></button>
          </div>
        </div>

        <div class="mt20 oh sb-select dkbr">
          <%-- 페이지 스케일  --%>
          <div id="listScaleBox2" class="w150px fl"></div>
        </div>

        <%-- 위즈모 테이블 --%>
        <div>
          <div id="theGrid3" class="mt10 mb20" style="height:250px;"></div>
        </div>
        <%-- 페이지 리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="page3" data-size="10">
          </ul>
        </div>
      </div>
      <%-- 저장 --%>
      <div class="btnSet">
        <span><a href="#" id="btnSaveStore" class="btn_blue"><s:message code="cmm.save" /></a></span>
      </div>
    </div>
  </div>
</div>

<script>

  <%-- 검색조건 및 dataMap 조회 --%>
  var hqOfficeCd  = wcombo.genInput("#s_hqOfficeCd");
  var hqOfficeNm  = wcombo.genInput("#s_hqOfficeNm");
  var storeCd     = wcombo.genInput("#s_storeCd");
  var storeNm     = wcombo.genInput("#s_storeNm");
  var lastVer     = wcombo.genInput("#s_lastVer");
  var vanCd       = wcombo.genInput("#s_vanCd");  //TODO combobox로 바꿔야함

  var clsFg       = ${ccu.getCommCode("001")};
  var sysStatFgCb = wcombo.genCommonBox("#s_sysStatFg", ${ccu.getCommCode("009")});
  var clsFgCb     = wcombo.genCommonBox("#s_clsFg", clsFg);
  var clsFgDataMap= new wijmo.grid.DataMap(clsFg, 'value', 'name');

  <%-- 적용매장 Header --%>
  var hData3 =
    [
      {binding:"gChk", header:"<s:message code='verManage.store.chk' />", dataType:wijmo.DataType.Boolean, width:40},
      {binding:"hqOfficeCd", header:"<s:message code='verManage.store.hqOfficeCd' />", isReadOnly:true},
      {binding:"hqOfficeNm", header:"<s:message code='verManage.store.hqOfficeNm' />", isReadOnly:true},
      {binding:"storeCd", header:"<s:message code='verManage.store.storeCd' />", isReadOnly:true},
      {binding:"storeNm", header:"<s:message code='verManage.store.storeNm' />", isReadOnly:true},
      {binding:"lastVer", header:"<s:message code='verManage.store.lastVer' />", isReadOnly:true},
      {binding:"clsFg", header:"<s:message code='verManage.store.clsFg' />", dataMap:clsFgDataMap, isReadOnly:true},
      {binding:"sysStatFg", header:"<s:message code='verManage.store.sysStatFg' />", dataMap:sysStatFgDataMap, isReadOnly:true}
    ];

  <%-- 매장조회 그리드 생성 --%>
  var grid3 = wgrid.genGrid("#theGrid3", hData3, "${menuCd}", 3, ${clo.getColumnLayout(3)});
  var ldata2         = ${ccu.getListScale()};
  var listScaleBox2  = wcombo.genCommonBox("#listScaleBox2", ldata2);

  grid3.isReadOnly  = false;
  grid3.itemsSource = new wijmo.collections.CollectionView();

  <%-- 매장 추가 레이어 열기--%>
  function showAddStorelLayer(){
    $("#addStorelLayer").show();
    $("#dim2").show();

    grid3.itemsSource = [];

    hqOfficeCd.text           = "";
    hqOfficeNm.text           = "";
    storeCd.text              = "";
    storeNm.text              = "";
    lastVer.text              = "";
    vanCd.text                = "";
    clsFgCb.selectedValue     = "";
    sysStatFgCb.selectedValue = "";
  }

  <%-- 매장 추가 레이어 버튼 클릭 --%>
  $("#btnCloseSLayer").click(function(){
    closeStoreLayer();
  });

  <%-- 매장 추가 레이어 닫기 --%>
  function closeStoreLayer(){
    $("#addStorelLayer").hide();
    $("#dim2").hide();
  }

  <%-- 페이징 --%>
  $(document).on("click", ".page3", function() {
    searchStore($(this).data("value"));
  });

  <%-- 조회 버튼 클릭 --%>
  $("#btnSearchStore").click(function(){
    searchStore(1);
  });

  <%-- 매장 조회--%>
  function searchStore(index) {

    var param = {};

    param.verSerNo    = selectVerSerNo;
    param.hqOfficeCd  = hqOfficeCd.text;
    param.hqOfficeNm  = hqOfficeNm.text;
    param.storeCd     = storeCd.text;
    param.storeNm     = storeNm.text;
    param.vanCd       = vanCd.text;
    param.clsFg       = clsFgCb.selectedValue;
    param.sysStatFg   = sysStatFgCb.selectedValue;
    param.lastVer     = lastVer.text;
    param.curr        = index;
    param.listScale   = listScaleBox2.selectedValue;

    $.postJSON("/pos/confg/verManage/applcStore/srchStoreList.sb", param, function(result) {
      var list = result.data.list;

      grid3.itemsSource = list;
      grid3.itemsSource.trackChanges = true;
      grid3.itemsSource.canSort = true;

      if(list.length === undefined || list.length == 0) {
        s_alert.pop(result.message);
        return;
      }
      page.make("#page3", result.data.page.curr, result.data.page.totalPage);
    },
      function (result) {
        s_alert.pop(result.message);

      }
    );
  }

  <%-- 저장버튼 클릭 --%>
  $("#btnSaveStore").click(function(){
    var paramArr = [];
    for(var i = 0; i < grid3.collectionView.itemCount; i++ ){
      var item = grid3.collectionView.items[i];
      if(item.gChk){
        item.verSerNo = selectVerSerNo;
        paramArr.push(item);
      }
    }

    if(paramArr.length <= 0) {
      s_alert.pop("<s:message code='cmm.not.select'/>");
      return;
    }

    $.postJSONArray("/pos/confg/verManage/applcStore/regist.sb", paramArr, function(result) {
      s_alert.pop("<s:message code='cmm.saveSucc' />");
      grid3.collectionView.clearChanges();
      closeStoreLayer();
      $("#storeInfoTab").click();
    },
    function(result) {
      s_alert.pop(result.message);
    });
  });

</script>
