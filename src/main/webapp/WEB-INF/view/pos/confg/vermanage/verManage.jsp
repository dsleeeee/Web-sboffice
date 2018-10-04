<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">
  <div class="searchBar flddUnfld">
    <a href="#" class="open">${menuNm}</a>
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
        <%-- 버전일련번호 --%>
        <th><s:message code="verManage.verSerNo" /></th>
        <td>
          <div class="sb-select">
            <div id="srchVerSerNo"></div>
          </div>
        </td>
        <%-- 버전적용명 --%>
        <th><s:message code="verManage.verSerNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchVerSerNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>
    <div class="fr">
      <%-- 신규버전등록 --%>
      <button id="btnRegist" class="btn_skyblue"><s:message code="verManage.regist.new" /></button>
      <%-- 엑셀 다운로드 --%>
      <button id="btnExcel" class="btn_skyblue" ><s:message code="cmm.excel.down" /></button>
    </div>
  </div>

  <%-- 위즈모 테이블 --%>
  <div class="wj-TblWrap mt10">
    <div id="theGrid1" style="height:450px;"></div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
</div>

<script>

<%-- 선택된 포스버전  --%>
var selectVerSerNo = "";
var selectVerSerCnt = 0;


<%-- 검색조건 및 dataMap 조회 --%>
var verSerNo    = wcombo.genInput("#srchVerSerNo");
var verSerNm    = wcombo.genInput("#srchVerSerNm");
var containYn   = ${ccu.getCommCodeExcpAll("058")};
var useYn       = ${ccu.getCommCodeExcpAll("067")};
var progFg      = ${ccu.getCommCodeExcpAll("059")};

var containYnDataMap = new wijmo.grid.DataMap(containYn, 'value', 'name');
var useYnDataMap     = new wijmo.grid.DataMap(useYn, 'value', 'name');
var progFgDataMap    = new wijmo.grid.DataMap(progFg, 'value', 'name');

<%-- Header --%>
var hData1 =
  [
    {binding:"verSerNo", header:"<s:message code='verManage.verSerNo' />", allowMerging:true},
    {binding:"verSerNm", header:"<s:message code='verManage.verSerNm' />", allowMerging:true},
    {binding:"progFg", header:"<s:message code='verManage.progFg' />", allowMerging:true, dataMap:progFgDataMap},
    {binding:"pgmYn", header:"<s:message code='verManage.pgm' />", allowMerging:true, dataMap:containYnDataMap},
    {binding:"dbYn", header:"<s:message code='verManage.db' />", allowMerging:true, dataMap:containYnDataMap},
    {binding:"imgYn", header:"<s:message code='verManage.img' />", allowMerging:true, dataMap:containYnDataMap},
    {binding:"fileSize", header:"<s:message code='verManage.fileSize' />", allowMerging:true},
    {binding:"regCnt", header:"<s:message code='verManage.regCnt' />", allowMerging:true},
    {binding:"recvCnt", header:"<s:message code='verManage.recvCnt' />", allowMerging:true},
    {binding:"regDt", header:"<s:message code='verManage.regDt' />", allowMerging:true},
    {binding:"regId", header:"<s:message code='verManage.regId' />", allowMerging:true},
    {binding:"useYn", header:"<s:message code='verManage.useYn' />", allowMerging:true, dataMap:useYnDataMap}
  ];

<%-- 그리드 생성 --%>
var grid1         = wgrid.genGrid("#theGrid1", hData1, "${menuCd}", 1, ${clo.getColumnLayout(1)});
var ldata         = ${ccu.getListScale()};
var listScaleBox  = wcombo.genCommonBox("#listScaleBox", ldata);

grid1.isReadOnly      = true;
grid1.allowMerging    = wijmo.grid.AllowMerging.ColumnHeaders;

<%-- merge할 헤더 추가 --%>
for (var i = 0; i < 1; i++) {
  var hr = new wijmo.grid.Row();
  grid1.columnHeaders.rows.push(hr);
}

for (var row = 0; row < grid1.columnHeaders.rows.length; row++) {
  grid1.columnHeaders.rows[row].allowMerging = true;
  for (var col = 0; col < grid1.columns.length; col++) {
    if ((row == 0 && col == 0) || row == 1 && col == 0 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.verSerNo' />");
    }
    if ((row == 0 && col == 1) || row == 1 && col == 1 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.verSerNm' />");
    }
    if ((row == 0 && col == 2) || row == 1 && col == 2 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.progFg' />");
    }
    if ((row == 0 && col == 3) || row == 0 && col == 4 || row == 0 && col == 5 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.incldDtls' />");
    }
    if ((row == 0 && col == 6) || row == 1 && col == 6 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.fileSize' />");
    }
    if ((row == 0 && col == 7) || row == 1 && col == 7 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.regCnt' />");
    }
    if ((row == 0 && col == 8) || row == 1 && col == 8 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.recvCnt' />");
    }
    if ((row == 0 && col == 9) || row == 1 && col == 9 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.regDt' />");
    }
    if ((row == 0 && col == 10) || row == 1 && col == 10 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.regId' />");
    }
    if ((row == 0 && col == 11) || row == 1 && col == 11 ) {
      grid1.columnHeaders.setCellData(row, col, "<s:message code='verManage.useYn' />");
    }
  }
}

<%-- 그리드 포맷 --%>
grid1.formatItem.addHandler(function(s, e) {
  if (e.panel == s.cells) {
    var col = s.columns[e.col];
    var item = s.rows[e.row].dataItem;
    if( col.binding == "verSerNo" ) {
      wijmo.addClass(e.cell, 'wijLink');
    }
  }
});

<%-- 그리드 선택 이벤트 --%>
grid1.addEventListener(grid1.hostElement, 'mousedown', function(e) {
  var ht = grid1.hitTest(e);
  if( ht.cellType == wijmo.grid.CellType.Cell) {
    var col = ht.panel.columns[ht.col];
    if( col.binding == "verSerNo") {
      var param = {};
      selectVerSerNo = grid1.rows[ht.row].dataItem.verSerNo;  // 선택된 버전일련번호
      param.verSerNo = grid1.rows[ht.row].dataItem.verSerNo;
      searchVerInfoDtl(param);
      searchVerStore(param);
    }
  }
});

<%-- 조회버튼 클릭 --%>
$("#btnSearch").click(function(e){
  search(1);
});

<%-- 페이징 --%>
$(document).on("click", ".page1", function() {
  search($(this).data("value"));
});

<%-- 포스버전관리 목록 조회 --%>
function search(index) {
  //TODO validation 추가

  var param = {};
  param.verSerNo  = verSerNo.text;
  param.verSerNm  = verSerNm.text;
  param.listScale = listScaleBox.selectedValue;
  param.curr = index;

  $.postJSON("/pos/confg/verManage/verInfo/list.sb", param, function(result) {
    var list = result.data.list;

    if(list.length === undefined || list.length == 0) {
      s_alert.pop(result.message);
      return;
    }
    grid1.itemsSource = list;
    page.make("#page", result.data.page.curr, result.data.page.totalPage);
  },
    function (result) {
      s_alert.pop(result.message);
      return;
    }
  );
}

$("#btnRegist").click(function(){
  selectVerSerNo = "";
  showVerInfoDtlRegistLayer();
});

<%-- 엑셀 다운로드 버튼 클릭 --%>
$("#btnExcel").click(function(){
  var name = "${menuNm}";
  wexcel.down(grid1, name, name + ".xlsx");
});

</script>

<%-- 버전 상세정보 레이어 --%>
<c:import url="/WEB-INF/view/pos/confg/vermanage/verInfoDtl.jsp">
</c:import>

<%-- 매장추가 레이어 --%>
<c:import url="/WEB-INF/view/pos/confg/vermanage/storeAdd.jsp">
</c:import>
