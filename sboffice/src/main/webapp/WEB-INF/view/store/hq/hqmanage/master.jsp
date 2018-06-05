<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%--
* id 규칙
 1. v로 시작하는 id는 단순 조회용 데이터
 2. r로 시작하는 id는 수정, 등록용 데이터
--%>


<%-- 본사 상세정보 레이어 --%>

<div id="dim1" class="fullDimmed" style="display:none;"></div>
<div id="hqDtlLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <!--layerContent-->
    <div class="title w600">
      <p id="popTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <%-- 본사정보, 메뉴권한, 코드자리수, 환경설정, 브랜드관리 탭 --%>
        <div class="tabType1">
          <ul>
            <li><a id="hqInfoTab" href="javascript:;" class="on"><s:message code="hqManage.hqInfo" /></a></li>
            <li><a id="menuSettingTab" href="javascript:;"><s:message code="hqManage.menuSetting" /></a></li>
            <%-- <li><a id="envSettingTab" href="javascript:;"><s:message code="hqManage.envSetting" /></a></li> --%>
          </ul>
        </div>
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
      

        <div id="viewArea" class="mt20 sc" style="height:350px;">
          <table class="tblType01">
            <colgroup>
              <col class="w15" />
              <col class="w35" />
              <col class="w15" />
              <col class="w35" />
            </colgroup>
            <tbody>
              <tr>
                <%-- 본사코드 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.hqOfficeCd" /><em class="imp">*</em></div>
                </th>
                <td id="vHqOfficeCd"></td>
                <%-- 본사명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.hqOfficeNm" /><em class="imp">*</em></div>
                </th>
                <td id="vHqOfficeNm"></td>
              </tr>
              <tr>
                <%-- 대표자명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.ownerNm" /><em class="imp">*</em></div>
                </th>
                <td id="vOwnerNm"></td>
                <%--사업자번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizNo" /><em class="imp">*</em></div>
                </th>
                <td id="vBizNo"></td>
              </tr>
              <tr>
                <%-- 상호명/사업자매장명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizStoreNm" /><em class="imp">*</em></div>
                </th>
                <td id="vBizStoreNm"></td>
                <%-- 날씨표시지역 --%>
                <th><s:message code="hqManage.weatherArea" /></th>
                <td id="vWeatherArea"></td>
              </tr>
              <tr>
                <%-- 휴대폰번호 --%>
                <th><s:message code="hqManage.telNo" /></th>
                <td id="vTelNo"></td>
                <%-- 팩스번호 --%>
                <th><s:message code="hqManage.faxNo" /></th>
                <td id="vFaxNo"></td>
              </tr>
              <tr>
                <%-- 상태구분 --%>
                <th><s:message code="hqManage.sysStatFg" /></th>
                <td id="vSysStatFg"></td>
                <%-- 시스템 오픈일자 --%>
                <th><s:message code="hqManage.sysOpenDate" /></th>
                <td id="vSysOpenDate"></td>
              </tr>
              <tr>
                <%-- 주소 --%>
                <th><s:message code="hqManage.addr" /></th>
                <td colspan="3" id="vAddr"></td>
              </tr>
              <tr>
                <%-- 이메일주소 --%>
                <th><s:message code="hqManage.emailAddr" /></th>
                <td id="vEmailAddr"></td>
                <%-- 홈페이지 --%>
                <th><s:message code="hqManage.hmpgAddr" /></th>
                <td id="vHmpgAddr"></td>
              </tr>
              <tr>
                <%-- 관리업체 --%>
                <th><s:message code="hqManage.agency" /></th>
                <td id="vAgency"></td>
                <%-- 용도구분 --%>
                <th><s:message code="hqManage.clsFg" /></th>
                <td id="vClsFg"></td>
              </tr>
            </tbody>
          </table>
        </div>
      
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
      
        <div id="regArea" class="mt20 sc" style="height:350px;display:none;">
          <form id="regForm">
            <table class="tblType01">
              <colgroup>
                <col class="w15" />
                <col class="w35" />
                <col class="w15" />
                <col class="w35" />
              </colgroup>
              <tbody>
                <tr>
                  <%-- 본사코드 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.hqOfficeCd" /><em class="imp">*</em></div>
                  </th>
                  <td colspan="3">
                     <div id="rHqOfficeTxt">
                      <div class="sb-select">
                        <div id="rHqOfficeCd"></div>
                      </div>
                    </div>
                    <div id="rHqOfficeRadio">
                      <span class="sb-radio">
                        <input type="radio" name="rHqOfficeCdType" id="rHqOfficeCdr1" value='<s:message code="hqManage.hqType.comm" />' checked style="width:17px; height:17px; margin-right: 1px;"/>
                        <label for="rdo1"><s:message code="hqManage.comm" /></label>
                      </span>
                      <span class="sb-radio">
                        <input type="radio" name="rHqOfficeCdType" id="rHqOfficeCdr2" value='<s:message code="hqManage.hqType.demo" />' style="width:17px; height:17px; margin-right: 1px;"/>
                        <label for="rdo1"><s:message code="hqManage.demo" /></label>
                      </span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 본사명 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.hqOfficeNm" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rHqOfficeNm"></div>
                    </div>
                  </td>
                  <%-- 대표자명 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.ownerNm" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rOwnerNm"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%--사업자번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.bizNo" /><em class="imp">*</em></div>
                  </th>
                  <td colspan="3">
                    <span class="w10 txtIn pdr5 pdb5" >
                      <div class="sb-select">
                        <div id="rBizNo1"></div>
                      </div>
                    </span>
                    <span class="w10 txtIn pdr5">
                      <div class="sb-select">
                        <div id="rBizNo2"></div>
                      </div>
                    </span>
                    <span class="w15 txtIn">
                      <div class="sb-select">
                        <div id="rBizNo3"></div>
                      </div>
                    </span>
                    <%-- 사업자번호 중복체크 --%>
                    <span class="w20 pdb10">
                      <a href="javascript:;" class="btn_grayS" id="btnChkBizNo"><s:message code="hqManage.chk.duplicate.bizNo" /></a>
                    </span>
                  </td>
                </tr>
                <tr>
                  <%-- 상호명/사업자매장명 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.bizStoreNm" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rBizStoreNm"></div>
                    </div>
                  </td>
                  <%-- 날씨표시지역 --%>
                  <th><s:message code="hqManage.weatherArea" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rWeatherArea"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 휴대폰번호 --%>
                  <th><s:message code="hqManage.telNo" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rTelNo"></div>
                    </div>
                  </td>
                  <%-- 팩스번호 --%>
                  <th><s:message code="hqManage.faxNo" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rFaxNo"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 상태구분 --%>
                  <th><s:message code="hqManage.sysStatFg" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rSysStatFg"></div>
                    </div>
                  </td>
                  <%-- 시스템 오픈일자 --%>
                  <th><s:message code="hqManage.sysOpenDate" /></th>
                  <td>
                  <div class="sb-select">
                    <div id="rSysOpenDate"></div>
                  </div>
                  </td>
                </tr>
                <tr>
                  <%-- 주소 --%>
                  <th><s:message code="hqManage.addr" /></th>
                  <td colspan="3">
                    <span class="w20 txtIn pdr5">
                      <div class="sb-select w100">
                        <div id="rPostNo"></div>
                      </div>
                    </span>
                    <%-- 주소찾기 버튼 --%>
                    <span class="w20 pdb10">
                      <a href="javascript:;" class="btn_grayS" id="btnFindAddr"><s:message code="hqManage.findAddr" /></a>
                    </span>
                    
                    <br/>
                    <span class="w40 txtIn pdr5">
                      <div class="sb-select">
                        <div id="rAddr"></div>
                      </div>
                    </span>
                    <span class="w45 txtIn">
                      <div class="sb-select">
                        <div id="rAddrDtl"></div>
                      </div>
                    </span>
                  </td>
                </tr>
                <tr>
                  <%-- 이메일주소 --%>
                  <th><s:message code="hqManage.emailAddr" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rEmailAddr"></div>
                    </div>
                  </td>
                  <%-- 홈페이지 --%>
                  <th><s:message code="hqManage.hmpgAddr" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rHmpgAddr"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 관리업체 --%>
                  <th><s:message code="hqManage.agency" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rAgency"></div>
                    </div>
                  </td>
                  <%-- 용도구분 --%>
                  <th><s:message code="hqManage.clsFg" /></th>
                  <td>
                    <div class="sb-select">
                      <div id="rClsFg"></div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </form>
        </div> 
        
      </div>
        
      <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
      <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

      <div class="btnSet">
        <%-- 등록 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnReg" style="display:none;"><s:message code="cmm.new.add" /></a></span>
        <%-- 저장 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnSave" style="display:none;"><s:message code="cmm.save" /></a></span>
        <%-- 수정 --%>
        <span><a href="javascript:;" class="btn_blue" id="btnEdit"><s:message code="cmm.edit" /></a></span>
        <%-- 닫기 --%>
        <span><a href="javascript:;" class="btn_gray" id="btnClose"><s:message code="cmm.close" /></a></span>
      </div>
    </div>
    <!--//layerContent-->
  </div>
</div>

<script>

  var rHqOfficeCd   = wcombo.genInput("#rHqOfficeCd");
  var rHqOfficeNm   = wcombo.genInput("#rHqOfficeNm");
  var rOwnerNm      = wcombo.genInput("#rOwnerNm");
  var rBizNo1       = wcombo.genInput("#rBizNo1");
  var rBizNo2       = wcombo.genInput("#rBizNo2");
  var rBizNo3       = wcombo.genInput("#rBizNo3");
  var rBizStoreNm   = wcombo.genInput("#rBizStoreNm");
  var rWeatherArea  = wcombo.genCommonBox("#rWeatherArea", areaCd);
  var rTelNo        = wcombo.genInput("#rTelNo");
  var rFaxNo        = wcombo.genInput("#rFaxNo");
  var rSysStatFg    = wcombo.genCommonBox("#rSysStatFg", sysStatFg);
  var rSysOpenDate  = wcombo.genDateVal("#rSysOpenDate", "${sessionScope.sessionInfo.startDt}");
  var rPostNo       = wcombo.genInput("#rPostNo");
  var rAddr         = wcombo.genInput("#rAddr");
  var rAddrDtl      = wcombo.genInput("#rAddrDtl");
  var rEmailAddr    = wcombo.genInput("#rEmailAddr");
  var rHmpgAddr     = wcombo.genInput("#rHmpgAddr");
  var rAgency       = wcombo.genCommonBox("#rAgency", agencyList);
  var rClsFg        = wcombo.genCommonBox("#rClsFg", clsFg);
  
  rHqOfficeCd.isReadOnly  = true;
  //rPostNo.isReadOnly      = true;
  //rAddr.isReadOnly        = true;
  
  rHqOfficeNm.isRequired = true;
  
  <%-- 본사정보 탭 클릭 --%>
  $("#hqInfoTab").click(function(e){
    
  });
  
  <%-- 메뉴권한 탭 클릭 --%>
  $("#codeSettingTab").click(function(e){
    
  });
  
  <%-- 코드자리수 탭 클릭 --%>
  $("#envSettingTab").click(function(e){
    
  });

  <%-- 본사신규등록 팝업 열기 --%>
  function openRegistLayer() {
    
    $("#popTitle").text("<s:message code='hqManage.newHq' />");
    
    $("#hqDtlLayer").show();
    $("#dim1").show();

    $("#viewArea").hide();
    $("#regArea").show();
    
    $("#btnReg").show();
    $("#btnSave").hide();
    $("#btnEdit").hide();
    
    $("#rHqOfficeTxt").hide();
    $("#rHqOfficeRadio").show();
  }
  
  <%-- 상세정보 팝업 열기 --%>
  function openDtlLayer(items) {
    
    getDtlData(items);
    
    $("#popTitle").text("["+ items.hqOfficeCd +"] " + items.hqOfficeNm);
    
    $("#hqDtlLayer").show();
    $("#dim1").show();
    
    $("#btnReg").hide();
    $("#btnSave").hide();
    $("#btnEdit").show();
  }
  
  function getDtlData(items) {
    var param = items;
    
    $.postJSON("/store/hq/hqmanage/master/dtlInfo.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      var data = result.data;

      <%-- 상세정보 --%>
      $("#vHqOfficeCd").text(data.hqOfficeCd);
      $("#vHqOfficeNm").text(data.hqOfficeNm);
      $("#vOwnerNm").text(data.ownerNm);
      $("#vBizNo").text(data.bizNo1 + "-" + data.bizNo2 + "-" + data.bizNo3);
      $("#vBizStoreNm").text(data.bizStoreNm);
      $("#vWeatherArea").text(data.areaCd);
      $("#vTelNo").text(data.telNo);
      $("#vFaxNo").text(data.faxNo);
      $("#vSysStatFg").text(data.sysStatFgNm);
      $("#vSysOpenDate").text(data.sysOpenDate);
      $("#vAddr").text("("+data.postNo+") "+data.addr + " "+data.addrDtl);
      $("#vEmailAddr").text(data.emailAddr);
      $("#vHmpgAddr").text(data.hmpgAddr);
      $("#vAgency").text(data.agencyNm);
      $("#vClsFg").text(data.clsFgNm);
      
      <%-- 상세정보 수정 --%>
      rHqOfficeCd.text           = data.hqOfficeCd;
      rHqOfficeNm.text           = data.hqOfficeNm;
      rOwnerNm.text              = data.ownerNm;
      rBizNo1.text               = data.bizNo1;
      rBizNo2.text               = data.bizNo2;
      rBizNo3.text               = data.bizNo3;
      rBizStoreNm.text           = data.bizStoreNm;
      rWeatherArea.selectedValue = data.areaCd;
      rTelNo.text                = data.telNo;
      rFaxNo.text                = data.faxNo;
      rSysStatFg.selectedValue   = data.sysStatFg;
      rSysOpenDate.value         = data.sysOpenDate;
      rPostNo.text               = data.postNo;
      rAddr.text                 = data.addr;
      rAddrDtl.text              = data.addrDtl;
      rEmailAddr.text            = data.emailAddr;
      rHmpgAddr.text             = data.hmpgAddr;
      rAgency.selectedValue      = data.agencyCd;
      rClsFg.selectedValue       = data.clsFg;
      
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
    
  <%-- 사업자번호 중복체크 버튼 클릭 --%>
  $("#btnChkBizNo").click(function(e){
    
    <%-- 사업자번호 중복체크 --%>
    var param = {};
    param.bizNo1 = rBizNo1.text;
    param.bizNo2 = rBizNo2.text;
    param.bizNo3 = rBizNo3.text;
    param.bizNo = rBizNo1.text + rBizNo2.text + rBizNo3.text;
    
    //openBizInfoLayer(param);
    
    $.postJSON("/store/hq/hqmanage/master/chkBizNo.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      if(result.data == 0 ){
        //s_alert("중복되는거 없음 메세지")
      } else {
        // 사업자번호 사용현황 팝업
        openBizInfoLayer(param);
      }
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
    
  });
  
  <%-- 주소찾기 버튼 클릭 --%>
  $("#btnFindAddr").click(function(e){
    
    
  });
  
  <%-- 신규등록 버튼 클릭 --%>
  $("#btnReg").click(function(e){
  
    //TODO validation 추가- focus

    <%-- 본사코드 --%>
    var hqType = $('input:radio[name="rHqOfficeCdType"]:checked').val();
    
    // 조건 더 있나 보고
    if(hqType == "C") {
      if(rSysStatFg.selectedValue == "9") {
        s_alert.pop("<s:message code='login.layer.pwchg.limit' />");
        return;
      }
    }

    <%-- 사업자번호 중복체크 --%>
    var param = {};
    param.bizNo = rBizNo1.text + rBizNo2.text + rBizNo3.text;
    
    $.postJSON("/store/hq/hqmanage/master/chkBizNo.sb", param, function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      
      if(result.data > 0){
        var msg = "<s:message code='hqManage.duplicate.bizNo.msg'/>";
        s_alert.popConf(msg, function(){
        
          // 다음단계 진행
          var sendUrl = "/store/hq/hqmanage/master/regist.sb";
          saveHqOffice(sendUrl);
        });
      } else {
        // 다음단계 진행
        var sendUrl = "/store/hq/hqmanage/master/regist.sb";
        saveHqOffice(sendUrl);
      }
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
    
  });
  
  <%-- 저장 버튼 클릭 (수정) --%>
  $("#btnSave").click(function(e){
    var sendUrl = "/store/hq/hqmanage/master/modify.sb";
    saveHqOffice(sendUrl);
  });
  
  <%-- 저장 --%>
  function saveHqOffice(sendUrl) {
    
    var param = {};
    
    param.hqOfficeCd = rHqOfficeCd.text;
    param.hqOfficeNm = rHqOfficeNm.text;
    param.ownerNm = rOwnerNm.text;
    param.bizNo = rBizNo1.text + rBizNo2.text + rBizNo3.text;
    param.bizStoreNm = rBizStoreNm.text;
    param.telNo = rTelNo.text;
    param.faxNo = rFaxNo.text;
    param.emailAddr = rEmailAddr.text;
    param.hmpgAddr = rHmpgAddr.text;
    param.postNo = rPostNo.text;
    param.addr = rAddr.text;
    param.addrDtl = rAddrDtl.text;
    param.areaCd = rWeatherArea.selectedValue;
    param.sysStatFg = rSysStatFg.selectedValue;
    param.sysOpenDate = getDate(rSysOpenDate);
    param.agencyCd = rAgency.selectedValue;
    param.clsFg = rClsFg.selectedValue;
    
    $.postJSONSave(sendUrl, JSON.stringify(param), function(result) {
      if(result.status === "FAIL") {
        s_alert.pop(result.message);
        return;
      }
      s_alert.pop("<s:message code='cmm.saveSucc'/>");
      $(".btn_close").click();
    })
    .fail(function(){
      s_alert.pop("Ajax Fail");
    });
  }
  
  <%-- 수정 버튼 클릭 --%>
  $("#btnEdit").click(function(e){
    $("#viewArea").hide();
    $("#regArea").show();

    $("#btnReg").hide();
    $("#btnSave").show();
    $("#btnEdit").hide();
    
    $("#rHqOfficeTxt").show();
    $("#rHqOfficeRadio").hide();
    
  });
  
  <%-- 닫기 버튼 클릭 --%>
  $(".btn_close, #btnClose").click(function(e){
    resetForm();
    $("#viewArea").show();
    $("#regArea").hide();
    $("#hqDtlLayer").hide();
    $("#dim1").hide();
  });
  
  <%-- 폼 리셋 --%>
  function resetForm() {
    $("#regForm")[0].reset();
    rWeatherArea.selectedValue  = "";
    rSysStatFg.selectedValue    = "";
    rAgency.selectedValue       = "";
    rClsFg.selectedValue        = "";
    rSysOpenDate.value          = new Date();
  }
  
  <%-- 탭 클릭 --%>
  $(".tabType1 ul li #menuSettingTab").click(function(e){
    
    if(selectedHq.hqOfficeCd == "") {
      s_alert.pop("<s:message code='hqManage.require.regist.hq'/>");
      return;
    }

    if(!$("#viewArea").is(":visible")) {
      var msg = "<s:message code='hqManage.confirm.editmode.quit'/>";
      s_alert.popConf(msg, function(){
        changeTab1();
      });
    } else { 
      changeTab1();
    }
  });
  
  function changeTab1() {
    resetForm();
    
    $("#hqDtlLayer").hide();
    $("#dim1").hide();
    
    $("#munuAuthLayer").show();
    $("#dim2").show();
    
    openAuthLayer();
  }
</script>

