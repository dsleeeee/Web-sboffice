/****************************************************************
 *
 * 파일명 : touchKey.js
 * 설  명 : 판매터치키 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.01.11     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

/** 그리드 생성 */
app.controller('touchKeyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('touchKeyCtrl', $scope, $http, false));

  //터치키 그룹코드 콤보
  $scope._setComboData("touchKeyGrpCombo", touchKeyGrpData);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("touchKeyCtrl", function(event, data) {
    $scope.searchTouchKeyList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 사이드-속성 목록 조회
  $scope.searchTouchKeyList = function(){
    var params = {};
    params.tukeyGrpCd = $scope.touchKeyGrp;
    params.listScale = 500;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodInfoSearch/touchKey/getTouchKeyList.sb", params);
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.tukeyGrpCd = $scope.touchKeyGrp;

    $scope._broadcast('touchKeyExcelCtrl', params);
  };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('touchKeyExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('touchKeyExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("touchKeyExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prodInfoSearch/touchKey/getTouchKeyExcelList.sb", params, function() {
      if ($scope.excelFlex.rows.length <= 0) {
        $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
        return false;
      }

      $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
      $timeout(function () {
        wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.excelFlex, {
          includeColumnHeaders: true,
          includeCellStyles   : true,
          includeColumns      : function (column) {
            return column.visible;
          }
        }, messages["prodInfoSearch.touchKey"] + getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);