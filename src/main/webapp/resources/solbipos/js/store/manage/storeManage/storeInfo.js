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
  $scope._setComboData("envHqOfficeCd", hqList);

  // console.log($scope)

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
    $scope.sysStatFgVal = s.selectedValue;
  };
  $scope.getSysStatFgVal = function(){
    return $scope.sysStatFgVal;
  };

  // 매장선택
  $scope.envStoreCdVal;
  $scope.setEnvStoreCdVal = function(s){
    $scope.envStoreCdVal = s.selectedValue;
  };
  $scope.getEnvStoreCdVal = function(){
    return $scope.envStoreCdVal;
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

    // console.log($scope.envHqOfficeCdCombo);
    // console.log($scope.sysStatFgCombo);

    $scope.store.storeCd                = '자동채번';
    $scope.store.beforeBizNo            = '';
    $scope.areaCdCombo.selectedIndex    = 0;
    $scope.clsFgCombo.selectedIndex     = 0;
    $scope.sysStatFgCombo.selectedIndex = 0;
    $scope.store.sysOpenDate.value      = new Date();


    $scope.sysOpenDateCombo.isReadOnly  = false;
    $scope.sysStatFgCombo.isReadOnly    = false;
    $scope.clsFgCombo.isReadOnly        = false;
    $scope.readOnlyStatus               = false;
  };

  /*********************************************************
   * 매장정보 조회
   * *******************************************************/
  $scope.getStoreInfo = function(){

    var storeScope = agrid.getScope('storeManageCtrl'); // 선택된 매장
    var params     = storeScope.getSelectedStore();

    // todo 데이터 메세지
    // 데이터가 있는데 response.data.message 가 '"조회 데이터가 없습니다."' 라고 나오는 경우
    $scope._postJSONQuery.withOutPopUp( '/store/manage/storeManage/storeManage/getStoreDetail.sb', params, function(response){
      // console.log(response.data)
      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.storeInfoLayer.hide();
        return false;
      }

      var installPosCnt   = response.data.data.instPosCnt;
      var storeDetailInfo = response.data.data.storeDtlInfo;

      $("#storeInfoTitle").text("[" + storeDetailInfo.storeCd + "] " + storeDetailInfo.storeNm);

      $scope.store                        = storeDetailInfo;
      $scope.store.hqOfficeNm             = storeDetailInfo.hqOfficeNm;
      $scope.store.installPosCnt          = installPosCnt;
      $scope.store.beforeBizNo            = storeDetailInfo.bizNo;
      $scope.store.sysOpenDate.value      = stringToDate(storeDetailInfo.sysOpenDate);

      $scope.areaCdCombo.selectedValue    = storeDetailInfo.areaCd;
      $scope.clsFgCombo.selectedValue     = storeDetailInfo.clsFg;
      $scope.sysStatFgCombo.selectedValue = storeDetailInfo.sysStatFg;

      $scope.readOnlyStatus               = true;
      $scope.sysOpenDateCombo.isReadOnly  = true;

      if(storeDetailInfo.sysStatFg === '9'){
        $scope.sysStatFgCombo.isReadOnly  = true;
      } else {
        $scope.sysStatFgCombo.isReadOnly  = false;
      }
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

        if( !$.isEmptyObject(hqScope.getHq())  ){
          $scope.store.hqOfficeCd = hqScope.getHq().hqOfficeCd;
          $scope.store.hqOfficeNm = hqScope.getHq().hqOfficeNm;

          if(hqScope.getHq().sysStatFg === '9') {
            $scope.sysStatFgCombo.selectedValue = '9';
            $scope.sysStatFgCombo.isReadOnly = true;
          }else{
            $scope.sysStatFgCombo.selectedValue = '1';
            $scope.sysStatFgCombo.isReadOnly = false;
          }
        }
      });
    });
    event.preventDefault();
  };

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
    var popup = $scope.agencyLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var agencyScope = agrid.getScope('searchAgencyCtrl');
      agencyScope.$apply(function(){
        agencyScope._gridDataInit();

        if( !$.isEmptyObject(agencyScope.getAgency())  ){
          $scope.store.agencyCd = agencyScope.getAgency().agencyCd;
          $scope.store.agencyNm = agencyScope.getAgency().agencyNm;
        }
      });
    });
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
  };

  /*********************************************************
   * 주소검색 TODO
   * *******************************************************/
  $scope.searchAddr = function(){
  };

  /*********************************************************
   * 매장환경 탭 클릭
   * *******************************************************/
  $scope.changeTab = function(){

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

}]);
