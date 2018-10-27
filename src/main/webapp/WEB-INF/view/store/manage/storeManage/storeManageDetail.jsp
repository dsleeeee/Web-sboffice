<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 매장 선택 전 --%>
<div class="w65 fr" id="noDataArea">
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <p class="tc s18 mt200 bk"><s:message code="storeManage.require.select.store" /></p>
    <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.hqOffice" /><br /><s:message code="storeManage.can.regist.store" /></p>
    <p class="tc s14 mt40 lh25"><s:message code="storeManage.select.store" /><br /><s:message code="storeManage.can.edit.store" /></p>
  </div>
</div>

<%-- 매장 상세정보 --%>
<div class="w65 fr" id="storeInfoViewArea" style="display:none;">
  <div class="wj-TblWrapBr ml10 pd20" style="height:700px;">
    <h2 class="h2_tit" id="storeInfoTitle"></h2>

    <%--============================================= 탭 =============================================--%>
    <ul class="subTab">
      <%-- 매장정보 --%>
      <li><a id="storeInfo" href="#" class="on"><s:message code="storeManage.storeInfo" /></a></li>
      <%-- 메뉴권한 --%>
      <%-- <li><a id="storeMenuAuth" href="#"><s:message code="storeManage.storeMenuAuth" /></a></li> --%>
      <%-- 매장환경 --%>
      <li><a id="storeEnv"  href="#"><s:message code="storeManage.storeEnv" /></a></li>
    </ul>

    <%--============================================= 기본정보 =============================================--%>
    <f:form id="regForm">
      <h3 class="h3_tbl"><s:message code="storeManage.basicInfo" /></h3>
      <table class="searchTbl">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
          <tr>
            <%-- 매장코드 (등록시에 일반/데모 선택 라디오버튼, 조회시에 등록된 매장코드 보여줌) --%>
            <th><s:message code="storeManage.storeCd" /><em class="imp">*</em></th>
            <td id="rStoreCdTxt"> <%-- 수정 --%>
              <input type="text" id="rStoreCd" class="sb-input w100" readonly="readonly"/>
            </td>
            <td id="rStoreCdRadio"> <%-- 등록 --%>
              <span class="sb-radio">
                <input type="radio" name="rStoreCdRadio" id="rStoreCdRadio1" value='<s:message code="storeManage.storeType.comm" />' checked style="width:17px; height:17px; margin-right: 1px;"/>
                <label for="rStoreCdRadio"><s:message code="storeManage.comm" /></label>
              </span>
              <span class="sb-radio">
                <input type="radio" name="rStoreCdRadio" id="rStoreCdRadio2" value='<s:message code="storeManage.storeType.demo" />' style="width:17px; height:17px; margin-right: 1px;"/>
                <label for="rStoreCdRadio"><s:message code="storeManage.demo" /></label>
              </span>
            </td>
            <%-- 매장명 --%>
            <th><s:message code="storeManage.storeNm" /><em class="imp">*</em></th>
            <td><input type="text" id="rStoreNm" class="sb-input w100" maxlength="15"/></td>
          </tr>
          <tr>
            <%-- 상호명 --%>
            <th><s:message code="storeManage.bizStoreNm" /><em class="imp">*</em></th>
            <td><input type="text" id="rBizStoreNm" class="sb-input w100" /></td>
            <%-- 대표자명 --%>
            <th><s:message code="storeManage.onwerNm" /><em class="imp">*</em></th>
            <td><input type="text" id="rOwnerNm" class="sb-input w100" /></td>
          </tr>
          <tr>
            <%-- 포스개점일자 --%>
            <th><s:message code="storeManage.posOpenDate" /></th>
            <td>
              <div class="sb-select">
                <span class="txtIn">
                  <input id="rOpenPosDate" name="startDate" class="w200px" />
                </span>
              </div>
            </td>
            <%-- 날씨표시지역 --%>
            <th><s:message code="storeManage.weatherArea" /><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="rArea"></div>
              </div>
            </td>
          </tr>
          <tr>
            <%-- 용도 --%>
            <th><s:message code="storeManage.cls" /><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="rClsFg"></div>
              </div>
            </td>
            <%-- 매장상태 --%>
            <th><s:message code="storeManage.sysStatFg" /><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="rSysStatFg"></div>
              </div>
            </td>
          </tr>
          <tr>
            <%-- 설치포스수 --%>
            <th><s:message code="storeManage.installPosCnt" /><em class="imp">*</em></th>
            <td><input type="text" id="rInstallPosCnt" class="sb-input w100"/></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <%-- 사업자번호 --%>
            <th><s:message code="storeManage.bizNo" /><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="rBizNo1" class="sb-input w10" maxlength="3"/>-
              <input type="text" id="rBizNo2" class="sb-input w10" maxlength="2"/>-
              <input type="text" id="rBizNo3" class="sb-input w15" maxlength="5"/>
              <a id="btnChkBizNo" href="#" class="btn_grayS ml5"><s:message code="storeManage.chk.duplicate" /></a><Br />
            </td>
          </tr>
          <tr>
            <%-- 전화번호 --%>
            <th><s:message code="storeManage.telNo" /><em class="imp">*</em></th>
            <td><input type="text" id="rTelNo" class="sb-input w100" placeholder="<s:message code='storeManage.bizNo.comment' />" /></td>
            <%-- 팩스번호 --%>
            <th><s:message code="storeManage.faxNo" /></th>
            <td><input type="text" id="rFaxNo" class="sb-input w100" placeholder="<s:message code='storeManage.bizNo.comment' />" /></td>
          </tr>
          <tr>
            <%-- 이메일 --%>
            <th><s:message code="storeManage.emailAddr" /></th>
            <td colspan="3"><input type="text" id="rEmailAddr" class="sb-input w100" /></td>
          </tr>
          <tr>
            <%-- 홈페이지 --%>
            <th><s:message code="storeManage.hmpgAddr" /></th>
            <td colspan="3"><input type="text" id="rHmpgAddr" class="sb-input w100" /></td>
          </tr>
          <tr>
            <%-- 주소 //TODO 주소검색 추가 필요 --%>
            <th><s:message code="storeManage.addr" /><em class="imp">*</em></th>
            <td colspan="3">
              <input type="text" id="rPostNo" class="sb-input w30" />
              <a id="btnSrchAddr" href="#" class="btn_grayS ml5"><s:message code="storeManage.srchAddr" /></a><Br />
              <input type="text" id="rAddr" class="sb-input w100" />
              <input type="text" id="rAddrDtl" class="sb-input w100" />
            </td>
          </tr>
          <tr>
            <%-- 관리업체 //TODO 팝업으로 변경 --%>
            <th><s:message code="storeManage.manageVan" /><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="rManageVan"></div>
              </div>
            </td>
            <%-- 대리점 //TODO 팝업으로 변경 --%>
            <th><s:message code="storeManage.agency" /><em class="imp">*</em></th>
            <td>
              <div class="sb-select">
                <div id="rAgency"></div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <%-- 비고 --%>
      <h3 class="h3_tbl"><s:message code="storeManage.remark" /></h3>
      <table class="searchTbl">
        <colgroup>
          <col class="w15" />
          <col class="w35" />
          <col class="w15" />
          <col class="w35" />
        </colgroup>
        <tbody>
          <tr>
            <%-- 시스템비고 --%>
            <th><s:message code="storeManage.systemRemark" /></th>
            <td colspan="3"><input id="rSysRemark" type="text" class="sb-input w100" /></td>
          </tr>
          <tr>
            <%-- 본사비고 --%>
            <th><s:message code="storeManage.hdRemark" /></th>
            <td colspan="3"><input id="rHdRemark" type="text" class="sb-input w100" /></td>
          </tr>
          <tr>
            <%-- 특이사항 --%>
            <th><s:message code="storeManage.uniqueRemark" /></th>
            <td colspan="3"><input id="rStoreRemark" type="text" class="sb-input w100" /></td>
          </tr>
        </tbody>
      </table>

      <%-- ============================================= 추가설정 (등록시에만)  =============================================--%>
      <div id="additionalArea" style="display:none;">
        <h3 class="h3_tbl"><s:message code="storeManage.additionalSetting" /></h3>
        <table class="searchTbl">
          <colgroup>
            <col class="w15" />
            <col class="w35" />
            <col class="w15" />
            <col class="w35" />
          </colgroup>
          <tbody>
            <tr>
              <%-- 매장환경복사 --%>
              <th><s:message code="storeManage.copyStoreSetting" /></th>
              <td colspan="3" class="oh">
                <div class="sb-select mr5 w50 fl">
                  <%-- 본사 선택 --%>
                  <div id="rEnvHqOffice" class="w100 mb5"></div>
                  <%-- 매장선택 --%>
                  <div id="rEnvStore" class="w100"></div>
                </div>
                <%-- 매장환경조회 버튼 --%>
                <a id="btnStoreSetting" href="#" class="btn_grayS mt35 mb15"><s:message code="storeManage.srchStoreSetting" /></a><br />
                <%-- 매장환경 체크박스  --%>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="storeEnvChk" value="storeEnv"/><%-- 매장환경 --%>
                  <label for="storeEnvChk" ><s:message code="storeManage.storeEnv" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="posEnvChk" value="posEnv"/>  <%-- 포스환경 --%>
                  <label for="posEnvChk" ><s:message code="storeManage.posEnv" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="foodEnvChk" value="foodEnv"/> <%-- 외식환경 --%>
                  <label for="foodEnvChk" ><s:message code="storeManage.foodEnv" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="kitchenPrintChk" value="kitchenPrint"/> <%-- 주방프린터 --%>
                  <label for="kitchenPrintChk" ><s:message code="storeManage.kitchenPrint" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="productChk" value="product"/><%-- 상품 --%>
                  <label for="productChk" ><s:message code="storeManage.product" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="salePriceChk" value="salePrice"/><%-- 판매가격 --%>
                  <label for="salePriceChk" ><s:message code="storeManage.salePrice" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="supplyPriceChk" value="supplyPrice"/><%-- 공급가격 --%>
                  <label for="supplyPriceChk" ><s:message code="storeManage.supplyPrice" /></label>
                </span>
                <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" id="touchKeyChk" value="touchKey"/><%-- 터치키(판매) --%>
                  <label for="touchKeyChk" ><s:message code="storeManage.touchKey" /></label>
                </span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <%--============================================= 매장 코너 승인 설정 =============================================--%>
      <h3 class="h3_tbl pdt5 lh30"><s:message code="storeManage.vanSetting" /></h3>
      <table class="searchTbl2">
        <tbody>
          <tr>
            <%-- 코너 사용여부 --%>
            <th class="tc"><s:message code="storeManage.cornerUseYn" /></th>
            <td class="tc" colspan="3">
              <div class="sb-select w100">
                <div id="rCornerUseYn"></div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <%--============================================= 포스별 승인 (매장 코너 승인 설정이 '포스별승인'일때 사용) ============================================= --%>
      <div id="posApproveArea">
        <h3 class="h3_tbl"><s:message code="storeManage.posApprove" /></h3>
        <div id="thePosGrid" style="height:150px;"></div>
      </div>
    </f:form>

    <%--============================================= 버튼 =============================================--%>
    <div class="tc mt20">
      <%-- 신규 등록 --%>
      <button id="btnRegist" class="btn_blue"><s:message code="cmm.new.add" /></button>
      <%-- 저장 --%>
      <button id="btnSave"class="btn_blue" style="display:none;"><s:message code="cmm.save" /></button>
    </div>
  </div>
</div>

<script>

var selectedHq = "";

var vanList        = ${ccu.getVanList()};
var agencyList     = ${ccu.getAgencyList()};
var hqList         = ${ccu.getHqOfficeList()};
var clsFg          = ${ccu.getCommCodeSelect("001")};
var sysStatFg      = ${ccu.getCommCodeSelect("005")};
var areaCd         = ${ccu.getCommCodeSelect("061")};
var cornerUseYn    = ${cnv.getEnvCodeExcpAll("2028")};
var useYn          = ${ccu.getCommCodeExcpAll("067")};

<%-- ============================================= 그리드, 위즈모 관련 =========================================== --%>

var rOpenPosDate   = wcombo.genDateVal("#rOpenPosDate", "${sessionScope.sessionInfo.startDate}");
var rClsFg         = wcombo.genCommonBox("#rClsFg", clsFg);
var rSysStatFg     = wcombo.genCommonBox("#rSysStatFg", sysStatFg);
var rArea          = wcombo.genCommonBox("#rArea", areaCd);
var rManageVan     = wcombo.genCommonBox("#rManageVan", vanList);
var rAgency        = wcombo.genCommonBox("#rAgency", agencyList);
var rEnvHqOffice   = wcombo.genCommonBox("#rEnvHqOffice", hqList);
var rEnvStore      = wcombo.genCommonBox("#rEnvStore", []);
var rCornerUseYn   = wcombo.genCommonBox("#rCornerUseYn", cornerUseYn);
var vanDataMap     = new wijmo.grid.DataMap(vanList, 'value', 'name');

rCornerUseYn.selectedValue = "3";
rCornerUseYn.isReadOnly = true; // TODO 추후 코너 추가시 readonly 해제 후 기능개발

<%-- 포스별 승인 그리드 헤더 --%>
var posHeaderData =
  [
    {binding:"posNo", header:"<s:message code='storeManage.posNo' />", width:"*", isReadOnly:true},
    {binding:"vanCertYn", header:"<s:message code='storeManage.vanCertYn' />", dataType:wijmo.DataType.Boolean, width:40},
    {binding:"vanCd", header:"<s:message code='storeManage.vanCd' />",dataMap:vanDataMap, width:"*"},
    {binding:"vanTermnlNo", header:"<s:message code='storeManage.vanTermnlNo' />", width:"*"},
    {binding:"vanSerNo", header:"<s:message code='storeManage.vanSerNo' />", width:"*"},
    {binding:"vanCertStartDate", header:"<s:message code='storeManage.vanCertStartDate' />", isReadOnly:true, width:"*"},
    {binding:"vanCertEndDate", header:"<s:message code='storeManage.vanCertEndDate' />", isReadOnly:true, width:"*"},
    {binding:"vanCertCnt", header:"<s:message code='storeManage.vanCertCnt' />", isReadOnly:true, width:"*"}
  ];

var thePosGrid    = wgrid.genGrid("#thePosGrid", posHeaderData);

thePosGrid.isReadOnly = false;

<%-- 추가설정에서 본사 클릭시, 해당 본사의 매장 목록 조회 --%>
rEnvHqOffice.selectedIndexChanged.addHandler(function(s, e){
  var param = {};
  param.hqOfficeCd = rEnvHqOffice.selectedValue;

  $.postJSON("/store/manage/storeManage/storeManage/getStoreComboList.sb", param, function(result) {
    rEnvStore.itemsSource = result.data.list;
  },
    function (result) {
      s_alert.pop(result.message);
      return;
    }
  );
});

<%-- ============================================= 신규등록, 수정 폼 관련 =========================================== --%>

<%-- form reset --%>
function resetForm() {
  $("#regForm")[0].reset();

  rClsFg.selectedIndex      = 0;
  rSysStatFg.selectedIndex  = 0;
  rArea.selectedIndex       = 0;
  rAgency.selectedIndex     = 0;
  rManageVan.selectedIndex  = 0;
  rOpenPosDate.value        = new Date();
  rEnvHqOffice.selectedIndex = 0;
  rClsFg.isReadOnly = false;

  $("#rInstallPosCnt").removeAttr("readonly");

  // 본사가 데모면 데모 매장으로만 등록하도록
  if(selectedHq.hqSysStatFg == "9") {
    $("#rStoreCdRadio2").prop("checked", true);
    $("input[name=rStoreCdRadio]").attr("disabled", true);
    rSysStatFg.selectedValue = "9";
    rSysStatFg.isReadOnly = true;
  } else {
    $("#rStoreCdRadio1").prop("checked", true);
    $("input[name=rStoreCdRadio]").attr("disabled", false);
    rSysStatFg.selectedValue = "";
    rSysStatFg.isReadOnly = false;
  }

  // 포스별승인 그리드 데이터 초기화

  rCornerUseYn.selectedValue = "3";
  rCornerUseYn.selectedIndex = 3;
  thePosGrid.itemsSource = new wijmo.collections.CollectionView([]);

}

<%-- 매장 신규등록 --%>
function newStoreReg(hqOfficeInfo) {

  selectedHq = hqOfficeInfo;
  resetForm();

  $("#noDataArea").hide();
  $("#storeInfoViewArea").show();
  $("#additionalArea").show();

  $("#storeInfoTitle").text("<s:message code='storeManage.new.store' />");

  $("#rStoreCdTxt").hide();
  $("#rStoreCdRadio").show();

  $("#btnRegist").show();
  $("#btnSave").hide();
}

<%-- 매장 상세정보 조회 --%>
function showStoreDetail() {

  var param = selectedStore;

  $.postJSON("/store/manage/storeManage/storeManage/getStoreDetail.sb", param, function(result) {
    isBizChk = true;
    setStoreData(result.data);
  },
    function (result) {
      s_alert.pop(result.message);
      return;
    }
  );
}

<%-- 매장 상세정보 데이터 셋팅 --%>
function setStoreData(data){

  $("#noDataArea").hide();
  $("#storeInfoViewArea").show();
  $("#additionalArea").hide();

  var storeDtlInfo = data.storeDtlInfo;
  var instPosCnt = data.instPosCnt;
  var posApproveList = data.posApproveList;

  $("#storeInfoTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);

  // 조회한 데이터 셋팅
  $("#rStoreCd").val(storeDtlInfo.storeCd);
  $("#rStoreNm").val(storeDtlInfo.storeNm);
  $("#rInstallPosCnt").val(instPosCnt);
  $("#rOwnerNm").val(storeDtlInfo.ownerNm);
  $("#rBizStoreNm").val(storeDtlInfo.bizStoreNm);
  $("#rBizNo1").val(storeDtlInfo.bizNo1);
  $("#rBizNo2").val(storeDtlInfo.bizNo2);
  $("#rBizNo3").val(storeDtlInfo.bizNo3);
  $("#rTelNo").val(storeDtlInfo.telNo);
  $("#rFaxNo").val(storeDtlInfo.faxNo);
  $("#rEmailAddr").val(storeDtlInfo.emailAddr);
  $("#rHmpgAddr").val(storeDtlInfo.hmpgAddr);
  $("#rPostNo").val(storeDtlInfo.postNo);
  $("#rAddr").val(storeDtlInfo.addr);
  $("#rAddrDtl").val(storeDtlInfo.addrDtl);
  $("#rSysRemark").val(storeDtlInfo.sysRemark);
  $("#rhdRemark").val(storeDtlInfo.hdRemark);
  $("#rStoreRemark").val(storeDtlInfo.remark);

  rClsFg.selectedValue = storeDtlInfo.clsFg;
  rSysStatFg.selectedValue = storeDtlInfo.sysStatFg;
  rArea.selectedValue = storeDtlInfo.areaCd;
  rManageVan.selectedValue = storeDtlInfo.vanCd;
  rAgency.selectedValue = storeDtlInfo.agencyCd;

  var sysOpenDate = storeDtlInfo.sysOpenDate;
  sysOpenDateStr = sysOpenDate.split(".");
  var sysOpenDateType = new Date(sysOpenDateStr[0], sysOpenDateStr[1], sysOpenDateStr[2]);
  rOpenPosDate.value = sysOpenDateType;

  rClsFg.isReadOnly = true;
  $("#rInstallPosCnt").attr("readonly", "readonly");

  thePosGrid.itemsSource = new wijmo.collections.CollectionView(posApproveList);
  thePosGrid.itemsSource.trackChanges = true;

  $("#rStoreCdTxt").show();
  $("#rStoreCdRadio").hide();

  $("#btnRegist").hide();
  $("#btnSave").show();
}


<%-- ============================================= 버튼 클릭 이벤트 관련 =========================================== --%>

<%-- 매장유형선택 이벤트 --%>
$("input[name=rStoreCdRadio]").change(function(){
  var thisVal = $(this).val();
  if(thisVal == "<s:message code='storeManage.storeType.demo' />") {
    rSysStatFg.selectedValue = "9";
    rSysStatFg.isReadOnly = true;
  } else {
    rSysStatFg.selectedValue = "";
    rSysStatFg.isReadOnly = false;
  }
});

<%-- 설치포스수 변경시 포스별 벤사 설정 그리드 변경--%>
$("#rInstallPosCnt").change(function(){
  thePosGrid.itemsSource = new wijmo.collections.CollectionView([]);

  var posCnt = $("#rInstallPosCnt").val();
  for(var i=1; i<=posCnt; i++){
    var newItem = thePosGrid.collectionView.addNew();
    newItem.posNo  = (i < 10 ? "0"+i : i);
    newItem.vanCd  = "001"; // 기본벤사 KCP
    newItem.vanCertYn  = "N";
    thePosGrid.collectionView.commitNew();
  }
});


<%-- 사업자번호 중복 체크 버튼 클릭--%>
var isBizChk = false;

$("#btnChkBizNo").click(function(){

  if($("#rBizNo1").val() == "" || $("#rBizNo1").val() == "" || $("#rBizNo1").val() == "") {
    s_alert.pop("<s:message code='storeManage.require.input.bizNo'/>");
    return;
  }

  var param = {};
  param.bizNo1 = $("#rBizNo1").val();
  param.bizNo2 = $("#rBizNo2").val();
  param.bizNo3 = $("#rBizNo3").val();
  param.bizNo = $("#rBizNo1").val() + $("#rBizNo2").val() + $("#rBizNo3").val();

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


<%-- 매장환경조회 버튼 클릭 --%>
$("#btnStoreSetting").click(function(){
  if(rEnvHqOffice.selectedValue == null || rEnvHqOffice.selectedValue == "") {
    s_alert.pop("<s:message code='storeManage.require.select.hq' />");
    return;
  }

  if(rEnvStore.selectedValue == null || rEnvStore.selectedValue == "") {
    s_alert.pop("<s:message code='storeManage.require.select.store' />");
    return;
  }

  var param = {};
  param.hqOfficeCd = rEnvHqOffice.selectedValue;
  param.storeCd = rEnvStore.selectedValue;

  openEnvInfoLayer(param);
});

<%-- 신규등록 --%>
$("#btnRegist").click(function(){
  chkVal("/store/manage/storeManage/storeManage/saveStoreInfo.sb");
});

<%-- 저장 --%>
$("#btnSave").click(function(){
  chkVal("/store/manage/storeManage/storeManage/updateStoreInfo.sb");
});

<%-- 매장환경 탭 클릭시 --%>
$("#storeInfoViewArea #storeEnv").click(function(){
  if( selectedStore == null || selectedStore.storeCd == null || selectedStore.storeCd == "") {
    s_alert.pop("<s:message code='storeManage.require.regist.store1'/>");
    return;
  }
  openEnvLayer();
});

<%-- ============================================= 데이터 저장 관련 =========================================== --%>

<%-- validation --%>
function chkVal(sendUrl) {

  <%-- 매장명을 입력해주세요. --%>
  var msg = "<s:message code='storeManage.storeNm'/> <s:message code='cmm.require.text'/>";
  if($("#rStoreNm").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 대표자명을 입력해주세요. --%>
  var msg = "<s:message code='storeManage.onwerNm'/> <s:message code='cmm.require.text'/>";
  if($("#rOwnerNm").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 사업자번호를 입력해주세요. --%>
  var msg = "<s:message code='storeManage.bizNo'/> <s:message code='cmm.require.text'/>";
  if($("#rBizNo1").val() === "" || $("#rBizNo2").val() === "" || $("#rBizNo3").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 상호명을 입력해주세요. --%>
  var msg = "<s:message code='storeManage.bizStoreNm'/> <s:message code='cmm.require.text'/>";
  if($("#rBizStoreNm").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 용도를 선택해주세요. --%>
  var msg = "<s:message code='storeManage.clsFg'/> <s:message code='cmm.require.select'/>";
  if(rClsFg.selectedValue == "" || rClsFg.selectedValue == null) {
    s_alert.pop(msg);
    return;
  }

  <%-- 매장상태구분을 선택해주세요. --%>
  var msg = "<s:message code='storeManage.sysStatFg'/> <s:message code='cmm.require.select'/>";
  if(rSysStatFg.selectedValue == "" || rSysStatFg.selectedValue == null) {
    s_alert.pop(msg);
    return;
  }

  <%-- 설치포스수 중복체크를 해주세요. --%>
  var msg = "<s:message code='storeManage.installPosCnt'/> <s:message code='cmm.require.text'/>";
  if($("#rInstallPosCnt").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 날씨표시지역을 선택해주세요. --%>
  var msg = "<s:message code='storeManage.weatherArea'/> <s:message code='cmm.require.select'/>";
  if(rArea.selectedValue == "" || rArea.selectedValue == null) {
    s_alert.pop(msg);
    return;
  }

  <%-- 사업자번호를 입력해주세요. --%>
  var msg = "<s:message code='storeManage.bizNo'/> <s:message code='cmm.require.text'/>";
  if($("#rBizNo1").val() === "" || $("#rBizNo2").val() === "" || $("#rBizNo3").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 사업자번호 중복체크를 해주세요. --%>
  var msg = "<s:message code='storeManage.require.duplicate.bizNo'/>";
  if(!isBizChk) {
    s_alert.pop(msg);
    return;
  }

  <%-- 전화번호를 입력해주세요. --%>
  var msg = "<s:message code='storeManage.telNo'/> <s:message code='cmm.require.text'/>";
  if($("#rTelNo").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 이메일주소를 입력해주세요. --%>
  <%--
  var msg = "<s:message code='storeManage.emailAddr'/> <s:message code='cmm.require.text'/>";
  if($("#rEmailAddr").val() === "") {
    s_alert.pop(msg);
    return;
  }
  --%>

  <%-- 주소를 입력해주세요. --%>
  var msg = "<s:message code='storeManage.addr'/> <s:message code='cmm.require.text'/>";
  if($("#rPostNo").val() === "" || $("#rAddr").val() === "" || $("#rAddrDtl").val() === "") {
    s_alert.pop(msg);
    return;
  }

  <%-- 관리업체를 선택해주세요. --%>
  var msg = "<s:message code='storeManage.manageVan'/> <s:message code='cmm.require.select'/>";
  if(rManageVan.selectedValue == "" || rManageVan.selectedValue == null) {
    s_alert.pop(msg);
    return;
  }

  <%-- 대리점를 선택해주세요. --%>
  var msg = "<s:message code='storeManage.agency'/> <s:message code='cmm.require.select'/>";
  if(rAgency.selectedValue == "" || rAgency.selectedValue == null) {
    s_alert.pop(msg);
    return;
  }

  <%-- 신규등록시 --%>
  if($("#rStoreCdRadio").is(":visible")) {

    <%--  본사코드 데모 선택시 상태도 데모를 선택해주세요. --%>
    var storeType = $('input:radio[name="rStroreCdRadio"]:checked').val();
    var msg = "<s:message code='storeManage.storeType.comm.error' />";
    if(storeType == "C") {
      if(rSysStatFg.selectedValue == "9") {
        s_alert.pop(msg);
        return;
      }
    }

    <%-- 매장환경복사 체크된 값이 있을때 --%>
    if($("input:checkbox[name='copyChk']:checked").length > 0) {

      <%-- 매장환경 복사할 본사를 선택해주세요.--%>
      var msg = "<s:message code='storeManage.copy.storeEnv.hqOfficeCd'/> <s:message code='cmm.require.select'/>";
      if(rEnvHqOffice.selectedValue == "" || rEnvHqOffice.selectedValue == null) {
        s_alert.pop(msg);
        return;
      }
      <%-- 매장환경 복사할 매장을 선택해주세요.--%>
      var msg = "<s:message code='storeManage.copy.storeEnv.storeCd'/> <s:message code='cmm.require.select'/>";
      if(rEnvStore.selectedValue == "" || rEnvStore.selectedValue == null) {
        s_alert.pop(msg);
        return;
      }
    }
  }


  <%-- 포스별 승인 정보 --%>
  for(var i=0; i<thePosGrid.itemsSource.items.length; i++) {
    var msg = "<s:message code='storeManage.vanCd'/> <s:message code='cmm.require.select'/>";
    if(thePosGrid.itemsSource.items[i].vanCd == ""){
      s_alert.pop(msg);
      return;
    }

    var msg = "<s:message code='storeManage.vanTermnlNo'/> <s:message code='cmm.require.text'/>";
    if(thePosGrid.itemsSource.items[i].vanTermnlNo == null || thePosGrid.itemsSource.items[i].vanTermnlNo == ""){
      s_alert.pop(msg);
      return;
    }

    var msg = "<s:message code='storeManage.vanSerNo'/> <s:message code='cmm.require.text'/>";
    if(thePosGrid.itemsSource.items[i].vanSerNo == null  || thePosGrid.itemsSource.items[i].vanSerNo == ""){
      s_alert.pop(msg);
      return;
    }
  }

  saveStore(sendUrl);
}

<%-- 신규등록 또는 저장 --%>
function saveStore(sendUrl){
  var param = {};

  if($("#rStoreCdRadio").is(":visible")) {
    param.hqOfficeCd = selectedHq.hqOfficeCd;
  }else {
    param.hqOfficeCd = selectedStore.hqOfficeCd;
  }

  param.storeCd         = $("#rStoreCd").val();
  param.storeNm         = $("#rStoreNm").val();
  param.openPosDate     = getDate(rOpenPosDate);
  param.installPosCnt  = $("#rInstallPosCnt").val();
  param.ownerNm         = $("#rOwnerNm").val();
  param.bizStoreNm      = $("#rBizStoreNm").val();
  param.bizNo1          = $("#rBizNo1").val();
  param.bizNo2          = $("#rBizNo2").val();
  param.bizNo3          = $("#rBizNo3").val();
  param.bizNo           = $("#rBizNo1").val() + $("#rBizNo2").val() + $("#rBizNo3").val();
  param.telNo           = $("#rTelNo").val();
  param.faxNo           = $("#rFaxNo").val();
  param.emailAddr       = $("#rEmailAddr").val();
  param.hmpgAddr        = $("#rHmpgAddr").val();
  param.postNo          = $("#rPostNo").val();
  param.addr            = $("#rAddr").val();
  param.addrDtl         = $("#rAddrDtl").val();
  param.areaCd          = rArea.selectedValue;
  param.clsFg           = rClsFg.selectedValue;
  param.sysStatFg       = rSysStatFg.selectedValue;
  param.sysOpenDate     = getDate(rOpenPosDate);
  param.vanCd           = rManageVan.selectedValue;
  param.agencyCd        = rAgency.selectedValue;
  param.sysRemark       = $("#rSysRemark").val();
  param.hdRemark        = $("#rHdRemark").val();
  param.remark          = $("#rStoreRemark").val();

  param.copyHqOfficeCd  = rEnvHqOffice.selectedValue;
  param.copyStoreCd     = rEnvStore.selectedValue;
  param.cornerUseYn     = rCornerUseYn.selectedValue; // 코너 사용여부에 따라 포스별, 코너별 그리드 구분

  var copyChkVal = "";

  $("input[name=copyChk]:checked").each(function() {
    copyChkVal += ($(this).val() + "|");
  });
  param.copyChkVal = copyChkVal;

  $.postJSONSave(sendUrl, param, function(result) {

    s_alert.pop("<s:message code='cmm.saveSucc'/>");

    if(selectedStore.storeCd == undefined) {
      selectedStore.storeCd = result.data;
    }
    //포스 데이터 저장 (//TODO 추후 코너 개발시 추가개발)
    saveOtherInfo();
  },
    function (result) {
      s_alert.pop(result.message);
      return;
    }
  );
}

<%-- 기본정보 이외 정보 저장 --%>
function saveOtherInfo() {

  var paramArr = new Array();

  for(var i=0; i<thePosGrid.itemsSource.items.length; i++) {
    thePosGrid.itemsSource.items[i].storeCd = selectedStore.storeCd;
    paramArr.push(thePosGrid.itemsSource.items[i]);
  }

  $.postJSONArray("/store/manage/storeManage/storeManage/saveStorePosInfo.sb", paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    search(1);
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
}


</script>
