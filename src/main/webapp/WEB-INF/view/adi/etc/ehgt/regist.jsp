<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/adi/etc/ehgt/regist" />

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />

<style>
.font .wj-form-control{font-size:0.75em; color:#222}
</style>
<div class="subCon">
  <%--tab---%>
  <ul class="subTab mb20">
    <li><a href="javascript:;" class="on"><s:message code="ehgt.tabRegist" /></a></li>
    <%--<li><a href="#"><s:message code="ehgt.tabGoodsPrice" /></a></li> --%>
  </ul>
  <%--//tab---%>

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
        <th>
          <s:message code="cmm.search.date" />
        </th>
        <td colspan="3">
          <%-- 조회 일자 --%>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 oh pdb20 bb">
    <button id="btnSearch" class="btn_blue fr"><s:message code="cmm.search" /><%--조회--%></button>
  </div>

<c:if test="${orgnFg == 'HQ'}">
  <div class="mt20 tr">
    <button id="btnRegist" class="btn_skyblue"><s:message code="ehgt.btnCrncyRegist" /><%--통화구분등록--%></button>
  </div>
</c:if>

<c:if test="${orgnFg == 'HQ'}">
  <%--2단--%>
  <div class="wj-TblWrap mt20 oh">
    <%--left--%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr mr10" style="height:400px;">
        <h3 class="h3_tbl2 pdt5 lh30 mt20">
            <s:message code="ehgt.labelCrncyNew" />
            <span class="fr"><a id="btnSave" href="javascript:;" class="btn_grayS"><s:message code="cmm.save"/></a></span>
        </h3>
        <table class="searchTbl2">
        <colgroup>
        <col class="w20" />
        <col class="w15" />
        <col class="w5" />
        <col class="w20" />
        <col class="w30" />
        <col class="w10" />
        </colgroup>
        <thead>
        <tr>
          <th colspan="6">
            <s:message code="ehgt.date"/>
            <input id="saleDate" class="w120 font" />
          </th>
        </tr>
        </thead>
        <tbody>
      <c:forEach var="item" items="${hqCrncys}">
        <tr>
          <td class="tc">${item.nmcodeItem2}</td>
          <td class="tr">${item.nmcodeItem1} &nbsp; ${item.nmcodeNm}</td>
          <td class="tc">=</td>
          <td class="tc"><s:message code="ehgt.korea"/></td>
          <td class="tc"><input type="text" id="krwAmt_${item.nmcodeNm}" data-cd="${item.nmcodeNm}" data-unit="${item.nmcodeItem1}" class="crncy-item sb-input w100 tr" /></td>
          <td class="tc">KRW</td>
        </tr>
      </c:forEach>
        </tbody>
        </table>
      </div>
    </div>
    <%--//left--%>

    <%--right--%>
    <div class="w50 fr">
      <div class="wj-TblWrapBr ml10 pd20" style="height:400px;">
        <%--위즈모 테이블--%>
        <div id="theGrid"></div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
    <%--//right--%>
  </div>
  <%--//2단--%>
</c:if>

<c:if test="${orgnFg != 'HQ'}">

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height:400px;">
    <div id="theGrid" class="pd20"></div>
  </div>
  <%--//위즈모 테이블--%>

</c:if>

</div>

<%-- 통화구분등록 --%>
<c:if test="${orgnFg == 'HQ'}">
<c:import url="/WEB-INF/view/adi/etc/ehgt/crncy.jsp"/>
</c:if>


<script>

  $(document).ready(function() {
    
    <%-- 환율관리 일자별 환율 --%>
    var rdata = 
      [
        {binding: "saleDate", header: "<s:message code='ehgt.date' />", allowMerging: true, format: 'yyyy-MM-dd', width: "*"}

      <c:forEach var="item" items="${hqCrncys}">
        ,{binding: "crncy${item.nmcodeNm}", header: "${item.nmcodeItem1} ${item.nmcodeNm}", dataType:wijmo.DataType.Number, format:"n2", width: "*"}
      </c:forEach>

      ];
    
    var grid         = wgrid.genGrid("#theGrid", rdata, false);
    var startDt      = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
    var endDt        = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
    
    <%-- Row Header 없애기 --%>
    grid.rowHeaders.columns.splice(0, 1);
    
<c:if test="${orgnFg == 'HQ'}">
    
    var saleDate     = wcombo.genDate("#saleDate");
    
    <%-- TODO 그리드 포맷 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "saleDate" ) {
          wijmo.addClass(e.cell, 'wijLink');
          //item.saleDate = wijmo.Globalize.parseDate(item.saleDate, 'yyyy-MM-dd');
          //console.log(item);
        }
      }
    });
    
    <%-- 그리드 선택 이벤트 --%>
    grid.addEventListener(grid.hostElement, 'click', function(e) {
      var ht = grid.hitTest(e);
      if ( ht.cellType == wijmo.grid.CellType.Cell ) {
        var col = ht.panel.columns[ht.col];
        if( col.binding == "saleDate" ) {
          var selectedRow = grid.rows[ht.row].dataItem;
          var param = {};
          param.saleDate = selectedRow.saleDate;
          
          if(param.saleDate != '') {
            $.postJSON("${baseUrl}/detail.sb", param, function(result) {
              var list = result.data.list;
              saleDate.value = wijmo.Globalize.parseDate(selectedRow.saleDate, 'yyyyMMdd');
              $.each(list, function(key, value){
                $("#krwAmt_" + value.crncyCd).val(value.krwAmt);
              });
            },
            function(result) {
              s_alert.pop(result.data.msg);
            });
          }

        }
      }
    });
    
    <%-- 환율 저장 --%>
    $("#btnSave").click(function(e){
      var paramArr = new Array();
      $(".crncy-item").each(function(index, element) {
        paramArr.push({
          saleDate:getDate(saleDate),
          crncyCd:$(this).data("cd"),
          crncyAmt:$(this).data("unit"),
          krwAmt:$(this).val()
        });
      });
      
      $.postJSONArray("${baseUrl}/save.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
      },
      function(result) {
        s_alert.pop(result.data.msg);
      });
    });
    
    <%-- 통화 구분 등록 팝업 --%>
    $("#btnRegist").click(function(e){
      _showCrncyRegistLayer();
    });

</c:if>

    <%-- 다중 헤더 --%>
    var headerCols = new Array();
    <c:forEach var="item" items="${hqCrncys}">
    headerCols.push("${item.nmcodeItem2}");
    </c:forEach>
    
    var hr = new wijmo.grid.Row();
    grid.columnHeaders.rows.push(hr);
    
    var idx = 0;
    grid.allowMerging = wijmo.grid.AllowMerging.ColumnHeaders;
    for (var row = 0; row < grid.columnHeaders.rows.length; row++) {
      grid.columnHeaders.rows[row].allowMerging = true;
      for (var col = 0; col < grid.columns.length; col++) {
        if(col == 0) {
          grid.columnHeaders.setCellData(row, col, "<s:message code='ehgt.date'/>");
        }
        
        if(col != 0 && row == 0) {
          grid.columnHeaders.setCellData(row, col, headerCols[idx++]);
        }
      }
    }
    
    <%-- 리스트 조회 --%>
    $("#btnSearch").click(function(e){
      search();
    });

    <%-- 리스트 조회 --%>
    function search() {
      var param = {};
      param.startDt = getDate(startDt);
      param.endDt = getDate(endDt);
      wgrid.getGridData("${baseUrl}/list.sb", param, grid);
    }

  });
</script>

