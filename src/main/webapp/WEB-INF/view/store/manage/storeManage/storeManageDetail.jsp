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
      <li><a id="storeInfo" href="javascript:;" class="on"><s:message code="storeManage.storeInfo" /></a></li>
      <%-- 메뉴권한 --%>
      <%-- <li><a id="storeMenuAuth" href="javascript:;"><s:message code="storeManage.storeMenuAuth" /></a></li> --%>
      <%-- 매장환경 --%>
      <li><a id="storeEnv"  href="javascript:;"><s:message code="storeManage.storeEnv" /></a></li>
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
                  <input id="rOpenPosDate" name="startDt" class="w200" />
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
              <a id="btnChkBizNo" href="javascript:;" class="btn_grayS ml5"><s:message code="storeManage.chk.duplicate" /></a><Br />
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
            <th><s:message code="storeManage.emailAddr" /><em class="imp">*</em></th>
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
              <a id="btnSrchAddr" href="javascript:;" class="btn_grayS ml5"><s:message code="storeManage.srchAddr" /></a><Br />
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
            <th><s:message code="storeManage.hqRemark" /></th>
            <td colspan="3"><input id="rHqRemark" type="text" class="sb-input w100" /></td>
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
                <a id="btnStoreSetting" href="javascript:;" class="btn_grayS mt35 mb15"><s:message code="storeManage.srchStoreSetting" /></a><br />
                <%-- 매장환경 체크박스  --%>
                <%-- <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" value="productCls"/> <label for="chk1" ><s:message code="storeManage.product" /></label></span> --%><%-- 상품분류--%>
                <%-- <span class="chk mr10 pdb5 txtIn"><input type="checkbox" name="copyChk" value="auth"/> <label for="chk2" ><s:message code="storeManage.auth" /></label></span> --%><%-- 메뉴권한 --%>
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

      <%--============================================= VAN사 설정 =============================================--%>
      <h3 class="h3_tbl"><s:message code="storeManage.vanSetting" /></h3>
      <table class="searchTbl2">
        <colgroup>
          <col class="w15" />
          <col class="w40" />
          <col class="w45" />
        </colgroup>
        <thead>
          <tr>
            <%-- VAN사 --%>
            <th><s:message code="storeManage.vanCompany" /></th>
            <%-- VAN사 계약번호 --%>
            <th><s:message code="storeManage.vanContract" /></th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <%-- VAN사 --%>
            <td>
              <div class="sb-select">
                <div id="rVan"></div>
              </div>
            </td>
            <%-- VAN사 계약번호 --%>
            <td><input id="rVanContractNum" type="text" class="sb-input w100" /></td>
          </tr>
        </tbody>
      </table>

      <%--============================================= 매장 코너 승인 설정 =============================================--%>
      <h3 class="h3_tbl pdt5 lh30"><s:message code="storeManage.storeCornerSetting" /></h3>
      <table class="searchTbl2">
        <tbody>
          <tr>
            <%-- 코너 사용여부 --%>
            <th class="tc"><s:message code="storeManage.cornerUseYn" /></th>
            <td class="tc">
              <div class="sb-select">
                <div id="rCornerUseYn"></div>
              </div>
            </td>
            <%-- 현금영수증 자진발급 --%>
            <th class="tc"><s:message code="storeManage.cashBillAutoUseYn" /></th>
            <td class="tc">
              <div class="sb-select">
                <div id="rCashBillAutoUseYn"></div>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <%--============================================= 코너별 승인 (매장 코너 승인 설정이 '코너개별승인'일때 사용) =============================================--%>
      <div id="cornerApproveArea" style="display:none;">
        <h3 class="h3_tbl pdt5 lh30"><s:message code="storeManage.cornerApproval" />
          <span class="fr">
            <%-- 추가 --%>
            <a href="javascript:;" id="btnCornerAdd" class="btn_grayS mr5"><s:message code="cmm.add" /></a>
            <%-- 삭제 --%>
            <a href="javascript:;" id="btnCornerDel" class="btn_grayS"><s:message code="cmm.delete" /></a>
          </span>
        </h3>
        <div id="theCornerGrid" style="height:150px;"></div>
      </div>

      <%--============================================= 포스별 승인 (매장 코너 승인 설정이 '포스별승인'일때 사용) ============================================= --%>
      <div id="posApproveArea" style="display:none;">
        <h3 class="h3_tbl">포스별 승인</h3>
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

var selectedHqOfficeCd = "";

var vanList        = ${ccu.getVanList()};
var agencyList     = ${ccu.getAgencyList()};
var hqList         = ${ccu.getHqOfficeList()};
var clsFg          = ${ccu.getCommCodeSelect("003")};
var sysStatFg      = ${ccu.getCommCodeSelect("005")};
var areaCd         = ${ccu.getCommCodeSelect("092")};
var cornerUseYn    = ${ccu.getEnvCodeExcpAll("114")};
var cashBillYn     = ${ccu.getEnvCodeExcpAll("276")};
var useYn          = ${ccu.getCommCodeExcpAll("904")};

console.log(hqList);

<%-- ============================================= 그리드, 위즈모 관련 =========================================== --%>

var rOpenPosDate   = wcombo.genDateVal("#rOpenPosDate", "${sessionScope.sessionInfo.startDt}");
var rClsFg         = wcombo.genCommonBox("#rClsFg", clsFg);
var rSysStatFg     = wcombo.genCommonBox("#rSysStatFg", sysStatFg);
var rArea          = wcombo.genCommonBox("#rArea", areaCd);
var rManageVan     = wcombo.genCommonBox("#rManageVan", vanList);
var rAgency        = wcombo.genCommonBox("#rAgency", agencyList);
var rEnvHqOffice   = wcombo.genCommonBox("#rEnvHqOffice", hqList);
var rEnvStore      = wcombo.genCommonBox("#rEnvStore", []);
var rVan           = wcombo.genCommonBox("#rVan", vanList);
var rCornerUseYn   = wcombo.genCommonBox("#rCornerUseYn", cornerUseYn);
var rCashBillUseYn = wcombo.genCommonBox("#rCashBillAutoUseYn", cashBillYn);

<%-- 코너별 승인 그리드 헤더 --%>
var cornerHeaderData =
  [
    {binding:"gChk", header:"<s:message code='storeManage.chk' />", dataType:wijmo.DataType.Boolean, width:40},
    {binding:"cornrCd", header:"<s:message code='storeManage.cornrCd' />", visible:false, width:"*" },
    {binding:"cornrNm", header:"<s:message code='storeManage.cornrNm' />", maxLength:15, width:"*"},
    {binding:"vanTermNo", header:"<s:message code='storeManage.vanContract' />", maxLength:20, width:"*"},
    {binding:"vanSerNo", header:"<s:message code='storeManage.vanSerNo' />", maxLength:20, width:"*"},
    {binding:"ownerNm", header:"<s:message code='storeManage.onwerNm' />", maxLength:15, width:"*"},
    {binding:"bizNo", header:"<s:message code='storeManage.bizNo' />", maxLength:10, width:"*"},
    {binding:"telNo", header:"<s:message code='storeManage.telNo' />", maxLength:15, width:"*"},
    {binding:"useYn", header:"<s:message code='storeManage.useYn' />", dataType:wijmo.DataType.Boolean, width:"*"}
  ];

<%-- 포스별 승인 그리드 헤더 --%>
var posHeaderData =
  [
    {binding:"posNo", header:"<s:message code='storeManage.posNo' />"},
    {binding:"vanTermnlNo", header:"<s:message code='storeManage.vanTermNo' />", visible:false },
    {binding:"vanCertYn", header:"<s:message code='storeManage.vanCertYn' />", dataType:wijmo.DataType.Boolean},
    {binding:"vanCertStartDate", header:"<s:message code='storeManage.vanCertStartDate' />", isReadOnly:true},
    {binding:"vanCertEndDate", header:"<s:message code='storeManage.vanCertEndDate' />", isReadOnly:true},
    {binding:"vanCertCnt", header:"<s:message code='storeManage.vanCertCnt' />", isReadOnly:true}
  ];

var theCornerGrid = wgrid.genGrid("#theCornerGrid", cornerHeaderData);
var thePosGrid    = wgrid.genGrid("#thePosGrid", posHeaderData);

theCornerGrid.isReadOnly = false;
thePosGrid.isReadOnly = false;

<%-- 추가설정에서 본사 클릭시, 해당 본사의 매장 목록 조회 --%>
rEnvHqOffice.selectedIndexChanged.addHandler(function(s, e){
  var param = {};
  param.hqOfficeCd = rEnvHqOffice.selectedValue;

  $.postJSON("/store/manage/storeManage/storeManage/getStoreComboList.sb", param, function(result) {

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    rEnvStore.itemsSource = result.data.list;
  }
  ,function(){
      s_alert.pop("Ajax Fail");
  });
});

<%-- 코너 사용여부 변경 --%>
rCornerUseYn.selectedIndexChanged.addHandler(function(s, e){

  if(rCornerUseYn.selectedValue == null || rCornerUseYn.selectedValue == "" || rCornerUseYn.selectedValue == "") {
    $("#cornerApproveArea").hide();
    $("#posApproveArea").hide();
  }
  else if(rCornerUseYn.selectedValue == "1" || rCornerUseYn.selectedValue == "2" ) {  <%-- 매장단위승인 또는 코너 개별승인--%>

    $("#cornerApproveArea").show();
    $("#posApproveArea").hide();

    <%--
    if(thePosGrid.rows.length > 0){
      s_alert.pop("<s:message code='storeManage.subCorner.exist' />");
      return;
    }
    --%>
    //theCornerGrid.itemsSource = [];
    theCornerGrid.itemsSource = new wijmo.collections.CollectionView([]);
    theCornerGrid.itemsSource.trackChanges = true;
  }
  else if(rCornerUseYn.selectedValue == "3" ) { <%-- 포스별승인 --%>

    $("#cornerApproveArea").hide();
    $("#posApproveArea").show();

    if(theCornerGrid.rows.length > 0) {
      if(theCornerGrid.rows[0].vanTermNo != ""){
        s_alert.pop("<s:message code='storeManage.terminal.exist' />");
        return;
      }
    }
    // 포스별 승인 그리드 내용 지우고
    for(var i = thePosGrid.itemsSource.itemCount-1; i >= 0; i-- ){
      thePosGrid.itemsSource.removeAt(i);
    }

    // 설치포스수 만큼 포스수 입력
    var posCnt = $("#rInstallPosCnt").val();

    for(var i=1; i<=posCnt; i++){
      var newItem = thePosGrid.collectionView.addNew();
      newItem.posNo  = (i < 10 ? "0"+i : i);
      newItem.vanCertYn  = "N";
      thePosGrid.collectionView.commitNew();
    }
  }
});



<%-- ============================================= 신규등록, 수정 폼 관련 =========================================== --%>

<%-- form reset --%>
function resetForm() {
  $("#regForm")[0].reset();

  rClsFg.selectedIndex      = 0;
  rSysStatFg.selectedIndex  = 0;
  rArea.selectedIndex       = 0;
  rVan.selectedIndex        = 0;
  rAgency.selectedIndex     = 0;
  rManageVan.selectedIndex  = 0;
  rOpenPosDate.value        = new Date();
  rEnvHqOffice.selectedIndex    = 0;
  rCornerUseYn.selectedIndex    = 0;
  rCashBillUseYn.selectedIndex  = 0;

  rClsFg.isReadOnly = false;
  $("#rInstallPosCnt").removeAttr("readonly");
}

<%-- 매장 신규등록 --%>
function newStoreReg(hqOfficeCd) {

  selectedHqOfficeCd = hqOfficeCd;

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

    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    console.log(result);

    setStoreData(result.data);
  }
  ,function(){
    s_alert.pop("Ajax Fail");
  });
}

<%-- 매장 상세정보 데이터 셋팅 --%>
function setStoreData(data){

  $("#noDataArea").hide();
  $("#storeInfoViewArea").show();
  $("#additionalArea").hide();

  console.log(data);

  var storeDtlInfo = data.storeDtlInfo;
  var instPosCnt = data.instPosCnt;
  var vanCornrList = data.vanCornrList;
  var cornrApproveList = data.cornrApproveList;
  var posApproveList = data.posApproveList;

  $("#storeInfoTitle").text("[" + selectedStore.storeCd + "] " + selectedStore.storeNm);

  // 조회한 데이터 셋팅
  $("#rStoreCd").val(storeDtlInfo.storeCd);
  $("#rStoreNm").val(storeDtlInfo.storeNm);
  //$("#rOpenPosDate").val();
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
  $("#rHqRemark").val(storeDtlInfo.hqRemark);
  $("#rStoreRemark").val(storeDtlInfo.remark);

  rClsFg.selectedValue = storeDtlInfo.clsFg;
  rSysStatFg.selectedValue = storeDtlInfo.sysStatFg;
  rArea.selectedValue = storeDtlInfo.areaCd;
  rManageVan.selectedValue = storeDtlInfo.vanCd;
  rAgency.selectedValue = storeDtlInfo.agencyCd;
  rOpenPosDate.value = storeDtlInfo.sysOpenDate;

  if(vanCornrList.length > 0) {
    $("#rVan").val(vanCornrList[0].vanCd);
    $("#rVanContractNum").val(vanCornrList[0].vanTermnlNo);//TODO 벤사 계약번호 확인(vanTermnlNo와 vanSerNo)

  }

  rClsFg.isReadOnly = true;
  $("#rInstallPosCnt").attr("readonly", "readonly");

  theCornerGrid.itemsSource = new wijmo.collections.CollectionView(cornrApproveList);
  thePosGrid.itemsSource = new wijmo.collections.CollectionView(posApproveList);

  theCornerGrid.itemsSource.trackChanges = true;
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
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    isBizChk = true;

    if(result.data == 0 ){
      <%-- 중복되는 사업자번호가 없습니다.--%>
      s_alert.pop("<s:message code='hqManage.no.duplicate.bizNo.msg'/>");
    } else {
      <%-- 사업자번호 사용현황 팝업 --%>
      openBizInfoLayer(param);
    }
  }
  ,function(){
    s_alert.pop("Ajax Fail");
  });
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


<%-- 코너별 승인 데이터 추가--%>
$("#btnCornerAdd").click(function(){
  var newItem = theCornerGrid.collectionView.addNew();
  newItem.chk = false;
  theCornerGrid.collectionView.commitNew();
});

<%-- 코너별 승인 데이터 삭제--%>
$("#btnCornerDel").click(function(){

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
  if(selectedStore.storeCd == null || selectedStore.storeCd == "") {
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
  //TODO 등록시에만 체크
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
  var msg = "<s:message code='storeManage.emailAddr'/> <s:message code='cmm.require.text'/>";
  if($("#rEmailAddr").val() === "") {
    s_alert.pop(msg);
    return;
  }

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
  saveStore(sendUrl);
}

<%-- 신규등록 또는 저장 --%>
function saveStore(sendUrl){
  var param = {};

  param.hqOfficeCd      = selectedHqOfficeCd;

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
  param.sysStatFg       = rSysStatFg.selectedValue
  param.sysOpenDate     = getDate(rOpenPosDate);
  param.vanCd           = rManageVan.selectedValue;
  param.agencyCd        = rAgency.selectedValue;
  param.sysRemark       = $("#rSysRemark").val();
  param.hqRemark        = $("#rHqRemark").val();
  param.remark          = $("#rStoreRemark").val();

  param.copyHqOfficeCd  = rEnvHqOffice.selectedValue;
  param.copyStoreCd     = rEnvStore.selectedValue;
  param.cornerUseYn     = rCornerUseYn.selectedValue; // 코너 사용여부에 따라 포스별, 코너별 그리드 구분

  var copyChkVal = "";

  $("input[name=copyChk]:checked").each(function() {
    copyChkVal += ($(this).val() + "|");
  });
  param.copyChkVal = copyChkVal;

  //TODO 우선 아래 코너 제외하고 저장해보기
  $.postJSONSave(sendUrl, param, function(result) {

    console.log("====================================================");
    console.log(result);
    if(result.status === "FAIL") {
      s_alert.pop(result.message);
      return;
    }
    s_alert.pop("<s:message code='cmm.saveSucc'/>");

    selectedStore = result.data[0];
    search(1);
    //showStoreDetail();
    //saveOtherInfo();
  }
  ,function(result){
    console.log(result);
    s_alert.pop(result.message);
  });
}

<%-- 기본정보 이외 정보 저장 --%>
function saveOtherInfo() {

  var saveUrl = "";

  if(rCornerUseYn.selectedValue == "1" || rCornerUseYn.selectedValue == "2" ) { <%-- 매장단위 승인, 코너개별승인  --%>
    saveUrl = "/store/manage/storeManage/storeManage/saveStoreCornr.sb";
  }  else if(rCornerUseYn.selectedValue == "3" ) { <%-- 포스별 승인 --%>
    saveUrl = "/store/manage/storeManage/storeManage/saveStorePos.sb";
  }

  if(rCornerUseYn.selectedValue == "1" || rCornerUseYn.selectedValue == "2" ){ <%-- 매장단위 승인, 코너개별승인  --%>

    var paramArr = new Array();

    for(var i=0; i<theCornerGrid.collectionView.itemsEdited.length; i++){
      theCornerGrid.collectionView.itemsEdited[i].status = "U";
      paramArr.push(theCornerGrid.collectionView.itemsEdited[i]);
    }
    for(var i=0; i<theCornerGrid.collectionView.itemsAdded.length; i++){
      theCornerGrid.collectionView.itemsAdded[i].status = "I";
      paramArr.push(grid2.collectionView.itemsAdded[i]);
    }
    for(var i=0; i<theCornerGrid.collectionView.itemsRemoved.length; i++){
      theCornerGrid.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(theCornerGrid.collectionView.itemsRemoved[i]);
    }
  }
  else if(rCornerUseYn.selectedValue == "3" ){ <%-- 포스별 승인 --%>

    var paramArr = new Array();

    for(var i=0; i<thePosGrid.collectionView.itemsEdited.length; i++){
      thePosGrid.collectionView.itemsEdited[i].status = "U";
      paramArr.push(thePosGrid.collectionView.itemsEdited[i]);
    }
    for(var i=0; i<thePosGrid.collectionView.itemsAdded.length; i++){
      grthePosGridid2.collectionView.itemsAdded[i].status = "I";
      paramArr.push(thePosGrid.collectionView.itemsAdded[i]);
    }
    for(var i=0; i<thePosGrid.collectionView.itemsRemoved.length; i++){
      thePosGrid.collectionView.itemsRemoved[i].status = "D";
      paramArr.push(thePosGrid.collectionView.itemsRemoved[i]);
    }
  }

  if(paramArr.length <= 0) {
    s_alert.pop("<s:message code='cmm.not.modify'/>");
    return;
  }

  $.postJSONArray(saveUrl, paramArr, function(result) {
    s_alert.pop("<s:message code='cmm.saveSucc' />");
    theCornerGrid.collectionView.clearChanges();
    showStoreDetail();
  },
  function(result) {
    s_alert.pop(result.data.msg);
  });
}


</script>
