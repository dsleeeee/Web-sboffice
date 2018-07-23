<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메뉴권한 레이어 --%>

<div id="dim2" class="fullDimmed" style="display:none;"></div>
<div id="munuAuthLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w600">
      <p id="popTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <%-- 본사정보, 메뉴권한, 코드자리수, 환경설정, 브랜드관리 탭 --%>
        <div class="tabType1">
          <ul>
            <li><a id="" href="javascript:;"><s:message code="hqManage.hqInfo" /></a></li>
            <li><a id="menuSettingTab" href="javascript:;" class="on"><s:message code="hqManage.menuSetting" /></a></li>
          </ul>
        </div>

        <div class="tabType2 mt20">
          <ul>
            <%-- 웹사이트 메뉴 --%>
            <li><a id="webMenu" href="javascript:;" class="on"><s:message code="hqManage.webMenu" /></a></li>
            <%-- 모바일 메뉴 --%>
            <li><a id="mobMenu" href="javascript:;"><s:message code="hqManage.mobMenu" /></a></li>
          </ul>
        </div>
          
        <div id="webArea">
          <%-- 권한복사 영역 --%>
          <table class="tblType01 moreDark mb10 mt10">
            <colgroup>
              <col class="w20" />
              <col class="w55" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 메뉴권한복사 --%>
                <th><s:message code="hqManage.copy.authorExcept" /></th>
                <td colspan="3">
                  <div class="sb-select fl w40 mr10">
                    <span id="authCombo"></span>
                  </div>
                  <%-- 메뉴권한복사 버튼  --%>
                  <div class="fl">
                    <a href="javascript:;" class="btn_grayS" id="btnCopyAuth"><s:message code="hqManage.copy.auth" /></a>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <%-- 사용가능한 메뉴 --%>
          <div class="oh mt10">
            <div class="wj-TblWrap mr10" style="height:200px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="hqManage.usable.menu" /> </span>
                <span class="fr"><a id="btnAddMenu" href="javascript:;" class="btn_grayS2"><s:message code="cmm.save" /></a></span>
              </div>
              <%-- 위즈모 --%>
              <div id="avlblMenuGrid" style="height:160px;"></div> 
            </div>
          </div>
          
          <%-- 사용중인 메뉴 --%>
          <div class="oh mt10">
            <div class="wj-TblWrap mr10" style="height:200px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="hqManage.inUse.menu" /> </span>
                <span class="fr"><a id="btnRemoveMenu" href="javascript:;" class="btn_grayS2"><s:message code="cmm.delete" /></a></span>
              </div>
              <%-- 위즈모 --%>
              <div id="beUseMenuGrid" style="height:160px;"></div> 
            </div>
          </div>
          </div>
      </div>
    </div>
  </div>
</div>
<script>


$(document).ready(function() {
  
  
  
  
  
});

  /*
  var avlblMenuGridData = [
    {binding:"resrceCdLarge", header:"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false},
    {binding:"resrceNmLarge", header:"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:80},
    {binding:"menuChkMid", header:"<s:message code='hqManage.chk.menu' />", width:70,dataType:wijmo.DataType.Boolean, isReadOnly:false},
    {binding:"resrceCdMid", header:"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false},
    {binding:"resrceNmMid", header:"<s:message code='hqManage.mMenuNm' />", allowMerging:true},
    {binding:"menuChkSmall", header:"<s:message code='hqManage.chk.menu' />", width:70, dataType:wijmo.DataType.Boolean, isReadOnly:false},
    {binding:"resrceCdSmall", header:"<s:message code='hqManage.sMenuCd' />", visible:false},
    {binding:"resrceNmSmall", header:"<s:message code='hqManage.sMenuNm' />"}
  ];

  var beUseMenuGridData = [
    {binding:"resrceCdLarge", header:"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false},
    {binding:"resrceNmLarge", header:"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:80},
    {binding:"menuChkMid", header:"<s:message code='hqManage.chk.menu' />", width:70, dataType:wijmo.DataType.Boolean, isReadOnly:false},
    {binding:"resrceCdMid", header:"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false},
    {binding:"resrceNmMid", header:"<s:message code='hqManage.mMenuNm' />", allowMerging:true},
    {binding:"menuChkSmall", header:"<s:message code='hqManage.chk.menu' />", width:70, dataType:wijmo.DataType.Boolean, isReadOnly:false},
    {binding:"resrceCdSmall", header:"<s:message code='hqManage.sMenuCd' />", visible:false},
    {binding:"resrceNmSmall", header:"<s:message code='hqManage.sMenuNm' />"}
  ];
  */
  
  
  var avlblMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", "allowMerging":"true", "visible":"false", "width":"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", "allowMerging":"true", "width":"*"},
    {"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", "dataType":"wijmo.DataType.Boolean", "isReadOnly":"false", "width":"*"},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", "allowMerging":"true", "visible":"false", "width":"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", "allowMerging":"true", "width":"*"},
    {"binding":"menuChkSmall", "header":"<s:message code='hqManage.chk.menu' />", "dataType":"wijmo.DataType.Boolean", "isReadOnly":"false", "width":"*"},
    {"binding":"resrceCdSmall", "header":"<s:message code='hqManage.sMenuCd' />", "visible":"false", "width":"*"},
    {"binding":"resrceNmSmall", "header":"<s:message code='hqManage.sMenuNm' />", "width":"*"}
  ];

  var beUseMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", "allowMerging":"true", "visible":"false", "width":"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", "allowMerging":"true", "width":"*"},
    {"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", "dataType":"wijmo.DataType.Boolean", "isReadOnly":"false", "width":"*"},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", "allowMerging":"true", "visible":"false", "width":"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", "allowMerging":"true", "width":"*"},
    {"binding":"menuChkSmall", "header":"<s:message code='hqManage.chk.menu' />", "dataType":"wijmo.DataType.Boolean", "isReadOnly":"false", "width":"*"},
    {"binding":"resrceCdSmall", "header":"<s:message code='hqManage.sMenuCd' />", "visible":"false", "width":"*"},
    {"binding":"resrceNmSmall", "header":"<s:message code='hqManage.sMenuNm' />", "width":"*"}
  ];
  
  //var avlblMenuGrid = wgrid.genGrid("#avlblMenuGrid", avlblMenuGridData, "${menuCd}", 2, ${clo.getColumnLayout(2)});
  //var beUseMenuGrid = wgrid.genGrid("#beUseMenuGrid", beUseMenuGridData, "${menuCd}", 3, ${clo.getColumnLayout(3)});
  
  var avlblMenuGrid         = wgrid.genGrid("#avlblMenuGrid", avlblMenuGridData, "${menuCd}", 2, ${clo.getColumnLayout(2)});
  var beUseMenuGrid         = wgrid.genGrid("#beUseMenuGrid", beUseMenuGridData, "${menuCd}", 3, ${clo.getColumnLayout(3)});
  
  var authCombo  = wcombo.genCommonBox("#authCombo", null);

  avlblMenuGrid.allowMerging    = wijmo.grid.AllowMerging.All;
  beUseMenuGrid.allowMerging    = wijmo.grid.AllowMerging.All;
  
  <%-- 체크박스 초기화 --%>
  avlblMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
      if( col.binding == "menuChkSmall") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkSmall == true || item.menuChkSmall == "Y" ? 'checked' : '') + '>';
      }
    }
  });
    
  beUseMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
      if( col.binding == "menuChkSmall") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkSmall == true || item.menuChkSmall == "Y" ? 'checked' : '') + '>';
      }
    }
  });
  
  <%-- 체크박스 핸들러 --%>
  avlblMenuGrid.addEventListener(avlblMenuGrid.hostElement, 'mousedown', function(e) {
    var ht = avlblMenuGrid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "menuChkMid" || col.binding == "menuChkSmall") {
        avlblMenuGrid.beginUpdate();
        if(avlblMenuGrid.cells.getCellData(ht.row, ht.col, true)){
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        avlblMenuGrid.endUpdate();
      }
    }
  });

  beUseMenuGrid.addEventListener(beUseMenuGrid.hostElement, 'mousedown', function(e) {
    var ht = beUseMenuGrid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "menuChkMid" || col.binding == "menuChkSmall") {
        beUseMenuGrid.beginUpdate();
        if(beUseMenuGrid.cells.getCellData(ht.row, ht.col, true)){
          beUseMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          beUseMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        beUseMenuGrid.endUpdate();
        console.log(beUseMenuGrid.cells.getCellData(ht.row, ht.col, true))
      }
    }
  });

  <%-- 전체 체크가능한 체크박스 생성 --%>
  avlblMenuGrid.formatItem.addHandler(function(s, e) { //TODO 왜 체크가 안되는것인가 //TODO checkbox merge
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' class='wj-cell-check'>";
      }
    }
  });
  
  beUseMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' class='wj-cell-check'>";
      }
    }
  });
  
  <%-- 상세정보 팝업 열기 --%>
  function openAuthLayer() {

    $("#munuAuthLayer #popTitle").text("["+ selectedHq.hqOfficeCd +"] " + selectedHq.hqOfficeNm);

    var param = selectedHq;
    
    $.postJSON("/store/hq/hqManage/authorExcept/authHqList.sb", param, function(result) {
      console.log(result);
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      
      //authCombo.itemsSource = result.data.authHqList;
      authCombo.itemsSource = result.data.authHqList;
      avlblMenuGrid.itemsSource = result.data.avlblMenu;
      beUseMenuGrid.itemsSource = result.data.beUseMenu;
      
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 권한 복사 버튼 클릭 --%>
  $("#btnCopyAuth").click(function(e){
    var param = {};
    param.hqOfficeCd      = selectedHq.hqOfficeCd;
    param.copyHqOfficeCd  = authCombo.selectedValue;
    
    $.postJSONSave("/store/hq/hqManage/authorExcept/copyAuth.sb", JSON.stringify(param), function(result) {
      var res = result.data;
      if(res > 0) {
        s_alert.pop("<s:message code='cmm.copySucc' />");
      }
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  });
  
  <%-- 추가 버튼 클릭 --%>
  $("#btnAddMenu").click(function(e){
    
    var paramArr = new Array();
    for(var i=0; i<avlblMenuGrid.collectionView.items.length; i++){
      var avlblMenu = avlblMenuGrid.collectionView.items[i];
      if(avlblMenu.menuChkSmall || avlblMenu.menuChkMid) {
        avlblMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        avlblMenu.resrceCd = avlblMenu.resrceCdSmall;
        paramArr.push(avlblMenu);
      }
    }
    
    $.postJSONArray("/store/hq/hqManage/authorExcept/addAuth.sb", paramArr, function(result) {
      var res = result.data;
      if(res > 0) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        openAuthLayer();
      }
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
    
  });

  <%-- 삭제 버튼 클릭 --%>
  $("#btnRemoveMenu").click(function(e){
    var paramArr = new Array();
    for(var i=0; i<beUseMenuGrid.collectionView.items.length; i++){
      var beUseMenu = beUseMenuGrid.collectionView.items[i];
      if(beUseMenu.menuChkSmall || beUseMenu.menuChkMid) {
        beUseMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        beUseMenu.resrceCd = beUseMenu.resrceCdSmall;
        paramArr.push(beUseMenu);
      }
    }
    
    $.postJSONArray("/store/hq/hqManage/authorExcept/removeAuth.sb", JSON.stringify(paramArr), function(result) {
      var res = result.data;
      if(res > 0) {
        s_alert.pop("<s:message code='cmm.delSucc' />");
        openAuthLayer();
      }
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
     
  });

  <%-- 탭 클릭 --%>
  $(".tabType1 ul li #hqInfoTab").click(function(){
    changeTab2();
  });
  
  function changeTab2(pageId){
    $("#hqDtlLayer").show();
    $("#dim1").show();
    
    $("#munuAuthLayer").hide();
    $("#dim2").hide();
    
    openDtlLayer(selectedHq);
  }
  
  <%-- 모바일 메뉴 클릭--%>
  $("#mobMenu").click(function(e){
    s_alert.pop("<s:message code='cmm.menu.preparing' />");
  });
  
  <%-- 닫기 버튼 클릭 --%>
  $("#munuAuthLayer .btn_close").click(function(e){
    $("#munuAuthLayer").hide();
    $("#dim2").hide();
  });

</script>