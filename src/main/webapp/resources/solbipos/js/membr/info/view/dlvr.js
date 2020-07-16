/****************************************************************
 *
 * 파일명 : dlvr.js
 * 설  명 : 배달지조회및 변경 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.15    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('dlvrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {
  // 회월 듣급

  $scope.classList = [
    {value: '1', name: '전체'},
    {value: '2', name: '기본등급'},
    {value: '3', name: '중간등급'},
    {value: '4', name: 'VIP'},
    {value: '5', name: '특별등급'},
    {value: '6', name: '미친등급'}
  ]
  $scope.memberClass = $scope.classList[0]

  $scope.areaList = [
    {value: '1', name: '전체'},
    {value: '2', name: '구로동'},
    {value: '3', name: '신림동'},
    {value: '4', name: '청담동'}
  ]
  $scope.dlArea = $scope.areaList[0]

  $scope.useYnList = [
    {value: '1', name: '전체'},
    {value: '2', name: 'Y'},
    {value: '3', name: 'N'},
  ]
  $scope.useYn = $scope.useYnList[0]

  $scope.phoneUseYnList = [
    {value: '1', name: '전체'},
    {value: '2', name: 'Y'},
    {value: '3', name: 'N'},
  ]
  $scope.phoneUseYn = $scope.phoneUseYnList[0]

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('dlvrCtrl', $scope, $http, true));

  // // grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 그리드 DataMap 설정
    $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  };

  // 조회조건 콤보박스 데이터 Set
  $scope._setComboData("listScaleBox", gvListScaleBoxData);

// grid 초기화 : 생성되기전 초기화되면서 생성된다
  $scope.initGrid = function (s, e) {
    // 합계
    // add the new GroupRow to the grid's 'columnFooters' panel
    s.columnFooters.rows.push(new wijmo.grid.GroupRow());
    // add a sigma to the header to show that this is a summary row
    s.bottomLeftCells.setCellData(0, 0, '합계');
  };

  $scope.$on("dlvrCtrl", function (event, data) {
    // $scope.searchDlvrList();
    $scope.searchDlvrTelList();
    event.preventDefault();
  });

//
  $scope.searchDlvrList = function () {
    var params = {};
    params.membrNo = $scope.membrNo
    params.membrNm = $scope.membrNm
    $scope._inquiryMain("/membr/info/dlvr/dlvr/getDlvrList.sb", params, function () {
    }, false);
  };

//
  $scope.searchDlvrTelList = function () {
    var params = {};
    params.membrNo = $scope.membrNo
    params.membrNm = $scope.membrNm
    $scope._inquiryMain("/membr/info/dlvr/dlvr/getDlvrTelList.sb", params, function () {
    }, false);
  };

  // 엑셀 다운로드
  $scope.excelDownload = function () {

    if ($scope.flex.rows.length <= 0) {
      $scope._popMsg(messages["excelUpload.not.downloadData"]); // 다운로드 할 데이터가 없습니다.
      return false;
    }

    $scope.$broadcast('loadingPopupActive', messages["cmm.progress"]); // 데이터 처리중 메시지 팝업 오픈
    $timeout(function () {
      wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
        includeColumnHeaders: true,
        includeCellStyles: true,
        includeColumns: function (column) {
          return column.visible;
        }
      }, 'excelForm' + getToday() + '.xlsx', function () {
        $timeout(function () {
          $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
        }, 10);
      });
    }, 10);
  }

  $scope.infoDelete = function (option) {
    let param
    let goUrl
    if (option === 'tel') {
      goUrl = "/membr/info/dlvr/dlvr/deleteDlvrTel" /* 통신할 URL */
    } else {
      goUrl = "/membr/info/dlvr/dlvr/deleteDlvr" /* 통신할 URL */
    }
    console.log('goUrl', goUrl);
    $http({
      method: 'POST', //방식
      url: goUrl, /* 통신할 URL */
      params: param, /* 파라메터로 보낼 데이터 */
      headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    }).then(function successCallback() {
    }, function errorCallback(response) {
      // 로딩바 hide
      $scope.$broadcast('loadingPopupInactive');
      // called asynchronously if an error occurs
      // or server returns response with an error status.
      if (response.data.message) {
        $scope._popMsg(response.data.message);
      } else {
        $scope._popMsg(messages['cmm.error']);
      }
      return false;
    }).then(function () {
      // 'complete' code here
      if (typeof callback === 'function') {
        setTimeout(function () {
          callback();
        }, 10);
      }
    });

  }

}]);