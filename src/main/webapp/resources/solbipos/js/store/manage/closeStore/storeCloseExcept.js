/****************************************************************
 *
 * 파일명 : storeCloseExcept.js
 * 설  명 : 폐점제외매장 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2023.04.07     권지현      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 * 폐점제외매장 그리드 생성
 */
app.controller('storeCloseExceptCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeCloseExceptCtrl', $scope, $http, true));

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vanCdDataMap = new wijmo.grid.DataMap(vanComboList, 'value', 'name');
  };

  // 대표명칭 그리드 조회
  $scope.$on('storeCloseExceptCtrl', function(event, data) {

    var params = {};
    params.vanCd = $scope.vanCdCombo.selectedValue;
    params.sysStatFg = $scope.statFgCombo.selectedValue;
    params.agencyCd = $scope.agencyCd;
    params.agencyNm = $scope.agencyNm;
    params.hqOfficeCd = $scope.hqOfficeCd;
    params.hqOfficeNm = $scope.hqOfficeNm;
    params.storeCd = $scope.storeCd;
    params.storeNm = $scope.storeNm;

    // 조회 수행 : 조회URL, 파라미터, 콜백함수
    $scope._inquirySub("/store/manage/closeStore/storeCloseExcept/getStoreCloseExceptList.sb", params, function() {
      $scope._broadcast('storeCtrl');
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 삭제
  $scope.delete = function() {
    // 파라미터 설정
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        params.push($scope.flex.collectionView.items[i]);
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/store/manage/closeStore/storeCloseExcept/deleteStoreCloseExcept.sb", params, function() {
      $scope._broadcast('storeCloseExceptCtrl');
    });
  };

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, messages["storeCloseExcept.storeCloseExcept"] + "_" + messages["storeCloseExcept.storeCloseExcept"] + "_" + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
  // <-- //엑셀다운로드 -->
}]);

/**
 * 매장 그리드 생성
 */
app.controller('storeCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('storeCtrl', $scope, $http, false));

  $scope._setComboData("srchVanCd", vanComboList);
  $scope._setComboData("srchStatFg", sysStatFg);

  // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    $scope.vanCdDataMap = new wijmo.grid.DataMap(vanComboList, 'value', 'name');

    // 비고 작성시 체크
    s.cellEditEnded.addHandler(function (s, e) {
      if (e.panel === s.cells) {
        var col = s.columns[e.col];
        var item = s.rows[e.row].dataItem;
        if (col.binding === "remark") {
          item.gChk = true;
        }
      }
      s.collectionView.commitEdit();
    });
  };

  // 매장 그리드 조회
  $scope.$on("storeCtrl", function(event, data) {
    var params = {};
    params.vanCd = $scope.vanCdCombo.selectedValue;
    params.sysStatFg = $scope.statFgCombo.selectedValue;
    params.agencyCd = $scope.agencyCd;
    params.agencyNm = $scope.agencyNm;
    params.hqOfficeCd = $scope.hqOfficeCd;
    params.hqOfficeNm = $scope.hqOfficeNm;
    params.storeCd = $scope.storeCd;
    params.storeNm = $scope.storeNm;
    // 조회URL, 파라미터, 콜백함수 형태로 조회함수 호출
    $scope._inquirySub("/store/manage/closeStore/storeCloseExcept/getStoreList.sb", params, function() {
    });
    // 기능수행 종료 : 반드시 추가
    event.preventDefault();
  });

  // 저장
  $scope.save = function() {
    // 파라미터 설정
    var params = [];
    for (var i = 0; i < $scope.flex.collectionView.items.length; i++) {
      if($scope.flex.collectionView.items[i].gChk) {
        if($scope.maxChk($scope.flex.collectionView.items[i].remark)){
          params.push($scope.flex.collectionView.items[i]);
        } else {
          $scope._popMsg(messages["storeCloseExcept.remarkChk"]);
          return false;
        }
      }
    }
    // 저장기능 수행 : 저장URL, 파라미터, 콜백함수
    $scope._save("/store/manage/closeStore/storeCloseExcept/saveStoreCloseExcept.sb", params, function() {
      $scope._broadcast('storeCloseExceptCtrl');
    });
  };

  // MAX값 따지는 함수 50byte
  $scope.maxChk = function (val){
    var str = val;
    var strLength = 0;
    var strTitle = "";
    var strPiece = "";
    if(str === null || str === "" || str === undefined){
      return true;
    }
    for (i = 0; i < str.length; i++){
      var code = str.charCodeAt(i);
      var ch = str.substr(i,1).toUpperCase();
      //체크 하는 문자를 저장
      strPiece = str.substr(i,1)
      code = parseInt(code);
      if ((ch < "0" || ch > "9") && (ch < "A" || ch > "Z") && ((code > 255) || (code < 0))){
        strLength = strLength + 3; //UTF-8 3byte 로 계산
      }else{
        strLength = strLength + 1;
      }
      if(strLength > 500){ //제한 길이 확인
        return false;
      }else{
        strTitle = strTitle+strPiece; //제한길이 보다 작으면 자른 문자를 붙여준다.
      }
    }
    return true;
  };

  // 엑셀다운로드
  $scope.excelDownload = function(){

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUploadMPS.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles   : false,
        includeColumns      : function (column) {
          return column.visible;
        }
      }, messages["storeCloseExcept.storeCloseExcept"] + "_" + messages["storeCloseExcept.store"] + "_" + getCurDateTime() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  };
}]);
