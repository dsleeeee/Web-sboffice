<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <div class="searchBar">
    <a href="javascript:;" class="open">${menuNm}</a>
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
          <div class="sb-select">
            <div id="srchHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="hqManage.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%--사업자번호 --%>
        <th><s:message code="hqManage.bizNo" /></th>
        <td>
          <div class="sb-select">
            <div id="srchBizNo"></div>
          </div>
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

  <div class="mt10 pdb20 oh bb">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%--페이지 스케일 --%>
    <div id="listScaleBox" class="w130 fl"></div>
    <div class="tr">
      <%-- 본사신규등록 --%>
      <button class="btn_skyblue" id="btnRegist"><s:message code="hqManage.newHq" /></button>
      <%-- sms 전송 //TODO --%>
      <button class="btn_skyblue" id="btnSMS"><s:message code="hqManage.sendSMS" /></button>
      <%-- 엑셀다운로드 --%>
      <button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>
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
  var agencyList        = ${ccu.getAgencyList()};
  var areaCd            = ${ccu.getCommCodeSelect("092")};
  var clsFg             = ${ccu.getCommCodeSelect("003")};
  var sysStatFg         = ${ccu.getCommCodeSelect("005")};
  var clsFgDataMap      = new wijmo.grid.DataMap(clsFg, 'value', 'name');
  var sysStatFgDataMap  = new wijmo.grid.DataMap(sysStatFg, 'value', 'name');

  var srchHqOfficeCd = wcombo.genInputText("#srchHqOfficeCd", "7", "본사코드", function(s, e) {
      if ( s.maskFull ) {
          console.log("통과");
      } else {
          console.log("실패");
      }
  });

  var srchHqOfficeNm    = wcombo.genInput("#srchHqOfficeNm");
  var srchBizNo         = wcombo.genInput("#srchBizNo");
  var srchClsFg         = wcombo.genCommonBox("#srchClsFg", clsFg);
  var srchSysStatFg     = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);

  <%-- Header --%>
  var hData =
    [
      {binding:"hqOfficeCd", header:"<s:message code='hqManage.hqOfficeCd' />"},//본사코드
      {binding:"hqOfficeNm", header:"<s:message code='hqManage.hqOfficeNm' />"},//본사명
      {binding:"ownerNm", header:"<s:message code='hqManage.ownerNm' />"},//대표자명
      {binding:"clsFg", header:"<s:message code='hqManage.clsFg' />", dataMap:clsFgDataMap},//용도구분
      {binding:"sysStatFg", header:"<s:message code='hqManage.sysStatFg' />", dataMap:sysStatFgDataMap},//상태구분
      {binding:"cnt", header:"<s:message code='hqManage.storeCnt' />"},//매장수
      {binding:"posNm", header:"<s:message code='hqManage.posNm' />"},//사용포스
      {binding:"sysOpenDate", header:"<s:message code='hqManage.sysOpenDate' />"},//시스템오픈일
      {binding:"telNo", header:"<s:message code='hqManage.telNo' />"},//전화번호
      {binding:"faxNo", header:"<s:message code='hqManage.faxNo' />"},//팩스번호
      {binding:"smsChk", header:"<s:message code='hqManage.smsChk' />", dataType:wijmo.DataType.Boolean}//sms
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

      // 단독매장본사의 경우에는 선택 불가능
      if( col.binding == "hqOfficeCd" && grid.rows[ht.row].dataItem.hqOfficeCd != "00000") {
        selectedHq = grid.rows[ht.row].dataItem;
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
    if(srchHqOfficeCd.text.length > 5) {
      s_alert.pop("<s:message code='hqManage.hqOfficeCd'/><s:message code='cmm.regexp' arguments='5'/>");
      return;
    }

    if(srchHqOfficeNm.text.length > 15) {
      s_alert.pop("<s:message code='hqManage.hqOfficeNm'/><s:message code='cmm.regexp' arguments='15'/>");
      return;
    }

    if(srchBizNo.text.length > 15) {
      s_alert.pop("<s:message code='hqManage.bizNo'/><s:message code='cmm.regexp' arguments='15'/>");
      return;
    }

    var param = {};
    param.hqOfficeCd  = srchHqOfficeCd.text;
    param.hqOfficeNm  = srchHqOfficeNm.text;
    param.bizNo       = srchBizNo.text;
    param.clsFg       = srchClsFg.selectedValue;
    param.sysStatFg   = srchSysStatFg.selectedValue;
    param.listScale   = listScaleBox.selectedValue;
    param.curr        = index;

    $.postJSON("/store/hq/hqManage/hqManage/list.sb", param, function(result) {
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
    }
    ,function(){
        s_alert.pop("Ajax Fail");
    });
  }

  <%-- 본사신규등록 버튼 클릭--%>
  $("#btnRegist").click(function(){
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

<%-- 버전 상세정보 레이어 --%>
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
