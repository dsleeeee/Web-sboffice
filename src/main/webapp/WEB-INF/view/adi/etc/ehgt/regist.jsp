<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/adi/etc/ehgt/regist" />

<c:set var="orgnFg" value="${sessionScope.sessionInfo.orgnFg}" />
<c:set var="storeCd" value="${sessionScope.sessionInfo.storeCd}" />
<c:set var="isManager" value='false'/>
<c:if test="${orgnFg eq 'HQ' or storeCd eq '00000'}">
    <c:set var="isManager" value='true'/>
</c:if>

<style>
.font .wj-form-control{font-size:0.75em; color:#222}
</style>
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
        <th>
          <s:message code="cmm.search.date" />
        </th>
        <td colspan="3">
          <%-- 조회 일자 --%>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

<c:if test="${isManager}">
  <div class="mt20 tr">
    <button id="btnRegist" class="btn_skyblue"><s:message code="ehgt.btnCrncyRegist" /><%--통화구분등록--%></button>
  </div>
</c:if>

<c:if test="${isManager}">
  <%--2단--%>
  <div class="wj-TblWrap mt20 oh">
    <%--left--%>
    <div class="w50 fl">
      <div class="wj-TblWrapBr mr10" style="height:400px;">
        <h3 class="h3_tbl2 pdt5 lh30">
            <s:message code="ehgt.labelCrncyNew" />
            <span class="fr"><a id="btnSave" href="#" class="btn_grayS"><s:message code="cmm.save"/></a></span>
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
            <input id="saleDate" class="w120px font" />
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
      <div class="wj-TblWrapBr ml10" style="height:400px; overflow-x: hidden; overflow-y: hidden;">
        <%--위즈모 테이블--%>
        <div id="theGrid" style="overflow-y: auto;"></div>
        <%--//위즈모 테이블--%>
      </div>
    </div>
    <%--//right--%>
  </div>
  <%--//2단--%>
</c:if>

<c:if test="${not isManager}">

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height:400px;">
    <div id="theGrid" class="pd20"></div>
  </div>
  <%--//위즈모 테이블--%>

</c:if>

</div>

<%-- 통화구분등록 --%>
<c:if test="${isManager}">
<c:import url="/WEB-INF/view/adi/etc/ehgt/crncy.jsp"/>
</c:if>


<script>

  $(document).ready(function() {

    <%-- 환율관리 일자별 환율 --%>
    var rdata =
      [
        {binding: "saleDate", header: "<s:message code='ehgt.date' />", allowMerging: true, format: 'yyyy-MM-dd', width: "*", align: 'center'}

      <c:forEach var="item" items="${hqCrncys}">
        ,{binding: "crncy${item.nmcodeNm}", header: "${item.nmcodeItem1} ${item.nmcodeNm}", dataType:wijmo.DataType.Number, format:"n2", width: "*"}
      </c:forEach>

      ];

    var grid         = wgrid.genGrid("#theGrid", rdata, false);
    var startDate    = wcombo.genDateVal("#startDate", "${sessionScope.sessionInfo.startDate}");
    var endDate      = wcombo.genDateVal("#endDate", "${sessionScope.sessionInfo.endDate}");

    <%-- Row Header 없애기 --%>
    grid.rowHeaders.columns.splice(0, 1);

    <%-- 그리드 포맷 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "saleDate" ) {
          if(typeof item.saleDate === 'string') {
            item.saleDate = wijmo.Globalize.parseDate(item.saleDate, 'yyyyMMdd');
            //s.refresh();
          }
        }
      }
    });

<c:if test="${isManager}">

    var saleDate     = wcombo.genDate("#saleDate");

    <%-- 그리드 포맷 --%>
    grid.formatItem.addHandler(function(s, e) {
      if (e.panel == s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if( col.binding == "saleDate" ) {
          wijmo.addClass(e.cell, 'wijLink');
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
          param.saleDate = wijmo.Globalize.format(selectedRow.saleDate, 'yyyyMMdd');

          if(param.saleDate != '') {
            $.postJSON("${baseUrl}/detail.sb", param, function(result) {
              var list = result.data.list;

              saleDate.value = selectedRow.saleDate;
              $.each(list, function(key, value){
                var amt = addComma(value.krwAmt);
                $("#krwAmt_" + value.crncyCd).val(amt);
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
      var validParam = true;
      $(".crncy-item").each(function(index, element) {
        var val = $(this).val();
        val = removeComma(val);
        if(isEmpty(val) || (parseFloat(val) < 1 && parseFloat(val) > 10000)) {
          validParam = false;
          $(this).focus();
          return false;
        }
      });
      if(!validParam){
        s_alert.pop("<s:message code='cmm.input.fail'/>");
        return;
      }


      $(".crncy-item").each(function(index, element) {
        paramArr.push({
          saleDate:getDate(saleDate),
          crncyCd:$(this).data("cd"),
          crncyAmt:$(this).data("unit"),
          krwAmt:removeComma($(this).val())
        });

      });

      $.postJSONArray("${baseUrl}/save.sb", paramArr, function(result) {
        s_alert.pop("<s:message code='cmm.saveSucc' />");
        search();
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
      param.startDate = getDate(startDate);
      param.endDate = getDate(endDate);
      wgrid.getGridData("${baseUrl}/list.sb", param, grid);
      //grid.refresh();
    }

  });
</script>

