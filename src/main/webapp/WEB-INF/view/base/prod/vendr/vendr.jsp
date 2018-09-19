<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>

<div class="subCon">
  <%--searchTbl--%>
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
        <%-- 거래처코드 --%>
        <th><s:message code="vendr.vendrCd" /></th>
        <td>
          <div class="sb-select">
            <div id="vendrCd"></div>
          </div>
        </td>
        <%-- 거래처명 --%>
        <th><s:message code="vendr.vendrNm" /></th>
        <td>
          <div class="sb-select">
            <div id="vendrNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 거래처구분 --%>
        <th><s:message code="vendr.vendorFg" /></th>
        <td>
          <div class="sb-select">
            <div id="vendorFg"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>
  <%--//searchTbl--%>
  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="searchBtn">
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>

    <%-- 거래처 등록 --%>
    <button class="btn_skyblue fr" id="regVendr">
      <s:message code="vendr.reg" />
    </button>

  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid" style="height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="page1" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>

  <%-- 선택된 거래처 --%>
  var vendr;

  <%-- 검색조건 및 dataMap 조회 --%>
  var vendrCd  = wcombo.genInput("#vendrCd");
  var vendrNm  = wcombo.genInput("#vendrNm");
  var cdata    = ${ccu.getCommCodeSelect("011")};
  var vendorFg = wcombo.genCommonBox("#vendorFg", cdata);
  var ldata    = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
  var vendorFgNm = ${ccu.getCommCodeExcpAll("011")};
  var useYn      = ${ccu.getCommCodeExcpAll("067")};
  var vendorFgDataMap = new wijmo.grid.DataMap(vendorFgNm, 'value', 'name');
  var useYnDataMap    = new wijmo.grid.DataMap(useYn, 'value', 'name');

  var rdata =
  [
    {binding:"rnum",header:"No",width:"*"},
    {binding:"vendrCd",header:"<s:message code='vendr.vendrCd' />",width:"*"},
    {binding:"vendrNm",header:"<s:message code='vendr.vendrNm' />",width:"*"},
    {binding:"bizNo",header:"<s:message code='vendr.bizNo' />",width:"*"},
    {binding:"vendorFg",header:"<s:message code='vendr.vendorFg' />",dataMap:vendorFgDataMap,width:"*"},
    {binding:"ownerNm",header:"<s:message code='vendr.ownerNm' />",width:"*"},
    {binding:"telNo",header:"<s:message code='vendr.telNo' />",width:"*"},
    {binding:"addr",header:"<s:message code='vendr.addr' />",width:"*"},
    {binding:"useYn",header:"<s:message code='vendr.useYn' />",dataMap:useYnDataMap,width:"*"}
  ];

  var grid = wgrid.genGrid( "#theGrid", rdata);

  function search(index) {
    var param = {};

    param.vendrCd = vendrCd.text;
    param.vendrNm = vendrNm.text;
    param.vendorFg = vendorFg.selectedValue;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/base/prod/vendr/vendr/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length == 0) {
        s_alert.pop(result.message);
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result){
        s_alert.pop(result.data.msg);
      })
      .fail(function(){
        s_alert.pop("Ajax Fail");
    });
  }

  <%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "vendrCd" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'click', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
        var col = ht.panel.columns[ht.col];
        // 거래처코드
        if( col.binding == "vendrCd" ) {
            vendr = grid.rows[ht.row].dataItem;
            openDtlLayer(vendr);
        }
      }
  });

  <%-- 리스트 조회 --%>
  $("#searchBtn").click(function( e ){
    search(1);
  });

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  <%-- 거래처등록 --%>
  $("#regVendr").bind("click", function() {
    vendr = null;
    openRegistLayer("reg");
  });

  function hideVendr(){
    $("#layerVendr").hide();
    $("#dimVendr").hide();
  }

</script>
<c:import url="/WEB-INF/view/base/prod/vendr/regist.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
<c:import url="/WEB-INF/view/base/prod/vendr/trtmnt.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
</c:import>
