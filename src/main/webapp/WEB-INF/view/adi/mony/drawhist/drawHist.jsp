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
      <%-- 조회 일자 --%>
      <tr>
        <th><s:message code="cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
          </div>
        </td>
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
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid" style="height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page1" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>
</div>

<script>
$(document).ready(function(){

  var cashDrawOpenFg = ${ccu.getCommCodeExcpAll("103")};
  var cashDrawOpenFgDataMap = new wijmo.grid.DataMap(cashDrawOpenFg, 'value', 'name');
  var rdata = 
    [
      {"binding":"saleDate","header":"<s:message code='drawHist.sale.date' />",width:"*"},                  // 일자
      {"binding":"posNo","header":"<s:message code='drawHist.pos.no' />",width:"*"},                        // 포스번호
      {"binding":"cashDrawOpenSeq","header":"<s:message code='drawHist.cash.draw.open.seq' />",width:"*"},  // 일련번호
      {"binding":"cashDrawOpenDt","header":"<s:message code='drawHist.cash.draw.open.dt' />",width:"*"},    // 돈통오픈일시
      {"binding":"cashDrawOpenFg","header":"<s:message code='drawHist.cash.draw.open.fg' />",dataMap: cashDrawOpenFgDataMap, width:"*"},    // 구분
      {"binding":"empNm","header":"<s:message code='drawHist.emp.nm' />",width:"*"},                        // 판매원
      {"binding":"billNo","header":"<s:message code='drawHist.bill.no' />",width:"*"},                      // 영수번호
      {"binding":"totSaleAmt","header":"<s:message code='drawHist.total.sale'/>",width:"*"},
      {"binding":"totDcAmt","header":"<s:message code='drawHist.total.discount'/>",width:"*"},
      {"binding":"realSaleAmt","header":"<s:message code='drawHist.real.sale'/>",width:"*"}
   ];
  
  var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
  var startDt      = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
  var endDt        = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
  var ldata        = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
  var cdata        = ${ccu.getCommCode("009")};
  
  function search(index) {
    var param = {};

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.chkDt = $('#chkDt').is(":checked");
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;
    
    $.postJSON("/adi/mony/drawhist/drawhist/list.sb", param, function(result) {
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
  
  <%-- 리스트 조회 --%>
  $("#searchBtn").click(function( e ){
    search(1);
  });
  
  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });
});
</script>
