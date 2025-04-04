<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메뉴권한 레이어 --%>

<div id="munuAuthDim" class="fullDimmed" style="display:none;"></div>
<div id="munuAuthLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w600px">
      <p id="popTitle" class="tit"></p>
      <a href="#" class="btn_close"></a>
      <div class="con">
        <%-- 본사정보, 메뉴권한, 코드자리수, 환경설정, 브랜드관리 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 상세정보 탭 --%>
            <li><a id="hqInfoTab" href="#"><s:message code="hqManage.hqInfo" /></a></li>
            <%-- 환경설정 탭 --%>
            <li><a id="envSettingTab" href="#"><s:message code="hqManage.envSetting" /></a></li>
            <%-- 메뉴관리 탭  --%>
            <li><a id="menuSettingTab" href="#" class="on"><s:message code="hqManage.menuSetting" /></a></li>
          </ul>
        </div>

        <div class="tabType2 mt20">
          <ul>
            <%-- 웹사이트 메뉴 --%>
            <li><a id="webMenu" href="#" class="on"><s:message code="hqManage.webMenu" /></a></li>
            <%-- 모바일 메뉴 --%>
            <li><a id="mobMenu" href="#"><s:message code="hqManage.mobMenu" /></a></li>
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
                <div class="sb-select fl w60 mr10">
                  <span id="authCombo"></span>
                </div>
                <%-- 메뉴권한복사 버튼  --%>
                <div class="fl">
                  <a href="#" class="btn_grayS" id="btnCopyAuth"><s:message code="hqManage.copy.auth" /></a>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
          <%-- 사용 메뉴 --%>
          <div class="oh mt10" style="padding-bottom: 25px;">
            <div class="wj-TblWrap mr10" style="height:200px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="hqManage.use.menu" /> </span>
                <%-- 미사용등록 버튼 --%>
                <span class="fr"><a id="btnRemoveMenu" href="#" class="btn_grayS2"><s:message code="hqManage.exceptReg" /></a></span>
              </div>
              <%-- 위즈모 --%>
              <div id="avlblMenuGrid" style="height:160px;"></div>
            </div>
          </div>

          <%-- 미사용 메뉴 --%>
          <div class="oh mt10">
            <div class="wj-TblWrap mr10" style="height:200px;">
              <div class="oh mb10">
                <span class="fl bk lh20 s14"><s:message code="hqManage.except.menu" /> </span>
                <%-- 사용등록 버튼 --%>
                <span class="fr"><a id="btnAddMenu" href="#" class="btn_grayS2"><s:message code="hqManage.useReg" /></a></span>
              </div>
              <%-- 위즈모 --%>
              <div id="beUseMenuGrid" style="height:160px;"></div>
            </div>
          </div>
        </div>

          <div id="mobileArea">
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
                  <div class="sb-select fl w60 mr10">
                    <span id="authMobileCombo"></span>
                  </div>
                  <%-- 메뉴권한복사 버튼  --%>
                  <div class="fl">
                    <a href="#" class="btn_grayS" id="btnCopyMobileAuth"><s:message code="hqManage.copy.auth" /></a>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
            <%-- 사용 메뉴 --%>
            <div class="oh mt10" style="padding-bottom: 25px;">
              <div class="wj-TblWrap mr10" style="height:200px;">
                <div class="oh mb10">
                  <span class="fl bk lh20 s14"><s:message code="hqManage.use.menu" /> </span>
                  <%-- 미사용등록 버튼 --%>
                  <span class="fr"><a id="btnRemoveMobileMenu" href="#" class="btn_grayS2"><s:message code="hqManage.exceptReg" /></a></span>
                </div>
                <%-- 위즈모 --%>
                <div id="avlblMobileMenuGrid" style="height:160px;"></div>
              </div>
            </div>

            <%-- 미사용 메뉴 --%>
            <div class="oh mt10">
              <div class="wj-TblWrap mr10" style="height:200px;">
                <div class="oh mb10">
                  <span class="fl bk lh20 s14"><s:message code="hqManage.except.menu" /> </span>
                  <%-- 사용등록 버튼 --%>
                  <span class="fr"><a id="btnAddMobileMenu" href="#" class="btn_grayS2"><s:message code="hqManage.useReg" /></a></span>
                </div>
                <%-- 위즈모 --%>
                <div id="beUseMobileMenuGrid" style="height:160px;"></div>
              </div>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>
<script>


  var avlblMenuGrid;
  var beUseMenuGrid;
  var authCombo;
  var avlblMobileMenuGrid;
  var beUseMobileMenuGrid;
  var authMobileCombo;

  var avlblMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:90},
    //{"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:"*"},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", allowMerging:true, width:93},
    {"binding":"menuChkSmall", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:70},
    {"binding":"resrceCdSmall", "header":"<s:message code='hqManage.sMenuCd' />", visible:false, width:"*"},
    {"binding":"resrceNmSmall", "header":"<s:message code='hqManage.sMenuNm' />", width:230}
  ];

  var beUseMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:90},
    //{"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:"*"},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", allowMerging:true, width:93},
    {"binding":"menuChkSmall", "header":"<s:message code='hqManage.chk.menu' />", dataType: wijmo.DataType.Boolean, isReadOnly:false, width:70},
    {"binding":"resrceCdSmall", "header":"<s:message code='hqManage.sMenuCd' />", visible:false, width:"*"},
    {"binding":"resrceNmSmall", "header":"<s:message code='hqManage.sMenuNm' />", width:230 }
  ];

  avlblMenuGrid = wgrid.genGrid("#avlblMenuGrid", avlblMenuGridData, "${menuCd}", 2, ${clo.getColumnLayout(2)});
  beUseMenuGrid = wgrid.genGrid("#beUseMenuGrid", beUseMenuGridData, "${menuCd}", 3, ${clo.getColumnLayout(3)});
  authCombo     = wcombo.genCommonBox("#authCombo", null);

  avlblMenuGrid.allowMerging  = wijmo.grid.AllowMerging.All;
  beUseMenuGrid.allowMerging  = wijmo.grid.AllowMerging.All;

  <%-- 체크박스 초기화 --%>
  avlblMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      /*
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
       */
      if( col.binding == "menuChkSmall") {
        e.cell.innerHTML = '<input type="checkbox" name="chkAvlbl" class="wj-cell-check"' + (item.menuChkSmall == true || item.menuChkSmall == "Y" ? 'checked' : '') + '>';
      }
    }
  });

  beUseMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      /*
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
       */
      if( col.binding == "menuChkSmall") {
        e.cell.innerHTML = '<input type="checkbox" name="chkBeUse" class="wj-cell-check"' + (item.menuChkSmall == true || item.menuChkSmall == "Y" ? 'checked' : '') + '>';
      }
    }
  });

  <%-- 체크박스 핸들러 --%>
  avlblMenuGrid.addEventListener(avlblMenuGrid.hostElement, 'mousedown', function(e) {
    var ht = avlblMenuGrid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      /*
      if( col.binding == "menuChkMid" || col.binding == "menuChkSmall") {
        avlblMenuGrid.beginUpdate();
        if(avlblMenuGrid.cells.getCellData(ht.row, ht.col, true)){
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        avlblMenuGrid.endUpdate();
      }
      */

      if(col.binding == "menuChkSmall") {
        avlblMenuGrid.beginUpdate();
        if(avlblMenuGrid.cells.getCellData(ht.row, ht.col, true) == "true"){
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, false);
        }else {
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
      /*
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
      */

      if( col.binding == "menuChkSmall") {
        beUseMenuGrid.beginUpdate();
        if(beUseMenuGrid.cells.getCellData(ht.row, ht.col, true) == "true"){
          beUseMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          beUseMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        beUseMenuGrid.endUpdate();
      }

    }
  });

  <%-- 전체 체크가능한 체크박스 생성 --%>
  /*avlblMenuGrid.formatItem.addHandler(function(s, e) { //TODO 왜 체크가 안되는것인가 //TODO checkbox merge
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' id='chkAllAvlbl' class='wj-cell-check' onclick='chkAll(1);'>";
      }
    }
  });

  beUseMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' id='chkAllBeUse' class='wj-cell-check' onclick='chkAll(2);'>";
      }
    }
  });*/


  <%-- 권한 복사 버튼 클릭 --%>
  $("#btnCopyAuth").click(function(e){

    var param = {};
    param.hqOfficeCd      = selectedHq.hqOfficeCd;
    param.copyHqOfficeCd  = authCombo.selectedValue;

    console.log(param);

    $.postJSONSave("/store/hq/hqManage/authorExcept/copyAuth.sb", param, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.copySucc' />");
                openAuthLayer();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  });

  <%-- 추가 버튼 클릭 --%>
  $("#btnAddMenu").click(function(e){

    var paramArr = [];
    for(var i=0; i<beUseMenuGrid.collectionView.items.length; i++){
      var beUseMenu = beUseMenuGrid.collectionView.items[i];
      /*
      if(beUseMenu.menuChkSmall || beUseMenu.menuChkMid) {
        beUseMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        beUseMenu.resrceCd = beUseMenu.resrceCdSmall;
        paramArr.push(beUseMenu);
      }
      */
      if(beUseMenu.menuChkSmall) {
        beUseMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        beUseMenu.resrceCd = beUseMenu.resrceCdSmall;
        paramArr.push(beUseMenu);
      }
    }

    if(paramArr.length < 1){
      s_alert.pop(messages["storeManage.require.chkUseMenu"]);
      return;
    }

    $.postJSONArray("/store/hq/hqManage/authorExcept/addAuth.sb", paramArr, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.saveSucc' />");
                openAuthLayer();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );

  });

  <%-- 삭제 버튼 클릭 --%>
  $("#btnRemoveMenu").click(function(e){
    var paramArr = [];
    for(var i=0; i<avlblMenuGrid.collectionView.items.length; i++){
      var avlblMenu = avlblMenuGrid.collectionView.items[i];
      /*
      if(avlblMenu.menuChkSmall || avlblMenu.menuChkMid) {
        avlblMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        avlblMenu.resrceCd = avlblMenu.resrceCdSmall;
        paramArr.push(avlblMenu);
      }
      */
      if(avlblMenu.menuChkSmall) {
        avlblMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        avlblMenu.resrceCd = avlblMenu.resrceCdSmall;
        paramArr.push(avlblMenu);
      }
    }

    if(paramArr.length < 1){
      s_alert.pop(messages["storeManage.require.chkNotUseMenu"]);
      return;
    }

    $.postJSONArray("/store/hq/hqManage/authorExcept/removeAuth.sb", paramArr, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.delSucc' />");
                openAuthLayer();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  });

  <%-- 권한 팝업 열기 --%>
  function openAuthLayer() {

    $("#munuAuthLayer").show();
    $("#munuAuthDim").show();

    $("#munuAuthLayer #popTitle").text("["+ selectedHq.hqOfficeCd +"] " + selectedHq.hqOfficeNm);

    var param = selectedHq;

    $.postJSON("/store/hq/hqManage/authorExcept/getAuthHqList.sb", param, function(result) {
              authCombo.itemsSource = result.data.authHqList;
              avlblMenuGrid.itemsSource = result.data.avlblMenu;
              beUseMenuGrid.itemsSource = result.data.beUseMenu;
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  }


  /*function chkAll(val){
    //$('input[name=chkBeUse]').prop("checked", true);
    if (val == "1"){
      if($("input:checkbox[id='chkAllAvlbl']").is(":checked")){
        $("input:checkbox[id='chkAllAvlbl']").prop("checked", true);
        $("input:checkbox[name='chkAvlbl']").prop("checked", true);
      }else{
        $("input:checkbox[id='chkAllAvlbl']").prop("checked", false);
        $("input:checkbox[name='chkAvlbl']").prop("checked", false);
      }

    }else{
      if($("input:checkbox[id='chkAllBeUse']").is(":checked")){
        //$("input:checkbox[id='chkAllBeUse']").prop("checked", true);
        $("input:checkbox[name='chkBeUse']").prop("checked", true);
      }else{
        //$("input:checkbox[id='chkAllBeUse']").prop("checked", false);
        $("input:checkbox[name='chkBeUse']").prop("checked", false);
      }
    }

  }*/

  var avlblMobileMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:120},
    //{"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:"*"},
    {"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:70},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", allowMerging:true, width:200}
  ];

  var beUseMobileMenuGridData = [
    {"binding":"resrceCdLarge", "header":"<s:message code='hqManage.lMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmLarge", "header":"<s:message code='hqManage.lMenuNm' />", allowMerging:true, width:120},
    //{"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType:wijmo.DataType.Boolean, isReadOnly:false, width:"*"},
    {"binding":"menuChkMid", "header":"<s:message code='hqManage.chk.menu' />", dataType: wijmo.DataType.Boolean, isReadOnly:false, width:70},
    {"binding":"resrceCdMid", "header":"<s:message code='hqManage.mMenuCd' />", allowMerging:true, visible:false, width:"*"},
    {"binding":"resrceNmMid", "header":"<s:message code='hqManage.mMenuNm' />", allowMerging:true, width:200}
  ];

  avlblMobileMenuGrid = wgrid.genGrid("#avlblMobileMenuGrid", avlblMobileMenuGridData, "${menuCd}", 2, ${clo.getColumnLayout(2)});
  beUseMobileMenuGrid = wgrid.genGrid("#beUseMobileMenuGrid", beUseMobileMenuGridData, "${menuCd}", 3, ${clo.getColumnLayout(3)});
  authMobileCombo     = wcombo.genCommonBox("#authMobileCombo", null);

  avlblMobileMenuGrid.allowMerging  = wijmo.grid.AllowMerging.All;
  beUseMobileMenuGrid.allowMerging  = wijmo.grid.AllowMerging.All;

  <%-- 체크박스 초기화 --%>
  avlblMobileMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      /*
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
       */
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" name="chkMobileAvlbl" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
    }
  });

  beUseMobileMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      /*
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
       */
      if( col.binding == "menuChkMid") {
        e.cell.innerHTML = '<input type="checkbox" name="chkMobileBeUse" class="wj-cell-check"' + (item.menuChkMid == true || item.menuChkMid == "Y" ? 'checked' : '') + '>';
      }
    }
  });

  <%-- 체크박스 핸들러 --%>
  avlblMobileMenuGrid.addEventListener(avlblMobileMenuGrid.hostElement, 'mousedown', function(e) {
    var ht = avlblMobileMenuGrid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      /*
      if( col.binding == "menuChkMid" || col.binding == "menuChkSmall") {
        avlblMenuGrid.beginUpdate();
        if(avlblMenuGrid.cells.getCellData(ht.row, ht.col, true)){
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          avlblMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        avlblMenuGrid.endUpdate();
      }
      */

      if(col.binding == "menuChkMid") {
        avlblMobileMenuGrid.beginUpdate();
        if(avlblMobileMenuGrid.cells.getCellData(ht.row, ht.col, true) == "true"){
          avlblMobileMenuGrid.cells.setCellData(ht.row, ht.col, false);
        }else {
          avlblMobileMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        avlblMobileMenuGrid.endUpdate();
      }

    }
  });

  beUseMobileMenuGrid.addEventListener(beUseMobileMenuGrid.hostElement, 'mousedown', function(e) {
    var ht = beUseMobileMenuGrid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      /*
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
      */

      if( col.binding == "menuChkMid") {
        beUseMobileMenuGrid.beginUpdate();
        if(beUseMobileMenuGrid.cells.getCellData(ht.row, ht.col, true) == "true"){
          beUseMobileMenuGrid.cells.setCellData(ht.row, ht.col, false);
        } else {
          beUseMobileMenuGrid.cells.setCellData(ht.row, ht.col, true);
        }
        beUseMobileMenuGrid.endUpdate();
      }

    }
  });

  <%-- 전체 체크가능한 체크박스 생성 --%>
  /*avlblMenuGrid.formatItem.addHandler(function(s, e) { //TODO 왜 체크가 안되는것인가 //TODO checkbox merge
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' id='chkAllAvlbl' class='wj-cell-check' onclick='chkAll(1);'>";
      }
    }
  });

  beUseMenuGrid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.columnHeaders) {
      var html = e.cell.innerHTML;
      if(html == "<s:message code='hqManage.chk.menu' />") {
        e.cell.innerHTML = "<input type='checkbox' id='chkAllBeUse' class='wj-cell-check' onclick='chkAll(2);'>";
      }
    }
  });*/


  <%-- 권한 복사 버튼 클릭 --%>
  $("#btnCopyMobileAuth").click(function(e){

    var param = {};
    param.hqOfficeCd      = selectedHq.hqOfficeCd;
    param.copyHqOfficeCd  = authMobileCombo.selectedValue;

    console.log(param);

    $.postJSONSave("/store/hq/hqManage/authorExcept/copyMobileAuth.sb", param, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.copySucc' />");
                openAuthLayer2();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  });

  <%-- 추가 버튼 클릭 --%>
  $("#btnAddMobileMenu").click(function(e){

    var paramArr = [];
    for(var i=0; i<beUseMobileMenuGrid.collectionView.items.length; i++){
      var beUseMenu = beUseMobileMenuGrid.collectionView.items[i];
      /*
      if(beUseMenu.menuChkSmall || beUseMenu.menuChkMid) {
        beUseMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        beUseMenu.resrceCd = beUseMenu.resrceCdSmall;
        paramArr.push(beUseMenu);
      }
      */
      if(beUseMenu.menuChkMid) {
        beUseMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        beUseMenu.resrceCd = beUseMenu.resrceCdMid;
        paramArr.push(beUseMenu);
      }
    }

    if(paramArr.length < 1){
      s_alert.pop(messages["storeManage.require.chkUseMenu"]);
      return;
    }

    $.postJSONArray("/store/hq/hqManage/authorExcept/addMobileAuth.sb", paramArr, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.saveSucc' />");
                openAuthLayer2();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );

  });

  <%-- 삭제 버튼 클릭 --%>
  $("#btnRemoveMobileMenu").click(function(e){
    var paramArr = [];
    for(var i=0; i<avlblMobileMenuGrid.collectionView.items.length; i++){
      var avlblMenu = avlblMobileMenuGrid.collectionView.items[i];
      /*
      if(avlblMenu.menuChkSmall || avlblMenu.menuChkMid) {
        avlblMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        avlblMenu.resrceCd = avlblMenu.resrceCdSmall;
        paramArr.push(avlblMenu);
      }
      */
      if(avlblMenu.menuChkMid) {
        avlblMenu.hqOfficeCd = selectedHq.hqOfficeCd;
        avlblMenu.resrceCd = avlblMenu.resrceCdMid;
        paramArr.push(avlblMenu);
      }
    }

    if(paramArr.length < 1){
      s_alert.pop(messages["storeManage.require.chkNotUseMenu"]);
      return;
    }

    $.postJSONArray("/store/hq/hqManage/authorExcept/removeMobileAuth.sb", paramArr, function(result) {
              var res = result.data;
              if(res > 0) {
                s_alert.pop("<s:message code='cmm.delSucc' />");
                openAuthLayer2();
              }
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  });

  <%-- 권한 팝업 열기 --%>
  function openAuthLayer2() {

    $("#munuAuthLayer").show();
    $("#munuAuthDim").show();

    $("#munuAuthLayer #popTitle").text("["+ selectedHq.hqOfficeCd +"] " + selectedHq.hqOfficeNm);

    var param = selectedHq;

    $.postJSON("/store/hq/hqManage/authorExcept/getAuthMobileHqList.sb", param, function(result) {
              authMobileCombo.itemsSource = result.data.authMobileHqList;
              avlblMobileMenuGrid.itemsSource = result.data.avlblMobileMenu;
              beUseMobileMenuGrid.itemsSource = result.data.beUseMobileMenu;
            },
            function (result) {
              s_alert.pop(result.message);

            }
    );
  }

  <%-- 모바일 메뉴 클릭--%>
  $("#mobMenu").click(function(e){
    $("#mobMenu").addClass("on");
    $("#webMenu").removeClass("on");
    $("#webArea").hide();
    $("#mobileArea").show();
    openAuthLayer2();
  });

  $("#webMenu").click(function(e){
    $("#webMenu").addClass("on");
    $("#mobMenu").removeClass("on");
    $("#mobileArea").hide();
    $("#webArea").show();
    openAuthLayer();
  });

  <%-- 메뉴권한 화면 보여줌 --%>
  function showMenuAuth() {
    hideMaster();
    hideEnvSet();
    $("#webMenu").click();
  }

  <%-- 닫기 버튼 클릭 --%>
  $("#munuAuthLayer .btn_close").click(function(e){
    $("#munuAuthLayer").hide();
    $("#munuAuthDim").hide();
  });

  <%-- 메뉴권한 화면 숨김 --%>
  function hideMenuAuth() {
    $("#munuAuthLayer").hide();
    $("#munuAuthDim").hide();
  }

  <%-- 상세정보 탭 클릭 --%>
  $("#munuAuthLayer #hqInfoTab").click(function(){
    showMaster();
  });

  <%-- 상세정보 탭 클릭 --%>
  $("#munuAuthLayer #envSettingTab").click(function(){
    showEnvSet();
  });


</script>
