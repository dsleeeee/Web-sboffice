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

app.controller('storeInfoCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeInfoCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("areaCd", areaCd);

  // 관리자의 경우, 모든 본사(데모까지) 나오고, 총판의 경우, 자기가 관리하는 본사만 나오도록
  if(orgnFg === "AGENCY") {
    $scope._setComboData("envHqOfficeCd", authHqList);
  }else{
    $scope._setComboData("envHqOfficeCd", hqList);
  }

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

  /*********************************************************
   * [추가설정] 복사할 본사 클릭시, 해당 본사의 매장 목록 조회
   * *******************************************************/
  $scope.envHqOfficeCdVal;
  $scope.setEnvHqOfficeCdVal = function(s,e){

    $scope.envHqOfficeCdVal = s.selectedValue;

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
    } else {
      $scope.getStoreInfo();
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

    $scope.sysOpenDateCombo.isReadOnly  = false;
    $scope.store.sysOpenDate.value      = new Date();
    $scope.sysStatFgCombo.isReadOnly    = false;
    $scope.sysStatFgCombo.selectedIndex = 0;
    $scope.clsFgCombo.isReadOnly        = false;
    $scope.clsFgCombo.selectedIndex     = 0;
    $scope.areaCdCombo.selectedIndex    = 0;
    //$scope.store.installPosCnt.isReadOnly = false;
    $("#installPosCnt").attr("disabled", false);
    $("#installPosCnt").css('background-color', '#FFFFFF');
    $scope.store.installPosCnt          = '';
    $("#hdSysStatFg").val("");

    $scope.store.bizNo1 ="";
    $scope.store.bizNo2 ="";
    $scope.store.bizNo3 ="";
    $scope.store.beforeBizNo ="";
    $scope.store.telNo ="";
    $scope.store.faxNo ="";
    $scope.store.emailAddr ="";
    $scope.store.hmpgAddr ="";

    $scope.store.postNo ="";
    $scope.store.addr ="";
    $scope.store.addrDtl ="";

    $scope.readOnlyStatus               = false;

    $("#installPosCnt").css('background-color', '#ffffff');
    $("#storeCd").attr("readonly",true);
    $("#storeCd").css("width", "100%");
    $("#btnChkStoreCd").css("display", "none");
    $("#additionalArea").css("display", "");

    // 총판계정으로 접속한 경우, 해당 총판의 데이터만 조회되도록 함.
    // if(orgnFg === "AGENCY" && pAgencyCd !== "00000"){
    if(orgnFg === "AGENCY"){
      $scope.store.agencyCd = orgnCd;
      $scope.store.agencyNm = orgnNm;
      $("#agencyCd").val(orgnCd);
      $("#agencyNm").val(orgnNm);
    }

    $scope.store.mapStoreCd = "";

  };

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

      console.log('storeDetailInfo',storeDetailInfo);

      $("#storeInfoTitle").text("[" + storeDetailInfo.storeCd + "] " + storeDetailInfo.storeNm);

      $scope.store                        = storeDetailInfo;
      $scope.store.hqOfficeNm             = storeDetailInfo.hqOfficeNm;
      $scope.store.installPosCnt          = installPosCnt;
      $scope.store.beforeBizNo            = storeDetailInfo.bizNo;
      $scope.store.sysOpenDate.value      = storeDetailInfo.sysOpenDate;

      $scope.areaCdCombo.selectedValue    = storeDetailInfo.areaCd;
      $scope.clsFgCombo.selectedValue     = storeDetailInfo.clsFg;
      $scope.sysStatFgCombo.selectedValue = storeDetailInfo.sysStatFg;

      $scope.directManageYn               = storeDetailInfo.directManageYn;

      $scope.readOnlyStatus                  = true;
      $scope.sysOpenDateCombo.isReadOnly     = true;
      //$scope.store.installPosCnt.isReadOnly = true;
      $("#installPosCnt").attr("disabled", true);
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

      $scope.store.mapStoreCd = storeDetailInfo.mapStoreCd;
    });
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

    // 주소를 입력해주세요.
    var msg = messages["storeManage.addr"]+messages["cmm.require.text"];
    if($("#postNo").val() === "" || $("#addr").val() === "" || $("#addrDtl").val() === "") {
      $scope._popMsg(msg);
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

    return true;
  };


  /*********************************************************
   * 저장
   * *******************************************************/
  $scope.save = function(){

    if(!$scope.valueCheck()) return false;

    var params         = $scope.store;
    params.sysOpenDate = dateToDaystring($scope.store.sysOpenDate);
    params.postNo = $("#postNo").val();
    params.addr = $("#addr").val();
    params.addrdtl = $("#addrDtl").val();

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
    var params         = data;

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

      });
    }
    // 수정
    else {
      $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/updateStoreInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.storeInfoLayer.hide();
      });
    }
  };


  /*********************************************************
   * 본사 조회 팝업
   * *******************************************************/
  $scope.searchHq = function(){

    var storeScope = agrid.getScope('storeManageCtrl');

    if(!$.isEmptyObject(storeScope.getSelectedStore()) ) {  // 신규등록시에만 본사 조회 팝업 호출
      return false;
    }

    var popup = $scope.hqLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var hqScope = agrid.getScope('searchHqCtrl');
      hqScope.$apply(function(){
        hqScope._gridDataInit();

        if( !$.isEmptyObject(hqScope.getHq())  ) {
          $scope.store.hqOfficeCd = hqScope.getHq().hqOfficeCd;
          $scope.store.hqOfficeNm = hqScope.getHq().hqOfficeNm;
          $scope.store.storeCdInputType = hqScope.getHq().envst0027; // 매장코드 채번방식(자동/수동)
          $scope.store.storeCdChkFg = "";
          $scope.store.envst0043 = hqScope.getHq().envst0043; // 본사신규상품매장생성

          // 매장코드 채번방식
          if (hqScope.getHq().envst0027 === '1') { //수동
            $scope.store.storeCd = ''
            $("#storeCd").removeAttr("readonly");
            $("#storeCd").css("width", "60%");
            $("#btnChkStoreCd").css("display", "");

          } else {
            if (hqScope.getHq().envst0027 === '0') { //자동
              $scope.store.storeCd = '자동채번'
            } else {
              $scope.store.storeCd = ''
            }
            $("#storeCd").attr("readonly", true);
            $("#storeCd").css("width", "100%");
            $("#btnChkStoreCd").css("display", "none");
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
      $("#productChk").attr("disabled", true);
      $("#salePriceChk").attr("disabled", true);
      $("#supplyPriceChk").attr("disabled", true);
      $("#touchKeyChk").attr("disabled", true);

    // 신규등록할 매장이 프랜매장일 경우
    } else if($scope.store.hqOfficeCd !== "00000") {
      // 매장환경복사의 매장을 선택 안한후 신규등록할 매장을 수정하면
      if($scope.store.copyStoreCd == null || $scope.store.copyStoreCd == "") {
        $("#productChk").attr("disabled", false);
        $("#salePriceChk").attr("disabled", false);
        $("#supplyPriceChk").attr("disabled", false);
        $("#touchKeyChk").attr("disabled", false);

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
            $("#productChk").attr("disabled", true);
            $("#salePriceChk").attr("disabled", true);
            $("#supplyPriceChk").attr("disabled", true);
            $("#touchKeyChk").attr("disabled", true);

        // 매장환경복사 매장이 프랜매장일 경우
        } else if($scope.store.copyHqOfficeCd !== "00000") {
          // 매장환경복사 본사와 같은 본사면
          if($scope.store.hqOfficeCd === $scope.store.copyHqOfficeCd) {
            if ($("#productChk").is(":checked") === true) {
              $("#productChk").prop("checked", false);
            }
            $("#productChk").attr("disabled", true);
            $("#salePriceChk").attr("disabled", false);
            $("#supplyPriceChk").attr("disabled", false);
            $("#touchKeyChk").attr("disabled", false);

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
            $("#productChk").attr("disabled", true);
            $("#salePriceChk").attr("disabled", true);
            $("#supplyPriceChk").attr("disabled", true);
            $("#touchKeyChk").attr("disabled", true);
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
    if(orgnFg === "MASTER" || pAgencyCd === "00000") {
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

    // 관리자와 총판권한만 수정 가능
    if(orgnFg !== "MASTER" && orgnFg !== "AGENCY"){
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
      $scope._popMsg(messages["storeManage.storeCd"]+messages["cmm.require.text"]);
      return false;
    }

    var params    = {};
    params.storeCd = $scope.store.storeCd;

      $scope._postJSONQuery.withPopUp( "/store/manage/storeManage/storeManage/getStoreCdCnt.sb", params, function(response){

          var result = response.data.data;

          if(result === 0){ // 사용가능
              $scope._popMsg(messages["storeManage.notStoreCdDuplicate.msg"]);
              $scope.store.storeCdChkFg = $scope.store.storeCd;

          }else{ // 중복
              $scope._popMsg(messages["storeManage.storeCdDuplicate.msg"]);
              $scope.store.storeCdChkFg ="";
          }
      });

  };

}]);
