/****************************************************************
 *
 * 파일명 : memberRegist.js
 * 설  명 : 회원정보관리 > 회원정보등록 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('memberRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberRegistCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터
  $scope._setComboData("rEmailRecvYn", recvDataMapEx);
  $scope._setComboData("rSmsRecvYn", recvDataMapEx);
  $scope._setComboData("rGendrFg", genderDataMapEx);
  $scope._setComboData("rWeddingYn", weddingDataMap);
  $scope._setComboData("rRegStoreCd", regstrStoreList);
  $scope._setComboData("rUseYn", useDataMap);

  $scope.selectedMember;
  $scope.setSelectedMember = function(data) {
    $scope.selectedMember = data;
  };
  $scope.getSelectedMember = function(){
    return $scope.selectedMember;
  };

  /*********************************************************
   * 팝업 오픈
   * 선택된 회원이 없으면 : 신규등록 (폼 리셋)
   * 선택된 회원이 있으면 : 데이터 수정
   * *******************************************************/
  $scope.$on("memberRegistCtrl", function(event, data) {

    $scope.setSelectedMember(data);

    if($.isEmptyObject(data) ) {
      $scope.resetForm();
    } else {
      $scope.getMemberInfo();
    }

    event.preventDefault();
  });

  /*********************************************************
   * 회원 등록을 위한 폼 리셋
   * *******************************************************/
  $scope.resetForm = function(){

    $("#regForm")[0].reset();
    $("#memberInfoTitle").text("");

    $scope.$apply(function(){
      $scope.member.membrNo                 = '자동채번';
      $scope.member.beforeBizNo             = '';
      $scope.regStoreCdCombo.selectedIndex  = 0;
      $scope.genderCombo.selectedIndex      = 0;
      $scope.weddingYnCombo.selectedIndex   = 0;
      $scope.useYnCombo.selectedIndex       = 0;
      $scope.emailRecvYnCombo.selectedIndex = 0;
      $scope.smsRecvYnCombo.selectedIndex   = 0;

      $scope.member.weddingday.value        = new Date();
      $scope.member.birthday.value          = new Date();

      $scope.weddingDayCombo.isReadOnly     = false;
    });

  };

  /*********************************************************
   * 회원정보 조회
   * *******************************************************/
  $scope.getMemberInfo = function(){

    var params = $scope.getSelectedMember();

    $scope._postJSONQuery.withOutPopUp( '/membr/info/view/base/getMemberInfo.sb', params, function(response){

      console.log(response.data.data);

      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.memberRegistLayer.hide();
        return false;
      }
      var memberDetailInfo = response.data.data;

      $("#memberInfoTitle").text("[" + memberDetailInfo.membrNo + "] " + memberDetailInfo.membrNm);

      $scope.member                        = memberDetailInfo;

      if(memberDetailInfo.weddingYn == "Y") {
        $scope.weddingDayCombo.isReadOnly  = false;
        $scope.member.weddingday.value     = stringToDate(memberDetailInfo.weddingday);
      } else {
        $scope.weddingDayCombo.isReadOnly  = true;
        $scope.weddingDayCombo.value       = new Date();
      }

      $scope.birthdayCombo.value           = stringToDate(memberDetailInfo.birthday);

      // $scope.member.birthday               = stringToDate(memberDetailInfo.birthday);
      // $scope.member.weddingday             = stringToDate(memberDetailInfo.weddingday);


      // $scope.member.hqOfficeNm             = storeDetailInfo.hqOfficeNm;
      // $scope.member.installPosCnt          = installPosCnt;
      // $scope.member.beforeBizNo            = storeDetailInfo.bizNo;
      // $scope.member.sysOpenDate.value      = stringToDate(storeDetailInfo.sysOpenDate);

      // $scope.areaCdCombo.selectedValue    = storeDetailInfo.areaCd;
      // $scope.clsFgCombo.selectedValue     = storeDetailInfo.clsFg;
      // $scope.sysStatFgCombo.selectedValue = storeDetailInfo.sysStatFg;

      // $scope.readOnlyStatus               = true;
      // $scope.sysOpenDateCombo.isReadOnly  = true;

      // if(storeDetailInfo.sysStatFg === '9'){
      //   $scope.sysStatFgCombo.isReadOnly  = true;
      // } else {
      //   $scope.sysStatFgCombo.isReadOnly  = false;
      // }
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
      var msg = messages["storeManage.hqOffice"]+messages["cmm.require.select"];
      if( isNull( $scope.store.hqOfficeCd ) || isNull( $scope.store.hqOfficeNm )) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 매장명을 입력해주세요.
    var msg = messages["storeManage.storeNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.storeNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 대표자명을 입력해주세요.
    var msg = messages["storeManage.onwerNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.ownerNm)) {
      $scope._popMsg(msg);
      return;
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

    // 상호명을 입력해주세요.
    var msg = messages["storeManage.bizStoreNm"]+messages["cmm.require.text"];
    if( isNull( $scope.store.bizStoreNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 용도를 선택해주세요.
    var msg = messages["storeManage.clsFg"]+messages["cmm.require.select"];
    if( isNull( $scope.store.clsFg ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 매장상태구분을 선택해주세요.
    var msg = messages["storeManage.sysStatFg"]+messages["cmm.require.select"];
    if( isNull( $scope.store.sysStatFg ) ) {
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

    // 날씨표시지역을 선택해주세요.
    var msg = messages["storeManage.weatherArea"]+messages["cmm.require.select"];
    if( isNull( $scope.store.areaCd )) {
      $scope._popMsg(msg);
      return false;
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
    if( isNull( $scope.store.postNo ) || isNull( $scope.store.addr ) || isNull( $scope.store.addrDtl )) {
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


    var storeScope = agrid.getScope('storeManageCtrl');

    // 매장 신규 등록시
    if($.isEmptyObject(storeScope.getSelectedStore()) ) {

      var copyChkVal = "";

      $("input[name=copyChk]:checked").each(function() {
        copyChkVal += ($(this).val() + "|");
      });

      params.copyChkVal = copyChkVal;

      $scope._postJSONSave.withPopUp("/store/manage/storeManage/storeManage/saveStoreInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.storeInfoLayer.hide();
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
   * 주소검색 TODO
   * *******************************************************/
  $scope.searchAddr = function(){
  };


}]);
