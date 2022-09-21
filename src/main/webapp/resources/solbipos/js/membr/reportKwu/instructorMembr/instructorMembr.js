/****************************************************************
 *
 * 파일명 : instructorMembr.js
 * 설  명 : 강사별회원관리내역 JavaScript
 *
 *    수정일      수정자      Version        Function 명
 * ------------  ---------   -------------  --------------------
 * 2022.09.19     이다솜      1.0
 *
 * **************************************************************/
/**
 * get application
 */
var app = agrid.getApp();

// 구분 DropBoxDataMap
var vMembrUseYn = [
    {"name":"회원전체","value":""},
    {"name":"이용중회원","value":"1"},
    {"name":"종료회원","value":"2"}
];

/**
 *  강사별회원관리내역 조회 그리드 생성
 */
app.controller('instructorMembrCtrl', ['$scope', '$http', '$timeout', function ($scope, $http, $timeout) {

    // 상위 객체 상속 : T/F 는 picker
    angular.extend(this, new RootController('instructorMembrCtrl', $scope, $http, true));

    // 콤보박스 데이터 Set
    $scope._setComboData("teacherCdCombo", vTeacherCd);    // 강사명
    $scope._setComboData("classFgCombo", vClassFg);        // 강습구분
    $scope._setComboData("membrUseYnCombo", vMembrUseYn);  // 구분

    // grid 초기화 : 생성되기전 초기화되면서 생성된다
    $scope.initGrid = function (s, e) {

    };

    //
    $scope.$on("instructorMembrCtrl", function(event, data) {

        // 파라미터
        var params       = {};
        params.teacherCd = $scope.teacherCdCombo.selectedValue;   // 강사명
        params.classFg = $scope.classFgCombo.selectedValue;       // 강습구분
        params.membrUseYn = $scope.membrUseYnCombo.selectedValue; // 구분

        // 조회 수행 : 조회URL, 파라미터, 콜백함수
        $scope._inquiryMain("/membr/reportKwu/instructorMembr/getInstructorMembrList.sb", params, function() {});
    });

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
              }, '강사별회원관리내역' + getToday() + '.xlsx', function () {
                $timeout(function () {
                  $scope.$broadcast('loadingPopupInactive'); // 데이터 처리중 메시지 팝업 닫기
                }, 10);
              });
        }, 10);
    };

}]);