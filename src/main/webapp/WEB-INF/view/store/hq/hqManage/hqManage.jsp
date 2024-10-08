<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="agencyList" value="${agencyList}" />

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch" >
        <s:message code="cmm.search" />
      </button>
    </div>
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
        <%--본사코드 --%>
        <th><s:message code="hqManage.hqOfficeCd" /></th>
        <td>
          <input type="text" id="srchHqOfficeCd" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="hqManage.hqOfficeNm" /></th>
        <td>
          <input type="text" id="srchHqOfficeNm" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
        </td>
      </tr>
      <tr>
        <%--사업자번호 --%>
        <th><s:message code="hqManage.bizNo" /></th>
        <td>
          <input type="text" id="srchBizNo" class="sb-input w100" onkeyup="fnNxBtnSearch();"/>
        </td>
        <%-- 용도 --%>
        <th><s:message code="hqManage.clsFg" /></th>
        <td>
          <div class="sb-select">
            <div id="srchClsFg"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="hqManage.sysStatFg" /></th>
        <td>
          <div class="sb-select">
            <div id="srchSysStatFg"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 oh sb-select dkbr">
    <%--페이지 스케일 --%>
    <div id="listScaleBox" class="w100px fl"></div>
    <div class="tr">
      <%-- 본사신규등록 --%>
      <button class="btn_skyblue" id="btnRegist"><s:message code="hqManage.newHq" /></button>
      <%-- sms 전송 //TODO --%>
      <%--<button class="btn_skyblue" id="btnSMS"><s:message code="hqManage.sendSMS" /></button>--%>
      <%-- 엑셀다운로드 //TODO--%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
  </div>

  <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div id="theGrid" style="height:435px;"></div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>

</div>

<script>

  var selectedHq = "";

  <%-- 검색조건 및 dataMap 조회 --%>
  //공통에서 가져오던거 변경 >> 총판/대리점은 공통 못쓰고 자신이 관리하는 업체만 나와야 하기 때문
  //var agencyList = ${ccu.getAgencyList()};
  var agencyList = ${agencyList};
  var areaCd            = ${ccu.getCommCodeSelect("061")};
  var clsFg             = ${ccu.getCommCodeSelect("001")};
  var sysStatFg         = ${ccu.getCommCodeSelect("005")};
  var clsFgDataMap      = new wijmo.grid.DataMap(clsFg, 'value', 'name');
  var sysStatFgDataMap  = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

  var srchClsFg         = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFg     = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  <%-- Header --%>
  var hData =
    [
      {binding:"hqOfficeCd", header:"<s:message code='hqManage.hqOfficeCd' /> ", width:"*"},
      {binding:"hqOfficeNm", header:"<s:message code='hqManage.hqOfficeNm' />", width:"*"},
      {binding:"ownerNm", header:"<s:message code='hqManage.ownerNm' />", width:"*"},
      {binding:"clsFg", header:"<s:message code='hqManage.clsFg' />", dataMap:clsFgDataMap},
      {binding:"sysStatFg", header:"<s:message code='hqManage.sysStatFg' />", dataMap:sysStatFgDataMap, width:"*"},
      {binding:"cnt", header:"<s:message code='hqManage.storeCnt' />", width:"*"},
      {binding:"posNm", header:"<s:message code='hqManage.posNm' />", width:"*"},
      {binding:"sysOpenDate", header:"<s:message code='hqManage.sysOpenDate' />", width:"*"},
      {binding:"telNo", header:"<s:message code='hqManage.telNo' />", width:"*"},
      {binding:"faxNo", header:"<s:message code='hqManage.faxNo' />", width:"*"},
      {binding:"smsChk", header:"<s:message code='hqManage.smsChk' />", dataType:wijmo.DataType.Boolean, width:"*"}
    ];

  var grid          = wgrid.genGrid("#theGrid", hData, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var ldata         = ${ccu.getListScale()};
  var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);

  <%-- 체크박스 초기화 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "smsChk") {
        e.cell.innerHTML = '<input type="checkbox" class="wj-cell-check"' + (item.chk == true || item.chk == "Y" ? 'checked' : '') + '>';
      }
    }
  });

  <%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "hqOfficeCd" && s.rows[e.row].dataItem.hqOfficeCd != "00000") {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];

      <%-- 단독매장본사의 경우에는 선택 불가능 --%>
      if( col.binding == "hqOfficeCd" && grid.rows[ht.row].dataItem.hqOfficeCd != "00000") {
        selectedHq = grid.rows[ht.row].dataItem;
        console.log(selectedHq)
        openDtlLayer(selectedHq);
      }
    }
  });

  <%-- 조회 버튼 클릭 --%>
  $("#btnSearch").click(function(e){
    search(1);
  });

  <%-- 페이징 --%>
  $(document).on("click", ".page", function() {
    search($(this).data("value"));
  });

  <%-- 본사 목록 조회 --%>
  function search(index) {
    var param = {};

    param.hqOfficeCd  = $("#srchHqOfficeCd").val();
    param.hqOfficeNm  = $("#srchHqOfficeNm").val();
    param.bizNo       = $("#srchBizNo").val();
    param.clsFg       = srchClsFg.selectedValue;
    param.sysStatFg   = srchSysStatFg.selectedValue;
    param.listScale   = listScaleBox.selectedValue;
    param.curr        = index;

    $.postJSON("/store/hq/hqManage/hqManage/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length === undefined || list.length == 0) {
        grid.itemsSource = new wijmo.collections.CollectionView([]);
        s_alert.pop(result.message);
        return;
      }
      grid.itemsSource = list;
      page.make("#page", result.data.page.curr, result.data.page.totalPage);
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 본사신규등록 버튼 클릭--%>
  $("#btnRegist").click(function(){
    selectedHq = "";
    openRegistLayer();
  });

  <%-- SMS전송 버튼 클릭--%>
  $("#btnSMS").click(function(){
    s_alert.pop("서비스 준비중입니다.");
  });

  <%-- 엑셀 다운로드 버튼 클릭 --%>
  $("#btnExcel").click(function(){
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });
</script>

<%-- 본사 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/master.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 환경설정 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/config.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="orgnFg" value="${orgnFg}"/>
  <c:param name="orgnCd" value="${orgnCd}"/>
  <c:param name="orgnNm" value="${orgnNm}"/>
</c:import>

<%-- 메뉴권한 레이어 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/authorExcept.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>

<%-- 사업자번호 사용현황 레이어 팝업 --%>
<c:import url="/WEB-INF/view/store/hq/hqManage/bizInfo.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
