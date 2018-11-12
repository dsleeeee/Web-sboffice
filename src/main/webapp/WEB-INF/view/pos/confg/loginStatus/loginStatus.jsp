<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ufn" uri="solbipos/function" %>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">
  <%-- 조회조건 --%>
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
      <%-- 조회 일자 --%>
      <tr>
        <th><s:message code="cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
            <span class="chk ml10"> <input type="checkbox" id="chkDt" />
            <label for="chkDt"><s:message code="cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="cmm.hedofc.cd" /></th>
        <td>
          <input type="text" id="hqOfficeCd" name="hqOfficeCd" class="sb-input" maxlength="7" size="50">
        </td>
        <%-- 본사명 --%>
        <th><s:message code="cmm.hedofc.nm" /></th>
        <td>
          <input type="text" id="hqOfficeNm" name="hqOfficeNm" class="sb-input" maxlength="16" size="50">
        </td>
      </tr>
      <tr>
        <%-- 매장 코드 --%>
        <th><s:message code="cmm.mrhst.cd" /></th>
        <td>
          <input type="text" id="storeCd" name="storeCd" class="sb-input" maxlength="5" size="50">
        </td>
        <%-- 매장명 --%>
        <th><s:message code="cmm.mrhst.nm" /></th>
        <td>
          <input type="text" id="storeNm" name="storeNm" class="sb-input" maxlength="16" size="50">
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="cmm.status" /></th>
        <td>
          <div class="sb-select">
            <div id="sysStatFg"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt5 pdb20 oh">
    <button class="btn_blue fr" id="searchBtn">
      <s:message code="cmm.search" />
    </button>
  </div>

  <div class="mt5 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w100px fl"></div>
    <%-- 엑셀 다운로드 //TODO --%>
    <%--
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="cmm.excel.down" />
    </button>
    --%>
  </div>

  <%-- 위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <div id="theGrid" style="height:393px;"></div>
  </div>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="page1" data-size="10">
    </ul>
  </div>

</div>

<script>
$(document).ready(function(){

  var rdata =
    [
      {binding:"gChk",header:"No", visible:false},
      {binding:"hqOfficeCd",header:"<s:message code='loginStatus.hqOfficeCd' />", width:90, align:"center"},
      {binding:"hqOfficeNm",header:"<s:message code='loginStatus.hqOfficeNm' />", width:120, align:"center"},
      {binding:"storeCd",header:"<s:message code='loginStatus.storeCd' />", width:80, align:"center"},
      {binding:"storeNm",header:"<s:message code='loginStatus.storeNm' />", width:"*", align:"center"},
      {binding:"posNo",header:"<s:message code='loginStatus.posNo' />", width:60, align:"center"},
      {binding:"loginDate",header:"<s:message code='loginStatus.loginDate' />", width:80, align:"center"},
      {binding:"hwAuthKey",header:"<s:message code='loginStatus.hwAuthKey' />", width:130, align:"center"},
      {binding:"loginIp",header:"<s:message code='loginStatus.loginIp' />", width:90, align:"center"},
      {binding:"loginDt",header:"<s:message code='loginStatus.loginDt' />", width:120, align:"center"},
      {binding:"posVerNo",header:"<s:message code='loginStatus.posVerNo' />", width:100, align:"center"},
      {binding:"sysStatFgNm",header:"<s:message code='loginStatus.sysStatFgNm' />", width:75, align:"center"}
    ];

  var grid         = wgrid.genGrid("#theGrid", rdata);
  var startDate    = wcombo.genDateVal("#startDate", "${ufn:addDaysString( ufn:currentDateString() , -7)}");
  var endDate      = wcombo.genDateVal("#endDate", "${sessionScope.sessionInfo.endDate}");
  var ldata        = ${ccu.getListScale()};
  var cdata        = ${ccu.getCommCode("005")};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
  var sysStatFg    = wcombo.genCommonBox("#sysStatFg", cdata);

  function search(index) {
    var param = {};

    param.startDate = getDate(startDate);
    param.endDate = getDate(endDate);
    param.chkDt = $('#chkDt').is(":checked");
    param.hqOfficeCd = $("#hqOfficeCd").val();
    param.hqOfficeNm = $("#hqOfficeNm").val();
    param.storeCd = $("#storeCd").val();
    param.storeNm = $("#storeNm").val();
    param.sysStatFg = sysStatFg.selectedValue;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/pos/confg/loginStatus/loginStatus/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length == 0) {
        s_alert.pop(result.message);
        grid.itemsSource = [];

        return false;
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result){
        s_alert.pop(result.message);
      }
    );
  }

  <%-- 리스트 조회 --%>
  $("#searchBtn").click(function( e ){
    search(1);
  });

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  <%-- 전체기간 체크박스 --%>
  $(document).on("click", "#chkDt", function() {
    var chkDt = $('#chkDt').is(":checked");
    startDate.isDisabled = chkDt;
    endDate.isDisabled = chkDt;
  });

});

</script>
