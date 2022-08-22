/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : 포스버전관리 > 버전 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.12.31     김지은      1.0           Angular방식으로 변경
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**********************************************************************
 *  회원정보 그리드
 **********************************************************************/
app.controller('verRegistCtrl', ['$scope', '$http', function ($scope, $http) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRegistCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("progFgCombo", progFg);
  $scope._setComboData("useYnCombo", useYn);

  // 버전정보
  $scope.version;

  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  $scope.$on("verRegistCtrl", function(event, data) {

    // POS 버전에 따라 프로그램구분 콤보박스 셋팅
    if(window.location.href.indexOf("verInfo/view.sb") > 0){
      $scope.versionProgFgCombo.selectedValue = "1"; // [1] NXPOS_V1;
    }else{
      $scope.versionProgFgCombo.selectedValue = "2"; // [2] NXPOS_V2;
    }

    if(data !== undefined && !isEmptyObject(data)) {

      // 정보 셋팅
      $scope.setSelectVersion(data);

      // 버전 정보 조회
      $scope.getVersionInfo();

      // UI 셋팅
      $("#btnReg").css("display", "none");
      $("#btnMod").css("display", "");
      $("#verSerNo").attr("disabled", true);
      $("#verSerNo").css('background-color', '#F0F0F0');

    }else{

      // UI 셋팅
      $("#btnReg").css("display", "");
      $("#btnMod").css("display", "none");
      $("#verSerNo").attr("disabled", false);
      $("#verSerNo").css('background-color', '#FFFFFF');
    }

    // 프로그램구분은 수정 못하게 처리
    $("#progFg").attr("disabled", true);
    $("#progFg").css('background-color', '#F0F0F0');
    
    event.preventDefault();
  });

  // 파일업로드시 파일사이즈 변경
  $scope.uploadChange = function(){
    $scope.$apply(function() {
      var fileSize = document.getElementById("file").files[0].size;
      $scope.version.fileSize = fileSize;
    });
  };

  // 입력양식 체크
  $scope.chkForm = function(){

    // 버전일련번호 체크
    if($("#verSerNo").val() === null || $("#verSerNo").val() === undefined || $("#verSerNo").val() === "") {
      $scope._popMsg(messages["verManage.verSerNo"] + " " + messages["cmm.require.text"]);
      return;
    }

    // 버전적용명 체크
    if($("#verSerNm").val() === null || $("#verSerNm").val() === undefined || $("#verSerNm").val() === "") {
      $scope._popMsg(messages["verManage.verSerNm"] + " " + messages["cmm.require.text"]);
      return;
    }

    // 이미지명 형식 체크
    if($("#file").val() !== null && $("#file").val() !== undefined && $("#file").val() !== "") {
      var imgFullNm = $("#file").val().substring($("#file").val().lastIndexOf('\\') + 1);
      if (1 > imgFullNm.lastIndexOf('.')) {
        $scope._popMsg(messages["verManage.fileNmChk.msg"]);
        return;
      }
    }

    // 신규등록은 버전일련번호 중복체크 후 저장
    if(isEmptyObject($scope.getSelectVersion())){

      var params = {};
      params.verSerNo = $scope.version.verSerNo;

      $scope._postJSONQuery.withOutPopUp('/pos/confg/verManage/verInfo/chkVerSerNo.sb', params, function (response) {
          var res = response.data.data;
          if(res > 0) {
            $scope._popMsg(messages["verManage.verSerNoChk.msg"]);
            return false;
          }else{
            $scope.regist();
          }
      });
    }else{
      $scope.regist();
    }
  };

  // 저장
  $scope.regist = function(){

    var formData = new FormData($("#regForm")[0]);

    if($scope.version.fileDesc === null || $scope.version.fileDesc === undefined || $scope.version.fileDesc === ""){
      $scope.version.fileDesc = "";
    }

    formData.append("verSerNo", $scope.version.verSerNo);
    formData.append("verSerNm", $scope.version.verSerNm);
    formData.append("fileSize", $scope.version.fileSize);
    formData.append("fileDesc", $scope.version.fileDesc);
    formData.append("progFg", $scope.versionProgFgCombo.selectedValue);
    formData.append("pgmYn", $("#pgm").is(":checked") === true ? 'Y' : 'N');
    formData.append("dbYn",  $("#db").is(":checked") === true ? 'Y' : 'N');
    formData.append("imgYn", $("#img").is(":checked") === true ? 'Y' : 'N');
    formData.append("useYn", $scope.versionUseYnCombo.selectedValue);

    var url = '';

    if( isEmptyObject($scope.getSelectVersion()) ) {
      url = '/pos/confg/verManage/verInfo/regist.sb';
    } else {
      url = '/pos/confg/verManage/verInfo/modify.sb';
    }

    $scope.$broadcast('loadingPopupActive');

    $.ajax({
      url: url,
      type: "POST",
      data: formData,
      processData: false,
      contentType: false,
      cache: false,
      success: function(result) {
        // console.log('save result', result);

        if (result.status === "OK") {
          if( isEmptyObject($scope.getSelectVersion()) ) {
            $scope._popMsg("등록되었습니다.");
          } else {
            $scope._popMsg("저장되었습니다.");
          }

          $scope.$broadcast('loadingPopupInactive');
          $scope.close();
        }
        else if (result.status === "FAIL") {
          $scope._popMsg('Ajax Fail By HTTP Request');
          $scope.$broadcast('loadingPopupInactive');
        }
        else if (result.status === "SERVER_ERROR") {
          $scope._popMsg(result.message);
          $scope.$broadcast('loadingPopupInactive');
        }
        /*else if(result.status === undefined) {
          location.href = "/";
        }*/
        else {
          var msg = result.status + " : " + result.message;
          $scope._popMsg(msg);
          $scope.$broadcast('loadingPopupInactive');
        }
      },
      error : function(result){
        $scope._popMsg("error");
        $scope.$broadcast('loadingPopupInactive');
      }
    },function(){
      $scope._popMsg("Ajax Fail By HTTP Request");
      $scope.$broadcast('loadingPopupInactive');
    });
  };

  // 버전 정보 조회
  $scope.getVersionInfo = function(){

    var params = $scope.getSelectVersion();
    console.log('params' , params);

    $scope._postJSONQuery.withOutPopUp( "/pos/confg/verManage/verInfo/dtlInfo.sb", params, function(response){

      console.log('response',response);

      var data = response.data.data;
      $scope.version = data;

      // 포함내역
      if(data.pgmYn === 'Y'){
        $scope.version.pgm = true;
        $("#pgm").prop("checked", true);
      }
      if(data.dbYn === 'Y'){
        $scope.version.db = true;
        $("#db").prop("checked", true);
      }
      if(data.imgYn === 'Y'){
        $scope.version.img = true;
        $("#img").prop("checked", true);
      }

    });
  };

  // 닫기
  $scope.closePop = function(){
    // 초기화
    $scope.setSelectVersion(null);

    $scope.versionRegistLayer.hide();
    console.log("closePop");
  };

}]);

