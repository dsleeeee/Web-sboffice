<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%--
* id 규칙
 1. v로 시작하는 id는 단순 조회용 데이터
 2. r로 시작하는 id는 수정, 등록용 데이터
--%>


<%-- 본사 상세정보 레이어 --%>

<div id="hqDtlDim" class="fullDimmed" style="display:none;"></div>
<div id="hqDtlLayer" class="layer" style="display:none;">
  <div class="layer_inner">
    <div class="title w870">
      <p id="popTitle" class="tit"></p>
      <a href="javascript:;" class="btn_close"></a>
      <div class="con">
        <%-- 본사정보, 메뉴권한, 코드자리수, 환경설정 탭 --%>
        <div class="tabType1">
          <ul>
            <%-- 상세정보 탭 --%>
            <li><a id="hqInfoTab" href="javascript:;" class="on"><s:message code="hqManage.hqInfo" /></a></li>
            <%-- 환경설정 탭 --%>
            <li><a id="envSettingTab" href="javascript:;" ><s:message code="hqManage.envSetting" /></a></li>
            <%-- 메뉴관리 탭  --%>
            <li><a id="menuSettingTab" href="javascript:;"><s:message code="hqManage.menuSetting" /></a></li>
          </ul>
        </div>

        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>
        <%--XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX--%>

        <%-- 조회 --%>

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
                  <div class="impWrap"><s:message code="hqManage.hqOfficeCd" /></div>
                </th>
                <td id="vHqOfficeCd"></td>
                <%-- 본사명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.hqOfficeNm" /></div>
                </th>
                <td id="vHqOfficeNm"></td>
              </tr>
              <tr>
                <%-- 대표자명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.ownerNm" /></div>
                </th>
                <td id="vOwnerNm"></td>
                <%--사업자번호 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizNo" /></div>
                </th>
                <td id="vBizNo"></td>
              </tr>
              <tr>
                <%-- 상호명/사업자매장명 --%>
                <th>
                  <div class="impWrap"><s:message code="hqManage.bizStoreNm" /></div>
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

        <%-- 등록 / 수정 --%>

        <div id="regArea" class="mt20 sc" style="height:350px;display:none;">
          <form id="regForm">
            <table class="tblType01">
              <colgroup>
                <col class="w20" />
                <col class="w30" />
                <col class="w20" />
                <col class="w30" />
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
                        <label for="rHqOfficeCdr1"><s:message code="hqManage.comm" /></label>
                      </span>
                      <span class="sb-radio">
                        <input type="radio" name="rHqOfficeCdType" id="rHqOfficeCdr2" value='<s:message code="hqManage.hqType.demo" />' style="width:17px; height:17px; margin-right: 1px;"/>
                        <label for="rHqOfficeCdr2"><s:message code="hqManage.demo" /></label>
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
                      <div class="sb-select w70">
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
                  <th>
                    <div class="impWrap"><s:message code="hqManage.weatherArea" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rWeatherArea"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 휴대폰번호 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.telNo" /><em class="imp">*</em></div>
                  </th>
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
                  <th>
                    <div class="impWrap"><s:message code="hqManage.sysStatFg" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rSysStatFg"></div>
                    </div>
                  </td>
                  <%-- 시스템 오픈일자 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.sysOpenDate" /><em class="imp">*</em></div>
                  </th>
                  <td>
                  <div class="sb-select">
                    <div id="rSysOpenDate"></div>
                  </div>
                  </td>
                </tr>
                <tr>
                  <%-- 주소 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.addr" /><em class="imp">*</em></div>
                  </th>
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
                  <th>
                    <div class="impWrap"><s:message code="hqManage.emailAddr" /></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rEmailAddr"></div>
                    </div>
                  </td>
                  <%-- 홈페이지 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.hmpgAddr" /></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rHmpgAddr"></div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <%-- 관리업체 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.agency" /><em class="imp">*</em></div>
                  </th>
                  <td>
                    <div class="sb-select">
                      <div id="rAgency"></div>
                    </div>
                  </td>
                  <%-- 용도구분 --%>
                  <th>
                    <div class="impWrap"><s:message code="hqManage.clsFg" /><em class="imp">*</em></div>
                  </th>
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

      <%-- 공통 버튼 영역 --%>

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

<%-- ============================================= 신규등록, 수정 폼 관련 =========================================== --%>

  <%-- 본사신규등록 팝업 열기 --%>
  function openRegistLayer() {

    $("#hqDtlLayer #popTitle").text("<s:message code='hqManage.newHq' />");

    $("#hqDtlLayer").show();
    $("#hqDtlDim").show();

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

    $("#hqDtlLayer").show();
    $("#hqDtlDim").show();

    getDtlData(items);

    $("#popTitle").text("["+ items.hqOfficeCd +"] " + items.hqOfficeNm);

    $("#hqDtlLayer").show();
    $("#hqDtlDim").show();

    $("#btnReg").hide();
    $("#btnSave").hide();
    $("#btnEdit").show();
  }

  function getDtlData(items) {
    var param = items;

    $.postJSON("/store/hq/hqManage/master/dtlInfo.sb", param, function(result) {
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
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

  <%-- 폼 리셋 --%>
  function resetForm() {
    $("#regForm")[0].reset();

    rWeatherArea.selectedIndex  = 0;
    rSysStatFg.selectedIndex    = 0;
    rAgency.selectedIndex       = 0;
    rClsFg.selectedIndex        = 0;
    rSysOpenDate.value          = new Date();
  }

  <%-- 상세정보 보여주기 숨김 --%>
  function showMaster(){
    hideEnvSet();
    hideMenuAuth();
    resetForm();

    $("#hqDtlLayer").show();
    $("#hqDtlDim").show();
  }

  <%-- 상세정보 레이아웃 숨김 --%>
  function hideMaster(){
    $("#hqDtlLayer").hide();
    $("#hqDtlDim").hide();
  }

<%-- ============================================= 데이터 저장 관련 =========================================== --%>

  <%-- validation --%>
  function chkVal(sendUrl) {

    <%-- 본사명을 입력해주세요. --%>
    var msg = "<s:message code='hqManage.hqOfficeNm'/> <s:message code='cmm.require.text'/>";
    if(rHqOfficeNm.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 대표자명을 입력해주세요. --%>
    var msg = "<s:message code='hqManage.ownerNm'/> <s:message code='cmm.require.text'/>";
    if(rOwnerNm.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 사업자번호를 입력해주세요. --%>
    var msg = "<s:message code='hqManage.bizNo'/> <s:message code='cmm.require.text'/>";
    if(rBizNo1.text === "" || rBizNo2.text === "" || rBizNo3.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 사업자번호 중복체크를 해주세요. --%>
    var msg = "<s:message code='hqManage.require.duplicate.bizNo'/>";
    if(!isBizChk) {
      s_alert.pop(msg);
      return;
    }

    <%-- 상호명을 입력해주세요. --%>
    var msg = "<s:message code='hqManage.bizStoreNm'/> <s:message code='cmm.require.text'/>";
    if(rBizStoreNm.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 날씨표시지역을 선택해주세요. --%>
    var msg = "<s:message code='hqManage.weatherArea'/> <s:message code='cmm.require.select'/>";
    if(rWeatherArea.text == "선택" || rWeatherArea.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 전화번호를 입력해주세요. --%>
    var msg = "<s:message code='hqManage.telNo'/> <s:message code='cmm.require.text'/>";
    if(rTelNo.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 상태를 선택해주세요. --%>
    var msg = "<s:message code='hqManage.sysStatFg'/> <s:message code='cmm.require.select'/>";
    if(rSysStatFg.text == "선택" || rSysStatFg.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 주소를 입력해주세요. --%>
    var msg = "<s:message code='hqManage.addr'/> <s:message code='cmm.require.text'/>";
    if(rPostNo.text === "" || rAddr.text === "" || rAddrDtl.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 관리업체를 선택해주세요. --%>
    var msg = "<s:message code='hqManage.agency'/> <s:message code='cmm.require.select'/>";
    if(rAgency.text == "선택" || rAgency.text === "") {
      s_alert.pop(msg);
      return;
    }

    <%-- 용도를 선택해주세요. --%>
    var msg = "<s:message code='hqManage.clsFg'/> <s:message code='cmm.require.select'/>";
    if(rClsFg.text == "선택" || rClsFg.text === "") {
      s_alert.pop(msg);
      return;
    }

    saveHqOffice(sendUrl);
  }

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

    console.log(param)

    $.postJSONSave(sendUrl, param, function(result) {
      s_alert.pop("<s:message code='cmm.saveSucc'/>");
      $(".btn_close").click();
      search(1);
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  }

<%-- ============================================= 버튼 이벤트 관련 =========================================== --%>

  <%-- 사업자번호 중복체크 버튼 클릭 --%>
  var isBizChk = false;

  $("#btnChkBizNo").click(function(e){

    var param = {};
    param.bizNo1 = rBizNo1.text;
    param.bizNo2 = rBizNo2.text;
    param.bizNo3 = rBizNo3.text;
    param.bizNo = rBizNo1.text + rBizNo2.text + rBizNo3.text;

    $.postJSON("/store/hq/hqManage/master/chkBizNo.sb", param, function(result) {
      isBizChk = true;

      if(result.data == 0 ){
        <%-- 중복되는 사업자번호가 없습니다.--%>
        s_alert.pop("<s:message code='hqManage.no.duplicate.bizNo.msg'/>");
      } else {
        <%-- 사업자번호 사용현황 팝업 --%>
        openBizInfoLayer(param);
      }
    },
      function (result) {
        s_alert.pop(result.message);
        return;
      }
    );
  });

  <%-- 본사코드 선택 이벤트 --%>
  $("input[name=rHqOfficeCdType]").change(function(){
    var hqType = $("input[name=rHqOfficeCdType]:checked").val();
    //alert('changed value : '+ hqType);
    if(hqType == "D") {
      rSysStatFg.selectedValue = "9";
      rSysStatFg.isReadOnly = true;
    } else {
      rSysStatFg.selectedValue = "";
      rSysStatFg.isReadOnly = false;
    }
  });

  <%-- 주소찾기 버튼 클릭 --%>
  $("#btnFindAddr").click(function(e){
    //TODO 주소검색 공통 추가 필요
  });

  <%-- 신규등록 버튼 클릭 --%>
  $("#btnReg").click(function(e){
    chkVal("/store/hq/hqManage/master/regist.sb");
  });

  <%-- 저장 버튼 클릭 (수정) --%>
  $("#btnSave").click(function(e){
    chkVal("/store/hq/hqManage/master/modify.sb");
  });

  <%-- 수정 버튼 클릭 --%>
  $("#hqDtlLayer #btnEdit").click(function(e){
    $("#viewArea").hide();
    $("#regArea").show();

    $("#btnReg").hide();
    $("#btnSave").show();
    $("#btnEdit").hide();

    $("#rHqOfficeTxt").show();
    $("#rHqOfficeRadio").hide();

  });

  <%-- 닫기 버튼 클릭 --%>
  $("#hqDtlLayer .btn_close, #hqDtlLayer #btnClose").click(function(e){
    resetForm();
    $("#viewArea").show();
    $("#regArea").hide();
    $("#hqDtlLayer").hide();
    $("#hqDtlDim").hide();
  });


  <%-- 환경설정 탭 클릭 --%>
  $("#hqDtlLayer #envSettingTab").click(function(){
    if(selectedHq.hqOfficeCd == "") {
      s_alert.pop("<s:message code='hqManage.require.regist.hq'/>");
      return;
    }

    if(!$("#viewArea").is(":visible")) {
      var msg = "<s:message code='hqManage.confirm.editmode.quit'/>";
      s_alert.popConf(msg, function(){
        showEnvSet();
      });
    } else {
      showEnvSet();
    }
  });

  <%-- 메뉴권한 탭 클릭 --%>
  $("#hqDtlLayer #menuSettingTab").click(function(e){

    if(selectedHq.hqOfficeCd == "") {
      s_alert.pop("<s:message code='hqManage.require.regist.hq'/>");
      return;
    }

    if(!$("#viewArea").is(":visible")) {
      var msg = "<s:message code='hqManage.confirm.editmode.quit'/>";
      s_alert.popConf(msg, function(){
        showMenuAuth();
      });
    } else {
      showMenuAuth();
    }
  });

</script>

