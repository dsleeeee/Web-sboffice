/****************************************************************
 *
 * 파일명 : memberExcelUpload.js
 * 설  명 : 회원포인트조정 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2020.06.23    Daniel      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

/**
 *  세금계산서 요청목록 그리드 생성
 */
app.controller('memberExcelUploadCtrl', ['$scope', '$http', function ($scope, $http) {
  // 성공내역, 실페내역
  $scope.statuList = [
    {value: '1', name: '전체'},
    {value: '2', name: '성공내역'},
    {value: '3', name: '오류내역'}
  ]
  $scope.statu = $scope.statuList[0]

  // 상위 객체 상속 : T/F 는 picker
  angular.extend(this, new RootController('memberExcelUploadCtrl', $scope, $http, true));

  // // grid 초기화 : 생성되기전 초기화되면서 생성된다
  // $scope.initGrid = function (s, e) {
  //   // 그리드 DataMap 설정
  //   $scope.statusFgDataMap = new wijmo.grid.DataMap(statusDataFg, 'value', 'name');
  // };

  // 엑셀양식 다운로드
  $scope.excelForm = function (fg) {
    console.log('11111111')
    var excelUploadScope = agrid.getScope('excelUploadCtrl');
    var uploadFg = 'vendr'

    if (fg === 'excelFormDown') {
      excelUploadScope.excelFormDownload(uploadFg);
    }

    // $scope.uploadFg = uploadFg;

    // $scope.addRow();
    //
    // $timeout(function () {
    //   wijmo.grid.xlsx.FlexGridXlsxConverter.saveAsync($scope.flex, {
    //     includeColumnHeaders: true,
    //     includeCellStyles: true,
    //     includeColumns: function (column) {
    //       return column.visible;
    //     }
    //   }, 'excelForm.xlsx');
    // }, 10);
  };

  $scope.adjustAll = function () {
    // $http({
    //   method: 'POST', //방식
    //   url: "/membr/info/point/point/adjustAll.sb", /* 통신할 URL */
    //   params: param, /* 파라메터로 보낼 데이터 */
    //   headers: {'Content-Type': 'application/json; charset=utf-8'} //헤더
    // }).then(function successCallback(response) {
    // }, function errorCallback(response) {
    //   // 로딩바 hide
    //   $scope.$broadcast('loadingPopupInactive');
    //   // called asynchronously if an error occurs
    //   // or server returns response with an error status.
    //   if (response.data.message) {
    //     $scope._popMsg(response.data.message);
    //   } else {
    //     $scope._popMsg(messages['cmm.error']);
    //   }
    //   return false;
    // }).then(function () {
    //   // 'complete' code here
    //   if (typeof callback === 'function') {
    //     setTimeout(function () {
    //       callback();
    //     }, 10);
    //   }
    // });
  }

// $scope.$on("memberExcelUploadCtrl", function (event, data) {
//   $scope.searchMemberPointList();
//   event.preventDefault();
// });

// // 후불회원 그리드 조회
// $scope.searchMemberPointList = function () {
//   var params = {};
//   $scope._inquiryMain("membr/info/point/point/getMemberPointList.sb", params, function () {
//   }, false);
// };
}])
;