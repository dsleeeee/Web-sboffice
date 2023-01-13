/****************************************************************
 *
 * 파일명 : kioskKeyMap.js
 * 설  명 : 키오스크키맵 탭 JavaScript
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
app.controller('kioskKeyMapCtrl', ['$scope', '$http', function ($scope, $http) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kioskKeyMapCtrl', $scope, $http, false));

  $scope._setComboData("posNo", kioskPosList); // 키오스크용 포스 목록
  $scope._setComboData("tuClsType", kioskTuClsTypeList); // 키오스크용 키맵그룹 목록

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("kioskKeyMapCtrl", function(event, data) {
    $scope.searchKioskKeyMapList();
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 사이드-속성 목록 조회
  $scope.searchKioskKeyMapList = function(){
    var params = {};
    if(orgnFg === "STORE"){
      params.posNo = $scope.posNo;
    }
    params.tuClsType = $scope.tuClsType;
    params.listScale = 500;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수, 팝업결과표시여부
    $scope._inquiryMain("/base/prod/prodInfoSearch/kioskKeyMap/getKioskKeyMapList.sb", params);
  };

  // 매장권한) POS번호 선택 시, 키맵그룹 dropdown 조회
  $scope.setTuClsType = function (s) {

    // 키맵그룹 dropdown 재조회
    $scope.setTuClsDropdownList("F");
  }

  // 키맵그룹 dropdownLIst 재조회
  $scope.setTuClsDropdownList = function (type){

    // 키맵그룹 dropdown 재조회
    var newGrp = 0; // 새로 생성된 그룹의 index 번호(dropdown 셋팅을 위해)
    var url = '/base/prod/kioskKeyMap/kioskKeyMap/getKioskTuClsTypeList.sb';
    var params = {};
    if(orgnFg === "STORE") {params.posNo = $scope.posNoCombo.selectedValue;}

    //가상로그인 session 설정
    if(document.getElementsByName('sessionId')[0]){
      params['sid'] = document.getElementsByName('sessionId')[0].value;
    }

    // ajax 통신 설정
    $http({
      method : 'POST', //방식
      url    : url, /* 통신할 URL */
      params : params, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback(response) {
      if ($scope._httpStatusCheck(response, true)) {
        if (!$.isEmptyObject(response.data.data.list)) {
          var list = response.data.data.list;
          var comboArray = [];
          var comboData  = {};

          comboArray.unshift({name: "전체", value: ""});

          for (var i = 0; i < list.length; i++) {
            comboData = {};
            comboData.name  = list[i].name;
            comboData.value = list[i].value;
            comboArray.push(comboData);
          }

          $scope._setComboData("tuClsType", comboArray);
          if(orgnFg === "HQ") {
            $scope._setComboData("applyTuClsType", comboArray);
            $scope._setComboData("envTuClsType", comboArray);
          }
          if(orgnFg === "STORE"){
            $scope._setComboData("envStoreTuClsType", comboArray);
          }

          if(type === "F"){
            newGrp = 0;
          }else if (type === "L"){
            newGrp = list.length - 1;
          }
        }
      }
    }, function errorCallback(response) {
      $scope._popMsg(messages["cmm.error"]);
      return false;
    }).then(function () {
      $timeout(function () {

        // 신규그룹추가 후, 새 그룹으로 dropdownLIst 와 grid 셋팅
        $scope.setNewTuClsType(newGrp);

      }, 10);
    });
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {
    var params = {};
    if(orgnFg === "STORE"){
      params.posNo = $scope.posNo;
    }
    params.tuClsType = $scope.tuClsType;

    $scope._broadcast('kioskKeyMapExcelCtrl', params);
  };

}]);

/**
 *  엑셀다운로드 그리드 생성
 */
app.controller('kioskKeyMapExcelCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('kioskKeyMapExcelCtrl', $scope, $http, false));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
  };

  // 다른 컨트롤러의 broadcast 받기
  $scope.$on("kioskKeyMapExcelCtrl", function (event, data) {
    $scope.searchExcelList(data);
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 엑셀 리스트 조회
  $scope.searchExcelList = function (params) {
    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquiryMain("/base/prod/prodInfoSearch/kioskKeyMap/getKioskKeyMapExcelList.sb", params, function() {
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
        }, messages["prodInfoSearch.kioskKeyMap"] + getCurDateTime()+'.xlsx', function () {
          $timeout(function () {
            $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
          }, 10);
        });
      }, 10);
    });
  };

}]);