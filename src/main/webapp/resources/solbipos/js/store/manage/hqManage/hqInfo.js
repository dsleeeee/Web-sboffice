/****************************************************************
 *
 * 파일명 : hqInfo.js
 * 설  명 : 본사정보관리 > 본사상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.16     김지은      1.0
 *
 * **************************************************************/

app.controller('hqInfoCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqInfoCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("areaCd", areaCd);

  // 사업자번호 중복체크 여부
  $scope.isBizChk = false;

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

  /*********************************************************
   * grid 초기화
   * *******************************************************/
  $scope.initGrid = function (s, e) {};

  /*********************************************************
   * 팝업 오픈
   * 선택된 본사가 없으면 : 신규등록 (폼 리셋)
   * 선택된 본사가 있으면 : 데이터 조회
   * *******************************************************/
  $scope.$on("hqInfoCtrl", function(event, data) {

    console.log(">>>>>");

    var hqScope = agrid.getScope('hqManageCtrl');

    if($.isEmptyObject(hqScope.getSelectedHq()) ) {
      $scope.resetForm();
    } else {
      $scope.getHqInfo();
    }
    event.preventDefault();
  });

  /*********************************************************
   * 매장 신규 등록을 위한 폼 리셋
   * *******************************************************/
  $scope.resetForm = function(){

    $("#regForm")[0].reset();
    $("#hqInfoTitle").text("");
    $('#rHqOfficeCd').val('자동채번');

    $scope.hq.hqOfficeCd                = '자동채번'; // todo 왜 셋팅이 안되는지
    $scope.hq.beforeBizNo               = '';
    $scope.areaCdCombo.selectedIndex    = 0;
    $scope.clsFgCombo.selectedIndex     = 0;
    $scope.sysStatFgCombo.selectedIndex = 0;
    $scope.hq.sysOpenDate.value         = new Date();

    $scope.sysOpenDateCombo.isReadOnly  = false;
    $scope.sysStatFgCombo.isReadOnly    = false;
    $scope.clsFgCombo.isReadOnly        = false;
  };

  /*********************************************************
   * 본사정보 조회
   * *******************************************************/
  $scope.getHqInfo = function(){

    var hqScope = agrid.getScope('hqManageCtrl'); // 선택된 매장
    var params  = hqScope.getSelectedHq();

    // todo 데이터 메세지
    // 데이터가 있는데 response.data.message 가 '"조회 데이터가 shown.addHandle없습니다."' 라고 나오는 경우
    $scope._postJSONQuery.withOutPopUp( '/store/hq/hqManage/master/getHqDetailInfo.sb', params, function(response){
      console.log(response.data)
      if($.isEmptyObject(response.data) ) {
        $scope._popMsg(messages["cmm.empty.data"]);
        $scope.hqInfoLayer.hide();
        return false;
      }

      var hqDetailInfo = response.data.data;

      $("#hqInfoTitle").text("[" + hqDetailInfo.hqOfficeCd + "] " + hqDetailInfo.hqOfficeNm);

      $scope.hq                           = hqDetailInfo;
      $scope.hq.beforeBizNo               = hqDetailInfo.bizNo;
      $scope.hq.sysOpenDate.value         = stringToDate(hqDetailInfo.sysOpenDate);

      $scope.areaCdCombo.selectedValue    = hqDetailInfo.areaCd;
      $scope.clsFgCombo.selectedValue     = hqDetailInfo.clsFg;
      $scope.sysStatFgCombo.selectedValue = hqDetailInfo.sysStatFg;
      $scope.sysOpenDateCombo.isReadOnly  = true;
    });
  };

  /*********************************************************
   * 값 체크
   * *******************************************************/
  $scope.valueCheck = function(){

    var hqScope = agrid.getScope('hqManageCtrl');

    // 본사명을 입력해주세요.
    var msg = messages["hqManage.hqNm"]+messages["cmm.require.text"];
    if( isNull( $scope.hq.hqOfficeNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 대표자명을 입력해주세요.
    var msg = messages["hqManage.ownerNm"]+messages["cmm.require.text"];
    if( isNull( $scope.hq.ownerNm)) {
      $scope._popMsg(msg);
      return;
    }

    // 등록시나 이전값과 변경하여 변경되었을때, 사업자번호 중복체크를 해주세요.
    var msg = messages["hqManage.require.duplicate.bizNo"];
    var bizNoStr = $scope.hq.bizNo1 + $scope.hq.bizNo2 +  $scope.hq.bizNo3;
    if($.isEmptyObject(hqScope.getSelectedHq())  || (bizNoStr !== $scope.hq.beforeBizNo) ) {

      // 사업자번호를 입력해주세요.
      var msg = messages["hqManage.bizNo"]+messages["cmm.require.text"];
      if( isNull( $scope.hq.bizNo1 )|| isNull( $scope.hq.bizNo2 ) || isNull( $scope.hq.bizNo3 ) ) {
        $scope._popMsg(msg);
        return;
      }

      // 사업자번호는 숫자만 입력할 수 있습니다.
      var msg = messages["hqManage.bizNo"]+messages["cmm.require.number"];
      var numChkregexp = /[^0-9]/g;
      if(numChkregexp.test( $scope.hq.bizNo1 ) || numChkregexp.test( $scope.hq.bizNo2 ) || numChkregexp.test( $scope.hq.bizNo3 )) {
        $scope._popMsg(msg);
        return false;
      }

      if(!$scope.isBizChk) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 상호명을 입력해주세요.
    var msg = messages["hqManage.bizHqNm"]+messages["cmm.require.text"];
    if( isNull( $scope.hq.bizStoreNm )) {
      $scope._popMsg(msg);
      return false;
    }

    // 용도를 선택해주세요.
    var msg = messages["hqManage.clsFg"]+messages["cmm.require.select"];
    if( isNull( $scope.hq.clsFg ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 매장상태구분을 선택해주세요.
    var msg = messages["hqManage.sysStatFg"]+messages["cmm.require.select"];
    if( isNull( $scope.hq.sysStatFg ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 날씨표시지역을 선택해주세요.
    var msg = messages["hqManage.weatherArea"]+messages["cmm.require.select"];
    if( isNull( $scope.hq.areaCd )) {
      $scope._popMsg(msg);
      return false;
    }

    // 전화번호를 입력해주세요.
    var msg = messages["hqManage.telNo"]+messages["cmm.require.text"];
    if( isNull( $scope.hq.telNo ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 전화번호는 숫자만 입력할 수 있습니다.
    var msg = messages["hqManage.telNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if(numChkregexp.test( $scope.hq.telNo )) {
      $scope._popMsg(msg);
      return false;
    }

    // 팩스번호는 숫자만 입력할 수 있습니다.
    var msg = messages["hqManage.faxNo"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if( (!$.isEmptyObject($scope.hq.faxNo)) && numChkregexp.test( $scope.hq.faxNo )) {
      $scope._popMsg(msg);
      return false;
    }

    // 주소를 입력해주세요.
    var msg = messages["hqManage.addr"]+messages["cmm.require.text"];
    if( isNull( $scope.hq.postNo ) || isNull( $scope.hq.addr ) || isNull( $scope.hq.addrDtl )) {
      $scope._popMsg(msg);
      return false;
    }

    // 관리업체를 선택해주세요.
    var msg = messages["hqManage.agency"]+messages["cmm.require.select"];
    if( isNull( $scope.hq.agencyCd) ) {
      $scope._popMsg(msg);
      return false;
    }
    return true;
  };


  /*********************************************************
   * 저장
   * *******************************************************/
  $scope.save = function(){

    if(!$scope.valueCheck()) return false;

    var params         = $scope.hq;
    params.sysOpenDate = dateToDaystring($scope.hq.sysOpenDate);


    var hqScope = agrid.getScope('hqManageCtrl');

    // 본사 신규 등록시
    if($.isEmptyObject(hqScope.getSelectedHq()) ) {

      var copyChkVal = "";

      $("input[name=copyChk]:checked").each(function() {
        copyChkVal += ($(this).val() + "|");
      });

      params.copyChkVal = copyChkVal;

      $scope._postJSONSave.withPopUp("/store/hq/hqManage/master/saveHqInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        
        // 본사목록 재조회
        var hqScope = agrid.getScope('hqManageCtrl');
        hqScope.getHqList();
        
        $scope.hqInfoLayer.hide();
      });
    }
    // 수정
    else {
      $scope._postJSONSave.withPopUp("/store/hq/hqManage/master/updateHqInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.hqInfoLayer.hide();
      });
    }
  };

  /*********************************************************
   * 관리업체 조회 (대리점)
   * *******************************************************/
  $scope.searchManageVan = function(){
    var popup = $scope.agencyLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var agencyScope = agrid.getScope('searchAgencyCtrl');
      agencyScope.$apply(function(){
        agencyScope._gridDataInit();
        if( !$.isEmptyObject(agencyScope.getAgency())  ){
          $scope.hq.agencyCd = agencyScope.getAgency().agencyCd;
          $scope.hq.agencyNm = agencyScope.getAgency().agencyNm;
        }
      });
    });
  };

  /*********************************************************
   * 사업자번호 중복체크
   * *******************************************************/
  $scope.chkBizNo = function(){

    if( isNull ($scope.hq.bizNo1) || isNull ($scope.hq.bizNo2) || isNull ($scope.hq.bizNo3)) {
      $scope._popMsg(messages["hqManage.bizNo"]+messages["cmm.require.text"]);
      return false;
    }

    var popup = $scope.checkBizNoLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    popup.shown.addHandler(function (s) {
      setTimeout(function() {
        var bizNoScope = agrid.getScope('checkBizNoCtrl');
        bizNoScope.setBizNo( $scope.hq.bizNo1 + $scope.hq.bizNo2 +  $scope.hq.bizNo3);
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
   * 본사환경 탭 클릭
   * *******************************************************/
  $scope.showEnvSetting = function(){

    var hqScope = agrid.getScope('hqManageCtrl');

    if($.isEmptyObject(hqScope.getSelectedHq()) ) {
      $scope._popMsg(messages["hqManage.require.regist.hq"]);
      return false;
    }

    $scope.hqInfoLayer.hide();

    var envPopup = $scope.hqEnvLayer;

    // 팝업 열린 뒤. 딜레이줘서 열리고 나서 실행되도록 함
    envPopup.shown.addHandler(function (s) {
      setTimeout(function() {
        $scope._broadcast('hqEnvCtrl');
      }, 50)
    });

    // 팝업 닫을때
    envPopup.show(true, function (s) {
    });
  };

}]);
