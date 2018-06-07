<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <div class="searchBar">
    <a href="javscript:;" class="open">${menuNm}</a>
  </div>
  <table class="searchTbl">
    <colgroup>
      <col class="w15" />
      <col class="w35" />
      <col class="w15" />
      <col class="w35" />
    </colgroup>
    <tbody>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="storeManage.hqOfficeCd" /></th>
        <td><input type="text" class="sb-input w100" /></td>
        <%-- 본사명 --%>
        <th><s:message code="storeManage.hqOfficeNm" /></th>
        <td><input type="text" class="sb-input w100" /></td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="storeManage.storeCd" /></th>
        <td><input type="text" class="sb-input w100" /></td>
        <%-- 매장명 --%>
        <th><s:message code="storeManage.storeNm" /></th>
        <td><input type="text" class="sb-input w100" /></td>
      </tr>
      <tr>
        <%-- 사업자번호 --%>
        <th><s:message code="storeManage.bizNo" /></th>
        <td><input type="text" class="sb-input w100" /></td>
        <%-- 용도 --%>
        <th><s:message code="storeManage.clsFg" /></th>
        <td>
          <div class="sb-select">
              <div id="srchClsFg"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="storeManage.sysStatFg" /></th>
        <td>
          <div class="sb-select">
              <div id="srchSysStatFg"></div>
          </div>
        </td>
        <%-- 매장구분 --%>
        <th><s:message code="storeManage.storeFg" /></th>
        <td><input type="text" class="sb-input w100" /></td>
      </tr>
    </tbody>
  </table>
    
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="wj-TblWrap mt20">
    <div class="w50 fl">  
      <div class="wj-TblWrapBr mr10 pd20" style="height:700px;">
        <div class="sb-select dkbr mb10 oh">
          <%-- 페이지스케일 --%>
          <div id="listScaleBox" class="w130 fl"></div>
          <div class="fr">
            <%-- 전체펼치기 --%>
            <button class="btn_skyblue" id="btnExpand"><s:message code="cmm.all.expand" /></button>
            <%-- 전체접기 --%>
            <button class="btn_skyblue" id="btnFold"><s:message code="cmm.all.fold" /></button>
          </div>
        </div>
        <div id="theGrid" style="height:400px;"></div> 
          
        <%-- 페이지리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="page" data-size="10">
          </ul>
        </div>
        
      </div>
    </div>
      
    <div class="w50 fr" id="noDataArea">
      <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
        <p class="tc s18 mt200 bk"><s:message code="storeManage.require.select.store" /></p>
        <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.hqOffice" /><br /><s:message code="storeManage.can.regist.store" /></p>
        <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.store" /><br /><s:message code="storeManage.can.edit.store" /></p> 
      </div>
    </div>
    
  </div>
</div>

<script>

  var clsFg             = ${ccu.getCommCodeSelect("003")};
  var sysStatFg         = ${ccu.getCommCodeSelect("005")};

  var clsFgDataMap      = new wijmo.grid.DataMap(clsFg, 'value', 'name');
  var sysStatFgDataMap  = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  
  var srchClsFg         = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFg     = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  <%-- header --%>
  var hData =
    [
      {binding:"storeCd", header:"<s:message code='storeManage.storeCd' />"},
      {binding:"storeNm", header:"<s:message code='storeManage.storeNm' />"},
      {binding:"clsFg", header:"<s:message code='storeManage.clsFg' />", dataMap:clsFgDataMap},
      {binding:"sysStatFg", header:"<s:message code='storeManage.sysStatFg' />", dataMap:sysStatFgDataMap},
      {binding:"sysOpenDate", header:"<s:message code='storeManage.sysOpenDate' />"}
    ];
  
  var grid          = wgrid.genGrid("#theGrid", hData, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var ldata         = ${ccu.getListScale()};
  var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);
  
  grid.sortDescriptions= [ 'hdShopCdNm'];
  grid.groupDescriptions= [ 'hdShopCdNm'];

  <%-- 조회 버튼 클릭 --%>
  $("#btnSearch").click(function(){
    
    var param = {};
    
    
    // 검색 조건 추가
    $.postJSON("/store/manage/storeManage/storeManage/getStoreList.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var list = result.data.list;
      
      if(list.length === undefined || list.length == 0) {
        s_alert.pop(result.message);
        return;
      }
      grid.itemsSource = list;
      
      page.make("#page", result.data.page.curr, result.data.page.totalPage);
    })
    .fail(function(){
        s_alert.pop("Ajax Fail");
    });
    
  });
  
</script>
