/****************************************************************
 *
 * 파일명 : newStoreRegist.js
 * 설  명 : 매장정보관리 > 매장상세 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.10.23     김지은      1.0
 *
 * **************************************************************/

app.controller('storeRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeRegistCtrl', $scope, $http, false));

  // 조회조건 콤보박스 데이터
  $scope._setComboData("clsFg", clsFg);
  $scope._setComboData("sysStatFg", sysStatFg);
  $scope._setComboData("areaCd", areaCd);

  // $scope.openPosDate = wcombo.genDateVal("#openPosDate", startDt);

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

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {};

  // 팝업 오픈시 매장정보 조회
  $scope.$on("storeRegistCtrl", function(event, data) {
    event.preventDefault();
  });

  // 값 체크
  $scope.valueCheck = function(){

    // 매장명을 입력해주세요.
    var msg = messages["storeManage.storeNm"]+messages["cmm.require.text"];
    if( isNull( $("#storeNm").val())) {
      $scope._popMsg(msg);
      return false;
    }

    // 대표자명을 입력해주세요.
    var msg = messages["storeManage.onwerNm"]+messages["cmm.require.text"];
    if( isNull($("#ownerNm").val())) {
      $scope._popMsg(msg);
      return false;
    }

    // 사업자번호를 입력해주세요.
    var msg = messages["storeManage.bizNo"]+messages["cmm.require.text"];
    if( isNull($("#bizNo1").val())|| isNull( $("#bizNo2").val()) || isNull($("#bizNo3").val()) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 상호명을 입력해주세요.
    var msg = messages["storeManage.bizStoreNm"]+messages["cmm.require.text"];
    if( isNull($("#bizStoreNm").val())) {
      $scope._popMsg(msg);
      return false;
    }

    // 용도를 선택해주세요.
    var msg = messages["storeManage.clsFg"]+messages["cmm.require.select"];
    if( isNull( $scope.getClsFg() ) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 매장상태구분을 선택해주세요.
    var msg = messages["storeManage.sysStatFg"]+messages["cmm.require.select"];
    if( isNull( $scope.getSysStatFgVal()) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 설치포스수를 입력해주세요.
    // var msg = messages["storeManage.installPosCnt"]+messages["cmm.require.text"];
    // if( isNull($("#rInstallPosCnt").val())) {
    //   $scope._popMsg(msg);
    //   return false;
    // }

    // 날씨표시지역을 선택해주세요.
    var msg = messages["storeManage.weatherArea"]+messages["cmm.require.select"];
    if( isNull( $scope.getAreaCd())) {
      $scope._popMsg(msg);
      return false;
    }

    // 사업자번호를 입력해주세요.
    var msg = messages["storeManage.bizNo"]+messages["cmm.require.text"];
    if( isNull($("#bizNo1").val()) || isNull($("#bizNo2").val()) || isNull($("#bizNo3").val())) {
      $scope._popMsg(msg);
      return false;
    }

    // 이전값과 변경하여 변경되었을때, 사업자번호 중복체크를 해주세요. //TODO
    // var msg = messages["storeManage.require.duplicate.bizNo"];
    // if(!isBizChk) {
    //   $scope._popMsg(msg);
    //   return false;
    // }

    // 전화번호를 입력해주세요.
    var msg = messages["storeManage.telNo"]+messages["cmm.require.text"];
    if( isNull($("#telNo").val()) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 주소를 입력해주세요.
    var msg = messages["storeManage.addr"]+messages["cmm.require.text"];
    if( isNull($("#postNo").val()) || isNull($("#addr").val()) || isNull($("#addrDtl").val())) {
      $scope._popMsg(msg);
      return false;
    }

    // 관리업체를 선택해주세요.
    var msg = messages["storeManage.manageVan"]+messages["cmm.require.select"];
    if( isNull($("#manageVanCd").val()) ) {
      $scope._popMsg(msg);
      return false;
    }

    // 대리점를 선택해주세요.
    var msg = messages["storeManage.agency"]+messages["cmm.require.select"];
    if( isNull($("#agencyCd").val()) ) {
      $scope._popMsg(msg);
      return false;
    }
    return true;
  };

  // 저장
  $scope.save = function(){

    //값체크
    if (!valueCheck()) return false;

    var params = {};

    // params.hqOfficeCd      = $("#hqOfficeCd").val();
    // params.storeCd         = $("#storeCd").val();
    // params.storeNm         = $("#storeNm").val();
    // params.openPosDate     = wijmo.Globalize.format($scope.openPosDate.value, 'yyyyMMdd');
    // params.installPosCnt   = $("#installPosCnt").val();
    // params.ownerNm         = $("#ownerNm").val();
    // params.bizStoreNm      = $("#bizStoreNm").val();
    // params.bizNo1          = $("#bizNo1").val();
    // params.bizNo2          = $("#bizNo2").val();
    // params.bizNo3          = $("#bizNo3").val();
    // params.bizNo           = $("#bizNo1").val() + $("#bizNo2").val() + $("#bizNo3").val();
    // params.telNo           = $("#telNo").val();
    // params.faxNo           = $("#faxNo").val();
    // params.emailAddr       = $("#emailAddr").val();
    // params.hmpgAddr        = $("#hmpgAddr").val();
    // params.postNo          = $("#postNo").val();
    // params.addr            = $("#addr").val();
    // params.addrDtl         = $("#addrDtl").val();
    // params.clsFg           = $scope.getClsFgVal();
    // params.areaCd          = $scope.getAreaCdVal();
    // params.sysStatFg       = $scope.getSysStatFgVal();
    // params.vanCd           = $("#manageVanCd").val();
    // params.agencyCd        = $("#agencyCd").val();
    // params.sysOpenDate     = wijmo.Globalize.format($scope.openPosDate.value, 'yyyyMMdd');
    // params.sysRemark       = $("#sysRemark").val();
    // params.hdRemark        = $("#hdRemark").val();
    // params.remark          = $("#remark").val();

    params.bizNo           = $("#bizNo1").val() + $("#bizNo2").val() + $("#bizNo3").val();
    params.sysOpenDate     = wijmo.Globalize.format($scope.openPosDate.value, 'yyyyMMdd');

    // $scope._save("/store/manage/storeManage/storeManage/updateStoreInfo.sb", params, function () {
    //   $scope._popMsg(messages["cmm.saveSucc"]);
    // });

    event.preventDefault();
  };

  // 관리업체 조회
  $scope.searchManageVan = function(){
    var popup = $scope.vanLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var vanScope = agrid.getScope('searchVanCtrl');
      vanScope.$apply(function(){
        vanScope._gridDataInit();
        $("#manageVanCd").val(vanScope.getVan().vanCd);
        $("#manageVanNm").val(vanScope.getVan().vanNm);
      });
    });
    event.preventDefault();
  };

  // 대리점 조회
  $scope.searchAgency = function(){
    var popup = $scope.agencyLayer;

    // 팝업 닫을때
    popup.show(true, function (s) {
      var agencyScope = agrid.getScope('searchAgencyCtrl');
      agencyScope.$apply(function(){
        agencyScope._gridDataInit();
        $("#agencyCd").val(agencyScope.getAgency().agencyCd);
        $("#agencyNm").val(agencyScope.getAgency().agencyNm);
      });
    });
    event.preventDefault();
  };

  // 주소검색 TODO
  $scope.searchAddr = function(){
    event.preventDefault();
  };

  // 매장환경 탭 클릭
  $scope.changeTab = function(){

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
