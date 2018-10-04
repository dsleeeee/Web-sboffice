<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd" value="${sessionScope.sessionInfo.currentMenu.resrceCd}"/>
<c:set var="menuNm" value="${sessionScope.sessionInfo.currentMenu.resrceNm}"/>

<div class="subCon">
  <div class="searchBar flddUnfld">
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
        <%--등록일자 --%>
        <th><s:message code="hqEmp.regDt" /></th>
        <td colspan="3">
          <div class="sb-select">
            <span class="txtIn"><input id="startDt" name="startDt" class="w200" /></span>
            <span class="rg">~</span>
            <span class="txtIn"><input id="endDt" name="endDt" class="w200" /></span>
            <span class="chk ml10"><input type="checkbox" id="chkDt" />
            <label for="chkDt"><s:message code="cmm.all.day" /></label>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%--사원번호 --%>
        <th><s:message code="hqEmp.empNo" /></th>
        <td>
          <div class="sb-select">
            <div id="empNo"></div>
          </div>
        </td>
        <%-- 사원명 --%>
        <th><s:message code="hqEmp.empNm" /></th>
        <td>
          <div class="sb-select">
            <div id="empNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%--사원ID --%>
        <th><s:message code="hqEmp.userId" /></th>
        <td>
          <div class="sb-select">
            <div id="userId"></div>
          </div>
        </td>
        <%-- 재직여부 --%>
        <th><s:message code="hqEmp.serviceFg" /></th>
        <td>
          <div class="sb-select">
            <div id="serviceFg"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%--휴대폰번호 --%>
        <th><s:message code="hqEmp.mpNo" /></th>
        <td>
          <div class="sb-select">
            <div id="mpNo"></div>
          </div>
        </td>
        <%-- 웹사용여부 --%>
        <th><s:message code="hqEmp.webUseYn" /></th>
        <td>
          <div class="sb-select">
            <div id="webUseYn"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%--SMS 수신여부 --%>
        <th><s:message code="hqEmp.smsRecvYn" /></th>
        <td>
          <div class="sb-select">
            <div id="smsRecvYn"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <div class="mt10 pdb20 oh">
    <%-- 조회 --%>
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search" /></button>
  </div>

  <div class="mt40 oh sb-select dkbr">
    <%--페이지 스케일 --%>
    <div id="listScaleBox" class="w130 fl"></div>
    <div class="tr">
      <%-- 본사신규등록 --%>
<c:if test="${orgnFg == 'HQ'}">
      <button class="btn_skyblue" id="btnRegist"><s:message code="hqEmp.hqEmpReg" /></button>
</c:if>
      <%-- sms 전송 //TODO --%>
      <button class="btn_skyblue" id="btnSMS"><s:message code="hqEmp.sendSMS" /></button>
      <%-- 엑셀다운로드 //TODO --%>
      <%--<button class="btn_skyblue" id="btnExcel"><s:message code="cmm.excel.down" /></button>--%>
    </div>
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

  var hdata =
    [
      {"binding":"rnum","header":"No"},
      {"binding":"empNo","header":"<s:message code='hqEmp.empNo' />", width: '*'},
      {"binding":"empNm","header":"<s:message code='hqEmp.empNm' />", width: '*'},
      {"binding":"userId","header":"<s:message code='hqEmp.userId' />", width: '*'},
      {"binding":"serviceFgNm","header":"<s:message code='hqEmp.serviceFg' />", width: '*'},
      {"binding":"mpNo","header":"<s:message code='hqEmp.mpNo' />", width: '*'},
      {"binding":"smsRecvYn","header":"<s:message code='hqEmp.smsChk' />", dataType:wijmo.DataType.Boolean, width: '*', visible:false}

    ];

  var grid           = wgrid.genGrid("#theGrid", hdata,"${menuCd}", 1, ${clo.getColumnLayout(1)});
  var ldata          = ${ccu.getListScale()};
  var fireFgData     = ${ccu.getCommCode("007")};
  var webUseYnData   = ${ccu.getCommCode("067")};
  var smsRecvYnData  = ${ccu.getCommCode("072")};
  var listScaleBox   = wcombo.genCommonBox("#listScaleBox", ldata);
  var startDt        = wcombo.genDateVal("#startDt", "${sessionScope.sessionInfo.startDt}");
  var endDt          = wcombo.genDateVal("#endDt", "${sessionScope.sessionInfo.endDt}");
  var empNo          = wcombo.genInput("#empNo");
  var empNm          = wcombo.genInput("#empNm");
  var userId         = wcombo.genInput("#userId");
  var serviceFg      = wcombo.genCommonBox("#serviceFg",fireFgData);
  var mpNo           = wcombo.genInput("#mpNo");
  var webUseYn       = wcombo.genCommonBox("#webUseYn",webUseYnData);
  var smsRecvYn      = wcombo.genCommonBox("#smsRecvYn",smsRecvYnData);

  <%-- 그리드 포맷 --%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "empNm" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'click', function(e) {
    var ht = grid.hitTest(e);
    if ( ht.cellType == wijmo.grid.CellType.Cell ) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "empNm" ) {
        selectedRow = grid.rows[ht.row].dataItem;
        openDtlLayer(selectedRow);
      }
    }
  });

  function search(index) {
    var param = {};

    param.startDt = getDate(startDt);
    param.endDt = getDate(endDt);
    param.chkDt = $('#chkDt').is(":checked");
    param.empNo = empNo.text;
    param.empNm = empNm.text;
    param.userId = userId.text;
    param.serviceFg = serviceFg.selectedValue;
    param.mpNo = mpNo.text;
    param.webUseYn = webUseYn.selectedValue;
    param.smsRecvYn = smsRecvYn.selectedValue;
    param.listScale = listScaleBox.selectedValue;
    param.curr = index;

    $.postJSON("/base/store/emp/hq/list.sb", param, function(result) {
      var list = result.data.list;

      if(list.length == 0) {
        s_alert.pop(result.message);
      }

      grid.itemsSource = list;
      page.make("#page1", result.data.page.curr, result.data.page.totalPage);
      },
      function(result){
        var resultKey;
        for(var k in result.data) {
          resultKey = k;
        }

        s_alert.pop(result.data[resultKey]);
      }
      ,function(){

        s_alert.pop("Ajax Fail");
    });
  }

  <%-- 리스트 조회 --%>
  $("#btnSearch").click(function( e ){
    search(1);
  });

  <%-- 엑셀 다운로드 --%>
  $("#excelBtn").click(function( e ){
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });

  <%-- 페이징 --%>
  $(document).on("click", ".page1", function() {
    search($(this).data("value"));
  });

  <%-- 전체기간 체크박스 --%>
  $(document).on("click", "#chkDt", function() {
    var chkDt = $('#chkDt').is(":checked");
    startDt.isDisabled = chkDt;
    endDt.isDisabled = chkDt;
  });

  <%-- 본사신규등록 버튼 클릭--%>
  $("#btnRegist").click(function(){
    openRegistLayer();
  });

  <%-- SMS전송 버튼 클릭--%>
  $("#btnSMS").click(function(){
    s_alert.pop("서비스 준비중입니다.");
  });

  <%-- 엑셀 다운로드 버튼 클릭 --%>
  $("#btnExcel").click(function(){
    var name = "${menuNm}";
    wexcel.down(grid, name, name + ".xlsx");
  });

</script>

 <%-- 사원신규등록 레이어 팝업 --%>
 <c:import url="/WEB-INF/view/base/store/emp/hqEmpSave.jsp">
 </c:import>

 <%-- 사원상세/수정 팝업 --%>
 <c:import url="/WEB-INF/view/base/store/emp/hqEmpDetail.jsp">
 </c:import>

