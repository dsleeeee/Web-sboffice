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
        <th><s:message code="label.cmm.search.date" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"> <input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDt" name="endDt" class="w200" /></span>
            <span class="chk ml10"> <input type="checkbox" id="chkDt" /> 
            <label for="chk"><s:message code="label.cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 본사코드 --%>
        <th><s:message code="label.cmm.hedofc.cd" /></th>
        <td>
          <div class="sb-select">
            <div id="hqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="label.cmm.hedofc.nm" /></th>
        <td>
          <div class="sb-select">
            <div id="hqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장 코드 --%>
        <th><s:message code="label.cmm.mrhst.cd" /></th>
        <td>
          <div class="sb-select">
            <div id="storeCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="label.cmm.mrhst.nm" /></th>
        <td>
          <div class="sb-select">
            <div id="storeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 상태 --%>
        <th><s:message code="label.cmm.status" /></th>
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
  <%--//searchTbl--%>

  <div class="mt10 pdb20 oh bb">
    <button class="btn_blue fr" id="searchBtn">
      <s:message code="label.cmm.search" />
    </button>
  </div>

  <div class="mt20 oh sb-select dkbr">
    <%-- 페이지 스케일  --%>
    <div id="listScaleBox" class="w150 fl"></div>

    <%-- 엑셀 다운로드 --%>
    <button class="btn_skyblue fr" id="excelBtn">
      <s:message code="label.cmm.excel.down" />
    </button>
  </div>

  <%--위즈모 테이블--%>
  <div class="wj-TblWrapBr mt10" style="height: 400px;">
    <%-- 개발시 높이 조절해서 사용--%>
    <%-- tbody영역의 셀 배경이 들어가는 부분은 .bdBg를 넣어주세요. --%>
    <div id="theGrid"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <ul id="pagenav">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>
$(document).ready(function(){

  var rdata = 
    [
      {"binding":"rnum","header":"No"},
      {"binding":"hqOfficeCd","header":"<s:message code='hqOfficeCd' />"},
      {"binding":"hqOfficeNm","header":"<s:message code='hqOfficeNm' />"},
      {"binding":"storeCd","header":"<s:message code='storeCd' />"},
      {"binding":"storeNm","header":"<s:message code='storeNm' />"},
      {"binding":"posNo","header":"<s:message code='posNo' />"},
      {"binding":"loginDate","header":"<s:message code='loginDate' />"},
      {"binding":"hwAuthKey","header":"<s:message code='hwAuthKey' />"},
      {"binding":"loginIp","header":"<s:message code='loginIp' />"},
      {"binding":"loginDt","header":"<s:message code='loginDt' />"},
      {"binding":"posVerNo","header":"<s:message code='posVerNo' />"},
      {"binding":"sysStatFgNm","header":"<s:message code='sysStatFgNm' />"},
      {"binding":"loginSeq","header":"loginSeq"}
    ];
  
  var cl1 = JSON.stringify(${clo.getColumnLayout(1)});
  var viewName = "${sessionScope.sessionInfo.currentMenu.resrceCd}";
  var grid = wgrid.genGrid("#theGrid", rdata, viewName, 1, cl1);
  var hqOfficeCd = wcombo.genInput("#hqOfficeCd");
  var hqOfficeNm = wcombo.genInput("#hqOfficeNm");
  var storeCd = wcombo.genInput("#storeCd");
  var storeNm = wcombo.genInput("#storeNm");
  var startDt = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
  var endDt = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
  var ldata = ${ccu.getListScale()};
  var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
  var cdata	= ${ccu.getCommCode("009")};
  var sysStatFg	= wcombo.genCommonBox("#sysStatFg", cdata);
  
  function search(index) {
    var param = {};

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.chkDt = $('#chkDt').is(":checked");
  	param.hqOfficeCd = hqOfficeCd.text;
  	param.hqOfficeNm = hqOfficeNm.text;
  	param.storeCd = storeCd.text;
  	param.storeNm = storeNm.text;
  	param.sysStatFg = sysStatFg.selectedValue;
  	param.listScale = listScaleBox.selectedValue;
  	param.curr = index;
  	
  	$.postJSON("/pos/confg/loginstatus/loginstatus/list.sb", param, function(result) {
		var list = result.data.list;
		
		if(list.length == 0) {
		  s_alert.pop(result.message);
		}
		
		grid.itemsSource = list;
		page.make(result.data.page.curr, result.data.page.totalPage);
  	})
  	.fail(function(){
  		s_alert.pop("Ajax Fail");
  	});
  }
  
  <%-- 리스트 조회 --%>
  $("#searchBtn").click(function( e ){
    search(1);
  });
  
  <%-- 엑셀 다운로드 --%>
  $("#excelBtn").click(function( e ){
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });
  
  $(document).on("click", ".pagenav", function() {
    search($(this).data("value"));
  });
  
  $(document).on("click", "#chkDt", function() {
    var chkDt = $('#chkDt').is(":checked");
	startDt.isDisabled = chkDt;
	endDt.isDisabled = chkDt;      
  });
  
});
  
</script>



