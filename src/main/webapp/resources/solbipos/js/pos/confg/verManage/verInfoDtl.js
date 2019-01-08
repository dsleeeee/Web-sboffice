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
app.controller('verDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('verDetailCtrl', $scope, $http, true));

  // 콤보박스 데이터
  $scope._setComboData("progFgCombo", progFg);
  $scope._setComboData("useYnCombo", useYn);

  // 버전정보
  $scope.version;

  // 조회 버튼 클릭
  $scope.$on("verDetailCtrl", function(event, data) {
    $scope.getVersionInfo();
    event.preventDefault();
  });

  // 버전 목록 조회
  $scope.getVersionInfo = function(){
    var params = {};

    var scope = agrid.getScope('verManageCtrl');
    params    = scope.getSelectVersion();

    console.log('params' , params);

    $scope._postJSONQuery.withOutPopUp( "/pos/confg/verManage/verInfo/dtlInfo.sb", params, function(response){

      console.log('response',response);

      var data = response.data.data;
      $scope.version = data;

      // 포함내역
      var incldDtlsStr = '';
      if(data.pgmYn) incldDtlsStr += 'PGM';
      if(data.dbYn) {
        if(incldDtlsStr === '') {
          incldDtlsStr += 'DB';
        } else {
          incldDtlsStr += ' / DB';
        }
      }
      if(data.imgYn) {
        if(incldDtlsStr === '') {
          incldDtlsStr += 'IMAGE';
        } else {
          incldDtlsStr += ' / IMAGE';
        }
      }
      $scope.version.incldDtls = incldDtlsStr;
    });
    // $scope._inquiryMain("/pos/confg/verManage/verInfo/dtlInfo.sb", params, function() {
    // });
  };

  // 닫기
  $scope.close = function(){
    $scope.versionInfoDetailLayer.hide();
  };

  // 탭변경
  $scope.changeTab = function(){
    console.log('changeTab');
    $scope.versionInfoDetailLayer.hide();
    $scope.storeAddLayer.show(true);
  };


  //todo 등록매장수를 상세화면이나 리스트에 추가
}]);

