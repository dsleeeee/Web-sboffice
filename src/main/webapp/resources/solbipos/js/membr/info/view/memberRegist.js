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

  // 기본 회원등급
  if(memberClassList.length == 0) {
    memberClassList = [{value: "", name: "선택"}, {value: "0001", name: "기본등급"}];
  }

  // 조회조건 콤보박스 데이터
  $scope._setComboData("rEmailRecvYn", recvDataMapEx);
  $scope._setComboData("rSmsRecvYn", recvDataMapEx);
  $scope._setComboData("rGendrFg", genderDataMapEx);
  $scope._setComboData("rWeddingYn", weddingDataMap);
  $scope._setComboData("rRegStoreCd", regstrStoreList);
  $scope._setComboData("rUseYn", useDataMap);
  $scope._setComboData("rMemberClass", memberClassList);

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


  $scope.changeWeddingCombo = function(s, e){
    if(s.selectedValue === 'Y') {
      $scope.weddingDayCombo.isReadOnly     = false;
    } else {
      $scope.weddingDayCombo.isReadOnly     = true;
    }
  };


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
      $scope.useYnCombo.selectedValue       = 'Y';
      $scope.emailRecvYnCombo.selectedIndex = 0;
      $scope.smsRecvYnCombo.selectedIndex   = 0;
      $scope.memberClassCombo.selectedIndex = 0;

      $scope.weddingDayCombo.selectedValue  = new Date();
      $scope.weddingDayCombo.selectedValue  = new Date();
      $scope.birthdayCombo.selectedValue    = new Date();

      $scope.weddingDayCombo.refresh();
      $scope.birthdayCombo.refresh();

      $scope.weddingDayCombo.isReadOnly     = true;
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

    // 신규등록일 경우
    if( $.isEmptyObject($scope.selectedMember) ){
      // 등록매장을 선택해주세요.
      var msg = messages["regist.reg.store.cd"]+messages["cmm.require.select"];
      console.log("regStoreCdCombo : "+ $scope.regStoreCdCombo.selectedValue )

      if( isNull( $scope.regStoreCdCombo.selectedValue )) {
        $scope._popMsg(msg);
        return false;
      }
    }

    // 전화번호는 숫자만 입력할 수 있습니다.
    var msg = messages["regist.tel"]+messages["cmm.require.number"];
    var numChkregexp = /[^0-9]/g;
    if(numChkregexp.test( $scope.member.telNo )) {
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

    var params         = $scope.member;
    // params.sysOpenDate = dateToDaystring($scope.store.sysOpenDate);

    console.log(params);

    // 회원 신규 등록시
    if($.isEmptyObject($scope.selectedMember) ) {
      $scope._postJSONSave.withPopUp("/membr/info/view/base/registMemberInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.memberRegistLayer.hide();
      });
    }
    // 수정
    else {
      $scope._postJSONSave.withPopUp("/membr/info/view/base/updateMemberInfo.sb", params, function () {
        $scope._popMsg(messages["cmm.saveSucc"]);
        $scope.memberRegistLayer.hide();
      });
    }
  };

  /*********************************************************
   * 주소검색 TODO
   * *******************************************************/
  $scope.searchAddr = function(){
  };


}]);
