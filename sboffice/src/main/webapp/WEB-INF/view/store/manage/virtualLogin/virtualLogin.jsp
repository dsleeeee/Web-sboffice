<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/store/manage/virtuallogin/virtuallogin/"/>

<div class="subCon">

  <div class="searchBar">
    <a href="javascript:;" class="open"><s:message code="verRecv.verrecv" /></a>
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
          <div class="sb-select">
            <div id="verSerNo"></div>
          </div>
        </td>
        <%-- 버전적용명 --%>
        <th><s:message code="verRecv.verSerNm" /></th>
        <td>
          <div class="sb-select">
            <div id="verSerNm"></div>
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
    <%-- 엑셀 다운로드 --%>
    <button id="btnExcel" class="btn_skyblue fr"><s:message code="cmm.excel.down" /></button>
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

	var rdata =
	    [
	      {"binding":"rnum","header":"No","width":60},
	      {"binding":"hqOfficeCd","header":"<s:message code='virtualLogin.hqOfficeCd' />"},
	      {"binding":"hqOfficeNm","header":"<s:message code='virtualLogin.hqOfficeNm' />"},
	      {"binding":"storeCd","header":"<s:message code='virtualLogin.storeCd' />"},
	      {"binding":"storeNm","header":"<s:message code='virtualLogin.storeNm' />"},
	      {"binding":"clsFg","header":"<s:message code='virtualLogin.clsFg' />"},
	      {"binding":"sysStatFgNm","header":"<s:message code='virtualLogin.sysStatFgNm' />"},
	      {"binding":"ownerNm","header":"<s:message code='virtualLogin.ownerNm' />"},
	      {"binding":"telNo","header":"<s:message code='virtualLogin.telNo' />"},
	      {"binding":"mpNo","header":"<s:message code='virtualLogin.mpNo' />"},
	      {"binding":"agencyNm","header":"<s:message code='virtualLogin.agencyNm' />"},
	      {"binding":"sysOpenDate","header":"<s:message code='virtualLogin.sysOpenDate' />"},
	      {"binding":"sysClosureDate","header":"<s:message code='virtualLogin.sysClosureDate' />"},
	    ];

	var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
	grid.autoSizeColumn(1, true);
    var ldata        = ${ccu.getListScale()};
    var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);

    grid.itemsSourceChanged = function(sender, args) {
        sender.autoSizeColumns();
    };

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
      // validation 추가

      var param = {};
      param.verSerNo  = verSerNo.text;
      param.verSerNm  = verSerNm.text;
      param.listScale = listScaleBox.selectedValue;
      param.curr = index;

      $.postJSON("store/manage/virtuallogin/virtuallogin/list.sb", param, function(result) {
        if(result.status === "FAIL") {
          s_alert.pop(result.message);
          return;
        }
        var list = result.data.list;

        if(list.length === undefined || list.length == 0) {
          s_alert.pop(result.message);
          return;
        }
        grid1.itemsSource = list;

        page.make("#page", result.data.page.curr, result.data.page.totalPage);
      })
      .fail(function(){
          s_alert.pop("Ajax Fail");
      });
    }

});

</script>