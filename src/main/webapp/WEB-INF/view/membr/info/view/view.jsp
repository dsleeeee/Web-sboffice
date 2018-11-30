<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="menuCd">${sessionScope.sessionInfo.currentMenu.resrceCd}</c:set>
<c:set var="menuNm">${sessionScope.sessionInfo.currentMenu.resrceNm}</c:set>
<c:set var="orgnCd">${sessionScope.sessionInfo.orgnCd}</c:set>


<div class="subCon">
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
      <tr>
        <%-- 검색기간 --%>
        <th>
          <div class="sb-select">
            <div id="periodDate"></div>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn"> <input id="startDate" name="startDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="endDate" name="endDate" class="w200px" /></span>
            </span>
          </div>
        </td>
        <%-- 생일, 결혼기념일 날짜 --%>
        <th>
          <div class="sb-select">
            <div id="anvrsDate"></div>
          </div>
        </th>
        <td>
          <div class="sb-select">
            <span class="txtIn"> <input id="anvrsStartDate" name="anvrsStartDate" class="w200px" /></span>
            <span class="rg">~</span>
            <span class="txtIn"> <input id="anvrsEndDate" name="anvrsEndDate" class="w200px" /></span>
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 회원번호 --%>
        <th><s:message code="regist.membr.no"/></th>
        <td>
          <div class="sb-select">
          <div id="memberNo"></div>
          </div>
        </td>
        <%-- 회원명 --%>
        <th><s:message code="regist.membr.nm"/></th>
        <td>
          <div class="sb-select">
            <div id="memberNm"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 등록매장 --%>
        <th><s:message code="regist.reg.store.cd"/></th>
        <td>
          <div class="sb-select">
            <div id="regStore"></div>
          </div>
        </td>
        <%-- 전화번호 --%>
        <th><s:message code="regist.tel"/></th>
        <td>
          <div class="sb-select">
            <div id="telNo"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 회원카드번호 --%>
        <th><s:message code="regist.membr.card.no"/></th>
        <td>
          <div class="sb-select">
            <div id="membrCardNo"></div>
          </div>
        </td>
        <%-- E-Mail --%>
        <th>E-Mail</th>
        <td>
          <div class="sb-select">
            <div id="membrEmail"></div>
          </div>
        </td>
      </tr>
      <tr>
        <%-- 이메일 수신 --%>
        <th><s:message code="regist.email.recv"/></th>
        <td>
          <div class="sb-select">
            <div id="emailRecvYn"></div>
          </div>
        </td>
        <%-- SMS 수신 --%>
        <th><s:message code="regist.sms.recv"/></th>
        <td>
          <div class="sb-select">
            <div id="smsRecvYn"></div>
          </div>
        </td>

      </tr>
      <tr>
        <%-- 회원등급 --%>
        <%--
        <th><s:message code="regist.class.cd"/></th>
        <td>
          <div class="sb-select">
            <div id="classCd"></div>
          </div>
        </td>
        --%>
        <%-- 성별 --%>
        <th><s:message code="regist.gender"/></th>
        <td>
          <div class="sb-select">
            <div id="gender"></div>
          </div>
        </td>
        <th></th>
        <td></td>
      </tr>
    </tbody>
  </table>

  <%-- 조회버튼 --%>
  <div class="mt10 pdb20 oh">
    <button class="btn_blue fr" id="btnSearch"><s:message code="cmm.search"/></button>
  </div>

  <div class="wj-TblWrap mt40">
    <%-- left --%>
    <div class="w50 fl">
      <div class="wj-TblWㅣrapBr mr10 pd20" style="height:700px;">
        <div id="theGrid"></div>
      </div>
    </div>
    <%-- left --%>
    <c:import url="/WEB-INF/view/membr/info/view/base.jsp">
    </c:import>
  </div>
</div>

<script>
$(document).ready(function(){
  var rdata =
      [
        {binding:"membrNo",      header:"<s:message code='regist.membr.no' />"},
        {binding:"membrNm",      header:"<s:message code='regist.membr.nm' />"},
        <%--{binding:"membrClassCd", header:"<s:message code='regist.class.cd' />"},--%>
        {binding:"membrClassNm", header:"<s:message code='regist.class.nm' />"},
        {binding:"membrCardNo",  header:"<s:message code='regist.card.no' />"}
      ];
  var grid         = wgrid.genGrid("#theGrid", rdata);
  var recvData     = ${ccu.getCommCode("072")}; <%--수신, 미수신--%>
  var recvDataEx   = ${ccu.getCommCodeExcpAll("072")}; <%--수신, 미수신--%>
  var genderData   = ${ccu.getCommCode("055")}; <%--여자, 남자, 사용안함--%>
  var genderDataEx = ${ccu.getCommCodeExcpAll("055")}; <%--여자, 남자, 사용안함--%>
  var useDataEx    = ${ccu.getCommCodeExcpAll("067")}; <%--사용, 미사용--%>
  var periodDate    = ${ccu.getCommCodeExcpAll("077")}; <%--조회기간--%>
  var weddingData   = ${ccu.getCommCodeExcpAll("076")}; <%--결혼유무--%>

  <%--조회 조건 생성--%>
  var startDate    = wcombo.genDateVal("#startDate", "${sessionScope.sessionInfo.startDate}");
  var endDate      = wcombo.genDateVal("#endDate", "${sessionScope.sessionInfo.endDate}");
  var anvrsStartDate = wcombo.genDateVal("#anvrsStartDate", "${sessionScope.sessionInfo.startDate}");
  var anvrsEndDate   = wcombo.genDateVal("#anvrsEndDate", "${sessionScope.sessionInfo.endDate}");
  <%--var classCd      = wcombo.genCommonBox("#classCd", ${comboData}); //TODO 회원등급 추가 후 작업 예정 --%>
  var periodDate   = wcombo.genCommonBox("#periodDate", periodDate);
  var anvrsDate    = wcombo.genCommonBox("#anvrsDate", ${ccu.getCommCode("032")});
  var gender       = wcombo.genCommonBox("#gender", genderData);
  var emailRecvYn  = wcombo.genCommonBox("#emailRecvYn", recvData);
  var smsRecvYn    = wcombo.genCommonBox("#smsRecvYn", recvData);
  var regStore     = wcombo.genCommonBox("#regStore", ${regstrStoreListAll});
  var memberNo     = genInputText("#memberNo","10");
  var memberNm     = genInputText("#memberNm","15");
  var membrCardNo  = genInputText("#membrCardNo", "40");
  var membrEmail   = genInputText("#membrEmail", "180");
  var telNo        = genInputText("#telNo", "15");

  <%-- 기본매장코드 --%>
  var defaultStoreCd = "${defaultStoreCd}";

  <%--그리드 링크 이벤트 등록--%>
  grid.formatItem.addHandler(function(s, e) {
    if (e.panel == s.cells) {
      var col = s.columns[e.col];
      var item = s.rows[e.row].dataItem;
      if( col.binding == "membrNo" ) {
        wijmo.addClass(e.cell, 'wijLink');
      }
    }
  });

  <%-- 그리드 선택 이벤트 --%>
  grid.addEventListener(grid.hostElement, 'mousedown', function(e) {
    var ht = grid.hitTest(e);
    if( ht.cellType == wijmo.grid.CellType.Cell) {
      var col = ht.panel.columns[ht.col];
      if( col.binding == "membrNo") {
        selectData = grid.rows[ht.row].dataItem;
        searchMembr(selectData);
      }
    }
  });

  <%--선택한 회원 정보 조회--%>
  function searchMembr(data) {
    var param = {};
    param.membrNo = data.membrNo;
    param.membrOrgnCd = data.membrOrgnCd;

    $.postJSON("/membr/info/view/base/getMemberInfo.sb", param, function(result) {
      infoInit();
      var data = result.data;

      vMembrNo.value     = data.membrNo;
      vMembrNm.value     = data.membrNm;
      vMembrNmEng.value  = data.memberEngNm;
      vMembrCardNo.value = data.membrCardNo;
      vBrthdDt.text     = data.birthday;
      vTel.value         = data.telNo;
      vEmail.value       = data.emailAddr;
      vAddr1.value       = data.postNo;
      vAddr2.value       = data.addr;
      vRemark.value      = data.remark;
      vRegStore.selectedValue = data.regStoreCd;
      // vClassCd.selectedValue = data.membrClassCd;
      vGender.selectedValue = data.gendrFg;
      vWedding.selectedValue = data.weddingYn;
      vUseYn.selectedValue = data.useYn;
      vEmailRecv.selectedValue = data.emailRecvYn;
      vSmsRecv.selectedValue = data.smsRecvYn;

      // $("#storeCd").val(data.postpaidStoreCds)
      // $("#storeCdText").val(data.postpaidStoreNms);
      $("#vMembrOrgnCd").val(data.membrOrgnCd);
      $("#membrNoNm").text("<s:message code='regist.membr.info'/>" + " [" + data.membrNo + "/" + data.membrNm + "]");
      vMembrNo.isReadOnly = true;

      $("#noDataArea").hide();
      $("#basicInfrm").show();

      // 회원의 등록매장이 본사의 기본매장코드와 같으면 후불회원 적용매장 가능
      editPostpaidStore();
    },
    function(result){
      s_alert.pop(result.message);
    });
  }

  <%--초기화--%>
  function init() {
    $("#basicInfrm").hide();
    $("#membrNoNm").text("<s:message code='regist.membr.info'/>");
  }

  <%--조회 버튼--%>
  $("#btnSearch").click(function(){
    searchMembrs();
  });

  <%--회원정보 조회--%>
  function searchMembrs() {
    var param = {};
    param.membrNo = memberNo.value;
    param.membrNm = memberNm.value;
    param.membrCardNo = membrCardNo.value;
    param.telNo = telNo.value;
    param.emailAddr = membrEmail.value;
    param.regStoreCd = regStore.selectedValue;
    // param.membrClassCd = classCd.selectedValue;
    param.gendrFg = gender.selectedValue;
    param.emailRecvYn = emailRecvYn.selectedValue;
    param.smsRecvYn = smsRecvYn.selectedValue;
    param.periodType = periodDate.selectedValue;
    param.periodStartDate = getDate(startDate);
    param.periodEndDate = getDate(endDate);
    param.anvType = anvrsDate.selectedValue;
    param.anvStartDate = getDate(anvrsStartDate);
    param.anvEndDate = getDate(anvrsEndDate);

    $.postJSON("/membr/info/view/view/list.sb", param, function(result) {
      var list = result.data.list;
      if(list.length == 0) {
        s_alert.pop(result.message);
      }
      grid.itemsSource = list;
      infoInit();
      $("#membrNoNm").text("<s:message code='webMenu.new'/>");
      $("#noDataArea").show();
      $("#basicInfrm").hide();
      $("#membrCardInfo").hide();
    },
    function(result){
      s_alert.pop(result.message);
    });
  }

  init();
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
<%--base.jsp 관련 부분--%>


  <%--기본정보 탭 생성--%>
  var vMembrNo     = genInputText("#vMembrNo", "10");
  var vMembrNm     = genInputText("#vMembrNm", "15");
  var vMembrNmEng  = genInputText("#vMembrNmEng", "15");
  var vTel         = genInputText("#vTel", "15");
  var vEmail       = genInputText("#vEmail", "180");
  var vAddr1       = genInputText("#vAddr1", "60");
  var vAddr2       = genInputText("#vAddr2", "60");
  var vMembrCardNo = genInputText("#vMembrCardNo", "40");
  var vRemark      = genInputText("#vRemark", "160");
  var vBrthdDt     = wcombo.genDate("#vBrthdDt");
  var vRegStore    = wcombo.genCommonBox("#vRegStore", ${regstrStoreListAll});
  <%--var vClassCd     = wcombo.genCommonBox("#vClassCd", ${comboData});--%>
  var vGender      = wcombo.genCommonBox("#vGender", genderDataEx);
  var vWedding     = wcombo.genCommonBox("#vWedding", weddingData);
  var vEmailRecv   = wcombo.genCommonBox("#vEmailRecv", recvDataEx);
  var vUseYn       = wcombo.genCommonBox("#vUseYn", useDataEx);
  var vSmsRecv     = wcombo.genCommonBox("#vSmsRecv", recvDataEx);

  vMembrNo.isReadOnly = true;

  vRegStore.selectedIndexChanged.addHandler(function(s, e){
    editPostpaidStore();
  });

  // 후불회원 적용매장
  function editPostpaidStore(){
    // 회원의 등록매장이 본사의 기본매장코드와 같으면 후불회원 적용매장 가능
    if(vRegStore.selectedValue === defaultStoreCd ) {
      $("#postpaidStore").show();
    } else {
      $("#postpaidStore").hide();
    }
  }

  <%--기본정보 탭 초기화--%>
  function infoInit() {
    $("#noDataArea").hide();
    $("#membrCardInfo").hide();
    $("#basicInfrm").show();

    var inputArr = [
      vMembrNo, vMembrNm, vMembrNmEng, vMembrCardNo, vTel, vEmail, vAddr1, vAddr2, vRemark
    ].forEach(function(element){element.value="";});
    var selectArr = [
      vRegStore, vGender, vWedding, vEmailRecv, vUseYn, vSmsRecv
    ].forEach(function(element){element.selectedIndex=0;});
    $("#membrNoNm").text("<s:message code='webMenu.new'/>");
    vRegStore.selectedValue = '${orgnCd}'; <%--등록매장 세팅--%>
    vGender.selectedValue = 'N';
    vWedding.selectedValue = 'N';
    vUseYn.selectedValue = 'Y';

    // $("#storeCd").val("");
    // $("#storeCdText").val("");

  }

  <%--삭제 버튼--%>
  $("#btnDel").click(function(){
    var msg = "<s:message code='cmm.choo.delete'/>";
    s_alert.popConf(msg, function(){
      var param = {};
      param.membrNo = vMembrNo.value;
      param.membrOrgnCd = $("#vMembrOrgnCd").val();

      $.postJSON("/membr/info/view/base/remove.sb", param, function(result) {
        var msg = "<s:message code='cmm.deleteSucc' />";
        s_alert.popOk(msg, function() {
          searchMembrs();
        });
      },
      function(result){
        if(result.message != undefined || result.message == "") {
          s_alert.pop(result.message);
          return false;
        }
        var data = result.data;
        var keys = Object.keys(data);
        s_alert.popOk(data[keys[0]]);
      });
    });
  });

  <%-- 회원 저장 버튼--%>
  $("#btnSave").click(function(){
    var msg = "<s:message code='cmm.choo.save'/>";
    s_alert.popConf(msg, function(){

      var param = {};
      param.membrNo = vMembrNo.value;
      param.membrNm = vMembrNm.value;
      param.memberEngNm = vMembrNmEng.value;
      param.regStoreCd = vRegStore.selectedValue;
      param.membrCardNo = vMembrCardNo.value;
      param.gendrFg = vGender.selectedValue;
      param.weddingYn = vWedding.selectedValue;
      param.birthday = getDate(vBrthdDt);
      param.telNo = vTel.value;
      param.useYn = vUseYn.selectedValue;
      param.emailAddr = vEmail.value;
      param.postNo = vAddr1.value;
      param.addr = vAddr2.value;
      param.emailRecvYn = vEmailRecv.selectedValue;
      param.smsRecvYn = vSmsRecv.selectedValue;
      param.remark = vRemark.value;
      // param.postpaidStoreCds = $("#storeCd").val(); // 후불회원 적용매장

      $.postJSONSave("/membr/info/view/base/regist.sb", param, function(result) {
        var msg = "<s:message code='cmm.registSucc'/>";
        s_alert.popOk(msg, function() { //succss
          infoInit();
          searchMembrs();
        });
      },
      function(result){ // fail
        if(result.message != undefined || result.message == "") {
          s_alert.pop(result.message);
          return;
        }
        var data = result.data;
        var keys = Object.keys(data);
        if(keys.length > 0) {
          infoFocus(data, keys);
        }
      });
    });
  });

  <%--포커스 이동--%>
  function infoFocus(data, keys) {
    var focusTarget;
    var msg = "";
    keys.forEach(function(key){
      if(key == "membrNo") {
        focusTarget = vMembrNo;
        msg = "membrNo";
      }
      else if(key == "membrNm") {
        focusTarget = vMembrNm;
        msg = "membrNm";
      }
      else if(key == "regStoreCd") {
        focusTarget = vRegStore;
        msg = "regStoreCd";
      }
      else if(key == "emailRecvYn") {
        focusTarget = vEmailRecv;
        msg = "emailRecvYn";
      }
      else if(key == "smsRecvYn") {
        focusTarget = vSmsRecv;
        msg = "smsRecvYn";
      }
      else if(key == "useYn") {
        focusTarget = vUseYn;
        msg = "useYn";
      }
      else if(key == "membrCardNo") {
        focusTarget = vMembrCardNo;
        msg = "membrCardNo";
      }
      else if(key == "gendrFg") {
        focusTarget = vGender;
        msg = "gendrFg";
      }
      else if(key == "telNo") {
        focusTarget = vTel;
        msg = "telNo";
      }
      else if(key == "weddingYn") {
        focusTarget = vWedding;
        msg = "weddingYn";
      }
      else if(key == "emailRecvYn") {
        focusTarget = vEmailRecv;
        msg = "emailRecvYn";
      }
      else if(key == "smsRecvYn") {
        focusTarget = vSmsRecv;
        msg = "smsRecvYn";
      }
      else if(key == "useYn") {
        focusTarget = vUseYn;
        msg = "1";
      }
    });
    s_alert.popConf(data[msg], function(){
      focusTarget.focus();
    });
  }

  <%-- 후불회원 적용매장 버튼 클릭시 --%>
  $("#store").click(function(){
    console.log(selectData); //TODO 여기부터 >>>>>>>>>>>>>>>>>>>>>>>
    showStoreLayer();
    wijmo.grid.FlexGrid.refreshAll();
  });

  <%--신규등록 버튼--%>
  $("#btnNew").click(function(){
    var msg = "<s:message code='regist.new.msg'/>";
    s_alert.popConf(msg, function(){
      infoInit();
    });
  });

  <%--기본정보 탭--%>
  $("#btnInfo").click(function(){
    $("#noDataArea").hide();
    $("#membrCardInfo").hide();
    $("#basicInfrm").show();
    $("#btnInfo").attr("class", "on");
    $("#btnCard").attr("class", "");
  });

  <%--회원카드 탭--%>
  $("#btnCard").click(function(){
    $("#noDataArea").hide();
    $("#basicInfrm").hide();
    $("#membrCardInfo").show();
    $("#btnCard").attr("class", "on");
    $("#btnInfo").attr("class", "");
  });

  function genInputText(div, length) {
    return wcombo.genInputText(div, length, "", null);
  }
});
</script>

<%-- 매장 선택 --%>
<c:import url="/WEB-INF/view/membr/info/view/store.jsp">
  <c:param name="menuCd" value="${menuCd}"/>
  <c:param name="menuNm" value="${menuNm}"/>
  <c:param name="baseUrl" value="ㅁㅇㄷㅎ"/>
</c:import>

