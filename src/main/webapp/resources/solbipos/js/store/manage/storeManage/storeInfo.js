/****************************************************************
 *
 * 파일명 : storeInfo.js
 * 설  명 : 매장정보관리 > 매장상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

// 브랜드
var bHdHqBrandCd;

app.controller('storeInfoCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeInfoCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("areaCd", areaCd);
  $scope._setComboData("branchCd", [{"name": messages["cmm.select"], "value": ""}]);
  $scope._setComboData("vendorCd", vanList01);

  // [1250 맘스터치]
  // 콤보박스 데이터
  $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
  $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
  $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
  $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
  $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
  $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
  $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
  $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
  $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
  $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
  $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
  $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5

  // 관리자의 경우, 모든 본사(데모까지) 나오고, 총판의 경우, 자기가 관리하는 본사만 나오도록
  if(orgnFg === "AGENCY") {
    $scope._setComboData("envHqOfficeCd", authHqList);
  }else{
    $scope._setComboData("envHqOfficeCd", hqList);
  }

  // 시스템 오픈일자 / 포스개점일자
  var sysOpenDate = wcombo.genDateVal("#sysOpenDate", gvStartDate);
  var sysClosureDate = wcombo.genDateVal("#sysClosureDate", gvStartDate);

  // 사업자번호 중복체크 여부
  $scope.isBizChk = false;
  // readonly 값 체크 (조회인 경우 readonly : true)
  $scope.readOnlyStatus = false;

  // 날씨표시지역
  $scope.areaCdVal;
  $scope.setAreaCdVal = function(s){
    $scope.areaCdVal = s.selectedValue;
  };
  $scope.getAreaCdVal = function(){
    return $scope.areaCdVal;
  };

  // 용도
  $scope.clsFgVal;
  $scope.setClsFgVal = function(s){
    $scope.clsFgVal = s.selectedValue;
  };
  $scope.getClsFgVal = function(){
    return $scope.clsFgVal;
  };

  // 매장상태
  $scope.sysStatFgVal;
  $scope.setSysStatFgVal = function(s){
    if(s.selectedValue === "2"){
      $(".sysClosureDate").css("display", "");
    } else {
      $(".sysClosureDate").css("display", "none");
    }

     // 데모매장이 아닌 매장은 데모매장으로 수정 불가
    if($("#hdSysStatFg").val() !== null && $("#hdSysStatFg").val() !== "" && $("#hdSysStatFg").val() !== "9"){
        // 매장상태를 데모매장으로 변경할 수 없습니다.
        var msg = messages["storeManage.require.notSelectDemo"];
        if(s.selectedValue === "9"){
            $scope._popMsg(msg);
            s.selectedValue = $("#hdSysStatFg").val();
            return false;
        }
    }

    $scope.sysStatFgVal = s.selectedValue;
  };
  $scope.getSysStatFgVal = function(){
    return $scope.sysStatFgVal;
  };

  // 매장선택
  $scope.envStoreCdVal;
  // $scope.setEnvStoreCdVal = function(s){
  //   $scope.envStoreCdVal = s.selectedValue;
  // };
  // $scope.getEnvStoreCdVal = function(){
  //   return $scope.envStoreCdVal;
  // };
  $scope.setEnvStoreCdVal = function(s,e){
    $scope.envStoreCdVal = s.selectedValue;

    // 매장환경복사 체크 disabled
    $scope.copyStoreSettingChk();
  };

  // 수정시, 밴더코드 변경여부 파악을 위해
  var b_vendorCd = "";

  /*********************************************************
   * [추가설정] 복사할 본사 클릭시, 해당 본사의 매장 목록 조회
   * *******************************************************/
  $scope.envHqOfficeCdVal;
  $scope.setEnvHqOfficeCdVal = function(s,e){

    $scope.envHqOfficeCdVal = s.selectedValue;
    $scope.envStoreCdVal = "";
    $scope.store.copyStoreInfo = "";
    $scope.store.copyStoreCd = "";
    $scope.copyStoreSettingChk();

    if( isNull(s.selectedValue) ) {
      return false;
    }

    var params = {};
    params.hqOfficeCd = s.selectedValue;

    $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/getStoreComboList.sb", params,
      function(response){
        $scope._setComboData("envStoreCd", response.data.data.list);
      }
    );
  };
  $scope.getEnvHqOfficeCdVal = function (){
    return $scope.envHqOfficeCdVal;
  };

  $scope.searchStore = function(){

    if( isNull( $scope.envHqOfficeCdVal) ) {
      $scope._popMsg(messages["storeManage.hq.msg"]);
      return false;
    }

    var storeScope = agrid.getScope('storeManageCtrl');

    if(!$.isEmptyObject(storeScope.getSelectedStore()) ) {  // 신규등록시에만 매장 조회 팝업 호출
      return false;
    }

    var popup = $scope.storeLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var storeScope = agrid.getScope('searchStoreCtrl');
      storeScope.hqOfficeCd = $scope.getEnvHqOfficeCdVal();
      storeScope.$apply(function(){
        storeScope._gridDataInit();

        if( !$.isEmptyObject(storeScope.getStore())  ) {
          if(storeScope.getStore().storeCd === undefined){
            $scope.store.copyStoreInfo = "";
            $scope.store.copyStoreCd = "";
          } else {
            $scope.store.copyStoreInfo = "[" + storeScope.getStore().storeCd + "]" + storeScope.getStore().storeNm;
            $scope.store.copyStoreCd = storeScope.getStore().storeCd;
          }
          $scope.envStoreCdVal = storeScope.getStore().storeCd;

          // 매장환경복사 체크 disabled
          $scope.copyStoreSettingChk();
        }
      });
      // 본사 정보 초기화(이전데이터 남아있는 현상 발생)
      storeScope.setStore("");
    });
    event.preventDefault();
  };

  /*********************************************************
   * grid 초기화
   * *******************************************************/
  $scope.initGrid = function (s, e) {};

  /*********************************************************
   * 팝업 오픈
   * 선택된 매장이 없으면 : 신규등록 (폼 리셋)
   * 선택된 매장이 있으면 : 데이터 조회
   * *******************************************************/
  $scope.$on("storeInfoCtrl", function(event, data) {

    var storeScope = agrid.getScope('storeManageCtrl');

    // 기존 매장상태구분 값 hidden 초기화
    $("#hdSysStatFg").val("");

    if($.isEmptyObject(storeScope.getSelectedStore()) ) {
      $scope.resetForm();

      // 본사에서 등록시 본사정보 자동셋팅
      if(orgnFg === "HQ"){
        $scope.setHqInfo();
      }

    } else {
      $scope.chkVanFix();
    }

    event.preventDefault();
  });

  /*********************************************************
   * 매장 신규 등록을 위한 폼 리셋
   * *******************************************************/
  $scope.resetForm = function(){

    $("#viewForm")[0].reset();
    $("#storeInfoTitle").text("");

    $scope.store.hqOfficeCd = '';
    $scope.store.hqOfficeNm = '';
    $scope.store.storeCd = '';
    $scope.store.storeCdChkFg ="";
    $scope.store.storeCdInputType ="";
    $scope.store.storeNm ="";
    $scope.store.bizStoreNm ="";
    $scope.store.ownerNm ="";


    if (arrAgencyCol.indexOf(orgnCd) !== -1) {
      $("#lblVanFixFg").text("Y");
    }else{
      $("#lblVanFixFg").text("N");
    }

    // 시스템 오픈일자 / 포스개점일자
    // $("#sysOpenDate").attr("disabled", false);
    // $("#sysOpenDate").css('background-color', '#FFFFFF');
    sysOpenDate.value = getCurDate('-');
    // 시스템 폐점일자
    sysClosureDate.value = getCurDate('-');

    $scope.sysStatFgCombo.isReadOnly    = false;
    $scope.sysStatFgCombo.selectedIndex = 0;
    $scope.clsFgCombo.isReadOnly        = false;
    $scope.clsFgCombo.selectedIndex     = 0;
    $scope.areaCdCombo.selectedIndex    = 1; // 기본값: 서울특별시
    //$scope.store.installPosCnt.isReadOnly = false;
    //$("#installPosCnt").attr("disabled", false);
    $("#installPosCnt").css('background-color', '#FFFFFF');
    $scope.store.installPosCnt          = '';
    $("#hdSysStatFg").val("");

    $scope.store.bizNo1 ="";
    $scope.store.bizNo2 ="";
    $scope.store.bizNo3 ="";
    $scope.store.beforeBizNo ="";
    $scope.store.telNo ="";
    $scope.store.faxNo ="";
    $scope.store.mpNo ="";
    $scope.store.emailAddr ="";
    $scope.store.hmpgAddr ="";

    $scope.store.postNo ="";
    $scope.store.addr ="";
    $scope.store.addrDtl ="";

    $scope.readOnlyStatus               = false;

    $("#installPosCnt").css('background-color', '#ffffff');
    $("#storeCd").attr('placeholder','');
    $("#storeCd").attr("readonly",true);
    $("#storeCd").css("width", "100%");
    $("#btnChkStoreCd").css("display", "none");
    $("#additionalArea").css("display", "");
    // 아트박스(운영H0345,H0094)(개발DS012)
    if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
      $("#addr").attr("readonly", false);
    }else{
      $("#addr").attr("readonly", true);
    }

    $scope.store.vanCd = "";
    $scope.store.vanNm = "";

    // KOCES 총판은 관리벤사 KOCES로 설정
    if(arrAgencyCol.indexOf(orgnCd) !== -1){
      $scope.store.vanCd = "008";
      $scope.store.vanNm = "KOCES";
      $("#manageVanCd").val($scope.store.vanCd);
      $("#manageVanNm").val($scope.store.vanNm);
    }
    $scope.store.agencyCd = "";
    $scope.store.agencyNm = "";

    $scope._setComboData("branchCd", [{"name": messages["cmm.select"], "value": ""}]);
    $scope.branchCdCombo.selectedIndex = 0;
    $scope.store.branchCd = "";

    $scope.vendorCdCombo.selectedIndex = 0;
    $scope.store.vendorCd = "";
    b_vendorCd = "";

    // 총판계정으로 접속한 경우, 해당 총판의 데이터만 조회되도록 함.
    // if(orgnFg === "AGENCY" && pAgencyCd !== "00000"){
    if(orgnFg === "AGENCY"){
      $scope.store.agencyCd = orgnCd;
      $scope.store.agencyNm = orgnNm;
      $("#agencyCd").val(orgnCd);
      $("#agencyNm").val(orgnNm);
    }

    $scope.store.siteCd = "";
    $scope.store.mapStoreCd = "";
    $scope.store.latitude = "";
    $scope.store.longitude = "";
    $scope.store.vendorTermnlNo = "";
    $scope.store.vendorSerNo = "";
    $("#siteCd").val("");
    $("#mapStoreCd").val("");
    $("#latitude").val("");
    $("#longitude").val("");
    $("#vendorTermnlNo").val("");
    $("#vendorSerNo").val("");

    // 매장코드8이상사용 확인 초기화
    $("#hdDigit8Store").val("");

    // 웹사용자아이디 입력 영역 숨김
    $("#userId").val("");
    $("#userIdChkFg").val("");
    $("#userPwd").val("");
    $("#userPwdConf").val("");
    $("#trUser").css("display", "none");

    $scope.store.userId = "";
    $scope.store.userIdChkFg = "";
    $scope.store.userPwd = "";
    $scope.store.userPwdConf = "";

    // BBQ 매장코드 초기화
    $("#hdBbqStoreCd").val("");

    $scope.envHqOfficeCdCombo.selectedIndex = 0;

    // [1250 맘스터치]
    $("#hdMomsEnvstVal").val("");
    $("#trMomsEnvst").css("display", "none");
    $("#divMomsEnvst").css("display", "none");
    // 콤보박스 데이터
    $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
    $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
    $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
    $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
    $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
    $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
    $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
    $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
    $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
    $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
    $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
    $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5
  };

  $scope.chkVanFix = function(){
    var params = {};
    var storeScope  = agrid.getScope('storeManageCtrl'); // 선택된 매장
    params.storeCd  = storeScope.getSelectedStore().storeCd;
    // params.agencyCd = orgnCd;

    // VAN사 변경 허용 체크
    $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/chkVanFix.sb", params, function(response){
      var chkVanFix = response.data.data;
      if(chkVanFix === "Y" || chkVanFix === "N") {
        $("#lblVanFixFg").text(chkVanFix);
      }else{
        $("#lblVanFixFg").text("N");
      }
      $scope.getStoreInfo();
    });

  }

  /*********************************************************
   * 매장정보 조회
   * *******************************************************/
  $scope.getStoreInfo = function(){

    var storeScope = agrid.getScope('storeManageCtrl'); // 선택된 매장
    var params     = storeScope.getSelectedStore();

    // todo 데이터 메세지
    $scope._postJSONQuery.withOutPopUp( '/store/manage/storeManage/storeManage/getStoreDetail.sb', params, function(response){
      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.storeInfoLayer.hide();
        return false;
      }

      var installPosCnt   = response.data.data.instPosCnt;
      var storeDetailInfo = response.data.data.storeDtlInfo;
      var vBranchCd = storeDetailInfo.branchCd; // 그룹 (콤보 먼저 셋팅 후 바인딩시 사용)
      // [1250 맘스터치]
      // 콤보 먼저 셋팅 후 바인딩시 사용
      var vMomsTeam = nvl(storeDetailInfo.momsTeam, "");
      var vMomsAcShop = nvl(storeDetailInfo.momsAcShop, "");
      var vMomsAreaFg = nvl(storeDetailInfo.momsAreaFg, "");
      var vMomsCommercial = nvl(storeDetailInfo.momsCommercial, "");
      var vMomsShopType = nvl(storeDetailInfo.momsShopType, "");
      var vMomsStoreManageType = nvl(storeDetailInfo.momsStoreManageType, "");
      var vHqBrandCd = nvl(storeDetailInfo.hqBrandCd, "");
      var vMomsStoreFg01 = nvl(storeDetailInfo.momsStoreFg01, "");
      var vMomsStoreFg02 = nvl(storeDetailInfo.momsStoreFg02, "");
      var vMomsStoreFg03 = nvl(storeDetailInfo.momsStoreFg03, "");
      var vMomsStoreFg04 = nvl(storeDetailInfo.momsStoreFg04, "");
      var vMomsStoreFg05 = nvl(storeDetailInfo.momsStoreFg05, "");

      console.log('storeDetailInfo',storeDetailInfo);


      $("#storeInfoTitle").text("[" + storeDetailInfo.storeCd + "] " + storeDetailInfo.storeNm);

      $scope.store                        = storeDetailInfo;
      $scope.store.hqOfficeNm             = storeDetailInfo.hqOfficeNm;
      $scope.store.installPosCnt          = installPosCnt;
      $scope.store.beforeBizNo            = storeDetailInfo.bizNo;

      // 시스템 오픈일자 / 포스개점일자
      // $("#sysOpenDate").attr("disabled", true);
      // $("#sysOpenDate").css('background-color', '#F0F0F0');
      sysOpenDate.value = new Date(getFormatDate(storeDetailInfo.sysOpenDate, "-"));
      sysClosureDate.value = new Date(getFormatDate(storeDetailInfo.sysClosureDate, "-"));
      // 시스템 폐점일자

      $scope.areaCdCombo.selectedValue    = storeDetailInfo.areaCd;
      $scope.clsFgCombo.selectedValue     = storeDetailInfo.clsFg;
      $scope.sysStatFgCombo.selectedValue = storeDetailInfo.sysStatFg;

      // 직영구분
      if(storeDetailInfo.directManageYn === null || storeDetailInfo.directManageYn === undefined || storeDetailInfo.directManageYn === ""){
        $("input:radio[id='directManageY']").prop("checked", false); // 직영
        $("input:radio[id='directManageN']").prop("checked", false); // 가맹
      }else{
        if(storeDetailInfo.directManageYn === 'Y'){
          $("input:radio[id='directManageY']").prop("checked", true); // 직영
        }else if(storeDetailInfo.directManageYn === 'N'){
          $("input:radio[id='directManageN']").prop("checked", true); // 가맹
        }
      }

      $scope.readOnlyStatus                  = true;
      //$scope.store.installPosCnt.isReadOnly = true;
      //$("#installPosCnt").attr("disabled", true);
      $("#installPosCnt").css('background-color', '#F0F0F0');
      $("#hdSysStatFg").val(storeDetailInfo.sysStatFg);

      if(storeDetailInfo.sysStatFg === '9'){
          $scope.sysStatFgCombo.isReadOnly = true;
      } else {
          $scope.sysStatFgCombo.isReadOnly = false;
      }

      $scope.store.storeCdInputType = "";
      $scope.store.storeCdChkFg ="";
      $("#storeCd").attr("readonly",true);
      $("#storeCd").css("width", "100%");
      $("#btnChkStoreCd").css("display", "none");

      $("#additionalArea").css("display", "none");
      $scope.store.copyHqOfficeCd = "";
      $scope.store.copyStoreInfo = "";
      $("#storeEnvChk").prop("checked", false);
      $("#posEnvChk").prop("checked", false);
      $("#foodEnvChk").prop("checked", false);
      $("#kitchenPrintChk").prop("checked", false);
      $("#productChk").prop("checked", false);
      $("#salePriceChk").prop("checked", false);
      $("#supplyPriceChk").prop("checked", false);
      $("#posFnkeyChk").prop("checked", false);
      $("#touchKeyChk").prop("checked", false);
      $("#prtFormChk").prop("checked", false);
      $("#promotionChk").prop("checked", false);
      $("#dlvrProdChk").prop("checked", false);
      $("#menuAuthChk").prop("checked", false);
      $("#tableChk").prop("checked", false);

      // 아트박스(운영H0345,H0094)(개발DS012)
      if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
        $("#addr").attr("readonly", false);
      }else{
        $("#addr").attr("readonly", true);
      }

      $scope.vendorCdCombo.selectedValue = storeDetailInfo.vendorCd;
      b_vendorCd = storeDetailInfo.vendorCd;

      $scope.store.siteCd = storeDetailInfo.siteCd;
      $scope.store.mapStoreCd = storeDetailInfo.mapStoreCd;
      $scope.store.latitude = storeDetailInfo.latitude;
      $scope.store.longitude = storeDetailInfo.longitude;
      $scope.store.vendorTermnlNo = storeDetailInfo.vendorTermnlNo;
      $scope.store.vendorSerNo = storeDetailInfo.vendorSerNo;

      // 본사-그룹 콤보박스 set 후 -> 그룹정보 set
      $scope.setBranchDropdownList();

      // [1250 맘스터치]
      $("#hdMomsEnvstVal").val(storeDetailInfo.envst1250);
      $("#envst1114").val(storeDetailInfo.envst1114);
      if($("#hdMomsEnvstVal").val() === "1") {
        // [1250 맘스터치] 코드별 본사 공통코드 콤보박스 조회
        $scope.hqNmcodeComboList("151");
        $scope.hqNmcodeComboList("152");
        $scope.hqNmcodeComboList("153");
        $scope.hqNmcodeComboList("154");
        $scope.hqNmcodeComboList("155");
        $scope.hqNmcodeComboList("156");
        $scope.hqNmcodeComboList("167"); // momsStoreFg01
        $scope.hqNmcodeComboList("169"); // momsStoreFg02
        $scope.hqNmcodeComboList("170"); // momsStoreFg03
        $scope.hqNmcodeComboList("171"); // momsStoreFg04
        $scope.hqNmcodeComboList("172"); // momsStoreFg05
        // [1250 맘스터치] 브랜드 콤보박스 조회
        $scope.hqBrandCdComboList();

        $("#trMomsEnvst").css("display", "");
        $("#divMomsEnvst").css("display", "");
      } else if($("#envst1114").val() === "1") {
        // [1250 맘스터치] 브랜드 콤보박스 조회
        $scope.hqBrandCdComboList();
        $("#trMomsEnvst").css("display", "");
      } else {
        // 콤보박스 데이터
        $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
        $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
        $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
        $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
        $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
        $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
        if(vHqBrandCd === "" || vHqBrandCd === null) {
          $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
        } else {
          bHdHqBrandCd = vHqBrandCd;
        }
        $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
        $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
        $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
        $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
        $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5

        $("#trMomsEnvst").css("display", "none");
        $("#divMomsEnvst").css("display", "none");
      }

      // 콤보박스 먼저 set할 시간을 벌기 위해
      setTimeout(function() {
        $scope.branchCdCombo.selectedValue = vBranchCd;  // 그룹

        // [1250 맘스터치]
        if($("#hdMomsEnvstVal").val() === "1") {
          $scope.srchMomsTeamCombo.selectedValue = vMomsTeam;
          $scope.srchMomsAcShopCombo.selectedValue = vMomsAcShop;
          $scope.srchMomsAreaFgCombo.selectedValue = vMomsAreaFg;
          $scope.srchMomsCommercialCombo.selectedValue = vMomsCommercial;
          $scope.srchMomsShopTypeCombo.selectedValue = vMomsShopType;
          $scope.srchMomsStoreManageTypeCombo.selectedValue = vMomsStoreManageType;
          $scope.srchHqBrandCdCombo.selectedValue = vHqBrandCd;
          $scope.srchMomsStoreFg01Combo.selectedValue = vMomsStoreFg01;
          $scope.srchMomsStoreFg02Combo.selectedValue = vMomsStoreFg02;
          $scope.srchMomsStoreFg03Combo.selectedValue = vMomsStoreFg03;
          $scope.srchMomsStoreFg04Combo.selectedValue = vMomsStoreFg04;
          $scope.srchMomsStoreFg05Combo.selectedValue = vMomsStoreFg05;
        } else if($("#envst1114").val() === "1") {
          $scope.srchHqBrandCdCombo.selectedValue = vHqBrandCd;
        }
      }, 1000);

    });

    // 매장코드8이상사용 확인 초기화(상세화면에서는 필요가 없음)
    $("#hdDigit8Store").val("");

    // 웹사용자아이디 입력 영역 숨김(상세화면에서는 필요가 없음)
    $("#userId").val("");
    $("#userIdChkFg").val("");
    $("#userPwd").val("");
    $("#userPwdConf").val("");
    $("#trUser").css("display", "none");

    $scope.store.userId = "";
    $scope.store.userIdChkFg = "";
    $scope.store.userPwd = "";
    $scope.store.userPwdConf = "";

    // ERP 연동 매장 셋팅 팝업 관련 visible 처리(상세화면에서는 필요가 없음)
    $("#btnErpStoreSet").css("display", "none");

    // BBQ 매장코드 초기화(상세화면에서는 필요가 없음)
    $("#hdBbqStoreCd").val("");

  };

  /*********************************************************
   * 값 체크
   * *******************************************************/
  $scope.valueCheck = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    // 매장 신규 등록시 본사 선택 필수
    if($.isEmptyObject(storeScope.getSelectedStore()) ) {

      // 본사를 선택해주세요.
      var msg = messages["storeManage.hqOffice"] + messages["cmm.require.select"];
      if (isNull($scope.store.hqOfficeCd) || isNull($scope.store.hqOfficeNm) || isNull($scope.store.storeCdInputType)) {
        $scope._popMsg(msg);
        return false;
      }

      // [1262] 매장복사필수여부 사용 시 매장환경을 복사해야지만 저장 가능
     var msg = messages["storeManage.1262.msg"];
      if($("#envst1262").val() === "1" && isNull( $scope.envStoreCdVal)){
        $scope._popMsg(msg);
        return false;
      }

      // 매장코드 숫자 6자리 입력 금지(20250702)
      var msg = "등록제한된 매장코드입니다.(관리자에게 문의하여 주십시오.)";
      if (/^[0-9]+$/.test($scope.store.storeCd) === true && $scope.store.storeCd.length === 6) {
        $scope._popMsg(msg);
        return false;
      }

      // 매장코드 수동입력 시
      if ($scope.store.storeCdInputType === "1") {

        // 매장코드를 입력해주세요.
        var msg = messages["storeManage.storeCd"] + messages["cmm.require.text"];
        if (isNull($scope.store.storeCd)) {
          $scope._popMsg(msg);
          return false;
        }

        // 매장코드 중복체크를 해주세요.
        var msg = messages["storeManage.storeCdDuplicateChk.msg"];
        if (isNull($scope.store.storeCdChkFg)) {
          $scope._popMsg(msg);
          return false;
        }

        // 매장코드 중복체크를 다시 해주세요.
        var msg = messages["storeManage.storeCdDuplicateChkAgain.msg"];
        if ($scope.store.storeCd !== $scope.store.storeCdChkFg) {
          $scope._popMsg(msg);
          return false;
        }
      }

        // 맘스터치 (운영 DS034,DS038,DS040,H0393) (개발 DS021)
        if($scope.store.hqOfficeCd === "DS034" || $scope.store.hqOfficeCd === "DS038" || $scope.store.hqOfficeCd === "DS040" || $scope.store.hqOfficeCd === "H0393" || $scope.store.hqOfficeCd === "DS021") {
            // 매장코드 시작규칙
            if($scope.store.storeCd.substring(0, 1) == "C" || $scope.store.storeCd.substring(0, 1) == "D" || $scope.store.storeCd.substring(0, 1) == "E") {
            } else {
                $scope._popMsg("맘스터치 매장코드 시작규칙은 C, D, E 입니다."); // 맘스터치 매장코드 시작규칙은 C, D, E 입니다.
                return false;
            }
            // 매장코드 자리수
            if($scope.store.storeCd.length == 6) {
            } else {
                $scope._popMsg(messages["storeManage.moms.storeCdLengthChk"]); // 맘스터치 매장코드는 6자리만 가능합니다.
                return false;
            }
        // BBQ (운영 DS024,H0360) (개발 DS011)
        } else if($scope.store.hqOfficeCd === "DS024" || $scope.store.hqOfficeCd === "H0360" || $scope.store.hqOfficeCd === "DS011") {
            // 매장코드 시작규칙
            if($scope.store.storeCd.substring(0, 1) == "B" || $scope.store.storeCd.substring(0, 1) == "0" || $scope.store.storeCd.substring(0, 1) == "1" || $scope.store.storeCd.substring(0, 1) == "2" || $scope.store.storeCd.substring(0, 1) == "9") {
            } else {
                $scope._popMsg(messages["storeManage.bbq.storeCdChk"]); // BBQ 매장코드 시작규칙은 B(영어), 0, 1, 2, 9 입니다.
                return false;
            }
            // 매장코드 자리수
            if($scope.store.storeCd.length == 9) {
            } else {
                $scope._popMsg(messages["storeManage.bbq.storeCdLengthChk"]); // BBQ 매장코드는 9자리만 가능합니다.
                return false;
            }
        }
    }

    // 매장명을 입력해주세요.
    var msg = messages["storeManage.storeNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.storeNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 상호명을 입력해주세요.
    var msg = messages["storeManage.bizStoreNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.bizStoreNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 대표자명을 입력해주세요.
    var msg = messages["storeManage.onwerNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.ownerNm)) {
      $scope._popMsg(msg);
      return;
    }

    // 매장코드 수동입력 시 웹 사용자 아이디 관련
    if ($scope.store.storeCdInputType === "1") {
      if($("#hdDigit8Store").val() !== "" && $("#hdDigit8Store").val() !== null && $("#hdDigit8Store").val() !== undefined){

        // 웹사용자아이디(을)를입력해주세요.
        var msg = messages["storeManage.userId"] + messages["cmm.require.text"];
        if (isNull($scope.store.userId)) {
          $scope._popMsg(msg);
          return false;
        }

        // 웹사용자아이디 중복체크를 해주세요.
        var msg = messages["storeManage.userIdDuplicateChk.msg"];
        if (isNull($scope.store.userIdChkFg)) {
          $scope._popMsg(msg);
          return false;
        }

        // 웹사용자아이디 중복체크를 다시 해주세요.
        var msg = messages["storeManage.userIdDuplicateChkAgain.msg"];
        if ($scope.store.userId !== $scope.store.userIdChkFg) {
          $scope._popMsg(msg);
          return false;
        }

        // 비밀번호를 입력하세요.
        var msg = messages["storeManage.pwd"] + messages["cmm.require.text"];
        if (isNull($scope.store.userPwd)) {
          $scope._popMsg(msg);
          return false;
        }

        // 비밀번호확인을 입력하세요.
        var msg = messages["storeManage.pwdConf"] + messages["cmm.require.text"];
        if (isNull($scope.store.userPwdConf)) {
          $scope._popMsg(msg);
          return false;
        }

        // 비밀번호는 10자 이상 16자 이하로 입력해주세요.
        var msg = messages["storeManage.userPwdLengthRegexp.msg"];
        if (10 > $scope.store.userPwd.length || $scope.store.userPwd.length > 16) {
          $scope._popMsg(msg);
          return false;
        }

        // 비밀번호와 비밀번호확인이 일치하지 않습니다.
        var msg = messages["storeManage.pwdNotMatch.msg"];
        if ($scope.store.userPwd !== $scope.store.userPwdConf) {
          $scope._popMsg(msg);
          return false;
        }

      }
    }

    // 매장상태구분을 선택해주세요.
    var msg = messages["storeManage.sysStatFg"]+messages["cmm.require.select"];
    if( isNull( $scope.store.sysStatFg ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 데모매장이 아닌 매장은 데모매장으로 수정 불가
    if($("#hdSysStatFg").val() !== null && $("#hdSysStatFg").val() !== "" && $("#hdSysStatFg").val() !== "9"){
        // 매장상태를 데모매장으로 변경할 수 없습니다.
        var msg = messages["storeManage.require.notSelectDemo"];
        if( $scope.store.sysStatFg === "9") {
            $scope._popMsg(msg);
            return false;
        }
    }

    // 용도를 선택해주세요.
    var msg = messages["storeManage.clsFg"]+messages["cmm.require.select"];
    if( isNull( $scope.store.clsFg ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 날씨표시지역을 선택해주세요.
    var msg = messages["storeManage.weatherArea"]+messages["cmm.require.select"];
    if( isNull( $scope.store.areaCd )) {
      $scope._popMsg(msg);
      return false;
    }

    // 설치포스수를 입력해주세요. (매장 등록시에만)
    if($.isEmptyObject(storeScope.getSelectedStore()) ) {
      var msg = messages["storeManage.installPosCnt"]+messages["cmm.require.text"];
      if( isNull( $scope.store.installPosCnt )) {
        $scope._popMsg(msg);
        return false;
      }

      // 포스는 한 대 이상 설치되어야 합니다.
      var msg = messages["storeManage.installPosCnt"]+messages["cmm.require.text"];
      if( $scope.store.installPosCnt < 1) {
        $scope._popMsg(msg);
        return false;
      }

      // 설치포스수는 숫자만 입력할 수 있습니다.
      var msg = messages["storeManage.installPosCnt"]+messages["cmm.require.number"];
      var numChkregexp = /[^0-9]/g;
      if(numChkregexp.test( $scope.store.installPosCnt )) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 사업자번호를 입력해주세요.
    var msg = messages["storeManage.bizNo"]+messages["cmm.require.text"];
    if( isNull( $scope.store.bizNo1 )|| isNull( $scope.store.bizNo2 ) || isNull( $scope.store.bizNo3 ) ) {
      $scope._popMsg(msg);
      return;
    }

    // 사업자번호는 숫자만 입력할 수 있습니다.
    var msg = messages["storeManage.bizNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if(numChkregexp.test( $scope.store.bizNo1 ) || numChkregexp.test( $scope.store.bizNo2 ) || numChkregexp.test( $scope.store.bizNo3 )) {
      $scope._popMsg(msg);
      return false;
    }

    // 등록시나 이전값과 변경하여 변경되었을때, 사업자번호 중복체크를 해주세요. //TODO
    var msg = messages["storeManage.require.duplicate.bizNo"];
    var bizNoStr = $scope.store.bizNo1 + $scope.store.bizNo2 +  $scope.store.bizNo3;
    if($.isEmptyObject(storeScope.getSelectedStore())  || (bizNoStr !== $scope.store.beforeBizNo) ) {
      if(!$scope.isBizChk) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 전화번호를 입력해주세요.
    var msg = messages["storeManage.telNo"]+messages["cmm.require.text"];
    if( isNull( $scope.store.telNo ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 전화번호는 숫자만 입력할 수 있습니다.
    var msg = messages["storeManage.telNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if(numChkregexp.test( $scope.store.telNo )) {
      $scope._popMsg(msg);
      return false;
    }

    // 팩스번호는 숫자만 입력할 수 있습니다.
    var msg = messages["storeManage.faxNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if( (!$.isEmptyObject($scope.store.faxNo)) && numChkregexp.test( $scope.store.faxNo )) {
      $scope._popMsg(msg);
      return false;
    }

    // 핸드폰번호는 숫자만 입력할 수 있습니다.
    var msg = messages["storeManage.mpNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if( (!$.isEmptyObject($scope.store.mpNo)) && numChkregexp.test( $scope.store.mpNo )) {
      $scope._popMsg(msg);
      return false;
    }

    // 주소를 입력해주세요.
    // var msg = messages["storeManage.addr"]+messages["cmm.require.text"];
    // if($("#postNo").val() === "") {
    //   $scope._popMsg(msg);
    //   return false;
    // }

    // [주소검색] 기능으로 주소를 입력하여 주십시오.
    var msg = messages["storeManage.require.addr"]
    if($("#addr").val() === "") {
      $scope._popMsg(msg, function() {
        $("#addr").focus();
      });
      return false;
    }

    // 주소2(주소상세)를 입력하여 주십시오.
    var msg = messages["storeManage.require.addrDtl"]
    if($("#addrDtl").val() === "") {
      $scope._popMsg(msg, function() {
        $("#addrDtl").focus();
      });
      return false;
    }

    // 관리업체를 선택해주세요.
    var msg = messages["storeManage.manageVan"]+messages["cmm.require.select"];
    if( isNull( $scope.store.vanCd) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 대리점를 선택해주세요.
    var msg = messages["storeManage.agency"]+messages["cmm.require.select"];
    if( isNull( $scope.store.agencyCd) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 터미널번호를 입력하세요.
    var msg = messages["storeManage.vendorTermnlNo"] + messages["cmm.require.text"];
    if (isNull($scope.store.vendorTermnlNo)) {
      $scope._popMsg(msg);
      return false;
    } else {
      if ($scope.store.vendorCd === "001") { // KCP
          if ($scope.store.vendorTermnlNo.length != 10) {
              $scope._popMsg(messages["storeManage.vendorTermnlNo"] + "는 10자리로 입력하세요.");
              return false;
          }
      } else {
          if (nvl($scope.store.vendorTermnlNo.getByteLengthForOracle(), '') > 20) {
              $scope._popMsg(messages["storeManage.vendorTermnlNo"] + messages["storeManage.require.exact.data"]);
              return false;
          }
      }
    }

    // 시리얼번호를 입력하세요.
    var msg = messages["storeManage.vendorSerNo"] + messages["cmm.require.text"];
    if (isNull($scope.store.vendorSerNo)) {
      $scope._popMsg(msg);
      return false;
    } else {
      if (nvl($scope.store.vendorSerNo.getByteLengthForOracle(), '') > 20) {
          $scope._popMsg(messages["storeManage.vendorSerNo"] + messages["storeManage.require.exact.data"]);
          return false;
      }
    }

    // BBQ는 VAN - KCP 만 저장 가능
    // 개발 DS011
    // 운영 DS024 H0360
    if ($scope.store.hqOfficeCd == "DS011" || $scope.store.hqOfficeCd == "DS024" || $scope.store.hqOfficeCd == "H0360") {
      if ($scope.store.vendorCd == "001") {
      } else {
          // BBQ 매장은 VAN - KCP 선택하여 주십시오.
          $scope._popMsg(messages["storeManage.bbqSave.msg"]);
          return false;
      }
    }

    // KOCES 총판은 벤더코드 KOCES만 저장가능
    if($("#lblVanFixFg").text() == "Y") {
      if ($scope.store.vendorCd !== '008') { // KOCES
          $scope._popMsg(messages["storeManage.terminalManage"] + "는 [008] KOCES" + messages["cmm.require.select"]);
          return false;
      }
    }

    // 매장환경복사 체크값이 있을때
    if($("input:checkbox[name='copyChk']:checked").length > 0) {

      // 매장환경 복사할 본사를 선택해주세요.
      var msg = messages["storeManage.copy.storeEnv.hqOfficeCd"] + messages["cmm.require.select"];
      if( isNull( $scope.envHqOfficeCdVal) ) {
        $scope._popMsg(msg);
        return false;
      }
      // 매장환경 복사할 매장을 선택해주세요.
      var msg = messages["storeManage.copy.storeEnv.storeCd"] + messages["cmm.require.select"];
      if( isNull( $scope.envStoreCdVal) ) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 대리점를 선택해주세요.
    var msg = messages["storeManage.siteCd"] + "는 " +messages["cmm.max50Chk"];
    if(nvl($("#siteCd").val(), '').getByteLengthForOracle() > 50) {
      $scope._popMsg(msg);
      return false;
    }

    // KOCES 총판은 관리벤사 KOCES만 저장가능
    // if($("#lblVanFixFg").text() == "N") {
    //   if (arrAgencyCol.indexOf(orgnCd) !== -1) {
    //     // 관리벤사는 [008] KOCES를 선택해주십시오.
    //     var msg = messages["storeManage.manageVan"] + "는 [008] KOCES" + messages["cmm.require.select"];
    //     if ($scope.store.vanCd !== null && $scope.store.vanCd !== '008') {
    //       $scope._popMsg(msg);
    //       return false;
    //     }
    //   }
    // }

    if($("#lblVanFixFg").text() == "Y") {
      if ($scope.store.vanCd !== null && $scope.store.vanCd !== '008') {
        $scope._popMsg(messages["storeManage.manageVan"] + "는 [008] KOCES" + messages["terminalManage.require.select"]);
        return false;
      }
    }

    return true;
  };


  /*********************************************************
   * 저장
   * *******************************************************/
  $scope.save = function() {

      if (!$scope.valueCheck()) return false;
      
      var storeScope = agrid.getScope('storeManageCtrl');
          
      // 수정시에만 체크
      if (!$.isEmptyObject(storeScope.getSelectedStore())) {
          if (b_vendorCd !== "" && b_vendorCd !== null) {
              // 터미널관리 선택값이 변경한 경우 기존값과 중복되는지 확인
              if (b_vendorCd !== $scope.store.vendorCd) {
                  var params = $scope.store;
                  $scope._postJSONQuery.withOutPopUp("/store/manage/storeManage/storeManage/chkVendorCd.sb", params, function (response) {
                      if (response.data.data > 0) {
                          $scope._popMsg("터미널관리" + getVendorCdNm($scope.store.vendorCd.toString()) + "가 중복됩니다. 확인하여 주십시오(상세정보: [기초관리] - [매장정보관리] - [매장터미널관리])");
                          return false;
                      }else{
                          // 사업자번호 중복체크
                          $scope.bizNoChk();
                      }
                  });
              } else {
                  // 사업자번호 중복체크
                  $scope.bizNoChk();
              }
          } else if (b_vendorCd == "" || b_vendorCd == null) {
              // 사업자번호 중복체크
              $scope.bizNoChk();
          } else {
              $scope._popMsg(messages["cmm.registFail"]);
              return false;
          }
      }else{
          // 사업자번호 중복체크
          $scope.bizNoChk();
      }
  };

  // 사업자번호 중복체크
  $scope.bizNoChk = function () {

    var params         = $scope.store;
    params.sysOpenDate = wijmo.Globalize.format(sysOpenDate.value, 'yyyyMMdd');
    // 폐점일때만 폐점 날짜 저장
    if($scope.store.sysStatFg === "2"){
      params.sysClosureDate = wijmo.Globalize.format(sysClosureDate.value, 'yyyyMMdd');
    }
    params.directManageYn = $("input[name='directManage']:checked").val();
    params.postNo = $("#postNo").val();
    params.addr = $("#addr").val();
    params.addrdtl = $("#addrDtl").val();
    params.digit8Store = $("#hdDigit8Store").val();
    params.latitude = $("#latitude").val();
    params.longitude = $("#longitude").val();
    params.vendorTermnlNo = $("#vendorTermnlNo").val();
    params.vendorSerNo = $("#vendorSerNo").val();

     // ERP 연동 매장 등록인 경우, NXPOS_STORE_CD 값을 Update 하기 위함.
    if(orgnFg === "HQ") {
        if($("#hdBbqStoreCd").val() !== "") {
            params.bbqStoreCd = $("#hdBbqStoreCd").val();
        }
     }

    console.log('params',params);


    // 사업자번호 중복체크
    $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/bizNoCheckCount.sb", params, function(response){
      var bizNoCheck = response.data.data.result;
      $scope.bizNoCheck = bizNoCheck;

      if($scope.bizNoCheck.bizNo > 0) {
        // 해당 사업자번호[123-32-12312]가 이미 등록되어 있습니다. 계속 진행하시겠습니까?
        $scope._popConfirm(messages["storeManage.chk.bizNo1"] +"["+ $scope.store.bizNo1 +"-"+ $scope.store.bizNo2 +"-"+ $scope.store.bizNo3 +"]"+ messages["storeManage.chk.bizNo2"], function() {
            // 매장정보 저장
            $scope.storeSave(params);
        });
      } else if($scope.bizNoCheck.bizNo === 0) {
          // 매장정보 저장
          $scope.storeSave(params);
      }
    });
  };

  // 매장정보 저장
  $scope.storeSave = function(data){
    // 저장하시겠습니까?
    var msg = messages["cmm.choo.save"];
    if($("#hdMomsEnvstVal").val() === "1") {
      msg = msg + "<br/>처리량이 많아 오래 걸릴 수 있습니다.<br/>완료될때까지 창을 닫지말고 기다려 주십시오."
    }
    $scope._popConfirm(msg, function() {

      var params         = data;

      // [1250 맘스터치]
      params.momsEnvstVal = nvl($("#hdMomsEnvstVal").val(), "0");
      if($("#hdMomsEnvstVal").val() === "0" && $("#envst1114").val() === "0") {
        params.hqBrandCd = bHdHqBrandCd;
      }
      if(params.hqBrandCd === "" || params.hqBrandCd === null) {
        params.hqBrandCd = "0000000";
      }

      var storeScope = agrid.getScope('storeManageCtrl');

      // 매장 신규 등록시
      if($.isEmptyObject(storeScope.getSelectedStore()) ) {

        var copyChkVal = "";

        $("input[name=copyChk]:checked").each(function() {
          copyChkVal += ($(this).val() + "|");
        });

        params.copyChkVal = copyChkVal;

        $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/saveStoreInfo.sb", params, function (response) {

          var result = response.data.data;

          if(result === ""){
            $scope._popMsg(messages["cmm.registFail"]);
          }else{
            $scope._popMsg(messages["cmm.saveSucc"]);
            $scope.storeInfoLayer.hide();
          }

          $scope._broadcast('storeManageCtrl');
        });
      }
      // 수정
      else {

        // 터미널관리(밴더코드) 이전 선택값(터미널정보 update 시 필요)
        params.bVendorCd = b_vendorCd;

        $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/updateStoreInfo.sb", params, function () {
          $scope._popMsg(messages["cmm.saveSucc"]);
          $scope.storeInfoLayer.hide();
          $scope._broadcast('storeManageCtrl');
        });
      }

    });
  };


  /*********************************************************
   * 본사 조회 팝업
   * *******************************************************/
  $scope.searchHq = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if(!$.isEmptyObject(storeScope.getSelectedStore()) ) {  // 신규등록시에만 본사 조회 팝업 호출
      return false;
    }

    // 관리자등록시에만 본사 조회 팝업 호출(본사에서 등록시 본사정보 자동셋팅)
    if(orgnFg === "HQ"){
      return false;
    }

    var popup = $scope.hqLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var hqScope = agrid.getScope('searchHqCtrl');
      hqScope.$apply(function(){
        hqScope._gridDataInit();

        // 매장코드8이상사용 확인 초기화
        $("#hdDigit8Store").val("");

        // 웹사용자아이디 입력 영역 숨김
        $("#userId").val("");
        $("#userIdChkFg").val("");
        $("#userPwd").val("");
        $("#userPwdConf").val("");
        $("#trUser").css("display", "none");

        $scope.store.userId = "";
        $scope.store.userIdChkFg = "";
        $scope.store.userPwd = "";
        $scope.store.userPwdConf = "";

        // BBQ 매장코드 초기화(본사 선택시에는 사용안함)
        $("#hdBbqStoreCd").val("");

          if( !$.isEmptyObject(hqScope.getHq())  ) {
          // 본사정보 셋팅
          $scope.store.hqOfficeCd = hqScope.getHq().hqOfficeCd;
          $scope.store.hqOfficeNm = hqScope.getHq().hqOfficeNm;
          $scope.store.storeCdInputType = hqScope.getHq().envst0027; // 매장코드 채번방식(자동/수동)
          $scope.store.storeCdChkFg = "";
          $scope.store.envst0043 = hqScope.getHq().envst0043; // 본사신규상품매장생성
          $("#hdDigit8Store").val( hqScope.getHq().digit8Store); // 매장코드8이상사용

          $scope.envHqOfficeCdCombo.selectedValue = $scope.store.hqOfficeCd;

          // 아트박스(운영H0345,H0094)(개발DS012)
          if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
            $("#addr").attr("readonly", false);
          }else{
            $("#addr").attr("readonly", true);
          }

          // 매장코드 채번방식
          if (hqScope.getHq().envst0027 === '1') { //수동
            $scope.store.storeCd = '';
            $("#storeCd").attr('placeholder','대문자, 숫자 입력가능');
            $("#storeCd").removeAttr("readonly");
            $("#storeCd").css("width", "60%");
            $("#btnChkStoreCd").css("display", "");

            if($("#hdDigit8Store").val() === "" || $("#hdDigit8Store").val() === null || $("#hdDigit8Store").val() === undefined){
              // 웹 사용자 자동 등록
              $("#trUser").css("display", "none");
            }else{
              // 웹 사용자 직접 등록
              $("#trUser").css("display", "");
            }

          } else {
            if (hqScope.getHq().envst0027 === '0') { //자동
              $scope.store.storeCd = '자동채번';
              $("#storeCd").attr('placeholder','자동채번');
            } else {
              $scope.store.storeCd = '';
              $("#storeCd").attr('placeholder','');
            }
            $("#storeCd").attr("readonly", true);
            $("#storeCd").css("width", "100%");
            $("#btnChkStoreCd").css("display", "none");

            // 웹 사용자 자동 등록
            $("#trUser").css("display", "none");
          }

          if (hqScope.getHq().sysStatFg === '9') {
            $scope.sysStatFgCombo.selectedValue = '9';
            $scope.sysStatFgCombo.isReadOnly = true;
          } else {
            $scope.sysStatFgCombo.selectedValue = '1';
            $scope.sysStatFgCombo.isReadOnly = false;
          }

          // 매장환경복사 체크 disabled
          $scope.copyStoreSettingChk();

          // 본사-그룹 콤보박스 set
          $scope.setBranchDropdownList();

          // [1250 맘스터치]
          $("#hdMomsEnvstVal").val(hqScope.getHq().envst1250);
          $("#envst1114").val(hqScope.getHq().envst1114);
          console.log(hqScope.getHq().envst1114);
          if($("#hdMomsEnvstVal").val() === "1") {
            // [1250 맘스터치] 코드별 본사 공통코드 콤보박스 조회
            $scope.hqNmcodeComboList("151");
            $scope.hqNmcodeComboList("152");
            $scope.hqNmcodeComboList("153");
            $scope.hqNmcodeComboList("154");
            $scope.hqNmcodeComboList("155");
            $scope.hqNmcodeComboList("156");
            $scope.hqNmcodeComboList("167"); // momsStoreFg01
            $scope.hqNmcodeComboList("169"); // momsStoreFg02
            $scope.hqNmcodeComboList("170"); // momsStoreFg03
            $scope.hqNmcodeComboList("171"); // momsStoreFg04
            $scope.hqNmcodeComboList("172"); // momsStoreFg05
            // [1250 맘스터치] 브랜드 콤보박스 조회
            $scope.hqBrandCdComboList();

            $("#trMomsEnvst").css("display", "");
            $("#divMomsEnvst").css("display", "");
            $scope.srchMomsTeamCombo.selectedIndex = 0;
            $scope.srchMomsAcShopCombo.selectedIndex = 0;
            $scope.srchMomsAreaFgCombo.selectedIndex = 0;
            $scope.srchMomsCommercialCombo.selectedIndex = 0;
            $scope.srchMomsShopTypeCombo.selectedIndex = 0;
            $scope.srchMomsStoreManageTypeCombo.selectedIndex = 0;
            $scope.srchHqBrandCdCombo.selectedIndex = 0;
            $scope.srchMomsStoreFg01Combo.selectedIndex = 0;
            $scope.srchMomsStoreFg02Combo.selectedIndex = 0;
            $scope.srchMomsStoreFg03Combo.selectedIndex = 0;
            $scope.srchMomsStoreFg04Combo.selectedIndex = 0;
            $scope.srchMomsStoreFg05Combo.selectedIndex = 0;
          } else if($("#envst1114").val() === "1") {
            // [1250 맘스터치] 브랜드 콤보박스 조회
            $scope.hqBrandCdComboList();
            $("#trMomsEnvst").css("display", "");
            $scope.srchHqBrandCdCombo.selectedIndex = 0;
          }  else {
            // 콤보박스 데이터
            $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
            $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
            $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
            $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
            $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
            $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
            $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
            bHdHqBrandCd = "";
            $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
            $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
            $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
            $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
            $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5

            $("#trMomsEnvst").css("display", "none");
            $("#divMomsEnvst").css("display", "none");
          }

            $("#envst1262").val(hqScope.getHq().envst1262);

        }
      });
      
      // 본사 정보 초기화(이전데이터 남아있는 현상 발생)
      hqScope.setHq("");
    });
    event.preventDefault();
  };

  /** 매장환경복사 체크 disabled **/
  $scope.copyStoreSettingChk = function(){
    // 신규등록할 매장이 단독매장일 경우
    if ($scope.store.hqOfficeCd === "00000") {
      if ($("#productChk").is(":checked") === true) {
        $("#productChk").prop("checked", false);
      }
      if ($("#salePriceChk").is(":checked") === true) {
        $("#salePriceChk").prop("checked", false);
      }
      if ($("#supplyPriceChk").is(":checked") === true) {
        $("#supplyPriceChk").prop("checked", false);
      }
      if ($("#touchKeyChk").is(":checked") === true) {
        $("#touchKeyChk").prop("checked", false);
      }
      if ($("#prtFormChk").is(":checked") === true) {
        $("#prtFormChk").prop("checked", false);
      }
      if ($("#promotionChk").is(":checked") === true) {
        $("#promotionChk").prop("checked", false);
      }
      if ($("#dlvrProdChk").is(":checked") === true) {
        $("#dlvrProdChk").prop("checked", false);
      }
      $("#productChk").attr("disabled", true);
      $("#salePriceChk").attr("disabled", true);
      $("#supplyPriceChk").attr("disabled", true);
      $("#touchKeyChk").attr("disabled", true);
      $("#promotionChk").attr("disabled", true);
      $("#dlvrProdChk").attr("disabled", true);

    // 신규등록할 매장이 프랜매장일 경우
    } else if($scope.store.hqOfficeCd !== "00000") {
      // 매장환경복사의 매장을 선택 안한후 신규등록할 매장을 수정하면
      if($scope.store.copyStoreCd == null || $scope.store.copyStoreCd == "") {
        $("#productChk").attr("disabled", false);
        $("#salePriceChk").attr("disabled", false);
        $("#supplyPriceChk").attr("disabled", false);
        $("#touchKeyChk").attr("disabled", false);
        $("#promotionChk").attr("disabled", false);
        $("#dlvrProdChk").attr("disabled", false);

      // 매장환경복사의 매장을 선택 후 신규등록할 매장을 수정하면
      } else {
        // 매장환경복사 매장이 단독매장일 경우
        if ($scope.store.copyHqOfficeCd === "00000") {
            if ($("#productChk").is(":checked") === true) {
                $("#productChk").prop("checked", false);
            }
            if ($("#salePriceChk").is(":checked") === true) {
                $("#salePriceChk").prop("checked", false);
            }
            if ($("#supplyPriceChk").is(":checked") === true) {
                $("#supplyPriceChk").prop("checked", false);
            }
            if ($("#touchKeyChk").is(":checked") === true) {
                $("#touchKeyChk").prop("checked", false);
            }
            if ($("#prtFormChk").is(":checked") === true) {
                $("#prtFormChk").prop("checked", false);
            }
            if ($("#promotionChk").is(":checked") === true) {
                $("#promotionChk").prop("checked", false);
            }
            if ($("#dlvrProdChk").is(":checked") === true) {
                $("#dlvrProdChk").prop("checked", false);
            }
            $("#productChk").attr("disabled", true);
            $("#salePriceChk").attr("disabled", true);
            $("#supplyPriceChk").attr("disabled", true);
            $("#touchKeyChk").attr("disabled", true);
            $("#promotionChk").attr("disabled", true);
            $("#dlvrProdChk").attr("disabled", true);

        // 매장환경복사 매장이 프랜매장일 경우
        } else if($scope.store.copyHqOfficeCd !== "00000") {
          // 매장환경복사 본사와 같은 본사면
          if($scope.store.hqOfficeCd === $scope.store.copyHqOfficeCd) {
            $("#salePriceChk").attr("disabled", false);
            $("#supplyPriceChk").attr("disabled", false);
            $("#touchKeyChk").attr("disabled", false);
            $("#promotionChk").attr("disabled", false);
            $("#dlvrProdChk").attr("disabled", false);

          // 매장환경복사 본사와 다른 본사면
          } else if($scope.store.hqOfficeCd !== $scope.store.copyHqOfficeCd) {
            if ($("#productChk").is(":checked") === true) {
              $("#productChk").prop("checked", false);
            }
            if ($("#salePriceChk").is(":checked") === true) {
              $("#salePriceChk").prop("checked", false);
            }
            if ($("#supplyPriceChk").is(":checked") === true) {
              $("#supplyPriceChk").prop("checked", false);
            }
            if ($("#touchKeyChk").is(":checked") === true) {
              $("#touchKeyChk").prop("checked", false);
            }
            if ($("#prtFormChk").is(":checked") === true) {
              $("#prtFormChk").prop("checked", false);
            }
            if ($("#promotionChk").is(":checked") === true) {
              $("#promotionChk").prop("checked", false);
            }
            if ($("#dlvrProdChk").is(":checked") === true) {
              $("#dlvrProdChk").prop("checked", false);
            }
            $("#productChk").attr("disabled", true);
            $("#salePriceChk").attr("disabled", true);
            $("#supplyPriceChk").attr("disabled", true);
            $("#touchKeyChk").attr("disabled", true);
            $("#promotionChk").attr("disabled", true);
            $("#dlvrProdChk").attr("disabled", true);
          }
        }
      }
    }
  };
  /** //매장환경복사 체크 disabled **/

  /*********************************************************
   * 관리업체 조회
   * *******************************************************/
  $scope.searchManageVan = function(){
    var popup = $scope.vanLayer;

    if($("#lblVanFixFg").text() == "Y") {
      $scope._popMsg(messages["storeManage.manageVan"] + "는 [008] KOCES을(를) 변경할 수 없습니다.");
      return false;
    }

    // 팝업 닫을때
    popup.show(true, function (s) {
      var vanScope = agrid.getScope('searchVanCtrl');
      vanScope.$apply(function(){
        vanScope._gridDataInit();
        if( !$.isEmptyObject(vanScope.getVan())  ){
          $scope.store.vanCd = vanScope.getVan().vanCd;
          $scope.store.vanNm = vanScope.getVan().vanNm;
        }
      });
    });
  };

  /*********************************************************
   * 대리점 조회
   * *******************************************************/
  $scope.searchAgency = function(){
    if(orgnFg === "MASTER" || pAgencyCd === "00000" || orgnFg === "HQ") {
      var popup = $scope.agencyLayer;

      // 팝업 닫을때
      popup.show(true, function (s) {
        var agencyScope = agrid.getScope('searchAgencyCtrl');
        agencyScope.$apply(function () {
          agencyScope._gridDataInit();

          if (!$.isEmptyObject(agencyScope.getAgency())) {
            $scope.store.agencyCd = agencyScope.getAgency().agencyCd;
            $scope.store.agencyNm = agencyScope.getAgency().agencyNm;
          }
        });
      });
    }
  };

  /*********************************************************
   * 사업자번호 중복체크
   * *******************************************************/
  $scope.chkBizNo = function(){

    if( isNull ($scope.store.bizNo1) || isNull ($scope.store.bizNo2) || isNull ($scope.store.bizNo3)) {
      $scope._popMsg(messages["storeManage.bizNo"]+messages["cmm.require.text"]);
      return false;
    }

    var popup = $scope.checkBizNoLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        var bizNoScope = agrid.getScope('checkBizNoCtrl');
        bizNoScope.setBizNo( $scope.store.bizNo1 + $scope.store.bizNo2 +  $scope.store.bizNo3);
        bizNoScope._broadcast('checkBizNoCtrl');
      }, 50)
    });


    // 팝업 닫을때
    popup.show(true, function (s) {
      $scope.isBizChk = true;
    });
    event.preventDefault();
  };

  /*********************************************************
   * 매장환경 탭 클릭
   * *******************************************************/
  $scope.changeEnvTab = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if($.isEmptyObject(storeScope.getSelectedStore()) ) {
      $scope._popMsg(messages["storeManage.require.regist.store1"]);
      return false;
    }

    $scope.storeInfoLayer.hide();

    var envPopup = $scope.storeEnvLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    envPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        // 환경 검색값 초기화
        $("#srchConfig").val('');
        $scope._broadcast('storeEnvCtrl');
      }, 50)
    });

    // 팝업 닫을때
    envPopup.show(true, function (s) {
    });
  };

  /*********************************************************
   * 메뉴권한 탭 클릭
   * *******************************************************/
  $scope.changeAuthTab = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if($.isEmptyObject(storeScope.getSelectedStore()) ) {
      $scope._popMsg(messages["storeManage.require.regist.store1"]);
      return false;
    }

    $scope.storeInfoLayer.hide();

    var authPopup = $scope.storeAuthLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    authPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('storeAuthCtrl');
      }, 50)
    });

    // 팝업 닫을때
    authPopup.show(true, function (s) {
    });
  };

  /*********************************************************
   * 설치포스수 추가
   * *******************************************************/
  $scope.addPos = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if(isEmptyObject(storeScope.getSelectedStore()) ) {  // 수정모드시에만 포스추가 팝업 호출
      return false;
    }

    // 관리자, 총판, 본사 권한만 수정 가능
    if(orgnFg !== "MASTER" && orgnFg !== "AGENCY" && orgnFg !== "HQ"){
      return false;
    }

    var popup = $scope.storePosAddLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {

        var params      = {};
        params.storeCd = $scope.store.storeCd;
        params.storeNm = $scope.store.storeNm;
        params.posCnt = $scope.store.installPosCnt;

        $scope._broadcast('storePosAddCtrl', params);
      }, 50)
    });

    // 팝업 닫을때
    popup.show(true, function (s) {
    });

    event.preventDefault();

  };

  /*********************************************************
   * 매장코드 중복체크
   * *******************************************************/
  $scope.chkStoreCd = function(){

    if(isNull($scope.store.storeCd)) {
      // 매장코드을(를) 입력하세요.
      $scope._popMsg(messages["storeManage.storeCd"]+messages["cmm.require.text"]);
      return false;
    }

    // 매장코드 길이체크
    if($("#hdDigit8Store").val() === '') {
      if($("#storeCd").val().length !== 7) {
        // 매장코드는 7자리로 입력하세요.
        $scope._popMsg(messages["storeManage.require.storeCdLength7"]);
        return false;
      }
    }else {
      if($("#storeCd").val().length !== 6 && (8 > $("#storeCd").val().length || $("#storeCd").val().length > 12)) {
        // 매장코드는 6자리 또는 8~12자리로 입력하세요.
        $scope._popMsg(messages["storeManage.require.storeCdLength6Or8To12"]);
        return false;
      }
    }

    var params    = {};
    params.storeCd = $scope.store.storeCd;

      $scope._postJSONQuery.withPopUp( "/store/manage/storeManage/storeManage/getStoreCdCnt.sb", params, function(response){

          var result = response.data.data;

          if(result === 0){ // 사용가능
              $scope._popMsg(messages["storeManage.notStoreCdDuplicate.msg"]);
              $scope.store.storeCdChkFg = $scope.store.storeCd;
              
              // 매장코드 수동채번이면서, 매장코드8자리이상 사용매장인 경우만
              if ($scope.store.storeCdInputType === "1") {
                  if($("#hdDigit8Store").val() !== "" && $("#hdDigit8Store").val() !== null && $("#hdDigit8Store").val() !== undefined){
                      // 웹사용자아이디에 바인딩
                      $scope.setUserId();
                  }
              }

          }else{ // 중복
              $scope._popMsg(messages["storeManage.storeCdDuplicate.msg"]);
              $scope.store.storeCdChkFg ="";
          }
      });

  };

  // 본사에서 등록시 본사정보 자동셋팅
  $scope.setHqInfo = function () {
    $scope.store.hqOfficeCd = hqOfficeCd;
    $scope.store.hqOfficeNm = hqOfficeNm;
    $scope.store.storeCdInputType = hqEnvst0027; // 매장코드 채번방식 [0:자동(기본) / 1:수동]
    $scope.store.storeCdChkFg = "";
    $scope.store.envst0043 = hqEnvst0043; // 본사신규상품매장생성 [0:자동(기본) / 1:수동]
    $("#hdDigit8Store").val(digit8Store); // 매장코드8이상사용매장

    $("#lblVanFixFg").val("");

    $scope.envHqOfficeCdCombo.selectedValue = $scope.store.hqOfficeCd;

    // input에 값이 사라지는 현상방지
    $("#hqOfficeCd").val(hqOfficeCd);
    $("#hqOfficeNm").val(hqOfficeNm);

    // 매장코드 채번방식
    if (hqEnvst0027 === '1') { //수동
      $scope.store.storeCd = '';
      $("#storeCd").attr('placeholder','대문자, 숫자 입력가능');
      $("#storeCd").removeAttr("readonly");
      $("#storeCd").css("width", "60%");
      $("#btnChkStoreCd").css("display", "");
      // 아트박스(운영H0345,H0094)(개발DS012)
      if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
        $("#storeCd").attr("readonly", true);
      }

      // 매장코드8이상 사용매장인 경우만 웹 사용자 직접등록
      if($("#hdDigit8Store").val() === "" || $("#hdDigit8Store").val() === null || $("#hdDigit8Store").val() === undefined){
          // 웹 사용자 자동 등록
          $("#trUser").css("display", "none");
      }else{
          // 웹 사용자 직접 등록
          $("#trUser").css("display", "");
      }
    } else {
      if (hqEnvst0027 === '0') { //자동
        $scope.store.storeCd = '자동채번';
        $("#storeCd").val('자동채번');
        $("#storeCd").attr('placeholder','자동채번');
      } else {
        $scope.store.storeCd = '';
        $("#storeCd").attr('placeholder','');
      }
      $("#storeCd").attr("readonly", true);
      $("#storeCd").css("width", "100%");
      $("#btnChkStoreCd").css("display", "none");

      // 웹 사용자 자동 등록
      $("#trUser").css("display", "none");
    }

    if (hqSysStatFg === '9') {
      $scope.sysStatFgCombo.selectedValue = '9';
      $scope.sysStatFgCombo.isReadOnly = true;
    } else {
      $scope.sysStatFgCombo.selectedValue = '1';
      $scope.sysStatFgCombo.isReadOnly = false;
    }

    // 매장환경복사 체크 disabled
    $scope.copyStoreSettingChk();

    // 본사-그룹 콤보박스 set
    $scope.setBranchDropdownList();

    // ERP 연동 매장 셋팅 팝업 관련 visible 처리
    $("#lblErpStoreSet").text(hqOfficeNm + " 매장");
    
    if(erpLinkHq !== ""){ // ERP 연동 매장등록을 사용하는 본사인 경우
      $("#btnErpStoreSet").css("display", "");
    }else{
      $("#btnErpStoreSet").css("display", "none");
    }

    // 아트박스(운영H0345,H0094)(개발DS012)
    if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
      $("#addr").attr("readonly", false);
    }else{
      $("#addr").attr("readonly", true);
    }

    // [1250 맘스터치]
    $("#hdMomsEnvstVal").val(momsEnvstVal);
    $("#envst1114").val(brandUseFg);
    if($("#hdMomsEnvstVal").val() === "1") {
      // [1250 맘스터치] 코드별 본사 공통코드 콤보박스 조회
      $scope.hqNmcodeComboList("151");
      $scope.hqNmcodeComboList("152");
      $scope.hqNmcodeComboList("153");
      $scope.hqNmcodeComboList("154");
      $scope.hqNmcodeComboList("155");
      $scope.hqNmcodeComboList("156");
      $scope.hqNmcodeComboList("167"); // momsStoreFg01
      $scope.hqNmcodeComboList("169"); // momsStoreFg02
      $scope.hqNmcodeComboList("170"); // momsStoreFg03
      $scope.hqNmcodeComboList("171"); // momsStoreFg04
      $scope.hqNmcodeComboList("172"); // momsStoreFg05
      // [1250 맘스터치] 브랜드 콤보박스 조회
      $scope.hqBrandCdComboList();

      $("#trMomsEnvst").css("display", "");
      $("#divMomsEnvst").css("display", "");
      $scope.srchMomsTeamCombo.selectedIndex = 0;
      $scope.srchMomsAcShopCombo.selectedIndex = 0;
      $scope.srchMomsAreaFgCombo.selectedIndex = 0;
      $scope.srchMomsCommercialCombo.selectedIndex = 0;
      $scope.srchMomsShopTypeCombo.selectedIndex = 0;
      $scope.srchMomsStoreManageTypeCombo.selectedIndex = 0;
      $scope.srchHqBrandCdCombo.selectedIndex = 0;
      $scope.srchMomsStoreFg01Combo.selectedIndex = 0;
      $scope.srchMomsStoreFg02Combo.selectedIndex = 0;
      $scope.srchMomsStoreFg03Combo.selectedIndex = 0;
      $scope.srchMomsStoreFg04Combo.selectedIndex = 0;
      $scope.srchMomsStoreFg05Combo.selectedIndex = 0;
    } else if($("#envst1114").val() === "1") {
      // [1250 맘스터치] 브랜드 콤보박스 조회
      $scope.hqBrandCdComboList();
      $("#trMomsEnvst").css("display", "");
      $scope.srchHqBrandCdCombo.selectedIndex = 0;
    } else {
      // 콤보박스 데이터
      $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
      $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
      $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
      $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
      $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
      $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
      $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
      bHdHqBrandCd = "";
      $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
      $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
      $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
      $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
      $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5
      $("#trMomsEnvst").css("display", "none");
      $("#divMomsEnvst").css("display", "none");
    }
  };
  
  // 웹 사용자 아이디 중복체크
  $scope.chkUserId = function () {

     // 입력체크
     if(isNull($("#userId").val())){
       // 웹사용자아이디(을)를 입력하세요.
       $scope._popMsg(messages["storeManage.userId"]+messages["cmm.require.text"]);
       return false;
     }

     // 길이 및 형식 체크
     if($("#userId").val().length !== 6 && (8 > $("#userId").val().length || $("#userId").val().length > 12)){
       // 웹사용자아이디는 6자리 또는 8~12자리로 입력하세요.
       $scope._popMsg(messages["storeManage.userIdLengthRegexp2.msg"]);
       return false;
     }

     // 중복체크
     var params    = {};
     params.userId = $("#userId").val();
     params.erpLinkHq = erpLinkHq; // erp 연동 매장 여부 파악

    if(orgnFg === "MASTER" || orgnFg === "AGENCY"){ // 관리자화면에서 매장등록시, erpLinkHq 값이 없어서 중복체크 때 조회해봄.
      params.hqOfficeCd = $scope.store.hqOfficeCd;
    }

     $scope._postJSONQuery.withPopUp( "/store/manage/storeManage/storeManage/chkUserId.sb", params, function(response){

       var result = response.data.data;

       console.log('chk duplicate result', result);

       if(result == "SUCCESS"){
         $("#userIdChkFg").val($("#userId").val());
         $scope.store.userIdChkFg = $scope.store.userId;
         $scope._popMsg(messages["storeManage.notDuplicate.msg"]);
       } else if(result === "USER_ID_REGEXP"){
         $scope._popMsg(messages["storeManage.userIdRegexp.msg"]);
       } else if(result === "USER_ID_LENGHTH_REGEXP"){
         $scope._popMsg(messages["storeManage.userIdLengthRegexp.msg"]);
       } else if(result === "USER_ID_CANNOT_USE_HANGEUL"){
         $scope._popMsg(messages["storeManage.userIdNotUseHangeul.msg"]);
       } else if(result === "USER_ID_MUST_CONTAIN_ENG_CAHR"){
         $scope._popMsg(messages["storeManage.userIdContainEngChar.msg"]);
       } else if(result === "USER_ID_ONLY_ENG_NUM_CHAR"){
         $scope._popMsg(messages["storeManage.userIdOnlyEnvNumChar.msg"]);
       } else if(result === "USER_ID_DUPLICATE"){
         $("#userIdChkFg").val("");
         $scope.store.userIdChkFg = "";
         $scope._popMsg(messages["storeManage.userId.duplicate.msg"]);
       } else if (result === "USER_ID_LENGHTH_REGEXP_6OR_8TO12"){
         $scope._popMsg(messages["storeManage.userIdLengthRegexp2.msg"]);
       } else {
         $scope._popMsg(messages["storeManage.userId.notDuplicate.msg"]);
       }

     });
  };

  // 매장코드를 웹 사용자 아이디로 자동 바인딩
  $scope.setUserId = function () {
    // 맘스터치 (운영 DS034,DS038,DS040,H0393) (개발 DS021)
    if($scope.store.hqOfficeCd === "DS034" || $scope.store.hqOfficeCd === "DS038" || $scope.store.hqOfficeCd === "DS040" || $scope.store.hqOfficeCd === "H0393" || $scope.store.hqOfficeCd === "DS021"){
      $("#userId").val('moms' + $("#storeCd").val().toString().toLowerCase());
      $scope.store.userId = 'moms' + $("#storeCd").val().toString().toLowerCase();
    } else {
      $("#userId").val($("#storeCd").val().toString().toLowerCase());
      $scope.store.userId = $("#storeCd").val().toString().toLowerCase();
    }
    
    // 자동바인딩 후에 다시 웹 사용자 아이디 중복체크를 하도록 유도하기 위해 초기화
    $("#userIdChkFg").val("");
    $scope.store.userIdChkFg = "";
  };

  // ERP 연동 매장 셋팅 팝업
  $scope.erpStoreSet = function () {

    var storeScope = agrid.getScope('storeManageCtrl');

    if(!$.isEmptyObject(storeScope.getSelectedStore()) ) {  // 신규등록시에만 ERP 연동 매장 셋팅 팝업 호출
      return false;
    }

    var popup = $scope.erpStoreSetLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var erpStoreScope = agrid.getScope('erpStoreSetCtrl');
      erpStoreScope.$apply(function(){
        erpStoreScope._gridDataInit();

        if(!$.isEmptyObject(erpStoreScope.getErpStore())) {

          // 기존 값 초기화
          $("#storeCd").val("");
          $("#storeCdChkFg").val("");
          $("#storeNm").val("");
          $("#bizStoreNm").val("");
          $("#ownerNm").val("");
          $("#userId").val("");
          $("#userIdChkFg").val("");
          $("#userPwd").val("");
          $("#userPwdConf").val("");
          $scope.areaCdCombo.selectedIndex = 1; // 기본값: 서울특별시
          $("input:radio[id='directManageY']").prop("checked", false); // 직영구분 - 직영
          $("input:radio[id='directManageN']").prop("checked", false); // 직영구분 - 가맹
          $("#installPosCnt").val("");
          $("#bizNo1").val("");
          $("#bizNo2").val("");
          $("#bizNo3").val("");
          $("#telNo").val("");
          $("#mpNo").val("");
          $("#postNo").val("");
          $("#addr").val("");
          $("#addrDtl").val("");
          $("#manageVanNm").val("");
          $("#manageVanCd").val("");
          $("#agencyNm").val("");
          $("#agencyCd").val("");
          $scope._setComboData("branchCd", [{"name": messages["cmm.select"], "value": ""}]);
          $scope.branchCdCombo.selectedIndex = 0;
          $scope.vendorCdCombo.selectedIndex = 0;
          $("#siteCd").val("");
          $("#mapStoreCd").val("");
          $("#latitude").val("");
          $("#longitude").val("");
          $("#vendorTermnlNo").val("");
          $("#vendorSerNo").val("");

          $scope.store.storeCd = "";
          $scope.store.storeCdChkFg = "";
          $scope.store.storeNm = "";
          $scope.store.bizStoreNm = "";
          $scope.store.ownerNm = "";
          $scope.store.userId = "";
          $scope.store.userIdChkFg = "";
          $scope.store.userPwd = "";
          $scope.store.userPwdConf = "";
          $scope.store.areaCd = "";
          $scope.store.directManageYn = "";
          $scope.store.installPosCnt = "";
          $scope.store.bizNo1 = "";
          $scope.store.bizNo2 = "";
          $scope.store.bizNo3 = "";
          $scope.store.telNo = "";
          $scope.store.mpNo = "";
          $scope.store.postNo = "";
          $scope.store.addr = "";
          $scope.store.addrDtl = "";
          $scope.store.vanNm = "";
          $scope.store.vanCd = "";
          $scope.store.agencyNm = "";
          $scope.store.agencyCd = "";
          $scope.store.branchCd = "";
          $scope.store.vendorCd = "";
          $scope.store.siteCd = "";
          $scope.store.mapStoreCd = "";
          $scope.store.latitude = "";
          $scope.store.longitude = "";
          $scope.store.vendorTermnlNo = "";
          $scope.store.vendorSerNo = "";

          // ERP 연동 매장 정보 셋팅
          // 매장코드 수동입력 시
          if($scope.store.storeCdInputType === "1") {
            // 아트박스(운영H0345,H0094)(개발DS012)
            if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
              $("#storeCd").val("P" + erpStoreScope.getErpStore().bbqStoreCd);
              $scope.store.storeCd = "P" + erpStoreScope.getErpStore().bbqStoreCd;
              $("#storeCd").attr("readonly", true);
            } else {
              $("#storeCd").val(erpStoreScope.getErpStore().bbqStoreCd);
              $scope.store.storeCd = erpStoreScope.getErpStore().bbqStoreCd;
            }
          }

          $("#storeNm").val(erpStoreScope.getErpStore().storeNm);
          $("#bizStoreNm").val(erpStoreScope.getErpStore().bizStoreNm);
          $("#ownerNm").val(erpStoreScope.getErpStore().ownerNm);
          // 아트박스 (운영 H0345,H0094) (개발 DS012)
          if($scope.store.hqOfficeCd === "H0345" || $scope.store.hqOfficeCd === "H0094" || $scope.store.hqOfficeCd === "DS012") {
            $("#userId").val("p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase());
            $("#userPwd").val("p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234");
            $("#userPwdConf").val("p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase()  + "1234");
            $scope.store.userId = "p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase();
            $scope.store.userPwd = "p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
            $scope.store.userPwdConf = "p" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
          } else if ($scope.store.hqOfficeCd === "DS034" || $scope.store.hqOfficeCd === "DS038" || $scope.store.hqOfficeCd === "DS040" || $scope.store.hqOfficeCd === "H0393" || $scope.store.hqOfficeCd === "DS021") {
            // 맘스터치 (운영 DS034,DS038,DS040,H0393) (개발 DS021)
            $("#userId").val("moms" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase());
            $("#userPwd").val(erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234");
            $("#userPwdConf").val(erpStoreScope.getErpStore().bbqStoreCd.toLowerCase()  + "1234");
            $scope.store.userId = "moms" + erpStoreScope.getErpStore().bbqStoreCd.toLowerCase();
            $scope.store.userPwd = erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
            $scope.store.userPwdConf = erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
          } else {
              $("#userId").val(erpStoreScope.getErpStore().bbqStoreCd.toLowerCase());
              $("#userPwd").val(erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234");
              $("#userPwdConf").val(erpStoreScope.getErpStore().bbqStoreCd.toLowerCase()  + "1234");
              $scope.store.userId = erpStoreScope.getErpStore().bbqStoreCd.toLowerCase();
              $scope.store.userPwd = erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
              $scope.store.userPwdConf = erpStoreScope.getErpStore().bbqStoreCd.toLowerCase() + "1234";
          }
          $scope.areaCdCombo.selectedValue = erpStoreScope.getErpStore().areaCd;
          if(erpStoreScope.getErpStore().directManageYn === null || erpStoreScope.getErpStore().directManageYn === undefined || erpStoreScope.getErpStore().directManageYn === ""){
            $("input:radio[id='directManageY']").prop("checked", false); // 직영
            $("input:radio[id='directManageN']").prop("checked", false); // 가맹
          }else{
            if(erpStoreScope.getErpStore().directManageYn === 'Y'){
              $("input:radio[id='directManageY']").prop("checked", true); // 직영
            }else if(erpStoreScope.getErpStore().directManageYn === 'N'){
              $("input:radio[id='directManageN']").prop("checked", true); // 가맹
            }
          }
          $("#installPosCnt").val(erpStoreScope.getErpStore().posCnt);
          $("#bizNo1").val(erpStoreScope.getErpStore().bizNo.substring(0, 3));
          $("#bizNo2").val(erpStoreScope.getErpStore().bizNo.substring(3, 5));
          $("#bizNo3").val(erpStoreScope.getErpStore().bizNo.substring(5, 10));
          $("#telNo").val(erpStoreScope.getErpStore().telNo);
          $("#mpNo").val(erpStoreScope.getErpStore().mpNo);
          $("#postNo").val(erpStoreScope.getErpStore().postNo);
          $("#addr").val(erpStoreScope.getErpStore().addr);
          $("#addrDtl").val(erpStoreScope.getErpStore().addrDtl);
          $("#manageVanNm").val(erpStoreScope.getErpStore().vanNm);
          $("#manageVanCd").val(erpStoreScope.getErpStore().vanCd);
          $("#agencyNm").val(erpStoreScope.getErpStore().agencyNm);
          $("#agencyCd").val(erpStoreScope.getErpStore().agencyCd);
          $("#mapStoreCd").val(erpStoreScope.getErpStore().mapStoreCd);

          $scope.store.storeNm = erpStoreScope.getErpStore().storeNm;
          $scope.store.bizStoreNm = erpStoreScope.getErpStore().bizStoreNm;
          $scope.store.ownerNm = erpStoreScope.getErpStore().ownerNm;
          $scope.store.areaCd = erpStoreScope.getErpStore().areaCd;
          $scope.store.directManageYn = erpStoreScope.getErpStore().directManageYn;
          $scope.store.installPosCnt = erpStoreScope.getErpStore().posCnt;
          $scope.store.bizNo1 = erpStoreScope.getErpStore().bizNo.substring(0, 3);
          $scope.store.bizNo2 = erpStoreScope.getErpStore().bizNo.substring(3, 5);
          $scope.store.bizNo3 = erpStoreScope.getErpStore().bizNo.substring(5, 10);
          $scope.store.telNo = erpStoreScope.getErpStore().telNo;
          $scope.store.mpNo = erpStoreScope.getErpStore().mpNo;
          $scope.store.postNo = erpStoreScope.getErpStore().postNo;
          $scope.store.addr = erpStoreScope.getErpStore().addr;
          $scope.store.addrDtl = erpStoreScope.getErpStore().addrDtl;
          $scope.store.vanNm = erpStoreScope.getErpStore().vanNm;
          $scope.store.vanCd = erpStoreScope.getErpStore().vanCd;
          $scope.store.agencyNm = erpStoreScope.getErpStore().agencyNm;
          $scope.store.agencyCd = erpStoreScope.getErpStore().agencyCd;
          $scope.store.mapStoreCd = erpStoreScope.getErpStore().mapStoreCd;

          // 매장환경복사에서 본사선택 콤보박스 자동셋팅
          $scope.envHqOfficeCdCombo.selectedValue = $("#hqOfficeCd").val();

          // Bbq 매장코드 갖고있기
          $("#hdBbqStoreCd").val(erpStoreScope.getErpStore().bbqStoreCd);

        }
      });

      // ERP 연동 매장 정보 초기화(이전데이터 남아있는 현상 발생)
      erpStoreScope.getErpStore("");

    });
    event.preventDefault();

  };

  // 지도보기 팝업
  $scope.openMap = function () {

    // 위도,경도 또는 주소가 있는지 체크
    if($("#latitude").val() === "" || $("#longitude").val() === "") {
      if($("#addr").val() === ""){
        $scope._popMsg(messages["storeManage.mapOpen.msg"]); // 정확한 주소가 없어 지도를 조회할 수 없습니다.
        return;
      }
    }

    var params = {};
    params.title = messages["storeManage.storeLocation"]; // 지도 팝업 title
    params.markerNm = $("#storeNm").val();                // 지도 위치 마커명
    params.addr = $("#addr").val();                       // 주소
    params.latitude = $("#latitude").val();               // 위도
    params.longitude = $("#longitude").val();             // 경도

    $scope.mapPopLayer.show(true);
    $scope._broadcast('mapPopCtrl', params);

  };
  
  // 본사-그룹 콤보박스 set
  $scope.setBranchDropdownList = function(){

    var params = {};
    params.hqOfficeCd = $scope.store.hqOfficeCd;

    $scope._postJSONQuery.withOutPopUp( "/store/manage/storeManage/storeManage/getBranchCombo.sb", params,
        function(response){
          $scope._setComboData("branchCd", response.data.data.list);
        }
    );
  };

  // 팝업 닫기 전 초기화
  $scope.closeStoreInfo = function () {
    $scope.resetForm();
  }

  // [1250 맘스터치] 코드별 본사 공통코드 콤보박스 조회
  $scope.hqNmcodeComboList = function (nmcodeGrpCd) {
    var params = {};
    params.hqOfficeCd = $scope.store.hqOfficeCd;
    params.nmcodeGrpCd = nmcodeGrpCd;

    $scope._postJSONQuery.withOutPopUp('/store/manage/storeManage/storeManage/getHqNmcodeComboList.sb', params, function (response) {
      if (response.data.data.list.length > 0) {
        var dataList = response.data.data.list;

        if(nmcodeGrpCd === "151") {
          $scope._setComboData("momsTeamCombo", dataList); // 추가정보-팀별
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "152") {
          $scope._setComboData("momsAcShopCombo", dataList); // 추가정보-AC점포별
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "153") {
          $scope._setComboData("momsAreaFgCombo", dataList); // 추가정보-지역구분
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "154") {
          $scope._setComboData("momsCommercialCombo", dataList); // 추가정보-상권
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "155") {
          $scope._setComboData("momsShopTypeCombo", dataList); // 추가정보-점포유형
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "156") {
          $scope._setComboData("momsStoreManageTypeCombo", dataList); // 추가정보-매장관리타입
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "167") {
          $scope._setComboData("momsStoreFg01Combo", dataList); // 추가정보-매장그룹
          dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "169") {
            $scope._setComboData("momsStoreFg02Combo", dataList); // 추가정보-매장그룹2
            dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "170") {
            $scope._setComboData("momsStoreFg03Combo", dataList); // 추가정보-매장그룹3
            dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "171") {
            $scope._setComboData("momsStoreFg04Combo", dataList); // 추가정보-매장그룹4
            dataList.unshift({name: "선택", value: ""});
        } else if(nmcodeGrpCd === "172") {
            $scope._setComboData("momsStoreFg05Combo", dataList); // 추가정보-매장그룹5
            dataList.unshift({name: "선택", value: ""});
        }

      } else {
        if(nmcodeGrpCd === "151") {
          $scope._setComboData("momsTeamCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-팀별
        } else if(nmcodeGrpCd === "152") {
          $scope._setComboData("momsAcShopCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-AC점포별
        } else if(nmcodeGrpCd === "153") {
          $scope._setComboData("momsAreaFgCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-지역구분
        } else if(nmcodeGrpCd === "154") {
          $scope._setComboData("momsCommercialCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-상권
        } else if(nmcodeGrpCd === "155") {
          $scope._setComboData("momsShopTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-점포유형
        } else if(nmcodeGrpCd === "156") {
          $scope._setComboData("momsStoreManageTypeCombo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장관리타입
        } else if(nmcodeGrpCd === "167") {
          $scope._setComboData("momsStoreFg01Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹
        } else if(nmcodeGrpCd === "169") {
            $scope._setComboData("momsStoreFg02Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹2
        } else if(nmcodeGrpCd === "170") {
            $scope._setComboData("momsStoreFg03Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹3
        } else if(nmcodeGrpCd === "171") {
            $scope._setComboData("momsStoreFg04Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹4
        } else if(nmcodeGrpCd === "172") {
            $scope._setComboData("momsStoreFg05Combo", [{"name": messages["cmm.select"], "value": ""}]); // 추가정보-매장그룹5
        }
      }
    });
  }

  // [1250 맘스터치] 브랜드 콤보박스 조회
  $scope.hqBrandCdComboList = function () {
    var params = {};
    params.hqOfficeCd = $scope.store.hqOfficeCd;

    $scope._postJSONQuery.withOutPopUp('/store/manage/storeManage/storeManage/getHqBrandCdComboList.sb', params, function (response) {
      if (response.data.data.list.length > 0) {
        var dataList = response.data.data.list;
        $scope._setComboData("hqBrandCdCombo", dataList); // 브랜드
      } else {
        // 콤보박스 데이터
        $scope._setComboData("hqBrandCdCombo", [{"name": "기본브랜드", "value": "0000000"}]); // 브랜드
      }
    });
  };

// 매장환경복사 항목 전체선택/해제
  $scope.allChkYn = function (val) {
    if (val === 'Y') {
      $("input:checkbox[name='copyChk']").prop("checked", true);

      // 신규등록할 매장이 단독매장일 경우, 일부 매장환경 복사 금지
      if ($scope.store.hqOfficeCd === "00000") {
        $("input:checkbox[id='productChk']").prop("checked", false);
        $("input:checkbox[id='salePriceChk']").prop("checked", false);
        $("input:checkbox[id='supplyPriceChk']").prop("checked", false);
        $("input:checkbox[id='touchKeyChk']").prop("checked", false);
        $("input:checkbox[id='promotionChk']").prop("checked", false);
        $("input:checkbox[id='dlvrProdChk']").prop("checked", false);
      }

    } else {
      $("input:checkbox[name='copyChk']").prop("checked", false);
    }
  };

}]);
// 터미널관리(밴더코드) 코드값으로 명칭 가져오기
function getVendorCdNm(cd) {
    var list = vanList01;
    for (var i = 0; i < list.length; i++) {
        if (list[i].value === cd) {
            return list[i].name;
        }
    }
}

