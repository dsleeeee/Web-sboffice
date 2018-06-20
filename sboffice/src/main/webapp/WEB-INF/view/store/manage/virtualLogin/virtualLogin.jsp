<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="baseUrl" value="/store/manage/virtualLogin/virtualLogin/"/>

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
        <%-- 본사코드 --%>
        <th><s:message code="virtualLogin.hqOfficeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="virtualLogin.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="virtualLogin.storeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="virtualLogin.storeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 용도 --%>
        <th><s:message code="virtualLogin.clsFgNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchClsFg"></div>
          </div>
        </td>
        <%-- 상태 --%>
        <th><s:message code="virtualLogin.sysStatFgNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchSysStatFg"></div>
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
    <div id="theGrid" style="width:100%;height:393px;"></div>
  </div>
  <%--//위즈모 테이블--%>

  <%-- 페이지 리스트 --%>
  <div class="pageNum mt20">
    <%-- id --%>
    <ul id="page" data-size="10">
    </ul>
  </div>
  <%--//페이지 리스트--%>

</div>

<script>
    var popupCnt = 0;
    
	$(document).ready(function() {
	  
	    var clsFg          = ${ccu.getCommCodeSelect("003")};
	    var sysStatFg      = ${ccu.getCommCodeSelect("005")};
	    
	    var srchHqOfficeCd = wcombo.genInput("#srchHqOfficeCd");
	    var srchHqOfficeNm = wcombo.genInput("#srchHqOfficeNm");
	    var srchStoreCd    = wcombo.genInput("#srchStoreCd");
	    var srchStoreNm    = wcombo.genInput("#srchStoreNm");
	    var srchClsFg      = wcombo.genCommonBox("#srchClsFg", clsFg);
	    var srchSysStatFg  = wcombo.genCommonBox("#srchSysStatFg", sysStatFg);
	   
		var rdata =
		    [
		      {"binding":"hqOfficeCd","header":"<s:message code='virtualLogin.hqOfficeCd' />"},
		      {"binding":"hqOfficeNm","header":"<s:message code='virtualLogin.hqOfficeNm' />"},
		      {"binding":"storeCd","header":"<s:message code='virtualLogin.storeCd' />"},
		      {"binding":"storeNm","header":"<s:message code='virtualLogin.storeNm' />"},
		      {"binding":"clsFgNm","header":"<s:message code='virtualLogin.clsFgNm' />"},
		      {"binding":"sysStatFgNm","header":"<s:message code='virtualLogin.sysStatFgNm' />"},
		      {"binding":"ownerNm","header":"<s:message code='virtualLogin.ownerNm' />"},
		      {"binding":"telNo","header":"<s:message code='virtualLogin.telNo' />"},
		      {"binding":"mpNo","header":"<s:message code='virtualLogin.mpNo' />"},
		      {"binding":"agencyNm","header":"<s:message code='virtualLogin.agencyNm' />"},
		      {"binding":"sysOpenDate","header":"<s:message code='virtualLogin.sysOpenDate' />"},
		      {"binding":"sysClosureDate","header":"<s:message code='virtualLogin.sysClosureDate' />"}
		    ];
	
		var grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
		grid.autoSizeColumn(1, true);
	    var ldata        = ${ccu.getListScale()};
	    var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);
	
	    <%-- 그리드 길이 자동 FIT --%>
	    grid.itemsSourceChanged.addHandler(function(sender, args) {
	        sender.autoSizeColumns();
	    });
	
	    <%-- 그리드 포맷 --%>
	    grid.formatItem.addHandler(function(s, e) {
	      if (e.panel == s.cells) {
	        var col = s.columns[e.col];
	        var item = s.rows[e.row].dataItem;
	        if( col.binding == "hqOfficeCd" && s.rows[e.row].dataItem.hqOfficeCd != "00000") {
	          wijmo.addClass(e.cell, 'wijLink');
	        }
	        if( col.binding == "storeCd" && s.rows[e.row].dataItem.storeCd != "00000") {
	          wijmo.addClass(e.cell, 'wijLink');
	        }
	        if( col.binding == "agencyNm" ) {
	          wijmo.addClass(e.cell, 'wijLink');
	        }
	      }
	    });
	
	    <%-- 그리드 선택 이벤트 --%>
	    grid.addEventListener(grid.hostElement, 'click', function(e) {
	      var ht = grid.hitTest(e);
	      if( ht.cellType == wijmo.grid.CellType.Cell) {
	          var col = ht.panel.columns[ht.col];
	          // 단독매장본사의 경우에는 선택 불가능
	          if( col.binding == "hqOfficeCd" && grid.rows[ht.row].dataItem.hqOfficeCd != "00000") {
	            selectedRow = grid.rows[ht.row].dataItem;
	            vLoginProcess(selectedRow.hqUserId);
	          }
	          if( col.binding == "storeCd" && grid.rows[ht.row].dataItem.storeCd != "00000") {
	            selectedRow = grid.rows[ht.row].dataItem;
	            vLoginProcess(selectedRow.msUserId);
	          }
	          if( col.binding == "agencyNm" ) {
	            selectedRow = grid.rows[ht.row].dataItem;
	            vLoginProcess(selectedRow.cmUserId);
	          }
	        }
	    });
	
	    <%-- 조회버튼 클릭 --%>
	    $("#btnSearch").click(function(e){
	      search(1);
	    });
	
	    <%-- 페이징 --%>
	    $(document).on("click", ".page", function() {
	      search($(this).data("value"));
	    });
	
	    <%-- 가상로그인 대상 목록 조회 --%>
	    function search(index) {
	      // validation 추가
	      var param = {};
	      param.hqOfficeCd = srchHqOfficeCd.text;
	      param.hqOfficeNm = srchHqOfficeNm.text;
	      param.hqStoreCd  = srchStoreCd.text;
	      param.hqStoreNm  = srchStoreNm.text;
	      param.clsFg      = srchClsFg.selectedValue;
	      param.sysStatFg  = srchSysStatFg.selectedValue;
	      param.listScale  = listScaleBox.selectedValue;
	      param.curr       = index;
	
	      $.postJSON("/store/manage/virtualLogin/virtualLogin/list.sb", param, function(result) {
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
	      })
	      .fail(function(){
	          s_alert.pop("Ajax Fail");
	      });
	    };
	    
	    function vLoginProcess(value) {
	      if ( isEmpty(value) ) {
	        alert("해당ID가 존재하지 않습니다.");
	        return false;
	      } else {
	        /* post */
	        popupCnt = popupCnt + 1
	        var form = document.createElement("form");
	        form.setAttribute("method", "POST");
	        form.setAttribute("action", "/store/manage/virtualLogin/virtualLogin/vLogin.sb");
	        form.setAttribute("target", "vLogin"+ popupCnt);
	        var formField = document.createElement("input");
	        formField.setAttribute("type", "hidden");
	        formField.setAttribute("name", "vUserId");
	        formField.setAttribute("value", value);
	        form.appendChild(formField);
	        document.body.appendChild(form);
	        var popup = window.open("", "vLogin"+ popupCnt, "width=1024, height=768");
	        var crono = window.setInterval(function() {
	          if (popup.closed !== false) { // !== opera compatibility reasons
	            window.clearInterval(crono);
	            var param = {};
	            param.vUserId  = value;
	            $.postJSON("/store/manage/virtualLogin/virtualLogin/vLogout.sb", param, function(result) {
	              if(result.status === "FAIL") {
	                s_alert.pop(result.message);
	                return;
	              }
	            })
	            .fail(function(){
	                s_alert.pop("Ajax Fail");
	            });
	          }
	        }, 250); 
	        form.submit();
	        document.body.removeChild(form);
	          
	        }
	    };
	    
	});

</script>