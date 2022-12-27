/****************************************************************
 *
 * 파일명 : allergy.js
 * 설  명 : 원산지 탭 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.12.23     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */

var app = agrid.getApp();

/** 그리드 생성 */
app.controller('allergyCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('allergyCtrl', $scope, $http, false));

  $scope._setComboData("prodHqBrandCdCombo", momsHqBrandCdComboList); // 상품브랜드

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("allergyCtrl", function(event, data) {
    $scope.searchAllergyList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 옵션 목록 조회
  $scope.searchAllergyList = function(){
    var params = {};
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodCds = $("#allergyProdSelectCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
    // '전체' 일때
    if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }
    params.listScale = 500;
    console.log(params);

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodInfoSearch/allergy/getAllergyList.sb", params);
  };

  // 매장선택 모듈 팝업 사용시 정의
  // 함수명 : 모듈에 넘기는 파라미터의 targetId + 'Show'
  // _broadcast : 모듈에 넘기는 파라미터의 targetId + 'Ctrl'
  $scope.allergyProdSelectShow = function () {
    $scope._broadcast('allergyProdSelectCtrl');
  };

  // 상품분류정보 팝업
  $scope.popUpProdClass = function() {
    var popUp = $scope.prodClassPopUpLayer;
    popUp.show(true, function (s) {
      // 선택 버튼 눌렀을때만
      if (s.dialogResult === "wj-hide-apply") {
        var scope = agrid.getScope('prodClassPopUpCtrl');
        var prodClassCd = scope.getSelectedClass();
        var params = {};
        params.prodClassCd = prodClassCd;
        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._postJSONQuery.withPopUp("/popup/getProdClassCdNm.sb", params,
            function(response){
              $scope.prodClassCd = prodClassCd;
              $scope.prodClassNm = response.data.data;
            }
        );
      }
    });
  };

  // 상품분류정보 선택취소
  $scope.delProdClass = function(){
    $scope.prodClassCd = "";
    $scope.prodClassNm = "";
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    params.prodClassCd = $scope.prodClassCd;
    params.prodCd = $scope.prodCd;
    params.prodNm = $scope.prodNm;
    params.prodCds = $("#orgplceProdSelectCd").val();
    params.prodHqBrandCd = $scope.prodHqBrandCd;
    // '전체' 일때
    if(params.prodHqBrandCd === "" || params.prodHqBrandCd === null) {
      var momsHqBrandCd = "";
      for(var i=0; i < momsHqBrandCdComboList.length; i++){
        if(momsHqBrandCdComboList[i].value !== null) {
          momsHqBrandCd += momsHqBrandCdComboList[i].value + ","
        }
      }
      params.userBrands = momsHqBrandCd;
    }

    $scope._broadcast('allergyExcelCtrl', params);
  };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('allergyExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('allergyExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("allergyExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prodInfoSearch/allergy/getAllergyExcelList.sb", params, function() {
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
        }, messages["prodInfoSearch.allergy"] + getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);