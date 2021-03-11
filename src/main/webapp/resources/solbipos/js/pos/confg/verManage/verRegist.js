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

  $scope.isEdit = false;

  // 버전정보
  $scope.version;

  $scope.selectVersion;
  $scope.setSelectVersion = function(ver){
    $scope.selectVersion = ver;
  };
  $scope.getSelectVersion = function(){
    return $scope.selectVersion;
  };

  // 조회 버튼 클릭
  $scope.$on("verRegistCtrl", function(event, data) {

    $scope.setSelectVersion(data);

    if( !isEmptyObject($scope.getSelectVersion()) ) {
      $scope.isEdit = true;
      $scope.getVersionInfo();
    }

      event.preventDefault();
  });

  // 파일업로드시 파일사이즈 변경
  $scope.uploadChange = function(){
    $scope.$apply(function() {
      var fileSize = document.getElementById("file").files[0].size;
      $scope.version.fileSize = fileSize;
    });
  };

  // 저장
  $scope.regist = function(){

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

    var formData = new FormData($("#regForm")[0]);

    formData.append("verSerNo", $scope.version.verSerNo);
    formData.append("verSerNm", $scope.version.verSerNo);
    formData.append("fileSize", $scope.version.fileSize);
    formData.append("fileDesc", $scope.version.fileDesc === undefined ? "" : $scope.version.fileDesc);
    formData.append("progFg", $scope.versionProgFgCombo.selectedValue);
    formData.append("pgmYn", $('input:checkbox[id="pgm"]').is(":checked"));
    formData.append("dbYn",  $('input:checkbox[id="db"]').is(":checked"));
    formData.append("imgYn", $('input:checkbox[id="img"]').is(":checked"));
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
        else if(result.status === undefined) {
          location.href = "/";
        }
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

  // 버전 목록 조회
  $scope.getVersionInfo = function(){
    var params = {};
    params    = $scope.getSelectVersion();

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
  $scope.close = function(){
    $scope.versionRegistLayer.hide();
  };


}]);

