<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="hqComboList">${hqOfficeComboList}</c:set>

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
        <th><s:message code="storeManage.sysStatFg" /></th>
        <td><input type="text" class="sb-input w100" /></td>
      </tr>
    </tbody>
  </table>
    
  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="wj-TblWrap mt20">
    <div class="w35 fl">  
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
        <div id="theGrid" style="height:550px;"></div> 
          
        <%-- 페이지리스트 --%>
        <div class="pageNum mt20">
          <%-- id --%>
          <ul id="page" data-size="10">
          </ul>
        </div>
        
      </div>
    </div>
    
    <%-- 매장 상세정보 --%>
    <c:import url="/WEB-INF/view/store/manage/storeManage/storeManageDetail.jsp">
      <c:param name="menuCd" value="${menuCd}"/>
      <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
    
    <%-- 매장 환경설정 --%>
    <c:import url="/WEB-INF/view/store/manage/storeManage/storeConfigManage.jsp">
      <c:param name="menuCd" value="${menuCd}"/>
      <c:param name="menuNm" value="${menuNm}"/>
    </c:import>
    
  </div>
</div>

<script>

  var selectedStore;

  var clsFg             = ${ccu.getCommCodeSelect("003")};
  var sysStatFg         = ${ccu.getCommCodeSelect("005")};

  var clsFgDataMap      = new wijmo.grid.DataMap(clsFg, 'value', 'name');
  var sysStatFgDataMap  = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');
  
  var srchClsFg         = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFg     = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  <%-- header --%>
  var hData =
    [
      {binding:"hqOfficeCd", header:"<s:message code='storeManage.storeCd' />", visible:false, width:"*"},
      {binding:"hqOfficeNm", header:"<s:message code='storeManage.storeNm' />", visible:false, width:"*"},
      {binding:"storeCd", header:"<s:message code='storeManage.storeCd' />", width:"*"},
      {binding:"storeNm", header:"<s:message code='storeManage.storeNm' />", width:"*"},
      {binding:"clsFg", header:"<s:message code='storeManage.clsFg' />", dataMap:clsFgDataMap, width:"*"},
      {binding:"sysStatFg", header:"<s:message code='storeManage.sysStatFg' />", dataMap:sysStatFgDataMap, width:"*"},
      {binding:"sysOpenDate", header:"<s:message code='storeManage.sysOpenDate' />", width:"*"}
    ];
  
  var grid          = wgrid.genGrid("#theGrid", hData, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  
  grid.showMarquee = true;
  
  
  var ldata         = ${ccu.getListScale()};
  var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);
  
  <%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "storeNm") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });
  
  <%-- 그리드 선택 이벤트 --%>
  /*  //TODO 헤더 작업하고 합치자
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "storeNm") {
        selectedStore = grid.rows[ht.row].dataItem;
        showDetail();
      }
    }
  });
   */
  
  <%-- 조회 버튼 클릭 --%>
  $("#btnSearch").click(function(){
    search(1);
  });
  
  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });
  
  <%-- 매장 목록 조회 --%>
  function search(index) {

    var param = {};
    param.listScale   = listScaleBox.selectedValue;
    param.curr        = index;
    
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

      grid.itemsSource = new wijmo.collections.CollectionView(list, {
        groupDescriptions : [ 'hqOfficeCd']
      });
      
      <%-- 그리드 선택 이벤트 --%>
      grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
        var ht = grid.hitTest(e);
        
        if(ht.panel == grid.cells) {
          if (grid.rows[ht.row] instanceof wijmo.grid.GroupRow) {
            // 그룹헤더일때 본사코드 받아와서 본사에 매장 새로 등록 가능하도록
            var hdOfficeCd = grid.rows[ht.row].dataItem.name;
            newStoreReg(hdOfficeCd);
          } else {
            var col = ht.panel.columns[ht.col];
            if( col.binding == "storeNm") {
              selectedStore = grid.rows[ht.row].dataItem;
              showDetail();
            }
          }
        }
      });
      
      page.make("#page", result.data.page.curr, result.data.page.totalPage);

      $("#noDataArea").show();
      $("#storeInfoViewArea").hide();
      $("#storeEnvInfoArea").hide();
      
    })
    .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 매장 선택시 --%>
  function showDetail() {
    
    var param = selectedStore;  //TODO 수정필요
    
    $.postJSON("/store/manage/storeManage/storeManage/getStoreDetail.sb", param, function(result) {
      
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }

      $("#noDataArea").hide();
      $("#storeEnvInfoArea").hide();
      
      showStoreDetail(result.data);
    })
    .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }
</script>

<%-- 매장환경조회 팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeEnvSearch.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사업자번호 사용현황 레이어 팝업 --%>
<%-- 
<c:import url="/WEB-INF/view/store/hq/hqManage/bizInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
 --%>
 
<%-- 포스 환경설정 : 테이블 그룹셋팅 레이어팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storeTabGrpSetting.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스 환경설정 : 테이블 명칭설정 레이어팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/storePosNmSetting.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 포스 환경설정 : 테이블 설정복사 레이어팝업 --%>
<c:import url="/WEB-INF/view/store/manage/storeManage/posSettingCopy.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>