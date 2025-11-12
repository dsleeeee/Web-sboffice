/****************************************************************
 *
 * 파일명 : sideMenuAttr.js
 * 설  명 : 사이드메뉴>속성 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2018.11.14     노현수      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 사이드메뉴 그리드 생성
 */
app.controller('sideMenuCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('sideMenuCtrl', $scope, $http, false));
  // 속성 탭
  $scope.isAttrTab = false;
  // 선택메뉴 탭
  $scope.isSelectMenuTab = true;
  // 선택메뉴(싱글) 탭
  $scope.isSelectMenuSingleTab = true;
  // 옵션메뉴(배달) 탭
  $scope.isOptionMenuDlvrTab = true;
  // 사이드메뉴관리 탭
  $scope.isManageTab = true;
  // 탭변경
  $scope.changeTab = function(type){
    // 속성 탭
    if ( type === 'A' ) {
      $("#sideMenuAttr").addClass("on");
      $("#sideMenuSelectMenu").removeClass("on");
      $("#sideMenuSelectMenuSingle").removeClass("on");
      $("#optionMenuDlvr").removeClass("on");
      $("#sideMenuManage").removeClass("on");
      $scope.isAttrTab = false;
      $scope.isSelectMenuTab = true;
      $scope.isSelectMenuSingleTab = true;
      $scope.isOptionMenuDlvrTab = true;
      $scope.isManageTab = true;
      // 속성 조회
      $scope._broadcast("sideMenuAttrClassCtrl");
    // 선택메뉴 탭
    } else if ( type === 'C' ) {
      $("#sideMenuAttr").removeClass("on");
      $("#sideMenuSelectMenu").addClass("on");
      $("#sideMenuSelectMenuSingle").removeClass("on");
      $("#optionMenuDlvr").removeClass("on");
      $("#sideMenuManage").removeClass("on");
      $scope.isAttrTab = true;
      $scope.isSelectMenuTab = false;
      $scope.isSelectMenuSingleTab = true;
      $scope.isOptionMenuDlvrTab = true;
      $scope.isManageTab = true;
      // 선택그룹 조회
      $scope._broadcast("sideMenuSelectGroupCtrl");
      // 그리드 refresh
      setTimeout(function () {
        $scope._broadcast("selectMenuRefresh");
      }, 10);
    // 선택메뉴(싱글) 탭
    } else if ( type === 'S' ) {
      $("#sideMenuAttr").removeClass("on");
      $("#sideMenuSelectMenu").removeClass("on");
      $("#sideMenuSelectMenuSingle").addClass("on");
      $("#optionMenuDlvr").removeClass("on");
      $("#sideMenuManage").removeClass("on");
      $scope.isAttrTab = true;
      $scope.isSelectMenuTab = true;
      $scope.isSelectMenuSingleTab = false;
      $scope.isOptionMenuDlvrTab = true;
      $scope.isManageTab = true;
      // 선택그룹(싱글) 조회
      $scope._broadcast("sideMenuSelectGroupSingleCtrl");
      // 그리드 refresh
      setTimeout(function () {
        $scope._broadcast("selectMenuSingleRefresh");
      }, 10);
    // 옵션메뉴(배달) 탭
    }else if( type === 'O' ){
      $("#sideMenuAttr").removeClass("on");
      $("#sideMenuSelectMenu").removeClass("on");
      $("#sideMenuSelectMenuSingle").removeClass("on");
      $("#optionMenuDlvr").addClass("on");
      $("#sideMenuManage").removeClass("on");
      $scope.isAttrTab = true;
      $scope.isSelectMenuTab = true;
      $scope.isSelectMenuSingleTab = true;
      $scope.isOptionMenuDlvrTab = false;
      $scope.isManageTab = true;
      // 선택그룹 조회
      $scope._broadcast("optionMenuDlvrSelectGroupCtrl");
      // 그리드 refresh
      setTimeout(function () {
      $scope._broadcast("optionMenuDlvrRefresh");
      }, 10);
    // 사이드메뉴관리 탭
    }else if ( type === 'M' ) {
      $("#sideMenuAttr").removeClass("on");
      $("#sideMenuSelectMenu").removeClass("on");
      $("#sideMenuSelectMenuSingle").removeClass("on");
      $("#optionMenuDlvr").removeClass("on");
      $("#sideMenuManage").addClass("on");
      $scope.isAttrTab = true;
      $scope.isSelectMenuTab = true;
      $scope.isSelectMenuSingleTab = true;
      $scope.isOptionMenuDlvrTab = true;
      $scope.isManageTab = false;
      // 사이드메뉴관리 조회
      $scope._broadcast("sideMenuManageCtrl");
    }
  };
  // 탭 조회
  $scope.queryTab = function() {


    if (!$scope.isAttrTab) {

      $("#sideMenuAttrTitle").html("");
      var attrScope = agrid.getScope('sideMenuAttrAttrCtrl');
      attrScope._gridDataInit();   // 속성 그리드 초기화

      // 속성분류 조회
      $scope._broadcast("sideMenuAttrClassCtrl");

    } else if (!$scope.isSelectMenuTab) {

      $("#sideSelectGroupTitle").html("");
      var attrScope = agrid.getScope('sideMenuSelectClassCtrl');
      attrScope._gridDataInit();   // 선택분류 그리드 초기화

      $("#sideClassTitle").html("");
      var prodScope = agrid.getScope('sideMenuSelectProdCtrl');
      prodScope._gridDataInit();   // 선택상품 그리드 초기화

      // 선택그룹 조회
      $scope._broadcast("sideMenuSelectGroupCtrl");

    } else if (!$scope.isSelectMenuSingleTab) {

      $("#sideSelectGroupSingleTitle").html("");
      var attrScope = agrid.getScope('sideMenuSelectClassSingleCtrl');
      attrScope._gridDataInit();   // 선택분류(싱글) 그리드 초기화

      $("#sideClassSingleTitle").html("");
      var prodScope = agrid.getScope('sideMenuSelectProdSingleCtrl');
      prodScope._gridDataInit();   // 선택상품(싱글) 그리드 초기화

      // 선택그룹(싱글) 조회
      $scope._broadcast("sideMenuSelectGroupSingleCtrl");

    } else if (!$scope.isOptionMenuDlvrTab) {

        $("#optionMenuDlvrSelectGroupTitle").html("");
        var attrScope = agrid.getScope('optionMenuDlvrSelectClassCtrl');
        attrScope._gridDataInit();   // 옵션메뉴(배달) 그리드 초기화

        $("#optionMenuDlvrSelectClassTitle").html("");
        var prodScope = agrid.getScope('optionMenuDlvrSelectProdCtrl');
        prodScope._gridDataInit();   // 옵션메뉴(배달) 그리드 초기화

        // 옵션메뉴(배달) 조회
        $scope._broadcast("optionMenuDlvrSelectGroupCtrl");

    } else if (!$scope.isManageTab) {

      // 리스트 재조회
      var manageScope = agrid.getScope('sideMenuManageCtrl');
      manageScope.searchSideMenuManage();
    }
  };

}]);

