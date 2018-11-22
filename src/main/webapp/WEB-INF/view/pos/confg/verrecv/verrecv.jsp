<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/pos/confg/verRecv/verRecv/"/>

<div class="subCon">
  <%-- 탭 --%>
  <ul class="subTab mb20">
    <li><a href="#" id="verrecv" class="on"><s:message code="verRecv.verrecv" /></a></li>
    <li><a href="#" id="storerecv"><s:message code="verRecv.storerecv" /></a></li>
    <li><a href="#" id="verstore"><s:message code="verRecv.verstore" /></a></li>
  </ul>

  <div class="searchBar flddUnfld">
    <a href="#" class="open fl">${menuNm}</a>
    <%-- 조회 --%>
    <div class="mr15 fr" style="display:block;position: relative;margin-top: 6px;">
      <button class="btn_blue fr" id="btnSearch">
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
        <%-- 버전일련번호 --%>
        <th><s:message code="verRecv.verSerNo" /></th>
        <td>
          <input type="text" id="verSerNo" name="verSerNo" size="50" maxlength="20" class="sb-input">
        </td>
        <%-- 버전적용명 --%>
        <th><s:message code="verRecv.verSerNm" /></th>
        <td>
          <input type="text" id="verSerNm" name="verSerNm" size="50" maxlength="10" class="sb-input">
        </td>
      </tr>
    </tbody>
  </table>

  <!--2단-->
  <div class="wj-TblWrap mt20" style="overflow-y: hidden;">
   <!--left-->
    <div class="w35 fl">
      <div class="wj-TblWrapBr mr10 pd20" style="height:460px;">
        <span><s:message code="verRecv.verInfo" /></span>
        <div class="sb-select dkbr mb10 oh">
          <%-- 엑셀 다운로드 //TODO --%>
          <%--
          <div class="fr">
            <button id="btnExcel1" class="btn_skyblue"><s:message code="cmm.excel.down" /></button>
          </div>
          --%>
        </div>
        <%--위즈모 테이블 --%>
        <div id="theGrid1" style="height:390px;"></div>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt20">
        <ul id="page1" data-size="10">
        </ul>
      </div>
    </div>

    <div class="w65 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:460px; overflow-y: hidden;">
        <span><s:message code="verRecv.recvStore"/></span>
        <div class="sb-select dkbr mb10 oh">
          <%-- 엑셀 다운로드//TODO --%>
          <%--
          <div class="tr">
            <button id="btnExcel2" class="btn_skyblue"><s:message code="cmm.excel.down" /></button>
          </div>
          --%>
        </div>
        <p class="s12 bk tl mb10 mt10" id="storeTit"></p>
        <div id="theGrid2" style="height:390px;"></div>
      </div>
      <%-- 페이지 리스트 --%>
      <div class="pageNum mt10">
        <ul id="page2" data-size="10">
        </ul>
      </div>
    </div>
  </div>
</div>

<script>

  <%-- 탭 클릭 --%>
  $("#verrecv").click(function(e){
    e.preventDefault();
    location.href = "/pos/confg/verRecv/verRecv/list.sb";
  });

  $("#storerecv").click(function(e){
    e.preventDefault();
    location.href = "/pos/confg/verRecv/storeRecv/list.sb";
  });

  $("#verstore").click(function(e){
    e.preventDefault();
    location.href = "/pos/confg/verRecv/verStore/list.sb";
  });

  <%-- Header --%>
  var hData1 =
    [
      {binding:"verSerNo", header:"<s:message code='verRecv.verSerNo' />", width:100},
      {binding:"verSerNm", header:"<s:message code='verRecv.verSerNm' />", width:"*"},
      {binding:"regCnt", header:"<s:message code='verRecv.regCnt' />", width:60},
      {binding:"recvCnt", header:"<s:message code='verRecv.recvCnt' />", width:60}
    ];

  var hData2 =
    [
      {binding:"storeCd", header:"<s:message code='verRecv.storeCd' />", width:90},
      {binding:"storeNm", header:"<s:message code='verRecv.storeNm' />", width:"*"},
      {binding:"posNo", header:"<s:message code='verRecv.posNo' />", width:60},
      {binding:"posNm", header:"<s:message code='verRecv.posNm' />", width:100},
      {binding:"verRecvDt", header:"<s:message code='verRecv.verRecvDt'/>", width:"*"},
      {binding:"posIp", header:"<s:message code='verRecv.posIp' />", width:110}
    ];

  <%-- 그리드 생성 --%>
  var grid1         = wgrid.genGrid("#theGrid1", hData1, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var grid2         = wgrid.genGrid("#theGrid2", hData2, "${menuCd}", 2, ${clo.getColumnLayout(1)});
  var ldata         = ${ccu.getListScale()};

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

  var selValue = "";

  <%-- 그리드 선택 이벤트 --%>
  grid1.addEventListener(grid1.hostElement, 'mousedown', function(e) {
    var ht = grid1.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "verSerNo") {
        var items = grid1.rows[ht.row].dataItem;
        selValue = items.verSerNo;
        var storeTitle = "[" + items.verSerNo + "] "+ items.verSerNm;
        $("#storeTit").text(storeTitle);
        searchStore(1);
      }
    }
  });

  <%-- 매장조회 --%>
  function searchStore(index) {

    var param = {};
    param.verSerNo = selValue;
    param.listScale = "10";
    param.curr      = index;

    $.postJSON("${baseUrl}" + "storeList.sb", param, function(result) {
      var list = result.data.list;
      grid2.itemsSource = list;
      page.make("#page2", result.data.page.curr, result.data.page.totalPage);
    },
      function(result) {
        s_alert.pop(result.message);
        grid2.itemsSource = new wijmo.collections.CollectionView([]);
        return;
    });
  }

  <%-- 조회버튼 클릭 --%>
  $("#btnSearch").click(function(e){
    search(1);
  });

  <%-- 버전리스트 조회  --%>
  function search(index) {

    var param = {};
    param.verSerNo  = $("#verSerNo").val();
    param.verSerNm  = $("#verSerNm").val();
    param.listScale = "10";
    param.curr      = index;

    $.postJSON("${baseUrl}" + "list.sb", param, function(result) {
      var list = result.data.list;
      if(list.length === undefined || list.length == 0) {
        s_alert.pop(result.message);
        grid1.itemsSource = new wijmo.collections.CollectionView([]);
        grid2.itemsSource = new wijmo.collections.CollectionView([]);
        return;
      }
      grid1.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
    },
      function(result) {
        s_alert.pop(result.message);
        return;
    });
  }

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  $(document).on("click", ".page2", function() {
    searchStore($(this).data("value"));
  });

  <%-- 엑셀다운로드 --%>
  $("#btnExcel1").click(function(e){
    var name = "${menuNm}";
    wexcel.down(grid1, name, name + ".xlsx");
  });

  $("#btnExcel2").click(function(e){
    var name = "${menuNm}";
    wexcel.down(grid2, name, name + ".xlsx");
  });

</script>
