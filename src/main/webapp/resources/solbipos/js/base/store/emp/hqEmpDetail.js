/****************************************************************
 *
 * 파일명 : hqEmpDetail.js
 * 설  명 : 본사사원정보 상세조회 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.21     김지은      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 본사사원 상세조회
 */
app.controller('hqEmpDetailCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('hqEmpDetailCtrl', $scope, $http, false));

  // 선택된 사원
  $scope.selectedHqEmp;

  // 브랜드사용여부 가 1일때만
  if(userHqBrandYn == "1") {
    $("#trUserHqBrandYnDtl").css("display", "")
  } else {
    $("#trUserHqBrandYnDtl").css("display", "none")
  }

  // [1250 맘스터치]
  // 콤보박스 데이터
  $scope._setComboData("momsTeamDtlCombo", momsTeamComboList); // 추가정보-팀별
  $scope._setComboData("momsAcShopDtlCombo", momsAcShopComboList); // 추가정보-AC점포별
  $scope._setComboData("momsAreaFgDtlCombo", momsAreaFgComboList); // 추가정보-지역구분
  $scope._setComboData("momsCommercialDtlCombo", momsCommercialComboList); // 추가정보-상권
  $scope._setComboData("momsShopTypeDtlCombo", momsShopTypeComboList); // 추가정보-점포유형
  $scope._setComboData("momsStoreManageTypeDtlCombo", momsStoreManageTypeComboList); // 추가정보-매장관리타입

  // _broadcast
  $scope.$on("hqEmpDetailCtrl", function(event, data) {
    $scope.selectedHqEmp = data;
    $scope.getHqEmpList(data);
    event.preventDefault();
  });

  // 본사사원정보관리 그리드 조회
  $scope.getHqEmpList = function(data){

    var params = data;
    $scope._postJSONQuery.withOutPopUp( "/base/store/emp/hq/detail.sb", params, function(response){
      var data             = response.data.data;
      $scope.hqEmp         = data;
      $scope.hqEmp.empInfo = ' [' + data.empNo + ']' + data.empNm;

      if($scope.hqEmp.useYn === "Y") {
        $scope.hqEmp.useYn = "사용";
      } else {
        $scope.hqEmp.useYn = "미사용";
      }
      if($scope.hqEmp.mainSaleFg === "0") {
        $scope.hqEmp.mainSaleFg = "사용";
      } else {
        $scope.hqEmp.mainSaleFg = "미사용";
      }

      // [1250 맘스터치]
      $scope.hqEmp.momsTeam = nvl(data.momsTeam, "");
      $scope.hqEmp.momsAcShop = nvl(data.momsAcShop, "");
      $scope.hqEmp.momsAreaFg = nvl(data.momsAreaFg, "");
      $scope.hqEmp.momsCommercial = nvl(data.momsCommercial, "");
      $scope.hqEmp.momsShopType = nvl(data.momsShopType, "");
      $scope.hqEmp.momsStoreManageType = nvl(data.momsStoreManageType, "");
    });
  };

  // 수정버튼 클릭
  $scope.modify = function(){

    $scope.hqEmpRegistLayer.show(true, function(){
      $scope.getHqEmpList($scope.selectedHqEmp);
      var scope = agrid.getScope('hqEmpRegistCtrl');
      $scope.getHqEmpList($scope.selectedHqEmp);
    });

  };

  // 탭변경
  $scope.changeTab = function() {

        $scope.hqEmpDetailLayer.hide();
        $scope.hqEmpAuthLayer.show(true);

        $scope._broadcast('hqEmpAuthCtrl', $scope.selectedHqEmp);
        event.preventDefault();
  };

  // 닫기버튼 클릭
  $scope.close = function(){
    $scope.hqEmpDetailLayer.hide();
  };

}]);
