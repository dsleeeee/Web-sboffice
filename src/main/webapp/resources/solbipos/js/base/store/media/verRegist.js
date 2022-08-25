/****************************************************************
 *
 * 파일명 : verInfoDtl.js
 * 설  명 : 미디어관리 > 버전 상세보기 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2021.06.09     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 파일타입
var verRegistFileTypeComboData = [
  {"name":"POS 듀얼모니터(광고)","value":"001"},
  {"name":"로고","value":"002"},
  {"name":"키오스크(인트로)","value":"003"},
  {"name":"DID","value":"004"},
  {"name":"POS테이블 바탕화면","value":"005"},
  {"name":"로그인로고","value":"006"},
  {"name":"POS (인트로)","value":"007"}
];

app.controller('verRegistCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verRegistCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("useYnCombo", useYnData);
  $scope._setComboData("fileTypeCombo", verRegistFileTypeComboData);

  // 등록일자 셋팅
  var startDate = wcombo.genDateVal("#startDate", gvStartDate);
  var endDate = wcombo.genDateVal("#endDate", gvEndDate);

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

  // 파일타입
  $scope.fileType;
  $scope.setFileType = function(ver){
    $scope.fileType = ver;
  };
  $scope.getFileType = function(){
    return $scope.fileType;
  };

  // 파일업로드시 파일사이즈 변경
  $scope.maxDate = function(){
    endDate.value = new Date('2099-12-31');
  };

  // 조회 버튼 클릭
  $scope.$on("verRegistCtrl", function(event, data) {

    $scope.setSelectVersion(data);

    if( !isEmptyObject($scope.getSelectVersion()) ) {
      $scope.isEdit = true;
      $scope.getVersionInfo();
    } else {
      $scope.versionUseYnCombo.selectedValue = "Y";
    }
    event.preventDefault();
  });

  // 파일업로드시 파일사이즈 변경
  $scope.uploadChange = function(){
    $scope.$apply(function() {
      var maxSize = 20 * 1024 * 1024;
      var fileSize = document.getElementById("file").files[0].size;

      if(fileSize > maxSize) {
        alert("첨부파일 사이즈는 20MB 이내로 등록 가능합니다.    ");

        // 첨부파일 리셋
        var agent = navigator.userAgent.toLowerCase();
        if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
          // ie 일때
          $("#file").replaceWith( $("#file").clone(true) );
          $scope.version.uploadFile = "";
        } else {
          // other browser 일때
          $("#file").val("");
          $scope.version.uploadFile = "";
        }
        $scope.version.fileSize = "";

        return false;
      } else {
        $scope.version.fileSize = fileSize;
      }
    });
  };

  // 입력 값 체크
  $scope.mediaSave = function (){
    if(Number(wijmo.Globalize.format(startDate.value, 'yyyyMMdd')) > Number(wijmo.Globalize.format(endDate.value, 'yyyyMMdd'))){
      $scope._popMsg(messages["media.date.msg"]);
      return;
    }

    // 버전적용명 체크
    if($("#verSerNm").val() === null || $("#verSerNm").val() === undefined || $("#verSerNm").val() === "") {
      $scope._popMsg(messages["media.verSerNm"] + " " + messages["cmm.require.text"]);
      return;
    }


    if( isEmptyObject($scope.getSelectVersion()) ) { // 신규등록
      // 파일 체크
      if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
        $scope._popMsg(messages["media.file"] + " " + messages["cmm.require.text"]);
        return;
      }
      // else {
      //   var type = '(.*?)\\.(' + $scope.getFileType().replace(",","|").replace(".","") + ')$';
      //   console.log(type);
      //   // 확장자 체크
      //   var reg = new RegExp(type);
      //   if(!$("#file").val().match(reg)) {
      //     $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
      //     return;
      //   }
      // }
    } else{ // 수정
      if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
      }
      // else {
      //   var type = '(.*?)\\.(' + $scope.getFileType().replace(",","|").replace(".","") + ')$';
      //   // 확장자 체크
      //   var reg = new RegExp(type);
      //   if(!$("#file").val().match(reg)) {
      //     $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
      //     return;
      //   }
      // }
    }
    $scope.chkRegist();
  }

  // 날짜 중복여부 체크
  $scope.chkRegist = function(){

    // 날짜 체크
    var param = {};
    param.storeCd = storeCd;
    param.startDate = wijmo.Globalize.format(startDate.value, 'yyyyMMdd');
    param.endDate =  wijmo.Globalize.format(endDate.value, 'yyyyMMdd');
    if( isEmptyObject($scope.getSelectVersion()) ) {
      param.verSerNo = '0';
    } else {
      param.verSerNo = $scope.version.verSerNo;
    }
    param.fileType = $scope.versionFileTypeCombo.selectedValue;
    param.useYn = $scope.versionUseYnCombo.selectedValue;

    $.postJSON("/base/store/media/chkDate.sb", param, function(result) {
          $scope.$broadcast('loadingPopupActive');
          if(result.status === 'OK') {
            $scope.regist();
          }
        },
        function(result) {
          $scope._popMsg($scope.versionFileTypeCombo.text + messages["media.chkDate.msg"]);
        });
  };

  // 파일 타입 변경 시 첨부파일 확장자 변경
  $scope.fileTypeChg = function(s){
    if($("#file").val() === null || $("#file").val() === undefined || $("#file").val() === "") {
    } else {
      // 첨부파일 리셋
      var agent = navigator.userAgent.toLowerCase();
      if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
        // ie 일때
        $("#file").replaceWith( $("#file").clone(true) );
        // media.fileSize.max
        $scope.version.uploadFile = "";
      } else {
        // other browser 일때
        $("#file").val("");
        $scope.version.uploadFile = "";
      }
    }

    var param = {};
    param.fileType = $scope.versionFileTypeCombo.selectedValue;
    $.ajax({
      url:"/base/store/media/getFileType.sb",
      type:"POST",
      data:param,
      success:function(result){
        $scope.setFileType(result);
        $("#file").attr("accept",result);

      }
    });

  }

  // 저장
  $scope.regist = function(){
    var formData = new FormData($("#regForm")[0]);

    formData.append("orgnCd", orgnCd);
    if(orgnFg === "HQ"){
      formData.append("hqOfficeCd", hqOfficeCd);
    } else if(orgnFg === "STORE") {
      formData.append("hqOfficeCd", "");
      formData.append("storeCd", storeCd);
    }
    formData.append("userId", userId);
    formData.append("verSerNo", $scope.version.verSerNo);
    formData.append("verSerNm", $scope.version.verSerNm);
    formData.append("startDate", wijmo.Globalize.format(startDate.value, 'yyyyMMdd'));
    formData.append("endDate", wijmo.Globalize.format(endDate.value, 'yyyyMMdd'));
    formData.append("fileType", $scope.versionFileTypeCombo.selectedValue);
    formData.append("fileSize", $scope.version.fileSize);
    formData.append("useYn", $scope.versionUseYnCombo.selectedValue);


    var url = '';

    if( isEmptyObject($scope.getSelectVersion()) ) {
      url = '/base/store/media/verInfo/regist.sb';
    } else {
      url = '/base/store/media/verInfo/modify.sb';
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
          $scope._popMsg(messages["media.fileChk.msg"] + $scope.getFileType() + messages["media.fileChk.msg2"]);
          $scope.$broadcast('loadingPopupInactive');
        }
        else if (result.status === "SERVER_ERROR") {
          $scope._popMsg(result.message);
          $scope.$broadcast('loadingPopupInactive');
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

    $scope._postJSONQuery.withOutPopUp( "/base/store/media/verInfo/dtlInfo.sb", params, function(response){

      console.log('response',response);

      var data = response.data.data;
      $scope.version = data;

      startDate.value = getFormatDate($scope.version.startDate, '-');
      endDate.value = getFormatDate($scope.version.endDate, '-');

      $("#fileIn").attr("colspan", 1);
      $("#fileOrgH").show();
      $("#fileOrgD").show();
    });
  };

  // 닫기
  $scope.close = function(){
    endDate.value = new Date(Date.now());

    // 첨부파일 리셋
    var agent = navigator.userAgent.toLowerCase();
    if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ){
      // ie 일때
      $("#file").replaceWith( $("#file").clone(true) );
      $scope.version.uploadFile = "";
    } else {
      // other browser 일때
      $("#file").val("");
      $scope.version.uploadFile = "";
    }

    $("#fileIn").attr("colspan", 3);
    $("#fileOrgH").hide();
    $("#fileOrgD").hide();

    var scope  = agrid.getScope('mediaCtrl');
    scope._broadcast('mediaCtrl');
    $scope.versionRegistLayer.hide();
  };

}]);

