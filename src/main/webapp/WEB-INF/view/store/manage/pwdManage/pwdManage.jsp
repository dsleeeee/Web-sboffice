<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>
<c:set var="baseUrl" value="/store/manage/pwdManage/pwdManage/"/>

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
        <th><s:message code="pwdManage.hqOfficeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeCd"></div>
          </div>
        </td>
        <%-- 본사명 --%>
        <th><s:message code="pwdManage.hqOfficeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchHqOfficeNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 매장코드 --%>
        <th><s:message code="pwdManage.storeCd" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreCd"></div>
          </div>
        </td>
        <%-- 매장명 --%>
        <th><s:message code="pwdManage.storeNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchStoreNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 사용자ID --%>
        <th><s:message code="pwdManage.userId" /></th>
        <td>
          <div class="sb-select">
            <div id="srchUserId"></div>
          </div>
        </td>
        <%-- 사용자명 --%>
        <th><s:message code="pwdManage.userNm" /></th>
        <td>
          <div class="sb-select">
            <div id="srchUserNm"></div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>

  <%-- 조회 --%>
  <div class="mt10 pdb20 oh">
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

<%-- 비밀번호 변경 레이어 --%>
<div id="pwdModifyTent" class="fullDimmed" style="display: none;"></div>
<div id="pwdModifyLayer" class="layer" style="display: none;">
  <div class="layer_inner">
    <div class="title w300">
      <%-- 비밀번호 변경 --%>
      <p class="tit"><s:message code="pwdManage.modify" /></p>
      <a href="javascript:;" class="btn_close pwdModifyClose"></a>
      <input id="pwdModifyUserId" style="display: none;" />
      <div class="con">
        <div>
          <table class="tblType01">
            <colgroup>
              <col class="w40" />
              <col class="w60" />
            </colgroup>
            <tbody>
              <%-- 사용자ID --%>
              <tr>
                <th><s:message code="pwdManage.userId" /></th>
                <td id="layerUserId"></td>
              </tr>
              <%-- 사용자명 --%>
              <tr>
                <th><s:message code="pwdManage.userNm" /></th>
                <td id="layerUserNm"></td>
              </tr>
              <%-- 새비밀번호 --%>
              <tr>
                <th><s:message code="pwdManage.newPassword" /></th>
                <td>
                    <input id="layerNewPassword" type="password" maxlength="16"/>
                    <span id="newPasswordError" class="errorMsg" style="display: none"></span>
                </td>
              </tr>
              <%-- 새비밀번호확인 --%>
              <tr>
                <th><s:message code="pwdManage.confirmPassword" /></th>
                <td>
                    <input id="layerConfirmPassword" type="password" maxlength="16" />
                    <span id="confirmPasswordError" class="errorMsg" style="display: none"></span>
                </td>
              </tr>
            </tbody>
          </table>
          <p class="mt20 s12">
            <s:message code="login.layer.pwchg.policy" arguments="6,20" />
          </p>
        </div>
      </div>
      <div id="viewBtnArea" class="mt10 tc">
        <%-- 변경하기 --%>
        <button class="btn_skyblue" id="btnModify"><s:message code="pwdManage.modifyBtn" /></button>
      </div>
    </div>
  </div>
</div>

<script>
    var popupCnt = 0;
    var grid;
	$(document).ready(function() {

	    var srchHqOfficeCd = wcombo.genInput("#srchHqOfficeCd");
	    var srchHqOfficeNm = wcombo.genInput("#srchHqOfficeNm");
	    var srchStoreCd    = wcombo.genInput("#srchStoreCd");
	    var srchStoreNm    = wcombo.genInput("#srchStoreNm");
	    var srchUserId     = wcombo.genInput("#srchUserId");
	    var srchUserNm     = wcombo.genInput("#srchUserNm");

	    <%-- dataMap --%>
	    var serviceFg = ${ccu.getCommCodeExcpAll("008")};
	    var webUseYn = ${ccu.getCommCodeExcpAll("904")};
	    var serviceFgDataMap = new wijmo.grid.DataMap(serviceFg, 'value', 'name');
	    var webUseYnDataMap = new wijmo.grid.DataMap(webUseYn, 'value', 'name');

		var rdata =
		    [
		      { binding:"hqOfficeCd",header:"<s:message code='pwdManage.hqOfficeCd' />", width:"*" },
		      { binding:"hqOfficeNm",header:"<s:message code='pwdManage.hqOfficeNm' />", width:"*" },
		      { binding:"storeCd",header:"<s:message code='pwdManage.storeCd' />", width:"*" },
		      { binding:"storeNm",header:"<s:message code='pwdManage.storeNm' />", width:"*" },
		      { binding:"userId",header:"<s:message code='pwdManage.userId' />", width:"*" },
		      { binding:"userNm",header:"<s:message code='pwdManage.userNm' />", width:"*" },
		      { binding:"serviceFg",header:"<s:message code='pwdManage.serviceFg' />", dataMap:serviceFgDataMap, width:"*" },
		      { binding:"webUseYn",header:"<s:message code='pwdManage.webUseYn' />", dataMap:webUseYnDataMap, width:"*" },
		      { binding:"emailAddr",header:"<s:message code='pwdManage.emailAddr' />", width:"*" },
		      { binding:"mpNo",header:"<s:message code='pwdManage.mpNo' />", width:"*" },
		      { binding:"addr",header:"<s:message code='pwdManage.addr' />", width:"*" }
		    ];

		grid         = wgrid.genGrid("#theGrid", rdata, "${menuCd}", 1, ${clo.getColumnLayout(1)});
		grid.autoSizeColumn(1, true);
	    var ldata        = ${ccu.getListScale()};
	    var listScaleBox = wcombo.genCommonBox("#listScaleBox", ldata);

	    <%-- 그리드 포맷 --%>
	    grid.formatItem.addHandler(function(s, e) {
	      if (e.panel == s.cells) {
	        var col = s.columns[e.col];
	        var item = s.rows[e.row].dataItem;
	        if( col.binding == "userId" ) {
	          wijmo.addClass(e.cell, 'wijLink');
	        }
	      }
	    });

	    <%-- 그리드 선택 이벤트 --%>
	    grid.addEventListener(grid.hostElement, 'click', function(e) {
	      var ht = grid.hitTest(e);
	      if( ht.cellType == wijmo.grid.CellType.Cell) {
	          var col = ht.panel.columns[ht.col];
	          // 사용자ID
	          if( col.binding == "userId" ) {
	            selectedRow = grid.rows[ht.row].dataItem;
	            showPwdManageLayer(selectedRow);
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
	      param.userId     = srchUserId.text;
	      param.userNm     = srchUserNm.text;
	      param.listScale  = listScaleBox.selectedValue;
	      param.curr       = index;

	      $.postJSON("/store/manage/pwdManage/pwdManage/list.sb", param, function(result) {
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
	      }
	      ,function(){
	          s_alert.pop("Ajax Fail");
	      });
	    };

	    <%-- 레이어영역 시작 --%>

	    $(".pwdModifyClose").click(function(e){
	      closePwdManageLayer();
	    });

	    function showPwdManageLayer(data) {
	      $("#pwdModifyTent").show();
	      $("#pwdModifyLayer").show();

	      $("#pwdModifyUserId").val(data.userId);
	      $("#layerUserId").text(data.userId);
	      $("#layerUserNm").text(data.userNm);

	    }

	    function closePwdManageLayer() {
	      <%-- 비밀번호 값 초기화 --%>
	      $("#layerNewPassword").val("");
	      $("#layerConfirmPassword").val("");
	      <%-- 창닫기 --%>
	      $("#pwdModifyTent").hide();
	      $("#pwdModifyLayer").hide();
	    }

	    <%-- 비밀번호 변경 --%>
	    $("#btnModify").click(function(e) {

	      var param = {};
	      param.userId = $("#pwdModifyUserId").val();
	      param.newPassword = $("#layerNewPassword").val();
	      param.confirmPassword = $("#layerConfirmPassword").val();

	      $.postJSON("/store/manage/pwdManage/pwdManage/modify.sb", param,
	        function(result) {
	          var msg = "<s:message code='pwdManage.modifySucc'/>";
	          s_alert.popOk(msg, function() {
	            closePwdManageLayer();
	          });
	        },
	        function(result) {
	          processError(result.data);
	        }
	      );

	    });

	    function processError(data) {

	      if (data.newPassword != undefined) {
	        $("#newPasswordError").text(data.newPassword != undefined ? data.newPassword : "");
	        $("#newPasswordError").show();
	      } else {
	        $("#newPasswordError").hide();
	      }

	      if (data.confirmPassword != undefined) {
	        $("#confirmPasswordError").text(
	            data.confirmPassword != undefined ? data.confirmPassword : "");
	        $("#confirmPasswordError").show();
	      } else {
	        $("#confirmPasswordError").hide();
	      }

	      if (data.msg != undefined) {
	        s_alert.pop(data.msg);
	      }
	    }

	});

</script>
